/**
 * Created by jiangling on 16/05/2017.
 */

/**
 * Created by mcz on 2017/6/13.
 */

localStorage.setItem("accountNum",'498');
localStorage.setItem("openId",123);

var openId = localStorage.getItem("openId");
var accountNum = localStorage.getItem("accountNum");

if(openId && openId != "null") {
    // 微信端
    var shareData = {
        title: '校友企业',
        desc: '',
        link: location.href.split('?')[0].split('#')[0],
        imgUrl: ''
    };
    mkShareInfo(location.href.split('#')[0], shareData, 0);
}


//mian
myApp.controller('mainViewCtrls', function($scope,$state,$http) {
    $('title').html('校友企业');

    //搜索的字段
    $scope.name='';
    //类型的字段
    $scope.type='';

    //获取企业类型
    B.ready(function() {
        $http({
            method: 'POST',
            url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "267","content": {"dictTypeValue":"enterprise_type"}}'
            },
            // method: 'GET',
            // url: serverUrl+'/upms_dict?type=enterprise_type',
            dataType: 'json'
        }).then(function successCallback(response) {
            console.log(response.data.obj);
            console.log(response.data.obj.length);
            $scope.qiyetypes = response.data.obj;
        }, function errorCallback(response) {
            //console.log(response.data)
        });
    });
    //监听自定义指令---企业类型加载完毕
    $scope.$on('ngRepeatFinished', function( ngRepeatFinishedEvent ) {
        var widths=0;
        $('#scroller li').each(function(){
            widths += $(this).width()+20;
        });
        $('#scroller').width(widths);
        var myScroll = new IScroll('#wrapper', { scrollX: true, scrollY: false, mouseWheel: true });
    })

    //类型下拉的
    $scope.types=false;
    $scope.fans=false;
    //箭头
    $scope.jiantou=true;
    //点击箭头
    $scope.qytrypes = function(){
        if($scope.jiantou){
            $scope.jiantou=false;

            $scope.types=true;
            $scope.fans=true;
        }else{
            $scope.jiantou=true;
            $scope.types=false;
            $scope.fans=false;
        }

    }

    //默认类型为全部
    $scope.dqid='';
    //企业类型下拉中的选择
    $scope.chosetype = function(item){
        $scope.jiantou=true;
        $scope.types=false;
        $scope.fans=false;

        // console.log(item);
        $scope.dqid=item.dictId;

        if(item==''){
            $scope.dqid='';

            $scope.name='';
            $scope.type='';
            $scope.goloadmore($scope.name,$scope.type);
        }else{
            pages= 0;
            $scope.qiyelist=[];

            $scope.name='';
            $scope.type=item.dictValue;
            // $scope.dataNull=true;
            // $scope.moredata=true; //是否允许上拉
            $scope.goloadmore($scope.name,$scope.type);
        }

    }



    //初始化企业列表数据------------------------------------------------
    //是否允许加载
    $scope.moredata=true;
    //开始的页数
    var pages=0;
    //加载的条目
    var row=10;
    //企业列表的数据
    $scope.qiyelist=[];

    //下拉刷新
    $scope.doRefresh = function() {
        //重新调用上拉加载
        $scope.name='';
        $scope.goloadmore($scope.name,$scope.type);
        $scope.$broadcast('scroll.refreshComplete');
    };
    //调用上拉加载  -----重新加载的过渡
    $scope.goloadmore = function(name,type){

        console.log(name+'---与---'+type)
        //把数据初始化
        pages= 0;
        $scope.qiyelist=[];
        $scope.moredata=true;
        $scope.dataNull=false;

        if(name==''){
            //调用上拉加载
            $scope.loadMore(name,type)
        }else{
            $scope.dataNull=true;
            $scope.moredata=false; //是否允许上拉
            pages = 1;
            $scope.getqiyelist(pages,row,name,type);
        }

    }
    //上拉加载
    $scope.loadMore = function(name,type) {
        pages += 1;
        console.log(pages+'----------------------------------------------');
        $scope.getqiyelist(pages,row,name,type);
    };


    //搜索框
    $scope.searchs='';
    $scope.seachMain = function(){
        if($scope.searchs && $scope.searchs!=''){
            $scope.clearbtn=true;
        }else{
            $scope.clearbtn=false;
        }
    }
    //确定搜索--
    $scope.keyup = function($event){
        if($event.keyCode==13){
            // alert($scope.searchs);
            $scope.name=$scope.searchs;
            $scope.goloadmore($scope.name,$scope.type);
        }

    }
    //清除
    $scope.clearbtn=false;
    $scope.clearInput = function(){
        $scope.searchs='';
        $scope.name='';
        $scope.clearbtn=false;
        $scope.goloadmore($scope.name,$scope.type);
    }




    //加载企业列表--------------------
    $scope.getqiyelist = function(pages,row,name,type){
        console.log('{"command": "262","content": {"page":\"' + pages + '\", "rows":\"' + row + '\","type":\"'+type+'\","name":"'+name+'"}}')
        $http({
            // method: 'GET',
            // url: B.serverUrl+'/bus_enterprise?page_num='+pages+'&page_size='+row+'&name='+name+'&type='+type,
            method: 'POST',
            url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "262","content": {"page":\"' + pages + '\", "rows":\"' + row + '\","type":\"'+type+'\","name":"'+name+'"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            console.log(response.data.obj.rows);
            if(response.data.obj.rows.length>0){
                for(var i in response.data.obj.rows){
                    $scope.qiyelist.push(response.data.obj.rows[i]);
                }
            }else{
                $scope.dataNull=true;
                $scope.moredata=false; //是否允许上拉
            }
            $scope.$broadcast('scroll.infiniteScrollComplete');
        }, function errorCallback(response) {
            //console.log(response.data)
        });
    }

})

