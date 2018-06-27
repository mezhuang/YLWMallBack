<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/easyui/1.3.6/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/easyui/1.3.6/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/easyui/1.3.6/extends/css/icon.css" />
<script type="text/javascript" src="<%=basePath%>plugins/easyui/1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/easyui/locale/1.3.6/easyui-lang-zh_CN.js"></script>