<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs---选项卡面板</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	
	<div title="XX管理系统" data-options="region:'north'" style="height: 100px">北部区域</div>
	<div title="系统菜单" data-options="region:'west'" style="width: 200px">
		<!-- 折叠面板效果 -->
		<div class="easyui-accordion" data-options="fit:true">
		<div title="面板一">
			<a class="easyui-linkbutton" onclick="doAdd();">百度</a>
			<script type="text/javascript">
				function doAdd() {
					//添加一个动态面板
					$("#tt").tabs("add",{
						title:"这个是动态的",
						content:"<iframe frameborder=\"0\" width=\"100%\" height=\"100%\" src=\"page_base_staff.action\"></iframe>",
						closable:true,
						iconCls:"icon-search"
					});
				};
			</script>
		</div>
		<div title="面板二"></div>
		<div title="面板三"></div>
		</div>
	</div>
	
	<div  data-options="region:'center'">
	<!-- tabs效果 -->
		<div id="tt" class="easyui-tabs">
		<div data-options="closable:true,iconCls:'icon-add'" title="面板一"></div>
		<div data-options="closable:true" title="面板二"></div>
		<div title="面板三"></div>
		</div>
	</div>
	
	
	<div data-options="region:'east'" style="width: 100px">东部区域</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>