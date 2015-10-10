/**
 * @author Venkatesh
 * Date : 04 October, 2015
 * EmployeeServlet controls all methods related to employee.
 */

package servlet;

// TO read csv file
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

import bean.BookBean;

import dao.BookDAO;

/**
 * Servlet implementation class LibraryServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		BookDAO bookDAO = new BookDAO();
		//DepartmentDAO deptDAO = new DepartmentDAO();
		Logger log = null;
		log = log.getLogger("Controller Servlet");
		try
		{
			/**
			 * Login handler 
			 
			if(action.equals("login"))
			{
				String uName = request.getParameter("userName");
				String password = request.getParameter("password");
				if((uName == "" || uName == null) || (password == "" || password == null))
				{
					session.invalidate();
					request.setAttribute("invalidLogin", "Invalid Login details.");
					forwardToLocation("jsp/Login.jsp", request, response);
				}
				else if(!uName.equals("") && !password.equals(""))
				{
					session.setAttribute("userName", uName);
					forwardToLocation("jsp/Home.jsp", request, response);
				}
			}
			
			/**
			 * Logout handler 
			 
			if(action.equals("logout"))
			{
				session.invalidate();
				request.setAttribute("logoutSuccess", "You have been successfully logged out.");
				forwardToLocation("jsp/Login.jsp", request, response);
			}
			
			
			/**
			 * Code to handle Employee related issues  
			 
			if(action.equals("Employee"))
			{
				String para = request.getParameter("control");
				
				if(para.equals("registerEmployee"))
				{
					EmployeeBean empB = new EmployeeBean();
					empB.setFirstName(request.getParameter("firstName"));
					empB.setMiddleName(request.getParameter("middleName"));
					empB.setLastName(request.getParameter("lastName"));
					System.out.println(request.getParameter("dateOfBirth"));
					System.out.println(empDAO.getDateValue(request.getParameter("dateOfBirth"), empDAO.date_to_dtbs));
					empB.setDateOfBirth(empDAO.getDateValue(request.getParameter("dateOfBirth"), empDAO.date_to_dtbs));
					empB.setGender(request.getParameter("gender"));
					empB.setAddress(request.getParameter("address"));
					empB.setContactNum(request.getParameter("contactNum"));
					empB.setPersonalMobile(request.getParameter("personalMobile"));
					empB.setDesignation(request.getParameter("designation"));
					empB.setYearsOfExp(Integer.parseInt(request.getParameter("experience")));


					String msg = empDAO.registerEmployee(empB);

					request.setAttribute("EmployeeRegSuccess", msg);
					session.setAttribute("EmployeeList", null);
					displayEmployeeListPage(request, response);

				}
				else if(para.equals("View"))
				{
					long empId = Long.parseLong(request.getParameter("empId"));
					ArrayList<EmployeeBean> arrEmployee = empDAO.viewEmployee(empId);
					if(arrEmployee.size()!=0)
					{
						request.setAttribute("EmployeeDetails", arrEmployee);
					}
					forwardToLocation("jsp/EmployeeView.jsp", request, response);
				}
				else if(para.equals("Edit"))
				{
					long empId = Long.parseLong(request.getParameter("empId"));
					ArrayList<EmployeeBean> arrEmployee = empDAO.viewEmployee(empId);
					if(arrEmployee.size()!=0)
					{
						request.setAttribute("EmployeeDetails", arrEmployee);
					}
					forwardToLocation("jsp/EmployeeEdit.jsp", request, response);
				}
				else if(para.equals("Update"))
				{
					EmployeeBean empB = new EmployeeBean();
					empB.setEmployeeId(Long.parseLong(request.getParameter("empId")));
					empB.setFirstName(request.getParameter("firstName"));
					empB.setMiddleName(request.getParameter("middleName"));
					empB.setLastName(request.getParameter("lastName"));
					empB.setDateOfBirth(empDAO.getDateValue(request.getParameter("dateOfBirth"), empDAO.date_to_dtbs));
					empB.setGender(request.getParameter("gender"));
					empB.setAddress(request.getParameter("address"));
					empB.setContactNum(request.getParameter("contactNum"));
					empB.setPersonalMobile(request.getParameter("personalMobile"));
					empB.setDesignation(request.getParameter("designation"));
					empB.setYearsOfExp(Integer.parseInt(request.getParameter("experience")));


					String msg = empDAO.updateEmployee(empB);

					request.setAttribute("EmployeeUpdateSuccess", msg);
					session.setAttribute("EmployeeList", null);
					displayEmployeeListPage(request, response);
				}
				else if(para.equals("Delete"))
				{
					long empId = Long.parseLong(request.getParameter("empId"));
					String msg = empDAO.deleteEmployee(empId);
					
					request.setAttribute("EmployeeDeleteSuccess", msg);
					
					session.setAttribute("EmployeeList", null);
					displayEmployeeListPage(request, response);
					
				}
				else if(para.equals("viewAllEmp"))
				{
					displayEmployeeListPage(request, response);
				}
			}

			
			/**
			 * Code to handle Department related issues  
			 
			if(action.equals("Dept"))
			{
				String para = request.getParameter("control");
				
				
				if(para.equals("registerDept"))
				{
					DepartmentBean deptB = new DepartmentBean();
					deptB.setDeptName(request.getParameter("deptName"));
					deptB.setDeptHead(request.getParameter("deptHead"));
					deptB.setContactNumber(request.getParameter("deptContactNum"));

					String msg = deptDAO.registerDept(deptB);
					request.setAttribute("DeptRegSuccess", msg);

					session.setAttribute("DeptList", null);
					displayDeptListPage(request, response);

				}
				else if(para.equals("View"))
				{

					long deptId = Long.parseLong(request.getParameter("deptId"));
					ArrayList<DepartmentBean> arrDept = deptDAO.viewDept(deptId);
					if(arrDept.size()!=0)
					{
						request.setAttribute("DeptDetails", arrDept);
					}
					forwardToLocation("jsp/DeptView.jsp", request, response);

				}
				else if(para.equals("Edit"))
				{

					long deptId = (Long.parseLong(request.getParameter("deptId")));
					ArrayList<DepartmentBean> arrDept = deptDAO.viewDept(deptId);
					if(arrDept.size()!=0)
					{
						request.setAttribute("DeptDetails", arrDept);
					}
					forwardToLocation("jsp/DeptEdit.jsp", request, response);

				}
				else if(para.equals("Update"))
				{
					long deptId = (Long.parseLong(request.getParameter("deptId")));
					DepartmentBean deptB = new DepartmentBean();
					deptB.setDepartmentId(deptId);
					deptB.setDeptName(request.getParameter("deptName"));
					deptB.setDeptHead(request.getParameter("deptHead"));
					deptB.setContactNumber(request.getParameter("deptContactNum"));

					String msg = deptDAO.updateDept(deptB);
					request.setAttribute("DeptUpdateSuccess", msg);

					session.setAttribute("DeptList", null);
					displayDeptListPage(request, response);

				}
				else if(para.equals("Delete"))
				{
					long deptId = (Long.parseLong(request.getParameter("deptId")));
					String msg = deptDAO.deleteDepartment(deptId);
					request.setAttribute("DeptDeleteSuccess", msg);

					session.setAttribute("DeptList", null);
					displayDeptListPage(request, response);
				}
				else if(para.equals("viewAllDept"))
				{
					displayDeptListPage(request, response);
				}
			}
			
			/**
			 * Code to handle Assignment of Employee to Department  
			 
			if(action.equals("assignPage"))
			{
				if(session.getAttribute("EmployeeList")==null)
				{
					ArrayList<EmployeeBean> arrAllEmployeeList = empDAO.viewAllEmployee(true);
					session.setAttribute("EmployeeList", arrAllEmployeeList);
				}
				if(session.getAttribute("DeptList")==null)
				{
					ArrayList<DepartmentBean> arrAllDeptList = deptDAO.viewAllDeptDetails(true);
					session.setAttribute("DeptList", arrAllDeptList);
				}
				
				getAllAssignedDept(request, response);
			}
			
			if(action.equals("assignDept"))
			{
				long empId = 0,deptId = 0;
				try
				{
					empId = Long.parseLong(request.getParameter("empId"));
					deptId = Long.parseLong(request.getParameter("deptId"));
				}
				catch(NumberFormatException nfe)
				{
					empId = 0;
					deptId = 0;
				}
				String msg = empDAO.assignDept(empId, deptId);
				request.setAttribute("AssignDeptMsg", msg);
				
				session.setAttribute("AssignedList",null);
				getAllAssignedDept(request, response);
			}
			
			/**
			 * Code to handle Book search
			 */
			if(action.equals("SearchBook"))
			{
				session.setAttribute("BookSearchList",null);
				session.setAttribute("search", null);
				String searchText = request.getParameter("searchBook");
				String searchCriteria = request.getParameter("searchCriteria");
				//System.out.println(searchCriteria + "....Reached Search...." + searchText);
				
				ArrayList<BookBean> arrSearchList = bookDAO.getSearchBookList(searchText, searchCriteria);
				if(arrSearchList.size()!=0)
				{
					if(session.getAttribute("BookSearchList")==null)
					{
						session.setAttribute("BookSearchList", arrSearchList);
					}
				}
				session.setAttribute("search", searchText);
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			
			/**
			 * Code to handle Read file (CSV)
			 */
			if(action.equals("readFile"))
			{
				BufferedReader fileReader = null;
				final String DELIMITER = "\t";
				try
		        {
		            String line = "";
		            //Create the file reader
		            fileReader = new BufferedReader( 
		            		     new FileReader(
		            		     "E:\\UTD Study Materials\\3 FALL 15\\" + 
		            		     "CS6360 Database Design\\DB Prog assignment\\books.csv"));
		             
		            java.sql.Connection conn = bookDAO.getConn();
		            //Read the file line by line
		            while ((line = fileReader.readLine()) != null)
		            {
		                //Get all tokens available in line
		                String[] tokens = line.split("\t(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		                /*for(String token : tokens)
		                {
		                    //Print all tokens
		                    System.out.println(token);
		                }*/
		                bookDAO.insertBookAuthors(conn, tokens);
		            }
		            
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		        finally
		        {
		            try {
		                fileReader.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
			}
			
			/**
			 * Code to handle Next & previous list page of employee details
			 
			if(action.equals("empListNext"))
			{
				forwardToLocation("jsp/EmployeeList.jsp", request, response);
			}
			if(action.equals("empListPrevious"))
			{
				forwardToLocation("jsp/EmployeeList.jsp", request, response);
			}
			
			
			/**
			 * Code to handle Next & previous list page of department details
			 
			if(action.equals("deptListNext"))
			{
				forwardToLocation("jsp/DepartmentList.jsp", request, response);
			}
			if(action.equals("deptListPrevious"))
			{
				forwardToLocation("jsp/DepartmentList.jsp", request, response);
			}
			
			/**
			 * Code to handle Next & previous list page of employees assigned to department
			 
			if(action.equals("assignListNext"))
			{
				forwardToLocation("jsp/AssignDept.jsp", request, response);
			}
			if(action.equals("assignListPrevious"))
			{
				forwardToLocation("jsp/AssignDept.jsp", request, response);
			}
			
			/**
			 * Code to handle Next & previous list page of search details
			 */
			if(action.equals("searchListNext"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			if(action.equals("searchListPrevious"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			
			/**
			 * Code to add employee details using file upload functionality
			 
			if(action.equals("empFile"))
			{
				String contentType = request.getContentType();
				
				//check the content type is not equal to Null and the passed data from mulitpart/form-data        
				if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
				{
					DataInputStream in = new DataInputStream(request.getInputStream());
					//we are taking the length of Content type data                
					int formDataLength = request.getContentLength();
					
					byte dataBytes[] = new byte[formDataLength];
					
					int byteRead = 0;
					int totalBytesRead = 0;
					
					//loop converting the uploaded file into byte                
					while(totalBytesRead < formDataLength)
					{
						byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
						totalBytesRead += byteRead;
					}	
					
					String file = new String(dataBytes);
					
					//System.out.println("Details from client:\n"+file+"\n==============");
					
					//for saving the file name                
					String saveFile = file.substring(file.indexOf("filename=\"")+10);
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
					int lastIndex = contentType.lastIndexOf("=");
					
					String boundary = contentType.substring(lastIndex + 1,contentType.length());
					int pos;
					//extracting the index of file                 
					pos = file.indexOf("filename=\"");
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					int boundaryLocation = file.indexOf(boundary, pos)-4 ;
					int startPos = ((file.substring(0, pos)).getBytes()).length;
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length; 
					
					// creating a new file with the same name and writing the content in new file                
					FileOutputStream fileOut = new FileOutputStream(saveFile);                
					fileOut.write(dataBytes, startPos, (endPos - startPos));                
					fileOut.flush();                
					fileOut.close();
					
					String file1 = new String(dataBytes,startPos,(endPos-startPos));
					String splitter = "\n";
					
					String data1[] = file1.split(splitter);
									
					String splitter2 = ",";
					
					String msg[] = new String[data1.length];
					EmployeeBean empB = null;
					for(int i=1;i<data1.length;i++)
					{
						String data2[] = data1[i].split(splitter2);
						for(int j = 0; j<data2.length;j++)
						{
							if(data2[j].indexOf("\"") == 0)
							{
								int start = data2[j].indexOf("\"");
								int end = data2[j].lastIndexOf("\"");
								data2[j] = data2[j].substring(start + 1 ,end);
							}
							if(data2[j].indexOf('\r')>0 )
							{
								int end = data2[j].lastIndexOf('\r');
								data2[j] = data2[j].substring(0 ,end);
							}
							
						}
						empB= new EmployeeBean();
						empB.setFirstName(data2[0]);
						empB.setMiddleName(data2[1]);
						empB.setLastName(data2[2]);
						empB.setDateOfBirth(data2[3]);
						empB.setGender(data2[4]);
						empB.setAddress(data2[5]);
						empB.setContactNum(data2[6]);
						empB.setPersonalMobile(data2[7]);
						empB.setDesignation(data2[8]);
						empB.setYearsOfExp(Integer.parseInt(data2[9]));
						msg[i]= empDAO.registerEmployee(empB);
						
						
					}
					
					session.setAttribute("EmployeeList", null);
					request.setAttribute("EmpUploadMsg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("jsp/Registration_File.jsp");
					rd.forward(request, response);
				}
			}
			
			/**
			 * Code to add department details using file upload functionality
			 
			if(action.equals("deptFile"))
			{
				String contentType = request.getContentType();
				
				//check the content type is not equal to Null and the passed data from mulitpart/form-data        
				if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
				{
					DataInputStream in = new DataInputStream(request.getInputStream());
					//we are taking the length of Content type data                
					int formDataLength = request.getContentLength();
					
					byte dataBytes[] = new byte[formDataLength];
					
					int byteRead = 0;
					int totalBytesRead = 0;
					
					//loop converting the uploaded file into byte                
					while(totalBytesRead < formDataLength)
					{
						byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
						totalBytesRead += byteRead;
					}	
					
					String file = new String(dataBytes);
					
					//System.out.println("Details from client:\n"+file+"\n==============");
					
					//for saving the file name                
					String saveFile = file.substring(file.indexOf("filename=\"")+10);
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
					int lastIndex = contentType.lastIndexOf("=");
					
					String boundary = contentType.substring(lastIndex + 1,contentType.length());
					int pos;
					//extracting the index of file                 
					pos = file.indexOf("filename=\"");
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					int boundaryLocation = file.indexOf(boundary, pos)-4 ;
					int startPos = ((file.substring(0, pos)).getBytes()).length;
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length; 
					
					// creating a new file with the same name and writing the content in new file                
					FileOutputStream fileOut = new FileOutputStream(saveFile);                
					fileOut.write(dataBytes, startPos, (endPos - startPos));                
					fileOut.flush();                
					fileOut.close();
					
					String file1 = new String(dataBytes,startPos,(endPos-startPos));
					String splitter = "\n";
					
					String data1[] = file1.split(splitter);
									
					String splitter2 = ",";
					
					String msg[] = new String[data1.length];
					DepartmentBean deptB = null;
					for(int i=1;i<data1.length;i++)
					{
						String data2[] = data1[i].split(splitter2);
						for(int j = 0; j<data2.length;j++)
						{
							if(data2[j].indexOf("\"") == 0)
							{
								int start = data2[j].indexOf("\"");
								int end = data2[j].lastIndexOf("\"");
								data2[j] = data2[j].substring(start + 1 ,end);
							}
							if(data2[j].indexOf('\r')>0 )
							{
								int end = data2[j].lastIndexOf('\r');
								data2[j] = data2[j].substring(0 ,end);
							}
							
						}
						deptB = new DepartmentBean();
						deptB.setDeptName(data2[0]);
						deptB.setDeptHead(data2[1]);
						deptB.setContactNumber(data2[2]);
						msg[i]= deptDAO.registerDept(deptB);
						
					}
					
					session.setAttribute("DeptList", null);
					request.setAttribute("DeptUploadMsg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("jsp/Registration_File.jsp");
					rd.forward(request, response);
				}
			}*/
			
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	/**
	private void getAllAssignedDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Logger log = null;
		log = log.getLogger("Controller Servlet : getAllAsignedDept()");
		HttpSession session = request.getSession();
		DepartmentDAO deptDAO = new DepartmentDAO();
		ArrayList<EmployeeBean> arrAssignedList = new ArrayList<EmployeeBean>();
		try
		{
			if(session.getAttribute("AssignedList")==null)
			{
				arrAssignedList = deptDAO.assignedList();
				session.setAttribute("AssignedList", arrAssignedList);
			}
			
			forwardToLocation("jsp/AssignDept.jsp", request, response);
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	/**
	 * Helper method to display all Employee details and forward to Employee List page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 
	private void displayEmployeeListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger log = null;
		log = log.getLogger("Controller Servlet : displayEmployeeListPage()");
		ArrayList<EmployeeBean> arrAllEmployeeList = new ArrayList<EmployeeBean>();
		EmployeeDAO empDAO = new EmployeeDAO();
		HttpSession session = request.getSession();
		try
		{
			if(session.getAttribute("EmployeeList")==null)
			{
				arrAllEmployeeList = empDAO.viewAllEmployee(true);
				session.setAttribute("EmployeeList", arrAllEmployeeList);
			}
			forwardToLocation("jsp/EmployeeList.jsp", request, response);
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * Helper method to display all Department details and forward to Dept List page	
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 
	private void displayDeptListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger log = null;
		log = log.getLogger("Controller Servlet : displayDeptListPage()");
		ArrayList<DepartmentBean> arrAllDeptList = new ArrayList<DepartmentBean>();
		DepartmentDAO deptDAO = new DepartmentDAO();
		HttpSession session = request.getSession();
		try
		{
			if(session.getAttribute("DeptList")==null)
			{
				arrAllDeptList = deptDAO.viewAllDeptDetails(true);
				session.setAttribute("DeptList", arrAllDeptList);
			}
			forwardToLocation("jsp/DepartmentList.jsp", request, response);
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * Helper method to dispatch to other JSP pages
	 * @param url
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToLocation(String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(url);

		if( null != rd ){
			rd.forward(request, response);
		}else{	
			//
			response.sendError(403, "No page found matching the URL:"+ url );
		};
	}

}
