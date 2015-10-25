<%@include file="include/header1.jsp" %>
<%@page import="java.util.ArrayList" %>
<%@page import="bean.BookBean" %>
<%@page import="bean.BookCopiesBean" %>

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
			<!-- tr>
			<td colspan="2"><br/><span style="margin-left: 90px;"><input type="button" name="cancel" value="Back" onclick="window.location.href='<%=context %>/jsp/Home.jsp'" /></span></td>
			</tr-->
			</table>
			<%	
			if (request.getAttribute("arrListBookCopiesB") != null) {
				ArrayList<BookCopiesBean> arrListBookCopiesB = (ArrayList<BookCopiesBean>) request.getAttribute("arrListBookCopiesB");
			%>
			<br> <br>
			<table style="font-size: 16px; margin-left: 50px;" border=1>
			<tr>
				<th colspan="5" style="text-align:center;">List of places to checkout</th>
			</tr>
			<tr>
				<th>Branch Id</th>
				<th>Branch Name</th>
				<th>Total Copies</th>
				<th>Copies Available</th>
				<th>Action</th>
			</tr>
			<% for (BookCopiesBean bookCopyB : arrListBookCopiesB) {
			%> 
				<tr>
					<td><%=bookCopyB.getBranchId() %></td>
					<td><%=bookCopyB.getBranchName() %></td>
					<td><%=bookCopyB.getNoOfCopies() %></td>
					<td><%=bookCopyB.getCopiesAvailable() %></td>
					<td><input type="button" name="checkout" value="Checkout" 
							onclick="window.location.href='<%=context %>/jsp/Checkout.jsp?bookId=<%=bookB.getBookId() %>&branchId=<%=bookCopyB.getBranchId() %>'"
							<%if (bookCopyB.getCopiesAvailable() <= 0) {%>disabled<%}%>/>
					</td>
				</tr>	
			<%
				}
			%>
			</table>
			<%
		}

	}
%>
</form>
		</div>
    
    </div>
 