<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  //System.out.println(System.nanoTime());    
%>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
</script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>plugins/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>plugins/ueditor/editor_api.js"></script>
<script>var ueditorCount=true;//定义全局变量，用于字数统计时超过限制时不允许提交</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/ueditor/themes/default/ueditor.css" />