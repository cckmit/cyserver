<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">

        function removeCodeImage(codeImage) {
            $(codeImage).parent().remove();
            $("#codeImageBtn").prop('disabled', false);
        }

        $(function () {

            var codeImageBtn = $("#codeImageBtn");
            new AjaxUpload(codeImageBtn, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        $('#codeImage').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeCodeImage(this)"></div><input type="hidden" name="weiXinAccount.codeImage" value="'+msg.url+'"/></div>');
                        $("#codeImageBtn").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });


            $.ajax({
                url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!initSetting.action?accountType=' +${param.accountType},
                dataType: 'json',
                success: function (result) {
                    if (result != null && result.id != undefined) {
                        $('form').form('load', {
                            'weiXinAccount.accountToken': result.accountToken,
                            'weiXinAccount.accountNumber': result.accountNumber,
                            'weiXinAccount.accountType': result.accountType,
                            'weiXinAccount.accountEmail': result.accountEmail,
                            'weiXinAccount.accountDesc': result.accountDesc,
                            'weiXinAccount.accountAccessToken': result.accountAccessToken,
                            'weiXinAccount.accountAppId': result.accountAppId,
                            'weiXinAccount.accountAppSecret': result.accountAppSecret,
                            'weiXinAccount.encryptMessage': result.encryptMessage,
                            'weiXinAccount.encodingAesKey': result.encodingAesKey,
                            'weiXinAccount.partner': result.partner,
                            'weiXinAccount.partnerKey': result.partnerKey,
//                            'weiXinAccount.codeImage': result.codeImage,
                            'weiXinAccount.addTokenTime': result.addTokenTime,
                            'weiXinAccount.subscribeType': result.subscribeType,
                            'weiXinAccount.welcomes': result.welcomes,
                            'weiXinAccount.newsId': result.newsId,
                            'weiXinAccount.news': result.news,
                        });


                        if(result.codeImage != null || $.trim(result.codeImage) != '') {
                            $('#codeImage').append('<div style="float:left;width:180px;"><img src="'+result.codeImage+'" width="150px" height="150px"/><div class="bb001" onclick="removeCodeImage(this)"></div><input type="hidden" name="weiXinAccount.codeImage" value="'+result.codeImage+'"/></div>');
                            $("#codeImageBtn").prop('disabled', 'disabled');
                        }


//                        alert(JSON.stringify(result.news));
                        if (result.subscribeType != undefined  && result.subscribeType != '') {
                            $('#imgText').hide();
                            if (result.subscribeType == 20) {
                                $('#typeName').text("图文");
                                if (result.newsId != undefined && result.newsId != '' && result.news != undefined) {
                                    $('#text').show();
                                    $('#newsDetail').show();
                                    addhtml(result.news);
                                }else {
                                    $('#imgText').show();
                                    $('#text').hide();
                                    $('#type').combobox('setValue', '');
                                }
                            } else if (result.subscribeType == 10) {
                                if (result.welcomes != undefined && result.welcomes != '') {
                                    $('#typeName').text("文本");
                                    $('#welcomes').show();
                                    $('#text').show();
                                }else {
                                    $('#imgText').show();
                                    $('#text').hide();
                                    $('#type').combobox('setValue', '');
                                }
                            }else {
                                $('#imgText').show();
                            }
                        } else {
                            $('#imgText').show();
                            $('#text').hide();
                        }
                    }else {
                        $('#imgText').show();
                    }
                },
                beforeSend: function () {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                complete: function () {
                    // window.location.reload();
                    parent.$.messager.progress('close');

                }
            });
        });
        function save($dialog, $grid, $pjq) {
            console.log($('form').form('validate'));
            if ($('form').form('validate')) {
                var type = $('#type').combobox("getValue");
                if (type == undefined || type == '') {
                    parent.$.messager.alert('提示', '请选择关注欢迎类型', 'error');
                    return false;
                }
                if (type == 10) {
                    $('#newsId').remove()
                    if ($('#welcom').val() == '') {
                        $.messager.alert('提示', '请输入欢迎语', 'error');
                        return false;
                    }
                } else if (type = 20) {
                    $('#welcom').remove();

                    if ($('#id').combobox('getValue') == '' || $('#id').combobox('getValue') == undefined) {
                        if ($('#channel').combobox('getValue') == '' || $('#channel').combobox('getValue') == undefined) {
                            $.messager.alert('提示', '请选择新闻渠道', 'error');
                            return false;
                        } else if ($('#catalogId').combotree('getValue') == '' || $('#catalogId').combotree('getValue') == undefined) {
                            $.messager.alert('提示', '请选择新闻栏目', 'error');
                            return false;
                        } else {

                            $.messager.alert('提示', '请选择新闻', 'error');
                            return false;
                        }

                    }
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!save.action?',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            window.location.reload();
                            parent.$.messager.alert('提示', result.msg,
                                    'info');
                        } else {
                            parent.$.messager.alert('提示', result.msg,
                                    'error');
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据提交中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');

                    }
                });
            }
        }
        ;
        function change() {
            $('#text').hide();
            $('#newsDetail').hide();
            $('#imgText').show();
            $('#type').combobox('setValue', '');
        }
        function addhtml(news) {

            var html = '<td style="text-align: center">' + news.title + '</td><td style="text-align: center">' + news.channelName + '</td><td style="text-align: center">' + news.type + '</td><td style="text-align: center">' + news.mainName + '</td><td style="text-align: center">' + news.dept_name + '</td>';
            if (news.channels == undefined || news.channels ==''){
                html =html+ '<td style="text-align: center"></td>'
            }else {
                html =html+'<td style="text-align: center">' + '(' + news.channels.replace(/_/g, ')[').replace(/,/g, ']<br>(') + ']' + '</td>';
            }
            html = html +'<td style="text-align: center">' + news.createDate + '</td><td style="text-align: center">' + news.status + '</td>';
            $('#content').html(html);
        }
        function getNewsById(newsId) {

            if (newsId != null && newsId != '') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_newsDetail.action',
                    data: {'id': newsId},
                    dataType: 'json',
                    success: function (result) {
                        if (result != null && result != '') {
                            $('#newsDetail').show();
                            addhtml(result);
                        }
                    }
                })
            }
        }
    </script>
