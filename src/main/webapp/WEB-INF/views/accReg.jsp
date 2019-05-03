<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Account Registration Form</title>
<style>
.error {
	color: #FF0000;
}
</style>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css1/accRegistration.css">
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
.inputWithIcon #pw {
	position: absolute;
	left: 0;
	top: 20px;
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

		$('form[id="accRegForm"]').validate({
			rules : {
				firstName : 'required',
				lastName : 'required',
				email : {
					required : true,
					email : true,
				},
				password : {
					required : true,
					minlength : 5,
				},
				dob : 'required',
				gender : 'required',
				role : 'required',
				phno : 'required',
				ssn : 'required'
			},
			messages : {
				firstName : 'Please enter first name',
				lastName : 'please enter last name',
				email : 'Please enter a valid email',
				password : {
					required : 'Please enter password',
					minlength : 'Password must be at least 5 characters long'
				},
				dob : 'Please select dob',
				//gender : 'Please select Gender',
				role : 'Please select a Role',
				phno : 'Please enter Phno',
				ssn : 'Please enter SSN'
			},
			submitHandler : function(form) {
				form.submit();
			}
		});

		$("#email").blur(function() {
			var enteredEmail = $("#email").val();
			$.ajax({
				url : window.location + "/validateEmail",
				data : "email=" + enteredEmail,
				success : function(result) {
					if (result == 'Duplicate') {
						$("#emailMsg").html("Email already registered.!!");
						$("#email").focus();
						$("#createAccBtn").prop("disabled", true);
					} else {
						$("#emailMsg").html("");
						$("#createAccBtn").prop("disabled", false);
					}

				}
			});

		});

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "1960:" + new Date(),
			maxDate : new Date(),
			dateFormat : 'dd/mm/yy'
		});
	});
</script>
</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies7.jpg">

	<h3 style="color: white; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform">
		<h1>Account Registration</h1>
		<form:form action="accReg" method="POST" modelAttribute="accModel"
			id="accRegForm">
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
			<p>Email :</p>
			<div class="inputWithIcon">
			<form:input path="email" id="email" placeholder="Email" />
			<i class="fa fa-envelope fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<span id="emailMsg" style="color: red"></span>
			<div class="inputWithIcon">
			<p>Password :</p>
			<form:password path="password" id="pwd" placeholder="Password" />
			<i class="fa fa-unlock-alt" aria-hidden="true" id="pw"></i>
			</div>
			<p>Date Of Birth :</p>
			<div class="inputWithIcon">
			<form:input path="dob" id="datepicker" placeholder="DOB" />
			<i class="fa fa-calendar" aria-hidden="true"></i>
			</div>
			<p>Gender :</p>
			<form:radiobuttons path="gender" items="${gendersList}" />
			<p>SSN NO :</p>
			<div class="inputWithIcon">
			<form:input path="ssn" id="ssn" placeholder="SSN NO" />
			<i class="fa fa-list-alt" aria-hidden="true"></i>
			</div>
			<p>Phone Number :</p>
			<div class="inputWithIcon">
			<form:input path="phno" id="phno" placeholder="Phone no" />
			<i class="fa fa-phone fa-lg fa-fw" aria-hidden="true"></i>
			</div>
			<p>Role :</p>
			<div class="inputWithIcon">
			<form:select path="role" id="role" items="${rolesList}" />
			<i class="fa fa-users" aria-hidden="true"></i>
			</div>
			<input type="submit" value="Register" id="createAccBtn" />
		</form:form>
	</div>

</body>
</html>