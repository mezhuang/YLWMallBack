package org.andy.shop.service.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.andy.shop.common.YLConstant;
import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.CartAndPayService;
import org.andy.shop.service.UserInfoService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 创建时间：2015-1-30 上午11:40:19
 * 
 * @author andy
 * @version 2.2 实现接口
 */

@Service("cartAndPayService")
public class CartAndPayServiceImpl implements CartAndPayService {

	private static final Logger LOGGER = Logger
	.getLogger(CartAndPayServiceImpl.class);
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public UserInfoPo getById(Integer id) {
		return userInfoDao.getById(id);
	}
	public String getPrePayIdByOrderInfo(@RequestParam Map<String,String> map) throws Exception {

        String UTF8 = "UTF-8";
        String appid=map.get("appid");
        String body="test";
//        appid:'wx3e71fb7bb3e423a3'  ,
//        mch_id:'1501941241' ,
//    device_info:'1000',
//    body:'test' ,
//    nonce_str:'ibuaiVcKdpRxkhJA' ,
//     apiKey:'r432gffhr45fg4ffew3r32hhhfddssgg'
        
//        String reqBody = "<xml><body>test</body><trade_type>NATIVE</trade_type><mch_id>11473623</mch_id><sign_type>HMAC-SHA256</sign_type><nonce_str>b1089cb0231011e7b7e1484520356fdc</nonce_str><detail /><fee_type>CNY</fee_type><device_info>WEB</device_info><out_trade_no>20181909105959000000111190</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><notify_url>http://test.letiantian.com/wxpay/notify</notify_url><sign>78F24E555374B988277D18633BF2D4CA23A6EAF06FEE0CF1E50EA4EADEEC41A3</sign><spbill_create_ip>123.12.12.123</spbill_create_ip></xml>";
        String reqBody = map.get("dataXml");
        //        URL httpUrl = new URL("https://14.215.140.116/pay/unifiedorder");
//       String reqBody ="<xml><appid>wx3e71fb7bb3e423a3</appid>"+
//	"<body>test</body>"+
//	"<device_info>1000</device_info>"+
//	"<mch_id>1501941241</mch_id>"+
//	"<nonce_str>ibuaiVcKdpRxkhJA</nonce_str>"+
//	"<openid>ozLOG5LWXLjDpxZLrD1DxXIKxHWg</openid>"+
//	"<out_trade_no>sdfsdgdfgdg</out_trade_no>"+
//	"<spbill_create_ip>203.195.200.199</spbill_create_ip>"+
//	"<total_fee>1</total_fee>"+
//	"<trade_type>JSAPI</trade_type>"+
//	"<sign>7294281A0DA28736BD30E5D931EB3522</sign></xml>";

        LOGGER.info("reqBody:"+reqBody);
       
        URL httpUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(10*1000);
        httpURLConnection.setReadTimeout(10*1000);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer!=null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream!=null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream!=null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(resp);
		
	
		 return  null;
	}
	


}
