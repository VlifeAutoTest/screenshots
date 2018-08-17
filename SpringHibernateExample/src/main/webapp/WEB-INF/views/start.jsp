<!DOCTYPE html>
<html>
<head>
    <title>Calculator App Using Spring 4 WebSocket</title>
    <script src="assets/js/sockjs.min.js"></script>
    <script src="assets/js/stomp.min.js"></script>
    <script type="text/javascript">
        var stompClient = null; 
        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('calculationDiv').style.visibility = connected ? 'visible' : 'hidden';
            document.getElementById('calResponse').innerHTML = '';
        }
        function connect() {
            var socket = new SockJS('/log');
			stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/showMessage', function(calResult){
                	showMessage(JSON.parse(calResult.body).content);
                });
            });
        }
        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }
        function sendNum() {
            var name = document.getElementById('name').value;
            stompClient.send("/websocket/log", {}, JSON.stringify({ 'name': name}));
        }
        function showMessage(message) {
            var response = document.getElementById('calResponse');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body>
<noscript><h2>Enable Java script and reload this page to run Websocket Demo</h2></noscript>
<h1>Calculator App Using Spring 4 WebSocket</h1>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button><br/><br/>
    </div>
    <div id="calculationDiv">
        <label>Number One:</label><input type="text" id="name" /><br/>
        <button id="sendNum" onclick="sendNum();">Send to Add</button>
        <p id="calResponse"></p>
    </div>
</div>
</body>
</html> 