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
}
