package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import bean.*;

public class CheckoutDAO extends GenericDAO{
	Logger log = null;
	
	public int verifyAvailableCopies(BookBean bookB, LibraryBranchBean branchB) throws Exception{
		log = log.getLogger("CheckoutDAO : verifyAvailableCopies()");
		int copies = 0;
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Select book.title, TRIM(GROUP_CONCAT(author_name SEPARATOR ', ')), " +
							"branch_name, address, copies_available " +
							"from book, book_authors, library_branch, book_copies " +
							"where book.book_id = book_authors.book_id " +
							"and book.book_id = book_copies.book_id " +
							"and library_branch.branch_id = book_copies.branch_id " +
							"and book.book_id = ? " +
							"and library_branch.branch_id = ? " +
							"group by book.book_id , title";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, bookB.getBookId());
			prepStmnt.setInt(2, branchB.getBranchID());
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				bookB.setBookTitle(rs.getString(1));
				
				AuthorBean authorB = new AuthorBean(bookB.getBookId());
				authorB.setAuthorList(rs.getString(2));
				
				bookB.setAuthorBean(authorB);
				
				branchB.setBranchName(rs.getString(3));
				branchB.setAddress(rs.getString(4));
				
				copies = rs.getInt(5);
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return copies;
	}
	
	public int getTotalCheckedOutCopies(int cardNo) throws Exception{
		log = log.getLogger("CheckoutDAO : getTotalCheckedOutCopies()");
		int totalCopies = 0;
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Select card_no, count(*) " + 
							"from book_loans " +
							"where card_no = ? " +
							"group by card_no";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				totalCopies = rs.getInt(2);
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return totalCopies;
	}
	
	
	public int createCheckout(BookLoansBean loanB, int copies) throws Exception{
		log = log.getLogger("CheckoutDAO : createCheckout()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			int loanId = getNextLoanId(conn);
			
			if (loanId <= 0){
				log.error("Error getting the Loan_id from the sequence table");
				return -1;
			}
			
			loanB.setLoanId(loanId);
			getDates(loanB);
			
			String query = "Insert into book_loans (loan_id, book_id, branch_id, card_no, date_out, due_date)" + 
							"values (?, ?, ?, ?, ?, ?)";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, loanB.getLoanId());
			prepStmnt.setString(2, loanB.getBookId());
			prepStmnt.setInt(3, loanB.getBranchId());
			prepStmnt.setInt(4, loanB.getCardNo());
			prepStmnt.setString(5, getDateValue(loanB.getDateOut(), date_to_dtbs));
			prepStmnt.setString(6, getDateValue(loanB.getDueDate(), date_to_dtbs));
			
			prepStmnt.executeUpdate();
			
			updateBookCopies(conn, loanB.getBookId(), loanB.getBranchId(), copies);
			
			conn.commit();
			
		}catch(Exception e){
			log.error(e.getMessage());
			conn.rollback();
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return loanB.getLoanId();
			
	}
		
	
	private int getNextLoanId(Connection conn){
		log = log.getLogger("CheckoutDAO : getNextLoanId()");
		int loanId = -1;
		String seq_name = "book_loans";
		
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			String query = "Select value " +
							"from sequence " +
							"where seq_name = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, seq_name);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				loanId = rs.getInt(1);
				loanId++;
				break;
			}
			
			query = "Update sequence set value = ? where seq_name = ?";
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, loanId);
			prepStmnt.setString(2, seq_name);
			
			prepStmnt.executeUpdate();
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return loanId;
	}
	
	
	
	private void getDates(BookLoansBean loanB){
		log = log.getLogger("CheckoutDAO : getDates()");
		
		try{
			loanB.setDateOut(getCurrentDate());
			loanB.setDueDate(dateAdd(loanB.getDateOut(), checkoutDays));
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	
	
	
	private void updateBookCopies(Connection conn, String bookId, int branchId, int copies){
		log = log.getLogger("CheckoutDAO : updateBookCopies()");
		PreparedStatement prepStmnt = null;
		
		try{
			copies --;
			String query = "update book_copies set copies_available = ? where book_id = ? and branch_id = ?";
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, copies);
			prepStmnt.setString(2, bookId);
			prepStmnt.setInt(3, branchId);
			
			prepStmnt.executeUpdate();
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	
	
	
}
