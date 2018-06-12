package org.andy.shop.qcloudsms;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;


import java.io.IOException;
import java.util.Random;

public class YLQCloudsms {
	

	
	public String specifysend(String userPhone)
	{
		 int appid = 1400081484; // 1400开头

			// 短信应用SDK AppKey
			 String appkey = "d7bfbc3a54b2a446c9bfe54b598a3aa6";

			// 需要发送短信的手机号码
//			 String[] phoneNumbers = {"15208945149", "15208985488", "18876175897"};
			 String[] phoneNumbers = {userPhone};
			// 短信模板ID，需要在短信应用中申请
			 int templateId = 138242; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
			
			// 签名
			 String smsSign = "远联家居"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
			 //生成随机数
			 int YLRandom=0;
			 Random rand = new Random();
			  for(int i=0; i<10; i++) {
				   YLRandom =rand.nextInt(1000000) + 1;
			  }
			  String YLRandomStr=String.valueOf(YLRandom);
			  //传入短信的验证码
			  String[] params = {YLRandomStr,"3"};

		try {
		    
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
		        templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		    System.out.print(result);
		    
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
		return YLRandomStr;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				 
		YLQCloudsms ylsms= new YLQCloudsms();
//		ylsms.specifysend();
		
	}
//	void sendSingleSms(int appid,String appkey,String[] phoneNumbers)
//	{
//		try {
//		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//		    SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
//		        "【腾讯云】您的验证码是: 5678", "", "");
//		    System.out.print(result);
//		} catch (HTTPException e) {
//		    // HTTP响应码错误
//		    e.printStackTrace();
//		} catch (JSONException e) {
//		    // json解析错误
//		    e.printStackTrace();
//		} catch (IOException e) {
//		    // 网络IO错误
//		    e.printStackTrace();
//		}
//	}

}
