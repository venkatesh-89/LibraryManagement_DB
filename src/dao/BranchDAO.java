package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import bean.*;

public class BranchDAO extends GenericDAO{
	Logger log = null;
	
	/**
	public String addNewBranch(LibraryBranchBean branchB) throws Exception{
		log = log.getLogger("BranchDAO : addNewBorrower()");
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
							"address = ? and " +
							"city = ? and " +
							"state = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, branchB.getfName());
			prepStmnt.setString(2, branchB.getlName());
			prepStmnt.setString(3, branchB.getEmailId());
			prepStmnt.setString(4, branchB.getAddress());
			prepStmnt.setString(5, branchB.getCity());
			prepStmnt.setString(6, branchB.getState());
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
			query = "Insert into borrower (card_no, fname, lname, email, address, city, state, phone) values (?,?,?,?,?,?,?,?)";
			
			prepStmnt2 = conn.prepareStatement(query);
			prepStmnt2.setInt(1, card_no);
			prepStmnt2.setString(2, branchB.getfName());
			prepStmnt2.setString(3, branchB.getlName());
			prepStmnt2.setString(4, branchB.getEmailId());
			prepStmnt2.setString(5, branchB.getAddress());
			prepStmnt2.setString(6, branchB.getCity());
			prepStmnt2.setString(7, branchB.getState());
			prepStmnt2.setString(8, branchB.getPhone());
			
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
	*/

	
	public LibraryBranchBean viewBranchDetails(LibraryBranchBean branchB) throws Exception{
		log = log.getLogger("BranchDAO : viewBranchDetails()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Select branch_id, branch_name, address " +
							"from library_branch where branch_id = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, branchB.getBranchID());
			rs = prepStmnt.executeQuery();
			
			while (rs.next()) {
				branchB.setBranchName(rs.getString(2));
				branchB.setAddress(rs.getString(3));
				
			}
			
		}
		catch(Exception e){
			log.error(e);
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		
		return branchB;
	}
	
	
	public String updateBranch(LibraryBranchBean branchB) throws Exception{
		log = log.getLogger("BranchDAO : updateBranch()");
		String msg = "Update successful";
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Update library_branch set branch_name = ?, address = ? " +
							"where branch_id = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, branchB.getBranchName());
			prepStmnt.setString(2, branchB.getAddress());
			prepStmnt.setInt(3, branchB.getBranchID());
			
			prepStmnt.executeUpdate();
			conn.commit();
		}
		catch(Exception e){
			msg = e.getMessage();
			log.error(e);
		}
		
		return msg;
	}
	
	
	public String deleteBranch(int branchId) throws Exception{
		log = log.getLogger("BranchDAO : deleteBranch()");
		String msg = "Delete Successful";
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			String query = "Delete from library_branch where branch_id = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, branchId);
			prepStmnt.executeUpdate();
			conn.commit();
			
		}
		catch (Exception e){
			msg = e.getMessage();
			log.error(e);
		}
		
		return msg;
	}
	
	public ArrayList<LibraryBranchBean> viewAllBranch() throws Exception
	{
		log = log.getLogger("BranchDAO : viewAllBranch()");
		ArrayList<LibraryBranchBean> arrBranchList = null;
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			String query = "Select branch_id, branch_name, address " +
							"from library_branch order by branch_id asc";
			
			prepStmnt = conn.prepareStatement(query);
			
			rs = prepStmnt.executeQuery();
			arrBranchList = new ArrayList<LibraryBranchBean>();
			LibraryBranchBean branchB = null;
			while(rs.next())
			{
				branchB = new LibraryBranchBean();
				branchB.setBranchID(rs.getInt(1));
				branchB.setBranchName(rs.getString(2));
				branchB.setAddress(rs.getString(3));
				
				arrBranchList.add(branchB);
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
		return arrBranchList;
	}
}
