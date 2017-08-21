/**
 * Created by Cha0res on 2016/10/10.
 */
function chuiZhiErJi(){
    $(".ce > li > table a").click(function(){
        $(this).addClass("xz").parents().siblings().find("a").removeClass("xz");
        $(this).parents().siblings().find(".er").hide(100);
        $(this).siblings(".er").toggle(100);
        $(this).parents().siblings().find(".er > li > .thr").hide().parents().siblings().find(".thr_nr").hide();
    })

    $(".er > li > a").click(function(){
        $(this).addClass("sen_x").parents().siblings().find("a").removeClass("sen_x");
        $(this).parents().siblings().find(".thr").hide(100);
        $(this).siblings(".thr").toggle(100);
    })
}

