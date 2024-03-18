<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome page</title>
<link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>
<%
    String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<h1>Dobrodosao USER <%= username %>!</h1>
<br>
<br>
<div id="container">
<a href = "question/start">POKRENI KVIZ!</a>
<br>
<br>
    <form action="/logout" method="post" id ="input_form">        
        <br>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="submit" value="Logout" class="submit" />
        <label > </label>
    </form>
    <br>
    <h3>Izaberite </h3>
</div>
</body>
</html>