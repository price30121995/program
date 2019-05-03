<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show Plans Page</title>
<link rel="stylesheet" type="text/css" href="css1/viewPlan.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js">
	
</script>
<style type="text/css">
	#edit {
    color: blue;
    font-size: 20px;
}
#delete {
    color: red;
    font-size: 20px;
}
#active {
    color: green;
    font-size: 20px;
}

</style>

<script>
	$(document).ready(function() {
		$('#planTable').DataTable({
			//"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
			"sPaginationType" : "full_numbers"
		});
	});
	function confirmDelete() {
		return confirm("Are you sure you want to Delete ?");
	}
	function confirmActivate() {
		return confirm("Are you sure you want to Activate ?");
	}
</script>

</head>
<%@ include file="header-inner.jsp"%><br/>
<body background="images/ies14.jpg">
	<h3 style="color: green; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="viewPlans">
	<h2>VIEW PLANS</h2>
	<table border="1" id="planTable">
		<thead>
			<tr>
				<th>S.No</th>
				<th>Plan Name</th>
				<th>Plan Description</th>
				<th>Plan Start Date</th>
				<th>Plan End Date</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${plans}" var="plan" varStatus="index">
				<tr>
					<td><c:out value="${index.count}" /></td>
					<td><c:out value="${plan.planName}" /></td>
					<td><c:out value="${plan.planDescription}" /></td>
					<td><c:out value="${plan.planStartDate}" /></td>
					<td><c:out value="${plan.planEndDate}"/> </td>
					<td align="center"><a href="editPlan?planId=${plan.planId}"><i class="fa fa-pencil" aria-hidden="true"  id="edit">|</i></a> 
						<c:if
							test="${plan.activeSw =='Y'}">
							<a href="deletePlan?planId=${plan.planId}"
								onclick="return confirmDelete()"><i class="fa fa-trash" aria-hidden="true" id="delete"></i></a>
						</c:if>
						<c:if test="${plan.activeSw =='N'}">
							<a href="activatePlan?planId=${plan.planId}"
								onclick="return confirmActivate()"><i class="fa fa-exchange" aria-hidden="true" id="active"></i></a>
						</c:if></td>
						
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>