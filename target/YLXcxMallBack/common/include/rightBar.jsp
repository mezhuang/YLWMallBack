<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
 <!-- 右侧侧边菜单  -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/effect/sideBar/css/rightbar.css" />
<script type="text/javascript" src="<%= basePath %>plugins/effect/sideBar/js/rightBar.js"></script>