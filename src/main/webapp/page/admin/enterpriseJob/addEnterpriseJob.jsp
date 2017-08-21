
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

        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                var salaryMax = parseFloat($('#salaryMax').val());
                var salaryMin = parseFloat($('#salaryMin').val());
                var experienceMax = parseFloat($('#experienceMax').val());
                var experienceMin = parseFloat($('#experienceMin').val());
                if(experienceMin > experienceMax) {
                    parent.$.messager.alert('提示', '经验下限必须小于经验上限', 'error');
                    return false;
                }
                if(salaryMin > salaryMax) {
                    parent.$.messager.alert('提示', '薪资下限必须小于薪资上限', 'error');
                    return false;
                }

                $.ajax({
                    url : '${pageContext.request.contextPath}/enterpriseJob/enterpriseJobAction!save.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else
                        {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function()
                    {
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        parent.$.messager.progress('close');
                    }
                });
            }
        }


        var map;
        $(function () {
            // 创建Map实例
            map= new BMap.Map("allmap");
            // 初始化地图,设置中心点坐标和地图级别
            var point = new BMap.Point(116.331398,39.897445);
            map.centerAndZoom(point,12);
            /*var myIcon = new BMap.Icon("${pageContext.request.contextPath}/images/markers.png", new BMap.Size(23, 25), {
             // 指定定位位置
             offset: new BMap.Size(10, 25),
             // 当需要从一幅较大的图片中截取某部分作为标注图标时，需要指定大图的偏移位置
             imageOffset: new BMap.Size(0, -250) // 设置图片偏移
             });
             // 创建标注对象并添加到地图
             var marker = new BMap.Marker(point,{icon: myIcon});
             map.addOverlay(marker);    */                 // 将标注添加到地图中



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
            map.addEventListener("click", function(e){
                var pt = e.point;
                $('#longitude').val(e.point.lng );
                $('#latitude').val( e.point.lat);
                geoc.getLocation(pt, function(rs){
                    var addComp = rs.addressComponents;
                    var str = addComp.province  + addComp.city  + addComp.district + addComp.street + addComp.streetNumber;
                    $('#locationDesc').val(str);
                    $('#address').val(str);
                });
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



        //用户点击搜索按钮之后，首先根据用户所选的城市设定为中心城市，默认为北京是中心城市
        //同时可以搜索出用户所输入信息的周边的地址来供用户选择，
        function chaxun() {
            //获取用户所输入的信息
            var keyword  =	$('#locationDesc').val();
            //搜素用户输入信息的周边地址并作出标记
            var local = new BMap.LocalSearch(map, {
                renderOptions:{map: map}
            });
            local.searchInBounds(keyword, map.getBounds());
            map.addEventListener("dragend",function(){
                map.clearOverlays();
                local.searchInBounds(keyword, map.getBounds());
            });
        }


    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            招聘信息
        </legend>
        <div style="width:512px;height:384px;border:2px solid gray;position: absolute; top:190px; left:130px" id="allmap"></div>
        <table class="ta001">
            <tr>
                <th>校友企业</th>
                <td id="enterpriseTd">
                    <input id="enterpriseId" name="enterpriseJob.enterpriseId" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getEnterpriseList.action'" />
                </td>
            </tr>

            <tr>
                <th>岗位类型</th>
                <td>
                    <input id="type" name="enterpriseJob.type" class="easyui-combobox" style="width: 250px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterpriseJob/enterpriseJobAction!doNotNeedSecurity_getType.action'" />
                </td>
            </tr>

            <tr>
                <th>
                    岗位名称
                </th>
                <td colspan="3">
                    <input name="enterpriseJob.name" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>


            <tr>
                <th>招聘城市</th>
                <td colspan="3">
                    <input class="easyui-combobox" name="resourceAreaProvince" id="province1"
                           data-options="
						  required:true,
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province1').combobox('clear');
			                	$('#city1').combobox('clear');
			                	$('#city1').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city1').combobox('clear');
							$('#city1').combobox('reload', url);
						}
                    	">
                    &nbsp; <input class="easyui-combobox" name="resourceAreaCity"  id="city1"
                                  data-options="
						  required:true,
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city').combobox('clear');
			                }
			            }],

                    	">
                </td>
            </tr>


            <tr>
                <th>
                    位置描述
                </th>
                <td colspan="3">
                    <input  name="enterpriseJob.longitude" id="longitude" type="hidden" class="easyui-validatebox"/>
                    <input  name="enterpriseJob.latitude"  id="latitude" type="hidden" class="easyui-validatebox"/>
                    <input  name="enterpriseJob.locationDesc" id="locationDesc"  class="easyui-validatebox"style="width: 250px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value=""/>
                    <input type="button"  onclick="chaxun()" value="搜索"/>

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
                <td colspan="3">
                    <input id="experienceMin" name="enterpriseJob.experienceMin" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>
                    -
                    <input id="experienceMax" name="enterpriseJob.experienceMax" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>年&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
                </td>
            </tr>

            <tr>
                <th>
                    薪资
                </th>
                <td colspan="3">
                    <input id="salaryMin" name="enterpriseJob.salaryMin" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>
                    -
                    <input id="salaryMax" name="enterpriseJob.salaryMax" class="easyui-validatebox"
                            style="width: 110px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
                </td>
            </tr>

            <tr>
                <th>学历要求</th>
                <td>
                    <input class="easyui-combobox" style="width: 150px;" name="enterpriseJob.education"
                           data-options="
								valueField: 'dictName',
								textField: 'dictName',
								editable:false,
								url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历')
							" />
                </td>
            </tr>
            <tr>
                <th>
                    招聘人数
                </th>
                <td colspan="3">
                    <input name="enterpriseJob.recruitersNum" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
                </td>
            </tr>

            <tr>
                <th>
                    职位描述
                </th>
                <td colspan="3">
					<textarea id="description" rows="7" cols="100"
                              name="enterpriseJob.description"></textarea>
                </td>
            </tr>

            <tr>
                <th>职位要求</th>
                <td>
                    <textarea id="demand" rows="10" cols="100" name="enterpriseJob.demand" style="width: 700px; height: 250px;" ></textarea>
                </td>
            </tr>

            <tr>
                <th>
                    状态
                </th>
                <td >
                    <select id="status" class="easyui-combobox" data-options="editable:false,"  name="enterpriseJob.status" style="width: 150px;">
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
