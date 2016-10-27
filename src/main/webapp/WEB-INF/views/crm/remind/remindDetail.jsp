<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>

<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>

<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuivalidate.jsp"%>
<%@ include file="/static/common/doing.jsp"%>

<c:set var="entityName" value="customer"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer"  /><!-- 命名空间 -->
<script type="text/javascript">
var getProvinces,getArea;
$(function() {
	postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
	//postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
		$('#${entityName}_list').buildGridByJs(
				{
					crud_listUrl : '${ctx}/crm/remind/remindDetailList1?remindType=${remindType}',
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
					crud_sortName : 'birthRemainDays',
					crud_sortOrder : 'asc',
					crud_pageSize :20,
					
					crud_frozenColumns: [[
					  					/* {
			            field: 'ck',
			            checkbox: true
			        }, */
			        	{
			            field: 'crCustomerNumber',
			            title: '客户编号',
			            align:'center',
			            width: fixWidth(0.13),
			            sortable: true,
				   		},
			        	{
			            field: 'crName',
			            title: '客户姓名',
			            align:'center',
			            width: fixWidth(0.13),
			            sortable: true,
			            formatter : function(value, rec) {
							var links = '<a href="javascript:void(0)"  onclick="show('+ rec.id+')">'+value+'</span>';
							return links;
			            	}
				   		}]],

				   		crud_columns: [[ {
				            field: 'crBirthday',
				            title: '出生日期',
				            align:'center',
				            width: fixWidth(0.13),
				            sortable: true
				        },/*{
				            field: 'crCityId',
				            title: '分公司',
				            align:'center',
				            width: fixWidth(0.14),
				            formatter : function(value,rec) {
				            	var prName123='';
				          		  	if(rec.crCityId!=null){
				          		  		prName123+=filterAreaById(getArea,rec.crCityId)==null?filterAreaById(getArea,rec.crCityId).name:'';
			          		  		}
				            	return prName123;
				            }
				        },{
				            field: 'tel',
				            title: '手机',
				            align:'center',
				            width: fixWidth(0.14),
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
				        },{
				            field: 'crCustomerType',
				            title: '客户类型',
				            align:'center',
				            width: fixWidth(0.1),
				            formatter : function(value,rec) {
				            	var prName123='';
				         		$.ajax({
				          		  	type: 'POST',
				          		  	async:false,
				          		  	url: '${ctx}/crm/param/findAllByPrType?prType=customerType',
				          		  	success: function(row){
				          		  	for(var i=0;i<row.length;i++){
					            		if(row[i].prValue == value) {
					            			prName123 = row[i].prName;
					            			}
				            			}
				              		},
				          			dataType: 'json'
				          		}); 
				            	return prName123;
				            },
				            sortable: true
				        },*/{
				            field: 'crGatherDate',
				            title: '采集时间',
				            align:'center',
				            width: fixWidth(0.13),
				            sortable: true
				        },{
				            field: 'crGather',
				            title: '客户经理',
				            align:'center',
				            width: fixWidth(0.13),
				            sortable: true,
				            formatter : function(value,rec) {
				            	var prName123='';
				            	$.ajax({
				          		  	type: 'POST',
				          		  	async:false,
				          		  	url: '${ctx}/crm/param/getStaffName?staffId='+value,
				          		  	success: function(row){
						            	prName123 = row[0];
				              		},
				          			dataType: 'json'
				          		}); 
				            	return prName123;
				            }
				        },
				        {
				            field: 'birthRemainDays',
				            title: '剩余天数',
				            align:'center',
				            width: fixWidth(0.13),
				            sortable: false
// 				            formatter : function(rec) {
// 								var foId = rec.id;
// 								var birth = rec.crBirthday;
								
// 								return links;
// 							}
				        },
				        {
								field : 'opt',
								title : '操作',
								width :fixWidth(0.16),
								align : 'center',
								formatter : function(value, rec) {
									var foId = rec.id;
									var links = '<security:authorize  ifAnyGranted="010101,010102,010103"><a href="javascript:void(0)"  onclick="show('+ foId+')">查看</span>&nbsp;&nbsp;</security:authorize>';
									links += '<security:authorize  ifAnyGranted="010108,010109,010110"><a href="${namespacePath}/investInfoList?customerid=' + foId+'">业务</a></security:authorize>';
									return links;
								}
							} ] ],

					crud_query : {
						title : '高级查询',
						windowId:'${entityName}_query',
						form : '${ctx}/crm/remind/query',
						formId:'${entityName}_queryForm',
						width : 550,
						height : 250,
						callback : function() {
							$('#${entityName}_normalQuery').searchbox('setValue', '');
						}
					}
				});
		$('#idType').combobox({
			url:'${ctx}/crm/param/findAllByPrType?prType=idtype',
			valueField : 'prValue',
			textField : 'prName',
			validType : 'selectValidator["idType"]',
			filter : function(q, row) {
				if (row.prName.indexOf(q) != -1) {
					return true;
				}},
				onSelect:function(row){
					if(row.prName=="身份证"){
				    	$('#idNum').validatebox({validType:"idcard"});
					    }else{
					    	$('#idNum').validatebox({validType:""});
					 }
				}
		});
		if(${msg=='success'}){
			$.messager.show({
				title : '提示',
				msg : '保存成功'
			});
		}
	}); 
	var jumpId;
	var jumpWord;
	function edit(foId){
		jumpId = foId;
		<security:authorize ifAnyGranted="010104,010105,010106,010118,010119">
        
        <security:authorize ifNotGranted="010104,010106,010118,010119">
          jump('edit_lc');
          return false;
  		</security:authorize>
  		
  		<security:authorize ifNotGranted="010105,010106,010118,010119">	
  		jump('edit_xd');
  		   return false;
  		</security:authorize>
  		
    	<security:authorize ifNotGranted="010104,010105,010106,010119">
  	   jump('edit_mx');
  	   return false;
        </security:authorize>
    	
        <security:authorize ifNotGranted="010104,010105,010106,010118">
  		jump('edit_mxlc');
  		return false;
  	  </security:authorize>
    	
  	$('#${entityName}_dlg').dialog('open').dialog('setTitle','模板选择');
  	</security:authorize>
	}
	function show(foId){
		jumpId = foId;
	 <security:authorize ifAnyGranted="010101,010102,010103,010116,010117">	
    
	<security:authorize ifNotGranted="010101,010102,010103,010117">
	jump('show_mx');
	return false;
	</security:authorize>

	<security:authorize ifNotGranted="010101,010102,010103,010116">
	jump('show_mxlc');
	return false;
	</security:authorize>
	
	<security:authorize ifNotGranted="010102,010103,010116,010117">
	jump('show_xd');
	return false;
	</security:authorize>
	
	<security:authorize ifNotGranted="010101,010103,010116,010117">
	jump('show_lc');
	return false;
	</security:authorize>
	
	$('#${entityName}_showdlg').dialog('open').dialog('setTitle','模板选择');	
</security:authorize>
	}
	function jump(word){
		$('#${entityName}_showdlg').dialog('close');
		$('#${entityName}_dlg').dialog('close');
		var url = '${namespacePath}/'+word+'?id='+jumpId;
		redirect(url)
	}
	function createjump(word){
		var idType = '';
		var idNum = '';
		jumpWord = word;
		var url = '${namespacePath}/'+jumpWord+'?idType='+idType+'&idNum='+encodeURI(idNum);
		redirect(url);
/* 		$('#${entityName}_yanzheng').dialog('open').dialog('setTitle','客户查询');
		$('#${entityName}_yanzhengForm').form('clear'); */
	}
	function search(){
		var idType = $('#idType').combobox('getValue');
		var idNum = $('#idNum').val();
		if($('#${entityName}_yanzhengForm').form('validate')){
			$.ajax({
	  		  	type: 'POST',
	  		  	async:false,
	  		  	url: '${namespacePath}/customerByidTypeAndidNum?idType='+idType+'&idNum='+encodeURI(idNum),
				onSubmit : function() {
					return $(this).form('validate');
				},
	  		  	success: function(value){
	  		  		if(value==null){
		  		  		var url = '${namespacePath}/'+jumpWord+'?idType='+idType+'&idNum='+encodeURI(idNum);
		  				redirect(url);
	  		  		}else{
	  		  		$('#${entityName}_dg').dialog('open').dialog('setTitle','客户查询');
	  		  		$('#${entityName}_dgForm').form('clear');
		  		  	 $.ajax({
		  		  		type: 'POST',
		  		  		async:false,
		  		  		url:'${ctx}/crm/param/findAllByPrType?prType=idtype',
		  		  		success: function(row){
			  		  	  	for(var i=0;i<row.length;i++){
			            		if(row[i].prValue == value.crIdtype) {
			            			value.crIdtype = row[i].prName;
			            			}
		            			}
		  			  		},
		  	  		  	dataType: 'json'
		  	  		});  
	  		  		$('#${entityName}_dgForm').form('load',value);
	  		  		}
	      		},
	  			dataType: 'json'
	  		}); 
		}
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
						<%-- <security:authorize  ifAnyGranted="010113,010114,010115">
        					<a href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-add'">新增</a>  
        				</security:authorize> --%>
						<!-- $('#${entityName}_list').buildGridByJs('input'); -->
					</td>
					<td style="text-align: right">
						<input id="${entityName}_normalQuery" class="easyui-searchbox" 
						data-options="prompt:'请输入查询内容', menu:'#mm', searcher:function(value,name){
						if(name=='crName'){
							$('#${entityName}_list').buildGridByJs('load',{'search_EQ_crName': value});
						}
										$('#${entityName}_queryForm').form('clear');
										}"  style="width: 200px"></input> 
						<a href="#" class="easyui-linkbutton" iconCls="icon-adsearch" plain="true" onclick="$('#${entityName}_list').buildGridByJs('query')">高级查询</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-cleanreload" plain="true" onclick="cleanAndReload()">重新载入</a></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 高级查询 -->
	<div id="mm" style="width:120px">
		<div data-options="name:'crName',iconCls:'icon-crName'">客户姓名</div>
	</div>
	<!-- 以下列表 -->
	<div region="center" border="false" style="width:100%;">
		<table id="${entityName}_list">
	</table>
	</div>	
	<div id="mm1" style="width:340px;">  
       	<security:authorize  ifAllGranted="010114"><div><a href="javascript:void(0)"  onclick="createjump('create_lc');">理财模板</a></div>  </security:authorize>
        <security:authorize  ifAllGranted="010113"><div><a href="javascript:void(0)"  onclick="createjump('create_xd');">信贷模板</a></div>  </security:authorize>
        <security:authorize  ifAllGranted="010115"><div><a href="${ctx}/createcusbz_flow" >标准模板</a></div>  </security:authorize>
        <security:authorize  ifAllGranted="010120"><div><a href="javascript:void(0)"  onclick="createjump('create_mx');"><!-- 民信 -->信贷模板</a></div>  </security:authorize>
        <security:authorize  ifAllGranted="010121"><div><a href="javascript:void(0)"  onclick="createjump('create_mxlc');"><!-- 民信 -->理财模板</a></div>  </security:authorize> 
 
    </div>  
    <!-- 以下修改页面跳转 -->
