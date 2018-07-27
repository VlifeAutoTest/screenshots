<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
<title>自动化测试平台</title>
<style type="text/css"></style>

<script>
	$(document).ready(function() {
		$("#server").click(function() {
			$.ajax({ //这是$.ajax()方法  
				type : "GET",
				url : "serverlist",
				dataType : "html",//接受响应的数据类型，我的响应是一个页面，所以这里用“html”
				success : function(data) {
					$('#div1').html(data);
				},
			});
		});
	});
</script>
<script type="text/javascript">
	var menuClick = function(menuUrl) {
		$("#iframe-page-content").attr('src', menuUrl);
	};

	function autoResize(id) {
		var newheight;
		var newwidth;

		if (document.getElementById) {
			newheight = document.getElementById(id).contentWindow.document.body.scrollHeight;
			newwidth = document.getElementById(id).contentWindow.document.body.scrollWidth;
		}

		document.getElementById(id).height = (newheight) + "px";
		document.getElementById(id).width = (newwidth) + "px";
	}
</script>



</head>
<body>
	<div class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<div class="navbar-brand">
					<small class="glyphicon glyphicon-fire"></small> 字体与主题审核
				</div>
			</div>
			<ul class="nav navbar-nav nav-stacked navbar-right">
				<li title="当前连接的手机数"><a href="http://10.2.10.123:7100"
					target="_blank"> <span class="glyphicon glyphicon-phone"></span>
						<i class="badge">${ counectnum }</i>
				</a></li>
				<li title="正在运行的任务"><a href="#"> <span
						class="glyphicon glyphicon-tasks"></span> <i class="badge"> ${ countrunningcase }
					</i>
				</a></li>
				<li title="消息"><a href="#"> <span
						class="glyphicon glyphicon-bell"></span> <i class="badge">1</i>
				</a></li>
				<li title="邮件"><a href="#"> <span
						class="glyphicon glyphicon-envelope"></span> <i class="badge">1</i>
				</a></li>
				<li><a href="#" data-toggle="dropdown"> <img
						class="img-circle" src="user_photo.png" width="25" height="25" />
						<small>欢迎  </small> ${user.name}<span class="caret"></span>
				</a>
					<ul class="nav nav-pills nav-stacked dropdown-menu">
						<!--  <li class="active"><a href="#"> <span
								class="glyphicon glyphicon-cog"></span> 设置
						</a></li> -->
						<li class="divider"></li>
						<li><a href="#" onclick="menuClick('help')"> <span class="	glyphicon glyphicon-book"></span>
								帮助中心
						</a></li> 
						<li class="divider"></li>
						<li><a href="<c:url   value="/logout"/>"> <span
								class="glyphicon glyphicon-off"></span> 登出
						</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<div style="width: 100%">
		<!-- 左侧菜单栏 -->

		<div id="main-container">
			<div id="sidebar" class="col-md-2 column">
				<div class="col-md-12">

					<div class="panel-group" id="box">
						<div class="panel panel-success">
							<div class="panel-heading">
								<a href="#collapseA" data-parent="#box" data-toggle="collapse"
									class="panel-title">系统管理</a>
							</div>
							<div class="panel-collapse collapse" id="collapseA">
								<div class="panel-body">
									<ul class="nav nav-pills nav-stacked">
										<li class="on"><a href="#"
											onclick="menuClick('userlist')">用户管理</a></li>
										<li class="on"><a href="#"
											onclick="menuClick('role-permission')">角色分配</a></li>
										

									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-success">
							<div class="panel-heading">
								<a href="#collapseB" data-parent="#box" data-toggle="collapse"
									class="panel-title">资源管理</a>
							</div>
							<div class="panel-collapse collapse" id="collapseB">
								<div class="panel-body">
									<ul class="nav nav-pills nav-stacked">
										<li class="on"><a id="theme" href="#"
											onclick="menuClick('themelist-0')">资源列表</a></li>
										<!--  <li class="on" ><a id="upload" href="#" onclick="menuClick('uploadfiles')">上传资源</a></li>   -->
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-success">
							<div class="panel-heading">
								<a href="#collapseD" data-parent="#box" data-toggle="collapse"
									class="panel-title">审核查询</a>
							</div>
							<div class="panel-collapse collapse in " id="collapseD">
								<div class="panel-body">
									<ul class="nav nav-pills nav-stacked">
										<li class="on"><a href="#" onclick="menuClick('check')">增加审核</a></li>
										<li class="on"><a href="#" onclick="menuClick('query')">审核查询</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-success">
							<div class="panel-heading">
								<a href="#collapseE" data-parent="#box" data-toggle="collapse"
									class="panel-title">基础信息</a>
							</div>
							<div class="panel-collapse collapse" id="collapseE">
								<div class="panel-body">
									<ul class="nav nav-pills nav-stacked">
										<li class="on"><a href="#"
											onclick="menuClick('vendorlist')">厂商管理</a></li>
										<li class="on"><a href="#"
											onclick="menuClick('mobilelist-1')">手机管理</a></li>
										<li class="on"><a id="server" href="#"
											onclick="menuClick('serverlist')">服务器管理</a></li>
										<li class="on"><a href="#"
											onclick="menuClick('applicationlist-0-0')">应用管理</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-10">
				<div class="bread-crumb" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><span class="glyphicon glyphicon-home"></span><a
							href="#" onclick="menuClick('list')">Home</a><span
							class="glyphicon glyphicon-remove clo" style="display: none"></span></li>
					</ul>
					<ul >
					
					
					
					
					</ul>
				</div>

				<div>
					<iframe id="iframe-page-content" src="query" width="100%"
						frameborder="no" border="0" marginwidth="0" marginheight="0"
						scrolling="no" onload="autoResize('iframe-page-content')"
						allowtransparency="yes"></iframe>
				</div>

			</div>
		</div>
	</div>

	<script>
		$(function() {
			$(".on")
					.click(
							function() {
								var ab = this.innerHTML;
								$(".breadcrumb").empty();
								$(".breadcrumb")
										.append(
												"<li ><span class='glyphicon glyphicon-home'  ></span><a  href='#' onclick=  menuClick('list')  >Home</a><span class='glyphicon glyphicon-remove clo' style='display:none'></span></li>  ");
								$(".breadcrumb").append("<li>" + ab + "</li>");

							});
		});
	</script>
</body>


</html>
