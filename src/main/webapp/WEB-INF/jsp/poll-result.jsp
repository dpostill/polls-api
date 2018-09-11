<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<c:url value="/css/main.css" var="jstlCss" />
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${jstlCss}" />
</head>

<body>

<h1>Poll Results</h1>

<div class="col-lg-6">
	<c:forEach items="${results}" var="result" varStatus="loop">
		<h4>Q${loop.index + 1} - ${result.question}</h4>
		
		<table class="table table-condensed result_table">
			<tr>
		        <th>Choice</th>
		        <th>Votes</th>
		    </tr>
	 	    <c:forEach items="${result.choices}" var="choice">
			    <tr>
			    	<td>${choice.choice}</td>
			    	<td>${choice.votes}</td>
			    </tr>
		    </c:forEach>
		</table>  
		<br>
	</c:forEach>
</div>

</body>
</html>
