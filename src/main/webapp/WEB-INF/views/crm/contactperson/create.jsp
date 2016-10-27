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

	<c:set var="entityName" value="contactperson"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/contactperson"  /><!-- 命名空间 -->
<c:set var="isMx" value="${urlnow=='edit_mx' || urlnow=='create_contactmx'?true:false}"/>
<script type="text/javascript">
var getProvinces,getArea;
var isMx = ${urlnow=='edit_mx' || urlnow=='create_contactmx'?'true':'false' };
var tlTelTypeTypeList=new Array();
var arAddrTypeList=new Array();
var arProvinceList=new Array();
var priorityList=new Array();

window.onload = function(){
	postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
	postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');

		addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//客户户口地址
		comboboxUtil.setComboboxByUrl('cpRelation', "${ctx}/crm/param/findAllByPrType?prType=relation", 'prValue', 'prName', '150', isMx);//关系
		comboboxUtil.setComboboxByUrl('cpOccupation', "${ctx}/crm/param/findAllByPrType?prType=occupation", 'prValue', 'prName', '150', false);//职业
		comboboxUtil.setComboboxByUrl('cpPost', "${ctx}/crm/param/findAllByPrType?prType=duty", 'prValue', 'prName', '150', isMx);//职务  
		comboboxUtil.setComboboxByUrl('CpersonType', "${ctx}/crm/param/findAllByPrType?prType=contactpersonType", 'prValue', 'prName', '150', isMx);//联系人类型
}

