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

public interface  UserInfoDao extends Serializable {


	UserInfoPo getById(Integer id);

	List<UserInfoPo> findAll();

	Integer save(UserInfoPo entity);

	Integer getByCustomerPhone(String customerPhone);

	List<Map<String, Object>> getUserListByGroupCode(String groupCode)throws Exception;

	List<Map<String, Object>> getRefereeInfobyCustomerPhone(String customerPhone)
			throws Exception;

	Integer addReceiGoodsAdress(Map<String, String> map);

	List<Map<String, Object>> getReceiGoodsAdressByOpenId(
			Map<String, String> map) throws Exception;

	

}
