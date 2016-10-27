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
var lxrList,telList,addrList,bankList;
var getProvinces,getArea;
var getChannelTop,getChannels;
	$(function() {
		postAjax(false, "${namespacePath}/telAllBycustomerid?id=${customers.id}", function(data) {telList = data}, 'json');
		postAjax(false, "${namespacePath}/addrAllBycustomerid?id=${customers.id}", function(data) {addrList = data}, 'json');
		
		postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
		postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');

		var tlTelTypeTypeList=new Array();
		var arAddrTypeList=new Array();
		var arProvinceList=new Array();
		var priorityList=new Array();
		var relationList=new Array();
		var prTypeNameList=["telType","addrType","priority","relation"];
			$.ajax({
			  	type: 'POST',
			  	async:false,
			  	url: '${ctx}/crm/param/findAllByPrTypeList?prTypeList='+prTypeNameList,
			  	success: function(row){
			  		tlTelTypeTypeList = row[0];
			  		arAddrTypeList = row[1];
			  		priorityList = row[2];
			  		relationList = row[3];
	  		},
				dataType: 'json'
			}); 
			
			//渠道通路选择  2014-9-10
			postAjax(false, "${ctx}/crm/channel/getChannelTopAll", function(data) {getChannelTop = data}, 'json');
			postAjax(false, "${ctx}/crm/channel/getChannelAll", function(data) {getChannels = data}, 'json');
			addrChannel('topChannel','midChannel','divChannelMid','','');
			
			addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//通讯地址
			addrProvice('cparProvince','cparCity','cparCounty','cparStreet','cparAddrDetail');//公司地址
		//	addrProvice('zjArProvince','zjArCity','zjArCounty','zjArStreet','zjArAddrDetail');//发证机关所在地
			comboboxUtil.setComboboxByUrl('crEducation', "${ctx}/crm/param/findAllPrType?prType=education", 'prValue', 'prName', '150', false);//学历
			comboboxUtil.setComboboxByUrl('crMaritalStatus', "${ctx}/crm/param/findAllPrType?prType=maritalStatus", 'prValue', 'prName', '150', false);//婚姻状况
			comboboxUtil.setComboboxByUrl('crIndustry', "${ctx}/crm/param/findAllPrType?prType=industry", 'prValue', 'prName', '150', false);//行业
			comboboxUtil.setComboboxByUrl('crDuty', "${ctx}/crm/param/findAllPrType?prType=duty", 'prValue', 'prName', '150', false);//职务
			comboboxUtil.setComboboxByUrlWithpanelHeight('crNationality', "${ctx}/crm/param/getNations", 'id', 'name', '150', false);//国籍
			comboboxUtil.setComboboxByUrl('crLanguage', "${ctx}/crm/param/findAllPrType?prType=language", 'prValue', 'prName', '150', false);//语言
			comboboxUtil.setComboboxByUrl('crSource', "${ctx}/crm/param/findAllPrType?prType=source", 'prValue', 'prName', '150', false);//客户来源
			comboboxUtil.setComboboxByUrl('crRiskLevel', "${ctx}/crm/param/findAllPrType?prType=riskLevel", 'prValue', 'prName', '150', false);//客户风险等级
			comboboxUtil.setComboboxByUrl('cpRelation', "${ctx}/crm/param/findAllPrType?prType=relation", 'prValue', 'prName', '150', false);//客户关系
			comboboxUtil.setComboboxByUrlWithpanelHeight('crCityId', "${ctx}/crm/param/findAllByPrType?prType=OrganFortune", 'prValue', 'prName', '150', false);//地区

			//comboboxUtil.setComboboxByUrlWithpanelHeight('crGather', "${ctx}/crm/param/getStaffList", 'id', 'name', '150', false);//获取客户经理列表
			//comboboxUtil.setComboboxByUrlWithpanelHeight('crDeptManager', "${ctx}/crm/param/getStaffList2", 'id', 'name', '150', false);//获取部门经理列表
		$(".m_table td").has("label").css("text-align","right");
		
		$('#fflayout').layout('collapse','east'); 
		
		$.post('${namespacePath}/edit_lcById', {id : ${customers.id}}, function(row) {
			/* if(row.customer_crGather!=null){
				postAjax(false, "${ctx}/crm/param/getStaffName?staffId="+row.customer_crGather, function(data) {row.customer_crGather=data[0];}, 'json');
			}
			if(row.customer_crDeptManager!=null){
				postAjax(false, "${ctx}/crm/param/getStaffName?staffId="+row.customer_crDeptManager, function(data) {row.customer_crDeptManager=data[0];}, 'json');
			} */
			if(row.addr_arProvince!=null){
				addrCity('arProvince','arCity','arCounty','arStreet','arAddrDetail',row.addr_arProvince);//通讯地址
			}
			if(row.addr_arCity!=null){
				addrCounty('arProvince','arCity','arCounty','arStreet','arAddrDetail',row.addr_arCity);//通讯地址
			}
			if(row.cpaddr_arProvince!=null){
				addrCity('cparProvince','cparCity','cparCounty','cparStreet','cparAddrDetail',row.cpaddr_arProvince);//单位地址
			}
			if(row.cpaddr_arCity!=null){
				addrCounty('cparProvince','cparCity','cparCounty','cparStreet','cparAddrDetail',row.cpaddr_arCity);//单位地址
			}
/* 			if(row.zj_arProvince!=null){
				addrCity('zjArProvince','zjArCity','zjArCounty','zjArStreet','zjArAddrDetail',row.zj_arProvince);//单位地址
			}
			if(row.zj_arCity!=null){
				addrCounty('zjArProvince','zjArCity','zjArCounty','zjArStreet','zjArAddrDetail',row.zj_arCity);//单位地址
			} */
		    var date = new Date(row.customer_crIssueDate);
			// row.customer_crIssueDate = row.customer_crIssueDate?date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate():'';
			var date = new Date(row.customer_crExpiryDate);
			// row.customer_crExpiryDate = row.customer_crExpiryDate?date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate():'';
			if(row.customer_crExpiryDate == '2099-12-31'){
				row.customer_crExpiryDate = '';
				row.forever_crExpiryDate = 0;
				$('#customer_crExpiryDate').hide();
				}
			//加载渠道通路
			if(row.top_channel!=null && row.mid_channel!=null ){
				$('#divChannelMid').show();
				addrChanVal('topChannel','midChannel','divChannelMid',row.top_channel);
			}
			//加载偏好信息
			if(row.customer_hobbies!=null){
				initHobbies(row.customer_hobbies);
			}
			$('#ff').form('load',row);
		}, 'json');		

		$('#lxr').datagrid({   
		    url:'${namespacePath}/lxr?id=${customers.id}',   
		    rownumbers:true,
		    fitColumns:true,
			sortName : 'id',
			sortOrder : 'asc',
		    columns:[[   
		        {field:'cpName',title:'联系人姓名',width:fixWidth(0.05),formatter : function(value, rec) {return '<a href="${ctx}/crm/contactperson/show?id='+rec.id+'&customerid=${customers.id}&urlnow=${urlnow}" >'+value+'</a>';}},   
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
		    url:'${namespacePath}/tel?id=${customers.id}',  
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
		    url:'${namespacePath}/bankN?id=${customers.id}',   
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
			            postAjax(false,'${ctx}/crm/param/findAllByPrType?prType=accountType',function(row){prName123 = formatVal(row,value);},'json');
		            	return prName123;
		            }}, 
		        {field:'baBankName',title:'银行名称',width:fixWidth(0.05)}, 
		        {field:'baBranchName',title:'支行名称',width:fixWidth(0.05)}, 
		        {field:'baValid',title:'是否有效',width:fixWidth(0.05),formatter : function(value, rec,foId) {
					if(value == "1"){return "有效";}else{return "无效"}
				}}
		    ]]   
		});  
		
		$('#addr').datagrid({   
		    url:'${namespacePath}/addr?id=${customers.id}',  
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
	}); 
