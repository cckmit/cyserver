/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('studypathCtrls', function($scope,$http,$stateParams,$state) {
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

    $scope.education='';

    $scope.selectValue = function(education){
        console.log(education);
        $scope.xuename=false;
        $scope.education=education;
    }

    $scope.rx_wenzi=true;
    $scope.by_wenzi=true;


    $scope.startDateVar = true;
    $scope.endDateVar = true;
    $scope.delVar = true;
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



    //根据是否传送id, 判断是编辑还是新增
    var id = $stateParams.id;
    var actionVar; //定义"增",删 改 查
    if(id!=''){
        //编辑
        //隐藏"删除"按钮
        $scope.delVar = false;

        showInfo($scope,$http,$stateParams,$state,id);//展示状态中可以编辑

        //点击"入学时间", 编辑时间
        $scope.editStartDate = function(){
            $scope.startDateVar = false;
        }

        //点击"毕业时间", 编辑时间
        $scope.editEndDate = function(){
            $scope.endDateVar = false;
        }

        //修改保存
        actionVar = 2;

    }else{
        //新增
        //隐藏"删除"按钮
        $scope.delVar = false;

        //新增保存
        actionVar = 1;
    }



    //点击"保存"
    $scope.saveStudyPath = function(){
        if(checkPathForm($scope)){
            B.ready(function () {
                submitPathForm($scope,$http,$stateParams,$state,actionVar,id);
            })

        }
    }


})

//校验
function checkPathForm($scope){
    //testing :校验
    if(!$scope.schoolName || $scope.schoolName==''){
        layer.msg('请填写学校名称');
        return false;
    }
    if(!$scope.education || $scope.education==''){
        layer.msg('请填写学历/学位');
        return false;
    }
    if(!$scope.profession || $scope.profession==''){
        layer.msg('请填写专业名称');
        return false;
    }

    //对时间的校验
    if(!$scope.rxdate || $scope.rxdate==''){
        layer.msg('请填写入学时间');
        return false;
    }
    if(!$scope.bydate || $scope.bydate==''){
        layer.msg('请填写毕业时间');
        return false;
    }

    //入学时间<毕业时间
    if($scope.rxdate >=  $scope.bydate){
        layer.msg('入学时间应小于毕业时间');
        return false;
    }

    return true;

}


//显示信息
function showInfo($scope,$http,$stateParams,$state,id){

    B.ready(function () {

        //设置提交的json值
        var jsonParam = {"command":"282","content":{ "type": 1, "action": 3,"data":{}}};

        jsonParam.content.data.id = id;


        // console.log(JSON.stringify(jsonParam));


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
                $scope.pathObj = response.data.obj;

                //展示状态时,input不可编辑
                // $('.school_input').attr("disabled","disabled");
                $('.school_input').css("background","none");

                //隐藏提示信息
                $scope.rx_wenzi = false;
                $scope.by_wenzi = false;

                //展示项目
                $scope.schoolName = $scope.pathObj.schoolName;
                $scope.education = $scope.pathObj.education;
                $scope.profession = $scope.pathObj.profession;

                $scope.rxdate=new Date($scope.pathObj.startDate);
                $scope.bydate = new Date($scope.pathObj.endDate);



            }


        }, function errorCallback(response) {

        });

    })

}


//编辑保存, 增加保存,提交表单
function submitPathForm($scope,$http,$stateParams,$state, actionVar,id){

    console.log(actionVar);

    //设置提交的json值
    var jsonParam = {"command":"282","content":{ "type":{},"action":{},"data":{}}};

    jsonParam.content.type = 1;
    jsonParam.content.action = actionVar;
    $scope.resumeBaseId = "072c78dfd69344e5a1951732725b74ef";
    jsonParam.content.data.resumeBaseId = $scope.resumeBaseId;
    jsonParam.content.data.id = id;
    jsonParam.content.data.schoolName = $scope.schoolName;//学校名称
    jsonParam.content.data.education = $scope.education; //学历/学位
    jsonParam.content.data.profession = $scope.profession; //专业

    var startDate = JSON.stringify($scope.rxdate).substring(1,11);
    var endDate = JSON.stringify($scope.bydate).substring(1,11);

     jsonParam.content.data.startDate = startDate;
     jsonParam.content.data.endDate = endDate;

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
                $state.go('onlineresume');
            });
        }


    }, function errorCallback(response) {

    });

}






