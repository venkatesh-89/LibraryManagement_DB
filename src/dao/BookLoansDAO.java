package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import bean.BookLoansBean;
import bean.BorrowerBean;


public class BookLoansDAO extends GenericDAO{
	Logger log = null;
	
	public static double finePerDay = 0.25;
	
	public ArrayList<BookLoansBean> getSearchLoanList(String bookId, String cardNo) throws Exception
	{
		log = log.getLogger("BookLoansDAO : getSearchLoanList()");
		ArrayList<BookLoansBean> arrSearchList = new ArrayList<BookLoansBean>();
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		int bCardNo;
		String[] bName;
		
		try
		{
			bCardNo = cardNo == "" ? 0 : Integer.parseInt(cardNo);
			
			conn = getConnection();
			
			String query = "Select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in " +
							"from book_loans " +
							"where (book_id like ? or ? = '') " +
							"and (card_no = ? or ? = 0)";
			
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, "%" + bookId + "%");
			prepStmnt.setString(2, bookId);
			prepStmnt.setInt(3, bCardNo);
			prepStmnt.setInt(4, bCardNo);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				BookLoansBean loanB = new BookLoansBean();
				
				loanB.setLoanId(rs.getInt(1));
				loanB.setBookId(rs.getString(2));
				loanB.setBranchId(rs.getInt(3));
				loanB.setCardNo(rs.getInt(4));
				loanB.setDateOut(getDateValue(rs.getString(5), dtbs_to_date));
				loanB.setDueDate(getDateValue(rs.getString(6), dtbs_to_date));
				String date_in = rs.getString(7);
				if (date_in == "" || date_in == null){
					loanB.setDateIn(date_in);
				}
				else{
					loanB.setDateIn(getDateValue(date_in,dtbs_to_date));
				}
				
				arrSearchList.add(loanB);
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
	
		return arrSearchList;
	
	}
	
	
	public BookLoansBean getLoanDetails(BookLoansBean loanB) throws Exception{
		log = log.getLogger("BookLoansDAO : getLoanDetails()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			
			String query = "Select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in " +
							"from book_loans " +
							"where loan_id = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, loanB.getLoanId());
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				loanB.setBookId(rs.getString(2));
				loanB.setBranchId(rs.getInt(3));
				loanB.setCardNo(rs.getInt(4));
				loanB.setDateOut(getDateValue(rs.getString(5),dtbs_to_date));
				loanB.setDueDate(getDateValue(rs.getString(6),dtbs_to_date));
				String date_in = rs.getString(7);
				if (date_in == "" || date_in == null){
					loanB.setDateIn(date_in);
				}
				else{
					loanB.setDateIn(getDateValue(date_in,dtbs_to_date));
				}
				
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return loanB;
	}
	
	
	public void calculateFines() throws Exception{
		log = log.getLogger("BookLoansDAO : calculateFines()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		PreparedStatement prepStmnt2 = null;
		PreparedStatement prepStmnt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try{
			conn = getConnection();
			
			String query = "Select loan_id, book_id, branch_id, card_no, date_out, due_date, date_in " +
							"from book_loans " + 
							"where date_in is NULL " + 
							"or date_in > due_date";
			
			prepStmnt = conn.prepareStatement(query);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				int loanId = rs.getInt(1);
				long diff = 0;
				String query2 = "Select loan_id, fine_amt, paid from fines where loan_id = ?";
				
				prepStmnt2 = conn.prepareStatement(query2);
				prepStmnt2.setInt(1, loanId);
				
				rs2 = prepStmnt2.executeQuery();

				Date today = new Date();
				DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dueDate = dtFormat.parse(rs.getString(6));
				
				if (rs.getString(7) == null){
					diff = dateDiff(today, dueDate);
				}
				else{
					Date dateIn = dtFormat.parse(rs.getString(7));
					diff = dateDiff(dateIn, dueDate);
				}
				if (diff > 0){
					try{
						if (rs2.next()){
							if(rs2.getInt(1) > 0){
								//System.out.println(rs2.getInt(3));
								if (rs2.getInt(3) == 1){
									continue;
								}
								//System.out.println(rs2.getInt(3));
								
								String query3 = "Update fines set fine_amt = ? where loan_id = ?";
								prepStmnt3 = conn.prepareStatement(query3);
								
								prepStmnt3.setDouble(1, diff * finePerDay);
								prepStmnt3.setInt(2, loanId);
								
								prepStmnt3.executeUpdate();
							}
						}
						else{
							String query3 = "Insert into fines (loan_id, fine_amt, paid) values (?, ?, 0)";
							prepStmnt3 = conn.prepareStatement(query3);
							
							prepStmnt3.setInt(1, loanId);
							prepStmnt3.setDouble(2, diff * finePerDay);
							
							prepStmnt3.executeUpdate();
						}
					}catch(Exception e){
						log.error(e.getMessage());
						conn.rollback();
					}
					conn.commit();
				}
				
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
			conn.rollback();
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
	}
	
	
	public double viewFineDeatils(int cardNo) throws Exception{
		log = log.getLogger("BookLoansDAO : viewFineDeatils()");
		double fineAmt = 0;
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Select SUM(fine_amt) " +
							"from book_loans, fines " +
							"where book_loans.loan_id = fines.loan_id " +
							"and fines.paid != 1 " +
							"and book_loans.card_no = ? " +
							"GROUP BY book_loans.card_no;";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				fineAmt = rs.getDouble(1);
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return fineAmt;
		
	}
	
	
	public int getRemainingBookCount(int cardNo){
		log = log.getLogger("BookLoansDAO : viewFineDeatils()");
		int count = 0;
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			
			String query = "select count(*) from book_loans where date_in is Null and card_no = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt(1);
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return count;
	}
	
	
	public boolean payFine(int cardNo) throws Exception{
		log = log.getLogger("BookLoansDAO : payFine()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		boolean stat = true;
		
		try{
			conn = getConnection();
			
			String query = "update fines, book_loans set paid = 1 where fines.loan_id = book_loans.loan_id and card_no = ?;";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			
			prepStmnt.executeUpdate();
			conn.commit();
			
		}catch(Exception e){
			log.error(e.getMessage());
			stat = false;
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return stat;
	}
	
	
	public double getPaidFineAmt(int cardNo) throws Exception{
		log = log.getLogger("BookLoansDAO : getPaidFineAmt()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		double paidFineAmt = 0;
		
		try{
			conn = getConnection();
			
			String query = "Select SUM(fine_amt) " +
							"from book_loans, fines " +
							"where book_loans.loan_id = fines.loan_id " +
							"and fines.paid = 1 " +
							"and book_loans.card_no = ? " +
							"GROUP BY book_loans.card_no;";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			
			rs = prepStmnt.executeQuery();
			
			while(rs.next()){
				paidFineAmt = rs.getDouble(1);
				break;
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return paidFineAmt;
	}

}