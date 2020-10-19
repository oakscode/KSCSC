package com.paddy.OPCT.exceptions;

public class InvalidLoginException extends Exception {
	
	String str1;

	InvalidLoginException(String str2) {
		str1=str2;
	   }
	   public String toString(){ 
		return ("Usernsme not available: "+str1) ;
	   }

}
