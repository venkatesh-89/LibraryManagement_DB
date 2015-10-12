package bean;

import java.util.ArrayList;

public class AuthorBean {

	private String bookId;
	private ArrayList<String> authorList;
	
	public AuthorBean(){
		//default Constructor
	};
	
	public AuthorBean(String book_id){
		this.bookId = book_id;
		authorList = new ArrayList<String>();
	}
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String book_id) {
		this.bookId = book_id;
	}
	public ArrayList<String> getAuthorList() {
		return authorList;
	}
	public void setAuthorList(ArrayList<String> authorList) {
		this.authorList = authorList;
	}
	
	@Override
	public String toString(){
		String out = "";
		for(String author : authorList){
			out += author + ", ";
		}
		out = out.substring(0, out.length() - 2);
		return out;
	}
}
