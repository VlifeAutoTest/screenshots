<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>主题与字体资源</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 
	<style>	h4{
background:#F8F8F8;
font-size:18px;
color:#6699ff;
}</style>


</head>

<body>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">

			<table class="table" frame="void">

						<tbody>
							<tr align="center">
								<td>
									<img src="assets/img/blue.jpg" class="img-responsive img-circle"  alt="Cinque Terre" width="200" height="200"> 
								</td>
								<td>
									<img src="assets/img/blue.jpg" class="img-responsive img-circle" alt="Cinque Terre" width="200" height="200"> 
								</td>
								<td>
									<img src="assets/img/red.jpg" class="img-responsive img-circle" alt="Cinque Terre" width="200" height="200"> 
								</td>
								<td>
									<img src="assets/img/blue.jpg" class="img-responsive img-circle" alt="Cinque Terre" width="200" height="200"> 
								</td>
							</tr>
							<tr  align="center">
								<td >
									qa1 Devices: 5
								</td>
								<td>
									qa2 Devices: 2
								</td>
								<td>
									qa3 Devices: 0
								</td>
								<td>
									qa4 Devices: 1
								</td>
							</tr>
						</tbody>
					</table>

			<table id="table1" class="table">
				<thead>
					<tr>
						<th>编号</th>
						<th>厂商</th>
						<th>型号</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<tr><td><font size="3" color="#6699ff">QA1</font></td></tr>
					<tr class="success">
						<td>1</td>
						<td>vivo</td>
						<td>xplay5</td>
						<td>Free</td>
					</tr>
					<tr class="success">
						<td>2</td>
						<td>oppo</td>
						<td>x20</td>
						<td>Free</td>
					</tr>
					<tr class="info">
						<td>3</td>
						<td>vivo</td>
						<td>xxxxx</td>
						<td>Busy</td>
					</tr>
					<tr class="warning">
						<td>4</td>
						<td>huawei</td>
						<td>mate9</td>
						<td>Pending</td>
					</tr>
					<tr class="success">
						<td>5</td>
						<td>haier</td>
						<td>xxxxx</td>
						<td>Free</td>
					</tr>
					<tr><td><font size="3" color="#6699ff">QA2</font></td></tr>
					<tr class="success">
						<td>1</td>
						<td>vivo</td>
						<td>xplay7</td>
						<td>Free</td>
					</tr>
					<tr class="warning">
						<td>2</td>
						<td>oppo</td>
						<td>x20</td>
						<td>Free</td>
					</tr>
					<tr>
						<td>3</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>4</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>5</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr><td><font size="3" color="#6699ff">QA3</font></td></tr>
					<tr>
						<td>1</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>2</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>3</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>4</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>5</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr><td><font size="3" color="#6699ff">QA4</font></td></tr>
					<tr class="success">
						<td>1</td>
						<td>vivo</td>
						<td>xplay5</td>
						<td>Free</td>
					</tr>
					<tr>
						<td>2</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>3</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>4</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>5</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			
			<h4>QA4</h4>
			<table id="table4" class="table">
				<tbody>
					<tr class="success">
						<td>1</td>
						<td>vivo</td>
						<td>xplay5</td>
						<td>Free</td>
					</tr>
					<tr>
						<td>2</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>3</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>4</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>5</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>										
		</div>
	</div>
			
</div>

</body>
</html>