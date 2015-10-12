<%@include file="include/header1.jsp" %>
<%@page import="bean.BorrowerBean"%> 
 
 <title>Edit Borrower</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
    <h2>Edit Borrower : </h2>

	<form method="post" action="<%=context %>/ctr">
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
			<td><input name="fName" type="text" maxlength="30" value="<%=borrowerB.getfName() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td><input name="lName" type="text" maxlength="30" value="<%=borrowerB.getlName() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input name="email" type="email" maxlength="50" value="<%=borrowerB.getEmailId() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><input name="address" type="text" maxlength="100" value="<%=borrowerB.getAddress() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>City</th>
			<td><input name="city" type="text" maxlength="50" value="<%=borrowerB.getCity() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>State</th>
			<td><input name="state" type="text" maxlength="50" value="<%=borrowerB.getState() %>"/> </td>
			<td></td>
		</tr>
		<tr>
			<th>Phone</th>
			<td><input name="phone" type="text" maxlength="20" value="<%=borrowerB.getPhone() %>"/> </td>
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
