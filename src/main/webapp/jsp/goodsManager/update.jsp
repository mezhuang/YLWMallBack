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
	String goodsId =request.getParameter("id");
	out.println("goodsId:"+goodsId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">  
	<head>
		<base href="<%=basePath%>">
		<title>编辑商品</title>
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
		
			//获取需要修改的记录	
			function initUpdateRecord() {
						$.ajax({
							type:"get",
							url:"<%=basePath%>getGoodsRecordDetail.do",
							data:{"id":"<%=goodsId%>"}, 
							success:function(data) {
							
									// var str = "${list}"
									// str里面的内容是：[{"a":"1","b":"2"},{"a":"3","b":"4"}]
									console.log(data);
									var dataList = JSON.parse(data);
									//var jsonObj =  JSON.parse(data);//转换为json对象
									//var dataList = eval("(" + data + ")");
									for(var i in dataList) {
									
										console.log("i:"+i);
										var i1=1;
										var i2=2;
										var i3=3;
										var i4=4;
										var i5=5;
										
										
										console.log(dataList[i].goods_tile + dataList[i].goods_model_number);
										$("#goodsId").val(dataList[i].goods_id);
									   $("#goodsTile").val(dataList[i].goods_tile);
									   $("#goodsModelNumber").val(dataList[i].goods_model_number);
									   $("#goodsOrgPrice").val(dataList[i].goods_org_price);
									   $("#goodsCurrPrice").val(dataList[i].goods_curr_price);
									   $("#goodsStock").val(dataList[i].goods_stock);
									   
										console.log(dataList[i].goodsImageList);
										var imageListTmp = dataList[i].goodsImageList;
										console.log(dataList[i].goodsImageList[i].goods_image_url);
										//var imageList = eval("(" + dataList[i].goodsImageList + ")");
									   var reImagesFileNo =parseInt(dataList[i].image_file_no);
									   imageFileNO=reImagesFileNo;
									   console.log("reImagesFileNo:"+reImagesFileNo);
									   
									   //显示图片
									   var one=1;
									   var zore=one-1;
									   $("#preview"+one+"").attr('src',dataList[i].goodsImageList[zore].goods_image_url); 
									   $("#imagediplay"+one+"").val(dataList[i].goodsImageList[zore].position_code); 
									  $("#imagediplay"+one+"").text(dataList[i].goodsImageList[zore].twolevel_name);
									    
									   for(var j=2;j<=reImagesFileNo;j++)
									   {
										  	var rJ=j-1;
										   $("#imageFile"+rJ+"").after("<tr id=\"imageFile"+j+"\"><td name='imageFileNO' hidden='true'></td>"+
				                            	"<td><input type='file' name='caseImage"+j+"'  onchange=\"viewImage(this,'localImag"+j+"','preview"+j+"')\"></td>"+
				                            	"<td><div id=\"localImag"+j+"\"><img id=\"preview"+j+"\" width='100px' height='120px' style='diplay:none' /></td>"+
											 	"<td> <select name='imagediplay"+j+"' id='imagediplay"+j+"'><option value=''>请选择显示位置</option><option value='07001'>滚动轮播</option> <option value='07002'>详情明细</option> </select></>"+
											 	"<td><button type='button' class='btn btn-danger btn-xs' style='border-radius:4px; margin-top:-5px;' onclick='deleteImageTr(this)'><i class='icon-trash icon-on-right bigger-110'></i>删除</button></td></tr>");
										   
											$("#preview"+j+"").attr('src',dataList[i].goodsImageList[rJ].goods_image_url); 
											$("#imagediplay"+j+"").val(dataList[i].goodsImageList[rJ].position_code); 
									  		$("#imagediplay"+j+"").text(dataList[i].goodsImageList[rJ].twolevel_name);
					 
										}
										//回显规格和价格列表
										 var reFomatPriceFileNo =parseInt(dataList[i].format_price_no);
										  formatAndPriceNO = reFomatPriceFileNo;
										 $("#formatName"+one+"").val(dataList[i].goodsImageList[zore].format_name); 
										 $("#orgPrice"+one+"").val(dataList[i].goodsImageList[zore].org_price); 
										 $("#currPrice"+one+"").val(dataList[i].goodsImageList[zore].curr_price); 
											for(var k =2;k<=reFomatPriceFileNo;k++)
											{	
												 var rk=k-1;
												 $("#formatAndPriceId"+rk+"").after("<tr id=\"formatAndPriceId"+k+"\"><td ><input type='text'  name='formatName"+k+"' value=''/></td><td ><input type='text'  name='orgPrice"+k+"' value=''/></td><td ><input type='text'  id='currPrice"+k+"' name='currPrice"+k+"' value=''/></td><td><button type='button' class='btn btn-danger btn-xs' style='border-radius:4px; margin-top:-5px;' onclick='deleteCurrent(this)'><i class='icon-trash icon-on-right bigger-110'></i>删除</button></td><tr>");
												 $("#formatName"+k+"").val(dataList[i].goodsImageList[rk].format_name); 
												 $("#orgPrice"+k+"").val(dataList[i].goodsImageList[rk].org_price); 
												 $("#currPrice"+k+"").val(dataList[i].goodsImageList[rk].curr_price);
											}
						
										$('#onelevelCode01 option:selected').text(dataList[i].goodsClassList[i].twolevel_name);//选中的文本
									   $('#onelevelCode01 option:selected').val(dataList[i].goodsClassList[i].twolevel_code);//选中的值
										
										$('#onelevelCode02 option:selected').text(dataList[i].goodsClassList[i1].twolevel_name);//选中的文本
									   $('#onelevelCode02 option:selected').val(dataList[i].goodsClassList[i1].twolevel_code);//选中的值
									   
									   	$('#onelevelCode03 option:selected').text(dataList[i].goodsClassList[i2].twolevel_name);//选中的文本
									   $('#onelevelCode03 option:selected').val(dataList[i].goodsClassList[i2].twolevel_code);//选中的值
									   
									   	$('#onelevelCode04 option:selected').text(dataList[i].goodsClassList[i3].twolevel_name);//选中的文本
									   $('#onelevelCode04 option:selected').val(dataList[i].goodsClassList[i3].twolevel_code);//选中的值
										
										$('#onelevelCode05 option:selected').text(dataList[i].goodsClassList[i4].twolevel_name);//选中的文本
									   $('#onelevelCode05 option:selected').val(dataList[i].goodsClassList[i4].twolevel_code);//选中的值
									   
									   	$('#onelevelCode06 option:selected').text(dataList[i].goodsClassList[i5].twolevel_name);//选中的文本
									   $('#onelevelCode06 option:selected').val(dataList[i].goodsClassList[i5].twolevel_code);//选中的值
									   
									
									}
								
								
							
								//$("#preview1").src.val(data.goodsImageList[0].goods_image_url);
								//$("#preview1").attr('src',data[0].goodsImageList[0].goods_image_url); 
								 //$("#province").val(data.goodsClassList[0].twolevel_code);
								//$("#province").find("option[text='"+data.goodsClassList[0].twolevel_name+"']").attr("selected",true);;
								
								
							    
							     
							//	$("#edit_phone").val(data.cust_phone);
							//	$("#edit_mobile").val(data.cust_mobile); 
							
								
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
            
            initUpdateRecord();
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
										 	"<td> <select name='imagediplay"+imageFileNO.toString()+"' id='imagediplay"+imageFileNO.toString()+"'><option value=''>请选择显示位置</option><option value='001'>轮播</option> <option value='002'>详情介绍</option> </select></>"+
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
                                    <a href="javascript:" class="hover" style="left: 0px; z-index: 99">编辑商品 </a>
                                    <div class="clear">
                                    </div>
                                </div>
                            </div>
                            <div class="btnblock" id="btnblock" style="top: -5px">
                                <a href="javascript:void(0)" onclick="submitForm();" id="submitBtn" class="easyui-linkbutton" plain="true" icon="icon-save"> 修改 </a>
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
                        <form id="add" name="add" action="<%=basePath%>updateGoodsRecord.do" method="post" enctype="multipart/form-data">
                            
                            <!-- 表单元素table start -->
                            <table cellspacing="0" width="100%" cellpadding="2" border="0">
                            	<input type="hidden" name="goodsId" id="goodsId"/>
								
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
													<div id="localImag1"><img id="preview1" width='100px' height='120px' style="diplay:none" />
													</div>
											</td>
										 	<td witdth='100px' >
						                    <select name="imagediplay1" id="imagediplay1">
						                        <option value="">请选择位置</option>
						                        <option value="001">轮播</option>
						                        <option value="002">详情介绍</option>
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
									<td name='formatCode1' value='001' hidden='true'>300</td>
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
					                    <select name="onelevelCode02" id="onelevelCode02">
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
