<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Testing JSP</title>
<link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>
<div id="container">
<h1>UNESITE PODATKE ZA NOVOG KORISNIKA </h1>
<br>	

	<form  action="save-user" method="post" id ="input_form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="id">
		<br>
		<label for="ime">Ime</label>
		<input type="text" name="ime" placeholder="Uneti Vase ime:" id="ime" required />
		<br>
		<label for="prezime">Prezime</label>
		<input type="text" name="prezime" placeholder="Uneti prezime:" id="prezime" required />
		<br>
		<label for="username">Username</label>
		<input type="text" name="username" placeholder="Uneti username:" id="username" required />
		<br>
		<label for="password">Password</label>
		<input type="password" name="password" placeholder="Uneti password:" id="password" required />
		<br>
		<label for="email">Email</label>
		<input type="email" name="email" placeholder="pera.peric@gmail.com" required />
		<span class="form_hint">Validan format je: pera.peric@gmail.com</span>
		<br>
		<br>
		<input type="hidden" name="role" value ="USER">

		<input  type="submit" value="Posalji podatke" class="submit">
	</form>
</div>>
</body>
</html>