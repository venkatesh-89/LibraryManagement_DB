function file()
{
	if(document.empFile.F1.value==""||document.empFile.F1.value==null)
		{
			alert("Please select the file to upload");
			return false;
		}
	
	if(document.deptFile.F2.value==""|| document.deptFile.F2.value==null)
	{
		alert("Please select the file to upload");
		return false;
	}
}