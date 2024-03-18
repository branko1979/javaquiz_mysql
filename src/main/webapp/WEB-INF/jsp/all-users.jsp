<%@ page import="java.util.List,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="javaQuiz.model.User"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>All Participants</title>
<link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>
	<h1>${requestScope.msg }</h1>
	<table border="1" width="90%" align="center">
		<thead>
			<tr>
				<th>Ime</th>
				<th>Prezime</th>
				<th>Username</th>
				<th>Password</th>
				<th>Datum zadnjeg prijavljivanja</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>

			<%
			List<User> participants = (List<User>) request.getAttribute("participants");
			for (User participant : participants) {
			%>
			<tr>
				<td><%=participant.getIme()%></td>
				<td><%=participant.getPrezime()%></td>
				<td><%=participant.getUsername()%></td>
				<td><%=participant.getPassword()%></td>
				<td><%=participant.getDatum()%></td>
				<td><%=participant.getEmail()%></td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>

</body>
</html>
