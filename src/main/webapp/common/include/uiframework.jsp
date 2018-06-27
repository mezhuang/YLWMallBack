<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>uiframework/js/framework.js"></script>
<link href="<%=basePath %>uiframework/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>uiframework/skins/blue/style.css" rel="stylesheet" type="text/css" id="theme" themeColor="blue" positionTarget="positionContent"/>
<link href="<%=basePath %>uiframework/skin/style.css" rel="stylesheet" type="text/css" id="skin"/>
