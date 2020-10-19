package com.supplyco.jsonqr.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.supplyco.jsonqr.dao.AdminDao;
import com.supplyco.jsonqr.model.Dippo;
import com.supplyco.jsonqr.model.Login;

public class Admin_Dao_impl implements AdminDao{
	
	private JdbcTemplate jdbcTemplate;

	public Admin_Dao_impl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Login login(Login login) throws SQLException{

		String sql = "select userid,password,deppo from login where userid ='"+login.getUserid()+"' and password='"+login.getPassword()+"' and deppo ='"+login.getDeppo()+"'  " ;

		 Login u = jdbcTemplate.queryForObject(sql, new RowMapper<Login>() {
			public Login mapRow(ResultSet rs, int row) throws SQLException {

				Login l = new Login();
				
				l.setUserid(rs.getString("userid"));
				l.setPassword(rs.getString("password"));
				l.setDeppo(rs.getString("deppo"));

				return l;
			}
		});
		return login;

	}




}