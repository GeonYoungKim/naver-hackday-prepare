<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTH HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dTH">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


<%
	String beforeNo = "<<";
	String afterNo = ">>";
	Map<String, Object> map = (Map) request.getAttribute("map");
	System.out.println(map);
	int before = (Integer) ((List) map.get("footerList")).get(0);
	int after = (Integer) ((List) map.get("footerList")).get(((List) map.get("footerList")).size() - 1);
	String id = request.getAttribute("id").toString();
	int unit = (Integer) request.getAttribute("unit");
	String alarm = map.get("alarm").toString();
	System.out.println(alarm);
%>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script>

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

</script>

</head>
<script>
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
</script>
<body onload="connect('<%=alarm%>')">
	<div>
		<button onclick="logout()">로그아웃</button>
	</div>
	<table BORDER="1" BORDERCOLOR="black" CELLPADDING="5" ALIGN="center">
		<TR>
			<TH align="center" WIDTH="70">컨텐츠</TH>
			<TH align="center" WIDTH="70">그룹</TH>
		<TR>
			<%
				for (int i = 0; i < ((List) map.get("tableList")).size(); i++) {
			%>
		
		<TR>
			<td align="center" WIDTH="70"><a
				href="/naver/notice-select?num=<%=((Integer) ((List<Map<String, Object>>) map.get("tableList")).get(i).get("num"))%>"><%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("content")%></a></td>
			<td align="center" WIDTH="70"><%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("notice_element")%></td>
			<td><button
					onclick="noticeUpdate('<%=id%>','<%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("num")%>','<%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("user_id")%>')">수정</button></td>
			<td><button
					onclick="noticeDelete('<%=id%>','<%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("num")%>','<%=((List<Map<String, Object>>) map.get("tableList")).get(i).get("user_id")%>','<%=((Integer) map.get("pagingNo"))%>','<%=unit%>')">삭제</button></td>
		</TR>
		<%
			}
		%>
	</table>
	<table>
		<%
			if (before > 10) {
		%>
		<tr>
			<td><a href="javascript:void(0);"
				onclick="post('/naver/notice', {id: '<%=id%>',no:'<%=before - 1%>',unit:'<%=unit%>'})"><%=beforeNo%></a></td>
		</tr>
		<%
			}
		%>
		<tr>
			<%
				for (int i = 0; i < ((List) map.get("footerList")).size(); i++) {
					if (((List) map.get("footerList")).get(i) == map.get("pagingNo")) {
			%>
			<td><a href="javascript:void(0);"
				onclick="post('/naver/notice', {id: '<%=id%>',no:'<%=((List) map.get("footerList")).get(i)%>',unit:'<%=unit%>'})"><b><%=((List) map.get("footerList")).get(i)%></b></a>
				
			</td>
			<%
				} else {
			%>
			<td><a href="javascript:void(0);"
				onclick="post('/naver/notice', {id: '<%=id%>',no:'<%=((List) map.get("footerList")).get(i)%>',unit:'<%=unit%>'})"><%=((List) map.get("footerList")).get(i)%></a>
				
			</td>
			<%
				}
				}
			%>
		</tr>
		<%
			if ((after % 10) == 0 && after != (Long) map.get("allNo")) {
		%>
		<tr>
			<td>
			<a href="javascript:void(0);"
				onclick="post('/naver/notice', {id: '<%=id%>',no:'<%=after + 1%>',unit:'<%=unit%>'})"><%=afterNo%></a></td>
				<%-- 
			<a
				href="/naver/notice/<%=id%>?no=<%=after + 1%>&unit=<%=unit%>"><%=afterNo%></a></td> --%>
		</tr>
		<%
			}
		%>

	</table>

	<form>
		<table CELLPADDING="10" ALIGN="center">
			<tr>
				<td><select id="unit" " name="unit"
					onchange="chageLangSelect('<%=id%>')">
						<option disabled selected>단위를 지정해 주세요</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
				</select>
			</tr>
		</table>
	</form>
	<button type="submit"
		onclick="location.href='/naver/insert-notice-form'">공지사항 추가</button>
</body>
</html>