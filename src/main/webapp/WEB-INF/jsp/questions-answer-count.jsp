<%@ page import="java.util.List,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="javaQuiz.model.Question"%>
<%@page import="javaQuiz.dto.QuestionWithAnswerCount"%>

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

<table border = "1" width = "70%" align = "center">
	<thead>
		<tr>
			<th>Question Id</th>
			<th>Question</th>
			<th>Question Count</th>
			<th>
			<% if ("correct".equals(request.getAttribute("mode"))) { %>
			Correct Answer Count
			<%} else if("wrong".equals(request.getAttribute("mode"))) { %>
			Wrong Answer Count
			<%} %>
			</th>
			<th>
			<% if ("correct".equals(request.getAttribute("mode"))) { %>
			Percentage Correct Answers
			<%} else if("wrong".equals(request.getAttribute("mode"))) { %>
			Percentage Wrong Answers
			<%} %>
			</th>
		</tr>
	</thead>
	<tbody>
	<%List<QuestionWithAnswerCount> questions = (List<QuestionWithAnswerCount>) request.getAttribute("questions"); %>
	<% for ( QuestionWithAnswerCount questionCount : questions){ %>
		<tr>
			<td><%= questionCount.getQuestion().getId() %></td>
			<td><%= questionCount.getQuestion().getText() %></td>
			<td><%= questionCount.getQuestionCount()  %></td>
			<td><%= questionCount.getCorrectAnswerCount()   %></td>
			<td><%= questionCount.getPercentage()   %></td>
		</tr>
	<% } %>
	</tbody>
</table>

</body>
</html>
