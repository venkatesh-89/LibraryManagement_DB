function validate(){
	
	var branchName = $('#branchName').val();
	var address = $('#address').val();
	
	if (branchName == ""){
		alert("Please enter branch name");
		return false;
	}
	
	if (address == ""){
		alert("Please enter branch address");
		return false;
	}
}