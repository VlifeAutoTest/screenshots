<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>厂商应用列表</title>
	<!-- <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /> -->
	<!-- <script src="assets/js/jquery.min.js"></script> -->
	<!-- <script src="assets/js/bootstrap.js"></script> -->
	<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>    
    <script type="text/javascript" src="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/js/bootstrap-select.js"></script>      
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>    
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/css/bootstrap-select.css">      
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">    
    <script type="text/javascript">    
        $(window).on('load', function () {    
    
            $('.selectpicker').selectpicker({    
                'selectedText': 'cat'    
            });    
    
            //$('.selectpicker').selectpicker('hide');    
        });    
    </script>  
	
	
	
	
	
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
		               // $("#app").append("<option value=''>----请选择----</option>");  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#app").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                }); 
		                $("#app").selectpicker("refresh");
		                $("#app").selectpicker("render");    
		            }  
		        });  
		    });  
		      
		 
		    $("#vendor").change(function(){  
		        var vid = $("#vendor").val();
		        $.ajax({  
		            type:"GET",  
		            url :"list-mobiles-by-"+vid,  
		            dataType:"json",  
		            success:function(data){
		                $("#mobile").empty();  
		                $("#mobile").append("<option value=''>----请选择----</option>");  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#mobile").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                });  
		               
		            }  
		        });  
		    }); 
		    
		    $("#vendor").change(function(){  
		        var vid = $("#vendor").val();
		        $.ajax({  
		            type:"GET",  
		            url :"list-all-resources",  
		            dataType:"json",  
		            success:function(data){
		                $("#resource").empty();  
		               // $("#resource").append("<option value=''>----请选择----</option>");  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#resource").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                    
		                });  
		                $("#resource").selectpicker("refresh");
		                $("#resource").selectpicker("render");    
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
		  <b hidden="true"  id ="bpath">${runinfo.getImagepath()}</b>
		<table class="table table-striped table-hover table-responsive">
		    <tr>
		    	<td><label for="vendor">厂商: </label> </td>
				<td><form:select path="vid" items="${vendors}" multiple="false" itemValue="id" itemLabel="name"  id="vendor">
					<form:options  items="${vendors}" itemValue="id" itemLabel="name"/>
				</form:select></td>
				<td><form:errors path="vid" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
		    	<td><label for="resource">资源: </label> </td>
				<td><form:select path="resource" multiple="multiple" itemValue="id" itemLabel="name"  id="resource"  class="selectpicker bla bla bli" data-live-search="true"  /></td>
				<td><form:errors path="resource" cssClass="error"/></td>
		    </tr>
		    
		    
		    <tr>
		    	<td><label for="mid">手机: </label> </td>
			<td><form:select path="mid" multiple="false" itemValue="id" itemLabel="name" id="mobile" /></td>
				<td><form:errors path="mid" cssClass="error"/></td>
		    </tr>
		    

		    <tr>
		    	<td><label for="app">应用: </label> </td>
				<td><form:select path="app" multiple="multiple" itemValue="id" itemLabel="name" id="app" class="selectpicker bla bla bli" data-live-search="true"/></td>
				<td><form:errors path="app" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td colspan="3">
					<input id ="open" type="submit" value="开始截图"/>
					
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
</body>


<script type="text/javascript">

 $(document).ready(function () { 
	 //alert(${browserAddress }) ;
	 if( ${istrue }==true ){
		 //alert("2222") ;
		var bpath="ftp://192.168.1.230"+$("#bpath").text();
		
		//alert("3333") ;
		
		setTimeout(function () {window.open(bpath); }, 5000);
		
	 }
	 
	 
 } );
		
	


</script>
</html>