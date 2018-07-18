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
</style>

<script type="text/javascript">
	$(function() {
		$("#vendor")
				.prepend(
						"<option value=''  selected='selected'>Nothing selected</option>");
	});
</script>



<script>
	$(function() {

		$("#vendor").change(
				function() {
					var vid = $("#vendor").val();
					$.ajax({
						type : "GET",
						url : "list-apps-by-" + vid,
						dataType : "json",
						success : function(data) {
							$("#app").empty();
							// $("#app").append("<option value='' selected='selected'>----请选择----</option>");  
							$.each(data, function(index, item) {
								console.info("item:" + item.id);
								$("#app").append(
										"<option value='"+item.id+"'>"
												+ item.name + "</option>");
							});
							$("#app").selectpicker("refresh");
							$("#app").selectpicker("render");
						}
					});
				});

		$("#vendor").change(
				function() {
					var vid = $("#vendor").val();
					$.ajax({
						type : "GET",
						url : "list-all-mobiles-" + vid,
						dataType : "json",
						success : function(data) {
							$("#mobile").empty();
							/* 		                $("#mobile").append("<option value='' selected='selected'>Nothing selected</option>");   */
							$.each(data, function(index, item) {
								console.info("item:" + item.id);
								$("#mobile").append(
										"<option value='"+item.id+"'>"
												+ item.name + "</option>");
							});
							$("#mobile").selectpicker("refresh");
							$("#mobile").selectpicker("render");
						}
					});
				});

		$("#vendor").change(
				function() {
					var vid = $("#vendor").val();
					$.ajax({
						type : "GET",
						url : "list-all-resources",
						dataType : "json",
						success : function(data) {
							$("#resource").empty();
							// $("#resource").append("<option value='' selected='selected'>----请选择----</option>");  
							$.each(data, function(index, item) {
								console.info("item:" + item.id);
								$("#resource").append(
										"<option value='"+item.id+"'>"
												+ item.name + "</option>");
							});
							$("#resource").selectpicker("refresh");
							$("#resource").selectpicker("render");
						}
					});
				});

	});

	/*  获取的是当前的时间*/
	function getNowFormatDate(interval) {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate() + interval;
		var hour = date.getHours();
		var minutes = date.getMinutes()
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}

		if (hour >= 0 && hour <= 9) {
			hour = "0" + hour;
		}

		if (minutes >= 0 && minutes <= 9) {
			minutes = "0" + minutes;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate + "T" + hour + seperator2 + minutes;

		/* 		var currentdate = date.getFullYear() + seperator1 + month + seperator1
		 + strDate; */

		return currentdate;
	};

	/* 获取的是三天前的时间 */
	function getThreeDaysBeforeDate() {
		var seperator1 = "-";
		var date = new Date(), timestamp, newDate;
		timestamp = date.getTime();
		// 获取三天前的日期
		newDate = new Date(timestamp - 3 * 24 * 3600 * 1000);
		var year = newDate.getFullYear();
		// 月+1是因为js中月份是按0开始的
		var month = newDate.getMonth() + 1;
		var day = newDate.getDate();
		if (day < 10) { // 如果日小于10，前面拼接0
			day = '0' + day;
		}
		if (month < 10) { // 如果月小于10，前面拼接0
			month = '0' + month;
		}
		return year + seperator1 + month + seperator1 + day;

	}

	$(document).ready(function() {

		sdate = getThreeDaysBeforeDate();
		edate = getNowFormatDate(0);

		$("#s_time").val(edate);
		$("#e_time").val(edate);

	});
</script>

</head>



<body>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">审核查询</a>
		</div>
		<div class="panel-body">

			<div class="panel-body table-responsive">
				<form:form method="POST" modelAttribute="runinfo">
					<table class="table">

						<tr>
							<td><label for="vendor">厂商: </label></td>
							<td><form:select path="vid" items="${vendors}"
									multiple="false" itemValue="id" itemLabel="name" id="vendor"
									class="selectpicker bla bla bli">
									<form:options items="${vendors}" itemValue="id"
										itemLabel="name" />
								</form:select></td>
							<td><form:errors path="vid" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label for="resource">资源: </label></td>
							<td><form:select path="resource" multiple="multiple"
									itemValue="id" itemLabel="name" id="resource"
									class="selectpicker bla bla bli" data-live-search="true">
								</form:select></td>
							<td><form:errors path="resource" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="mid">手机: </label></td>
							<td><form:select path="mid" multiple="multiple"
									itemValue="id" itemLabel="name" id="mobile"
									class="selectpicker bla bla bli" />
						</tr>

						<tr>
							<td><label for="app">应用: </label></td>
							<td><form:select path="app" multiple="multiple"
									itemValue="id" itemLabel="name" id="app"
									class="selectpicker bla bla bli" data-live-search="true">
								</form:select></td>
							<td><form:errors path="app" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label for="">开始日期: </label></td>
							<td><form:input path="stime" id="s_time"
									type="datetime-local" value="" pattern="yyyy-MM-dd HH:mm"
									name="begintime" /></td>
						</tr>

						<tr>
							<td><label for="">结束日期: </label></td>
							<td><form:input path="etime" id="e_time"
									type="datetime-local" value="" pattern="yyyy-MM-dd HH:mm"
									name="endtime" /></td>

						</tr>
						<tr>
							<td><input type="submit" value="查询" /></td>
							<td><font size="3" color="red" id="showpath">${message}</font></td>
						</tr>
					</table>

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
												<a href="http://192.168.1.230:8080${dt[7]}/${dt[8]} "
												target="_blank">下载</a></td>
											<td><b id="qdf" style="display: none;">\\192.168.1.230${dt[7]}</b>
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
						<!-- 		<tfoot>
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
						</tfoot> -->

					</table>
				</form:form>

			</div>

		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#result_table").on(
					"click",
					"#ww",
					function(event) {
						//alert("44444");
						var ee = $(this).closest("tr").find("td").eq(6).find(
								"b").text();
						alert(ee);
						// $("#ll").append(ee) ;
						//$("#showpath").empty();
						//$("#showpath").text(ee);
					});
		});
	</script>
</body>

</html>