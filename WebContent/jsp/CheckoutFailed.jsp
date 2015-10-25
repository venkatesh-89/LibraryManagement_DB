<%@include file="include/header1.jsp" %>

 <title>Success</title>

  	<%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920"> 
 		
 		<%if (request.getAttribute("errMsg") != null){ 
 			String errorMsg = (String) request.getAttribute("errMsg"); %>
 			
    		<h2 style="color:red;"><%=errorMsg %></h2> 
    	
    	<%} %>
    	
    	</div>
        
    </div>
