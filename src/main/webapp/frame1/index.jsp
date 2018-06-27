<%@page import="java.io.File"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.common.util.SysConfig"%>
<%@page import="com.dhcc.login.po.DhccUser"%>
<%@page import="com.dhcc.bpm.identity.user.po.UserPo"%>
<%@page import="com.dhcc.bpm.identity.menu.util.MenuUtil,com.dhcc.bpm.identity.menu.po.MenuPo"%>
<%@ page import="com.dhcc.bpm.WFServer"%>
<%@ page import="com.dhcc.common.util.DataDicUtil" %>
<%@ page import="com.dhcc.hrm.param.po.DataDicPo" %>
<%@ page import="com.dhcc.common.util.DataDicConstant"%>
<%@ page import="com.dhcc.bpm.identity.dept.po.DeptPo,com.dhcc.common.util.ParamConfigUtil"%>
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
    String showUserName = userPo.getRealName();//显示的人员名
    String img_path=userPo.getImg_path();
    String rootPath = request.getRealPath(File.separator);// 根路径
    //System.out.println("rootPath："+rootPath+"\nbasePath："+basePath+"\npath："+path+"\nimg_path："+img_path);
    //当前登录人的岗位
	com.dhcc.bpm.identity.post.po.PostPo postPo = com.dhcc.common.util.UserUtil.getPost(request);
    String postName = "";//岗位名称
    if(postPo!=null && postPo.getPostName()!=null) {
    	postName = postPo.getPostName();
    }
    List<DataDicPo> dicPost = DataDicUtil.getDataDicByTypeCode(DataDicConstant.POSITION);
    String position = "";//职务名称
    String positionseq="";
    for(DataDicPo dataDicPo:dicPost){ 
    	if(dataDicPo.getData_Id().equals(userPo.getPosition())){
    			position=dataDicPo.getData_Name();
    			positionseq=dataDicPo.getSeq();
    	}
	}
    List<DeptPo> list = WFServer.getDeptListByUserId(userPo.getId(), "1");//获取登录人所在部门信息实例：DeptPo list(一个人可能身兼数职，同时在多个部门)
    //List<MenuPo> root_menu = MenuUtil.getFirstMenuList(userPo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=basePath%>favicon.ico" rel="shortcut icon" />
<title><%= SysConfig.SITE_NAME %></title>
<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>frame1/js/leftmenu.js"></script>
<jsp:include page = "/common/include/dialog.jsp" flush = "true"/>
<!-- dwr框架  消息推送  start-->
<script type='text/javascript' src='<%=basePath%>dwr/engine.js'></script>  
<script type='text/javascript' src='<%=basePath%>dwr/util.js'></script>  
<script type="text/javascript" src="<%=basePath%>dwr/interface/UserReminds.js"></script>
<script>
	//用户的职务的排序序号
	var positionseq='<%=positionseq%>';
	//判断部门的字数是否超过10个，超过则头像的人员详情高度为92px,部门名称换行显示
	var dcount=0;
	if(<%=!list.isEmpty()&& list.size()>0 && list.get(0).getDeptName().length()>10%>){
		dcount=11;
	}
	$(function(){
		$.ajax({ 
			type : "POST", 
			dataType : "text", 
			url:'<%=basePath%>UserManage_getHeadImgPath.do', 
			traditional: true,//在struts2下该属性必须有 
			data:{id:'<%=userPo.getId() %>'}, 
			success:function(result){ 
				if(result!=null&&result!='')
					$("#head_photo").attr("src","<%=basePath%>"+result);    
			}
		});
	})
</script>
<!-- dwr框架  消息推送  end  -->
<link href="<%=basePath+"frame1/"%>css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath+"frame1/"%>css/style-menu.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath+"frame1/"%>css/style-leftmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath+"frame1/"%>css/tc.css" />
 <style>
