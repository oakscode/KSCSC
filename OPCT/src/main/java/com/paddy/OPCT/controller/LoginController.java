package com.paddy.OPCT.controller;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.User;
import com.paddy.OPCT.options.MD5Hashing;

@Controller
public class LoginController {
	
	@Autowired
	private Dao_Admin dao;
	
	@RequestMapping(value="/login")
	public ModelAndView getlogin(HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView("login/login") ;
		return mv;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView postlogin(HttpServletResponse response, HttpSession session,@ModelAttribute("login") Login login) {
		String PasswordToHash = null ;
		MD5Hashing md5 = new MD5Hashing();
		
		try {
			ModelAndView mv = new ModelAndView("redirect:/home");
			PasswordToHash = md5.getHash(login.getPassword());
			int id_user = dao.LoginValidity(login.getUsername(), PasswordToHash);
			
			User user =  dao.list_user(id_user);
			
			session.setAttribute("id_usertype", user.getId_usertype());
			session.setAttribute("id_user",user.getId_user());
			session.setAttribute("title",user.getTitle());
			
			
			 return mv;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (EmptyResultDataAccessException e) {
			
			System.out.println("Invalied User name or Password");
		}
		return getlogin(response);
		
	}
	@RequestMapping(value="/logout")
	public String logout(HttpServletResponse response, HttpSession session){
		
		session.invalidate();
		
		return "redirect:/login";
	}
	
	@RequestMapping(value="/welcome")
	public ModelAndView getwelcome(HttpServletResponse response, HttpSession session){
		
		
		return new ModelAndView("admin/welcome");
	}
	

}
