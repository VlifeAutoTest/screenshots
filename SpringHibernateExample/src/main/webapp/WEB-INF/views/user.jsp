<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>手机应用</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<style type="text/css"></style>
<style>
select {
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	border: 1px solid #C2C2C2;
	box-shadow: 1px 1px 4px #EBEBEB;
	-moz-box-shadow: 1px 1px 4px #EBEBEB;
	-webkit-box-shadow: 1px 1px 4px #EBEBEB;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	padding: 7px;
	outline: none;
	width: 18%;
}
</style>

</head>

<body>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">用户</a>
		</div>
		<div class="panel-body">

			<form:form method="POST" modelAttribute="user">
				<form:input type="hidden" path="id" id="id" />
				<table class="table table-striped table-hover table-responsive">
					<tr>
						<td><label for="name">用户名: </label></td>
						<td><form:input path="name" id="name" /></td>
						<td><form:errors path="name" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="passwd">密码: </label></td>
						<td><form:input path="passwd" id="passwd" type="password"/></td>
						<td><form:errors path="passwd" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="email">Email: </label></td>
						<td><form:input path="email" id="uemail" /></td>
						<td><form:errors path="email" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="is_active">生效: </label></td>
						<td><form:checkbox path="is_active" id="available" value="1" checked="checked"/></td>
						<td><form:errors path="is_active" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label for="role">角色: </label></td>
						<c:choose>
							<c:when test="${edit}">
								<td><form:select path="role.id">
										<c:forEach var="item" items="${roles}">
											<c:choose>
												<c:when test="${rname==item.getName()}">
													<form:option selected="true" value="${item.getId()}"
 														label="${item.getName()}" /> 
												</c:when> 

 												<c:otherwise> 
 													<form:option value="${item.getId()}" 
 														label="${item.getName()}" /> 
 												</c:otherwise> 
 											</c:choose> 
 										</c:forEach> 
 									</form:select></td> 

							</c:when>

							<c:otherwise>
								<td><form:select path="role.id" items="${roles}"
										multiple="false"  itemValue="id" itemLabel="name"/></td>
								<td><form:errors path="role" cssClass="error" /></td>
							</c:otherwise>
						</c:choose>
					</tr>

					<tr>
						<td colspan="3"><c:choose>
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
	<a href="<c:url value='/userlist' />">用户管理</a>
</body>
</html><title>Insert title here</title>
</head>
<body>

</body>
</html>