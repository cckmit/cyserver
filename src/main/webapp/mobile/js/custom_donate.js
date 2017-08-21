//底部按钮点击效果
$(document).on('tap','.ui-footer button',function(){
    if($(this).data('href')){
        location.href= $(this).data('href');
    }
});
$(document).on('tap','.ui-footer li',function(){
    if($(this).data('href')){
        location.href= $(this).data('href');
    }
});
//列表按钮点击效果
$(document).on('tap','.ui-list li',function(){
    if($(this).data('href')){
        location.href= $(this).data('href');
    }
});
$(document).on('tap','.ui-form-item-link',function(){
    if($(this).data('href')){
        location.href= $(this).data('href');
    }
});
$(document).on('tap','.ui-notice-btn button',function(){
    if($(this).data('href')){
        location.href= $(this).data('href');
    }
});

$("#submitDonate").tap(function(){
    var dia=$.dialog({
        title:'温馨提示',
        content:'温馨提示内容',
        button:["确认","取消"]
    });

    dia.on("dialog:action",function(e){
        console.log(e.index)
    });
    dia.on("dialog:hide",function(e){
        console.log("dialog hide")
    });

});