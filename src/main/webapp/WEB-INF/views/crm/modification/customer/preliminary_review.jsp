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

	<c:set var="entityName" value="modification"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/modification"  /><!-- 命名空间 -->
<script type="text/javascript">	
var getProvinces,getArea;
$(function() {
	postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
	postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
        loadAuditState('auditResult');
        initValidationInfo(); 
        loadAuditLog(${id},'<%=ParamConstant.AUDIT_TYPE_MODIFICATION_CUSTOMER%>','shenherizi');
        $('#biangeng').datagrid({  
			url: '${namespacePath}/ModificationDetail/find/all?id=${id}',
		    columns:[[   
		        {field:'field',title:'信息项名', align:'center',width:200,formatter : function(value,rec){return value?value.fdFieldCn:'';}},   
		        {field:'mlFieldOldValue',title:'原值', align:'center',width:150,hidden:true},   
		        {field:'mlFieldOldText',title:'原值', align:'center',width:250,formatter :  function(value,rec){
		        	return formatValue(value,rec,'old');
			        }}, 
		        {field:'mlFieldNewValue',title:'新值', align:'center',width:150,hidden:true}, 
		        {field:'mlFieldNewText',title:'新值', align:'center',width:250,formatter :  function(value,rec){
		        	return formatValue(value,rec,'new');
				       }}
		    ]]   
		}); 				
		$.post('${namespacePath}/modification/find/one', {id :${id}}, function(data) {
			$.post('${ctx}/crm/customer/customerById', {id :data.mnSourceId}, function(rowData) {
		    	var prName123='';
            	$.ajax({
          		  	type: 'POST',
          		  	async:false,
          		  	url: '${ctx}/crm/param/getStaffName?staffId='+rowData.crGather,
          		  	success: function(row){
		            	prName123 = row[0];
              		},
          			dataType: 'json'
          		}); 
            	objectId = rowData.id;
	    		var labels=['name', 'idNum','crNum','staff'];
	    		var vals =[rowData.crName,rowData.crIdnum,rowData.crCustomerNumber,prName123];
	    		var len = labels.length;
	    		for(var i = 0; i < len; i++) {
	    			setVal(labels[i],vals[i]);
	    		}
			}, 'json');
			$('#ff').form('load',data);
		}, 'json');

		showPreliminaryReviewAttachment(${id},'<%=ParamConstant.ATTACHMENT_TYPE_MODIFICATION_CUSTOMER%>','客户变更单附件');
		//点击查看附件加载附件显示信息
		$("#mAttachment").click(function (){
			showPreliminaryReviewAttachment(${id},'<%=ParamConstant.ATTACHMENT_TYPE_MODIFICATION_CUSTOMER%>','客户变更单附件');
		});
		$(".m_table td").has("label").css("text-align","right");	
	});
//添加验证条件
function initValidationInfo() {   
	//添加备注验证
	$('#content').validatebox({required: true, validType: 'maxlength[200]'});  
}
<%--//加载审核状态
function loadAuditState(id) {
	var auditStateList,size = 0;
	postAjax(false, getContextPath() + "/crm/param/findAllByPrType?prType=requestState", function(data) { auditStateList = data;}, 'json');
	comboboxUtil.setComboboxByData(id, filterStateList(auditStateList), 'prValue', 'prName', '150', true);
	
}

