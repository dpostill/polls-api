<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<c:url value="/css/main.css" var="jstlCss" />
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${jstlCss}" />
</head>

<body>

<h1>Poll page</h1>

<form:form method="POST" commandName="selection" action="poll-submit.html">

<h4>Q ${questionIndex + 1}/${questionCount} - ${question}</h4>

<ul>
	<form:radiobuttons element="li" path="selected" items="${choices}" />
</ul>
	        
<input type="submit" value="Submit" class="btn btn-primary" />

</form:form>

</body>
</html>