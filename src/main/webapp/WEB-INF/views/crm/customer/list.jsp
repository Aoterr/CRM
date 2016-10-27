<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
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

<c:set var="entityName" value="customer" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer" />
<!-- 命名空间 -->
<script type="text/javascript">
window.top["customer_list"]=function(){
	$('#${entityName}_list').datagrid('load');
};
var getProvinces,getArea,system;
$(function() {	
	var customerTypeList,idtypeList;
	/* postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');   */
	postAjax(false, "${ctx}/crm/param/findAllByPrType?prType=OrganFortune", function(data) {getArea = data}, 'json');
	postAjax(false, "${ctx}/crm/param/findAllByPrType?prType=customerType", function(data) {customerTypeList = data}, 'json');
	postAjax(false, "${ctx}/crm/param/findAllPrType?prType=idtype", function(data) {idtypeList = data}, 'json');
	postAjax(false, "${namespacePath}/sysParam", function(data) {
				system = data.logoPath;system=system=='logo'?false:true;}, 'json'); //是否隐藏地区字段 
	
	
		$('#${entityName}_list').buildGridByJs(
				{
					crud_listUrl : '${namespacePath}/list',
					crud_windowId : '${entityName}_input',
					crud_formId : '${entityName}_form',
					crud_inputUrl : '${namespacePath}/parameter_input',
					crud_editUrl : '${namespacePath}!edit.action',
					crud_saveUrl : '${namespacePath}!save.action',
					crud_deleteUrl : '${namespacePath}/delete',
					crud_winTitle : '客户服务',
					crud_winWidth : 500,
					crud_winHeight : 300,
					crud_idField : 'id',
					crud_sortName : 'crModifyDate,id',
					crud_sortOrder : 'desc',
					cache:true,
					crud_pageSize :15,
					crud_pageList:[15,30,45,60],
					crud_frozenColumns: [[
					{
  						field: 'crCustomerNumber',
			            title: '客户编号',
			            align:'center',
			            width: fixWidth(0.1),
			            sortable: true
			        }, {
			            field: 'crName',
			            title: '客户姓名',
			            align:'center',
			            width: fixWidth(0.1),
			            sortable: true,
			            formatter : function(value, rec) {
							var links = '<a href="javascript:void(0)"  onclick="show('+ rec.id+')">'+value+'</span>';
							return links;
			            	}
				   		}]],

						    crud_columns: [[ {
					            field: 'crIdtype',
					            title: '证件类型',
					            align:'center',
					            width: fixWidth(0.1),
					            formatter : function(value,rec) {
					            	var prName123='';
				          		  	for(var i=0;i<idtypeList.length;i++){
					            		if(idtypeList[i].prValue == value) {
					            			prName123 = idtypeList[i].prName;
					            		}
				            		}
					            	return prName123;
					            },
					            sortable: true
					        },{
						            field: 'crIdnum',
						            title: '证件号码',
						            align:'center',
						            width: fixWidth(0.1),
						            sortable: true,
						            formatter : function(value,rec) {
										if(rec.crIdnum!=null)
											var val = '****'+rec.crIdnum.substring(rec.crIdnum.length-4);
										return val;
					            }
						        },{
						            field: 'crCityId',
						            title: '地区',
						            align:'center',
						            width: fixWidth(0.1),
						            hidden: system,
						            formatter :function(value, rec){return formatVal(getArea,value)}
						        },/*{
						            field: 'tel',
						            title: '手机',
						            align:'center',
						            width: fixWidth(0.1),
						            formatter : function(value,rec) {
						            	var prName123='';
						         		$.ajax({
						          		  	type: 'POST',
						          		  	async:false,
						          		  	url: '${namespacePath}/findMoblie?customerid='+rec.id,
						          		  	success: function(row){
						          		  		if(row.length!=0){					          		  			
							          		  			prName123 = row[0].tlTelNum;
							          		  		}
						              		},
						          			dataType: 'json'
						          		}); 
						            	return prName123;
						            }
						        },*/{
						            field: 'crCustomerType',
						            title: '客户类型',
						            align:'center',
						            width: fixWidth(0.1),
						            formatter : function(value,rec) {
						            	var prName123='';
					          		  	for(var i=0;i<customerTypeList.length;i++){
						            		if(customerTypeList[i].prValue == value) {
						            			prName123 = customerTypeList[i].prName;
						            		}
					            		}
						            	return prName123;
						            },
						            sortable: true
						        },{
						            field: 'crGatherDate',
						            title: '采集时间',
						            align:'center',
						            width: fixWidth(0.1),
						            sortable: true
						        },{
						            field: 'crGather',
						            title: '客户经理',
						            align:'center',
						            width: fixWidth(0.1),
						            sortable: true,
						            formatter : function(value,rec) {
						            	if(value!=null){
						            		var prName123='';
							            	$.ajax({
							          		  	type: 'POST',
							          		  	async:false,
							          		  	url: '${ctx}/crm/param/getStaffName?staffId='+value,
							          		  	success: function(row){
							          		  		if(row.length!=0){
							          		  			prName123 = row[0];
							          		  		}								            	
							              		},
							          			dataType: 'json'
							          		}); 
							            	return prName123;
						            	}else{
						            		return "";
						            	}
						            	
						            }
						        },{
								field : 'opt',
								title : '操作',
								width :300,
								align : 'center',
								formatter : function(value, rec) {
									var foId = rec.id;
									var links = '<a href="javascript:void(0)"  onclick="show('+ foId+')">查看</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
									if(rec.crCustomerType!=<%= ParamConstant.CRCUSTOMERTYPECUSTOMER%>&&rec.crCustomerType!=<%= ParamConstant.OLDCRCUSTOMERTYPECUSTOMER%>){
										links += '<a href="javascript:void(0)"  onclick="edit('+ foId+')">修改</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
									}else{
										links += '<a href="javascript:void(0)"  onclick="showfushuxinxi('+ foId+')">附属信息</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
									}
									 	links +='<a href="javascript:void(0)"  onclick="jumpBusinessPage('+ foId+')">业务</a>'
									 	links += '&nbsp;&nbsp;|&nbsp;&nbsp;'
										links +='<a href="javascript:void(0)"  onclick="jumpAccessoryPage('+ foId+')">附件</a>'
									return links;
								}
							} ] ],

					crud_query : {
						title : '高级查询',
						windowId:'${entityName}_query',
						form : '${namespacePath}/query',
						formId:'${entityName}_queryForm',
						width : 650,
						height : 300,
						callback : function() {
							$('#${entityName}_normalQuery').searchbox('setValue', '');
						}
					}
				});
		

		// 关闭附属信息，<!-- 民信 -->附属信息 刷新页面
	/* 	$('.easyui-dialog.none').dialog({
		    onClose:function(){
		    	this.close();
		    	//redirect('${namespacePath}');
		    }
		}); */
		
	}); 
	
