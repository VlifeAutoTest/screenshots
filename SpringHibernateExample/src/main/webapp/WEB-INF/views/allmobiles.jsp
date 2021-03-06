<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>手机列表</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
	<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/alert.js"></script>
<style type="text/css"></style>
</head>

<body>
	<div class="panle panel-success ">
		<div class="panel-heading col-md-10">
			<a class="panel-title">手机列表</a>
		</div>
		<div class="panel-body table-responsive col-md-10">
			<table
				class="table table-striped table-hover table-nonfluid table-responsive">
				<thead>
					<tr>
						<th>名称</th>
						<th>udid</th>
						<th>分辨率</th>
						<th>操作系统版本</th>
						<th>厂商</th>
						<th>连接方式</th>
						<td><a href="<c:url value='/newmobile' />">+ 增加手机</a></td>
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
							<td><c:if test="${mobile.wififlag == 0}"> USB</c:if> <c:if
									test="${mobile.wififlag == 1 }"> Wifi</c:if></td>
							<td><a href="<c:url value='/edit-${mobile.id}-mobile' />">编辑</a></td>
								<td>
							<a class="delete"
								value="<c:url value='/delete-${mobile.id}-mobile-${page}' />"
								href="#">删除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="8">
							<ul class="pagination">
								<li><a href="<c:url value="/mobilelist-1"/>">首页</a></li>
								<li><a
									href='<c:url value="/mobilelist-${page-1<=0 ? 1 : page-1}"></c:url>'>&laquo</a></li>
								<c:forEach begin="1" end="${totalPages}" varStatus="loop">
									<c:set var="active" value="${loop.index==page?'active':''}" />
									<li class="${active}"><a
										href="<c:url value="/mobilelist-${loop.index}"/>">${loop.index}</a></li>
								</c:forEach>
								<li><a
									href="<c:url value="/mobilelist-${page+1<totalPages?page+1:totalPages}"/>">&raquo;</a></li>
								<li><a href="<c:url value="/mobilelist-${totalPages}"/>">末页</a></li>
							</ul>
						</td>
					</tr>
				</tfoot>
			</table>


		</div>
	</div>
	<br />
		<script type="text/javascript">
		$(".delete").bind("click", function() {
			var va = $(this).attr("value");
			var vb = $(this);
			myConfirm('删除手机', '你确定要删除这个手机吗?', function(r) {
				if (r) {
					window.location.href = va;
				}
			});
		});
	</script>
</body>
</html>