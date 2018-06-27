<%@page import="com.dhcc.common.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.login.po.DhccUser"%>
<%@page import="com.dhcc.bpm.identity.user.po.UserPo,com.dhcc.common.util.ProcessDefIdUtil"%>
<%@page import="com.dhcc.bpm.identity.menu.util.MenuUtil,com.dhcc.bpm.identity.menu.po.MenuPo"%>
<%@ page import="com.dhcc.bpm.identity.dept.po.DeptPo,com.dhcc.common.util.ParamConfigUtil"%>
<%@ page import="com.dhcc.common.util.FunctionUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	DhccUser loginUser = null;
	Object obj = request.getSession().getAttribute("DhccUser");
	if (obj != null) {
		loginUser = (DhccUser) obj;
	} else {
		out.println("<script>top.location='" + basePath + "login.jsp';return false;</script>");
		return;
	}
	UserPo userPo = loginUser.getUserPo();
	Date datetime = new Date();
    Calendar now = Calendar.getInstance(); 
    String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
    now.setTime(datetime);  
    int week_index = now.get(Calendar.DAY_OF_WEEK) - 1;  
    if(week_index<0){  
        week_index = 0;  
    } 
    String date=DateUtil.format(datetime, "yyyy-MM-dd");
    
    String sameDeptUserId=request.getParameter("sameDeptUserId");
	if("null".equals(sameDeptUserId)){
		sameDeptUserId=null;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>BODY-海南OA首页</title>
<base href="<%=basePath+"frame1/"%>"/>
<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery-1.8.0.min.js"></script>
<jsp:include page="/common/include/highCharts.jsp" flush="true" />
<jsp:include page="/common/include/dialog.jsp" flush="true" />
<link href="<%=basePath+"frame1/"%>css/style-body.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath+"frame1/"%>css/style-bottom.css" rel="stylesheet" type="text/css" />
<script>
var initDaiBan=0;
var timer;
$(function(){
	initBodyData();
	timer=setInterval("initBodyData()",600000);//1000为1秒钟
})
function initBodyData(){
	//校验是否session过期
	$.ajax({
        url: '<%=basePath%>' + 'LoginManage_isSessionOut.do',
        async: false,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        },
        success: function(data) {
        	if(data.indexOf("0")<0){
        		clearInterval(timer);
        		alert("登录超时，请重新登录系统！");
        		top.location.href("<%=basePath%>login.jsp");
        	}else{
        		getOnLineUserNum();
                getConsultBooksData();
                getNoticeInfo();
                getRegulationList();
                getMeetingtableList();
                getNoViewMessageCount();
            	if(<%=Boolean.parseBoolean(ParamConfigUtil.getParamConfigByPkey("eyou_server_open"))%>){
            		getUnreadMailMessageCount();
            	}
            	getWorkListRunningList();
        	}
        }
    });
}
function getUnreadMailMessageCount(){
	$.ajax({
        url: '<%=basePath%>' + 'EyouMailManage_getUnreadMailMessageCount.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        },
        success: function(data) {
        	var datas=[];
        	datas=data.split("_");
        	$(".linum1").html(datas[0]);
        	$(".linum2").html(datas[1]);
        	$(".linum3").html(datas[2]);
        	$(".linum4").html(datas[3]);
        }
    });
}
function getOnLineUserNum() {
    var onLineUserNum =0;
    var version="";
    $.ajax({ 
		type : "POST", 
		async: false,
		dataType:"text",
		url:'<%=basePath%>SysVersionManage_getCurrentVersion.do', 
		traditional: true,//在struts2下该属性必须有 
		data:{}, 
		success:function(result){ 
			version=result;
		}
	});
    //获取在线人员
    $.ajax({
        url: '<%=basePath%>' + 'TreeProcessManage_getOnLineUserNum.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        //在struts2下该属性必须有 
        data:{
            assigneeUserId:'<%=loginUser.getUserPo().getId()%>'
        },
        success: function(data) {
            onLineUserNum = data;
            $(".peoplenum").html("<a href='javascript:void(0)' onclick='javascript:toChat();'>在线人数 "+data+" 人</a><div style='float:right;margin-right:40%;'>Copyright © 2015 Powered By DHCC V"+version+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:updateDesc()'>版本更新说明</a></div>");
        }
    });
}
function updateDesc(){
	openWin('<%=basePath%>versiondetail.jsp', 800, 500, '版本更新说明');
}
function toChat(){
	openWin('<%=basePath%>onlineuser/onlineuserlist.jsp',1000,530,'在线用户');
}
//请勿删除，该方法没有用到，但是为了处理首页的弹出框口关闭时报错的问题,必须存在
function initTable(){
	if(initDaiBan==1){
		getWorkListRunningList();	
	}
	initDaiBan=0;
}
function getNoticeInfo() {
    $.ajax({
        url: '<%=basePath%>' + 'NoticeInfoManage_getNoticeInfoIndex.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        },
        success: function(data) {
        	$("#noticeInfo_ul").html(data);
        }
    });
}
function toNoticeDetail(id) {
		$.ajax({
			type : "POST", 
			url:'<%=basePath%>'+"NoticeInfoManage_isNeedSign.do",
			traditional:true,
			data:{id:id},
			success:function(result){
			if(result!=null&&result=='Y'){
				$.ajax({
					type : "POST", 
					url:'<%=basePath%>'+"NoticeReceiverManage_checkSign.do",
					traditional:true,
					data:{noticeId:id},
					success:function(result){
						if(result==0){
							if(confirm("您好，此通知需要签收后查看，请确认签收")){
								$.ajax({
									type:"POST",
									url:'<%=basePath%>'+"NoticeReceiverManage_addNoticeReceiver.do",
									traditional:true,
									data:{noticeId:id},
									success:function(result){
										if(result-0>0){
											var url = '<%=basePath%>'+'NoticeInfoManage_toNoticeInfoDetail.do?id='+id;
											openWin(url,1000,530,'通知详情');
											return;
										}else{
											alert("未成功签收，请重试或者联系管理员！谢谢！");
											return;
										}
									}
								});
							}
						}else{
							var url = '<%=basePath%>'+'NoticeInfoManage_toNoticeInfoDetail.do?id='+id;
							openWin(url,1000,530,'通知详情');
						}
					}
				});
			}else{
				var url = '<%=basePath%>'+'NoticeInfoManage_toNoticeInfoDetail.do?id='+id;
				openWin(url,1000,530,'通知详情');
			}
		}
	});
}
function toConsultBooksDetail(id){
	var url = "<%=basePath%>ConsultBooksManage_toConsultBooksDetail.do?id="+id;
	openWin(url,800,500,'参阅材料详情');
}
var w=window.top.document.body.offsetWidth;
var wo_width=parseInt((w-170)*0.3*0.7);
function getWorkListRunningList() {
    $.ajax({
        url: '<%=basePath%>' + 'ProcessMonitorManage_getWorkListRunningListIndex.do',
        async: false,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        	wo_width:wo_width
        },
        success: function(data) {
        	$("#task_table_tr").html(data);
        }
    });
}
var ac_width=parseInt((w-170)*0.38*0.75);
function getRegulationList() {
    $.ajax({
        url: '<%=basePath%>' + 'RegulationsManage_getRegulationsDataOnIndex.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        	
        },
        success: function(data) {
        	$("#consultBooksData").html(data);
        }
    });
}
function getConsultBooksData() {
    $.ajax({
        url: '<%=basePath%>' + 'ConsultBooksManage_getConsultBooksDataOnIndex.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        	ac_width:ac_width,from:1//标记从body1页面获取的数据
        },
        success: function(data) {
        	$("#activitytable_table").html(data);
        }
    });
}
function toRegulationsDetail(id){
	var url = "<%=basePath%>RegulationsManage_toRegulationsDetail.do?id="+id;
	openWin(url,800,500,'规章制度详情');
}
function getMeetingtableList() {
    $.ajax({
        url: '<%=basePath%>' + 'MeetManage_getMeetingListIndex.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        },
        success: function(data) {
        	$("#nodemessage_ul").html(data);
        }
    });
}
function toMeetDetail(id){
	openWin('<%=basePath%>NewMeetManage_toNewMeetDetail.do?id='+id, 800, 500, '会议详情').max();
}
function getNoViewMessageCount() {
    $.ajax({
        url: '<%=basePath%>' + 'NoticeMessageManage_getNoViewMessageCountIndex.do',
        async: true,
        type: "post",
        dataType: 'text',
        traditional: true,
        data:{
        },
        success: function(res) {
        	if(Number(res>9)){
        		$("#linum5").html("<a href='javascript:void(0)' onclick='javascript:toMyMessage();'>"+res+"</a>");
        	}else{
        		$("#linum5").html("<a href='javascript:void(0)' onclick='javascript:toMyMessage();'> 0"+res+"</a>");
        	}
        }
    });
}
function toMyMessage(){
	var url = "<%=basePath%>sys/noticemessage/list.jsp?currentMenuId=d1dd5329-e264-4c00-b2af-345427a1aa14";
	top.window.frames["frmright"].addTab('d1dd5329-e264-4c00-b2af-345427a1aa14','我的消息',url,1);
}
function initDaiBanCharts(){
	var colors = Highcharts.getOptions().colors;
	$.ajax({ 
		type : "POST", 
		dataType:"text",
		async:false,
		url:'<%=basePath%>ProcessMonitorManage_getHighChartsData.do', 
		traditional: true,//在struts2下该属性必须有 
		data:{}, 
		success:function(result){ 
			var categories1 = result.split("|")[0];
		    var data1 = result.split("|")[1];
			var res1=eval(categories1)
			var res2=eval(data1)
			var categories = res1;
		    var data = res2 ;
			// Build the data arrays
			var browserData = [];
			var versionsData = [];
			for (var i = 0; i < data.length; i++) {
			    // add browser data
			    browserData.push({
			        name: categories[i],
			        y: data[i].y,
			        color: colors[i]
			    });
			    // add version data
			    for (var j = 0; j < data[i].drilldown.data.length; j++) {
			        var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
			        versionsData.push({
			            name: data[i].drilldown.categories[j],
			            y: data[i].drilldown.data[j],
			            color: colors[i]
			        });
			    }
			}
			// Create the chart
			$('#container').highcharts({
			    chart: {
			        type: 'pie',
			        height:210,
			        width:310,
			        backgroundColor: 'rgba(0,0,0,0)',
			        margin: [0, 0, 0, 25] //距离上下左右的距离值 
			    },
			    title: {
			        text: ''
			    },
			    yAxis: {
			        title: {
			            text: ''
			        }
			    },
			    plotOptions: {
				    pie: {
		                startAngle: -90,
		                endAngle:90,  
		                center: ['42%', '75%']
		            }
			    },
			    tooltip: {
				    valueSuffix: ''
			    },
			    series: [{
			        name: '待办数量',
			        data: browserData,
			        size: '150%',
			        events: {
			        	click: function(e) {
			        		toDaiBan();
			        	}
			        },
			        dataLabels: {
			            formatter: function() {
			                return this.y > 0 ? this.point.name :null;
			            },
			            color: 'white',
			            distance: -55
			        }
			    }, {
			        name: '待办数量',
			        data: versionsData,
			        size: '150%',
			        innerSize: '120%',
			        events: {
			        	click: function(e) {
			        		toDaiBan();
			        	}
			        },
			        dataLabels: {
			            formatter: function() {
			                // display only if larger than 1
			                //return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +''  : null;
			            }
			        }
			    }]
			});
		}
	});
}
function toDaiBan(){
	var url = "<%=basePath%>bpm/worklist/index.jsp?from=index&currentMenuId=14839536-4356-40ce-b2f0-6e3a7f3a07a9";
	top.window.frames["frmright"].addTab('14839536-4356-40ce-b2f0-6e3a7f3a07a9','待办业务',url,1);
}
function TaskClaim(taskId,processInstanceId,taskName,versionId){
	$.ajax({ 
		type : "POST", 
		dataType:"text",
		async:false,
		url:'<%=basePath%>WorkListRunningManage_isTaskCompleted.do', 
		traditional: true,//在struts2下该属性必须有 
		data:{taskId:taskId}, 
		success:function(result){ 
			if(result=="true"){
				alert("当前任务已被完成，不需要办理!"); 
				//initTable(); 
			}
			else if(result=="false"){
				$.ajax({ 
					type : "POST", 
					dataType:"text",
					async:false,
					url:'<%=basePath%>WorkListRunningManage_isProcessSuspend.do', 
					traditional: true,//在struts2下该属性必须有 
					data:{processInstanceId:processInstanceId}, 
					success:function(result){ 
						if(result=="true"){
							alert("当前任务已被挂起，不允许办理!"); 
							//initTable(); 
						}
						else if(result=="false"){
						/*
						    $.ajax({ 
									type : "POST", 
									dataType:"text",
									async:false,
									url:basePath+'<%=sameDeptUserId==null?"WorkListRunningManage_claimTask.do":"WorkListRunningManage_qiangTask.do"%>', 
									traditional: true,//在struts2下该属性必须有 
									data:{taskId:rows[0].taskId,<%=sameDeptUserId==null?("assigneeUserId:getUserId()"):("userId:'"+sameDeptUserId+"'")%>}, 
									success:function(result){ 
										if(result=="false"){
											$.messager.alert('提示',"当前任务已被其他人认领，不允许再次认领!"); 
											initTable(); 
										}
										else if(result=="true"){
										    //openWin(basePath+"bpm/worklist/handle/form.jsp?taskId="+rows[0].taskId,"业务办理-"+rows[0].taskName,true);
										    openWin(basePath+"ProcessHandleManage_toHandleForm.do?taskId="+rows[0].taskId,"业务办理-"+rows[0].taskName);
										    initTable(); 
										    
										}
										else {
										   $.messager.alert('提示',"任务认领失败，原因不明，请联系系统管理员！");
										}
									}
								});
						*/
							var url="<%=basePath%>ProcessHandleManage_toHandleForm.do?taskId="+taskId; 
							//$.dialog({title:"业务办理-"+taskName,width:'1000px',height:'600px',lock:true,content:'url:'+url}).max();
							initDaiBan=1;
							if(versionId=="<%=ProcessDefIdUtil.综合行政_发起常务会议%>" || versionId=="<%=ProcessDefIdUtil.综合行政_发起会议%>" || versionId=="<%=ProcessDefIdUtil.督查督办_督查专报%>" || versionId=="<%=ProcessDefIdUtil.督查督办_督办汇报%>"){
								openWin(url,1000,500,"业务办理-"+taskName,200,'max');
							}else{
								openWin(url,false,false,"业务办理-"+taskName,200,true,true);
							}
							//initTable();
						}
						else {
						   $.messager.alert('提示',"任务认领失败，原因不明，请联系系统管理员！");
						}
					}
				});
			}
			else {
			   $.messager.alert('提示',"任务办理失败，完成情况不明，请联系系统管理员！");
			}
		}
	});
}
$(function () {
	initDaiBanCharts();
});
</script>
</head>

