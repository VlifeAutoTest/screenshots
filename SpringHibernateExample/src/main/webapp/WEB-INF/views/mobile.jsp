<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>手机资源</title>
<script src="assets/js/jquery-3.3.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
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

</head>

<body>
	<script type="text/javascript">
		$(function() {
			var sta = $
			{
				mobile.getWififlag()
			}
			;
			if (sta == 1) {

				$("#optwifi").prop("selected", "selected");
				$("#optusb").prop("selected", "");
			}

		});
	</script>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">手机资源</a>
		</div>
		<div class="panel-body">

			<form:form method="POST" modelAttribute="mobile">
				<form:input type="hidden" path="id" id="id" />
				<table class="table table-striped table-hover table-responsive">

					<tr>
						<td><label>连接类型:</label></td>
						<td><select id="sel" onchange="module()">

								<option id="optusb" selected="selected" value="usb">Usb模式
								</option>
								<option id="optwifi" value="wifi">Wifi模式</option>
						</select></td>
						<td></td>
					</tr>

					<tr class="usb">
						<td><label for="name">名称:</label></td>
						<td><form:input path="name" id="name" /></td>
						<td><form:errors path="name" cssClass="error" /></td>
					</tr>


					<tr class="usb">
						<td><label for="uid">Uid: </label></td>
						<td><form:input path="uid" id="uid" /></td>
						<td><form:errors path="uid" cssClass="error" /></td>
					</tr>

					<tr class="usb">
						<td><label for="size">分辨率: </label></td>
						<td><form:input path="size" id="size" /></td>
						<td><form:errors path="size" cssClass="error" /></td>
					</tr>

					<tr class="usb">
						<td><label for="os">操作系统版本: </label></td>
						<td><form:input path="os" id="os" /></td>
						<td><form:errors path="os" cssClass="error" /></td>
					</tr>

					<tr class="wifi" style="display: none;">
						<td><label>手机的ip: </label></td>
						<td><form:input path="address" id="address" /></td>

					</tr>
					<tr class="wifi" style="display: none;">
						<td><label>端口号:</label></td>
						<td><form:input path="port" id="port" /> <a id="tryconnect"
							href="#"> +Wifi连接测试</a></td>

					</tr>
					<tr style="display: none;">
						<td><label>wifi_flag</label></td>
						<td><form:input path="wififlag" id="wififlag" /></td>

					</tr>

					<tr>
						<td><label for="vendor">厂商: </label></td>
						<c:choose>
							<c:when test="${edit}">
								<td><form:select path="vendor.id">
										<c:forEach var="item" items="${vendors}">
											<c:choose>
												<c:when test="${vname==item.getName()}">
													<form:option selected="true" value="${item.getId()}"
														label="${item.getName()}" />
												</c:when>

												<c:otherwise>
													<form:option value="${item.getId()}"
														label="${item.getName()}" />
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select></td>

							</c:when>

							<c:otherwise>
								<td><form:select path="vendor.id" items="${vendors}"
										multiple="false" itemValue="id" itemLabel="name" /></td>
								<td><form:errors path="vendor.id" cssClass="error" /></td>
							</c:otherwise>
						</c:choose>
					</tr>

					<tr>
						<td colspan="3"><c:choose>
								<c:when test="${edit}">
									<input id="edi" class="btn btn-success" type="submit"
										value="更新" />
								</c:when>
								<c:otherwise>
									<input id="sub" class="btn btn-success" type="submit"
										value="增加" />
								</c:otherwise>
							</c:choose></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	Go back to
	<a href="<c:url value='/mobilelist-1' />">手机应用列表</a>
</body>


<script type="text/javascript">
	function module() {
		var value = $("#sel option:selected").val();
		var sta2 = ${ edit };
		if (sta2 == true) {

			if (value == "wifi") {
				$(".usb").hide();
				$(".wifi").show();
				$("#edi").attr("disabled", "true")

			} else {
				$(".usb").show();
				$(".wifi").hide();

				$("#sub").removeAttr("disabled");
			}

		} else if (sta2 == false) {
			if (value == "wifi") {
				$("#wififlag").val("1");
				$(".usb").hide();
				$(".wifi").show();
				$("#address").val("");
				$("#port").val("");
				$("#sub").attr("disabled", "true")

			} else {
				$("name").val("");
				$("uid").val("");
				$("size").val("");
				$("os").val("");

				$("#wififlag").val("0");
				$(".usb").show();
				$(".wifi").hide();
				$("#address").val("1.1.1.1");
				$("#port").val(1);
				$("#sub").removeAttr("disabled");
			}
		}

	}

	$("#tryconnect").click(function() {
		var text = $("#address").val() + ":" + $("#port").val();
		var str = JSON.stringify(text);
		$.ajax({
			type : "post",
			dataType : "TEXT",
			url : "trywificonnect",
			data : {
				value : str
			},
			ContentType : "application/json;charset=UTF-8",
			success : function(result) {

				var res = result.split("#");
				if (res.length == 1) {
					if (res[0] == "false") {
						alert("尝试连接失败!");
					} else if (res[0] == "messwarn") {
						alert("IP或端口号填写错误!");
					}
				} else {
					$("#size").val(res[1]);
					$("#name").val(res[2]);
					$("#os").val(res[3]);
					$("#uid").val(res[4]);
					$("#sub").removeAttr("disabled");
					alert("正常和手机建立连接!");
				}

			},
			error : function() {
				alert("尝试连接手机失败");

			}
		});

	});
</script>
</html>