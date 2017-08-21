<!DOCTYPE html>
<html>

<%@ page language="java" pageEncoding="UTF-8" import="com.cy.util.*" %>

<%

    String accountNum = request.getParameter("accountNum");

    String category = request.getParameter("category") == null ? "1" : request.getParameter("category");

    String id = request.getParameter("id");

    String isWhat = request.getParameter("isWhat");
%>

<head>
    <title>发布招聘</title>
    <meta name="Description" content="发布招聘"/>
    <meta name="Keywords" content="窗友,发布招聘"/>
    <meta name="author" content="Rainly"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="../css/cy_core.css">
    <link rel="stylesheet" href="../css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/appeal.css">
    <script src="../js/B.js" type="text/javascript" ></script>
    <style>
        select {
            /*background: url(../../images/icon_chevron_right.png) no-repeat 100%;*/
            height: 29px;
            width: 100%;
            overflow: hidden;
            text-align: right;
        }

        .labelheader_qzzp {
            font-size: 14px;
            color: #4AA7DE;
            background: #ffffff;
            border-top: 1px solid #F9F9FB;
            height: 25px;
            padding: 10px 0px 0px 15px;
            margin: 15px 0px 0px 0px;
        }

        .rowstyle {
            border-bottom: 1px solid #e0e0e0;
        }

        .tableFrame {
            width: 100%;
            margin-top: 10px;
        }

        .tableFrame tr th {
            width: 10px;
        }

        .tableFrame tr th, .tableFrame tr td {
            background: #ffffff;
            border-bottom: 1px solid #e0e0e0;
        }

        .tableFrame tr td {
        }

        #qzzptable {
            width: 100%;
        }

        #qzzptable tr td {
            padding: 2px 0px 2px 0px;
            border-bottom: 1px solid #e0e0e0;
            height: 40px;
            background: #ffffff;
            color: #A9A9A9;
        }

        #qzzptable tr td input, #qzzptable tr td select {
            color: #A9A9A9;
            border-width: 0px;
        }

        #qzzptable tr td input {
            width: 300px;
        }
    </style>
    <script>

        function doit1() {
            $("#select1").click();
//      $('#select1').click(function(){
//        alert(111);
//      })
        }
    </script>
</head>
<body style="background-color:#EFEFF4;">

<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
    <button class="ui-btn-lg ui-btn-primary" id="sumbit">发 送</button>
</footer>
<form id="saveForm" name="f1" method="post">
    <section class="ui-container create-post">
        <div style="background-color: white">
            <div class="ui-form-item ui-border-b">
                <label for="title" style="color: #777777;">职位名称:</label>
                <input type="text" name="title" id="title" value="" size="20" style="padding-left: 70px;margin-left: 10px;">
            </div>
            <div class="ui-form-item ui-border-b">
                <label for="address" style="color: #777777;">工作地点:</label>
                <input type="text" name="address" id="address" value="" size="20" style="padding-left: 70px;margin-left: 10px;">
            </div>
            <div class="ui-form-item ui-border-b">
                <label for="type" style="color: #777777;">职业类型:</label>
                <div style="margin-left: 75px;">
                    <select name="type" id="type" style="border: none; -moz-appearance: none; -webkit-appearance: none;background: transparent">
                        <option value="全职">全职</option>
                        <option value="实习">实习</option>
                        <option value="兼职">兼职</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="ui-form-item ui-border-b" style="height:30px;line-height:30px;color: #777777;">岗位描述:</div>

        <div class="ui-form-item ui-form-item-pure ui-form-item-textarea ui-border-b" style="margin-bottom: 0px;">
            <textarea name="description" id="description" placeholder="岗位详情描述"></textarea>
        </div>
        <div class="ui-form-item ui-form-item-pure ui-form-item-textarea ui-border-b" style="margin-top: 0px;">
            <textarea name="requirement" id="requirement" placeholder="岗位要求描述"></textarea>
        </div>
    </section>
    <input type="hidden" name="cyServ.region" id="regionId" value=""/>
    <input type="hidden" name="cyServ.type" id="helpType" value="9"/>
</form>

<script src="../js/zepto.min.js" type="text/javascript"></script>

