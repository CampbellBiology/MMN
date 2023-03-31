<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="DB.*"%>
<%@page import="DataClass.*"%>

<%@page import="java.util.ArrayList"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>메뉴</title>
</head>
<body>
	<h2>메뉴</h2>
	<hr>

	<%
	DB_Conn _db = new DB_Conn();
	_db.constructMenuMap();
	ArrayList<menuData> list = _db.menufindAll();
	%>
	<select name="menu">
		<%
		int cnt = 0;
		for (menuData md : list) {
		%>
		<option value="<%=md.getFoodCode()%>" <%=cnt == 0 ? "selected" : ""%>>
			<%=md.getFoodName()%></option>
		<%
		cnt++;
		}
		%>

	</select>

</body>
</html>