//on-finish-render-type="ngRepeatFinished" 自定义指令----企业类型加载完毕
myApp.directive('onFinishRenderType', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
});





//校友企业

myApp.controller('enterpriseListViewCtrl', function($scope,$http,$stateParams) {

    $scope.serverUrl=B.serverUrl;

    //test img
    $scope.imgServerUrl=B.imageServiceHttp;

    //展开-收起panel_2
    $scope.myVar = false;
    $scope.serVar = true;
    $scope.toggle= function(){

        //箭头的样式改变
        var $arrow = $(".item_arrow").children("i");


        if($arrow.hasClass('fa-chevron-down')){
            $arrow.removeClass('fa-chevron-down').addClass('fa-chevron-up');

        }else if($arrow.hasClass('fa-chevron-up')){
            $arrow.removeClass('fa-chevron-up').addClass('fa-chevron-down');

         }


        //企业类型panel_2显示和隐藏
        $scope.myVar = !$scope.myVar; //对panel_2的收展控制

        //搜索框的显示和隐藏
        $scope.serVar = !$scope.serVar;//对搜索框的收展控制

    }

    //加载企业类型
    loadFirmTypeList($scope, $http);

    //设定参数
    var rowsOfPage = '2'; //每页加载条数
    $scope.hasMore = false;
    $scope.currentPage = 1;

    //提示标语
    $scope.dataNull = false;
    //初始化列表
    loadFirmList($scope,$http,1,rowsOfPage,'','');

    $scope.doRefresh = function() {

        $scope.currentPage = 1;

        B.ready(function () {
            // alert('11'+'{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + $scope.rowsOfPage + '\"}}')

            $http({
                method: 'POST',
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "262","content": {"page":"1", "rows":\"' + rowsOfPage + '\"}}'
                },
                dataType: 'json',
                rowsOfPage: rowsOfPage,
                currentPage: $scope.currentPage
            }).then(function successCallback(response) {

                $scope.firmList = response.data.obj.rows;
                $scope.total = response.data.obj.total;

                if ($scope.firmList.length > 0) {
                    $scope.bItems = $scope.firmList || [];
                    $scope.hasMore = ($scope.bItems.length < $scope.total);

                    // if($scope.bItems.length == 0) {
                    //     $scope.dataNull = true;
                    // } else {
                    //     $scope.dataNull = false;
                    // }
                } else {
                    $scope.hasMore = false;
                }

                $scope.doInfinite();

                $scope.$broadcast("scroll.refreshComplete");

            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });

        })
    };

    //上拉加载，分批加载服务端剩余的数据
    $scope.doInfinite = function() {
        if (!$scope.hasMore) {
            $scope.$broadcast("scroll.infiniteScrollComplete");
            return;
        }
        //  如果当前页数大于等于总页数，说明已经没数据可再加载了。
        $scope.currentPage += 1;
        B.ready(function () {

            // alert('doInfinite'+'{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + rowsOfPage + '\"}}')

            $http({
                method: 'POST',
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + rowsOfPage + '\"}}'
                },
                dataType: 'json',
                rowsOfPage: rowsOfPage,
                currentPage: $scope.currentPage
            }).then(function successCallback(response) {
                // console.log("firm_list"+response);

                $scope.firmList = response.data.obj.rows;
                $scope.total = response.data.obj.total;

                if ($scope.firmList.length>0) {

                    for (var i = 0; i < $scope.firmList.length; i++) {
                        $scope.bItems.push($scope.firmList[i]);

                    }

                    $scope.hasMore = ($scope.bItems.length < $scope.total);

                    if($scope.bItems.length == $scope.total){
                        $scope.dataNull = true;
                    }

                }
                else {
                    $scope.hasMore = false;
                    $scope.dataNull = true;

                }

                $scope.$broadcast("scroll.infiniteScrollComplete");


            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });

        })


    };

    //点击企业类型
    $scope.enterpriseList = function (typeId,name) {

        //toggle panel
        $scope.myVar = !$scope.myVar;

        //点击顶部导航企业类型, 添加样式
        $('.i_'+typeId).addClass('chk_active').siblings().removeClass('chk_active');

        //同步"panel_2"中的点击样式
        $('.t_'+typeId).addClass('chkBg_activ').siblings().removeClass('chkBg_activ');

        //隐藏pannel_2, 显示ser_bar
        $scope.myVar = false;
        $scope.serVar = true;

        //设定参数
        var rowsOfPage = '2'; //每页加载条数
        $scope.hasMore = false; //判断是否加载数据完毕
        $scope.currentPage = 1;

        //初始化
        loadFirmList($scope, $http,1,rowsOfPage,typeId,name);

        //下拉刷新
        $scope.doRefresh = function() {

            $scope.currentPage = 1;

            B.ready(function () {
                // alert('dofresher'+'{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + $scope.rowsOfPage + '\"}}')


                $http({
                    method: 'POST',
                    url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                    data: {
                        jsonStr: '{"command": "262","content": {"page":"1", "rows":\"' + rowsOfPage + '\","type":\"'+typeId+'\","name":"'+name+'"}}'
                    },
                    dataType: 'json',
                    rowsOfPage: rowsOfPage,
                    currentPage: $scope.currentPage
                }).then(function successCallback(response) {
                    // console.log(response);
                    // console.log(response.data.success);

                    $scope.firmList = response.data.obj.rows;
                    $scope.total = response.data.obj.total;

                    if ($scope.firmList.length > 0) {
                        $scope.bItems = $scope.firmList || [];
                        $scope.hasMore = ($scope.bItems.length < $scope.total);

                        // if($scope.bItems.length == 0) {
                        //     $scope.dataNull = true;
                        // } else {
                        //     $scope.dataNull = false;
                        // }
                    } else {
                        $scope.hasMore = false;
                    }

                    $scope.doInfinite(typeId);

                    $scope.$broadcast("scroll.refreshComplete");

                }, function errorCallback(response) {
                    //alert('22:'+JSON.stringify(response));
                });

            })
        };

        //上拉加载，分批加载服务端剩余的数据
        $scope.doInfinite = function() {
            if (!$scope.hasMore) {
                $scope.$broadcast("scroll.infiniteScrollComplete");
                return;
            }
            //  如果当前页数大于等于总页数，说明已经没数据可再加载了。
            $scope.currentPage += 1;
            B.ready(function () {

                // alert('22'+'{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + rowsOfPage + '\","type":\"'+typeId+'\","name":"'+name+'"}}')

                $http({
                    method: 'POST',
                    url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                    data: {
                        jsonStr: '{"command": "262","content": {"page":\"' + $scope.currentPage + '\", "rows":\"' + rowsOfPage + '\","type":\"'+typeId+'\","name":"'+name+'"}}'
                    },
                    dataType: 'json',
                    rowsOfPage: rowsOfPage,
                    currentPage: $scope.currentPage
                }).then(function successCallback(response) {
                    // console.log("22222"+response);

                    $scope.firmList = response.data.obj.rows;
                    $scope.total = response.data.obj.total;

                    if ($scope.firmList.length>0) {

                        for (var i = 0; i < $scope.firmList.length; i++) {
                            $scope.bItems.push($scope.firmList[i]);

                        }
                        $scope.hasMore = ($scope.bItems.length < $scope.total);

                        if($scope.bItems.length == $scope.total){
                            $scope.dataNull = true;
                        }
                        // alert("22"+$scope.hasMore)
                    } else {
                        $scope.hasMore = false;
                        $scope.dataNull = true;

                    }

                    $scope.$broadcast("scroll.infiniteScrollComplete");


                }, function errorCallback(response) {
                    //alert('22:'+JSON.stringify(response));
                });

            })


        };

    }


    //点击panel_2中的企业类型,加载企业列表
    /*$scope.showFirmList = function(typeId){
        $scope.myVar = !$scope.myVar;


        //点击企业类型,添加样式

        //加载某类型的企业列表
        $scope.enterpriseList(typeId,'');


    }*/


    //搜索公司
    $scope.ser = function(name){

        //根据是否"被选择:, 来去其企业类型值
        var typeVal=$('.chk_active').attr('data-val');

        //"全部"
        if(typeVal=='all'){
            typeVal = '';
        }

        //加载搜索结果
        $scope.enterpriseList(typeVal,name);


     }


    //ser_input 获取焦点
    $scope.serFocus = function(){
        $('.ser_tip').hide();
        $('.ser_input').css('padding-left','0.3rem');
        $('.ser_input').css('width','91%');
        $('.icon_clear').show();

    }

    //ser_input 取消焦点
    $scope.serBlur = function(){
        $('.ser_input').val('');
        $('.icon_clear').hide();
        $scope.ser('');
    }


    //ser_input clear
    $scope.serClear = function () {
        $('.ser_input').val('');
        $('.icon_clear').hide();
        $scope.ser('');
    }


})