$(function() {
	
	if(!isTopUrl()){ 
		$("#conSave").hide(); //隐藏保存并继续添加按钮
	}
	
	//身份证号码补全
	$("#cpId").blur(function(){
		var idType = $('#cpIdtype').combobox('getValue')
		if(idType=='1'){
			var idNum = $("#cpId").val();
			var list = showBirthday(idNum);
			$('#cpBirthday').val(list[0]);
			$("input:checkbox[name='cpSex']").each(function(){
				$(this).attr('checked',false);
				if($(this).val()==list[1]){
					$(this).attr("checked",true);
					} 
			})
		}
	});
	
	
	var prTypeNameList=["telType","addrType","priority"];
	postAjax(false,'${ctx}/crm/param/findAllByPrTypeList?prTypeList='+prTypeNameList, function(row){
  		tlTelTypeTypeList = row[0];
  		arAddrTypeList = row[1];
  		priorityList = row[2];
	},'json')

		comboboxUtil.setComboboxByData('tlTelType',tlTelTypeTypeList, 'prValue', 'prName', '150', true,function(record){
			var content;
			if(record.prName == "手机"){
				content = '<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 150px;"/>';
				$("#tlTelTypeId").html(content);
				$('#tlTelNum').validatebox({validType:"mobile"});
			}else{
				content = '<input id="tlAreaCode" class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"/>-';
				content +='<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;" />-';
				content +='<input id="tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"/>';
				$("#tlTelTypeId").html(content);
				$('#tlAreaCode').validatebox({validType:"numberL[3,4]"});
				$('#tlTelNum').validatebox({validType:"numberL[6,8]"});
				$('#tlExtCode').validatebox({validType:"numberL[0,6]"});
			}
			$("#tlTelType").val(record.prValue);
			 $('#tlPriority').combobox('select','');
		});
		comboboxUtil.setComboboxByData('arAddrType',arAddrTypeList, 'prValue', 'prName', '150', true,function(record){
			 $("#arAddrType").val(record.prValue);
			 $('#arPriority').combobox('select','');
		});

	comboboxUtil.setComboboxByUrl('cpIdtype', "${ctx}/crm/param/findAllByPrType?prType=idtype", 'prValue', 'prName', '150', false,function(record){
	    if(record.prName=='身份证'){
	    	$('#cpId').validatebox({validType:"idcard"});
		    }else{
		    	$('#cpId').validatebox({validType:""});
		 }
	});
	
	comboboxUtil.setComboboxByUrl('tlPriority', "${ctx}/crm/param/findAllByPrType?prType=priority", 'prValue', 'prName', '150', true,function(value){
		$('#saveTelId').linkbutton({  
		    disabled:false
		});
		if(value.prName=="高" && $("#tlPH").val()!='1'){
			if($("#tlTelType").val()=='' || $("#tlTelType").val()==null){
				$.messager.confirm('提示','请先选择电话类型！'); 
				$('#saveTelId').linkbutton({  
				    disabled:true
				});	
				return;
			}
			   dealPriority('tel','saveTelId','tlPriority',$("#tlTelType").val());		 
		}else{
			$('#saveTelId').linkbutton({  
			    disabled:false
			});
		}
	});//电话优先级
	comboboxUtil.setComboboxByUrl('arPriority', "${ctx}/crm/param/findAllByPrType?prType=priority", 'prValue', 'prName', '150', true,function(value){
		$('#saveAddrId').linkbutton({  
		    disabled:false
		});
			if(value.prName=="高"  && $("#adPH").val()!='1'){
				if($("#arAddrType").val()=='' || $("#arAddrType").val()==null){
					$.messager.confirm('提示','请先选择地址类型！'); 
					$('#saveAddrId').linkbutton({  
					   	 disabled:true
						});
				}
				dealPriority('addr','saveAddrId','arPriority',$("#arAddrType").val());			
			}else{
				$('#saveAddrId').linkbutton({  
				    disabled:false
				});
			}
	});//地址优先级		
	
	//优先级处理 Jinghr,2013-7-24 18:30:58
	function dealPriority(datagrid,buttonId,field,type){
		if ($('#'+datagrid).datagrid('getChanges').length) { 
	        var insertRows = $('#'+datagrid).datagrid('getChanges');  
	        if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						if(field=='tlPriority'){
						if((insertRows[i].tlPriority) == 1 && insertRows[i].tlTelType==type ){
						 $.messager.confirm('提示','同一类型只能录入一条最高优先级，请先修改原最高优先级！'); 
							$('#'+buttonId).linkbutton({  
							    disabled:true
							});	
						}
						}else if(field=='arPriority'){
							if((insertRows[i].arPriority) == 1 && insertRows[i].arAddrType==type){
								 $.messager.confirm('提示','同一类型只能录入一条最高优先级，请先修改原最高优先级！'); 
									$('#'+buttonId).linkbutton({  
									    disabled:true
									});	
								}
						}
					}
				} 
		 } 
	}
	
	// 电话信息 
	$('#tel').datagrid({   
		width:860,
	    columns:[[   
	        {field:'tlAreaCode',title:'区号',width:100},   
	        {field:'tlTelNum',title:'电话',width:200,formatter:function(value,rec){
	        	telExist(value,isMx);
	        	return value;
	        }},   
	        {field:'tlExtCode',title:'分机号',width:100}, 
	        {field:'tlTelType',title:'电话类型',width:100,
	            formatter : function(value,rec) {
	            	return formatVal(tlTelTypeTypeList,value);
	            }}, 
	        {field:'tlPriority',title:'优先级',width:100,
		            formatter : function(value,rec) {
		            	return formatVal(priorityList,value);
	            }}, 	
	        {field:'tlValid',title:'是否有效',width:100,formatter : function(value,rec,foId) {
					if(value == "1"){return "有效";}else{return "无效";}
			}}, 	        
	        {field:'opt',title:'<a href="javascript:void(0)" class="easyui-linkbutton" onclick="appendTel()">添加</a>',width:90,formatter : function(value, rec,foId) {

				var links = '<a href="javascript:void(0)" onclick="editTel('+foId+')">编辑</span>&nbsp;&nbsp;&nbsp;&nbsp;';
				/* links += '<a href="javascript:void(0)"  onclick="removeTel('+foId+')">删除</a>'; */
				return links;
			}} 
	    ]]   
	}).datagrid('acceptChanges');  
	$('#addr').datagrid({ 
		width:970,
	    columns:[[   
	        {field:'arProvince',title:'省',width:100,
	            formatter : function(value,rec) {
	            	var prName123=filterAreaById(getProvinces,value);//,getArea
	            	return prName123?prName123.name:'';
	            }},   
	        {field:'arCity',title:'市',width:100,
		            formatter : function(value,rec) {
			        	var prName123=filterAreaById(getArea,value);//,getArea
		            	return prName123?prName123.name:'';
		            }},   
	        {field:'arCounty',title:'县/区',width:100,
			            formatter : function(value,rec) {
				        	var prName123=filterAreaById(getArea,value);//,getArea
			            	return prName123?prName123.name:'';
			            }}, 
	        {field:'arAddrDetail',title:'详细地址',width:200,formatter:function(value,rec){
	        	if(rec.arAddrType=='1' || rec.arAddrType=='2')
	        	addrExist(value,isMx);
	        	return value;}}, 
	        {field:'arAddrType',title:'地址类型',width:100, formatter : function(value,rec){return formatVal(arAddrTypeList,value);}}, 
	        {field:'arPriority',title:'优先级',width:100, formatter : function(value,rec) {return formatVal(priorityList,value);}}, 
	        {field:'arValid',title:'是否有效',width:100,formatter : function(value, rec,foId) {
					if(value == "1"){return "有效";}else{return "无效"}
			}}, 	
	        {field:'opt',title:'<a href="javascript:void(0)" class="easyui-linkbutton" onclick="appendAddr()">添加</a>',width:90,formatter : function(value, rec,foId) {
				var links = '<a href="javascript:void(0)" onclick="editAddr('+foId+')">编辑</span>&nbsp;&nbsp;&nbsp;&nbsp;';
				/* links += '<a href="javascript:void(0)"  onclick="removeAddr('+foId+')">删除</a>'; */
				return links;
			}} 
	    ]]   
	}).datagrid('acceptChanges');  
	$(".m_table td").has("label").css("text-align","right");
	$(".m_table th").has("label").css("text-align","right");
});

