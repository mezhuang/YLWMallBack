<html>

<head>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<META http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
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
</script>
</head>
<body>
<h2>欢迎来到远联家居，系统开发中，敬请期待。

<tr>
		<td> 图片<img src="./images/a.png"/></td>
</tr>

</h2>
<button></button>
</body>
</html>
