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
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="../../resources/js/insertNotice.js"></script>
</head>
<%
	String userId=(String)session.getAttribute("userId");
	System.out.println(userId);
%>
<body>	
		<!--유저 아이디 전송-->
		<input type="text" hidden name="userId" id="userId" value="<%=userId%>"/>
		<div class="wrap">
		    <textarea id="content" name="content" rows="40" cols="50" maxlength="1000" ></textarea>
		    <span id="counter">###</span>
		</div>
		 <input type="checkbox" id="A" name="A" value="A">A 
		 <input type="checkbox" id="B" name="B" value="B">B 
		 <input type="checkbox" id="C" name="C" value="C">C
	
		<input type="file" name="file" id="file" multiple /> 
		<button onclick="mySubmit()">전송</button>	
</body>

</html>