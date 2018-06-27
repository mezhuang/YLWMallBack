/**
	仝令玮 2012.09.16
	easyui的扩展方法
**/
//仝令玮 2012.09.05 下载excel
//	注意：该下载方法需要与action中getList相关方法同时使用，方法中获取到excel参数时调用父类方法即可
//		 目前还不能处理复杂表头，如果是复杂表头，请自己将要下载的field和对应的显示名称封装成数组
//  使用方法示例：在js中调用 
//	该方法可接收一个参数，该参数是一个对象，该对象中有一下几个属性： 
//	1、 excel_sheet_name：导出excel工作簿的名称，默认为sheet，【string类型】
//	2、 excel_fields_arr：导出excel中要列出的对象的属性集合，默认取datagrid中显示的那些属性，如果需要指定请传递该参数 【数组类型，元素为string】
//	3、 excel_fields_name_arr：与上面参数对应的显示名称，如果传入了上面的参数，则这个参数一定要传如 【数组类型，元素为string】
$.extend($.fn.datagrid.methods,{
	excel:function(jq, param) {
		//1 获取datagrid对象
		var options = jq.datagrid('options');//这样来获得所有的datagrid属性
		var total = jq.datagrid('getData').total;
		if(!total || total<1) {//总记录数为0，就不需要导出了
			alert('没有可下载的数据');
			return false;
		}else if(total>6000){
			if(!window.confirm('数据量较大,装载Excel文件需要较长时间,确定要执行吗?')) {
				return false;
			}
		}
		var excel_sheet_name, excel_fields_arr, excel_fields_name_arr;
		
		if(param) {
			if(param.excel_sheet_name) 
				excel_sheet_name = param.excel_sheet_name;
			if(param.excel_fields_arr) 
				excel_fields_arr = param.excel_fields_arr;
			if(param.excel_fields_name_arr) 
				excel_fields_name_arr = param.excel_fields_name_arr;
		}
		//2 处理url
	 	var url = options.url;
	 	//if(url.indexOf('?')>0) url += '&excel=true';
	 	//else  url += '?excel=true';
	 	
	 	//3 处理查询条件（这里用url带参数的方式传递到后台）
	 	var queryParams = options.queryParams;
	 	var queryParam_arr = [];
	 	for(var att in queryParams) {
	 		//queryParam_arr.push(att+"="+queryParams[att]);
            queryParam_arr.push("\""+att+"\":\""+queryParams[att]+"\"");
	 	}
	 	
	 	//4 处理要显示的field，包括字段名和对应显示的表头中文名
	 	var fields = [];
	 	var fields_name = [];
	 	if(excel_fields_arr && excel_fields_name_arr) {//如果指定了，就用指定的field信息
	 		fields = excel_fields_arr;
	 		fields_name = excel_fields_name_arr;
	 	}else {
	 		var all_columns = options.columns[0];//目前还不能处理复杂表头，如果是复杂表头，请自己将要下载的field和对应的显示名称封装成数组
		 	for(var att in all_columns) {
		 		if(all_columns[att] && !all_columns[att].hidden && all_columns[att].field) {
		 			fields.push(all_columns[att].field);
		 			fields_name.push(all_columns[att].title);
		 		}
		 	}
	 	}
	 	var dataArr=[];
        dataArr.push("\"excel\":true");
        dataArr.push("\"excel_fields\":\""+fields.join(',')+"\"");
        dataArr.push("\"excel_fields_name\":\""+fields_name.join(',')+"\"");
        dataArr.push("\"excel_sheet_name\":"+(excel_sheet_name?"\""+excel_sheet_name+"\"":"\""+sheet+"\""));
        dataArr.push(queryParam_arr.join(","));
        var dataJsonStr = dataArr.join(",");
        data = $.parseJSON('{'+dataJsonStr+'}');
        	
	 	//5 提交ajax请求来生成excel文件并下载
	 	$.ajax({
	 		type:'post',
	 		dataType:"text",
	 		async:false,
	 		url:url,
	 		data:data,
	 		success:function(path) {
	 		    window.location.href = basePath + 'excelExport.do?url=' + path;//下载
	 			//window.location.href = path;//下载
	 		}
	 	});
	}
});

