<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import = "dk.itu.ssas.finalproject.Utils"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%= Utils.TITLE %></title>
	</head>
<body>
	<h1>SSAS Photo Sharing Webapp</h1>
	<h2>Existing users:</h2>
	<% if (request.getParameter("login_failure") != null) { %>
	<h3>Login failure. Try again?</h3>
	<% } %>
	<form method="get" action="login.jsp">
		Username: <input type="text" name="username"><br> 
		Password: <input type="text" name="password"><br>		
		<input type="reset" value="Reset">
		<input type="submit" value="Login">		
	</form>
	<h2>New users:</h2>
	<% if (request.getParameter("create_failure") != null) { %>
	<h3>Username already taken. Try again?</h3>
	<% } %>
	<form method="get" action="register.jsp">
		Username: <input type="text" name="username" /><br> 
		Password: <input type="password" name="password" /><br>
		<input type="reset" value="Reset">
		<input type="submit" value="Create">
	</form>
</body>
</html>