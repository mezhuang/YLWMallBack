<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/effect/3dmenu/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/effect/3dmenu/css/component.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/effect/3dmenu/css/selfDefine.css" />
<!-- csstransforms3d-shiv-cssclasses-prefixed-teststyles-testprop-testallprops-prefixes-domprefixes-load --> 
<script src="<%=basePath%>plugins/effect/3dmenu/js/modernizr.custom.25376.js"></script>