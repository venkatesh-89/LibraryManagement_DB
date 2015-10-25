<%@include file="include/header1.jsp" %>
<%@page import="bean.*" %>

<style>
th{
	text-align:right;
}

td.sep{
	width:15px;
}
</style>

 <title>Success</title>

  	<%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920"> 
    	<%if (request.getAttribute("bookB") != null && 
    			request.getAttribute("branchB") != null &&
    			request.getAttribute("borrowB") != null &&
    			request.getAttribute("loanB") != null){
    			
    			BookBean bookB = (BookBean) request.getAttribute("bookB") ;
    			LibraryBranchBean branchB = (LibraryBranchBean) request.getAttribute("branchB") ;
    			BorrowerBean borrowB = (BorrowerBean) request.getAttribute("borrowB") ;
    			BookLoansBean loanB = (BookLoansBean) request.getAttribute("loanB") ;
    	%>
    	
    	
    	<h2>Checkout Receipt : </h2>
    	
    	<table>
    		<tr>
    			<th>Book Id</th>
    			<td class="sep">:</td>
    			<td><%=bookB.getBookId() %></td>
    		</tr>
    		<tr>
    			<th>Book Title</th>
    			<td class="sep">:</td>
    			<td><%=bookB.getBookTitle() %></td>
    		</tr>
    		<tr>
    			<th>Book Author(s)</th>
    			<td class="sep">:</td>
    			<td><%=bookB.getAuthorBean().toString() %></td>
    		</tr>
    		<tr>
    			<th>Branch Name</th>
    			<td class="sep">:</td>
    			<td><%=branchB.getBranchName() %></td>
    		</tr>
    		<tr>
    			<th>Branch Address</th>
    			<td class="sep">:</td>
    			<td><%=branchB.getAddress() %></td>
    		</tr>
    		<tr>
    			<th>Borrower Name / Card No</th>
    			<td class="sep">:</td>
    			<td><%=borrowB.getfName() + " " + borrowB.getlName() + " (" + borrowB.getCardNo() + ")"%></td>
    		</tr>
    		<tr>
    			<th>Borrower Address</th>
    			<td class="sep">:</td>
    			<td><%=borrowB.getAddress() %></td>
    		</tr>
    		<tr>
    			<th>Transaction Number (Loan Id)</th>
    			<td class="sep">:</td>
    			<td><%=loanB.getLoanId() %></td>
    		</tr>
    		<tr>
    			<th>Checkout Date</th>
    			<td class="sep">:</td>
    			<td><%=loanB.getDateOut() %></td>
    		</tr>
    		<tr>
    			<th>Due Date</th>
    			<td class="sep">:</td>
    			<td><%=loanB.getDueDate() %></td>
    		</tr>
    	</table>
    	
    	<br><br>
    	
    	<p align="center" style="font-size:20px"><i>Thank You</i></p>
    	<%	
    	}
    	%>
    	
    	</div>
        
    </div>
