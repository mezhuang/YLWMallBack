<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript">
	//var basePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>plugins/dialog/dialog.js?self=false&skin=chrome"></script>
<script type="text/javascript" src="<%= basePath %>plugins/dialog/dialog.extends.js"></script>
<script type="text/javascript">
	function QuickReplyManage(){
		$.dialog({
			title:'意见维护',
			width:'600px',
			height:'440px',
			zIndex:2100,
			fixed:true, 
		    lock: true,
		    drag: false,
		    max:false,
		    min:false,
			content:'url:<%=basePath %>personal/quickreply/list.jsp',
			close:function(){
				$.ajax({
					type:'POST',
					url:'<%=basePath%>QuickReplyManage_getQuickReplyList.do',
					data:{
						begin:-1,
						end:-1
					},
					traditional: true,//在struts2下该属性必须有 
					success:function(result){
						//console.info(result);
						var rows = eval("(" + result + ")").rows;
						//console.info(document);
						var obj = document.getElementById('quickReplySelect');
						obj.options.length=1;
						for(var i=0;i<rows.length;i++){
							if(rows[i].content.length>30){
								obj.options.add(new Option(rows[i].content.substring(0,30)+"…",rows[i].content));
							}else{
								obj.options.add(new Option(rows[i].content,rows[i].content));
							}
						}
					}
				});
			}
		});
	}
</script>
