function getTopNum(num){
    if(num > 0 && num < 10) {
        return 10;
    }
    var length = (num.toString()).length ;
    var base = parseInt(Math.pow(10,length-1));

    var count = num / base ;
    return Math.floor(count) * base;

}

function chartOfUserInfoMap(splitList ,seriesData) {
    var max = 0 ;
    var avg = 0 ;
    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_chartOfUserInfoMap.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null && data.length > 0) {
                max = parseInt(data[0].value) ;
                for(var i=0;i<data.length ;i++){
                    avg += getTopNum(parseInt(data[i].value)) ;
                    seriesData[i] = data[i] ;
                }
                avg = avg / data.length;
            }
        }
    });

    if(avg > 0 ) {

        splitList[0] = {start: (avg / 2) * 3} ;
        splitList[1] = {start: (avg / 2) * 2,end: (avg / 2) * 3} ;
        splitList[2] = {start: (avg / 2) * 1,end: (avg / 2) * 2} ;
        splitList[3] = {end: (avg / 2) * 1} ;
    }
}
function userInfoMapChartInit(myChart){
    var splitList = new Array() ;
    var seriesData = new Array() ;
    chartOfUserInfoMap(splitList ,seriesData) ;
    var option = {
        title : {
            text: '各省校友',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            x:'left',
            data:['校友数']
        },
        dataRange: {
            x: 'left',
            y: 'bottom',
//                    splitList: [
//                        {start: 20},
//                        {start: 15, end: 20},
//                        {start: 10, end: 15},
//                        {start: 5, end: 10},
////                        {start: 10, end: 200},
////                        {start: 10, end: 200, label: '10 到 200（自定义label）'},
////                        {start: 5, end: 5, label: '5（自定义特殊颜色）', color: 'black'},
//                        {end: 5}
//                    ],
            splitList: splitList,
            color: ['#E0022B', '#E09107', '#A3E00B']
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            x: 'right',
            y: 'center',
            feature : {
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
                'china': true
            }
        },
        series : [
            {
                name: '校友数',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{
                        label:{
                            show:true,
                            textStyle: {
                                color: "rgb(249, 249, 249)"
                            }
                        }
                    },
                    emphasis:{label:{show:true}}
                },
                data:seriesData
            }
        ]
    };



    myChart.clear();
    // 为echarts对象加载数据
    myChart.setOption(option);
}

function userInfoMapEchart(userInfoMapMain) {

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
            'echarts/chart/line',
            'echarts/chart/pie',
            'echarts/chart/map',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById(userInfoMapMain));
            userInfoMapChartInit(myChart);

        }
    );

}