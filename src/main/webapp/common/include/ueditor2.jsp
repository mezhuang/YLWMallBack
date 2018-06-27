<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  //System.out.println(System.nanoTime());    
%>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    
</script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>plugins/ueditor1.4.3/ueditor.config.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>plugins/ueditor1.4.3/ueditor.all.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>plugins/ueditor1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
 function getUEditor(wordCount,maximumWords,minFrameHeight,textarea,elementPathEnabled){
	 var editorOption = {
		        //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
		        toolbars: [['FullScreen', 'Source', 'fontfamily', 'fontsize', 'Undo', 'Redo', 'italic', 'Bold', 'underline', 'subscript', 'superscript', 'imagecenter', 'inserttable', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify', 'forecolor', 'backcolor', '|', 'insertimage', 'emotion', 'insertvideo', 'attachment','pagebreak', '|']],
		        //autoClearinitialContent: true,//focus时自动清空初始化时的内容
		        //wordCount: wordCount==''?true:wordCount,
		        wordCount: wordCount==false,
		        //开启字数统计
		        //maximumWords: maximumWords==''?1000:maximumWords,
		        maximumWords: maximumWords=2147483647,
		        //最多1000字
		        //textarea: textarea,
		        minFrameHeight: minFrameHeight==''?150:minFrameHeight,
		        //最小高度
		        elementPathEnabled: elementPathEnabled==''?false:elementPathEnabled //关闭elementPath
		    };
	 return UE.getEditor(textarea,editorOption);
 }
</script>