package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import bean.*;

public class BorrowerDAO extends GenericDAO{
	Logger log = null;
	
	public String addNewBorrower(BorrowerBean borrowerB) throws Exception{
		log = log.getLogger("BorrowerDAO : addNewBorrower()");
		String msg = "";
		int card_no = 0;
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		PreparedStatement prepStmnt2 = null;
		ResultSet rs = null;
		
		try{
			
			conn = getConnection();
			
			String query = "Select card_no from borrower " +
							"where fname = ? and " +
							"lname = ? and " + 
							"email = ? and " +
							"address = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, borrowerB.getfName());
			prepStmnt.setString(2, borrowerB.getlName());
			prepStmnt.setString(3, borrowerB.getEmailId());
			prepStmnt.setString(4, borrowerB.getAddress());
			
			rs = prepStmnt.executeQuery();
			
			if (rs.next()){
				if(rs.getInt(1) > 0){
					//send error to front end
					msg = "Error - Borrower already exists with Card No : " + rs.getInt(1) ;
					return msg;
				}
			}
			rs.close();
			
			query = "Select max(card_no) from borrower";
			
			rs = prepStmnt.executeQuery(query);
			if (rs.next()){
				card_no = rs.getInt(1) + 1;
			}
			System.out.println(card_no);
			query = "Insert into borrower (card_no, fname, lname, email, address, phone) values (?,?,?,?,?,?)";
			
			prepStmnt2 = conn.prepareStatement(query);
			prepStmnt2.setInt(1, card_no);
			prepStmnt2.setString(2, borrowerB.getfName());
			prepStmnt2.setString(3, borrowerB.getlName());
			prepStmnt2.setString(4, borrowerB.getEmailId());
			prepStmnt2.setString(5, borrowerB.getAddress());
			prepStmnt2.setString(6, borrowerB.getPhone());
			
			prepStmnt2.executeUpdate();
			conn.commit();
			
		}
		catch(Exception ex){
			log.error(ex);
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		msg = "Borrower created successfully with Card No : " + card_no;
		return msg;
		
	}

	
	public boolean viewBorrowerDetails(BorrowerBean borrowerB) throws Exception{
		log = log.getLogger("BorrowerDAO : viewBorrowerDetails()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		boolean status = false;
		
		try{
			conn = getConnection();
			String query = "Select card_no, fName, lName, email, address, phone " +
							"from borrower where card_no = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, borrowerB.getCardNo());
			rs = prepStmnt.executeQuery();
			
			while (rs.next()) {
				status = true;
				borrowerB.setfName(rs.getString(2));
				borrowerB.setlName(rs.getString(3));
				borrowerB.setEmailId(rs.getString(4));
				borrowerB.setAddress(rs.getString(5));
				borrowerB.setPhone(rs.getString(6));
				break;
			}
			
		}
		catch(Exception e){
			log.error(e);
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return status;
	}
	
	
	public String updateBorrower(BorrowerBean borrowerB) throws Exception{
		log = log.getLogger("BorrowerDAO : updateBorrower()");
		String msg = "Update successful";
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Update borrower set fName = ?, lName = ?, email = ?, address = ?, phone = ? " +
							"where card_no = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, borrowerB.getfName());
			prepStmnt.setString(2, borrowerB.getlName());
			prepStmnt.setString(3, borrowerB.getEmailId());
			prepStmnt.setString(4, borrowerB.getAddress());
			prepStmnt.setString(5, borrowerB.getPhone());
			prepStmnt.setInt(6, borrowerB.getCardNo());
			
			prepStmnt.executeUpdate();
			conn.commit();
		}
		catch(Exception e){
			msg = e.getMessage();
			log.error(e);
		}
		
		return msg;
	}
	
	
	public String deleteBorrower(int cardNo) throws Exception{
		log = log.getLogger("BorrowerDAO : deleteBorrower()");
		String msg = "Delete Successful";
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Delete from borrower where card_no = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, cardNo);
			prepStmnt.executeUpdate();
			conn.commit();
			
		}
		catch (Exception e){
			msg = e.getMessage();
			log.error(e);
		}
		
		return msg;
	}
	
	public ArrayList<BorrowerBean> viewAllBorrower() throws Exception
	{
		log = log.getLogger("BorrowerDAO : viewAllBorrower()");
		ArrayList<BorrowerBean> arrBorrowerList = null;
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			String query = "Select card_no, fName, lName, email, address, phone " +
							"from borrower order by card_no asc";
			
			prepStmnt = conn.prepareStatement(query);
			
			rs = prepStmnt.executeQuery();
			arrBorrowerList = new ArrayList<BorrowerBean>();
			BorrowerBean borrowerB = null;
			while(rs.next())
			{
				borrowerB = new BorrowerBean();
				borrowerB.setCardNo(rs.getInt(1));
				borrowerB.setfName(rs.getString(2));
				borrowerB.setlName(rs.getString(3));
				borrowerB.setEmailId(rs.getString(4));
				borrowerB.setAddress(rs.getString(5));
				borrowerB.setPhone(rs.getString(6));
				
				arrBorrowerList.add(borrowerB);
			}
			
		}
		catch(Exception e)
		{
			log.error(e);
		}
		finally
		{
			closeConnection(rs, prepStmnt, conn);
		}
		return arrBorrowerList;
	}
}
