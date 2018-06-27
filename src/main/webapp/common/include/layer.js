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