package org.andy.shop.service;

import java.util.List;
import java.util.Map;

import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.entity.UserInfoPo;

/**
 * 创建时间：2015-1-30 上午11:31:34
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoService 接口
 */

public interface UserInfoService {

	// 通过Id查询UserInfo
	UserInfoPo getById(Integer id);

	public List<Map<String, Object>> getUserListByGroupCode(String groupCode)throws Exception ;

	// 添加UserInfo
	Integer save(UserInfoPo userInfo);

	//根据电话号码获取客户记录数
	Integer getByCustomerPhone(String customerPhone);
	//获取导购信息
	List<String> findAllGuideInfo();

	List<Map<String, Object>> getRefereeInfobyCustomerPhone(String customerPhone)
			throws Exception;

	List<Map<String, Object>> getRefereeInfobyEmployeeCode(String employeeCode)
			throws Exception;

	String specifysend(String userPhone);



}
