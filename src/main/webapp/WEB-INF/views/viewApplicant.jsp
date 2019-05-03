<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Applicants</title>
<link rel="stylesheet" type="text/css" href="css1/viewApplicant.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	
<style type="text/css">
	#edit {
    color: blue;
    font-size: 20px;
}

</style>

<script type="text/javascript">
$(document).ready(function() {
	$('#viewApplicantTbl').DataTable({
		"pagingType" : "full_numbers"
	});
});
</script>

</head>

<%@ include file="header-inner.jsp" %>
<body>
	<h3 style="color: green; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="viewApplicants">
	<h2>EDIT ACCOUNTS</h2>

	<table border="1" id="viewApplicantTbl">
		<thead>
			<tr>
				<th>S.No</th>
				<th>Application No</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Date of Birth</th>
				<th>SSN</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${appModel}" var="app" varStatus="index">
				<tr>
					<td><c:out value="${index.count}" /></td>
					<td><c:out value="${app.appId}" /></td>
					<td><c:out value="${app.firstName}" /></td>
					<td><c:out value="${app.lastName}" /></td>
					<td><c:out value="${app.dob}" /></td>
					<td><c:out value="${app.ssn}" /></td>

					<td align="center"><a href="editApp?appId=${app.appId}"><i class="fa fa-pencil" aria-hidden="true"  id="edit"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>