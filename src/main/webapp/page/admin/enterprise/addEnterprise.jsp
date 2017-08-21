
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int isRichTextConvert = Global.IS_RICH_TEXT_CONVERT;
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=j5bQGIAXNd5rrnfu83is90HhX1n3xvMd"></script>
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
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
			width: 500px;
			height: 400px;
		}
		#locationDescSpan{
			width: 500px;
			text-align: center;
		}

	</style>
	<script type="text/javascript">
		//编辑器里面的内容图片上传
		KindEditor.ready(function(K) {
			K.create('#description',{
				items : [
					'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
					'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
					'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
					'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
				afterChange:function(){
					this.sync();
				}
			});
		});


		function submitForm($dialog, $grid, $pjq)
		{

			if ($('form').form('validate'))
			{
                //富文本是否转编码 lixun 2017.5.5
                if( '<%=isRichTextConvert%>' == '1' )
                {
                    $("#description").val(strToBase64($("#description").val()));
                }
				$.ajax({
					url : '${pageContext.request.contextPath}/enterprise/enterpriseAction!save.action',
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

		$(function () {
			uploadPic("#logo_upload_button", "enterprise.logo", "#logoPic", "logo");
			uploadPic("#poster_upload_button", "enterprise.posterPic", "#posterPic", "poster");
		});
		function uploadPic(upload_button_name, picName, picDivName, picId)
		{
			var w = 80;
			var h = 80;
			if(picId == "poster"){
				w = 200;
				h = 80;
			}

			var button = $(upload_button_name), interval;
			new AjaxUpload(button,
					{
						action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
						name : 'upload',
						onSubmit : function(file, ext)
						{
							if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)))
							{
								$.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
								return false;
							}
							$.messager.progress({text : '图片正在上传,请稍后....'});
						},
						onComplete : function(file, response)
						{
							$.messager.progress('close');
							var resp = $.parseJSON(response);

							if (resp.error == 0)
							{
								$(picDivName).append(
										'<div style="float:left; margin-bottom: 5px; margin-left: 5px; position:relative">'+
										'<img src="'+resp.url+'" width="'+w+'px" height="'+h+'px" />'+
										'<div class="bb001" style="left:'+(w-15)+'px; top:0;   position:absolute"  onclick="removePic(this,\'' + upload_button_name + '\')">'+
										'</div>'+
										'<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+resp.url+'"/></div>'
								);


								$(upload_button_name).prop('disabled', 'disabled');
							}
							else
							{
								$.messager.alert('提示', response, 'error');
							}
						}
					});

		}


		function removePic(pic, upload_button_name)
		{
			$(pic).parent().remove();
			$(upload_button_name).prop('disabled', false);
		}

		var map;


		var poiIcon = new BMap.Icon("${pageContext.request.contextPath}/images/star.png", new BMap.Size(23, 25));
		var clickIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
			offset: new BMap.Size(10, 25), // 指定定位位置
			imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
		});

		var preMarker ;

		/**
		 * 创建百度地图实例
		 * @param longitude
		 * @param latitude
		 */
		function createMap(div,longitude,latitude ,hasVal ,needClick) {// 创建Map实例
			map= new BMap.Map(div, {enableMapClick:false});
			// 初始化地图,设置中心点坐标和地图级别
			var point = new BMap.Point(longitude,latitude);
			map.centerAndZoom(point,12);
			// 创建标注对象并添加到地图
			var poiMarker ;
			if(hasVal == 1) {
				poiMarker = new BMap.Marker(point,{icon: clickIcon});
				preMarker = poiMarker ;
			} else {
				poiMarker = new BMap.Marker(point,{icon: poiIcon});
			}
			map.addOverlay(poiMarker);


			var geoc = new BMap.Geocoder();
			//添加地图类型控件
//			map.addControl(new BMap.MapTypeControl());
			//开启鼠标滚轮缩放
			map.enableScrollWheelZoom(true);
			//添加默认缩放平移控件
			map.addControl(new BMap.NavigationControl());
			var localSearch = new BMap.LocalSearch(map);
			//允许自动调节窗体大小
			localSearch.enableAutoViewport();

			if(needClick == 1) {
				//点击地图上某一点获取这个地点的经纬度坐标和详细的地理位置（省、市、区、街道名称、街道门牌号）
				//并将这个地址的经纬度坐标以及详细的地址记录下来之后赋值给数据库里的字段，点击保存的时候，将数据保存到数据库中。
				map.addEventListener("click", function (e) {
					if (e.overlay != poiMarker) {
						map.removeOverlay(e.overlay);
					}
					var marker = new BMap.Marker(e.point, {icon: clickIcon});
					map.removeOverlay(preMarker);
					map.addOverlay(marker);
					preMarker = marker;

					var pt = e.point;
					$('#longitude').val(e.point.lng);
					$('#latitude').val(e.point.lat);
					geoc.getLocation(pt, function (rs) {
						var addComp = rs.addressComponents;
						var str = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
						$('#locationDesc').val(str);
//					$('#address').val(str);
						$('#locationDescSpan').html(str);
					});
				});
			}

		}

		/**
		 * 切换城市
		 * @type {BMap.LocalSearch}
		 */
		var switchCity = new BMap.LocalSearch("中国", {
			onSearchComplete: function(result){
				if (switchCity.getStatus() == BMAP_STATUS_SUCCESS){
					var res = result.getPoi(0);
					var point = res.point;
					map.centerAndZoom(point, 12);
					var poiMarker = new BMap.Marker(point,{icon: poiIcon});
					point.enableMapClick = false ;
					map.clearOverlays();
					map.addOverlay(poiMarker);
				}
			},renderOptions: {  //结果呈现设置，
				map: map,
				autoViewport: true,
				selectFirstResult: true
			}
		});

		$(function () {
			createMap("allmap",116.331398,39.897445,0,1);
			$('#city').combobox({
				onChange: function (n, o) {
					var city = $("#city").combobox('getValue');
					if(city && city != '' && city != undefined  && city != '北京市' && city != '北京' ){
						switchCity.search(city);
					}else{
						switchCity.search("北京");
					}
				}
			});

		});

		//用户点击搜索按钮之后，首先根据用户所选的城市设定为中心城市，默认为北京是中心城市
		//同时可以搜索出用户所输入信息的周边的地址来供用户选择，
		function searchPosition() {
			//获取用户所输入的信息
			var keyword  =	$('#address').val();
			//搜素用户输入信息的周边地址并作出标记
			var local = new BMap.LocalSearch(map, {
				renderOptions:{
					map: map,
					autoViewport: true,
					selectFirstResult: true
				}
			});
			local.searchInBounds(keyword, map.getBounds());
		}





	</script>
