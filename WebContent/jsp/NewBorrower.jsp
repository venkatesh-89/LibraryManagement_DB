<%@include file="include/header1.jsp" %>
 
 <title>Add New Borrower</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
    <h2>Add a new borrower</h2>

	<form method="post" action="<%=context %>/ctr">
	<table>
		<tr>
			<th>First Name</th>
			<td><input name="fName" type="text" maxlength="30"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td><input name="lName" type="text" maxlength="30"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input name="email" type="email" maxlength="50"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><input name="address" type="text" maxlength="100"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>City</th>
			<td><input name="city" type="text" maxlength="50"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>State</th>
			<td><input name="state" type="text" maxlength="50"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Phone</th>
			<td><input name="phone" type="text" maxlength="20"/> </td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Submit"/> </td>
			<td><input type="hidden" name="action" value="AddBorrower"></td>
		</tr>
	</table>
	</form>
