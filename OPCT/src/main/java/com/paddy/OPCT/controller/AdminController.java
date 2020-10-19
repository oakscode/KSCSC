package com.paddy.OPCT.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.dao.Dao_Paddy;
import com.paddy.OPCT.exceptions.InvalidLoginException;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.Menus_sub;
import com.paddy.OPCT.model.User;
import com.paddy.OPCT.model.Usertype;

@Controller
public class AdminController {

	@Autowired
	private Dao_Admin dao;

	@Autowired
	private Dao_Paddy daopaddy;

	@RequestMapping(value = "/home")
	public ModelAndView getHome(HttpServletResponse response, HttpSession session) {

		ModelAndView mv = new ModelAndView("OPCT");
		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		mv.addObject("menus", menus);
		return mv;
	}

	@RequestMapping(value = "/userlist")
	public String get_useradd(HttpServletResponse response, HttpSession session, Model model,String insertstatus) {

		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<User> user = dao.list_user();
		List<Usertype> usertype = dao.list_usertype();
		
		model.addAttribute("insertstatus", insertstatus);
		model.addAttribute("usertype", usertype);
		model.addAttribute("menus", menus);
		model.addAttribute("users", user);
		model.addAttribute("page", "admin/userlist.jsp");

		return "OPCT";

	}
	@RequestMapping(value = "/deleteuser")
	public String get_deleteuser(HttpServletResponse response, HttpSession session, Model model, int id) {

		int update[] = dao.delete_user(id);
		
		for (int i : update) { 
			  
            System.out.print(i + " batch update"); 
        }
		
		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<User> user = dao.list_user();

		model.addAttribute("menus", menus);
		model.addAttribute("users", user);
		model.addAttribute("page", "admin/userlist.jsp");

		return "redirect:/userlist";

	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String post_adduser(HttpServletResponse response, HttpSession session, Model model,
			@ModelAttribute User user, @ModelAttribute Login login) {
		boolean b = false;
		try {

			b = dao.varifyUsername(login.getUsername(), login.getPassword());
			if (!b){

				model.addAttribute("insertstatus", "<font color=\"red\">User Not inserted</font>");
			}else {
				dao.insert_user(user, login);
				model.addAttribute("insertstatus", "<font color=\"green\">User registerd successfully!.</font>");
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		

		return "redirect:/userlist";

	}
	
	@RequestMapping(value = "/edituser")
	public String get_edituser(HttpServletResponse response, HttpSession session, Model model,String id) {

		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<Usertype> usertype = dao.list_usertype();

		model.addAttribute("usertype", usertype);
		model.addAttribute("menus", menus);
		model.addAttribute("page", "admin/edituser.jsp");

		return "OPCT";

	}
	
	@RequestMapping(value = "/usertypelist")
	public String get_listusertype(HttpServletResponse response, HttpSession session, Model model) {

		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<Usertype> usertype = dao.list_usertype();
		
		
		model.addAttribute("menus", menus);
		model.addAttribute("usertype",usertype);
		model.addAttribute("page", "admin/usertypelist.jsp");

		return "OPCT";

	}
	
	//addusertype
	@RequestMapping(value = "/addusertype", method = RequestMethod.POST)
	public String post_addusertype(HttpServletResponse response, HttpSession session, Model model,
								@ModelAttribute Usertype m) {
		
		int i = dao.insert_usertype(m);
		System.out.println("usertype insertion status : "+ i);
		
		return "redirect:/usertypelist";
	}

}
