<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script>
	function mySubmit() {
		var fileObject = document.getElementById("fileForm");
		var files = fileObject.files;
		var flag=false;
		
		for (var i = 0; i < files.length; i++) {
			var fileName = files[i].name; // 파일명
			if (fileName.indexOf('.png') != -1 || fileName.indexOf('.xslx') != -1 ||
					fileName.indexOf('.xls') != -1 || fileName.indexOf('.txt') != -1 ||
					fileName.indexOf('.tsv') != -1 || fileName.indexOf('.csv') != -1 ) {
				//업로드 로직
				console.log("log1");
				this.upload(files[i]);
			}else{
				if(flag==true){
					//업로드 로직
					console.log("log2");
					this.upload(files[i]);
				}else{
					if(confirm("지원하지 않는 파일 형식이 있습니다. 그래도 업로드 하시겠습니까?")){
						flag=true;
						//업로드 로직
						console.log("log3");
						this.upload(files[i]);
					}else{
						return;
					}
				}				
			}
		}
		
	function upload(file){
		$.post("http://localhost:8080/naver/uploadTest", //Required URL of the page on server
				{ // Data Sending With Request To Server
				
				},
				function(response,status){ // Required Callback Function
					if(response=="true"){
						alert("새로운 공지가 등록되었습니다.");
					}
				});
	}
</script>
</head>
<body>
	<input type="file" name="file" id="file" multiple /> 
	<button onclick="mySubmit()">전송</button>	
</body>
</html>