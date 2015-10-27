<%@page import="java.util.ArrayList" %>
<%@page import="bean.LibraryBranchBean" %>
<%@include file="include/header1.jsp" %>  
<title>Library Branch List</title>  

	<%@include file="include/headerLinks.jsp" %>
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Library Branch List</h2><br/>
    	
    	<span style="font-size: 18px;color: red;text-align: center;" >
<%if(request.getAttribute("BranchRegSuccess")!= null)
	{
		out.println(request.getAttribute("BranchRegSuccess"));
	}
	else if(request.getAttribute("BranchUpdateSuccess")!=null)
	{
		out.println(request.getAttribute("BranchUpdateSuccess"));
	}
	else if(request.getAttribute("BranchDeleteSuccess")!=null)
	{
		out.println(request.getAttribute("BranchDeleteSuccess"));
	}%>
	</span><br/>
	
	<br/><br/>
<%if(session.getAttribute("BranchList")!=null)
{ %>	
	
	<%ArrayList<LibraryBranchBean> arrBranchList = (ArrayList<LibraryBranchBean>) session.getAttribute("BranchList");
	int size = arrBranchList.size();
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
		<th width=200px>Library Branch Id</th>
		<th width=200px>Branch Name</th>
		<th width=400px>Address</th>
		<th width=250px colspan=3>Action</th>
		</tr>
		<% 
		for (i = start, j=0; (j<50 && i < size) ; i++, j++) 
		{
			LibraryBranchBean branchB = arrBranchList.get(i);
		%>
	<tr>
	<td><%=i+1 %></td>
	<td><a href="<%=context %>/ctr?action=Branch&control=View&branchId=<%=branchB.getBranchID() %>"><%=branchB.getBranchID() %></a></td>
	<td><%=branchB.getBranchName() %></td>
	<td><%=branchB.getAddress() %></td>
	<td colspan=2><a href="<%=context %>/ctr?action=Branch&control=Edit&branchId=<%=branchB.getBranchID() %>">Edit</a> || 
	<a href="<%=context %>/ctr?action=Branch&control=Delete&branchId=<%=branchB.getBranchID() %>"
			onclick="return confirm('Are you sure you want to delete?');">Delete</a></td>
	</tr>
	<%} %>
	<tr><td style="border:0px" colspan=7><br/></td></tr>
	<tr>
		<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=branchListNext&currentPosition=<%=0 %>">First</a>
		</td>
		<td style="text-align: left; border:0px">
<% 
		if(start!=0)
		{
%>						
			<a href="<%=context %>/ctr?action=branchListPrevious&currentPosition=<%=start-50 %>">Previous</a></td>
<% 
		}
%>						
		<td style="text-align: center; border:0px" colspan="2">
<% 		if(size-(size%50)==size){a = size - 50;}else {a=(size-(size%50));}
		if(size%50 == 0){b = size/50;}else{b = (size/50)+1;}
		for(int k=0; k < b;k++)
		{
%>
			<a href="<%=context %>/ctr?action=branchListPrevious&currentPosition=<%=50*(k) %>"><%if((start/50)==k){ %><font style="color: red;"><%=k+1%></font><%;}else{out.println(k+1);} %></a>&nbsp;&nbsp;
<%		}
%>
		</td>
		<td style="text-align: right; border:0px">
<%		if(i<size)
		{ 
%>
			<a href="<%=context %>/ctr?action=branchListNext&currentPosition=<%=start+50 %>">Next</a>
<%		} 
%>
		</td>
		<td style="text-align: center; border:0px" width=50px><a href="<%=context %>/ctr?action=branchListNext&currentPosition=<%=a %>">Last</a></td>
		</tr>
		<tr>
		<td style="text-align: center;border:0px" colspan="6">Total No.of Records : <%=size %> </td>
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