//是否录入电话信息
function telExist(value,isMx){
	if(isMx)
		if(value!='' && value!=null)
			$("#telExist").val("1");
		else
			$("#telExist").val("0");
	else
		$("#telExist").val("0");	
}

//是否录入地址信息
function addrExist(value,isMx){
	if(isMx)
		if(value!='' && value!=null)
			$("#addrExist").val("1");
		else
			$("#addrExist").val("0");
	else
		$("#addrExist").val("0");	
}

function appendTel(){
	$("#tlPH").val("0");
	$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话添加');
	$('#${entityName}_TelForm').form('clear');
	$('#${entityName}_TelForm').form('load',{"tlValid":"1"});
};
function appendAddr(){
	$("#adPH").val("0");
	$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址添加');
	$('#${entityName}_AddrForm').form('clear');
	$('#${entityName}_AddrForm').form('load',{"arValid":"1"});
};
function saveTel(){
	var rowid = $('#telid').val();
	if($('#${entityName}_TelForm').form('validate')){
		var val = $("[name='tlValid']:checked").val();
		if(rowid){
			$('#tel').datagrid('updateRow',{index:rowid,
	            row:{
				tlAreaCode:$('#tlAreaCode').val()?$('#tlAreaCode').val():'',
				tlTelNum:$('#tlTelNum').val(),
				tlExtCode:$('#tlExtCode').val()?$('#tlExtCode').val():'',
				tlTelType:$('#tlTelType').combobox('getValue'),
				tlPriority:$('#tlPriority').combobox('getValue'),
				tlValid:val?val:''			
			}});
			$('#${entityName}_Tel').dialog('close');
			}else{
			$('#tel').datagrid('appendRow',{
				tlAreaCode:$('#tlAreaCode').val()?$('#tlAreaCode').val():'',
				tlTelNum:$('#tlTelNum').val(),
				tlExtCode:$('#tlExtCode').val()?$('#tlExtCode').val():'',
				tlTelType:$('#tlTelType').combobox('getValue'),
				tlPriority:$('#tlPriority').combobox('getValue'),
				tlValid:val?val:''
			});
			$('#${entityName}_Tel').dialog('close');
		}
	}
	$('#${entityName}_TelForm').form('load',row);
	$("#tlPH").val("0");
}
function saveAddr(){
	var rowid = $('#addrid').val();
	if($('#${entityName}_AddrForm').form('validate')){
		var val = $("[name='arValid']:checked").val();
		if(rowid){
			$('#addr').datagrid('updateRow',{index:rowid,
	            row:{
	            	arProvince:$('#arProvince').combobox('getValue')?$('#arProvince').combobox('getValue'):'',
	          		arCity:$('#arCity').combobox('getValue')?$('#arCity').combobox('getValue'):'',
	          		arCounty:$('#arCounty').combobox('getValue')?$('#arCounty').combobox('getValue'):'',
	          		arAddrType:$('#arAddrType').combobox('getValue'),
	          		arAddrDetail:$('#arAddrDetail').val()?$('#arAddrDetail').val():'',
	          		arPriority:$('#arPriority').combobox('getValue'),
	          		arValid:val?val:''
			}});
			$('#${entityName}_Addr').dialog('close');
			}else{
			$('#addr').datagrid('appendRow',{
				arProvince:$('#arProvince').combobox('getValue')?$('#arProvince').combobox('getValue'):'',
				arCity:$('#arCity').combobox('getValue')?$('#arCity').combobox('getValue'):'',
				arCounty:$('#arCounty').combobox('getValue')?$('#arCounty').combobox('getValue'):'',
				arAddrType:$('#arAddrType').combobox('getValue'),
				arAddrDetail:$('#arAddrDetail').val()?$('#arAddrDetail').val():'',
				arPriority:$('#arPriority').combobox('getValue'),
				arValid:val?val:''
			});
			$('#${entityName}_Addr').dialog('close');
		}
	}
	$("#adPH").val("0");
}

