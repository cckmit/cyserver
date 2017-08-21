$(function(){
	$(".login-tap span").click(function(){
		if($(this).hasClass("login-tap-no-current")) {
			$('.login-tap span:not(#'+this.id+')').addClass("login-tap-no-current");
			$(this).removeClass("login-tap-no-current");
			$('.login-input:not(#'+this.id+'-input)').addClass("hidden");
			$('#'+this.id+'-input').removeClass("hidden");
		}
	});
	
});
