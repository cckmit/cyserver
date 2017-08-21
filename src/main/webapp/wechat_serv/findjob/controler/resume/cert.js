/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('certViewCtrls', function($scope,$http,$stateParams) {
    $scope.xuename=true;
    $scope.deshow=false;
    $scope.xueweilist=[
        { title : '高中',id:'1'},
        { title : '大专',id:'2'},
        { title : '本科',id:'3'},
        { title : '研究生',id:'4'},
        { title : '硕士',id:'5'},
        { title : '博士',id:'6'},
        { title : '专家',id:'7'}
    ];

    $scope.destate={};

    $scope.selectValue = function(destate){
        console.log(destate);
        $scope.xuename=false;
    }

    $scope.rx_wenzi=true;
    $scope.by_wenzi=true;



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

    //毕业时间
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

