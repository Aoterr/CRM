 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ include file="/static/common/taglibs.jsp"%>
	<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>	
<c:set var="entityName" value="customer"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer"  /><!-- 命名空间 -->
<script type="text/javascript">

	$(function() {
		comboboxUtil.setComboboxByUrl('crCustomerType', "${ctx}/crm/param/findAllByPrType?prType=customerType", 'prValue', 'prName', '150', false);//客户来源
	});
</script> 
<form id="customer_queryForm" method="post" style="padding:10px 20px">
	<div class="subtitle">请输入查询参数：</div>
	<table class="m_table" style="width:100%;">
		 	<tr> 
			<td>条件选择:</td> 
			<td colspan="3">
				<input id="isor" type="radio" class="query" name="search_EQ_isor" value="false" checked="checked"/> 满足全部条件
				<input id="isor" type="radio"  name="search_EQ_isor" value="true"/> 满足任一条件
			</td> 
		 </tr> 
 
		<tr> 
			<td >证件号码 :</td> 
			<td style="width:125px">
				<input name="search_EQ_crIdnum" class="query" style="width:200px"/>
			</td>
			
		 </tr>
		 <tr> 
			<td>采集时间 :</td> 
			<td style="width:400px" colspan="3">
				<input type="text" class="query Wdate" style="width: 160px;" name="search_GTE_crGatherDate" onclick="WdatePicker({dateFmt:'yyyy/MM/dd'})"  /> 
			 ~  <input type="text" class="query Wdate" style="width: 160px;" name="search_LTE_endDate-crGatherDate" onclick="WdatePicker({dateFmt:'yyyy/MM/dd'})"  /> 
			</td>
		</tr>
		
		<tr> 
			<td>客户姓名 :</td> 
			<td style="width:125px">
				<input name="search_LIKE_crName" class="query" style="width:100px"/>
			</td> 			
		 </tr>
		 <tr> 
			<td>客户类型 :</td> 
			<td style="width:125px">
				<input id="crCustomerType" name="search_EQ_crCustomerType" class="query"/>
			</td> 			
		 </tr>
	</table> 
	</form>