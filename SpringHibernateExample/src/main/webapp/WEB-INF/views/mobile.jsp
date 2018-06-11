<%@ page language="java" 
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>mobile Form</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
   <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
   <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>

	<h2>Mobile Form</h2>
 
	<form:form method="POST" modelAttribute="mobile">
		<form:input type="hidden" path="id" id="id"/>
		<table>
			<tr>
				<td><label for="name">Name: </label> </td>
				<td><form:input path="name" id="name"/></td>
				<td><form:errors path="name" cssClass="error"/></td>
		    </tr>
	    
			<tr>
				<td><label for="vendor">Vendor: </label> </td>
				<td><form:input path="vendor" id="vendor"/></td>
				<td><form:errors path="vendor" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td><label for="uid">UID: </label> </td>
				<td><form:input path="uid" id="uid"/></td>
				<td><form:errors path="uid" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td><label for="status">Status: </label> </td>
				<td><form:input path="status" id="status"/></td>
				<td><form:errors path="status" cssClass="error"/></td>
		    </tr>
		    
<!-- 	    	<tr>  -->
<!-- 				<td><label for="tserver">Server_ID: </label> </td>  -->
<%-- 				<td><form:select path="tserver" items="${tserver.id}" --%>
<%--                     multiple="true" /></td> --%>
<%-- 				<td><form:errors path="tserver" cssClass="error"/></td> --%>
<!-- 	    	</tr>  -->
	
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="save"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
	<br/>
	<br/>
	Go back to <a href="<c:url value='/list' />">List of All Employees</a>
</body>
</html>