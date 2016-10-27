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
<c:set var="entityName" value="attachment"  /><!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/attachment"  /><!-- 命名空间 -->
<script type="text/javascript">
$(function() {
	//$(".m_table td").has("label").css("text-align","right");
	
	$.post('${namespacePath}/get', {id : ${attachmentId}}, function(row) {
		//获取附件类型
		comboboxUtil.setComboboxByUrl('atTypeOne', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+row.atTypeOne, 'prValue', 'prName', '150', false);
		comboboxUtil.setComboboxByUrl('atType', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+row.atType, 'prValue', 'prName', '150', false);
		$('#ff').form('load',row);
	}, 'json');

	$('#detail').datagrid({ 
		url:encodeURI("${ctx}/file/get/files?id=" + ${attachmentId}),
	    columns:[[ 
			{field:'id',title:'ID',width:fixWidth(0.15),hidden:true},
			{field:'fName',title:'文件名',width:fixWidth(0.15),formatter: formatFileName},  
			{field:'fCratetime',title:'上传时间',width:fixWidth(0.15),align:'center', formatter: formatDateYYYYMMDDHHMMSS},  
			{field:'fCreateid',title:'上传人',width:fixWidth(0.15),align:'center',formatter : formatOperator},  
			{field:'fModifytime',title:'最后修改时间',width:fixWidth(0.15), align:'center',formatter: formatDateYYYYMMDDHHMMSS},  
			{field:'fModifyid',title:'最后修改人',width:fixWidth(0.10),align:'center', formatter : formatOperator},
			{field:'fOrderNo',title:'序号',width:fixWidth(0.05),align:'center',hidden:true/*, formatter : formatOperator*/},
			{field:'option',title:'操作',width:fixWidth(0.10),align:'center', formatter : formatOption},
			{field:'order',title:'顺序调整',width:fixWidth(0.10),align:'center', formatter : formatOrder}
	    ]]/*,
	    onDblClickRow:function(rowIndex, rowData){
	    	loadCustomerInfo(rowData)//加载客户信息
		    loadBankInfo(rowData);//加载银行信息
		    loadContactPerson(rowData);//加载联系人
			$('#dd').dialog('close');
		}*/  
	}); 

	function formatOption(value,rec) {
		return '<a href="javascript:void(0)" onclick="deleteOneFile(' + rec.id + ')" >删除</a>';
	}
	
	function formatOrder(value,rec) {
		return '<button onclick="move(event,this,true)">↑</button><button  onclick="move(event,this,false)">↓</button>';
	}
	
	//格式化操作员
	function formatOperator(value,rec) {
		var val;
		postAjax(false,"${ctx}/crm/param/getStaffName?staffId="+rec.fCreateid,function(data){val = data[0]}, 'json');
		return val;
		//return '王武';
	}

	//格式化文件名
	function formatFileName(value, rec) {
		return '<a href="${ctx}/file/download/' + rec.id + '" >' + value + '</a>';
	}
	initValidationInfo();
});

function move(e, target, isUp) {
	var $view = $(target).closest('div.datagrid-view');
	var index = $(target).closest('tr.datagrid-row').attr('datagrid-row-index');
	var $row = $view.find('tr[datagrid-row-index=' + index + ']');
// 	console.info($(this).prev());
// 	$(this).prev()
// 	console.info($(this));
// 	console.info($('.datagrid-cell-c1-id'));
// 	console.info($('.datagrid-cell-c1-fOrderNo'));
// 	console.info($(this).next());
// 	console.info($($view).('tr.datagrid-row'));
	if (isUp) {
		$row.each(function() {
			$(this).prev().before($(this));
		});
	} else {
		$row.each(function() {
			$(this).before($(this).next());
		});
	}
// 	var $btable = $(target).closest('datagrid-row');
// 	console.info($btable);
// 	console.info($('#detail').datagrid('getRows'));
	$row.removeClass('datagrid-row-over');
	e.stopPropagation();
}

