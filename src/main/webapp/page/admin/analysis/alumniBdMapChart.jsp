<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">

        <title>各省份校友会分布</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!-- ECharts单文件引入 -->
        <jsp:include page="../../../inc.jsp"></jsp:include>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script src="${pageContext.request.contextPath}/jslib/echarts/dist/echarts.js"></script>
        <%--<script src="${pageContext.request.contextPath}/jslib/echarts/echartsHome.js"></script>
        <script src="${pageContext.request.contextPath}/jslib/echarts/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/jslib/echarts/BMap.js"></script>--%>
        <script src="${pageContext.request.contextPath}/page/admin/analysis/js/alumniBMap.js"></script>
        <%--<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>--%>

        <script>
            var geoCoordData = new Array() ;
            var markPointData = new Array() ;
            var markLineData = new Array() ;
            var markLineSeriesData = new Array() ;
            var school = "窗友科技大学" ;
            var schoolCity = "武汉" ;
            var schoolPoint = new Array();
            var max = 0 ;

            function searchByStationName() {

                findProvincePoint();
                $.ajax({
                    url : '${pageContext.request.contextPath}/analysis/analysisAction!doNotNeedSecurity_getSchoolPoint.action',
                    dataType : 'json',
                    async:false ,
                    success : function(data)
                    {
                        if(data != null) {
                            schoolCity = data.schoolCity ;
                            school = data.school ;
                            schoolPoint[0] = data.lng ;
                            schoolPoint[1] = data.lat ;
//                            alert(schoolCity + "- "+ schoolPoint)
                            geoCoordData[school]=schoolPoint;

                        }
                    }
                });
            }

            function chartOfAlumniMap() {
                $.ajax({
                    url : '${pageContext.request.contextPath}/analysis/analysisAction!doNotNeedSecurity_chartOfAlumniCountMap.action',
                    dataType : 'json',
                    async:false ,
                    success : function(data)
                    {
                        if(data != null && data.length > 0) {
                            markPointData = data ;
                            for(var i = 0 ; i< data.length ;i++) {
                                if(data[i].value > max) {
                                    max = data[i].value;
                                }
//                                markLineData[i] = [{name:school},{name:data[i].name}] ;
                                markLineSeriesData[i] = [{name:school},{name:data[i].name,value:data[i].value}] ;
                            }
//                            max = parseInt(data[0].value) ;
                        }
                    }
                });
            }

            function findProvincePoint() {

                $.ajax({
                    url : '${pageContext.request.contextPath}/analysis/analysisAction!doNotNeedSecurity_findProvincePoint.action',
                    dataType : 'json',
                    async:false ,
                    success : function(data)
                    {
                        if(data != null) {
                            geoCoordData = data.pointMap ;
                            for(var i = 0 ; i< data.nameList.length ;i++) {
                                markLineData[i] = [{name:school},{name:data.nameList[i]}] ;
//                                markLineSeriesData[i] = [{name:school},{name:data[i].name,value:data[i].value}] ;
                            }
//                            max = parseInt(data[0].value) ;
                        }
                    }
                });
            }
        </script>
    </head>
