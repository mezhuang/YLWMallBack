<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script src="<%=basePath%>plugins/effect/3dmenu/js/classie.js"></script>
<script src="<%=basePath%>plugins/effect/3dmenu/js/menu.js"></script>