<div id="${entityName}_dlg" class="easyui-dialog" style="width:340px;height:90px;padding:10px 20px;text-align: center;" closed="true" buttons="#${entityName}_dlg-buttons">
		<security:authorize  ifAllGranted="010104"><a href="javascript:void(0)" onclick="jump('edit_lc');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">理财模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010105"><a href="javascript:void(0)" onclick="jump('edit_xd');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">信贷模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010106"><a href="javascript:void(0)" onclick="jump('edit_bz');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">标准模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010118"><a href="javascript:void(0)" onclick="jump('edit_mx');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"><!-- 民信 -->信贷模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010119"><a href="javascript:void(0)" onclick="jump('edit_mxlc');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"><!-- 民信 -->理财模板</a>  </security:authorize>
</div>
    <!-- 以下查看页面跳转 -->
<div id="${entityName}_showdlg" class="easyui-dialog" style="width:340px;height:90px;padding:10px 20px;text-align: center;" closed="true" buttons="#${entityName}_dlg-buttons">
		<security:authorize  ifAllGranted="010101"><a href="javascript:void(0)" onclick="jump('show_lc');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">理财模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010102"><a href="javascript:void(0)" onclick="jump('show_xd');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">信贷模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010103"><a href="javascript:void(0)" onclick="jump('show_bz');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">标准模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010116"><a href="javascript:void(0)" onclick="jump('show_mx');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"><!-- 民信 -->信贷模板</a>  </security:authorize>
		<security:authorize  ifAllGranted="010117"><a href="javascript:void(0)" onclick="jump('show_mxlc');" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"><!-- 民信 -->理财模板</a>  </security:authorize>
