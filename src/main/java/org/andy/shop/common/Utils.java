package org.andy.shop.common;

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
}
