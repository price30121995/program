<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Child Details</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet" type="text/css" href="css1/childDetails.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	
<style type="text/css">
	#edit {
    color: blue;
    font-size: 18px;
}
#delete {
    color: red;
    font-size: 18px;
}

</style>

<script>
	
	$(document).ready(function() {
		/* $('#childTbl').DataTable({
			"pagingType" : "full_numbers"
		});
 */
		$(".weekInc").hide();
		$("input[type=radio]").change(function() {
			if ($("input[type=radio]:checked").val().length == 2)
				$(".weekInc").hide();
			else
				$(".weekInc").show();
		});

		$('form[id="childDetailsForm"]').validate({
			rules : {
				childName : 'required',
				childGender: 'required',
				childDob: 'required',
				ssn1: {
					required: true,
					minlength: 3
				},
				ssn2: {
					required: true,
					minlength: 2
				},
				ssn3: {
					required: true,
					minlength: 4
				}

			},
			messages : {
				childName : 'Please Enter Child Name',
				childGender: 'Please Select Child Gender',
				childDob: 'Please Enter Child Date of Birth',
				ssn1: 'Please Enter Valid SSN',
				ssn2: 'Please Enter Valid SSN',
				ssn3: 'Please Enter Valid SSN',

			},
			submitHandler : function(form) {
				form.submit();
			}
		});

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "1960:" + new Date(),
			maxDate : new Date(),
			dateFormat : 'dd/mm/yy'
		});

		$('.input-ssn-number').on('keyup change', function() {
			$t1 = $("#ssn1");
			$t2 = $("#ssn2");
			$t3 = $("#ssn3");
			if ($t1.val().length > 2) {
				$t2.prop("disabled", false);
				$t2.focus();
			}else{
				$t2.prop("disabled", true);
				$t3.prop("disabled", true);
			}
			if ($t2.val().length > 1) {
				$t3.prop("disabled", false);
				$t3.focus();
			}else{
				$t3.prop("disabled", true);
			}

			$t3.blur(function() {
				var card_number = '';
				card_number += $t1.val() + $t2.val() + $t3.val();
				$('#ssn').val(card_number);
			})
		});

		$(window).load(function() {
			$t1 = $("#ssn1");
			$t2 = $("#ssn2");
			$t3 = $("#ssn3");
			$ssn = $("#ssn");
			$s1 = $ssn.val().substring(0, 3);
			$s2 = $ssn.val().substring(3, 5);
			$s3 = $ssn.val().substring(5, 9);
			$t1.val($s1);
			$t2.val($s2);
			$t3.val($s3);
			//for hiding and showing add/update button
			if($("#childId").val().length==0){
				$("#updateBtn").hide();
				$("#addBtn").show();
			}else{
				$("#updateBtn").show();
				$("#addBtn").hide();
			}
			//for disabling next button
			$no=$("#childNos").val();
			if(($(".trClass").length)<1)
				$("#nextBtn").prop("disabled", true);
			else
				$("#nextBtn").prop("disabled", false);
			
		});
	});
</script>
<style type="text/css">
a {
	text-decoration: none;
}
</style>

</head>
<%@ include file="header-inner.jsp"%>
<body background="images/ies19.jpg">

	<h3 style="color: white; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="regform" style="width=80%">
		<h1>CHILD DETAILS</h1>

		<form:form action="childDetails" method="POST"
			modelAttribute="childDetails" id="childDetailsForm">
			<p>Case Id :</p>
			<form:input path="caseId" readonly="true" />
			<p>Individual's Name :</p>
			<form:input path="indivName" readonly="true" />
			<div style="border: 2; border-color: yellow;">
				<p>Child Name:</p>
				<form:input path="childName" id="childName"
					placeholder="Enter Child Name" />
				<p>Child Gender:</p>
				<form:radiobuttons path="childGender" items="${listGender}" />
				<p>Child Date Of Birth :</p>
				<form:input path="childDob" id="datepicker" placeholder="DOB" />
				<p>Child SSN :</p>
				<input type="num" id="ssn1" class="input-ssn-number" maxlength="3" />
				<input type="num" id="ssn2" class="input-ssn-number" maxlength="2" />
				<input type="num" id="ssn3" class="input-ssn-number1" maxlength="4" />
				<form:hidden path="childSSN" id="ssn" />
				<form:hidden path="childId" id="childId" />
				<input type="submit" value="Add" id="addBtn">
				<input type="submit" value="Update" id="updateBtn"><br>
			</div>
			<div class="viewPlan" style="width=80%">
				<table border="1" id="childTbl">
					<thead>
						<tr>
							<th>S.No</th>
							<th>Child Id</th>
							<th>Child Name</th>
							<th>Gender</th>
							<th>DOB</th>
							<th>SSN</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="childTblBody">
						<c:forEach items="${listChildModels}" var="child" varStatus="index">
							<tr class="trClass">
								<td><c:out value="${index.count}" /></td>
								<td><c:out value="${child.childId}" /></td>
								<td><c:out value="${child.childName}" /></td>
								<td><c:out value="${child.childGender}" /></td>
								<td><c:out value="${child.childDob}" /></td>
								<td><c:out value="${child.childSSN}" /></td>

								<td align="center"><a href="editChildDetails?childId=${child.childId}" id="editBtn"><i class="fa fa-pencil" aria-hidden="true"  id="edit">|</i></a>
								<a href="deleteChildDetails?childId=${child.childId}&caseId=${child.caseId}&indivName=${child.indivName}" onclick='return confirm("Are you sure, you want to delete ?")'><i class="fa fa-trash" aria-hidden="true" id="delete"></i></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<a href="determineEligibility"><input type="button" value="Next" id="nextBtn"/></a>
		</form:form>
	</div>

</body>
</html>
