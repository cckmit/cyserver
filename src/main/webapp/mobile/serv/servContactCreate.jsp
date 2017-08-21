<!DOCTYPE html>
<html>

<%@ page language="java" pageEncoding="UTF-8" import="com.cy.util.*" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%

    String accountNum = request.getParameter("accountNum");

//String region = request.getParameter("region");
//String isAll =  request.getParameter("isAll");
    String category = request.getParameter("category");

    StringBuffer bu = new StringBuffer();

    bu.append("/mobile/serv/servContact.jsp?accountNum=");
    bu.append(accountNum);
    bu.append("&category=");
    bu.append(category);

    JSONObject titleObj = new JSONObject();
    titleObj.put("title", "联系学校");
    titleObj.put("backUrl", bu.toString());
    titleObj.put("backTitle","返回");

    String title = titleObj.toJSONString();
%>


<head>
    <title><%=title%></title>
    <meta name="Description" content="联系校友会"/>
    <meta name="Keywords" content="窗友,联系校友会"/>
    <meta name="author" content="Rainly"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="../css/cy_core.css">
    <link rel="stylesheet" href="../css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/contact.css">
</head>
<body>
<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
    <button id="submitid" class="ui-btn-lg ui-btn-primary">发 送</button>
</footer>
<form id="saveForm" method="post">
    <section class="ui-container create-post">
        <div class="ui-form-item ui-form-item-pure ui-border-b" style="background-color: #ffffff;margin: 10px 0px;">
            <label for="alumniId" style="color: #777777;">留言发送至:</label>
            <div style="margin-left: 85px;">
                <select name="cyContact.alumniId" id="alumniId" style="border: none; -moz-appearance: none; -webkit-appearance: none;background: transparent">
                    <option value="1">总会</option>
                </select>
            </div>
        </div>
        <div class="ui-form-item ui-form-item-pure ui-border-b" style="background-color: #ffffff">
            <label for="title" style="color: #777777;">标题:</label>
            <input type="text" id="title" name="cyContact.title" maxlength="120" style="padding-left: 45px;">
        </div>
        <div class="ui-form-item ui-form-item-pure ui-form-item-textarea ui-border-b" style="background-color: #ffffff">
            <textarea id="content" placeholder="说点什么..." name="cyContact.content"></textarea>
        </div>
        <input type="hidden" name="cyContact.accountNum" value="<%=accountNum %>"/>
        <input type="hidden" name="cyContact.category" value="<%=category %>"/>
    </section>
</form>

<script src="../js/cy_core.js" type="text/javascript"></script>
<script src="../js/zepto.min.js" type="text/javascript"></script>
<script src="../js/global.js" type="text/javascript"></script>
<script src="../js/dropload.min.js" type="text/javascript"></script>
<script src="../js/lrz.mobile.min.js"></script>
<script src="../js/B.js"></script>
<script type="text/javascript">

    var jsonStr = {
        "title":"发帖",
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
    var accountNum = '<%= accountNum %>';
    var category = '<%= category %>';
    function fallback() {

        window.location.href = B.serverUrl + "/mobile/serv/servContact.jsp?accountNum=" + accountNum + "&category="+category;

    }

    $(function(){
        // 查询当前用户已加入且为正式会员的所有分会
        $.ajax({
            type: 'post',
            url: 'mobServAction!doNotNeedSessionAndSecurity_selectAlumniByUserId.action?userId=<%=accountNum%>',
            dataType: 'json',
            success: function(data){
//                alert(JSON.stringify(data));
                if(data && data.length > 0){
                    for(var i = 0; i < data.length; i++){
                        $("#alumniId").append("<option value='" + data[i].alumniId + "'>" + data[i].alumniName + "</option>")
                    }
                }
            },
            error: function(xhr, type){
                // 保存失败
            }
        });

        $("#submitid").tap(function () {

            if ($('#title').val().trim() == '') {
                $.dialog({
                    title: '温馨提示',
                    content: '请输入标题',
                    button: ["确认"]
                });
                return;
            }

            if ($('#content').val().trim() == '') {
                $.dialog({
                    title: '温馨提示',
                    content: '请输入相关内容',
                    button: ["确认"]
                });
                return;
            }

            var tmpMsg = '';

            var dia = $.dialog({
                title: '温馨提示',
                content: '请确认信息填写无误',
                button: ["确认", "返回"]
            });

            dia.on("dialog:action", function (e) {
                console.log(e.index);
                if (e.index == 0) {

                    $.ajax({
                        url: 'mobServAction!doNotNeedSessionAndSecurity_insertContact.action?isWlight=true',
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
                                        window.location.href = 'servContact.jsp?category=<%=category %>&accountNum=<%=accountNum%>';
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
    });

</script>

</body>
</html>