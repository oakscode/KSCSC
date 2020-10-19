package com.paddy.OPCT.model;

import java.util.List;

public class Menus {

	private int id_menu;
	private int active;
	private int usertype;
	private String usertype_title;
	private String title;
	private List<Menus_sub> menus_sub;
	private String icon;
	private String page;
	private int type;
	
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUsertype_title() {
		return usertype_title;
	}
	public void setUsertype_title(String usertype_title) {
		this.usertype_title = usertype_title;
	}
	public List<Menus_sub> getMenus_sub() {
		return menus_sub;
	}
	public void setMenus_sub(List<Menus_sub> menus_sub) {
		this.menus_sub = menus_sub;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId_menu() {
		return id_menu;
	}
	public void setId_menu(int id_menu) {
		this.id_menu = id_menu;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	
	
}
