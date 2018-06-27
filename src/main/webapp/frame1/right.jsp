<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.dhcc.common.util.ParamConfigUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<jsp:include page = "/common/include/jquery.jsp" flush = "true"/>
		<jsp:include page = "/common/include/easyui.jsp" flush = "true"/>
        <jsp:include page = "/common/include/dialog.jsp" flush = "true"/>
        <link href="<%=basePath%>frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="blue" prePath="./" />
        
		<style type="text/css">
			body {margin: 0px;padding: 0px;} 
			.tabs-title{font-family:宋体,SimSun,微软正黑体,Microsoft JhengHei,新宋体, NSimSun;}
		</style>
	</head>

	<body onresize="WindowResize()" style="overflow:scroll;overflow-x:hidden;overflow-y:hidden;">
		<div id="tabs" class="easyui-tabs" style="boder:0px;">
		<script>
		$("#tabs").attr("height",($(document).height())+"px;");
	</script>
			<div title="首页" closable="false" style="overflow: hidden;">
			</div>
		</div>
        <div id="tabsMenu" class="easyui-menu" style="width:120px;">  
            <div name="close" data-options="iconCls:''">关闭</div>  
            <div name="Other" data-options="iconCls:''">关闭其他</div>  
            <div name="All" data-options="iconCls:''">关闭所有</div>
        </div>
        
	</body>
	
	<script type="text/javascript">
	    window.onerror=function(){//屏蔽JavaScript出错提示  2012.3.31 贺波
          return true;
        }
	    var tabPanel="tabs";
	    var lastTabs = new Array();
	    
	    
	  	//绑定tabs的右键菜单
	    $("#tabs").tabs({
	        onContextMenu : function (e, title) {
	            e.preventDefault();
	            if(title=="首页"){
	        		return;
	        	}
	            $('#tabsMenu').menu('show', {
	                left : e.pageX,
	                top : e.pageY
	            }).data("tabTitle", title);
	        }
	    });
	    //实例化menu的onClick事件
	    $("#tabsMenu").menu({
	        onClick : function (item) {
	            CloseTab(this, item.name);
	        }
	    });
	    /*
	     *zhangyouxue 2015.09.07
	     *用于流程发起关闭弹框事件
	     */
	    function CloseTabByOA(tabTitle) {
	    	$("#tabs").tabs("close", tabTitle);
	    }
	  	//几个关闭事件的实现
	    function CloseTab(menu, type) {
	        var curTabTitle = $(menu).data("tabTitle");
	        var tabs = $("#tabs");
	        if (type === "close" && curTabTitle!="首页") {
	            tabs.tabs("close", curTabTitle);
	            return;
	        }
	        
	        var allTabs = tabs.tabs("tabs");
	        var closeTabsTitle = [];
	        
	        $.each(allTabs, function () {
	            var opt = $(this).panel("options");
	            if (opt.closable && opt.title != curTabTitle && type === "Other") {
	                closeTabsTitle.push(opt.title);
	            } else if (opt.closable && type === "All") {
	                closeTabsTitle.push(opt.title);
	            }
	        });
	        
	        for (var i = 0; i < closeTabsTitle.length; i++) {
	        	if(curTabTitle!="首页"){
	        		tabs.tabs("close", closeTabsTitle[i]);
	        	}
	        }
	    }

	    
	    
    	var left_page_height =$(document).height();
    	//if(left_page_height.indexOf("px") > 0){
    	//	left_page_height = left_page_height.substring(0, left_page_height.indexOf("px"));
    	//}
    	
	    $("#tabs").tabs({ 
			width:$("#tabs").parent().width(), 
			height:left_page_height
		}); 
	    
	    //页面加载完成  设置tabs的宽高
	    $(function(){ 
			$("#tabs").tabs({ 
				width:$("#tabs").parent().width(), 
				height:left_page_height
			}); 
		}); 
		
		//页面缩放时，设置tabs 和 iframe的宽高
		function WindowResize(){
			//tlw 2013.11.4 获取左侧页面的高度
	    	left_page_height = $(window).height();
	        var tab = $('#tabs').tabs('getSelected');
			$('#tabs').tabs({
	            width:$("#tabs").parent().width(), 
	            height:left_page_height+"px"
		    }); 
		    $('#tabs').tabs('select',tab.panel('options').title);
		    
		    //设置iframe
		    TabSelected();
		}
		
		//选中tab时，设置iframe的宽高
		function TabSelected(){
			var tab = $('#tabs').tabs('getSelected');
			// 获取选中的 tab panel 和它的 tab 对象   
			if(tab && tab.find('iframe').length > 0){   
				var iframe = tab.find('iframe')[0]; 
				iframe.style.height=left_page_height-40;
				iframe.style.width='100%';
			}  
		}
		
		//获取iframe所包含页面的高度
		function getInnerHeight() {
			return $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.document.body.scrollHeight;
		}
		//获取iframe所包含页面的宽度
		function getInnerWidth() {
			return $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.document.body.scrollWidth;
		}
    	    
		//添加标签页  为防止多次点击打开同一标签页，打开之前先进行判断，如果存在则刷新，不存在则创建  贺波 2012.3.6
		function addTab(id,title,url,closable){
		   //如果未打开该标签页，则新建该标签页 贺波 2012.3.6
		   if(!$('#'+tabPanel).tabs('exists',title)){
			   	var tabs = $("#tabs");
			    var allTabs = tabs.tabs("tabs");
			    if(allTabs.length>9){
			    	var count=allTabs.length-9;
			    	for(var i=0;i<count;i++){
			    		tabs.tabs("close", i+1);
					}
			    }
				$('#'+tabPanel).tabs('add',{
				    id:id,
					title:title,
					style:{padding:'1px'},
	                content:'<iframe name="'+id+'" src="'+url+'" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>',
	                closable:closable=='1'?true:false,
			        fit:true,
			        selected:true
				});
		    }else{ 
		        $("#"+tabPanel).tabs('select',title);		    
		        var tab = $('#'+tabPanel).tabs('getTab',title);
                $("#"+tabPanel).tabs('update',{
			        tab:tab,
			        options:{
			           title:title,
			           style:{padding:'1px'},
			           content:'<iframe name="'+id+'" src="'+url+'" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>',
	                   closable:closable=='1'?true:false,
			           fit:true,
			           selected:true
			         }
                });		    
		    }
		    //设置一下选中tab的高度、宽度
		    WindowResize();
		}
      
		function update(){
			index++;
			var tab = $('#tabs').tabs('getSelected');
			$('#tabs').tabs('update', {
				tab: tab,
				options:{
					title:'new title'+index,
					iconCls:'icon-save'
				}
			});
		}
		
		var currentTabTitle=null;
		$("#"+tabPanel).tabs({ 
              onSelect: function(tt) { 
                  lastTabs = $.grep(lastTabs, function(n, i) { return n != tt; }); 
                  //重新压入，保证 最新的在最上面 
                  lastTabs.push(tt); 
                  //更新当前            
                  currentTabTitle = tt; 
              }, 
              onClose: function(tt) { 
                  //移除 tt 
                  lastTabs = $.grep(lastTabs, function(n, i) { return n != tt; }); 
                  //重新选择 
                  $("#"+tabPanel).tabs("select", lastTabs[lastTabs.length - 1]); 
              } 
         }); 

		
		function getSelected(){
			var tab = $('#tabs').tabs('getSelected');
			alert('Selected: '+tab.panel('options').title);
		}
		$(function(){
			//top.document.getElementById('currMenuId').value=menuId;
			//top.document.getElementById('frmright').src=url;
			//判断处长和处长以上职务，首页展示body页面   处长以下 展示body1页面
			var myseq=top.positionseq;
			var czseq='<%=ParamConfigUtil.getParamConfigByPkey("CH_POSITION_SEQ")%>';
			if(Number(myseq)<=Number(czseq)){//处长和处长以上
				top.window.frames["frmright"].addTab("mydesk","首页","<%=basePath%>frame1/hainan-body.jsp",0);
			}else{// 处长以下
				top.window.frames["frmright"].addTab("mydesk","首页","<%=basePath%>frame1/hainan-body1.jsp",0);
			}
		})
		
		//通过选项卡标题关闭
		function closeTabByTitle(title){
			$("#"+tabPanel).tabs('close',title);
		}
	</script>
</html>
