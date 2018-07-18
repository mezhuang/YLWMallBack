package org.andy.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.YLConstant;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.qcloudsms.YLQCloudsms;
import org.andy.shop.service.GroupMapService;
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
	@Autowired
	private GroupMapService groupMapService;

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
		String reStr="fail";
		//根据分销经纪人分组编码，获取分销经纪人组ID
		try {

			String groupId = groupMapService.getByGroupIdByGroupCode(YLConstant.GROUP_CODE_REFEREE);
			map.put("groupId", groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ret = userPowerService.applytoReferee(map);
		if("1".equals(ret))
		{
			reStr= "success";
		}
	
//		LOGGER.info("guideInfos:"+guideInfos);
		 return reStr;
	}
	//增加收货地址
	@RequestMapping(value="/addReceiGoodsAdress.do",method = {RequestMethod.POST })
	@ResponseBody
	public String addReceiGoodsAdress(@RequestParam Map<String,String> map) {
		LOGGER.info("json申请成为分销商结果");
		String reStr="fail";
		//根据分销经纪人分组编码，获取分销经纪人组ID
		Integer addRet=0;
		try {

			 addRet = userInfoService.addReceiGoodsAdress(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reStr.valueOf(addRet);
	
//		LOGGER.info("guideInfos:"+guideInfos);
		 return reStr;
	}
	//修改收货地址
	@RequestMapping(value="/updateReceiGoodsAdress.do",method = {RequestMethod.POST })
	@ResponseBody
	public String updateReceiGoodsAdress(@RequestParam Map<String,String> map) {
		LOGGER.info("修改收货地址");
		String reStr="fail";
		//根据分销经纪人分组编码，获取分销经纪人组ID
		Integer addRet=0;
		try {

			 addRet = userInfoService.updateReceiGoodsAdress(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reStr.valueOf(addRet);
	
//		LOGGER.info("guideInfos:"+guideInfos);
		 return reStr;
	}

	/*根据用户openId，获取用户权限列表
	 * */
	@RequestMapping(value="/getUserPowerList.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getUserPowerList(@RequestParam Map<String,String> map) {
		LOGGER.info("json返回用户全部的权限");
		 String openId =map.get("openId");
		 List<Map<String, Object>> guideInfos = userPowerService.getUserPowerByOpenId(openId);
		return guideInfos;
	}
	
	/*根据分组编码，获取用户列表
	 * 参数:
	 * */
	@RequestMapping(value="/getUserInfoByGroupCode.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getUserInfoByGroupCode(@RequestParam Map<String,String> map) {
		LOGGER.info("根据分组编码，获取用户列表");
		String requestGroupCode=map.get("groupCode");
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		try {
			userList = userInfoService.getUserListByGroupCode(requestGroupCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 return  userList;
	}
	/*根据用户ID和分组编码，设置用户岗位
	 * 参数:
	 * */
	@RequestMapping(value="/setUserPosistionByUserIdAndGroupId.do",method = {RequestMethod.GET })
	@ResponseBody
	public String setUserPosistionByUserIdAndGroupId(@RequestParam Map<String,String> map) {
		LOGGER.info("setRefereePosition.do...");
		String resultStr="0";
		String userId = map.get("userId");
		String groupCode = map.get("groupCode");
		
		try {
		String groupId = groupMapService.getByGroupIdByGroupCode(groupCode);
		
			 Map<String,String> addMap = new HashMap<String, String>(); 
			 addMap.put("userId", userId);
			 addMap.put("groupId", groupId);
			 resultStr =groupMapService.addUserToGroupMap(addMap) ;//新增分销经理成员关系
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 return  resultStr;
	}
	/*根据用户ID和分组编码，删除用户岗位
	 * 参数:
	 * */
	@RequestMapping(value="/delUserPosistionByUserIdAndGroupId.do",method = {RequestMethod.GET })
	@ResponseBody
	public String delUserPosistionByUserIdAndGroupId(@RequestParam Map<String,String> map) {
		LOGGER.info("setRefereePosition.do...");
		String resultStr="0";
		String userId = map.get("userId");
		String groupCode = map.get("groupCode");
		
		try {
			 String groupId = groupMapService.getByGroupIdByGroupCode(groupCode);
			 Map<String,String> delMap = new HashMap<String, String>(); 
			 delMap.put("userId", userId);
			 delMap.put("groupId", groupId);
			 resultStr =groupMapService.deleteGroupMapByUserIdAndGroupId(delMap) ;//新增分销经理成员关系
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		 return  resultStr;
	}
	//根据手机号，获取手机验证码
	@RequestMapping(value="/getUserPhoneVerification.do",method = {RequestMethod.GET })
	@ResponseBody
	public String getUserPhoneVerification(@RequestParam Map<String,String> map) {
		LOGGER.info("setRefereePosition.do...");
		String resultStr="0";
		String userPhone = map.get("userPhone");
		resultStr = userInfoService.specifysend(userPhone);
	
		 return  resultStr;
	}
	
	
	@RequestMapping(value="/getReceiGoodsAdressList.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getReceiGoodsAdressList(@RequestParam Map<String,String> map) {
		LOGGER.info("json返回用户全部的权限");
		 List<Map<String, Object>> guideInfos = null;
		try {
			guideInfos = userInfoService.getReceiGoodsAdressByOpenId(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return guideInfos;
	}
}
