package org.andy.shop.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.andy.shop.entity.UserInfoPo;

/**
 * 创建时间：2015-1-30 上午11:22:37
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoDao
 */

public interface  SaleInfoDao extends Serializable {


	String addSaleInfo(Map<String, String> Map) throws Exception;


	List<Map<String, Object>> getSaleInfoList(String openId, String startIndex,
			String indexSize)throws Exception;


	String addGoodsOnlineOrder(Map<String, String> map) throws Exception;


	String addGoodsdetailList(Map<String, String> map) throws Exception;


	List<Map<String, Object>> getGoodsOrderListByOpenId(Map<String, String> map)
			throws Exception;


	String updateOrderStatus(Map<String, String> map) throws Exception;


}
