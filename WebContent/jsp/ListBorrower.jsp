<%@page import="java.util.ArrayList" %>
<%@page import="bean.BorrowerBean" %>
<%@include file="include/header1.jsp" %>  
<title>Borrower List</title>  

	<%@include file="include/headerLinks.jsp" %>
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Borrower List</h2><br/>
    	
    	<span style="font-size: 18px;color: red;text-align: center;" >
<%if(request.getAttribute("BorrowerRegSuccess")!= null)
	{
		out.println(request.getAttribute("BorrowerRegSuccess"));
	}
	else if(request.getAttribute("BorrowerUpdateSuccess")!=null)
	{
		out.println(request.getAttribute("BorrowerUpdateSuccess"));
	}
	else if(request.getAttribute("BorrowerDeleteSuccess")!=null)
	{
		out.println(request.getAttribute("BorrowerDeleteSuccess"));
	}%>
	</span><br/>
	
	<br/><br/>
<%if(session.getAttribute("BorrowerList")!=null)
{ %>	
	
	<%ArrayList<BorrowerBean> arrBorrowerList = (ArrayList<BorrowerBean>) session.getAttribute("BorrowerList");
	int size = arrBorrowerList.size();
	if(size > 0){
		String currentPosition = request.getParameter("currentPosition");
		int start = 0;
		if(currentPosition != null)
		{
			start = Integer.parseInt(currentPosition);
		}
		int a = 0,b = 0, i = 0, j = 0;  // Declaring the i outside for loop to show current position
		%>
		<table align="center" style="font-size: 16px;text-align: center;" border=1>
		<tr>
		<th width=50px>Sr.No</th>
		<th width=200px>Library Card No</th>
		<th width=200px>First Name</th>
		<th width=200px>Last Name</th>
		<th width=250px>Email</th>
		<th width=250px colspan=2>Action</th>
		</tr>
		<% 
		for (i = start, j=0; (j<50 && i < size) ; i++, j++) 
		{
			BorrowerBean borrowerB = arrBorrowerList.get(i);
		%>
	<tr>
	<td><%=i+1 %></td>
	<td><a href="<%=context %>/ctr?action=Borrower&control=View&cardNo=<%=borrowerB.getCardNo() %>"><%=borrowerB.getCardNo() %></a></td>
	<td><%=borrowerB.getfName() %></td>
	<td><%=borrowerB.getlName() %></td>
	<td><%=borrowerB.getEmailId() %></td>
	<td colspan=2><a href="<%=context %>/ctr?action=Borrower&control=Edit&cardNo=<%=borrowerB.getCardNo() %>">Edit</a> || 
	<a href="<%=context %>/ctr?action=Borrower&control=Delete&cardNo=<%=borrowerB.getCardNo() %>"
			onclick="return confirm('Are you sure you want to delete?');">Delete</a></td>
	</tr>
	<%} %>
	<tr><td style="border:0px" colspan=7><br/></td></tr>
	<tr>
		<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=borrowerListNext&currentPosition=<%=0 %>">First</a>
		</td>
		<td style="text-align: left; border:0px">
<% 
		if(start!=0)
		{
%>						
			<a href="<%=context %>/ctr?action=borrowerListPrevious&currentPosition=<%=start-50 %>">Previous</a></td>
<% 
		}
%>						
		<td style="text-align: center; border:0px" colspan="3">
<% 		if(size-(size%50)==size){a = size - 50;}else {a=(size-(size%50));}
		if(size%50 == 0){b = size/50;}else{b = (size/50)+1;}
		for(int k=0; k < b;k++)
		{
%>
			<a href="<%=context %>/ctr?action=borrowerListPrevious&currentPosition=<%=50*(k) %>"><%if((start/50)==k){ %><font style="color: red;"><%=k+1%></font><%;}else{out.println(k+1);} %></a>&nbsp;&nbsp;
<%		}
%>
		</td>
		<td style="text-align: right; border:0px">
<%		if(i<size)
		{ 
%>
			<a href="<%=context %>/ctr?action=borrowerListNext&currentPosition=<%=start+50 %>">Next</a>
<%		} 
%>
		</td>
		<td style="text-align: center; border:0px" width=50px><a href="<%=context %>/ctr?action=borrowerListNext&currentPosition=<%=a %>">Last</a></td>
		</tr>
		<tr>
		<td style="text-align: center;border:0px" colspan="7">Total No.of Records : <%=size %> </td>
		</tr>
	</table>
<%} 
	else
	{
		out.println("No records found in database.");	
	}
}
%>


		</div>
    
    </div>
