/**
 * Created by mcz on 2017/6/2.
 */

myApp.controller('onlineresumeViewCtrls', function($scope,$http,$stateParams,$window) {

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

    loadResumeInfo($scope,$http,$stateParams);

    //添加个人评价,文字内容计数
    /*$scope.userPingjia="";
    $scope.textalength=$scope.userPingjia.length;
    $scope.teatarea=function(){
        $scope.userPingjia=$scope.userPingjia.substring(0,100);
        $scope.textalength=$scope.userPingjia.length;
    }*/



    //删除教育经历
    $scope.delPath = function(id){

            //设置提交的json值
            var jsonParam = {"command":"282","content":{ "type": 1, "action": 4,"data":{}}};

            jsonParam.content.data.id = id;


            console.log(JSON.stringify(jsonParam));


            $http({
                method: 'POST',
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data:{
                    jsonStr:JSON.stringify(jsonParam)
                },
                dataType: 'json'
            }).then(function successCallback(response) {
                console.log(response.data);

                if(response.data.success){
                    layer.msg(response.data.msg, {
                        icon: 1,
                        time: 2000
                    }, function(){
                        //刷新
                        $window.location.reload();
                    });

                }


            }, function errorCallback(response) {

            });

        }

})


//加载简历信息
function loadResumeInfo($scope,$http,$stateParams){
    // console.log('{"command": "283","content": {"accountNum": "'+accountNum+'"}}')
    B.ready(function () {

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

                //简历id
                $scope.resumeId=$scope.resumeObj.id;

                //教育经历列表
                $scope.eduList = $scope.resumeObj.resumeEducations;

                $scope.workExpList = $scope.resumeObj.resumeWorkExperiences;
                $scope.projectExpList = $scope.resumeObj.resumeProjectExperiences;
                $scope.skillList = $scope.resumeObj.resumeSkills;
                $scope.awardList = $scope.resumeObj.resumeRewardAtSchools;
                $scope.certList = $scope.resumeObj.resumeCertificates;




            }

        }, function errorCallback(response) {

        });

    })

}