/**
 * Created by mcz on 2017/6/2.
 */

myApp.controller('resumeViewCtrls', function($scope,$http,$stateParams) {

    //根据账号获取个人信息
    //初始化账号
    var openId = localStorage.getItem("openId");

    openId='123'; //假数据

    accountNum = '420';

    /*if(openId && openId != "null") {
     // 微信端
     accountNum = localStorage.getItem("accountNum");
     }else{
     alert("尚未登录,请先登录.");
     }*/

    showResumeInfo($scope,$http,$stateParams);
    
})

//加载简历信息
function showResumeInfo($scope,$http,$stateParams){
    $http({
        method: 'POST',
        url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
        data:{
            jsonStr:'{"command": "283","content": {"accountNum": "'+accountNum+'"}}'
        },
        dataType: 'json'
    }).then(function successCallback(response) {
        console.log(response.data.obj);

        if(response.data.success){
            $scope.resumeObj=response.data.obj;

            //个人信息

            //教育经历列表
            $scope.eduList = response.data.obj.resumeEducations;

            //工作/实习经验


        }

    }, function errorCallback(response) {

    });

}