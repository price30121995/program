<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Plan Registration</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css/planReg.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(function() {
		$('form[id="planRegForm"]').validate({
			rules : {
				planName : 'required',
				planDescription : 'required',
				planStartDate : 'required',
				planEndDate : 'required',

			},
			messages : {
				planName : 'Please enter plan name',
				planDescription : 'please enter description details',
				planStartDate : 'Please select start date',
				planEndDate : 'Please select end date',
			},
			submitHandler : function(form) {
				form.submit();
				
			}
		});

		$("#pname").blur(function() {
			var enteredPlan = $("#pname").val();

			$.ajax({
				url : window.location + "/validatePlan",
				data : "plan=" + enteredPlan,
				success : function(result) {
					if (result == 'Duplicate') {
						$("#planMsg").html("Plan already Existing.!!");
						$("#pname").focus();
						//$("#createPlanBtn").hide();
						$("#createPlanBtn").prop('disabled', true);
					} else {
						$("#planMsg").html("");
						$("#createPlanBtn").prop('disabled', false);
					}
				}
			});
		});

		$("#sdate").datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate : new Date(),
					onSelect : function(date) {

						//Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
						$('#edate').datepicker('option', "minDate",
								$("#sdate").datepicker("getDate"));

					}
				});

		$("#edate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true

		});

	});
</script>
</head>
<%@ include file="header-inner.jsp"%><br />
<body background="images/planReg.jpg">

	<h3 style="color:yellow;text-align:center">${success}</h3>
	<h3 style="color:red;text-align:center">${failure}</h3>
	<div class="planform">
		<h1>Plan Register</h1>
		<form:form action="planReg" id="planRegForm" method="POST" modelAttribute="planModel">
			<p>Plan Name</p>
			<form:input path="planName" id="pname" placeholder="Plan Name" />
			<span id="planMsg" style="color: red"></span>
			<p>Plan Description</p>
			<form:textarea path="planDescription" id="pdesc"/>
			<p>Plan Start Date</p>
			<form:input path="planStartDate" id="sdate" placeholder="Start Date" />
			<p>Plan End Date</p>
			<form:input path="planEndDate" id="edate" placeholder="End Date" /> 
			<input type="submit" value="REGISTER" id="createPlanBtn"/>		
		</form:form>
	</div>
	</body>
 </html>