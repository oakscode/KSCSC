package com.paddy.OPCT.dao;

import java.util.List;

import com.paddy.OPCT.model.FarmerDetailsForChangeAcNo;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.User;

public interface Dao_Paddy {
	
	//insert queries
	public int insert_user(User u,Login l);
	
	//object queries
	public FarmerDetailsForChangeAcNo FarmerDetailsByFarmerId(String rego);

	
	//List queries
	public List<FarmerDetailsForChangeAcNo> PRSReceiptDetailsByFarmerId(String farmerID);
	
	//update queries
	public int[] updateFarmerAccno(int Iduser,String accno,String regno);

}
