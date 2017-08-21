/**
 * Created by mcz on 2017/6/6.
 */


//index
myApp.controller('indexView', function($scope,$state,$http,$timeout,myFactoryHttp,$sce) {
    var urls=B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action';
    var jsons={
        "command": "511",
        "content": {
            "activityId": "a043ae341a494700a27a3203a1b319db"
        }
    };
    // 同步调用，获得承诺接口
    var promise = myFactoryHttp.query(urls,jsons);
    // 调用承诺API获取数据 .resolve
    promise.then(function(data) {

        $scope.hdxiangqing=data.obj;
        $scope.sce = $sce.trustAsResourceUrl;

        // $scope.yinyue=$scope.hdxiangqing.activityMusicList[0].filePath;
        $scope.yinyue="https://0.s3.envato.com/files/122779281/preview.mp3";


        //传递值
        $scope.$broadcast('xiangqing',$scope.hdxiangqing);
    }, function(data) {
        //console.log('2:'+data);
    });
})



//mian1
myApp.controller('main1ViewCtrls', function($scope,$state,$http,myFactoryHttp,$timeout) {
    //接收值
    $scope.$on('xiangqing', function(event,data1){
        $scope.main2=data1;//接受值
        //活动开始时间
        $scope.activebegin=$scope.main2.startTime;
        // $scope.activebegin='2017-06-12 18:06:00';
        daojishi()
    });


    //活动开始时间
    // $scope.activebegin='2017-06-08 15:55';

    var daojishi = function () {

        // 当前时间
        $scope.nowTime = new Date();
        //活动开始时间格式化
        $scope.endTime = new Date($scope.activebegin.replace(/-/g, "/"));
        // 相差的时间
        $scope.t = $scope.endTime.getTime() - $scope.nowTime.getTime();

        // console.log($scope.nowTime);
        // console.log($scope.endTime);
        // console.log($scope.t);
        if($scope.t/1000<=31){
            $state.go('main2');
            //时
            $scope.timeshi = 0;
            //分
            $scope.timefen = 0;
            //秒
            $scope.timemiao =0;
            return false;

        }
        //天
        $scope.timetian = Math.floor($scope.t/1000/60/60/24);
        //时
        $scope.timeshi = Math.floor($scope.t/1000/60/60%24);
        //分
        $scope.timefen = Math.floor($scope.t/1000/60%60);
        //秒
        $scope.timemiao = Math.floor($scope.t/1000%60);

        $timeout(daojishi,1000)
    };

})


//mian2
myApp.controller('main2ViewCtrls', function($scope,$http,$state,$timeout) {

    $scope.sstimes=31;

    var sanshi = function () {
        if($scope.sstimes>0){
            $scope.sstimes--;
            $timeout(sanshi,1000)
        }else{
            $state.go('main3');
        }
    }
    sanshi()

})

