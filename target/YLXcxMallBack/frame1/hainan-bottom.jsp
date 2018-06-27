<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.login.po.DhccUser"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	DhccUser loginUser = null;
	Object obj = request.getSession().getAttribute("DhccUser");
	if (obj != null) {
		loginUser = (DhccUser) obj;
	} else {
		out.println("<script>top.location='" + basePath + "login.jsp';return false;</script>");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<base href="<%=basePath+"frame1/"%>"/>
<link href="<%=basePath+"frame1/"%>css/style-bottom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery-1.8.0.min.js"></script>
</head>
<script>
$(function(){
    getOnLineUserNum();
})
function getOnLineUserNum() {
    var onLineUserNum =0;
    //获取在线人员
    $.ajax({
        url: '<%=basePath%>' + 'TreeProcessManage_getOnLineUserNum.do',
        async: false,
        type: "post",
        dataType: 'text',
        traditional: true,
        //在struts2下该属性必须有 
        data:{
            assigneeUserId:'<%=loginUser.getUserPo().getId()%>'
        },
        success: function(data) {
            onLineUserNum = data;
            $(".peoplenum").html("在线人数"+data+"人");
        }
    });
}
</script>
<body>
<div class="bottom">
<div class="peoplenum">在线人数1人</div>
</div>
</body>
</html>
