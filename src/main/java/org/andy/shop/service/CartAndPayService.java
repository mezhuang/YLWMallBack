package org.andy.shop.service;

import java.util.List;
import java.util.Map;

import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.entity.UserInfoPo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建时间：2015-1-30 上午11:31:34
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoService 接口
 */

public interface CartAndPayService {

	// 通过Id查询UserInfo
	UserInfoPo getById(Integer id);
	public Map<String,String> getPrePayIdByOrderInfo(@RequestParam Map<String,String> map) throws Exception ;



}
