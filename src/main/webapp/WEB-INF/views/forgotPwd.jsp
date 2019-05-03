<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css1/forgotPwd.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(function() {

		$('form[id="forgotPwdForm"]').validate({
			rules : {

				email : {
					required : true,
					email : true
				}
			},//rules
			messages : {
				email : 'Please enter a valid email'
			},
			submitHandler : function(form) {
				form.submit();
			}
		});//validate

	});

</script>
</head>

<body background="images/forgotPwd.jpg">
	<h3 style="color: yellow; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="box">
		<h2>Enter Your Email Address.</h2>
		<form action="forgotPwd" method="POST" id="forgotPwdForm">
			
			<input type="email" name="email" id="email" placeholder="&#xf0e0; Enter Email"/> 
			<input type="submit"value="Send Recovery Link" />
			<h3 style="color: blue; text-align: right">
				<a href="/IES/">Login?</a>
			</h3>
		</form>
	</div>
</body>
</html>