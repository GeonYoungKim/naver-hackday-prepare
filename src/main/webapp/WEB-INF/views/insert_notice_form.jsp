<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="resources/css/insert_notice_form.css"
	rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
</head>
<%
	String userId=(String)session.getAttribute("userId");
	System.out.println(userId);
%>
<body>	
	<form action="/naver/insert-notice" method="post" onsubmit="send()">
		<!--유저 아이디 전송-->
		<input type="text" hidden name="userId" id="userId" value="<%=userId%>"/>
		<div class="wrap">
		    <textarea id="content" name="content" rows="40" cols="50" maxlength="1000" ></textarea>
		    <span id="counter">###</span>
		</div>
		 <input type="checkbox" id="A" name="A" value="A">A 
		 <input type="checkbox" id="B" name="B" value="B">B 
		 <input type="checkbox" id="C" name="C" value="C">C 
		 <input type="submit" value="등록"/>
	</form>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/js/geonyoung.js"></script>
</html>