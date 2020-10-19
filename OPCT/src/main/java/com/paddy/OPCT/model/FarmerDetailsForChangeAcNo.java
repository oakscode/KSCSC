package com.paddy.OPCT.model;

public class FarmerDetailsForChangeAcNo {
	
	String Accno;
	String Farmername;
	String PaymentOrderNumber;
	String PaymentArrOrderNumber;
	String Prsno;
	String bankname;
	String bankplace;
	
	

	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankplace() {
		return bankplace;
	}
	public void setBankplace(String bankplace) {
		this.bankplace = bankplace;
	}
	String farmerid;
	public String getAccno() {
		return Accno;
	}
	public void setAccno(String accno) {
		Accno = accno;
	}
	public String getFarmername() {
		return Farmername;
	}
	public void setFarmername(String farmername) {
		Farmername = farmername;
	}
	public String getPaymentOrderNumber() {
		return PaymentOrderNumber;
	}
	public void setPaymentOrderNumber(String paymentOrderNumber) {
		PaymentOrderNumber = paymentOrderNumber;
	}
	public String getPaymentArrOrderNumber() {
		return PaymentArrOrderNumber;
	}
	public void setPaymentArrOrderNumber(String paymentArrOrderNumber) {
		PaymentArrOrderNumber = paymentArrOrderNumber;
	}
	public String getPrsno() {
		return Prsno;
	}
	public void setPrsno(String prsno) {
		Prsno = prsno;
	}

	public String getFarmerid() {
		return farmerid;
	}
	public void setFarmerid(String farmerid) {
		this.farmerid = farmerid;
	}
	
	
		
}
