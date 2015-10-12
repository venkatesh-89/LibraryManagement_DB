<%@include file="include/header1.jsp" %>

<%@page import="bean.BookBean" %>

    <title>View Book Details</title>
    
    <%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Book Details :</h2><br/>
    	
<form name="viewBook" action="<%=context %>/ctr">
<%if(request.getAttribute("bookB")!=null)
	{
		BookBean bookB = (BookBean) request.getAttribute("bookB");
		%>
			<table style="font-size: 16px; margin-left: 50px;" border=0>
			<tr>
			<th>Book Id (ISBN)</th>
			<td><%=bookB.getBookId() %></td>
			</tr>
			<tr>
			<th>Book Title</th>
			<td><%=bookB.getBookTitle() %></td>
			</tr>
			<tr>
			<th>Authors</th>
			<td><%=bookB.getAuthorBean().toString() %></td>
			</tr>
			
			<tr>
			<td align="right"><br/>
			<input type="submit" name="control" value="Edit"/>
			<input type="hidden" name="action" value="Book" />
			<input type="hidden" name="bookId" value="<%=bookB.getBookId() %>" />
			</td><td><br/>
			<input type="submit" name="control" value="Delete" onclick="return confirm('Are you sure you want to delete?');"/></td>
			</tr>
			<tr>
			<td colspan="2"><br/><span style="margin-left: 90px;"><input type="button" name="cancel" value="Back" onclick="window.location.href='<%=context %>/jsp/Home.jsp'" /></span></td>
			</tr>
			</table>
<%		
	}
%>
</form>


		</div>
    
    </div>
 