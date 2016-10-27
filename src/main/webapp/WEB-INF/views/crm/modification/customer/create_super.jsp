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
<<style>
/*links*/
#accordian a {
	text-decoration: none;
	font-size: 11px;
	line-height: 27px;
	display: block;
	padding: 0 15px;
	transition: all 0.15s;
}
#accordian a:hover {
	background: #FFF;
	border-left: 5px solid lightgreen;
	border-right: 5px solid lightgreen;
}
</style>
	<c:set var="entityName" value="modification"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/modification"  /><!-- 命名空间 -->
<script type="text/javascript">	
var objectId=null;
var field,mlFieldOldValue,mlFieldNewValue,mlFieldOldText,mlFieldNewText,mlFieldNew,sourceId,customerTypeCode;
var customer;
var arCityField,arCountyField,arStreetField,arAddrDetailField,arZipCodeField;
var getProvinces,getArea;
	$(function() {
		postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
		postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
		//变更单 数据表单 接口用法 
		$('#biangeng').datagrid({  
			nowrap:false/*, 
		    columns:[[   
		        {field:'field',title:'信息项名', align:'center',width:200,formatter : function(value,rec){return value?value.fdFieldCn:'';}},   
		        {field:'mlFieldOldValue',title:'原值', align:'center',width:150,hidden:true},   
		        {field:'mlFieldOldText',title:'原值', align:'center',width:150,formatter :  function(value,rec){
			        return formatValue(value,rec,'old');  }}, 
		        {field:'mlFieldNewValue',title:'新值', align:'center',width:150,hidden:true}, 
		        {field:'mlFieldNewText',title:'新值', align:'center',width:150,formatter :  function(value,rec){
		        	 return formatValue(value,rec,'new');}}, 
		        {field:'sourceId',title:'联系人id',hidden: true, formatter : function(value,rec){
		        	 var sourceId = $('#contactperson').combobox('getValue');
		        	return sourceId?sourceId:'';
		        	}}, 	 
		        {field:'opt',title:'操作', align:'center',width:100,formatter : function(value,rec,rowIndex){return '<a href="javascript:void(0)"  onclick="remove('+ rowIndex+')">删除</span>';}}  
		    ]] */ 
		}).datagrid('acceptChanges'); 				
		$(".m_table td").has("label").css("text-align","right");	
	});
	