function removeAddr(id){
	$("#addrExist").val("0");
	$('#addr').datagrid('deleteRow',id);
	var data = $('#addr').datagrid('getData');
	$('#addr').datagrid('loadData',data);
	$('#saveAddrId').linkbutton({  
	    disabled:false
	});
}
function removeTel(id){
	$("#telExist").val("0");
	$('#tel').datagrid('deleteRow',id);
	var data = $('#tel').datagrid('getData');
	$('#tel').datagrid('loadData',data);
	$('#saveTelId').linkbutton({  
	    disabled:false
	});
}
function editAddr(id){
	$('#saveAddrId').linkbutton({  
	    disabled:false
	});	
	$('#addr').datagrid('selectRow', id);
	var row = $('#addr').datagrid('getSelected');
	row.addrid=id;
	$("#adPH").val(row.arPriority);
	$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址添加');
	$('#${entityName}_AddrForm').form('clear');
	$('#${entityName}_AddrForm').form('load',row);
}
function editTel(id){
	$('#saveTelId').linkbutton({  
	    disabled:false
	});	
	$('#tel').datagrid('selectRow', id);
	var row = $('#tel').datagrid('getSelected');
	row.telid=id;
	$("#tlPH").val(row.tlPriority);
	$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话添加');
	$('#${entityName}_TelForm').form('clear');
	if(row.tlTelType == <%= ParamConstant.MOBILE%>){
		content = '<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 150px;"/>';
		$("#tlTelTypeId").html(content);
		$('#tlTelNum').validatebox({validType:"mobile"});
	}else{
		content = '<input id="tlAreaCode" class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"/>-';
		content +='<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;" requried/>-';
		content +='<input id="tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"/>';
		$("#tlTelTypeId").html(content);
		$('#tlAreaCode').validatebox({validType:"numberL[3,4]"});
		$('#tlTelNum').validatebox({validType:"numberL[6,8]"});
		$('#tlExtCode').validatebox({validType:"numberL[0,6]"});
	}
	$('#${entityName}_TelForm').form('load',row);
}

