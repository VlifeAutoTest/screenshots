<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>主题与字体资源</title>
	
	<script src="http://upcdn.b0.upaiyun.com/libs/jquery/jquery-2.0.2.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<style type="text/css"></style> 

<script>



$(document).ready(function(){
	
	var aa=${edit};
	
	if (aa) {
		$("#wq").css("display","none");
	
}
else{
	$("#we").css("display","none");
};
	});
 



</script>
</head>

<body>

	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">主题与字体资源更新</a>  
            </div>  
    <div class="panel-body">  
 
	<form:form method="POST" modelAttribute="theme"  enctype="multipart/form-data" id ="fromuserud" >
		<form:input type="hidden" path="id" id="id"/>
		<table class="table table-striped table-hover">
			<tr>
				<td><label for="name">名称: </label> </td>
<<<<<<< HEAD
				<td><form:input path="name" id="name"/>
				<font id="qwe" size="3" color="red">${message}</font> </td>
=======
				<td><form:input path="name" id="name"/></td>
>>>>>>> branch 'master' of https://github.com/VlifeAutoTest/screenshots.git
				<td><form:errors path="name" cssClass="error"/></td>
		    </tr>
		    <form:input type="hidden" path="path" id="path]"/>
<<<<<<< HEAD
		    <%-- <tr id ="we" >
=======
		    <tr id ="we" >
>>>>>>> branch 'master' of https://github.com/VlifeAutoTest/screenshots.git
				<td><label for="path">保存路径： </label> </td>
				<td><form:input path="path" id="path" /></td>
				<td><form:errors path="path" cssClass="error"/></td>
		    </tr> --%>
	
			 <tr id ="wq">
				<td><label for="">文件名: </label> </td>
<<<<<<< HEAD
				<td><input type="file" name="file" >
				<font id="qwe" size="3" color="red">${messagetwo}</font></td>
=======
				<td><input type="file" name="file" ></td>
>>>>>>> branch 'master' of https://github.com/VlifeAutoTest/screenshots.git
				<%--  <td><form:errors path="path" cssClass="error"/></td>  --%>
		    </tr>
	
	
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="更新"/>
						</c:when>
						<c:otherwise>
<<<<<<< HEAD
							<input type="submit"  id ="addnew" value="增加" />
=======
							<input type="submit" value="增加" />
>>>>>>> branch 'master' of https://github.com/VlifeAutoTest/screenshots.git
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
<<<<<<< HEAD
	Go back to <a href="<c:url value='/themelist-1' />">字体与主题列表</a>
=======
	Go back to <a href="<c:url value='/themelist' />">字体与主题列表</a>
>>>>>>> branch 'master' of https://github.com/VlifeAutoTest/screenshots.git
</body>


<script type="text/javascript">

/* $(function(){
	
	$("#addnew").click(function(){
		var date=$("#fromuserud")[0];  //获取表单中的值
		var formData = new FormData(date);  //使用FormData()处理表单中的值
		    $.ajax({
		        url:"newtheme",  //发送的URL
		        type:"POST",        //类型
		        data:formData,      //将数据发送
		        async: false,       //是否同步，否
		            cache: false,       //是否缓存，否
		            contentType: false,     //需要使用
		            processData: false,     //需要使用
		        success:function(result){
		        //发送成功后需要执行的动作
		        }
		    });
		});
	
	
	});
 */


</script>

</html>