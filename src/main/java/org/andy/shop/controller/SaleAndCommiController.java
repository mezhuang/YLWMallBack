package org.andy.shop.controller;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.YLConstant;
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
	


	
	/*申请分销
	 * 参数:
	 * */
	@RequestMapping(value="/addSaleInfoAndCommi.do",method = {RequestMethod.GET })
	@ResponseBody
	public String addSaleInfoAndCommi(@RequestParam Map<String,String> map)  {
		LOGGER.info("导购增加销售信息");
		LOGGER.info("保护期时长protectDays:"+YLConstant.protectDays);
		String result ="success";
		//新增销售信息
		String ret="";
		try {
				ret = saleInfoService.addSaleInfo(map);
				
				
				if(ret.equals("1"))
				{
					LOGGER.info("给分销商结佣");
					String isTask=null;//是否有分销人员带看
					int transMoney=0;//成交价钱
					String customerPhone = map.get("customerPhone");
					String customerName  = map.get("customerName");
					String transTime  = map.get("transTime");
					String productInfo=map.get("productInfo");
		
					transMoney=Integer.valueOf((String)map.get("transMoney"));
					
					//1、根据客户电话，获取报备时间、分销人员姓名、电话
					List<Map<String, Object>>	refereeList = userInfoService.getRefereeInfobyCustomerPhone(customerPhone);	
					String reFereeUserPhone=null;
					String employeeCode=null;//分销经理员工编码
					String reportTime=null;//
		
					
					for(Map<String, Object> referMap :refereeList)
					{
						reportTime=(String)referMap.get("report_time");
						//判断报备时间是否在保护期内
						int diffDay =(int)Utils.getDiffDays(transTime, reportTime);
						if((diffDay+1)<YLConstant.protectDays)
						{
							reFereeUserPhone = (String) referMap.get("user_phone");
							employeeCode=(String)referMap.get("employee_code");
							break;
						}
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
							double commissionSize=transMoney*YLConstant.refereeManagerTaskRadio;//带看费率
							
							commisionMap.put("commiRatio",String.valueOf(YLConstant.refereeManagerTaskRadio));
							commisionMap.put("commiMoney", String.valueOf(commissionSize));
							
						}else
						{
							double commissionSize=transMoney*YLConstant.refereeManagerNoTaskRadio;
							
							commisionMap.put("commiRatio",String.valueOf(YLConstant.refereeManagerNoTaskRadio));
							commisionMap.put("commiMoney", String.valueOf(commissionSize));
							
						}
						commisionMap.put("commiStatus", "1");
						commisionMap.put("refereePhone", reFereeUserPhone);
						commisionMap.put("customerPhone", customerPhone);
						commisionMap.put("customerPame", customerName);
						commisionMap.put("isTask", isTask);
						commisionMap.put("productInfo", productInfo);
						commissionService.addCommissioin(commisionMap);
						
					}else
					{
					
					//5、如果是分销经纪人，还要判断是否给分销经理提成
						if(isTask=="1")
						{
							double commissionSize=transMoney*YLConstant.refereeTaskRadio;//带看费率
							
							commisionMap.put("commiRatio", String.valueOf(YLConstant.refereeTaskRadio));
							commisionMap.put("commiMoney", String.valueOf(commissionSize));
							
						}else
						{
							double commissionSize=transMoney*YLConstant.refereeNoTaskRadio;
							
							commisionMap.put("commi_ratio", String.valueOf(YLConstant.refereeNoTaskRadio));
							commisionMap.put("commi_money", String.valueOf(commissionSize));
							
						}
						commisionMap.put("commiStatus", "1");
						commisionMap.put("refereePhone", reFereeUserPhone);
						commisionMap.put("customerPhone", customerPhone);
						commisionMap.put("customerName", customerName);
						commisionMap.put("isTask", isTask);
						
						commissionService.addCommissioin(commisionMap);
						
					
					//6、判断是否存在上级(销售经理)，若有，则新增销售经理提成
						//1、根据客户电话，获取分销人员姓名和电话
						if(!(employeeCode==null||"".equals(employeeCode))){
							List<Map<String, Object>>	superiorRefereeList = userInfoService.getRefereeInfobyEmployeeCode(employeeCode);		
							if(isTask=="1")
							{
								double superiorCommissionSize=transMoney*YLConstant.superiorRefereeTaskRadio;//分销商带看费率
								
								commisionMap.put("commiRatio", String.valueOf(YLConstant.superiorRefereeTaskRadio));
								commisionMap.put("commiMoney", String.valueOf(superiorCommissionSize));
								
							}else
							{
								double superiorCommissionSize=transMoney*YLConstant.superiorRefereeNoTaskRadio;//分销经理不带看费率
								
								commisionMap.put("commiRatio", String.valueOf(YLConstant.superiorRefereeNoTaskRadio));
								commisionMap.put("commiMoney", String.valueOf(superiorCommissionSize));
								
							}
							commisionMap.put("commiStatus", "1");
							commisionMap.put("refereePhone", reFereeUserPhone);
							commisionMap.put("customerPhone", customerPhone);
							commisionMap.put("customerName", customerName);
							
							commissionService.addCommissioin(commisionMap);
			
						}
						
					}
				}else
				{
					result="新增销售信息异常，结佣失败!";
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		 return result ;
	}
	/*根据导购openId，获取录入的销售信息
	 * */
	@RequestMapping(value="/getSaleInfoListByGuideOpenId.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getSaleInfoListByGuideOpenId(@RequestParam Map<String,String> map)  {
		LOGGER.info("json返回各个导购录入的销售信息列表");
		 String openId =map.get("openId");
		// List<Map<String, Object>> guideInfos = userPowerService.getUserPowerByOpenId(openId);
//		LOGGER.info("guideInfos:"+guideInfos);
		 List<Map<String, Object>> salesInfolist= new ArrayList<Map<String, Object>>();
		try {
			salesInfolist = saleInfoService.getSaleInfoList(openId, "0", "10");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return salesInfolist;
	}
	
}






