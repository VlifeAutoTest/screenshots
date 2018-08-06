<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Themes and Fonts</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<style type="text/css"></style>
<script
	src="assets/js/jquery.min.js"></script>

</head>
<body>
	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">主题与字体资源</a>
		</div>
		<div class="panel-body">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>资源名</th>
						<th>审核序号</th>
						<th>保存路径</th>
						<th><a href="<c:url value='/newtheme-0' />">+ 增加资源</a></th>
						<th>
							<!--  页面搜索的代码 -->
							<form method="post">
								<div class="input-group" style="width: 200px">
									<input type="text" name="search" class="form-control"
										placeholder="请输入搜索关键字"> <span class="input-group-btn">
										<button class="btn btn-primary" type="submit">搜索</button>
									</span>
								</div>
							</form>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${themes}" var="theme">
						<tr>
							<td>${theme.name}</td> 
							<td>${theme.checknumber}</td>
							<td><a id="aa"
								href="${downfilepath}/${fn:substring(theme.path, 7, -1)}"
								target="_blank">${theme.path}</a></td>
							<td><a href="<c:url value='/delete-${theme.id}-theme-${page}' />">删除</a></td>


							<td></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="8">

							<ul class="pagination">
								<li><a href="<c:url value="/themelist-1"/>">首页</a></li>
								<li><a
									href='<c:url value="/themelist-${page-1<=0 ? 1 : page-1}"></c:url>'>&laquo</a></li>
								<c:forEach begin="1" end="${totalPages}" varStatus="loop">
									<c:set var="active" value="${loop.index==page?'active':''}" />
									<li class="${active}"><a
										href="<c:url value="/themelist-${loop.index}"/>">${loop.index}</a></li>
								</c:forEach>
								<li><a href="<c:url value="/themelist-${page+1<totalPages?page+1:totalPages}"/>">&raquo;</a></li>
								<li><a href="<c:url value="/themelist-${totalPages}"/>">末页</a></li>
							</ul>


						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>

</body>
</html>