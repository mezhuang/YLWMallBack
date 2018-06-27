/**
	仝令玮 2012.09.16
	弹窗插件的扩展方法
**/


//打开弹窗 cyy 2014.1.18
//第一个参数url表示弹窗的地址（必输）
//第二个参数width表示弹窗的宽度（选填，默认值800）
//第三个参数height表示弹窗的高度（选填，默认值500）
//第四个参数name表示弹出窗口的名字，（选填，默认值为空字符串）
//第五个参数zIndex表示弹窗的序号，默认1976，数值越大则弹出的时候越靠前。【注意：这个是用来控制多层弹窗的显示位置的，如果是第一层弹窗，这个参数不要填写，如果是弹窗中的弹窗， 这个值填2000】
//第六个参数isMax表示弹窗是否以最大化方式打开(可以填写true、max)
//第七个参数isCancelX表示是否取消右上角的关闭叉号 (可以填写true、cancelX)
function openWin(url,width,height,name,zIndex, isMax, isCancelX,max_allowable,min_allowable){
	var url =url; //转向网页的地址;
	var iWidth = 800; //弹出窗口的宽度;
	var iHeight = 500; //弹出窗口的高度;
	var iName = ''; //网页名称，可为空;
	var izIndex = 1976; //zIndex初始值;
	var iIsCancelX = true;
    max_allowable= true;
    //如果指定了宽、高、名字，则用指定的
    if(width){
       iWidth = width; 
    }
    if(height){
       iHeight = height; 
    }
    if(name){
       iName = name; 
    }
    if(zIndex){
    	izIndex = zIndex; 
    }
    if(isCancelX==true || isCancelX=='cancelX') {
    	iIsCancelX = false;
    }
    if(max_allowable==undefined||max_allowable==''){
    	max_allowable=false;
    }
    if(min_allowable==undefined||min_allowable==''){
    	min_allowable=false;
    }
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	//判断是否是最大化窗口打开
	if(isMax && (isMax==true||isMax=='max')) {
		//弹窗方式1 - lhdialog组件
		return $.dialog({
			lock: true,
			drag: false,
			max:false,
		    min:false,
			title: iName,
			cancel:iIsCancelX?'':false,
			zIndex: izIndex,
		    content: 'url:'+url
		}).max();
	}else {
		//弹窗方式1 - lhdialog组件
		return $.dialog({
			lock: true,
			drag: false,
			max:true,//max_allowable,
		    min:min_allowable,
		    resize: false,
			title: iName,
			cancel:iIsCancelX?'':false,
		    width: iWidth+'px',
		    height: iHeight,
		    zIndex: izIndex,
		    content: 'url:'+url
		});
	}
	
	//弹窗方式2 - 原生弹窗
    //window.open(url, iName, 'width='+iWidth+', height='+iHeight+', toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}
function openWinDialog(url,width,height,name,zIndex){
	var url =url; //转向网页的地址;
	var iWidth = 800; //弹出窗口的宽度;
	var iHeight = 500; //弹出窗口的高度;
	var iName = ''; //网页名称，可为空;
	var izIndex = 1976; //zIndex初始值;
	var iIsCancelX = true;
    
    //如果指定了宽、高、名字，则用指定的
    if(width){
       iWidth = width; 
    }
    if(height){
       iHeight = height; 
    }
    if(name){
       iName = name; 
    }
    if(zIndex){
    	izIndex = zIndex; 
    }
    
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    window.open(url, iName, 'width='+iWidth+', height='+iHeight+', toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//选择人员（弹窗方式）的默认方法
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数 rType 为char类型,U 表示返回userId H 表示返回hiringId
function select_users(single,ids_receiver, names_receiver,rType) {
	var url = basePath+'common/dialog/userselect.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'')
			+ '&rType=' + (rType?rType:'') ;
			//+ '&deptId=' + (deptId?deptId:'');
	//用jQuery代替$，防止与prototype冲突 --yuegb 2013.1.6
	//openWin(url,width,height,name)
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '700px',
	    height: 400,
	    zIndex: 2000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择人员', 'width=700, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//选择人员（弹窗方式）的默认方法
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数display_field为String类型，代表用户列表需要显示的字段，多个值以,隔开
//第五个参数selected_field为String类型，代表已选择人员列表需要显示的字段，多个值以,隔开
function choose_users(single,ids_receiver,names_receiver,display_field,selected_field,from) {
	var url ='';
	//if(single){
		//url = basePath+'common/dialog/singleUserchoose.jsp?single='+(single?single:'') 
		//	+ '&userid=' + (ids_receiver?ids_receiver:'') 
		//	+ '&username=' + (names_receiver?names_receiver:'')
		//	+'&display='+(display_field?display_field:'id,realName,deptName,postName')
		//	+'&seldisplay='+(selected_field?selected_field:'id,realName');
	//}else{
		url = basePath+'common/dialog/userchoose.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'')
			+'&display='+(display_field?display_field:'id,realName,deptName,postName')
			+'&seldisplay='+(selected_field?selected_field:'id,realName');
	//}
			
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-800)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
		width: '960px',
	    height: 495,
		//width: parseInt($(document).width()*0.7),
	    //height: parseInt($(document).height()*0.735),
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
	//window.open(url,'选择人员', 'width=800, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//邮箱管理 选择收件人（弹窗方式）的方法
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数display_field为String类型，代表用户列表需要显示的字段，多个值以,隔开
//第五个参数selected_field为String类型，代表已选择人员列表需要显示的字段，多个值以,隔开
function mail_users(single,ids_receiver,names_receiver,names_div,display_field,selected_field) {
	var url = basePath+'common/dialog/mailuser.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'')
			+'&names_div='+names_div;
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-800)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '800px',
	    height: 400,
	    zIndex: 2000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
	//window.open(url,'选择人员', 'width=800, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//选择某部门的人员
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数deptId为String类型，代表部门ID
function select_users_bydept(single,ids_receiver,names_receiver,deptId) {
	var url = basePath+'common/dialog/selectuserbydept.jsp?';
	var height=500;
	if(single=='true'){
		url = basePath+'common/dialog/singleSelectUserByDept.jsp?';
		height=530;
	}
	url = url+'single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'')
			+'&deptId='+(deptId?deptId:'');
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-800)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '800px',
	    height: height,
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
	//window.open(url,'选择人员', 'width=800, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//选择部门负责人
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
function select_deptmanager(single,ids_receiver,names_receiver) {
	var url = basePath+'common/dialog/selectdeptmanager.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'');
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-800)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '800px',
	    height: 400,
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
	//window.open(url,'选择人员', 'width=800, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

//选择培训项目--培训课程（弹窗方式）的默认方法--谷超超2014年2月28日10：00：00
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
function select_courses(single,ids_receiver, names_receiver,projectid,projectname) {
	var url = basePath+'common/dialog/pro_courseselect.jsp?single='+(single?single:'') 
			+ '&courseid=' + (ids_receiver?ids_receiver:'') 
			+ '&coursename=' + (names_receiver?names_receiver:'')
			+ '&projectid=' + (projectid?projectid:'') 
			+ '&projectname=' + (projectname?projectname:'') ;
			//+ '&deptId=' + (deptId?deptId:'');
	//用jQuery代替$，防止与prototype冲突 --yuegb 2013.1.6
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择课程',
	    width: '800px',
	    height: 400,
	    zIndex: 2000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择课程', 'width=700, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}
function select_userdep(single,ids_receiver, names_receiver,depid,depname) {
	var url = basePath+'common/dialog/dept_userselect.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&depid=' + (depid?depid:'') 
			+ '&depname=' + (depname?depname:'') 
			+ '&username=' + (names_receiver?names_receiver:'') ;
			//+ '&deptId=' + (deptId?deptId:'');
	//用jQuery代替$，防止与prototype冲突 --yuegb 2013.1.6
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '670px',
	    height: 495,
	    zIndex: 2000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择人员', 'width=670, height=450, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}
//根据岗位id（多个岗位用","隔开），获取该岗位下的人员
//第一个参数single为bool类型， 是否可以选择多条记录
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数postId为String类型， 代表要查询的岗位id
//第五个参数rType为String类型， U代表返回userId  H 返回hiringId
function select_userpost(single,ids_receiver, names_receiver,postId,rType) {
	var url = basePath+'common/dialog/post_userselect.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'') 
			+ '&postId=' + (postId?postId:'') 
			+ '&rType=' + (rType?rType:'') ;
			//+ '&deptId=' + (deptId?deptId:'');
	//用jQuery代替$，防止与prototype冲突 --yuegb 2013.1.6
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '670px',
	    height: 495,
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择人员', 'width=670, height=450, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

/**
 * 部门选择通用弹框
 * @param single 是否单选
 * @param ids_receiver 部门编号获取控件ID
 * @param names_receiver 部门名称获取控件ID
 * @param callbackFun 指定回调方法名称，可以为空,如果为空默认回调addDept(deptIds,deptNames)。例如，select_dept(true, '', '','getSelectedDept');
 * @param pId 根据传递的pId，获取以该节点ID为根节点的机构树
 * @param isNotNull 是否必选，默认可以不传递该参数，如果需要在弹窗中必须选择部门后才能点击确定按钮，那么可以赋值true
 */
function select_dept(single, ids_receiver, names_receiver,callbackFun,pId,isNotNull) {
	var url = basePath+'common/dialog/deptselect.jsp?single='+(single?single:'');
	var height='403px';
	var width='700px';
	if(single=='true'){
		url = basePath+'common/dialog/singleDeptSelect.jsp?single='+(single?single:'');
		height='505px';
		width='420px';
	}
	url = url +'&deptid=' + (ids_receiver?ids_receiver:'') 
			+ '&deptname=' + (names_receiver?names_receiver:'')
			+ '&callbackFun='+(callbackFun?callbackFun:'')
			+ '&pId='+(pId?pId:'')
			+ '&isNotNull='+(isNotNull?isNotNull:'');
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:true,
	    min:false,
		title: '选择部门',
	    width: width,
	    height: height,
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择部门', 'width=700, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

function dialogdep(single, ids_receiver, names_receiver, append, onlyLeafCheck) {
	var api = frameElement.api, W = api.opener;
	var url = basePath+'common/dialog/dialoguser/depts.jsp?single='+(single?single:'') 
			+ '&ids_receiver=' + (ids_receiver?ids_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'')
			+ '&onlyLeafCheck=' + (onlyLeafCheck?onlyLeafCheck:'');
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择部门',
	    width: '700px',
	    height: 400,
	    zIndex: 2000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择部门', 'width=700, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}
//选择岗位（combobox方式）默认方法
//使用方式：在页面加载完成后调用该方法
//第一个参数是选择器的id，（string类型）
//第二个参数是部门的id，如果该值不为空，则下拉列表只显示该部门及其下属部门（string类型，多个部门用逗号分割）
//第三个参数是选择器的宽度，默认值为200，为0时使用默认值（number类型）
//第四个参数是选择器的高度，默认值为200，为0时使用默认值（number类型）
//第五个参数是是否是多选，默认为false（boolean类型）
//第六个参数是是否只能为末级部门，默认为false（boolean类型）
//第七个参数是默认选择部门的ids（number类型的数组）
//第八个参数是多选时是否触发级联，默认为false（boolean类型）【级联是指选中父类，则子类自动选中，选中所有子类，父类自动选中】
//第九个参数是下拉框数据的来源url，需要时可自己写action来获取数据（string类型）
function initPostSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	initSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultIds, cascadeCheck, url?url:basePath+'PostSelect_getDdep.do');
}
//选择部门（combobox方式）默认方法
//使用方式：在页面加载完成后调用该方法
//第一个参数是选择器的id，（string类型）
//第二个参数是部门的id，如果该值不为空，则下拉列表只显示该部门及其下属部门（string类型，多个部门用逗号分割）
//第三个参数是选择器的宽度，默认值为200，为0时使用默认值（number类型）
//第四个参数是选择器的高度，默认值为200，为0时使用默认值（number类型）
//第五个参数是是否是多选，默认为false（boolean类型）
//第六个参数是是否只能为末级部门，默认为false（boolean类型）
//第七个参数是默认选择部门的ids（number类型的数组）
//第八个参数是多选时是否触发级联，默认为false（boolean类型）【级联是指选中父类，则子类自动选中，选中所有子类，父类自动选中】
//第九个参数是下拉框数据的来源url，需要时可自己写action来获取数据（string类型）
function initDeptSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	initSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultIds, cascadeCheck, url?url:basePath+'DepSelect_getDdep.do');
}


//根据部门选择人员（combobox方式）默认方法
//使用方式：在页面加载完成后调用该方法
//第一个参数是选择器的id，（string类型）
//第二个参数是部门的id，如果该值不为空，则下拉列表只显示该部门的人员，含子部门（string类型，多个部门用逗号分割）
//第三个参数是选择器的宽度，默认值为200，为0时使用默认值（number类型）
//第四个参数是选择器的高度，默认值为200，为0时使用默认值（number类型）
//第五个参数是是否是多选，默认为false（boolean类型）
//第六个参数是是否只能为末级部门，默认为false（boolean类型）
//第七个参数是默认选择部门的ids（number类型的数组）
//第八个参数是多选时是否触发级联，默认为false（boolean类型）【级联是指选中父类，则子类自动选中，选中所有子类，父类自动选中】
//第九个参数是下拉框数据的来源url，需要时可自己写action来获取数据（string类型）
//第十个参数是用来标记是否可直接输入文本到选择框
function initUserSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultUserIds, cascadeCheck, url, editable) {
	initSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultUserIds, cascadeCheck, url?url:basePath+'UserDialog_selectUser.do', true, editable);//最后这个属性true用来说明是选择人员，为了单选用户时对部门进行过滤
	//说明：仝令玮2012-8-31
	//  由于该插件没有提供控制单个节点是否可选的方法，所以对源文件进行了改动。 在选择人员时，用户节点使用了"iconCls":"icon-user"，非用户节点使用了"iconCls":"icon-notuser"，
	//	就是根据这个iconCls属性对是否出现选择框进行了控制，见plugins/easyui/jquery.easyui.min.js第1146行的改动
	//	上面的改动只对多选用户生效，单选时是通过onSelect事件进行了控制，所以多给initSelector传了一个参数
}