function loadFirmTypeList($scope, $http){
    // console.log('{"command": "267","content": {"dictTypeValue":"enterprise_type"}}')

    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "267","content": {"dictTypeValue":"enterprise_type"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            // console.log($scope.firmTypeList)
            if (response.data.obj && response.data.obj.length>0){
                $scope.firmTypeList=response.data.obj;

                //iscroll
                $scope.V_width=window.screen.width/5 ;

                $scope.tabLen = ($scope.firmTypeList.length + 1); // + 全部

                //len
                $scope.len = $scope.tabLen * ($scope.V_width/100 +0.2);
                // console.log($scope.len)

                $('.nav_scroll_list').css("width",($scope.len) +"rem");

                //$('.nav_item').css('width',($scope.V_width / 100)+'rem');

            }


            //选择4个
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}


function loadFirmList($scope, $http,page,rowsOfPage,typeId,name){

    // alert("0000"+'{"command": "262","content": {"page":\"'+page+'\", "rows":\"'+rowsOfPage+'\","type":\"'+typeId+'\"}}')

    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "262","content": {"page":\"'+page+'\", "rows":\"'+rowsOfPage+'\","type":\"'+typeId+'\","name":"'+name+'"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            $scope.firmList = response.data.obj.rows;
            $scope.total = response.data.obj.total;
            // alert($scope.firmList.length)


            if ($scope.firmList.length > 0) {
                $scope.bItems = $scope.firmList || [];
                $scope.hasMore = ($scope.bItems.length < $scope.total);

                /*if($scope.bItems.length == 0) {
                    $scope.dataNull = true;
                } else {
                    $scope.dataNull = false;
                }*/

            } else {
                $scope.hasMore = false;
            }

            $scope.$broadcast("scroll.refreshComplete");
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })

}



