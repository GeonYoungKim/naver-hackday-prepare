<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Hello WebSocket</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script type="text/javascript">
    	var stompClient=null;
    	
    	function setConnected(connected){
    		document.getElementById('connect').disabled=connected;
    		document.getElementById('disconnect').disabled=!connected;
    		document.getElementById('conversationDiv').style.visibility=connected?'visible':'hidden';
    		document.getElementById('response').innerHTML='';    	
    	}
    	
    	function connect(){
    		var socket = new SockJS('/naver/hello');
    		stompClient=Stomp.over(socket);
    		stompClient.connect({}, function(frame) {
            	setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/greetings', function(calResult){
                	showResult(JSON.parse(calResult.body).content);
                });
            });
    	}
    	
    	function disconnect() {
    		if(stompClient != null){
    			 stompClient.disconnect();
    		}
            setConnected(false);
            console.log("Disconnected");
    	}

        function sendName() {
            var name = document.getElementById('name').value;
            stompClient.send("/app/hello",{},JSON.stringify({'name':name}));
        }

        function showResult(message) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap='break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body onload="disconnect()">
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>

<div>
    <div>
        <button id="connect" onclick="connect()">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect()">Disconnect</button>
    </div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name"/>
        <button id="sendName" onclick="sendName()">Send</button>
        <p id="response"></p>
    </div>
</div>
</body>
</html>