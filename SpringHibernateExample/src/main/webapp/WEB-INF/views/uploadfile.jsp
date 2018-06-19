<%@ page language="java" contentType="text/html; charset= UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Employee Registration Form</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 

</head>

<body>

	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">上传资源</a>  
            </div>  
    <div class="panel-body">  
    
  <form:form method="post"  enctype="multipart/form-data">
		<input type="file" name="file">
		<br>
		<input type="submit" value="上传" class="btn btn-success"/>
		</form:form>
    
    
 
	
</div>
</div >
	Go back to <a href="<c:url value='/themelist' />">List of All Themes</a>
</body>
</html>