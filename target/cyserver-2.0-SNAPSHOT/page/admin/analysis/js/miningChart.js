function chartOfMining(miningXAxisData,miningSeriesData) {

    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_chartOfMining.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null && data.length > 0) {
                for(var i = 0; i < data.length ;i++) {
                    miningXAxisData[i] = data[i].name ;
                    miningSeriesData[i] = parseInt(data[i].value) ;
                }
            }
        }
    });
}

function miningChartInit(myChart){
    var miningXAxisData = new Array() ;
    var miningSeriesData = new Array() ;
    chartOfMining(miningXAxisData,miningSeriesData) ;
    var option = {
        title : {
            text: '被挖掘校友数',
            subtext: '每月被挖掘校友数'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['被挖掘校友数']
        },
        toolbox: {
            show : true,
            feature : {
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : miningXAxisData
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'被挖掘校友数',
                type:'bar',
                data: miningSeriesData ,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };


    myChart.clear();
    // 为echarts对象加载数据
    myChart.setOption(option);
}


function miningEchart(miningChartMain) {

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
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById(miningChartMain));
            miningChartInit(myChart);

        }
    );

}