function deleteOneFile(fileId) {
	if(confirm('确认删除文件?')){
		var url = (getContextPath() + "/file/delete/one?id=" + fileId);
		postAjax(false, url, function(data) {
			var result1 = data.result;
			if(result1.success) {
				$.messager.show({ title: 'success',msg: "删除成功!"});
				$('#detail').datagrid('load',{});
			}else{
				$.messager.show({title: 'Error',msg: "删除失败,请稍后重试!"})
			} 
		},'json');
	}
}

//初始化验证信息
function initValidationInfo() {
	$('#atName').validatebox({required: true, validType: 'maxlength[40]'});
	$('#atMemo').validatebox({required: false, validType: 'maxlength[40]'});
}

function saveChange() {
	var idArr = $('.datagrid-cell-c1-id');
	var noArr = $('.datagrid-cell-c1-fOrderNo');
	var file;
	var resultArr = new Array();
	var resultStr = '';
	for (var i = 0; i < idArr.length; i++) {
		var fileId = idArr[i].innerText;
		var fileNo = noArr[i].innerText;
		resultStr += fileId + ',' + fileNo + ';'
// 		file = new Object();
// 		console.info(fileId);
// 		console.info(fileNo);
// 		file.id=fileId;
// 		file.fOrderNo=fileNo;
// 		resultArr.push(file);
	}
// 	for (var j = 0; j < resultArr.length; j++) {
// 		console.info(resultArr[j]);
// 	}
// 	console.info('------------------------------------------------------------------------------');
	if (!$('ff').form('validate')) {
		return;
	}
	var atName = encodeURIComponent($('#atName').val());
// 	alert(atName);
	var atMemo = encodeURIComponent($('#atMemo').val());
// 	alert(atMemo);
	postAjax(false, getContextPath() + "/attachment/update/one?attachmentId=${attachmentId}&atName=" + atName + "&atMemo=" + atMemo + "&fileOrderList=" + resultStr, 
			function(data) {
			var result = JSON.stringify(data);
			var res = eval('('+result+')').result;
			if(res.success) {
				$.messager.show({ title: 'success',msg: "修改成功!"});
				//redirect('${namespacePath}/show/customer/attachment?customerid=${id}');
				self.location=document.referrer;
			}
			});
	//console.info($('.datagrid-cell-c1-id')[0].innerText);
	//console.info($('.datagrid-cell-c1-fOrderNo')[0].innerText);
}

</script>

<body class="easyui-layout">
	<div data-options="region:'center'" >
	    <form id="ff" method="post" action="${namespacePath}/save">
		    <table  class="m_table" style="width:100%;">
		    	<tr>
					<th class="title" colspan="8">附件编辑页面</th>
				</tr>
			   <tr>
	      			<td><label for="atName">附件名</label></td>
					<td><input id="atName" name="atName"></td>
					<td><label for="atType">附件类型</label></td>
					<td><input id="atTypeOne" class="easyui-combobox" name="atTypeOne" disabled="disabled"/>
						<input id="atType" class="easyui-combobox" name="atType" disabled="disabled"/>
					</td>
			   </tr>
			   <tr>
	      			<td><label for="atMemo">备注</label></td>
					<td colspan="3"><textarea id="atMemo" name="atMemo"  style="height:80px;width: 400px;"></textarea></td>
			   </tr> 
			</table>
			<br><br>
	    </form>
			<table width="100%">                                              
			    <tr><td colspan="4"><table id="detail" width="100%"></table></td></tr>
	        </table>
   	     <div style="background:#fafafa;text-align:center;padding:5px">
   	     	<a href="${namespacePath}/customer/toCusAttachmentAdd?id=${attachmentId}&busiId=${id}" class="easyui-linkbutton">添加附件文件</a>
   	     	<a href="#" class="easyui-linkbutton" onclick="saveChange();">提交更改</a>  
            <a href="#" class="easyui-linkbutton" onclick="history.go(-1);return false;">返回</a>  
        </div> 
	</div>
</body>