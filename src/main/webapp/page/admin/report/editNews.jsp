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
	var sChannel = "" ;	//lixun
	var sSource = "" ;	//lixun
	var isChanged = 0;

    var editor;

	$(function() {
		//富文本
        editor = UM.getEditor('content');

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

	});

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

		/*$('#qudao').combogrid("grid").datagrid("loadData", jsonstr);*/


		if( '${news.category}' != '0'){
			sChannel = '${news.channel}';
		}

	});

	function removeNewsPic(newsPic) {
		$(newsPic).parent().remove();
		$("#news_upload_button").prop('disabled', false);
	}


	function submitForm($dialog, $grid, $pjq){
		if($('#introduction').val()==''){
			parent.$.messager.alert('提示', '请输入新闻简介', 'error');
			return false;
		}
		/*if($('#newsUrl').val()==''){
			parent.$.messager.alert('提示', '请输入新闻网页链接', 'error');
			return false;
		}*/
		if($('input[name="news.pic"]').val()==undefined){
			parent.$.messager.alert('提示', '请上传活动封面图片', 'error');
			return false;
		}



		if ($('form').form('validate'))
		{
			var convert = "${convert}";
			var URL = "";
			if(convert==""){
				URL = "${pageContext.request.contextPath}/mobile/news/newsAction!update.action?isChanged=" + isChanged;
			}else if(convert=="1"){
				//新闻添加,将地方新闻COPY一份转为总会新闻
				$("#newsId").val("0");
				URL = "${pageContext.request.contextPath}/mobile/news/newsAction!save.action?isChanged=" + isChanged;
			}

            var resultStr = $('form').serialize();
            /*var submitArray = resultStr.split('&');
            resultStr = '';
            for(var i in submitArray){
                var item = submitArray[i];
                if(item.indexOf('news.content') >= 0){
					var realHtml = editor.getContent();
					//realHtml = realHtml.substring(realHtml.indexOf('</p>')+4, realHtml.length);
                    alert(realHtml);
                    var encodeHtml = escape(realHtml);
                    alert(encodeHtml);
                    item = 'news.content=' + encodeHtml;
                }
                if(i > 0){
                    resultStr += '&';
                }
                resultStr += item;
            }*/

			$.ajax({
				url : URL,
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

	function addHtml( data )
	{
//		alert( JSON.stringify( data.rows[1] ) );
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
                'url':url+data.rows[i].dictValue,'icons':[{iconCls:'icon-clear',handler: function(e){$(this).combotree('clear');}}],'onBeforeSelect': func, 'onChange':function(){isChanged=1;} };
        	$('#newsType_'+i).combotree( datas );
        	$('#channel_'+i).hide();

		}

		//显示
		var ids = '${news.channel}'.split( ',' );
		var iValues = '${news.channelIds}'.split( ',' );
		for( var i = 0; i < ids.length; ++i )
		{
			for( var j = 0; j < lines; ++j )
			{
				if( data.rows[j].dictValue == ids[i] )
				{
					$('#channel_'+j).show();
					$('#newsType_'+j).combotree({'required': false });
					$('#newsType_'+j).combotree( 'setValue', iValues[i]) ;
				}
			}
		}

	}

	/**
	 * 预览新闻
     */
	function previewMobNew() {
		var title = $("#newsTitle").val();
		var content = editor.getContent();

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
<form method="post" id="editNewsForm" class="form">
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
					<input name="news.newsId" type="hidden" id="newsId"
							value="${news.newsId}">
					<input id="newsTitle" name="news.title" class="easyui-validatebox"
						style="width: 700px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="100" value="${news.title}"/>
				</td>
			</tr>
			<tr>
				<th>
					新闻简介
				</th>
				<td colspan="3">
					<textarea id="introduction" rows="7" cols="100"
						name="news.introduction">${news.introduction}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					网页链接
				</th>
				<td colspan="3">
					<textarea id="newsUrl" rows="3" cols="100" name="news.newsUrl" maxlength="120">${news.newsUrl}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					频道
				</th>
				<td colspan="3">
					<input name="news.channelName" class="easyui-combobox" style="width: 150px;" value="${news.channelName}"
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

					<select id="newstype" name="news.type"  class="easyui-combogrid" style="width:150px" data-options="
							panelWidth: 200,
							required:true,
							multiple: true,
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
							onLoadSuccess:function(data){
								$('#newstype').combogrid('setValues', ${news.typeStr});
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
								//url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?source='+sSource+'&channel='+sChannel;
								//$('#newsTypeCate').combotree('reload',url);
								sSource = value.value;
								$('#qudao').combogrid('clear');
								$('#qudao').combogrid('grid').datagrid('loadData', jsonstr);
						},
						onLoadSuccess:function(){
								sSource =  ${news.source};
								$('#source' ).combobox( 'setValue', ${news.source} );
								if( ${news.source} == 2 ){
								}
							}
						">
						<option value="1">总会</option>
						<option value="2">本会</option>
					</select>
				</td>
			</tr>

			<tr id="trQudao">
				<th>渠道</th>

				<td colspan="3">
					<select  id="qudao" name="news.channel" class="easyui-combogrid" style="width:150px" data-options="
							panelWidth: 200,
							multiple: true,
							required:true,
							idField: 'dictValue',
							textField: 'dictName',
							columns: [[
								{field:'dictValue',checkbox:true},
								{field:'dictName',title:'标签名称',width:80}
							]],
							fitColumns: true,
							editable:false,
							panelHeight:110,
							onCheck: function( rowIndex, rowData )
							{
								$('#channel_'+rowIndex).show();
								$('#newsType_'+rowIndex).combotree({'required': true});
								isChanged = 1;
							},
							onUncheck: function( rowIndex, rowData )
							{
								$( '#channel_'+rowIndex ).hide();
								$( '#newsType_'+rowIndex ).combotree({'required': false});
								$( '#newsType_'+rowIndex ).combotree('clear');
							},
							onLoadSuccess : function( data ) {
								$('#qudao').combogrid('setValues', '${news.channel}');
								//alert(JSON.stringify(data));
								addHtml( data );
							}

							">
					</select>

				</td>
				<!-- lixun 2016-8-17
				<td>
					<select id="qudao" class="easyui-combobox" style="width: 150px" name="news.channel" data-options="
						editable:false,required:true,panelHeight:80,
						onSelect: function(value){
							$('#newsTypeCate').combotree('clear');
							sChannel = value.value;
							var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?source='+sSource+'&channel='+sChannel;
							$('#newsTypeCate').combotree('reload',url);
						},
						onLoadSuccess : function(){
							$('#qudao').combobox('setValue', '${news.channel}');
							<%--$('#newsTypeCate').combotree('reload',url);--%>
						}
						" >
						<option value="10">手机</option>
						<option value="20">网页</option>
						<option value="30">微信</option>
					</select>
				</td>
				-->
			</tr>
			<!-- lixun 2016-8-17
			<tr>
				<th>
					新闻栏目
				</th>
				<td colspan="3">
					<select name="news.category"  id="newsTypeCate" class="easyui-combotree"
							data-options="
								editable:false,
								required:true,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								panelHeight:150,
								url:'${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action',
								icons:[{
									iconCls:'icon-clear',
									handler: function(e){
										$('#newsTypeCate').combotree('clear');
									}
								}],
								onBeforeSelect: function(node){
									var isLeaf = $(this).tree('isLeaf', node.target);
									if (!isLeaf) {
										$.messager.show({
											msg: '请选择子栏目'
										});
										return false;
									}
                                },
                                onLoadSuccess : function( node, data )
                                {
                                }"
							style="width: 150px;">
					</select>
				</td>
			</tr>
			-->
			<tr id="dept">
				<th>
					所属组织
				</th>
				<td colspan="3">

					<input name="news.dept_id" id="deptId" value="${news.dept_id}" type="hidden" />
					<input name="news.dept_id" id="deptName" value="${news.dept_name}" type="hidden" />
					${news.dept_name}
				</td>
			</tr>

			<tr>
				<th>
					时间
				</th>
				<td colspan="3">
					<input name="news.createDate" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="${news.createDate}" />
				</td>
			</tr>

			<tr>
				<th>
					新闻内容
				</th>
				<td colspan="3">
					<script id="content" name="news.content" type="text/plain" style="width:700px;height:300px;">
						${news.content}
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
					<img id="oldPic" style="width:400px" src="${news.pic}"/>
					<input type="hidden" name="news.pic" id="newsPic" value="${news.pic}" />
				</td>
			</tr>--%>
			<tr>
				<th>
					新闻封面上传
				</th>
				<td colspan="3">
					<c:choose>
						<c:when test="${news.pic!=null and news.pic!=''}">
							<input type="button" id="news_upload_button" value="上传图片" disabled="disabled">
						</c:when>
						<c:otherwise>
							<input type="button" id="news_upload_button" value="上传图片">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>
					新闻封面图片
				</th>
				<td colspan="3">
					<div id="newsPic">
						<c:if test="${news.pic!=null and news.pic!=''}">
							<div style="float:left;width:180px;"><img src="${news.pic}" width="150px" height="150px"/><div class="bb001" onclick="removeNewsPic(this)"></div><input type="hidden" name="news.pic" value="${news.pic}"/></div>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<th>
					新闻状态
				</th>
				<td colspan="3">
					${news.status}
				</td>
			</tr>
			<tr>
				<th>
					审批意见
				</th>
				<td colspan="3">
					${news.opinions}
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
  <script type="text/javascript">
   /*   var tmp=$('<div class="resizer">'+
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
          $('#oldPic').remove();
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
*/
  </script>
</html>