<%@include file="include/header1.jsp" %>
 
 <title>Home</title>
        
  	<%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920"> 
    	<!--  
    	<p style="font-size: 16px;color: black;" align="right">Welcome <b><%=session.getAttribute("userName")%>,</b> <a style="color: yellow;" href="<%=context%>/ctr?action=logout">(Logout)</a></p>
    	-->
        	<h2> Welcome User<%--=session.getAttribute("userName")--%></h2>
        	<br/><br/>
        	<!--a href="<%--=context--%>/ctr?action=logout">Logout</a-->
        </div>
        
    </div>
