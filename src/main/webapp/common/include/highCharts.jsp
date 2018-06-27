<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<script src="<%=basePath %>/plugins/HighCharts/Highcharts-4.0.3/js/highcharts.js"></script>
<!--修改了 exporting.src.js 使其不再显示导出的相关功能 -->
<script src="<%=basePath %>/plugins/HighCharts/Highcharts-4.0.3/js/modules/exporting.src.js"></script>
<script src="<%=basePath %>/plugins/HighCharts/Highcharts-4.0.3/js/highcharts-more.js"></script> 
<script src="<%=basePath %>/plugins/HighCharts/Highcharts-4.0.3/js/modules/data.js"></script> 
<script>
   Highcharts.setOptions({
			global: { 
				useUTC: true//中文区时间格式
				}
				// 中文时间显示字符串替换
				,	
				lang: {
					loading:'数据加载中...',
					printChart:'打印报表',
					downloadJPEG:'导出为JPEG格式的文件',
					downloadPDF:'导出为PDF格式的文件',
					downloadPNG:'导出为PNG格式的文件',
					downloadSVG:'导出为SVG格式的文件',
					rangeSelectorFrom: '从',  
	                rangeSelectorTo: '到',  
	                rangeSelectorZoom:'区域' ,   
					months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
					shortMonths : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
					weekdays: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
					thousandsSep:''// 去掉千位的逗号
				}
			});
</script>	