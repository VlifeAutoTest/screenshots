<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TestServer</title>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/alert.js"></script>
<style type="text/css"></style>
</head>


<body>
	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">角色管理</a>
		</div>
		<div class="panel-body" class="col-sm-3">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>角色名</th>
						<th>描述</th>
						<th></th>
						<th></th>
						<td><a href="<c:url value='/newrole' />">+ 增加角色</a></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${roles}" var="role">
						<tr>
							<td>${role.name}</td>
							<td>${role.description}</td>
							<td><a
								href="<c:url value='/edit-${role.id}-role' />">编辑</a></td>
								
							<td><a class="delete"
								value="<c:url value='/delete-${role.id}-role' />"
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
			myConfirm('删除用户组', '你确定要删除这个用户组吗?', function(r) {
				if (r) {
					window.location.href = va;
				}
			});
		});
	</script>
</body>
</html>