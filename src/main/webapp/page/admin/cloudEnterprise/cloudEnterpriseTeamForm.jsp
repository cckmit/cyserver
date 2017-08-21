<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">


        var action = '${param.action}';
        var id = '0';

        var enterpriseId = '${param.enterpriseId}';
        $(function(){
            if(action != '0'){
                id = '${param.id}';
                $('#clickTr').show();
                if(action == '1'){
                    $('input').attr('readonly', true);
                    $('.easyui-combobox').combobox({'readonly': true});
                }else{
                    $('#enterpriseId').combobox({'readonly': true});
                }

            }else{
                if(enterpriseId){
                    $('#enterpriseId').combobox({'readonly': true});
                    $('#enterpriseId').combobox('setValue', enterpriseId);
                }
            }


            if(id != '0'){
                $.ajax({
                    url: '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseTeamAction!getById.action',
                    data: {
                        'cloudEnterpriseTeam.cloudTeamId': id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'cloudEnterpriseTeam.id': result.id,
                                'cloudEnterpriseTeam.fullName': result.fullName,
                                'cloudEnterpriseTeam.position': result.position,
                                'cloudEnterpriseTeam.isAlumni': result.isAlumni,
                                'cloudEnterpriseTeam.classinfo': result.classinfo,
                                'cloudEnterpriseTeam.enterpriseName': result.enterpriseName,
                                'cloudEnterpriseTeam.description': result.description
                            });

                            if(result.pic){
                                $('#pic').append('<img src="'+ result.pic +'" width="80px" style="margin:10px" height="80px"/>');
                            }


                            if(action == '1'){
                                $(".bb001").removeAttr("onclick");
                            }
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }else{
                $('#sort').val(0);
            }
        });

    </script>
</head>
<body>
<form method="post" id="editProcutForm">
    <table class="ta001">
        <input type="hidden" name="cloudEnterpriseTeam.id" />
        <tr>
            <th>姓名</th>
            <td>
                <input id="fullName" name="cloudEnterpriseTeam.fullName" class="easyui-validatebox" disabled="disabled"
                       style="width: 150px;"/>
            </td>
        </tr>
        <tr>
            <th>职位</th>
            <td>
                <input id="position" name="cloudEnterpriseTeam.position" class="easyui-validatebox" disabled="disabled"
                       style="width: 150px;"/>
            </td>
        </tr>
        <tr>
            <th>校友企业</th>
            <td>
                <input id="enterpriseId" name="cloudEnterpriseTeam.enterpriseName" class="easyui-validatebox" disabled="disabled"
                       style="width: 300px;"
                      />
            </td>
        </tr>
        <tr>
            <th>班级信息</th>
            <td>
                <input id="classinfo" name="cloudEnterpriseTeam.classinfo" class="easyui-validatebox" disabled="disabled"
                       style="width: 300px;"
                       data-options="required:true,validType:'customRequired'"
                       />
            </td>
        </tr>
        <tr>
            <th>头像</th>
            <td>
                <div style="float:left;width:180px;" id="pic">
                </div>
            </td>
        </tr>
        <tr>
            <th>
                个人简介
            </th>
            <td >
                <textarea id="description" rows="7" cols="100" disabled="disabled"
                              name="cloudEnterpriseTeam.description"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
