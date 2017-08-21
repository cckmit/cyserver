/**
 * Created by mcz on 2017/6/2.
 */

myApp.controller('resumezhuangtaiViewCtrls', function($scope,$http,$stateParams) {

    //设置默认的当前状态
    $scope.active_tabs='1';

    $scope.xianyin=false;

    $scope.chkItem=function(index){
        //更改的当前状态
        $scope.active_tabs=index;

        $scope.sVar=index;

        if(index!='1'){
            $scope.xianyin=true;
        }else{
            $scope.xianyin=false;
        }
        //重新获取
        $scope.tabChanges();
    }

    //初始化数据------------
    //是否允许加载
    $scope.moredata=true;
    //开始的页数
    var pages=0;
    //加载的条目数
    var row=2;
    //招聘简历的数据
    $scope.zpList=[];

    $scope.dataNull=false;
    //下拉刷新
    $scope.doRefresh2 = function() {

        //重新调用上拉加载
        $scope.goloadmore();
        $scope.$broadcast('scroll.refreshComplete');
    };

    //重新调用上拉加载
    $scope.goloadmore = function(){
        $scope.tabChanges();

    }


    //上拉加载
    $scope.loadMore2 = function() {
        pages += 1;
        //console.log(pages+'----------------------------------------------');
        zhaopinListJianli($scope,$http,pages,row,2);
        $scope.$broadcast('scroll.infiniteScrollComplete');
    };

    // 根据tab选择
    $scope.tabChanges = function(){
        pages= 0;
        $scope.zpList=[];
        $scope.moredata=true;
        $scope.dataNull=false;
        //调用上拉加载
        $scope.loadMore2();
    }


    //面试通知
    $scope.mianshititle = function(index){
        // console.log(index)
        $scope.dqmianshi=true;
        $scope.jieshowx=true;
        $scope.yjieshowx=false;

        $scope.mianshi={
            title:'qqqw',
            id:index
        };
    }

    //监听全局点击事件
    $scope.handleClick = function( event ){
        // console.log(event.target.className)
        if(event.target.className=='wai_divbix'){
            $scope.dqmianshi=false;
        }
    };
    //点击接受
    $scope.jieshou=function(){
        $scope.jieshowx=false;
        $scope.yjieshowx=true;
    }

})

//获取简历列表
function zhaopinListJianli($scope,$http,pages,row){
    console.log("-----------------qweqw-eqw-e-qwe-qw-e-----"+pages)
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
