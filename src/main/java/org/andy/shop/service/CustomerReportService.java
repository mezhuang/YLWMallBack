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

public interface CustomerReportService {

	// 通过Id查询UserInfo
	CustomerReportPo getById(Integer id);

	// 查询全部的UserInfo
	List<CustomerReportPo> findAll();

	// 添加CustomerReport
	Integer save(CustomerReportPo customerReport);

	public List<Map<String, Object>> getAllReportcustomerList(String openId,String startIndex,String indexSize) ;
	
}
