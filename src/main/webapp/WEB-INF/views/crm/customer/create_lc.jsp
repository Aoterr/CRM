<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page import="com.zendaimoney.utils.helper.SystemParameterHelper"%>
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
<c:set var="entityName" value="customer" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer" />
<!-- 命名空间 -->
<style type="text/css">        
input[type='text']{           
	width:90px;        
	}
#myemail, .newemail{
	cursor:default;
	line-height:18px;
}
</style>
<script type="text/javascript"><!--
var getProvinces,getArea;
var getChannelTop,getChannels;


	$(function() {
		if("${crIdNum}"!=''){
			$("input[type='checkbox'][name='customer_crIdtype'][value='1']").attr("checked", "checked")
			$('#crIdnum').validatebox({validType:"idcard"});	
		}
		//身份证性别生日输入
		$("#crIdnum").blur(function(){
			$('#sexM').attr("checked",false);
			$('#sexF').attr("checked",false);
			$('#crBirthday').val("");
			var id=String($(this).val()), sex=id.slice(14,17)%2?  $('#sexM').attr("checked",'checked') : $('#sexF').attr("checked",'checked');
			var list = showBirthday(id);
			$('#crBirthday').val(list[0]);
			
			var result = searchCustomer();
			if(result!=null){
				$.messager.alert('提示','该客户已经存在！');
				$('#submit').linkbutton({  
				    disabled:false
				});
			}	
		});
		
		//联系人身份证性别输入
		$("#cpId").blur(function(){
			if($("[name='cp_cpIdtype']:checked").val()!='1') return false;
			$('#cpId').validatebox({validType:"idcard"});
			$('#cpsexM').attr("checked",false);
			$('#cpsexF').attr("checked",false);
			var id=String($(this).val()), sex=id.slice(14,17)%2?  $('#cpsexM').attr("checked",'checked') : $('#cpsexM').attr("checked",'checked');		
			var list = showBirthday(id);
			$('#cp_cpBirthday').val(list[0]);
		
			if(($("#crIdnum").val()==$("#cpId").val())){
				$.messager.alert('提示','该证件号已经存在！');
				$('#submit').linkbutton({  
				    disabled:false
				});
			}	
		});
		
		//E-Mail
		$("#crEmail").blur(function(){
			var lengths = $('#crEmail').val().toLowerCase().indexOf("@");
			if (lengths != -1){
				var aa = $('#crEmail').val().toLowerCase().split("@");
				if (aa[1]=="zendaimoney.com") {
					$.messager.alert('提示','不能用公司邮箱！');
					$('#crEmail').attr("value",'');
					$('#submit').linkbutton({  
					    disabled:false
					});
				}
			}
			var result = searchEmail();
			if(result!=null){
				$.messager.alert('提示','该E-Mail已经存在！');
				$('#crEmail').attr("value",'');
				$('#submit').linkbutton({  
				    disabled:false
				});
			}
		});
		
		postAjax(false, "${ctx}/crm/param/getProvinces", function(data) {getProvinces = data}, 'json');
		postAjax(false, "${ctx}/crm/param/getArea", function(data) {getArea = data}, 'json');
		addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//通讯地址
		addrProvice('cparProvince','cparCity','cparCounty','cparStreet','cparAddrDetail');//公司地址
		
		//渠道通路选择   2014-9-10
		postAjax(false, "${ctx}/crm/channel/getChannelTop", function(data) {getChannelTop = data}, 'json');
		postAjax(false, "${ctx}/crm/channel/getChannels", function(data) {getChannels = data}, 'json');
		addrChannel('topChannel','midChannel','divChannelMid','','');
		
		comboboxUtil.setComboboxByUrl('crEducation', "${ctx}/crm/param/findAllByPrType?prType=education", 'prValue', 'prName', '150', false);//学历
		comboboxUtil.setComboboxByUrl('crMaritalStatus', "${ctx}/crm/param/findAllByPrType?prType=maritalStatus", 'prValue', 'prName', '150', false);//婚姻状况
		comboboxUtil.setComboboxByUrl('crIndustry', "${ctx}/crm/param/findAllByPrType?prType=industry", 'prValue', 'prName', '150', false);//行业
		comboboxUtil.setComboboxByUrl('crDuty', "${ctx}/crm/param/findAllByPrType?prType=duty", 'prValue', 'prName', '150', false);//职务
		comboboxUtil.setComboboxByUrlWithpanelHeight('crNationality', "${ctx}/crm/param/getNations", 'id', 'name', '150', false);//国籍
		comboboxUtil.setComboboxByUrl('crLanguage', "${ctx}/crm/param/findAllByPrType?prType=language", 'prValue', 'prName', '150', false);//语言
		comboboxUtil.setComboboxByUrl('crSource', "${ctx}/crm/param/findAllByPrType?prType=source", 'prValue', 'prName', '150', false);//客户来源
		comboboxUtil.setComboboxByUrl('crRiskLevel', "${ctx}/crm/param/findAllByPrType?prType=riskLevel", 'prValue', 'prName', '150', false);//风险等级
		comboboxUtil.setComboboxByUrl('cpRelation', "${ctx}/crm/param/findAllByPrType?prType=relation", 'prValue', 'prName', '150', false);//客户关系
		
		
		comboboxUtil.setComboboxByUrlWithpanelHeight('crCityId', "${ctx}/crm/param/findOrganFortuneExceptHLW", 'prValue', 'prName', '150', true);//地区
    	$('#crNationality').combobox('select',<%=ParamConstant.CHINAID%>);
 
		$(".m_table td").has("label").css("text-align","right");
		//$('#fflayout').layout('collapse','east');  
	});
	 
	var checkSubmitFlg = false;
	function checkSubmit(){
		if(checkSubmitFlg == true){ 
			return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
		}
		checkSubmitFlg = true;
		return true;
	} 
	function saveLc() {
		if (checkSubmit()) {
			$('#submit').linkbutton({  
			    disabled:true
			});
		   saveChannel();//2014-9-10 渠道保存
			$('#ff').form('submit', {
				url : '${namespacePath}/saveLc',
				onSubmit : function() {
					var result = searchCustomer();
					if(result!=null){
						$.messager.alert('提示','该客户已经存在！');
						$('#submit').linkbutton({  
						    disabled:false
						});
						checkSubmitFlg = false;
						return false;
					}else{
						if($(this).form('validate')){
							return true;
						}else{
							$('#submit').linkbutton({  
							    disabled:false
							});
							checkSubmitFlg = false;
							return false;
						}
					}
				},
				success : function(result) {
					$('#submit').linkbutton({  
					    disabled:false
					});
					var result = eval('(' + result + ')');
					if (result.result.success) {
						window.parent.show('提示','保存成功！');
						//upTabTitle('产品申请',"${ctx}"+result.result.url);
						//redirect("${ctx}"+result.result.url);	//跳转
						//createUpdateTabsInframePage('产品申请新增',"${ctx}"+result.result.url,'客户信息','客户信息新增','customer_list');
						closeCurPageUpdateDatagrid('客户信息','客户信息新增','customer_list');

						//closeCurPage('客户信息');
					} else {
						$.messager.show({
							title: '提示',
							msg: '新增失败    '+result.result.errMsg
						});
					}
					checkSubmitFlg = false;
				}
			})
		}
	} 
	function idyz(obj){
	    var val = $("[name='customer_crIdtype']:checked").val();
	    $('#crIdnum').attr('disabled',false);
	    if(val==<%= ParamConstant.IDTYPESF%>){
	    	$('#crIdnum').validatebox({validType:"idcard"});
		    }else{
		    	$('#crIdnum').validatebox({validType:""});
		 }
		}
	function idyz2(obj){
	    var val = $("[name='cp_cpIdtype']:checked").val();
	    $('#cpId').attr('disabled',false);
	    if(val==<%= ParamConstant.IDTYPESF%>){
	    	$('#cpId').validatebox({validType:"idcard"});
		    }else{
		    	$('#cpId').validatebox({validType:""});
		 }
		}
	function searchCustomer(){
		var idType = $("[name='customer_crIdtype']:checked").val();
		var idNum = $('#crIdnum').val();
		var customerId;
		var result;
		$.ajax({
 		  	type: 'POST',
 		  	async:false,
 		  	url: '${namespacePath}/customerByidTypeAndidNum?idType='+idType+'&idNum='+encodeURI(idNum)+'&customerId=',
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
	
	function checkIdnum(obj){
		if(typeof($("[name='customer_crIdtype']:checked").val()) == 'undefined'){
			$.messager.show({
				title: '提示',
				msg: '<font color=red>请先选择客户证件类型!  </font>  '
			});
			obj.attr('disabled',true);
		}
	}
	
	function checkCpidnum(obj){
		if(typeof($("[name='cp_cpIdtype']:checked").val()) == 'undefined'){
			$.messager.show({
				title: '提示',
				msg: '<font color=red>请先选择联系人证件类型!  </font>  '
			});
			obj.attr('disabled',true);
		}
	}
	
	//检查email的唯一性
	function searchEmail(){
		var crEmail = $('#crEmail').val();
		var result;
		$.ajax({
 		  	type: 'POST',
 		  	async:false,
 		  	url: '${namespacePath}/customerByCrEmail?crEmail='+crEmail+'&customerId=',
 		  	success: function(value){
 		  		result = value;
     		},
 			dataType: 'json'
 		});
	 	return result; 
	}
--></script>
<body class="easyui-layout" id="fflayout">
	<div data-options="region:'center'" id="main">
		<form id="ff" method="post">
			<div class="m_title">理财客户申请</div>
	    	<div class="m_content2" style="width:100%;">
	    		<div class="m_mar">
			    <table  class="m_table" style="width:100%;">
			    	<tr class="tr1">
			       		<td colspan="8">个人资料</td>
			       </tr>
			       <tr class="tr2">
			       		<td colspan="8"></td>
			       </tr>
					<tr>
						<td nowrap="nowrap"><label for="crName" class="bitian">姓名（中文）</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crName" required="true" maxLength="33"  value="${crName}"/></td>
						<td nowrap="nowrap"><label for="crEname">姓名（英文）</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crEname" maxLength="20"/></td>
						<td nowrap="nowrap"><label for="crSex" class="bitian1">性别</label></td>
						<td nowrap="nowrap"><input type="checkbox" id="sexM" name="customer_crSex" onclick="checkedThis($(this))" value="1">男
							<input id="sexF" type="checkbox" name="customer_crSex" onclick="checkedThis($(this))" value="0">女
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
						<td nowrap="nowrap" colspan="7">
							<c:forEach items="${crIdtypes}" var="crIdtypes">
								<input  type="checkbox" name="customer_crIdtype" onclick="checkedThis($(this));idyz(this)"  value="${crIdtypes.prValue}">&nbsp;${crIdtypes.prName}&nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crIdnum" class="bitian1">证件号码</label></td>
						<td nowrap="nowrap" colspan="7"><input id="crIdnum" class="easyui-validatebox" type="text" name="customer_crIdnum" style="width: 400px" maxlength="20" value="${crIdNum}"  onclick="checkIdnum($(this));"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crBirthday">签发日期</label></td>
						<td nowrap="nowrap"><input type="text" class="Wdate" name="customer_crIssueDate" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
						<td nowrap="nowrap"><label for="crBirthday" >失效日期</label></td>
						<td nowrap="nowrap"><input id="customer_crExpiryDate" type="text" class="Wdate" name="customer_crExpiryDate" onfocus="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})"/>
							<input type="checkbox" id = "forever_crExpiryDate" name = "forever_crExpiryDate" onclick="foreverCrExpiryDate(this);" value="0">长期
							</td>
						<td nowrap="nowrap"><label for="arProvince" >发证机关所在地</label></td>
						<td nowrap="nowrap" colspan="3" nowrap="nowrap">
							<!-- <input id="zjArProvince"  name="zj_arProvince" style="width: 80px">省
							<input id="zjArCity"  name="zj_arCity" style="width: 80px">市
							<input id="zjArCounty"  name="zj_arCounty" style="width: 80px">县 
							<input id="zjArStreet" class="easyui-validatebox" name="zj_arStreet" maxLength="20"> -->
							<input id="zjArAddrDetail" class="easyui-validatebox" name="zj_arAddrDetail" style="width: 250px" maxLength="66">
							<!-- <input id="arStreet" class="easyui-validatebox" name="addr_arStreet" onchange="addrbuquan();" maxLength="20"> 街/路 -->
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crOccupation" >职业 </label></td>
						<td nowrap="nowrap" colspan="5">
							<c:forEach items="${crOccupations}" var="crOccupations">
								<input type="checkbox" name="customer_crOccupation" onclick="checkedThis($(this))" value="${crOccupations.prValue}">&nbsp;${crOccupations.prName}&nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</td>
						<td nowrap="nowrap"><label for="crEducation">学历</label></td>
						<td nowrap="nowrap"><input id="crEducation"  name="customer_crEducation"></td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crIndustry">行业</label></td>
						<td nowrap="nowrap"><input id="crIndustry"  name="customer_crIndustry"></td>
						<td nowrap="nowrap"><label for="crCompany">单位名称</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crCompany" maxLength="33"/></td>
						<td nowrap="nowrap"><label for="crWorkYears">工作年限</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="customer_crWorkYears" validType="validnum[50]"/></td>
						<td nowrap="nowrap"><label for="crDuty">职务</label></td>
						<td nowrap="nowrap"><input id="crDuty"  name="customer_crDuty"></td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crCompanySize">单位规模</label></td>
						<td nowrap="nowrap" colspan="7">						
							<c:forEach items="${crCompanySizes}" var="crCompanySizes">
								<input type="checkbox" name="customer_crCompanySize" onclick="checkedThis($(this))" value="${crCompanySizes.prValue}">&nbsp;${crCompanySizes.prName}&nbsp;&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
	
						<td nowrap="nowrap"><label for="tlTelNum">固定电话</label></td>
						<td nowrap="nowrap">
							<input class="easyui-validatebox" type="text" name="tel_tlAreaCode" style="width: 40px;" validType="numberL[3,4] "/>-
							<input class="easyui-validatebox" type="text" name="tel_tlTelNum" style="width: 100px;" validType="numberL[6,8]"/>-
							<input class="easyui-validatebox" type="text" name="tel_tlExtCode" style="width: 40px;" validType="numberL[0,6]"/></td>
						<td nowrap="nowrap"><label for="tlTelNum" class="bitian">移动电话</label></td>
						<td nowrap="nowrap">
							<input id="tlTelNum" class="easyui-validatebox" name="mobile_tlTelNum" maxlength='15' validType="mobile" value="${telNums}"  required="true"></td>
						<td nowrap="nowrap"><label for="crEmail">E-Mail</label></td>
						<td nowrap="nowrap"><input id="crEmail" class="easyui-validatebox" type="text" name="customer_crEmail" validType="email" maxLength="50" style="width:150px" onfocus="emails('crEmail','main')"/></td>
						<td></td>
						<td></td>
<!-- 						<td nowrap="nowrap"><label for="crEmail1">传真</label></td>
						<td nowrap="nowrap">
							<input class="easyui-validatebox" type="text" name="cz_tlAreaCode" style="width: 40px;" validType="number" maxLength="4"/>-
							<input class="easyui-validatebox" type="text" name="cz_tlTelNum" style="width: 100px;" validType="number" maxLength="20"/>-
							<input class="easyui-validatebox" type="text" name="cz_tlExtCode" style="width: 40px;" validType="number" maxLength="6"/></td> -->
					</tr>
					<tr>
						<td nowrap="nowrap" rowspan="2"><label for="arProvince" class="bitian1">通讯地址</label></td>
						<td colspan="7" nowrap="nowrap">中国
							<input id="arProvince" class="easyui-combobox" name="addr_arProvince" style="width: 120px">省
							<input id="arCity" class="easyui-combobox" name="addr_arCity" style="width: 120px" validType="selectValidator['arCity']">市
							<input id="arCounty" class="easyui-combobox" name="addr_arCounty" style="width: 80px" validType="selectValidator['arCounty']">县 
						<!-- 	<input id="arStreet" class="easyui-validatebox" name="addr_arStreet" maxLength="20"> 街/路 -->
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
			       		<td colspan="8">紧急联系人资料</td>
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
						<td nowrap="nowrap"><input type="checkbox" id="cpsexM" name="cp_cpSex" onclick="checkedThis($(this))" value="1">男&nbsp;&nbsp;
							<input type="checkbox" id="cpsexF" name="cp_cpSex" onclick="checkedThis($(this))" value="0">女</td>
						<td nowrap="nowrap"><label for="cpBirthday">出生日期 </label></td>
						<td nowrap="nowrap"><input type="text" class="Wdate" id="cp_cpBirthday" name="cp_cpBirthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="cpIdtype">证件类型 </label></td>
						<td nowrap="nowrap" colspan="7">			
							<c:forEach items="${crIdtypes}" var="crIdtypes">
								<input type="checkbox" name="cp_cpIdtype" onclick="checkedThis($(this))" onBlur="idyz2(this)" value="${crIdtypes.prValue}">${crIdtypes.prName}&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="cpId">证件号码</label></td>
						<td nowrap="nowrap" colspan="7"><input id="cpId" class="easyui-validatebox" type="text" name="cp_cpId" style="width: 400px" onclick="checkCpidnum($(this));"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="tlTelNum" class="bitian1">移动电话</label></td>
						<td nowrap="nowrap"><input id="tlTelNum" class="easyui-validatebox" name="cpmobile_tlTelNum" validType="mobile"></td>
						<td nowrap="nowrap"><label for="tlTelNum">固定电话</label></td>
						<td nowrap="nowrap">
							<input class="easyui-validatebox" type="text" name="cptel_tlAreaCode" style="width: 40px;" validType="numberL[3,4]" />-
							<input class="easyui-validatebox" type="text" name="cptel_tlTelNum" style="width: 100px;" validType="numberL[6,8]" />-
							<input class="easyui-validatebox" type="text" name="cptel_tlExtCode" style="width: 40px;" validType="numberL[0,6]" /></td>
						<td nowrap="nowrap"><label for="cpEmail">E-Mail</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="cp_cpEmail"  validType="email" style="width:150px"></input></td>
						<td nowrap="nowrap"><label for="cpRelation" class="bitian1">与申请人关系</label></td>
						<td nowrap="nowrap"><input id="cpRelation"  name="cp_cpRelation"></td> 
					</tr>
					<tr>
						<td nowrap="nowrap" rowspan="2"><label for="arProvince">通讯地址</label></td>
						<td colspan="7" nowrap="nowrap">中国
							<input id="cparProvince" class="easyui-combobox" name="cpaddr_arProvince" style="width: 120px">省
							<input id="cparCity" class="easyui-combobox" name="cpaddr_arCity" style="width: 120px" validType="selectValidator['cparCity']">市
							<input id="cparCounty" class="easyui-combobox" name="cpaddr_arCounty" style="width: 80px" validType="selectValidator['cparCounty']">县 
							<!-- <input id="cparStreet" class="easyui-validatebox" name="cpaddr_arStreet" maxLength="20"> 街/路 -->
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
								<input type="checkbox" name="customer_crFileAccept" onclick="checkedThis($(this));vEmail($(this));" value="${fileReceives.prValue}">${fileReceives.prName}&nbsp;&nbsp;
							</c:forEach>
						</td>
						<td nowrap="nowrap"><label for="customer_crCityId" class="bitian">分公司所属地区</label></td>
						<td nowrap="nowrap">
							<input id="crCityId"  name="customer_crCityId" style="width: 80px;"  required="true"/>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap"><label for="crGather" class="bitian">客户经理</label></td>
						<td nowrap="nowrap"><input id="crGather" class="easyui-validatebox" type="text" name="customer_crGather" required="true"/></td>
						<td nowrap="nowrap"><label for="crSource" class="bitian1">信息来源</label></td>
						<td nowrap="nowrap"><input id="crSource"  type="text" name="customer_crSource"/></td>
						<td nowrap="nowrap"><label for="crGatherDate">采集日期</label></td>
						<td nowrap="nowrap"><input type="text" class="Wdate" name="customer_crGatherDate"  value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
						<td nowrap="nowrap"><label for="crDeptManager">直属主管</label></td>
						<td nowrap="nowrap"><input id="crDeptManager" class="easyui-validatebox" type="text" name="customer_crDeptManager" /></td>
					</tr>
					<tr>
						<td nowrap="nowrap" ><label for="topChannel">渠道通路</label></td>
						<td  nowrap="nowrap" >
						<input id="custCrChannel"  name="customer_crChannel"  type ="hidden" >
						<div nowrap="nowrap">
						<div nowrap="nowrap"  style ="float:left">
							<input id="topChannel" class="easyui-combobox" name="top_channel" style="width: 120px">  </div>
						<div id="divChannelMid" nowrap="nowrap" style="display:none; margin-left:10px" >
							 &nbsp;&nbsp;<input id="midChannel" class="easyui-combobox"  name="mid_channel" style="width: 120px ; display:none " validType="selectValidator['midChannel']"></div>
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
				<a href="javascript:void(0)" class="easyui-linkbutton" plain="" onclick="saveLc();" icon="icon-save">保存</a> 
				<a href="#" onclick="closeCurPage('客户信息新增')" class="easyui-linkbutton" plain="" icon="icon-undo">取消</a>
			</div>
		</div>
		
	</div>

		
	</div>

	 
</body>
<script type="text/javascript" src="${ctx}/static/js/utils/email.js"></script>