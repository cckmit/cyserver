/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('projectexpCtrls', function($scope,$http,$stateParams) {

    $scope.rx_wenzi=true;
    $scope.by_wenzi=true;

    //开始时间
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

    //结束时间
    // var biyedate="2015-07";
    var biyedate="";
    if(biyedate!=''){
        $scope.bydate=new Date(biyedate);
    }
    $scope.biyeDate = function(){
        if($scope.bydate!=''){
            $scope.by_wenzi=false;
        }else{
            $scope.by_wenzi=true;
        }
    }

})

