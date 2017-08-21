function searchByStationName(alumniMapMarkLineData,baseJson,alumniMapGeoCoordData,alumniMapSchoolPoint) {

    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_getSchoolPoint.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null) {
                baseJson.alumniMapSchoolCity = data.schoolCity ;
                baseJson.alumniMapSchool = data.school ;
                alumniMapSchoolPoint[0] = data.lng ;
                alumniMapSchoolPoint[1] = data.lat ;
                alumniMapGeoCoordData[baseJson.alumniMapSchool]=alumniMapSchoolPoint;

            }
        }
    });
    findProvincePoint(alumniMapMarkLineData,alumniMapGeoCoordData,baseJson);
}

function chartOfAlumniMap(baseJson,alumniMapMarkPointData,alumniMapMarkLineSeriesData) {
    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_chartOfAlumniCountMap.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null && data.length > 0) {
                // alumniMapMarkPointData.concat(data) ;
                for(var i = 0 ; i< data.length ;i++) {
                    alumniMapMarkPointData[i] = data[i] ;
                }
                for(var i = 0 ; i< data.length ;i++) {
                    if(data[i].value > baseJson.alumniMapMax) {
                        baseJson.alumniMapMax = data[i].value;
                    }
                    alumniMapMarkLineSeriesData[i] = [{name:baseJson.alumniMapSchool},{name:data[i].name,value:data[i].value}] ;
                }
            }
        }
    });
}

function findProvincePoint(alumniMapMarkLineData,alumniMapGeoCoordData,baseJson) {

    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_findProvincePoint.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null) {
                for (var key in data.pointMap)
                {
                    alumniMapGeoCoordData[key] = data.pointMap[key] ;
                }
                for(var i = 0 ; i< data.nameList.length ;i++) {
                    alumniMapMarkLineData[i] = [{name:baseJson.alumniMapSchool},{name:data.nameList[i]}] ;
                }

            }
        }
    });
}

function alumniMapChartInit(myChart){
    var alumniMapGeoCoordData       = {} ;
    var alumniMapMarkPointData      = new Array() ;
    var alumniMapMarkLineData       = new Array() ;
    var alumniMapMarkLineSeriesData = new Array() ;
    var alumniMapSchoolPoint        = new Array();
    var baseJson = {alumniMapSchool:"皖西学院",alumniMapSchoolCity:"六安",alumniMapMax:0} ;
    searchByStationName(alumniMapMarkLineData,baseJson,alumniMapGeoCoordData,alumniMapSchoolPoint);
    chartOfAlumniMap(baseJson,alumniMapMarkPointData,alumniMapMarkLineSeriesData) ;
    var alumniMapSchool             = baseJson.alumniMapSchool     ;
    var alumniMapSchoolCity         = baseJson.alumniMapSchoolCity ;
    var alumniMapMax                = baseJson.alumniMapMax        ;
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
            data:[alumniMapSchool],
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
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        dataRange: {
            min : 0,
            max : alumniMapMax,
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
                    data : alumniMapMarkLineData
                },
                geoCoord:alumniMapGeoCoordData
            },
            {
                name: alumniMapSchool,
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
                    data : alumniMapMarkLineSeriesData
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
                    data : alumniMapMarkPointData
                }
            }
        ]
    };

    myChart.clear();
    // 为echarts对象加载数据
    myChart.setOption(option);
}


function alumniMapEchart(alumniMapChartMain) {
    // 路径配置
    require.config({
        paths: {
            echarts: 'jslib/echarts/dist'
        }
    });

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/map'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById(alumniMapChartMain));
            alumniMapChartInit(myChart);

        }
    );

}