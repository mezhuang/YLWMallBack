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

public interface CommissionService {


	String addCommissioin(Map<String, String> Map) throws Exception;

	List<Map<String, Object>> getCommissioinInfo(String openId)
			throws Exception;

	

}
