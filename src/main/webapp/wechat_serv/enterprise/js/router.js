/**
 * Created by jiangling on 16/05/2017.
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

//test by jl
/*myApp.run(function($ionicPlatform) {
    $ionicPlatform.ready(function() {
        if(window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        }
        if(window.StatusBar) {
            StatusBar.styleDefault();
        }
    });
})*/

var myApp=angular.module("myApp", ['ionic'],httpPost);

// myApp.run(['$rootScope',function ($rootScope) {
//
//     //当且仅当path或url变化成功后触发
//     $rootScope.$on('$locationChangeSuccess',function (event,msg) {
//
//         console.log([event,msg]);
//
//     });
//
// }])

myApp.config(['$stateProvider','$ionicConfigProvider', '$urlRouterProvider', function($stateProvider, $ionicConfigProvider, $urlRouterProvider) {
    $ionicConfigProvider.scrolling.jsScrolling(true);

    //凡是输入的路由不正确,都会跳转到这个页面, 合成整个项目后, 为index.html
    $urlRouterProvider.otherwise('/main');
    $stateProvider

        //校友企业 mian
        .state('main', {
            url: '/main',
            templateUrl: "wrap/main.html",
            controller: 'mainViewCtrls'
        })


        // .state('enterprise_list', {  //企业列表
        //     url: '/enterprise_list',
        //     templateUrl: "wrap/enterprise_list.html",
        //     controller: 'enterpriseListViewCtrl'
        // })

        .state('enterprise_detail', {  //企业详情
            url: '/enterprise_detail/:id',
            templateUrl: "wrap/enterprise_detail.html",
            controller: 'enterpriseDetailViewCtrl'
        })
        .state('product_list', {  //产品列表
            url: '/product_list/:id',
            templateUrl: "wrap/product_list.html",
            controller: 'productListViewCtrl'
        })
        .state('product_detail', {  //产品详情
            url: '/product_detail/:id',
            templateUrl: "wrap/product_detail.html",
            controller: 'productDetailViewCtrl'
        })
        .state('position_list', {  //职位列表
            url: '/position_list/:id',
            templateUrl: "wrap/position_list.html",
            controller: 'positionListViewCtrl'
        })
        .state('position_detail', {  //职位列表
            url: '/position_detail/:id',
            templateUrl: "wrap/position_detail.html",
            controller: 'positionDetailViewCtrl'
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
        }else{
            return '../img.product.png';
        }
    };
});

//滤镜： -------
myApp.filter('filter_picss', function () {
    return function (text) {
        if(text && text!=''){
            return text.split(',')[0];
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


//滤镜： 处理富文本
myApp.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);
