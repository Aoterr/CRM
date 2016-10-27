﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//System.out.println(validationRule);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>CRM</title>
    <%@ include file="/static/common/meta.jsp"%>
    
	<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
	<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
	<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
	<%@ include file="/static/common/jscsslibs/easyuivalidate.jsp"%>
	
	<%@ include file="/static/common/jscsslibs/easyuiportal.jsp"%>
	<%@ include file="/static/common/jscsslibs/tools.jsp"%>
	<%@ include file="/static/common/jscsslibs/fusioncharts.jsp"%>
	<%@ include file="/static/common/softPhone.jsp"%>
	
	<style type="text/css">
	
	.subtitle{
	font-size:14px;
	font-weight:bold;
	color:#666;
	padding:10px 5px;
}
.m_table {border-collapse:collapse;} 
.m_table tr th.title {text-align:left;background:#E0EDFE;color:#15428B;font-weight:bold;padding:5px;line-height:15px;}
.m_table tr.tr1 {background:#efefef;}
.m_table tr td {border:1px dotted #CCCCCC; line-height:25px; padding:0 4px;}
	
	
	
/*  body {
	font: 12px/20px "微软雅黑", "宋体", Arial, sans-serif, Verdana, Tahoma;
	padding: 0;
	margin: 0;
} */

 

.cs-north {
	height:69px;
	padding:0;
	margin:0;
}

 

.cs-north-logo {background: url(${ctx}/static/images/logo/logo.png) no-repeat;float: left;height: 66px;width: 600px;display: inline-block;behavior: url("jquery-easyui-1.1.2/themes/images/iepngfix.htc");}

/* .cs-north-logo {
	height: 40px;
	margin: 15px 0px 0px 5px;
	display: inline-block;
	color:#000000;font-size:22px;font-weight:bold;text-decoration:none
} */

.cs-west {
	width:130px;padding:0px;
}

.cs-south {height: 21px;border-top: 1px solid #e4e4e4;}

.cs-navi-tab {
	padding: 5px;
}
.cs-tab-menu {
	width:120px;
}
.cs-home-remark {
	padding: 10px;
}
.wrapper {
    float: right;
    height: 30px;
    margin-left: 10px;
}
.ui-skin-nav {
    float: right;
	padding: 0;
	margin-right: 10px;
	list-style: none outside none;
	height: 30px;
}

.ui-skin-nav .li-skinitem {
    float: left;
    font-size: 12px;
    line-height: 30px;
	margin-left: 10px;
    text-align: center;
}
.ui-skin-nav .li-skinitem span {
	cursor: pointer;
	width:10px;
	height:10px;
	display:inline-block;
}
.ui-skin-nav .li-skinitem span.cs-skin-on{
	border: 1px solid #FFFFFF;
}

.ui-skin-nav .li-skinitem span.gray{background-color:gray;}
.ui-skin-nav .li-skinitem span.pepper-grinder{background-color:#BC3604;}
.ui-skin-nav .li-skinitem span.blue{background-color:blue;}
.ui-skin-nav .li-skinitem span.cupertino{background-color:#D7EBF9;}
.ui-skin-nav .li-skinitem span.dark-hive{background-color:black;}
.ui-skin-nav .li-skinitem span.sunny{background-color:#FFE57E;}
.ui-skin-nav .li-skinitem span.liteblue{background-color:#86D2F6;}

</style>
	
	<script type="text/javascript" src="${ctx}/static/js/utils/softPhone.js"></script>
	<script>
		$(function(){
			$(".left_nav_col li").click(function(){
				$(".left_nav_colbg").removeClass("left_nav_colbg");
				$(this).addClass("left_nav_colbg");
			});

			//head中选择某个标签
			$(".head_right div a").click(function(){
			      $(".head_right div a").html('');
				  $(this).html('<span></span>');
			});
			
			//top标签选中
			
		   $('body').layout();
	       $('body').css('visibility', 'visible');

	     initTabs('tabs');
	      
	     
	       $.messager.show({
				title: '${staff.name }&nbsp; 欢迎您！',
				msg: '&nbsp;&nbsp;欢迎使用${sysParam.compName }CRM管理系统！<br>',
				timeout: 4000,
				showType: 'slide' 
			}); 
	       
	        
	       
	       		var themes = {
	       			'default' : '${ctx}/static/js/jquery-easyui/themes/cus/easyui.css',
					'gray' : '${ctx}/static/js/jquery-easyui/themes/gray/easyui.css',
					'pepper-grinder' : '${ctx}/static/js/jquery-easyui/themes/pepper-grinder/easyui.css',
					'blue' : '${ctx}/static/js/jquery-easyui/themes/default/easyui.css',
					'cupertino' : '${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css',
					'dark-hive' : '${ctx}/static/js/jquery-easyui/themes/dark-hive/easyui.css',
					'sunny' : '${ctx}/static/js/jquery-easyui/themes/sunny/easyui.css'
				};

				var skins = $('.li-skinitem span').click(function() {
					var $this = $(this);
					if($this.hasClass('cs-skin-on')) return;
					skins.removeClass('cs-skin-on');
					$this.addClass('cs-skin-on');
					var skin = $this.attr('rel');
					$('#swicth-style').attr('href', themes[skin]);
					setCookie('cs-skin', skin);
					skin == 'dark-hive' ? $('.cs-north-logo').css('color', '#FFFFFF') : $('.cs-north-logo').css('color', '#000000');
				});

				if(getCookie('cs-skin')) {
					var skin = getCookie('cs-skin');
					$('#swicth-style').attr('href', themes[skin]);
					$this = $('.li-skinitem span[rel='+skin+']');
					$this.addClass('cs-skin-on');
					skin == 'dark-hive' ? $('.cs-north-logo').css('color', '#FFFFFF') : $('.cs-north-logo').css('color', '#000000');
				}
				$(".m_table td").has("label").css("text-align","right");
				$.extend($.fn.validatebox.defaults.rules, {   
				    passwordEqual: {   
				        validator: function(value){ 
				        	var i =$('#newPassword').val();
				        	var ii =$('#newPassword2').val();
				        	if(i==ii){return true;}
				        },   
				        message: '两次新密码不一致!'  
				    }   
				});
		});
		
		function setCookie(name,value) {//两个参数，一个是cookie的名子，一个是值
		    var Days = 30; //此 cookie 将被保存 30 天
		    var exp = new Date();    //new Date("December 31, 9998");
		    exp.setTime(exp.getTime() + Days*24*60*60*1000);
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		}

		function getCookie(name) {//取cookies函数        
		    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		     if(arr != null) return unescape(arr[2]); return null;
		}
		
		function show(title,msg){
		       $.messager.show({
		    		title:title,
					msg:msg 
				});
		}	
		function logout(){
			 $.messager.confirm('提示', '确认退出系统吗?', function(result){  
	                if(result) {  
	                	top.location = "${ctx}/index/logout";
	                          
	                }  
	            });  
		}
		function changePassword(){
			$('#${entityName}_dlg').dialog('open').dialog('setTitle','修改密码');
			//$('#${entityName}_fm').form('clear');
			$('#oldPassword').val('');
			$('#newPassword').val('');
			$('#newPassword2').val('');
		}
		function savePassword(){
			$('#${entityName}_fm').form('submit',{
				url: '${ctx}/sys/uc/savePassword',
				onSubmit: function(){
						return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.result.success) {
						show('提示','修改密码成功！');
						$('#${entityName}_dlg').dialog('close');
					} else {
						$.messager.show({
							title: '提示',
							msg: '修改密码失败      '+result.result.errMsg
						});
					}
				}
			})
		}
		function reloadTabGrid(title,top) {
             if ($("#tabs" ).tabs('exists', title)) {
                  $('#tabs').tabs('select' , title);
                  window.top[top].call();
            }
	   }
		
		
	</script>
 
  </head>
  	<script language="JavaScript" type="text/javascript">
  	if(self!=top){
	  parent.tabRefresh('tabs');
	  
	}
	</script>

<body class="easyui-layout" style="visibility: hidden;">
	<!-- 话务系统工具条   隐藏 
	<OBJECT ID="GHOCX"
                CLASSID="CLSID:07ECBD9D-1BCC-4104-B1A3-1B2AC7669072"
                CODEBASE="${ctx}/static/cab/GohighAgent.cab#version=1,0,1,3" width= 100%  height="0">
    </OBJECT>-->	
    <!-- 话务系统工具条 new   隐藏  65pt-->	
    <object id="CSSPHONE" 
			 classid="CLSID:A972798F-50FC-4818-BCE2-2472BC68766C" 
			 codebase="CrystalSoftPhone32.CAB#version=3,2,0,3" style="width: 100%; height:0">
	</object>
	<!-- 头部 -->
	<div region="north" border="false"  split="false" class="cs-north" >
	    <div class="cs-north-bg">
			<div class="cs-north-logo">
				<div class="head_t">
					<div class="head_t_msg" align="right"><img src="${ctx}/static/images/user.png"/> 欢迎您！ ${sysuser.userName } </div>
					<div class="head_t_if">
						
					</div>
				</div>
			</div>
			<div class="" style = "float: right;margin-top: 5px;">
			 
			<div class="head_t_img">
			</div>
			
				<div class="head_t_font" >
						<ul class="ui-skin-nav">			
						<li class="li-skinitem" title="蓝色畅想"><span class="liteblue" rel="default"></span></li>	
						<li class="li-skinitem" title="阳光"><span class="sunny" rel="sunny"></span></li>
						<li class="li-skinitem" title="灰色"><span class="gray" rel="gray"></span></li>
						<li class="li-skinitem" title="胡椒"><span class="pepper-grinder" rel="pepper-grinder"></span></li>
						<li class="li-skinitem" title="蓝色"><span class="blue" rel="blue"></span></li>
						<li class="li-skinitem" title="浅蓝"><span class="cupertino" rel="cupertino"></span></li>
						<li class="li-skinitem" title="黑暗"><span class="dark-hive" rel="dark-hive"></span></li>
						
						</ul>	
		
				</div>
				<div class="head_btn3"><a href="javascript:logout();"></a></div>
				<div class="head_btn4"><a href="javascript:changePassword();"></a></div>				 
			 	 
			</div>
		</div>
	</div>
	<!-- 底部 -->
	<div region="south" border="false" split="true" style="height:30px;padding:0px;">
		<div class="cs-south">
			<div class="bottom_font" align="center">&copy;  <a href="http://www.zendaimoney.com" target="_blank">证大财富</a>  版权所有  Copyright&copy;2012 All Right Reserved</div>
		</div>
	</div>
	<!--左部的菜单-->	
	<div id="contents" region="west" border="true" split="true" title="菜单栏" iconCls="icon-menu" class="cs-west"  href="${ctx}/index/left">
	</div>
	<!-- 右部主框架 -->
	<div id="main" region="center" border="false"   style="overflow:hidden;" >		
		<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="我的桌面" href="${ctx}/index/right"   loadingMessage='数据装载中......' closable="false" fit="true" style="width:100%;padding1:0px;">
		    </div> 
		</div>
	</div>

	<!-- 修改密码 -->
	<div id="${entityName}_dlg" class="easyui-dialog" iconCls="icon-edit" style="width:400px;height:250px;padding:10px 20px" closed="true" buttons="#${entityName}_dlg-buttons">
		<div class="subtitle" >修改密码</div> 
		<form id="${entityName}_fm" method="post">
		    <table  class="m_table" style="width:100%;">
			   <tr>
	      			<td><label>账号</label></td>
					<td><input type="hidden" name="staffid" value="${staff.id}">${staff.name}
			        </td>
			   </tr>
			   <tr>
	      			<td><label class="bitian">原密码</label></td>
					<td>
						<input class="easyui-validatebox" type="password" name="oldPassword" id="oldPassword" data-options="required:true"/>
					</td>
			   </tr>
			   <tr>
	      			<td><label class="bitian">新密码</label></td>
					<td>
						<input class="easyui-validatebox" type="password" name="newPassword" id="newPassword" data-options="required:true"/>
					</td>
			   </tr>
			   			   <tr>
	      			<td><label class="bitian">重复新密码</label></td>
					<td>
						<input class="easyui-validatebox" type="password" name="newPassword2" id="newPassword2" data-options="required:true" validType="passwordEqual"/>
					</td>
			   </tr>
			</table>
	    </form>
	</div>
	<!-- 修改密码保存取消 -->
	<div id="${entityName}_dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePassword()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${entityName}_dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
