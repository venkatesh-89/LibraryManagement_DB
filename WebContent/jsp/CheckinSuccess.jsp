<%@include file="include/header1.jsp" %>

 <title>Success</title>

  	<%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920"> 
 		
 		<%if (request.getAttribute("Msg") != null){ 
 			String Msg = (String) request.getAttribute("Msg"); %>
 			
    		<h2 style="color:green;"><%=Msg %></h2> 
    	
    	<%} %>
    	
    	</div>
        
    </div>
