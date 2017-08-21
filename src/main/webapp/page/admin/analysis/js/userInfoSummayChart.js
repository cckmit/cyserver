
function chartOfUserInfoSummay(summayData) {

    $.ajax({
        url : './analysis/analysisAction!doNotNeedSecurity_userInfoSummary.action',
        dataType : 'json',
        async:false ,
        success : function(data)
        {
            if(data != null && data.length > 0) {
                summayData.userTotal = data[0].count ;      // 正式校友数
                summayData.authCount = data[1].count ;      // 被认证校友数
                summayData.miningCount = data[2].count ;    // 被挖掘校友数
            }
        }
    });
}
function userInfoSummayChartInit(myChart,userInfoSummayChartMain){
    var summayData = {userTotal:0,authCount:0,miningCount:0} ;
    chartOfUserInfoSummay(summayData) ;

    var auth = {name:"被认证校友",value:summayData.authCount} ;
    var mining = {name:"被挖掘校友",value:summayData.miningCount} ;
    if(summayData.userTotal != 0) {
        auth.name = (Math.floor((auth.value / summayData.userTotal) * 10000)/100) + "%" + auth.name ;
        mining.name = (Math.floor((mining.value / summayData.userTotal) * 10000)/100) + "%" + mining.name ;
    }

    var height = document.getElementById(userInfoSummayChartMain).offsetHeight;
    var avg = 0 ;
    avg = height / 12 ;
    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var placeHolderStyle = {
        normal : {
            color: 'rgba(235,235,235,0.2)',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: 'rgba(183,183,183,0.2)'
        }
    };
    var option = {
        title: {
            text: '校友统计',
            subtext: '正式校友数: '+summayData.userTotal,
            x: 'center',
            y: 'center',
            itemGap: 5,
            textStyle : {
                color : 'rgba(30,144,255,0.8)',
                fontFamily : '微软雅黑',
                fontSize : 10,
                fontWeight : 'bolder'
            }
        },
        tooltip : {
            show: true,
            formatter: "{b} : <br/>{c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : document.getElementById(userInfoSummayChartMain).offsetWidth / 2,
            y : avg,
            itemGap:avg / 2,
            data:[auth.name,mining.name]
        },
        toolbox: {
            show : true,
            feature : {
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name:'1',
                type:'pie',
                clockWise:false,
                radius : [height/2 - 2 * avg, height/2 - avg],
                itemStyle : dataStyle,
                data:[
                    {
                        value:summayData.authCount,
                        name:auth.name
                    },
                    {
                        value:summayData.userTotal - summayData.authCount,
                        name:'未被认证校友',
                        itemStyle : placeHolderStyle
                    }
                ]
            },
            {
                name:'2',
                type:'pie',
                clockWise:false,
                radius : [height/2 - 3*avg, height/2 - 2*avg],
                itemStyle : dataStyle,
                data:[
                    {
                        value:summayData.miningCount,
                        name:mining.name
                    },
                    {
                        value:summayData.userTotal - summayData.miningCount,
                        name:'未被挖掘校友',
                        itemStyle : placeHolderStyle
                    }
                ]
            }
        ]
    };

    myChart.clear();
    // 为echarts对象加载数据
    myChart.setOption(option);
}

function userInfoSummayEchart(userInfoSummayChartMain) {

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
            var myChart = ec.init(document.getElementById(userInfoSummayChartMain));
            userInfoSummayChartInit(myChart,userInfoSummayChartMain);

        }
    );

}