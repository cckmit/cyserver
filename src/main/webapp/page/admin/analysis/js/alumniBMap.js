
var geoCoordData = new Array() ;
var markPointData = new Array() ;
var markLineData = new Array() ;
var markLineSeriesData = new Array() ;
var school = "窗友科技大学" ;
var schoolCity = "武汉" ;
var schoolPoint = new Array();
var max = 0 ;

var myChart ;

function searchByStationName() {

    findProvincePoint();
    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_getSchoolPoint.action',
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
        url : './analysis/analysisAction!doNotNeedSecurity_chartOfAlumniCountMap.action',
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


(function () {
    require.config({
        paths: {
            echarts: 'jslib/echarts/dist'
        },
        packages: [
            {
                name: 'BMap',
                location: './jslib/echarts/dist/map',
                main: 'main'
            }
        ]
    });

    require(
    [
        'echarts',
        'BMap',
        'echarts/chart/map'
    ],
    function (echarts, BMapExtension) {
        // $('#main').css({
        //     height:$('body').height(),
        //     width: $('body').width()
        // });

        // 初始化地图
        var BMapExt = new BMapExtension($('#main')[0], BMap, echarts,{
            enableMapClick: false
        });
        var map = BMapExt.getMap();
        var container = BMapExt.getEchartsContainer();

        var startPoint = {
            x: 104.114129,
            y: 37.550339
        };

        var point = new BMap.Point(startPoint.x, startPoint.y);
        map.centerAndZoom(point, 5);
        map.enableScrollWheelZoom(true);
        // 地图自定义样式
        map.setMapStyle({
            styleJson: [
                  {
                       "featureType": "water",
                       "elementType": "all",
                       "stylers": {
                            "color": "#044161"
                       }
                  },
                  {
                       "featureType": "land",
                       "elementType": "all",
                       "stylers": {
                            "color": "#004981"
                       }
                  },
                  {
                       "featureType": "boundary",
                       "elementType": "geometry",
                       "stylers": {
                            "color": "#064f85"
                       }
                  },
                  {
                       "featureType": "railway",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "highway",
                       "elementType": "geometry",
                       "stylers": {
                            "color": "#004981"
                       }
                  },
                  {
                       "featureType": "highway",
                       "elementType": "geometry.fill",
                       "stylers": {
                            "color": "#005b96",
                            "lightness": 1
                       }
                  },
                  {
                       "featureType": "highway",
                       "elementType": "labels",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "arterial",
                       "elementType": "geometry",
                       "stylers": {
                            "color": "#004981"
                       }
                  },
                  {
                       "featureType": "arterial",
                       "elementType": "geometry.fill",
                       "stylers": {
                            "color": "#00508b"
                       }
                  },
                  {
                       "featureType": "poi",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "green",
                       "elementType": "all",
                       "stylers": {
                            "color": "#056197",
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "subway",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "manmade",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "local",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "arterial",
                       "elementType": "labels",
                       "stylers": {
                            "visibility": "off"
                       }
                  },
                  {
                       "featureType": "boundary",
                       "elementType": "geometry.fill",
                       "stylers": {
                            "color": "#029fd4"
                       }
                  },
                  {
                       "featureType": "building",
                       "elementType": "all",
                       "stylers": {
                            "color": "#1a5787"
                       }
                  },
                  {
                       "featureType": "label",
                       "elementType": "all",
                       "stylers": {
                            "visibility": "off"
                       }
                  }
            ]
        });

        var myChart = BMapExt.initECharts(container);
        window.onresize = myChart.onresize;
        var option = init();
        BMapExt.setOption(option);
        // init() ;
    }
);
})();


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
    return option ;




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

    // 为echarts对象加载数据
//            myChart.setOption(option);
//     BMapExt.setOption(option);
}