html,body{
height:100%;
}
</style>
<script>
	$(function(){
		//iframe跨域单点登录
		if(<%=Boolean.parseBoolean(ParamConfigUtil.getParamConfigByPkey("xjjwebservice.open"))%>){
			var url= "<%=ParamConfigUtil.getParamConfigByPkey("XJJ_PROJECT_PATH")%>logonOther.jsp?username=<%=userPo.getUserName()%>&token=<%=SysConfig.WEB_PROJECT_KEY%>";   //将验证新境界用户名密码的地址指向此iframe
	        var src=document.createElement("iframe");//以下创建一个隐藏的iframe 
	        src.setAttribute("width",0);  
	        src.setAttribute("height",0);  
	        src.src=url;  
	        document.body.appendChild(src); 
		}
	})
  //退出系统 确认消息
    function LogOut(){
        $.dialog({
            title:'信息确认',
            lock: true,
            content: "<span style='font-size:9pt'><img src='<%= basePath %>plugins/dialog/skins/icons/prompt_yellow.gif' width='34' height='34' align='absmiddle' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，<%=showUserName%>，确定要退出系统吗？</span>",
            width: '400px',
            height: '150px',
            max:false,
    	    min:false,
            ok: function(){
                window.location="<%=basePath + "LoginManage_distroyLogin.do"%>";
            },
            cancelVal: '取消',
            cancel: true /*为true等价于function(){}*/
        });
    }