//内部方法
//第十个参数是用来标记是否是选择人员(因为单选用户时要过滤部门，所以多接受了的个参数作为标记)
//第十一个参数是用来标记是否可直接输入文本到选择框
function initSelector(selector, rootDeptIds, width, height, multiple, onlyLeafCheck, defaultIds, cascadeCheck, url, isSelectUser, editable) {
	$('#'+selector).combotree( {
		width:width?width:200,
		panelHeight:height?height:200,
		onlyLeafCheck:onlyLeafCheck?true:false,//是否只选末级节点
		cascadeCheck:cascadeCheck?true:false,//关联选择
		required: false,//取消required验证
		url : (url?url:basePath+'DepSelect_getDdep.do') + (rootDeptIds?('?rootDeptIds='+rootDeptIds):''),//获取数据URL
		multiple:multiple?true:false,
		lines:true,
		editable:editable?true:false,
		//选择树节点触发事件
		onSelect : function(node) {
			//如果是单选，而且只允许选择末级节点，要处理一下（不知道为什么onlyLeafCheck只对多选起作用）
			if(!multiple && onlyLeafCheck) {
				var tree = $(this).tree;//返回树对象
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf) {
					$('#'+selector).combotree('clear');//清除选中
				}
			}
			//单选用户的时候，如果选择的不是用户，也清空一下
			if(!multiple && isSelectUser && node.iconCls=='icon-notuser') {
				$('#'+selector).combotree('clear');//清除选中
			}
			
		}
		
	});
	//赋默认值
	if(defaultIds)//是否传了值
		if($.isArray(defaultIds))//是否是数组
			$('#'+selector).combotree('setValues', defaultIds);
		else //是否一个数字
			$('#'+selector).combotree('setValue', defaultIds);
}


