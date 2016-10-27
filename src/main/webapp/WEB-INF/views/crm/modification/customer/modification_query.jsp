
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>

<c:set var="entityName" value="customer" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/modification" />
<!-- 命名空间 -->
<script type="text/javascript">
	$(function() {
		var stateList, productList;
		postAjax(false,
				'${ctx}/crm/param/findAllByPrTypePro?prType=requestState',
				function success(data) {
					stateList = data;
				}, 'json');
		comboboxUtil.setComboboxByData("state", stateList, 'prValue', 'prName',
				'100', false);
		$('#crDepartment').combotree(
				{
					url : '${ctx}/crm/param/findAllDepart',
					width : 250,
					onSelect : function(node) {
						postAjax(false,
								'${ctx}/crm/customer/getStaffByDepartment?departmentId='
										+ node.id, function(data) {
									if (data == null || data == '') {
										$("#busiDeparment").val('000000');
									} else {
										$("#busiDeparment").val(data);
									}
								}, 'json');
						//comboboxUtil.setComboboxByUrl('staff', '${ctx}/crm/attribution/getStaffByDepartment?departmentId=' + node.id, 'id', 'name', '150', false);
					}
				}); //业务部门 
	});
</script>
<form id="modification_queryForm" method="post"
	style="padding: 10px 20px">
<div class="subtitle">请输入查询参数：</div>
<table class="m_table" style="width: 100%;">
	<tr>
		<td>证件号码 :</td>
		<td style="width: 125px"><input name="search_EQ_C.crIdnum"
			class="query" style="width: 200px" /></td>
		<td nowrap="nowrap">变更类型 :</td>
		<td><input id="modifiType" class="query easyui-combobox"
			name="search_EQ_MODIFI.searchType"
			data-options="valueField:'id',textField:'fdFieldCn',url:'${ctx}/modification/querySearch/getModifiSearchValue?fdClassEn=TongXunXinXi'"
			style="width: 150px" /></td>
		</td>
	</tr>
	<tr>
		<td>变更时间 :</td>
		<td style="width: 400px" colspan="3"><input type="text"
			class="query Wdate" style="width: 160px;" name="search_GTE_M.mnDate"
			onclick="WdatePicker({dateFmt:'yyyy/MM/dd'})" /> ~ <input
			type="text" class="query Wdate" style="width: 160px;"
			name="search_LTE_endDate-M.mnDate"
			onclick="WdatePicker({dateFmt:'yyyy/MM/dd'})" /></td>
	</tr>
</table>
</form>