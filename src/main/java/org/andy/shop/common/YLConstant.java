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
static public String     GROUP_CODE_REFERMANAGER="10004";//分销经理组编码
static public String     GROUP_CODE_REFERDIRECTOR="10005";//分销总监组编码
static public String     GROUP_CODE_CUSTOMER="10006";//客户组编码


static public int  protectDays_referee=14;//分销经纪人和经理保护期时长
static public int  protectDays_dicrector=30;//总监保护期时长
static public double  referee_NoTaskRadio=0.01;
static public double referee_TaskRadio=0.03;
static public double  refereeManager_NoTaskRadio=0.02;
static public double refereeManager_TaskRadio=0.04;
static public double refereeDicrector_NoTaskRadio=0.03;
static public double refereeDicrector_TaskRadio=0.05;


}
