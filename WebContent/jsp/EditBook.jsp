<%@include file="include/header1.jsp" %>
<%@page import="bean.BookBean"%> 
 
 <title>Edit Book</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
    <h2>Edit Book : </h2>

	<form method="post" action="<%=context %>/ctr">
	<% BookBean bookB = null; String type = "";
	if (request.getAttribute("controlType") != null){
		type = (String) request.getAttribute("controlType");
		bookB = (BookBean) request.getAttribute("bookB");
	} %>
	<table style="margin-left:50px;">
		<tr>
			<th>Book ID (ISBN)</th>
			<td><input name="bookId" type="text" maxlength="10" value="<%=bookB.getBookId() %>" <%if(type.equals("View")){%> readonly<% } %> disabled/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Book Title</th>
			<td><textarea name="bookTitle" rows=4 cols=50 maxlength="300" <%if(type.equals("View")){%> readonly<% } %>><%=bookB.getBookTitle() %></textarea> </td>
			<td></td>
		</tr>
		<tr>
			<th>Authors</th>
			<td><input name="authors" type="text" maxlength="150" value="<%=bookB.getAuthorBean().toString() %>" <%if(type.equals("View")){%> readonly<% } %> /> </td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="<%if(type.equals("View")){%>Edit<%}else{ %>Update <%}%>"/> 
				<input type="reset" value="Reset"/>
				<input type="button" value="Delete"/>
			</td>
			<td><input type="hidden" name="action" value="UpdateBook"></td>
		</tr>
	</table>
	</form>
