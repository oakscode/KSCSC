package com.paddy.OPCT.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.Menus_sub;
import com.paddy.OPCT.model.User;
import com.paddy.OPCT.model.Usertype;


@Controller
public class MenuController {
	
	@Autowired
	private Dao_Admin dao;
	
	
	@RequestMapping(value = "/menulist")
	public String get_menulist(HttpServletResponse response, HttpSession session, Model model) {

		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<Menus> allmenus = dao.list_menus();
		List<Usertype> usertype = dao.list_usertype();

		model.addAttribute("usertype", usertype);
		model.addAttribute("menus", menus);
		model.addAttribute("allmenus", allmenus);
		model.addAttribute("page", "menu/menulist.jsp");

		return "OPCT";
	}
	@RequestMapping(value = "/submenulist")
	public String get_submenulist(HttpServletResponse response, HttpSession session, Model model, int id) {

		List<Menus> menus = dao.list_menus((int) session.getAttribute("id_usertype"));
		List<Menus_sub> allmenus = dao.list_menus_sub(id);

		model.addAttribute("menus", menus);
		model.addAttribute("allmenus", allmenus);
		model.addAttribute("page", "menu/submenulist.jsp");

		return "OPCT";
	}
	@RequestMapping(value = "/addmainmenu", method = RequestMethod.POST)
	public String post_addmainmenu(HttpServletResponse response, HttpSession session, Model model,
								@ModelAttribute Menus m) {
		
		int i = dao.insert_mainmenu(m);
		System.out.println("Mainmenu insertion status : "+ i);

		return "redirect:/menulist";
	}
	@RequestMapping(value = "/addsubmenu", method = RequestMethod.POST)
	public String post_addsubmenu(HttpServletResponse response, HttpSession session, Model model,
								@ModelAttribute Menus_sub m) {
		
		int i = dao.insert_submenu(m);
		System.out.println("submenu insertion status : "+ i);
		
		return "redirect:/menulist";
	}
	//deletesubmenu
	
	@RequestMapping(value = "/deletesubmenu")
	public String get_deletesubmenu(HttpServletResponse response, HttpSession session, Model model, int id_menu, int id_menu_sub) {

		int[] update = dao.delete_submenu(id_menu_sub);
		
		for (int i : update) { 
			  
            System.out.print(i + " sub menu update"); 
        }
		
		return "redirect:/submenulist?id="+id_menu;
	}
	//deletemainmenu
	@RequestMapping(value = "/deletemainmenu")
	public String get_deletemainmenu(HttpServletResponse response, HttpSession session, Model model, int id_menu) {

		int[] update = dao.delete_mainmenu(id_menu);
		
		for (int i : update) { 
			  
            System.out.println(i + " mainmenu and sub menu  update"); 
        }
		return "redirect:/menulist";
	}
	//addsinglemenu
	@RequestMapping(value = "/addsinglemenu", method = RequestMethod.POST)
	public String post_addsinglemenu(HttpServletResponse response, HttpSession session, Model model,
								@ModelAttribute Menus m) {
		
		int i = dao.insert_singlemenu(m);
		System.out.println("Mainmenu insertion status : "+ i);

		return "redirect:/menulist";
	}

}
