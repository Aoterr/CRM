<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

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
		$('#pp').portal({
			border : false,
			fit : false
		});
		//add();
		initPagea();
	});

	function initPagea() {
		
		<c:forEach items="${busDesktopToolbar}" var="toolbar">
		$.ajax({
			url : getContextPath() + '/${toolbar.address}',
			type : 'post',
			dataType : 'html',
			timeout : 15000,
			error : function() {
			},
			success : function(html) {
				var code = "#" + '${toolbar.code}';
				$(code).html("");
				$(code).html(html);
			}
		});
		</c:forEach>
	}

	function add() {
		for ( var i = 0; i < 2; i++) {
			var p = $('<div/>').appendTo('body');
			p
					.panel({
						title : 'Title' + i,
						content : '<div style="padding:0px;background:#f0f0f0 ;" class="easyui-tabs"><div title="逾期2" href=""  closable="false" fit="true"  >11</div><div title="逾期" href=""  closable="false" fit="true"  >22</div></div>',
						height : 100,
						closable : true,
						collapsible : true
					});
			$('#pp').portal('add', {
				panel : p,
				columnIndex : i + 1
			});
		}
		$('#pp').portal('resize');
	}
	function remove() {
		$('#pp').portal('remove', $('#pgrid'));
		$('#pp').portal('resize');
	}
	
	function openAnn(annId){
		var url = "${ctx }/sys/announcement/show?id="+annId;
		showCommonDialog("showAnn",url,"查看公告",800,500);
	}
	
	function manageTools(){
		var url = "${ctx}/desktopToolbox/manageTools";
		showCommonDialog("ManageTools",url,"快捷方式管理",300,500);
     	//tabRefresh('tabs','我的桌面');
	}
</script>

<div region="north" class="title" border="false" style="height: 0px;">
</div>
<div region="center" border="false"
	style="height: 100%; overflow: none;">
	<div class="m-style">
		<div id="pp" style="position: relative">
			<div style="width: 25%;">
				<c:forEach items="${busDesktopToolbar}" var="cusRemind">
					<div title="${cusRemind.title}" id="${cusRemind.code}"
						iconCls="icon-remind"></div>
				</c:forEach>
			</div>
			<div style="width: 40%;">
				<div id="pgrid" title="统计" closable="true" style="height:440px "
					iconCls="icon-tablestatic">
					<div id="chartContainer">FusionCharts will load here</div>
					<script type="text/javascript">
                    </script>
				</div>
			</div>
			<div style="width: 35%;">
				<div title="快捷方式" iconCls="icon-tools" closable="true"
					style="padding: 0 10px; overflow: hidden;">
					<ul class="m_ul_img">
					<c:forEach items="${customerDesktopToolbox}" var="box">
						<li>
						<a href="#"
								onclick="createTabsIframe('tabs','${box.boxTitle}','${box.menuUrl }');" title="${box.boxTitle }">
						<img src="${ctx }/static/images/toolico/${box.menuCode }.png"><br />
						${box.boxTitle }</a>
						</li>
						 </c:forEach>
					</ul>
					<p class="m_p">
						<%-- <img src="${ctx}/static/images/plus.png" style="float: left;"><a
							href="#" style="float: left;">&nbsp;添加新的快捷工具</a> --%><img
							src="${ctx}/static/images/manage.png"><a href="#" onclick="manageTools()">&nbsp;管理我的快捷方式</a>
					</p>
				</div>
				
			</div>
		</div>
	</div>
</div>