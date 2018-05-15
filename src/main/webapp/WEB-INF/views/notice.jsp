<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTH HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dTH">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
	String beforeNo="<<";
	String afterNo=">>";
	Map<String, Object> map = (Map) request.getAttribute("map");	
	System.out.println(map);
	int before=(Integer)((List)map.get("footerList")).get(0);
	int after=(Integer)((List)map.get("footerList")).get(((List)map.get("footerList")).size()-1);
	String id=(String)session.getAttribute("userId");
	int unit=(Integer)request.getAttribute("unit");
%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>
<script>
function chageLangSelect(){
    var unitSelect = document.getElementById("unit");
    // select element에서 선택된 option의 value가 저장된다.
    var selectValue = unitSelect.options[unitSelect.selectedIndex].value;
    location.href = "/naver/notice?no=<%=((Integer)map.get("pagingNo"))%>&unit="+selectValue;
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
function noticeDelete(userId,num,noticeId){
	var id=userId;
	var jsNum=num;
	var noticeId = noticeId;
	
	if(id!=noticeId){
		alert("삭제 권한이 없습니다.");
	}else{
		if(confirm("정말 삭제 하시겠습니까?")){
   			$.post("http://localhost:8080/naver/delete-notice", //Required URL of the page on server
   					{ // Data Sending With Request To Server
   					noticeNum:jsNum
   					},
   					function(response,status){ // Required Callback Function
   						location.href = "/naver/notice?no="+<%=((Integer)map.get("pagingNo"))%>+"&unit="+<%=unit%>;
   					});
			
		}else{
			alert("삭제 취소 되었습니다.");
		}
	}
}
</script>
<body>
	<table BORDER="1" BORDERCOLOR="black" CELLPADDING="5" ALIGN="center">
		<TR>
			<TH align="center" WIDTH="70">컨텐츠</TH>
			<TH align="center" WIDTH="70">그룹</TH>
		<TR>
		<%
			for (int i = 0; i < ((List) map.get("tableList")).size(); i++) {
		%>
		
		<TR>
			<td align="center" WIDTH="70"><%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("content") %></td>
			<td align="center" WIDTH="70"><%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("notice_element") %></td>
			<td><button onclick="noticeUpdate('<%=id%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("num")%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("user_id")%>')">수정</button></td>
			<td><button onclick="noticeDelete('<%=id%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("num")%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("user_id")%>')">삭제</button></td>
		</TR>
		<%
			}
		%>
	</table>
	<table>
		<%if(before>10){%>
			<tr><td><a href="/naver/notice?no=<%=before-1%>&unit=<%=unit%>"><%=beforeNo %></a></td></tr>
		<% }%>
		<tr>
			<%
				for (int i = 0; i < ((List) map.get("footerList")).size(); i++) {
					if(((List) map.get("footerList")).get(i)==map.get("pagingNo")){
			%>
				<td><a href="/naver/notice?no=<%=((List) map.get("footerList")).get(i)%>&unit=<%=unit%>"><b><%=((List) map.get("footerList")).get(i)%></b></a></td>
			
					<%}else{ %>
					<td><a href="/naver/notice?no=<%=((List) map.get("footerList")).get(i)%>&unit=<%=unit%>"><%=((List) map.get("footerList")).get(i)%></a></td>
			
					<%}
				} %>
		</tr>
		<%if((after%10)==0&&after!=(Long)map.get("allNo")){ %>
			<tr><td><a href="/naver/notice?no=<%=after+1%>&unit=<%=unit%>"><%=afterNo %></a></td></tr>
		<%} %>
		
	</table>
	
	<form>
		<table CELLPADDING="10" ALIGN="center">
			<tr>
				<td>
					<select id="unit"" name="unit" onchange="chageLangSelect()">
						<option disabled selected>단위를 지정해 주세요</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
					</select>
	            </tr>
		</table>
	</form>
		<button type="submit" onclick="location.href='/naver/insert-notice-form'">공지사항 추가</button>
	</body>

</html>