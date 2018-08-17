<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>WebSocket Logger</title>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/sockjs.min.js"></script>
    <script src="assets/js/stomp.min.js"></script>
    <title>服务器日志</title>
	<link rel="stylesheet" type="text/css"
			href="assets/css/bootstrap.min.3.css" />
	<style type="text/css"></style>
</head>

<body>
<div class="panle panel-success">
		<div class="panel-heading">
			<a class="panel-title">测试服务器</a>
		</div>
<button onclick="openSocket()">开启日志</button><button onclick="closeSocket()">关闭日志</button>
<div id="filelog-container" style="height: 300px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
    <div></div>
</div>
</div>
</body>
<script>
    var stompClient = null;
    $(document).ready(function() {openSocket();});
    function openSocket() {
        if(stompClient==null){
            var socket = new SockJS('/log');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
//                 stompClient.subscribe('/topic/pullLogger', function(event) {
//                     var content=JSON.parse(event.body);
//                     $("#log-container div").append(content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :"+content.body).append("<br/>");
//                     $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
//                 },{
//                     token:"kltoen"
//                 });
                stompClient.subscribe('/topic/pullFileLogger', function(event) {
                    var content=event.body;
                    $("#filelog-container div").append(content).append("<br/>");
                    $("#filelog-container").scrollTop($("#filelog-container div").height() - $("#filelog-container").height());
                });
            });
        }
    }
    function closeSocket() {
        if (stompClient != null) {
            stompClient.disconnect();
            stompClient=null;
        }
    }
</script>

</html>