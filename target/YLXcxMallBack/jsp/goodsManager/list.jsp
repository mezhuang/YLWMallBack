<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>



<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取当前登录人
//DhccUser user = UserUtil.getCurrentUser(request);
/*if(user==null){
	out.print("<script>parent.location.reload();</script>");
    return;
}*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">  
	<head>
	    <title>接口映射列表</title>
	    <base href="<%=basePath%>">
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<jsp:include page="/common/include/jquery.jsp" flush="true"/>
		<jsp:include page="/common/include/easyui.jsp" flush="true"/>
		<jsp:include page="/common/include/dialog.jsp" flush="true"/>
		<script type="text/javascript" src="<%= basePath %>plugins/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function() {
				initTable();
			});
			function fillsize(percent) {
				var bodyWidth = document.body.clientWidth;
				return (bodyWidth - 90) * percent;
			}

			$(window).resize(function(){
				$('#InterfaceMap').datagrid('resize');
				$('#InterfaceMapPanel').panel('resize');
			});
			function initTable(){
				$('#InterfaceMap').datagrid({
					//title:'列表显示',//标题文字 
					iconCls:'icon-save',//一个css类，将提供一个背景图片作为标题图标 
					width:'100%',//fillsize(0.99)
					height:'auto',
					nowrap: false,//是否在一行显示数据 
					striped: true,//奇偶行使用不同背景色，默认为false 
					collapsible:true,
					url:'<%=basePath%>InterfaceMapManage_getList.do',//从远程站点请求数据的URL 
					//sortName: 'id',//可以排序的列 
					//sortOrder: 'desc',//定义列的排序顺序，只能用 'asc' 或 'desc' 
					remoteSort: true,//定义是否从服务器给数据排序
					singleSelect:true,//只允许选中一行 
					//checkOnSelect:false,//选中一行时同时选中checkbox 
					//selectOnCheck:true,//选中checkbox时同时选中改行 
					fitColumns: true,//自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动 
					pageNumber:1,//当设置了 pagination 特性时，初始化页码 
					pageSize: 1,//当设置了 pagination 特性时，初始化页码尺寸，即每页显示的记录条数，默认为10
					pageList:10,//当设置了pagination 特性时，初始化页面尺寸的选择列表，可以设置每页记录条数的列表 
					idField:'id',//标识字段 
					frozenColumns:[[
						{field:'ck',checkbox:true}
					]],
					queryParams:{
							createName:document.getElementById("createName").value,
							createTime:document.getElementById("createTime").value
					},	
					columns:[[				
						{field:'id',title:'ID',width:fillsize(1),align:'center',hidden:true,sortable:true},
						{field:'createName',title:'创建人',width:fillsize(1),align:'center',hidden:false,sortable:true	},
						{field:'createTime',title:'创建时间',width:fillsize(1),align:'center',hidden:false,sortable:true	},
							
						{field:'interfaceId',title:'接口名称',width:fillsize(1),align:'center',hidden:true},
						{field:'deptId',title:'部门名称',width:fillsize(1),align:'center',hidden:true}
					]],
					toolbar:[
						{
							text:'新建',
							disabled:false,//加上disabled:true属性就能使该按钮变灰不可用 
							iconCls:'icon-add',
							handler:Add
						},'-',{
							text:'删除',
							disabled:false,
							iconCls:'icon-remove',
							handler:Delete
						},'-',{
							text:'修改',
							disabled:false,
							iconCls:'icon-edit',
							handler:Update
						},'-',{
							text:'查看',
							disabled:false,
							iconCls:'icon-search',
							handler:Detail
						}
					],
					pagination : true,//在 datagrid 的底部显示分页栏 
					rownumbers : true,//显示行号 
					loadMsg : '查询中，请稍后……'//当从远程站点加载数据时，显示的提示信息 
				});
				clearSelections();//取消已经选择的多选框
				var p = $('#InterfaceMap').datagrid('getPager');
				$(p).pagination( {
					onBeforeRefresh : function() {
						//alert('before refresh');
						//initTable();
					},
					beforePageText : '第',//页数文本框前显示的汉字 
					afterPageText : '共{pages}页',
					displayMsg : '共{total}条记录，本页显示第{from}-{to}条'
				});
			}
			
			function clearSelections(){
				$('#InterfaceMap').datagrid('clearSelections');
			}
			
			function Add(){
				openWin('<%=basePath%>gwbl/base/interfacemap/add.jsp', 800, 500, '新建接口映射');
			}
			
			function Delete(){
				var ids =[];
				var rows = $('#InterfaceMap').datagrid('getSelections');
				if(rows.length<1){
					$.messager.alert('提示','请选择一条记录！','warning');
					return;
				}
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].id);
				}
				$.messager.confirm('确认','确认删除选中的记录？',function(result){  
					if(result){ 
						$.ajax({ 
							type : "POST", 
							url:'<%=basePath%>InterfaceMapManage_deleteInterfaceMap.do', 
							traditional: true,//在struts2下该属性必须有 
							data:{ids:ids}, 
							success:function(result){ 
								alert(result); 
								initTable(); 
							}
						});
					}
				})
			}
			
			function Update(){
				var rows = $('#InterfaceMap').datagrid('getSelections');
				if(rows.length<1){
					alert('请选择一条记录！');
					return;
				} else if(rows.length>1){
					alert('只能选择一条记录！');
					return;
				}
				openWin('<%=basePath%>InterfaceMapManage_toInterfaceMapUpdate.do?id='+rows[0].id, 800, 500, '修改接口映射');
			}
			
			function Detail() {
				var rows = $('#InterfaceMap').datagrid('getSelections');
				if (rows.length < 1) {
					alert('请选择一条记录！');
					return;
				} else if (rows.length > 1) {
					alert('只能选择一条记录！');
					return;
				}
				openWin('<%=basePath%>InterfaceMapManage_toInterfaceMapDetail.do?id='+rows[0].id, 800, 500, '查看接口映射');
			}
		</script>
	</head>
	<body>
		<div id="InterfaceMapPanel" class="easyui-panel" style="width:'100%';padding:6px;" data-options="title:'接口映射',iconCls:'icon-search',collapsible:false,minimizable:false,maximizable:false,closable:false">
			<label>创建人</label><input type="text" id="createName" name="createName" />
			<label>创建时间</label><input type="text" id="createTime" name="createTime" readonly onClick="WdatePicker({el:'createTime', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			<a href="javascript:void(0)" onclick="initTable()" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
		</div>
		<table id="InterfaceMap"></table>
	</body>
</html>
