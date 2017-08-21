/**
 * Created by mcz on 2017/6/2.
 */

myApp.controller('yaoyueViewCtrls', function($scope,$http,$stateParams) {
    $scope.dq_nav='1'
    $scope.chkItem=function(index){
        $scope.dq_nav=index;
    }
})