//过滤掉不需要的状态
function filterStateList(stateList) {
	var newStateList = new Array(),size = 0,len = stateList.length;
	for(var i = 0; i < len; i++) {
		if(stateList[i].prName != '新建' && stateList[i].prName != '待质检'/*&& stateList[i].prName != '拒绝'*/&& stateList[i].prName != '撤销') {
			newStateList[size] = stateList[i];
			size++;
		}
	}
	return newStateList;
}--%>
	//格式化
	function formatValue(value,rec,fg){
	if (rec.field.fdFieldOption == 'text'||rec.field.fdFieldOption == 'date') {
		if(fg == 'old'){
			return rec.mlFieldOldValue == '2099-12-31'?'长期':rec.mlFieldOldValue;
		}else{
			return rec.mlFieldNewValue == '2099-12-31'?'长期':rec.mlFieldNewValue;
		}
	}
	if (rec.field.fdFieldOption == 'select'||rec.field.fdFieldOption == 'checkbox') {
		var prName123='';
		if(rec.field.fdReserve != 'nations'&&rec.field.fdReserve != 'sex'){
			postAjax(false, "${ctx}/"+rec.field.fdUrl, function(data) {
    			for(var i=0; i<data.length;i++){
    				if(fg == 'old'){
    					if(data[i].prValue==rec.mlFieldOldValue){
							prName123 = data[i].prName;
							}
    				}else{
    					if(data[i].prValue==rec.mlFieldNewValue){
							prName123 = data[i].prName;
							}
    				}
  		  	}},'json')
		}
		if(rec.field.fdReserve == 'nations'){
			postAjax(false, "${ctx}/"+rec.field.fdUrl, function(data) {
    			for(var i=0; i<data.length;i++){
    				if(fg == 'old'){
    					if(data[i].id==rec.mlFieldOldValue){
							prName123 = data[i].name;
							}
    				}else{
    					if(data[i].id==rec.mlFieldNewValue){
							prName123 = data[i].name;
							}
    				}
  		  	}},'json')
		}
		if(rec.field.fdReserve == 'sex') {
			if(fg == 'old'){
				if(rec.mlFieldOldValue=='1'){
					prName123 = '男';
				}else if(rec.mlFieldOldValue=='0'){
					prName123 = '女';
				}
			}else{
				if(rec.mlFieldNewValue=='1'){
					prName123 = '男';
				}else if(rec.mlFieldNewValue=='0'){
					prName123 = '女';
				}
			}
		}
		return prName123;
	}
	if(rec.field.fdFieldOption == 'tel'){
		if(rec.field.fdReserve == '1'||rec.field.fdReserve == '2'){
			if(fg == 'old'){
				return telsplit(rec.mlFieldOldValue);
			}else{
				return telsplit(rec.mlFieldNewValue);
			}
		}
		if(rec.field.fdReserve == '3'){
			if(fg == 'old'){
				return rec.mlFieldOldValue;
			}else{
				return rec.mlFieldNewValue;
			}
		}
	}
	if(rec.field.fdFieldOption == 'addr'){
		if(rec.field.fdReserve=='house'){
			if(fg == 'old'){
				return houseAddrSplit(rec.mlFieldOldValue);
			}else{
				return houseAddrSplit(rec.mlFieldNewValue);
			}
		}else{
			if(fg == 'old'){
				return addrsplit(rec.mlFieldOldValue);
			}else{
				return addrsplit(rec.mlFieldNewValue);
			}
		}
	}
	if(rec.field.fdFieldOption == 'tlPriority'){
		if(fg == 'old'){
			var prName123='';
			postAjax(false, "${ctx}/crm/customer/getTelById?obId="+rec.mlFieldOldValue, function(data) {
    			if(data.length>0){prName123 = data[0];}
  		  	},'json')
			return prName123;
		}else{
			var prName123='';
			postAjax(false, "${ctx}/crm/customer/getTelById?obId="+rec.mlFieldNewValue, function(data) {
    			if(data.length>0){prName123 = data[0];}
  		  	},'json')
			return prName123;
		}
	}
	if(rec.field.fdFieldOption == 'arPriority'){
		if(fg == 'old'){
			var prName123='';
			postAjax(false, "${ctx}/crm/customer/getAddrById?obId="+rec.mlFieldOldValue, function(data) {
    			if(data.length>0){prName123 = data[0];}
  		  	},'json')
			return prName123;
		}else{
			var prName123='';
			postAjax(false, "${ctx}/crm/customer/getAddrById?obId="+rec.mlFieldNewValue, function(data) {
    			if(data.length>0){prName123 = data[0];}
  		  	},'json')
			return prName123;
		}
	}
}
	function save(obj){
		obj.linkbutton({disabled:true}); 
		$('#sh').form('submit',{
				url: '${namespacePath}/modification/audit?id='+${id},
				onSubmit: function(){
					if($(this).form('validate')){
						return true;
					}else{
						obj.linkbutton({disabled:false}); 
						return false;
					}
				},
				success: function(result){
					obj.linkbutton({disabled:false}); 
					var result = eval('('+result+')');
					if (result.result.success) {
						window.parent.show('提示','保存成功！');
						closeCurPageUpdateDatagrid('信息变更','信息变更质检','modification_customer_list');
						//redirect('${ctx}/modification/customer');	//跳转
					} else {
						$.messager.show({
							title: '提示',
							msg: '修改失败'+result.result.errMsg
						});
					}
				}
			})
	}
	function addrsplit(mlFieldOldValue){
		var addStringList = mlFieldOldValue.split("!@#");
		var addrString='';
		if(addStringList[0]!=null&&addStringList[0]!=''){
			addrString += (addStringList[0]?filterAreaById(getProvinces,addStringList[0])?filterAreaById(getProvinces,addStringList[0]).name:'':'') +' ';
		}
		if(addStringList[1]!=null&&addStringList[1]!=''){
			addrString += (addStringList[1]?filterAreaById(getArea,addStringList[1])?filterAreaById(getArea,addStringList[1]).name:'':'') +' ';
		}
		if(addStringList[2]!=null&&addStringList[2]!=''){
			addrString += (addStringList[2]?filterAreaById(getArea,addStringList[2])?filterAreaById(getArea,addStringList[2]).name:'':'') +' ';
		}
		if(addStringList[3]!=null&&addStringList[3]!=''){
			addrString += addStringList[3]+'  ';
		}
		if(addStringList[4]!=null&&addStringList[4]!=''){
			addrString += addStringList[4]+'    ';
		}
		if(addStringList[5]!=null&&addStringList[5]!=''){
			addrString += '邮编：'+addStringList[5];
		}
		return addrString;
	}
	
	//房产地址分割（房产地址少街道字段）
	function houseAddrSplit(mlFieldOldValue){
		if(mlFieldOldValue==null) return '';
		var addStringList = mlFieldOldValue.split("!@#");
		var addrString='';
		if(addStringList[0]!=null&&addStringList[0]!=''){
			addrString += (addStringList[0]?filterAreaById(getProvinces,addStringList[0])?filterAreaById(getProvinces,addStringList[0]).name:'':'') +' ';
		}
		if(addStringList[1]!=null&&addStringList[1]!=''){
			addrString += (addStringList[1]?filterAreaById(getArea,addStringList[1])?filterAreaById(getArea,addStringList[1]).name:'':'') +' ';
		}
		if(addStringList[2]!=null&&addStringList[2]!=''){
			addrString += (addStringList[2]?filterAreaById(getArea,addStringList[2])?filterAreaById(getArea,addStringList[2]).name:'':'') +' ';
		}
		/* if(addStringList[3]!=null&&addStringList[3]!=''){
			addrString += addStringList[3]+'  ';
		} */
		if(addStringList[3]!=null&&addStringList[3]!=''){
			addrString += addStringList[3]+'    ';
		}
		if(addStringList[4]!=null&&addStringList[4]!=''){
			addrString += '邮编：'+addStringList[4];
		}
		return addrString;
	}
	
	function telsplit(mlFieldOldValue){
		var telStringList = mlFieldOldValue.split("!@#");
		var telString='';
		if(telStringList[0]!=null&&telStringList[0]!=''){
			telString += telStringList[0]+'-';
			}
		telString += telStringList[1];
		if(telStringList[2]!=null&&telStringList[2]!=''){
			telString += '-'+telStringList[2];
			}
		return telString;
	}

