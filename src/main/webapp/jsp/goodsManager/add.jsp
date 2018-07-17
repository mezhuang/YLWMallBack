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
			var imageFileNO=1;//上传图片分组序号
		    	var formatAndPriceNO=1;//商品规格和价格序号
			$(document).ready(function(){
				$.formValidator.initConfig( {
					formID : "add",
					onError:function(msg){
						alert(msg);
					}
				});
				/*
				for(int i=1;i<imageFileNO;i++)
				{
					$("#imagediplay"+i+"")
					.formValidator({
						empty:true,
						onShow:"请选择图片显示位置",
						onFocus:"请选择图片显示位置",
						onCorrect:"请选择图片显示位置"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"请选择图片显示位置"
					});	
				}
				for(int i=1;i<formatAndPriceNO;i++)
				{
					$("#currPrice"+i+"")
					.formValidator({
						empty:true,
						onShow:"请输入当前价格",
						onFocus:"请输入当前价格",
						onCorrect:"您输入的当前价格合法"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"当前价格有误,请确认"
					});	
				}
				
					for(int i=1;i<7;i++)
				{
					$("#onelevelCode0"+i+"")
					.formValidator({
						empty:true,
						onShow:"请商品分类",
						onFocus:"请商品分类",
						onCorrect:"您选择商品分类合法"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"商品分类有误,请确认"
					});	
				}
					*/
				$("#goodsTile")
					.formValidator({
						empty:true,
						onShow:"请输入标题",
						onFocus:"请输入标题",
						onCorrect:"您输入的标题合法"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"创建人有误,请确认"
					});		
			
				$("#goodsModelNumber")
					.formValidator({
						empty:true,
						onShow:"请输入商品型号",
						onFocus:"请输入商品型号",
						onCorrect:"您输入的型号合法"
					})
					.inputValidator({
						//min:1,
						max:50,
						onError:"商品型号有误,请确认"
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
    			
			};
	
			//关闭窗口
			function closeWindow(){
			parent.location.reload();//刷新父亲对象
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
            
            console.log(imageId);
            console.log(viewId);
          	//  var preview = document.getElementById('preview1');
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
        };
        
        
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
        
        	//添加规格和价格标签
    
			function addFormatAndPrice(){
				var preformatAndPriceNO=formatAndPriceNO;
				formatAndPriceNO++;
				$("#formatAndPriceId"+preformatAndPriceNO+"").after("<tr id=\"formatAndPriceId"+formatAndPriceNO+"\"><td name='formatCode"+formatAndPriceNO+"' value='00"+formatAndPriceNO+"' hidden='true'></td><td ><input type='text'  name='formatName"+formatAndPriceNO+"' value=''/></td><td ><input type='text'  name='orgPrice"+formatAndPriceNO+"' value=''/></td><td ><input type='text'  id='currPrice"+formatAndPriceNO+"' name='currPrice"+formatAndPriceNO+"' value=''/></td><td><button type='button' class='btn btn-danger btn-xs' style='border-radius:4px; margin-top:-5px;' onclick='deleteCurrent(this)'><i class='icon-trash icon-on-right bigger-110'></i>删除</button></td><tr>");
				$("#formatAndPriceNO").val(formatAndPriceNO);
			}
			/**********删除规格和价格标签***********/
			function deleteCurrent(obj){
				$(obj).parent().parent().remove();
				formatAndPriceNO--;
				$("#formatAndPriceNO").val(formatAndPriceNO);
			}
			
			
			//添加图片上传标签
			
			function addImageFileTr(){
				//保留上一张图片位置
				var preImageFileNO=imageFileNO;
				imageFileNO++;
				$("#imageFile"+preImageFileNO+"").after("<tr id=\"imageFile"+imageFileNO+"\"><td name='imageFileNO' hidden='true'></td>"+
			                            	"<td><input type='file' name='caseImage"+imageFileNO.toString()+"'  onchange=\"viewImage(this,'localImag"+imageFileNO.toString()+"','preview"+imageFileNO.toString()+"')\"></td>"+
			                            	"<td><div id=\"localImag"+imageFileNO.toString()+"\"><img id=\"preview"+imageFileNO.toString()+"\" width=-1 height=-1 style='diplay:none' /></td>"+
										 	"<td> <select name='positionCode"+imageFileNO.toString()+"' id='positionCode"+imageFileNO.toString()+"'><option value=''>请选择显示位置</option><option value='07001'>滚动轮播</option> <option value='07002'>详情明细</option> </select></>"+
										 	"<td><button type='button' class='btn btn-danger btn-xs' style='border-radius:4px; margin-top:-5px;' onclick='deleteImageTr(this)'><i class='icon-trash icon-on-right bigger-110'></i>删除</button></td></tr>");
				$("#imageFileNO").val(imageFileNO);
			}
			/**********删除图片上传标签***********/
			function deleteImageTr(obj){
				$(obj).parent().parent().remove();
				imageFileNO--;
				$("#imageFileNO").val(imageFileNO);
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
                        <div>
                        <!-- 表单 start -->
                        <form id="add" name="add" action="<%=basePath%>addGoodsRecord.do" method="post" enctype="multipart/form-data">
                            <div>
                            <!-- 表单元素table start -->
                            <table id ="addTableId"  cellspacing="0" width="100%" cellpadding="2" border="0">
                            <tr>
                            		<td >标题：
                            		
                            			<input type="text" id="goodsTitle" name="goodsTitle" value=""/>
                            		</td>
                            	</tr>
                            	<tr>
                            		<td >型号：
                            		
                            			<input type="text" id="goodsModelNumber" name="goodsModelNumber" value=""/>
                            		</td>
                            	</tr>
                            
                            	<!-- ---------------- 上传图片及设置图片显示位置------------------------------->
                                <tr class="FormatPriceGroup" id="FormatPriceGroup">
                            		<td class="td-left">
                            			<text>上传图片</text>
                            		</td>
                            		<td >
                            			<text>预览图片</text>
                            		</td>
                            		<td >
                            			<text>设置图片显示位置</text>
                            		</td>
                            		
                            	</tr>
                            	<tr id ="imageFile1" height="30" align="left" valign="middle">
                            				<input id="imageFileNO"  name="imageFileNO"  value="1" hidden="true"/>
			                            	<td><input type="file" name="caseImage1"  onchange="viewImage(this,'localImag1','preview1')">
			                            	</td>
			                            	<td>
			                            	<!--<p class="help-block">建议尺寸88*88</p>-->
													</div>
													<div id="localImag1"><img id="preview1" width=-1 height=-1 style="diplay:none" />
													</div>
											</td>
										 	<td>
						                    <select name="positionCode1" id="positionCode1">
						                        <option value="">请选择位置</option>
						                        <option value="07001">滚动轮播</option>
						                        <option value="07002">详情明细</option>
						                    </select>
					                    
							                </td>
							                <td style="padding:10px;">
										        	<button type="button" class="btn btn-success btn-xs" style="border-radius:4px; margin-top:-5px; margin-left:-4px;" onclick="addImageFileTr()">
														<i class="icon-plus icon-on-right bigger-110"></i>添加
													</button>
											</td>
								</tr>
								
								<!---------产品规格和价格--------------------------->
								<tr class="FormatPriceGroup" id="FormatPriceGroup">
                            		<td class="td-left">
                            		<text>产品规格</text>
                            		</td>
                            		<td class="td-right"><text>原价</text></td>
                            		<td class="td-right"><text>现价</text></td>
                            	</tr>
								<tr id="formatAndPriceId1" >
									<input id= "formatAndPriceNO" name="formatAndPriceNO" value="1" hidden='true'/>
									<td name='formatCode1' value='001' hidden='true'></td>
                            		<td >
                            			<input type="text" id="formatName1" name="formatName1" value=""/>
                            		</td>
                            		<td >
                            			<input type="text" id="orgPrice1" name="orgPrice1" value=""/>
                            		</td>
                            		<td >
                            			<input type="text" id="currPrice1" name="currPrice1" value=""/>
                            		</td>
                            		 <td style="padding:10px;">
										        	<button type="button" class="btn btn-success btn-xs" style="border-radius:4px; margin-top:-5px; margin-left:-4px;" onclick="addFormatAndPrice()">
														<i class="icon-plus icon-on-right bigger-110"></i>添加
													</button>
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
                            		
                            		<td class="td-right"><text>库存：</text>
                            			<input type="text" id="goodsStock" name="goodsStock" value=""/>
                            		</td>
                            	</tr>
      
                            	<tr>
                            		
                            		<td class="td-right">
                            		<text>备注：</text>
                            			<input type="text" id="remark" name="remark" value="" placeholder="选填"/>
                            		</td>
                            	</tr>

								
                            	<tr>
                            	
                            		<td class="td-right">
                            		<text>创建人：</text>
                            			<input type="text" id="createName" name="createName" value="周芬"/>
                            		</td>
                            	</tr>
                            	<!-- 
		                            	<tr>
		                            		<td class="td-right">
		                            		<text>创建时间：</text>
		                            			<input type="text" id="createTime" name="createTime" readonly class="Wdate" onClick="WdatePicker({el:'createTime', dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		                            		</td>
		                            	</tr>
                            	 -->
                            	
                            
                            	
			    
                            	
		
                               </div>
                            </table>
                             </div>
                            <!-- 表单元素table end -->
                        </form>
                         </div>
                        <!-- 表单 end -->
                    </td>
                </tr>
                <!-- 页面主体内容 end -->
            </table>
        </div>
        <!-- 页面容器 end -->
    </body>
</html>
