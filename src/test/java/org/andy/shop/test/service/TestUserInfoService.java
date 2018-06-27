package org.andy.shop.test.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.CustomerReportDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.CustomerReportService;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.service.UserPowerService;
import org.andy.shop.test.service.HttpRequestMyTest1;
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
	private UserPowerService userPowerService;
	
	@Autowired
	private CustomerReportService customerReportService;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired 
	private CustomerReportDao customerReportDao;
	
	@Autowired
	private UserPowerDao userPowerDao;
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
//测试客户报备列表
//	@Test
//	public void testHttpRequestTest() {
//		HttpRequestMyTest1 httpRequestTest =new HttpRequestMyTest1();
//		String param="openId=ozLOG5LWXLjDpxZLrD1DxXIKxHWg&startIndex=1&indexSize=5";
//		String returnStr =  httpRequestTest.sendGet("http://localhost:80/YLXcxMallBack/getCustomerReportList.do", param);
//		System.out.println(returnStr);
//		LOGGER.info("returnStr:"+returnStr);
////		String param ="";
//		//test hello
////		String returnStr =  httpRequestTest.sendGet("http://localhost/YLXcxMallBack/", param);
////		System.out.println(returnStr);
////		LOGGER.info("returnStr:"+returnStr);
//}
	//测试申请分销商
	@Test 
	public void testpath() {
		//获取当前路径
		 File directory = new File("");//设定为当前文件夹 
		 try{ 
		     System.out.println("当前路径:"+directory.getCanonicalPath());//获取标准的路径 
		     System.out.println(directory.getAbsolutePath());//获取绝对路径 
		 }catch(Exception e){
			 e.printStackTrace();
		 } 
	}
//	
	
	//测试申请分销商
//	@Test 
//	public void testapplyToReferee() {
//		
//		UUID uuid1=UUID.randomUUID();
//	     String userId = uuid1.toString(); 
//		 String openId ="fffff";
//		 String userName ="xiaoliu";
//		 String userPhone ="18812345678";
//		 String userRegTime = Utils.getCurrentDate();
//		 Map<String,String> map = new HashMap<String,String>();
//		 map.put("openId", openId);
//		 map.put("userName", userName);
//		 map.put("userPhone", userPhone);
//		 map.put("userRegTime", userRegTime);
//		 userPowerService.applytoReferee(map);
//		
////		String param ="";
//		//test hello
////		String returnStr =  httpRequestTest.sendGet("http://localhost/YLXcxMallBack/", param);
////		System.out.println(returnStr);
////		LOGGER.info("returnStr:"+returnStr);
//}
	

}
