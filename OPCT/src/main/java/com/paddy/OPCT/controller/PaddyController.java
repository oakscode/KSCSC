package com.paddy.OPCT.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.dao.Dao_Paddy;
import com.paddy.OPCT.model.FarmerDetailsForChangeAcNo;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.Usertype;


@Controller
public class PaddyController {
	
	@Autowired
	private Dao_Paddy daopaddy;
	
	@Autowired
	private Dao_Admin daoadmin;
	
	//change-farmer-acno
	
	@RequestMapping(value = "/change-farmer-acno")
	public String get_change_farmer_acno(HttpServletResponse response, HttpSession session, Model model) {

		List<Menus> menus = daoadmin.list_menus((int) session.getAttribute("id_usertype"));
		model.addAttribute("menus", menus);
		model.addAttribute("page", "paddy/change-farmer-acno.jsp");

		return "OPCT";

	}
	//updateFarmerRegno
	@RequestMapping(value = "/change-farmer-acno", method = RequestMethod.POST)
	public String post_updateFarmeraccno(HttpServletResponse response, HttpSession session, Model model,
			@RequestParam String regno,@RequestParam String newaccno,@ModelAttribute Usertype m) {
		
		int idUser = (int) session.getAttribute("id_usertype");
	
		int[] updt= daopaddy.updateFarmerAccno(idUser, newaccno, regno);
		System.out.println(updt.toString()+" : Batch Update Status");
		
		List<Menus> menus = daoadmin.list_menus(idUser);
		model.addAttribute("menus", menus);
		model.addAttribute("page", "paddy/change-farmer-acno.jsp");
		model.addAttribute("msg", "Updated Successfully");
		
		return "OPCT";
	}
	
	
	//Ajax Page
	@RequestMapping(value = "/FarmerAcchangeDataForm/{regno}")
	public String get_details(HttpServletResponse response, HttpSession session, Model model,@PathVariable("regno") String regno  ) {

		try {
			FarmerDetailsForChangeAcNo fd = daopaddy.FarmerDetailsByFarmerId(regno);
			List<FarmerDetailsForChangeAcNo> li = daopaddy.PRSReceiptDetailsByFarmerId(fd.getFarmerid());
			
		} catch (Exception e) {
			
			return "redirect:/ajax_msg/nulldata";
		}
		
		FarmerDetailsForChangeAcNo fd = daopaddy.FarmerDetailsByFarmerId(regno);
		List<FarmerDetailsForChangeAcNo> li = daopaddy.PRSReceiptDetailsByFarmerId(fd.getFarmerid());
		
		model.addAttribute("prsdetails", li);
		model.addAttribute("accno",fd.getAccno());
		model.addAttribute("farmername",fd.getFarmername());
		model.addAttribute("bank",fd.getBankname()+" , "+fd.getBankplace());
		

		return "ajax/FarmerAcchangeDataForm";

	}

}
