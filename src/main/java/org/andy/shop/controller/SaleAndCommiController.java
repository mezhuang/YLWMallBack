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

import com.alibaba.fastjson.JSON;

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
		LOGGER.info("保护期时长protectDays:"+YLConstant.protectDays_referee);
		String result ="success";
		//新增销售信息
		String ret="";
		try {
				ret = saleInfoService.addSaleInfo(map);
				
				//新增销售信息成功，则立马结佣
				if(ret.equals("1"))
				{
					LOGGER.info("给分销商结佣");
					String isTask=map.get("isTask");//是否有分销人员带看
					
					String customerPhone = map.get("customerPhone");
					String customerName  = map.get("customerName");
					String transTime  = map.get("transTime");
					String productInfo=map.get("productInfo");
					int transMoney=0;//成交价钱
					transMoney=Integer.valueOf((String)map.get("transMoney"));
					
					//1、根据客户电话，获取报备时间、分销人员姓名、电话、上级电话
					List<Map<String, Object>>	refereeList = userInfoService.getRefereeInfobyCustomerPhone(customerPhone);	
					String reFereeUserPhone=null;
					String managPhone=null;//分销经理员工编码
					String reportTime=null;//
					//2、根据分销员电话，获取分销员角色					
					for(Map<String, Object> referMap :refereeList)
					{
						reportTime=(String)referMap.get("report_time");
						//判断报备时间是否在保护期内
						int diffDay =(int)Utils.getDiffDays(transTime, reportTime);
						if((diffDay+1)<YLConstant.protectDays_referee)
						{
							reFereeUserPhone = (String) referMap.get("user_phone");
							managPhone=(String)referMap.get("manager_phone");
							break;
						}
					}
					
		
					//3、根据分销员电话，获取分销员职位
					List<Map<String,Object>> referPositionList = userPowerService.getUserPowerByUserPhone(reFereeUserPhone);
					//4、如果是总监，直接结佣
					for(Map<String, Object>  referPositionMap:referPositionList)
					{
						
						
						if(YLConstant.GROUP_CODE_REFERDIRECTOR.equals(referPositionMap.get("group_code")))
						{
							double dicrectorRaio =0;
							if("1".equals(isTask))
							{
								
								 dicrectorRaio = YLConstant.refereeDicrector_TaskRadio;
							
							}else{
								 dicrectorRaio = YLConstant.refereeDicrector_NoTaskRadio;

							}
							String dicrestorResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,dicrectorRaio , reFereeUserPhone,reFereeUserPhone);

							return dicrestorResult ;
						}
					}
					//5、如果是分销经理，则检查上级是否为分销总监，如果是，则结佣
					for(Map<String, Object>  referPositionMap:referPositionList)
					{
						String commiManaerResult ="";
						
						if(YLConstant.GROUP_CODE_REFERMANAGER.equals(referPositionMap.get("group_code")))
						{
							double dicrectorRaio =0;
							if("1".equals(isTask))
							{
								
								 dicrectorRaio = YLConstant.refereeManager_TaskRadio;
							
							}else{
								 dicrectorRaio = YLConstant.refereeManager_NoTaskRadio;

							}
							commiManaerResult=commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,dicrectorRaio , reFereeUserPhone,reFereeUserPhone);

							
						}
						
						//判断分销经理的上级是否为分销总监						
						if(managPhone != null && managPhone.length() != 0)
						{
							int isReStr =userPowerService.isGroupPositionBygroupCode(managPhone, YLConstant.GROUP_CODE_REFERDIRECTOR);
							if(isReStr>=1)
							{
								
								double dicrectorRaio1=0;
								if("1".equals(isTask)){
									dicrectorRaio1=YLConstant.refereeDicrector_TaskRadio-YLConstant.refereeManager_TaskRadio;
								}else
								{
									
								
								     dicrectorRaio1 =YLConstant.refereeDicrector_NoTaskRadio-YLConstant.refereeManager_NoTaskRadio;
								}
								String dicrestorResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,dicrectorRaio1 , managPhone,reFereeUserPhone);
								return dicrestorResult;
							}
						}else
						{
							return commiManaerResult;
						}
						
					  }
					
					//6、如果是分销经纪人，则分别判断是否给分销经理和分销总监结佣
					for(Map<String, Object>  referPositionMap:referPositionList)
					{
						
						
						if(YLConstant.GROUP_CODE_REFEREE.equals(referPositionMap.get("group_code")))
						{
							double refereeRaio =0;
							if("1".equals(isTask))
							{
								
								refereeRaio = YLConstant.referee_TaskRadio;
							
							}else{
								refereeRaio = YLConstant.referee_NoTaskRadio;

							}
							String commiRefereeResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,refereeRaio , reFereeUserPhone,reFereeUserPhone);

							if(!"1".equals(commiRefereeResult))
							{
								LOGGER.info("给分销经纪人结佣失败");
								result=YLConstant.ERROR_CODE_COMMIFAIL;
							}
						}
						
						//判断分销经纪人的上级是否为分销经理						
						if(managPhone != null && managPhone.length() != 0)
						{
							int isReStr =userPowerService.isGroupPositionBygroupCode(managPhone, YLConstant.GROUP_CODE_REFERMANAGER);
							if(isReStr>=1)
							{
								
								double Raio1=0;
								if("1".equals(isTask)){
									Raio1=YLConstant.refereeManager_TaskRadio-YLConstant.referee_TaskRadio;
								}else
								{
									
								
									Raio1 =YLConstant.refereeManager_NoTaskRadio-YLConstant.referee_NoTaskRadio;
								}
								String managerByreferResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,Raio1 , managPhone,reFereeUserPhone);
								//判断分销经理的上级是否为总监，若是，则总监费率为减去经理佣金
								List<Map<String,Object>> mMangerUserInfo=userPowerService.getUserPowerByUserPhone(managPhone);								
								String mMangerPhone ="";
								for(Map<String,Object> map1:mMangerUserInfo)
								{
									if( YLConstant.GROUP_CODE_REFERDIRECTOR.equals( map1.get("group_code")))
									{
										mMangerPhone =(String) map1.get("manager_phone");
										break;
									}
									
								}
								//给经理的上级总监结佣
								double mMangerRaio=0;
								if("1".equals(isTask)){
									mMangerRaio=YLConstant.refereeDicrector_TaskRadio-YLConstant.refereeManager_TaskRadio;
								}else
								{
									
								
									mMangerRaio =YLConstant.refereeDicrector_NoTaskRadio-YLConstant.refereeManager_NoTaskRadio;
								}
								String mManagerReferResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,mMangerRaio , mMangerPhone,managPhone);
								
								
							}
							
							
						}
						//如果分销员上级是总监，则总监费率为减去分销经纪人佣金
						int  isDicrector=userPowerService.isRefereeDicrector(managPhone, YLConstant.GROUP_CODE_REFERDIRECTOR);
						if(isDicrector>=1)
						{
							double Raio3=0;
							if("1".equals(isTask)){
								Raio3=YLConstant.refereeDicrector_TaskRadio-YLConstant.referee_TaskRadio;
							}else
							{
								
							
								Raio3 =YLConstant.refereeDicrector_TaskRadio-YLConstant.referee_NoTaskRadio;
							}
							String managerByreferResult =commissionService.setCommissionByPosistion(customerPhone, customerName, productInfo, transMoney, isTask,Raio3 , managPhone,managPhone);
							
						}
						
					  }
					
					
					}
				
					
					
					
					/*
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
		*/		
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
	
	
	
	
	@RequestMapping(value="/addOnlineGoodsOrder.do",method = {RequestMethod.POST })
	@ResponseBody
	public String addOnlineGoodsOrder(@RequestParam Map<String,String> map)  {
	String reStr=null;
	try {
		//增加订单基本信息
		reStr = saleInfoService.addGoodsOnlineOrder(map);
		
		//增加订单商品列表
		String goodsOrderListStr = map.get("goodsOrderListStr");
		List<Map<String,String>> goodsOrderList =  (List) JSON.parse(goodsOrderListStr);
		for(Map<String,String> goodsMap:goodsOrderList)
		{
			goodsMap.put("goodsOrderId", map.get("goodsOrderId"));
			saleInfoService.addGoodsdetailList(goodsMap);
		}
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return reStr;
	}
	
	
	@RequestMapping(value="/getGoodsOrderListByOpenId.do",method = {RequestMethod.POST })
	@ResponseBody
	public List<Map<String, Object>> getGoodsOrderListByOpenId(@RequestParam Map<String,String> map)  {
		LOGGER.info("json返回各个导购录入的销售信息列表");
	
		// List<Map<String, Object>> guideInfos = userPowerService.getUserPowerByOpenId(openId);
//		LOGGER.info("guideInfos:"+guideInfos);
		 List<Map<String, Object>> salesInfolist= new ArrayList<Map<String, Object>>();
		try {
			salesInfolist = saleInfoService.getGoodsOrderListByOpenId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return salesInfolist;
	}
	
	@RequestMapping(value="/updateOrderStatus.do",method = {RequestMethod.POST })
	@ResponseBody
	public String updateOrderStatus(@RequestParam Map<String,String> map)  {
		LOGGER.info("修改订单状态");
	
		// List<Map<String, Object>> guideInfos = userPowerService.getUserPowerByOpenId(openId);
//		LOGGER.info("guideInfos:"+guideInfos);
//		 List<Map<String, Object>> salesInfolist= new ArrayList<Map<String, Object>>();
		String upateResult =null;
		try {
			 upateResult = saleInfoService.updateOrderStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return upateResult;
	}
	
}






