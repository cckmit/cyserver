<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="控件编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="控件编号"%>
<%@ attribute name="height" type="java.lang.String" required="false" description="编辑器高度"%>
<%@ attribute name="width" type="java.lang.String" required="false" description="编辑器宽度" %>
<%@ attribute name="value" type="java.lang.String" required="false" description="编辑器宽度" %>
<!--style给定宽度可以影响编辑器的最终宽度-->
<script type="text/plain" id="${id}" style="width:${empty width ? 1000:width}px;height:${empty height ? 240:height}px;">${value}</script>

<input name="${name}" type="hidden" id="${id}Input" value="<c:out value="${value}" />">
<script type="text/javascript">
	$(function(){
		init();
	})
	function init() {
		UM.getEditor('${id}').setContent($("#${id}Input").val()) ;
	}
	//这段要放在文本编辑器的实例化之后
	function ${id}Uptext(){
		if (UM.getEditor('${id}').hasContents() || $.trim(UM.getEditor('${id}').getContent()) != $("#${id}Input").val()){
			$("#${id}Input").val(UM.getEditor('${id}').getContent());
		}
	}
</script>