<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.common.util.SysConfig"%>
<%@page import="com.dhcc.login.po.DhccUser"%>
<%@page import="com.dhcc.bpm.identity.user.po.UserPo"%>
<%@page import="com.dhcc.bpm.identity.menu.util.MenuUtil,com.dhcc.bpm.identity.menu.po.MenuPo"%>
<%@ page import="com.dhcc.bpm.WFServer"%>
<%@ page import="com.dhcc.bpm.identity.dept.po.DeptPo"%>
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
    String menuid = request.getParameter("menuid");
    String showUserName = userPo.getRealName();//显示的人员名
    //当前登录人的岗位
	com.dhcc.bpm.identity.post.po.PostPo postPo = com.dhcc.common.util.UserUtil.getPost(request);
    String postName = "";//岗位名称
    if(postPo!=null && postPo.getPostName()!=null) {
    	postName = postPo.getPostName();
    }
    List<DeptPo> list = WFServer.getDeptListByUserId(userPo.getId(), "1");//获取登录人所在部门信息实例：DeptPo list(一个人可能身兼数职，同时在多个部门)
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>左侧菜单</title>
<base href="<%=basePath+"frame1/"%>"/>
<link href="<%=basePath+"frame1/"%>css/style-menu.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath+"frame1/"%>js/jquery-1.7.min.js"></script>
<script type="text/javascript">
 $(function(){
	 $(".photo").mouseover(function(){
		 $(".photomassage").show();
		 
		 
		 })
	 $(".photo").mouseout(function(){
		 
		 $(".photomassage").hide();
		 
		 })
	  
	  })
  $(function(){
	 $(".navmenu").mouseover(function(){
		 $(this).children("ul").show();
		 
		 
		 })
	 $(".navmenu").mouseout(function(){
		 
		 $(this).children("ul").hide();
		 
		 })
	  
	  })
	  function onMenuClick(menuId, menuName, url) {
		top.document.getElementById('currMenuId').value=menuId;
		//top.document.getElementById('frmright').src=url;
		top.window.frames["frmright"].addTab(menuId,menuName,url,1);
	}
</script>

</head>

<body class="menu">
<div id="hideCon" class="top1"> 
    <p>&nbsp;</p>
    <div class="photo"><img src="<%=basePath+"frame1/"%>images/photo.png" /></div>
     <div class="photomassage">
       <div class="top1li-1"><img src="<%=basePath+"frame1/"%>images/xinxijiantou.png" /></div>
       <div class="top1li-2"><%=userPo.getRealName() %>&nbsp;<%=list.get(0).getDeptName() %>&nbsp;<%=postName %></div>
     </div>   
  </div>
  <ul>
  <%
				    String firstThirdMenuId="";
					//获取二级菜单
					List<MenuPo> menus = new ArrayList();
					if (menuid != null && !menuid.equals("")) {
					    menus = MenuUtil.getMenubyId(menuid,userPo);
					} else {
						//menus = MenuUtils.getByLevel(2);//获得所有2级菜单\
						return;
					}

					//输出二级和三级菜单
					List<MenuPo> sons = null;
					int index = 1;
					String url = "";
					int i = 0;
					
					int firstSecordMenu=0;					
					
					
					for (MenuPo m1 : menus) {
						boolean showM1 = true;
						//if (loginUser.isSysAdmin() && m1.getFather().getId().equals("01")) {//系统管理员 拥有系统管理权限(无需在数据库中映射) tlw 20120925
						//tlw 2013.4.27 系统管理员拥有所有权限
                        
                        if(m1.getState().equals("0")) //禁用
                            continue;

						if (showM1) {
							//有的模块只到二级菜单
							url = (m1.getUrl() == null || m1.getUrl().equals("")) ? "javascript:;" : (basePath + m1.getUrl());//不为空，则证明有目标地址
							firstSecordMenu++;
							int firstThirdMenu=0;
				%>
							<li class="navmenu">
							  <a>
							  <img  class="onemenu" src="<%=basePath + (m1.getIcon()!=null?m1.getIcon().replaceAll("\\\\","/"):"") %>"/><%=m1.getMenuName()%>
							  </a>
							<ul>
				<%
				            sons = m1.getSonMenu();
							for (MenuPo m2 : sons) {
								//tlw 2013.2.27 如果type是button
								if(m2.getType().equals("button"))
									continue;
								
								if(m2.getState().equals("0")) //禁用
		                            continue;
                                
								boolean showM2 = true;
								if (showM2) {
									firstThirdMenu++;
									if(firstSecordMenu==1&&firstThirdMenu==1){
										firstThirdMenuId=m2.getId();
									}
				%>
									<li>
										<a id="<%=m2.getId()%>" href="javascript:void(0);" onclick="javascript:onMenuClick('<%= m2.getId() %>','<%= m2.getMenuName() %>','<%=basePath + m2.getUrl() + (m2.getUrl()!=null&&m2.getUrl().contains("?")?"&":"?") + "menuId=" + m2.getId() %>');" id="<%= m2.getId() %>" target="frmright"><span class="text_slice"><%=m2.getMenuName()%></span></a>
									</li>
									
									
				<%
								}
							}
				%>
							</ul>
							</li>
				<%
						}
					}
				%>
</ul>
</body>
</html>
