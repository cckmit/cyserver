/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('skillViewCtrls', function($scope,$http,$stateParams) {
    $scope.xuename=true;
    $scope.deshow=false;
    $scope.xueweilist=[
        { title : '一般',id:'1'},
        { title : '良好',id:'2'},
        { title : '熟悉',id:'3'},
        { title : '精通',id:'4'}
    ];

    $scope.destate={};

    $scope.selectValue = function(destate){
        console.log(destate);
        $scope.xuename=false;
    }



})

