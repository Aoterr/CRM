<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<c:set var="entityName" value="attachment"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/attachment"  /><!-- 命名空间 -->
<script type="text/javascript">
$(function() {
	//$(".m_table td").has("label").css("text-align","right");
	
	$.post('${namespacePath}/get', {id : ${id}}, function(row) {
		//获取附件类型
		comboboxUtil.setComboboxByUrl('atTypeOne', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+row.atTypeOne, 'prValue', 'prName', '150', false);
		comboboxUtil.setComboboxByUrl('atType', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+row.atType, 'prValue', 'prName', '150', false);
		$('#ff').form('load',row);
	}, 'json');

	$('#detail').datagrid({ 
		url:encodeURI("${ctx}/file/get/files?id=" + ${id}),
	    columns:[[  
			{field:'fName',title:'文件名',width:fixWidth(0.2),formatter: formatFileName},  
			{field:'fCratetime',title:'上传时间',width:fixWidth(0.2),align:'center', formatter: formatDateYYYYMMDDHHMMSS},  
			{field:'fCreateid',title:'上传人',width:fixWidth(0.2),align:'center',formatter : formatOperator},  
			{field:'fModifytime',title:'最后修改时间',width:fixWidth(0.2), align:'center',formatter: formatDateYYYYMMDDHHMMSS},  
			{field:'fModifyid',title:'最后修改人',width:fixWidth(0.15),align:'center', formatter : formatOperator}
	    ]],
	    onDblClickRow:function(rowIndex, rowData){
	    	loadCustomerInfo(rowData)//加载客户信息
		    loadBankInfo(rowData);//加载银行信息
		    loadContactPerson(rowData);//加载联系人
			$('#dd').dialog('close');
		}  
	}); 

	//格式化操作员
	function formatOperator(value,rec) {
		var val;
		postAjax(false,"${ctx}/crm/param/getStaffName?staffId="+rec.fCreateid,function(data){val = data[0]}, 'json');
		return val;
		//return '王武';
	}

	//格式化文件名
	function formatFileName(value, rec) {
		return '<a href="${ctx}/file/download/' + rec.id + '" >' + value + '</a>';
	}
});



</script>

<body class="easyui-layout">
	<div data-options="region:'center'" >
	    <form id="ff" method="post" action="${namespacePath}/save">
		    <table  class="m_table" style="width:100%;">
		    	<tr>
					<th class="title" colspan="8">附件详细信息</th>
				</tr>
			   <tr>
	      			<td><label for="atName">附件名</label></td>
					<td><input id="atName" name="atName"  disabled="disabled"></td>
					<td><label for="atType">附件类型</label></td>
					<td><input id="atTypeOne" class="easyui-combobox" name="atTypeOne" disabled="disabled"/>
						<input id="atType" class="easyui-combobox" name="atType" disabled="disabled"/>
					</td>
			   </tr>
			   <tr>
	      			<td><label for="atMemo">备注</label></td>
					<td colspan="3"><textarea name="atMemo"  style="height:80px;width: 400px;" disabled="disabled"></textarea></td>
			   </tr> 
			</table>
			<br><br>
			<table width="100%">                                              
			    <tr><td colspan="4"><table id="detail" width="100%"></table></td></tr>
	        </table>
	    </form>
   	     <div style="background:#fafafa;text-align:center;padding:5px">  
            <a href="#" class="easyui-linkbutton" onclick="history.go(-1);return false;">返回</a>  
        </div> 
	</div>
</body>