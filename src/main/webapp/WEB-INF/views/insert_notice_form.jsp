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
<script type="text/javascript">
/**
 * 
 */

var stompClient = null;
var socket = new SockJS('/ROOT/hello');
stompClient = Stomp.over(socket);
function mySubmit() {
	console.log("asdf");
	var A = (document.getElementById('A').checked) ? 'A' : 'NO';
	var B = (document.getElementById('B').checked) ? 'B' : 'NO';
	var C = (document.getElementById('C').checked) ? 'C' : 'NO';
	var content=document.getElementById('content').value;
	var id=document.getElementById('userId').value;
	
	console.log(A);
	console.log(B);
	console.log(C);
	console.log(content);
	console.log(id);
	
	$.ajax({
        url: "http://localhost:8080/ROOT/notice/insert",
        contentType: false,
        processData: false,
        contentType: "application/json",
        data: JSON.stringify({
    		userId : id,
    		content:content,
    		A : A,
    		B : B,
    		C : C
    	}),                         // Setting the data attribute of ajax with file_data
        type: 'post',
        success : function(data) {
			document.getElementById('content').value=null;
			document.getElementById('A').checked=null;
			document.getElementById('B').checked=null;
			document.getElementById('C').checked=null;
			
        	var file_data = $("#file").prop("files");
        	var flag=false;
        	var count=0;
        	for (var i = 0; i < file_data.length; i++) {
        		var fileName = file_data[i].name; // 파일명
        		if (fileName.indexOf('.png') != -1 || fileName.indexOf('.xslx') != -1 ||
        				fileName.indexOf('.xls') != -1 || fileName.indexOf('.txt') != -1 ||
        				fileName.indexOf('.tsv') != -1 || fileName.indexOf('.csv') != -1 ) {
        			//업로드 로직
        			count++;
        			continue;	        			
        		}else{
        			if(flag==true){
        				count++;
        				continue;
        			}else{
        				if(confirm("지원하지 않는 파일 형식이 있습니다. 그래도 업로드 하시겠습니까?")){
            				count++;
            				flag=true;
            				continue;
            			}else{	        				
            				return;
            			}
        			}
        			
        		}				
        	}
        	if(count==file_data.length){
        		for (var i = 0; i < file_data.length; i++) {
            		console.log("log1");
            		upload(file_data[i],data,i,file_data.length-1,id);
        		}
            }
        	
        }
	})
	
	stompClient.send("/app/hello", {}, JSON.stringify({
		'id' : id,
		'A' : A,
		'B' : B,
		'C' : C
	}));
	
	
	
}
function upload(file,noticeNum,i,end,id){
	console.log(noticeNum);
	var jsI=i;
	var jsEnd=end;
	var userId=id;
	if(jsI==jsEnd){
		var form_data = new FormData();                  // Creating object of FormData class
		form_data.append("file",file)          
		form_data.append("noticeNum",noticeNum)
		$.ajax({
	            url: "http://localhost:8080/ROOT/file/upload",
	            dataType: 'script',
	            cache: false,
	            contentType: false,
	            processData: false,
	            data: form_data,                         // Setting the data attribute of ajax with file_data
	            type: 'post',
	            success : function(data) {
	            	post("/ROOT/notice", {id: userId});
	            }
	   })	
	}else{
		var form_data = new FormData();                  // Creating object of FormData class
		form_data.append("file",file)          
		form_data.append("noticeNum",noticeNum)
		$.ajax({
	            url: "http://localhost:8080/ROOT/file/upload",
	            dataType: 'script',
	            cache: false,
	            contentType: false,
	            processData: false,
	            data: form_data,                         // Setting the data attribute of ajax with file_data
	            type: 'post'
	   })	
	}
	
}
$(document).ready(function () {
    $('#content').keyup(function (e){
        var content = $(this).val();
        $('#counter').html(content.length + '/1000');
        $('#content').keyup();
    });
    
});
function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}
</script>
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