 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ include file="/static/common/taglibs.jsp"%>
	<%@ include file="/static/common/meta.jsp"%>

<c:set var="entityName" value="customer"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer"  /><!-- 命名空间 -->
<script type="text/javascript">
var lxrList,telList,addrList,bankList;
var tlTelTypeTypeList,arAddrTypeList,priorityList,bankList,accountTypeList,relationList;
var getProvinces,getArea;
$(function() {
	postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
	postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
	postAjax(false, "${namespacePath}/telAllBycustomerid?id=${customers.id}", function(data) {telList = data}, 'json');
	postAjax(false, "${namespacePath}/addrAllBycustomerid?id=${customers.id}", function(data) {addrList = data}, 'json');
	
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=telType", function(data) {tlTelTypeTypeList = data}, 'json');
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=addrType", function(data) {arAddrTypeList = data}, 'json');
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=priority", function(data) {priorityList = data}, 'json');
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=bank", function(data) {bankList = data}, 'json');
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=accountType", function(data) {accountTypeList = data}, 'json');
	postAjax(false,"${ctx}/crm/param/findAllByPrType?prType=relation", function(data) {relationList = data}, 'json');
 
	$('#lxr').datagrid({   
	    url:'${ctx}/crm/customer/lxr?id=${customers.id}',   
	    rownumbers:true,
	    fitColumns:true,
		sortName : 'id',
		sortOrder : 'asc',
	    columns:[[   
	        {field:'cpName',title:'联系人姓名',width:fixWidth(0.05)},   
	        {field:'cpRelation',title:'与客户关系',width:fixWidth(0.05),formatter : function(value,rec) {return formatVal(relationList,value);}},   
	        {field:'cpSex',title:'性别',width:fixWidth(0.05),formatter : function(value, rec) {if(value=="1"){return "男";}else if(value =="0"){return "女";}}}, 
	        {field:'name1',title:'电话号码',width:fixWidth(0.05),formatter : function(value,rec) {
		            	var prName123='';
		            	var row = filterTel(telList,"1",rec.id,true);
	          		  	if(row.length!=0){
	          		  		 if(row[0].tlAreaCode!=null){						          		  			
		          		  			prName123 = row[0].tlTelNum;
				            		if(row[0].tlAreaCode!=null){prName123 = row[0].tlAreaCode+'-'+prName123;}
				            		if(row[0].tlExtCode!=null){prName123=prName123+ '-'+row[0].tlExtCode;}
	          		  		}else{prName123 = row[0].tlTelNum;} 
	          		  		}
		            	return prName123;
		            }}, 
	        {field:'name2',title:'地址',width:fixWidth(0.05),
		            formatter : function(value,rec) {
		            	var prName123='';
		            	var row = filterAddr(addrList,"1",rec.id,true)
          		  		if(row.length!=0){
	          		  		prName123=row[0].arAddrDetail;
	          		  		}
		            	return prName123;
		            }}, 
	        {field:'cpInputTime',title:'创建时间',width:fixWidth(0.05),
			            formatter : function(value, rec) {
			            	var date = new Date(value);
			        		return value?date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds():'';
			            }} 
	    ]]   
	});  

	$('#tel').datagrid({   
	    url:'${ctx}/crm/customer/tel?id=${customers.id}',  
	    rownumbers:true,
	    fitColumns:true,
		sortName : 'id',
		sortOrder : 'asc',
	    columns:[[
	        {field:'tlAreaCode',title:'电话区号',width:fixWidth(0.05)},   
	        {field:'tlTelNum',title:'电话号码',width:fixWidth(0.05)},   
	        {field:'tlExtCode',title:'分机',width:fixWidth(0.05)}, 
	        {field:'tlTelType',title:'电话类型',width:fixWidth(0.05),
	            formatter : function(value,rec) {
	            	return formatVal(tlTelTypeTypeList,value);
            }}, 
	        {field:'tlPriority',title:'优先级',width:fixWidth(0.05),
	            formatter : function(value,rec) {
	            	return formatVal(priorityList,value);
            }}, 
	        {field:'tlValid',title:'是否有效',width:fixWidth(0.05),formatter : function(value, rec,foId) {
				if(value == "1"){return "有效";}else{return "无效"}
			}}
	    ]]   
	});  
	$('#bank').datagrid({   
	    url:'${ctx}/crm/customer/bankN?id=${customers.id}',   
	    rownumbers:true,
	    fitColumns:true,
		sortName : 'id',
		sortOrder : 'asc',
	    columns:[[   
	        {field:'baAccountName',title:'开户名',width:fixWidth(0.05)},   
	        {field:'baAccount',title:'银行账号',width:fixWidth(0.05)},   
	        {field:'baAccountType',title:'账号类型',width:fixWidth(0.05),
	            formatter : function(value,rec) {
	            	var prName123='';
	            	prName123 = formatVal(accountTypeList,value);
	            	return prName123;
	            }}, 
	        {field:'baBankName',title:'银行名称',width:fixWidth(0.05)}, 
	        {field:'baBranchName',title:'支行名称',width:fixWidth(0.05)}, 
	        {field:'baValid',title:'是否有效',width:fixWidth(0.05),formatter : function(value, rec,foId) {
				if(value == "1"){return "有效";}else{return "无效"}
			}},
			{field:'opt',title:'操作',width:fixWidth(0.05),formatter : function(value, rec) {
				var foId = rec.id;
				var links =  "<a href=''  onclick='return delBank("+foId+");'>删除</span>";
				return links;
			}}
	    ]]   
	});  
	$('#addr').datagrid({   
	    url:'${ctx}/crm/customer/addr?id=${customers.id}',  
	    fitColumns:true,
	    rownumbers:true,
		sortName : 'id',
		sortOrder : 'asc',
	    columns:[[   
	        {field:'arProvince',title:'省',width:fixWidth(0.05),formatter : function(value,rec) {
	            	var prName123=filterAreaById(getProvinces,value);//,getArea
	            	return prName123?prName123.name:'';
	        }
		        },   
	        {field:'arCity',title:'市',width:fixWidth(0.05),formatter : function(value,rec) {
	        	var prName123=filterAreaById(getArea,value);//,getArea
            	return prName123?prName123.name:'';
	        }
		        }, 
	        {field:'arCounty',title:' 县/区',width:fixWidth(0.05),formatter : function(value,rec) {
	        	var prName123=filterAreaById(getArea,value);//,getArea
            	return prName123?prName123.name:'';
	        }
		        }, 
			{field:'arStreet',title:'街/路',width:fixWidth(0.05)}, 
	        {field:'arAddrDetail',title:'地址',width:fixWidth(0.05)}, 
	        {field:'arZipCode',title:'邮编',width:fixWidth(0.05)}, 
	        {field:'arAddrType',title:'地址类型',width:fixWidth(0.05),formatter : function(value,rec){return formatVal(arAddrTypeList,value);}}, 
	        {field:'arPriority',title:'优先级',width:fixWidth(0.05),formatter : function(value,rec) {return formatVal(priorityList,value);}},	
	        {field:'arValid',title:'是否有效',width:fixWidth(0.05),formatter : function(value, rec,foId) {
				if(value == 1){return "有效";}else{return "无效"}
			}}
	    ]]   
	});  
	comboboxUtil.setComboboxByDataWithpanelHeight('baBankName',bankList, 'prValue', 'prName', '150', true);//银行名称
	comboboxUtil.setComboboxByData('baAccountType',accountTypeList, 'prValue', 'prName', '150', true,function(newValue,oldValue){
    	if(newValue.prValue== <%= ParamConstant.BANKECARD%>){
    		$('#baAccount').validatebox({validType:"bankAccount"});
	    	}else{
	    		$('#baAccount').validatebox({validType:""});
		    	};
    });//账户类型

	addrProvice('addrdata_arProvince','addrdata_arCity','addrdata_arCounty','addrdata_arStreet','addrdata_arAddrDetail');//客户地址编辑	
	comboboxUtil.setComboboxByData('addrdata_arAddrType',arAddrTypeList, 'prValue', 'prName', '150', true,function(record){
		if(record.prValue == <%= ParamConstant.ADDZJ%>){
			$("#addrdata_arAddrTypeId").hide();
		}else{
			$("#addrdata_arAddrTypeId").show();
		}
	});//地址类型

	comboboxUtil.setComboboxByData('teldata_tlTelType',tlTelTypeTypeList, 'prValue', 'prName', '150', true,function(record){
		var content;
		if(record.prValue == <%= ParamConstant.MOBILE%>){
			content = '<input id="teldata_tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 150px;"/>';
			$("#teldata_tlTelTypeId").html(content);
			$('#teldata_tlTelNum').validatebox({validType:"mobile"});
		}else{
			content = '<input id="teldata_tlAreaCode" class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"/>-';
			content +='<input id="teldata_tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;"/>-';
			content +='<input id="teldata_tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"/>';
			$("#teldata_tlTelTypeId").html(content);
			$('#teldata_tlAreaCode').validatebox({validType:"numberL[3,4]"});
			$('#teldata_tlTelNum').validatebox({validType:"numberL[6,8]"});
			$('#teldata_tlExtCode').validatebox({validType:"numberL[0,6]"});
		}
	});
});
function newLxr(){
	$('#xjLxr').dialog('open'); 
}
function newTel(){
	$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话添加');
	$('#${entityName}_TelForm').form('clear');
	$('#${entityName}_TelForm').form('load',{"tlValid":"1"});
};
function newAddr(){
	$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址添加');
	$('#${entityName}_AddrForm').form('clear');
	$('#${entityName}_AddrForm').form('load',{"arValid":"1"});
};
function newBank(){
	$('#${entityName}_Bank').dialog('open').dialog('setTitle','银行账号添加');
	$('#${entityName}_BankForm').form('clear');
	$('#${entityName}_BankForm').form('load',{"baAccountName":"${customers.crName}","baValid":"1"});
};
function saveTelF(){
	$('#${entityName}_TelForm').form('submit',{
		url: '${namespacePath}/saveTel?customerid=${customers.id}',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(rows){
			$('#${entityName}_Tel').dialog('close');
			$('#tel').datagrid('reload',rows);
		}
	})
}
function saveAddrF(){
	$('#${entityName}_AddrForm').form('submit',{
		url: '${namespacePath}/saveAddr?customerid=${customers.id}',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(rows){
			$('#${entityName}_Addr').dialog('close');
			$('#addr').datagrid('reload',rows);
		}
	})
}
function saveBank(){
	$('#${entityName}_BankForm').form('submit',{
		url: '${namespacePath}/saveBank?customerid=${customers.id}',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(rows){
			$('#${entityName}_Bank').dialog('close');
			$('#bank').datagrid('reload',rows);
		}
	})
}

