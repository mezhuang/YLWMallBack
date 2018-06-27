(function(){
    //Section 1 : 按下自定义按钮时执行的代码
    var a= {
        exec:function(editor)
        {
        	editor.insertHtml("{流程定义版本:version}");			
        }
    },
    //Section 2 : 创建自定义按钮、绑定方法
    b='version';
    CKEDITOR.plugins.add(b,{
        init:function(editor){
            editor.addCommand(b,a);
            editor.ui.addButton('version',{
                label:'流程定义版本',
                icon: this.path + 'version.png',
                command:b
            });
        }
    });
})();
