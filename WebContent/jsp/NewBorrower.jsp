<%@include file="include/header1.jsp" %>
<%@include file="include/headerLinks.jsp" %>

<script type="text/javascript" src="<%=context %>/javascript/Borrower.js"></script> 
 <title>Add New Borrower</title>
        
    <h2>Add a new borrower</h2>

	<%if(request.getAttribute("msg") != null){ %>
		<span><%=request.getAttribute("msg") %> </span>
	<% } %>
	
	<br><br>
	<form method="post" action="<%=context %>/ctr" onsubmit="return validate();">
	<table>
		<tr>
			<th>First Name</th>
			<td><input name="fName" id="fName" type="text" maxlength="30"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td><input name="lName" id="lName" type="text" maxlength="30"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input name="email" id="email" type="email" maxlength="50"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><textarea name="address" id="address" rows=4 cols=20 maxlength="100"></textarea> </td>
			<td></td>
		</tr>
		<tr>
			<th>Phone</th>
			<td><input name="phone" id="phone" type="text" maxlength="20"/> </td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Submit"/> </td>
			<td><input type="hidden" name="action" value="AddBorrower"></td>
		</tr>
	</table>
	</form>
