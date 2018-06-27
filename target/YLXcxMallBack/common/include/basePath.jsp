<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<basePath id = "dhccBasePath" value ="<%=basePath%>"></basePath>
<script type="text/javascript">
    function getBasePath(){
		var basePath=$('#dhccBasePath').attr("value");
		return basePath;
	}
</script>

