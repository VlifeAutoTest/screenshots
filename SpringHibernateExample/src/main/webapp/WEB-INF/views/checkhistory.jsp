<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>厂商应用列表</title>
<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrap-select.js"></script>
<script src="assets/js/bootstrap.min-3.0.0.js"></script>
<script src="assets/js/alert.js"></script>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap-select.css">
<link href="assets/css/bootstrap.min.3.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="assets/css/xcConfirm.css" />
<script src="assets/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>

<style type="text/css"></style>
<style>
select {
	box-sizing: border-box;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	border: 1px solid #C2C2C2;
	box-shadow: 1px 1px 4px #EBEBEB;
	-moz-box-shadow: 1px 1px 4px #EBEBEB;
	-webkit-box-shadow: 1px 1px 4px #EBEBEB;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	padding: 7px;
	outline: none;
	width: 18%;
}
#mytxt {
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
	z-index: -10;
}
</style>




</head>



<body>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">审核历史</a>
		</div>
		<div class="panel-body">

			<div class="panel-body table-responsive">
				

					<table
						class="table table-striped table-hover table-nonfluid table-responsive "
						id="result_table">


						<thead>
							<tr>
								<th>厂商</th>
								<th>手机</th>
								<th>资源(审核序号)</th>
								<th>应用</th>
								<th>截图类型</th>
								<th>图片路径</th>
								<th></th>
								<th>开始时间</th>
								<th>结束时间</th>
								<!-- <th>日志</th> -->

							</tr>
						<thead>
						<tbody>
							<c:choose>
								<c:when test="${queryflag}">

									<c:forEach items="${detail}" var="dt">
										<tr>
											<td>${dt[0]}</td>
											<td>${dt[1]}</td>
											<td>${dt[3]}</td>
											<td>${dt[4]}</td>
											<td>${dt[11]}</td>
											<td><a href="#" value="${dt[7]}" id="ww">预览 </a><br>
												<a href="${downloadfilepath}${dt[7]}/${dt[8]} "
												target="_blank">下载</a></td>
											<td><b id="qdf" style="display: none;">${samip}${dt[7]}</b>
											</td>
											<td>${dt[5]}</td>
											<td>${dt[6]}</td>



											
										</tr>
									</c:forEach>

								</c:when>

								<c:otherwise>

								</c:otherwise>
							</c:choose>
						</tbody>
						<tfoot>
					<tr>
						<td colspan="8">

							<ul class="pagination">
								<li><a href="<c:url value="/checkhistory-1"/>">首页</a></li>
								<li><a
									href='<c:url value="/checkhistory-${page-1<=0 ? 1 : page-1}"></c:url>'>&laquo</a></li>
								<c:forEach begin="1" end="${totalPages}" varStatus="loop">
									<c:set var="active" value="${loop.index==page?'active':''}" />
									<li class="${active}"><a
										href="<c:url value="/checkhistory-${loop.index}"/>">${loop.index}</a></li>
								</c:forEach>
								<li><a
									href="<c:url value="/checkhistory-${page+1<totalPages?page+1:totalPages}"/>">&raquo;</a></li>
								<li><a href="<c:url value="/checkhistory-${totalPages}"/>">末页</a></li>
							</ul>


						</td>
					</tr>
				</tfoot>

					</table>
				

			</div>

		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#result_table").on("click","#ww",function(event) {
						var ee = $(this).closest("tr").find("td").eq(6).find("b").text();
						var txt="截图路径为: <a onclick='copyText()' >点击复制! </a> &nbsp;&nbsp;<font id='kkk' color='red' ></font> <p id='text' hidden='true'>"+ee+"</p> <textarea id='mytxt'  > </textarea>";
			        	myAlert('截图路径',txt,function(){   
			                //要回调的方法  
			              
			            });  
				
					});
		});
	</script>
	<script type="text/javascript">

function copyText() {
	 var text = document.getElementById("text").innerText;
     var input = document.getElementById("mytxt");
     input.value = text; 
     input.select(); 
     document.execCommand("copy"); 
  $("#kkk").empty();
  $("#kkk").append("复制成功!");
}
</script>
</body>

</html>