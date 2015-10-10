package bean;

public class BookBean 
{
	private String bookId;
	private String bookTitle;
	private AuthorBean Authors;
	
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public AuthorBean getAuthors() {
		return Authors;
	}
	public void setAuthors(AuthorBean authors) {
		Authors = authors;
	}
	
}
