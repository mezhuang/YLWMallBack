package org.andy.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.YLConstant;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.service.UserPowerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 创建时间：2015-2-1 下午9:40:03
 * 
 * @author andy
 * @version 2.2
 * 
 * userInfo的控制层
 */
@Controller
public class UserInfoController {

	private static final Logger LOGGER = Logger
			.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserPowerService userPowerService;

//	@RequestMapping("/showInfo/{userId}")
//	public String showUserInfo(ModelMap modelMap, @PathVariable int userId) {
//		LOGGER.info("查看用户：" + userId);
//		UserInfoPo userInfo = userInfoService.getById(1);
//		modelMap.addAttribute("userInfo", userInfo);
//		return "/user/showInfo";
//	}
//	
	@RequestMapping(value="/getCustomerInfoList.do",method = {RequestMethod.GET })
	@ResponseBody
	public  List<Map<String, Object>> getCustomerInfoList() {
		LOGGER.info("json返回全部用户的信息");
		List<Map<String, Object>> userInfos = null;
		try {
			userInfos = userInfoService.getUserListByGroupCode(YLConstant.GROUP_CODE_CUSTOMER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfos;
	}
	@RequestMapping(value="/getGuideInfoList.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<String> getGuideInfoList() {
		LOGGER.info("json返回全部导购的信息");
		List<String> guideInfos = userInfoService.findAllGuideInfo();
//		LOGGER.info("guideInfos:"+guideInfos);
		return guideInfos;
	}
	
	/*申请分销
	 * 参数:
	 * */
	@RequestMapping(value="/applyToReferee.do",method = {RequestMethod.GET })
	@ResponseBody
	public String applyToReferee(@RequestParam Map<String,String> map) {
		LOGGER.info("json申请成为分销商结果");
		userPowerService.applytoReferee(map);
	
	
//		LOGGER.info("guideInfos:"+guideInfos);
		 return  "success";
	}
	/*根据分组编码，获取用户列表
	 * 参数:
	 * */
	@RequestMapping(value="/getUserInfoByGroupCode.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getUserInfoByGroupCode(@RequestParam Map<String,String> map) {
		LOGGER.info("根据分组编码，获取用户列表");
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		try {
			userList = userInfoService.getUserListByGroupCode(YLConstant.GROUP_CODE_REFEREE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
//		LOGGER.info("guideInfos:"+guideInfos);
		 return  userList;
	}
	/*根据用户openId，获取用户权限列表
	 * */
	@RequestMapping(value="/getUserPowerList.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getUserPowerList(@RequestParam Map<String,String> map) {
		LOGGER.info("json返回用户全部的权限");
		 String openId =map.get("openId");
		 List<Map<String, Object>> guideInfos = userPowerService.getUserPowerByOpenId(openId);
//		LOGGER.info("guideInfos:"+guideInfos);
		return guideInfos;
	}
}