//部门树结构 tlw 2012.9.2
function initDeptTree(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	initTree(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url ? url : basePath + 'DepSelect_getDdep.do');
}

//人员树结构 tlw 2012.9.2
function initUserTree(selector, checkbox, onlyLeafCheck, defaultUserIds, cascadeCheck, url) {
	initTree(selector, checkbox, onlyLeafCheck, defaultUserIds, cascadeCheck, url?url:basePath+'selectuser.do');
}

//人员树结构 wx 2012.12.28
function initUserTree2(selector, checkbox, onlyLeafCheck, defaultUserIds, cascadeCheck, url) {
	initTree2(selector, checkbox, onlyLeafCheck, defaultUserIds, cascadeCheck, url?url:basePath+'selectuser.do');
}

//组树结构 tlw 2012.10.24
function initGroupTree(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	initTree(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url?url:basePath+'selectgroup.do');
}

//树结构
function initTree(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	$('#'+selector).tree( {
		onlyLeafCheck:onlyLeafCheck?true:false,//是否只选末级节点
		cascadeCheck:cascadeCheck?true:false,//关联选择
		url : (url?url:basePath+'DepSelect.do') ,//获取数据URL
		checkbox:checkbox?true:false,
		lines:true,
		onLoadSuccess : function() {
			//赋默认值
			if(defaultIds){//是否传了值
				if($.isArray(defaultIds)) {//是否是数组
					$('#'+selector).tree('check', defaultIds);
					for(var id in defaultIds) {
						var defaultNode = $('#'+selector).tree('find', defaultIds[id]);
						if(defaultNode)
							$('#'+selector).tree('check', defaultNode.target);
					}
				}else{ //是否一个数字
					var defaultNode = $('#'+selector).tree('find', defaultIds);
					if(defaultNode)
						$('#'+selector).tree('check', defaultNode.target);
				}
			}
		}
	});
}

