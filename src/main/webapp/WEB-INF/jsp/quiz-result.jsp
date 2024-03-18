<%@ page import="java.util.List,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javaQuiz.model.User" %>
<%@ page import="javaQuiz.dto.UserWithAnswerCount" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <title><%= request.getAttribute("msg") %></title>
    <link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>
<h1><%= request.getAttribute("msg") %></h1>

<%
 	List<UserWithAnswerCount> participants = (List<UserWithAnswerCount>) request.getAttribute("participants"); 
    String userName = request.getUserPrincipal().getName();
    //String userName = ${pageContext.request.userPrincipal.name};
    String firstParticipantUsername = participants.get(0).getUser().getUsername();
    if (userName.equals(firstParticipantUsername)) {
%>
    <h1>Čestitam vi ste na prvom mestu!</h1>
<%
    }
%>

<table border="1" width="90%" align="center">
    <thead>
    <tr>
        <th>Broj ispravnih odgovora</th>
        <th>Ime</th>
        <th>Prezime</th>
        <th>Username</th>
    </tr>
    </thead>
    <tbody>


    <% for (UserWithAnswerCount participant : participants) { %>
        <tr>
            <td><%= participant.getAnswerCount() %></td>
            <% User user = participant.getUser(); %>
            <td><%= user.getIme() %></td>
            <td><%= user.getPrezime() %></td>
            <td><%= user.getUsername() %></td>
        </tr>
    <% } %>
    </tbody>
</table>
<br><br><br><br>
<a href ="/user/homeuser">Vrati se na pocetnu stranu</a>
</body>
</html>