</script>
<body class="easyui-layout" id="fflayout">
	<div data-options="region:'center'" id="main">
		<div id="tt" class="easyui-tabs" style="background:#fafafa;" data-options="fit:true">  
   			<div title="客户信息">  
				<form id="ff" method="post">
				<input type="hidden" name="customer_id">
				<div class="m_content2" style="width:100%;">
	    		<div class="m_mar">
				<table class="m_table" style="width:100%;">
		    	<tr class="tr1">
		       		<td colspan="8">个人资料	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户编号:${customers.crCustomerNumber}
		       			<input type="hidden" name="customer_id"><input type="hidden" name="customer_optlock"/>
		       			</td>
		        </tr>
		        <tr class="tr2">
		       		<td colspan="8"></td>

				<tr>
					<td><label for="crName" class="bitian">姓名（中文）</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crName" disabled="disabled"/></td>
					<td><label for="crEname">姓名（英文）</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crEname" disabled="disabled"/></td>
					<td><label for="crSex" class="bitian1">性别</label></td>
					<td><input type="checkbox" name="customer_crSex" onclick="checkedThis($(this))" value="1" disabled="disabled">男
						<input type="checkbox" name="customer_crSex" onclick="checkedThis($(this))" value="0" disabled="disabled">女
					</td>
					<td><label for="crBirthday">出生日期</label></td>
					<td><input type="text" class="Wdate" name="customer_crBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  disabled="disabled"/></td>
				</tr>
				<tr>
					<td><label for="crNationality" class="bitian1">国籍</label></td>
					<td><input id="crNationality"  type="text" name="customer_crNationality" disabled="disabled"/></td>
					<td><label for="crLanguage">使用语言</label></td>
					<td><input id="crLanguage"  type="text" name="customer_crLanguage" disabled="disabled"/></td>
					<td><label for="crMother">母亲姓名</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crMother" disabled="disabled"/></td>
					<td><label for="crMaritalStatus">婚姻状况</label></td>
					<td><input id="crMaritalStatus"  name="customer_crMaritalStatus" disabled="disabled"></td>
				</tr>
				<tr>
					<td><label for="crIdtype" class="bitian1">证件类型 </label></td>
					<td colspan="7">
						<c:forEach items="${crIdtypes}" var="crIdtypes">
							<input type="checkbox" name="customer_crIdtype" onclick="checkedThis($(this))" onBlur="idyz(this)" value="${crIdtypes.prValue}" disabled="disabled">&nbsp;${crIdtypes.prName}&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td><label for="crIdnum" class="bitian1">证件号码</label></td>
					<td colspan="7"><input id="crIdnum" class="easyui-validatebox" type="text" name="customer_crIdnum" style="width: 400px" disabled="disabled"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crIssueDate" >签发日期</label></td>
					<td nowrap="nowrap"><input type="text" class="Wdate" name="customer_crIssueDate" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" disabled="disabled"/></td>
					<td nowrap="nowrap"><label for="crExpiryDate" >失效日期</label></td>
					<td nowrap="nowrap"><input id="customer_crExpiryDate" type="text" class="Wdate" name="customer_crExpiryDate" onfocus="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})" disabled="disabled"/>
							<input type="checkbox" id = "forever_crExpiryDate" name = "forever_crExpiryDate" onclick="foreverCrExpiryDate(this);" value="0" disabled="disabled">长期</td>
					<td nowrap="nowrap"><label for="arProvince" >发证机关所在地</label></td>
					<td colspan="3" nowrap="nowrap">
						<input type="hidden" name="zj_id">
