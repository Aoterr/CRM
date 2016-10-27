package org.springside.modules.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.orm.SearchFilter.Operator;

import com.google.common.collect.Lists;
import com.zendaimoney.crm.BaseController;
import com.zendaimoney.utils.Collections3;
/**
 * 高级查询组合接口
 *
 */
public class DynamicSpecifications {
	private static final ConversionService conversionService = new DefaultConversionService();
	 
	/**
	 * 拼接查询条件
	 * @param filters
	 * @param otherFilters
	 * @param clazz
	 * @param shareType
	 * @param gatherParam
	 * @return
	 */
	public static <T> Specification<T> bySearchFilter(
			final Collection<SearchFilter> filters,final Collection<SearchFilter> otherFilters, final Class<T> clazz,
			final Integer shareType, final String gatherParam) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {								
				List<Predicate> otherPredicates = Lists.newArrayList();
				if(Collections3.isNotEmpty(otherFilters)){
					otherPredicates = Lists.newArrayList();
					initPredicate(otherPredicates, otherFilters, root, builder);
				}
				 
				if (Collections3.isNotEmpty(filters) || otherPredicates.size()>0) {
					ArrayList<SearchFilter> filtersList = Lists.newArrayList();
					ArrayList<SearchFilter> sameFilters = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						filtersList.add(filter);
					}

					for (int i = 0; i < filtersList.size() - 1; i++) {
						for (int j = filtersList.size() - 1; j > i; j--) {
							if (filtersList.get(j).fieldName.equals(filtersList
									.get(i).fieldName)) {
								sameFilters.add(filtersList.get(j));
								sameFilters.add(filtersList.get(i));
							}
						}
					}
					filtersList.removeAll(sameFilters);
					boolean isOr = false;
					List<Predicate> predicates = Lists.newArrayList();
					List<Predicate> predicatesSame = Lists.newArrayList();
					isOr = initPredicate(predicates, filtersList, root, builder);
					initPredicate(predicatesSame, sameFilters, root, builder);

					if (predicates.size() > 0 ||predicatesSame.size() > 0 || otherPredicates.size()>0) {
						if (isOr) {
							List<Predicate> predicates_or = Lists
									.newArrayList();
							if (otherPredicates.size()>0)
								predicates_or.addAll(otherPredicates);
							
							if (predicatesSame.size() > 0)
								predicates.add(builder.or(predicatesSame
										.toArray(new Predicate[predicatesSame
												.size()])));
							if (predicates.size() > 0)
								predicates_or.add(builder.or(predicates
										.toArray(new Predicate[predicates
												.size()])));
							
							
							return builder
									.and(predicates_or
											.toArray(new Predicate[predicates_or
													.size()]));
						} else {
							if (otherPredicates.size()>0)
								predicates.addAll(otherPredicates);
							predicates.addAll(predicatesSame);
							return builder.and(predicates
									.toArray(new Predicate[predicates.size()]));
						}
					}
				}

				return builder.conjunction();
			}
		};
	}

	private static <T> boolean initPredicate(List<Predicate> predicates,
			Collection<SearchFilter> filters, Root<T> root,
			CriteriaBuilder builder) {
		boolean isOr = false;
		for (SearchFilter filter : filters) {
			if (filter.fieldName.equals(BaseController.ISORFIX)) {
				isOr = Boolean.parseBoolean(filter.value.toString());
				continue;
			}
			String[] names = StringUtils.split(filter.fieldName, ".");
			Path expression = root.get(names[0]);
			for (int i = 1; i < names.length; i++) {
				expression = expression.get(names[i]);
			}
			Class attributeClass = expression.getJavaType();		
			if (!attributeClass.equals(String.class)
					&& filter.value instanceof String
					&& conversionService.canConvert(String.class,
							attributeClass)
					&& !filter.operator.toString().equals(Operator.IN.toString())
							) {
					filter.value = conversionService.convert(filter.value,attributeClass);				
			}

			switch (filter.operator) {
			case EQ:
				predicates.add(builder.equal(expression, filter.value));
				break;
			case LIKE:
				predicates.add(builder.like(expression, "%" + filter.value
						+ "%"));
				break;
			case GT:
				predicates.add(builder.greaterThan(expression,
						(Comparable) filter.value));
				break;
			case LT:
				predicates.add(builder.lessThan(expression,
						(Comparable) filter.value));
				break;
			case GTE:
				predicates.add(builder.greaterThanOrEqualTo(expression,
						(Comparable) filter.value));
				break;
			case LTE:
				predicates.add(builder.lessThanOrEqualTo(expression,
						(Comparable) filter.value));
				break;
			case IN:
				Object[] value = StringUtils.split(filter.value.toString(), ",");
				predicates.add(expression.in(value));
				break;
			case ISNULL:
				predicates.add(builder.isNull(expression));
				break;
			case NOTIN:
				Object[] valueNot = StringUtils.split(filter.value.toString(), ",");
				predicates.add(builder.not(expression.in(valueNot)));
				break;
			}
		}
		return isOr;
	}
}