function showfushuxinxi(foId){
	$("#fsxx").val(foId);
	fushuxinxi();
	return false;
	
}
	
function fushuxinxi(){
	$('#${entityName}_fushuxinxi').dialog('close');
	$('#fushuxinxi').dialog('open').dialog('refresh','${namespacePath}/fushuxinxi?customerId='+$("#fsxx").val()); 
}
	

	var jumpId;
	var jumpWord;
	function edit(foId){
		jumpId = foId;
		 jumpEditPage('edit_lc');
	     return false;	
	}
	function show(foId){
		jumpId = foId;
		jumpShowPage('show_lc');            
	}
	function jump(word){
		$('#${entityName}_showdlg').dialog('close');
		$('#${entityName}_dlg').dialog('close');
		var url = '${namespacePath}/'+word+'?id='+jumpId;
		redirect(url)
	}
	function jumpShowPage(word){
		$('#${entityName}_showdlg').dialog('close');
		$('#${entityName}_dlg').dialog('close');
		var url = '${namespacePath}/'+word+'?id='+jumpId;
		createTabsInframePage('客户信息查看',url);
	}
	function jumpEditPage(word){
		$('#${entityName}_showdlg').dialog('close');
		$('#${entityName}_dlg').dialog('close');
		var url = '${namespacePath}/'+word+'?id='+jumpId;
		createTabsInframePage('客户信息修改',url);
	}
	function jumpBusinessPage(jumpId){
		var url = '${namespacePath}/investInfoList?customerid='+jumpId;
		createTabsInframePage('业务',url);
	}
	function jumpAccessoryPage(jumpId){
		var url = '${ctx}/attachment/show/customer/attachment?customerid='+jumpId;
		createTabsInframePage('附件',url);
	}
	function createjump(word){
		var idType = '';
		var idNum = '';
		jumpWord = word;
		var url = '${namespacePath}/'+jumpWord;
		createTabsInframePage('客户信息新增',url)
		//redirect(url);
/* 		$('#${entityName}_yanzheng').dialog('open').dialog('setTitle','客户查询');
		$('#${entityName}_yanzhengForm').form('clear'); */
	}
	
	function cleanAndReload(){
		$('#${entityName}_normalQuery').searchbox('setValue','');
		$('#${entityName}_list').datagrid('load',{});
	}
