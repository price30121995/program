<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Income Details</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css1/incomeDetails.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(document).ready(function() {
		$(".weekInc").hide();
		$("input[type=radio]").change(function(){
			if($("input[type=radio]:checked").val().length==2)
				$(".weekInc").hide();
			else
				$(".weekInc").show();
		});
		
		$('form[id="accRegForm"]').validate({
			rules : {
				isEmployee : 'required'
				
			},
			messages : {
				isEmployee : 'Please select Employment Status',
				
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
</script>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies8.jpg">

	<h3 style="color: green; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform">
		<h1>Income Details</h1>
		<form:form action="incomeDetails" method="POST" modelAttribute="incomeDetails"
			id="incomeDetailsForm">
			<p>Case Id :</p>
			<form:input path="caseId" readonly = "true" id="caseId"/>
			<p>Individual's Name :</p>
			<form:input path="indivName" readonly = "true" id="indName"/>
			<p>Is Working Employee:</p>
			<form:radiobuttons path="isEmployee" items="${listIsEmp}" id="isEmployee"/>
			
			<div class="weekInc">
			<p>Weekly Income:</p>
			$<form:input path="weeklyIncome" id="weeklyIncome" placeholder="Weekly Income"/></div>
			
			<p>Other Income:</p>
			$<form:input path="otherIncome" placeholder="Other Income" id="otherIncome"/>
			
			<input type="submit" value="Next" id="createAppBtn" />
		</form:form>
	</div>

</body>
</html>