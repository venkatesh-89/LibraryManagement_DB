package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import bean.*;

public class BorrowerDAO extends GenericDAO{
	Logger log = null;
	
	public int addNewBorrower(BorrowerBean borrowerB) throws Exception{
		log = log.getLogger("BorrowerDAO : addNewBorrower()");
		int card_no = 0;
		
		try{
			Connection conn = null;
			PreparedStatement prepStmnt = null;
			ResultSet rs = null;
			
			conn = getConnection();
			
			String query = "Select card_no from borrower " +
							"where fname = ? and " +
							"lname = ? and " + 
							"email = ? and " +
							"address = ? and " +
							"city = ? and " +
							"state = ?";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, borrowerB.getfName());
			prepStmnt.setString(2, borrowerB.getlName());
			prepStmnt.setString(3, borrowerB.getEmailId());
			prepStmnt.setString(4, borrowerB.getAddress());
			prepStmnt.setString(5, borrowerB.getCity());
			prepStmnt.setString(6, borrowerB.getState());
			rs = prepStmnt.executeQuery();
			
			if (rs.getInt(1) > 0){ 
				//send error to front end
				return card_no;
			}
			
			query = "Select max(card_no) from borrower";
			
			rs = prepStmnt.executeQuery(query);
			card_no = rs.getInt(1) + 1; 
			
			query = "Insert into borrower (card_no, fname, lname, email, address, city, state, phone) values (?,?,?,?,?,?,?,?)";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setInt(1, card_no);
			prepStmnt.setString(2, borrowerB.getfName());
			prepStmnt.setString(3, borrowerB.getlName());
			prepStmnt.setString(4, borrowerB.getEmailId());
			prepStmnt.setString(5, borrowerB.getAddress());
			prepStmnt.setString(6, borrowerB.getCity());
			prepStmnt.setString(7, borrowerB.getState());
			prepStmnt.setString(8, borrowerB.getPhone());
			
			//prepStmnt.executeUpdate();
			conn.commit();
			
		}
		catch(Exception ex){
			log.error(ex);
		}
		return card_no;
		
	}

}
