<%@include file="include/header1.jsp" %>

<%@page import="bean.BorrowerBean" %>

    <title>View Borrower</title>
    
    <%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Borrower Details :</h2><br/>
    	
		<form name="viewBorrower" action="<%=context %>/ctr">
		<%if(request.getAttribute("borrowerB")!=null)
		{
			BorrowerBean borrowerB = (BorrowerBean) request.getAttribute("borrowerB");
		%>
			<table style="font-size: 16px; margin-left: 50px;" border=0>
			<tr>
			<th>Library Card No</th>
			<td><%=borrowerB.getCardNo() %></td>
			</tr>
			<tr>
			<th>First Name</th>
			<td><%=borrowerB.getfName() %></td>
			</tr>
			<tr>
			<th>Last Name</th>
			<td><%=borrowerB.getlName() %></td>
			</tr>
			<tr>
			<th>Email Id</th>
			<td><%=borrowerB.getEmailId() %></td>
			</tr>
			<tr>
			<th>Address</th>
			<td><%=borrowerB.getAddress() %></td>
			</tr>
			<tr>
			<th>City</th>
			<td><%=borrowerB.getCity() %></td>
			</tr>
			<tr>
			<th>State</th>
			<td><%=borrowerB.getState() %></td>
			</tr>
			<tr>
			<th>Phone</th>
			<td><%=borrowerB.getPhone() %></td>
			</tr>
			<tr>
			<td align="right"><br/>
			<input type="submit" name="control" value="Edit"/>
			<input type="hidden" name="action" value="Borrower" />
			<input type="hidden" name="cardNo" value="<%=borrowerB.getCardNo() %>" />
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
 