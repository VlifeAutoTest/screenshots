<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>手机列表</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style>  
</head>

<body>
	<div class="panle panel-success ">  
            <div class="panel-heading col-md-10">  
                <a class="panel-title">手机列表</a>  
            </div>  
            <div class="panel-body table-responsive col-md-10">  
                <table class="table table-striped table-hover table-nonfluid table-responsive">  
                    <thead>  
                        <tr>  
                            <th>Name</th>  
                            <th>Uid</th>  
                            <th>Size</th>
                            <th>Os</th>
                            <th>Vendor</th>
                            <td><a href="<c:url value='/newmobile' />">+ Add Mobile</a></td>  
                        </tr>  
                    </thead>  
                    <tbody>  
                       		<c:forEach items="${mobiles}" var="mobile">
								<tr>
								<td>${mobile.name}</td>
								<td>${mobile.uid}</td>
								<td>${mobile.size}</td>
								<td>${mobile.os}</td>
								<td>${mobile.getVendor().getName()}</td>
								<td><a href="<c:url value='/edit-${mobile.uid}-mobile' />">edit</a></td>
								<td><a href="<c:url value='/delete-${mobile.uid}-mobile' />">delete</a></td>
								</tr>
							</c:forEach>
                    </tbody>  
                    <tfoot>  
                        <tr>  
                            <td colspan="8">  
                                <ul class="pagination">  
                                    <li><a href="/mobilelist-${page-1}">&laquo</a></li>  
                                <c:forEach  begin="1" end="${totalPages}" varStatus="loop">  
                                 <c:set var="active" value="${loop.index==page?'active':''}"/>
                              
                    				<li class="${active}"><a href="<c:url value="/mobilelist-${loop.index}"/>">${loop.index}</a></li>
                                
                                 </c:forEach>
                                
                                     
									<li><a href="<c:url value="/mobilelist-${page+1}"/>">&raquo;</a></li>
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