/**
 * Created by mcz on 2017/6/3.
 */
myApp.controller('workwillViewCtrls', function($scope,$http,$stateParams) {
    $scope.worktype=false;
    $scope.workhangye=false;
    $scope.workcity=false;
    $scope.workxinzi=false;

    $scope.zw_types=true;
    $scope.zw_hangyes=true;
    $scope.zw_citys=true;
    $scope.zw_xinzis=true;

    $scope.gongztype=function(index){
        if(index==1){
            $scope.worktype=true;
            $scope.workhangye=false;
            $scope.workcity=false;
            $scope.workxinzi=false;
        }else if(index==2){
            $scope.worktype=false;
            $scope.workhangye=true;
            $scope.workcity=false;
            $scope.workxinzi=false;
        }else if(index==3){
            $scope.worktype=false;
            $scope.workhangye=false;
            $scope.workcity=true;
            $scope.workxinzi=false;
        }
        else if(index==4){
            $scope.worktype=false;
            $scope.workhangye=false;
            $scope.workcity=false;
            $scope.workxinzi=true;
        }
    }
})



//工作类型的
myApp.directive("workType", function () {
    return {
        // scope: {}, //独立作用域
        restrict:"AE",
        templateUrl: 'view/work_will/willlist/worktype.html',
        replace:true,
        controller: function($scope) {  //暴露共用的属性和方法
            //一级的类型
            $scope.one=[
                {
                    title:'销售|客服|市场',
                    id:'1'
                },
                {
                    title:'财务|人力资源|行政',
                    id:'2'
                },
                {
                    title:'项目|质量|高级管理',
                    id:'3'
                },{
                    title:'IT|互联网|通信',
                    id:'4'
                },{
                    title:'房产|建筑|物业|管理',
                    id:'5'
                },{
                    title:'销售|客服|市场',
                    id:'6'
                },
                {
                    title:'销售|客服|市场',
                    id:'7'
                },{
                    title:'销售|客服|市场',
                    id:'8'
                },
                {
                    title:'销售|客服|市场',
                    id:'9'
                }
            ]

            //二级的类型
            $scope.er1=[
                {
                    title:'销售|客服|市场1',
                    id:'1'
                },
                {
                    title:'财务|人力资源|行政1',
                    id:'2'
                },
                {
                    title:'项目|质量|高级管理1',
                    id:'3'
                },{
                    title:'IT|互联网|通信1',
                    id:'4'
                },{
                    title:'房产|建筑|物业|管理1',
                    id:'5'
                },{
                    title:'销售|客服|市场1',
                    id:'6'
                },
                {
                    title:'销售|客服|市场1',
                    id:'7'
                },{
                    title:'销售|客服|市场1',
                    id:'8'
                },
                {
                    title:'销售|客服|市场',
                    id:'9'
                }
            ]
            $scope.er2=[
                {
                    title:'销售|客服|市场12',
                    id:'1'
                },
                {
                    title:'财务|人力资源|行政12',
                    id:'2'
                },
                {
                    title:'项目|质量|高级管理12',
                    id:'3'
                },{
                    title:'IT|互联网|通信12',
                    id:'4'
                },{
                    title:'房产|建筑|物业|管理12',
                    id:'5'
                },{
                    title:'销售|客服|市场12',
                    id:'6'
                },
                {
                    title:'销售|客服|市场12',
                    id:'7'
                },{
                    title:'销售|客服|市场1',
                    id:'8'
                },
                {
                    title:'销售|客服|市场',
                    id:'9'
                }
            ]

            //三级的类型
            $scope.san1=[
                {
                    title:'销售|客服|市场33-',
                    id:'1'
                },
                {
                    title:'财务|人力资源|行政33-',
                    id:'2'
                },
                {
                    title:'项目|质量|高级管理33-',
                    id:'3'
                },{
                    title:'IT|互联网|通信33-',
                    id:'4'
                },{
                    title:'房产|建筑|物业|管理33-',
                    id:'5'
                },{
                    title:'销售|客服|市场',
                    id:'6'
                },
                {
                    title:'销售|客服|市场',
                    id:'7'
                },{
                    title:'销售|客服|市场',
                    id:'8'
                },
                {
                    title:'销售|客服|市场',
                    id:'9'
                }
            ]
            $scope.san2=[
                {
                    title:'销售|客服|市场33',
                    id:'1'
                },
                {
                    title:'财务|人力资源|行政33',
                    id:'2'
                },
                {
                    title:'项目|质量|高级管理33',
                    id:'3'
                },{
                    title:'IT|互联网|通信33',
                    id:'4'
                },{
                    title:'房产|建筑|物业|管理33',
                    id:'5'
                },{
                    title:'销售|客服|市场',
                    id:'6'
                },
                {
                    title:'销售|客服|市场',
                    id:'7'
                },{
                    title:'销售|客服|市场',
                    id:'8'
                },
                {
                    title:'销售|客服|市场',
                    id:'9'
                }
            ]

            //二级的所有类型 默认隐藏
            $scope.erji_type=false;

            //一级的点击
            $scope.oneChange = function(item){
                console.log(item);
                $scope.erji_type=true;
                if(item.id==1){
                    $scope.er=$scope.er1;
                }else{
                    $scope.er=$scope.er2;
                }
            }
            //二级的点击
            $scope.erChange = function(item){
                console.log(item);
                if(item.id==1){
                    $scope.san=$scope.san1;
                }else{
                    $scope.san=$scope.san2;
                }
            }
            //三级的点击
            $scope.sanChange = function(item){
                console.log(item);
                $scope.erji_type=false;
                $scope.worktype=false;

                $scope.zw_types=false;
                $scope.type_title=item.title;
            }



        },
        link: function(scope, element, attrs) {  //操作DOM

            //一级
            $('.type_one').on('click','.oness',function(){
                $(this).addClass('dq_one');
                $(this).siblings().removeClass('dq_one');
            })
            //二级
            $('.type_t_er').on('click','.erss',function(){
                $(this).addClass('dq_one');
                $(this).siblings().removeClass('dq_one');
            })
            //三级
            $('.type_t_san').on('click','.sanss',function(){
                $(this).addClass('dq_one');
                $(this).siblings().removeClass('dq_one');
            })

        }
    }
});

