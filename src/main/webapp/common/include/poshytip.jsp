<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- jQuery and the Poshy Tip plugin files -->
<script type="text/javascript" src="<%=basePath %>plugins/poshytip/jquery.poshytip.min.js"></script>

<!-- Tooltip classes -->
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-yellow/tip-yellow.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-violet/tip-violet.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-darkgray/tip-darkgray.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-skyblue/tip-skyblue.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>plugins/poshytip/tip-green/tip-green.css" type="text/css" />

