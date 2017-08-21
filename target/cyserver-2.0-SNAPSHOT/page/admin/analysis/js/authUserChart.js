
function chartOfRegistered(registeredLegendData,registeredSeriesData) {

    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_chartOfRegistered.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null && data.length > 0) {
                for(var i = 0; i < data.length ;i++) {
                    registeredLegendData[i] = data[i].name ;
                    registeredSeriesData[i] = {"name":data[i].name,"value":data[i].value}
                }
            }
        }
    });
}
function authUserChartInit(myChart){
    var registeredLegendData = new Array() ;
    var registeredSeriesData = new Array() ;
    chartOfRegistered(registeredLegendData,registeredSeriesData) ;
    var option = {
        title : {
            text: '已被认证/未被认证校友数',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} :<br/> {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:registeredLegendData
        },
        calculable : true,
        series : [
            {
                name:'校友数',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:registeredSeriesData
            }
        ]
    };
    myChart.clear();
    // 为echarts对象加载数据
    myChart.setOption(option);
}

function authUserEchart(authUserChartMain) {

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
            var myChart = ec.init(document.getElementById(authUserChartMain));
            authUserChartInit(myChart);

        }
    );

}