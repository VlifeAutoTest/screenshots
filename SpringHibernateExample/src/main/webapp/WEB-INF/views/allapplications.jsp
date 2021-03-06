<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>厂商应用列表</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
	<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/alert.js"></script>
<style type="text/css"></style>
</head>

<script type="text/javascript">
	function changeFunc() {
		var selectBox = document.getElementById("selectBox");
		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
		var link = "applicationlist-1-" + Number(selectedValue) ;
		window.location.href = link;

	}
</script>

<body>
	<div class="panle panel-success ">
		<div class="panel-heading col-md-10">
			<a class="panel-title">厂商应用列表</a>
		</div>
		<div class="panel-body table-responsive col-md-10">
			<table
				class="table table-striped table-hover table-nonfluid table-responsive">
				<thead>
					<tr>
						<th>应用名称</th>
						<th>厂商</th>
						<th data-sortable=true>截图策略</th>

						<td><a href="<c:url value='/newapplication' />">+ 增加应用</a></td>

						<td>厂商筛选：<select id="selectBox" onchange="changeFunc();">
								<option value="  " selected="selected">请选择</option>
								<c:forEach items="${vendors}" var="vendor">
									<option value=" ${vendor.getId()} ">${vendor.getName()}</option>
								</c:forEach>
						</select></td>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${applications}" var="app">
						<tr>
							<td>${app.alias}</td>
							<td>${app.getVendor().getName()}</td>
							<td>${app.style == 'Custom'? '定制截图' : '随机截图'}
							
							
							
							</td>
							<c:if test="${app.style =='Custom' }">
							<td><a href="<c:url value='/edit-${app.id}-application' />"></a></td>
							</c:if>
							
							<c:if test="${app.style =='Random' }">
							<td><a href="<c:url value='/edit-${app.id}-application' />">编辑</a></td>
							</c:if>
							
								<td>
							<a class="delete"
								value="<c:url value='/delete-${app.id}-application-${page}' />"
								href="#">删除</a>
							</td>
							
							
							
							
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="8" id="asd">

							<ul class="pagination">
								<li><a href="<c:url value="/applicationlist-1-0"/>">首页</a></li>
								<li><a
									href='<c:url  value="/applicationlist-${page-1<=0 ? 1 : page-1}-0"></c:url>'>
										&laquo; </a></li>
								<c:forEach begin="1" end="${totalPages}" varStatus="loop">
									<c:set var="active" value="${loop.index==page?'active':''}" />
									<li class="${active}"><a
										href="<c:url value="/applicationlist-${loop.index}-0"/>">${loop.index}</a></li>
								</c:forEach>
								<li><a href="<c:url value="/applicationlist-${page+1<totalPages?page+1:totalPages}-0"/>">
										&raquo;</a></li>
								<li><a
									href="<c:url value="/applicationlist-${totalPages == 0 ? 1 : totalPages }-0"/>">末页</a></li>
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
			myConfirm('删除应用', '你确定要删除这个应用吗?', function(r) {
				if (r) {
					window.location.href = va;
				}
			});
		});
	</script>
</body>
</html>