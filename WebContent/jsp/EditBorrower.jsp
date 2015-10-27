<%@include file="include/header1.jsp" %>
<%@page import="bean.BorrowerBean"%> 
<%@include file="include/headerLinks.jsp" %>

<script type="text/javascript" src="<%=context %>/javascript/Borrower.js"></script>  
 <title>Edit Borrower</title>
      
    <h2>Edit Borrower : </h2>

	<form method="post" action="<%=context %>/ctr" onsubmit="return validate();">
	<% BorrowerBean borrowerB = null; String type = "";
	if (request.getAttribute("controlType") != null){
		type = (String) request.getAttribute("controlType");
		borrowerB = (BorrowerBean) request.getAttribute("borrowerB");
	} %>
	<table style="margin-left:50px;">
		<tr>
			<th>Library Card No.</th>
			<td><input name="cardNo" type="text" maxlength="10" value="<%=borrowerB.getCardNo() %>" <%if(type.equals("View")){%> readonly<% } %> disabled/> </td>
			<td></td>
		</tr>
		<tr>
			<th>First Name</th>
			<td><input name="fName" id="fName" type="text" maxlength="30" value="<%=borrowerB.getfName() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td><input name="lName" id="lName" type="text" maxlength="30" value="<%=borrowerB.getlName() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input name="email" id="email" type="email" maxlength="50" value="<%=borrowerB.getEmailId() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><textarea name="address" id="address" maxlength="100" rows=4 cols=20><%=borrowerB.getAddress() %> </textarea> </td>
			<td></td>
		</tr>
		<tr>
			<th>Phone</th>
			<td><input name="phone" id="phone" type="text" maxlength="20" value="<%=borrowerB.getPhone() %>"/> </td>
			<td></td>
		</tr>
		
		<tr>
			<td align="center" colspan="2"><br/>
				<input type="submit" name="control" value="Update"/>
				<input type="hidden" name="action" value="Borrower" />
			
				<input type="hidden" name="cardNo" value="<%=borrowerB.getCardNo() %>"></input>
			
				<input type="reset" name="reset" value="Reset" />
				<input type="submit" name="control" value="Delete" onclick="return confirm('Are you sure you want to delete?');"/>
			</td>
		</tr>
	</table>
	</form>
