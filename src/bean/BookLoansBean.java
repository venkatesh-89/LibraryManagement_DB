package bean;

public class BookLoansBean {

	
	private int loanId;
	private String bookId;
	private int branchId;
	private int cardNo;
	private String dateOut;
	private String dueDate;
	private String dateIn;
	
	public BookLoansBean(){};
	
	public BookLoansBean(int loanId){
		this.loanId = loanId;
	}
	
	
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public String getDateOut() {
		return dateOut;
	}
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDateIn() {
		return dateIn;
	}
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}

	public String toString(){
		String str = this.loanId + ", " +
					this.bookId + ", " +
					this.branchId + ", " +
					this.cardNo + ", " +
					this.dateOut + ", " +
					this.dueDate + ", " +
					this.dateIn;
		
		return str;
	}
}
