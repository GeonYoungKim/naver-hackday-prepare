/**
 * 
 */

var stompClient = null;
var socket = new SockJS('/naver/hello');
stompClient = Stomp.over(socket);


function chageLangSelect(id){
	var userId=id;
    var unitSelect = document.getElementById("unit");
    // select element에서 선택된 option의 value가 저장된다.
    var selectValue = unitSelect.options[unitSelect.selectedIndex].value;
    post("/naver/notice", {id: id,no:1,unit:selectValue});
    
}
function noticeUpdate(userId,num,noticeId){
	console.log("수정");
	var id=userId;
	var jsNum=num;
	var noticeId = noticeId;
	
	if(id!=noticeId){
		alert("수정 권한이 없습니다.");
	}else{
		if(confirm("정말 수정 하시겠습니까?")){
			location.href = "/naver/update-notice-form?num="+jsNum;
		}else{
			alert("수정 취소 되었습니다.");
		}
	}
}

function noticeDelete(userId,num,noticeId,no,unit){
	console.log("삭제");
	var id=userId;
	var jsNum=num;
	var noticeId = noticeId;
	var jsNo=no;
	var jsUnit=unit;
	
	if(id!=noticeId){
		alert("삭제 권한이 없습니다.");
	}else{
		if(confirm("정말 삭제 하시겠습니까?")){
			$.ajax({
	            url: "http://localhost:8080/naver/notice/delete/"+jsNum,
	            contentType: false,
	            processData: false, // Setting the data attribute of ajax with file_data
	            type: 'post',
	            success : function(data) {
	            	post("/naver/notice", {id: id,no:jsNo,unit:jsUnit});
	            }
			})	
		}else{
			alert("삭제 취소 되었습니다.");
		}
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
function disconnect() {
	if(stompClient != null){
		 stompClient.disconnect();
	}
    console.log("Disconnected");
}


function showResult(message) {
	var userId="<%=id%>";
	console.log(userId);
	
		$.ajax({
			url : "http://localhost:8080/naver/alarm/judge",
			contentType : false,
			processData : false,
			contentType : "application/json",
			data : JSON.stringify({
				userId : userId,
				data : message
			}), // Setting the data attribute of ajax with file_data
			type : 'post',
			success : function(data) {
				if (data == "true") {
					alert("새로운 공지가 등록되었습니다.");
				}
			}
		});
	}

	function logout() {
		disconnect();
		location.href = "/naver/logout";
	}

	function connect(alarm) {
		console.log("load");
		var alarmFlag = alarm;
		if (alarmFlag == "YES") {
			alert("새로운 공지가 있습니다.");
		}
		var socket = new SockJS('/naver/hello');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {

			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/greetings', function(calResult) {
				showResult(JSON.parse(calResult.body).content);
			});
		});
	}