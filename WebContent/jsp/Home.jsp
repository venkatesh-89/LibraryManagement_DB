<%@include file="include/header1.jsp" %>
 
 <title>Home</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920"> 
    	<!--  
    	<p style="font-size: 16px;color: black;" align="right">Welcome <b><%=session.getAttribute("userName")%>,</b> <a style="color: yellow;" href="<%=context%>/ctr?action=logout">(Logout)</a></p>
    	-->
        	<h2> Welcome User<%--=session.getAttribute("userName")--%></h2>
        	<!--a href="<%--=context--%>/ctr?action=logout">Logout</a-->

			<p>Book Management : </p>
			<ul> 
        	<li> <a href="<%=context%>/jsp/NewBook.jsp">Create a new Book </a></li>
        	<li> <a href="<%=context%>/jsp/ViewBook.jsp">View/Update an existing Book </a></li>
        	</ul>
        	
        	<p>Borrower Management : </p>
        	<ul>
        	<li> <a href="<%=context%>/jsp/NewBorrower.jsp">Add a new Borrower </a></li>
        	<li> <a href="<%=context%>/ctr?action=Borrower&control=ViewAll">View all Borrowers </a></li>
        	</ul>
        	
        	<p>Library Branch Management : </p>
        	<ul>
        	<li> <a href="<%=context%>/jsp/NewBranch.jsp">Add a new Branch </a></li>
        	<li> <a href="<%=context%>/ctr?action=Branch&control=ViewAll">View all Branches </a></li>
        	</ul>
        </div>
        
    </div>