<!-- 						<input id="zjArProvince"  name="zj_arProvince" style="width: 80px" disabled="disabled">省
						<input id="zjArCity"  name="zj_arCity" style="width: 80px" disabled="disabled">市
						<input id="zjArCounty"  name="zj_arCounty" style="width: 80px" disabled="disabled">县 
						<input id="zjArStreet" class="easyui-validatebox" name="zj_arStreet" maxLength="20" disabled="disabled"> -->
						<input id="zjArAddrDetail" class="easyui-validatebox" name="zj_arAddrDetail" maxLength="66" style="width: 250px" disabled="disabled">
						<!-- <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" onchange="addrbuquan();" maxLength="20"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td><label for="crOccupation" >职业 </label></td>
					<td colspan="5">
						<c:forEach items="${crOccupations}" var="crOccupations">
							<input type="checkbox" name="customer_crOccupation" onclick="checkedThis($(this))" value="${crOccupations.prValue}" disabled="disabled">&nbsp;${crOccupations.prName}&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
					<td><label for="crEducation">学历</label></td>
					<td><input id="crEducation"  name="customer_crEducation" disabled="disabled"></td>
				</tr>
				<tr>
					<td><label for="crIndustry">行业</label></td>
					<td><input id="crIndustry"  name="customer_crIndustry" disabled="disabled"></td>
					<td><label for="crCompany">单位名称</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crCompany" disabled="disabled"/></td>
					<td><label for="crWorkYears">工作年限</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crWorkYears"  disabled="disabled"/></td>
					<td><label for="crDuty">职务</label></td>
					<td><input id="crDuty"  name="customer_crDuty" disabled="disabled"></td>
				</tr>
				<tr>
					<td><label for="crCompanySize">单位规模</label></td>
					<td colspan="7">						
						<c:forEach items="${crCompanySizes}" var="crCompanySizes">
							<input type="checkbox" name="customer_crCompanySize" onclick="checkedThis($(this))" value="${crCompanySizes.prValue}" disabled="disabled">&nbsp;${crCompanySizes.prName}&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td><label for="tlTelNum">固定电话</label></td>
					<td nowrap="nowrap">
						<input class="easyui-validatebox" type="text" name="tel_tlAreaCode" style="width: 40px;"   disabled="disabled"/>-
						<input class="easyui-validatebox" type="text" name="tel_tlTelNum" style="width: 100px;"   disabled="disabled"/>-
						<input class="easyui-validatebox" type="text" name="tel_tlExtCode" style="width: 40px;"   disabled="disabled"/></td>
					<td><label for="tlTelNum" class="bitian">移动电话</label></td>
					<td><input id="tlTelNum" class="easyui-validatebox" name="mobile_tlTelNum"   required="true" disabled="disabled" ></td>

					<td><label for="crEmail">E-Mail</label></td>
					<td><input class="easyui-validatebox" type="text" name="customer_crEmail" disabled="disabled"/></td>
						<td></td>
						<td></td>
				</tr>
				<tr>
					<td rowspan="2"><label for="arProvince" class="bitian1">通讯地址</label></td>
					<td colspan="7" nowrap="nowrap">中国
						<input id="arProvince" class="easyui-combobox" name="addr_arProvince" style="width: 120px" disabled="disabled">
						<input id="arCity" class="easyui-combobox" name="addr_arCity" style="width: 120px" disabled="disabled" >
						<input id="arCounty" class="easyui-combobox" name="addr_arCounty" style="width: 120px"  disabled="disabled">
						 <!-- <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" onchange="addrbuquan();"  disabled="disabled"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td colspan="5"><input id="arAddrDetail" class="easyui-validatebox" name="addr_arAddrDetail" style="width: 400px"  disabled="disabled"></td>
					<td><label for="arZipCode" class="bitian1">邮编</label></td>
					<td><input class="easyui-validatebox" type="text" name="addr_arZipCode"   disabled="disabled"/></td>
				</tr>
				<tr class="tr1">
		       		<td colspan="8">紧急联系人资料<input type="hidden" name="cp_id"><input type="hidden" name="cp_optlock"/></td>
		        </tr>
		        <tr class="tr2">
		       		<td colspan="8"></td>
			    </tr>
				<tr>
					<td><label for="cpName" class="bitian1">姓名（中文）</label></td>
					<td><input class="easyui-validatebox" type="text" name="cp_cpName"  disabled="disabled"/></td>
					<td><label for="cpEname">姓名（英文）</label></td>
					<td><input class="easyui-validatebox" type="text" name="cp_cpEname"  disabled="disabled"/></td>
					<td><label for="cpSex" class="bitian1">性别</label></td>
					<td><input type="checkbox" name="cp_cpSex" onclick="checkedThis($(this))" value="1"  disabled="disabled">男&nbsp;&nbsp;
						<input type="checkbox" name="cp_cpSex" onclick="checkedThis($(this))" value="0" disabled="disabled" >女</td>
					<td><label for="cpBirthday">出生日期 </label></td>
					<td><input type="text" class="Wdate" name="cp_cpBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  disabled="disabled" /></td>
				</tr>
				<tr>
					<td><label for="cpIdtype">证件类型 </label></td>
					<td colspan="7">			
						<c:forEach items="${crIdtypes}" var="crIdtypes">
							<input type="checkbox" name="cp_cpIdtype" onclick="checkedThis($(this))" onBlur="idyz2(this)" value="${crIdtypes.prValue}"  disabled="disabled">${crIdtypes.prName}&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td><label for="cpId">证件号码</label></td>
					<td colspan="7"><input id="cpId" class="easyui-validatebox" type="text" name="cp_cpId" style="width: 400px"  disabled="disabled"/></td>
				</tr>
				<tr>
					<td><label for="tlTelNum" class="bitian1">移动电话</label></td>
					<td><input id="tlTelNum" class="easyui-validatebox" name="cpmobile_tlTelNum"  disabled="disabled"/></td>
					<td><label for="tlTelNum">固定电话</label></td>
					<td nowrap="nowrap">
						<input class="easyui-validatebox" type="text" name="cptel_tlAreaCode" style="width: 40px;"   disabled="disabled"/>-
						<input class="easyui-validatebox" type="text" name="cptel_tlTelNum" style="width: 100px;"   disabled="disabled"/>-
						<input class="easyui-validatebox" type="text" name="cptel_tlExtCode" style="width: 40px;"   disabled="disabled"/></td>
					<td><label for="cpEmail">E-Mail</label></td>
					<td><input class="easyui-validatebox" type="text" name="cp_cpEmail"  disabled="disabled"/></td>
					<td nowrap="nowrap"><label for="cpRelation" class="bitian1">与申请人关系</label></td>
					<td nowrap="nowrap"><input id="cpRelation"  name="cp_cpRelation" disabled="disabled"></td> 
				</tr>
				<tr>
					<td rowspan="2"><label for="arProvince">通讯地址</label></td>
					<td colspan="7" nowrap="nowrap">中国
						<input id="cparProvince" class="easyui-combobox" name="cpaddr_arProvince" style="width: 120px"  disabled="disabled">
						<input id="cparCity" class="easyui-combobox" name="cpaddr_arCity" style="width: 120px"  disabled="disabled">
						<input id="cparCounty" class="easyui-combobox" name="cpaddr_arCounty" style="width: 120px"  disabled="disabled"> 
					<!-- 	<input id="cparStreet" class="easyui-validatebox" name="cpaddr_arStreet" onchange="cpaddrbuquan();"  disabled="disabled"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td colspan="5"><input id="cparAddrDetail" class="easyui-validatebox" name="cpaddr_arAddrDetail" style="width: 400px"  disabled="disabled" ></td>
					<td><label for="cpaddr_arZipCode">邮编</label></td>
					<td><input class="easyui-validatebox" type="text" name="cpaddr_arZipCode"    disabled="disabled"/></td>
				</tr>
				<tr>
					<td colspan="2"><label class="bitian1">您希望通过哪种方式接收文件？</label></td>
					<td colspan="4" nowrap="nowrap">
						<c:forEach items="${fileReceives}" var="fileReceives">
							<input type="checkbox" name="customer_crFileAccept" onclick="checkedThis($(this))" value="${fileReceives.prValue}" disabled="disabled">${fileReceives.prName}&nbsp;&nbsp;
						</c:forEach>
					</td>
					<td nowrap="nowrap"><label for="customer_crCityId" class="bitian">分公司所属地区</label></td>
					<td nowrap="nowrap">
						<input id="crCityId"  name="customer_crCityId" style="width: 80px;" disabled="disabled"/>市
					</td>
				</tr>
				<tr>
					<td><label for="crGather" class="bitian">客户经理</label></td>
					<td><input id="crGather" class="easyui-validatebox" type="text" name="customer_crGather" disabled="disabled"/></td>
					<td><label for="crSource" class="bitian1">信息来源</label></td>
					<td><input id="crSource"  type="text" name="customer_crSource" disabled="disabled"/></td>
					<td><label for="crGatherDate">采集时间</label></td>
					<td><input type="text" class="Wdate" name="customer_crGatherDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" disabled="disabled"/></td>
					<td nowrap="nowrap"><label for="crDeptManager" class="bitian">直属主管</label></td>
					<td nowrap="nowrap"><input id="crDeptManager" class="easyui-validatebox" type="text" name="customer_crDeptManager" disabled="disabled"/></td>
				</tr>
					<tr>
						<td nowrap="nowrap" ><label for="topChannel" >渠道通路</label></td>
						<td nowrap="nowrap" >
						<div nowrap="nowrap">  
						<div nowrap="nowrap"  style ="float:left">
							<input id="topChannel" class="easyui-combobox" name="top_channel" style="width: 120px" disabled="disabled" >  </div>
						<div id="divChannelMid" nowrap="nowrap" style="display:none; margin-left:10px" >
							 &nbsp;&nbsp; <input id="midChannel" class="easyui-combobox"  name="mid_channel" style="width: 120px " disabled="disabled"  validType="selectValidator['midChannel']"></div>
						</div>
						</td>
						<td nowrap="nowrap"><label for="crGather" >客户申请书编号</label></td>
						<td colspan="3" nowrap="nowrap"><input class="easyui-validatebox" type="text" id="crApplicationNumber" name="customer_crApplicationNumber"  
						    validType="crApplicationNo" style="width: 200px" maxLength="20" disabled="disabled"/></td>
						    <td nowrap="nowrap"><label for="crRiskLevel" >客户风险等级</label></td>
						<td  nowrap="nowrap"><input type="text" id="crRiskLevel" name="customer_crRiskLevel"  style="width: 200px" maxLength="20"  disabled="disabled"/></td>
					</tr>
			  <tr class="tr1">
			       		<td colspan="8">附加属性</td>
			       </tr>
			       <tr class="tr2">
			       		<td colspan="8"></td>
			       </tr>
			       <tr>
			        <td nowrap="nowrap"><label for="crHobby">客户偏好</label>
			        </td>
			        <td nowrap="nowrap" colspan="7">
			        <c:forEach items="${hobbies}" var="hobbies">
			            &nbsp;<input type="checkbox" name="customer_hobby" value="${hobbies.prValue}" disabled="disabled">${hobbies.prName}&nbsp;&nbsp;&nbsp;
					</c:forEach>
					</td>
			       </tr>
				<tr>
					<td style="height: 25px;border: 0" ></td>
				</tr>
					</table>
					</div>
					</div>
				</form>
				<div class="m_btn_box" style="width:100%;">
				<div id="formButtom" style="width:100%;text-align:center;background:/* #fafafa */;border:0px solid #8DB2E3;position:absolute;bottom:16px;/* right:40%; */">
					<a href="#" onclick="closeCurPage('客户信息查看')" class="easyui-linkbutton">关闭</a>
					</div>
				</div> 
			   </div> 
    
			<div title="附属信息" style="padding:20px;">  
	        <table width="100%">                                              
	       		<tr><td align="left">联系人信息</td><td align="right"></td></tr>
	       		<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="lxr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">电话信息</td><td align="right"></td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="tel" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">地址信息</td><td align="right"></td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="addr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">银行信息</td><td align="right"></td></tr>
	        	<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="bank" width="100%"></table></td></tr>
	        </table>
	   		</div>  
    	</div>
	</div>
	<div data-options="region:'east',title:'操作'" style="width: 80px; padding:5px;">
	</div>
</body>