function delBank(id){
	var i = confirm('确认删除？');
        if (i){  
        	$.post('${namespacePath}/delBank', {id : id}, function(rows) {
        		 if(rows == 0){
        			$.messager.show({
        				title: '无法删除银行信息',
        				msg: '<font color=red>该客户的银行信息已被录入业务中或变更中！</font>'
        			});
        		}
        		$('#bank').datagrid('reload',{});}, 'json');	
        }
        return false;
}
</script>
 
<form id="customer_queryForm" method="post" style="padding:10px 20px">
	<div class="subtitle">客户姓名：${customers.crName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证件号码：${customers.crIdnum}  </div>
	<table width="100%">                                              
	       		<tr><td align="left">联系人信息</td><td align="right">
	       		<security:authorize ifAllGranted="010114">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newLxr();">新增</a>
	       		</security:authorize>
	       		</td></tr>
	       		<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="lxr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">电话信息</td><td align="right">
			   <security:authorize ifAllGranted="010114">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newTel();">新增</a>
			     </security:authorize></td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="tel" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">地址信息</td><td align="right">
			    <security:authorize ifAllGranted="010114">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newAddr();">新增</a>
			    </security:authorize>
			    </td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="addr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">银行信息</td><td align="right">
			     <security:authorize ifAllGranted="010114">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBank();">新增</a>
			     </security:authorize>
			    </td></tr>
	        	<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="bank" width="100%"></table></td></tr>
			    <tr><td colspan="4"><br></td></tr>
	        </table>
</form>

<div id="${entityName}_Tel" class="easyui-dialog" style="width:500px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsTel" data-options="onMove: easyuiPanelOnMove"> 
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">电话添加</div>
		<form id="${entityName}_TelForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="teldata_telid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label for="tlTelType">电话类型:</label></td>
				<td nowrap="nowrap"><input id="teldata_tlTelType" class="easyui-combobox" name="tlTelType"></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label>电话:</label></td>
				<td nowrap="nowrap" id="teldata_tlTelTypeId">
				<input id="teldata_tlAreaCode"  class="easyui-validatebox" validType="numberL[3,4]" type="text" name="tlAreaCode" style="width: 40px;"/>-
				<input id="teldata_tlTelNum" class="easyui-validatebox" validType="numberL[6,8]" type="text" name="tlTelNum" style="width: 100px;"/>-
				<input id="teldata_tlExtCode" class="easyui-validatebox" validType="numberL[0,6]" type="text" name="tlExtCode" style="width: 40px;"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap"></td>
				<td nowrap="nowrap"><input id="teldata_tlValid" name="tlValid"  type="hidden" value="1"></td>
			</tr>
			</table>
		</form>
		</div>
		</div>
</div>

<div id="${entityName}_Addr" class="easyui-dialog" style="width:620px;height:320px;" closed="true" buttons="#${entityName}_dlg-buttonsAddr" data-options="onMove: easyuiPanelOnMove">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">地址添加</div>
		<form id="${entityName}_AddrForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="addrdata_addrid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label for="arAddrType">地址类型:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arAddrType" class="easyui-combobox" name="arAddrType"></td>
			</tr>
			<tr  id="addrdata_arAddrTypeId">
				<td nowrap="nowrap"><label>地址:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arProvince" class="easyui-combobox" name="arProvince" style="width: 60px;">省-
				<input id="addrdata_arCity" class="easyui-combobox" name="arCity" style="width: 100px;" validType="selectValidator['addrdata_arCity']">市-
				<input id="addrdata_arCounty" class="easyui-combobox" name="arCounty" style="width: 60px;" validType="selectValidator['addrdata_arCounty']">县-
				<input id="addrdata_arStreet" class="easyui-validatebox" name="arStreet"> 街/路</td>
				<!-- <input id="addrdata_arStreet" class="easyui-validatebox" name="arStreet" onchange="addrdata_addrbuquan();" > 街/路</td> -->
			</tr>
			<tr>
				<td nowrap="nowrap"><label>详细地址</label></td>
				<td nowrap="nowrap"><input id="addrdata_arAddrDetail" class="easyui-validatebox" type="text" name="arAddrDetail" style="width: 350px;"></input></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label for="arZipCode">邮编:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arZipCode" class="easyui-validatebox" name="arZipCode"  validType="ZIP"></td>
			</tr>
			<tr>
			<tr>
				<td nowrap="nowrap"></td>
				<td nowrap="nowrap"><input id="addrdata_arValid" name="arValid"  type="hidden" value="1"></td>
			</tr>
				</table>
		</form>
		</div>
		</div>
		
</div>

<div id="${entityName}_Bank" class="easyui-dialog" style="width:550px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsBank" data-options="onMove: easyuiPanelOnMove">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">银行账号添加</div>
		<form id="${entityName}_BankForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="bankdata_bankid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label>开户名:</label></td>
				<td nowrap="nowrap"><input id="baAccountName" name="baAccountName" class="easyui-validatebox" required="true" readonly="readonly" style="color: gray;"></td>
				<td nowrap="nowrap"><label>账户类型:</label></td>
				<td nowrap="nowrap"><input id="baAccountType" class="easyui-combobox" name="baAccountType" onchange="vBaAccount();"></td>
				
				</tr>
			<tr>
				<td nowrap="nowrap"><label>银行名称:</label></td>
				<td nowrap="nowrap"><input id="baBankName" class="easyui-combobox" name="baBankCode"  ></td>
				<td nowrap="nowrap"><label>支行名称:</label></td>
				<td nowrap="nowrap"><input name="baBranchName" class="easyui-validatebox" required="true">
			</td>
			</tr>
			<tr>
			    <td nowrap="nowrap"><label>银行账号:</label></td>
				<td nowrap="nowrap"><input id="baAccount" name="baAccount" class="easyui-validatebox" required="true"></td>
			
				<td nowrap="nowrap"></td>
				<td nowrap="nowrap"><input name="baValid"  type="hidden" value="1">
				</td>
			</tr>
			</table>
		</form>
	</div>
</div>
</div>

<div id="${entityName}_dlg-buttonsTel">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveTelF()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Tel').dialog('close')">取消</a>
</div>
<div id="${entityName}_dlg-buttonsAddr">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddrF()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Addr').dialog('close')">取消</a>
</div>
<div id="${entityName}_dlg-buttonsBank">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBank()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Bank').dialog('close')">取消</a>
</div>
   	<div id="xjLxr" class="easyui-dialog" closed="true" data-options="  
	    title: '客户联系人信息', 
	    href:'${ctx}/crm/customer/xjLxr?customerId='+${customers.id},  
	    width: 1000,
	    height: 400,     
	    cache: false,   
	    modal: true,
	    onMove: easyuiPanelOnMove    
">     
   </div>