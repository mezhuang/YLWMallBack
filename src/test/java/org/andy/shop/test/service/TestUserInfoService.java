package org.andy.shop.test.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.dao.CustomerReportDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.CustomerReportService;
import org.andy.shop.service.UserInfoService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * 创建时间：2015-1-30 下午12:01:47
 * 
 * @author andy
 * @version 2.2
 */
// 引入Spring环境测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestUserInfoService {

	private static final Logger LOGGER = Logger
			.getLogger(TestUserInfoService.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CustomerReportService customerReportService;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired 
	private CustomerReportDao customerReportDao;
/*
	@Test
	public void testGetAllGuideInfo() {
		//测试查询全部用户

	}
	
	@Test
	public void testGetCustomerReportRecords() {

		
		List<Map<String, Object>> customerInfoList    = customerReportService.getAllReportcustomerList("ozLOG5LWXLjDpxZLrD1DxXIKxHWg","1","10");

		LOGGER.info("customerInfoList:"+customerInfoList);
		for(Map map:customerInfoList)
		{
			System.out.println(map.get("customer_name"));
			System.out.println(map.get("customer_phone"));
			System.out.println(map.get("visit_time"));
		}

	}

*/

	@Test
	public void testHttpRequestTest() {
		HttpRequestTest httpRequestTest =new HttpRequestTest();
		String param="openId=ozLOG5LWXLjDpxZLrD1DxXIKxHWg&startIndex=1&indexSize=5";
		String returnStr =  httpRequestTest.sendGet("http://203.195.200.199/springmvc_demo/getCustomerReportList.do", param);
		System.out.println(returnStr);
		LOGGER.info("returnStr:"+returnStr);
}
	

}
