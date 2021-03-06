<%@page import="java.util.ArrayList" %>
<%@page import="bean.BookBean"%>

<%@include file="include/header1.jsp" %>
<%@include file="include/headerLinks.jsp" %>
 
 
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
<script type="text/javascript" src="<%=context %>/javascript/Book.js"></script> 	    
<title>Search Book</title>	    
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Search Book</h2>	

<form method="post" action="<%=context %>/ctr" onsubmit="return searchBookValidate();">
<table class="inputTable">
<tr>
<td><input type="text" name="searchBook" id="searchBook" size="30" placeholder="Enter Book ID/ Title/ Author" /></td>
<td><select name="searchCriteria">
  <option value="bookId" selected>Book ID</option>
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
		if(session.getAttribute("search")!=null && session.getAttribute("searchCriteria") !=null )
		{
%>
		<font style="margin-left: 20px;font-size: 16px;color: blue;">Search results for <b><i><%=(String)session.getAttribute("search") %></i></b> :</font><br>
		<font style="margin-left: 20px;font-size: 16px;color: black;">Search Criteria : <b><%=(String)session.getAttribute("searchCriteria") %></b></font>
<%		} 
%>
		<table class="dataTable" border=1>
		<tr>
		<th width=50px>Sr.no</th>
		<th width=100px>Book ID</th>
		<th width=200px>Title</th>
		<th width=200px>Author(s)</th>
		<th width=100px>Action</th>
		<th width=50px>Checkout</th>
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
					
					<td><a href="<%=context %>/ctr?action=Book&control=View&bookId=<%=bookB.getBookId() %>"><%=bookB.getBookId() %></a></td>
					<td><div class="title"><%=bookB.getBookTitle() %></div></td>
					<%--for (String author : bookB.getAuthorBean().getAuthorList()){
						if (tempAuthors == "") { tempAuthors = author; }
						else { tempAuthors += ", " + author; } 
					}
					--%>
					<td><div class="author"><%=bookB.getAuthorBean().toString() %></div></td>
					<td><a href="<%=context %>/ctr?action=Book&control=Edit&bookId=<%=bookB.getBookId() %>">Edit</a> || 
						<a href="<%=context %>/ctr?action=Book&control=Delete&bookId=<%=bookB.getBookId() %>"
							onclick="return confirm('Are you sure you want to delete?\nThis will cascade delete all entries in library schema');">Delete</a>
					</td>
					<td><input type="button" value="View Copies" 
					onclick="window.location.href='<%=context%>/ctr?action=Book&control=ViewCopies&bookId=<%=bookB.getBookId()%>'"/></td>
				</tr>
<%			} 
%>
			<tr><td style="border:0px" colspan=6><br/></td></tr>
			<tr>
			<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=0 %>">First</a>
			</td>
			<td style="text-align: left;border:0px">
<% 
			if(start!=0)
			{
	%>						
				<a href="<%=context %>/ctr?action=searchListPrevious&currentPosition=<%=start-10 %>">Previous</a></td>
	<% 
			}
	%>						
			<td style="text-align: center;border:0px" colspan="2">
	<% 		if(size-(size%50)==size){a = size - 50;}else {a=(size-(size%50));}
			if(size%50 == 0){b = size/50;}else{b = (size/50)+1;}
			for(int k=0; k <b;k++)
			{
	%>
				<a href="<%=context %>/ctr?action=searchListPrevious&currentPosition=<%=50*(k) %>"><%if((start/50)==k){ %><font style="color: red;"><%=k+1%></font><%;}else{out.println(k+1);} %></a>&nbsp;&nbsp;
	<%		}
	%>
			</td>
			<td style="text-align: right;border:0px">
	<%		if(i<size)
			{ 
	%>
				<a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=start+50 %>">Next</a>
	<%		} 
	%>
			</td>
			<td style="text-align: center;border:0px"><a href="<%=context %>/ctr?action=searchListNext&currentPosition=<%=a %>">Last</a></td>
			</tr>
			<tr>
			<td style="text-align: center;border:0px" colspan="7">Total No.of Records : <%=size %> </td>
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