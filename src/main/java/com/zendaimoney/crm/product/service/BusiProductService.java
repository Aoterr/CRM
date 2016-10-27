package com.zendaimoney.crm.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.constant.ParamConstant;
import com.zendaimoney.crm.product.entity.BusiProduct;
import com.zendaimoney.crm.product.repository.BusiProductDao;

/**
 * 
 * @author zhanghao
 * @create 2012-11-29, 下午02:08:23
 * @version 1.0
 */
@Component
@Transactional(readOnly = true)
public class BusiProductService {
	@Autowired
	private BusiProductDao busiProductDao;

	public List<BusiProduct> findAllBusiProduct() {
		return (List<BusiProduct>) busiProductDao.findAll();
	}

	/*
	 * 仅仅用来查找双鑫+ 岁月+  
	 * @author wb_lyq
	 * @date 2015-10-29,下午02:12:56
	 */
	public List<BusiProduct> findCustomizedProduct(List<Long>  ids) {
		return (List<BusiProduct>) busiProductDao.findAllProductById(ids);
	}
	
	
	/**
	 * 查找所有的信贷产品
	 * 
	 * @author zhanghao
	 * @date 2012-11-29,下午02:12:56
	 * @return
	 */
	public List<BusiProduct> findAllCreditProduct() {
		return (List<BusiProduct>) busiProductDao.findAllProductByType("1");
	}

	/**
	 * 查找所有的理财产品
	 * 
	 * @author zhanghao
	 * @date 2012-11-29,下午02:12:45
	 * @return
	 */
	public List<BusiProduct> findAllFinanceProduct() {
		return (List<BusiProduct>) busiProductDao.findAllProductByType("2");
	}

	/**
	 * 查找单个产品
	 * 
	 * @author zhanghao
	 * @date 2013-01-25 20:12:46
	 * @param id
	 *            产品id
	 * @return 产品对象
	 */
	public BusiProduct findOneProduct(Long id) {
		return busiProductDao.findOne(id);
	}

	public BusiProduct findOneProductByCode(String ptProductCode) {
		return busiProductDao.findOneByPtProductCode(ptProductCode);
	}

	/**
	 * 按照types查找产品
	 * 
	 * @author zhanghao
	 * @date 2012-11-29,下午02:12:45
	 * @return
	 */
	public List<BusiProduct> findProductByTypes(List<String> type) {
		return (List<BusiProduct>) busiProductDao.findProductByTypes(type);
	}
	
	
	
	/** 
	* @Title: findAllFinanceProductNoPlus 
	* @Description: 查询不包含plus的理财产品
	* @return List<BusiProduct>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015-10-30 下午02:21:42 
	* @throws 
	*/
	public List<BusiProduct> findAllFinanceProductNoPlus() {
		List<BusiProduct> listPro=busiProductDao.findAllProductByType("2");
		for(int i=listPro.size()-1;i>=0;i--){
			BusiProduct bt=listPro.get(i);
			if(ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXINPLUS.equals(bt.getId()+"")||ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYINGPLUS.equals(bt.getId()+""))
					listPro.remove(i);
		}
		return listPro;
	}
	
	/** 
	* @Title: findAllFinanceProductNoPlus 
	* @Description: 查询plus的理财产品
	* @return List<BusiProduct>(描述入参以及返回参数的含义)
	* @author Sam.J
	* @date 2015-10-30 下午02:21:42 
	* @throws 
	*/
	public List<BusiProduct> findAllFinanceProductPlus() {
		List<BusiProduct> listPro=busiProductDao.findAllProductByType("2");
		for(int i=listPro.size()-1;i>=0;i--){
			BusiProduct bt=listPro.get(i);
			if(ParamConstant.FORTUNE_PRODUCT_ZHENGDASHUANGXINPLUS.equals(bt.getId()+"")||ParamConstant.FORTUNE_PRODUCT_ZHENGDAYUEWENYINGPLUS.equals(bt.getId()+""))
			     continue;
			listPro.remove(i);		
		}
		return listPro;
	}
}