//民信新增，地址，电话信息记录存在
function addrTelExistMx(){
	if(isMx){
		if($("#telExist").val()!='1'){
			$.messager.show({
				title: '联系人电话',
				msg: '<font color=red>请录入至少一条联系人电话记录,电话号码不能为空</font>'
			});
			return true;
		}
		if($("#addrExist").val()!='1'){
			$.messager.show({
				title: '联系人家庭/单位地址',
				msg: '<font color=red>请录入至少一条联系人家庭/单位地址</font>'
			});
			return true;
		}
		return false;
	}
	return false;
}

function save(){
	var changesRows = {insertTel:[],insertAddr:[]};
    if ($('#tel').datagrid('getChanges').length) { 
        var insertRows = $('#tel').datagrid('getChanges', "inserted");  
			if (insertRows.length>0) {
				for (var i=0;i<insertRows.length;i++) {
					changesRows.insertTel.push(insertRows[i]);
				}
			}
    }
    if ($('#addr').datagrid('getChanges').length) { 
        var insertRows = $('#addr').datagrid('getChanges', "inserted");  
			if (insertRows.length>0) {
				for (var i=0;i<insertRows.length;i++) {
					changesRows.insertAddr.push(insertRows[i]);
				}
			}
    }
	var jsonString = JSON.stringify(changesRows);
	setVal('jsonString',jsonString);
	
	$('#ff').form('submit',{
		url: '${namespacePath}/save?customerid='+${customerid},
		onSubmit: function(){
			return $(this).form('validate') && !addrTelExistMx();
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.result.success){
				redirect('${ctx}/crm/customer/${urlnow}/?id=${customerid}&tabs=1');	//跳转
				$(".easyui-tabs").tabs("select",1);
			} else {
				$.messager.show({
					title: 'Error',
					msg: result.result.errMsg
				});
			}
		}
	})
}


function continueSave(){
	var changesRows = {insertTel:[],insertAddr:[]};
    if ($('#tel').datagrid('getChanges').length) { 
        var insertRows = $('#tel').datagrid('getChanges', "inserted");  
			if (insertRows.length>0) {
				for (var i=0;i<insertRows.length;i++) {
					changesRows.insertTel.push(insertRows[i]);
				}
			}
    }
    if ($('#addr').datagrid('getChanges').length) { 
        var insertRows = $('#addr').datagrid('getChanges', "inserted");  
			if (insertRows.length>0) {
				for (var i=0;i<insertRows.length;i++) {
					changesRows.insertAddr.push(insertRows[i]);
				}
			}
    }
	var jsonString = JSON.stringify(changesRows);
	setVal('jsonString',jsonString);
	
	$('#ff').form('submit',{
		url: '${namespacePath}/save?customerid='+${customerid},
		onSubmit: function(){
			return $(this).form('validate') && !addrTelExistMx();
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.result.success){
				window.parent.show('提示','保存成功！');
				redirect('${ctx}/crm/contactperson/create?customerid=${customerid}&urlnow=${urlnow}');	//跳转
			} else {
				$.messager.show({
					title: 'Error',
					msg: result.result.errMsg
				});
			}
		}
	})
}

