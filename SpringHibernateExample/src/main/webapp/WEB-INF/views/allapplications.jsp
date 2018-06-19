<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>厂商应用列表</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style>  
</head>

  <script type="text/javascript">

   function changeFunc() {
    var selectBox = document.getElementById("selectBox");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    var link = "list-" + selectedValue.toString() + "-application";
    window.location.href=link;
   }

  </script>

<body>
	<div class="panle panel-success ">  
            <div class="panel-heading col-md-10">  
                <a class="panel-title">厂商应用列表</a>  
            </div>  
            <div class="panel-body table-responsive col-md-10">  
                <table class="table table-striped table-hover table-nonfluid table-responsive">  
                    <thead>  
                        <tr>  
                            <th>Name</th>  
                            <th>Vendor</th>
                            <td><a href="<c:url value='/newapplication' />">+ Add Application</a></td> 
                            <td>厂商筛选：<select id="selectBox" onchange="changeFunc();">
 							    <c:forEach items="${vendors}" var="vendor">
									<option value="${vendor.getId()}">${vendor.getName()}<option/>
								</c:forEach>
							</select></td>
							 <td><a href="<c:url value='/list-1-application' />">test</a></td> 
                        </tr>  
                    </thead>  
                    <tbody>  
                       		<c:forEach items="${applications}" var="app">
								<tr>
								<td>${app.name}</td>
								<td>${app.getVendor().getName()}</td>
								<td><a href="<c:url value='/edit-${app.id}-application' />">edit</a></td>
								<td><a href="<c:url value='/delete-${app.id}-application' />">delete</a></td>
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