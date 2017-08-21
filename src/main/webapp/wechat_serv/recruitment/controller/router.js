/**
 * Created by jiangling on 05/06/2017.
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


var myApp=angular.module("myApp", ['ionic'],httpPost);


myApp.config(['$stateProvider','$ionicConfigProvider', '$urlRouterProvider', function($stateProvider, $ionicConfigProvider, $urlRouterProvider) {
    $ionicConfigProvider.scrolling.jsScrolling(true);

    //凡是输入的路由不正确,都会跳转到这个页面, 合成整个项目后, 为index.html
    $urlRouterProvider.otherwise('/main');
    $stateProvider

        /*.state('main', {  //入口
            url: '/main',
            templateUrl: "main.html",
            controller: 'mainCtrl'
        })*/
        .state('findjob', {  //找工作
            url: '/findjob',
            templateUrl: "find_job/findjob.html",
            controller: 'findJobCtrl'
        })
        .state('myresume', {  //我的
            url: '/myresume',
            templateUrl: "my_resume/myresume.html",
            controller: 'myResumeCtrl'
        })






}]);




//滤镜： 判断图片是否有http或者https
myApp.filter('filter_pic', function () {
    return function (text) {
        if(text && text!=''){
            if(text.indexOf("http:") == 0 || text.indexOf("https:") == 0 ){
                return text;
            }else{
                // text = B.serverUrl+text;
                text = B.imageServiceHttp+text;
                return text;
            }
        }
    };
});

//截断超长字符串的过滤器
myApp.filter("truncate", function(){
    return function(text, length){
        if (text) {
            var ellipsis = text.length > length ? "..." : "";
            return text.slice(0, length) + ellipsis;
        };
        return text;
    }
});