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
		
		BookDAO bookDAO = new BookDAO();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		
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
			else if (action.equals("AddBorrower")){
				BorrowerBean borrowB = new BorrowerBean();
				borrowB.setfName(request.getParameter("fName"));
				borrowB.setlName(request.getParameter("lName"));
				borrowB.setEmailId(request.getParameter("email"));
				borrowB.setAddress(request.getParameter("address"));
				borrowB.setCity(request.getParameter("city"));
				borrowB.setState(request.getParameter("state"));
				borrowB.setPhone(request.getParameter("phone"));
				
				int card_no = borrowerDAO.addNewBorrower(borrowB);
				
			}
			else if(action.equals("SearchBook"))
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
			else if(action.equals("searchListNext"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			else if(action.equals("searchListPrevious"))
			{
				forwardToLocation("jsp/SearchBook.jsp", request, response);
			}
			else{
				System.out.println("No Method in Servlet");
			}
				
			
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
