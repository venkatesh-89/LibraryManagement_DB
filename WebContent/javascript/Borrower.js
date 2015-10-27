function validate(){
	var fName = $('#fName').val();
	var lName = $('#lName').val();
	var email = $('#email').val();
	var address = $('#address').val();
	var phone = $('#phone').val();
	
	if (fName == ""){
		alert("Please enter First name");
		return false;
	}
	
	if (lName == ""){
		alert("Please enter Last name");
		return false;
	}
	
	if (email == ""){
		alert("Please enter Email");
		return false;
	}
	else{
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var stat = re.test(email);
		if (stat == false){
			alert("Enter valid Email");
			return stat;
		}
	}
	
	if (address == ""){
		alert("Please enter Address");
		return false;
	}
	
	if (phone == ""){
		alert("Please enter Phone number");
		return false;
	}
}