/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.language = 'zh-cn';
	config.uiColor = 'D3D3D3';//    #008DE6(蓝色)    #FFFFFF(白色)   #D3D3D3(灰色)
	config.skin='office2013';//office2003 v2 kama
	config.removePlugins = 'elementspath';
    config.resize_enabled = false;// 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js
	config.filebrowserUploadUrl="upload/ckeditorUpload.do";
	config.enterMode = CKEDITOR.ENTER_BR;
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+config.font_names;
    config.autoUpdateElement = true;// 当提交包含有此编辑器的表单时，是否自动更新元素内的数据
    config.disableObjectResizing = false; //是否开启 图片和表格 的改变大小的功能 config.disableObjectResizing = true; 默认为开启
	config.toolbar='MyBar';
	config.toolbar_MyBar=[
					['Source','-','Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo','SelectAll'],
					['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','Outdent','Indent'],
					['Maximize'],
					'/',
					['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
					['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
					'/',
					['Styles','Format','Font','FontSize'],
					['TextColor','BGColor']
		        ];
	config.removeDialogTabs = 'image:advanced,image:Link,image:info';
};