<script src="../js/global.js" type="text/javascript"></script>
<script src="../js/dropload.min.js" type="text/javascript"></script>
<script src="../js/lrz.mobile.min.js"></script>
<script src="../js/B.js" type="text/javascript" ></script>
<script type="text/javascript">

    var jsonStr = {
        "title":"发布招聘",
        "btn1":{
            "imgname":"icon_Back@2x.png",
            "imgversion":"20170214",
            "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABoAAAAsCAYAAAB7aah+AAAAAXNSR0IArs4c6QAAAN1JREFUWAntlkEOgkAMRRkTVl7EA3gPD+D53MjGa7lyr4vxF2gghkIHqnHxmzQdMp3+6YOkVFWw5ZxP8Cu8Di49lOtFnohiN3i8GIpKJyqCZWuxYig5JdJrdZ3thsbXrUQEJxt4PCa90kIn0tF2dBRR3K5IXC5MmkRcSsIVicuFSZOIS0l4owy+M3xuaF1SSi9vQTMP76aGy4CyTP4DZIpuNxSiGDHaBPiB2GwKdoixAJadSow2m4Kdf8N4x4X2Bfe3U2c6e2DvaJ9csTMhFi+i9xqJfU/kQ+ygzz+Pb1Kohi6oSMRTAAAAAElFTkSuQmCC",
            "name":"返回",
            "action":"fallback"
        }/*,
         "btn2":{
         "imgname":"icon_add@2x.png",
         "imgversion":"20170214",
         "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAADgAAAA4CAYAAACohjseAAAAAXNSR0IArs4c6QAAAMRJREFUaAXt2UsKwzAAA9E4979zPhfwLIxhMJOtaKroGQrNuDZfz3fNvmJ81yxfze7VG9g/3wPahahfgrSQPU/QLkT9EqSF7HmCdiHqlyAtZM8TtAtRvwRpIXueoF2I+iVIC9nzBO1C1G/Q/5Z0A3veEbULUb8EaSF7nqBdiPodL7j13dy/Lv3O9n6QziDkxx/RHhBOgD5OUE8EBROEgfRxgnoiKJggDKSPE9QTQcEEYSB9nKCeCAomCAPp4wT1RFDweMEXDEkMapUbW8YAAAAASUVORK5CYII=",
         "name":"添加",
         "action":"added"
         },
         "btn3":{
         "imgname":"icon_more_detail@2x.png",
         "imgversion":"20170214",
         "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAAAQAAAASCAYAAAB8fn/4AAAAAXNSR0IArs4c6QAAADBJREFUGBlj+P//vycQP4ZiTwYoA0iBwWMmBnQAFEfVgq6ACD6GGUABkBtgYPBaCwAdi4T3K1E4vwAAAABJRU5ErkJggg==",
         "name":"",
         "droplist":[
         {
         "name":"分享",
         "action":"share"
         },
         {
         "name":"复制",
         "action":"copy"
         },
         {
         "name":"删除",
         "action":"deleted"
         }
         ]
         }*/
    }
    function menuConfig() {
        window.webkit.messageHandlers.AppModel.postMessage({body: jsonStr});
        return jsonStr;

    }
    function jsonConfig() {
        var str = JSON.stringify(jsonStr);
        window.stub.jsMethod(str);
        return jsonStr;

    }
    var isWhat = '<%=isWhat%>';
    var accountNum = '<%=accountNum%>';
    var category = '<%=category%>';
    var id = '<%= id %>'
    function fallback() {

        if(id  && id != '' && id != 'null'){
            window.location.href = B.serverUrl + "/mobile/serv/favourDetailQzzp.jsp?accountNum=" + accountNum + "&category=" + category + "&isWhat=" + isWhat + "&id=" + id;
        }else{
            if(isWhat && isWhat != '' && isWhat != 'null'){
                if(isWhat == 1){
                    window.location.href = B.serverUrl + "/mobile/serv/favourListQzzp.jsp?accountNum=" + accountNum + "&category=" + category + "&isWhat=" + isWhat;
                }else{
                    window.location.href = B.serverUrl + "/mobile/serv/favourMyListQzzp.jsp?accountNum=" + accountNum + "&category=" + category + "&isWhat=" + isWhat;
                }
            }else{
                return;
            }
        }



    }

    <%--
    function getCityFromProvince(){

            //alert($('#regionProvinceId').val());

        $.getJSON("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobCapitalFromProvince.action?provinceId="+$('#regionProvinceId').val(), function(items){
            $('#regionCityId').empty();
            for(var i = 0; i < items.length; i++){
                $('#regionCityId').append('<option value="' + items[i]['cityName'] + '">' + items[i]['cityName'] + '</option>');
            }
            $('#regionCityId').trigger('change');

        });

    }


    $.getJSON("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobProvinceAndId.action", function(items){
        for(var i = 0; i < items.length; i++){
            $('#regionProvinceId').append('<option value="' + items[i]['id'] + '">' + items[i]['provinceName'] + '</option>');
        }
        $('#regionProvinceId').trigger('change');
        //alert($('#regionProvinceId').value);
    });
    --%>

    $('#sumbit').click(function () {

        if ($('#title').val().trim() == '') {
            $.dialog({
                title: '温馨提示',
                content: '请输入职位名称',
                button: ["确认"]
            });
            return;
        }

        if ($('#address').val().trim() == '') {
            $.dialog({
                title: '温馨提示',
                content: '请输入工作地点',
                button: ["确认"]
            });
            return;
        }

        if ($('#description').val().trim() == '') {
            $.dialog({
                title: '温馨提示',
                content: '请输入岗位详情描述',
                button: ["确认"]
            });
            return;
        }

        if ($('#requirement').val().trim() == '') {
            $.dialog({
                title: '温馨提示',
                content: '请输入岗位要求描述',
                button: ["确认"]
            });
            return;
        }

        var tmpMsg = '';

        var dia = $.dialog({
            title: '温馨提示',
            content: '请确认信息填写无误',
            button: ["确认创建", "返回修改"]
        });

        dia.on("dialog:action", function (e) {
            console.log(e.index);
            if (e.index == 0) {

                //创建时正在加载
                var manban=$('<div id="manban" style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 9999; background: transparent;"><div style=" position: absolute; left: 50%; top: 50%; margin-left: -65px; margin-top: -55px; width: 130px; height: 110px; display: -webkit-box; -webkit-box-orient: vertical; -webkit-box-align: center; text-align: center; background: rgba(0, 0, 0, 0.65); border-radius: 6px; color: #fff; font-size: 16px;"><img style="width: 37px; margin-top:20px;margin-bottom: 5px" src="../img/loading-jiazai.gif"><p style="margin: 0;">加载中...</p></div></div>');
                $("body").append(manban);

                $.ajax({
                    url: 'mobServAction!doNotNeedSessionAndSecurity_addQzzp.action?accountNum=<%=accountNum%>&category=<%=category%>',
                    data: $('form').serialize(),
                    dataType: 'json',
                    type: "post",
                    success: function (result) {
                        if (result.success) {
                            var dia2 = $.dialog({
                                title: '温馨提示',
                                content: result.msg,
                                button: ["确认"]
                            });

                            dia2.on("dialog:action", function (e) {
                                console.log(e.index);

                                if (e.index == 0) {

                                    //删除正在加载
                                    //获取div节点对象
                                    var divNode2 = document.getElementById("manban");
                                    //获取父节点
                                    var parentNode = divNode2.parentNode;
                                    parentNode.removeChild(divNode2);

                                    window.location.href = 'favourMyListQzzp.jsp?accountNum=<%=accountNum%>&isWhat=2&category=<%=category%>';
                                }
                            });

                        } else {
                            var dia2 = $.dialog({
                                title: '温馨提示',
                                content: result.msg,
                                button: ["确认"]
                            });
                        }
                    },
                    beforeSend: function () {

                    },
                    complete: function () {

                    }
                });


            }
        });


        dia.on("dialog:hide", function (e) {
            console.log("dialog hide");
        });
        dia2.on("dialog:hide", function (e) {
            console.log("dialog hide");
        });


    });


</script>

</body>
</html>