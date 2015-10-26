<%@page import="java.util.ArrayList" %>
<%@page import="bean.*"%>

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
	text-align: left; 
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
<script type="text/javascript" src="<%=context %>/javascript/SearchFines.js"></script>
<script type="text/javascript">

$(document).ready(function() {
    $('#getPaidFineAmt').click(function(event) {  
    	var cardNumber=$('#cardNo').text();
     $.get('<%=context %>/ctr',{action:"Fines", control:"getOldFineAmt", cardNo:cardNumber},function(jsonObj) { 
    	 	var json = JSON.parse(jsonObj);
    	 	if (json.status == false){
    			alert(json.errMsg);
    	 	}else{
    	 		var paidFineAmt = json.paidFineAmt;
    	 		alert("You have paid a total fine of \n $" + paidFineAmt );
    	 	}
        });
    });
});
</script>
	    
<title>Search Fines</title>	    
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Search Fines :</h2>	

<form method="post" action="<%=context %>/ctr" onsubmit="return validateForm();">
	<table class="inputTable" border=0>
		<tr>
			<td align="right">Card No :</td>
			<td><input type="text" name="searchCardNo" id="searchCardNo" size="30" maxLength="10" onkeypress='validate(event)'/></td>
		</tr>
		
		<tr>
			<td colspan="2" align="center"> <br>
				<input type="submit" name="search" value="Search">
				<input type="reset" name="reset" value="Reset" >
				<input type="hidden" name="action" value="Fines">
				<input type="hidden" name="control" value="View">
			</td>
		</tr>
</table>
</form>
		<%if (request.getAttribute("noBorrower") != null) { 
			String errorMsg = (String) request.getAttribute("noBorrower");
		%>
			<h2 style="color:red"><i><%=errorMsg %> </i></h2>
		<% }else if (request.getAttribute("borrowerB") != null && request.getAttribute("fineAmt") !=null ){
			BorrowerBean borrowB = (BorrowerBean)	request.getAttribute("borrowerB");
			double fineAmt = Double.parseDouble(request.getAttribute("fineAmt").toString());
			int checkoutRemainingCopies = 0;
			String errMsg = "";
			if (fineAmt > 0){
				checkoutRemainingCopies = Integer.parseInt(request.getAttribute("checkedCount").toString());
			}
			if (fineAmt <= 0 || (fineAmt > 0 && checkoutRemainingCopies > 0)){
				if (request.getAttribute("errMsg") != null) errMsg = (String) request.getAttribute("errMsg"); 
			}
		%>
			<h3 style="color:red"><i><%=errMsg %> </i></h3>
			<table class="dataTable">
				<tr>
					<td align="right">Borrower Card No :</td>
					<td><p id="cardNo"><%=borrowB.getCardNo() %></p></td>
				</tr>
				<tr>
					<td align="right">Borrower Name :</td>
					<td><%=borrowB.getfName() + " " + borrowB.getlName() %></td>
				</tr>
				<tr>
					<td align="right">Email :</td>
					<td><%=borrowB.getEmailId() %></td>
				</tr>
				<tr>
					<td align="right">Total Fine Amount :</td>
					<td>$ <%=fineAmt %></td>
				</tr>
				<tr>
					<td align="right" > <br>
						<input type="button" name="control" value="Pay" <%if (fineAmt <= 0 || (fineAmt > 0 && checkoutRemainingCopies > 0)){ %> 
							disabled
						<% } %> onclick="window.location.href='<%=context %>/ctr?action=Fines&control=Pay&cardNo=<%=borrowB.getCardNo() %>'"/>
					</td>
					<td><br>
						<input type="button" id="getPaidFineAmt" value="Get paid fine Amount"  />
					</td>
				</tr>
			</table>
		
		<% } %>
		
		
		</div>
    
    </div>