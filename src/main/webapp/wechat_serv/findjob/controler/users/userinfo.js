/**
 * Created by mcz on 2017/6/2.
 */

//编辑个人简历
myApp.controller('usersViewCtrls', function($scope,$http,$stateParams,$state) {

    //根据账号获取个人信息
    //初始化账号
    var openId = localStorage.getItem("openId");

    openId='123'; //假数据

    accountNum = '420';
    $scope.accountNum = accountNum;

    /*if(openId && openId != "null") {
     // 微信端
     accountNum = localStorage.getItem("accountNum");
     }else{
     alert("尚未登录,请先登录.");
     }*/



    //简历id
    $scope.id = $stateParams.id;

    //格式化生日日期
    $scope.tipVar=false;

    var birthDateStr="";
    if(birthDateStr!=''){
        $scope.birthDate=new Date(birthDateStr);

    }
    $scope.editDate = function(){
        if($scope.birthDate!=''){

            $scope.tipVar=false;
        }else{
            $scope.tipVar=true;
        }
    }


    //根据账号获取用户信息
    $scope.birthInput = true;
    $scope.birthShow = true;

    $scope.iconVar = true; //性别"向右"箭头


    //编辑爱好后,传"爱好"/评论 到personal_account
    var txt=$stateParams.txt;
    if(txt!=''){
        $scope.hobbyTxt = txt;
        $scope.selCommentTxt = txt;
    }


    //点击"出生日期",编辑"出生日期"
    $scope.birthSel = function(){
        $scope.birthInput = true;//隐藏原有信息, 显示编辑状态
        $scope.birthShow = false;//隐藏原有信息, 显示编辑状态
        $scope.iconVar = false;
    };

    $scope.pName = '';
    $scope.cName = '';

    //点击选择居住城市
    $scope.resideSel = function(){
        $scope.iconVar = false;
        $scope.resideVar = true;

        //test: 初始化弹框的值
        if($scope.resideCity && $scope.resideCity!=''){
            $scope.pName = $scope.province;
            $scope.cName = $scope.city;
        }

    };


    //点击选择户口所在地
    $scope.originSel = function(){
        $scope.iconVar = false;
        $scope.hukouVar = true;

        if($scope.originCity && $scope.originCity!=''){
            $scope.pName_hk = $scope.province_hk;
            $scope.cName_hk = $scope.city_hk;
        }

    } ;

    //加载城市列表
    loadLocationList($scope,$http,$stateParams,$state);


    //居住:点击省, 获取城市列表
    $scope.getCityList= function(item){

        var city0 ='';
        //居住城市:点击样式
        $scope.index1 = item.id;
        $scope.pName = item.text;
        $scope.focusIndex1 = $scope.index1;

        //判断城市列表是否为空
        if(item.children != null && item.children.length > 0) {
            $scope.cityList = item.children;

            //没有点击城市, 默认获取城市列表中的第一个
            city0 = $scope.cityList[0];

            $scope.city0 = city0 ;

        }
        //城市列表为空
        $scope.getCity(city0);

    } ;

    //居住:点击城市,获取城市
    $scope.getCity = function(item){
        if (item != null) {

            //居住城市:点击样式
            $scope.index2 = item.id;
            $scope.cName = item.text;
            $scope.focusIndex2 = $scope.index2;

        }

    };

    //居住:点击城市弹窗的"确定", 获取所选城市 , 并且关闭confirm
    $scope.confirmReside=function(){

        //将弹窗上的"city"返回给主页面
        if($scope.pName != '' && $scope.cName!=''){
            $scope.resideCity = $scope.pName+"-"+$scope.cName;
        }

        //关闭弹窗
        $scope.resideVar=false;


    };


    //户口所在地:点击省, 获取城市列表
    $scope.getCityHkList= function(item){

        var city0_hk ='';

        //户口所在地:点击样式
        $scope.index1 = item.id;
        $scope.pName_hk = item.text;
        $scope.focusIndex1 = $scope.index1;

        //判断城市列表是否为空
        if(item.children != null && item.children.length > 0) {
            $scope.cityHkList = item.children;

            //没有点击城市, 默认获取城市列表中的第一个
            city0_hk = $scope.cityHkList[0];
            $scope.city0_hk = city0_hk ;


        }

        //城市列表为空
        $scope.getCityHk(city0_hk);

    } ;

    //户口所在地:点击城市,获取城市
    $scope.getCityHk = function(item){
        if (item != null) {

            //户口所在地:点击样式
            $scope.index2 = item.id;
            $scope.cName_hk = item.text;
            $scope.focusIndex2 = $scope.index2;
        }

    };

    //户口所在地:点击城市弹窗的"确定", 获取所选城市 , 并且关闭confirm
    $scope.confirmHukou=function(){

        //将弹窗上的"city"返回给主页面
        if($scope.pName_hk!='' && $scope.cName_hk!=''){
            $scope.originCity = $scope.pName_hk+"-"+$scope.cName_hk;
        }


        //关闭弹窗
        $scope.hukouVar=false;


    };



    //从简历信息中获得个人信息(624)
    loadUserInfo($scope,$http,$stateParams,$state); //展示状态中可以编辑


    $scope.touxiangbox=false;

    //上传头像
    $scope.myImage='';
    $scope.myCroppedImage='';

    $scope.yuantu='';
    $scope.fileName='';
    $scope.fileExt='';

    //插件的自定义函数
    var handleFileSelect=function(evt) {
        $scope.touxiangbox=true;

        //console.log(evt)
        var file=evt.currentTarget.files[0];

        //获取文件名与尾缀
        $scope.fileExt=evt.currentTarget.files[0].name;
        $scope.fileName = $scope.fileExt.substring(0, $scope.fileExt.lastIndexOf("."));
        $scope.fileName = $scope.fileName.substr($scope.fileName.lastIndexOf("\\") + 1, $scope.fileName.length);
        $scope.fileExt = $scope.fileExt.substr($scope.fileExt.lastIndexOf("."), $scope.fileExt.length);
        $scope.fileExt = $scope.fileExt.toLowerCase();
        if($scope.fileExt == '.gif' || $scope.fileExt == '.jpg' || $scope.fileExt == '.png' || $scope.fileExt == '.bmp' || $scope.fileExt == '.jpeg') {} else {
            layer.open({
                title: '提示'
                ,content: '请上传正确的图片文件'
            });
            return false;
        }

        var reader = new FileReader();
        reader.onload = function (evt) {
            $scope.$apply(function($scope){
                $scope.myImage=evt.target.result;
            });
        };
        reader.readAsDataURL(file);
    };
   
    angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);

    //点击确认上传时
    $scope.picshangchuan = function(){
        if(!$scope.fileName || $scope.fileName==''){
            layer.open({
                title: '提示'
                ,content: '请上传图片'
            });
            return false;
        }
        //alert($scope.fileName+$scope.fileExt);

        $scope.touxiangbox=false;

        B.ready(function() {
            var $Blob = getBlobBydataURL($scope.myCroppedImage,"image/png");
            $scope.file = $Blob;

            var uploadImgParam = {
                uploadFileBase64: $scope.myCroppedImage,
                uploadFileName: $scope.fileName+$scope.fileExt,
                jsonStr: '{"command": "6","content": {"accountNum": \"'+accountNum+'\"}}'
            };
            $http({
                method: 'POST',
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: uploadImgParam,
                dataType: 'json'
            }).then(function successCallback(response) {
                $scope.pic=response.data.obj;

                //alert(JSON.stringify(response));
            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });
        })
    }
    //点击取消
    $scope.picquxiao = function(){
        $scope.touxiangbox=false;
        $scope.myImage='';
        $scope.myCroppedImage='';

        $scope.yuantu='';
        $scope.fileName='';
        $scope.fileExt='';
    }




    //点击"保存"
    $scope.saveUserInfo = function(){
        if(checkForm($scope)){
            submitForm($scope,$http,$stateParams,$state);
        }

    }


});

