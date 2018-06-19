<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>主题与字体资源</title>
	
	<script src="http://upcdn.b0.upaiyun.com/libs/jquery/jquery-2.0.2.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 

<script>



$(document).ready(function(){
	
	var aa=${edit};
	
	if (aa) {
		$("#wq").css("display","none");
	
}
else{
	$("#we").css("display","none");
};
	});
 



</script>
</head>

<body>

	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">主题与字体资源更新</a>  
            </div>  
    <div class="panel-body">  
 
	<form:form method="POST" modelAttribute="theme"  enctype="multipart/form-data"  >
		<form:input type="hidden" path="id" id="id"/>
		<table class="table table-striped table-hover">
			<tr>
				<td><label for="name">Name: </label> </td>
				<td><form:input path="name" id="name"/></td>
				<td><form:errors path="name" cssClass="error"/></td>
		    </tr>
		    <form:input type="hidden" path="path" id="path]"/>
		    <tr id ="we" >
				<td><label for="path">Path: </label> </td>
				<td><form:input path="path" id="path" /></td>
				<td><form:errors path="path" cssClass="error"/></td>
		    </tr>
	
			 <tr id ="wq">
				<td><label for="">File: </label> </td>
				<td><input type="file" name="file" ></td>
				<%--  <td><form:errors path="path" cssClass="error"/></td>  --%>
		    </tr>
	
	
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Save" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
	Go back to <a href="<c:url value='/themelist' />">List of All Themes</a>
</body>
</html>