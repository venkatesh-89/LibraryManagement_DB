<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%!
//Declare class(instance) variable to store context name. 
//It will be available in all jsps which include current jsp.
String context;
public void jspInit(){
	//super.jspInit();
	context = getServletContext().getContextPath();
}
%>



