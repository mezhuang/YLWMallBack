<html>

<head>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<META http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<jsp:include page="/common/include/jquery.jsp" flush="true"/>
		<jsp:include page="/common/include/easyui.jsp" flush="true"/>
		<jsp:include page="/common/include/dialog.jsp" flush="true"/>
<script lanuage="javascript">
$("#saveuddd").click(function(){
                    var saveDataAry=[];
                    var data1={"name":"test","password":"gz"};
                    var data2={"name":"ququ","password":"gr"};
                    saveDataAry.push(data1);
                    saveDataAry.push(data2);
                    $.ajax({
                        type:"POST",
                        url: "http://203.195.200.199/springmvc_demo/addCustomerReport.do",
                        dataType:"json",
                        contentType:"application/json", // 指定这个协议很重要
                        data:JSON.stringify(saveDataAry), //只有这一个参数，json格式，后台解析为实体，后台可以直接用
                        success:function(data){
                        }
                    });
                })
                
                
/**********添加多文件上传************/
 function plusFile(){
$("#otherFile").append("<p style='margin-top:-2px;'><input type='file' name='file' style='display:inline; width:180px;'/><button type='button' class='btn btn-danger btn-xs' style='border-radius:4px; margin-top:-5px;' onclick='deleteCurrent(this)'><i class='icon-trash icon-on-right bigger-110'></i>删除</button></p>");
            };
                
/**********删除多文件上传***********/
function deleteCurrent(obj){
	$(obj).parent().remove();
      }          
</script>
</head>
<body>
<h2>欢迎来到远联家居，系统开发中，敬请期待。

<table class="exhibit_table" style="font-size:13px; text-align:left;">  
										    
										    <tr>  
										        <td style="width:80px;" align="right">上传计划单</td>  
										        <td style="padding:10px;"><input type="file" name="file" style="display:inline; width:180px;"/>
										        	<button type="button" class="btn btn-success btn-xs" style="border-radius:4px; margin-top:-5px; margin-left:-4px;" onclick="plusFile()">
														<i class="icon-plus icon-on-right bigger-110"></i>添加
													</button>
												</td>										      						        
										    </tr>  
										    <tr> 
											    <td></td>
											    <td style="padding:10px;"><div id="otherFile"></div></td>
											</tr>
											
										</table> 

</h2>
<button></button>
</body>
</html>