</script>
<body class="easyui-layout">
	<div data-options="region:'center'" id="main" >
	    <form id="ff" method="post" action="${namespacePath}/save">
	    <div class="m_title" >新建联系人信息</div>
	    <div class="m_content2" style="width:100%;" >
		    <div class="m_mar">
		    <input type="hidden" name="customerid" value = "${customerid}" >
		    <input id = "jsonString" name = "jsonString" type="hidden">
		    <input id="tlPH" type="hidden" value="0"/>
	        <input id="adPH" type="hidden" value="0"/>
		    <table  class="m_table" width="100%">
			    <tr class="tr1">
			       	<td colspan="2"> 联系人基本信息  <input id="telExist" type="hidden" value="0"><input id="addrExist" type="hidden" value="0"></td>
			    	<td colspan="1"> <label for="kehu">客户：</label></td>
			    	<td colspan="1" align="left"> ${customerName}</td>
			    </tr>
			    <tr class="tr2">
			       	<td colspan="4"></td>
			    </tr>
			    <tr>
		    	  	<td><label for="cpName" class="bitian"></label>姓名(中文)：</td>
			    	<td><input class="easyui-validatebox" type="text" name="cpName" validType="CHS" required="true"/></td>
			    	<td><label for="cpEname">姓名(英文)：</label></td>
			    	<td><input class="easyui-validatebox" type="text" name="cpEname" validType="EHS" /></td>
			    </tr>
	 			<tr>
					<td><label for="cpIdtype">证件类型：</label></td>
			    	<td><input id="cpIdtype" class="easyui-combobox" type="text" name="cpIdtype"></input></td>
			    	<td><label for="cpSex"  class="${isMx?'':'bitian1'}">性别：</label></td>
			    	<td>
			    		<input type="checkbox" name="cpSex" onclick="checkedThis($(this))" value="1">男
						<input type="checkbox" name="cpSex" onclick="checkedThis($(this))" value="0">女
					</td>
			    </tr>
			     <tr>
		    	  	<td><label for="cpSex">证件号码：</label></td>
			    	<td><input id="cpId" class="easyui-validatebox" type="text" name="cpId"  /></td>
			    	
			        <td><label for="cpSex" class="${isMx?'bitian':''}" style="display:${isMx?'block':'none' }">联系人类型</label></td>
			    	<td><div style="display:${isMx?'block':'none' }"><input id="CpersonType" class="easyui-combobox" type="text" name="cpContactpersonType" style="width: 400px"></div></td>
			    </tr>
	
	  		    <tr>
			    	<td><label for="cpBirthday">出生日期 ：</label></td>
			    	<td><input type="text" class="Wdate" id="cpBirthday"  name="cpBirthday" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
			    	<td><label for="cpRelation" class="${isMx?'bitian':'bitian1'}">与客户关系：</label></td>
			    	<td><input id="cpRelation" class="easyui-combobox" name="cpRelation"></td>
			    </tr>
			    	<tr class="tr1">
			       		<td colspan="4">职业信息</td>
			       </tr>
			       <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
		       	 <tr>
			    	<td width="15%"><label for="cpWorkunit" class="${isMx?'bitian':''}">单位名称：</label></td>
			    	<td width="35%"><input class="easyui-validatebox" type="text" name="cpWorkunit"  ${isMx?'required':''}></input></td>
			    	<td width="15%"><label for="cpOccupation">职业：</label></td>
			    	<td width="35%"><input id="cpOccupation" class="easyui-combobox" name="cpOccupation"></td>
			    </tr>
			    <tr>
			    	<td><label for="cpPost" class="${isMx?'bitian':''}">职务：</label></td>
			    	<td><input id="cpPost" class="easyui-combobox" name="cpPost"></td>
			    	<td><label for="cpDepart">工作部门：</label></td>
			    	<td><input class="easyui-validatebox" type="text" name="cpDepart"></input></td>
			    </tr>
			
			    <tr class="tr1">
			       		<td colspan="4">联系信息</td>
			       </tr>
			       <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
		       	<tr>
			    	<td><label for="cpQq">QQ：</label></td>
			    	<td><input class="easyui-validatebox" type="text" name="cpQq" validType="QQ"></td>
			    	<td><label for="cpMsn">MSN：</label></td>
			    	<td><input class="easyui-validatebox" type="text" name="cpMsn"></td>
			    </tr>
			    <tr>
			    	<td><label for="cpEmail">E-Mail：</label></td>
			    	<td colspan="3"><input class="easyui-validatebox" type="text" name="cpEmail"  validType="email"></input></td>
			    </tr>
			    
			    <tr>
	      			<td>
			            <label for="cpMemo">备注:</label>
						</td>
					<td colspan="3">
			            <textarea name="cpMemo" style="height:60px;width: 40%;"></textarea>		        </td>
			   </tr>
			</table>
			
			 <table  class="m_table" width="100%">
			<tr>
			    <td><label for="crQq" class="${isMx?'bitian':'bitian1'}">电话：</label></td>
			    	<td colspan="3">
			    	<table id="tel" class="easyui-datagrid"   width="100%">   
	            </table>
			    	</td>
			    </tr>
			    <tr>
			    <td><label for="crQq" class="${isMx?'bitian':''}">地址：</label></td>
			    	<td colspan="3">
			    	<table id="addr" class="easyui-datagrid"  width="100%">   
	            </table>
			    </tr>
			 </table>
			    
			</div>
			</div>
	    </form>
  	    <div style="background:#fafafa;text-align:center;padding:5px">  
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save();">保存</a>  
             <a id="conSave" href="javascript:void(0)" class="easyui-linkbutton" onclick="continueSave();">保存并继续添加</a> 
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="redirect('${ctx}/crm/customer/${urlnow}/?id=${customerid}&tabs=1');">取消</a>  
        </div> 
	</div>
	

