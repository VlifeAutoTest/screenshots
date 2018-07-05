<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@   page   import= "com.vlife.checkserver.mobilestatus.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
<script src="assets/js/jquery.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>主题与字体资源</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 
	<style>	h4{
background:#F8F8F8;
font-size:18px;
color:#6699ff;
}</style>

<script type="text/javascript">
$(document).ready(function(){
$("#fresh").click(function(){
	setTimeout(function () { <%CheckMobileSattus cms =new CheckMobileSattus();cms.run();%> window.location.reload() ;}, 5000);  
  });
});
</script>


</head>

<body>
 <div class="panle panel-success">  
            <div class="panel-heading" >  
           <span class="glyphicon glyphicon-refresh" ></span> <b> &nbsp; 插入新手机时,请 </b><a  id ="fresh" href="#"   >Click here</a>
            </div>  

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">

			<table class="table" frame="void">

						<tbody>
							<tr align="center">
								<c:forEach items="${status}" var="st">
									<td>
										<c:choose>
											<c:when test="${st[1] > 0}">
												<img src="assets/img/blue.jpg" class="img-responsive img-circle" alt="Cinque Terre" width="200" height="200">
											</c:when>
											<c:otherwise>
												<img src="assets/img/red.jpg" class="img-responsive img-circle" alt="Cinque Terre" width="200" height="200"> 
											</c:otherwise>
										</c:choose>
									</td>
								</c:forEach>
							</tr>
							<tr align="center">
								<c:forEach items="${status}" var="st">
	
									<td>${st[0]} Devices: ${st[1]} </td>
								</c:forEach>
							</tr>
						</tbody>
					</table>

			<table id="table1" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>服务器</th>
						<th>状态</th>
						<th>厂商</th>
						<th>名字</th>
						<th>Udid</th>
						<th>操作系统版本</th>
						<th>分辨率</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${devinfo}" var="df">
						<tr>
							<td>${df[0]}</td>
							<td>${df[1]}</td>
							<td>${df[2]}</td>
							<td>${df[3]}</td>
							<td>${df[4]}</td>
							<td>${df[5]}</td>
							<td>${df[6]}</td>

						</tr>
					</c:forEach>
					
				</tbody>
			</table>
								
		</div>
	</div>
			
</div>
</div>
</body>
</html>