/**
 * Created by mcz on 2017/5/24.
 */


//index首页的
myApp.controller('tabindexViewCtrls', function($scope,$http,$stateParams,$state) {
    $state.go('tabindex.index');
})


//tabindex首页的
myApp.controller('indexCtr', function($scope,$http,$stateParams,$state) {
    $scope.select1=true;
    //顶部选项卡的切换
    $scope.changeTabs = function(index){
        if(index==1){
            $scope.select1=true;
            $scope.select2=false;
            $scope.select3=false;
        }else if(index==2){
            $scope.select1=false;
            $scope.select2=true;
            $scope.select3=false;
        }else{
            $scope.select1=false;
            $scope.select2=false;
            $scope.select3=true;
        }
    }
})

//有顶部tabs的主页面
myApp.controller('mainViewCtrls', function($scope,$http,$stateParams,$state) {
    $state.go('tabindex.index.main1');
})

//tab1
myApp.controller('main1ViewCtrls', function($scope,$http,$ionicActionSheet,$timeout) {

    var vm=$scope.vm={};
    vm.cb = function () {
        console.log(vm.CityPickData.areaData)
    }
    vm.CityPickData = {
        areaData: ['请选择城市'],
        title: '区域筛选',
        hardwareBackButtonClose: false
    }


    //初始化数据------------
    //是否允许加载
    $scope.moredata=true;
    //开始的页数
    var pages=0;
    //加载的条目数
    var row=3;
    //招聘列表的数据
    $scope.zpList=[];

    $scope.dataNull=false;
    //下拉刷新
    $scope.doRefresh = function() {
        //把数据初始化
        pages= 0;
        $scope.zpList=[];
        $scope.moredata=true;
        $scope.dataNull=false;

        //调用上拉加载
        $scope.loadMore()
        // zhaopinList($scope,$http,pages,row,1);
        $scope.$broadcast('scroll.refreshComplete');
    };

    //上拉加载
    $scope.loadMore = function() {
        pages += 1;
        // console.log(pages+'----------------------------------------------');
        zhaopinList($scope,$http,pages,row,2);
        // $scope.$broadcast('scroll.infiniteScrollComplete');
    };


    //搜索框中有无内容
    $scope.seachMain = function(){
        if($scope.searchs && $scope.searchs!=''){
            console.log(111);
        }else{
            console.log(222)
        }
    }
    //点击搜索框
    //清除
    $scope.clearInput = function(){
    }
})

//获取招聘列表
function zhaopinList($scope,$http,pages,row){
    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data:{
                jsonStr:'{"command": "236","content": {"page": "'+pages+'","rows": "'+row+'","category": "37","topnews": "20"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            if(response.data.obj && response.data.obj.rows && response.data.obj.rows.length>0){
                for(var i in response.data.obj.rows){
                    $scope.zpList.push(response.data.obj.rows[i]);
                    $scope.$broadcast('scroll.infiniteScrollComplete');
                }
            }
            if(!response.data || !response.data.obj || !response.data.obj.rows || response.data.obj.rows.length==0){
                $scope.moredata=false;
                $scope.$broadcast('scroll.infiniteScrollComplete');
                $scope.dataNull=true;
            }
            // console.log($scope.zpList);
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}


//tab2
// myApp.controller('main2ViewCtrls', function($scope,$http,$stateParams) {})

//tab3
// myApp.controller('main3ViewCtrls', function($scope,$http,$stateParams) {})


//通过工厂模式创建自定义服务--------获取数据的服务-
myApp.factory('myFactoryHttp', function($http, $q) {
    return {
        // urls 为请求的地址 jsons 为请求的参数
        query : function(urls,jsons) {
            var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
            B.ready(function() {
                $http({
                    method: 'POST',
                    url: urls,
                    data: {
                        jsonStr: JSON.stringify(jsons)
                    },
                    dataType: 'json'
                }).then(function successCallback(response) {
                    deferred.resolve(response.data);  // 声明执行成功，即http请求数据成功，可以返回数据了
                }, function errorCallback(response) {
                    deferred.reject(response.data);   // 声明执行失败，即服务器返回错误
                });
            });
            return deferred.promise;   // 返回承诺，这里并不是最终数据，而是访问最终数据的API
        }
    };

});