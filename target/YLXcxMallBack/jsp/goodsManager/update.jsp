<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.dhcc.gwbl.base.interfacemap.po.InterfaceMapPo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	String pageUrl = request.getRequestURL().toString();
	String listpageUrl = pageUrl.substring(0,pageUrl.lastIndexOf("/")+1)+"list.jsp";
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>编辑接口映射</title>
		<jsp:include page="/common/include/jquery.jsp" flush="true"/>
		<jsp:include page="/common/include/easyui.jsp" flush="true"/>
		<jsp:include page="/common/include/dialog.jsp" flush="true"/>
		<jsp:include page="/common/include/formValidator.jsp" flush="true"/>
		<script type="text/javascript" src="<%= basePath %>plugins/My97DatePicker/WdatePicker.js"></script>
		
		
		<script language="javascript">
			function senfe(o,a,b,c,d){
				var t=document.getElementById(o).getElementsByTagName("tr");
				for(var i=0;i<t.length;i++){
					t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
				}
			}
			$(document).ready(function(){
				senfe("InterfaceMap","#f3f8fd","#ffffff","#ADADAD","#f391a9");
				$.formValidator.initConfig( {
					formID : "update",
					theme : 'SolidBox',
					mode : 'AutoTip',
					inIframe : true
				});
				$("#createName")
					.formValidator({
						empty:true,
						onShow:"请输入创建人",
						onFocus:"请输入创建人",
						onCorrect:"您输入的创建人合法"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"创建人有误,请确认"
					});
		
				$("#createTime")
					.formValidator({
						empty:true,
						onShow:"请选择创建时间",
						onFocus:"请选择创建时间",
						onCorrect:"您选择的创建时间合法"
					})
					.inputValidator({
						onError:"创建时间有误,请确认"
					});
		
		
			});
			$(window).resize(function(){
				$.formValidator.reloadAutoTip();
				$('#InterfaceMapPanel').panel('resize');
			});
		</script>
		
	</head>
	<body>
		<div id="InterfaceMapPanel" class="easyui-panel" style="width:'100%';padding:6px;" data-options="title:'编辑接口映射',iconCls:'icon-add',collapsible:true,minimizable:false,maximizable:false,closable:false">
		<form id="update" name="update" action="<%=basePath%>InterfaceMapManage_updateInterfaceMap.do" method="post">
			<input type="hidden" name="id" id="id" value="<%=po.getId()==null?"":po.getId()%>"/>
			<table id="InterfaceMap" cellspacing="1" cellpadding="0" border="0" bgcolor="#95a5d2" width="100%">
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>创建人</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%">
						<input type="text" id="createName" name="createName" value="<%= po.getCreateName()!=null?po.getCreateName():"" %>"/>
					</td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" align="center" width="30%"><strong>创建时间</strong></td>
					<td bgcolor="#f3f8fd" align="left" width="70%">
						<input type="text" id="createTime" name="createTime" value="<%= po.getCreateTime()!=null?po.getCreateTime():"" %>" readonly onClick="WdatePicker({el:'createTime', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
				</tr>
				<tr height="30" align="left" valign="middle">
					<td bgcolor="#f3f8fd" colspan="2" align="center" width="100%">
						<a href="javascript:void(0)" onclick="$('#update').submit()" class="easyui-linkbutton" id="prsBtn" plain="true" icon="icon-save">提交</a>
						<a href="javascript:void(0)" onclick="javascript:window.location.href='<%=listpageUrl %>';" class="easyui-linkbutton" plain="true" icon="icon-back">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
