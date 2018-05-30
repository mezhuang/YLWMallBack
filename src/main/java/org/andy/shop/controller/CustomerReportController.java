package org.andy.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.AddCustomerReportPo;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.service.CustomerReportService;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.common.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 创建时间：2015-2-1 下午9:40:03
 * 
 * @author andy
 * @version 2.2
 * 
 * userInfo的控制层
 */
@Controller
public class CustomerReportController {

	private static final Logger LOGGER = Logger
			.getLogger(CustomerReportController.class);
	@Autowired
	private CustomerReportService customerReportService;

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoDao userInfoDao;

//	@RequestMapping("/showInfo/{userId}")
//	public String showUserInfo(ModelMap modelMap, @PathVariable int userId) {
//		LOGGER.info("查看用户：" + userId);
//		UserInfo userInfo = customerReportService.getById(userId);
//		modelMap.addAttribute("userInfo", userInfo);
//		return "/user/showInfo";
//	}

//	@RequestMapping("/getCustomerReportList")
//	public @ResponseBody List<CustomerReport> getCustomerReportList() {
//		LOGGER.info("json返回全部用户的信息");111111111111111110000000000000000000
//		List<UserInfo> userInfos = userInfoService.findAll();
//		return userInfos;
//	}·······
	//新增客户报备
//	@RequestMapping("/addCustomerReport.do")
//	public boolean addCustomerReport(@RequestBody CustomerReportPo customerReportPo) {
@RequestMapping(value = "/addCustomerReport.do",method = {RequestMethod.POST })
@ResponseBody
public String addCustomerReport(@RequestParam Map<String,String> map){ // spring MVC只能解析外层的json格式，内部的bean转化为Map格式的键值对，需要对map解析

//		 List<UserInfoPo> userList = new ArrayList<UserInfoPo>();
		// JSONObject object = JSONObject.fromObject();
//		    JSONArray array = JSONArray.fromObject(usersList);
//		    String jsonstr = array.toString();

		 //新增意向客户
		 UserInfoPo customerInfo = new UserInfoPo();	 
//		 UUID uuid1=UUID.randomUUID();
//	     String customerId = uuid1.toString(); 
//	     customerInfo.setUserId(customerId);
		 
		 
		 String customerPhone = map.get("customerPhone");
		 LOGGER.info("客户号码：" + customerPhone);
		 //客戶电话号码为空，则返回
		 if(customerPhone.isEmpty())
		 {

			 return "10001";
		 }	
		 //获取是否已报备记录
		 int customerPhoneCount  = 0;
			try{
			customerPhoneCount = userInfoDao.getByCustomerPhone(customerPhone);
			System.out.println("count:"+customerPhoneCount);
			LOGGER.info("console-count:"+customerPhoneCount);
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
		//获取报备时长
			
		//客户已存在报备且未过期，返回
		 if(customerPhoneCount>0)
		 {
			 return "10002"; 
		 }
		 
		 customerInfo.setUserName(map.get("customerName"));
		 customerInfo.setUserPhone(customerPhone);
		 customerInfo.setUserType("1");
		 userInfoService.save(customerInfo);
		 //新增带看人
		 UserInfoPo taskInfo = new UserInfoPo();
		 taskInfo.setUserName(map.get("taskName"));
		 taskInfo.setUserPhone(map.get("taskPhone"));
		 taskInfo.setUserType("2");
		 userInfoService.save(taskInfo);
	 
		 
		 //新增客户报备表
		 CustomerReportPo customerReportPo =new CustomerReportPo();
		 customerReportPo.setCustomerPhone(map.get("customerPhone"));
		 String guidePhone = map.get("guidePhone");
		 LOGGER.info("导购信息：" + guidePhone);
		 customerReportPo.setGuidePhone(guidePhone);
		 customerReportPo.setTaskPhone(map.get("taskPhone"));
		 customerReportPo.setVisitTime(map.get("visitTime"));
		 customerReportPo.setIsTask(map.get("isTask"));
		 customerReportPo.setRemark(map.get("remark"));
		 customerReportPo.setOpenId(map.get("openId"));
		 customerReportService.save(customerReportPo);

//	      for(Map<String,String> map : usersList){
//	    	  UserInfoPo userInfoPo = new UserInfoPo();
//	    	  String customerName = map.get("customerName");
//    	  LOGGER.info("客户姓名：" + customerName);
//	    	  userInfoPo.setUserName(map.get("customerPhone"));
//	    	  LOGGER.info("客户号码：" + customerName);
//	          userList.add(userInfoPo);
//	      }
//	      System.out.println("报备结束");
	      return  "success";
	     // 这里就可以使用 userList 了
	 }
@RequestMapping(value = "/getCustomerReportList.do",method = {RequestMethod.GET })
@ResponseBody
 public  List<Map<String, Object>> getCustomerReportList(@RequestParam Map<String,String> map) {
	 String openId= map.get("openId");
	 String startIndex =map.get("startIndex");
	 String indexSize =map.get("indexSize");
	//			LOGGER.info("json返回全部导购的信息");
	List<Map<String, Object>> customerReportInfos = customerReportService.getAllReportcustomerList(openId,startIndex,indexSize);
		LOGGER.info("guideInfos:"+customerReportInfos);
	return customerReportInfos;
}
 
//	public String toIndex(ModelMap modelMap,)) {  
//	
//		String str="OK";
//		System.out.println("come in here!");
////		LOGGER.info("查看用户：" + customerReportPo);
////		
////		UserInfoPo userInfoPo = new UserInfoPo();
////		userInfoService.save(userInfoPo);
////		modelMap.addAttribute("CustomerReport", userInfo);
////		CustomerReportPo customerReportPo =new CustomerReportPo();
//		
////		customerReportService.save(customerReportPo);
//		
//		return str;
//	}
	 @RequestMapping(value="/hello.do")  
	    public String toIndex(String hello,ModelMap model) {  
	          
	        model.addAttribute("helloworld", "hello "+hello);  
	        return "index";  
	    }  
}
