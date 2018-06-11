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
<script type="text/javascript">
function selectNotice(num) {
	var noticeNum=num;
	
	$.ajax({
		url : "http://localhost:8080/ROOT/notice/select/"+noticeNum,
		cache : false,
		contentType: "application/json",
		processData : false,		
		type : 'post',
		success : function(data) {
			var obj=JSON.parse(data);
			console.log(data);
			document.getElementById("content").placeholder=obj[0].content;
			for(var i = 0; i < obj.length; i++){ 
				var fileName=obj[i].file_route.substring(obj[i].file_route.lastIndexOf("\\")+1,obj[i].file_route.length);
				var option = $("<li>"+fileName+"<button onclick='fileDelete("+obj[i].file_num+","+noticeNum+")'"+">삭제</button></li>");
                $('#files').append(option);
            }
		}
	})
	
}

function fileDelete(fileNum,noticeNum){
	var jsFileNum=fileNum;
	var jsNoticeNum=noticeNum;
	if(confirm("정말 삭제 하시겠습니까?")){
		$.ajax({
            url: "http://localhost:8080/ROOT/file/delete/"+jsFileNum,
            contentType: false,
            processData: false,
            type: 'post',
            success : function(data) {
            	location.href="/ROOT/update-notice-form?num="+jsNoticeNum;
            }
		})	
	}else{
		alert("삭제 취소 되었습니다.");
	}
}
</script>


<body onload="selectNotice('<%=num%>')">
<input type="text" id="userId" value=<%=userId%> hidden>
	<input type="text" id="num" name="num" value=<%=num%> hidden>
	content
	<input type="text" id="content" name="content">
	<br/>
	<list id="files">
		
	</list>
	<input type="file" name="file" id="file" multiple /> 
	<button onclick="update()">수정</button>
</body>
</html>