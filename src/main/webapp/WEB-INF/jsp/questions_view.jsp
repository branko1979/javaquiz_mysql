<%@ page import="java.util.List,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="org.springframework.security.web.csrf.CsrfToken" %>
<%@page import="javaQuiz.model.WrongAnswer"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz</title>
<link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>
<%  
String questionText = (String) request.getAttribute("questionText");
int position = (int) request.getAttribute("position");
int numberOfQuestions = (int) request.getAttribute("numberOfQuestions");
List<WrongAnswer> answers = (List<WrongAnswer>) request.getAttribute("answers");
String userName = request.getUserPrincipal().getName();
%>
<div id="container">
<h3>Question: <%= questionText %> </h3>
<h3> Question number: <%= (position + 1) %>  /  <%= numberOfQuestions %>   </h3>
<h2>USERNAME: <%= userName %></h2>
<form action="/user/submitAnswers" method="post" id ="input_form">
<ol>
  <%  for (WrongAnswer answer : answers) { %>
    <li>
      <input type="checkbox" name="selectedAnswers" style="width: 50px;" value="<%= answer.getId() %>">
      <%= answer.getText() %>
    </li>
  <% } %>
</ol>
<br><br>
<% if (position != 0) { %>
  <a href="<%= position - 1 %>">Previous Question</a>
<% } %>

 <% if (position + 1 < numberOfQuestions) { %>
  <a href="<%= position + 1 %>">Skip Question - Don't delete old Answers</a>
<% } %>
<br><br>
<input type="hidden" name="nextQuestionId" value="<%= position %>" />
<input type="hidden" name="userName" value="<%= request.getUserPrincipal().getName() %>"/>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<button type="submit" class="submit">Submit Answers</button>
</form>
<br><br><br>
<a href="/user/homeuser">Odustani od kviza</a>
</div>
</body>
</html>
