function validateAll()
{
	var fname = document.empReg.firstName.value;
	var lname = document.empReg.lastName.value;
	var dob = document.empReg.dateOfBirth.value;
	var gender = document.empReg.gender.value;
	var address = document.empReg.address.value;
	var cont = document.empReg.contactNum.value;
	var mobile = document.empReg.personalMobile.value;
	var desig = document.empReg.designation.value;
	var exp = document.empReg.experience.value;
	var alpha = /^[a-zA-z '.]+$/;
	var num = /^[0-9]+$/;
	
	
	if(fname==null || fname == "")
	{
		alert("Please enter first name.");
		document.empReg.firstName.focus();
		return false;
	}
	else if(!fname.match(alpha))
	{
		alert("Please enter only alphabets in first name.");
		document.empReg.firstName.focus();
		return false;
	}
	
	if(lname==null || lname == "")
	{
		alert("Please enter last name.");
		document.empReg.lastName.focus();
		return false;
	}
	else if(!lname.match(alpha))
	{
		alert("Please enter only alphabets in last name.");
		document.empReg.lastName.focus();
		return false;
	}
	
	if(dob==null || dob == "")
	{
		alert("Please select date of birth.");
		document.empReg.dateOfBirth.focus();
		return false;
	}
	
	if(address==null || address == "")
	{
		alert("Please enter address.");
		document.empReg.address.focus();
		return false;
	}
	
	if(cont==null || cont == "")
	{
		alert("Please enter contact number.");
		document.empReg.contactNum.focus();
		return false;
	}
	else if(!cont.match(num))
	{
		alert("Contact number must contain only numbers.");
		document.empReg.contactNum.focus();
		return false;
	}
	
	
	if(mobile==null || mobile == "")
	{
		alert("Please enter Personal Mobile number.");
		document.empReg.personalMobile.focus();
		return false;
	}
	else if(!mobile.match(num))
	{
		alert("Contact number must contain only numbers.");
		document.empReg.personalMobile.focus();
		return false;
	}
	else if(mobile.length!=10)
	{
		alert("Contact number must contain 10 digits.");
		document.empReg.personalMobile.focus();
		return false;
	}
	
	if(desig == null || desig =="")
	{
		alert("Please enter desigantion");
		document.empReg.designation.focus();
		return false;
	}
	else if(!desig.match(alpha))
	{
		alert("Desigantion should contain only aplhabets.");
		document.empReg.designation.focus();
		return false;
	}
	
	if(exp == null || exp =="")
	{
		alert("Please enter experience.");
		document.empReg.experience.focus();
		return false;
	}
	else if(!exp.match(num))
	{
		alert("Desigantion should contain only aplhabets.");
		document.empReg.experience.focus();
		return false;
	}
	return true;
}