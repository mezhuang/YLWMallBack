package org.andy.shop.controller;

import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.Utils;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.CommissionService;
import org.andy.shop.service.SaleInfoService;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.service.UserPowerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class SaleAndCommiController {

	private static final Logger LOGGER = Logger
			.getLogger(SaleAndCommiController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserPowerService userPowerService;
	@Autowired
	private SaleInfoService saleInfoService;
	@Autowired
	private CommissionService commissionService;
	@Value("#(protect.days)")
	private Integer protectDays;
	@Value("#{refereeManager.noTaskRadio}")  
	private String refereeManager_noTaskRadio;
	@Value("#{refereeManager.taskRadio}")  
	private String refereeManager_taskRadio;
	@Value("#{referee.noTaskRadio}")  
	private String referee_noTaskRadio;
	@Value("#{referee.taskRadio}")  
	private String referee_taskRadio;
	@Value("#{superiorReferee.taskRadio}")  
	private String superiorRefereeTaskRadio;
	@Value("#{superiorReferee.noTaskRadio}")  
	private String superiorRefereeNTaskRadio;
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
	public  List<UserInfoPo> getCustomerInfoList() {
		LOGGER.info("json返回全部用户的信息");
		List<UserInfoPo> userInfos = userInfoService.findAll();
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
	/*获取全部导购信息
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
	/*申请分销
	 * 参数:
	 * */
	@RequestMapping(value="/addSaleInfoAndCommi.do",method = {RequestMethod.GET })
	@ResponseBody
	public String addSaleInfoAndCommi(@RequestParam Map<String,String> map) throws Exception {
		LOGGER.info("导购增加销售信息");
		String result ="success";
		//新增销售信息
		String ret = saleInfoService.addSaleInfo(map);
		
		if(ret.equals("success"))
		{
			String isTask=null;//是否有分销人员带看
			int transMoney=0;//成交价钱
			String customerPhone = map.get("customerPhone");
			String customerName  = map.get("customerName");
			String transTime  = map.get("transTime");

			transMoney=Integer.valueOf((String)map.get("transMoney"));
			
			//1、根据客户电话，获取报备时间、分销人员姓名、电话
			List<Map<String, Object>>	refereeList = userInfoService.getRefereeInfobyCustomerPhone(customerPhone);	
			String reFereeUserPhone=null;
			String employeeCode=null;//分销经理员工编码
			String reportTime=null;//

			
			for(Map<String, Object> referMap :refereeList)
			{
				reFereeUserPhone = (String) referMap.get("user_phone");
				employeeCode=(String)referMap.get("employee_code");
				reportTime=(String)referMap.get("trans_time");
			}
			//判断报备时间是否在保护期内
			int diffDay =(int)Utils.getDiffDays(transTime, reportTime);
			if((diffDay+1)<protectDays)
			{
				result="outProtectDays";
			}

			//2、根据分销员电话，判断是分销经纪人还是销售经理
			int isrefereeManager = userPowerService.isRefereeManger(reFereeUserPhone);
			Map<String, String>  commisionMap = new HashMap<String, String>();		
			if(isrefereeManager==1)//分销经理
			{
			//4、如果是销售经理，则直接结算；
			
//				commi_id
//				commi_level
//				commi_ratio
//				commi_money
//				commi_status
//				referee_phone
//				customer_phone
//				customer_name
				if(isTask=="1")
				{
					int manTaskRadio = Integer.valueOf(refereeManager_taskRadio); //带看费率
					int commissionSize=transMoney*manTaskRadio;
					
					commisionMap.put("commi_ratio", "5%");
					commisionMap.put("commi_money", String.valueOf(commissionSize));
					
				}else
				{
					int manNoTaskRadio = Integer.valueOf(refereeManager_noTaskRadio); //分销经理不带看费率
					int commissionSize=transMoney*manNoTaskRadio;
					
					commisionMap.put("commi_ratio", refereeManager_taskRadio);
					commisionMap.put("commi_money", String.valueOf(commissionSize));
					
				}
				commisionMap.put("commi_status", "1");
				commisionMap.put("referee_phone", reFereeUserPhone);
				commisionMap.put("customer_phone", customerPhone);
				commisionMap.put("customer_name", customerName);
				
				commissionService.addCommissioin(commisionMap);
				
			}else
			{
			
			//5、如果是分销经纪人，还要判断是否给分销经理提成
				if(isTask=="1")
				{
					int TaskRadio = Integer.valueOf(referee_taskRadio); //带看费率
					int commissionSize=transMoney*TaskRadio;
					
					commisionMap.put("commi_ratio", referee_taskRadio);
					commisionMap.put("commi_money", String.valueOf(commissionSize));
					
				}else
				{
					int NoTaskRadio = Integer.valueOf(referee_noTaskRadio); //分销经理不带看费率
					int commissionSize=transMoney*NoTaskRadio;
					
					commisionMap.put("commi_ratio", referee_noTaskRadio);
					commisionMap.put("commi_money", String.valueOf(commissionSize));
					
				}
				commisionMap.put("commi_status", "1");
				commisionMap.put("referee_phone", reFereeUserPhone);
				commisionMap.put("customer_phone", customerPhone);
				commisionMap.put("customer_name", customerName);
				
				commissionService.addCommissioin(commisionMap);
				
			
			//6、判断是否存在上级(销售经理)，若有，则新增销售经理提成
				//1、根据客户电话，获取分销人员姓名和电话
				if(!(employeeCode==null||"".equals(employeeCode))){
					List<Map<String, Object>>	superiorRefereeList = userInfoService.getRefereeInfobyEmployeeCode(employeeCode);		
					if(isTask=="1")
					{
						int superiorTaskRadioTmp = Integer.valueOf(superiorRefereeTaskRadio); //带看费率
						int superiorCommissionSize=transMoney*superiorTaskRadioTmp;
						
						commisionMap.put("commi_ratio", superiorRefereeTaskRadio);
						commisionMap.put("commi_money", String.valueOf(superiorCommissionSize));
						
					}else
					{
						int superiorNotaskRadioTmp = Integer.valueOf(superiorRefereeNTaskRadio); //分销经理不带看费率
						int superiorCommissionSize=transMoney*superiorNotaskRadioTmp;
						
						commisionMap.put("commi_ratio", superiorRefereeNTaskRadio);
						commisionMap.put("commi_money", String.valueOf(superiorCommissionSize));
						
					}
					commisionMap.put("commi_status", "1");
					commisionMap.put("referee_phone", reFereeUserPhone);
					commisionMap.put("customer_phone", customerPhone);
					commisionMap.put("customer_name", customerName);
					
					commissionService.addCommissioin(commisionMap);
					
					
				}
					
				
			}
		}
	
		 return result ;
	}
}