<body>
<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head">1111111</div>
<input type="button" value="柱状图" onclick="init1();" >
<input type="button" value="饼状图" onclick="init2();" >
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:800px;"></div>
    <!-- ECharts单文件引入 -->
    <%--<script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '${pageContext.request.contextPath}/jslib/echarts/dist'
            },
            packages: [
                {
                    name: 'BMap',
                    location: '${pageContext.request.contextPath}/jslib/echarts/dist/map',
                    main: 'main'
                }
            ]
        });

        var myChart ;
        // 使用
        require(
                [
                    'echarts',
                    'BMap',
                    'echarts/chart/map'
                ],
                function (ec) {
                    // 初始化地图
                    var BMapExt = new BMapExtension(domMain, BMap, require('echarts'), require('zrender'));
                    var map = BMapExt.getMap();
                    var container = BMapExt.getEchartsContainer();
                    var point = new BMap.Point(startPoint.x, startPoint.y);
                    map.centerAndZoom(point, 5);
                    map.enableScrollWheelZoom(true);


                    if (myChart && myChart.dispose) {
                        myChart.dispose();
                    }
                    // 基于准备好的dom，初始化echarts图表
                    myChart = BMapExt.initECharts(container, curTheme);
                    window.onresize = myChart.resize;
                    init();

                }
        );
        function init(){
            searchByStationName();
            chartOfAlumniMap() ;
            var option = {
                backgroundColor: '#1b1b1b',
                color: ['gold','aqua','lime'],
                title : {
                    text: '校友会数',
                    subtext:'各省校友会数',
                    x:'center',
                    textStyle : {
                        color: '#fff'
                    }
                },
                tooltip : {
                    trigger: 'item',
                    formatter: '{b}'
                },
                legend: {
                    orient: 'vertical',
                    x:'left',
                    data:[school],
                    selectedMode: 'single',
                    textStyle : {
                        color: '#fff'
                    }
                },
                toolbox: {
                    show : true,
                    orient : 'vertical',
                    x: 'right',
                    y: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                dataRange: {
                    min : 0,
                    max : max,
                    calculable : true,
                    color: ['#ff3333', 'orange', 'yellow','lime','aqua'],
                    textStyle:{
                        color:'#fff'
                    }
                },
                series : [
                    {
                        name: '全国',
                        type: 'map',
                        roam: true,
                        hoverable: false,
                        mapType: 'china',
                        itemStyle:{
                            normal:{
                                borderColor:'rgba(100,149,237,1)',
                                borderWidth:0.5,
                                areaStyle:{
                                    color: '#1b1b1b'
                                }
                            }
                        },
                        data:[],
                        markLine : {
                            smooth:true,
                            symbol: ['none', 'circle'],
                            symbolSize : 1,
                            itemStyle : {
                                normal: {
                                    color:'#fff',
                                    borderWidth:1,
                                    borderColor:'rgba(30,144,255,0.5)'
                                }
                            },
                            data : markLineData
                        },
                        geoCoord:geoCoordData
                    },
                    {
                        name: school,
                        type: 'map',
                        mapType: 'china',
                        data:[],
                        markLine : {
                            smooth:true,
                            effect : {
                                show: true,
                                scaleSize: 1,
                                period: 30,
                                color: '#fff',
                                shadowBlur: 10
                            },
                            itemStyle : {
                                normal: {
                                    borderWidth:1,
                                    lineStyle: {
                                        type: 'solid',
                                        shadowBlur: 10
                                    }
                                }
                            },
                            data : markLineSeriesData
//                                    [[{"name":"窗友科技大学"},{"name":"北京","value":2}],[{"name":"窗友科技大学"},{"name":"湖北","value":1}],[{"name":"窗友科技大学"},{"name":"天津","value":1}]]
                        },
                        markPoint : {
                            symbol:'emptyCircle',
                            symbolSize : function (v){
                                return 10 + v/10
                            },
                            effect : {
                                show: true,
                                shadowBlur : 0
                            },
                            itemStyle:{
                                normal:{
                                    label:{show:false}
                                },
                                emphasis: {
                                    label:{position:'top'}
                                }
                            },
                            data : markPointData
                        }
                    }
                ]
            };




//            var option = {
//                color: ['gold','aqua','lime'],
//                title : {
//                    text: '模拟迁徙',
//                    subtext:'数据纯属虚构',
//                    x:'right'
//                },
//                tooltip : {
//                    trigger: 'item',
//                    formatter: function (v) {
//                        return v[1].replace(':', ' > ');
//                    }
//                },
//                legend: {
//                    orient: 'vertical',
//                    x:'left',
//                    data:['北京', '上海', '广州'],
//                    selectedMode: 'single',
//                    selected:{
//                        '上海' : false,
//                        '广州' : false
//                    }
//                },
//                toolbox: {
//                    show : true,
//                    orient : 'vertical',
//                    x: 'right',
//                    y: 'center',
//                    feature : {
//                        mark : {show: true},
//                        dataView : {show: true, readOnly: false},
//                        restore : {show: true},
//                        saveAsImage : {show: true}
//                    }
//                },
//                dataRange: {
//                    min : 0,
//                    max : 100,
//                    y: '60%',
//                    calculable : true,
//                    color: ['#ff3333', 'orange', 'yellow','lime','aqua']
//                },
//                series : [
//                    {
//                        name:'北京',
//                        type:'map',
//                        mapType: 'none',
//                        data:[],
//                        geoCoord: geoCoordData,
//
//                        markLine : {
//                            smooth:true,
//                            effect : {
//                                show: true,
//                                scaleSize: 1,
//                                period: 30,
//                                color: '#fff',
//                                shadowBlur: 10
//                            },
//                            itemStyle : {
//                                normal: {
//                                    borderWidth:1,
//                                    lineStyle: {
//                                        type: 'solid',
//                                        shadowBlur: 10
//                                    }
//                                }
//                            },
//                            data : markLineSeriesData
//                        },
//                        markPoint : {
//                            symbol:'emptyCircle',
//                            symbolSize : function (v){
//                                return 10 + v/10
//                            },
//                            effect : {
//                                show: true,
//                                shadowBlur : 0
//                            },
//                            itemStyle:{
//                                normal:{
//                                    label:{show:false}
//                                }
//                            },
//                            data : markPointData
//                        }
//                    },
//                    {
//                        name:'全国',
//                        type:'map',
//                        mapType: 'none',
//                        data:[],
//                        markLine : {
//                            smooth:true,
//                            symbol: ['none', 'circle'],
//                            symbolSize : 1,
//                            itemStyle : {
//                                normal: {
//                                    color:'#fff',
//                                    borderWidth:1,
//                                    borderColor:'rgba(30,144,255,0.5)'
//                                }
//                            },
//                            data : markLineData
//                        }
//                    }
//                ]
//            };

            myChart.clear();
            // 为echarts对象加载数据
//            myChart.setOption(option);
            BMapExt.setOption(option);
        }
    </script>--%>


</body>
</html>