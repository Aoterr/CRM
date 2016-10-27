<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
    
	<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
	<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
	<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>

<%@ include file="/static/common/doing.jsp"%>

<c:set var="entityName" value="customer" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/crm/customer" />
<script type="text/javascript">
	$(function() {
		$('#zh').datagrid({   
		    url:'',   
		    rownumbers:true,
		    fitColumns:true,
			sortName : 'id',
			sortOrder : 'asc',
		    columns:[[   
		        {field:'cpName1',title:'挂账',width:fixWidth(0.05)}, 
		        {field:'cpName2',title:'投资',width:fixWidth(0.05)}, 
		        {field:'cpName3',title:'应收利息',width:fixWidth(0.05)}, 
		        {field:'cpName4',title:'贷款',width:fixWidth(0.05)},
		        {field:'cpName5',title:'应付利息',width:fixWidth(0.05)},
		        {field:'cpName6',title:'利息收入',width:fixWidth(0.05)}, 
		        {field:'cpName7',title:'利息支出',width:fixWidth(0.05)}
		    ]]   
		}); 
		$('#lc').datagrid({   
		   // url:'${ctx}/crm/param/getLcM?id=${customers.id}', 
		    url:'',
		    rownumbers:true,
		    fitColumns:true,
			sortName : 'id',
			sortOrder : 'asc',
		    columns:[[   
		        {field:'investLendingNo',title:'出借编号',width:fixWidth(0.06),formatter :function(value,rec){
		        	var id ;
		        	postAjax(false, "${namespacePath}/getIdByFeLendNoExceptApp?feLendNo="+value, function(data) {
		        		id = data;}, 'json');
		        	if(id==0) return value;
		        	return '<a href="#" onclick="createTabsInframePage(\'业务详情查看\',\'${ctx}/product/purchase/show?type=2&id=' +id  + '\')">'+value+'</a>'
		        }},   
				{field:'productType',title:'出借方式',width:fixWidth(0.04)},
				{field:'pv',title:'当前债权价值',width:fixWidth(0.04),formatter: function (value) {
					return roundNumber(value,2);
				}},
				{field:'loanReturnDate',title:'债权还款日',width:fixWidth(0.04)},
				{field:'investAmt',title:'初始投资金额',width:fixWidth(0.04)},
				{field:'investStartDate',title:'实际出借日期',width:fixWidth(0.05),formatter : formatDate},
				{field:'investEndDate',title:'到期日期',width:fixWidth(0.05),formatter : formatDate},
				{field:'manageFeeDiscount',title:'管理费折扣',width:fixWidth(0.04)},
				{field:'protocolNo',title:'合同编号',width:fixWidth(0.05)},
				{field:'manageFeeRate',title:'管理费率（未乘折扣）',width:fixWidth(0.06)},
				{field:'investStatus',title:'投资状态',width:fixWidth(0.04)}
			]]   
		}); 
		$('#xd').datagrid({   
		    url:'',   
		    rownumbers:true,
		    fitColumns:true,
			sortName : 'id',
			sortOrder : 'asc',
		    columns:[[   
				{field:'borrowNo',title:'借款编号',width:fixWidth(0.06)},		
				{field:'productName',title:'产品名称',width:fixWidth(0.04)},	
				{field:'contractMoney',title:'合同金额',width:fixWidth(0.04)},	
				{field:'term',title:'期数',width:fixWidth(0.03)},
				{field:'startDate',title:'起始日期',width:fixWidth(0.05),formatter : formatDate},	
				{field:'endDate',title:'结束日期',width:fixWidth(0.05),formatter : formatDate},	
				{field:'currentTerm',title:'当前期数',width:fixWidth(0.04)},	
				{field:'contractNo',title:'合同编号',width:fixWidth(0.05)},	
				{field:'currentState',title:'当前状态',width:fixWidth(0.04)}
			]]   
		}); 
	});
	
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center'" id="main">
	<div class="m_title">业务信息</div>
		<div class="m_content" style="width:100%;">
			<table width="100%">                   
	       		<tr><td align="left">账户信息</td></tr>
	       		<tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="zh" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">理财信息</td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="lc" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
			    <tr><td align="left">贷款信息</td></tr>
			    <tr><td colspan="4"><hr></td></tr>
			    <tr><td colspan="4"><table id="xd" width="100%"></table></td></tr>
			    <tr><td><br></td></tr>
	        </table>
		</div>
   	    <div class="m_btn_box" style="width:100%;">   
            <a href="#" onclick="closeCurPage('业务')" class="easyui-linkbutton">返回</a>  
        </div> 
	</div>  
</body>