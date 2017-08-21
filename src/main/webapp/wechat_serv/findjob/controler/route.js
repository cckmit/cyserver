

var httpPost = function($httpProvider) {
    /*******************************************
     说明：$http的post提交时，纠正消息体
     ********************************************/
    // Use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    /*
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function(obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
        for (name in obj) {
            value = obj[name];
            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value instanceof Object) {
                for (subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value !== undefined && value !== null)
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
        return query.length ? query.substr(0, query.length - 1) : query;
    };
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [
        function(data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
        }
    ];
};
//定义主模块并注入依赖

var myApp=angular.module("myApp", ['ionic','ngImgCrop'],httpPost);

myApp.config(function($ionicConfigProvider) {
    $ionicConfigProvider.views.maxCache(5);

    // note that you can also chain configs
    $ionicConfigProvider.backButton.text('Go Back').icon('ion-chevron-left');
});

myApp.config(['$stateProvider','$ionicConfigProvider', '$urlRouterProvider', function($stateProvider, $ionicConfigProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/main1');
    $stateProvider

        //招聘主页面 1 找工作
        .state('main1', {
            url: '/main1',
            templateUrl: "view/main/main_list/main1.html",
            controller: 'main1ViewCtrls'
        })
        //招聘主页面 2 我的
        .state('main2', {
            url: '/main2',
            templateUrl: "view/main/main_list/main2.html",
            controller: 'main2ViewCtrls'
        })


        //职位详情
        .state('zhiweicenter', {
            url: '/zhiweicenter/:id',
            templateUrl: "view/my_resumes/resume_detail.html",
            controller: 'zhiweicenterViewCtrls'
        })
        //职位收藏
        .state('zhiweishoucang', {
            url: '/zhiweishoucang',
            templateUrl: "view/my_resumes/resume_collect.html",
            controller: 'zhiweisoucangViewCtrls'
        })

        //HR邀约
        .state('yaoyue', {
            url: '/yaoyue',
            templateUrl: "view/my_resumes/invite_list.html",
            controller: 'yaoyueViewCtrls'
        })


        //在线简历
        .state('onlineresume', {
            url: '/onlineresume',
            templateUrl: "view/my_resumes/resume_online_01.html",
            controller: 'onlineresumeViewCtrls'
        })
        //简历预览
        .state('resume', {
            url: '/resume',
            templateUrl: "view/my_resumes/resume_online_03.html",
            controller: 'resumeViewCtrls'
        })
        //简历状态
        .state('jianlizhaungtai', {
            url: '/jianlizhaungtai',
            templateUrl: "view/my_resumes/resume_list.html",
            controller: 'resumezhuangtaiViewCtrls'
        })
        //管理求职意向
        .state('workstatus', {
            url: '/workstatus',
            templateUrl: "view/work_will/workstatus_sel.html",
            controller: 'workstatusViewCtrls'
        })
        //期望职位
        .state('workwill', {
            url: '/workwill',
            templateUrl: "view/work_will/will_sel.html",
            controller: 'workwillViewCtrls'
        })

        //添加教育经历
        .state('studypath', {
            url: '/studypath/:id',
            templateUrl: "view/resume_add/studypath.html",
            controller: 'studypathCtrls'
        })

        //添加工作/实习经历
        .state('workexp', {
            url: '/workexp/:resumeId:id',
            templateUrl: "view/resume_add/workexp.html",
            controller: 'workexpCtrls'
        })
        //编辑工作描述
        .state('workdesc', {
            url: '/work_desc/:id:desc',
            templateUrl: "view/resume_add/work_desc.html",
            controller: 'workdescCtrls'
        })

        //添加项目经验
        .state('projectexp', {
            url: '/projectexp/:id',
            templateUrl: "view/resume_add/projectexp.html",
            controller: 'projectexpCtrls'
        })



        //添加在校学习情况
        .state('award', {
            url: '/award/:id',
            templateUrl: "view/resume_add/award.html",
            controller: 'awardViewCtrls'
        })

        //添加专业技能
        .state('skill', {
            url: '/skill/:id',
            templateUrl: "view/resume_add/skill.html",
            controller: 'skillViewCtrls'
        })

        //添加证书
        .state('cert', {
            url: '/cert',
            templateUrl: "view/resume_add/cert.html",
            controller: 'certViewCtrls'
        })







        //个人信息 保存前

        //传递id, 保存
        .state('users', {
            url: '/users/:id:txt',
            templateUrl: "view/personal/personal_account.html",
            controller: 'usersViewCtrls'
        })

        //个人信息 保存后
        .state('users_cun', {
            url: '/users_cun',
            templateUrl: "view/personal/personal_infor.html",
            controller: 'users_cunViewCtrls'
        })

        //编辑个人爱好
        .state('users_hobby', {
            url: '/users_hobby/:id:txt',
            templateUrl: "view/personal/personal_hobby.html",
            controller: 'usershobbyCtrls'
        })
        //编辑个人评论
        .state('users_comment', {
            url: '/users_comment/:id:txt',
            templateUrl: "view/personal/personal_comment.html",
            controller: 'usersCommentCtrls'
        })



}]);




//滤镜： 判断图片是否有http或者https
myApp.filter('filter_pic', function () {
    return function (text) {
        if(text && text!=''){
            if(text.indexOf("http:") == 0 || text.indexOf("https:") == 0 ){
                return text;
            }else{
                // text = B.serverUrl+text;
                text = B.imageServiceHttp+text;
                return text;
            }
        }
    };
});

//截断超长字符串的过滤器
myApp.filter("truncate", function(){
    return function(text, length){
        if (text) {
            var ellipsis = text.length > length ? "..." : "";
            return text.slice(0, length) + ellipsis;
        };
        return text;
    }
});