//树结构
function initTree2(selector, checkbox, onlyLeafCheck, defaultIds, cascadeCheck, url) {
	$('#'+selector).tree( {
		onlyLeafCheck:onlyLeafCheck?true:false,//是否只选末级节点
		cascadeCheck:cascadeCheck?true:false,//关联选择
		url : (url?url:basePath+'DepSelect.do') ,//获取数据URL
		checkbox:checkbox?true:false,
		lines:true,
		onLoadSuccess : function() {
			//赋默认值
			if(defaultIds){//是否传了值
				if($.isArray(defaultIds)) {//是否是数组
					$('#'+selector).tree('check', defaultIds);
					for(var id in defaultIds) {
						var defaultNode = $('#'+selector).tree('find', defaultIds[id]);
						if(defaultNode)
							$('#'+selector).tree('check', defaultNode.target);
					}
				}else{ //是否一个数字
					var defaultNode = $('#'+selector).tree('find', defaultIds);
					if(defaultNode)
						$('#'+selector).tree('check', defaultNode.target);
				}
			}
		},
		onClick : function(node){
			if(node.iconCls == 'icon-user'){
				moveOption2(node.id,node.text,document.getElementById('SelectRoleRight'));
			}
		}
	});
}

//选择岗位（弹窗方式）的默认方法  --by岳广彬 2012 12.13
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean,代表是否是单选，默认为false
//第二个参数postId为String类型， 代表父页面接收返回ids的容器的id
//第三个参数postName为String类型， 代表父页面接收返回names的容器的id
//第四个参数deptIds_receiver为string，代表父页面接收返回的部门id的容器的id
//第五个参数deptIds_receiver为string，代表父页面接收返回的部门name的容器的id
//第六个参数deptId，代表获取指定的部门
function select_post(single, postId_receiver, postName_receiver,deptId_receiver,deptName_receiver,deptId) {
	var url = basePath+'common/dialog/selectpost.jsp?single='+(single?single:'') 
			+ '&ids_receiver=' + (postId_receiver?postId_receiver:'') 
			+ '&names_receiver=' + (postName_receiver?postName_receiver:'') 
			+ '&deptIds_receiver=' + (deptId_receiver?deptId_receiver:'')
			+ '&dptNames_receiver='+(deptName_receiver?deptName_receiver:'')
			+ '&deptId='+(deptId?deptId:'');
	var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-700)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择岗位',
	    width: '700px',
	    height: 400,
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
    //window.open(url,'选择岗位', 'width=700, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}
