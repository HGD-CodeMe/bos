 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>layout---页面布局</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
 
	
	
	
	
	
</script>
</head>
<body >
	<!-- 方式一，简单 -->
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">姓名</th>		
				<th data-options="field:'name'">年龄</th>		
				<th data-options="field:'age'">性别</th>	
			</tr>	
		</thead>
	
		<tbody>
			<tr>
				<td>张三</td>
				<td>20</td>
				<td>男</td>
			</tr>
			
			<tr>
				<td>李四</td>
				<td>20</td>
				<td>男</td>
			</tr>
			
		</tbody>
	</table>
	
	<!-- 方式二，从数据库获取数据 -->
	<table class="easyui-datagrid" data-options="url:'/NEW_BOS/json/data.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">学号</th>		
				<th data-options="field:'name'">年龄</th>		
				<th data-options="field:'age'">性别</th>	
			</tr>	
		</thead>
	</table>
	
	<!-- 方式是三 通过js，动态创建datagrid-->
	
	<table id="grid"></table>
	<script type="text/javascript">
		$(function() {
			$("#grid").datagrid({
				columns:[[
							{field:"id",title:'编号',checkbox:true},
							{field:"name",title:'姓名'},
							{field:"age",title:'年龄'}
					    ]],
				url:'/NEW_BOS/json/data.json',	 
				
				toolbar:[
						{text:"添加",iconCls:'icon-add'},
						{text:"删除",iconCls:'icon-remove', handler:function() {
							//获得选中的行
						
							var rows = $("#grid").datagrid("getSelections");
							for ( var i = 0; i < rows.length; i++) {
								var id = rows[i].id;
								alert(id);
							}
						}},
						{text:"修改",iconCls:'icon-edit'},
						],
				pagination:true   
			});
		});
	</script>
	
</body>
</html>