</div>
<!--  -->
<div id="${entityName}_yanzheng" class="easyui-dialog" style="width:600px;height:180px;" closed="true" buttons="#${entityName}_dlg-buttons">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">客户查询添加</div>
		<form id="${entityName}_yanzhengForm" method="post" style="margin:0;padding:10px 20px;" novalidate>
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="idType">证件类型:</label></td>
				<td><input id="idType" class="easyui-combobox" name="idType" required="true"></td>
				<td><label for="idNum">证件号码:</label></td>
				<td><input id="idNum" class="easyui-validatebox" name="idNum" style="width: 200px" required="true"></td>
			</tr>
			</table>
		</form>
		</div>
		</div>
</div>
<div id="${entityName}_dlg-buttons">
	<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_yanzheng').dialog('close')">取消</a>
</div>
<!--  -->
<div id="${entityName}_dg" class="easyui-dialog" style="width:500px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsdg">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">已存在客户</div>
		<form id="${entityName}_dgForm" method="post" style="margin:0;padding:10px 20px;" novalidate>
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="crName">客户名称:</label></td>
				<td><input id="crName" type="text" name="crName"  readonly="readonly"></td>
			</tr>
			<tr>
				<td><label for="crIdtype">证件类型:</label></td>
				<td><input id="crIdtype" type="text" name="crIdtype" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label for="crIdnum">证件号码:</label></td>
				<td><input id="crIdnum" class="easyui-validatebox" name="crIdnum" style="width: 200px" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label for="crGather">营业部（采集人）:</label></td>
				<td><input id="crGather" class="easyui-validatebox" name="crGather" readonly="readonly"></td>
			</tr>
			
			</table>
		</form>
		</div>
		</div>
</div>
<div id="${entityName}_dlg-buttonsdg">
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_dg').dialog('close')">取消</a>
</div>
</div>