</script>  
<body class="easyui-layout">
	<div data-options="region:'center'" id="main">
	    	<div class="m_title">新建变更单</div>
	    	<div class="m_content" style="width:820px;">

			    <table  class="m_table no_border" style="width:100%;">
				   <tr class="tr1">
			       		<td colspan="4">基本信息</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
			       <tr>
						<td nowrap="nowrap"><label for="name">客户姓名</label></td>
						<td><input id="name" class="easyui-validatebox" name="name" disabled="disabled"></td>
						<td nowrap="nowrap"><label for="idNum">证件号码</label></td>
						<td><input id="idNum" class="easyui-validatebox" name="idNum" disabled="disabled"></td>
				   </tr>
				   <tr>
						<td nowrap="nowrap"><label for="crNum">客户编号</label></td>
						<td><input id="crNum" class="easyui-validatebox" name="crNum" disabled="disabled"></td>
						<td nowrap="nowrap"><label for="staff">客户经理</label></td>
						<td><input id="staff" class="easyui-validatebox" name="staff" disabled="disabled"></td>
				   </tr>
				   <tr class="tr1">
			       		<td colspan="4">变更明细&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id ="mAttachment">附件信息</a></td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
				</table>
				<table id="biangeng" class="easyui-datagrid" width="100%"></table>
			<form id="ff" method="post">
	    		<input type="hidden" id="mnSourceId" name="mnSourceId">
	    		<input type="hidden" id="mnType" name="mnType" value="<%= ParamConstant.BIANGENGCUSTOMER%>">
				<table  class="m_table no_border" style="width:100%;">
				   <tr class="tr1">
			       		<td colspan="4">备注</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
			       <tr>
						<td colspan="4"><textarea name="mnMemo" rows="6" style="width: 100%" title="请输入文本！" disabled="disabled"></textarea></td>
				   </tr>
				   
				</table>
				
	    	</form>
	    	<table  class="m_table no_border" style="width:100%;">
				   <tr class="tr1">
			       		<td colspan="4">审核日志</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
				</table>
				<table id="shenherizi" class="easyui-datagrid" width="100%" nowrap="false"></table>
	    	<form id="sh" method="post">
				<table  class="m_table no_border" style="width:100%;">
				   <tr class="tr1">
			       		<td colspan="4">审核信息
