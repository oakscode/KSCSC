package com.supplyco.jsonqr.einvoice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplyco.jsonqr.model.BchDtls;
import com.supplyco.jsonqr.model.BuyerDtls;
import com.supplyco.jsonqr.model.DispDtls;
import com.supplyco.jsonqr.model.DocDtls;
import com.supplyco.jsonqr.model.EwbDtls;
import com.supplyco.jsonqr.model.ItemList;
import com.supplyco.jsonqr.model.MasterEinvoice;
import com.supplyco.jsonqr.model.SellerDtls;
import com.supplyco.jsonqr.model.TranDtls;
import com.supplyco.jsonqr.model.ValDtls;

public class JsonInit {
	
	private static String isuno;
	private static String olcode;
	private static String jsonpath;
	
	public JsonInit(){
		
		System.out.println("Building JSON file..");
	}
	
public boolean createjson(String isuno,String olcode,String jsonpath,MasterEinvoice master) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
	        
	            mapper.writeValue(new File(jsonpath), master);
	            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(master);
	            System.out.println(jsonString);
	            System.out.println("Build success!");
	            return true;
	      
			
		} catch (Exception e) {
			System.out.println("Create JSON Exception "+e.getMessage());
			return false;
		}
	}
}
