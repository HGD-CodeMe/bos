<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ztree---树形插件</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<!-- 使用div指定区域 -->
	<div title="XX管理系统" data-options="region:'north'" style="height: 100px">北部区域</div>
	<div title="系统菜单" data-options="region:'west'" style="width: 200px">
		<!-- 折叠面板效果 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div title="面板一">
				<a class="easyui-linkbutton" onclick="doAdd();">百度</a>
				<script type="text/javascript">
					function doAdd() {
						//动态添加一个选项卡面板
						$("#tt")
								.tabs(
										"add",
										{
											title : '这个可是动态的',
											content : '<iframe frameborder="0" width="100%" height="100%" src="page_base_staff.action"></iframe>',
											closable : true,
											iconCls : 'icon-search'
										});
					}
				</script>
			</div>
			
			<div title="面板二">
				<ul id="ztree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						var setting = {};//设置ztree相关的属性
						
						//构造json数据
						var zNodes=[
						           {name:'系统管理'},//key值固定，每个json对象 对应每个节点数据
								   {name:'用户管理',children:[
								   							{name:'用户修改'},
								   							{name:'用户添加'}
								                           ]},//json对象对应一个节点数据
								   {name:'权限管理'},
								   ];
						//创建ztree
						$.fn.zTree.init($("#ztree1"),setting,zNodes);				   
								   
					});
				</script>
				<!-- 展示树形菜单 ：使用标准json数据构造-->
				
			</div>
			
			
			<div title="面板三">
				<ul id="ztree2" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						var setting = {
							data: {
								simpleData: {
									enable:true//启用简单json数据描叙节点数据
								}
							
							}
						
						};//设置ztree相关的属性
						
						var zNodes = [
									  {id:'1',pId:'0',name:'系统管理'},
									  {id:'2',pId: '0',name:'用户管理'},
									  {id:'21',pId:'2',name:'用户添加'},
									  {id:'22',pId:'2',name:'用户修改'}
						             ];
						             
						             
					  $.fn.zTree.init($("#ztree2"),setting,zNodes);	
						
					});
				</script>
				<!-- 展示树形菜单 ：使用简单json数据构造-->
							   
				</div>
				
				
			<div title="面板四">
			<!-- 发送ajax请求数据 -->
			<ul id="ztree3" class="ztree"></ul>
				<script type="text/javascript">
				
				    var url="${pageContext.request.contextPath}/json/menu.json";
					
					var setting = {
							data: {
								simpleData: {
									enable:true//启用简单json数据描叙节点数据
								}
							},
							callback: {//绑定事件
								onClick:function(a,b,treeNode) {
									var page = treeNode.page;
									
									
									if(page != undefined) {//需要打开选项卡
									//判断当前选项卡是否已经打开
									var e = $("#tt").tabs("exists",treeNode.name);
									if(e) {
										//已经打开
									    $("#tt").tabs("select",treeNode.name);
									}else {
											
										$("#tt")
								.tabs(
										"add",
										{
											title : treeNode.name,
											content : '<iframe frameborder="0" width="100%" height="100%" src="'+page+'"></iframe>',
											closable : true,
											iconCls : 'icon-search'
										});
									}
							
									
									}
								}
							}
						};
							
					$.post(url,{},function(data){
						  $.fn.zTree.init($("#ztree3"),setting,data);	
					},'json');
				
				</script>
				
					
							   
				</div>	
				
				</div>
			</div>
			
			
					
			
				

	<div data-options="region:'center'">
		<!-- 选项卡面板效果 -->
		<div id="tt" class="easyui-tabs" data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div data-options="closable:true,iconCls:'icon-help'" title="面板一">棉衣一</div>
			<div title="面板二">test2</div>
			<div title="面板三">test3</div>
		</div>
	</div>
	<div data-options="region:'east'" style="width: 100px">东部区域</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>