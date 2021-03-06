<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@   page import="com.vlife.checkserver.mobilestatus.*"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<script src="assets/js/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>主题与字体资源</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<style type="text/css"></style>
<style>
h4 {
	background: #F8F8F8;
	font-size: 18px;
	color: #6699ff;
}
</style>




</head>

<body>
	<div class="panle panel-success">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-refresh"></span> <b> &nbsp;
				插入新手机时,请 </b><a id="fresh" href='<c:url value="/refresh"></c:url>'>Click here</a>
				<span id="me"></span><span style="color: red" id="time"></span><span id="sess"></span>
				<font id="qwe" size="3" color="red">${message}</font>
		</div>

		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">

					<table class="table" frame="void">

						<tbody>
							<tr align="center">
								<c:forEach items="${status}" var="st">
									<td><c:choose>
											<c:when test="${st[1] > 0}">
												<img src="assets/img/blue.jpg"
													class="img-responsive img-circle" alt="Cinque Terre"
													width="200" height="200">
											</c:when>
											<c:otherwise>
												<img src="assets/img/red.jpg"
													class="img-responsive img-circle" alt="Cinque Terre"
													width="200" height="200">
											</c:otherwise>
										</c:choose></td>
								</c:forEach>
							</tr>
							<tr align="center">
								<c:forEach items="${status}" var="st">

									<td>${st[0]}Devices:${st[1]}</td>
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

<script type="text/javascript">

var n=10;
function showTime(){  
       n=n-1;  
       $("#time").empty();
       $("#time").append(n);
       
       if(n>0){
       setTimeout("showTime()",1000);  
       }
       
   }  ;
	$(document).ready(function() {
		


		$("#fresh").click(function() {
			 $("#me").empty();
			 $("#time").empty();
			 $("#sess").empty() ;
			$("#me").prepend("正在检测手机状态,请稍等, 倒计时") ;
			 $("#time").prepend("10") ;
			 $("#sess").prepend("秒!") ;
			 showTime(); 
			setTimeout(function() {
	window.location.reload();
			}, 10000);
		});

	});
</script>
</html>