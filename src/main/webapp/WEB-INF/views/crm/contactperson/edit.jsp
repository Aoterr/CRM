<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
var mobileValue;
var getProvinces,getArea;
var addrE = 0;
var isMx = ${urlnow=='edit_mx' || urlnow=='create_contactmx'?'true':'false' };
$(function() {
	
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
	
	postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
	postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
	var tlTelTypeTypeList=new Array();
	var arAddrTypeList=new Array();
	var arProvinceList=new Array();
	var priorityList=new Array();
	var prTypeNameList=["telType","addrType","","priority"];
	postAjax(false,'${ctx}/crm/param/findAllByPrTypeList?prTypeList='+prTypeNameList, function(row){
  		tlTelTypeTypeList = row[0];
  		arAddrTypeList = row[1];
  		arProvinceList = row[2];
  		priorityList = row[3];
	},'json')

		addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//客户户口地址
		//comboboxUtil.setComboboxByUrl('cpRelation', "${ctx}/crm/param/findAllByPrType?prType=relation", 'prValue', 'prName', '150', isMx);//关系
	//	comboboxUtil.setComboboxByUrl('cpOccupation', "${ctx}/crm/param/findAllByPrType?prType=occupation", 'prValue', 'prName', '150', false);//职业
	//	comboboxUtil.setComboboxByUrl('cpPost', "${ctx}/crm/param/findAllByPrType?prType=duty", 'prValue', 'prName', '150', isMx);//职务
	//	comboboxUtil.setComboboxByUrl('CpersonType', "${ctx}/crm/param/findAllByPrType?prType=contactpersonType", 'prValue', 'prName', '150', isMx);//联系人类型
		
	/* 	comboboxUtil.setComboboxByUrl('cpIdtype', "${ctx}/crm/param/findAllByPrType?prType=idtype", 'prValue', 'prName', '150', false,function(record){
		    if(record.prName=='身份证'){
		    	$('#cpId').validatebox({validType:"idcard"});
			    }else{
			    	$('#cpId').validatebox({validType:""});
			 }
		}); */
		
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
				$.post('${namespacePath}/telHighPriority?customerid='+${contactpersons.id}+'&telType='+$("#tlTelType").val(),function(result) {
					if(result.data=='hasHigh'){
						$.messager.confirm('提示','同一类型只能录入一条最高优先级，请先修改原最高优先级！'); 
						$('#saveTelId').linkbutton({  
					   	 disabled:true
						});	
					}
					}, 'json');	
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
			if(value.prName=="高" && $("#adPH").val()!='1'){
				if($("#arAddrType").val()=='' || $("#arAddrType").val()==null){
					$.messager.confirm('提示','请先选择地址类型！'); 
					$('#saveAddrId').linkbutton({  
					   	 disabled:true
						});
				}
				$.post('${namespacePath}/addrHighPriority?customerid='+${contactpersons.id}+'&arAddrType='+$("#arAddrType").val(),function(result) {
					if(result.data=='hasHigh'){
						$.messager.confirm('提示','同一类型只能录入一条最高优先级，请先修改原最高优先级！'); 
						$('#saveAddrId').linkbutton({  
					   	 disabled:true
						});	
					}
				}, 'json');	
			}else{
				$('#saveAddrId').linkbutton({  
				    disabled:false
				});
			}
		});//地址优先级			
		comboboxUtil.setComboboxByData('tlTelType',tlTelTypeTypeList, 'prValue', 'prName', '150', true,function(record){
			var content;
			if(record.prName == "手机"){
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
			$("#tlTelType").val(record.prValue);
			 $('#tlPriority').combobox('select','');
		});
		comboboxUtil.setComboboxByData('arAddrType',arAddrTypeList, 'prValue', 'prName', '150', true,function(record){
			 $("#arAddrType").val(record.prValue);
			 $('#arPriority').combobox('select','');
		});
	
	$('#tel').datagrid({   
		url: '${namespacePath}/tel?id=${contactpersons.id}',
		width:950,
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
	        {field:'tlValid',title:'是否有效',width:100,formatter : function(value, rec,foId) {
					if(value == "1"){return "有效";}else{return "无效"}
			}}, 	        
	        {field:'opt',title:'<a href="javascript:void(0)" class="easyui-linkbutton" onclick="newTel()">添加</a>',width:100,formatter : function(value, rec,foId) {

				var links = '<a href="" onclick="return editTel('+rec.id+')">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
				links += '<a href=""  onclick="return delTel('+rec.id+')">删除</a>';
				return links;
			}} 
	    ]]   
	});  
	$('#addr').datagrid({ 
		url: '${namespacePath}/addr?id=${contactpersons.id}',
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
	        	//if(rec.arAddrType=='1' || rec.arAddrType=='2')
		        	addrExist(rec.arAddrType,isMx);
	        		return value;}}, 
	        {field:'arAddrType',title:'地址类型',width:100, formatter : function(value,rec){return formatVal(arAddrTypeList,value);}},
	        {field:'arPriority',title:'优先级',width:100, formatter : function(value,rec) {return formatVal(priorityList,value);}}, 
	        {field:'arValid',title:'是否有效',width:55,formatter : function(value, rec,foId) {
					if(value == "1"){return "有效";}else{return "无效"}
			}}, 	
	        {field:'opt',title:'<a href="javascript:void(0)" class="easyui-linkbutton" onclick="newAddr()">添加</a>',width:90,formatter : function(value, rec,foId) {
				var links = '<a href="" onclick="return editAddr('+rec.id+')">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
				links += '<a href=""  onclick="return delAddr('+rec.id+')">删除</a>';
				return links;
			}} 
	    ]]   
	});  
	$.ajax({
		type: 'POST',
		async:false,
		url: '${namespacePath}/contactpersonById?id=${contactpersons.id}',
		success: function(row){
			comboboxUtil.editComboboxByUrl('cpRelation', "${ctx}/crm/param/findAllPrType?prType=relation",row.cpRelation, 'prValue', 'prName', '150', isMx);//关系
			comboboxUtil.editComboboxByUrl('cpOccupation', "${ctx}/crm/param/findAllPrType?prType=occupation",row.cpOccupation, 'prValue', 'prName', '150', false);//职业
			comboboxUtil.editComboboxByUrl('cpPost', "${ctx}/crm/param/findAllPrType?prType=duty", row.cpPost,'prValue', 'prName', '150', isMx);//职务
			comboboxUtil.editComboboxByUrl('CpersonType', "${ctx}/crm/param/findAllPrType?prType=contactpersonType", row.cpContactpersonType,'prValue', 'prName', '150', isMx);//联系人类型
			comboboxUtil.editComboboxByUrl('cpIdtype', "${ctx}/crm/param/findAllPrType?prType=idtype",row.cpIdtype, 'prValue', 'prName', '150', false,function(record){
			    if(record.prName=='身份证'){
			    	$('#cpId').validatebox({validType:"idcard"});
				    }else{
				    	$('#cpId').validatebox({validType:""});
				 }
			});
			$('#ff').form('load',row);
		},
		dataType: 'json'
		});	
	$.ajax({
		type: 'POST',
		async:false,
		url: '${ctx}/crm/param/findParameterByPrTypeAndPrName?prType=telType&prName='+encodeURI('手机'),
		success: function(row){
			mobileValue = row.prValue;
		},
		dataType: 'json'
	});
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
function addrExist(type,isMx){
	if(isMx){
		if(type == '1' || type == '2')
			addrE = addrE+1;
		if(addrE == 0)
			$("#addrExist").val("0");
		else
			$("#addrExist").val("1");
	}else{
		$("#addrExist").val("0");
	}
}

