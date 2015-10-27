<%@include file="include/header1.jsp" %>
<%@include file="include/headerLinks.jsp" %>

<script type="text/javascript" src="<%=context %>/javascript/Book.js"></script> 
 <title>Create New Book</title>
    
    <h2>Create New Book </h2>

	<form method="post" action="<%=context %>/ctr" onsubmit="return validate();">
	<table>
		<tr>
			<th>Book ID (ISBN)</th>
			<td><input name="bookId" id="bookId" type="text" maxlength="10"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Book Title</th>
			<td><input name="bookTitle" id="bookTitle" type="text" maxlength="300"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Authors</th>
			<td><input name="authors" id="authors" type="text" maxlength="150"/> </td>
			<td>Enter multiple authors with ', '<i>(Eg: Sidney Sheldon, J.R.Tolkien)</i></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Submit"/> </td>
			<td><input type="hidden" name="action" value="CreateBook"></td>
		</tr>
	</table>
	</form>
