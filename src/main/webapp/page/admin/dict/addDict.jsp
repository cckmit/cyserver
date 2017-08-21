<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form id="adddict" method="post" action="">
			<table align="center" cellpadding="0" cellspacing="0" class="tlh-table">
				<tr>
					<td style="width:90px;" align="right">字典名称：</td>
					<td>
						<input type="hidden" name="dictModel.dictTypeId" id="dictTypeId" value="${param.dictTypeId}">
						<input name="dictModel.dictName" id="dictName"  class="easyui-validatebox" data-options="required:'true'"/>
					</td>
				</tr>
				<tr>
					<td  align="right">值：</td>
					<td>
						<input  name="dictModel.dictValue" class="easyui-validatebox" data-options="validType:'numbers',required:'true'"/>
					</td>
				</tr>
				<tr>
					<td align="right">字典类别：</td>
					<td>
						<input readonly="readonly" value="${param.dictTypeName}" class="easyui-validatebox" data-options="required:'true'"/>
					</td>
				</tr>
			</table>
		</form>	
