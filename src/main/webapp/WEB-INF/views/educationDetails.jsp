<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Education Details</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css1/educationPlan.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(document).ready(function() {
				
		$('form[id="educationDetailsForm"]').validate({
			rules : {
				completedYear : 'required'
				
			},
			messages : {
				completedYear : 'Please Enter Education Completion Year',
				
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
</script>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies18.jpg">

	<h3 style="color: white; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform">
		<h1>Education Details</h1>
		<form:form action="educationDetails" method="POST" modelAttribute="educationDetails"
			id="educationDetailsForm">
			<p>Case Id :</p>
			<form:input path="caseId" id="cid" readonly = "true"/>
			<p>Individual's Name :</p>
			<form:input path="indivName" id="indName" readonly = "true"/>
			<p>Highest Qualification:</p>
			<form:select path="highestQlfy" id="hqly" items="${listHgstQlfy}"/>
			<p>Completed Year:</p>
			<form:input path="completedYear" id="ccy" placeholder="Enter Education Completion Year"/>
			<p>Grade:</p>
			<form:select path="grade" id="grade" items="${listGrade}"/>
			
			<input type="submit" value="Next"/>
		</form:form>
	</div>

</body>
</html>