</script>
<div class="easyui-layout" fit="true" id="main">
	<!-- 以下表头工具栏-->
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<table cellpadding="0" cellspacing="0" style="width: 100%">
				<tr>
					<td>
						<DIV class=datagrid-btn-separator></DIV>
						<a href="#" class="easyui-menubutton"
								data-options="menu:'#mm1',iconCls:'icon-add'">新增</a>
					</td>
					<td style="text-align: right"><input
						id="${entityName}_normalQuery" class="easyui-searchbox"
						data-options="prompt:'请输入查询内容', menu:'#mm', searcher:function(value,name){
						if(name=='crName'){
							$('#${entityName}_list').buildGridByJs('load',{'search_LIKE_crName': value});
						}
						if(name=='crCustomerNumber'){
							$('#${entityName}_list').buildGridByJs('load',{'search_EQ_crCustomerNumber': value});
						}
													$('#${entityName}_normalQuery').searchbox('setValue','');
						
										$('#${entityName}_queryForm').form('clear');
										}"
						style="width: 220px"></input> <a href="#"
						class="easyui-linkbutton" iconCls="icon-adsearch" plain="true"
						onclick="$('#${entityName}_list').buildGridByJs('query')">高级查询</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-cleanreload"
						plain="true" onclick="cleanAndReload()">重新载入</a></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 高级查询 -->
	<div id="mm" style="width: 120px">
		<div data-options="name:'crName',iconCls:'icon-crName'">客户姓名</div>
		<div data-options="name:'crCustomerNumber',iconCls:'icon-crName'">客户编号</div>
	</div>
	<!-- 以下列表 -->
	<div region="center" border="false" style="width: 100%;">
		<table id="${entityName}_list">
		</table>
	</div>
	
	<div id="mm1" style="width: 100px;">
			<div>
				<a href="javascript:void(0)" onclick="createjump('create_lc');">理财模板</a>
			</div>
	</div>
	<!-- 以下修改页面跳转 -->
	<div id="${entityName}_dlg" class="easyui-dialog"
		style="width: 550px; height: 90px; padding: 10px 20px; text-align: center;"
		closed="true" buttons="#${entityName}_dlg-buttons">
			<a href="javascript:void(0)" onclick="jumpEditPage('edit_lc');"
				class="easyui-linkbutton" data-options="iconCls:'icon-edit'">理财模板</a>		
	</div>
	<!-- 以下查看页面跳转 -->
	<div id="${entityName}_showdlg" class="easyui-dialog"
		style="width: 550px; height: 90px; padding: 10px 20px; text-align: center;"
		closed="true" buttons="#${entityName}_dlg-buttons">
			<a href="javascript:void(0)" onclick="jumpShowPage('show_lc');"
				class="easyui-linkbutton" data-options="iconCls:'icon-edit'">理财模板</a>
	</div>
	
		<input id="fsxx" type="hidden"/>
		<!-- 以下查看附属信息页面跳转 -->
	<div id="${entityName}_fushuxinxi" class="easyui-dialog"
		style="width: 350px; height: 90px; padding: 10px 20px; text-align: center;"
		closed="true" buttons="#${entityName}_dlg-buttons">
			<a href="javascript:void(0)" onclick="fushuxinxi();"
				class="easyui-linkbutton" data-options="iconCls:'icon-edit'">理财模板</a>
	</div>
		<div id="fushuxinxi" class="easyui-dialog none" closed="true" data-options="  
			    title: '客户附属信息', 
			    width: 800,   
			    height: 400,    
			    cache: false,   
			    modal: true"></div> 
 
</div>