</script>
<script type="text/javascript">
 //falg区分菜单数据来源，本项目以及新境界的项目数据
 function MenuClick(menuId, menuName, url,target,flag){
     //document.getElementById(target).src=url;
     $.ajax({ 
			type : "POST", 
			dataType:"text",
			async:false,
			url:'<%=basePath%>MenuManage_getSecondMenu.do', 
			traditional: true,//在struts2下该属性必须有 
			data:{id:menuId,flag:flag}, 
			success:function(res){ 
				 if(res.indexOf("error")!=-1){
					 top.location="<%=basePath%>login.jsp";
					 return false;
				 }else{
					 var targetObj =$("#secondMenu").html(res);
					 $.parser.parse(targetObj);
					 $(".sdmenu").hide();
						$(".sdmenu").find("div>div").hide();
						$($(".lmenu_bg").children()[0]).find(".sdmenu").show();//展开二级
						$($(".lmenu_bg").children()[0]).addClass("menulvl1");
						/**以下一行注释，若需要展开三级菜单 取消注释即可
						$($($(".lmenu_bg").children()[0]).find(".sdmenu").children()[0]).find("a").show();//若第一个二级菜单下有三级菜单，则展开
						*/
						
						//一级菜单点击事件
						$(".menutop").click(function(){
							//判断是否点击的是已展开的
							if($(this).parent().hasClass("menulvl1")){
								//折叠当前
								$(this).parent().find(".sdmenu").slideUp(500);
								$(this).parent().removeClass("menulvl1");
							}else{	
								//折叠其他二级
								$(".sdmenu").slideUp(500);
								$(".menutop").parent().removeClass("menulvl1");
								//展开当前二级
								$(this).parent().find(".sdmenu").slideDown(500);
								$(this).parent().addClass("menulvl1");
							}
						});
						//二级菜单点击事件
						$(".sdmenu>div>span").click(function(){
							//判断是否点击的是已展开的
							if($(this).parent().hasClass("menulvl2")){
								//折叠当前
								$(this).parent().find("div").slideUp(500);
								$(this).parent().removeClass("menulvl2");
							}else{
								//存在三级菜单
								if($(this).find("a").attr("href")==undefined){
									//折叠其他三级
									$(".sdmenu>div>div").slideUp(500);
									$(".sdmenu>div").removeClass("menulvl2");
									//展开当前三级
									$(this).parent().find("div").slideDown(500);
									$(this).parent().addClass("menulvl2");
								}else{
									//不存在三级菜单，链接直接跳转 并清除其他二级菜单展开状态、三级菜单选中状态 并折叠三级
									$(".sdmenu>div>div").slideUp(500);
									$(".sdmenu>div").removeClass("menulvl2");
									$(".sdmenu>div>div>a").removeClass("current");
								}
							}
						});
						//三级菜单点击事件（选中状态）
						$(".sdmenu>div>div>a").click(function(){
							$(".sdmenu>div>div>a").removeClass("current");
							$(this).addClass("current");	
						});
				 }
			}
	});
 }
 function onMenuClick(menuId, menuName, url) {
		top.document.getElementById('currMenuId').value=menuId;
		//top.document.getElementById('frmright').src=url;
		top.window.frames["frmright"].addTab(menuId,menuName,url,1);
}
//通过该方法与后台交互，确保推送时能找到指定用户  
 function onPageLoad(){  
    var userName = '<%=userPo.getUserName()%>';  
    UserReminds.getData(userName);  //调用后台程序 
 }  
 //推送信息  
 var initcount=0;
 var t=0;
 var sh=0;
 function showMessage(autoMessage){ 
	 try{
		 var msg=[];
		 msg=autoMessage.split("DHCC_DHCC");
		 if(initcount==0){
			 //首次登陆展示3条
			 $('#tr0').css('display','none');
			 $('#tr2').css('display','none');
			 $('#tr1').css('display','none');
			 for(var i=0;i<msg.length;i++){
				 if(msg[i]!=null && msg[i]!=""){
					 $('#tr'+i).css('display','');
					 $('#td'+i).html(msg[i]);
				 }
			 }
		 }else{
			 //只有一条
			 if(msg[0]!=null && msg[0]!=""){
				 $('#tr0').css('display','');
				 $('#tr1').css('display','none');
				 $('#tr2').css('display','none');
				 $('#td0').html(msg[0]);
			 }
		 }
		 if(sh==0 || sh==-1){
			 $('.alretbox').show();
			 t = setTimeout(function() {popout();},60000);
			 sh=2;
		 }
	 }catch(e){
		
	 }
	 initcount=1;
 } 
 $(document).ready(function(){
      var w=window.top.document.body.offsetWidth;
  	  var menuCount = parseInt((w-700)/85); 
	  document.getElementById("topframe").src="<%=basePath%>frame1/hainan-banner.jsp?menuCount="+menuCount;
  });
 function updateState(chatId,state){
		$.ajax({ 
			type : "POST", 
			dataType : "text", 
			url:'<%=basePath%>updateState.do', 
			traditional: true,//在struts2下该属性必须有 
			params : {
				chatId : chatId,
				state:state
			},
			success:function(result){ 
				
			},
			error:function(){
				$.messager.alert('提示','该条消息的附件被删除','warning');
			}
		});
	}
 function toMail(){
		try{
			$.ajax({
		        url: '<%=basePath%>' + 'EyouMailManage_getCurrentTime.do',
		        async: false,
		        type: "post",
		        dataType: 'text',
		        traditional: true,
		        data:{
		        },
		        success: function(data) {
		        	var datas=[];
		        	datas=data.split("_");
		        	var url= "http://<%=ParamConfigUtil.getParamConfigByPkey("eyou_ip")%>/api/sso/login?auth_type=simple&auth_signature="+datas[1]+"&auth_timestamp="+datas[0]+"&auth_key=<%=ParamConfigUtil.getParamConfigByPkey("api_key")%>&email=<%=userPo.getUserName()%>@<%=ParamConfigUtil.getParamConfigByPkey("eyou_domain")%>";
		    		window.open(url);
		        }
		    });
		}catch(e){
			
		}
	}
</script>
</head>
<body style="overflow-y:hidden;height:100%;overflow-x:hidden;" onload="resize();">
<input type="hidden" id="currMenuId" value="" />
<div id="hbox" class="top">
	<iframe scrolling="auto" height="100%" width="100%" frameBorder=0 id="topframe" name="topframe" src="" allowTransparency="true"></iframe>
