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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css1/planReg.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
	
<style type="text/css">

.inputWithIcon {
	position: relative;
}

.inputWithIcon i {
	position: absolute;
	left: 0;
	top: 0px;
	padding: 9px 8px;
	color: #aaa;
	transition: 0.3s;
}

.inputWithIcon input:focus + i {
  color: dodgerBlue;
}

</style>

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
<body background="images/ies.jpg">

	<h3 style="color:yellow;text-align:center">${success}</h3>
	<h3 style="color:red;text-align:center">${failure}</h3>
	<div class="planform">
		<h1>Plan Register</h1>
		<form:form action="planReg" id="planRegForm" method="POST" modelAttribute="planModel">
			<p>Plan Name</p>
			<div class="inputWithIcon">
			<form:input path="planName" id="pname" placeholder="Plan Name" />
			<i class="fa fa-user fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<span id="planMsg" style="color: red"></span>
			<p>Plan Description</p>
			<div class="inputWithIcon">
			<form:textarea path="planDescription" id="pdesc" placeholder="Write some description here "/>
			<i class="fa fa-window-maximize" aria-hidden="true"></i>
			</div>
			<p>Plan Start Date</p>
			<div class="inputWithIcon">
			<form:input path="planStartDate" id="sdate" placeholder="Start Date" />
			<i class="fa fa-calendar" aria-hidden="true"></i>
			</div>
			<p>Plan End Date</p>
			<div class="inputWithIcon">
			<form:input path="planEndDate" id="edate" placeholder="End Date" />
			<i class="fa fa-calendar" aria-hidden="true"></i>
			</div> 
			<input type="submit" value="REGISTER" id="createPlanBtn"/>		
		</form:form>
	</div>
	</body>
 </html>