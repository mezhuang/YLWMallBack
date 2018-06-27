<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<base href="<%=basePath+"frame1/"%>"/>
<link href="<%=basePath+"frame1/"%>css/style-tanchuangtixing.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="tanchuang">
<div class="chakan">
   <p>截止上次登录，你共有<span>28条</span>未读提醒消息</p>
   <input  class="chakanbut"type="button" value="查看详情" />
   </div>
<div class="guanbi"><input type="button" value="" /></div>
</div>
</body>
</html>
