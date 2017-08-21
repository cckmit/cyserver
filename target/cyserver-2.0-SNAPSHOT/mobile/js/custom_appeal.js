


var dia = null;

Zepto(function($){
    //点赞效果
    $(document).on('tap','.like',function(){
    	var serviceId = $(this).attr('rel');
        var likeNum = $(this).children('.like-num').text();

        doServPraise(serviceId);
        
        
        if($(this).hasClass("clicked")){
            likeNum --;
            
            if(likeNum == 0)
            {
            	$(this).html('<i class="fa fa-heart-o"></i> 赞');
            }
            else
            {
            	$(this).html('<i class="fa fa-heart-o"></i> <span class="like-num">'+likeNum+'</span>');
            }
            
            
            $(this).removeClass("clicked");
        } else {
            likeNum ++;
            $(this).html('<i class="fa fa-heart"></i> <span class="like-num">'+likeNum+'</span>');
            $(this).addClass("clicked");
        }
    });

    //收藏效果
    $(document).on('tap','.favorites',function(){
    	var serviceId = $(this).attr('rel');
    	doServFavorite(serviceId);
        if($(this).hasClass("clicked")){
            $(this).html('<i class="fa fa-star-o"></i>');
            $(this).removeClass("clicked");
            //此处添加取消收藏代码
        } else {
            $(this).html('<i class="fa fa-star"></i>');
            $(this).addClass("clicked");
            //此处添加添加收藏代码
        }
    });
    
    
    
    
    $(document).on('tap','.ui-actionsheet .ui-actionsheet-del',function(){
        var serviceId = $(this).attr("rel");
      	$('.ui-actionsheet').removeClass('show');
          dia=$.dialog({
            title:'温馨提示',
            content:'您真的要删除该信息吗？',
            button:["确认","取消"]
          });
          
          dia.on("dialog:action",function(e){
            console.log(e.index);
            if(e.index == 0){
              //这里写提交数据代码
            	
            	doServDel(serviceId);
            }
          });
          dia.on("dialog:hide",function(e){
            console.log("dialog hide");
          });
        });
        
        
        
        $(document).on('tap','.ui-actionsheet .ui-actionsheet-complaint',function(){
        var serviceId = $(this).attr("rel");
        $('.ui-actionsheet').removeClass('show');
          dia=$.dialog({
            title:'温馨提示',
            content:'您真的要举报该信息吗？',
            button:["确认","取消"]
          });
          
          dia.on("dialog:action",function(e){
            console.log(e.index);
            if(e.index == 0){
              //这里写提交数据代码
            	
            	doServComplaint(serviceId);
            }
          });
          dia.on("dialog:hide",function(e){
            console.log("dialog hide");
          });
        });
    
    

    //链接功能
    $(document).on('tap','.post-content article',function(){
        if($(this).parent().data('href')){
            location.href= $(this).parent().data('href');
        }
    });
    $(document).on('tap','.ui-footer-btn li',function(){
        if($(this).data('href')){
            location.href= $(this).data('href');
        }
    });
    $(document).on('tap','.comment',function(){
        if($(this).parents('.post-action').prev().data('href')){
            location.href= $(this).parents('.post-action').prev().data('href');
        }
    });

    //底部弹出菜单
    $("body").append(actionsheetTpl);
    $(document).on('tap','.show-bot-menu',function(){
        $('.ui-actionsheet').addClass('show');
    });
    $(document).on('tap','.ui-actionsheet .cancel',function(){
        $('.ui-actionsheet').removeClass('show');
    });

    //添加图片浏览
    $("body").append(pswpTpl);

});



function doServPraise(serviceId)
{
	$.ajax({
			url : "mobServAction!doNotNeedSessionAndSecurity_praise.action?" + 
					"cyServPraise.serviceId=" + serviceId +
					"&cyServPraise.accountNum=" + accountNum,

			dataType : 'json',
	        type: "POST"
	    });

}



function doServFavorite(serviceId)
{
	$.ajax({
			url : "mobServAction!doNotNeedSessionAndSecurity_favorite.action?" + 
					"cyServFavorite.serviceId=" + serviceId +
					"&cyServFavorite.accountNum=" + accountNum,

			dataType : 'json',
	        type: "POST"
	    });

}




function doServComplaint(serviceId)
{
	$.ajax({
			url : "mobServAction!doNotNeedSessionAndSecurity_complaint.action?" + 
					"cyServComplaint.serviceId=" + serviceId +
					"&cyServComplaint.accountNum=" + accountNum + 
					"&cyServComplaint.reason=手机用户举报",
			
			dataType : 'json',
	        type: "POST",
			
	        success: function(data) 
	        {
	        	
	        	if(data.status == true)
	        	{
	        		dia=$.dialog({
				        title:'温馨提示',
				        content:'举报成功！',
				        button:["确认"]
				      });
	        		
	        	}
	        	else
	        	{
	        		dia=$.dialog({
				        title:'温馨提示',
				        content:'举报失败或您已经举报过！',
				        button:["确认"]
				      });
	        	}
	        	
	        },
	        complete: function()
	        {
	        	dia.on("dialog:hide",function(e){
				        console.log("dialog hide");
				});
	        }
	    });

}


function doServDel(serviceId)
{
	
	$.ajax({
			url : "mobServAction!doNotNeedSessionAndSecurity_deleteServ.action?" + 
					"cyServ.id=" + serviceId +
					"&cyServ.accountNum=" + accountNum,
			
			dataType : 'json',
	        type: "POST",
			
	        success: function(data) 
	        {
	        	
	        	if(data.status == true)
	        	{
	        		dia=$.dialog({
				        title:'温馨提示',
				        content:'删除成功！',
				        button:["确认"]
				      });
	        		//alert("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=" + category+listExtensionParameters+"&cyServ.region=" + region + "&cyServ.currentRow=0&cyServ.incremental=" + $(".thread-list > li").length);
				    
	        		if(isMyList)
	        		{
	        			//getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=" + category+listExtensionParameters+"&cyServ.region=" + region + "&cyServ.currentRow=0&cyServ.incremental=" + $(".thread-list > li").length,".current .thread-list","update",appealTpl,null);
	        			location.reload();
	        		}
	        		else
	        		{
	        			getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=" + category+listExtensionParameters+"&cyServ.region=" + region + "&cyServ.currentRow=0&cyServ.incremental=" + $(".thread-list > li").length,".thread-list","update",appealTpl,null);
	        		}
	        		
	        		
	        		
	        	}
	        	else
	        	{
	        		dia=$.dialog({
				        title:'温馨提示',
				        content:'删除失败，请重试！',
				        button:["确认"]
				      });
	        	}
	        	
	        },
	        complete: function()
	        {
	        	dia.on("dialog:hide",function(e){
				        console.log("dialog hide");
				});
	        }
	    });

}
