<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Vendor</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<style type="text/css"></style>
</head>


<body>
	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">厂商</a>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>厂商名称</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${vendors}" var="vendor">
						<tr>
							<td>${vendor.name}</td>
							<td>
							</td>
							<td><a href="<c:url value='/delete-${vendor.id}-vendor' />">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
				</tfoot>
			</table>


		</div>
	</div>
	<br />

</body>
</html>