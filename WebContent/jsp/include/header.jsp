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


<% 
	response.addHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	if (session == null || session.getAttribute("userName") == null || session.getAttribute("userName").equals("")) 
	{
		
		response.sendRedirect(context+"/jsp/Login.jsp");
	}
%>        

