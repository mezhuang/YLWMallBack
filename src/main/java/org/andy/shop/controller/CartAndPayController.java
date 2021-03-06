package org.andy.shop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.YLConstant;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.qcloudsms.YLQCloudsms;
import org.andy.shop.service.CartAndPayService;
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
 * 购物车及支付
 * @author andy
 * @version 2.2
 * 
 * cartAndPay的控制层
 */
@Controller
public class CartAndPayController {

	private static final Logger LOGGER = Logger
			.getLogger(CartAndPayController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserPowerService userPowerService;
	@Autowired
	private GroupMapService groupMapService;
	
	@Autowired
	private CartAndPayService cartAndPayService;

	/*根据用户提交的预支付信息，申请支付编码
	 * 参数:
	 * */
	@RequestMapping(value="/getPrePayIdByOrderInfo.do",method = {RequestMethod.GET })
	@ResponseBody
	public Map<String,String> getPrePayIdByOrderInfo(@RequestParam Map<String,String> map) {
		LOGGER.info("根据用户提交的预支付信息，申请支付编码");
		LOGGER.info("dataXml:"+map.get("dataXml"));
		
		Map<String, String> reStr = null;
		try {
			reStr = cartAndPayService.getPrePayIdByOrderInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  reStr;
	}	
	
	@RequestMapping(value="/addToShoppingCart.do",method = {RequestMethod.POST })
	@ResponseBody
	public String addToShoppingCart(@RequestParam Map<String,String> map) {
		LOGGER.info("加入购物车");
		boolean addFlag=true;
		
		
		String  reStr = null;
		try {
			//判断购物车是否已存在该记录，若有，则增加记录即可。
			 List<Map<String, Object>> list=	cartAndPayService.getShoppingCartListByOpenId(map);
			 for(Map<String, Object> reMap:list)
			 {
				if(reMap.get("goods_id").equals(map.get("goodsId")))
				{
					if( reMap.get("format_code").equals(map.get("formatCode")))
					{
						Long  addTmp =(Long)reMap.get("num");
						Integer addnum= addTmp.intValue()+Integer.parseInt(map.get("buyNumber"));
						map.put("buyNumber", String.valueOf(addnum));
						map.put("shoppingCartId", String.valueOf( reMap.get("shopping_cart_id")));
						reStr = cartAndPayService.updateShoppingCartNum(map);
						addFlag=false;
					}
				}
				 
			 }
			if(addFlag)
			{
				reStr = cartAndPayService.addToShoppingCart(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  reStr;
	}	
	
	@RequestMapping(value="/deleteShoppingCart.do",method = {RequestMethod.GET })
	@ResponseBody
	public String deleteShoppingCart(@RequestParam Map<String,String> map) {
		LOGGER.info("加入购物车");
		
		
		String  reStr = null;
		try {
			reStr = cartAndPayService.deleteShoppingCart(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  reStr;
	}	
	
	@RequestMapping(value="/getShoppingCartListByOpenId.do",method = {RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> getShoppingCartListByOpenId(@RequestParam Map<String,String> map) {
		LOGGER.info("获取购物车列表");
		
		
		List<Map<String, Object>>  reStr = null;
		try {
			reStr = cartAndPayService.getShoppingCartListByOpenId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  reStr;
	}
	
}
