<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.zendaimoney.constant.ParamConstant"%>
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
<c:set var="entityName" value="sysuser" />
<!-- 实体名 -->
<c:set var="namespacePath" value="${ctx}/sysuser" />
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
$(function() {
	$.extend($.fn.validatebox.defaults.rules, {   
	    passwordEqual: {   
	        validator: function(value){ 
	        	var i =$('#newPassword').val();
	        	var ii =$('#newPassword2').val();
	        	if(i==ii){return true;}
	        },   
	        message: '两次输入的密码不一致!'  
	    },
	    checkUsername: {   
	        validator: function(value){
	        	var flag = false;
	        	postAjax(false, "${namespacePath}/checkUserNameIsUsed?username=" + value , function(data) { 
		        		var res=JSON.stringify(data);
			        	var result = eval('(' + res + ')');
						if (result.result.success) {
							flag= true;
						}else{
							flag= false;
						}
					}, 'json');
	        	
	        	return flag;
	        },   
	        message: '用户名已存在!'  
	    }
	    
	});
});


var checkSubmitFlg = false;
function checkSubmit(){
	if(checkSubmitFlg == true){ 
		return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
	}
	checkSubmitFlg = true;
	return true;
} 

function save() {
	if (checkSubmit()) {
		$('#submit').linkbutton({  
		    disabled:true
		});
	 //  saveChannel();//2014-9-10 渠道保存
		$('#ff').form('submit', {
			url : '${namespacePath}/save',
			onSubmit : function() {
				if($(this).form('validate')){
					return true;
				}else{
					checkSubmitFlg = false;
					return false;
				}
			},
			success : function(result) {
				$('#submit').linkbutton({  
				    disabled:false
				});
				var result = eval('(' + result + ')');
				if (result.result.success) {
					//window.parent.show('提示','保存成功！');
					$.messager.show({ title: '提示',msg: '保存成功！'});
					closeCurPageUpdateDatagrid('用户信息','用户信息新增','sysuser_list');
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


</script>
<body class="easyui-layout" id="fflayout">
	<div data-options="region:'center'" id="main">
		<form id="ff" method="post">
			<div class="m_title">用户新增</div>
	    	<div class="m_content2" style="width:100%;">
	    		<div class="m_mar">
			    <table  class="m_table" style="width:100%;">
			    	<tr class="tr1">
			       		<td colspan="4">用户信息</td>
			       </tr>
			       <tr class="tr2">
			       		<td colspan="4"></td>
			       </tr>
					<tr>
						<td nowrap="nowrap"><label for="crName" class="bitian">用户名</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="text" name="sysuser_userName" validType="checkUsername" data-options="required:true" maxLength="33"/></td>
											</tr>
					<tr>
						<td nowrap="nowrap"><label for="crEname" class="bitian">用户密码</label></td>
						<td nowrap="nowrap"><input class="easyui-validatebox" type="password" name="sysuser_userPassword" id="newPassword" data-options="required:true"  maxLength="20"/></td>						
					</tr>
				  	<tr>
		      			<td><label class="bitian">重复密码</label></td>
						<td>
							<input class="easyui-validatebox" type="password" name="newPassword2" id="newPassword2" data-options="required:true" validType="passwordEqual"/>
						</td>
			   		</tr>
			   						
					<tr>
						<td nowrap="nowrap"><label  class="bitian1">用户类型 </label></td>
						<td nowrap="nowrap" colspan="3">
							<select name="sysuser_userLevel" class="easyui-validatebox" data-options="required:true">
								<option value="1">普通用户</option>
								<option value="2">系统管理员</option>
							</select>
						</td>
					</tr>					
				</table>
				</div>
			</div>
		</form>
		<div class="m_btn_box" style="width:100%;">
			<div id="formButtom" style="width:100%;text-align:center;background:/* #fafafa */;border:0px solid #8DB2E3;bottom:16px;/* right:40%; */">
				<a href="javascript:void(0)" class="easyui-linkbutton" plain="" onclick="save();" icon="icon-save">保存</a> 
				<a href="#" onclick="closeCurPage('用户信息新增')" class="easyui-linkbutton" plain="" icon="icon-undo">取消</a>
			</div>
		</div>
		
	</div>		
	</div>	 
</body>
