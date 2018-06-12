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
/**
 * 
 */
function update() {
	var num = document.getElementById('num').value;
	var content = document.getElementById('content').value;
	var userId = document.getElementById('userId').value;
	$.ajax({
		url : "http://localhost:8080/ROOT/notice/update/" + num,
		cache : false,
		contentType : "application/json",
		processData : false,
		data : JSON.stringify({
			content : content,
			userId : userId
		}), // Setting the data attribute of ajax with file_data
		type : 'post',
		success : function(data) {
			document.getElementById('content').value = null;
			var file_data = $("#file").prop("files");
			var flag = false;
			var count = 0;
			for (var i = 0; i < file_data.length; i++) {
				var fileName = file_data[i].name; // 파일명
				if (fileName.indexOf('.png') != -1
						|| fileName.indexOf('.xslx') != -1
						|| fileName.indexOf('.xls') != -1
						|| fileName.indexOf('.txt') != -1
						|| fileName.indexOf('.tsv') != -1
						|| fileName.indexOf('.csv') != -1) {
					// 업로드 로직
					count++;
					continue;
				} else {

					if (flag == true) {
						count++;
						continue;
					} else {
						if (confirm("지원하지 않는 파일 형식이 있습니다. 그래도 업로드 하시겠습니까?")) {
							count++;
							flag = true;
							continue;
						} else {
							return;
						}
					}

				}

			}
			if (count == file_data.length) {
				for (var i = 0; i < file_data.length; i++) {
					console.log("log1");
					upload(file_data[i], num, i, file_data.length - 1,userId);
				}
			}

		}
	})
	// return "redirect:/notice?id="+userId;
}
function upload(file, noticeNum, i, end,id) {
	console.log(noticeNum);
	var jsI=i;
	var jsEnd=end;
	var userId=id;
	if(jsI==jsEnd){
		var form_data = new FormData(); // Creating object of FormData class
		form_data.append("file", file)
		form_data.append("noticeNum", noticeNum)
		$.ajax({
			url : "http://localhost:8080/ROOT/file/upload",
			dataType : 'script',
			cache : false,
			contentType : false,
			processData : false,
			data : form_data, 
			type : 'post',
			success : function(data) {
				post("/ROOT/notice", {id:userId});
			}
		})
	} else {
		var form_data = new FormData(); // Creating object of FormData class
		form_data.append("file", file)
		form_data.append("noticeNum", noticeNum)
		$.ajax({
			url : "http://localhost:8080/ROOT/file/upload",
			dataType : 'script',
			cache : false,
			contentType : false,
			processData : false,
			data : form_data,
			type : 'post'
		})
	}
}
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