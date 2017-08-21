/**
 * Created by mcz on 2017/6/6.
 */

var httpPost = function($httpProvider) {
    /*******************************************
     说明：$http的post提交时，纠正消息体
     ********************************************/
    // Use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    /*
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function(obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
        for (name in obj) {
            value = obj[name];
            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value instanceof Object) {
                for (subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value !== undefined && value !== null)
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
        return query.length ? query.substr(0, query.length - 1) : query;
    };
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [
        function(data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
        }
    ];
};
//定义主模块并注入依赖
var activeApp=angular.module("activeApp", ['ionic'],httpPost);

//active
activeApp.controller('activeViewCtrls', function($scope,$state,$http,$interval,$timeout) {
    var reg_phone=/^1\d{10}$/;  //广泛校验

    var openId = localStorage.getItem("openId");
    //var openId=1234;

    $scope.getuser=false;
    //点击报名
    $scope.baomingbtn= function (){
        $scope.getuser=true;
        $('body').css("overflow","hidden");
    }
    //点击关闭
    $scope.closelogin= function (){
        $scope.getuser=false;
        $('body').css("overflow","auto");
    }

    //用户报名
    $scope.user={
        username:'',
        phone:'',
        usercode:''
    }


    //是否可以报名
    $scope.baoming=true;
    //报名开始时间
    var strattimts;
    //报名结束时间
    var endtimts;
    //活动开始时间
    var huodongstrat;
    //活动结束时间
    var huodongend;

    //报名人列表
    $scope.bmrlist=true;

    //当前用户的中奖信息
    $scope.dquserzj=false;
    //中奖名单
    $scope.zhmingdan=false;

    //是否中奖
    $scope.userzhognj='';

    var activityId='a043ae341a494700a27a3203a1b319db';
    // var activityId='0ca4a84a17094f0cb94700379f664c26';

    var createDate='';

    //获取活动详情
    B.ready(function() {
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "511","content": {"activityId": "'+activityId+'"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            // alert('11:'+JSON.stringify(response));

            //console.log(response.data);
            $scope.xiangqing=response.data.obj;


            //获取当前时间
            var nowTime = new Date();
            //获取报名开始时间
            strattimts=new Date($scope.xiangqing.signUpStartTime.replace(/-/g, "/"));
            //获取报名结束时间
            endtimts=new Date($scope.xiangqing.signUpEndTime.replace(/-/g, "/"));
            //活动开始时间
            huodongstrat=new Date($scope.xiangqing.startTime.replace(/-/g, "/"));
            //活动结束时间
            huodongend=new Date($scope.xiangqing.endTime.replace(/-/g, "/"));

            // //获取当前时间
            // var nowTime = new Date('2017/06/08 15:06:00');
            //
            // //获取报名开始时间
            // strattimts=new Date('2017/06/08 14:06:00');
            // //获取报名结束时间
            // endtimts=new Date('2017/06/08 16:06:00');
            // //活动开始时间
            // huodongstrat=new Date('2017/06/08 18:06:00');
            // //活动结束时间
            // huodongend=new Date('2017/06/08 20:06:00');


            //时间格式化
            nowTime = nowTime.getTime();
            strattimts = strattimts.getTime();
            endtimts= endtimts.getTime();
            huodongstrat= huodongstrat.getTime();
            huodongend=huodongend.getTime();

            //报名开始与结束
            if(strattimts<nowTime && nowTime<endtimts){
            //报名中  --不显示个人中奖信息与中奖人名单
                $scope.baoming=true;
                //获取报名人列表
                gerbaomrlist(createDate,1);

                //当前用户的中奖信息
                $scope.dqusers(0);
            }else if(endtimts<nowTime){
            //报名已结束 --显示个人中奖信息与中奖人名单
                $scope.baoming=false;
                $scope.bmtext='报名已结束';
                //获取报名人列表
                gerbaomrlist(createDate,0);

                //当前用户的中奖信息
                // $scope.dqusers(0);
            }else{
            //报名未开始 --不显示个人中奖信息与中奖人名单
                $scope.baoming=false;
                $scope.bmrlist=false;
                $scope.bmtext='报名未开始';

                //当前用户的中奖信息
                //$scope.dqusers(1);
            }


            //活动是否结束
            if(huodongend<nowTime){
            //活动已结束
                $scope.dquserzj=true;
                $scope.zhmingdan=true;
                $scope.bmtext='活动已结束';
                //当前用户的中奖信息
                $scope.dqusers(1);
            }else if(huodongstrat>nowTime){
            //活动未开始
                $scope.dquserzj=false;
                $scope.zhmingdan=false;
            }else{
            //活动进行中
                $scope.dquserzj=false;
                $scope.zhmingdan=false;
            }

        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });
    })
    $scope.$on('ngPic', function() {
        $(function(){
            var swiper = new Swiper('.swiper-container');
        });
    })

    //点击确定
    $scope.tijiao = function(){
        console.log($scope.user);

        if( $scope.user.phone=='' || !reg_phone.test($scope.user.phone)){
            layer.open({
                title: '提示'
                ,content: '请填写正确的电话号码'
            });
            return false;
        }

        if( $scope.user.username==''){
            layer.open({
                title: '提示'
                ,content: '请填写您的姓名'
            });
            return false;
        }
        if( $scope.user.usercode==''){
            layer.open({
                title: '提示'
                ,content: '请填写验证码'
            });
            return false;
        }

        //console.log('{"command": "510","content": {"activityId": "'+$scope.xiangqing.id+'","name": "'+$scope.user.username+'","telePhone": "'+$scope.user.phone+'","openId": "'+openId+'"}}');
        B.ready(function() {
            $http({
                method: 'POST',
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "510","content": {"activityId": "'+activityId+'","name": "'+$scope.user.username+'","telePhone": "'+$scope.user.phone+'","openId": "'+openId+'"}}'
                },
                dataType: 'json'
            }).then(function successCallback(response) {
                if(response.data.success){
                    $scope.baoming=false;
                    $scope.bmtext='已报名';
                    $scope.closelogin();
                    layer.msg('报名成功', {
                        icon: 6,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        //do something
                    });
                }else{
                    layer.open({
                        title: '提示',
                        icon: 5,
                        content: response.data.msg
                    });
                }

                // $scope.xiangqing=response.data.obj;
            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });
        })
    }



    $scope.codeBtn=false;

    var times=60;
    $scope.codeVue="获取验证码";
    //倒计时
    $scope.getyanzhengma = function(){

        $('.getcode').attr('disabled','disabled');
        if(times>=0){
            $scope.codeBtn=true;
            $scope.codeVue=times+"s后获取";
            times--;
            $timeout($scope.getyanzhengma,1000);
        }else{
            $('.getcode').removeAttr('disabled');
            $scope.codeBtn=false;
            $scope.codeVue="获取验证码";
            times=60;
        }
    }
    //点击获取验证码
    $scope.getcodes = function(){
        if( $scope.user.phone=='' || !reg_phone.test($scope.user.phone)){
            layer.open({
                title: '提示'
                ,content: '请填写正确的电话号码'
            });
            return false;
        }
        //倒计时
        $scope.getyanzhengma();
        //获取验证码
        huoquyanzhengma($http,$scope.user.phone);
    }

    $scope.baomingren=[];

    //获取报名人列表
    var gerbaomrlist = function(createDates,flag){

        //console.log('{"command": "512","content": {"activityId": "'+$scope.xiangqing.id+'","createDate":"'+createDates+'"}}')
        B.ready(function() {
            $http({
                method: 'POST',
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "512","content": {"activityId": "'+activityId+'","createDate":"'+createDates+'"}}'
                },
                dataType: 'json'
            }).then(function successCallback(response) {


                if(response.data.obj && response.data.obj!=''){
                    for(var i in response.data.obj){
                        $scope.baomingren.push(response.data.obj[i]);
                    }
                }

                if(response.data.obj && response.data.obj!='' && response.data.obj[0] && response.data.obj[0].createDate!=''){
                    var m = new Date(new Date(response.data.obj[0].createDate).getTime()+1000);
                    // var m = new Date(new Date(q).getTime()+1000);
                    var nian=m.getFullYear();
                    var yue=m.getMonth()+1;
                    if(yue<10){
                        yue = '0'+yue;
                    }
                    var ri=m.getDate();
                    if(ri<10){
                        ri = '0'+ri;
                    }
                    var shi=m.getHours();
                    if(shi<10){
                        shi = '0'+shi;
                    }
                    var fen=m.getMinutes();
                    if(fen<10){
                        fen = '0'+fen;
                    }
                    var miao=m.getSeconds();
                    if(miao<10){
                        miao = '0'+miao;
                    }
                    createDate=nian+'-'+yue+'-'+ri+' '+shi+':'+fen+':'+miao;
                }else{
                    createDate=createDate;
                }

                if(flag=='1'){
                    $interval(function(){
                        gerbaomrlist(createDate,0)
                    },5000)
                }

            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });
        })



    }



    //当前用户的中奖信息
    $scope.dqusers=function(flag){
        //flag = 0 为活动报名中   1 为活动已结束
        //console.log('{"command": "515","content": {"activityId": "'+$scope.xiangqing.id+'","openId":"'+openId+'"}}')
        B.ready(function() {
            $http({
                method: 'POST',
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "515","content": {"activityId": "'+activityId+'","openId":"'+openId+'"}}'
                },
                dataType: 'json'
            }).then(function successCallback(response) {

                if( !response.data || response.data=='' || !response.data.obj || response.data.obj==''){
                //没有获取到
                    //当前用户的中奖信息
                    $scope.dquserzj=false;
                }
                else{

                //已报名
                    $scope.baoming=false;
                    if(flag == '0'){
                        $scope.bmtext='已报名';
                    }else {
                        $scope.bmtext='活动已结束';

                        $scope.dquserzj=true;
                    }

                    $scope.dauserxinxi=response.data.obj;
                    //是否中奖
                    if($scope.dauserxinxi.isWinning=='1'){
                        $scope.userzhognj=true;
                    }else{
                        $scope.userzhognj=false;
                    }

                }
                // alert('11:'+JSON.stringify(response));
            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });
        })
    }



    //获取中奖人列表
    B.ready(function() {
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "514","content": {"activityId": "'+activityId+'"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            // alert('11:'+JSON.stringify(response));

            $scope.zhongjaingren=response.data.obj;

        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });
    })




})

