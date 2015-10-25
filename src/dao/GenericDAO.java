	/************************************************************************************************
	* Library Management               																*
	* GenericDAO.java																				*
	* Purpose : Defining the methods for getting and closing a connection with Database 			*
	* Date : 10/4/2015 10:03 AM																		*
	* @authors: Venkatesh N 																		*
	* 		  																						*
	* @version 1.0.0.0 																				*
	* @param conn : Connection 																		*
	* @param rs : ResultSet 																		*
	* @param prepStmnt : PreparedStatement 															*
	* @param dburl : String 																		*
	* @param dbUserName : String 																	*
	* @param dbPassword : String 																	*
	*************************************************************************************************/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class GenericDAO 
{
	//Defining the logger
	Logger log =null;
	
	//Defining public to_char and to_date integers for use
	public final static int date_to_dtbs = 1;
	public final static int dtbs_to_date = 2;
	
	//Defining public date formats
	public final static String dateToDtbs = "MM-dd-yyyy";
	public final static String dtbsToDate = "yyyy-MM-dd";
	
	//Defining checkout due time in days
	public final static int checkoutDays = 14;
	
	//Method to get connection from Database using jdbc
	public Connection getConnection()
	{
		log = log.getLogger(GenericDAO.class);
		Connection conn = null;
		try
		{
			String dburl = "jdbc:mysql://localhost:3306/library2";
			String dbUserName = "root";
			String dbPassword = "";
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dburl,dbUserName,dbPassword);
			conn.setAutoCommit(false);
			
		}
		catch (ClassNotFoundException cnfe)
		{
			log.error(cnfe);
		}
		catch (SQLException sqle)
		{
			log.error(sqle);
		}
		return conn;
	}
	
	//Method for closing connection from database.
	public void closeConnection(ResultSet rs,PreparedStatement statement,Connection conn) throws Exception
	{
		log = log.getLogger(GenericDAO.class);
		try
		{
			if(rs!=null)
				rs.close();
			if(statement!=null)
				statement.close();
			if(conn!=null)
				conn.close();
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}
	
	public String getDateValue(String dateStr, int dateType) throws Exception
	{
		String result = "";
		Date dt = null;
		DateFormat dateToDtbs = new SimpleDateFormat("MM-dd-yyyy");
		DateFormat dtbsToDate = new SimpleDateFormat("yyyy-MM-dd");
		
		try
		{
			switch(dateType)
			{
			case date_to_dtbs:
				dt = dateToDtbs.parse(dateStr);
				result = dtbsToDate.format(dt);
				break;
			case dtbs_to_date:
				dt = dtbsToDate.parse(dateStr);
				result = dateToDtbs.format(dt);
				break;
			}
			
		}catch (Exception ex)
		{
			log.error(ex);
		}
		
		return result;
	}
	
	public String getCurrentDate() throws Exception{
		DateFormat dtFormat = new SimpleDateFormat(dateToDtbs);
		return dtFormat.format(new Date());
	}
	
	public long dateDiff(Date dateOut, Date dateIn) throws Exception{
		long diff = -1;
		
		diff = dateOut.getTime() - dateIn.getTime();
		
		return (diff/(1000*60*60*24));
	}
	
	public String dateAdd(String dateIn, int days){
		DateFormat dtFormat = new SimpleDateFormat(dateToDtbs);
		Calendar cal = Calendar.getInstance();;
		Date dateNew = null;
		try{
			cal.setTime(dtFormat.parse(dateIn));
			cal.add(Calendar.DATE, days);
			dateNew = cal.getTime();
		}catch(Exception e){
			log.error(e);
		}
		return dtFormat.format(dateNew);
	}
}
