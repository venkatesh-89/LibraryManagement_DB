package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

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
		String control = "";
		if (request.getParameter("control") != null){
			control = request.getParameter("control");
		}
		
		BookDAO bookDAO = new BookDAO();
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		BranchDAO branchDAO = new BranchDAO();
		CheckoutDAO checkoutDAO = new CheckoutDAO();
		BookLoansDAO loanDAO = new BookLoansDAO();
		
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
				borrowB.setPhone(request.getParameter("phone"));
				
				String msg = borrowerDAO.addNewBorrower(borrowB);
				request.setAttribute("msg", msg);
				clearBorrowerList(request);
				forwardToLocation("jsp/NewBorrower.jsp", request, response);
			}
			
			if (action.equals("AddBranch")){
				LibraryBranchBean branchB = new LibraryBranchBean();
				branchB.setBranchName(request.getParameter("branchName"));
				branchB.setAddress(request.getParameter("address"));
				String msg = branchDAO.addNewBranch(branchB);
				
				request.setAttribute("msg", msg);
				clearBranchList(request);
				forwardToLocation("jsp/NewBranch.jsp", request, response);
			}
			
			if (action.equals("Book")){
				BookBean bookB = new BookBean(request.getParameter("bookId"));

				if (control.equals("Delete")){
					String msg = bookDAO.deleteBook(bookB);
					request.setAttribute("msg", msg);
					System.out.println(msg + '\n' + request.getContextPath());
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
					ArrayList<BookCopiesBean> arrListBookCopiesB = new ArrayList<BookCopiesBean>();
					
					bookB = bookDAO.viewBookDetails(bookB, arrListBookCopiesB);
					request.setAttribute("bookB", bookB);
					request.setAttribute("arrListBookCopiesB", arrListBookCopiesB);
					if(control.equals("View")){
						request.setAttribute("controlType", "View");
						forwardToLocation("jsp/ViewBook.jsp", request, response);
					}
					else if (control.equals("Edit")){
						request.setAttribute("controlType", "Edit");
						forwardToLocation("jsp/EditBook.jsp", request, response);
					}
					if(control.equals("ViewCopies")){
						request.setAttribute("controlType", "View");
						forwardToLocation("jsp/ViewBookCopies.jsp", request, response);
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
					
					boolean stat = borrowerDAO.viewBorrowerDetails(borrowerB);
					if (stat == true ){
						request.setAttribute("borrowerB", borrowerB);
						request.setAttribute("controlType", "View");
					}
					forwardToLocation("jsp/ViewBorrower.jsp", request, response);
				}
				else if (control.equals("Edit")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					BorrowerBean borrowerB = new BorrowerBean(cardNo);
					
					boolean stat = borrowerDAO.viewBorrowerDetails(borrowerB);
					if (stat == true ){
						request.setAttribute("borrowerB", borrowerB);
						request.setAttribute("controlType", "Edit");
					}
					forwardToLocation("jsp/EditBorrower.jsp", request, response);
				}
				else if (control.equals("Update")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					BorrowerBean borrowerB = new BorrowerBean(cardNo);
					
					borrowerB.setfName(request.getParameter("fName"));
					borrowerB.setlName(request.getParameter("lName"));
					borrowerB.setEmailId(request.getParameter("email"));
					borrowerB.setAddress(request.getParameter("address"));
					borrowerB.setPhone(request.getParameter("phone"));
					
					String msg = borrowerDAO.updateBorrower(borrowerB);
					request.setAttribute("BorrowerUpdateSuccess", msg);
					clearBorrowerList(request);
					displayBorrowerListPage(request, response);
				}
				else if (control.equals("Delete")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					
					String msg = borrowerDAO.deleteBorrower(cardNo);
					
					request.setAttribute("BorrowerDeleteSuccess", msg);
					clearBorrowerList(request);
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
					clearBranchList(request);
					displayBranchListPage(request, response);
				}
				else if (control.equals("Delete")){
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					
					String msg = branchDAO.deleteBranch(branchId);
					
					request.setAttribute("BranchDeleteSuccess", msg);
					clearBranchList(request);
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
			
			if(action.equals("SearchLoans"))
			{
				session.setAttribute("LoanSearchList",null);
				
				String bookId = request.getParameter("searchBookId") == null ? "" : request.getParameter("searchBookId");
				String cardNo = request.getParameter("searchCardNo") == null ? "" : request.getParameter("searchCardNo");
				String borrowerName = request.getParameter("searchBorrowerName") == null ? "" : request.getParameter("searchBorrowerName");
				
				ArrayList<BookLoansBean> arrSearchList = loanDAO.getSearchLoanList(bookId, cardNo, borrowerName);
				if(arrSearchList.size()!=0)
				{
					if(session.getAttribute("LoanSearchList")==null)
					{
						session.setAttribute("LoanSearchList", arrSearchList);
					}
				}
				session.setAttribute("searchLoan", null);
				forwardToLocation("jsp/SearchLoans.jsp", request, response);
			}
			
			if(action.equals("Checkout"))
			{
				if(control.equals("getBorrower"))
				{
					int cardNo = 0;
					String errMsg = "";
					try{
						cardNo = Integer.parseInt(request.getParameter("cardNo"));
					}
					catch(Exception e){
						errMsg = "Please enter a valid card number";
						JSONObject json = new JSONObject();
						json.put("status", false);
						json.put("errMsg", errMsg);
						response.setContentType("text/plain");
					    response.getWriter().write(json.toString());
					}
					
					if (errMsg.equals("")){
						BorrowerBean borrowB = new BorrowerBean(cardNo);
						boolean stat = borrowerDAO.viewBorrowerDetails(borrowB);
						try{
							JSONObject json = new JSONObject();
							json.put("status", stat);
							if (stat == true){
								json.put("borrowerName", borrowB.getfName() + " " + borrowB.getlName());
							    json.put("borrowerEmail", borrowB.getEmailId());
							}
							else{
								json.put("borrowerName", "");
							    json.put("borrowerEmail", "");
							}
							
						    response.setContentType("text/plain");
						    response.getWriter().write(json.toString());
						}
						catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				}
				
				if (control.equals("Checkout")){
					String bookId = request.getParameter("bookId");
					int branchId = Integer.parseInt(request.getParameter("branchId"));
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					String errMsg = "";
					
					while(true){
						
						//Check borrower status
						BorrowerBean borrowB = new BorrowerBean(cardNo);
						boolean stat = borrowerDAO.viewBorrowerDetails(borrowB);
						if (stat != true){
							//Send an card number does not exists
							break;
						}
						
						//Check book copy availability from the branch requested
						BookBean bookB = new BookBean(bookId);
						LibraryBranchBean branchB = new LibraryBranchBean(branchId);
						int copies = checkoutDAO.verifyAvailableCopies(bookB, branchB); 
						System.out.println("Total Copies available : " + copies);
						if (copies <= 0){
							errMsg = "Sorry! There are no copies available to checkout in Branch : " + branchB.getBranchName();
							request.setAttribute("errMsg", errMsg);
							forwardToLocation("jsp/CheckoutFailed.jsp", request, response);
							break;
						}
						
						//Check if the total no.of checked out books is gt 3
						int checkedCopies = checkoutDAO.getTotalCheckedOutCopies(borrowB.getCardNo());
						System.out.println("Total checked out book for card no:" + borrowB.getCardNo() + " is : " + checkedCopies );
						if (checkedCopies >= 3){
							errMsg = "Sorry! The selected borrower with Card No. " + borrowB.getCardNo() + " has " + checkedCopies +  " books already checked out";
							request.setAttribute("errMsg", errMsg);
							forwardToLocation("jsp/CheckoutFailed.jsp", request, response);
							break;
						}
						
						//Checkout Process starts
						/** Insert into the book_loans table**/ 
						BookLoansBean loanB = new BookLoansBean();
						loanB.setBookId(bookB.getBookId());
						loanB.setBranchId(branchB.getBranchID());
						loanB.setCardNo(borrowB.getCardNo());
						int loanId = checkoutDAO.createCheckout(loanB, copies);
						
						if (loanId > 0){
							request.setAttribute("bookB", bookB);
							request.setAttribute("branchB", branchB);
							request.setAttribute("borrowB", borrowB);
							request.setAttribute("loanB", loanB);
							forwardToLocation("jsp/CheckoutSuccess.jsp", request, response);
						}
						break;
					}
				}
			}
			
			if(action.equals("Check_in")){
				if(control.equals("View")){
					int loanId = Integer.parseInt(request.getParameter("LoanId"));
					
					BookLoansBean loanB = new BookLoansBean(loanId);
					loanB = loanDAO.getLoanDetails(loanB);
					
					BookBean bookB = new BookBean(loanB.getBookId());
					bookB = bookDAO.viewBookDetails(bookB, null);
					
					LibraryBranchBean branchB = new LibraryBranchBean(loanB.getBranchId());
					branchB = branchDAO.viewBranchDetails(branchB);
					
					BorrowerBean borrowerB = new BorrowerBean(loanB.getCardNo());
					borrowerDAO.viewBorrowerDetails(borrowerB);
					
					request.setAttribute("bookB", bookB);
					request.setAttribute("branchB", branchB);
					request.setAttribute("borrowB", borrowerB);
					request.setAttribute("loanB", loanB);
					forwardToLocation("jsp/Check_in.jsp", request, response);
				}
				
				if (control.equals("Check-In Book")){
					String dateIn = request.getParameter("dateIn");
					System.out.println(dateIn);
					int loanId = Integer.parseInt(request.getParameter("loanId"));
					
					BookLoansBean loanB = new BookLoansBean(loanId);
					int copies = checkoutDAO.getCopiesForCheckin(loanB);
					
					boolean stat = checkoutDAO.createCheckIn(loanB, dateIn, copies);
					String Msg = "Check-in was successful for borrower with Card No. " + loanB.getCardNo();
					request.setAttribute("Msg", Msg);
					forwardToLocation("jsp/CheckinSuccess.jsp", request, response);
				}
			}
			
			
			if (action.equals("Fines")){
				if (control.equals("Calculate")){
					System.out.println(control);
					loanDAO.calculateFines();
					String Msg = "Fines calculated successfully";
					request.setAttribute("Msg", Msg);
					forwardToLocation("/jsp/CheckinSuccess.jsp", request, response);
				}
				
				if (control.equals("View")){
					int cardNo = Integer.parseInt(request.getParameter("searchCardNo"));
					
					BorrowerBean borrowB = new BorrowerBean(cardNo);
					boolean stat = borrowerDAO.viewBorrowerDetails(borrowB);
					
					if (stat == true){
						double fineAmt = loanDAO.viewFineDeatils(cardNo);
						String errMsg = "";
						if (fineAmt > 0){
							int checkedCount = loanDAO.getRemainingBookCount(cardNo);
							request.setAttribute("checkedCount", checkedCount);
							if (checkedCount > 0){
								errMsg = "Please Check-in all books to pay the fine";
								request.setAttribute("errMsg", errMsg);
							}
						}
						else{
							errMsg = "No fines to pay for this member";
							request.setAttribute("errMsg", errMsg);
						}
						
						request.setAttribute("borrowerB", borrowB);
						request.setAttribute("fineAmt", fineAmt);
					}
					else
						request.setAttribute("noBorrower", "No borrower exists");
					forwardToLocation("/jsp/SearchFines.jsp", request, response);
					
				}
				
				if (control.equals("Pay")){
					int cardNo = Integer.parseInt(request.getParameter("cardNo"));
					//BorrowerBean borrowB = new BorrowerBean(cardNo);
					boolean stat = loanDAO.payFine(cardNo);
					
					if (stat == true){
						request.setAttribute("Msg", "Your payment was successful");
						forwardToLocation("/jsp/CheckinSuccess.jsp", request, response);
					}
						
				}
				
				if (control.equals("getOldFineAmt")){
					int cardNo = 0;
					String errMsg = "";
					try{
						cardNo = Integer.parseInt(request.getParameter("cardNo"));
					}
					catch(Exception e){
						errMsg = "Please enter a valid card number";
						JSONObject json = new JSONObject();
						json.put("status", false);
						json.put("errMsg", errMsg);
						response.setContentType("text/plain");
					    response.getWriter().write(json.toString());
					}
					
					if (errMsg.equals("")){
						
						double paidFineAmt = loanDAO.getPaidFineAmt(cardNo);
						try{
							JSONObject json = new JSONObject();
							json.put("status", true);
							json.put("paidFineAmt", paidFineAmt);
							
						    response.setContentType("text/plain");
						    response.getWriter().write(json.toString());
						}
						catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
					
				}
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
			
			if(action.equals("branchListNext"))
			{
				forwardToLocation("jsp/ListBranch.jsp", request, response);
			}
			
			if(action.equals("branchListPrevious"))
			{
				forwardToLocation("jsp/ListBranch.jsp", request, response);
			}
			
			if(action.equals("searchLoanListNext"))
			{
				forwardToLocation("jsp/SearchLoans.jsp", request, response);
			}
			
			if(action.equals("searchLoanListPrevious"))
			{
				forwardToLocation("jsp/SearchLoans.jsp", request, response);
			}
			
			/** Testing code for prints */
			if(action.equals("test"))
			{
				
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
	
	private void clearBorrowerList(HttpServletRequest request) throws Exception{
		Logger log = null;
		log = log.getLogger("Controller Servlet : clearBorrowerList()");
		HttpSession session = request.getSession();
		try{
			if(session.getAttribute("BorrowerList")!=null){
				session.setAttribute("BorrowerList", null);
			}
		}
		catch(Exception e){
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
	
	private void clearBranchList(HttpServletRequest request) throws Exception{
		Logger log = null;
		log = log.getLogger("Controller Servlet : clearBranchList()");
		HttpSession session = request.getSession();
		try{
			if(session.getAttribute("BranchList")!=null){
				session.setAttribute("BranchList", null);
			}
		}
		catch(Exception e){
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
