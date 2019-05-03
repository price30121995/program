<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Applicant Registration Form</title>
<style>
.error {
	color: #FF0000
}
</style>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css1/appRegistration.css">
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
	top: 5px;
	padding: 9px 8px;
	color: #aaa;
	transition: 0.3s;
}

.inputWithIcon input:focus + i {
  color: dodgerBlue;
}

</style>

<script>
	$(document).ready(function() {

		$('form[id="appRegForm"]').validate({
			rules : {
				firstName : 'required',
				lastName : 'required',
				dob : 'required',
				gender : 'required',
				ssn : 'required'
			},
			messages : {
				firstName : 'Please enter first name',
				lastName : 'please enter last name',
				dob : 'Please select dob',
				gender : 'Please select Gender',
				ssn : 'Please enter SSN'
			},
			submitHandler : function(form) {
				form.submit();
			}
		});


		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange: "1960:"+new Date(),
			maxDate : new Date(),
			dateFormat : 'dd/mm/yy'
		});

		$('.input-ssn-number').on('keyup change', function() {
			$t1 = $("#ssn-1");
			$t2 = $("#ssn-2");
			$t3 = $("#ssn-3");
			if ($t1.val().length > 2) {
				$t2.focus();
			}
			if ($t2.val().length > 1) {
				$t3.focus();
			}
			
			$t3.blur(function() {
			 var card_number = '';
			card_number += $t1.val() + $t2.val() + $t3.val();
			$('#ssn').val(card_number);
			})
		});
		
		$(window).load(function(){
			$t1 = $("#ssn-1");
			$t2 = $("#ssn-2");
			$t3 = $("#ssn-3");
			$ssn=$("#ssn");
			$s1=$ssn.text().substring(0,2);
			//alert($ssn.text());
		});

	});
</script>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies5.jpg">

	<h3 style="color: white; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform">
		<h1>Applicant Registration</h1>
		<form:form action="appReg" method="POST" modelAttribute="appModel"
			id="appRegForm">
			<p>First Name</p>
			<div class="inputWithIcon">
			<form:input path="firstName" id="fname" placeholder="First Name" />
			<i class="fa fa-user fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<p>LastName</p>
			<div class="inputWithIcon">
			<form:input path="lastName" id="lname" placeholder="Last Name" />
			<i class="fa fa-user fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<p>Date Of Birth :</p>
			<div class="inputWithIcon">
			<form:input path="dob" id="datepicker" placeholder="DOB" />
			<i class="fa fa-calendar" aria-hidden="true"></i>
			</div>
			<p>Gender :</p>
			<form:radiobuttons path="gender" id="gen" items="${gendersList}" />
			<p>SSN NO :</p>
			<input type="num" id="ssn-1" class="input-ssn-number" maxlength="3" />
			<input type="num" id="ssn-2" class="input-ssn-number" maxlength="2" />
			<input type="num" id="ssn-3"  class="input-ssn-number1" maxlength="4" />
			<form:hidden path="ssn" id="ssn" placeholder="SSN NO" />
			<p>Phone Number :</p>
			<div class="inputWithIcon">
			<form:input path="phno" id="phno" placeholder="Phone no" />
			<i class="fa fa-phone fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<p>Email :</p>
			<div class="inputWithIcon">
			<form:input path="email" id="email" placeholder="Email" />
			<i class="fa fa-envelope fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<input type="submit" value="Register" id="createAppBtn" />
		</form:form>
	</div>

</body>
</html>