// 产品列表
myApp.controller('productListViewCtrl', function($scope,$http,$stateParams) {

    $('title').html('产品列表');

    $scope.picList =[];

    //设定参数
    var rowsOfPage = '2'; //每页加载条数

    var enterpriseId = $stateParams.id;

    //加载产品列表
    loadProductList($scope, $http,1,rowsOfPage,enterpriseId);

    //初始化参数
    $scope.hasMore = false;
    $scope.currentPage = 1;

    //提示标语
    $scope.dataNull = false;

    //下拉刷新
    $scope.doRefresh = function() {

        $scope.currentPage = 1;

        B.ready(function () {

            $http({
                method: 'POST',
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "264","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+$scope.currentPage+'\", "rows":\"'+rowsOfPage+'\"}}'
                },
                dataType: 'json',
                rowsOfPage: rowsOfPage,
                currentPage: $scope.currentPage
            }).then(function successCallback(response) {


                $scope.productList = response.data.obj.rows;
                $scope.total = response.data.obj.total;

                if ($scope.productList.length > 0) {
                    $scope.pItems = $scope.productList || [];
                    $scope.hasMore = ($scope.pItems.length < $scope.total);


                } else {
                    $scope.hasMore = false;
                }

                $scope.doInfinite();

                $scope.$broadcast("scroll.refreshComplete");

            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });

        })
    };


    //上拉加载，分批加载服务端剩余的数据
    $scope.doInfinite = function() {
        if (!$scope.hasMore) {
            $scope.$broadcast("scroll.infiniteScrollComplete");
            return;
        }
        //  如果当前页数大于等于总页数，说明已经没数据可再加载了。
        $scope.currentPage += 1;

        B.ready(function () {


            $http({
                method: 'POST',
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "264","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+$scope.currentPage+'\", "rows":\"'+rowsOfPage+'\"}}'
                },
                dataType: 'json',
                rowsOfPage: rowsOfPage,
                currentPage: $scope.currentPage
            }).then(function successCallback(response) {

                $scope.productList = response.data.obj.rows;
                $scope.total = response.data.obj.total;

                if ($scope.productList.length>0) {

                    for (var i = 0; i < $scope.productList.length; i++) {
                        $scope.pItems.push($scope.productList[i]);

                    }
                    $scope.hasMore = ($scope.pItems.length < $scope.total);

                    if($scope.pItems.length == $scope.total){
                        $scope.dataNull = true;
                    }

                } else {
                    $scope.hasMore = false;
                    $scope.dataNull = true;

                }

                $scope.$broadcast("scroll.infiniteScrollComplete");


            }, function errorCallback(response) {
                //alert('22:'+JSON.stringify(response));
            });

        })


    };



})