<div id="${entityName}_Tel" class="easyui-dialog" style="width:500px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsTel">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">电话添加</div>
		<form id="${entityName}_TelForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="telid" type="hidden" name="telid">
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="tlTelType">电话类型:</label></td>
				<td><input id="tlTelType" class="easyui-combobox" name="tlTelType"></td>
			</tr>
			<tr>
				<td><label>电话:</label></td>
				<td id="tlTelTypeId"><input id="tlAreaCode"  class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"/>-
				<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;" ${isMx?'required':''}/>-
				<input id="tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"/>
				</td>
			</tr>
			<tr>
				<td><label for="tlPriority">优先级:</label></td>
				<td><input id="tlPriority" class="easyui-combobox" name="tlPriority"  ></td>
			</tr>
			<tr>
				<td><label>是否有效:</label></td>
				<td><input id="tlValid" name="tlValid"  type="radio" value="1" >有效
				<input id="tlValid" name="tlValid"   type="radio" value="0" >无效</td>
			</tr>
			</table>
		</form>
		</div>
		</div>
</div>

<div id="${entityName}_Addr" class="easyui-dialog" style="width:500px;height:320px;" closed="true" buttons="#${entityName}_dlg-buttonsAddr">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">地址添加</div>
		<form id="${entityName}_AddrForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="addrid" type="hidden" name="addrid">
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="arAddrType">地址类型:</label></td>
				<td><input id="arAddrType" class="easyui-combobox" name="arAddrType"></td>
			</tr>
			<tr>
				<td><label>地址:</label></td>
				<td><input id="arProvince" class="easyui-combobox" name="arProvince" style="width:110px;">省-
					<input id="arCity" class="easyui-combobox" name="arCity" style="width: 100px;" validType="selectValidator['arCity']">市-
					<input id="arCounty" class="easyui-combobox" name="arCounty" style="width: 60px;" validType="selectValidator['arCounty']">县
					<input id="arStreet" type="hidden" name="arStreet" value="">
				</td>
			</tr>
			<tr>
				<td><label>详细地址</label></td>
				<td><input id="arAddrDetail" class="easyui-validatebox" type="text" name="arAddrDetail" style="width: 350px;" ${isMx?'required':''}></input></td>
			</tr>
			<tr>
				<td><label for="arPriority">优先级:</label></td>
				<td><input id="arPriority" class="easyui-combobox" name="arPriority"  ></td>
			</tr>
			<tr>
				<td><label>是否有效:</label></td>
				<td><input id="arValid" name="arValid"  type="radio" value="1">有效
				<input id="arValid" name="arValid"   type="radio" value="0" >无效</td>
			</tr>
				</table>
		</form>
		</div>
		</div>
</div>
<div id="${entityName}_dlg-buttonsTel">
	<a id="saveTelId" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveTel()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Tel').dialog('close')">取消</a>
</div>
<div id="${entityName}_dlg-buttonsAddr">
	<a id="saveAddrId" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddr()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Addr').dialog('close')">取消</a>
</div>
	</body>
