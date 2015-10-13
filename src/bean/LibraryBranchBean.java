package bean;

public class LibraryBranchBean {
	
	private int branchId;
	private String branchName;
	private String address;
	
	public LibraryBranchBean(){}
	
	public LibraryBranchBean(int branchId){
		this.branchId = branchId;
	}
	
	public int getBranchID() {
		return branchId;
	}
	public void setBranchID(int branchID) {
		this.branchId = branchID;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
