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
	Map<String, Object> map = (Map) request.getAttribute("map");	
	System.out.println(map);
	String id=(String)session.getAttribute("userId");
	int unit=(Integer)request.getAttribute("unit");
%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>
<script>
function chageLangSelect(){
    var unitSelect = document.getElementById("unit");
    // select element���� ���õ� option�� value�� ����ȴ�.
    var selectValue = unitSelect.options[unitSelect.selectedIndex].value;
    location.href = "/naver/notice?no=<%=((Integer)map.get("pagingNo"))%>&unit="+selectValue;
}
function noticeUpdate(userId,map){
	var id=userId;
	var afterMap=map;
	
	var jsMap = new Map(JSON.parse(afterMap));
	alert("as");
	if(id!=map.noticeId){
		alert("���� ������ �����ϴ�.");
	}else{
		if(confirm("���� ���� �Ͻðڽ��ϱ�?")){
			
		}else{
			alert("���� ��� �Ǿ����ϴ�.");
		}
	}
}
function noticeDelete(userId,num,noticeId){
	var id=userId;
	var jsNum=num;
	var noticeId = noticeId;
	
	if(id!=noticeId){
		alert("���� ������ �����ϴ�.");
	}else{
		if(confirm("���� ���� �Ͻðڽ��ϱ�?")){
   			$.post("http://localhost:8080/naver/delete-notice", //Required URL of the page on server
   					{ // Data Sending With Request To Server
   					noticeNum:jsNum
   					},
   					function(response,status){ // Required Callback Function
   						location.href = "/naver/notice?no="+<%=((Integer)map.get("pagingNo"))%>+"&unit="+<%=unit%>;
   					});
			
		}else{
			alert("���� ��� �Ǿ����ϴ�.");
		}
	}
}
</script>
<body>
	<table BORDER="1" BORDERCOLOR="black" CELLPADDING="5" ALIGN="center">
		<TR>
			<TH align="center" WIDTH="70">������</TH>
		<TR>
		<%
			for (int i = 0; i < ((List) map.get("tableList")).size(); i++) {
		%>
		
		<TR>
			<td align="center" WIDTH="70"><%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("content") %></td>
			<td><button onclick="noticeUpdate('<%=id%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i)%>')">����</button></td>
			<td><button onclick="noticeDelete('<%=id%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("num")%>','<%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("user_id")%>')">����</button></td>
		</TR>
		<%
			}
		%>
	</table>
	<table>
		<tr>
			<%
				for (int i = 0; i < ((List) map.get("footerList")).size(); i++) {
			%>
			<td><a href="/naver/notice?no=<%=((List) map.get("footerList")).get(i)%>&unit=<%=unit%>"><%=((List) map.get("footerList")).get(i)%></a></td>
			
			<%} %>
		</tr>
	</table>
	
	<form>
		<table CELLPADDING="10" ALIGN="center">
			<tr>
				<td>
					<select id="unit"" name="unit" onchange="chageLangSelect()">
						<option disabled selected>������ ������ �ּ���</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
					</select>
	            </tr>
		</table>
	</form>
		<button type="submit" onclick="location.href='/naver/insert-notice-form'">�������� �߰�</button>
	</body>

</html>