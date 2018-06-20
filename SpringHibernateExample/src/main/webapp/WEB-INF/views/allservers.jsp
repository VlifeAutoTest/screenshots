<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>TestServer</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style>  
</head>


<body>
	<div class="panle panel-success">  
            <div class="panel-heading" >  
                <a class="panel-title">测试服务器</a>  
            </div>  
            <div class="panel-body">  
                <table class="table table-striped table-hover">  
                    <thead>  
                        <tr>  
                            <th>Ssn</th>  
                            <th>Address</th>
                            <th>User Name</th>
                            <th>Password</th>   
                            <th></th>  
                            <td><a href="<c:url value='/servernew' />">+ Add Server</a></td>  
                        </tr>  
                    </thead>  
                    <tbody>  
                       		<c:forEach items="${servers}" var="server">
								<tr>
								<td>${server.ssn}</td>
								<td>${server.address}</td>
								<td>${server.uname}</td>
								<td>******</td>
								<td><a href="<c:url value='/edit-${server.ssn}-testserver' />">edit</a></td>
								<td><a href="<c:url value='/delete-${server.ssn}-testserver' />">delete</a></td>
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
	<br/>
	
</body>
</html>