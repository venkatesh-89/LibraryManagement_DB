function validate(){
	var bookId = $('#bookId').val();
	var title = $('#bookTitle').val();
	var authors = $('#authors').val();
	
	if (bookId == ""){
		alert("Please enter Book Id");
		return false;
	}
	
	if (bookId.length < 10){
		alert("Invalid Book Id, length cannot be less than 10");
		return false;
	}
	
	if (title == ""){
		alert("Please enter Book Title");
		return false;
	}
	
	if (authors == ""){
		alert("Please enter Author Names");
		return false;
	}
}