//仝令玮 2012.09.09 扩展easyui tree的两个方法 获取实心节点
$.extend($.fn.tree.methods,{
	getCheckedExt: function(jq){//获取checked节点（包括实心）
		var checked = $(jq).tree("getChecked");
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2,function(){
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	},
	getSolidExt:function(jq){//获取实心节点（只有实心）
		var checked =[];
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2,function(){
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	}
});
//提示alert显示为中文
$.extend($.messager.defaults,{  
    ok:"确定",  
    cancel:"取消"  
});


$.extend($.fn.datagrid.methods, {
	statistics: function (jq) {
		var opt=$(jq).datagrid('options').columns;
		var rows = $(jq).datagrid("getRows");
		
		var footer = new Array();
		footer['sum'] = "";
		footer['avg'] = "";
		footer['max'] = "";
		footer['min'] = "";
		
		for(var i=0; i<opt[0].length; i++){
			if(opt[0][i].sum){
				footer['sum'] = footer['sum'] + sum(opt[0][i].field)+ ',';
			}
			if(opt[0][i].avg){
				footer['avg'] = footer['avg'] + avg(opt[0][i].field)+ ',';
			}
			if(opt[0][i].max){
				footer['max'] = footer['max'] + max(opt[0][i].field)+ ',';
			}
			if(opt[0][i].min){
				footer['min'] = footer['min'] + min(opt[0][i].field)+ ',';
			}
		}

		var footerObj = new Array();
		
		if(footer['sum'] != ""){
			var tmp = '{' + footer['sum'].substring(0,footer['sum'].length - 1) + "}";
			var obj = eval('(' + tmp + ')');
			if(obj[opt[0][0].field] == undefined){
				footer['sum'] += '"' + opt[0][0].field + '":"<b>合计</b>"';
				obj = eval('({' + footer['sum'] + '})');
			}else{
				obj[opt[0][0].field] = "<b>合计</b>" + obj[opt[0][0].field];
			}
			footerObj.push(obj);
		}
		
		if(footer['avg'] != ""){
			var tmp = '{' + footer['avg'].substring(0,footer['avg'].length - 1) + "}";
			var obj = eval('(' + tmp + ')');
			if(obj[opt[0][0].field] == undefined){
				footer['avg'] += '"' + opt[0][0].field + '":"<b>当页均值:</b>"';
				obj = eval('({' + footer['avg'] + '})');
			}else{
				obj[opt[0][0].field] = "<b>当页均值:</b>" + obj[opt[0][0].field];
			}
			footerObj.push(obj);
		}
		
		if(footer['max'] != ""){
			var tmp = '{' + footer['max'].substring(0,footer['max'].length - 1) + "}";
			var obj = eval('(' + tmp + ')');
			
			if(obj[opt[0][0].field] == undefined){
				footer['max'] += '"' + opt[0][0].field + '":"<b>当页最大值:</b>"';
				obj = eval('({' + footer['max'] + '})');
			}else{
				obj[opt[0][0].field] = "<b>当页最大值:</b>" + obj[opt[0][0].field];
			}
			footerObj.push(obj);
		}
		
		if(footer['min'] != ""){
			var tmp = '{' + footer['min'].substring(0,footer['min'].length - 1) + "}";
			var obj = eval('(' + tmp + ')');
			
			if(obj[opt[0][0].field] == undefined){
				footer['min'] += '"' + opt[0][0].field + '":"<b>当页最小值:</b>"';
				obj = eval('({' + footer['min'] + '})');
			}else{
				obj[opt[0][0].field] = "<b>当页最小值:</b>" + obj[opt[0][0].field];
			}
			footerObj.push(obj);
		}
		
		
		
		if(footerObj.length > 0){
			$(jq).datagrid('reloadFooter',footerObj); 
		}
		
		
		function sum(filed){
			var sumNum = 0;
			for(var i=0;i<rows.length;i++){
				if(!isNaN(rows[i][filed]))
				sumNum += Number(rows[i][filed]);
			}
			return '"' + filed + '":"' + sumNum.toFixed(2) +'"';
		};
		
		function avg(filed){
			var sumNum = 0;
			for(var i=0;i<rows.length;i++){
				if(!isNaN(rows[i][filed]))
				sumNum += Number(rows[i][filed]);
			}
			return '"' + filed + '":"'+ (rows.length==0?'0.00':(sumNum/rows.length).toFixed(2)) +'"';
		}

		function max(filed){
			var max = 0;
			for(var i=0;i<rows.length;i++){
				if(i==0){
					if(!isNaN(rows[i][filed]))
					max = Number(rows[i][filed]);
				}else{
					if(!isNaN(rows[i][filed]))
					max = Math.max(max,Number(rows[i][filed]));
				}
			}
			return '"' + filed + '":"'+ max.toFixed(2) +'"';
		}
		
		function min(filed){
			var min = 0;
			for(var i=0;i<rows.length;i++){
				if(i==0){
					if(!isNaN(rows[i][filed]))
					min = Number(rows[i][filed]);
				}else{
					if(!isNaN(rows[i][filed]))
					min = Math.min(min,Number(rows[i][filed]));
				}
			}
			return '"' + filed + '":"'+ min.toFixed(2) +'"';
		}
	}
});