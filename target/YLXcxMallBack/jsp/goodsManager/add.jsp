<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@page import="org.andy.shop.common.Utils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	//获取当前登录人
	/*DhccUser user = UserUtil.getCurrentUser(request);
	if(user==null){
		out.print("<script>parent.location.reload();</script>");
		return;
	}
	
	String id = new Uuid().getUUID();
	//获得当前登录人
	DhccUser loginUser = UserUtil.getCurrentUser(request);*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">  
	<head>
		<base href="<%=basePath%>">
		<title>添加接口映射</title>
		<jsp:include page="/common/include/jquery.jsp" flush="true"/>
		<jsp:include page="/common/include/easyui.jsp" flush="true"/>
		<jsp:include page="/common/include/dialog.jsp" flush="true"/>
		<jsp:include page="/common/include/formValidator.jsp" flush="true"/>
		<script type="text/javascript" src="<%= basePath %>plugins/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript">
			$(document).ready(function(){
				$.formValidator.initConfig( {
					formID : "add",
					onError:function(msg){
						alert(msg);
					}
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
			
			var submitPage;//定义用于提交流程的 子窗口对象
			//提交表单
			function submitForm() {
    			$('#submitBtn').linkbutton('disable');//把提交按钮设置 为不可用
    			//这里要判断是否要提交流程 【【【 一定要修改下面的代码 】】】
    			var isNeedProcess = false;
    			if(isNeedProcess) {
    				ajaxSubmitFromUserNode();
    			}else {
    				$('#add').submit();
    			}
			}
			//流程提交
			function ajaxSubmitFromUserNode(){
				$.ajax({
					type: 'POST',
					url: '<%=basePath%>InterfaceMapManage_addInterfaceMap.do',
					data:  $('#add').serialize(),
					success: function(msg){
						var url = '<%=basePath%>bpm/worklist/handle/ajaxhandle.jsp?systemVariableBytearrayId='+msg;
					    submitPage = openWin(url, 500, 300, '任务提交办理');
				   	}
				}); 
			}
			//关闭窗口
			function closeWindow(){
				if(frameElement==null||frameElement==undefined){
					window.opener.initTable();
					window.close();
				}else{
					frameElement.api.opener.initTable();//刷新父窗口的列表
					if(submitPage){
						submitPage.close();//关闭子页面，submitPage是子窗口对象
					}
					frameElement.api.close();//关闭当前页面
				}
			}
		</script>
	</head>
		
	<body class="main">
        <!-- 页面容器 start -->
        <div id="mContainer">
            <table cellpadding="0" cellspacing="0" width="100%">
                <!-- 页面标题、提交按钮 start -->
                <tr id="path">
                    <td>
                        <div class="path">
                            <div class="menublock" id="menublock">
                                <div class="nav">
                                    <a href="javascript:" class="hover" style="left: 0px; z-index: 99">新建接口映射 </a>
                                    <div class="clear">
                                    </div>
                                </div>
                            </div>
                            <div class="btnblock" id="btnblock" style="top: -5px">
                                <a href="javascript:void(0)" onclick="submitForm();" id="submitBtn" class="easyui-linkbutton" plain="true" icon="icon-save"> 提交 </a>
                                <a href="javascript:void(0)" onclick="javascript:closeWindow();" class="easyui-linkbutton" plain="true" icon="icon-back"> 关闭 </a>
                            </div>
                        </div>
                    </td>
                </tr>
                <!-- 页面标题、提交按钮 end -->
                <!-- 页面主体内容 start -->
                <tr>
                    <td class="content" id="tdcontent">
                        <div id="errorlist">
                        </div>
                        <!-- 表单 start -->
                        <form id="add" name="add" action="<%=basePath%>InterfaceMapManage_addInterfaceMap.do" method="post">
                            
                            <!-- 表单元素table start -->
                            <table cellspacing="0" width="100%" cellpadding="2" border="0">
                            	<tr height="30" align="left" valign="middle">
                            		<td class="td-left">创建人：</td>
                            		<td class="td-right">
                            			<input type="text" id="createName" name="createName" value=""/>
                            		</td>
                            		<td class="td-left">创建时间：</td>
                            		<td class="td-right">
                            			<input type="text" id="createTime" name="createTime" readonly class="Wdate" onClick="WdatePicker({el:'createTime', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                            		</td>
                            	</tr>
		
                                <!-- 空行 - 用来补白 -->
                                <tr style="height:10px;"></tr>
		
                                <!-- 创建人 start -->
                                <tr>
                                    <td width="10%">
                                        创&nbsp;建&nbsp;人：
                                    </td>
                                    <td width="40%">
                                  
                                    </td>
                                    <td width="10%">
                                        创建时间：
                                    </td>
                                    <td width="40%">
                                        <input id="createTime" name="createTime" type="text" value="<%=Utils.getCurrentDate()%>" readonly='readonly' />
                                    </td>
                                </tr>
                                <!-- 创建人 end -->
                            </table>
                            <!-- 表单元素table end -->
                        </form>
                        <!-- 表单 end -->
                    </td>
                </tr>
                <!-- 页面主体内容 end -->
            </table>
        </div>
        <!-- 页面容器 end -->
    </body>
</html>