//mian3
myApp.controller('main3ViewCtrls', function($document,$scope,$http,$interval,myFactoryHttp,$timeout) {

    //默认显示的图与文字
    $scope.dqUser={
        name: "？？？",
        // phone: "***********",
        telephone: "13542276684",
        headSrc: "img/liwu.png"
    };

    //定义全局变量----------
    var index = '';  //选中的数组的索引；
    var timer='';    //定义定时器
    var opens=true;   //判断是否可以点击开始 ---避免重复点击

    $scope.begif='开始';

    $scope.luckyUser=[];  //展示获奖人的信息
    $scope.xinxi=[];       //报名人列表

    var nums1 = 0;
    var nums2 = 0;
    var nums3 = 0;

    $scope.prize1=[];
    $scope.prize2=[];
    $scope.prize3=[];

    //获取活动详情
    var urls=B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action';
    var jsons={
        "command": "511",
        "content": {
            "activityId": "a043ae341a494700a27a3203a1b319db"
        }
    };
    // 同步调用，获得承诺接口
    var promise3 = myFactoryHttp.query(urls,jsons);
    // 调用承诺API获取数据 .resolve
    promise3.then(function(data) {
        $scope.main3=data.obj;//接受值
        //所有奖品列表
        $scope.prizes=$scope.main3.activityPrizeList;

        // 1
        $scope.prize1=$scope.prizes[0];
        // 2
        $scope.prize2=$scope.prizes[1];
        // 3
        $scope.prize3=$scope.prizes[2];

        nums1 = $scope.prize1.num;
        nums2 = $scope.prize2.num;
        nums3 = $scope.prize3.num;

    }, function(data) {
        //console.log('2:'+data);
    });


    //获取报名人列表
    var bmrurls=B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action';
    var bmrjsons={
        "command": "512",
        "content": {
            "activityId": "a043ae341a494700a27a3203a1b319db"
        }
    };
    // 同步调用，获得承诺接口
    var bmrpromise = myFactoryHttp.query(bmrurls,bmrjsons);
    // 调用承诺API获取数据 .resolve
    bmrpromise.then(function(data) {
        // console.log('1:'+data);
        $scope.xinxi=data.obj;
    }, function(data) {
        //console.log('2:'+data);
    });


    //生成随机数
    $scope.randomNum = function(minNum,maxNum){
        switch(arguments.length){
            case 1:
                return parseInt(Math.random()*minNum+1);
                break;
            case 2:
                return parseInt(Math.random()*(maxNum-minNum+1)+minNum);
                break;
            default:
                return 0;
                break;
        }
    };

    //根据随机数获取数组中的数据
    $scope.geiData=function(){
        index = $scope.randomNum(0,$scope.xinxi.length-1);
        //当前闪动到的数据
        $scope.dqUser=$scope.xinxi[index];
    }

    //开始定时器循环
    $scope.openinterval = function(){
        //定时循环获取随机数并获取数组中的数据
        timer=$interval($scope.geiData,100);
    }


    //停止定时器循环
    $scope.stopinterval = function(){
        $interval.cancel(timer);
    }


    var nums=0;

    //当前为一等奖
    $scope.yideng=false;
    //当前为二等奖
    $scope.erdeng=true;
    //当前为三等奖
    $scope.sandeng=true;

    $scope.bghuibtn=false;

    $scope.tiaozhuan=false;

    $scope.yaojiang=true;

    //点击开始
    $scope.strat=function(del){
        var n1=parseInt(nums1);
        var n2=parseInt(nums2)+parseInt(nums1);
        var n3=parseInt(nums3)+parseInt(nums2)+parseInt(nums1);

        if($scope.tiaozhuan){
            return false;
        }

        if(opens){
            opens = false;
            $scope.begif='结束';
            //打开循环定时器
            $scope.openinterval();
        }else{
            nums++;
            // console.log(nums+'次');
            // console.log(nums)
            if(nums<n1){
                $scope.yideng=false;
                $scope.erdeng=true;
                $scope.sandeng=true;
            }
            if(nums >= n1 && nums < n2){
                $scope.yideng=true;
                $scope.erdeng=false;
                $scope.sandeng=true;
            }
            if(nums >= n2 && nums < n3){
                $scope.yideng=true;
                $scope.erdeng=true;
                $scope.sandeng=false;
            }

            if(nums >= n3){
                $scope.yideng=true;
                $scope.erdeng=true;
                $scope.sandeng=true;

                $scope.bghuibtn=true;
                $scope.tiaozhuan=true;

                $scope.yaojiang=false;
            }
            $scope.end(del);
        }
        if(del==2){
            $scope.$apply();
        }
    }

    //点击结束
    $scope.end = function(del){
        opens = true;
        $scope.begif='开始';
        //停止定时器
        $scope.stopinterval();
        if(!index || index==''){
            return false;
        }
        //console.log($scope.dqUser);

        var n1=parseInt(nums1);
        var n2=parseInt(nums2)+parseInt(nums1);
        var n3=parseInt(nums3)+parseInt(nums2)+parseInt(nums1);
        // console.log('nums:'+nums);
        // console.log('111:'+n1);
        // console.log('222:'+n2);
        // console.log('333:'+n3);

        if(nums<=n1){
            // console.log('一等奖');
            var awardsId=$scope.prize1;
        }
        if(nums>n1 && nums<=n2){
            // console.log('二等奖');
            var awardsId=$scope.prize2;
        }
        if(nums>n2 && nums<=n3){
            // console.log('三等奖');
            var awardsId=$scope.prize3;
        }

        //提交中奖人
        var zjurls=B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action';
        var zjjsons={
            "command": "513",
            "content": {
                "activityId": $scope.dqUser.activityId,
                "applicantId": $scope.dqUser.id,
                "awardsId": awardsId.id
            }
        };

        // console.log(zjjsons)
        // 同步调用，获得承诺接口
        var bmrpromise = myFactoryHttp.query(zjurls,zjjsons);
        // 调用承诺API获取数据 .resolve
        bmrpromise.then(function(data) {
            console.log(data);
            if(data.success){
                layer.open({
                    title: '提示',
                    icon: 6,
                    content: '<p style="color: #333333;font-size: 16px;">恭喜<span style="color: #ffb80a;">'+$scope.dqUser.name+'</span>获得<span style="color: #ff6248;">'+awardsId.name+':'+awardsId.prizeName+'</span></p>'
                });
            }
            // $scope.xinxi=data.obj;
        }, function(data) {
            //console.log('2:'+data);
        });

        // console.log($scope.xinxi[index])

        //选择是否要从数组中删除选中后的信息
        if(del && del==1){
            //在数组中删除该数据  -------------  防止重复获取
            $scope.xinxi.splice(index,1);
        }
    }

    $document.bind("keyup", function(event) {
        if (event.keyCode == 32) {
            $scope.strat(2);
        }
    })

})

//mian4
myApp.controller('main4ViewCtrls', function($scope,$http,myFactoryHttp,$timeout,$interval) {


    var getzjelist= function(){
        //获取中奖人名单
        var bmrurls=B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action';
        var bmrjsons={
            "command": "514",
            "content": {
                "activityId": "a043ae341a494700a27a3203a1b319db"
            }
        };
        // 同步调用，获得承诺接口
        var bmrpromise = myFactoryHttp.query(bmrurls,bmrjsons);
        // 调用承诺API获取数据 .resolve
        bmrpromise.then(function(data) {
            $scope.zjrlist=data.obj;
            // console.log('1:'+data);
        }, function(data) {
            // console.log('2:'+data);
        });

        // $interval(getzjelist,2000)
    }
    getzjelist();

    $scope.$on('ngList', function() {
        $(function(){
            $('.list').liMarquee({
                direction: 'up',
                runshort: false,
                hoverstop:false,
                scrollamount: 25
            });
        });
    });
})
//on-finish-render="ngRepeatFinished" 自定义指令----中奖人列表加载完毕
myApp.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngList');
                });
            }
        }
    }
});



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


