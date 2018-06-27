<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript">
function OrtOpenCenterWindow (URL, Name, Features)
{
	try
	{
		var nWidth;
		var nHeight;
		var nLeft;
		var nTop;
		var strVal;
		var nPos;
		var strComb;
		var i;
		var chVal;
		nLeft = 0;
		nTop = 0;
		if (Features == null)
			return (window.open (URL, Name, Features));
		strVal = Features.toUpperCase ();
		if (strVal.indexOf ("LEFT", 0) >= 0 || strVal.indexOf ("TOP", 0) >= 0)
			return (window.open (URL, Name, Features));
		if ((nPos = strVal.indexOf ("WIDTH", 0)) < 0)
			return (window.open (URL, Name, Features));
		if ((nPos = strVal.indexOf ("=", nPos)) < 0)
			return (window.open (URL, Name, Features));
		strComb = "";
		for (i = nPos + 1; i < Features.length; i ++)
		{
			chVal = Features.charAt (i);
			if (chVal == " " || (chVal >= "0" && chVal <= "9"))
				strComb += chVal;
			else
				break;
		}
		if ((nWidth = eval (TrimString (strComb))) <= 0)
			return (window.open (URL, Name, Features));
		if ((nPos = strVal.indexOf ("HEIGHT", 0)) < 0)
			return (window.open (URL, Name, Features));
		if ((nPos = strVal.indexOf ("=", nPos)) < 0)
			return (window.open (URL, Name, Features));
		strComb = "";
		for (i = nPos + 1; i < Features.length; i ++)
		{
			chVal = Features.charAt (i);
			if (chVal == " " || (chVal >= "0" && chVal <= "9"))
				strComb += chVal;
			else
				break;
		}
		if ((nHeight = eval (TrimString (strComb))) <= 0)
			return (window.open (URL, Name, Features));
		nLeft = (window.screen.width - nWidth) / 2;
		nTop = (window.screen.height - nHeight) / 2;
		Features += ",left=" + nLeft + ",top=" + nTop;
		return (window.open (URL, Name, Features));
	}
	catch(err)
	{
		alert(err);
	}
}
	function fullscreen(){ //在ie下可行     
		var subWin = OrtOpenCenterWindow ("<%=basePath%>index.jsp", "",'width='+(window.screen.availWidth)+',height='+(window.screen.availHeight-50)+ ',top=0,left=0,fullscreen=no,location=yes,resizable=yes,status=no,toolbar=no,menubar=no,scrollbars=no,channelmode=yes');    
		subWin.focus();
		window.opener=null;
	    window.open('','_self');
	    window.close();
	} 
	fullscreen();
</script>
