/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('workexpCtrls', function($scope,$http,$stateParams,$state) {

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



    //传递参数, 工作经验id
    $scope.id= $stateParams.id;
    // console.log("11111"+$scope.id);

    //简历id resumeId
    $scope.resumeId= $stateParams.resumeId;
    console.log($scope.resumeId + $scope.id);
    var actionVar;

    if(!$scope.id || $scope.id==''){
        //add
        actionVar = 1;

    }else{
        //加载工作/实习经历
        loadWorkExp($scope,$http,$stateParams);
        //edit
        actionVar = 2;
        // loadWorkExp($scope,$http);
    }


    //点击"保存",提交表单
    $scope.saveWorkExpForm = function(){
        if(checkWorkExpForm($scope)){
            B.ready(function () {
                submitWorkExpForm($scope,$http,$state,actionVar);
            })

        }
    }




});

//加载工作/实习经历
function loadWorkExp($scope,$http){


    B.ready(function () {

        //设置提交的json值
        var jsonParam = {"command":"282","content":{ "type": 2, "action": 3,"data":{}}};

        jsonParam.content.data.id = $scope.id;


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

                $scope.workExp = response.data.obj;


                //id
                $scope.id = $scope.workExp.id;

                $('.company_input').css("background","none");

                //隐藏提示信息
                $scope.rx_wenzi = false;
                $scope.by_wenzi = false;

                //展示项目
                $scope.companyName = $scope.workExp.companyName;
                $scope.rxdate=new Date($scope.workExp.startDate);
                $scope.bydate = new Date($scope.workExp.endDate);
                $scope.positionName = $scope.workExp.positionName;
                $scope.desc = $scope.workExp.desc;


            }


        }, function errorCallback(response) {

        });

    })

}

//验证工作经历表单
function checkWorkExpForm($scope){
    if(!$scope.companyName ||$scope.companyName=='' ){
        layer.msg("请输入公司名称.")
        return false;
    }
    return true;

}

//验证工作描述
function checkWorkDescForm($scope){
    if(!$scope.desc || $scope.desc==''){
        layer.msg("请输入工作／实习描述或输入'无'.");
        return false;
    }
    return true;
}

function submitWorkExpForm($scope,$http,$state,actionVar){

    //设置提交的json值
    var jsonParam = {"command":"282","content":{ "type":{},"action":{},"data":{}}};

    jsonParam.content.type = 2;
    jsonParam.content.action = actionVar;
    jsonParam.content.data.resumeBaseId = $scope.resumeId;
    jsonParam.content.data.id = $scope.id;
    jsonParam.content.data.companyName = $scope.companyName;
    jsonParam.content.data.positionName = $scope.positionName;
    jsonParam.content.data.desc = $scope.desc;

    if($scope.rxdate){
        var startDate = JSON.stringify($scope.rxdate).substring(1,11);
    }
    if($scope.bydate){
        var endDate = JSON.stringify($scope.bydate).substring(1,11);
    }



    jsonParam.content.data.startDate = startDate;
    jsonParam.content.data.endDate = endDate;

    console.log(JSON.stringify(jsonParam));

    return;

    $http({
        method: 'POST',
        url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
        data:{
            jsonStr:JSON.stringify(jsonParam)
        },
        dataType: 'json'
    }).then(function successCallback(response) {
        // console.log(response.data);

        if(response.data.success){
            layer.msg(response.data.msg, {
                icon: 1,
                time: 2000
            }, function(){
                // $state.go('onlineresume');
                $state.go('workexp',{'id':  $scope.id});
            });


        }


    }, function errorCallback(response) {

    });


}


//工作描述
myApp.controller('workdescCtrls', function($scope,$http,$stateParams,$state) {

    $scope.desc = $stateParams.desc;
    $scope.id = $stateParams.id;

    console.log($scope.id)

    //计算字数
    if($scope.desc.length>0){
        $scope.count = 2000-($scope.desc.length);
        $scope.txtCount = function () {
            $scope.count = 2000-($scope.desc.length);
        }

    }else {
        $scope.count = 2000;
        $scope.txtCount = function () {
            $scope.count = 2000-($scope.desc.length);
        }
    }


    //保存文字内容
    $scope.saveWorkDesc = function(){
        if(checkWorkDescForm($scope)){
            B.ready(function () {
                submitWorkExpForm($scope,$http,$state,2);
            })

        }

    }


});