</head>

<body>
<form method="post" class="form">
    <input name="weiXinAccount.id" type="hidden"/>
    <input name="weiXinAccount.accountType" type="hidden" value="${param.accountType}">
    <fieldset>
        <legend>${param.accountType eq 10 ? '校友会':'基金会'}公众号基本信息</legend>
        <table class="ta001">

            <tr>
                <th>公众微信号</th>
                <td><input name="weiXinAccount.accountNumber"
                           class="easyui-validatebox"
                           style="width: 300px;" required/>
                </td>
            </tr>
            <tr>
                <th>电子邮箱</th>
                <td><input name="weiXinAccount.accountEmail" class="easyui-validatebox"
                           data-options="validType:'email'" style="width: 300px;" required/>
                </td>
            </tr>
            <tr>
                <th>公众帐号名称</th>
                <td>
                    <input hidden name="weiXinAccount.accountName" value="${param.accountType eq 10 ? '校友会':'基金会'}公众号">
                    ${param.accountType eq 10 ? '校友会':'基金会'}公众号
                </td>
            </tr>

            <tr>
                <th>公众帐号APPID</th>
                <td><input name="weiXinAccount.accountAppId"
                           class="easyui-validatebox"
                           style="width: 300px;" required/>
                </td>
            </tr>
            <tr>
                <th style="width: 120px">公众帐号APPSECRET</th>
                <td><input  name="weiXinAccount.accountAppSecret"
                              class="easyui-validatebox" style="width: 300px;" required>
                </td>
            </tr>
            <tr>
                <th>公众帐号TOKEN</th>
                <td><input  name="weiXinAccount.accountToken"
                              class="easyui-validatebox" style="width: 300px;" required>
                </td>
            </tr>

            <tr>
                <th>公众帐号描述</th>
                <td><textarea rows="5" cols="100" name="weiXinAccount.accountDesc"></textarea>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<th>ACCESS_TOKEN</th>--%>
                <%--<td><textarea rows="5" cols="100" name="weiXinAccount.accountAccessToken"--%>
                              <%--class="easyui-validatebox" required></textarea>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>TOKEN获取时间</th>--%>
                <%--<td>--%>
                    <%--<input name="weiXinAccount.addTokenTime" class="easyui-datetimebox"--%>
                           <%--data-options="editable:false,validType:'customRequired'"--%>
                           <%--style="width: 150px;" required/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <th>消息加解密方式</th>
                <%--<td><input name="weiXinAccount.encryptMessage" class="easyui-validatebox" style="width: 300px;" required/></td>--%>
                <td>
                    <input type="radio" name="weiXinAccount.encryptMessage" value="10" style="width: 20px;" checked>不加密
                    <input type="radio" name="weiXinAccount.encryptMessage" value="20" style="width: 20px;">加密
                </td>
            </tr>
            <tr>
                <th>消息加密密钥</th>
                <td>
                    <input  name="weiXinAccount.encodingAesKey" class="easyui-validatebox"
                            style="width: 370px;" required>
                </td>
            </tr>
            <tr>
                <th>微信支付商户号</th>
                <td>
                    <input  name="weiXinAccount.partner" class="easyui-validatebox"
                            style="width: 370px;">
                </td>
            </tr>
            <tr>
                <th>微信支付商户API密钥</th>
                <td>
                    <input  name="weiXinAccount.partnerKey" class="easyui-validatebox"
                            style="width: 370px;">
                </td>
            </tr>
            <tr>
                <th>微信公共号二维码上传</th>
                <td>
                    <input type="button" id="codeImageBtn" value="上传二维码图片">
                </td>
            </tr>
            <tr>
                <th>微信公共号二维码</th>
                <td>
                    <div id="codeImage">
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    关注欢迎类型
                </th>
                <td colspan="3">
                    <div id="text" hidden>
                        <span id="typeName"></span>
                        <a class="easyui-linkbutton"
                           data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="change()">变更</a>
                    </div>
                    <div id="imgText" hidden>
                        <select id="type" class="easyui-combobox" name="weiXinAccount.subscribeType" data-options="
                        value:'',
                        editable:false,
                        prompt:'--请选择类型--',
                        onSelect: function(value){
                        value = value.value;
                        if (value == '10') {
                            $('#news').hide();
                            $('#newsDetail').hide();
                            $('#welcomes').show();
                        } else if (value == '20') {
                            $('#news').show();
                            $('#welcomes').hide();
                            $('#newsDetail').hide();
                        }
						}
                    ">
                            <option value="10">文本</option>
                            <option value="20">图文</option>
                        </select>
                    </div>
                </td>
            </tr>
            <tr id="welcomes" hidden>
                <th>关注欢迎语</th>
                <td>
                    <textarea rows="3" cols="100" id="welcom" name="weiXinAccount.welcomes"
                              class="easyui-validatebox"></textarea>
                </td>
            </tr>

        </table>
    </fieldset>
    <fieldset id="news" hidden>
        <legend>关注欢迎新闻</legend>
        <table class="ta001">
            <tr>
                <th>关注新闻渠道</th>
                <td colspan="3">
                    <select class="easyui-combobox" id="channel" name="weiXinAccount.channel" data-options="
                        value:'',
                        editable:false,
                        prompt:'--请选择渠道--',
                        onSelect: function(value){
                        value = value.value;
                        url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+value;
                        $('#column').show();
                        $('#catalogId').combotree('clear');
                        $('#catalogId').combotree('reload', url);
						}
                    ">
                        <option value="10">手机</option>
                        <option value="20">网页</option>
                        <option value="30">微信</option>
                    </select>
                </td>
            </tr>
            <tr id="column" hidden>
                <th>新闻栏目</th>
                <td>
                    <select id="catalogId" class="easyui-combotree" style="width: 150px;"
                            data-options="
								editable:false,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								prompt:'--请选择新闻栏目--',
								onSelect: function(rec){
								    channel = $('#channel').combobox('getValue');
								    url ='${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_newsList.action?category='+rec.id+'&channel='+channel;
								    $('#newsId').show();
								    $('#id').combobox('clear');
								    $('#id').combobox('reload', url);
								}
								">

                    </select>
                </td>
            </tr>
            <tr id="newsId" hidden>
                <th>新闻</th>
                <td>
                    <select id="id" name="weiXinAccount.newsId" class="easyui-combobox" style="width: 500px"
                            data-options="
									editable:false,
									valueField:'newsId',
									textField:'title',
									prompt:'--请选择新闻--',
									onSelect: function(rec){
								   getNewsById(rec.newsId);
								}
									">
                    </select>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset id="newsDetail" hidden>
        <legend>关注欢迎新闻详情</legend>
        <table class="ta001">
            <tr style="text-align: center">
                <th style="text-align: center">标题</th>
                <th style="text-align: center">频道</th>
                <th style="text-align: center">兴趣标签</th>
                <th style="text-align: center">新闻来源</th>
                <th style="text-align: center">所属组织</th>
                <th style="text-align: center">新闻栏目</th>
                <th style="text-align: center">时间</th>
                <th style="text-align: center">状态</th>
            </tr>
            <tr id="content">

            </tr>
        </table>
    </fieldset>
    <table align="center">
        <tr>
            <td>
                <a href="javascript:void(0);" class="easyui-linkbutton"
                   data-options="iconCls:'ext-icon-save',plain:true" onclick="save();">保存</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
