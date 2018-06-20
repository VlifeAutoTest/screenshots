<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>厂商应用列表</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/bootstrap.js"></script>
	<style type="text/css"></style> 
	<style>
		select{
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
	
	<script>

		$(function(){  
		
		    $("#vendor").change(function(){  
		        var vid = $("#vendor").val();
		        $.ajax({  
		            type:"GET",  
		            url :"list-apps-by-"+vid,  
		            dataType:"json",  
		            success:function(data){
		                $("#app").empty();  
		                $("#app").append("<option value=''>----请选择----</option>");  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#app").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                });  
		            }  
		        });  
		    });  
		      
		 
		      
		}); 
</script>

</head>



<body>

	<div class="panle panel-success">  
            <div class="panel-heading">  
                <a class="panel-title">资源审核</a>  
            </div>  
    <div class="panel-body">  
 
	<form:form method="POST" modelAttribute="runinfo">
		<table class="table table-striped table-hover table-responsive">
		    
		    <tr>
		    	<td><label for="vendor">厂商: </label> </td>
				<td><form:select path="vid" items="${vendors}" multiple="false" itemValue="id" itemLabel="name"  id="vendor"/></td>
				<td><form:errors path="vid" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
		    	<td><label for="mid">手机: </label> </td>
<%-- 				<td><form:select path="mid" items="${mobiles}" multiple="false" itemValue="id" itemLabel="name" /></td> --%>
				<td><form:select path="mid" multiple="false" itemValue="id" itemLabel="name" id="mobile" /></td>
				<td><form:errors path="mid" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
		    	<td><label for="resource">资源: </label> </td>
				<td><form:select path="resource" items="${resources}" multiple="multiple" itemValue="id" itemLabel="name" /></td>
				<td><form:errors path="resource" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
		    	<td><label for="app">应用: </label> </td>
<%-- 				<td><form:select path="app" items="${apps}" multiple="multiple" itemValue="id" itemLabel="name" /></td> --%>
				<td><form:select path="mid" multiple="false" itemValue="id" itemLabel="name" id="app" /></td>
				<td><form:errors path="app" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td colspan="3">
					<input type="submit" value="开始截图"/>
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
</body>
</html>