//获取验证码
function huoquyanzhengma($http,phone){
    B.ready(function() {
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "3","content": {"phoneNum": \"'+phone+'\","secretKey": "getRegisterCode123"}}'
            },  //作为消息体参数
            dataType: 'json'
        }).then(function successCallback(response) {

            // layer.open({
            //     title: '提示'
            //     ,content: response.data.msg
            // });

            // alert('11:'+JSON.stringify(response));
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });
    })
}


//on-finish-pic ---轮播图加载完毕
activeApp.directive('onFinishPic', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngPic');
                });
            }
        }
    }
});



//滤镜： 判断图片是否有http或者https
activeApp.filter('filter_pic', function () {
    return function (text) {
        if(text && text!=''){
            if(text.indexOf("http:") == 0 || text.indexOf("https:") == 0 ){
                return text;
            }else{
                // text = B.serverUrl+text;
                text = B.imageServiceHttp+text;
                return text;
            }
        }else{
            return '../img/1.jpg';
        }
    };
});

//截断超长字符串的过滤器
activeApp.filter("truncate", function(){
    return function(text, length){
        if (text) {
            var ellipsis = text.length > length ? "..." : "";
            return text.slice(0, length) + ellipsis;
        };
        return text;
    }
});

//手机号中间四位为****
activeApp.filter("phonenum", function(){
    return function(text, length){
        if (text) {
            return text.substring(0,3)+'****'+text.substring(7,11);
        };
        return text;
    }
});

//滤镜： 处理富文本
activeApp.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);