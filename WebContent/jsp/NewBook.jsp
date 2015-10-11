<%@include file="include/header1.jsp" %>
 
 <title>Create New Book</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
    <h2>Create New Book </h2>

	<form method="post" action="<%=context %>/ctr">
	<table>
		<tr>
			<th>Book ID (ISBN)</th>
			<td><input name="bookId" type="text" maxlength="10"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Book Title</th>
			<td><input name="bookTitle" type="text" maxlength="300"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Authors</th>
			<td><input name="authors" type="text" maxlength="150"/> </td>
			<td>Enter multiple authors with ', '<i>(Eg: Sidney Sheldon, J.R.Tolkien)</i></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Submit"/> </td>
			<td><input type="hidden" name="action" value="CreateBook"></td>
		</tr>
	</table>
	</form>
