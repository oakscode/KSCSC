package com.supplyco.jsonqr.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import com.supplyco.jsonqr.dao.MasterDAO;
import com.supplyco.jsonqr.model.BchDtls;
import com.supplyco.jsonqr.model.BuyerDtls;
import com.supplyco.jsonqr.model.Dippo;
import com.supplyco.jsonqr.model.DispDtls;
import com.supplyco.jsonqr.model.DocDtls;
import com.supplyco.jsonqr.model.EwbDtls;
import com.supplyco.jsonqr.model.ItemList;
import com.supplyco.jsonqr.model.Login;
import com.supplyco.jsonqr.model.MasterEinvoice;
import com.supplyco.jsonqr.model.SellerDtls;
import com.supplyco.jsonqr.model.Soutdetail;
import com.supplyco.jsonqr.model.Southead;
import com.supplyco.jsonqr.model.TranDtls;
import com.supplyco.jsonqr.model.ValDtls;

public class MasterDAO_impl implements MasterDAO {
	
	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate oraclejdbcTemplate;
	
	public MasterDAO_impl(DataSource mysqldataSource,DataSource oracledataSource) {
		jdbcTemplate = new JdbcTemplate(mysqldataSource);
		oraclejdbcTemplate = new JdbcTemplate(oracledataSource);
		
	}
	

	@Override
	public List<Dippo> dippolist() {

		String sql = "SELECT x.* FROM hodms.dippo x\r\n" + 
				"WHERE D_TYPE ='d'";

		List<Dippo> li = jdbcTemplate.query(sql, new RowMapper<Dippo>() {
			public Dippo mapRow(ResultSet rs, int row) throws SQLException {

				Dippo dippo = new Dippo();
				dippo.setDipponame(rs.getString("Dipponame"));
				dippo.setDippocode(rs.getString("Dippocode"));
				return dippo;
			}
		});

		return li;
	}


	@Override
	public List<Southead> isulist(final String dippocode) {
		
		String sql = "select a.*,max(a.TrDate) from southead a join sindetailgst b on a.IsuNo = b.isuno and a.ThisOLCode ='"+dippocode+"' and a.IsuDate >= '2020-03-01' and a.IsuMode ='2' and b.BStatus ='1' \r\n" + 
				     " and a.active =1 and a.stage=1 GROUP by a.IsuNo ORDER BY a.IsuNo ASC";
		
		System.out.println("issue list->"+sql);
		
		List<Southead> li =jdbcTemplate.query(sql, new RowMapper<Southead>() {

			@Override
			public Southead mapRow(ResultSet rs, int rowNum) throws SQLException {
				Southead sout = new Southead();
				sout.setIsuno(rs.getString("isuno"));
				sout.setIsudate(rs.getString("isudate"));
				sout.setIsumode(rs.getString("isumode"));
				sout.setSaleMode(rs.getString("salemode"));
				sout.setTrnsCode(rs.getString("Transcode"));
				sout.setNetAmt(rs.getString("netAmt"));
				sout.setViewurl("<a href=\"viewissue?isuno="+sout.getIsuno()+"&olcode="+dippocode+"\">view</a>");
				
				
				return sout;
			}
			});

		return li;
	}


	@Override
	public Southead isuhead(String isuno,String dippocode) {

		String sql = "select a.*,c.dipponame ,max(a.TrDate) from southead a join sindetailgst b on a.IsuNo = b.isuno join dippo c on a.ThisOLCode =c.Dippocode where "
				   +"a.ThisOLCode ='"+dippocode+"' and a.IsuDate >= '2020-03-01' and a.IsuMode ='2' and b.BStatus ='1' and a.active =1 and a.stage=1 \r\n" + 
					"and a.IsuNo = '"+isuno+"' GROUP by a.IsuNo ORDER BY a.IsuNo ASC" ;

		Southead south = jdbcTemplate.queryForObject(sql, new RowMapper<Southead>() {
			public Southead mapRow(ResultSet rs, int row) throws SQLException {

				Southead s = new Southead();
				
				s.setThisolcode(rs.getString("thisolcode"));
				s.setDipponame(rs.getString("dipponame"));
				s.setIsuno(rs.getString("isuno"));
				s.setIsudate(rs.getString("isudate"));
				s.setProdAmt(rs.getString("prodamt"));
				s.setTaxAmt(rs.getString("taxamt"));
				s.setDiscAmt(rs.getString("discamt"));
				s.setTotalAmt(rs.getString("totalamt"));
				s.setRoundoffamt(rs.getString("roundoffamt"));
				s.setNetAmt(rs.getString("netamt"));
				
				
				

				return s;
			}
		});
		return south;

	}


