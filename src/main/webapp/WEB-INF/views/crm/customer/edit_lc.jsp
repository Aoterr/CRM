<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
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
<style type="text/css">        
#myemail, .newemail{
	cursor:default;
	line-height:18px;
}
</style>
<script type="text/javascript">
var lxrList,telList,addrList,bankList;
var getProvinces,getArea;
var getChannelTop,getChannels;
	$(function() {
		
		if(${tabs}=='1')
			$(".easyui-tabs").tabs("select",1);
		

		postAjax(false, "${namespacePath}/telAllBycustomerid?id=${customers.id}", function(data) {telList = data}, 'json');
		postAjax(false, "${namespacePath}/addrAllBycustomerid?id=${customers.id}", function(data) {addrList = data}, 'json');

		postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
		postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
		addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//通讯地址
		addrProvice('cparProvince','cparCity','cparCounty','cparStreet','cparAddrDetail');//公司地址
		addrProvice('addrdata_arProvince','addrdata_arCity','addrdata_arCounty','addrdata_arStreet','addrdata_arAddrDetail');//客户地址编辑	
			
		//渠道通路选择  2014-9-10
		postAjax(false, "${ctx}/crm/channel/getChannelTopAll", function(data) {getChannelTop = data}, 'json');
		postAjax(false, "${ctx}/crm/channel/getChannelAll", function(data) {getChannels = data}, 'json');
		addrChannel('topChannel','midChannel','divChannelMid','','');
				
		/* comboboxUtil.setComboboxByUrlWithpanelHeight('crGather', "${ctx}/crm/param/getStaffList", 'id', 'name', '150', true,function(record){
			comboboxUtil.setComboboxByUrlWithpanelHeight('crDeptManager', "${ctx}/crm/param/getStaffList2?id="+record.id, 'id', 'name', '150', false,function(){},function(data){
		    	//$('#crDeptManager').combobox('select',data[0].id);
		     });//获取部门经理列表		
		},function(record){
			$('#crGather').combobox('select',record[0].id);
		});//获取客户经理列表 */

		//附属信息
		comboboxUtil.setComboboxByUrlWithpanelHeight('baBankName', "${ctx}/crm/param/findAllByPrType?prType=bank", 'prValue', 'prName', '150', true);//银行名称
		comboboxUtil.setComboboxByUrl('baAccountType', "${ctx}/crm/param/findAllByPrType?prType=accountType", 'prValue', 'prName', '150', true,function(newValue,oldValue){
	    	if(newValue.prValue== <%= ParamConstant.BANKECARD%>){
	    		 $('#baAccount').validatebox({validType:"bankAccount"});
		    	}else{
		    		$('#baAccount').validatebox({validType:""});
			    	} 
	    });//账户类型
	    
		comboboxUtil.setComboboxByUrl('teldata_tlPriority', "${ctx}/crm/param/findAllByPrType?prType=priority", 'prValue', 'prName', '150', true,function(value){
			if(value.prValue==<%= ParamConstant.priorityHighNum%>){
				var i = confirm('如果选择高优先级，则原最高优先级会自动降为中优先级，是否确认？');
	            if (!i){
	            	$('#teldata_tlPriority').combobox('clear');
	            }				
			}
		});//电话优先级
		comboboxUtil.setComboboxByUrl('addrdata_arPriority', "${ctx}/crm/param/findAllByPrType?prType=priority", 'prValue', 'prName', '150', true,function(value){
			if(value.prValue==<%= ParamConstant.priorityHighNum%>){
				var i = confirm('如果选择高优先级，则原最高优先级会自动降为中优先级，是否确认？');
	            if (!i){  
	            	$('#teldata_tlPriority').combobox('clear');
	            }			   
			}
		});//地址优先级
		
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
		comboboxUtil.setComboboxByData('addrdata_arAddrType',arAddrTypeList, 'prValue', 'prName', '150', true,function(record){
			if(record.prValue == <%= ParamConstant.ADDZJ%>){
				$("#addrdata_arAddrTypeId").hide();
			}else{
				$("#addrdata_arAddrTypeId").show();
			}
		});//地址类型


		$(".m_table td").has("label").css("text-align","right");
		$('#fflayout').layout('collapse','east'); 
		$.post('${namespacePath}/edit_lcById', {id : ${customers.id}}, function(row) {
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
			/* if(row.customer_crGather!=null){
				comboboxUtil.setComboboxByUrlWithpanelHeight('crDeptManager', "${ctx}/crm/param/getStaffList2?id="+row.customer_crGather, 'id', 'name', '150', false);//获取部门经理列表
				} */
		    if(row.customer_crIdtype==<%= ParamConstant.IDTYPESF%>){
		    	$('#crIdnum').validatebox({validType:"idcard"});
			    }else{
			    	$('#crIdnum').validatebox({validType:""});
			 }
		    if(row.cp_cpIdtype==<%= ParamConstant.IDTYPESF%>){
		    	$('#cpId').validatebox({validType:"idcard"});
			    }else{
			    	$('#cpId').validatebox({validType:""});
			 }
		  
		    var date = new Date(row.customer_crIssueDate);

		//	row.customer_crIssueDate = row.customer_crIssueDate?date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate():'';

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
			$('#crName').focus();
			//客户界面
			comboboxUtil.editComboboxByUrl('crSource', "${ctx}/crm/param/findAllPrType?prType=source",row.customer_crSource, 'prValue', 'prName', '150', false);//客户来源
			comboboxUtil.editComboboxByUrl('crRiskLevel', "${ctx}/crm/param/findAllPrType?prType=riskLevel",row.customer_crRiskLevel, 'prValue', 'prName', '150', false);//客户风险等级
			comboboxUtil.editComboboxByUrl('crEducation', "${ctx}/crm/param/findAllPrType?prType=education",row.customer_crEducation, 'prValue', 'prName', '150', false);//学历
			comboboxUtil.editComboboxByUrl('crMaritalStatus', "${ctx}/crm/param/findAllPrType?prType=maritalStatus",row.customer_crMaritalStatus, 'prValue', 'prName', '150', false);//婚姻状况
			comboboxUtil.editComboboxByUrl('crIndustry', "${ctx}/crm/param/findAllPrType?prType=industry",row.customer_crIndustry, 'prValue', 'prName', '150', false);//行业
			comboboxUtil.editComboboxByUrl('crDuty', "${ctx}/crm/param/findAllPrType?prType=duty",row.customer_crDuty, 'prValue', 'prName', '150', false);//职务
			comboboxUtil.setComboboxByUrlWithpanelHeight('crNationality', "${ctx}/crm/param/getNations",'id', 'name', '150', false);//国籍
			comboboxUtil.editComboboxByUrl('crLanguage', "${ctx}/crm/param/findAllPrType?prType=language",row.customer_crLanguage, 'prValue', 'prName', '150', false);//语言
			comboboxUtil.editComboboxByUrl('cpRelation',"${ctx}/crm/param/findAllPrType?prType=relation", row.cp_cpRelation,'prValue', 'prName', '150', false);//客户关系
			comboboxUtil.editComboboxByUrl('crCityId', "${ctx}/crm/param/findAllByPrType?prType=OrganFortune",row.customer_crCityId, 'prValue', 'prName', '150', true);//地区

		}, 'json');	
		
		
	});

