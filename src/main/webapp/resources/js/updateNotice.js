/**
 * 
 */
function update() {
	var num = document.getElementById('num').value;
	var content = document.getElementById('content').value;
	var userId = document.getElementById('userId').value;
	$.ajax({
		url : "http://localhost:8080/naver/notice/update/" + num,
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
			url : "http://localhost:8080/naver/file/upload",
			dataType : 'script',
			cache : false,
			contentType : false,
			processData : false,
			data : form_data, 
			type : 'post',
			success : function(data) {
				post("/naver/notice", {id:userId});
			}
		})
	} else {
		var form_data = new FormData(); // Creating object of FormData class
		form_data.append("file", file)
		form_data.append("noticeNum", noticeNum)
		$.ajax({
			url : "http://localhost:8080/naver/file/upload",
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
