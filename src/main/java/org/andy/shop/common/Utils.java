package org.andy.shop.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	//获取当前时间
static public String getCurrentDate()
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String  currentDate= df.format(new Date());// new Date()为获取当前系统时间
    return currentDate;
}

//根据两个字符串，计算间隔天数
static public long getDiffDays(String startDateStr,String endDateStr) throws ParseException
{
	 DateFormat startFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	 DateFormat endFormat = new SimpleDateFormat("yyyy-MM-dd"); 

     Date startDate = startFormat.parse(startDateStr);  
     Date endDate = endFormat.parse(endDateStr);
     long days = (endDate.getTime() - startDate.getTime()) / (24*3600*1000);
	return days;  

}
}
