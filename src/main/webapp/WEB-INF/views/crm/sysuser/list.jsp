<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>

<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/JSON-js-master/json2.js"></script>

<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuivalidate.jsp"%>
<%@ include file="/static/common/doing.jsp"%>

<c:set var="entityName" value="sysuser" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/sysuser" />
<!-- 命名空间 -->
<script type="text/javascript">
window.top["sysuser_list"]=function(){
	$('#${entityName}_list').datagrid('load');
};
var getProvinces,getArea,system;
$(function() {	

	$('#${entityName}_list')
				.buildGridByJs(
						{
							crud_listUrl : '${namespacePath}/list',
							crud_windowId : '${entityName}_input',
							crud_formId : '${entityName}_form',
							crud_inputUrl : '${namespacePath}/parameter_input',
							crud_editUrl : '${namespacePath}!edit.action',
							crud_saveUrl : '${namespacePath}!save.action',
							crud_deleteUrl : '${namespacePath}/delete',
							crud_winTitle : '用户管理',
							crud_winWidth : 500,
							crud_winHeight : 300,
							crud_idField : 'id',
							crud_sortName : 'id',
							crud_sortOrder : 'desc',
							cache : true,
							crud_pageSize : 15,
							crud_pageList : [ 15, 30, 45, 60 ],
							crud_columns : [ [
									{
										field : 'userName',
										title : '用户名',
										align : 'center',
										width : fixWidth(0.1),
										sortable : true
									},
									{
										field : 'lastLoginTime',
										title : '上次登录时间',
										align : 'center',
										width : fixWidth(0.1),
										sortable : true,
										formatter : formatDateNew
									},
									{
										field : 'userLevel',
										title : '用户级别',
										align : 'center',
										width : fixWidth(0.1),
										sortable : true,
										formatter : function(value, rec) {
											var prName123 = '';
											if (value == '1') {
												prName123 = "普通用户";
											}else{
												prName123 = "系统管理员";
											}
											return prName123;
										}
									},
									{
										field : 'opt',
										title : '操作',
										width : 300,
										align : 'center',
										formatter : function(value, rec) {
											var foId = rec.id;
											var links = '<a href="javascript:void(0)"  onclick="resetpwd('+ foId+ ')">重置密码</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
											links += '<a href="javascript:void(0)"  onclick="del('+ foId+ ')">删除</a>'
											return links;
										}
									} 
									] ]
						});
	});
	
	var jumpWord;
	function cleanAndReload() {
		$('#${entityName}_normalQuery').searchbox('setValue', '');
		$('#${entityName}_list').datagrid('load', {});
	}
	// 格式化时间
	function formatDateNew(value) {
		if (value == null) {
			return "";
		}
		var date = new Date(value);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+checkTime(date.getHours())+':'+checkTime(date.getMinutes())+':'+checkTime(date.getSeconds());
	}
	function checkTime(i){
	    if (i<10){
	        i = "0" + i;
	    }
	    return i;
	}
	
	function createjump(word){
		jumpWord = word;
		var url = '${namespacePath}/'+jumpWord;
		createTabsInframePage('用户信息新增',url)
	}
	function resetpwd(id){
		jumpWord=id;
		if(confirm('确认是重置该用户密码?')){
			var url =getContextPath() + "/sysuser/resetpwd?id="+jumpWord; 
			postAjax(false, url, function(result) {
				var result = eval('('+result+')').result;
				if(result.success) {
					$.messager.show({ title: 'success',msg: result.sucMsg});
					$('#${entityName}_list').datagrid('load',{});
				}else{
					$.messager.show({title: 'Error',msg: result.errMsg})
				} 
			});
		}
	}
	function del(id,type){
		if(confirm('确认是否删除当前这项记录?')){
			var url =getContextPath() + "/sysuser/del?id="+jumpWord; 
			postAjax(false, url, function(result) {
				var result = eval('('+result+')').result;
				if(result.success) {
					$.messager.show({ title: 'success',msg: result.sucMsg});
					$('#${entityName}_list').datagrid('load',{});
				}else{
					$.messager.show({title: 'Error',msg: result.errMsg})
				} 
			});
		}
	}
</script>
<div class="easyui-layout" fit="true" id="main">
	<!-- 以下表头工具栏-->
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<table cellpadding="0" cellspacing="0" style="width: 100%">
				<tr>
					<td>
						<div class=datagrid-btn-separator></div> 
							<a href="#" class="easyui-menubutton" data-options="iconCls:'icon-add'" onclick="createjump('create_sysuser');">新增</a>					
					</td>
					<td style="text-align: right"><input
						id="${entityName}_normalQuery" class="easyui-searchbox"
						data-options="prompt:'请输入查询内容', menu:'#mm', searcher:function(value,name){
							if(name=='userName'){
								$('#${entityName}_list').buildGridByJs('load',{'search_LIKE_userName': value});
							}
						
							$('#${entityName}_normalQuery').searchbox('setValue','');
						
							$('#${entityName}_queryForm').form('clear');
							}"
						style="width: 220px"></input>
						<a href="#" class="easyui-linkbutton" iconCls="icon-cleanreload"
						plain="true" onclick="cleanAndReload()">重新载入</a></td>
				</tr>
			</table>
		</div>
	</div>
	
	<!-- 查询项 -->
	<div id="mm" style="width: 120px">
		<div data-options="name:'userName',iconCls:'icon-crName'">用户名</div>
	</div>
	<!-- 以下列表 -->
	<div region="center" border="false" style="width: 100%;">
		<table id="${entityName}_list">
		</table>
	</div>
 
</div>

 

