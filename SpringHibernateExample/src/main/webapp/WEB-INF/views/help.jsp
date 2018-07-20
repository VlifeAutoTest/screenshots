<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>error</title>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.3.css" />
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>
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
<style type="text/css">

.g-section-3 h3{transform:translate(0px,-40px);opacity:0;filter:alpha(opacity=100)}
.g-section-3 .faq-1{transform:translate(0px,80px);opacity:0;filter:alpha(opacity=100)}
.g-section-3 .faq-2{transform:translate(0px,80px);opacity:0;filter:alpha(opacity=100)}
.g-section-3 .faq-3{transform:translate(0px,80px);opacity:0;filter:alpha(opacity=100)}

.g-section-3.active h3{transform:translate(0px,0);opacity:1;transition:all .6s}
.g-section-3.active .faq-1{transform:translate(0px,0);opacity:1;transition:all .6s}
.g-section-3.active .faq-2{transform:translate(0px,0);opacity:1;transition:all .8s}
.g-section-3.active .faq-3{transform:translate(0px,0);opacity:1;transition:all 1s}
</style>
</head>

<body>

	<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">用户指导手册</a>
		</div>
		<div class="panel-body">

			
		</div>
	</div>


<script type="text/javascript">
$(function(){
    $("#btn31").click(function(){
        $('.g-section-3').addClass('active');
    });
    $("#btn32").click(function(){
        $('.g-section-3').removeClass('active');
    });
   

});
</script>

<div class="g-section g-section-3 active" style="width: 1311px;">
    <div class="c-content">
        <div class="m-faq">
            <h3>常见问题</h3>
            
            <dl class="faq-2">
                <dt>Q：为什么有时网站报500错误Message显示:  Could not open Hibernate Session for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Could not open connection</dt>
                <dd><strong>A：</strong>这个是因为不能连接数据库导致的,请检查本服务部署的机器是否登陆了网关；</dd>
            </dl>
            
            <dl class="faq-1">
                <dt>Q：为什么我点击有些功能提示我不具备该项操作权限？</dt>
                <dd><strong>A：</strong>这个是因为您的账户所在的用户组没有此项功能的操作权限导致的,如果需要请联系管理人管添加相应权限；</dd>
            </dl>
            
            
            <dl class="faq-2">
                <dt>Q：为什么手机连接到机器上了,但是此系统并没有检测到我的手机？</dt>
                <dd><strong>A：</strong>这是因为本系统每5分钟刷新一次手机状态的,所以有时不能及时发现连接的手机,但是可以点击Home进入手机状态页面点击Click here强制更新手机状态；</dd>
            </dl>
            
            
            <dl class="faq-3">
                <dt>Q：为什么手机管理里面的手机和应用管理的应用一开始都设置了厂商,但是突然间有的手机或应用的厂商显示没有？</dt>
                <dd><strong >A：</strong>这个可能是因为你在厂商管理里面把它们相关厂商给删除了,所以会厂商不显示；
                    
                </dd>
            </dl>
            
              <dl class="faq-2">
                <dt>Q：增加审核和审核查询直接点击资源,手机,应用下拉列表为什么会为空？</dt>
                <dd><strong>A：</strong>由于使用了动态加载技术,所以需要在选择资源,手机,应用前选择厂商的,然后相关的信息才会被加载出来；</dd>
            </dl>
            
             <dl class="faq-2">
                <dt>Q：审核查询怎么查询一段时间的全部的信息？</dt>
                <dd><strong>A：</strong>当厂商,资源,手机,应用全部是默认选项,Nothing selected时,点击查询,此时查询到的结果为该段时间内的所有审核信息,也就是说,当某一项不选择任何东西,
            	<br>  &nbsp;&nbsp; &nbsp;  是默认状态Nothing selected时，代表的是该项的全选状态；</dd>
            </dl>
            
            
            
             <dl class="faq-2">
                <dt>Q：为什么进行某些操作时会出现错误,显示500的错误？</dt>
                <dd><strong>A：</strong>请速与本系统维护人员反馈；</dd>
            </dl>
            
        </div>
    </div>
</div>



	
</body>
</html><title>Insert title here</title>
</head>
<body>

</body>
</html>