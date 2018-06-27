<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript" src="<%=basePath%>plugins/formValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/formValidator/formValidatorRegex.js"></script>
