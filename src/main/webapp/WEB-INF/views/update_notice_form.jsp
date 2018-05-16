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
	List<Map<String,Object>> notice=(List<Map<String,Object>>)request.getAttribute("notice");
	int num=(Integer)notice.get(0).get("num");
%>
<body>
<form action="/naver/update-notice" method="post">
	<input type="text" name="num" value=<%=num %> hidden>
	content <input type="text" name="content">
	<input type="submit" value="수정"> 
</form>
</body>
</html>