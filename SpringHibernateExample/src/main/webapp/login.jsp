<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自动化测试平台</title>
<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="assets/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="assets/css/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="assets/css/component.css" />
</head>
<body>

	<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>欢迎访问自动化测试平台</h3>
						<form action="##" name="f" method="post" id="form1">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="logname" class="text" style="color: #000000 !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="logpass" class="text" style="color: #000000 !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="mb2">
							
							<a class="act-but submit"   onclick="login()" type="submit" href="" style="color: #FFFFFF"> 登录  </a>
							<!-- <input type="submit" class="act-but submit" style="color: #FFFFFF" value="登录"> -->
							
							
							</div>
						</form>
					</div>
				</div>
			</div>
			</div>
	<!-- /container -->
	<script src="assets/js/TweenLite.min.js"></script>
	<script src="assets/js/EasePack.min.js"></script>
	<script src="assets/js/rAF.js"></script>
	<script src="assets/js/demo-1.js"></script>
	<div style="text-align: center;"></div>


</body>


<script type="text/javascript">
function login(){
    $.ajax({
    //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "login" ,//url
        data: $('#form1').serialize(),
        success: function (result) {
           
        },
        error : function() {
            alert("异常！");
            
        }
    });
}
</script>
</html>