</head>

<body>
<form method="post" class="form" style="position: relative">
	<fieldset>
		<legend>
			企业信息
		</legend>
		<div style="width:500px;height:400px;border:2px solid gray;position: absolute; top:240px; left:130px;margin-top:10px;margin-left: 10px;" id="allmap"></div>
		<table class="ta001">
			<tr>
				<th>
					企业名称
				</th>
				<td colspan="3">
					<input name="enterprise.name" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>企业类型</th>
				<td>
					<input id="type" name="enterprise.type" class="easyui-combobox" style="width: 250px;" value=""
						   data-options="editable:false,required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getType.action'" />
				</td>
			</tr>

			<tr>
				<th>融资阶段</th>
				<td>
					<input id="financingStage" name="enterprise.financingStage" class="easyui-combobox" style="width: 250px;" value=""
						   data-options="editable:false,required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getFinancingStage.action?dictTypeValue=25'" />
				</td>
			</tr>

			<tr>
				<th>
					主营业务
				</th>
				<td colspan="3">
					<input  name="enterprise.mainBusiness" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>城市</th>
				<td colspan="3">
					<input class="easyui-combobox" name="resourceAreaProvince" id="province"
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
			                	$('#province').combobox('clear');
			                	$('#city').combobox('clear');
			                	$('#city').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city').combobox('clear');
							$('#city').combobox('reload', url);
						}
                    	">
					&nbsp; <input class="easyui-combobox" name="resourceAreaCity"  id="city"
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
					企业地址
				</th>
				<td colspan="3">
					<input  name="enterprise.longitude" id="longitude" type="hidden" class="easyui-validatebox"/>
					<input  name="enterprise.latitude"  id="latitude" type="hidden" class="easyui-validatebox"/>
					<input  name="enterprise.locationDesc" id="locationDesc" type="hidden" class="easyui-validatebox"/>
					<input   name="enterprise.address" id="address" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="40" value=""/>
					<input type="button"  onclick="searchPosition()" value="搜索"/>

				</td>

			</tr>

			<tr>
				<th>
					选择详细企业地址
				</th>
				<td>
					<div id="bdMap" style="margin-top:10px;margin-left: 10px;"></div>
					<div id="locationDescSpan">中福丽宫</div>
			   </td>
			</tr>


			<tr>
				<th>
					网址
				</th>
				<td colspan="3">
					<input  name="enterprise.website" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>
					企业邮箱
				</th>
				<td colspan="3">
					<input name="enterprise.recruitEmail" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'email'"
						   maxlength="40" value=""/>
				</td>
			</tr>


			<tr>
				<th>
					业务范围
				</th>
				<td colspan="3">
					<input name="enterprise.serviceArea" class="easyui-validatebox"
						   style="width: 250px;"
						   maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>
					联系人名称
				</th>
				<td colspan="3">
					<input name="enterprise.linkman" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>
					联系电话
				</th>
				<td colspan="3">
					<input  name="enterprise.contactNumber" class="easyui-validatebox"
						   style="width: 250px;"
						   data-options="required:true,validType:'telePhone'"
						   maxlength="20" value=""/>
				</td>
			</tr>
			<tr>
				<th>
					标语
				</th>
				<td colspan="3">
					<input  name="enterprise.slogan" class="easyui-validatebox"
							style="width: 250px;"
							data-options="validType:'customRequired'"
							maxlength="40" value=""/>
				</td>
			</tr>

			<tr>
				<th>
					公司简介
				</th>
				<td colspan="3">
					<textarea id="summary" rows="7" cols="100"
							  name="enterprise.summary"></textarea>
				</td>
			</tr>

			<tr>
				<th>图文详情</th>
				<td>
					<textarea id="description" rows="10" cols="100" name="enterprise.description" style="width: 700px; height: 250px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th>Logo</th>
				<td>
					<input type="button" id="logo_upload_button" value="上传log">(Logo大小为：80px x 80px)

					<div id="logoPic"></div>
				</td>
			</tr>
			<tr>
				<th>海报图片</th>
				<td>
					<input type="button" id="poster_upload_button" value="上传海报图片">(图片大小为：200px x 80px)
					<div id="posterPic"></div>
				</td>
			</tr>

		</table>
	</fieldset>
</form>
</body>
</html>