<body style="background:url(<%=basePath+"frame1/"%>images/bg.png) no-repeat left bottom; background-attachment:fixed;">
    <div class="neirong">

    <div class="nra">
     
        <div class="news">
            <div class="newsbt"><img class="newsbtbg" src="<%=basePath+"frame1/"%>images/news-bt.png" /></div>
            <div class="newsnr">
                <ul id="noticeInfo_ul">
                   
                </ul> 
             </div>                  
          </div>
        <div class="activity" style="height:355px;">
        
           <div class="activitybt">
              <span><img src="<%=basePath+"frame1/"%>images/canyuecailiao1.png" /></span>
            </div>
            
           <div style="width:100%;">
               <ul id="activitytable_table">
                   
               </ul>
            </div>   
         
        </div>
        
      </div>
       
   <div class="four-modular">
   
  <div class="task">
             <div class="taskbt"></div>
             <div class="jindu" id="container" style="min-width:280px;height:103px"></div>
              
             <div class="note">进行中的任务</div>
             <div class="task-table">
                <table id="task_table_tr" cellpadding="0" width="100%" cellspacing="10" border="0">
                      
                </table>
            </div> 
             
         </div>
          <div class="fenge"></div> 
         
         
         <div class="meeting">
         <div class="meetingbt"></div>
         <div class="meetingnr">
             <div class="node"></div>
             <ul class="nodemessage" id="nodemessage_ul">
             
             </ul>
         </div>
       
         </div>
           <div class="fenge"></div>
       
          
         <div class="data">
         <div class="databt" style="background:url(<%=basePath+"frame1/"%>images/guizhangzhidu.png) no-repeat;"></div>
         <ul class="dataul" id="consultBooksData">
         </ul>
         
         </div>
         <div class="fenge"></div>
         
         
          <div class="remind">
             <div class="remindbt"></div>
             <ul class="remindnr">
             <a href="javascript:void(0)" onclick="top.toMail();"><li class="unread"><span class="linum1">00</span></li></a>
             <a href="javascript:void(0)" onclick="top.toMail();"><li class="inbox"><span class="linum2">00</span></li></a>
             <a href="javascript:void(0)" onclick="top.toMail();"><li class="outbox"><span class="linum3">00</span></li></a>
             <a href="javascript:void(0)" onclick="top.toMail();"><li class="draftbox"><span class="linum4">00</span></li></a>
             <a href="javascript:void(0)" onclick="top.doDetail();"><li class="remindnum" id="remindnum1"><span class="linum5" id="linum5" >00</span></li></a>
             </ul>
          </div>
   
   </div>  
   <div class="footer">
</div> 
</div>
<div id="bottom_index" class="bottom">
	<div class="peoplenum"></div>
</div>
</body>
</html>
