<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session != null) {
		System.out.println(session.getAttribute("user"));
		session.setAttribute("user", null);
		session.setAttribute("username", null);
		session = null;
	}

response.sendRedirect("index.jsp");
%>
<html>
<head>
	<title> SSAS picture service | logout </title>
</head>
<body>
	<h1> Successfully logged out! </h1>
	<a href="index.jsp"> login </a>
</body>
</html>