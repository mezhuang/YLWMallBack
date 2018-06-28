<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.dhcc.gwbl.base.interfacemap.po.InterfaceMapPo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	String pageUrl = request.getRequestURL().toString();
	String listpageUrl = pageUrl.substring(0,pageUrl.lastIndexOf("/")+1)+"list.jsp";
	
	//InterfaceMapPo po = (InterfaceMapPo)request.getAttribute("InterfaceMapPo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>查看接口映射</title>
		<jsp:include page="/common/include/jquery.jsp" flush="true"/>
		<jsp:include page="/common/include/easyui.jsp" flush="true"/>
		
		
	</head>
	<body>
		<div id="InterfaceMapPanel" class="easyui-panel" style="width:'100%';padding:6px;" data-options="title:'查看接口映射',iconCls:'icon-add',collapsible:true,minimizable:false,maximizable:false,closable:false">
		<form id="update" name="update" action="<%=basePath%>InterfaceMap_updateInterfaceMap.do" method="post">
			<table id="InterfaceMap" cellspacing="1" cellpadding="0" border="0" bgcolor="#95a5d2" width="100%">
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>主键</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%"><%= po.getId()!=null?po.getId():"" %></td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>接口名称</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%"><%= po.getInterfaceId()!=null?po.getInterfaceId():"" %></td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>部门名称</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%"><%= po.getDeptId()!=null?po.getDeptId():"" %></td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>创建人</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%"><%= po.getCreateName()!=null?po.getCreateName():"" %></td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>创建时间</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%"><%= po.getCreateTime()!=null?po.getCreateTime():"" %></td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" colspan="2" align="center" width="100%">
						<a href="javascript:void(0)" onclick="javascript:window.location.href='<%=listpageUrl %>';" class="easyui-linkbutton" plain="true" icon="icon-back">返回列表</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
