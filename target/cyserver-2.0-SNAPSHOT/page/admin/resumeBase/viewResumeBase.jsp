<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/26
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
            <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

        <!DOCTYPE html PUBLIC>
        <html>
        <head>
            <base href="<%=basePath%>">
            <title></title>
            <meta http-equiv="pragma" content="no-cache">
            <meta http-equiv="cache-control" content="no-cache">
            <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=j5bQGIAXNd5rrnfu83is90HhX1n3xvMd"></script>
            <meta http-equiv="expires" content="0">
            <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
            <meta http-equiv="description" content="This is my page">
            <jsp:include page="../../../inc.jsp"></jsp:include>

            <script type="text/javascript">

                $(function () {
                    var id = $('#resumeBaseId').val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/resumeBase/resumeBaseAction!getById.action',
                        data: { 'resumeBaseId' : id },
                        dataType: 'json',
                        success: function (result) {

                            if (result.id != undefined) {
                                console.log(result);
                                $('#name').text(result.name?result.name:"");
                                $('#sex').text(result.sex?result.sex:"");
                                $('#birthday').text(result.birthday?result.birthday:"");
                                $('#experienceMin').text(result.experienceMin?result.experienceMin:"");
                                $('#experience').text(result.experience?result.experience:"");
                                $('#city').text(result.city?result.city:"");
                                $('#telephone').text(result.telephone?result.telephone:"");
                                $('#email').text(result.email?result.email:"");
                                $('#placeOrigin').text(result.placeOrigin?result.placeOrigin:"");
                                if(result.resumeEducations && result.resumeEducations.length > 0) {
                                    var resumeEducations = result.resumeEducations;
                                    for(var i in resumeEducations){
                                        var domNode =
                                            '<tr><th>学习经历'+(parseInt(i)+1)+'</th>' +
                                                '<td>'+
                                                '学校：'+resumeEducations[i].schoolName+
                                                '<br>学历：'+resumeEducations[i].education+
                                                '<br>专业：'+resumeEducations[i].profession+
                                                '<br>时间：'+resumeEducations[i].startDate+'～'+resumeEducations[i].endDate+
                                                '</td>'+
                                            '</tr>';
                                        $('#re').append(domNode);
                                    }
                                }
                                if(result.resumeWorkExperiences && result.resumeWorkExperiences.length > 0) {
                                    var resumeWorkExperiences = result.resumeWorkExperiences;
                                    for(var i in resumeWorkExperiences){
                                        var domNode =
                                            '<tr><th>工作经验'+(parseInt(i)+1)+'</th>' +
                                            '<td>'+
                                            '公司名：'+resumeWorkExperiences[i].companyName+
                                            '<br>职位：'+resumeWorkExperiences[i].positionName+
                                            '<br>时间：'+resumeWorkExperiences[i].startDate+'～'+resumeEducations[i].endDate+
                                            '<br>描述：'+resumeWorkExperiences[i].desc+
                                            '</td>'+
                                            '</tr>';
                                        $('#rwe').append(domNode);
                                    }
                                }

                                if(result.resumeProjectExperiences && result.resumeProjectExperiences.length > 0) {
                                    var resumeProjectExperiences = result.resumeProjectExperiences;
                                    for(var i in resumeProjectExperiences){
                                        var domNode =
                                            '<tr><th>项目经验'+(parseInt(i)+1)+'</th>' +
                                            '<td>'+
                                            '项目名：'+resumeProjectExperiences[i].projectName+
                                            '<br>时间：'+resumeProjectExperiences[i].startDate+'～'+resumeEducations[i].endDate+
                                            '<br>描述：'+resumeProjectExperiences[i].desc+
                                            '</td>'+
                                            '</tr>';
                                        $('#rpe').append(domNode);
                                    }
                                }

                                if(result.resumeSkills && result.resumeSkills.length > 0) {
                                    var resumeSkills = result.resumeSkills;
                                    for(var i in resumeSkills){
                                        var domNode =
                                            '<tr><th>技能'+(parseInt(i)+1)+'</th>' +
                                            '<td>'+
                                            '技能：'+resumeSkills[i].skillName+
                                            '<br>熟练度：'+resumeSkills[i].proficiency+
                                            '</td>'+
                                            '</tr>';
                                        $('#rs').append(domNode);
                                    }
                                }
                                if(result.resumeRewardAtSchools && result.resumeRewardAtSchools.length > 0) {
                                    var resumeRewardAtSchools = result.resumeRewardAtSchools;
                                    for(var i in resumeRewardAtSchools){
                                        var domNode =
                                            '<tr><th>在校获奖'+(parseInt(i)+1)+'</th>' +
                                            '<td>'+
                                            '奖项：'+resumeRewardAtSchools[i].projectName+
                                            '<br>等级：'+resumeRewardAtSchools[i].level+
                                            '<br>时间：'+resumeRewardAtSchools[i].time+
                                            '<br>描述：'+resumeRewardAtSchools[i].desc+
                                            '</td>'+
                                            '</tr>';
                                        $('#rras').append(domNode);
                                    }
                                }
                                if(result.resumeCertificates && result.resumeCertificates.length > 0) {
                                    var resumeCertificates = result.resumeCertificates;
                                    for(var i in resumeCertificates){
                                        var domNode =
                                            '<tr><th>证书'+(parseInt(i)+1)+'</th>' +
                                            '<td>'+
                                            '证书：'+resumeCertificates[i].certificateName+
                                            '<br>时间：'+resumeCertificates[i].time+
                                            '</td>'+
                                            '</tr>';
                                        $('#rc').append(domNode);
                                    }
                                }


                                if(result.headPic!=undefined){
                                    $('#headPic').append('<div style="float:left;margin-bottom: 5px; margin-left: 5px; position:relative"><img src="'+result.headPic+'" width="80px" height="80px"/><div class="bb001" style="top:-10px; left:65px; position:absolute"  onclick="removeProjectPic(this)"></div><input type="hidden" name="resumeBase.headPic" value="'+result.headPic+'"/></div>');
                                    $("#news_upload_button").prop('disabled', 'disabled');
                                }

                            }
                        },

                        complete: function () {

                            parent.$.messager.progress('close');

                        }
                    });
                });
            </script>
        </head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            简历信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    姓名
                </th>
                <td colspan="3" id="name">
                    <input name="resumeBase.id"  id="resumeBaseId" type="hidden" value="${param.id}">
                </td>
            </tr>
            <tr>
                <th>性别</th>
                <td id="sex">

                </td>
            </tr>


            <tr>
                <th>
                    手机号
                </th>
                <td colspan="3" id="telephone">

                </td>
            </tr>

            <tr>
                <th>
                    邮箱
                </th>
                <td colspan="3" id="email">

                </td>
            </tr>

            <tr>
                <th>
                    出生日期
                </th>
                <td colspan="3" id="birthday">

                </td>
            </tr>
            <tr>
                <th>
                    工作经验
                </th>
                <td colspan="3" id="experience">

                </td>
            </tr>
            <tr>
                <th>
                    所在城市
                </th>
                <td colspan="3" id="city">

                </td>
            </tr>

            <tr>
                <th>
                    籍贯
                </th>
                <td colspan="3" id="placeOrigin">

                </td>
            </tr>

            <tr>
                <th>
                    头像
                </th>
                <td colspan="3">
                    <div id="headPic"></div>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset>
        <legend>
            教育经历
        </legend>
        <table class="ta001" id="re">
        </table>
    </fieldset>
    <fieldset>
        <legend>
            工作经验
        </legend>
        <table class="ta001" id="rwe">
        </table>
    </fieldset>
    <fieldset>
        <legend>
            项目经验
        </legend>
        <table class="ta001" id="rpe">
        </table>
    </fieldset>
    <fieldset>
        <legend>
            技能
        </legend>
        <table class="ta001" id="rs">
        </table>
    </fieldset>
    <fieldset>
        <legend>
            在校获奖情况
        </legend>
        <table class="ta001" id="rras">
        </table>
    </fieldset>
    <fieldset>
        <legend>
            证书
        </legend>
        <table class="ta001" id="rc">
        </table>
    </fieldset>
</form>
</body>
</html>
</title>
</head>
<body>

</body>
</html>
