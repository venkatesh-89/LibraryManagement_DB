function validate(evt) {
  var theEvent = evt || window.event;
  var key = theEvent.keyCode || theEvent.which;
  key = String.fromCharCode( key );
  var regex = /[0-9]/;
  if( !regex.test(key) ) {
    theEvent.returnValue = false;
    if(theEvent.preventDefault) theEvent.preventDefault();
  }
}

function validateInput(){
	var bookId = $('#searchBookId').val();
	var cardNo = $('#searchCardNo').val();
	var bName = $('#searchBorrowerName').val();
	
	if (bookId == ""){
		alert("Book Id cannot be empty");
		return false;
	}
	if (bookId.length < 10){
		alert("Invalid Book Id, length cannot be less than 10");
		return false;
	}
	if (cardNo == "" && bName == ""){
		alert("Card No and Borrower Name cannot be ");
		return false;
	}
	
	
}