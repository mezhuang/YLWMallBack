// JavaScript Document
//头像
$(function(){
	 $(".photo").mouseover(function(){
		 $(".photomassage").show();
		 
		 
		 })
	 $(".photo").mouseout(function(){
		 
		 $(".photomassage").hide();
		 
		 })
	  
	  })

//菜单  
$(function(){
	//首次加载 展开第一个一级菜单的二级菜单、三级菜单（如果有的话），折叠其他的二级菜单、三级菜单
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
	
});