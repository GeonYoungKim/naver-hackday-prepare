/**
 * 
 */

var stompClient = null;
var socket = new SockJS('/naver/hello');
stompClient = Stomp.over(socket);

function send() {
	console.log("aa");
	
	var A = (document.getElementById('A').checked) ? 'A' : 'NO';
	var B = (document.getElementById('B').checked) ? 'B' : 'NO';
	var C = (document.getElementById('C').checked) ? 'C' : 'NO';
	var id = document.getElementById('userId').value;
	stompClient.send("/app/hello", {}, JSON.stringify({
		'id' : id,
		'A' : A,
		'B' : B,
		'C' : C
	}));
}

$(document).ready(function () {
    $('#content').keyup(function (e){
        var content = $(this).val();
        $('#counter').html(content.length + '/1000');
        $('#content').keyup();
    });
    
});

function chageLangSelect(){
    var unitSelect = document.getElementById("unit");
    // select element에서 선택된 option의 value가 저장된다.
    var selectValue = unitSelect.options[unitSelect.selectedIndex].value;
    location.href = "/naver/notice?no=1&unit="+selectValue;
}
function noticeUpdate(userId,num,noticeId){
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
	var id=userId;
	var jsNum=num;
	var noticeId = noticeId;
	var jsNo=no;
	var jsUnit=unit;
	
	if(id!=noticeId){
		alert("삭제 권한이 없습니다.");
	}else{
		if(confirm("정말 삭제 하시겠습니까?")){
   			$.post("http://localhost:8080/naver/delete-notice", //Required URL of the page on server
   					{ // Data Sending With Request To Server
   					noticeNum:jsNum
   					},
   					function(response,status){ // Required Callback Function
   						location.href = "/naver/notice?no="+jsNo+"&unit="+jsUnit;
   					});
			
		}else{
			alert("삭제 취소 되었습니다.");
		}
	}
}

function disconnect() {
	if(stompClient != null){
		 stompClient.disconnect();
	}
    console.log("Disconnected");
}


function showResult(message) {
	$.post("http://localhost:8080/naver/alarm-judge", //Required URL of the page on server
				{ // Data Sending With Request To Server
				data:message
				},
				function(response,status){ // Required Callback Function
					if(response=="true"){
						alert("새로운 공지가 등록되었습니다.");
					}
				});
}

function logout() {
	disconnect();
	location.href="/naver/logout";
}

function connect(alarm){
	console.log("load");
	var alarmFlag=alarm;
	if(alarmFlag=="YES"){
		alert("새로운 공지가 있습니다.");
	}
	var socket = new SockJS('/naver/hello');
	stompClient=Stomp.over(socket);
	stompClient.connect({}, function(frame) {
    	
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(calResult){
        	showResult(JSON.parse(calResult.body).content);
        });
    });
}
