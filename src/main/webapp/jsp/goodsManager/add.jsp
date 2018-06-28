<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@page import="org.andy.shop.common.Utils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	//out.println("basePath:"+basePath);
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
		<title>添加商品</title>
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
    
    				$('#add').submit();
    			
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
			//上传后预览图片
			//上传图片后预览图片
function viewImage(file,imageId,viewId){
            //var preview = document.getElementById('preview1');
            var preview = document.getElementById(viewId);
            if(file.files && file.files[0]){
                //火狐下
                preview.style.display = "block";
                preview.style.width = "300px";
                preview.style.height = "120px";
                preview.src = window.URL.createObjectURL(file.files[0]);
            }else{
                //ie下，使用滤镜
                file.select();
                var imgSrc = document.selection.createRange().text;
                 //var localImagId = document.getElementById('localImagId1'); 
                var localImagId = document.getElementById(imageId); 
                //必须设置初始大小 
                localImagId.style.width = "250px"; 
                localImagId.style.height = "200px"; 
                try{ 
                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                locem("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc; 
                }catch(e){ 
                alert("您上传的图片格式不正确，请重新选择!"); 
                return false; 
                } 
                preview.style.display = 'none'; 
                document.selection.empty(); 
                } 
                return true; 
        }
        
        //实现产品类型（品类、风格、空间）二级联动
         $(function(){
            //页面加载完毕后开始执行的事件
            var city_json='{"品类":["沙发","床"],"风格":["现代中式","北欧轻奢"],"空间":["客厅","卧室"]}';
            var city_obj=eval('('+city_json+')');
            for (var key in city_obj)
            {
                $("#province").append("<option value='"+key+"'>"+key+"</option>");
            }
            $("#province").change(function(){
                var now_province=$(this).val();
                $("#city").html('<option value="">请选择子类</option>');
                for(var k in city_obj[now_province])
                {
                    var now_city=city_obj[now_province][k];
                    $("#city").append('<option value="'+now_city+'">'+now_city+'</option>');
                }
            });
        });
			
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
                                    <a href="javascript:" class="hover" style="left: 0px; z-index: 99">添加商品信息 </a>
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
                        <form id="add" name="add" action="<%=basePath%>addGoodsRecord.do" method="post" enctype="multipart/form-data">
                            
                            <!-- 表单元素table start -->
                            <table cellspacing="0" width="100%" cellpadding="2" border="0">
                            	<tr height="30" align="left" valign="middle">
                            	<td><input type="file" name="caseImage1"  onchange="viewImage(this,'localImag1','preview1')">
                            	</td>
                            	<td>
                            	<!--<p class="help-block">建议尺寸88*88</p>-->
										</div>
										<div id="localImag1"><img id="preview1" width=-1 height=-1 style="diplay:none" />
										</div>
								</td>
								</tr>
								<tr height="30" align="left" valign="middle">
                            	<td><input type="file" name="caseImage2"  onchange="viewImage(this,'localImag2','preview2')">
                            	</td>
                            	<td>
                            	<!--<p class="help-block">建议尺寸88*88</p>-->
										</div>
										<div id="localImag2"><img id="preview2" width=-1 height=-1 style="diplay:none" />
										</div>
								</td>
								</tr>
								<tr height="30" align="left" valign="middle">
                            	<td><input type="file" name="caseImage3"  onchange="viewImage(this,'localImag3','preview3')">
                            	</td>
                            	<td>
                            	<!--<p class="help-block">建议尺寸88*88</p>-->
										</div>
										<div id="localImag3"><img id="preview3" width=-1 height=-1 style="diplay:none" />
										</div>
								</td>
								</tr>
								
								
                            	<tr>
                            		<td >标题：
                            		
                            			<input type="text" id="goodsTile" name="goodsTile" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                            		<td >型号：
                            		
                            			<input type="text" id="goodsModelNumber" name="goodsModelNumber" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
					             
					                <td>
					                    <select name="onelevelCode01" id="onelevelCode01">
					                        <option value="">请选择品类</option>
					                        <option value="01001">沙发</option>
					                        <option value="01002">床</option>
					                        <option value="01003">电视柜</option>
					                    </select>
					                    
					                </td>
					          </tr>
					           <tr>
					                <td>
					                    <select name="onelevelCode02" id="onelevelCode2">
					                        <option value="">请选择风格</option>
					                        <option value="02001">北欧轻奢</option>
					                         <option value="02002">现代中式</option>
					                    </select>
					                    </td>
					           </tr>
					                    
					            <tr>
					           		 <td>
					                    <select name="onelevelCode03" id="onelevelCode03">
					                        <option value="">请选择空间</option>
					                        <option value="03001">客厅</option>
					                        <option value="03002">卧室</option>
					                        
					                    </select>
					              	  </td>
					           </tr>
					           <tr>
					           		<td>
					                    <select name="onelevelCode04" id="onelevelCode04">
					                        <option value="">请选择套餐</option>
					                        <option value="04001">限时特惠</option>
					                        <option value="04002">精英标配</option>
					                        <option value="04003">高端时尚</option>
					                        
					                    </select>
					                </td>
					           </tr>
					            <tr>
					            	<td>
					                    <select name="onelevelCode05" id="onelevelCode05">
					                        <option value="">请选择品牌</option>
					                        <option value="05001">中深宅配</option>
					                        <option value="05002">保罗沃克</option>
					                        
					                    </select>
					                </td>
					            </tr>
					             <tr>
					             	<td>
					                    <select name="onelevelCode06" id="onelevelCode06">
					                        <option value="">请选择展示位置</option>
					                        <option value="06001">首页轮播</option>
					                        <option value="06002">热销爆品</option>
					                        <option value="06003">精选推荐</option>
					                        
					                    </select>
					                </td>
					            </tr>
					            
                            	<tr>
                            	
                            		<td >
                            			<text>原价：</text><input type="text" id="goodsOrgPrice" name="goodsOrgPrice" value=""/>
                            		</td>
                            	</tr>
								<tr>
                            		
                            		<td class="td-right">
                            		<text>现价：</text>
                            			<input type="text" id="goodsCurrPrice" name="goodsCurrPrice" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                            		
                            		<td class="td-right"><text>库存：</text>
                            			<input type="text" id="goodsStock" name="goodsStock" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                         
                            		<td class="td-right">
                            		<text>商品详情：</text>
                            			<input type="text" id="goodsDetails" name="goodsDetails" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                            		
                            		<td class="td-right">
                            		<text>备注：</text>
                            			<input type="text" id="remark" name="remark" value=""/>
                            		</td>
                            	</tr>

								
                            	<tr>
                            	
                            		<td class="td-right">
                            		<text>创建人：</text>
                            			<input type="text" id="createName" name="createName" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                            		<td class="td-right">
                            		<text>创建时间：</text>
                            			<input type="text" id="createTime" name="createTime" readonly class="Wdate" onClick="WdatePicker({el:'createTime', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                            		</td>
                            	</tr>
		
                               
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
