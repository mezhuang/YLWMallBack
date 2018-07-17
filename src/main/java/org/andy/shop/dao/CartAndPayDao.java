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

public interface  CartAndPayDao extends Serializable {


	UserInfoPo getById(Integer id);

	String addToShoppingCart(Map<String, String> map) throws Exception;

	String deleteShoppingCart(Map<String, String> map) throws Exception;

	List<Map<String, Object>> getShoppingCartListByOpenId(
			Map<String, String> map) throws Exception;

	public String updateShoppingCartNum(Map<String,String> map )throws Exception;


	

}
