<%@include file="include/header1.jsp" %>
<%@page import="bean.BookBean"%> 
 <%@include file="include/headerLinks.jsp" %>
 
 <script type="text/javascript" src="<%=context %>/javascript/Book.js"></script> 
 
 <title>Edit Book</title>
        
  	<h2>Edit Book : </h2>

	<form method="post" action="<%=context %>/ctr" onsubmit="return validate();">
	<% BookBean bookB = null; String type = "";
	if (request.getAttribute("controlType") != null){
		type = (String) request.getAttribute("controlType");
		bookB = (BookBean) request.getAttribute("bookB");
	} %>
	<table style="margin-left:50px;">
		<tr>
			<th>Book ID (ISBN)</th>
			<td><input name="bookId" id="bookId" type="text" maxlength="10" value="<%=bookB.getBookId() %>" <%if(type.equals("View")){%> readonly<% } %> disabled/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Book Title</th>
			<td><textarea name="bookTitle" id="bookTitle" rows=4 cols=50 maxlength="300" <%if(type.equals("View")){%> readonly<% } %>><%=bookB.getBookTitle() %></textarea> </td>
			<td></td>
		</tr>
		<tr>
			<th>Authors</th>
			<td><input name="authors" id="authors" type="text" maxlength="150" value="<%=bookB.getAuthorBean().toString() %>" <%if(type.equals("View")){%> readonly<% } %> /> </td>
			<td></td>
		</tr>
		<tr>
			<td align="center" colspan="2"><br/>
				<input type="submit" name="control" value="Update"/>
				<input type="hidden" name="action" value="Book" />
			
				<input type="hidden" name="bookId" value="<%=bookB.getBookId() %>"></input>
			
				<input type="reset" name="reset" value="Reset" />
				<input type="submit" name="control" value="Delete" onclick="return confirm('Are you sure you want to delete?\nThis will cascade delete all entries in library schema');"/>
			</td>
		</tr>
	</table>
	</form>
