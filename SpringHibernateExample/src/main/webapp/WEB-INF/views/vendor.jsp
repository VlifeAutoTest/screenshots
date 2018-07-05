<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Vendor Update</title>
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
                <a class="panel-title">厂商</a>  
            </div>  
    <div class="panel-body">  

	<form:form method="POST" modelAttribute="vendor">
		<form:input type="hidden" path="id" id="id"/>

		<table class="table table-striped table-hover">  
	    
			<tr>
				<td><label for="name">名称: </label> </td>
				<td><form:input path="name" id="name"/></td>
				<td><form:errors path="name" cssClass="error"/></td>
		    </tr>
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="保存"/>
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
	Go back to <a href="<c:url value='/vendorlist' />">厂商列表</a>
</body>
</html>