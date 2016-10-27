<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>

<c:set var="entityName" value="attachment" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/attachment" />
<!-- 命名空间 -->
<script type="text/javascript">
$(function() {
	var attachmentList,stateList,productList;
	postAjax(false, '${ctx}/crm/param/findAllPrType?prType=attachmentType',function success(data){attachmentList = data;}, 'json');
	//postAjax(false, '${ctx}/crm/param/findAllByPrType?prType=attachmentType',function success(data){stateList = data;}, 'json');
	postAjax(false, '${ctx}/product/purchase/find/all/product',function success(data){productList = data;}, 'json');
	
	$('#${entityName}_list').buildGridByJs({
		crud_listUrl : '${namespacePath}/customer/attachment/modification/list?id=${id}',
		crud_windowId : '${entityName}_input',
		crud_formId : '${entityName}_form',
		crud_inputUrl : '${namespacePath}/parameter_input',
		crud_editUrl : '${namespacePath}!edit.action',
		crud_saveUrl : '${namespacePath}!save.action',
		crud_deleteUrl : '${namespacePath}/delete',
		crud_winTitle : '附件列表',
		crud_winWidth : 500,
		crud_winHeight : 300,
		crud_idField : 'id',
		crud_sortName : 'id', 
		crud_sortOrder : 'asc',
		crud_pageSize :15,
		crud_pageList:[15,30,45,60],
		crud_columns : [ [
				{field : 'atName',title : '附件名',align : 'center',width : fixWidth(0.2),sortable : true,formatter : builderLink},
				//{field : 'cproduct',title : '申请产品',align : 'center',width : fixWidth(0.1),sortable : true, formatter: formatProductName},
				{field : 'atType',title : '附件类型',align : 'center',width : fixWidth(0.2),sortable : true,formatter : function(value, rec){return formatVal(attachmentList,value)}},
				{field : 'atCratetime',title : '创建时间',align : 'center',width : fixWidth(0.2),formatter : formatDateYYYYMMDDHHMMSS,sortable : true},
				{field : 'atCreateid',title : '创建人',align : 'center',width : fixWidth(0.2),sortable : true,formatter : formatOperator},
				//{field : 'atState',title : '状态',align : 'center',width : fixWidth(0.1),sortable : true},
				{field : 'opt',title : '操作',width : 300,align : 'center',formatter : builderOperationLinks} 
		] ],
		crud_query : {title : '高级查询',windowId : '${entityName}_query',form : '${pagePath}/${namespacePath}-query.jsp',formId : '${entityName}_queryForm',width : 500,height : 230,
			callback : function() {$('#${entityName}_normalQuery').searchbox('setValue', '');}
		}
	});
	//格式化操作员
	function formatOperator(value,rec) {
		var val;
		postAjax(false,"${ctx}/crm/param/getStaffName?staffId="+rec.atCreateid,function(data){val = data[0];}, 'json');
		return val;
		//return '王武';
	}

	
	//构建超链接
	function builderLink(value, rec) {return '<a href="${namespacePath}/detail?id=' + rec.id + '">' + value + '</span>';}

	//构建操作链接
	function builderOperationLinks(value,rec){
		var foId = rec.id;
		var links = '<a href="${namespacePath}/detail?id=' + foId + '">查看</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
			links += '<a href="${namespacePath}/edit2?id=' + foId + '">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
			links += '<a href="#" onclick="deleteAttachment(' + foId + ')">删除</span>&nbsp;&nbsp;';
		return links;
	}
});

function deleteAttachment(id) {
	if(confirm('确认是否删除附件?')){
		var url = getContextPath() + "/attachment/delete?id=" + id;
		postAjax(false, url, function(result) {
			var result = eval('('+result+')').result;
			if(result.success) {
				$.messager.show({ title: 'success',msg: "删除成功!"});
				$('#${entityName}_list').datagrid('load',{});
			}else{
				$.messager.show({title: 'Error',msg: "删除失败,请稍后重试!"})
			} 
		});
	}
}

</script>
<div class="easyui-layout" fit="true">
	<!-- 以下表头工具栏-->
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<table cellpadding="0" cellspacing="0" style="width: 100%">
				<tr>
					<td>
						<a href="${namespacePath}/customer/modification/create?id=${id}&customerid=${customerid}&type=${type}" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						<a onclick="closeCurPage('信息变更附件')" iconCls="icon-back" class="easyui-linkbutton"  plain="true" >返回</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 以下列表 -->
	<div region="center" border="false" style="width: 100%;">
		<table id="${entityName}_list">
		</table>
	</div>
</div>