	@Override
	public List<Soutdetail> isudetail(String isuno, String dippocode) {
		String sql = "select a.*,b.cmname,max(a.TrDate),a.srate*a.isudqty as totalamt from soutdetail a join cm b on a.CMCode =b.CMCode join southead c "
				+ "on a.IsuNo =c.IsuNo where c.Active =1 and c.Stage = 1 and a.IsuNo='"+isuno+"' and a.ThisOLCode ='"+dippocode+"' GROUP by a.Slno order by a.slno";
		
		System.out.println("qry-->"+sql);
		
		List<Soutdetail> li =jdbcTemplate.query(sql, new RowMapper<Soutdetail>() {

			@Override
			public Soutdetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				Soutdetail soutd = new Soutdetail();
				
				soutd.setIsuno(rs.getString("isuno"));
				soutd.setCmcode(rs.getString("cmcode"));
				soutd.setCmname(rs.getString("cmname"));
				soutd.setDiscperc(rs.getString("discperc"));
				soutd.setIsudqty(rs.getString("isudqty"));
				soutd.setSlno(rs.getString("slno"));
				soutd.setSrate(rs.getString("srate"));
				soutd.setTaxperc(rs.getString("taxperc"));
				soutd.setTotalamt(rs.getString("totalamt"));
				
				return soutd;
			}
			});
		
		
		return li;
	}


	@Override
	public MasterEinvoice initJson(String isuno, String olcode) {
		
		
		String head = "select a.*,c.dipponame ,max(a.TrDate),ol.olname,ol.PIN,ol.district,cst.CSTName,b.GSTINNO as bgstin,cst.ad1,cst.ad2,cst.distname, cst.pincode as cstpincode from southead a join sindetailgst b on a.IsuNo = b.isuno join dippo c on a.ThisOLCode =c.Dippocode join ol ol on ol.OLCODE = a.ThisOLCode  join cst cst on cst.CSTCode =a.CCode  where a.ThisOLCode ='"+olcode+"' and a.IsuDate >= '2020-03-01' and a.IsuMode ='2' and b.BStatus ='1' and a.active =1 and a.stage=1 \r\n" + 
				"and a.IsuNo = '"+isuno+"' GROUP by a.IsuNo,ol.OLCODE ORDER BY a.IsuNo ASC" ;
		
		final String detail= "select a.*,b.cmname,max(a.TrDate),a.srate*a.isudqty as totalamt from soutdetail a \r\n" + 
				"join cm b on a.CMCode =b.CMCode join southead c on a.IsuNo =c.IsuNo \r\n" + 
				"where c.Active =1 and c.Stage = 1 and a.IsuNo='"+isuno+"' and a.ThisOLCode ='"+olcode+"' \r\n" + 
				"GROUP by a.Slno order by a.slno";
		
		
		
		
		
		System.out.println("sql-->"+head);
		
		MasterEinvoice me = jdbcTemplate.queryForObject(head, new RowMapper<MasterEinvoice>() {
			public MasterEinvoice mapRow(ResultSet rs, int row) throws SQLException {
				
				String gstin = rs.getString("bgstin");
				int Taxamt = rs.getInt("taxamt");
				int igst = 0 ;
				int cgst_sgst = 0 ;
				
				System.out.println("gstin check :"+gstin.substring(0, 2));
				
				try {
					if(gstin.substring(0, 2).equals("32")) {
						cgst_sgst =Taxamt/2;
					}
					else {
						igst=Taxamt;
					}
					
				} catch (Exception e) {
					e.getMessage();
				}

				MasterEinvoice master = new MasterEinvoice();
				
				
				TranDtls tranDtls = new TranDtls();
				tranDtls.setTaxSch("GST");
				tranDtls.setSupTyp("B2B");
				tranDtls.setIgstOnIntra("N");
				tranDtls.setRegRev(null);
				tranDtls.setEcmGstin(null);
				
				DocDtls docDtls = new DocDtls();
				
				docDtls.setTyp("INV");
				docDtls.setNo(rs.getString("isuno"));
				docDtls.setDt(rs.getString("isudate"));
				
				
				SellerDtls sellerDtls = new SellerDtls();
				sellerDtls.setGstin("000000000000000");
				sellerDtls.setLglNm("KERALA STATE CIVIL SUPPLIES CORPORATION LTD");
				sellerDtls.setTrdNm(null);
				sellerDtls.setAddr1(rs.getString("dipponame"));
				sellerDtls.setAddr2(null);
				sellerDtls.setLoc(rs.getString("district"));
				sellerDtls.setPin(rs.getInt("pin"));
				sellerDtls.setStcd("32");
				sellerDtls.setPh(null);
				sellerDtls.setEm(null);

				BuyerDtls buyerDtls = new BuyerDtls();
				buyerDtls.setGstin(rs.getString("bgstin"));
				buyerDtls.setLglNm(rs.getString("cstname"));
				buyerDtls.setTrdNm(rs.getString("cstname")); // same problem
				buyerDtls.setPos("32");
				buyerDtls.setAddr1(rs.getString("ad1"));
				buyerDtls.setAddr2(rs.getString("ad2"));
				buyerDtls.setLoc(rs.getString("distname"));
				buyerDtls.setPin(rs.getInt("cstpincode"));
				buyerDtls.setStcd("32");
				buyerDtls.setPh(null);
				buyerDtls.setEm(null);
				
//				DispDtls dispDtls = new DispDtls();
//				dispDtls.setNm(null);
//				dispDtls.setAddr1(null);
//				dispDtls.setAddr2(null);
//				dispDtls.setLoc(null);
//				dispDtls.setPin(0);
//				dispDtls.setStcd(null);
				
				ValDtls valDtls = new ValDtls();
				valDtls.setAssVal(rs.getInt("totalamt"));
				valDtls.setIgstVal(igst);
				valDtls.setCgstVal(cgst_sgst);
				valDtls.setSgstVal(cgst_sgst);
				valDtls.setCesVal(0);
				valDtls.setDiscount(rs.getInt("discamt"));
				valDtls.setOthChrg(0);
				valDtls.setRndOffAmt(rs.getInt("RoundOffAmt"));
				valDtls.setTotInvVal(rs.getInt("netamt"));
				
//				EwbDtls ewbDtls = new EwbDtls();
//				ewbDtls.setTransId("32AAACK6767FHZ5");
//				ewbDtls.setTransName("transOne");
//				ewbDtls.setTransMode("1");
//				ewbDtls.setDistance(25);
//				ewbDtls.setTransDocNo("20107");
//				ewbDtls.setTransDocDt("12/10/2020");
//				ewbDtls.setVehNo("KL 52 J 3263");
//				ewbDtls.setVehType("R");
				
				
				
				List<ItemList> itemlist = jdbcTemplate.query(detail, new RowMapper<ItemList>() {

					@Override
					public ItemList mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						String sql = "SELECT HSNCODE FROM MCODE.CM_DMS  WHERE CMCODE = '"+rs.getString("cmcode")+"' ";
						
						
						String hsn = oraclejdbcTemplate.queryForObject(sql, String.class);
						
						
						// TODO Auto-generated method stub
						ItemList itemList = new ItemList();
						itemList.setSlNo(rs.getString("Slno"));
						itemList.setPrdDesc(rs.getString("cmname"));
						itemList.setIsServc("N");
						itemList.setHsnCd(hsn);
						itemList.setBarcde(null);
						itemList.setQty(rs.getInt("isudqty"));
						itemList.setFreeQty(0);
						itemList.setUnit(rs.getString("ucode"));
						itemList.setUnitPrice(rs.getInt("srate")); //set int to double 300.00
						itemList.setTotAmt(rs.getInt("totalamt"));
						itemList.setDiscount(0);
						itemList.setPreTaxVal(0);
						itemList.setAssAmt(rs.getInt("totalamt"));
						itemList.setGstRt(rs.getInt("TaxPerc"));
						itemList.setIgstAmt(0);
						itemList.setCgstAmt(180);
						itemList.setSgstAmt(180);
						itemList.setCesRt(0.2);
						itemList.setCesAmt(6);
						itemList.setCesNonAdvlAmt(0);
						itemList.setStateCesRt(0.1);
						itemList.setStateCesAmt(3);
						itemList.setStateCesNonAdvlAmt(0);
						itemList.setOthChrg(0);
						itemList.setTotItemVal(3369);
						
						BchDtls bchDtls = new BchDtls();
						bchDtls.setNm("bb22yy");
						bchDtls.setExpDt("30/11/2026");
						bchDtls.setWrDt("90/11/2026");
						itemList.setBchDtls(bchDtls);
						
						return itemList;
					}
					
				}); 
				
				
				
				
				
				master.setVersion("1.1");
				master.setTranDtls(tranDtls);
				master.setDocDtls(docDtls);
				master.setSellerDtls(sellerDtls);
				master.setBuyerDtls(buyerDtls);
			//	master.setDispDtls(dispDtls);
				master.setShipDtls(null);
				master.setValDtls(valDtls);
				master.setEwbDtls(null);
				master.setItemList(itemlist);
				
				
				

				return master;
			}
		});
		
		
		
		
		
		
		return me;
		

	}







}
