<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Accounts</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css1/viewAccount.css">
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
#delete {
    color: red;
    font-size: 20px;
}
#active {
    color: green;
    font-size: 20px;
}

</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#accTbl').DataTable({
			"pagingType" : "full_numbers"
		});
	});

	function confirmDelete() {
		return confirm("Are you sure, you want to delete ?");
	}

	function confirmActivate() {
		return confirm("Are you sure, you want to Activate ?");
	}
</script>

</head>

<%@ include file="header-inner.jsp" %>
<body background="images/ies2.jpg">
	<h3 style="color: green; text-align: center">${success}</h3>
	<h3 style="color: red; text-align: center">${failure}</h3>
	<div class="viewAccounts">
	<h2>VIEW ACCOUNTS</h2>
	<table border="1" id="accTbl">
		<thead>
			<tr>
				<th>S.No</th>
				<th>FIRST NAME</th>
				<th>LAST NAME</th>
				<th>EMAIL</th>
				<th>ROLE</th>
				<th>ACTION</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${accounts}" var="acc" varStatus="index">
				<tr>
					<td><c:out value="${index.count}" /></td>
					<td><c:out value="${acc.firstName}" /></td>
					<td><c:out value="${acc.lastName}" /></td>
					<td><c:out value="${acc.email}" /></td>
					<td><c:out value="${acc.role}" /></td>

					<td align="center"><a href="editAcc?accId=${acc.accId}"><i class="fa fa-pencil" aria-hidden="true"  id="edit">|</i></a> 
					<c:if test="${acc.activeSw =='Y' }">
							<a href="delete?accId=${acc.accId}"
								onclick="return confirmDelete()"><i class="fa fa-trash" aria-hidden="true" id="delete"></i></a>
						</c:if> <c:if test="${acc.activeSw =='N' }">
							<a href="activate?accId=${acc.accId}"
								onclick="return confirmActivate()"><i class="fa fa-exchange" aria-hidden="true" id="active"></i></a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>