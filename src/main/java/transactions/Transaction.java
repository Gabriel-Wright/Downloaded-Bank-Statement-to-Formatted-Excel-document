package transactions;

import java.util.Objects;

public class Transaction {

	// variables
	private String iD; // unique ID of transaction
	private String date; // do I need this to be an Int and use tmp converter?
	private String trType; // transaction Type
	private String rawDescription; // raw description
	private String processedDescription; // processedDescription
	private String category; // categories -i.e. type of product or payement, e.g. paycheque, fashion
	private double paidIn;
	private double paidOut;
	private double balance; // balance at the time

	// getters and setters
	public String getID() {
		return iD;
	}

	public void setID(String iD) {
		this.iD = iD;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTrType() {
		return trType;
	}

	public void setTrType(String trType) {
		this.trType = trType;
	}

	public String getRawDescription() {
		return rawDescription;
	}

	public void setRawDescription(String rawDescription) {
		this.rawDescription = rawDescription;
	}

	public String getProcessedDescription() {
		return processedDescription;
	}

	public void setProcessedDescription(String processedDescription) {
		this.processedDescription = processedDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPaidIn() {
		return paidIn;
	}

	public void setPaidIn(double paidIn) {
		this.paidIn = paidIn;
	}

	public double getPaidOut() {
		return paidOut;
	}

	public void setPaidOut(double paidOut) {
		this.paidOut = paidOut;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	/* 
	 * ======================= METHODS ==============
	 */
	
	@Override
	public String toString() {
	    return "Transaction{" +
	            "iD='" + iD + '\'' +
	            ", date='" + date + '\'' +
	            ", trType='" + trType + '\'' +
	            ", rawDescription='" + rawDescription + '\'' +
	            ", processedDescription='" + processedDescription + '\'' +
	            ", category='" + category + '\'' +
	            ", paidIn=" + paidIn +
	            ", paidOut=" + paidOut +
	            ", balance=" + balance +
	            '}';
	}
	
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Transaction other = (Transaction) obj;
	    return Objects.equals(this.iD, other.iD) &&
	           Objects.equals(this.date, other.date) &&
	           Objects.equals(this.trType, other.trType) &&
	           Objects.equals(this.rawDescription, other.rawDescription) &&
	           Objects.equals(this.processedDescription, other.processedDescription) &&
	           Objects.equals(this.category, other.category) &&
	           Double.compare(this.paidIn, other.paidIn) == 0 &&
	           Double.compare(this.paidOut, other.paidOut) == 0 &&
	           Double.compare(this.balance, other.balance) == 0;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(iD, date, trType, rawDescription, processedDescription, category, paidIn, paidOut, balance);
	}


}