function select_post5(single, ids_receiver, names_receiver, append, deptId) {
	var url = basePath+'common/dialog/posts5.jsp?single='+(single?single:'') 
			+ '&ids_receiver=' + (ids_receiver?ids_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'') 
			+ '&deptId=' + (deptId?deptId:'');
	$.dialog({
		title:'选择岗位',
		width:'500px',
		height:'450px',
		lock: true,
		drag: false,
		max:false,
	    min:false,
		fixed:true,
		content:'url:'+url
	});
}


//从公司通讯录选择人员（弹窗方式）的默认方法--王小龙 2013年4月8日 08:54:07
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean,代表是否是单选，默认为false
//第二个参数numbers_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数append为boolean，代表是否采取追加的方式将值输出到指定容器，默认为false【注意，追加时会自动过滤掉重复元素】
//第五个参数deptId为string，代表选取指定部门的人员
function select_user_from_CPMessage(single, numbers_receiver, names_receiver, append, deptId) {
	var url = basePath+'common/message/select_from_company.jsp?single='+(single?single:'') 
			+ '&numbers_receiver=' + (numbers_receiver?numbers_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'') 
			+ '&deptId=' + (deptId?deptId:'');
	//用jQuery代替$，防止与prototype冲突 --yuegb 2013.1.6
	var api=jQuery.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title:'选择人员',
		width:'560px',
		height:'420px',
		lock:true,
		content:'url:'+url
	});
	api.zindex();
}
//从公司通讯录按部门选择人员（弹窗方式）的默认方法--王小龙 2013年4月8日 08:54:07
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean,代表是否是单选，默认为false
//第二个参数numbers_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数append为boolean，代表是否采取追加的方式将值输出到指定容器，默认为false【注意，追加时会自动过滤掉重复元素】
//第五个参数deptId为string，代表选取指定部门的人员
function select_user_by_org(single, numbers_receiver, names_receiver, append) {
	var url = basePath+'common/message/select_by_org.jsp?single='+(single?single:'') 
			+ '&numbers_receiver=' + (numbers_receiver?numbers_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'');
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
	    title:'选择人员',width:'720px',height:'460px',fixed:true,
	    content:'url:'+url
	});
}


