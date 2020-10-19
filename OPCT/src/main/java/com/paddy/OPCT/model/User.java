package com.paddy.OPCT.model;

public class User {
	
	
	private int id_user;
	private String title;
	private String empcode;
	private int id_usertype;
	private String usertype;
	
	
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public int getId_usertype() {
		return id_usertype;
	}
	public void setId_usertype(int id_usertype) {
		this.id_usertype = id_usertype;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}


	
	
	

}
