package bean;

import java.util.ArrayList;

public class AuthorBean {

	private String book_id;
	private ArrayList<String> Authors;
	
	public AuthorBean(String book_id)
	{
		this.book_id = book_id;
		Authors = new ArrayList<String>();
	}
	
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public ArrayList<String> getAuthors() {
		return Authors;
	}
	public void setAuthors(ArrayList<String> authors) {
		Authors = authors;
	}
	
	
}
