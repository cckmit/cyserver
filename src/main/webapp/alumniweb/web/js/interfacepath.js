/**
 * Created by mcz on 2016/11/18.
 */
// 接口请求
var interface = {} ;

// 1. public.js加载导航
// 1.1 加载导航
interface.loaderNav = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "235",content: {}}
}  ;
// 1.2 登录
interface.loginIn = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "10",content: { "accountNum": "accountNum", "password": "password"} }
}  ;

// 2. 首页
// 2.1 顶部轮播图
interface.loadExampleImage = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "236",content: {"category": "37", "topnews":"100"}}
}  ;
// 2.2 通知公告
interface.loadTongZhi = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "236",content: {"page": "1","rows": "5","category": "37"} }
}  ;
// 2.3 单个新闻栏目下的新闻
interface.loadNewsBoxData = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "236",content: {"page": "1","rows": "5","category": "37"} }
}  ;
// 2.4 初始化“视界”轮播新闻
interface.loadImageView = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "236",content: {"category": "38"}}
}  ;
// 2.5 新闻栏目列表
interface.loadNewsBoxList = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "239",content: {"channel": "20","isNavigation": "1","level": "1","isMain":"0"}}
}  ;
// 2.6 首页登录窗口
interface.loginWindw = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "10",content: { "accountNum": "'+accountNum+'", "password": "'+password+'"} }
}  ;

// 3. 列表页面
// 3.1 刷新左侧导航
interface.loadNewsList = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "236",content: {page: "page",rows: "pageSize",category: "id"}}
}  ;

// 4. 内容详情页面
// 4.1 内容详情
interface.contentDet = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "237",content: {"id":"newsId"}}
}  ;

// 5. 校友组织列表页面
// 5.1 获取分会信息
interface.alumniNews = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{ command: "28",content: { "type": "-1" } }
}  ;

// 6. 总会详情页面/分会详情页面
// 6.1 获取分会信息
interface.zongalumniNews = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "240",content: {"alumniId":"alumniId"}}
}  ;

// 7. 服务,捐赠页面
// 7.1 在线捐赠列表页donateList
interface.donatelist = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "221",content: {"page": "page","rows": "6","category": "26"}}
}  ;
// 加载子栏目下的新闻列表
interface.donatelistxin = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "73",content: {"page": "page","rows": "6","category": "26"}}
}  ;
//捐赠项目
interface.donatelistxiangmu = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "71",content: {"page": "page","rows": "6","category": "26"}}
}  ;
// 7.2 捐赠项目详情页
interface.donatelistcontent = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "237",content: {"id":"newsId "}}
}  ;
// 7.5 捐赠项目详情可捐赠页
interface.donatelistcontentJz = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "228",content: {"id":"projectId"}}
}  ;
// 7.4 加载订单详情
interface.donatelistcontentShop = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "229",content: {"id": "'+donationId+'"}}
}  ;

// 8. 个人信息页面
// 8.1 获取个人信息
interface.userpersonal = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{"command": "27","content": {"accountNum": "accountNum"}}
}  ;
// 8.2 修改个人信息
interface.userpersonalchange = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{"command":"23","content": {"accountNum": "accountNum","address": "gCity","email": "gEmail","hobby": "gHobby","password": "password","profession": "gProf","workUtil": "gWorkUtil","position": "gPosition","birthday": "gBirthday","sign":"gSign"}},
}  ;



// 9. 学习经历页面
// 9.1 获取学习经历 --- 可用学习经历判断是否已经被认证
interface.userstudyPath = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData:{command: "101",content: {"accountNum": "accountNum"}}
}  ;
// 9.2 获取班级成员
interface.userClassMates = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: { command: "242", content: {   "userId": " id "  } }
} ;

// 10. 班级认证页面
// 10.1 获取班级数据
interface.userstudyClass = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "301",content: {"accountNum":"accountNum'"}}
}  ;
// 10.1 9选3认证
interface.userstudyChose = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "218",content: {"strStudyPathId": "strStudyPathId"} }
}  ;
// 10.1 提交9选3用户认证
interface.userstudyChoseComit = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "15",content: {"password": "password","name": "name","phoneNum": "phoneNum","classmates": ["rz_name1","rz_name2","rz_name3"],"baseInfoId": ["strStudyPathId"]}}
}  ;
// 10.2 学号认证
interface.userstudyCardComit = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "202",content: {"phoneNum": "phoneNum","password": "password","studentnumber": "studentnumber","name": "name","baseInfoIdx": "strStudyPathId"}}
}  ;
// 10.3 身份证认证
interface.userstudyCardShenfenComit = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "203",content: {"phoneNum": "phoneNum","password": "password","card": "card","name": "name","baseInfoIdx": "strStudyPathId"}}
}  ;
// 10.3 邀请码认证
interface.userstudyCardYaoqingComit = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "205",content: {"phoneNum": "phoneNum","password": "password","invitecode": "invitecode","name": "name","baseInfoIdx": "strStudyPathId"}}
}  ;
// 10.4 获取学校、学院、年级、班级
interface.userstudyPathGet = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "211",content: {"level": "level","schoolId": "schoolId","collegeId": "collegeId","gradeId": "gradeId"}}
}  ;
// 10.5 申请加入班级
interface.userstudyFor = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "212",content: {"schoolName": "schoolN","departName": "departN","gradeName": "gradeN","className": "classN","accountNum": "accountNum"}}
}  ;

