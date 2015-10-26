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

function validateForm(){
	var cardNo = $('#searchCardNo').val();
	if ( cardNo == ""){
		alert("Please enter Card Number");
		return false;
	}
}