function loadProductList($scope, $http,page,rowsOfPage,enterpriseId){
// console.log('{"command": "264","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+page+'\", "rows":\"'+rowsOfPage+'\"}}')
    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "264","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+page+'\", "rows":\"'+rowsOfPage+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            $scope.productList = response.data.obj.rows;
            $scope.total = response.data.obj.total;

            console.log($scope.productList)

            if ($scope.productList.length > 0) {
                $scope.pItems = $scope.productList || [];
                $scope.hasMore = ($scope.pItems.length < $scope.total);


            } else {
                $scope.hasMore = false;
            }

            $scope.$broadcast("scroll.refreshComplete");
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}


//企业详情
myApp.controller('enterpriseDetailViewCtrl',function ($scope,$http, $stateParams) {

    var enterpriseId = $stateParams.id;
    var rowsOfPage = '2'; //每页加载条数

    $('title').html('企业详情')

    $scope.pItems=[];

    //加载详情
    loadFirmDetail($scope,$http,1,rowsOfPage,enterpriseId);



})

//加载企业详情
function loadFirmDetail($scope,$http,page,rowsOfPage, enterpriseId){

    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "263","content": {"enterpriseId":\"'+enterpriseId+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            // console.log(response.data.obj)
            $scope.firmDetail=response.data.obj;


            //加载单页上的产品列表
            loadProductList($scope, $http,1,2,enterpriseId);

            //加载单页上的职位列表
            loadPositionList($scope, $http,1,2,enterpriseId,0);

            //企业成员展示
            loadTeamMemberList($scope,$http,'',rowsOfPage,enterpriseId);


        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}


function loadTeamMemberList($scope, $http,page,rowsOfPage,enterpriseId){
    // console.log("1111"+"page="+"page | "+"rowsOfPage="+rowsOfPage | "+id=="+enterpriseId)

    // console.log('{"command": "621","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+page+'\", "rows":\"'+rowsOfPage+'\"}}')
    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "621","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+page+'\", "rows":\"'+rowsOfPage+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            $scope.memberList = response.data.obj.rows;
            $scope.total = response.data.obj.total;
            // console.log($scope.memberList);

            //scroller
            $scope.imgItem_width=(window.screen.width+20)/3 ;

            $scope.mListLen = $scope.memberList.length;

            //len
            // $scope.len = $scope.mListLen * ($scope.imgItem_width +20);
            $scope.len ={
                // 'width': $scope.mListLen * ($scope.imgItem_width +20)+'px'
                'width': $scope.mListLen * ($scope.imgItem_width ) +20+'px'
                // 'width': $scope.mListLen * ($scope.imgItem_width)+42+'px'
            }

            $scope.mPic = $scope.imgItem_width-10;


        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}



//产品详情
myApp.controller('productDetailViewCtrl',function ($scope,$http, $stateParams) {

    $('title').html('产品详情')


    var productId = $stateParams.id;

    //加载详情
    loadProductDetail($scope,$http, productId);

    //点赞产品
    $scope.praiseClick = function(hasPraised){
        // alert(praiseNum)
        if(!accountNum || accountNum == "null") {
            // 微信端
            alert("尚未登录,请先登录.");
            return false;
        }

        if(hasPraised=='0'){
            //该用户没有对产品点赞
            productPraise($scope,$http, productId,accountNum);

        }else{
            //对用户已经点赞
            productPraise($scope,$http, productId,accountNum);
        }


    }


    $scope.$on('ngRepeatFinished', function () {
        // 轮播图
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            autoplay: 2000,
            autoplayDisableOnInteraction: false,
            loop: true
        });

    });

})


