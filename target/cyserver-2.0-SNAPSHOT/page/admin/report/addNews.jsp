<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    <style type="text/css">
		  .container{
			  width: 400px;
		  }
		  .resizer{
			  overflow: hidden;
		  }
		  .resizer.have-img button.ok{
			  display: inline-block;
		  }
		  .resizer.have-img .inner {
			  display: block;
		  }
		  .inner{
			  width: 100%;
			  position: relative;
			  font-size: 0;
			  overflow: hidden;
			  display: none;
		  }
		  img{
			  margin: 0 auto;
			  width: 100%;
		  }

		  .frames{
			  position: absolute;
			  top: 0;
			  left: 0;
			  border: 1px solid red;
			  cursor: move;
			  outline: rgba(255, 255, 255, 0.6) solid 10000px;
		  }
		  canvas{
			  max-width: 100%;
			  margin:auto;
			  display: block;
		  }
	  </style>
<script type="text/javascript">

	var isNewTypeOpen = "0";
	var isNewType = "0";
	var sChannel = "" ;	//lixun
	var sSource = "" ;	//lixun

	$(function() {
	    //富文本
        window.um = UM.getEditor('content');

        //封面图上传
		var button = $("#news_upload_button"), interval;
		new AjaxUpload(button, {
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
					$('#newsPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeNewsPic(this)"></div><input type="hidden" name="news.pic" value="'+msg.url+'"/></div>');
					$("#news_upload_button").prop('disabled', 'disabled');
				} else {
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});

		/*if(${sessionScope.user.role.systemAdmin} == 1) {
			$("#dept").hide();
		}*/
	});

	function removeNewsPic(newsPic) {
		$(newsPic).parent().remove();
		$("#news_upload_button").prop('disabled', false);
	}

	/*var jsonstr = [
		{
			"id": "10",
			"name": "手机"
		},
		{
			"id": "30",
			"name": "微信"
		},
		{
			"id": "20",
			"name": "网页"
		}
	];*/
	var jsonstr;
	$(function(){
		$.ajax({
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getUserDeptId.action',
			success : function(result){
				if(result == 1){
					$('#sourcetr').hide();
				}
			}
		});


		$.ajax({
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 110,
			success : function(result){
				jsonstr = $.parseJSON(result)
				$('#qudao').combogrid("grid").datagrid("loadData", jsonstr);
			}
		});

		if( '${news.category}' != '0'){
			sChannel = '${news.channel}';
		}
		/*
		//选择渠道
		$('#qudao').change(
				function()
				{
					$('#newsType').combotree('clear');
					sChannel = $(this).val();
					var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?source='+sSource+'&channel='+sChannel;
					$('#newsType').combotree('reload',url);
				}
		);

		//lixun 新的方法
		sChannel = $('#qudao').val();
		sSource = $('#source').val();
		var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?source='+sSource+'&channel='+sChannel;
		$('#newsType').combotree('reload',url);
		*/

	});
	
	function submitForm($dialog, $grid, $pjq)
	{
		if($('#introduction').val()==''){
			parent.$.messager.alert('提示', '请输入新闻简介', 'error');
			return false;
		}
		/*
		if($('#newsUrl').val()==''){
			parent.$.messager.alert('提示', '请输入新闻网页链接', 'error');
			return false;
		}
		*/
		if($('input[name="news.pic"]').val()==undefined || $('input[name="news.pic"]').val() == ''){
			parent.$.messager.alert('提示', '请上传新闻封面图片', 'error');
			return false;
		}
		//手机1级栏目
		var type1 = $("#newsType1").val();
		//手机2级栏目
		var type2 = $("#newsType2").val();
		//网页1级栏目
		var webtype1 = $("#webNewsType1").val();
		//网页2级栏目
		var webtype2 = $("#webNewsType2").val();
		
		if(type1==0 && webtype1==0){
			parent.$.messager.alert('提示', '请选择手机或网页新闻的一级栏目', 'error');
			return false;
		}	
		if((type1 > 0) && (type2==0) && $("#newsType2 option").length>1  ){
			parent.$.messager.alert('提示', '请选择手机新闻二级栏目', 'error');
			return false;
		}
		if((webtype1 > 0) && (webtype2==0) && $("#webNewsType2 option").length>1  ){
			parent.$.messager.alert('提示', '请选择网页新闻二级栏目', 'error');
			return false;
		}
		if ($('form').form('validate'))
		{
            var resultStr = $('form').serialize();
            /*var submitArray = resultStr.split('&');
            resultStr = '';
            for(var i in submitArray){
                var item = submitArray[i];
                if(item.indexOf('news.content') >= 0){
                    var realHtml = window.um.getContent();
                    var encodeHtml = escape(realHtml);
                    item = 'news.content=' + encodeHtml;
                }
                if(i > 0){
                    resultStr += '&';
                }
                resultStr += item;
            }*/
			$.ajax({
				url : '${pageContext.request.contextPath}/mobile/news/newsAction!save.action',
				data : resultStr,
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
	};
	
	/** lixun 初始化部门名称*/
	$.ajax({
		type: "GET",
		url: "${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getDeptByAlumni.action",

		success: function( data ){
			//alert( data );
			var deptName = "";
			var jsonData = $.parseJSON( data );

			if( jsonData.alumniName == "" )
				$("#dept").hide();
			else
			{
				deptName = jsonData.alumniName;
				$("#tdDeptName").html( deptName );
			}
			//是否开通
			isNewTypeOpen = jsonData.isNewTypeOpen;
			if( isNewTypeOpen == "0" )
				$("#newTypeTr").hide();
			else
				$("#newsTypeAlumni").html( deptName )
		}
	});

	$(function(){
		//是否选择分会专栏
		$('#newTypeOpen').click(
				function( )
				{
					if( $('#newTypeOpen').is(':checked') )
					{
						$("#newsTypeAlumni").show();

						isNewType = "1";
					}
					else
					{
						$("#divNewsType").show();
						$("#newsTypeAlumni").hide();
						isNewType = "0";
					}
				}
		);

	});

	function addHtml( data )
	{
		//alert( JSON.stringify( data.rows[1] ) );
		var lines = parseInt( data.total );
		var sHtml = '';
		for( var i = 0; i < lines; ++i )
		{
			$('#channel_'+ i).remove();
			sHtml = sHtml + '<tr id="channel_' + i + '">' +
					' <th>' + data.rows[i].dictName + '新闻栏目</th>' +
					'<td colspan="3" >' +
					'<select hidden="true" name="cateList"  id="newsType_' + i + '" class="easyui-combotree" ' +
					'style="width: 150px;">' +
					' </select></td></tr>';
		}
		//alert( sHtml );
		$('#trQudao').after( sHtml );

		var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?source='+sSource+'&channel=';

		for( var i = 0; i < lines; ++i )
		{
			var func = function(node)
			{
				var isLeaf = $(this).tree('isLeaf', node.target);
				if (!isLeaf){
					$.messager.show({
						msg: '请选择子栏目'
					});
					return false;
				}
			};
			var datas = {'required': false,'url': url,'editable':false,'idField':'id','state':'open','textField':'text','parentField':'pid',
				'url':url+data.rows[i].dictValue,'icons':[{iconCls:'icon-clear',handler: function(e){$(this).combotree('clear');}}],'onBeforeSelect': func  }
			$('#newsType_'+i).combotree( datas );
			$('#channel_'+i).hide();

		}


	}


	/**
	 * 预览新闻
	 */
	function previewMobNew() {
		var title = $("#newsTitle").val();
		var content = window.um.getContent();

		if (title == null || $.trim(title) == '') {
			alert("新闻标题不能为空！");
			return ;
		}
		if (content == null || $.trim(content) == '') {
			alert("新闻内容不能为空！");
			return ;
		}

		post('${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_previewMobNew.action',
				{"mynews.title":title,"mynews.content":content}
		) ;

	}
	function post(URL, PARAMS) {
		var temp_form = document.createElement("form");
		temp_form.action = URL;
		temp_form.target = "_blank";
		temp_form.method = "post";
		temp_form.style.display = "none";
		for (var x in PARAMS) {
			var opt = document.createElement("textarea");
			opt.name = x;
			opt.value = PARAMS[x];
			temp_form.appendChild(opt);
		}
		document.body.appendChild(temp_form);
		temp_form.submit();
	}


</script>
</head>
  
<body>
<form method="post" id="addNewsForm">
<fieldset>
		<legend>
			新闻基本信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					标题
				</th>
				<td colspan="3">
					<input name="news.newsId" type="hidden" id="newsId" value="">
					<input id="newsTitle" name="news.title" class="easyui-validatebox"
						style="width: 700px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="100" value=""/>
				</td>
			</tr>
			<tr>
				<th>
					新闻简介
				</th>
				<td colspan="3">
					<textarea id="introduction" rows="7" cols="100"
						name="news.introduction"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					网页链接
				</th>
				<td colspan="3">
					<textarea id="newsUrl" rows="3" cols="100" name="news.newsUrl"  maxlength="120"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					频道
				</th>
				<td colspan="3">
					<input name="news.channelName" class="easyui-combobox" style="width: 150px;" value=""
						data-options="editable:false,required:true,
							        valueField: 'channelName',
							        textField: 'channelName',
							        url: '${pageContext.request.contextPath}/newsChannel/newsChannelAction!doNotNeedSecurity_initType.action'" />
				</td>
			</tr>
			<tr>
				<th>
					兴趣标签
				</th>
				<td colspan="3">
					<select id="news.type" name="news.type"  class="easyui-combogrid" style="width:150px" data-options=" 
							panelWidth: 150,
							multiple: true,
							required:true,
							idField: 'channelName',
							textField: 'channelRemark',
							url: '${pageContext.request.contextPath}/newsChannel/newsChannelAction!doNotNeedSecurity_getALLLabelList.action',
							method: 'get',
							columns: [[
								{field:'channelName',checkbox:true},
								{field:'channelRemark',title:'标签名称',width:80}
							]],
							fitColumns: true,
							editable:false,
							onLoadSuccess : function( data )
							{
								//alert( JSON.stringify( data ) );
							}
						">
					</select>
				</td>
			</tr>
			<tr id="sourcetr">
				<th>
					栏目来源
				</th>

				<td>
					<select id="source" name="news.source" class="easyui-combobox" style="width: 150px" data-options="
						editable:false,required:true,panelHeight:50,
						onSelect: function(value){
								$('#newsType').combotree('clear');
								var url = '';
								if(value.value == 1){
									sSource = '';
								}else if(value.value == 2){
									sSource = '2';
								}
								sSource = value.value;
								$('#qudao').combogrid('clear');
								$('#qudao').combogrid('grid').datagrid('loadData', jsonstr);
						}">
						<option value="1">总会</option>
						<option value="2">本会</option>
					</select>
				</td>

			</tr>
			<tr id="trQudao">
				<th>渠道</th>

				<td colspan="3">
					<select  id="qudao" name="news.channel" class="easyui-combogrid" style="width:150px" data-options="
							panelWidth: 150,
							multiple: true,
							required:true,
							idField: 'dictValue',
							textField: 'dictName',
							columns: [[
								{field:'dictValue',checkbox:true},
								{field:'dictName',title:'标签名称',width:80},


							]],
							fitColumns: true,
							editable:false,
							panelHeight:130,
							onCheck: function( rowIndex, rowData )
							{
								$('#channel_'+rowIndex).show();
								$('#newsType_'+rowIndex).combotree({'required': true});
							},
							onUncheck: function( rowIndex, rowData )
							{
								$( '#channel_'+rowIndex ).hide();
								$( '#newsType_'+rowIndex ).combotree({'required': false});
								$( '#newsType_'+rowIndex ).combotree('clear');
							},
							onLoadSuccess : function( data ) {

								addHtml( data );
							}

							">
					</select>
				</td>

			</tr>

			<tr id="dept">
				<th>
					所属组织
				</th>
				<td colspan="3" id="tdDeptName">
					<input name="deptName" id="deptName" type="hidden" />
				</td>
			</tr>
			
			<tr>
				<th>
					时间
				</th>
				<td colspan="3">
					<input name="news.createDate" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
				</td>
			</tr>
			<tr>
				<th>
					新闻内容
				</th>
				<td colspan="3">
					<script id="content" name="news.content" type="text/plain" style="width:700px;height:300px;">
					</script>
				</td>
			</tr>

			<%--<tr>
				<th>
					新闻封面上传
				</th>
				<td colspan="3">
					<input type="file" accept="images/*" id="getImg" value="请选择图片" />
					<input type="button" class="ok"  value="裁切" style="display: none;"/>
				</td>
			</tr>
			<tr>
				<th>
					新闻封面图片
				</th>
				<td colspan="3">
					<div class="container"></div>
					<input type="hidden" name="news.pic" id="newsPic" value="" />
				</td>
			</tr>--%>
			<tr>
				<th>
					新闻封面上传
				</th>
				<td colspan="3">
					<input type="button" id="news_upload_button" value="上传图片">
				</td>
			</tr>
			<tr>
				<th>
					新闻封面图片
				</th>
				<td colspan="3">
					<div id="newsPic"></div>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
  </body>
  <%--<script type="text/javascript">
	  var tmp=$('<div class="resizer">'+
			  '<div class="inner">'+
			  '<img style="margin:0; position:relative;top:0;left:0">'+
			  '<div class="frames"></div>'+
			  '</div>');
	  $.imageResizer=function(){
		  if(Uint8Array&&HTMLCanvasElement&&atob&&Blob){

		  }else{
			  return false;
		  }
		  var resizer=tmp.clone();

		  resizer.image=resizer.find('img')[0];
		  resizer.frames=resizer.find('.frames');
		  resizer.okButton=$('.ok');
		  resizer.getFile=$('#getImg');
		  resizer.frames.offset={
			  top:0,
			  left:0
		  };

		  resizer.okButton.click(function(){
			  resizer.clipImage();
			  resizer.okButton.hide();
			  resizer.getFile.show();
			  submitPic("");
		  });
		  resizer.clipImage=function(){
			  var nh=this.image.naturalHeight,
				nw=this.image.naturalWidth,

			  size=size>900?900:size;

			  var canvas=$('<canvas id="resizedCanvas" width="'+900+'" height="'+500+'"></canvas>')[0],
					  ctx=canvas.getContext('2d'),
					  scale=nw/this.offset.width,
					  x=this.frames.offset.left*scale,
					  y=this.frames.offset.top*scale,
					  w=this.frames.offset.width*scale,
					  h=this.frames.offset.height*scale;

			  ctx.drawImage(this.image,x,y,w,h,0,0,900,500);
			  var src=canvas.toDataURL();
			  this.canvas=canvas;
			  this.append(canvas);
			  this.addClass('uploading');
			  this.removeClass('have-img');

			  src=src.split(',')[1];
			  if(!src)return this.doneCallback(null);
			  src=window.atob(src);

			  var ia = new Uint8Array(src.length);
			  for (var i = 0; i < src.length; i++) {
				  ia[i] = src.charCodeAt(i);
			  };

			  this.doneCallback(new Blob([ia], {type:"image/png"}));
		  };

		  resizer.resize=function(file,done){
			  this.reset();
			  this.doneCallback=done;
			  this.setFrameSize(0);
			  this.frames.css({
				  top:0,
				  left:0
			  });
			  var reader=new FileReader();
			  reader.onload=function(){
				  resizer.image.src=reader.result;
				  reader=null;
				  resizer.addClass('have-img');
				  resizer.setFrames();
                  if(file.type !='image/gif'){
                      resizer.getFile.hide();
                      resizer.okButton.show();
				  }else{
                      resizer.frames.hide();
                      submitPic(resizer.image.src);
				  }
			  };
			  reader.readAsDataURL(file);
		  };

		  resizer.reset=function(){
			  this.image.src='';
			  this.removeClass('have-img');
			  this.removeClass('uploading');
			  this.find('canvas').detach();
		  };

		  //設置截取框尺寸
		  resizer.setFrameSize=function(size){
			  this.frames.offset.width = size;
			  this.frames.offset.height = size*5/9;
			  return this.frames.css({
				  width:size+'px',
				  height:size*5/9+'px'
			  });
		  };


		  //獲取選擇圖片尺寸
		  resizer.getDefaultSize=function(){
			  var width=this.find(".inner").width(),
					  height=this.find(".inner").height();
			  this.offset={
				  width:width,
				  height:height
			  };
			  console.log(this.offset);
			  return (width/height)>(9/5)?(height*9/5):width;
		  };

		  //截取框的移動
		  resizer.moveFrames=function(offset){
			  var x=offset.x,
					  y=offset.y,
					  top=this.frames.offset.top,
					  left=this.frames.offset.left,
					  fw=this.frames.offset.width,
					  fh=this.frames.offset.height,
					  width=this.offset.width,
					  height=this.offset.height;

			  if(x+fw+left>width){
				  x=width-fw;
			  }else{
				  x=x+left;
			  };

			  if(y+fh+top>height){
				  y=height-fh;
			  }else{
				  y=y+top;
			  };
			  x=x<0?0:x;
			  y=y<0?0:y;
			  this.frames.css({
				  top:y+'px',
				  left:x+'px'
			  });

			  this.frames.offset.top=y;
			  this.frames.offset.left=x;
		  };

		  (function(){
			  var time;
			  function setFrames(){
				  var size=resizer.getDefaultSize();
				  resizer.setFrameSize(size);
			  };

			  window.onresize=function(){
				  clearTimeout(time)
				  time=setTimeout(function(){
					  setFrames();
				  },1000);
			  };

			  resizer.setFrames=setFrames;
		  })();

		  (function(){
			  var lastPoint=null;
			  function getOffset(event){
				  event=event.originalEvent;
				  var x,y;
				  if(event.touches){
					  var touch=event.touches[0];
					  x=touch.clientX;
					  y=touch.clientY;
				  }else{
					  x=event.clientX;
					  y=event.clientY;
				  }

				  if(!lastPoint){
					  lastPoint={
						  x:x,
						  y:y
					  };
				  };

				  var offset={
					  x:x-lastPoint.x,
					  y:y-lastPoint.y
				  }
				  lastPoint={
					  x:x,
					  y:y
				  };
				  return offset;
			  };
			  resizer.frames.on('touchstart mousedown',function(event){
				  getOffset(event);
			  });
			  resizer.frames.on('touchmove mousemove',function(event){
				  if(!lastPoint)return;
				  var offset=getOffset(event);
				  resizer.moveFrames(offset);
			  });
			  resizer.frames.on('touchend mouseup',function(event){
				  lastPoint=null;
			  });
		  })();
		  return resizer;
	  };
	  var resizer=$.imageResizer(),resizedImage;

	  if(!resizer){
		  resizer=$("<p>你的瀏覽器不支持這些功能:</p><ul><li>canvas</li><li>Blob</li><li>Uint8Array</li><li>FormData</li><li>atob</li></ul>")
	  };

	  $('.container').append(resizer);

	  $('input').change(function(event){
		  $('#newsPic').val("");
		  var file=this.files[0];
		  resizer.resize(file,function(file){
			  resizedImage=file;
		  });


	  });

	  //提交圖片
	  var submitPic = function(str){
		  var url = '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadBase64News.action';
		  var uploadCanvas = document.getElementById("resizedCanvas");

          var base64Str;
          var fileName;
		  if( !uploadCanvas || !url ){
		      base64Str = str;
		      fileName = "temp.gif";
		  }else{
              base64Str = uploadCanvas.toDataURL("image/png");
              fileName = "temp.png";
		  }

		  if(!base64Str) return;

		  var uploadImgParam = {
			  uploadFileBase64: base64Str.substring(base64Str.lastIndexOf(",") + 1),
			  uploadFileName: fileName
		  };

		  $.ajax({
			  type: 'post',
			  url: url,
			  data: uploadImgParam,
			  dataType: 'json',
			  success: function(data) {
				  //alert(JSON.stringify(data));
				  if(data && data.error == '0' && data.saveUrl)
				  	$('#newsPic').val(data.saveUrl);
				  else
                      parent.$.messager.alert("提示",data.message);
			  },
			  error: function(xhr, type) {
				  // 保存失败
				  console.log(xhr);
			  },
			  beforeSend : function() {
				  parent.$.messager.progress({
					  text : '图片上传中....'
				  });
			  },
			  complete : function() {
				  parent.$.messager.progress('close');
			  }
		  });
	  }

  </script>--%>
</html>