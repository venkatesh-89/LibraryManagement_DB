<%@include file="include/header1.jsp" %>

    <title>Checkout Book</title>
 <style>
th{
	text-align:right;
}

td.sep{
	width:15px;
}
</style>
<script src="http://code.jquery.com/jquery-latest.js"> </script>
<script type="text/javascript">

$(document).ready(function() {
	$('#checkoutBtn').prop("disabled",true);
    $('#getBorrowerBtn').click(function(event) {  
    	var cardNumber=$('#cardNo').val();
     $.get('<%=context %>/ctr',{action:"Checkout", control:"getBorrower", cardNo:cardNumber},function(jsonObj) { 
    	 	var json = JSON.parse(jsonObj);
    	 	if (json.status == false){
    			alert(json.errMsg);
    			$('#checkoutBtn').prop("disabled",true);
    	 	}else{
    	 		$('#borrowerName').text(json.borrowerName);
            	$('#borrowerEmail').text(json.borrowerEmail);
            	$('#checkoutBtn').prop("disabled",false);
            	$('#cardNo').prop("readonly", true);
    	 	}
        });
    });
    $('#resetBtn').click(function(event){
    	$('#borrowerName').text("");
    	$('#borrowerEmail').text("");
    	$('#checkoutBtn').prop("disabled",true);
    	$('#cardNo').prop("readonly", false);
    });
});
</script>
   
    <%@include file="include/headerLinks.jsp" %>
    
      <div id="templatemo_content">
    
    	<div class="section_w920">
    	
    	<h2>Checkout Details :</h2><br/>
    	
<form name="checkoutBook" action="<%=context %>/ctr">
	<% String bookId = request.getParameter("bookId");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
	%>
			<table style="font-size: 16px; margin-left: 50px;" border=0>
			<tr>
			<th>Book Id (ISBN)</th>
			<td class="sep"> : </td>
			<td><%=bookId %></td>
			</tr>
			<tr>
			<th>Branch Id</th>
			<td class="sep"> : </td>
			<td><%=branchId %></td>
			</tr>
			<tr>
			<th>Card No</th>
			<td class="sep"> : </td>
			<td><input type="text" name="cardNo" id="cardNo" maxLength="10"/></td>
			<td><input type="button" name="getBorrowerBtn" id="getBorrowerBtn" value="Get Borrower Details" /></td>
			</tr>
			<tr ng-hide="">
			<th>Borrower Name</th>
			<td class="sep"> : </td>
			<td id="borrowerName"></td>
			</tr>
			<tr>
			<th>Borrower Email</th>
			<td class="sep"> : </td>
			<td id="borrowerEmail"></td>
			</tr>
			<tr>
			<td align="right"><br/>
				<input type="submit" name="control" id="checkoutBtn" value="Checkout"/>
				<input type="hidden" name="action" value="Checkout" />
				<input type="hidden" name="bookId" value=<%=bookId %> />
				<input type="hidden" name="branchId" value=<%=branchId %> />
			</td>
			<td></td>
			<td><br/>
				<input type="reset" name="reset" id="resetBtn" value="Reset User Details"/></td>
			</tr>
			</table>
			
</form>
		</div>
    
    </div>
 