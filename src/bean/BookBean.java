package bean;

public class BookBean 
{
	private String bookId;
	private String bookTitle;
	private AuthorBean authorBean;
	
	
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
	public AuthorBean getAuthorBean() {
		return authorBean;
	}
	public void setAuthorBean(AuthorBean authorBean) {
		this.authorBean = authorBean;
	}
	
	
}
