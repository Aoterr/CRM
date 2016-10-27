<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<c:set var="entityName" value="attachment" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/attachment" />
<style type="text/css">
.m-style .panel-header {
	background: url(${ctx}/static/images/m_table2_th.jpg) repeat-x;
}

.m-style .m_ul {
	margin: 10px 0;
}

.m-style .m_ul li {
	height: 30px;
	line-height: 30px;
	margin: 0 15px;
	padding: 0 10px;
	border-bottom: 1px dashed #ccc;
}

.m-style .m_ul2 li {
	margin: 0 25px;
}

.m-style .m_ul li.m_li {
	margin: 15px 15px 5px;
	background: url(${ctx}/static/images/lamp.png) 8px center no-repeat;
	border: 1px solid #E4E4E4;
	color: #2e83c4;
	padding: 0 30px;
	font-size: 14px;
}

.m-style .m_ul li span {
	float: right;
}

.m-style .m_ul_img {
	overflow: hidden;
	margin-top: 30px;
	border-bottom: 1px dashed #ccc;
}

.m-style .m_ul_img li {
	float: left;
	height: 80px;
	text-align: center;
	width: 88px;
}

.m-style .m_ul_img li img {
	height: 50px;
	width: 50px;
}

.m-style .m_p {
	height: 30px;
	line-height: 30px;
	width: 230px;
	margin: 0 auto;
	text-align: center;
}
</style>
<script type="text/javascript">
$(function() {
	var attachmentList,stateList,productList;
	postAjax(false, '${ctx}/crm/param/findAllByPrType?prType=attachmentType',function success(data){attachmentList = data;}, 'json');
	//基金产品
	if('${type}' && ('${type}' == '7')) {
		postAjax(false, '${ctx}/fund/purchase/find/all/product',function success(data){fundList = data;}, 'json');
	}else{
	postAjax(false, '${ctx}/product/purchase/find/all/product',function success(data){productList = data;}, 'json');
	}
	$('#${entityName}_list').datagrid({   
		url:'${ctx}/attachment/get/attachment?id=${id}&type=${type}',
	    rownumbers:true,
	    fitColumns:true,
	    fit:true,
		sortName : 'id',
		sortOrder : 'asc',
	    columns:[[   
			{field : 'atName',title : '附件名',align : 'center',width : fixWidth(0.2),sortable : true},
			{field : 'atType',title : '附件类型',align : 'center',width : fixWidth(0.2),sortable : true,formatter : function(value, rec){return formatVal(attachmentList,value)}},
			{field : 'atCratetime',title : '创建时间',align : 'center',width : fixWidth(0.2),formatter : formatDateYYYYMMDDHHMMSS,sortable : true},
			{field : 'atCreateid',title : '创建人',align : 'center',width : fixWidth(0.2),sortable : true,formatter : formatOperator},
			{field : 'opt',title : '操作',width : 300,align : 'center',formatter : builderOperationLinks} 
	    ]],
	    view: detailview,  
        detailFormatter:function(index,row){ 
            var files; 
        	postAjax(false, '${ctx}/file/get/files?id=' + row.id,function success(data){files = data;}, 'json');
            var innerHtml = '<div id="ddv-' + index + '" style="padding:5px 0">' ;
            for(var i = 0; i< files.length; i++) {
				innerHtml += "<ul class='m_ul'><li>&nbsp;</li></ul><a href='${ctx}/file/download/" + files[i].id + "'>" + files[i].fName + '</a></li></ul>';
            }
            innerHtml +='</div>';
            return innerHtml;  
        },
        onClickRow:function(index, row) {
        	var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-expand')){
				$('#${entityName}_list').datagrid('expandRow',index);
			}else{
				$('#${entityName}_list').datagrid('collapseRow',index);
			}
        }
	}); 
	//格式化操作员
	function formatOperator(value,rec) {
		var val;
		postAjax(false,"${ctx}/crm/param/getStaffName?staffId="+rec.atCreateid,function(data){val = data[0];}, 'json');
		return val;
	}
	
	//构建操作链接
	function builderOperationLinks(value,rec){
		return "<a href='javascript:void(0)' onclick='showRow(" + rec.id + ");'>详细信息</span>&nbsp;&nbsp;"
	}
	
});

function showRow(id){
	$('#${entityName}_list').datagrid('expandRow',id);
}
</script>
<div class="easyui-layout" fit="true">
	<!-- 以下列表 -->
	<div region="center" border="false" style="width: 100%;">
		<table id="${entityName}_list"></table>
	</div>
</div>
