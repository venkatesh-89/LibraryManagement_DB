<%@page import="bean.LibraryBranchBean"%>
<%@include file="include/header1.jsp" %>

<%@page import="bean.BorrowerBean" %>

    <title>View Branch</title>
    
    <%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Branch Details :</h2><br/>
    	
		<form name="viewBranch" action="<%=context %>/ctr">
		<%if(request.getAttribute("branchB")!=null)
		{
			LibraryBranchBean branchB = (LibraryBranchBean) request.getAttribute("branchB");
		%>
			<table style="font-size: 16px; margin-left: 50px;" border=0>
			<tr>
			<th>Library Branch Id</th>
			<td><%=branchB.getBranchID() %></td>
			</tr>
			<tr>
			<th>Branch Name</th>
			<td><%=branchB.getBranchName() %></td>
			</tr>
			<tr>
			<th>Branch Address</th>
			<td><%=branchB.getAddress() %></td>
			</tr>
			
			<tr>
			<td align="right"><br/>
			<input type="submit" name="control" value="Edit"/>
			<input type="hidden" name="action" value="Branch" />
			<input type="hidden" name="branchId" value="<%=branchB.getBranchID() %>" />
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
 