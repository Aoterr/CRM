<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/doing.jsp"%>
<!-- 实体名 -->
<c:set var="entityName" value="modification" />
<!-- 命名空间 -->
<c:set var="namespacePath" value="${ctx}/modification" />
<script type="text/javascript">
window.top["modification_customer_list"]=function(){
	$('#${entityName}_list').datagrid('load');
};
var stateList,getProvinces,getArea,getCity,customer;
$(function() {
	postAjax(false, '${ctx}/crm/param/findAllByPrType?prType=requestState',function success(data){stateList = data;}, 'json');
	/* postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json'); 
	postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');*/
	postAjax(false, "${ctx}/crm/param/findAllByPrType?prType=OrganFortune", function(data) {getCity = data}, 'json');
	//加载系统参数
	var stateList,productList;
	//postAjax(false, '${ctx}/crm/param/findAllByPrType?prType=requestState',function success(data){stateList = data;}, 'json');
	//postAjax(false, '${namespacePath}/find/all/product',function success(data){productList = data;}, 'json');
	var parameter='';
	if('${mnState}'!='')parameter='?search_EQ_M.mnState=${mnState}';
	$('#${entityName}_list').buildGridByJs({
		crud_listUrl : '${namespacePath}/customer/list/search'+parameter,
		crud_windowId : '${entityName}business_input',
		crud_formId : '${entityName}business_form',
		crud_inputUrl : '${namespacePath}/parameter_input',
		crud_editUrl : '${namespacePath}!edit.action',
		crud_saveUrl : '${namespacePath}!save.action',
		crud_deleteUrl : '${namespacePath}/delete',
		crud_searchUrl : '${namespacePath}/customer/list/search',
		crud_winTitle : '申请变更',
		crud_winWidth : 500,
		crud_winHeight : 300,
		crud_idField : 'id',
		crud_sortName : 'M.id',
		crud_sortOrder : 'desc',
		crud_pageSize :15,
		crud_pageList:[15,30,45,60],
		crud_columns : [ [
				{field : 'crName',title : '客户姓名',align : 'center',width : fixWidth(0.1),sortable : true,formatter : function(value,rec){ customer = formatCustomer(value,rec);return customer?customer.crName:''}},
				{field : 'crIdnum',title : '证件号码',align : 'center',width : fixWidth(0.1),sortable : true,
					formatter :  function(value,rec){
					    
						if(customer.crIdnum!=null)
						var	val = '****'+customer.crIdnum.substring(customer.crIdnum.length-4);
						return val;
				//	return customer?customer.crIdnum:''
					}},
				{field : 'crCityId',title : '城市',align : 'center',width : fixWidth(0.1),sortable : true ,formatter :  function(value,rec){
					return customer?filterArea(getCity,customer.crCityId):'';
					//return customer?filterArea(getArea,customer.crCityId)?filterAreaById(getArea,customer.crCityId).name:'':''
							}} ,
				{field : 'mnDate',title : '变更日期',align : 'center',width : fixWidth(0.1),formatter : formatDate,sortable : true},
				{field : 'crGather',title : '客户经理',align : 'center',width : fixWidth(0.1),sortable : true,formatter :  function(value,rec){
						var prName123='';
						if(customer!=null){
							$.ajax({
			          		  	type: 'POST',
			          		  	async:false,
			          		  	url: '${ctx}/crm/param/getStaffName?staffId='+customer.crGather,
			          		  	success: function(row){
					            	prName123 = row[0];
			              		},
			          			dataType: 'json'
			          		}); 
						}
						return prName123;
					}},
				{field : 'crCustomerNumber',title : '客户编号',align : 'center',width : fixWidth(0.1),sortable : true,formatter :  function(value,rec){return customer?customer.crCustomerNumber:''}},
				{field : 'crCustomerType',title : '客户类型',align : 'center',width : fixWidth(0.1),sortable : true,formatter :  function(value,rec){
						var prName123='';
						if(customer!=null){
							$.ajax({
			          		  	type: 'POST',
			          		  	async:false,
			          		  	url: '${ctx}/crm/param/findAllByPrType?prType=customerType',
			          		  	success: function(row){
			          		  	for(var i=0;i<row.length;i++){
				            		if(row[i].prValue == customer.crCustomerType) {
				            			prName123 = row[i].prName;
				            			}
			            			}
			              		},
			          			dataType: 'json'
			          		}); 
							}
		         		
		            	return prName123;
					}},
				{field : 'mnState',title : '状态',align : 'center',width : fixWidth(0.1),sortable : true,formatter : function(value, rec){return formatVal(stateList,value)}},	
				{field : 'opt',title : '操作',width : 300,align : 'center',formatter : builderOperationLinks} 
		] ],
		crud_query : {
			title : '高级查询',
			windowId : '${entityName}_query',
			form : '${namespacePath}/query_forcustomer',
			formId : '${entityName}_queryForm',
			width : 550,
			height : 270,
			callback : function() {$('#${entityName}_normalQuery').searchbox('setValue', '');}
		}
	});

	//格式化客户信息
	function formatCustomer(value,rec) {
		//var val,showCustomerUrl,flag = hasShowCustomerPermision(rec.type);
		var object; 
		postAjax(false, "${ctx}/crm/customer/customerById?id=" + rec.mnSourceId,function(data){object = data}, 'json');
		//showCustomerUrl = (rec.type == 1) ? '${ctx}/crm/customer/show_xd?id=' + rec.customerid : '${ctx}/crm/customer/show_lc?id=' + rec.customerid 
		//return flag ? '<a href="' + showCustomerUrl + '">' + rec.name + '</span>' : rec.name;

		return object;
	}

	//格式化客户经理
	function formatCustomerManager(value, rec) {
		//var name;
		//postAjax(false, "${ctx}/crm/param/getStaffName?staffId=" + value ,function(data){name = data }, 'json');
		//return name == 'undifined' ? "" : name;
	}


	//格式化产品名称
	function formatProductName(value, rec) {
		var val = '',len = productList.length;
		for(var i = 0; i < len; i++){
			if(productList[i].id == value) {
				val = productList[i].ptProductName;
				break;
			}
		};
		return val;
	}

	//构建操作链接
	function builderOperationLinks(value,rec){
		var links =  '<a href="javascript:void(0)"  onclick="jumpShowPage('+ rec.id+')">查看</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
			links +=  ((rec.mnState == 1||rec.mnState == 2)?'<a href="javascript:void(0)"  onclick="jumpEditPage('+ rec.id+')">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;':'');
	//		links += '<security:authorize ifAllGranted="010209">' + ((rec.mnState == 1||rec.mnState == 2)?'<a href="javascript:void(0)"  onclick="jumpEditPageSuper('+ rec.id+')">高级编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;':'') + '</security:authorize>';
			links += ((rec.mnState == 3)?'<a href="javascript:void(0)"  onclick="jumpInspectionPage('+ rec.id+')">质检</a>&nbsp;&nbsp;|&nbsp;&nbsp;':'') ;
			links += '<a href="javascript:void(0)"  onclick="jumpAccessoryPage('+ rec.mnSourceId +','+ rec.id+')">附件</a>'; 
		return links;
	}
});

