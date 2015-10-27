<%@include file="include/header1.jsp" %>
<%@page import="bean.LibraryBranchBean"%> 
<%@include file="include/headerLinks.jsp" %>

<script type="text/javascript" src="<%=context %>/javascript/Branch.js"></script> 
 
 <title>Edit Branch</title>
        
  	<h2>Edit Branch : </h2>

	<form method="post" action="<%=context %>/ctr" onsubmit="return validate();">
	<% LibraryBranchBean branchB = null; String type = "";
	if (request.getAttribute("controlType") != null){
		type = (String) request.getAttribute("controlType");
		branchB = (LibraryBranchBean) request.getAttribute("branchB");
	} %>
	<table style="margin-left:50px;">
		<tr>
			<th>Library Branch Id</th>
			<td><input name="branchId" id="branchId" type="text" maxlength="10" value="<%=branchB.getBranchID() %>" <%if(type.equals("View")){%> readonly<% } %> disabled/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Branch Name</th>
			<td><input name="branchName" id="branchName" type="text" maxlength="150" value="<%=branchB.getBranchName() %>" <%if(type.equals("View")){%> readonly<% } %> /> </td>
			<td></td>
		</tr>
		<tr>
			<th>Branch Address</th>
			<td><textarea name="address" id="address" rows=4 cols=50 maxlength="300" <%if(type.equals("View")){%> readonly<% } %>><%=branchB.getAddress() %></textarea> </td>
			<td></td>
		</tr>
		<tr>
			<td align="center" colspan="2"><br/>
				<input type="submit" name="control" value="Update"/>
				<input type="hidden" name="action" value="Branch" />
			
				<input type="hidden" name="branchId" value="<%=branchB.getBranchID() %>"></input>
			
				<input type="reset" name="reset" value="Reset" />
				<input type="submit" name="control" value="Delete" onclick="return confirm('Are you sure you want to delete?');"/>
			</td>
		</tr>
	</table>
	</form>
