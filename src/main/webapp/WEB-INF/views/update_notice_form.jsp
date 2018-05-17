<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<%
	int num = (Integer) request.getAttribute("num");
	String userId=request.getAttribute("id").toString();
%>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/js/updateNotice.js"></script>
<body>
<input type="text" id="userId" value=<%=userId%> hidden>
	<input type="text" id="num" name="num" value=<%=num%> hidden>
	content
	<input type="text" id="content" name="content">
	<button onclick="update()">수정</button>

</body>
</html>