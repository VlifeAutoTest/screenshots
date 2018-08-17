<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>厂商应用列表</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/alert.js"></script>
<style type="text/css"></style>
</head>

<script type="text/javascript">
	function changeFunc() {
		var selectBox = document.getElementById("selectBox");
		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
		var link = "applicationlist-1-" + selectedValue.toString();
		window.location.href = link;

	}
</script>

<body>
	<div class="panle panel-success ">
		<div class="panel-heading col-md-10">
			<a class="panel-title">用户管理</a>
		</div>
		<div class="panel-body table-responsive col-md-10">
			<table
				class="table table-striped table-hover table-nonfluid table-responsive">
				<thead>
					<tr>
						<th>名称</th>
						<th>密码</th>
						<th>Email</th>
						<th>角色</th>
						<td><a href="<c:url value='/newuser' />">+ 增加用户</a></td>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user.name}</td>
							<td>******</td>
							<td>${user.email}</td>
							<td>${user.getRole().getName()}</td>
							<td><a href="<c:url value='/edit-${user.id}-user' />">编辑</a></td>
							<td><a class="delete"
								value="<c:url value='/delete-${user.id}-user' />"
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
			myConfirm('删除用户', '你确定要删除这个用户吗?', function(r) {
				if (r) {
					window.location.href = va;
				}
			});
		});
	</script>
</body>
</html>