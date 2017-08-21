
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
    <style>
        .ta001 th{
            width: 107px;
        }
        .ta001 td{
            height: 33px;
        }
        #bdMap{
            height: 394px;
        }

    </style>
    <script type="text/javascript">

        $(function () {
            var id = $('#enterpriseId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterprisePositionAction!getById.action',
                data: { 'enterpriseId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {

                        $('form').form('load', {
                            'cloudEnterprisePosition.id': result.id,
                            'cloudEnterprisePosition.name': result.name,
                            'cloudEnterprisePosition.enterpriseName': result.enterpriseName,
                            'cloudEnterprisePosition.workExperience': result.workExperience,
                            'cloudEnterprisePosition.jobType': result.jobType,
                            'cloudEnterprisePosition.salaryMax': result.salaryMax,
                            'cloudEnterprisePosition.salaryMin': result.salaryMin,
                            'cloudEnterprisePosition.education': result.education,
                            'cloudEnterprisePosition.recruiterNumber': result.recruiterNumber,
                            'cloudEnterprisePosition.description': result.description,

                        });

                        console.log(result);
                        $('#enterpriseName').text(result.enterpriseName?result.enterpriseName:"");
                        $('#name').text(result.name?result.name:"");
                        $('#workExperience').text(result.workExperience?result.workExperience:"");
                        $('#jobType').text(result.jobType?result.jobType:"");
                        $('#salaryMax').text(result.salaryMax?result.salaryMax:"");
                        $('#salaryMin').text(result.salaryMin?result.salaryMin:"");
                        $('#education').text(result.education?result.education:"");
                        $('#recruiterNumber').text(result.recruiterNumber?result.recruiterNumber:"");
                        $('#description').text(result.description?result.description:"");
                        $('#positionType').text(result.positionType?result.positionType:"");
                        $('#city').text(result.city?result.city:"");
                        $('#longitude').text(result.longitude?result.longitude:"");
                        $('#latitude').text(result.latitude?result.latitude:"");

                        $('#city').text(result.city);

                        var longitude = result.longitude;
                        var latitude = result.latitude;
                        // 创建Map实例
                        map= new BMap.Map("allmap");
                        // 初始化地图,设置中心点坐标和地图级别
                        var point = new BMap.Point(longitude,latitude);
                        map.centerAndZoom(point,12);

                        var marker = new BMap.Marker(point);        // 创建标注
                        map.addOverlay(marker);                     // 将标注添加到地图中

                        var geoc = new BMap.Geocoder();
                        //添加地图类型控件
                        map.addControl(new BMap.MapTypeControl());
                        //开启鼠标滚轮缩放
                        map.enableScrollWheelZoom(true);
                        //添加默认缩放平移控件
                        map.addControl(new BMap.NavigationControl());
                        var localSearch = new BMap.LocalSearch(map);
                        //允许自动调节窗体大小
                        localSearch.enableAutoViewport();

                        //点击地图上某一点获取这个地点的经纬度坐标和详细的地理位置（省、市、区、街道名称、街道门牌号）
                        //并将这个地址的经纬度坐标以及详细的地址记录下来之后赋值给数据库里的字段，点击保存的时候，将数据保存到数据库中。
                       /* map.addEventListener("click", function(e){
                            var pt = e.point;
                            $('#longitude').val(e.point.lng );
                            $('#latitude').val( e.point.lat);
                            geoc.getLocation(pt, function(rs){
                                var addComp = rs.addressComponents;
                                var str = addComp.province  + addComp.city  + addComp.district + addComp.street + addComp.streetNumber;
                                $('#locationDesc').val(str);
                            });
                        });*/

                        editor.html(result.description);

                        if(result.posterPic){
                            $('#posterPic').append('<img src="'+ result.posterPic +'" width="200px" style="margin:10px" height="80px"/>');
                        }
                        if(result.logo){
                            $('#logoPic').append('<img src="'+ result.logo +'" width="80px" style="margin:10px" height="80px"/>');
                        }


                    }
                },

                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });






        $(document).ready(function () {
            $('#city1').combobox({
                onChange: function (n, o) {
                    var search = new BMap.LocalSearch("中国", {
                        onSearchComplete: function(result){
                            if (search.getStatus() == BMAP_STATUS_SUCCESS){
                                var res = result.getPoi(0);
                                var point = res.point;
                                map.centerAndZoom(point, 12);
                            }
                        },renderOptions: {  //结果呈现设置，
                            map: map,
                            autoViewport: true,
                            selectFirstResult: true
                        }
                    });

                    var city1 = $("#city1").combobox('getValue');
                    if(city1 && city1 != '' && city1 != undefined  && city1 != '北京市' && city1 != '北京' ){
                        search.search(city1);
                    }else{
                        search.search("北京");
                    }
                }
            });
        });



    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            招聘信息
        </legend>
        <div style="width:512px;height:384px;border:2px solid gray;position: absolute; top:195px; left:130px" id="allmap"></div>
        <table class="ta001">
            <tr>
                <th>校友企业</th>
                <td colspan="3" id="enterpriseName">
                    <input name="cloudEnterprisePosition.id"  id="enterpriseId" type="hidden" value="${param.id}">

                </td>
            </tr>

            <tr>
                <th>岗位类型</th>
                <td colspan="3" id="jobType">

                </td>
            </tr>

            <tr>
                <th>
                    岗位名称
                </th>
                <td colspan="3" id="name">

                </td>
            </tr>

            <tr id="rg123">
                <th>招聘城市</th>
                <td colspan="3">
                    <span id="city" name="cloudEnterprisePosition.city"></span>

                </td>
            </tr>

            <tr id="rg223" style="display: none;">
                <th>招聘城市</th>
                <td colspan="3">
                    <input name="cloudEnterprisePosition.city" id="resourceArea2" type="hidden"/>
                    <input class="easyui-combobox" name="resourceAreaProvince" id="province3"
                           data-options="
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province3').combobox('clear');
			                	$('#city3').combobox('clear');
			                	$('#city3').combobox('loadData',[]);
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city3').combobox('clear');
							$('#city3').combobox('reload', url);
							$('#area').combobox('clear');
							$('#area').combobox('loadData',[]);
						}
                    	"/>
                    &nbsp; <input class="easyui-combobox" name="resourceAreaCity" id="city3"
                                  data-options="
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city3').combobox('clear');
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
                    	"/>

                </td>
            </tr>


            <tr>
                <th>
                    位置描述
                </th>
                <td colspan="3">
                    <input  name="cloudEnterprisePosition.longitude" id="longitude" type="hidden" class="easyui-validatebox"/>
                    <input  name="cloudEnterprisePosition.latitude"  id="latitude" type="hidden" class="easyui-validatebox"/>
                    <input  name="cloudEnterprisePosition.locationDesc" id="locationDesc" disabled="disabled" class="easyui-validatebox"style="width: 250px;"
                            maxlength="40" value=""/>
                </td>

            </tr>

            <tr>
                <th>

                </th>
                <td id="bdMap">

                </td>
            </tr>

            <tr>
                <th>
                    经验
                </th>
                <td colspan="3" id="workExperience">

                </td>
            </tr>

            <tr>
                <th>
                    薪资
                </th>
                <td colspan="3">
                    <input  id="salaryMin" name="cloudEnterprisePosition.salaryMax" disabled="disabled" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>
                    -
                    <input id="salaryMax" name="cloudEnterprisePosition.salaryMin" disabled="disabled" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
                </td>
            </tr>

            <tr>
                <th>
                    学历要求
                </th>
                <td colspan="3" id="education">

                </td>
            </tr>
            <tr>
                <th>
                    招聘人数
                </th>
                <td colspan="3" id="recruiterNumber">
                </td>
            </tr>

            <tr>
                <th>
                    职位描述
                </th>
                <td colspan="3">
					<textarea id="description" rows="7" cols="100" disabled="disabled"
                              name="cloudEnterprisePosition.description"></textarea>
                </td>
            </tr>


            <tr>
                <th>
                    状态
                </th>
                <td >
                    <select id="status" class="easyui-combobox" data-options="editable:false," disabled="disabled"  name="cloudEnterprisePosition.status" style="width: 150px;">
                        <option value="10" >开启</option>
                        <option value="20" >关闭</option>
                    </select>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
