<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "dk.itu.ssas.project.Utils"%>
<%
	if(session != null) {
		session.removeAttribute("user");
		session.removeAttribute("username");
		session.removeAttribute("secret");
		session.invalidate();
		session = null;
	}

response.sendRedirect("index.jsp");
%>
<!DOCTYPE html>
<html>
<head>
	<title> <%= Utils.TITLE + " | logout" %> </title>
</head>
<body>
	<h1> Successfully logged out! </h1>
	<a href="index.jsp"> login </a>
</body>
</html>