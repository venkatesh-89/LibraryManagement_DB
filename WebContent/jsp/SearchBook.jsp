<%@page import="java.util.ArrayList" %>
<%@page import="bean.BookBean"%>

<%@include file="include/header1.jsp" %>
	    
<title>Search Book</title>	    
    
    <%@include file="include/headerLinks.jsp" %>
     
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	<!--
    	<p style="font-size: 16px;color: black;" align="right">Welcome <b><%=session.getAttribute("userName")%>,</b> <a style="color: yellow;" href="<%=context%>/ctr?action=logout">(Logout)</a></p>
    	 -->
    	<h2>Search Book</h2>	

<form method="post" action="<%=context %>/ctr">
<table style="font-size: 16px;margin-left: 50px">
<tr>
<td><input type="text" name="searchBook" size="30" value="Enter Book ID/ Title/ Author" onfocus="if(value == 'Enter Book ID/ Title/ Author'){value = '';}" /></td>
<td><select name="searchCriteria">
  <option value="" default>Select Search Criteria</option>
  <option value="bookId">Book ID</option>
  <option value="bookTitle">Book Title</option>
  <option value="authorName">Author Name</option>
  <option value="anyCriteria">Any Criteria</option>
</select></td>
<td><input type="submit" name="search" value="Search">
<input type="hidden" name="action" value="SearchBook"></td>
</tr>
</table>
</form>
<%if(session.getAttribute("BookSearchList")!=null)
	{
		if(session.getAttribute("search")!=null)
		{
%>
		<font style="margin-left: 50px;font-size: 16px;color: blue;">Search results for <b><i><%=(String)session.getAttribute("search") %></i></b> :</font>
<%		} 
%>
		<table style="font-size: 16px;margin-left: 50px;text-align: center;"; border=1px solid black; border-collapse: collapse" >
		<tr>
		<th width=50px>Sr.no</th>
		<!--th>Action</th>-->
		<th width=100px>Book ID</th>
		<th width=500px>Title</th>
		<th width=200px>Author(s)</th>
		<th width=100px></th>
		<th width=50px></th>
		</tr>
		
<%		ArrayList<BookBean> arrBookSearchList = (ArrayList<BookBean>) session.getAttribute("BookSearchList");
		int size = arrBookSearchList.size();
		if(size > 0)
		{
			String currentPosition = request.getParameter("currentPosition");
			int start = 0;
			if(currentPosition != null)
			{
				start = Integer.parseInt(currentPosition);
			}
			int a = 0,b = 0, i = 0, j = 0;  // Declaring the i outside for loop to show current position
			for (i = start, j=0; (j<50 && i < size) ; i++, j++) 
			{
				BookBean bookB = arrBookSearchList.get(i);
				String tempAuthors = "";
%>
				<tr>
					<td><%=i+1 %></td>
					<!-- 
					<td><a href="<%=context %>/ctr?action=Employee&control=View&empId=<%--=empB.getEmployeeId() --%>">View</a> || 
						<a href="<%=context %>/ctr?action=Employee&control=Edit&empId=<%--=empB.getEmployeeId() --%>">Edit</a> || 
						<a href="<%=context %>/ctr?action=Employee&control=Delete&empId=<%--=empB.getEmployeeId() --%>"
							onclick="return confirm('Are you sure you want to delete?');">Delete</a></td>
					 -->
					<td><%=bookB.getBookId() %></td>
					<td><%=bookB.getBookTitle() %></td>
					<%for (String author : bookB.getAuthorBean().getAuthorList()){
						if (tempAuthors == "") { tempAuthors = author; }
						else { tempAuthors += ", " + author; } 
					}
					%>
					<td><%=tempAuthors %></td>
				</tr>
<%			} 
%>
			<tr><td colspan=6><br/></td></tr>
			<tr>
			<td style="text-align: center;"><a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=0 %>">First</a>
			</td>
			<td style="text-align: left">
<% 
			if(start!=0)
			{
	%>						
				<a href="<%=context %>/ctr?action=searchListPrevious&currentPosition=<%=start-10 %>">Previous</a></td>
	<% 
			}
	%>						
			<td style="text-align: center" colspan="2">
	<% 		if(size-(size%50)==size){a = size - 50;}else {a=(size-(size%50));}
			if(size%50 == 0){b = size/50;}else{b = (size/50)+1;}
			for(int k=0; k <b;k++)
			{
	%>
				<a href="<%=context %>/ctr?action=searchListPrevious&currentPosition=<%=50*(k) %>"><%if((start/50)==k){ %><font style="color: red;"><%=k+1%></font><%;}else{out.println(k+1);} %></a>&nbsp;&nbsp;
	<%		}
	%>
			</td>
			<td style="text-align: right">
	<%		if(i<size)
			{ 
	%>
				<a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=start+50 %>">Next</a>
	<%		} 
	%>
			</td>
			<td style="text-align: center;"><a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=a %>">Last</a></td>
			</tr>
			<tr>
			<td style="text-align: center;" colspan="7">Total No.of Records : <%=size %> </td>
			</tr>
			</table>
<%		}
	}
	else
	{
		if(session.getAttribute("search")!=null)
		{
%>
			<font style="margin-left: 50px;font-size: 16px;color: blue;">Search results for <b><i><%=(String)session.getAttribute("search") %></i></b> :</font><br/><br/>
			<font style="margin-left: 50px;font-size: 17px;color: black;\"><b>No matching records found.</b></font>
<%		} 

		
	}
%>

<!-- 
		</div>
    
    </div>
    -->