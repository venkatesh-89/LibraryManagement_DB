
$(document).ready(function() {                       
    $('#getBorrowerBtn').click(function(event) {  
        var cardNumber=$('#cardNo').val();
     $.post('ControllerServlet',{action:"checkout", control:"getBorrower", cardNo:cardNumber},function(borrowerName) { 
            $('#borrowerName').text(borrowerName);
            //$('#borrowerEmail').text(borrowerEmail);
        });
    });
});