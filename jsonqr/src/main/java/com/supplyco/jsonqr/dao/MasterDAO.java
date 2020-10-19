package com.supplyco.jsonqr.dao;

import java.util.List;

import com.supplyco.jsonqr.model.Dippo;
import com.supplyco.jsonqr.model.MasterEinvoice;
import com.supplyco.jsonqr.model.Soutdetail;
import com.supplyco.jsonqr.model.Southead;

public interface MasterDAO {
	
	public List<Dippo> dippolist();
	public List<Southead> isulist(String dippocode);
	public Southead isuhead(String isuno,String dippocode);
	public List<Soutdetail> isudetail(String isuno,String dippocode);
	public MasterEinvoice initJson(String isuno , String olcode);

}