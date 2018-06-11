<%@ page language="java"  pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Themes and Fonts</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style>  

</head>


<body>
	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">主题与字体资源</a>  
            </div>  
            <div class="panel-body">  
                <table class="table table-striped table-hover">  
                    <thead>  
                        <tr>  
                            <th>Name</th>  
                            <th>Path</th>  
                            <th></th>  
                            <td><a href="<c:url value='/newtheme' />">+ Add Resource</a></td>  
                        </tr>  
                    </thead>  
					   <tbody>
							<c:forEach items="${themes}" var="theme">
								<tr>
								<td>${theme.name}</td>
								<td>${theme.path}</td>
								<td><a href="<c:url value='/edit-${theme.id}-theme' />">edit</a></td>
								<td><a href="<c:url value='/delete-${theme.id}-theme' />">delete</a></td>
								</tr>
							</c:forEach>
						</tbody>  
                    <tfoot>  
                        <tr>  
                            <td colspan="8">  
                                <ul class="pagination">  
                                    <li><a href="#">«</a></li>  
                                    <li><a href="#">1</a></li>  
                                    <li><a href="#">2</a></li>  
                                    <li><a href="#">3</a></li>  
                                    <li><a href="#">4</a></li>  
                                    <li><a href="#">5</a></li>  
                                    <li><a href="#">»</a></li>  
                                </ul>  
                            </td>  
                        </tr>  
                    </tfoot>  
                </table>
            </div>
            </div>

</body>
</html>