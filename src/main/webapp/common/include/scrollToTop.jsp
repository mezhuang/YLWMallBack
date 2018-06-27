<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/effect/scroll/style.css" />
<script type="text/javascript" src="<%=basePath%>plugins/effect/scroll/scroll.js"></script>