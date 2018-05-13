<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<style>
.wrap {
    width: 800px;
    height: auto;
    position: relative;
    display: inline-block;
}
.wrap textarea {
    width: 100%;
    resize: none;
    min-height: 4.5em;
    line-height:1.6em;
    max-height: 9em;
}
.wrap span {
    position: absolute;
    bottom: 5px;
    right: 5px;
}
#counter {
  background:rgba(255,0,0,0.5);
  border-radius: 0.5em;
  padding: 0 .5em 0 .5em;
  font-size: 0.75em;
}
</style>
<%
	String userId=(String)session.getAttribute("userId");
System.out.println(userId);
%>
<body>	
	<form action="/naver/insert-notice" method="post">
		<!--유저 아이디 전송-->
		<input type="text" hidden name="userId" value="<%=userId%>"/>
		
		<div class="wrap">
		    <textarea id="content" name="content" rows="40" cols="50" maxlength="1000" ></textarea>
		    <span id="counter">###</span>
		</div>
		
		 <input type="checkbox" name="A" value="A">A 
		 <input type="checkbox" name="B" value="B">B 
		 <input type="checkbox" name="C" value="C">C 
		 <input type="submit" value="등록"/>
	</form>
	
	
	
</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script>
$(document).ready(function () {
    $('#content').keyup(function (e){
        var content = $(this).val();
        $('#counter').html(content.length + '/1000');
        $('#content').keyup();
    });
    
});
</script>
</html>