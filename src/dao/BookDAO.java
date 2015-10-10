/**
 * @author Venkatesh
 * Date : 04 October, 2015
 * BookDAO consists of all methods to create, view, update and delete the employee details.
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import bean.*;

public class BookDAO extends GenericDAO
{
	Logger log = null;	
	
	
	public void insertBookAuthors(Connection conn, String[] tokens) throws Exception
	{
		log = log.getLogger("BookDAO : getSearchBookList()");
		
		PreparedStatement prepStmnt = null;
		PreparedStatement prepStmnt2 = null;
		ResultSet rs = null;
		
		try{
			String query = "Insert into book (book_id, title) values(?,?)";
			String query2 = "Insert into book_authors (book_id, author_name) values(?,?)";
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, tokens[0]);
			prepStmnt.setString(2, tokens[1]);
			prepStmnt.executeUpdate();
			//conn.commit();
			
			prepStmnt2 = conn.prepareStatement(query2);
			prepStmnt2.setString(1, tokens[0]);
			prepStmnt2.setString(2, tokens[2]);
			
			prepStmnt2.executeUpdate();
			conn.commit();
		}
		catch(Exception ex){
			log.error(ex);
		}
	}
	
	/**
	 * Method to search book by title, book_id, author(s)
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BookBean> getSearchBookList(String searchText, String searchCriteria) throws Exception
	{
		log = log.getLogger("BookDAO : getSearchBookList()");
		ArrayList<BookBean> arrSearchList = new ArrayList<BookBean>();
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		ResultSet rs = null;
		ResultSet rs_author = null;
		try
		{
			conn = getConnection();
			
			if (searchCriteria.equals("bookId")){
				String query = "Select " +
							   "book_id, title " +
							   "from book " +
							   "where book_id like '%" + searchText + "%';";
				
				prepStmnt = conn.prepareStatement(query);
				
				rs = prepStmnt.executeQuery();
				BookBean bookB = null;
				AuthorBean authorB = null;
				while(rs.next())
				{
					bookB = new BookBean();
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					
					query = "Select book_id, author_name " + 
							"from book_authors " +
							"where book_id = '" +
							bookB.getBookId() + "';";
					
					prepStmnt = conn.prepareStatement(query);
					rs_author = prepStmnt.executeQuery();
					
					authorB = new AuthorBean(bookB.getBookId());
					
					while(rs_author.next()){
						authorB.getAuthors().add(rs.getString(2));
					}
					
					bookB.setAuthors(authorB);
					
					arrSearchList.add(bookB);
				}
			}
			else if (searchCriteria.equals("bookTitle")){
				String query = "Select " +
							   "book_id, title " +
							   "from book " +
							   "where title like '%" + searchText + "%';";
			
				prepStmnt = conn.prepareStatement(query);
				
				rs = prepStmnt.executeQuery();
				BookBean bookB = null;
				while(rs.next())
				{
					bookB = new BookBean();
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					
					query = "Select book_id, author_name " + 
							"from book_authors " +
							"where book_id = '" +
							bookB.getBookId() + "';";
					
					prepStmnt = conn.prepareStatement(query);
					rs_author = prepStmnt.executeQuery();
					
					ArrayList<String> authorList = new ArrayList<String>();
					
					while(rs_author.next()){
						authorList.add(rs.getString(2));
					}
					
					bookB.setAuthors(authorList);
					
					arrSearchList.add(bookB);
				}
			}
			else if (searchCriteria.equals("authorName")){
				String query = "Select " +
							   "book_id, author_name " +
							   "from book_authors " +
							   "where author_name like '%" + searchText + "%';";
			
				prepStmnt = conn.prepareStatement(query);
				
				rs = prepStmnt.executeQuery();
				BookBean bookB = null;
				while(rs.next())
				{
					bookB = new BookBean();
					bookB.setBookId(rs.getString(1));
					//bookB.setBookTitle(rs.getString(2));
					
					query = "Select book_id, title " + 
							"from book " +
							"where book_id = '" +
							bookB.getBookId() + "';";
					
					prepStmnt = conn.prepareStatement(query);
					rs_author = prepStmnt.executeQuery();
					
					ArrayList<String> authorList = new ArrayList<String>();
					
					while(rs_author.next()){
						authorList.add(rs.getString(2));
					}
					
					bookB.setAuthors(authorList);
					
					arrSearchList.add(bookB);
				}
			}
			else if (searchCriteria.equals("anyCriteria")){
				String query = "Select " +
						"book.book_id, title, TRIM(GROUP_CONCAT(author_name SEPARATOR ', ')) " +
						"from book, book_authors " +
						"where book.book_id = book_authors.book_id " +
						"AND (UPPER(book.book_id) LIKE UPPER('%" + searchText + "%') " +
						"OR LOWER(title) LIKE LOWER('%" + searchText + "%') " +
						"OR (book.book_id IN (SELECT book_authors.book_id " +
						"from book_authors " + 
						"where author_name LIKE '%" + searchText + "%'))) " +
						"GROUP BY book.book_id , title;";
				
				prepStmnt = conn.prepareStatement(query);
			
				rs = prepStmnt.executeQuery();
				BookBean bookB = null;
				while(rs.next())
				{
					bookB = new BookBean();
					ArrayList<String> authorList = new ArrayList<String>();
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					String Authors = rs.getString(3);
					if (Authors.contains(", ")){
						String[] temp = Authors.split(", ");
						for (String author : temp){
							authorList.add(author);
						}
					}
					else{
						authorList.add(Authors);
					}
					bookB.setAuthors(authorList);
					
					arrSearchList.add(bookB);
				}
			}
		}
		catch(Exception e)
		{
			log.error(e);
		}
		return arrSearchList;
	}

	public Connection getConn()
	{
		Connection conn = null;
		
		conn = getConnection();
		
		return conn;
	}
	
}