// 	function remove(id){
// 		$('#biangeng').datagrid('deleteRow',id);
// 		var data = $('#biangeng').datagrid('getData');
// 		$('#biangeng').datagrid('loadData',data);
// 	}
	
	function formatContact(value,rec) {
	 	var sourceId = $('#contactperson').combobox('getValue');
     	return sourceId?sourceId:'';
	}
	
	function formatOption(value,rec,rowIndex) {
		return '<a href="javascript:void(0)"  onclick="remove1('+ rowIndex+')">删除</span>';
	}
	
	//删除变更项
	function remove1(id){
		$('#biangeng').datagrid('deleteRow', id);
		//重新加载次,防止删除时候的id错乱
		$('#biangeng').datagrid('loadData',$('#biangeng').datagrid('getData'));
	}
	
	function formatOldFieldText(value,rec) {
		return formatValue(value,rec,'old');
	}
	
	function formatNewFieldText(value,rec) {
		return formatValue(value,rec,'new');
	}

	function add(){
		if (!$('#ff1').form('validate') || $("#existFlag").val()!='') {
		       return false;
		}
		if(objectId == null){
			$.messager.alert('提示','请先选择客户！');
			return false; 
		}		
		field_textOrdate(); // 加载类型text或是date
		field_select();	    // 加载类型select
		field_checkbox();   // 加载类型checkbox
		field_addrOrTel();  // 加载地址电话
	}

  //根据类别填充变更项    
   	function loadModificationItem(record) {
		  if(customerTypeCode==''){
			  customerTypeCode(objectId);
		  }
	comboboxUtil.setComboboxByUrlWithpanelHeight('biangengxiang', "${ctx}/modification/modification/getBianVal?fdClassEn="+record.fdClassEn+"&customerTypeCode="+customerTypeCode, 'id', 'fdFieldCn', '150', true,function(record){
	//comboboxUtil.setComboboxByUrlWithpanelHeight('biangengxiang', "${ctx}/modification/modification/getBianValNew?fdClassEn="+record.fdClassEn, 'id', 'fdFieldCn', '150', true,function(record){
			field = record;
			//如果为紧急联系人，把紧急联系人id赋值给obId
			  var obId = $('#contactperson').combobox('getValue');
			  if(obId=='' && record.fdClassEn != 'JinJiLianXiRenXinXi'){
				  obId = objectId;
			  }
			postAjax(false, "${ctx}/crm/fieldform?fieldId="+record.id+"&objectId="+obId, function(data) {$("#formId").html(data);})			
			if($("#existFlag").val()==null||$("#existFlag").val()==''){
				loadfield_textOrdate(); // 加载类型text或是date
				loadfield_select();	    // 加载类型select
				loadfield_checkbox();   // 加载类型checkbox
				loadfield_addrOrTel();  // 加载地址电话
			}
			mlFieldNew = field.fdFieldEn;
		});
     }
  
  function loadRecord(record){
	  field = record;
		//如果为紧急联系人，把紧急联系人id赋值给obId
		  var obId = $('#contactperson').combobox('getValue');
		  if(obId=='' && record.fdClassEn != 'JinJiLianXiRenXinXi'){
			  obId = objectId;
		  }
		postAjax(false, "${ctx}/crm/fieldform?fieldId="+record.id+"&objectId="+obId, function(data) {$("#formId").html(data);})			
		if($("#existFlag").val()==null||$("#existFlag").val()==''){
			loadfield_textOrdate(); // 加载类型text或是date
			loadfield_select();	    // 加载类型select
			loadfield_checkbox();   // 加载类型checkbox
			loadfield_addrOrTel();  // 加载地址电话
		}
		mlFieldNew = field.fdFieldEn;
  }
  
	  //加载变更项 类型
  function loadCustomerModificationItem(){
  	if(objectId==null){
			$.messager.alert('提示','请先选择客户！');  	
		}else{
			comboboxUtil.setComboboxByUrl('xinxibiangeng', "${ctx}/modification/modification/getBianTypeNew", 'id', 'fdClassCn', '150', true,function(record){
			//comboboxUtil.setComboboxByUrl('xinxibiangeng', "${ctx}/modification/modification/getBianType", 'id', 'fdClassCn', '150', true,function(record){	
				contactPerson(record);
				loadModificationItem(record);
				$("#formId").html('');
  		});
		}
  }

	  //紧急联系人下拉框
  function contactPerson(record){
      $("#contact").css("display","none");
      if(record.fdClassEn == 'JinJiLianXiRenXinXi')
      	$("#contact").css("display","block");
      	comboboxUtil.setComboboxByUrl('contactperson', "${ctx}/crm/contactperson/cpList?customerid="+objectId, 'id', 'cpName', '150', true,function(data){
      		loadModificationItem(record);
      		$("#formId").html('');
      	});	
  }
	  
	function addBianGeng(){
		if(isAdd('biangeng', field, mlFieldOldValue, mlFieldNewValue,sourceId)){
			$('#biangeng').datagrid('appendRow',{ 
				field:field,
				mlFieldOldValue:mlFieldOldValue,
				mlFieldOldText:mlFieldOldText,
				mlFieldNewValue:mlFieldNewValue,
				mlFieldNewText:mlFieldNewText,
				sourceId:sourceId
			});
		}
		}

	function telListCreate(mlFieldNewValue){
		mlFieldNewValue = $("#tlAreaCode").val()+'!@#';
		mlFieldNewValue += $("#"+field.fdFieldEn).val()+'!@#';
		mlFieldNewValue += $("#tlExtCode").val();
		return mlFieldNewValue;
		}

	function addrListCreate(mlFieldNewValue){
		mlFieldNewValue = $("#"+field.fdFieldEn).combobox('getValue')+'!@#';
		mlFieldNewValue += $("#arCity").combobox('getValue')+'!@#';	
		mlFieldNewValue += $("#arCounty").combobox('getValue')+'!@#';
		mlFieldNewValue += $("#arStreet").val()+'!@#';
		mlFieldNewValue += $("#arAddrDetail").val()+'!@#';
		mlFieldNewValue += $("#arZipCode").val();
		return mlFieldNewValue;
		}

	//房产地址--添加
	function houseAddrListCreate(mlFieldNewValue){
		mlFieldNewValue = $("#"+field.fdFieldEn).combobox('getValue')+'!@#';
		mlFieldNewValue += $("#heCity").combobox('getValue')+'!@#';	
		mlFieldNewValue += $("#heCounty").combobox('getValue')+'!@#';
		mlFieldNewValue += $("#heAddrDetail").val()+'!@#';
		mlFieldNewValue += $("#heZipCode").val();
		return mlFieldNewValue;
		}

	var checkSubmitFlg = false;
	function checkSubmit(){
		if(checkSubmitFlg == true){ 
			return false;
		}
		checkSubmitFlg = true;
		return true;
	} 
	//保存
	function save(state){
		if (checkSubmit()) {
			var detailData = getDataGridRowsJson('biangeng');
			if(detailData == "" || detailData == null || detailData.length == 0) {
				alert("变更明细里至少存在一个变更项!");
				checkSubmitFlg = false;
				return false;
			}
			setVal('modificationDetailList',getDataGridRowsJson('biangeng'));
			$('#ff').form('submit',{
				url: '${namespacePath}/modification/save?state=' + state,
				success: function(result){
					if (eval('('+result+')').result.success){
						//redirect('${ctx}/modification/customer');
						closeCurPageUpdateDatagrid('信息变更','信息变更新增','modification_customer_list');
					} else {
						$.messager.show({title: 'Error',msg: result.result.errMsg});
					}
					checkSubmitFlg = false;
				}
			})
		}
	}
	

	//查找客户
    function qq(value,name){   
		if($.trim(value) == ''){
			$.messager.show({title: '提示',msg: "查询条件不能为空!"})
			return;
		}
     if( $('#biangeng').datagrid('getChanges', "inserted")!=0){
    	 $.messager.alert('提示','更换客户前请清空变更项！');  	
         }else if(value){
    		showCustomerByNameAndUrl(getVal('name'),'${ctx}/crm/customer/find/customerBysearchTypeAndsearchValue?searchType=' + name + '&searchValue=' + value,function(rowIndex, rowData){
    			qqLoad(rowData);
		},function(data){
			if(data.total == 0){
				$.messager.show({title: '提示',msg: "没有查询到客户信息!"})
				return;
			}
			if(data.total!=1)
				$('#dd').dialog('open');
			else
				qqLoad(data.rows[0]);
		});
	}
    
    }   

	function qqLoad(rowData){
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
		$('#mnSourceId').val(rowData.id);
		customerTypeCode(rowData.id);
		loadCustomerModificationItem();//加载变更项
	    $('#dd').dialog('close');
	}
    
	function customerTypeCode(id){
		postAjax(false, "${ctx}/modification/modification/customerTypeCode?customerId="+id,function(data) {
				customerTypeCode = data;
			}, 'json');
	}
	
	//是否可以往dataGrid里添加数据
	function isAdd(id, field, oldValue, newValue,sourceId) {
		if(oldValue == newValue) {
			alert("变更后的值不能与变更之前的值相同!");
			return false;
		}
		if(field.fdFieldIsnull == 1 && isEmpty(newValue)) {
			alert(field.fdFieldCn + "不能为空!");
			return false;
		}
		
		if(!isEmpty(field.fdFieldLength)) {
			if(newValue.length > field.fdFieldLength){
				alert(field.fdFieldCn + "长度不能大于" + field.fdFieldLength + "!");
				return false;
			}
		}
		var data = getDataGridRows(id),len = data.length;
		//表示当前dataGrid里没有数据,那么可以直接添加
		if(len == 0) return true;
		if(isexist(field.id, data)) {
			alert("当前变更项已经存在!");
			return false;
		}
		return true;
	}
	//是否可以往dataGrid里添加数据
	function isAdd2(id, field, oldValue, newValue) {
		if(field.fdFieldIsnull == 1 && isEmpty(newValue)) {
			alert(field.fdFieldCn + "不能为空!");
			return false;
		}
		if(!isEmpty(field.fdFieldLength)) {
			if(newValue.length > field.fdFieldLength){
				alert(field.fdFieldCn + "长度不能大于" + field.fdFieldLength + "!");
				return false;
			}
		}
		var data = getDataGridRows(id),len = data.length;
		//表示当前dataGrid里没有数据,那么可以直接添加
		if(len == 0) return true;
		if(isexist(field.id, data)) {
			return false;
		}
		return true;
	}
	//验证数据是否在变更项里存在
	function isexist(fieldId, dataList) {
		var flag = false, len = dataList.length;
		for(var i = 0; i < len; i++) { if(dataList[i].field.id == fieldId) {flag = true; break;} }
		return flag;
	}
	//获取dataGrid里的所有数据,对象格式
	function getDataGridRows(id) {
		return $('#' + id).datagrid('getRows');
	}
	//获取dataGrid里的所有数据,JSON格式
	function getDataGridRowsJson(id) {
		var data = $('#' + id).datagrid('getRows');
		return data.length == 0 ? data : JSON.stringify(data);
	}
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
			//性别
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
		if(rec.field.fdReserve == '1'||field.fdReserve == '2'){
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

function sourceIdval(){
	var sourceId = $('#contactperson').combobox('getValue');
	return sourceId?sourceId:'';
}

//新增类型text或是date
function field_textOrdate(){
			if(field.fdFieldOption == 'text'||field.fdFieldOption == 'date'){
				if(field.fdReserve == 'forever' && $("[name='forever_crExpiryDate']:checked").val()=='0'){
					mlFieldNewValue = '2099-12-31';mlFieldNewText = '长期';
					//addBianGeng();    
				}else{
					mlFieldNewValue = $("#"+mlFieldNew).val();mlFieldNewText = $("#"+mlFieldNew).val();
					//addBianGeng();	
				}
				if (field.fdFieldEn == 'crEmail') {
					mlFieldNewValue = mlFieldNewValue.toLowerCase();
					var lengths = mlFieldNewValue.indexOf("@");
					if (lengths != -1){
						var aa = mlFieldNewValue.split("@");
						if (aa[1]=="zendaimoney.com") {
							$.messager.show({
								title: '提示',
								msg: '不能用公司邮箱！'
							});
							return;
						}
					}
					var result = searchEmail(mlFieldNewValue);
					if(result!=null){
						$.messager.show({
							title: '提示',
							msg: '该E-Mail已经存在！'
						});
						return;
					}	
				}
				sourceId = sourceIdval();
				addBianGeng();
			}
}
//检查email的唯一性
function searchEmail(mlFieldNewValue){
	var crEmail = mlFieldNewValue;
	var result;
	$.ajax({
		  	type: 'POST',
		  	async:false,
		  	url: '${ctx}/crm/customer/customerByCrEmail?crEmail='+crEmail+'&customerId='+objectId,
		  	success: function(value){
		  		result = value;
 		},
			dataType: 'json'
		});
 	return result; 
}
//新增类型select
function field_select(){
	if(field.fdFieldOption == 'select'){
		mlFieldNewValue = $("#"+mlFieldNew).combobox('getValue');
		mlFieldNewText = $("#"+mlFieldNew).combobox('getText');
		sourceId = sourceIdval();
		addBianGeng();
	}
}

//新增类型checkbox
function field_checkbox(){
	if(field.fdFieldOption == 'checkbox'){
		mlFieldNewValue = $("input:checkbox[name='"+mlFieldNew+"']:checked").val();
		mlFieldNewText = $("input:checkbox[name='"+mlFieldNew+"']:checked").attr('disp');
		sourceId = sourceIdval();
		addBianGeng();
	}
}


//新增地址电话
function field_addrOrTel(){
		if($("#existFlag").val()==null||$("#existFlag").val()==''){
				if(field.fdFieldOption == 'tel'&&field.fdReserve == '3'){
					mlFieldNewValue = $("#"+field.fdFieldEn).val();
					mlFieldNewText = $("#"+field.fdFieldEn).val();
					sourceId = sourceIdval();
					addBianGeng();
				}
				if(field.fdFieldOption == 'tel'&&(field.fdReserve == '1'||field.fdReserve == '2')){
					mlFieldNewValue = telListCreate(mlFieldNewValue);
					mlFieldOldText = telsplit(mlFieldOldValue);
					sourceId = sourceIdval();
					addBianGeng();
				} 
				if(field.fdFieldOption == 'addr'){
					if(field.fdReserve=='house'){	
						mlFieldNewValue =houseAddrListCreate(mlFieldNewValue);
						mlFieldNewText = houseAddrSplit(mlFieldNewValue);
					}else{	
						mlFieldNewValue = addrListCreate(mlFieldNewValue);
						mlFieldNewText = addrsplit(mlFieldNewValue);
					}
					sourceId = sourceIdval();
					addBianGeng();
				}
				if(field.fdFieldOption == 'tlPriority'||field.fdFieldOption == 'arPriority'){
					mlFieldNewValue = $("#"+mlFieldNew).combobox('getValue');
					mlFieldNewText = $("#"+mlFieldNew).combobox('getText');
					sourceId = sourceIdval();
					addBianGeng();
				}
			}
}

//加载类型text或是date
function loadfield_textOrdate(){
			if(field.fdFieldOption == 'text'||field.fdFieldOption == 'date'){
				if(field.fdReserve == 'forever' && $("[name='forever_crExpiryDate']:checked").val()=='0'){
					mlFieldOldValue = '2099-12-31';
					mlFieldOldText = '长期';
				}else{
					mlFieldOldValue = $("#"+field.fdFieldEn).val();
					mlFieldOldText = $("#"+field.fdFieldEn).val();
				}
			}
}

//加载类型select
function loadfield_select(){
	if(field.fdFieldOption == 'select'){
		mlFieldOldValue = $("#"+field.fdFieldEn).combobox('getValue');
		mlFieldOldText = $("#"+field.fdFieldEn).combobox('getText');
	}
}

//加载类型checkbox
function loadfield_checkbox(){
	if(field.fdFieldOption == 'checkbox'){
		mlFieldOldValue = $("input:checkbox[name='"+field.fdFieldEn+"']:checked").val();
		mlFieldOldText = $("input:checkbox[name='"+field.fdFieldEn+"']:checked").attr('disp');
	}
}


//加载地址电话
function loadfield_addrOrTel(){
			if(field.fdFieldOption == 'tel'){
				if(field.fdReserve == '1'||field.fdReserve == '2'){
					mlFieldOldValue = telListCreate(mlFieldOldValue);
					mlFieldOldText = telsplit(mlFieldOldValue);
					}
				if(field.fdReserve == '3'){
					mlFieldOldValue = $("#"+field.fdFieldEn).val();mlFieldOldText = $("#"+field.fdFieldEn).val();
					}
			}
			if(field.fdFieldOption == 'addr'){
				if(field.fdReserve=='house')
				{
					mlFieldNewValue =houseAddrListCreate(mlFieldNewValue);
					mlFieldOldText =houseAddrSplit(mlFieldOldValue);
				}
				else
				{
					mlFieldOldValue = addrListCreate(mlFieldOldValue);
					mlFieldOldText = addrsplit(mlFieldOldValue);
				}
			}
			if(field.fdFieldOption == 'tlPriority'||field.fdFieldOption == 'arPriority'){
				mlFieldOldValue = $("#"+field.fdFieldEn).combobox('getValue');
				mlFieldOldText = $("#"+field.fdFieldEn).combobox('getText');
				}
}
    	
</script>  
<body class="easyui-layout">
	<div data-options="region:'center'" id="main">
	    	<div class="m_title">新建变更单</div>
	    	<div class="m_content" style="width:820px;">

			    <table  class="m_table no_border" style="width:100%;">
				   <tr>
						<td colspan="4">
						<input id="ss" class="easyui-searchbox" searcher="qq"  prompt="请输入查询内容" menu="#mm" style="width:300px"></input>
						</td>
				   </tr>
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
			       		<td colspan="4">变更信息</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
			       <tr>
						<td nowrap="nowrap"><label for="xinxibiangeng">信息分类</label></td>
						<td><input id="xinxibiangeng" class="easyui-combobox" name="xinxibiangeng"></td>
						<td nowrap="nowrap"><label for="biangengxiang">变更项</label></td>
						<td>
						<div id="contact" style="display:none">
						<input id="contactperson" class="easyui-combobox" name="contactperson" style="width: 200;" /></div>
						<input id="biangengxiang" class="easyui-combobox" name="biangengxiang" style="width: 200;"></td>
				   </tr>
				   <tr>
						
						<td colspan="4"><form id="ff1"><div id="formId"></div></form></td>
				   </tr>
				   <tr>
						<td colspan="4" align="center"><a href="#" onclick="add()" class="easyui-linkbutton" >增加</a></td>
				   </tr>
				   <tr class="tr1">
			       		<td colspan="4">变更明细</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
				</table>
				<table id="biangeng" class="easyui-datagrid" width="100%">
					<thead>  
				        <tr>
				        	<th data-options="field:'field',width:200,align:'center',formatter : function(value,rec){return value ? value.fdFieldCn : '';}">信息项名</th>
				        	<th data-options="field:'mlFieldOldValue',width:150,hidden:true">原值</th>
				            <th data-options="field:'mlFieldOldText',width:250,align:'center',formatter:formatOldFieldText">原值</th>  
				            <th data-options="field:'mlFieldNewValue',width:150,hidden:true">新值</th>  
				            <th data-options="field:'mlFieldNewText',width:250,align:'center',formatter : formatNewFieldText">新值</th> 
				            <th data-options="field:'sourceId',hidden: true,formatter : formatContact">联系人id</th>
				            <th data-options="field:'option',width:80,align:'center',formatter : formatOption">操作</th>  
				        </tr>
		        </thead>
				</table>
			<form id="ff" method="post">
			    <input type="hidden" id="sourceId" name="sourceId">
	    		<input type="hidden" id="mnSourceId" name="mnSourceId">
	    		<input type="hidden" id="modificationDetailList" name="modificationDetailList"/>
	    		<input type="hidden" id="mnType" name="mnType" value="<%= ParamConstant.BIANGENGCUSTOMER%>">
				<table  class="m_table no_border" style="width:100%;">
				   <tr class="tr1">
			       		<td colspan="4">备注</td>
			       </tr>
				   <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
			       <tr>
						<td colspan="4"><textarea name="mnMemo" rows="6" style="width: 100%" title="请输入文本" maxlength='65'></textarea></td>
				   </tr>	   
				</table>
	    	</form>
		<div class="m_btn_box" style="width:820px;">  
   	    	
        </div> 
        </div>
   	    <div class="m_btn_box" style="width:820px;">  
   	    	<a href="#" onclick="save(3)" class="easyui-linkbutton" >提交</a><!-- 审核中 -->
            <a href="#" onclick="save(1)" class="easyui-linkbutton" >暂存</a>  <!-- 待处理 -->
            <a href="#" onclick="closeCurPage('信息变更新增')" class="easyui-linkbutton" >取消</a>  
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