//从公共通讯录选择人员（弹窗方式）的默认方法--王小龙 2013年4月8日 08:54:07
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean,代表是否是单选，默认为false
//第二个参数numbers_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数append为boolean，代表是否采取追加的方式将值输出到指定容器，默认为false【注意，追加时会自动过滤掉重复元素】
//第五个参数deptId为string，代表选取指定部门的人员
function select_user_from_public(single, numbers_receiver, names_receiver, append) {
	var url = basePath+'AddressBookManage_getIndexList.do?type=1&where=choice&single='+(single?single:'') 
			+ '&numbers_receiver=' + (numbers_receiver?numbers_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'');
	$.dialog({title:'选择人员',width:'720px',height:'460px',fixed:true, lock: true,drag: false,max:false,min:false,content:'url:'+url});
}

//从个人通讯录选择人员（弹窗方式）的默认方法--王小龙 2013年4月8日 08:54:07
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean,代表是否是单选，默认为false
//第二个参数numbers_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数append为boolean，代表是否采取追加的方式将值输出到指定容器，默认为false【注意，追加时会自动过滤掉重复元素】
//第五个参数deptId为string，代表选取指定部门的人员
function select_user_from_personl(single, numbers_receiver, names_receiver, append) {
	var url = basePath+'AddressBookManage_getIndexList.do?type=0&where=choice&single='+(single?single:'') 
			+ '&numbers_receiver=' + (numbers_receiver?numbers_receiver:'') 
			+ '&names_receiver=' + (names_receiver?names_receiver:'') 
			+ '&append=' + (append?append:'');
	$.dialog({title:'选择人员',width:'720px',height:'460px',fixed:true, lock: true,drag: false,max:false,min:false,content:'url:'+url});
}

