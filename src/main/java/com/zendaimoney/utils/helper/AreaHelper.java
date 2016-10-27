package com.zendaimoney.utils.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.sys.area.service.AreaService;
import com.zendaimoney.sys.area.vo.AreaVo;

/**
 * 国家、区域、地区帮助类
 * 
 * @author zhanghao
 * @version $Revision:1.0.0, $Date: 2013-6-26 下午03:03:23 $
 */
public class AreaHelper {
	
	private static Log logger = LogFactory.getLog(AreaHelper.class);
	
	private static AreaService areaService = SpringContextHelper.getBean("areaService");

	private static LinkedHashMap<String, AreaVo> areaMap = new LinkedHashMap<String, AreaVo>();
	
	
	static {
		init(loadAreaData());
	}
	
	/**
	 * 初始化数据,将参数进行缓存
	 *  
	 * @author zhanghao
	 * @date 2013-6-26 下午03:23:18
	 * @param areaVoList  国家、区域、地区集合
	 */
	protected static void init(List<AreaVo> areaVoList) {
		for (AreaVo areaVo : areaVoList) {
			areaMap.put(areaVo.getId(), areaVo);
		}
	}
	
	/**
	 * 加载所有区域数据
	 *  
	 * @author zhanghao
	 * @date 2013-6-26 下午03:20:38
	 * @return
	 */
	protected static List<AreaVo> loadAreaData() {
		return areaService.getAllArea();
	}
	
	/**
	 * 重新加载系统参数数据到缓存中
	 * 
	 * @author zhanghao
	 * @date 2012-11-9,下午03:26:45
	 */
	public static void reloadAreaData() {
		areaMap.clear();
		init(loadAreaData());
	}
	
	/**
	 * 根据条件过滤
	 *  
	 * @author zhanghao
	 * @date 2013-6-26 下午03:38:19
	 * @param condtion 条件
	 * @return 符合条件的集合信息
	 */
	protected static List<AreaVo> filtrateArea(LinkedHashMap<String, String> filtrateCondtion){
		LinkedHashMap<String, AreaVo> filtrateAreaMap = new LinkedHashMap<String, AreaVo>(areaMap);	
		Iterator<Entry<String, AreaVo>> filtrateAreaIter = filtrateAreaMap.entrySet().iterator();
		AreaVo areaVo = null;
		
		while (filtrateAreaIter.hasNext()) {
			areaVo = filtrateAreaIter.next().getValue();
			if (filtrateCondtion.containsKey("level")
					&& !areaVo.getLevel().equals(filtrateCondtion.get("level"))) {
				filtrateAreaIter.remove();
			}
			if (filtrateCondtion.containsKey("fatherId")
					&& !areaVo.getFatherId().equals(
							filtrateCondtion.get("fatherId"))) {
				filtrateAreaIter.remove();
			}
		}
		return new ArrayList<AreaVo>(filtrateAreaMap.values());
	}
	
	/**
	 * 获取所有的AreaVo对象
	 * @return
	 */
	public static List<AreaVo> findAllArea() {
		return new ArrayList<AreaVo>(areaMap.values());
	}
	
	/**
	 * 根据级别查找区域信息对象
	 * @author Yuan Changchun
	 * @date 2013-6-27 下午04:10:08
	 * @param level 区域级别
	 * @return
	 */
	public static List<AreaVo> findAllAreaByLevel(String level){
		LinkedHashMap<String, String> filtrateCondtion = new LinkedHashMap<String, String>();
		filtrateCondtion.put("level", level);
		return filtrateArea(filtrateCondtion);
	}
	
	/**
	 * 根据区域父ID查找区域信息对象
	 * @author Yuan Changchun
	 * @date 2013-6-27 下午04:20:15
	 * @param fatherId
	 * @return
	 */
	public static List<AreaVo> findAllAreaByFatherId(String fatherId){
		LinkedHashMap<String, String> filtrateCondtion = new LinkedHashMap<String, String>();
		filtrateCondtion.put("fatherId", fatherId);
		return filtrateArea(filtrateCondtion);
	}
	
	/**
	 * 根据ID查找单个地区对象
	 *  
	 * @author zhanghao
	 * @date 2013-6-27 下午03:08:58
	 * @param id  区域ID
	 * @return
	 */
	public static AreaVo findOneArea(String id){
		return areaMap.get(id);
	}
	
	/**
	 * 格式化地址
	 *  
	 * @author zhanghao
	 * @date 2013年8月2日 上午11:40:19
	 * @param id
	 * @return
	 */
	public static String formatArea(String id){
		return findOneArea(id).getName();
	}
	
}
