package org.andy.shop.common;

 public class YLConstant {
static public String 	CUSTOMER_PHONE_NULL="10001";//客户号码字段为空
/*后续去掉系统组Id*/
static public String     SYS_MANAGER_GROUP_ID="18";//系统组Id
static public String     GUIDE_GROUP_ID="19";//导购组ID
static public String     REFEREE_GROUP_ID="20";//分销商组Id
static public String     CUSTOMER_GROUP_ID="21";//普通用户组Id
static public String     TASK_GROUP_ID="21";//普通用户组Id
/*改为分组编码
系统管理员	10001
家居顾问	10002
分销经理	10003
分销经纪人	10004
普通用户	10005
*/
static public String     GROUP_CODE_SYSMANAGER="10001";//系统组编码
static public String     GROUP_CODE_GUIDE="10002";//导购组编码
static public String     GROUP_CODE_REFEREE="10003";//分销商组编码
static public String     GROUP_CODE_CUSTOMER="10004";//客户组编码
static public String     GROUP_CODE_GENERAL="10005";//普通用户组编码


static public int  protectDays=7;//保护期时长
static public double  refereeNoTaskRadio=0.01;
static public double refereeTaskRadio=0.03;
static public double  refereeManagerNoTaskRadio=0.02;
static public double refereeManagerTaskRadio=0.05;
static public double superiorRefereeNoTaskRadio=0.01;
static public double superiorRefereeTaskRadio=0.02;


}
