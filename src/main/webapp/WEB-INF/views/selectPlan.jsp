<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Select Plan</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css1/selectPlan.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	
</script>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies12.jpg">

	<h3 style="color: green; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform">
		<h1>Select Plan</h1>
		<form:form action="selectPlan" method="POST" modelAttribute="planModel"
			id="selectPlanForm">
			<p>Case Id :</p>
			<form:input path="caseId" id="cid" placeholder="CaseId" readonly="true" />
			<p>First Name</p>
			<form:input path="firstName" id="fname" placeholder="First Name" readonly="true" />
			<p>LastName</p>
			<form:input path="lastName" id="lname" placeholder="Last Name" readonly="true" />
			<p>Select Plan :</p>
			<form:select path="planId" id="planName" items="${planList}" />
			
			<input type="submit" value="Next" id="createAppBtn" />
		</form:form>
	</div>

</body>
</html>