//工作行业的
myApp.directive("workHangye", function () {
    return {
        // scope: {}, //独立作用域
        restrict:"AE",
        templateUrl: 'view/work_will/willlist/workhangye.html',
        replace:true,
        controller: function($scope) {  //暴露共用的属性和方法

            $scope.work_hangye=[
                {title:'互联网',id:'1'},
                {title:'互联网1',id:'1'},
                {title:'互联网2',id:'1'},
                {title:'互联网3',id:'1'},
                {title:'互联网4',id:'1'},
                {title:'互联网5',id:'1'},
                {title:'互联网6',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网7',id:'1'},
                {title:'互联网8',id:'1'}
            ];


            //点击确定
            $scope.hangywBtn=function(){
                $scope.workhangye=false;
                if($('.dq_hangye').length>0){
                    var hangyeLsit=[];
                    $('.dq_hangye').each(function(){
                        hangyeLsit.push($(this).text());
                    });
                    //console.log(hangyeLsit);

                    $scope.zw_hangyes=false;
                    $scope.hangye_title=hangyeLsit.join("|");


                }
            }

        },
        link: function(scope, element, attrs) {  //操作DOM
            //点击选择行业
            $('.hy_box').on('click',".hy_list",function(){
                if($('.dq_hangye').length>=3){
                    $(this).removeClass('dq_hangye');
                    $('.hynums').html($('.dq_hangye').length);
                }else{
                    $(this).toggleClass('dq_hangye');
                    $('.hynums').html($('.dq_hangye').length);
                }
            });

            //点击重置
            $('.hychongzhi').click(function(){
                $('.dq_hangye').removeClass('dq_hangye');
                $('.hynums').html(0);
            })
        }
    }
});

//工作城市的
myApp.directive("workCity", function () {
    return {
        // scope: {}, //独立作用域
        restrict:"AE",
        templateUrl: 'view/work_will/willlist/workcity.html',
        replace:true,
        controller: function($scope) {  //暴露共用的属性和方法

            //市
            $scope.work_sheng=[
                {
                    title:'北京',
                    id:'1'
                },
                {
                    title:'上海',
                    id:'2'
                },
                {
                    title:'杭州',
                    id:'3'
                },{
                    title:'深圳',
                    id:'4'
                },{
                    title:'天津',
                    id:'5'
                },{
                    title:'黑龙江',
                    id:'6'
                }
            ]

            //省
            $scope.work_shi1=[
                {
                    title:'北京111',
                    id:'1'
                },
                {
                    title:'上海111',
                    id:'2'
                },
                {
                    title:'杭州111',
                    id:'3'
                },{
                    title:'深圳111',
                    id:'4'
                },{
                    title:'天津111',
                    id:'5'
                },{
                    title:'黑龙江',
                    id:'6'
                }
            ]
            $scope.work_shi2=[
                {
                    title:'北京222',
                    id:'1'
                },
                {
                    title:'上海222',
                    id:'2'
                },
                {
                    title:'杭州222',
                    id:'3'
                },{
                    title:'深圳222',
                    id:'4'
                },{
                    title:'天津222',
                    id:'5'
                },{
                    title:'黑龙江',
                    id:'6'
                }
            ]


            //市的点击
            $scope.workSheng=function(item){
                //console.log(item)
                if(item.id=='1'){
                    $scope.work_shi=$scope.work_shi1;
                }else{
                    $scope.work_shi=$scope.work_shi2;
                }
            }
            //省的点击
            $scope.workShi=function(item){
                //console.log(item)
                $scope.workcity=false;
                $scope.zw_citys=false;
                $scope.city_title=item.title;
            }
        },
        link: function(scope, element, attrs) {  //操作DOM

            //省
            $('.sheng').on('click','.shengde',function(){
                $(this).addClass('dq_one');
                $(this).siblings().removeClass('dq_one');
            })
            //市
            $('.shi').on('click','.shide',function(){
                $(this).addClass('dq_one');
                $(this).siblings().removeClass('dq_one');
            })

        }
    }
});

//工作薪资的
myApp.directive("workXinzi", function () {
    return {
        // scope: {}, //独立作用域
        restrict:"AE",
        templateUrl: 'view/work_will/willlist/workxinzi.html',
        replace:true,
        controller: function($scope) {  //暴露共用的属性和方法

            //薪资范围
            $scope.work_xinzis=[
                {
                    title:'面议'
                },
                {
                    title:'1k'
                },
                {
                    title:'2k'
                },
                {
                    title:'3k'
                },
                {
                    title:'4k'
                },
                {
                    title:'5k'
                },
                {
                    title:'6k'
                },
                {
                    title:'7k'
                },
                {
                    title:'8k'
                },
                {
                    title:'9k'
                },
                {
                    title:'10k'
                }
            ]

            //点击min薪资
            $scope.minXinzi = function (item){
                // console.log(item);
                $scope.xinzi_min=item.title;
            }
            //点击max薪资
            $scope.maxXinzi = function (item){
                // console.log(item);
                $scope.xaxzi_max=item.title;
            }

            //点击确定
            $scope.quedingXinzi = function (){
                $scope.workxinzi=false;

                if($scope.xinzi_min&& $scope.xinzi_min!='' && $scope.xaxzi_max && $scope.xaxzi_max!=''){
                    $scope.xz_fanwei=$scope.xinzi_min+'-'+$scope.xaxzi_max;
                    $scope.zw_xinzis=false;
                }

            }

        },
        link: function(scope, element, attrs) {  //操作DOM

            //最小薪资
            $('.left_boxs').on('click','.djxinzi',function(){
                $(this).addClass('dq_xinzi');
                $(this).siblings().removeClass('dq_xinzi');
            })
            //最大薪资
            $('.right_boxs').on('click','.djxinzi',function(){
                $(this).addClass('dq_xinzi');
                $(this).siblings().removeClass('dq_xinzi');
            })

        }
    }
});
