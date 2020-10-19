package com.paddy.OPCT.dao;

import java.awt.Menu;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.paddy.OPCT.exceptions.InvalidLoginException;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.Menus_sub;
import com.paddy.OPCT.model.User;
import com.paddy.OPCT.model.Usertype;



public interface Dao_Admin {
	
	public List<User> list_user();
	public User list_user(int id_user);
	
	public int[] delete_user(int id);
	public int[] delete_submenu(int id);
	public int[] delete_mainmenu(int id);
	
	public List<Menus> list_menus(int usertype);
	public List<Menus_sub> list_menus_sub(int id_menu);
	
	public List<Usertype> list_usertype();
	public List<Menus> list_menus();
	
	public int insert_user(User u,Login l) throws NoSuchAlgorithmException;
	public int insert_mainmenu(Menus memus);
	public int insert_submenu(Menus_sub submemus);
	public int insert_usertype(Usertype u);
	public int insert_singlemenu(Menus m);
	
	public int LoginValidity(String u,String p);
	
	public boolean varifyUsername(String username,String password) throws NoSuchAlgorithmException;
	
	
	
	

}
