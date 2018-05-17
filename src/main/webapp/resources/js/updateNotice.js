/**
 * 
 */
function update() {
		var num = document.getElementById('num').value;
		var content = document.getElementById('content').value;
		var userId = document.getElementById('userId').value;
		$.ajax({
			url : "http://localhost:8080/naver/notice/update/"+num,
			cache : false,
			contentType: "application/json",
			processData : false,
			data : JSON.stringify({
				content:content,
				userId:userId
			}), // Setting the data attribute of ajax with file_data
			type : 'post',
			success : function(data) {
				document.getElementById('content').value=null;
			}
		})
		
	}