//产品点赞
function productPraise($scope,$http, productId,accountNum){

    B.ready(function(){
        // console.log('{"command": "255","content": {"bussId": \"'+productId+'\","userId": \"'+accountNum+'\","type": "30","bussType": "50"}}');

        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "255","content": {"bussId": \"'+productId+'\","userId": \"'+accountNum+'\","type": "30","bussType": "50"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {

            console.log(response.data);

            if($scope.productDetail.hasPraised == '1'){
                $scope.productDetail.praiseNumber = parseInt($scope.productDetail.praiseNumber) -1;
                $scope.dianzan=false;
                $scope.productDetail.hasPraised = '0';
            }else if($scope.productDetail.hasPraised == '0'){
                $scope.productDetail.praiseNumber = parseInt($scope.productDetail.praiseNumber) +1;
                $scope.dianzan=true;
                $scope.productDetail.hasPraised = '1';
            }



        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })

}

function loadProductDetail($scope,$http,productId){

// console.log('{"command": "265","content": {"productId": \"'+productId+'\","accountNum":\"'+accountNum+'\"}}')
    B.ready(function(){

        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "265","content": {"productId": \"'+productId+'\","accountNum":\"'+accountNum+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            $scope.productDetail=response.data.obj;

            // console.log( $scope.productDetail);

            if($scope.productDetail.posterPic && $scope.productDetail.posterPic != ''){
                $scope.pPic = $scope.productDetail.posterPic.split(',');
            }
            // console.log($scope.pPic)

            if($scope.productDetail.hasPraised==1){
                // 已点
                $scope.dianzan=true;
            }else{
                $scope.dianzan=false;
            }

            // alert($scope.productDetail.praiseNumber);

        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })

}

myApp.directive('onFinishRenderFilters', function ($timeout) {

    return {
        restrict: 'A',
        link: function (scope, element, attr) {

            if (scope.$last === true) {    //判断是否是最后一条数据
                $timeout(function () {
                    scope.$emit('ngRepeatFinished'); //向父级scope传送ngRepeatFinished命令
                });
            }
        }
    };
});

// 职位列表
myApp.controller('positionListViewCtrl', function($scope,$http,$stateParams) {

    $('title').html('职位列表');

    //设定参数


    var enterpriseId = $stateParams.id;


    //初始化参数
    var rowsOfPage = '10'; //每页加载条数
    $scope.hasMoreJob = true; //默认加载更多
    $scope.jobNull = false; //提示标语
    $scope.jobItems = [];
    var currentPage = 0; //当前页


    //下拉刷新
    $scope.doRefreshJob = function() {
        currentPage = 1;
        rowsOfPage = '10';

        $scope.doInfiniteJob;
        $scope.$broadcast('scroll.refreshComplete');
    };




    //上拉加载，分批加载服务端剩余的数据
    $scope.doInfiniteJob = function() {

        currentPage += 1;

        loadPositionList($scope, $http,currentPage,rowsOfPage,enterpriseId,1);

    };


})

function loadPositionList($scope, $http,currentPage,rowsOfPage,enterpriseId,flag){

// console.log('{"command": "622","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+currentPage+'\", "rows":\"'+rowsOfPage+'\"}}')
    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "622","content": {"enterpriseId":\"'+enterpriseId+'\","page":\"'+currentPage+'\", "rows":\"'+rowsOfPage+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            // console.log(response);
            $scope.total = response.data.obj.total;
            if(flag==0){
                $scope.jobItems = response.data.obj.rows;
                $scope.$broadcast('scroll.infiniteScrollComplete');

            }else if(flag==1){

                if(response.data.obj && response.data.obj.rows && response.data.obj.rows.length>0){

                    for(var i in response.data.obj.rows){
                        $scope.jobItems.push(response.data.obj.rows[i]);
                    }

                    $scope.$broadcast('scroll.infiniteScrollComplete');

                }
                if(!response.data || !response.data.obj || !response.data.obj.rows || response.data.obj.rows.length==0){
                    $scope.hasMoreJob=false;
                    $scope.$broadcast('scroll.infiniteScrollComplete');
                    $scope.jobNull=true;
                }

            }


        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}


//职位详情
myApp.controller('positionDetailViewCtrl',function ($scope,$http, $stateParams) {
    $('title').html('职位详情');

    if(!accountNum || accountNum == "null") {
        // 微信端
        alert("尚未登录,请先登录.");
    }


    var positionId = $stateParams.id;

    //加载详情
    loadPositionDetail($scope,$http, positionId);


})

function loadPositionDetail($scope,$http, positionId) {

    // console.log('{"command": "623","content": {"id": \"'+positionId+'\","accountNum":\"'+accountNum+'\"}}')
    B.ready(function(){

        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data: {
                jsonStr: '{"command": "623","content": {"id": \"'+positionId+'\","accountNum":\"'+accountNum+'\"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            // console.log(response)
            $scope.positionDetail=response.data.obj;

            // alert($scope.productDetail.praiseNumber);

        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })


}