function cleanAndReload(){
	$('#${entityName}_normalQuery').searchbox('setValue','');
	$('#${entityName}_list').datagrid('load',{});
}

function filterArea(getArea,id){
	var len = getArea.length;
	for(i=0;i<len;i++){
		if(getArea[i].prValue==id){
				return getArea[i].prName;
			}
		}
	}
function jumpShowPage(jumpId){
	var url = '${namespacePath}/customer/show?id='+jumpId;
	createTabsInframePage('信息变更查看',url);
}

function jumpEditPage(jumpId){
	var url = '${namespacePath}/customer/edit?id='+jumpId;
	createTabsInframePage('信息变更修改',url);
 }

// 编辑所有基本信息  +关键信息
function jumpEditPageSuper(jumpId){
	var url = '${namespacePath}/customer/edit_super?id='+jumpId;
	createTabsInframePage('信息变更修改',url);
 }

function jumpInspectionPage(jumpId){
	var url = '${namespacePath}/customer/preliminary/review?id='+jumpId;
	createTabsInframePage('信息变更质检',url);
}
function jumpAccessoryPage(mnSourceId,jumpId){
	var url = '${ctx}/attachment/show/customer/attachment/modification?customerid=' + mnSourceId +'&id=' + jumpId +'&type=6';
	createTabsInframePage('信息变更附件',url);
}
</script>
<div class="easyui-layout" fit="true" id="main">
	<!-- 以下表头工具栏-->
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<table cellpadding="0" cellspacing="0" style="width: 100%">
				<tr>
					<td>
					    <c:if test="${mnState!=2}">
						<div class=datagrid-btn-separator></div> 
							<a href="#" onclick="createTabsInframePage('信息变更新增','${namespacePath}/customer/create')" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
							<a href="#" onclick="createTabsInframePage('信息变更新增','${namespacePath}/customer/create_super')" class="easyui-linkbutton" iconCls="icon-add" plain="true">高级变更新增</a>
						</c:if>
					</td>
					<td style="text-align: right">
						<input id="${entityName}_normalQuery" class="easyui-searchbox" data-options="prompt:'请输入查询内容', menu:'#mm',searcher:function(value,name){if(name=='customerid'){
							$('#${entityName}_list').buildGridByJs('query1',{'search_LIKE_C.crName': value});
						}$('#${entityName}_queryForm').form('clear');}"  style="width: 200px"></input> 
						<a href="#"
						class="easyui-linkbutton" iconCls="icon-adsearch" plain="true"
						onclick="$('#${entityName}_list').buildGridByJs('query')">高级查询</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-cleanreload" plain="true" onclick="cleanAndReload()">重新载入</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 以下列表 -->
	<div region="center" border="false" style="width: 100%;">
		<table id="${entityName}_list"></table>
	</div>
</div>

<div id="mm" style="width:120px">
	<div data-options="name:'customerid',iconCls:'icon-crName'">客户姓名</div>
</div>
