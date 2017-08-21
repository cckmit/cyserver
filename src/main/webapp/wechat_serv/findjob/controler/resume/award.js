myApp.controller('awardViewCtrls', function($scope,$http,$stateParams) {
    $scope.xueweilist=[
        { title : '一等奖'},
        { title : '二等奖'},
        { title : '三等奖'},
        { title : '优秀奖'},
        { title : '国家励志奖学金'},
        { title : '校友奖学金'},
        { title : '进步奖'}
    ];


    $scope.xuewei=false;
    $scope.xuewei_sy=true;
    $scope.rx_wenzi=true;


    //点击选择学位
    $scope.xueweiChange = function(){
        $scope.xuewei=true;
    }

    //点击确定
    $scope.queding = function(){
        $scope.xuewei=false;
        $scope.qdxuewei=$('.dq_xuewei').text();
        if($scope.qdxuewei && $scope.qdxuewei!=''){
            $scope.xuewei_sy=false;
        }else{
            $scope.xuewei_sy=true;
        }
    }


    //入学时间
    // var ruxuedate="2014-12";
    var ruxuedate="";
    if(ruxuedate!=''){
        $scope.rxdate=new Date(ruxuedate);
    }
    $scope.ruxueDate = function(){
        if($scope.rxdate!=''){
            $scope.rx_wenzi=false;
        }else{
            $scope.rx_wenzi=true;
        }
    }
})