//选择用户、部门（弹窗方式）的默认方法
//使用方式：给指定的容器加上onclick等时间来调用
//第一个参数single为boolean类型，是否单选
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数deptIds_receiver为String类型， 代表父页面接收返回deptIds的容器的id
//第五个参数deptNames_receiver为String类型， 代表父页面接收返回deptNames的容器的id
//第六个参数auto为boolean类型， 代表是否自动赋值：true代表是
function choose_users_depts(single, ids_receiver, names_receiver, deptIds_receiver, deptNames_receiver, auto) {
	var url = basePath + 'common/dialog/user_dept_choose.jsp?single=' + (single ? single : '') + '&userid=' + (ids_receiver ? ids_receiver : '') + '&username=' + (names_receiver ? names_receiver : '') + '&deptIds=' + (deptIds_receiver ? deptIds_receiver : '') + '&deptNames=' + (deptNames_receiver ? deptNames_receiver : '') + '&auto=' + (auto ? auto : '');
	var iTop = (window.screen.availHeight - 30 - 400) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - 800) / 2; // 获得窗口的水平位置;
	// 弹窗方式1 - lhdialog组件
	$.dialog({
		title : '选择用户',
		width: '960px',
	    height: 495,
		//width: parseInt($(document).width()*0.7),
	    //height: parseInt($(document).height()*0.735),
		lock: true,
		drag: false,
		max:false,
	    min:false,
		zIndex : 4000,
		content : 'url:' + url
	});
	// 弹窗方式2 - 原生弹窗
	// window.open(url,'选择用户', 'width=800, height=400, toolbars=no,
	// top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no,
	// menubar=no, resizable=no');
}

/**
 * @author WangXiaolong
 * @description 选取流程组
 * @param single
 * @param ids_receiver
 * @param names_receiver
 * @param names_receiverDiv
 * @return
 */
function select_group(single, ids_receiver, names_receiver, names_receiverDiv) {
	var url = basePath + 'common/dialog/grouplist.jsp?single=' + (single ? single : '') + '&groupid=' + (ids_receiver ? ids_receiver : '') + '&groupname=' + (names_receiver ? names_receiver : '') + '&groupnameDiv=' + (names_receiverDiv ? names_receiverDiv : '');
	var iTop = (window.screen.availHeight - 30 - 400) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - 700) / 2; // 获得窗口的水平位置;
	// 弹窗方式1 - lhdialog组件
	$.dialog({
		title : '选择流程组',
		width : '800px',
		height : 400,
		zIndex : 4000,
		content : 'url:' + url
	});
	// 弹窗方式2 - 原生弹窗
	// window.open(url,'选择流程组', 'width=700, height=400, toolbars=no,
	// top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no,
	// menubar=no, resizable=no');
}

//选择人员（弹窗方式）的默认方法
//使用方式：给指定的容器加上onclick等时间来调用
//第二个参数ids_receiver为String类型， 代表父页面接收返回ids的容器的id
//第三个参数names_receiver为String类型， 代表父页面接收返回names的容器的id
//第四个参数display_field为String类型，代表用户列表需要显示的字段，多个值以,隔开
//第五个参数selected_field为String类型，代表已选择人员列表需要显示的字段，多个值以,隔开
function choose_userMobiles(single,ids_receiver,names_receiver,phones_receiver,display_field,selected_field,from) {
	var url = basePath+'common/dialog/userchooseMobile.jsp?single='+(single?single:'') 
			+ '&userid=' + (ids_receiver?ids_receiver:'') 
			+ '&username=' + (names_receiver?names_receiver:'')
			+ '&telPhone=' + (phones_receiver?phones_receiver:'')
			+'&display='+(display_field?display_field:'id,realName,deptName,postName')
			+'&seldisplay='+(selected_field?selected_field:'id,realName');
	//var iTop = (window.screen.availHeight-30-400)/2; //获得窗口的垂直位置;
	//var iLeft = (window.screen.availWidth-10-800)/2; //获得窗口的水平位置;
	
	//弹窗方式1 - lhdialog组件
	$.dialog({
		lock: true,
		drag: false,
		max:false,
	    min:false,
		title: '选择人员',
	    width: '960px',
	    height: 495,
		//width: parseInt($(document).width()*0.7),
	    //height: parseInt($(document).height()*0.735),
	    zIndex: 4000,
	    content: 'url:'+url
	});
	//弹窗方式2 - 原生弹窗
	//window.open(url,'选择人员', 'width=800, height=400, toolbars=no, top='+iTop+',left='+iLeft+', scrollbars=yes, location=yes, status=no, menubar=no, resizable=no');
}

