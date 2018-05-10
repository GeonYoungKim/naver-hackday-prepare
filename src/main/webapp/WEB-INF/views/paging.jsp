<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTH HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dTH">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
	Map<String, Object> map = (Map) request.getAttribute("map");
%>
<body>
	<table BORDER="1" BORDERCOLOR="black" CELLPADDING="5" ALIGN="center">
		<TR>
			<TH align="center" WIDTH="70">컨텐츠</TH>
		<TR>
		<%
			for (int i = 0; i < ((List) map.get("tableList")).size(); i++) {
		%>
		
		<TR>
			<TH align="center" WIDTH="70"><%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("content") %></TH>
		<TR>
		<%
			}
		%>
		
		
		<button type="submit" onclick="location.href='/naver/insert-notice'">공지사항 추가</button>
	</body>
</html>