<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>欢迎访问自动化测试平台</title>
<script src="assets/js/jquery-1.10.2.min.js"></script>
<link href="assets/css/bootstrap.min.3.css" rel="stylesheet"
	id="bootstrap-css">
<script src="assets/js/bootstrap.min.3.js"></script>
<!------ Include the above in your HEAD tag ---------->

<style>
body {
	margin: 0;
	background-image: url('assets/img/dominik-schroder-14534-unsplash.jpg');
	background-repeat: no-repeat;
	background-position: 0% 0%;
	background-size: cover;
	background-color: #B5D0D8;
}
</style>
</head>
<body>

	<div class="container">
		<div id="loginbox" style="margin-top: 15%;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">登录</div>
					<div
						style="float: right; font-size: 80%; position: relative; top: -10px">
						<a href="#">忘记密码?</a>
					</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">
					<font id="qwe" size="2" color="red">${message}</font>

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="loginform" class="form-horizontal" role="form"
						action="login" method="post" name="f">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="text" class="form-control" name="logname" value=""
								placeholder="请输入用户名">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="logpass"
								placeholder="请输入密码">
						</div>



						<div class="input-group">
							<div class="checkbox">
								<label> <input id="login-remember" type="checkbox"
									name="remember" value="1"> 记住密码
								</label>
							</div>
						</div>


						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">
								<a id="btn-login" href="javascript:document.f.submit();"
									class="btn btn-success">登录 </a>
								<!--     <a id="btn-fblogin" href="#" class="btn btn-primary">Login with Facebook</a>
 -->
							</div>
						</div>


						<div class="form-group">
							<div class="col-md-12 control">
								<div
									style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%">
									还没有账号! <a href="#"
										onClick="$('#loginbox').hide(); $('#signupbox').show()">
										点击注册 </a>
								</div>
							</div>
						</div>
					</form>



				</div>
			</div>
		</div>
		<div id="signupbox" style="display: none; margin-top: 15%"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">注册</div>
					<div
						style="float: right; font-size: 85%; position: relative; top: -10px">
						<a id="signinlink" href="#"
							onclick="$('#signupbox').hide(); $('#loginbox').show()">登录</a>
					</div>
				</div>
				<div class="panel-body">
					<form id="signupform" class="form-horizontal" role="form">

						<div id="signupalert" style="display: none"
							class="alert alert-danger">
							<p>Error:</p>
							<span></span>
						</div>



						<div class="form-group">
							<label for="email" class="col-md-3 control-label">邮箱</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="signinemail"
									placeholder="请填写邮件地址">
							</div>
						</div>

						<div class="form-group">
							<label for="firstname" class="col-md-3 control-label">
								用户名</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="signinname"
									placeholder="请设置用户名">
							</div>
						</div>

						<div class="form-group">
							<label for="password" class="col-md-3 control-label">密码</label>
							<div class="col-md-9">
								<input type="password" class="form-control" name="signpasswd"
									placeholder="请设置登录密码" value="">
							</div>
						</div>


						<div class="form-group">
							<!-- Button -->
							<div class="col-md-offset-3 col-md-9">
								<button id="btn-signup" type="button" class="btn btn-info"
									onclick="sigin()">
									<i class="icon-hand-right"></i> &nbsp 注册
								</button>
								<span style="margin-left: 8px;"></span>
							</div>
						</div>

						<div style="border-top: 1px solid #999; padding-top: 20px"
							class="form-group">

							<div class="col-md-offset-3 col-md-9"></div>

						</div>



					</form>
				</div>
			</div>




		</div>
	</div>


	<script type="text/javascript">
		function sigin() {
			$.ajax({
				type : "POST",
				dataType : "TEXT",
				url : "/signin",
				data : $("#signupform").serialize(),
				success : function(result) {
					alert(result);
					window.location.reload();
				},
				error : function() {
					alert("注册失败!请检测信息填写是否符合规范.");
					window.location.reload();
				}
			});
		}
	</script>





</body>
</html>