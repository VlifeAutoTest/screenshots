<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TestServer</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
	<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/alert.js"></script>
<style type="text/css"></style>
</head>


<body>
	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">测试服务器</a>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>服务品名称</th>
						<th>IP地址</th>
						<th>用户名</th>
						<th>密码</th>
						<th></th>
						<td><a href="<c:url value='/servernew' />">+ 增加服务器</a></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${servers}" var="server">
						<tr>
							<td>${server.ssn}</td>
							<td>${server.address}</td>
							<td>${server.uname}</td>
							<td>******</td>
							<td><a
								href="<c:url value='/edit-${server.id}-testserver' />">编辑</a></td>
							<td><a class="delete"
								value="<c:url value='/delete-${server.id}-testserver' />"
								href="#">删除</a></td>	
								
								
								
								
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
				</tfoot>
			</table>


		</div>
	</div>
	<br />
	<script type="text/javascript">
		$(".delete").bind("click", function() {
			var va = $(this).attr("value");
			var vb = $(this);
			myConfirm('删除测试服务器', '你确定要删除这个测试服务器吗?', function(r) {
				if (r) {
					window.location.href = va;
				}
			});
		});
	</script>
</body>
</html>