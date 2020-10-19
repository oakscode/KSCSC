package com.supplyco.jsonqr.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplyco.jsonqr.dao.MasterDAO;
import com.supplyco.jsonqr.einvoice.JsonInit;
import com.supplyco.jsonqr.model.BchDtls;
import com.supplyco.jsonqr.model.BuyerDtls;
import com.supplyco.jsonqr.model.Dippo;
import com.supplyco.jsonqr.model.DispDtls;
import com.supplyco.jsonqr.model.DocDtls;
import com.supplyco.jsonqr.model.EwbDtls;
import com.supplyco.jsonqr.model.ItemList;
import com.supplyco.jsonqr.model.MasterEinvoice;
import com.supplyco.jsonqr.model.SellerDtls;
import com.supplyco.jsonqr.model.Soutdetail;
import com.supplyco.jsonqr.model.Southead;
import com.supplyco.jsonqr.model.TranDtls;
import com.supplyco.jsonqr.model.ValDtls;

@Controller
public class MasterController {
	
	@Autowired
	MasterDAO dao;
	
	@RequestMapping(value = "/issuelist",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String test(HttpServletResponse response,@RequestParam("dcode") String dcode) {
		
		System.out.println("Deppocode  "+dcode);
		
		List<Southead> isu = dao.isulist(dcode);
		int no=1;
		JSONArray userArray = new JSONArray();
		for(Southead s: isu) {
			
			String n=Integer.toString(no);
			
			String[] value =
					{ /* n, */
		         s.getIsuno(),
		         s.getIsudate(),
		         s.getIsumode(),
		         s.getSaleMode(),
		         s.getNetAmt(),
		         s.getViewurl()
		        }; 
			
			userArray.put(value);
	        no++;
		}
		
		String x=userArray.toString();
		return "{\"data\":"+x+"}";
		
	}
	//geneinvice
	@RequestMapping(value = "/viewissue")
	public ModelAndView getlogin(HttpServletResponse response,@RequestParam("isuno") String isuno,@RequestParam("olcode") String olcode) {

		Southead south = dao.isuhead(isuno, olcode);
		ModelAndView mv = new ModelAndView("viewissue");
		
		List<Soutdetail> li = dao.isudetail(isuno, olcode);
		
		for(Soutdetail s : li) {
			System.out.println("slno :"+s.getSlno());
			System.out.println("cmcode :"+s.getCmcode());
			System.out.println("cmname :"+s.getCmname());
			System.out.println("isudqty :"+s.getIsudqty());
		}
		
		
		mv.addObject("south", south);
		mv.addObject("soutd", li);
		
		return mv;
	}
	@RequestMapping("/download")
	   public void downloadFile3(HttpServletResponse resonse,@RequestParam("isuno") String isuno,@RequestParam("olcode") String olcode) throws IOException {
	     
		System.out.println("olcode :"+olcode);
		System.out.println("isuno  :"+isuno);
		
		String filepath = "D://temp//"+isuno+"inv"+olcode+".json";
		
		JsonInit ji = new JsonInit();
		MasterEinvoice meinv = dao.initJson(isuno, olcode);
		boolean flag = ji.createjson(isuno, olcode, filepath,meinv);
		
		if(flag) {
			
			File file = new File(filepath);

		      resonse.setContentType("application/pdf");
		      resonse.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		      BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		      BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
		      
		      byte[] buffer = new byte[1024];
		      int bytesRead = 0;
		      while ((bytesRead = inStrem.read(buffer)) != -1) {
		        outStream.write(buffer, 0, bytesRead);
		      }
		      outStream.flush();
		      inStrem.close();
			
		}
		  
	   }
	

}
