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
<title>상품 목록</title>
</head>
<body>
	<h2>상품 목록</h2>
	<hr>

<%
	DB_Conn _db = new DB_Conn();
	ArrayList <menuData> list = _db.menufindAll();
%>
	<select name="food">
	<% 
		for(menuData md:list){
			%>
			<option value = "<%= md.getFoodCode() %>" selected> <%= md.getFoodName() %></option>
			<%
		}
	 %>

	</select>

</body>
</html>