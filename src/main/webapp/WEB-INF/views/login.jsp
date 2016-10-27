<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zendaimoney.constant.ParamConstant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<html>
<head>
<c:set var="namespacePath" value="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CRM Login</title>

<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/login.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/crmmain.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/crm.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cus/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/particular.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/popup.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/extension/validate.js"></script>
<script src="${ctx}/static/js/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/date/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/utilTools.js"></script>
</head>
<body>
	<form id="slick-login" method="post" action="/crm/sysuser/login">
        <label for="username">username</label><input type="text" name="username" id="username" class="placeholder" placeholder="username">
        <label for="password">password</label><input type="password" name="password" id="password" class="placeholder" placeholder="password">
         <a href="javascript:void(0)" class="sel_btn ch_cls" plain="" id="submit" onclick="login();" ><div align="center" style="height:35px;line-height:38px;overflow:hidden;">Login</div></a>
    </form>
</body>
<script type="text/javascript">
var checkSubmitFlg = false;
	function checkSubmit(){
		if(checkSubmitFlg == true){ 
			return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
		}
		checkSubmitFlg = true;
		return true;
	}
 
	
	function login() {
		if (checkSubmit()) {
			$('#slick-login').form('submit', {
				url : '${namespacePath}/index/doLogin',
				onSubmit : function() {
					var username = $("#username").val();
					var password = $("#password").val();
					if(username==null || username=="" ){
						$.messager.alert('提示','用户名不能为空！');
						checkSubmitFlg = false;
						return false;
					}
					if(password==null|| password=="" ){
						$.messager.alert('提示','用密码不能为空！');
						checkSubmitFlg = false;
						return false;
					}
				},
				success : function(result) {				
					var result = eval('(' + result + ')');
					if (result.result.success) {
						window.location.href="${namespacePath}/index/loginSuccess";
					} else {
						$.messager.show({
							title: '提示',
							msg: '登录失败    '+result.result.errMsg
						});
					}
					checkSubmitFlg = false;
				}
			})
		}
	} 
</script>	
</html>