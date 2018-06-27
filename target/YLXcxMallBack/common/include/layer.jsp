<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 遮罩层start -->
  <div id="layerDiv" style="display: none; position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; background-color: black; z-index:2000; -moz-opacity: 0.7; opacity:.70; filter: alpha(opacity=70);"></div>
   <div id="layerTitle" style="display:none;position: fixed;margin-left: -250px;left: 50%;top:250px;z-index: 2001;padding: 0px;width:500px;height: 60px;background-color:transparent;">
        <div style="width: 100%;line-height:60px;font-size: 40px;color: white;padding-left: 70px;"><span id="layerTitleContent">数据处理中</span><span id="layerIng">...</span></div>
  </div>
<!-- 遮罩层end -->
<script language="javascript">
		var intervalObj;
		function ShowLayer(content){
			//设置提示内容
			if(content!=undefined&&content!=null&&content.length>0){
				document.getElementById("layerTitleContent").innerHTML=content;
			}
			//显示遮罩层
			$("#layerDiv").show();
			$("#layerTitle").show();
			
			intervalObj = setInterval(DataControlIng,300);
		}
		//控制数据处理提示“...”
		var count=0;
		var proStr=".";
		function DataControlIng(){
		    if(count==0){
		    	proStr="";
		    }else if(count>0&&count<=3){
		    	proStr+=".";
			}else {
				count=0;
				proStr="";
			}
		    document.getElementById('layerIng').innerHTML=proStr;
		    count++;
		}
		//隐藏遮罩层
		function HideLayer(){
			clearInterval(intervalObj);
			$("#layerTitle").hide();
			$("#layerDiv").hide();
			}
</script>