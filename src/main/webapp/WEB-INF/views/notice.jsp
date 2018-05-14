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
	int unit=(Integer)request.getAttribute("unit");
%>
<body>
	<table BORDER="1" BORDERCOLOR="black" CELLPADDING="5" ALIGN="center">
		<TR>
			<TH align="center" WIDTH="70">컨텐츠</TH>
		<TR>
		<%
			for (int i = 0; i < ((List) map.get("tableList")).size(); i++) {
		%>
		
		<TR>
			<TH align="center" WIDTH="70"><%=((List<Map<String,Object>>)map.get("tableList")).get(i).get("content") %></TH>
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
<script>
function chageLangSelect(){
    var unitSelect = document.getElementById("unit");
    // select element에서 선택된 option의 value가 저장된다.
    var selectValue = unitSelect.options[unitSelect.selectedIndex].value;
    location.href = "/naver/notice?no=<%=((Integer)map.get("pagingNo"))%>&unit="+selectValue;
    
 	   
}
</script>
</html>