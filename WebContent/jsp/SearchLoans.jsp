<%@page import="java.util.ArrayList" %>
<%@page import="bean.*"%>

<%@include file="include/header1.jsp" %>

<style>
.inputTable{
	font-size: 16px;
	margin-left: 20px;
}

.dataTable{
	font-size: 16px;
	margin-left: 20px;
	text-align: center; 
	border: 1;
}

.title {
  text-align: left;
  width: 30em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.author {
  text-align: left;
  width: 15em;
  #white-space: nowrap;
}

</style>

<script src="<%=context %>/javascript/SearchLoans.js"></script>
	    
<title>Search Book Loans</title>	    
    
    <%@include file="include/headerLinks.jsp" %>
     
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Search Book Loans :</h2>	

<form method="post" action="<%=context %>/ctr" onsubmit="return validateInput();">
	<table class="inputTable" border=0>
		<tr>
			<td align="right">Book Id :</td>
			<td><input type="text" name="searchBookId" id="searchBookId" size="30" /></td>
		</tr>
		<tr>
			<td align="right">Card No :</td>
			<td><input type="text" name="searchCardNo" id="searchCardNo" size="30" maxLength="10" onkeypress='validate(event)'/></td>
		</tr>
		<tr>
			<td colspan="2" align="center"> <br>
				<input type="submit" name="search" id="search" value="Search" />
				<input type="reset" name="reset" value="Reset" />
				<input type="hidden" name="action" value="SearchLoans" />
			</td>
		</tr>
</table>
</form>
<%if(session.getAttribute("LoanSearchList")!=null)
	{
		if(session.getAttribute("searchLoan")!=null)
		{
%>
		<font style="margin-left: 20px;font-size: 16px;color: blue;">Search results for <b><%=(String)session.getAttribute("searchLoan") %></b> -></font>
<%		} 
%>
		<table class="dataTable" border=1>
		<tr>
		<th >Sr.no</th>
		<th >Loan Id</th>
		<th >Book ID</th>
		<th >Branch Id</th>
		<th >Card No</th>
		<th >Checkout Date</th>
		<th >Due Date</th>
		<th >Date In</th>
		<th >Action</th>
		</tr>
		
<%		ArrayList<BookLoansBean> arrSearchList = (ArrayList<BookLoansBean>) session.getAttribute("LoanSearchList");
		int size = arrSearchList.size();
		if(size > 0)
		{
			String currentPosition = request.getParameter("currentPosition");
			int start = 0;
			boolean checkIn = false;
			if(currentPosition != null)
			{
				start = Integer.parseInt(currentPosition);
			}
			int a = 0,b = 0, i = 0, j = 0;  // Declaring the i outside for loop to show current position
			for (i = start, j=0; (j<50 && i < size) ; i++, j++) 
			{
				BookLoansBean loanB = arrSearchList.get(i);
				String tempAuthors = "";
%>
				<tr>
					<td><%=i+1 %></td>
					<td><%=loanB.getLoanId() %></td>
					<td><%=loanB.getBookId() %></td>
					<td><%=loanB.getBranchId() %></td>
					<td><%=loanB.getCardNo() %></td>
					<td><%=loanB.getDateOut() %></td>
					<td><%=loanB.getDueDate() %></td>
					<% if(loanB.getDateIn() != null){
						checkIn = false;%> 
						<td><%=loanB.getDateIn() %></td>
					<% 	}
						else{ checkIn = true;
					%>
						<td>-</td>
					<%} %>
					
					<td><input type="button" value="Check-in" 
					onclick="window.location.href='<%=context%>/ctr?action=Check_in&control=View&LoanId=<%=loanB.getLoanId()%>'" <%if(checkIn==false){%> disabled<% } %> /></td>
				</tr>
<%			} 
%>
			<tr><td style="border:0px" colspan=9><br/></td></tr>
			<tr>
			<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=searchLoanListNext&currentPosition=<%=0 %>">First</a>
			</td>
			<td style="text-align: left;border:0px">
<% 
			if(start!=0)
			{
	%>						
				<a href="<%=context %>/ctr?action=searchLoanListPrevious&currentPosition=<%=start-10 %>">Previous</a></td>
	<% 
			}
	%>						
			<td style="text-align: center;border:0px" colspan="5">
	<% 		if(size-(size%50)==size){a = size - 50;}else {a=(size-(size%50));}
			if(size%50 == 0){b = size/50;}else{b = (size/50)+1;}
			for(int k=0; k <b;k++)
			{
	%>
				<a href="<%=context %>/ctr?action=searchLoanListPrevious&currentPosition=<%=50*(k) %>"><%if((start/50)==k){ %><font style="color: red;"><%=k+1%></font><%;}else{out.println(k+1);} %></a>&nbsp;&nbsp;
	<%		}
	%>
			</td>
			<td style="text-align: right;border:0px">
	<%		if(i<size)
			{ 
	%>
				<a href="<%=context %>/ctr?action=searchLoanListNext&currentPosition=<%=start+50 %>">Next</a>
	<%		} 
	%>
			</td>
			<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=searchLoanListNext&currentPosition=<%=a %>">Last</a></td>
			</tr>
			<tr>
			<td colspan="2"></td>
			<td style="text-align: center;border:0px" colspan="5">Total No.of Records : <%=size %> </td>
			<td colspan="2"></td>
			</tr>
			</table>
<%		}
	}
	else
	{
		if(session.getAttribute("searchLoan")!=null)
		{
%>
			<font style="margin-left: 50px;font-size: 16px;color: blue;">Search results for :<b><%=(String)session.getAttribute("searchLoan") %></b> :</font><br/><br/>
			<font style="margin-left: 50px;font-size: 17px;color: black;\"><b>No matching records found.</b></font>
<%		} 

		
	}
%>


		</div>
    
    </div>