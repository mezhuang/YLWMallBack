<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  //System.out.println(System.nanoTime());    
%>
<script type="text/javascript" charset="UTF-8" >var basePath = "<%=basePath%>";</script>
<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery-1.8.3.min.js"></script>
<link href="<%=basePath%>/common/css/common.css" rel="stylesheet" type="text/css" />