</script>
<body class="easyui-layout"  id="fflayout">
	<div data-options="region:'center'" id="mian">
		<div id="tt" class="easyui-tabs" style="background:#fafafa;" data-options="fit:true">  
   			<div title="客户信息" id="man">  
				<form id="ff" method="post">
				<div class="m_content2" style="width:100%;">
	    		<div class="m_mar">
				<table class="m_table" style="width:100%;">
		    	<tr class="tr1">
		       		<td colspan="8">个人资料	<input id="customer_id" type="hidden" name="customer_id"><input type="hidden" name="customer_optlock"/></td>
		        </tr>
		        <tr class="tr2">
		       		<td colspan="8"></td>
			    </tr>
				<tr>
					<td nowrap="nowrap"><label for="crName" class="bitian">姓名（中文）</label></td>
					<td nowrap="nowrap"><input id="crName" class="easyui-validatebox" type="text" name="customer_crName" required="true" maxLength="33"/></td>
					<td nowrap="nowrap"><label for="crEname">姓名（英文）</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crEname" maxLength="20"/></td>
					<td nowrap="nowrap"><label for="crSex" class="bitian1">性别</label></td>
					<td nowrap="nowrap"><input type="checkbox" name="customer_crSex" onclick="checkedThis($(this))" value="1">男
						<input type="checkbox" name="customer_crSex" onclick="checkedThis($(this))" value="0">女
					</td>
					<td nowrap="nowrap"><label for="crBirthday">出生日期</label></td>
					<td nowrap="nowrap"><input id="crBirthday" type="text" class="Wdate" name="customer_crBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crNationality" class="bitian1">国籍</label></td>
					<td nowrap="nowrap"><input id="crNationality"  type="text" name="customer_crNationality"/></td>
					<td nowrap="nowrap"><label for="crLanguage">使用语言</label></td>
					<td nowrap="nowrap"><input id="crLanguage"  type="text" name="customer_crLanguage"/></td>
					<td nowrap="nowrap"><label for="crMother">母亲姓名</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crMother" maxLength="20"/></td>
					<td nowrap="nowrap"><label for="crMaritalStatus">婚姻状况</label></td>
					<td nowrap="nowrap"><input id="crMaritalStatus"  name="customer_crMaritalStatus"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crIdtype" class="bitian1">证件类型 </label></td>
					<td colspan="7">
						<c:forEach items="${crIdtypes}" var="crIdtypes">
							<input  type="${crIdtypes.prState=='1' || crIdtypes.prValue eq customers.crIdtype?'checkbox':'hidden'}" 
							name="${crIdtypes.prState=='1' || crIdtypes.prValue eq customers.crIdtype?'customer_crIdtype':''}" onclick="checkedThis($(this));idyz(this)" value="${crIdtypes.prValue}">
							&nbsp;${crIdtypes.prState=='1' || crIdtypes.prValue eq customers.crIdtype?crIdtypes.prName:''}&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crIdnum" class="bitian1">证件号码</label></td>
					<td nowrap="nowrap" colspan="7"><input id="crIdnum" class="easyui-validatebox" type="text" name="customer_crIdnum" maxlength="20" style="width: 400px" onBlur="yanzhengIdnum($(this))"></input></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crIssueDate">签发日期</label></td>
					<td nowrap="nowrap"><input type="text" class="Wdate" name="customer_crIssueDate" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
					<td nowrap="nowrap"><label for="crExpiryDate">失效日期</label></td>
					<td nowrap="nowrap"><input id="customer_crExpiryDate" type="text" class="Wdate" name="customer_crExpiryDate" onfocus="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})"/>
							<input type="checkbox" id = "forever_crExpiryDate" name = "forever_crExpiryDate" onclick="foreverCrExpiryDate(this);" value="0">长期
							</td>
					<td nowrap="nowrap"><label for="arProvince">发证机关所在地</label></td>
					<td colspan="3" nowrap="nowrap">
						<input type="hidden" name="zj_id">
				<!-- 		<input id="zjArProvince"  name="zj_arProvince" style="width: 80px">省
						<input id="zjArCity"  name="zj_arCity" style="width: 80px">市
						<input id="zjArCounty"  name="zj_arCounty" style="width: 80px">县 
						<input id="zjArStreet" class="easyui-validatebox" name="zj_arStreet" maxLength="20"> -->
						<input id="zjArAddrDetail" class="easyui-validatebox" name="zj_arAddrDetail" style="width: 250px" maxLength="66">
						<!-- <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" onchange="addrbuquan();" maxLength="20"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crOccupation">职业 </label></td>
					<td colspan="5">
						<c:forEach items="${crOccupations}" var="crOccupations">
							<input type="${crOccupations.prState=='1' || crOccupations.prValue eq customers.crOccupation?'checkbox':'hidden'}" 
							name="${crOccupations.prState=='1' || crOccupations.prValue eq customers.crOccupation?'customer_crOccupation':''}" onclick="checkedThis($(this))" value="${crOccupations.prValue}">
							&nbsp;${crOccupations.prState=='1' || crOccupations.prValue eq customers.crOccupation?crOccupations.prName:''}&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
					<td nowrap="nowrap"><label for="crEducation">学历</label></td>
					<td nowrap="nowrap"><input id="crEducation"  name="customer_crEducation"></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crIndustry">行业</label></td>
					<td nowrap="nowrap"><input id="crIndustry"  name="customer_crIndustry"></td>
					<td nowrap="nowrap"><label for="crCompany">单位名称</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crCompany" maxLength="30"/></td>
					<td nowrap="nowrap"><label for="crWorkYears">工作年限</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crWorkYears" validType="validnum[50]"/></td>
					<td nowrap="nowrap"><label for="crDuty">职务</label></td>
					<td nowrap="nowrap"><input id="crDuty"  name="customer_crDuty"></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crCompanySize">单位规模</label></td>
					<td colspan="7">						
						<c:forEach items="${crCompanySizes}" var="crCompanySizes">
							<input type="${crCompanySizes.prState=='1' || crCompanySizes.prValue eq customers.crCompanySize?'checkbox':'hidden'}"
							 name="${crCompanySizes.prState=='1' || crCompanySizes.prValue eq customers.crCompanySize?'customer_crCompanySize':''}" onclick="checkedThis($(this))" name="customer_crCompanySize" onclick="checkedThis($(this))" 
							 value="${crCompanySizes.prValue}">&nbsp;${crCompanySizes.prState=='1' || crCompanySizes.prValue eq customers.crCompanySize?crCompanySizes.prName:''}&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="tlTelNum">固定电话</label></td>
					<td nowrap="nowrap">
						<input type="hidden" name="tel_id">
						<input class="easyui-validatebox" type="text" name="tel_tlAreaCode" style="width: 40px;" validType="numberL[3,4]"/>-
						<input class="easyui-validatebox" type="text" name="tel_tlTelNum" style="width: 100px;" validType="numberL[6,8]"/>-
						<input class="easyui-validatebox" type="text" name="tel_tlExtCode" style="width: 40px;" validType="numberL[0,6]"/></td>
					<td nowrap="nowrap"><label for="tlTelNum" class="bitian">移动电话</label></td>
					<td nowrap="nowrap"><input type="hidden" name="mobile_id">
						<input id="tlTelNum" class="easyui-validatebox" name="mobile_tlTelNum" validType="mobile"  required="true"></td>
					<td nowrap="nowrap"><label for="crEmail">E-Mail</label></td>
					<td nowrap="nowrap"><input id="crEmail" class="easyui-validatebox" type="text" name="customer_crEmail" validType="email" maxLength="50" onblur="yanzhengEmail()" onfocus="emails('crEmail','man')"/></td>
						<td></td>
						<td></td>
				</tr>
				<tr>
					<td nowrap="nowrap" rowspan="2"><label for="arProvince" class="bitian1">通讯地址</label></td>
					<td colspan="7" nowrap="nowrap">中国
						<input type="hidden" name="addr_id">
						<input id="arProvince" class="easyui-combobox" name="addr_arProvince" style="width: 120px">省
						<input id="arCity" class="easyui-combobox" name="addr_arCity" style="width:120px" validType="selectValidator['arCity']">市
						<input id="arCounty" class="easyui-combobox" name="addr_arCounty" style="width: 100px" validType="selectValidator['arCounty']">县
						<!--  <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" maxLength="20"> 街/路 -->
						 <!-- <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" onchange="addrbuquan();" maxLength="20"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap" colspan="5">
						<input id="arAddrDetail" class="easyui-validatebox" name="addr_arAddrDetail" style="width: 400px" maxLength="66"></td>
					<td nowrap="nowrap"><label for="arZipCode" class="bitian1">邮编</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="addr_arZipCode" validType="ZIP"/></td>
				</tr>
				<tr class="tr1">
		       		<td colspan="8">紧急联系人资料<input type="hidden" name="cp_id"><input type="hidden" name="cp_optlock"/></td>
		        </tr>
		        <tr class="tr2">
		       		<td colspan="8"></td>
			    </tr>
				<tr>
					<td nowrap="nowrap"><label for="cpName" class="bitian1">姓名（中文）</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="cp_cpName" maxLength="33"/></td>
					<td nowrap="nowrap"><label for="cpEname">姓名（英文）</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="cp_cpEname" validType="EHS" maxLength="20"/></td>
					<td nowrap="nowrap"><label for="cpSex" class="bitian1">性别</label></td>
					<td nowrap="nowrap"><input type="checkbox" name="cp_cpSex" onclick="checkedThis($(this))" value="1">男&nbsp;&nbsp;
						<input type="checkbox" name="cp_cpSex" onclick="checkedThis($(this))" value="0">女</td>
					<td nowrap="nowrap"><label for="cpBirthday">出生日期 </label></td>
					<td nowrap="nowrap"><input type="text" class="Wdate" name="cp_cpBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="cpIdtype">证件类型 </label></td>
					<td colspan="7">			
						<c:forEach items="${crIdtypes}" var="crIdtypes">
							<input type="${crIdtypes.prState=='1' || crIdtypes.prValue eq cp.cpIdtype?'checkbox':'hidden'}" 
							name="${crIdtypes.prState=='1' || crIdtypes.prValue eq cp.cpIdtype?'cp_cpIdtype':''}" onclick="checkedThis($(this))" onBlur="idyz2(this)" value="${crIdtypes.prValue}">
							${crIdtypes.prState=='1' || crIdtypes.prValue eq cp.cpIdtype?crIdtypes.prName:''}&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="cpId">证件号码</label></td>
					<td nowrap="nowrap" colspan="7"><input id="cpId" class="easyui-validatebox" type="text" name="cp_cpId" style="width: 400px"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="tlTelNum" class="bitian1">移动电话</label></td>
					<td nowrap="nowrap"><input type="hidden" name="cpmobile_id">
						<input id="tlTelNum" class="easyui-validatebox" name="cpmobile_tlTelNum" validType="mobile"></td>
					<td nowrap="nowrap"><label for="tlTelNum">固定电话</label></td>
					<td nowrap="nowrap">
						<input type="hidden" name="cptel_id">
						<input class="easyui-validatebox" type="text" name="cptel_tlAreaCode" style="width: 40px;" validType="numberL[3,4]"/>-
						<input class="easyui-validatebox" type="text" name="cptel_tlTelNum" style="width: 100px;" validType="numberL[6,8]"/>-
						<input class="easyui-validatebox" type="text" name="cptel_tlExtCode" style="width: 40px;" validType="numberL[0,6]"/></td>
					<td nowrap="nowrap"><label for="cpEmail">E-Mail</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="cp_cpEmail"  validType="email"></input></td>
					<td nowrap="nowrap"><label for="cpRelation" class="bitian1">与申请人关系</label></td>
					<td nowrap="nowrap"><input id="cpRelation"  name="cp_cpRelation"></td> 
				</tr>
				<tr>
					<td nowrap="nowrap" rowspan="2"><label for="arProvince">通讯地址</label></td>
					<td colspan="7" nowrap="nowrap">中国
						<input type="hidden" name="cpaddr_id">
						<input id="cparProvince" class="easyui-combobox" name="cpaddr_arProvince" style="width: 80px">
						<input id="cparCity" class="easyui-combobox" name="cpaddr_arCity" style="width: 80px" validType="selectValidator['cparCity']">
						<input id="cparCounty" class="easyui-combobox" name="cpaddr_arCounty" style="width: 80px" validType="selectValidator['cparCounty']">
					<!-- 	<input id="cparStreet" class="easyui-validatebox" name="cpaddr_arStreet" maxLength="20"> 街/路 -->
						<!-- <input id="cparStreet" class="easyui-validatebox" name="cpaddr_arStreet" onchange="cpaddrbuquan();" maxLength="20"> 街/路 -->
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap" colspan="5">
						<input id="cparAddrDetail" class="easyui-validatebox" name="cpaddr_arAddrDetail" style="width: 400px" maxLength="66"></td>
					<td nowrap="nowrap"><label for="cpaddr_arZipCode">邮编</label></td>
					<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="cpaddr_arZipCode"  validType="ZIP"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap" colspan="2"><label class="bitian1">您希望通过哪种方式接收文件？</label></td>
					<td colspan="4" nowrap="nowrap">
						<c:forEach items="${fileReceives}" var="fileReceives">
							<input type="${fileReceives.prState=='1' || fileReceives.prValue eq customer.crFileAccept?'checkbox':'hidden'}" 
							name="${fileReceives.prState=='1' || fileReceives.prValue eq customer.crFileAccept?'customer_crFileAccept':''}" onclick="checkedThis($(this));vEmail($(this));" value="${fileReceives.prValue}">
							${fileReceives.prState=='1' || fileReceives.prValue eq customer.crFileAccept?fileReceives.prName:''}&nbsp;&nbsp;
						</c:forEach>
					</td>
					<td nowrap="nowrap"><label for="customer_crCityId" class="bitian">分公司所属地区</label></td>
					<td nowrap="nowrap">
						<input id="crCityId"  name="customer_crCityId" style="width: 80px;" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label for="crGather" class="bitian">客户经理</label></td>
					<td nowrap="nowrap"><input id="crGather" class="easyui-combobox" type="text" name="customer_crGather" required="true"/></td>
					<td nowrap="nowrap"><label for="crSource" class="bitian1">信息来源</label></td>
					<td nowrap="nowrap"><input id="crSource"  type="text" name="customer_crSource"/></td>
					<td nowrap="nowrap"><label for="crGatherDate">采集时间</label></td>
					<td nowrap="nowrap"><input type="text" class="Wdate" name="customer_crGatherDate"  value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td nowrap="nowrap"><label for="crDeptManager">直属主管</label></td>
					<td nowrap="nowrap"><input id="crDeptManager" class="easyui-combobox" type="text" name="customer_crDeptManager" /></td>
				</tr>
					<tr>
						<td nowrap="nowrap" ><label for="topChannel" >渠道通路</label></td>
						<td nowrap="nowrap" >
						<div nowrap="nowrap">  
						<input id="custCrChannel"  name="customer_crChannel"  type ="hidden" >
						<div nowrap="nowrap"  style ="float:left">
							<input id="topChannel" class="easyui-combobox" name="top_channel" style="width: 120px" >  </div>
						<div id="divChannelMid" nowrap="nowrap" style="display:none; margin-left:10px" >
							 &nbsp;&nbsp; <input id="midChannel" class="easyui-combobox"  name="mid_channel" style="width: 120px " validType="selectValidator['midChannel']"></div>
						</div>
						</td>
						<td nowrap="nowrap"><label for="crGather" >客户申请书编号</label></td>
						<td colspan="3" nowrap="nowrap"><input class="easyui-validatebox" type="text" id="crApplicationNumber" name="customer_crApplicationNumber"  validType="crApplicationNo" style="width: 200px" maxLength="20"/></td>
						<td nowrap="nowrap"><label for="crRiskLevel" >客户风险等级</label></td>
						<td  nowrap="nowrap"><input type="text" id="crRiskLevel" name="customer_crRiskLevel"  style="width: 200px" maxLength="20"/></td>
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
			            &nbsp;<input type="checkbox" name="customer_hobby" value="${hobbies.prValue}">${hobbies.prName}&nbsp;&nbsp;&nbsp;
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
					<div id="formButtom" style="width:100%;text-align:center;background:/* #fafafa */;border:0px solid #8DB2E3;bottom:16px;/* right:40%; */">
						<a href="#" id="submit" class="easyui-linkbutton" onclick="saveLc();">保存</a> <a
						href="#" onclick="closeCurPage('客户信息修改')" class="easyui-linkbutton">取消</a>
					</div>
				</div> 
			</div> 
    
			<div title="附属信息" style="padding:20px;">  
	        <table width="100%">                                              
	       		<tr><td align="left">联系人信息</td><td align="right"><a href="${ctx}/crm/contactperson/create?customerid=${customers.id}&urlnow=${urlnow}" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a></td></tr>
	       		<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="lxr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">电话信息</td><td align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newTel();">新增</a></td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="tel" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">地址信息</td><td align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newAddr();">新增</a></td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="addr" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">银行信息</td><td align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBank();">新增</a></td></tr>
	        	<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="bank" width="100%"></table></td></tr>
			    <tr><td colspan="4"><br></td></tr>
	        </table>
	   		</div>  
