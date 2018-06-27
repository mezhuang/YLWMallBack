<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.bpm.identity.menu.util.MenuUtil,com.dhcc.bpm.identity.menu.po.MenuPo"%>
<%@page import="com.dhcc.bpm.identity.user.po.UserPo"%>
<%@page import="com.dhcc.login.po.DhccUser"%>
<%@ page import="com.dhcc.common.util.ParamConfigUtil"%>
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
	List<MenuPo> root_menu = MenuUtil.getFirstMenuList(userPo);
	Integer menuCount = Integer.valueOf(request.getParameter("menuCount"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<base href="<%=basePath+"frame1/"%>"/>
<link href="<%=basePath+"frame1/"%>css/banner.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>frame1/css/style-firstmenu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>frame1/js/stepcarousel.js"></script>
<script>
var menuCount=<%=menuCount%>;
var width=window.top.document.body.offsetWidth; 
var falg=true;
if(menuCount>=<%=root_menu.size()%>){
	falg=false;
}
if(menuCount<=0){
	menuCount=0;
}
stepcarousel.setup({
	 galleryid: 'mygallery', //id of carousel DIV
	 beltclass: 'belt', //class of inner "belt" DIV containing all the panel DIVs
	 panelclass: 'panel', //class of panel DIVs each holding content
	 panelbehavior: {speed:300, wraparound:false, persist:true},
	 autostep: {enable:falg, moveby:menuCount, pause:10000},
	 defaultbuttons: {enable: falg, moveby: menuCount, leftnav: ['<%=basePath%>frame1/images/firstleft-2.png', -10, 25], rightnav: ['<%=basePath%>frame1/images/firstright-2.png', -5, 25]},
	 statusvars: ['statusA', 'statusB', 'statusC'], //register 3 variables that contain current panel (start), current panel (last), and total panels
	 contenttype: ['inline'], //content setting ['inline'] or ['external', 'path_to_external_file']
	 oninit:function(){
		 isloaded=true
		 document.getElementById('displaycssbelt').style.visibility="visible";
		 //document.getElementById('stocklevels').style.visibility="visible";
	 }
})
//oururl 一次菜单自己本身的url  如果oururl不为空 则需要打开url的地址
function MenuClickBanner(menuId, menuName, url,target,flag,oururl){
	 $(".nav_icon_h_item >a").each(function(i){
         $(".nav_icon_h_item >a").removeClass("current");
     });
     $("#"+menuId).addClass("current");
     parent.MenuClick(menuId, menuName, url,target,flag);
     if(oururl!=null && oururl!=''){
    	 parent.onMenuClick(menuId,menuName+"首页","<%=basePath%>"+oururl); 
     }
   	 //处理安全邮箱的问题
     if(menuId=='<%=ParamConfigUtil.getParamConfigByPkey("EYOU_MAIL_MENUID")%>'){
    	 top.toMail();
     }
}
$(window).resize(function() {
    var w=window.top.document.body.offsetWidth; 
    var menuCount = parseInt((w-700)/85); 
	location.href="<%=basePath%>frame1/hainan-banner.jsp?menuCount="+menuCount;
});
</script>
</head>
<body style="min-width:<%=85*menuCount-100%>px;">
    <div class="banner">
     <div class="banner-left">
      <div class="logo"></div>
      <div class="menu-f">
      	<div class="quite" onclick="parent.LogOut()" style="cursor:pointer"></div>
      	<div id="mygallery" class="stepcarousel">
		<div class="belt" id="displaycssbelt">
      	<%
									String firstMenuId = "";
									if(!root_menu.isEmpty() && root_menu.size()>0){
										for(int i=0;i<root_menu.size();i++) {
											if(!root_menu.get(i).getState().equals("0")){
	                                        	firstMenuId = root_menu.get(i).getId();
	                                        	break;
	                                        }
										}
									}
									if(!root_menu.isEmpty() && root_menu.size()>0){
										for(int i=0;i<root_menu.size();i++) {
											MenuPo m=root_menu.get(i);
											if(m.getState().equals("0"))//禁用
	                                            continue;
%>
		<div class="panel">
   		<div class="nav_icon_h_item">
			<a id="<%= m.getId() %>" onclick="MenuClickBanner('<%= m.getId() %>', '<%= m.getMenuName() %>', '<%=basePath%>frame1/hainan-menu.jsp?menuid=<%=m.getId()%>','frmleft','<%=m.getFlag()==null?"":m.getFlag()%>','<%=m.getUrl()==null?"":m.getUrl()%>')">
				<div class="nav_icon_h_item_img">
					<img src="<%=basePath + (m.getIcon()!=null?m.getIcon().replaceAll("\\\\","/"):"") %>" />
				</div>
				<div class="c" style="font-size: 12px;"><%=m.getMenuName()%></div> </a>
		</div>
		</div>
   <% } }%>
   </div></div>
   </div>
      </div>
    </div>
<script>
	document.getElementById("mygallery").style.width=(menuCount*85)+"px";
		$(function(){
			document.getElementById("<%=firstMenuId%>").click();
			try{
				document.getElementById("rightbtn").click();//该按钮在js中已经定义了，不允许删除该段代码；当一级菜单较少,不存在左右滚动图标时，rightbtn不存.zhangyx 2015-10-19
				document.getElementById("rightbtn").click();
			}catch(e){}
		})
	</script>
</body>
</html>