// 11. 所属分会
// 11.1 查询地方、行业校友会
interface.userBranchDiHang = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "28",content: {"type": "typeVal","userId": "accountNum","isJoin": "isJoinVal","status": "statusVal"}}
}  ;
// 11.2 退出分会接口
interface.userBranchOut = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "209",content: {"accountNum": "accountNum","password": "password","alumniIds": "fhId"}}
}  ;
// 11.3 查询地方行业分会
interface.userBranchDifang = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "28",content: {"type": "uId","userId": "accountNum","isJoin": "0"}}
}  ;
// 11.4 点击申请加入"按钮
interface.userBranchApply = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "207",content: {"accountNum": "accountNum","password": "password","alumniIds": "chk_value"}}
}  ;

// 12. 我的好友页面
// 12.1 获取好友列表
interface.userFriendsList = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "241",content: {"userId": "accountNum"}}
}  ;
// 12.2 添加好友(同意or拒绝)
interface.userFriendsverifing = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "43",content: {"userId": "accountNum","contactId": "contactId", "status": "vfv",  "applicantFlag":"0"}}
}  ;
// 12.3 搜索好友
interface.userFriendsFind = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "42",content: {"currUserId": "accountNum","name": "u_name","sex": "u_sex","age": "","professionId": "","cityId": ""}}
}  ;
// 12.4 添加好友(申请好友)
interface.userFriendsFor = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "43",content: {"userId": "accountNum","contactId": "contactId","applicantFlag":"1"}}
}  ;
// 12.5 删除好友
interface.userFriendsClear = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "44",content: {"userId": "accountNum","contactId": "contactId"}}
}  ;

// 13. 我的捐赠页面
// 13.1 获取我的捐赠
interface.userMyDonation = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "72",content: {"accountNum": "accountNum" ,"page":"pageCurrent","rows":"5"} }
}  ;

// 14. 修改密码页面
// 14.1 修改密码
interface.userChangePwd = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "16",content: {"phoneNum":"phoneNum",  "password": "txtNewPwd","checkCode":"txtCode" }}
}  ;
// 14.1 获取验证码
interface.userCodeGet = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "3",content: {"phoneNum":"phoneNum", "secretKey": "getRegisterCode123"}}
}  ;

// 15. 注册页面
// 15.1 用户注册
interface.userRegister = {
    url:'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "4",content: {"phoneNum":"txtMob",  "password":"txtPass","name": "txtName","checkCode":"txtCode" }}
}  ;


// 分会页面---------------------------------------------------------------------------
// 1. 分会public.js加载导航
// 1.1 加载导航
interface.FenhuiLoaderNav = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "239",content: {"channel": "20","isNavigation": "1","level": "1","deptId":"deptId"}}
}  ;
// 1.2 查询分会详情接口（当前分会）
interface.FenhuiDangQian = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "240",content: {"alumniId": "deptId"}}
}  ;

// 2. 分会首页
// 2.1 加载导航
interface.FenhuiLoaderNav = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "239",content: {"channel": "20","isNavigation": "1","level": "1","deptId":"deptId"}}
}  ;
// 2.2 初始化幻灯片
interface.FenhuiSlide = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "236",content: {"page": "1","rows": "5","category": "37"}}
}  ;
// 2.2 初始化“视界”轮播新闻
interface.FenhuiShijieSlide = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "236",content: {"category": "37"}}
}  ;
// 2.2 初始化栏目数据
interface.FenhuiColumn = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "236",content: {"page": "1","rows": "5","category": "id"} }
}  ;
// 2.3 通知栏
interface.FenhuiNotice = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "236",content: {"page": "1","rows": "5","category": "37"} }
}  ;

// 3. 分会列表页
// 3.1 加载栏目
interface.FenhuiList = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "239",content: {"channel": "20","isNavigation": "0","deptId":"deptId"}}
}  ;
// 3.1 列表
interface.FenhuiListLiebiao = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "236",content: {"page": "page","rows": "pageSize","category": "id"}}
}  ;
// 3. 分会详情页
// 3.1 加载栏目
interface.FenhuiContent = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "239",content: {"channel": "20","isNavigation": "1","level": "1","deptId":"deptId"}}
}  ;
// 3.2 当前栏目位置（面包屑）
interface.FenhuiContentPosition = {
    url: '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
    jsonData: {command: "237",content: {"id":"newsId"}}
}  ;