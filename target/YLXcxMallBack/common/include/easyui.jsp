<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/easyui/jquery.easyui.extends.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>frame1/css/style-easyui.css" />