<!-- 	    	<div title="兴趣爱好" style="padding:20px;">  
	        tab3   
	    	</div>   -->
    	</div>
	</div>
	
	<div data-options="region:'east',title:'操作'" style="width: 80px; padding:5px;">
	</div>
	
	<script type="text/javascript">
	var tlTelTypeTypeList;
	var arAddrTypeList;
	var priorityList;
	var relationList;
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
	
	$(function(){
		document.getElementById("baAccount").onkeyup =function() {
            this.value =this.value.replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");;
        };
		
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
				            }},		        
		        {field:'opt',title:'操作',width:fixWidth(0.05),formatter : function(value, rec) {
					var foId = rec.id;
					var links = '<a href="${ctx}/crm/contactperson/edit?id='+foId+'&urlnow=${urlnow}" >编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
					 links += '<a href=" "  onclick="return delLxr('+foId+');">删除</a>';
					return links;
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
				}},		        
		        {field:'opt',title:'操作',width:fixWidth(0.05),	formatter : function(value, rec) {
					var foId = rec.id;
					var links = '<a href=""  onclick="return editTel('+foId+');">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
					 links += "<a href=''  onclick='return delTel("+foId+");'>删除</span>"
					return links;
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
				}},		        
		        {field:'opt',title:'操作',width:fixWidth(0.05),formatter : function(value, rec) {
					var foId = rec.id;
					var links = '<a href=""  onclick="return editBank('+foId+');">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
					 links += "<a href=''  onclick='return delBank("+foId+");'>删除</span>"
					return links;
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
				}},		        
		        {field:'opt',title:'操作',width:fixWidth(0.05),formatter : function(value, rec) {
					var foId = rec.id;
    				var links = '<a href=""  onclick="return editAddr('+ foId+')">编辑</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
   				     links += "<a href=''  onclick='return delAddr("+ foId+");'>删除</span>"
   				   return links;
				}} 
		    ]]   
		});  
	})
	
	function delLxr(id){
		var i = confirm('确认删除？');
            if (i){  
            	$.post('${namespacePath}/delLxr', {id : id}, function(rows) {$('#lxr').datagrid('reload',rows);}, 'json');	
            }
            return false;
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
	
	function saveTel(){
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
	function editTel(id){
		$('#${entityName}_Tel').dialog('open').dialog('setTitle','电话编辑');
		$.post('${namespacePath}/telById', {id : id}, function(data) {
			if(data.tlTelType == <%= ParamConstant.MOBILE%>){
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
			$('#${entityName}_TelForm').form('load',data);
		}, 'json');
		return false;
	}
	function delTel(id){
		var i = confirm('确认删除？');
            if (i){  
            	$.post('${namespacePath}/delTel', {id : id}, function(rows) {$('#tel').datagrid('reload',rows);}, 'json');	
            }
            return false;
	}

	function saveAddr(){
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
	function editAddr(id){
		$('#${entityName}_Addr').dialog('open').dialog('setTitle','地址编辑');
		$.post('${namespacePath}/addrById', {id : id}, function(data) {
			if(data.arAddrType == <%= ParamConstant.ADDZJ%>){
				$("#addrdata_arAddrTypeId").hide();
			}else{
				$("#addrdata_arAddrTypeId").show();
				if(data.arProvince!=null){
					addrCity('addrdata_arProvince','addrdata_arCity','addrdata_arCounty','addrdata_arStreet','addrdata_arAddrDetail',data.arProvince);//单位地址
				}
				if(data.arCity!=null){
					addrCounty('addrdata_arProvince','addrdata_arCity','addrdata_arCounty','addrdata_arStreet','addrdata_arAddrDetail',data.arCity);//单位地址
				}
			}
			$('#${entityName}_AddrForm').form('load',data);
		}, 'json');
		return false;
	}
	function delAddr(id){
		var i = confirm('确认删除？');
        if (i){  
        	$.post('${namespacePath}/delAddr', {id : id}, function(rows) {$('#addr').datagrid('reload',rows);}, 'json');	
        }
        return false;
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
	function editBank(id){
		$('#${entityName}_Bank').dialog('open').dialog('setTitle','银行账号编辑');
		$.post('${namespacePath}/bankById', {id : id}, function(data) {
	    	if(data.baAccountType== <%= ParamConstant.BANKECARD%>){
	    		$('#baAccount').validatebox({validType:"bankAccount"});
		    	}else{
		    		$('#baAccount').validatebox({validType:""});
			    };
			$('#${entityName}_BankForm').form('load',data);
		}, 'json');
		return false;
	}
	function delBank(id){
		var i = confirm('确认删除？');
            if (i){  
            	$.post('${namespacePath}/delBank', {id : id}, function(rows) {
            		 if(rows == 0){
            			$.messager.show({
            				title: '无法删除银行信息',
            				msg: '<font color=red>该客户的银行信息已被录入业务中或变更中</font>'
            			});
            		}
            		$('#bank').datagrid('reload',{});}, 'json');
            }
            return false;
	}

	function idyz(obj){
	    var val = $("[name='customer_crIdtype']:checked").val();
	    if(val==<%= ParamConstant.IDTYPESF%>){
	    	$('#crIdnum').validatebox({validType:"idcard"});
		    }else{
		    	$('#crIdnum').validatebox({validType:""});
		 }
		}
	function idyz2(obj){
	    var val = $("[name='cp_cpIdtype']:checked").val();
	    if(val==<%= ParamConstant.IDTYPESF%>){
	    	$('#cpId').validatebox({validType:"idcard"});
		    }else{
		    	$('#cpId').validatebox({validType:""});
		 }
		}
	function saveLc(){
		var lengths = $('#crEmail').val().toLowerCase().indexOf("@");
		if (lengths != -1){
			var aa = $('#crEmail').val().toLowerCase().split("@");
			if (aa[1]=="zendaimoney.com") {
				$.messager.show({
					title: '提示',
					msg: '不能用公司邮箱！'
				});
				return false;
			}
		}
		vEmail(null);
		saveChannel();//2014-9-10 渠道保存
		var result1 = searchEmail();
		if(result1!=null){
			$.messager.show({
				title: '提示',
				msg: '该E-Mail已经存在！'
			});
			return false;
		}
		$('#submit').linkbutton({  
		    disabled:true
		}); 
		$('#ff').form('submit',{
			url: '${namespacePath}/saveLc',
			onSubmit: function(){
				var result = searchCustomer();
				if(result!=null){
					$.messager.show({
						title: '提示',
						msg: '该客户已经存在！'
					});
					$('#submit').linkbutton({  
					    disabled:false
					});
					return false;
				}else{
					if($(this).form('validate')){
						return true;
					}else{
						$('#submit').linkbutton({  
						    disabled:false
						}); 
						return false;
					}
				}
			},
			success: function(result){
				$('#submit').linkbutton({  
				    disabled:false
				}); 
				var result = eval('('+result+')');
				if (result.result.success){
					window.parent.show('提示','保存成功！');
					closeCurPageUpdateDatagrid('客户信息','客户信息修改','customer_list');
					//redirect('${namespacePath}');	//跳转
				} else {
					$.messager.show({
						title: '提示',
						msg: '修改失败'+result.result.errMsg
					});
				}
			}
		})
	}
	function searchCustomer(){

		var idType = $("[name='customer_crIdtype']:checked").val();
		var idNum = $('#crIdnum').val();
		var customerId = $('#customer_id').val();
		var result;
		$.ajax({
 		  	type: 'POST',
 		  	async:false,
 		  	url: '${namespacePath}/customerByidTypeAndidNum?idType='+idType+'&idNum='+encodeURI(idNum)+'&customerId='+customerId,
 		  	success: function(value){
 				
 		  		result = value;
     		},
 			dataType: 'json'
 		});
	 	return result; 
	}
	function yanzhengIdnum(obj){
		
		var result = searchCustomer();
		if(result!=null){
			$.messager.show({
				title: '提示',
				msg: '该客户已经存在！'
			});
		}else{
			var idType = $("[name='customer_crIdtype']:checked").val();
			if(idType=='1'){
				var idNum = $('#crIdnum').val();
				var list = showBirthday(idNum);
				$('#crBirthday').val(list[0]);
				$("input:checkbox[name='customer_crSex']").each(function(){
					$(this).attr('checked',false);
					if($(this).val()==list[1]){
						$(this).attr("checked",true);
						} 
				})
			}
		}
	}
	
	//E-Mail
	function yanzhengEmail(){
		var lengths = $('#crEmail').val().toLowerCase().indexOf("@");
		if (lengths != -1){
			var aa = $('#crEmail').val().toLowerCase().split("@");
			if (aa[1]=="zendaimoney.com") {
				$.messager.show({
					title: '提示',
					msg: '不能用公司邮箱！'
				});
				$('#submit').linkbutton({  
				    disabled:false
				});
			}
		}
		var result = searchEmail();
		if(result!=null){
			$.messager.show({
				title: '提示',
				msg: '该E-Mail已经存在！'
			});
			$('#submit').linkbutton({  
			    disabled:false
			});
		}	
	}
	//检查email的唯一性
	function searchEmail(){
		var crEmail = $('#crEmail').val();
		var customerId = $('#customer_id').val();
		var result;
		$.ajax({
 		  	type: 'POST',
 		  	async:false,
 		  	url: '${namespacePath}/customerByCrEmail?crEmail='+crEmail+'&customerId='+customerId,
 		  	success: function(value){
 		  		result = value;
     		},
 			dataType: 'json'
 		});
	 	return result; 
	}
	
	function vEmail(obj){
		var a = false;
		$("input[name='customer_crFileAccept']:checked").each(function(){
			if($(this).val()==<%= ParamConstant.FILERECEIVEMAIL%> || $(this).val()==<%= ParamConstant.FILERECEIVEMAILAll%>){
				a = true;
				}
		})
		if(a == true){
			$('#crEmail').validatebox({required:true});
			}
		else{
			$('#crEmail').validatebox({required:false});
			}
		}
	function foreverCrExpiryDate(obj){
		if(obj.checked==true){
	    	$('#customer_crExpiryDate').attr('disabled',true);
	    	$('#customer_crExpiryDate').hide();
	    }else{
	    	$('#customer_crExpiryDate').attr('disabled',false);
	    	$('#customer_crExpiryDate').show();
	    	
	    }
	}
	</script>
	
</body>
<script type="text/javascript" src="${ctx}/static/js/utils/email.js"></script>



<div id="${entityName}_Tel" class="easyui-dialog" style="width:500px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsTel"> 
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">电话添加</div>
		<form id="${entityName}_TelForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="teldata_telid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label for="tlTelType">电话类型:</label></td>
				<td nowrap="nowrap"><input id="teldata_tlTelType"  name="tlTelType"></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label>电话:</label></td>
				<td nowrap="nowrap" id="teldata_tlTelTypeId">
				<input id="teldata_tlAreaCode"  class="easyui-validatebox" validType="numberL[3,4]" type="text" name="tlAreaCode" style="width: 40px;"/>-
				<input id="teldata_tlTelNum" class="easyui-validatebox" validType="numberL[6,8]" type="text" name="tlTelNum" style="width: 100px;"/>-
				<input id="teldata_tlExtCode" class="easyui-validatebox" validType="numberL[0,6]" type="text" name="tlExtCode" style="width: 40px;"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label for="tlPriority">优先级:</label></td>
				<td nowrap="nowrap"><input id="teldata_tlPriority"  name="tlPriority"  ></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label>是否有效:</label></td>
				<td nowrap="nowrap"><input id="teldata_tlValid" name="tlValid"  type="radio" value="1" checked="checked">有效
					<input id="teldata_tlValid" name="tlValid"   type="radio" value="0" >无效</td>
			</tr>
			</table>
		</form>
		</div>
		</div>
</div>

<div id="${entityName}_Addr" class="easyui-dialog" style="width:620px;height:320px;" closed="true" buttons="#${entityName}_dlg-buttonsAddr">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">地址添加</div>
		<form id="${entityName}_AddrForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="addrdata_addrid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label for="arAddrType">地址类型:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arAddrType"  name="arAddrType"></td>
			</tr>
			<tr  id="addrdata_arAddrTypeId">
				<td nowrap="nowrap"><label>地址:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arProvince" class="easyui-combobox" name="arProvince" style="width: 60px;">省-
				<input id="addrdata_arCity" class="easyui-combobox" name="arCity" style="width: 100px;" validType="selectValidator['addrdata_arCity']">市-
				<input id="addrdata_arCounty" class="easyui-combobox" name="arCounty" style="width: 60px;" validType="selectValidator['addrdata_arCounty']">县-
				<!-- <input id="addrdata_arStreet" class="easyui-validatebox" name="arStreet"> 街/路</td> -->
				<!-- <input id="addrdata_arStreet" class="easyui-validatebox" name="arStreet" onchange="addrdata_addrbuquan();" > 街/路</td> -->
			</tr>
			<tr>
				<td nowrap="nowrap"><label>详细地址</label></td>
				<td nowrap="nowrap"><input id="addrdata_arAddrDetail" class="easyui-validatebox" type="text" name="arAddrDetail" style="width: 350px;"></input></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label for="arZipCode">邮编:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arZipCode" class="easyui-validatebox" name="arZipCode"  ></td>
			</tr>
			<tr>
			<td nowrap="nowrap"><label for="arPriority">优先级:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arPriority"  name="arPriority"  ></td>
				</tr>
			<tr>
				<td nowrap="nowrap"><label>是否有效:</label></td>
				<td nowrap="nowrap"><input id="addrdata_arValid" name="arValid"  type="radio" value="1">有效
				<input id="addrdata_arValid" name="arValid"   type="radio" value="0" >无效</td>
			</tr>
				</table>
		</form>
		</div>
		</div>
		
</div>

<div id="${entityName}_Bank" class="easyui-dialog" style="width:550px;height:280px;" closed="true" buttons="#${entityName}_dlg-buttonsBank">
<div class="easy-layout">
	<div data-options="region:'center'" >
 		<div class="subtitle">银行账号添加</div>
		<form id="${entityName}_BankForm" method="post" style="margin:0;padding:10px 30px;" novalidate>
		<input id="bankdata_bankid" type="hidden" name="id">
		<table  class="m_table" width="100%">
			<tr>
				<td nowrap="nowrap"><label>开户名:</label></td>
				<td nowrap="nowrap"><input id="baAccountName" name="baAccountName" class="easyui-validatebox" required="true" readonly="readonly" style="color: gray;" ></td>
				<td nowrap="nowrap"><label>账户类型:</label></td>
				<td nowrap="nowrap"><input id="baAccountType"  name="baAccountType" onchange="vBaAccount();"></td>				
			</tr>
			<tr>
				<td nowrap="nowrap"><label>银行名称:</label></td>
				<td nowrap="nowrap"><input id="baBankName"  name="baBankCode"  ></td>
				<td nowrap="nowrap"><label>支行名称:</label></td>
				<td nowrap="nowrap"><input name="baBranchName" class="easyui-validatebox" required="true">
			</td>
			</tr>
			<tr>
			<td nowrap="nowrap"><label>银行账号:</label></td>
				<td nowrap="nowrap"><input id="baAccount" name="baAccount" class="easyui-validatebox" required="true"></td>
				<td nowrap="nowrap"><label>是否有效:</label></td>
				<td nowrap="nowrap"><input name="baValid"  type="radio" value="1">有效
					<input name="baValid"   type="radio" value="0" >无效
				</td>
			</tr>
			</table>
		</form>
	</div>
</div>
</div>

<div id="${entityName}_dlg-buttonsTel">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveTel()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Tel').dialog('close')">取消</a>
</div>
<div id="${entityName}_dlg-buttonsAddr">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddr()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Addr').dialog('close')">取消</a>
</div>
<div id="${entityName}_dlg-buttonsBank">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBank()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_Bank').dialog('close')">取消</a>
</div>