</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
				   <tr>
						<td nowrap="nowrap" style="width: 100px"><label for="auditResult">审核结果</label></td>
						<td><input id="auditResult" class="easyui-combobox" name="auditResult"></td>
						<td></td>
						<td></td>
				   </tr>
				   <tr>
						<td nowrap="nowrap"><label for="content">审核备注</label></td>
						<td colspan="3"><textarea name="content" rows="6" style="width: 100%" title="请输入文本！"  maxlength='160'></textarea></td>
				   </tr>
				   
				</table>
	    	</form>
        </div>
   	    <div class="m_btn_box" style="width:820px;">  
   	    	 <a href="#" onclick="save($(this))" class="easyui-linkbutton" >确定</a>  
            <a href="#" onclick="closeCurPage('信息变更质检')" class="easyui-linkbutton" >返回</a>  
        </div> 
	</div>
		<div id="dd" class="easyui-dialog" closed="true" title="请选择客户" style="width:700px;height:400px;" data-options="iconCls:'icon-save',resizable:true,modal:true">  
    <table id="dg"></table>  
    <div id="mm" style="width:120px">  
	   <div name="crName" iconCls="icon-edit">客户姓名</div>  
	   <div name="crIdnum" iconCls="icon-edit">证件号码</div>  
	   <div name="crCustomerNumber" iconCls="icon-edit">客户编号</div>  
	</div>
</div> 
</body>