//加载省市列表
function loadLocationList($scope,$http,$stateParams,$state) {

    B.ready(function () {
        $http({
            method: 'POST',
            url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "245","content":{"pid":"3.1","level":"2"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            // console.log(response.data.obj);

            if (response.data.success) {

                if(response.data.obj && response.data.obj.length > 0){
                    $scope.provinceList = response.data.obj;
                    //没有点击省, 默认获取第一个
                    $scope.province0 = $scope.provinceList[0];
                }else{
                    alert('没有数据');
                }

            }

        }, function errorCallback(response) {

        });

    })
}


//从简历信息中获取个人信息
function loadUserInfo($scope,$http,$stateParams){
    console.log('{"command": "283","content": {"accountNum": "'+accountNum+'"}}')

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


                //个人信息
                $scope.pic = $scope.resumeObj.headPic;
                $scope.name = $scope.resumeObj.name;
                $scope.sex = $scope.resumeObj.sex;

                if($scope.resumeObj.birthday){
                    $scope.birthDate = new Date($scope.resumeObj.birthday);
                }


                $scope.telephone = $scope.resumeObj.telephone;
                $scope.telephone = $scope.telephone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');

                $scope.email = $scope.resumeObj.email;
                $scope.email = $scope.email.replace(/(\d{2})\d{4}(\d{4})/, '$1****$2');

                //test city
                if($scope.resumeObj.city && $scope.resumeObj.city!=''){
                    $scope.province = $scope.resumeObj.city.split(' ')[0]; //北京 北京市
                    $scope.city = $scope.resumeObj.city.split(' ')[1]; //北京市
                    $scope.resideCity = $scope.province+"-"+$scope.city;
                }else{
                    // alert("居住城市为空")

                }

                if($scope.resumeObj.placeOrigin && $scope.resumeObj.placeOrigin!=''){
                    $scope.province_hk = $scope.resumeObj.placeOrigin.split(' ')[0];
                    $scope.city_hk = $scope.resumeObj.placeOrigin.split(' ')[1];
                    $scope.originCity = $scope.province_hk+"-"+$scope.city_hk;
                }else{
                    // alert("户口所在地为空")
                }


                $scope.hobbyTxt= $scope.resumeObj.hobby;
                $scope.commentTxt = $scope.resumeObj.selComment;

            }

        }, function errorCallback(response) {

        });

    })

};

