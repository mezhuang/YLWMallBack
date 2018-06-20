package com.github.wxpay.sdk;

import java.util.Map;

public class YLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String xmlStr="<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
//        "<return_msg><![CDATA[OK]]></return_msg>\n" +
//        "<appid><![CDATA[wx273fe72f2db863ed]]></appid>\n" +
//        "<mch_id><![CDATA[1228845802]]></mch_id>\n" +
//        "<nonce_str><![CDATA[lCXjx3wNx45HfTV2]]></nonce_str>\n" +
//        "<sign><![CDATA[68D7573E006F0661FD2A77BA59124E87]]></sign>\n" +
//        "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
//        "<openid><![CDATA[oZyc_uPx_oed7b4q1yKmj_3M2fTU]]></openid>\n" +
//        "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
//        "<trade_type><![CDATA[NATIVE]]></trade_type>\n" +
//        "<bank_type><![CDATA[CFT]]></bank_type>\n" +
//        "<total_fee>1</total_fee>\n" +
//        "<fee_type><![CDATA[CNY]]></fee_type>\n" +
//        "<transaction_id><![CDATA[4008852001201608221983528929]]></transaction_id>\n" +
//        "<out_trade_no><![CDATA[20160822162018]]></out_trade_no>\n" +
//        "<attach><![CDATA[]]></attach>\n" +
//        "<time_end><![CDATA[20160822202556]]></time_end>\n" +
//        "<trade_state><![CDATA[SUCCESS]]></trade_state>\n" +
//        "<cash_fee>1</cash_fee>\n" +
//        "</xml>";
		String xmlStr ="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx3e71fb7bb3e423a3]]></appid><mch_id><![CDATA[1501941241]]></mch_id><device_info><![CDATA[1000]]></device_info><nonce_str><![CDATA[LZdkMTXlSnb7F7AT]]></nonce_str><sign><![CDATA[9BBA8CA0AFDAF46C692B18C47683847E]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx15180537240310a16ac4bb640728698939]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>";
		try {
		    System.out.println(xmlStr);
		    System.out.println("+++++++++++++++++");
		//    System.out.println(WXPayUtil.isSignatureValid(xmlStr, config.getKey()));
		    Map<String, String> hm = WXPayUtil.xmlToMap(xmlStr);
		    System.out.println("+++++++++++++++++");
		    System.out.println(hm);
//		    System.out.println(hm.get("attach").length());
		
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}

}
