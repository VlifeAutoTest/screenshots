<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>厂商应用列表</title>
<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/js/bootstrap-select.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/css/bootstrap-select.css">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css" href="assets/css/xcConfirm.css" />
<script src="assets/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">    
        $(window).on('load', function () {    
            $('.selectpicker').selectpicker({    
                'selectedText': 'cat'    
            });    
        });    
    </script>
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

#mytxt {
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
	z-index: -10;
}
</style>


<script type="text/javascript">
	
	$(function(){
		   $("#vendor").prepend("<option value=''  id ='bbba' selected='selected' >Nothing selected</option>");  
		}); 
	</script>

<script type="text/javascript">
	$(function(){
		   $("#vendor").change(function(){
			  
			  $("#bbba").remove() ;
			  $("#vendor").selectpicker("refresh");
              $("#vendor").selectpicker("render"); 
			 // alert("222");
		   });
		});
	</script>

<script>
		$(function(){  
			
			$("#vendor").change(function(){  
		        var vid = $("#vendor").val();
		        var style = $("#style").val();
		        var para = vid + "-" + style;
		        $.ajax({  
		            type:"GET",  
		            url :"list-apps-with-"+para,
		            dataType:"json",  
		            success:function(data){
		                $("#app").empty();  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#app").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                }); 
		                $("#app").selectpicker("refresh");
		                $("#app").selectpicker("render");    
		            }  
		        });  
		    });  
			
			
			
		
		    $("#style").change(function(){  
		        var vid = $("#vendor").val();
		        var style = $("#style").val();
		        var para = vid + "-" + style;
		        $.ajax({  
		            type:"GET",  
		            url :"list-apps-with-"+para,
		            dataType:"json",  
		            success:function(data){
		                $("#app").empty();  
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
		            	if(jQuery.isEmptyObject( data )){
		            		var txt=  "抱歉,当前选择的厂商下无可用手机!";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
						//	alert(txt);
		            	}
		                $("#mobile").empty();  
		                $.each(data,function(index,item){  
		                    console.info("item:"+item.id);  
		                    $("#mobile").append( "<option value='"+item.id+"'>"+item.name+"</option>");  
		                });  
		                $("#mobile").selectpicker("refresh");
		                $("#mobile").selectpicker("render");   
		               
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
				<b hidden="true" id="bpath">${runinfo.getImagepath()}</b>
				<table class="table table-striped table-hover table-responsive">
					<tr>
						<td><label for="vendor">厂商: </label></td>
						<td><form:select path="vid" items="${vendors}"
								multiple="false" itemValue="id" itemLabel="name" id="vendor"
								class="selectpicker bla bla bli" data-live-search="true">
								<form:options items="${vendors}" itemValue="id" itemLabel="name" />
							</form:select></td>
						<td><form:errors path="vid" cssClass="error" /></td>
					</tr>

					<tr>
						<td><label for="style">截图方式: </label></td>
						<td><form:select path="style" multiple="false" id="style"
								class="selectpicker bla bla bli" data-live-search="true">
								<form:option value="Custom" selected='selected'>Custom</form:option>
								<form:option value="Random">Random</form:option>
							</form:select></td>
						<td><form:errors path="style" cssClass="error" /></td>
					</tr>

					<tr>
						<td><label for="mid">手机: </label></td>
						<td><form:select path="mid" multiple="false" itemValue="id"
								itemLabel="name" id="mobile" class="selectpicker bla bla bli"
								data-live-search="true" /></td>
						<td><form:errors path="mid" cssClass="error" /></td>
					</tr>


					<tr>
						<td><label for="resource">资源: </label></td>
						<td><form:select path="resource" multiple="multiple"
								itemValue="id" itemLabel="name" id="resource"
								class="selectpicker bla bla bli" data-live-search="true" /> <a
							href="<c:url value='/newtheme-1' />"> +增加资源▲</a></td>
						<td><form:errors path="resource" cssClass="error" /></td>
					</tr>


					<tr>
						<td><label for="app">应用: </label></td>
						<td><form:select path="app" multiple="multiple"
								itemValue="id" itemLabel="name" id="app"
								class="selectpicker bla bla bli" data-live-search="true" /></td>
						<td><form:errors path="app" cssClass="error" /></td>
					</tr>

					<tr>
						<td colspan="1"><input id="open" type="submit" value="开始截图" />

						</td>
						<td><span id="me"></span><span style="color: red" id="time"></span><span
							id="sess"></span> <font id="qwe" size="3" color="red">${message}</font>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>

</body>


<!-- 此处js无措,但编译器时长会给出错误提示 -->
<script type="text/javascript">

function replaceAll(str, oldStr, newStr){
	var temp = '';
	temp = str.replace(oldStr, newStr);
	if(isContains(temp, oldStr)){
		temp = replaceAll(temp, oldStr, newStr);
	}
	return temp;
}


		var n=10;
		  function showTime(){  
		        n=n-1;  
		        $("#time").empty();
		        $("#time").append(n);
		        if(n==0){  
		          
		        	var path="\\\\192.168.1.230"+$("#bpath").text();
		        	var txt="截图路径为: <a onclick='copyText()' >点击复制! </a> &nbsp;&nbsp;<font id='kkk' color='red' ></font> <p id='text'>"+path+"</p> <textarea id='mytxt'> </textarea>";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.success);
					 $("#me").empty();
					 $("#time").empty();
					 $("#sess").empty() ;
						
		        }  
		        if(n>0){
		        setTimeout("showTime()",1000);  
		        }
		    }  
		  $(function(){
			  
	 if(${ istrue }==true){
		 $("#me").prepend("正在调用脚本,请稍等, 倒计时") ;
		 $("#time").prepend("10") ;
		 $("#sess").prepend("秒!") ;
		 showTime();  
		
			}

});
	 
	 
	 
	 
	 
</script>


<script type="text/javascript">

function copyText() {
	 var text = document.getElementById("text").innerText;
     var input = document.getElementById("mytxt");
     input.value = text; 
     input.select(); 
     document.execCommand("copy"); 
  $("#kkk").empty();
  $("#kkk").append("复制成功!");
}
</script>


</html>