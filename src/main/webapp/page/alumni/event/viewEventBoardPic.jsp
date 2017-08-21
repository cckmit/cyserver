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
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/showpic.css" type="text/css"></link>
	
	<script type="text/javascript">
        $(function () {
        	$.ajax({
				url : '${pageContext.request.contextPath}/event/eventAction!viewEventBoardPicx.action',
				data : {
					id : $('#boardId').val()
				},
				dataType : 'json',
				success : function(data) {
					var normal = '';
					var small = '';
					$.each(data, function(i, item) {
			            normal += '<li><img width="680" height="380" src="' + item["n"] + '" /></li>';
			            if(i==0) {
			            	small += '<li class="on"><i class="arr2"></i><img width="118" height="64" src="' + item["n"] + '" /></li>'
			            } else {
			            	small += '<li><i class="arr2"></i><img width="118" height="64" src="' + item["n"] + '" /></li>'
			            }
			        });
			        if(normal != '') {
			        	$('#buttons').css('display','block'); 
			        	$('#normalUL').append(normal);
			        	$('#smallUL').append(small);
			        }					
				},
				beforeSend:function(){
					
				},
				complete:function(){
					function G(s){
						return document.getElementById(s);
					}
					
					function getStyle(obj, attr){
						if(obj.currentStyle){
							return obj.currentStyle[attr];
						}else{
							return getComputedStyle(obj, false)[attr];
						}
					}
					
					function Animate(obj, json){
						if(obj.timer){
							clearInterval(obj.timer);
						}
						obj.timer = setInterval(function(){
							for(var attr in json){
								var iCur = parseInt(getStyle(obj, attr));
								iCur = iCur ? iCur : 0;
								var iSpeed = (json[attr] - iCur) / 5;
								iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
								obj.style[attr] = iCur + iSpeed + 'px';
								if(iCur == json[attr]){
									clearInterval(obj.timer);
								}
							}
						}, 30);
					}
				
					var oPic = G("picBox");
					var oList = G("listBox");
					
					var oPrev = G("prev");
					var oNext = G("next");
					var oPrevTop = G("prevTop");
					var oNextTop = G("nextTop");
				
					var oPicLi = oPic.getElementsByTagName("li");
					var oListLi = oList.getElementsByTagName("li");
					var len1 = oPicLi.length;
					var len2 = oListLi.length;
					
					var oPicUl = oPic.getElementsByTagName("ul")[0];
					var oListUl = oList.getElementsByTagName("ul")[0];
					var w1 = oPicLi[0].offsetWidth;
					var w2 = oListLi[0].offsetWidth;
				
					oPicUl.style.width = w1 * len1 + "px";
					oListUl.style.width = w2 * len2 + "px";
				
					var index = 0;
					
					var num = 5;
					var num2 = Math.ceil(num / 2);
				
					function Change(){
				
						Animate(oPicUl, {left: - index * w1});
						
						if(index < num2){
							Animate(oListUl, {left: 0});
						}else if(index + num2 <= len2){
							Animate(oListUl, {left: - (index - num2 + 1) * w2});
						}else{
							Animate(oListUl, {left: - (len2 - num) * w2});
						}
				
						for (var i = 0; i < len2; i++) {
							oListLi[i].className = "";
							if(i == index){
								oListLi[i].className = "on";
							}
						}
					}
					
					oNextTop.onclick = oNext.onclick = function(){
						index ++;
						index = index == len2 ? 0 : index;
						Change();
					}
				
					oPrevTop.onclick = oPrev.onclick = function(){
						index --;
						index = index == -1 ? len2 -1 : index;
						Change();
					}
				
					for (var i = 0; i < len2; i++) {
						oListLi[i].index = i;
						oListLi[i].onclick = function(){
							index = this.index;
							Change();
						}
					}
				}
			});	
			
        });
        
    </script>
</head>

<body>
<input name="board.id" type="hidden" id="boardId" value="${param.id}">
<div class="mod18Box">
	<div class="mod18">
		<div id="buttons" style="display:none;">
		<span id="prev" class="btn prev"></span>
		<span id="next" class="btn next"></span>
		<span id="prevTop" class="btn prev"></span>
		<span id="nextTop" class="btn next"></span>
		</div>
		<div id="picBox" class="picBox">
			<ul class="cf" id="normalUL">
			</ul>
		</div>
	
		<div id="listBox" class="listBox">
			<ul class="cf" id="smallUL">
			</ul>
		</div>
	</div>
</div>
</body>
</html>