function newTel(){
	$("#tlPH").val("0");
	$('#saveTelId').linkbutton({  
	    disabled:false
	});	
	$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话添加');
	$('#${entityName}_TelForm').form('clear');
};
function saveTel(){
	$('#${entityName}_TelForm').form('submit',{
		url: '${namespacePath}/saveTel?contactpersonid=${contactpersons.id}',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(rows){
			$('#${entityName}_Tel').dialog('close');
			$('#tel').datagrid('reload',rows);
		}
	});
	$("#tlPH").val("0");
}
function editTel(id){
	$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话编辑');
	$.post('${namespacePath}/telById', {id : id}, function(data) {
		 $("#tlPH").val(data.tlPriority);
		if(data.tlTelType == mobileValue){
			$("#tlTelTypeId").html('<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 150px;"/>');
			$('#tlTelNum').validatebox({validType:"mobile"});
		}else{
		content = '<input id="tlAreaCode" class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"/>-';
		content +='<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;"/>-';
		content +='<input id="tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"/>';
		$("#tlTelTypeId").html(content);
		$('#tlAreaCode').validatebox({validType:"numberL[3,4]"});
		$('#tlTelNum').validatebox({validType:"numberL[6,8]"});
		$('#tlExtCode').validatebox({validType:"numberL[0,6]"});
		}
		$('#${entityName}_TelForm').form('load',data);
	}, 'json');
	$('#saveTelId').linkbutton({  
	    disabled:false
	});
	return false;
}
function delTel(id){
	var i = confirm('确认删除？');
        if (i){  
        	$.post('${namespacePath}/delTel', {id : id}, function(rows) {$('#tel').datagrid('reload',rows);}, 'json');	
        }
    	$('#saveTelId').linkbutton({  
    	    disabled:false
    	});
    	$('#saveTelId').linkbutton({  
    	    disabled:false
    	});
        return false;
}
function newAddr(){
	$("#adPH").val("0");
	$('#saveAddrId').linkbutton({  
	    disabled:false
	});	
	$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址添加');
	$('#${entityName}_AddrForm').form('clear');
};
function saveAddr(){
	$('#${entityName}_AddrForm').form('submit',{
		url: '${namespacePath}/saveAddr?contactpersonid=${contactpersons.id}',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(rows){
			$("#adPH").val("0");
			$('#${entityName}_Addr').dialog('close');
			$('#addr').datagrid('reload',rows);
		}
	})
}
function editAddr(id){
	$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址编辑');
	$.post('${namespacePath}/addrById', {id : id}, function(data) {
		 $("#adPH").val(data.arPriority);
		if(data.arProvince!=null){//房产地址
			addrCity('arProvince','arCity','arCounty','arStreet','arAddrDetail',data.arProvince);//房产地址houseHeProvince
		}
		if(data.arCity!=null){
			addrCounty('arProvince','arCity','arCounty','arStreet','arAddrDetail',data.arCity);//房产地址houseHeProvince
		}
		$('#${entityName}_AddrForm').form('load',data);
	}, 'json');
	$('#saveAddrId').linkbutton({  
	    disabled:false
	});
	return false;
}
function delAddr(id){
	var i = confirm('确认删除？');
        if (i){  
        	$.post('${namespacePath}/delAddr', {id : id}, function(rows) {
        		addrE = 0;
        		$("#addrExist").val("0");
        		$('#addr').datagrid('reload',rows);}, 'json');	
        }
    	$('#saveAddrId').linkbutton({  
    	    disabled:false
    	});
        return false;
}

