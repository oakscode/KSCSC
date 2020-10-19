package com.paddy.OPCT.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.paddy.OPCT.dao.Dao_Paddy;
import com.paddy.OPCT.model.FarmerDetailsForChangeAcNo;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.User;

public class Dao_Paddy_impl implements Dao_Paddy {

	private JdbcTemplate jdbcTemplate;

	public Dao_Paddy_impl(DataSource msSqlDataSource) {

		jdbcTemplate = new JdbcTemplate(msSqlDataSource);
	}

	@Override
	public int insert_user(User u, Login l) {

		String sql = "INSERT INTO users (title, empcode, id_usertype) VALUES (?,?, ?)";
		int i = jdbcTemplate.update(sql, u.getTitle(), u.getEmpcode(), u.getId_usertype());

		return i;
	}

	@Override
	public int[] updateFarmerAccno(int Iduser, String accno, String regno) {

		String q1 = "insert into FarmerCropDetails_old select * from FarmerCropDetails where FarmerRegno='" + regno
				+ "'";
		String q2 = "update FarmerCropDetails set Accno='" + accno + "' where FarmerRegno='" + regno + "'";

		String[] sqlArray = { q1, q2 };

		int[] updateCounts = jdbcTemplate.batchUpdate(sqlArray);

		return updateCounts;
	}

	@Override
	public List<FarmerDetailsForChangeAcNo> PRSReceiptDetailsByFarmerId(String farmerID) {

		String sql = "SELECT CAST(PRSSlno AS varchar) +'/'+ CAST(PRSBook AS varchar) AS prsno, PaymentOrderNumber,PaymentArrOrderNumber FROM PRSReceipt \r\n"
				+ "WHERE FarmerID='" + farmerID + "'";

		List<FarmerDetailsForChangeAcNo> li = jdbcTemplate.query(sql, new RowMapper<FarmerDetailsForChangeAcNo>() {
			public FarmerDetailsForChangeAcNo mapRow(ResultSet rs, int row) throws SQLException {

				FarmerDetailsForChangeAcNo obj = new FarmerDetailsForChangeAcNo();

				obj.setPrsno(rs.getString("prsno"));
				obj.setPaymentArrOrderNumber(rs.getString("PaymentArrOrderNumber"));
				obj.setPaymentOrderNumber(rs.getString("PaymentOrderNumber"));

				System.out.println(obj.getPaymentOrderNumber());
				System.out.println(obj.getPaymentArrOrderNumber());

				return obj;
			}
		});

		return li;
	}

	@Override
	public FarmerDetailsForChangeAcNo FarmerDetailsByFarmerId(String rego) {
	
		FarmerDetailsForChangeAcNo obj = new FarmerDetailsForChangeAcNo();
		FarmerDetailsForChangeAcNo obj2 = new FarmerDetailsForChangeAcNo();
		
		String sql = "SELECT TOP 1 PERCENT fcd.FarmerID ,fcd.Accno,fr.Farmername FROM FarmerCropDetails fcd \r\n" + 
				     "JOIN FarmerRegistration fr ON fr.Farmerid = fcd.FarmerID \r\n" + 
				     "WHERE fcd.FarmerRegno = ?";

		obj = jdbcTemplate.queryForObject(
				sql, 
	               new Object[] { rego },
	               new RowMapper<FarmerDetailsForChangeAcNo>() {
	                   @Override
	                   public FarmerDetailsForChangeAcNo mapRow(ResultSet rs, int rowNumber) throws SQLException {
	                	   FarmerDetailsForChangeAcNo fd = new FarmerDetailsForChangeAcNo();
	                   
	                       // set other properties
	                	   fd.setFarmerid(rs.getString("FarmerID"));
	                	   fd.setAccno(rs.getString("Accno"));
	                	   fd.setFarmername(rs.getString("Farmername"));

	                       return fd;
	                   }
	               });
		 
       
       String sql0= "SELECT TOP 1 PERCENT PaymentOrderNumber FROM PRSReceipt WHERE FarmerID='"+obj.getFarmerid()+"'";
       String sql01= "SELECT TOP 1 PERCENT PaymentArrOrderNumber FROM PRSReceipt WHERE FarmerID='"+obj.getFarmerid()+"'";
      
       String pon= jdbcTemplate.queryForObject(sql0, String.class);
       String poan= jdbcTemplate.queryForObject(sql01, String.class);
		
		
       String sql1 = "select TOP 1 PERCENT BankId from paymentorder where POrderNo in('"+pon+"','"+poan+"')";

       String BankId= jdbcTemplate.queryForObject(sql1, String.class);
      
       String sql3 = "SELECT bm.bm_name,bp.bp_place FROM BankBranches bb\r\n" + 
       				 "JOIN BankMaster bm on bm.bm_code = bb.bb_bankcode\r\n" + 
       		         "JOIN BankPlaces bp on bp.bp_code = bb.bb_placecode \r\n" + 
       		         "WHERE bb.bb_id = ? ";
       
       obj2 = jdbcTemplate.queryForObject(
               sql3, 
               new Object[] { BankId },
               new RowMapper<FarmerDetailsForChangeAcNo>() {
                   @Override
                   public FarmerDetailsForChangeAcNo mapRow(ResultSet rs, int rowNumber) throws SQLException {
                	   FarmerDetailsForChangeAcNo fd = new FarmerDetailsForChangeAcNo();
                   
                       // set other properties
                	   fd.setBankname(rs.getString("bm_name"));
                	   fd.setBankplace(rs.getString("bp_place"));

                       return fd;
                   }
               });
       
       obj.setBankname(obj2.getBankname());
       obj.setBankplace(obj2.getBankplace());
       
       System.out.println("farmer Name : "+obj.getFarmername());
       System.out.println("Accno : "+obj.getAccno());
       System.out.println("bank name and place : "+obj.getBankname()+""+obj.getBankplace());

		
		return obj;
	}



}
