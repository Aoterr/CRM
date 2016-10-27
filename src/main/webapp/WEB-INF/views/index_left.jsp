<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<script type="text/javascript">
function executeMenuURL(url,content){
	var url =  url;
	var text = content;
	if(url!=null&&url!=""){
		if(url.indexOf("?") != -1){
			url += "&random=" + parseInt(100000*Math.random());
		}else{
			url += "?random=" + parseInt(100000*Math.random());
		}
		
		createTabsIframe('tabs',text,url);
	}
}
</script>
<div class="easyui-accordion" fit="true" border="false" style="background: url(${ctx}/static/js/jquery-easyui/ui/images/left_bg.jpg)">
	<div title="客户管理" headerCls="accordion-font-color" iconCls="icon-accordion-node" split="true">
			<ul class="easyui-tree">
				<li>
					<a href="javascript:void(0)" onclick="executeMenuURL('${ctx}/crm/customer','客户信息');"><span>客户信息</span></a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="executeMenuURL('${ctx}/modification/customer','信息变更');"><span>信息变更</span></a>
				</li>
			</ul>
		</div>
		
		<c:if test="${sysuser.userLevel == 2}">
			<div title="用户管理" headerCls="accordion-font-color" iconCls="icon-accordion-node" split="true">
				<ul class="easyui-tree">
					<li>
						<a href="javascript:void(0)" onclick="executeMenuURL('${ctx}/sysuser','用户信息');"><span>用户管理</span></a>
					</li>
				</ul>
			</div>
		</c:if>
		
</div>
