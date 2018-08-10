<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role Update Form</title>
<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<style type="text/css"></style>

<style>
.error {
	color: #ff0000;
}
</style>

<style>
table {
	table-layout: fixed;
	word-wrap: break-word;
}
</style>
</head>

<body>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">角色</a>
		</div>
		<div class="panel-body" class="col-sm-3">

			<form:form method="POST" modelAttribute="role">
				<form:input type="hidden" path="id" id="id" />

				<table class="table table-striped table-hover">
					<tr>
						<td><label for="name">角色名称: </label></td>
						<td><form:input path="name" id="name" /></td>
						<td><form:errors path="name" cssClass="error" /></td>
						<td colspan="2"></td>
					</tr>

					<tr>
						<td><label for="description">描述: </label></td>
						<td><form:input path="description" id="description" /></td>
						<td><form:errors path="description" cssClass="error" /></td>
						<td colspan="2"></td>
					</tr>

					<tr hidden="true">
						<td><label for="available">生效: </label></td>
						<td><form:checkbox path="available" id="available" value="1"
								checked="checked" /></td>
						<td><form:errors path="available" cssClass="error" /></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td><label>分配权限:</label></td>
						<td><input type="checkbox" id="checkall" value="" />选择全部</td>
						<td></td>
						<td colspan="2"></td>
					</tr>
					<c:forEach var="res" items="${resources}" varStatus="status">
						<c:set var="resid" value="${res.id}" scope="session" />

						<%
							String resid = String.valueOf(pageContext.findAttribute("resid"));
									int value = Integer.parseInt(resid);
									String status = "";
									boolean edit_flag = (boolean) request.getAttribute("edit");
									if (edit_flag) {
										List<Integer> rlist = (List<Integer>) request.getAttribute("relID");
										for (int i = 0; i < rlist.size(); i++) {
											Integer temp = rlist.get(i);
											if (value == temp) {
												status = "checked";
												break;
											}
										}

									}
						%>


						<c:choose>
							<%-- 							<c:when test="${status.index=='0'}"> --%>
							<%-- 		                		<tr><td><label>分配权限</label></td><td width="1%">${res.description}: <form:checkbox path="relresources" checked="<%=status%>" value ="${res.id}" style="float: left"/></td> --%>
							<%-- 		                	</c:when> --%>
							<c:when test="${status.index % 4 != '0'}">
								<td width="1%">${res.description}:<form:checkbox
										name="qwe2" class="ss" path="relresources" value="${res.id}"
										checked="<%=status%>" style="float: left" /></td>
							</c:when>
							<c:otherwise>
								</tr>
								<tr>
									<td></td>
									<td width="1%">${res.description}:<form:checkbox
											name="qwe2" class="ss" path="relresources" value="${res.id}"
											checked="<%=status%>" style="float: left" /></td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<tr>
						<td colspan="5"><c:choose>
								<c:when test="${edit}">
									<input type="submit" value="更新" />
								</c:when>
								<c:otherwise>
									<input type="submit" value="增加" />
								</c:otherwise>
							</c:choose></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	Go back to
	<a href="<c:url value='/role-permission' />">角色管理</a>
</body>

<script type="text/javascript">
	//全选/不选

	$(function() {
		document.getElementById('checkall').onclick = function() {

			if (this.checked) {
				$(".ss").prop("checked", true);
			} else {
				$(".ss").prop("checked", false);
			}
		}
	});
</script>
</html>