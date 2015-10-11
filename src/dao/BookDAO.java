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
	
	public Boolean createNewBook(String bookId, String bookTitle, String authors) throws Exception
	{
		log = log.getLogger("BookDAO : createNewBook()");
		
		Connection conn = null;
		PreparedStatement prepStmnt = null;
		
		try{
			conn = getConnection();
			String query = "Insert into book (book_id, title) values(?,?)";
			
			prepStmnt = conn.prepareStatement(query);
			prepStmnt.setString(1, bookId);
			prepStmnt.setString(2, bookTitle);
			try{
				//prepStmnt.executeUpdate();
				conn.commit();
			}
			catch(Exception ex){
				//Send to front end
				return false;
			}
			
			String query2  = "Insert into book_authors (book_id, author_name) values ";
			
			if (authors.contains(",")){
				String[] temp = authors.split(",");
				for (String author : temp){
					query2 += "(\"" + bookId +"\", \"" + author.trim() + "\"),";
				}
				if (query2.endsWith(",")){
					query2 = query2.substring(0, query2.length() - 1);
				}
			}
			else{
				query2 += "(\"" + bookId +"\", \"" + authors.trim() + "\")";
			}
			
			System.out.println(query2);
			//prepStmnt2 = conn.prepareStatement(query2);
			//prepStmnt2.executeUpdate();
			//conn.commit();
		}
		catch(Exception ex){
			log.error(ex);
		}
		return true;
	}
	
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
					
					authorB = new AuthorBean(bookB.getBookId());
					
					bookB.setAuthorBean(getAuthorsFromDb(conn, authorB));
					
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
				AuthorBean authorB = null;
				
				while(rs.next())
				{
					bookB = new BookBean();
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					
					authorB = new AuthorBean(bookB.getBookId());
					
					bookB.setAuthorBean(getAuthorsFromDb(conn, authorB));
					
					arrSearchList.add(bookB);
				}
			}
			else if (searchCriteria.equals("authorName")){
				String query = "Select " +
							   "book_id, title " +
							   "from book " + 
							   "where book_id in (Select book_id from book_authors " +
							   "where author_name like '%" + searchText + "%')";
			
				prepStmnt = conn.prepareStatement(query);
				
				rs = prepStmnt.executeQuery();
				log.info(query);
				BookBean bookB = null;
				AuthorBean authorB = null;
				
				while(rs.next())
				{
					bookB = new BookBean();
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					
					authorB = new AuthorBean(bookB.getBookId());
					
					bookB.setAuthorBean(getAuthorsFromDb(conn, authorB));
					
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
				AuthorBean authorB = null;
				
				while(rs.next())
				{
					bookB = new BookBean();
					
					bookB.setBookId(rs.getString(1));
					bookB.setBookTitle(rs.getString(2));
					String Authors = rs.getString(3);
					
					authorB = new AuthorBean(bookB.getBookId());
					
					if (Authors.contains(", ")){
						String[] temp = Authors.split(", ");
						for (String author : temp){
							authorB.getAuthorList().add(author);
						}
					}
					else{
						authorB.getAuthorList().add(Authors);
					}
					bookB.setAuthorBean(authorB);
					
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

	private AuthorBean getAuthorsFromDb(Connection conn, AuthorBean authorB) throws Exception{
		
		PreparedStatement prepStmnt = null;
		ResultSet rs_author = null;
		
		String query = "Select book_id, author_name " + 
						"from book_authors " +
						"where book_id = '" +
						authorB.getBookId() + "';";
		
		prepStmnt = conn.prepareStatement(query);
		rs_author = prepStmnt.executeQuery();
		
		while(rs_author.next()){
			authorB.getAuthorList().add(rs_author.getString(2));
		}
		
		return authorB;
	}
	
}
