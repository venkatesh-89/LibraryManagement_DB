<head>
<style>
#navlist li{
	display: inline;
	list-style-type: none;
	padding-right: 20px;
}
</style>

<script src="<%=getServletContext().getContextPath() %>/javascript/jquery-2.1.4.min.js"> </script>

</head>
<ul id="navlist">
    <li><a href="<%=getServletContext().getContextPath() %>/jsp/Home.jsp">Home</a></li>
    <li><a href="<%=getServletContext().getContextPath() %>/jsp/SearchBook.jsp">Search Books</a></li>
    <li><a href="<%=getServletContext().getContextPath() %>/jsp/SearchLoans.jsp">Search Book Loans</a></li>
         
    <li><a href="#">Contact</a></li>
    
</ul>
    
