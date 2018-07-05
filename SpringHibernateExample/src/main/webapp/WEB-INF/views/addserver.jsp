<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Server Update Form</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>

	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">测试服务器</a>  
            </div>  
    <div class="panel-body">  

	<form:form method="POST" modelAttribute="server">
		<form:input type="hidden" path="id" id="id"/>

		<table class="table table-striped table-hover">  
			<tr>
				<td><label for="ssn">服务器名称: </label> </td>
				<td><form:input path="ssn" id="ssn"/></td>
				<td><form:errors path="ssn" cssClass="error"/></td>
				<td>${errorInfo}</td>
		    </tr>
	    
			<tr>
				<td><label for="address">IP地址: </label> </td>
				<td><form:input path="address" id="address"/></td>
				<td><form:errors path="address" cssClass="error"/></td>
				<td></td>
		    </tr>
		    
		    <tr>
				<td><label for="uname">用户名: </label> </td>
				<td><form:input path="uname" id="uname"/></td>
				<td><form:errors path="uname" cssClass="error"/></td>
				<td></td>
		    </tr>
		    <tr>
				<td><label for="passwd">密码: </label> </td>
				<td><form:input path="passwd" id="passwd" type="password"/></td>
				<td><form:errors path="passwd" cssClass="error"/></td>
				<td></td>
		    </tr>
			<tr>
				<td colspan="4">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="更新"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="增加"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
	Go back to <a href="<c:url value='/serverlist' />">服务器列表</a>
</body>
</html>