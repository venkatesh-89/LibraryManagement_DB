package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import bean.*;

public class BranchDAO extends GenericDAO{
	Logger log = null;
	
	
	public String addNewBranch(LibraryBranchBean branchB) throws Exception{
		log = log.getLogger("BranchDAO : addNewBranch()");
		String msg = "";
		int branchId = 0;
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		PreparedStatement prepStmnt2 = null;
		ResultSet rs = null;
		
		try{
			
			conn = getConnection();
			
			String query = "Select branch_id from library_branch " +
							"where branch_name = ? and " +
							"address = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, branchB.getBranchName());
			prepStmnt.setString(2, branchB.getAddress());
			
			rs = prepStmnt.executeQuery();
			
			if (rs.next()){
				if(rs.getInt(1) > 0){
					//send error to front end
					msg = "Error - Branch already exists with Branch Id : " + rs.getInt(1) ;
					return msg;
				}
			}
			rs.close();
			
			query = "Select max(branch_id) from library_branch";
			
			rs = prepStmnt.executeQuery(query);
			if (rs.next()){
				branchId = rs.getInt(1) + 1;
			}
			System.out.println(branchId);
			query = "Insert into library_branch (branch_id, branch_name, address) values (?,?,?)";
			
			prepStmnt2 = conn.prepareStatement(query);
			prepStmnt2.setInt(1, branchId);
			prepStmnt2.setString(2, branchB.getBranchName());
			prepStmnt2.setString(3, branchB.getAddress());
			
			prepStmnt2.executeUpdate();
			conn.commit();
			
		}
		catch(Exception ex){
			log.error(ex);
		}
		finally{
			closeConnection(rs, prepStmnt, conn);
		}
		msg = "Branch created successfully with Branch Id : " + branchId;
		return msg;
		
	}
	

	
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
