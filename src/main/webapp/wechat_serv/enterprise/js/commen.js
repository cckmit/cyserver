/**
 * Created by jiangling on 16/05/2017.
 */

//rem 适配
document.documentElement.style.fontSize = $(document.documentElement).width()/3.75 + 'px';

$(window).on('resize', function() {
    document.documentElement.style.fontSize = $(document.documentElement).width()/3.75 + 'px';

})