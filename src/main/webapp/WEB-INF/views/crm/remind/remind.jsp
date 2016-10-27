<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<c:forEach items="${remindlist}" var="item">


	<ul class="m_ul m_ul2"> 
		<li class="m_li"><a 
		<c:if test="${!empty item.dailyRemindLink}">
			href="javascript:createTabsIframe('tabs','${item.dailyRemindContent }','${ctx }/${item.dailyRemindLink }&tab=true');"
		</c:if> 
			style=""> ${item.dailyRemindContent } </a></li>
	</ul>


</c:forEach>