</div>
<div id="menu_index" style="position:absolute;left:0;height:100%;z-index:1;overflow-y:auto;overflow-x:hidden;"  class="leftmenu">
  	<div id="hideCon" class="top1"> 
    <div class="photo"><img id="head_photo" src="<%=(userPo.getImg_path()!=null && !userPo.getImg_path().equals(""))?(basePath+userPo.getImg_path()):(basePath+"frame1/images/photo.png")%>" /></div>
    
     <div class="photomassage">
       <div class="top1li-1"><img  src="<%=basePath+"frame1/"%>images/xinxijiantou.png" /></div>
       <div class="top1li-2"><%=userPo.getRealName() %><br/><%=(!list.isEmpty()&& list.size()>0)?(list.get(0).getDeptName().length()>10?(list.get(0).getDeptName().substring(0, 10)+"<br/>"+list.get(0).getDeptName().substring(10, list.get(0).getDeptName().length())):list.get(0).getDeptName()):"" %><br/><%=position %></div>
     </div>   
  </div>
  <div id="secondMenu" class="lmenu_bg">
  </div>
</div>
<div id="body_index" style="margin-left:170px;z-index:1;">
	<iframe scrolling="auto" height="100%" width="100%" frameBorder=0 id="frmright" name="frmright" src="<%=basePath%>frame1/right.jsp" allowTransparency="true"></iframe>
</div>
<div class="alretbox" style="display:none;">
<div class="alretbox-title">我的消息<span class="ico-close" onclick="popout()"></span></div>
   <table border="0" cellspacing="0">
      <tr id="tr0">
         <td width="50"><div class="ico-mail"></div></td>
         <td id="td0" class="alretboxcont"></td>
      </tr>
      <tr id="tr1" class="grybg">
         <td><div class="ico-mail"></div></td>
         <td id="td1" class="alretboxcont"></td>
      </tr>
      <tr id="tr2">
         <td><div class="ico-mail"></div></td>
         <td id="td2" class="alretboxcont"></td>
      </tr>
   </table>
   <div class="btnbox">
      <div>
         <button class="btn1" onclick="ignoreAll()">忽略全部</button>
         <button class="btn2" onclick="doDetail()">查看全部</button>
      </div>
   </div>
   
</div>
<script>
function resize(){  
	document.getElementById('menu_index').style.height = (document.body.clientHeight -$("#hbox").height())+"px";
	document.getElementById('body_index').style.height = (document.body.clientHeight -$("#hbox").height())+"px";
    document.getElementById('frmright').style.height = (document.body.clientHeight -$("#hbox").height())+"px";  
}  
window.onresize = resize;
</script>
<script>
$(function(){  
    	//这句话千万不能少 ，表示允许使用推送技术  
		dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
    	onPageLoad();
    	dwr.engine.setErrorHandler(function(){});
	});
</script>
<script type="text/javascript">
	function doDetail(){
		try{
			$('.alretbox .alretbox-title .ico-close').click();
			onMenuClick('0b32093f-62b1-4ce3-a067-f1ae6903a089','我的消息','<%=basePath%>sys/noticemessage/list.jsp?currentMenuId=d1dd5329-e264-4c00-b2af-345427a1aa14');
		}catch(e){
		}
	}
	function toUrl(bussinessId) {
		$('.alretbox .alretbox-title .ico-close').click();
		var url='<%=basePath%>NoticeMessageManage_toNoticeMessageDetail.do?id='+bussinessId;
		$.dialog({id:'add1',parent:this,zIndex:100,title:'消息详情',width:'800px',height:'500px',lock:true,content:'url:'+url,close: function(event, ui) {initTable();}}).max();
	}
	function ignoreAll() {
		$('.alretbox .alretbox-title .ico-close').click();
		$.ajax({
	        url: '<%=basePath%>' + 'NoticeMessageManage_ignoreAllUnReadNoticeMessage.do',
	        async: true,
	        type: "post",
	        dataType: 'text',
	        traditional: true,
	        data:{
	        },
	        success: function(data) {
	        }
	    });
	}
	function popout() {
		$('.alretbox').hide();
		clearTimeout(t);
		sh=-1;
		t=-1;
	}
	function initTable(){
		
	}
</script>
</body>
</html>