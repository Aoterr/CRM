<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/doing.jsp"%>
<!-- 实体名 -->
<c:set var="entityName" value="attachment" />
<c:set var="namespacePath" value="${ctx}/attachment" />
<!-- 文件上传需要引入的相关js -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/swfupload/default.css">
<script type="text/javascript" src="${ctx}/static/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/static/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/static/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/static/swfupload/handlers_add.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/extension/validate.js"></script>

<script type="text/javascript"><!--
var swfu,attachmentSizeList;
$(function() {
	postAjax(false, "${ctx}/crm/param/findAllByPrType?prType=attachmentMaxSize", function(data) {
		attachmentSizeList = data;
	}, 'json');
	var settings = {
		flash_url : "${ctx}/static/swfupload/swfupload.swf",
		flash9_url : "${ctx}/static/swfupload/swfupload_fp9.swf",
		upload_url : "${ctx}/file/upload",
		file_size_limit : "20MB",
		file_types : "*.bmp;*.jpg;*.tiff;*.gif;*.png;*.jpeg;*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.txt;*.mp3;*.wave;*.wav;*.wma;*.*",
		file_types_description : "所有文件",
		file_upload_limit : 100,
		file_queue_limit : 0,
		custom_settings : {cancelButtonId : "btnCancel",progressTarget : "fsUploadProgress",filesQueue: new Array(),resultVoList: new Array()},
		debug : false,
		// Button settings
		button_image_url : "${ctx}/static/swfupload/XPButtonUploadText_61x22.png",
		button_width : "61",
		button_height : "22",
		button_placeholder_id : "spanButtonPlaceHolder",
		button_text : '<span class="theFont">添加文件</span>',
		button_text_style : ".theFont { font-size: 12; }",
		button_text_left_padding : 4,
		button_text_top_padding : 3,
		// The event handler functions are defined in handlers.js
		swfupload_preload_handler : preLoad,
		swfupload_load_failed_handler : loadFailed,
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,//选择文件后出错
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后
		//upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,//上传失败
		upload_success_handler : uploadSuccess,//上传成功
		upload_complete_handler : uploadComplete,//文件上传结束
		queue_complete_handler : queueComplete
	// Queue plugin event
	};
	swfu = new SWFUpload(settings);
	
	$('#${entityName}_list').datagrid({
		toolbar: '#tb'
	});
	
	var obj = loadAttachment();
	//填充附件类型
	comboboxUtil.setComboboxByUrlWithpanelHeight('atTypeOne', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+obj.atTypeOne, 'prValue', 'prName', '150', true,function(parent){
		//$('#atType').combobox('showPanel');
	});
	comboboxUtil.setComboboxByUrlWithpanelHeight('atType', '${ctx}/crm/param/findAllByPrTypeAndPrValue?prType=attachmentType&prValue='+obj.atType, 'prValue', 'prName', '150', true, function(rec) {
		//$('#atName').val(rec.prName);
	});//获取二级附件
// 	$('#atTypeOne').combobox('textbox').focus(function(){
// 		$('#atTypeOne').combobox('showPanel');
// 		});
	//初始化验证信息
	//initValidationInfo();
	$('#ff').form('load',obj);
});

function loadAttachment() {
	var obj;
	//加载附件信息
	var url1 = ("${ctx}/attachment/get?id=${id}");
	//console.info(url1);
	postAjax(false, url1, function(data) {
		//console.info(data);
		obj = data;
	},'json');
	return obj;
}

/***********上传**********/
function upload() {
// 	if (!$('ff').form('validate')) {
// 		alert('请您将信息选填完整.');
// 		return;
// 	}
	if (swfu.getStats().files_queued > 0) {
	//document.getElementById("btnCancel").disabled = true;
		swfu.startUpload();
	} else {
		alert("请选择要上传的文件!");
	}
}

<!-- 停止上传 -->
function stop() {
	
}
-->
</script>


<body class="easyui-layout" id="main">
	<div data-options="region:'center'" >
	    <form id="ff" method="post" action="${namespacePath}/save">
	    	<input type="hidden" id="id1" name="id1" value="${busiId }">
	    	<input type="hidden" id="type" name="type" value="<%=ParamConstant.ATTACHMENT_TYPE_CUSTOMER %>">
	    	<input type="hidden" id="customerid1" name="customerid1" value="${busiId }">
		    <table  class="m_table" style="width:888px;">
		    	<tr>
					<th class="title" colspan="8">新增附件文件</th>
				</tr>
			   <tr>
					<td><label for="atType">附件类型</label></td>
					<td><input id="atTypeOne" class="easyui-combobox" name="atTypeOne" disabled="disabled"/>
						<input id="atType" class="easyui-combobox" name="atType" disabled="disabled"/>
					</td>
			   </tr>
			   <tr>
	      			<td><label for="atName">附件名</label></td>
					<td><input id="atName" name="atName" disabled="disabled"/></td>
			   </tr>
			   <tr>
	      			<td><label for="atMemo">备注</label></td>
					<td colspan="3"><textarea name="atMemo" id="atMemo" style="height:80px;width: 600px;" disabled="disabled"></textarea></td>
			   </tr> 
			</table>
			
<!-- 			<table width="width:100%;"> -->
<!-- 				<tr> -->
<!-- 					<td valign="bottom"> -->
<!-- 						<span id="spanButtonPlaceHolder"></span> -->
<!-- 						<input id="btnCancel"  type="button" value="取消上传" onclick="swfu.cancelQueue();" disabled="disabled" />  -->
<!-- 						<span id="divStatus">已上传0个文件</span> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td colspan="3"> -->
<!-- 						<div id="fsUploadProgress"></div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
			<table id="${entityName}_list" class="easyui-datagrid" style="width: 888px;" data-options="striped:true,singleSelect:true,autoRowHeight:true,rownumbers:true">  
		    <thead>  
		        <tr>
		        	<th data-options="field:'fileIndex',width:280,hidden:true">fileIndex</th>
		        	<th data-options="field:'id',width:280,hidden:true">ID</th>
		            <th data-options="field:'name',width:280">文件名</th>  
		            <th data-options="field:'size',width:100">文件大小</th>  
		            <th data-options="field:'pro',width:230,align:'center'">进度</th>  
		            <th data-options="field:'state',width:120">状态</th>
		            <th data-options="field:'option',width:100,align:'center'">操作</th>  
		        </tr>  
		    </thead>  
		</table>
	    </form>
<!--    	     <div style="background:#fafafa;text-align:center;padding:5px;width: 878px;">   -->
<!--             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save();">保存</a> -->
<%-- 			<a href="${namespacePath}/show/customer/attachment?customerid=${id}" class="easyui-linkbutton">取消</a> --%>
<!--         </div>  -->
	</div>
	<div id="tb">
		<a id="spanButtonPlaceHolder"></a>
		<a href="javascript:void(0);" onclick="upload();" class="easyui-linkbutton">上传文件</a>
<!-- 		<button onclick="javascript:void(0)">上传文件</button> -->
<!-- 		<a id="btnCancel" href="javascript:void(0);" onclick="swfu.stopUpload();" class="easyui-linkbutton">停止上传</a> -->
<!-- 		<button>停止上传</button> -->
<!-- 		<a href="javascript:void(0);" onclick="stop();" class="easyui-linkbutton">停止上传</a> -->
<!-- 		<button>取消所有</button> -->
<%-- 		<a href="${namespacePath}/show/customer/attachment?customerid=${id}" class="easyui-linkbutton">返回上一页</a> --%>
			<a href="#" class="easyui-linkbutton" onclick="history.go(-1);return false;">返回上一页</a>
	</div>
</body>



