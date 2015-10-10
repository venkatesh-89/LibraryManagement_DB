function validateAll()
{
	var name = document.deptReg.deptName.value;
	var head = document.deptReg.deptHead.value;
	var cont = document.deptReg.deptContactNum.value;
	var alpha = /^[a-zA-z]+$/;
	var num = /^[0-9]+$/;
	if(name==null || name == "")
	{
		alert("Please enter department name.");
		document.deptReg.deptName.focus();
		return false;
	}
	else if(!name.match(alpha))
	{
		alert("Please enter only alphabets in department name.");
		document.deptReg.deptName.focus();
		return false;
	}
	if(head==null || head == "")
	{
		alert("Please enter department Head.");
		document.deptReg.deptHead.focus();
		return false;
	}
	else if(!head.match(alpha))
	{
		alert("Please enter only alphabets in department Head.");
		document.deptReg.deptHead.focus();
		return false;
	}
	if(cont==null || cont == "")
	{
		alert("Please enter contact number.");
		document.deptReg.deptContactNum.focus();
		return false;
	}
	else if(!cont.match(num))
	{
		alert("Contact number must contain only numbers.");
		document.deptReg.deptContactNum.focus();
		return false;
	}
	else if(cont.length!=10)
	{
		alert("Contact number must contain 10 digits.");
		document.deptReg.deptContactNum.focus();
		return false;
	}
	
	return true;
}