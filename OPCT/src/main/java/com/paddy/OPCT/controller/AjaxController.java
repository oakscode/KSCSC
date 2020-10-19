package com.paddy.OPCT.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.dao.Dao_Paddy;
import com.paddy.OPCT.model.FarmerDetailsForChangeAcNo;

@Controller
public class AjaxController {

	@Autowired
	private Dao_Admin dao;

	@Autowired
	private Dao_Paddy daopaddy;

	@RequestMapping(value = "/ajax_usercheck")
	@ResponseBody
	public String getusercheck(HttpServletResponse response, HttpSession session, String username) {

		boolean b = false;

		try {

			b = dao.varifyUsername(username, "");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println("username check :" + b);

		if (b) {
			return "<font color=\"green\">Username available</font>";
		}

		return "<font color=\"red\">Username not available</font>";
	}
	@RequestMapping(value = "/ajax_msg/{msg}")
	@ResponseBody
	public String ajaxmsg(HttpServletResponse response, HttpSession session,@PathVariable("msg") String msg) {
		
		if (msg.equals("nulldata")) {
			
			return "<font color=\"red\">No records</font>";
		}
		
		return msg;
	}

}
