package com.github.wxpay.sdk;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {

    public static void main(String[] args) throws Exception {

        // HostnameVerifier hnv = new HostnameVerifier() {
        //     public boolean verify(String hostname, SSLSession session) {
        //         // Always return true，接受任意域名服务器
        //         return true;
        //     }
        // };
        // HttpsURLConnection.setDefaultHostnameVerifier(hnv);

        String UTF8 = "UTF-8";
//        String reqBody = "<xml><body>测试商家-商品类目</body><trade_type>NATIVE</trade_type><mch_id>11473623</mch_id><sign_type>HMAC-SHA256</sign_type><nonce_str>b1089cb0231011e7b7e1484520356fdc</nonce_str><detail /><fee_type>CNY</fee_type><device_info>WEB</device_info><out_trade_no>20181909105959000000111190</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><notify_url>http://test.letiantian.com/wxpay/notify</notify_url><sign>78F24E555374B988277D18633BF2D4CA23A6EAF06FEE0CF1E50EA4EADEEC41A3</sign><spbill_create_ip>123.12.12.123</spbill_create_ip></xml>";
        String reqBody="<xml><appid>wx3e71fb7bb3e423a3</appid>"+
						"<body>test</body>"+
						"<device_info>WEB</device_info>"+
						"<fee_type>CNY</fee_type>"+
						"<mch_id>1501941241</mch_id>"+
						"<nonce_str>b1089cb0231011e7b7e1484520356fdd</nonce_str>"+
						"<notify_url>http://test.letiantian.com/wxpay/notify</notify_url>"+
						"<openid>ozLOG5LWXLjDpxZLrD1DxXIKxHWg</openid>"+
						"<out_trade_no>20181909105959000000111190</out_trade_no>"+
						"<sign_type>HMAC-SHA256</sign_type>"+
						"<spbill_create_ip>123.12.12.123</spbill_create_ip>"+
						"<total_fee>1</total_fee>"+
						"<trade_type>NATIVE</trade_type>"+
						"<sign>CAFF8C4A9195E2DA4B1BB9E94D9A3093</sign></xml>";
        //        URL httpUrl = new URL("https://14.215.140.116/pay/unifiedorder");
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

    }

}
