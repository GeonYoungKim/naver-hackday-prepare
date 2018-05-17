<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("selectOneNotice");
	String content = ((Map) ((List) map.get("notice")).get(0)).get("content").toString();
	List<Map<String, Object>> files = (List) map.get("file");
	System.out.println(map);
%>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	function download(fileNum) {
		console.log(fileNum);
		$.ajax({
	        url: "http://localhost:8080/naver/download-file",
	        contentType: false,
	        processData: false,
	        contentType: "application/json",
	        data: JSON.stringify({
	    		fileNum:fileNum
	    	}),                         // Setting the data attribute of ajax with file_data
	        type: 'post',
	        success : function(data) {
	        	alert(data);
	        }
		});
	}
</script>
</head>
<body>
	내용 :
	<font><%=content%></font>
	<ul>
		<%
			for (int i = 0; i < files.size(); i++) {
				String route = ((Map) files.get(i)).get("file_route").toString();
				String name = route.substring(route.lastIndexOf("\\") + 1);
				int fileNum=Integer.parseInt(((Map) files.get(i)).get("file_num").toString());
		%>
		<li><a href="/naver/download-file?fileNum=<%=fileNum%>"><%=name %></a></li>
		<%
			}
		%>
	</ul>

</body>
</html>