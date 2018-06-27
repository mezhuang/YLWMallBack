<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" charset="UTF-8" src="<%=basePath %>plugins/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/uploadify/uploadify.css"> </link>