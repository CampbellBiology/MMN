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
	<table border="1">
		<tr>
			<th>업종 코드</th>
			<th>업종명</th>
		</tr>


		<%
		DB_Conn _db = new DB_Conn();
		//dao.dbConn();   db 연결 확인 작업
		_db.constructStoreMap();

		ArrayList<storeData> list = _db.storefindAll();
		for (storeData dto : list) {
		%>

		<tr>
			<td><%=dto.storeCode%></td>
			<td><%=dto.storeName%></td>

		</tr>

		<%
		} //for 문의 끝
		%>
	</table>

</body>
</html>