//表单校验
function checkForm($scope){

    if(!$scope.birthDate || $scope.birthDate==''){
        layer.msg('请选择出生日期');
        return false;
    }

    if(!$scope.resideCity || $scope.resideCity==''){

        layer.msg('请填写居住城市');
        return false;
    }
    if(!$scope.originCity || $scope.originCity==''){
        layer.msg('请填写户口所在地');
        return false;
    }


    if(!$scope.email || $scope.email==''){
        layer.msg('请填写电子邮箱');
        return false;
    }
    //校验email格式
    if(!validateEmail($scope.email)){
        layer.msg('请填写正确的电子邮箱');
        return false;
    }

    if(!$scope.hobbyTxt || $scope.hobbyTxt==''){
        layer.msg('请填写兴趣爱好');
        return false;
    }
    if(!$scope.commentTxt || $scope.commentTxt==''){
        layer.msg('请填写个人评价');
        return false;
    }


    return true;

}

//爱好必填
function checkHobbyForm($scope){
    if(!$scope.hobbyTxt || $scope.hobbyTxt==''){
        layer.msg('请填写兴趣爱好或填写没有');
        return false;
    }
    return true;
}

//个人评价必填
function checkCommentForm($scope){

    if(!$scope.commentTxt || $scope.commentTxt==''){
        layer.msg('请填写个人评论或填写没有');
        return false;
    }
    return true;
}

//提交个人信息表单
function submitForm($scope,$http,$stateParams,$state){

    B.ready(function () {


    //设置提交的json值
    var jsonParam = {"command":"624","content":{ }};

    jsonParam.content.accountNum = $scope.accountNum;
    jsonParam.content.id = $scope.id;
    jsonParam.content.name = $scope.name;
    jsonParam.content.sex = $scope.sex;
    jsonParam.content.headPic = $scope.pic;

    //生日
    if($scope.birthDate){
        var mon = $scope.birthDate.getMonth() + 1;
        var day = $scope.birthDate.getDate();
        var year = $scope.birthDate.getFullYear();
        jsonParam.content.birthday = year + "-" + (mon<10?"0"+mon:mon) + "-" +(day<10?"0"+day:day);
    }

    if(($scope.pName && $scope.pName!='') && ($scope.cName && $scope.cName!='')){
        jsonParam.content.city = $scope.pName +" "+$scope.cName;
    }


    if(($scope.pName_hk && $scope.pName_hk!='') && ($scope.cName_hk && $scope.cName_hk!='')){
        jsonParam.content.placeOrigin = $scope.pName_hk +" "+$scope.cName_hk;
    }


    jsonParam.content.email = $scope.email; //邮件地址
    jsonParam.content.hobby =  $scope.hobbyTxt ; //爱好
    jsonParam.content.selComment =  $scope.commentTxt ; //个人评论


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
            layer.msg('提交成功', {
                icon: 1,
                time: 2000
            }, function(){
                $state.go("users",{'id':  $scope.id});
            });
        }


    }, function errorCallback(response) {

    });

    })

}



//填写个人爱好
myApp.controller('usershobbyCtrls', function($scope,$http,$stateParams,$state) {

    $scope.id = $stateParams.id;
    $scope.txt = $stateParams.txt;
    $scope.count = 100;

    countTxt($scope);

    $scope.saveHobby = function (text) {

        $scope.accountNum = '420';
        $scope.hobbyTxt = text;

        if(checkHobbyForm($scope)){
            submitForm($scope,$http,$stateParams,$state);
        }


    }

})

//填写个人评论
myApp.controller('usersCommentCtrls', function($scope,$http,$stateParams,$state) {

    //统计字数
    $scope.id = $stateParams.id;
    $scope.txt = $stateParams.txt;
    $scope.count = 100;

    countTxt($scope);

    $scope.saveComment = function (text) {

        $scope.accountNum = '420';
        $scope.commentTxt = text;
        //提交表单
        if(checkCommentForm($scope)){
            submitForm($scope,$http,$stateParams,$state);
        }


    }

})


//统计字数
function countTxt($scope){
    if($scope.txt==''){
        $scope.txtCount = function(){
            $scope.count = 100 - $scope.txt.length;
        }
    }else{
        $scope.count = 100 - $scope.txt.length;
        $scope.txtCount = function(){
            $scope.count = 100 - $scope.txt.length;
        }
    }
}

/**对Email的验证*/
function validateEmail(email) {
    var regEmail =  /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;

    return regEmail.test(email);
}



function getBlobBydataURL(dataURI,type){
    var binary = atob(dataURI.split(',')[1]);
    var array = [];
    for(var i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i));
    }
    return new Blob([new Uint8Array(array)], {type:type });
}