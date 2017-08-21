/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('workstatusViewCtrls', function($scope,$http,$stateParams) {

    $scope.aaa=true;

    $scope.zhistate = [
        {
            title:'已离职，可立即上岗',
            id:'1'
        },
        {
            title:'在职，正在考虑换个环境',
            id:'2'
        },
        {
            title:'在职，暂无考虑跳槽',
            id:'3'
        },
        {
            title:'在校学生',
            id:'4'
        }
    ];


    $scope.deshow=false;

    $scope.selectValue=function(destate){
        console.log(destate);
        $scope.aaa=false;
    }

})