//民信新增，地址，电话信息记录存在
function addrTelExistMx(){
	if(isMx){
		if($("#telExist").val()!='1'){
			$.messager.show({
				title: '联系人电话',
				msg: '<font color=red>请录入至少一条联系人电话记录,号码不能为空</font>'
			});
			return true;
		}
		if($("#addrExist").val()!='1'){
			$.messager.show({
				title: '联系人家庭/单位地址',
				msg: '<font color=red>请录入至少一条联系人家庭/单位地址记录</font>'
			});
			return true;
		}
		return false;
	}
	return false;
}

function save(){
	var changesRows = {insertTel:[],insertAddr:[]};
	var jsonString = JSON.stringify(changesRows);
	$('#ff').form('submit',{
		url: '${namespacePath}/save?customerid='+${customerid}+'&jsonString='+jsonString,
		onSubmit: function(){
			return $(this).form('validate') && !addrTelExistMx();
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.result.success){
				redirect('${ctx}/crm/customer/${urlnow}/?id=${customerid}&tabs=1');	//跳转
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
	<div data-options="region:'center'" id="main">
	    <form id="ff" method="post" action="${namespacePath}/save">
	     <div class="m_title">编辑联系人信息</div>
	    <div class="m_content2" style="width:100%;">
	     <div class="m_mar">
	    <input type="hidden" name="customerid" value = "${customerid}" >
	   	<input type="hidden" name="optlock"/>
	    <input type="hidden" name="id" >
	    <input id="tlPH" type="hidden" value="0"/>
	    <input id="adPH" type="hidden" value="0"/>
	    <input id="telExist" type="hidden" value="0">
	    <input id="addrExist" type="hidden" value="0">
	    <table  class="m_table" width="100%">
 				<tr class="tr1">
			       	<td colspan="2"> 联系人基本信息</td>
			    	<td colspan="1"> <label for="kehu">客户：</label></td>
			    	<td colspan="1" align="left"> ${customerName}</td>
			    </tr>
			    <tr class="tr2">
			       	<td colspan="4"></td>
			    </tr>
		    <tr>
	    	  	<td><label for="cpName" class="bitian">姓名(中文)：</label></td>
		    	<td><input class="easyui-validatebox" type="text" name="cpName" validType="CHS" required="true"/></td>
		    	<td><label for="cpEname">姓名(英文)：</label></td>
		    	<td><input class="easyui-validatebox" name="cpEname" validType="EHS"/></td>
		    </tr>
 			<tr>
				<td><label for="cpIdtype">证件类型：</label></td>
		    	<td><input id="cpIdtype" class="easyui-combobox" type="text" name="cpIdtype"></input></td>
		    	<td><label for="cpSex" class="${isMx?'':'bitian1'}">性别：</label></td>
		    	<td>
		    		<input type="checkbox" name="cpSex" onclick="checkedThis($(this))" value="1">男
					<input type="checkbox" name="cpSex" onclick="checkedThis($(this))" value="0">女
				</td>
		    </tr>
		     <tr>
	    	  	<td><label for="cpSex">证件号码：</label></td>
		    	<td><input id="cpId" class="easyui-validatebox" type="text" name="cpId" style="width: 60%"></td>
		    	<td><label for="cpSex" class="${isMx?'bitian':''}"  style="display:${isMx?'block':'none' }">联系人类型  </label></td>
			    <td><div style="display:${isMx?'block':'none' }"><input id="CpersonType" class="easyui-combobox" name="cpContactpersonType" style="width: 60%"></div></td>
		    </tr>

  		    <tr>
		    	<td><label for="cpBirthday">出生日期 ：</label></td>
		    	<td><input type="text" class="Wdate" id="cpBirthday" name="cpBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
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
		    	<td width="10%"><label for="cpWorkunit" class="${isMx?'bitian':''}">单位名称：</label></td>
		    	<td width="40%"><input class="easyui-validatebox" type="text" name="cpWorkunit" style="width: 25%;" ${isMx?'required':''}></input></td>
		    	<td width="10%"><label for="cpOccupation">职业：</label></td>
		    	<td width="40%"><input id="cpOccupation" class="easyui-combobox" type="text" name="cpOccupation"></td>
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
		    	<td colspan="3"><input class="easyui-validatebox" type="text" name="cpEmail" style="width: 25%;" validType="email"></input></td>
		    </tr>

		</table>
		
		<table class="m_table" width="100%">
					    <tr>
		    <td width="10%"><label for="crQq" class="${isMx?'bitian':'bitian1'}">电话：</label></td>
		    	<td colspan="3">
		    	<table id="tel" class="easyui-datagrid"   width="100%">   
            </table>
		    	</td>
		    </tr>
		    <tr>
		    <td width="10%"><label  class="${isMx?'bitian':''}" for="crQq">地址：</label></td>
		    	<td colspan="3">
		    	<table id="addr" class="easyui-datagrid"  width="100%">   
            </table>
		    </tr>
		    <tr>
      			<td >
		            <label for="cpMemo">备注:</label>
					</td>
				<td colspan="3"><textarea name="cpMemo" style="height:60px;width: 40%;"></textarea></td>
		   </tr>
		
		</table>
		
		</div>
		</div>
	    </form>
  	    <div style="background:#fafafa;text-align:center;padding:5px">  
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save();">保存</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="redirect('${ctx}/crm/customer/${urlnow}/?id=${customerid}&tabs=1');">取消</a>  
        </div> 
	</div>
	

<div id="${entityName}_Tel" class="easyui-dialog" style="width:500px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsTel">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">电话添加</div>
		<form id="${entityName}_TelForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="telid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="tlTelType">电话类型:</label></td>
				<td><input id="tlTelType" class="easyui-combobox" name="tlTelType"></td>
			</tr>
			<tr>
				<td><label>电话:</label></td>
				<td id="tlTelTypeId"><input id="tlAreaCode"  class="easyui-validatebox" type="text" name="tlAreaCode" style="width: 40px;"></input>-
				<input id="tlTelNum" class="easyui-validatebox" type="text" name="tlTelNum" style="width: 100px;"></input>-
				<input id="tlExtCode" class="easyui-validatebox" type="text" name="tlExtCode" style="width: 40px;"></input></td>
			</tr>
			<tr>
				<td><label for="tlPriority">优先级:</label></td>
				<td><input id="tlPriority" class="easyui-combobox" name="tlPriority"  ></td>
			</tr>
			<tr>
				<td><label>是否有效:</label></td>
				<td><input id="tlValid" name="tlValid"  type="radio" value="1" checked="checked">有效
				<input id="tlValid" name="tlValid"   type="radio" value="0" >无效</td>
			</tr>
			</table>
		</form>
		</div>
		</div>
</div>

<div id="${entityName}_Addr" class="easyui-dialog" style="width:500px;height:300px;" closed="true" buttons="#${entityName}_dlg-buttonsAddr">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">地址添加</div>
		<form id="${entityName}_AddrForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="addrid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td><label for="arAddrType">地址类型:</label></td>
				<td><input id="arAddrType" class="easyui-combobox" name="arAddrType"></td>
			</tr>
			<tr>
				<td><label>地址:</label></td>
				<td><input id="arProvince" class="easyui-combobox" name="arProvince" style="width: 90px;">省-
				<input id="arCity" class="easyui-combobox" name="arCity" style="width: 90px;" validType="selectValidator['arCity']">市-
				<input id="arCounty" class="easyui-combobox" name="arCounty" style="width: 70px;" validType="selectValidator['arCounty']">县<input id="arStreet" type="hidden" name="arStreet" value=""></td>
			</tr>
			<tr>
				<td><label>详细地址</label></td>
				<td><input id="arAddrDetail" class="easyui-validatebox" type="text" name="arAddrDetail" style="width: 250px;" ${isMx?'required':''}></input></td>
			</tr>
			<tr>
				<td><label for="arZipCode">邮编:</label></td>
				<td><input id="arZipCode" class="easyui-validatebox" name="arZipCode"  ></td>
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
