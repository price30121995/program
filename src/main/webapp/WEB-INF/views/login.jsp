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
  <link rel="stylesheet" type="text/css" href="css1/login.css">
  <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(function() {

		$('form[id="loginForm"]').validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				pwd: {
			        required: true,
			        minlength: 5
			      }
			},
			messages : {
				email : 'Please enter a valid email',
				pwd : {
					required : 'Please enter password',
					minlength : 'Password must be at least 5 characters long'
				}
			},
			submitHandler : function(form) {
				form.submit();
			}
		});

	});
</script>
</head>
<body background="images/ies4.jpg">

	<h3 style="color:red;text-align:center">${failure}</h3>
	<br><br><br>
	 <div class="loginbox">
    	<img src="images/avatar.png" class="avatar">
        <h1>Login Here</h1>
        <form action="login" method="POST" id="loginForm">
            <p>Username</p>
            <input type="text" name="email" placeholder="Enter Username"/>
            <p>Password</p>
            <input type="password" name="pwd" placeholder="Enter Password"/>
            <input type="submit" name="" value="Login"/>
            <a href="forgotPwd">Forgot password?</a><br>    
        </form>      
    </div>
</body>
</html>