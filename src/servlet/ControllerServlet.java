package servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bean.*;
import dao.*;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String control = request.getParameter("control");
		
		BookDAO bookDAO = new BookDAO();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		BranchDAO branchDAO = new BranchDAO();
		
		Logger log = null;
		log = log.getLogger("Controller Servlet");
		try
		{
			if(action.equals("CreateBook")){
				String bookId = request.getParameter("bookId");
				String bookTitle = request.getParameter("bookTitle");
				String authors = request.getParameter("authors");
				
				bookDAO.createNewBook(bookId, bookTitle, authors);
			}
			
			if (action.equals("AddBorrower")){
				BorrowerBean borrowB = new BorrowerBean();
				borrowB.setfName(request.getParameter("fName"));
				borrowB.setlName(request.getParameter("lName"));
				borrowB.setEmailId(request.getParameter("email"));
				borrowB.setAddress(request.getParameter("address"));
				borrowB.setCity(request.getParameter("city"));
				borrowB.setState(request.getParameter("state"));
				borrowB.setPhone(request.getParameter("phone"));
				
				String msg = borrowerDAO.addNewBorrower(borrowB);
				request.setAttribute("msg", msg);
				forwardToLocation("jsp/NewBorrower.jsp", request, response);
			}
			
			if (action.equals("Book")){
				BookBean bookB = new BookBean(request.getParameter("bookId"));

				if (control.equals("Delete")){
					String msg = bookDAO.deleteBook(bookB);
					request.setAttribute("msg", msg);
					System.out.println(msg + '\n' +request.getContextPath());
					//forwardToLocation(, request, response);
				}
				else if (control.equals("Update")){
					bookB.setBookTitle(request.getParameter("bookTitle"));
					AuthorBean authorB = new AuthorBean(bookB.getBookId());
					authorB.setAuthorList(request.getParameter("authors"));
					bookB.setAuthorBean(authorB);
					
					String msg = bookDAO.updateBookDetails(bookB);
				}
				else{

					bookB = bookDAO.viewBookDetails(bookB);
					request.setAttribute("bookB", bookB);
					if(control.equals("View")){
						request.setAttribute("controlType", "View");
						forwardToLocation("jsp/ViewBook.jsp", request, response);
					}
					else if (control.equals("Edit")){
						request.setAttribute("controlType", "Edit");
						forwardToLocation("jsp/EditBook.jsp", request, response);
					}					
				}
				
			}
			
			if (action.equals("Borrower")){
				if(control.equals("ViewAll")){
					displayBorrowerListPage(request, response);
				}
				else if (control.equals("View")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					BorrowerBean borrowerB = new BorrowerBean(cardNo);
					
					borrowerB = borrowerDAO.viewBorrowerDetails(borrowerB);
					request.setAttribute("borrowerB", borrowerB);
					request.setAttribute("controlType", "View");
					forwardToLocation("jsp/ViewBorrower.jsp", request, response);
				}
				else if (control.equals("Edit")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					BorrowerBean borrowerB = new BorrowerBean(cardNo);
					
					borrowerB = borrowerDAO.viewBorrowerDetails(borrowerB);
					request.setAttribute("borrowerB", borrowerB);
					request.setAttribute("controlType", "Edit");
					forwardToLocation("jsp/EditBorrower.jsp", request, response);
				}
				else if (control.equals("Update")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					BorrowerBean borrowerB = new BorrowerBean(cardNo);
					
					borrowerB.setfName(request.getParameter("fName"));
					borrowerB.setlName(request.getParameter("lName"));
					borrowerB.setEmailId(request.getParameter("email"));
					borrowerB.setAddress(request.getParameter("address"));
					borrowerB.setCity(request.getParameter("city"));
					borrowerB.setState(request.getParameter("state"));
					borrowerB.setPhone(request.getParameter("phone"));
					
					String msg = borrowerDAO.updateBorrower(borrowerB);
					request.setAttribute("BorrowerUpdateSuccess", msg);
					displayBorrowerListPage(request, response);
				}
				else if (control.equals("Delete")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					
					String msg = borrowerDAO.deleteBorrower(cardNo);
					
					request.setAttribute("BorrowerDeleteSuccess", msg);
					displayBorrowerListPage(request, response);
				}
			}
			
			if (action.equals("Branch")){
				if(control.equals("ViewAll")){
					displayBranchListPage(request, response);
				}
				else if (control.equals("View")){
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					LibraryBranchBean branchB = new LibraryBranchBean(branchId);
					
					branchB = branchDAO.viewBranchDetails(branchB);
					request.setAttribute("branchB", branchB);
					request.setAttribute("controlType", "View");
					forwardToLocation("jsp/ViewBranch.jsp", request, response);
				}
				else if (control.equals("Edit")){
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					LibraryBranchBean branchB = new LibraryBranchBean(branchId);
					
					branchB = branchDAO.viewBranchDetails(branchB);
					request.setAttribute("branchB", branchB);
					request.setAttribute("controlType", "Edit");
					forwardToLocation("jsp/EditBranch.jsp", request, response);
				}
				else if (control.equals("Update")){
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					LibraryBranchBean branchB = new LibraryBranchBean(branchId);
					
					branchB.setBranchName(request.getParameter("branchName"));
					branchB.setAddress(request.getParameter("address"));
					
					String msg = branchDAO.updateBranch(branchB);
					request.setAttribute("BranchUpdateSuccess", msg);
					displayBranchListPage(request, response);
				}
				else if (control.equals("Delete")){
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					
					String msg = branchDAO.deleteBranch(branchId);
					
					request.setAttribute("BranchDeleteSuccess", msg);
					displayBranchListPage(request, response);
				}
			}
			
			if(action.equals("SearchBook"))
			{
				session.setAttribute("BookSearchList",null);
				session.setAttribute("search", null);
				String searchText = request.getParameter("searchBook");
				String searchCriteria = request.getParameter("searchCriteria");
				
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
			
			if(action.equals("borrowerListNext"))
			{
				forwardToLocation("jsp/ListBorrower.jsp", request, response);
			}
			
			if(action.equals("borrowerListPrevious"))
			{
				forwardToLocation("jsp/ListBorrower.jsp", request, response);
			}
			
			if(action.equals("searchListNext"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			
			if(action.equals("searchListPrevious"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			
				
			
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	private void displayBorrowerListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger log = null;
		log = log.getLogger("Controller Servlet : displayBorrowerListPage()");
		ArrayList<BorrowerBean> arrAllBorrowerList = new ArrayList<BorrowerBean>();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		HttpSession session = request.getSession();
		try
		{
			if(session.getAttribute("BorrowerList")==null)
			{
				arrAllBorrowerList = borrowerDAO.viewAllBorrower();
				session.setAttribute("BorrowerList", arrAllBorrowerList);
			}
			forwardToLocation("jsp/ListBorrower.jsp", request, response);
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	private void displayBranchListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger log = null;
		log = log.getLogger("Controller Servlet : displayBranchListPage()");
		ArrayList<LibraryBranchBean> arrAllBranchList = new ArrayList<LibraryBranchBean>();
		BranchDAO branchDAO = new BranchDAO();
		HttpSession session = request.getSession();
		try
		{
			if(session.getAttribute("BranchList")==null)
			{
				arrAllBranchList = branchDAO.viewAllBranch();
				session.setAttribute("BranchList", arrAllBranchList);
			}
			forwardToLocation("jsp/ListBranch.jsp", request, response);
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
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
