<%@ page import="java.util.List,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.springframework.security.web.csrf.CsrfToken" %>
<%@page import="javaQuiz.model.User"%>
<%@page import="javaQuiz.model.Question"%>
<%@page import="javaQuiz.model.WrongAnswer"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache"> 
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>All Questions</title>
<link rel="stylesheet" type="text/css" href="/css/quizStyle.css">
</head>
<body>

<h1>${requestScope.msg }</h1>
<% if ("all-questions".equals(request.getAttribute("mode"))) { %>
    <br>
    <h2><a href="new-question">Add new question</a></h2>
    <br>
<table border = "1" width = "90%" align = "center">
	<thead>
		<tr>
			<th>Id</th>
			<th>Question</th>
			<th>Answers</th>
			<th></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	
	<% 
	Map<Question, List<WrongAnswer>> questionsAndAnswers = (Map<Question,List<WrongAnswer>>) request.getAttribute("questionsAndAnswers");
	for (Map.Entry<Question,List<WrongAnswer>> question : questionsAndAnswers.entrySet()) { 
	%>
		<tr>	
			<td><%= question.getKey().getId()%></td>
			<td><%= question.getKey().getText()%></td>
			<td>
			<%= question.getKey().getTrueAnswer()%>
				<br>
				<br>
				Wrong answers:
				<ul>
				<% for (WrongAnswer wrongAnswer : question.getValue()) {%>
					<li><%= wrongAnswer.getText()%></li>
				 <% } %>
				</ul>
			</td> 
			<td><a href="delete-question?id=<%= question.getKey().getId()%>">DELETE QUESTION + ANSWERS</a></td> 
			<td><a href="update-question?id=<%= question.getKey().getId()%>">UPDATE</a></td>
		</tr>
		<% } %>
	</tbody>
</table>
<%
 } else if("new-question".equals(request.getAttribute("mode"))) { 
%>
    <br><br>
    <div id ="container">
	<form action="save-question" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="id">
        <label>Insert text for question: </label><br>
        <input type="text" name="text" required="required" /><br><br>
        <label>Insert correct answer: </label><br>
        <input type="text" name="trueAnswer" required="required" />
        <input type="submit" value="Potvrdi">
    </form>
    </div>
 <% } else if ("update-question".equals(request.getAttribute("mode"))) {
	 	Question oldQuestion = (Question) request.getAttribute("oldQuestion");
	 %>
    <br><br>
    <div id ="container">
    <form action="update-wrong-answers" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="id" value="<%= oldQuestion.getId()%>" >
        <label>Change text for question: </label><br>
        <input type="text" name="text" required="required" value="<%= oldQuestion.getText() %>" /><br><br>
        <label>Insert new correct answer: </label><br>
        <input type="text" name="trueAnswer" required="required" value ="<%= oldQuestion.getTrueAnswer() %>"/>
        <input type="submit" value="Potvrdi izmenu">
    </form>
    </div>
<% } else if ("wrong-answers".equals(request.getAttribute("mode"))) { %>
    <div id ="container">
    <form id="myForm" action="save-wrong-answers" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="questionId" value="<%= request.getAttribute("question_id") %>">
        <input type="hidden" name="origin" value="newWrongAnswers">
        <div id="fieldsContainer">
            <input type="text" name="field[]" placeholder="Insert wrong answer 1">
        </div>
        <br>
        <button type="button" onclick="addNewField()">Add more wrong questions</button><br><br>
        <input type="submit" value="That's all wrong questions!">
    </form>
    </div>
<% } else if ("update-wrong-answers".equals(request.getAttribute("mode"))) { %>
    <table border="1" width="70%" align="center">
        <thead>
            <tr>
                <th>Id</th>
                <th>Wrong answer</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <% List<WrongAnswer> wrongAnswers = (List<WrongAnswer>) request.getAttribute("wrongAnswers");
            for (WrongAnswer wrongAnswer : wrongAnswers) { %>
                <tr>
                    <td><%= wrongAnswer.getId() %></td>
                    <td><%= wrongAnswer.getText() %></td>
                    <td><a href="update-wrong-answer?id=<%= wrongAnswer.getId() %>&questionId=<%= request.getAttribute("question_id") %>">UPDATE</a></td>
                    <td><a href="delete-wrong-answer?id=<%= wrongAnswer.getId() %>&questionId=<%= request.getAttribute("question_id") %>">DELETE</a></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    <br>
    <h2>Add wrong answer(s) for question:</h2>
    <div id ="container">
    <form id="myForm" action="save-wrong-answers" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="questionId" value="<%= request.getAttribute("question_id") %>">
        <input type="hidden" name="origin" value="addWrongAnswers">
        <div id="fieldsContainer">
            <input type="text" name="field[]" placeholder="Insert wrong answer 1">
        </div>
        <br>
        <button type="button" onclick="addNewField()">Add more wrong questions</button><br><br>
        <input type="submit" value="Submit aditional (wrong) answers">
    </form>
    </div>
<% } else if ("update-wrong-answer".equals(request.getAttribute("mode"))) {
	  WrongAnswer wrongAnswer = (WrongAnswer) request.getAttribute("wrongAnswer");
	  int question_id = (int) request.getAttribute("question_id");
	%>
	<div id ="container">
    <form action="save-wrong-answer" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="questionId" value="<%= question_id %>">
        <input type="hidden" name="id" value="<%= wrongAnswer.getId() %>">
        <input type="hidden" name="origin" value="addWrongAnswers">   
        <input type="text" name="text" required="required" value="<%= wrongAnswer.getText() %>">
        <input type="submit" value="Submit to change wrong answer">
		</form>
		</div>
	<% } %>
<br>
<br>
<h2><a href="all-questions">Back to all questions!</a></h2>
<br>
<br>
<h2><a href="homeadmin">BACK TO HOMEADMIN!</a></h2>
 <script> 
      function addNewField() {
          var container = document.getElementById('fieldsContainer');
          var fieldCount = container.getElementsByTagName('row').length + 2;
  		var row = document.createElement('row');
  		row.innerHTML = '<br><input type="text" name="field[]" placeholder ="Insert wrong answer ' + fieldCount +'"/><br>';
  		container.appendChild(row);
      }
</script>
</body>
</html>
