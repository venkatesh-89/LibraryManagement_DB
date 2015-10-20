<%@include file="include/header1.jsp" %>
 
 <title>Create New Branch</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
    <h2>Create New Branch </h2>

	<%if(request.getAttribute("msg") != null){ %>
		<span><%=request.getAttribute("msg") %> </span>
	<% } %>
	<br><br>
	
	<form method="post" action="<%=context %>/ctr">
	<table>
		<tr>
			<th>Branch Name</th>
			<td><input name="branchName" type="text" maxlength="300"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Branch Address</th>
			<td><textarea name="address" rows=4 cols=50 maxlength="300"></textarea> </td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Submit"/> </td>
			<td><input type="hidden" name="action" value="AddBranch"></td>
		</tr>
	</table>
	</form>
