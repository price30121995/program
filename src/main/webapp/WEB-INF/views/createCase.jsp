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
<link rel="stylesheet" type="text/css" href="css1/createCase.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
</style>
<script>
	$(document).ready(function() {
		
		 $('form[id="searchAppForm"]').validate({
			  wrapper: 'div',
		        errorLabelContainer: "#messageBox",
		        debug: false,
		        rules: {
		        	appId: "required",
		        },
		        messages: {
		        	appId: "Please enter Application Number.",
		        },
			submitHandler : function(form) {
				form.submit();
			}
		}); 
		
		
		$("#appId").keyup(function() {
			//alert("hi");
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : window.location + "/searchAppId",
				dataType : "json",
				success : function(data) {
					$("#appId").autocomplete({
						source : data
					});
				},
			});
		});
		
		$(window).load(function(){
			if($("#appSearchStatus").val()!="")
				$(".createCase").show();
			else
				$(".createCase").hide();
		});
	});
</script>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies3.jpg">
	<br>
	<br>
	<br>
	<form class="example" action="searchApplicant" method="GET" id="searchAppForm"
			style="margin:auto;max-width:300px">
		<input type="search" name="appId" id="appId"
				placeholder="Enter Application Number">
		<button type="submit" value="Search" id="searchBtn"><i class="fa fa-search"></i></button>
		<div id="messageBox"></div>
	</form>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="createCase">
		<h1>Create Case</h1>
		<table>
			<tr>
				<td><p>Applicant Id:</p></td>
				<td><p>${appModel.appId}</p></td>
			</tr>
			<tr>
				<td><p>Name:</p></td>
				<td><p>${appModel.firstName}${appModel.lastName}</p></td>
			</tr>
			<tr>
				<td><p>Date Of Birth :</p></td>
				<td><p>${appModel.dob}</p></td>
			</tr>
			<tr>
				<td><p>Gender :</p></td>
				<td><p>${appModel.gender}</p></td>
			</tr>
			<tr>
				<td><p>SSN :</p></td>
				<td><p>XXX - XX - ${appModel.ssn.toString().substring(5,9)}</p></td>
			</tr>
			<tr>
				<td><p>Phone Number :</p></td>
				<td><p>${appModel.phno}</p></td>
			</tr>
			<tr>
				<td><p>Email:</p></td>
				<td><p>${appModel.email}</p></td>
			</tr>
		</table>
		<input type="hidden" name="appSearchStatus" id="appSearchStatus"
			value="${appModel.appId}"> <a
			href="createCase?appId=${appModel.appId}" id="createCaseBtn"><input
			type="submit" value="Create Case"></a>

	</div>

</body>
</html>