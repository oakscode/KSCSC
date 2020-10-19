package com.supplyco.jsonqr.dao;

import java.sql.SQLException;
import java.util.List;

import com.supplyco.jsonqr.model.Dippo;
import com.supplyco.jsonqr.model.Login;


public interface AdminDao {
	
	public Login login(Login login) throws SQLException;


}
