/**
 * 
 */

var stompClient = null;
var socket = new SockJS('/naver/hello');
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
        url: "http://localhost:8080/naver/notice/insert",
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
        	
        	for (var i = 0; i < file_data.length; i++) {
        		var fileName = file_data[i].name; // 파일명
        		if (fileName.indexOf('.png') != -1 || fileName.indexOf('.xslx') != -1 ||
        				fileName.indexOf('.xls') != -1 || fileName.indexOf('.txt') != -1 ||
        				fileName.indexOf('.tsv') != -1 || fileName.indexOf('.csv') != -1 ) {
        			//업로드 로직
        			console.log("log1");
        			upload(file_data[i],data);
        		}else{
        			if(flag==true){
        				//업로드 로직
        				console.log("log2");
        				upload(file_data[i],data);
        			}else{
        				if(confirm("지원하지 않는 파일 형식이 있습니다. 그래도 업로드 하시겠습니까?")){
        					flag=true;
        					//업로드 로직
        					console.log("log3");
        					upload(file_data[i],data);
        				}else{
        					return;
        				}
        			}				
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
	post("/naver/notice/", {id: id});
	
	
}
function upload(file,noticeNum){
	console.log(noticeNum);
	var form_data = new FormData();                  // Creating object of FormData class
	form_data.append("file",file)          
	form_data.append("noticeNum",noticeNum)
	$.ajax({
            url: "http://localhost:8080/naver/file/upload",
            dataType: 'script',
            cache: false,
            contentType: false,
            processData: false,
            data: form_data,                         // Setting the data attribute of ajax with file_data
            type: 'post'
   })	
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