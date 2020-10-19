package com.paddy.OPCT.dao_impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.exceptions.InvalidLoginException;
import com.paddy.OPCT.model.Login;
import com.paddy.OPCT.model.Menus;
import com.paddy.OPCT.model.Menus_sub;
import com.paddy.OPCT.model.User;
import com.paddy.OPCT.model.Usertype;
import com.paddy.OPCT.options.MD5Hashing;

public class Dao_Admin_impl implements Dao_Admin {
	
	private JdbcTemplate jdbcTemplate;

	public Dao_Admin_impl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<User> list_user() {

		String sql = "SELECT a.id_user,a.title,a.empcode,a.id_usertype,b.title AS stringtype  FROM users a JOIN usertype b ON b.id_usertype = a.id_usertype ";

		List<User> li = jdbcTemplate.query(sql, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int row) throws SQLException {

				User u = new User();
				u.setId_user(rs.getInt("id_user"));
				u.setTitle(rs.getString("title"));
				u.setEmpcode(rs.getString("empcode"));
				u.setId_usertype(rs.getInt("id_usertype"));
				u.setUsertype(rs.getString("Stringtype"));

				return u;
			}
		});

		return li;
	}

	@Override
	public int LoginValidity(final String u, final String p) throws EmptyResultDataAccessException {
		
		System.out.println("user name"+u);
		System.out.println("password"+p);
		
		String sql = "SELECT id_user FROM credentials WHERE username =? AND PASSWORD=? ";

        Object[] inputs = new Object[] {u,p};
        int id_user = jdbcTemplate.queryForObject(sql, inputs, Integer.class);
        
 
	
        return id_user;
	}

	@Override
	public User list_user(int id_user) {

		String sql = "select id_user,title,empcode,id_usertype from users where id_user ="+id_user;

		 User u = jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int row) throws SQLException {

				User u = new User();
				u.setId_user(rs.getInt("id_user"));
				u.setTitle(rs.getString("title"));
				u.setEmpcode(rs.getString("empcode"));
				u.setId_usertype(rs.getInt("id_usertype"));

				return u;
			}
		});
		 
		return u;
	}

	@Override
	public List<Menus> list_menus(int usertype) {

		String sql = "SELECT * FROM menus WHERE usertype ="+usertype;

		List<Menus> li = jdbcTemplate.query(sql, new RowMapper<Menus>() {
			public Menus mapRow(ResultSet rs, int row) throws SQLException {

				Menus menu = new Menus();

				menu.setId_menu(rs.getInt("id_menu"));
				menu.setActive(rs.getInt("active"));
				menu.setUsertype(rs.getInt("usertype"));
				menu.setTitle(rs.getString("title"));
				menu.setIcon(rs.getString("icon"));
				menu.setPage(rs.getString("page"));
				menu.setType(rs.getInt("type"));
				menu.setMenus_sub(list_menus_sub(menu.getId_menu()));

				return menu;
			}
		});

		return li;
	}

	@Override
	public List<Menus_sub> list_menus_sub(int id_menu) {

		String sql = "SELECT id_menu_sub,id_menu,page,title,description,active FROM menus_sub WHERE id_menu ="+id_menu;

		List<Menus_sub> li = jdbcTemplate.query(sql, new RowMapper<Menus_sub>() {
			public Menus_sub mapRow(ResultSet rs, int row) throws SQLException {

				Menus_sub ms = new Menus_sub();
				ms.setId_menu_sub(rs.getInt("id_menu_sub"));
				ms.setId_menu(rs.getInt("id_menu"));
				ms.setActive(rs.getInt("active"));
				ms.setDescription(rs.getString("description"));
				ms.setPage(rs.getString("page"));
				ms.setTitle(rs.getString("title"));
			

				return ms;
			}
		});

		return li;
	}

	@Override
	public List<Usertype> list_usertype() {

		String sql = "select id_usertype,title from usertype;";

		List<Usertype> li = jdbcTemplate.query(sql, new RowMapper<Usertype>() {
			public Usertype mapRow(ResultSet rs, int row) throws SQLException {

				Usertype ut = new Usertype();
				
				ut.setId_usertype(rs.getInt("id_usertype"));
				ut.setTitle(rs.getString("title"));
				
				return ut;
			}
		});

		return li;
	}

	@Override
	public int insert_user(User u, Login l) throws NoSuchAlgorithmException {

		Date dt = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		String PasswordToHash;
		MD5Hashing md5 = new MD5Hashing();
		PasswordToHash = md5.getHash(l.getPassword());
		l.setPassword(PasswordToHash);
		
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("INSERT INTO users (title, empcode, id_usertype) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, u.getTitle());
		        statement.setString(2, u.getEmpcode());
		        statement.setInt(3, u.getId_usertype());
		        return statement;
		    }
		}, holder);

		long primaryKey = holder.getKey().longValue();
		
		if(primaryKey>0) 
		{
			String sql = "INSERT INTO credentials(id_user,username, password,active) VALUES (?,?, ?,?)";
			int i2 = jdbcTemplate.update(sql, primaryKey,l.getUsername(),l.getPassword(),1);
		}
		
	

		return 0;
		}
	
	

	@Override
	public boolean varifyUsername(String username, String password) throws NoSuchAlgorithmException {
		MD5Hashing md5 = new MD5Hashing();
		
		password =  md5.getHash(password);
		try {
			 boolean credentialExists = jdbcTemplate.queryForObject("SELECT 1 FROM credentials WHERE username = ? OR password = ?", new Object[]{username,password}, Integer.class) == 1;
			
		} catch (EmptyResultDataAccessException e) {
			return true;
		}
       
        return false;
       
	}

	@Override
	public List<Menus> list_menus() {

		String sql = "SELECT a.id_menu,a.usertype,a.title,a.active,b.title as usertype_title,a.icon as icon,a.page as page,a.type as type FROM menus a join usertype b on a.usertype = b.id_usertype";

		List<Menus> li = jdbcTemplate.query(sql, new RowMapper<Menus>() {
			public Menus mapRow(ResultSet rs, int row) throws SQLException {

				Menus menu = new Menus();
				
				menu.setId_menu(rs.getInt("id_menu"));
				menu.setTitle(rs.getString("title"));
				menu.setUsertype(rs.getInt("usertype"));
				menu.setActive(rs.getInt("active"));
				menu.setIcon(rs.getString("icon"));
				menu.setPage(rs.getString("page"));
				menu.setType(rs.getInt("type"));
				menu.setUsertype_title(rs.getString("usertype_title"));
				
				return menu;
			}
		});

		return li;
	}

	@Override
	public int[] delete_user(int id) {

		String[] sqlArray = { "DELETE FROM users WHERE id_user =" + id, "DELETE FROM credentials WHERE id_user =" + id, };

		int[] updateCounts = jdbcTemplate.batchUpdate(sqlArray);

		return updateCounts;
	}

	@Override
	public int insert_mainmenu(Menus m) {

			String sql = "INSERT INTO menus(usertype,title,active,icon,type) VALUES (?,?,?,?,?)";
			int i = jdbcTemplate.update(sql,m.getUsertype(),m.getTitle(),1,m.getIcon(),1);

		return i;
		}

	@Override
	public int insert_submenu(Menus_sub s) {

		String sql = "INSERT INTO menus_sub(id_menu,page,title,description,active) VALUES (?,?,?,?,?)";
		int i = jdbcTemplate.update(sql,s.getId_menu(),s.getPage(),s.getTitle(),s.getDescription(),1);

	return i;
	}

	@Override
	public int[] delete_submenu(int id) {

		String[] sqlArray = { "DELETE FROM menus_sub WHERE id_menu_sub =" + id};

		int[] updateCounts = jdbcTemplate.batchUpdate(sqlArray);

		return updateCounts;
	}

	@Override
	public int[] delete_mainmenu(int id) {

		String[] sqlArray = { "DELETE FROM menus WHERE id_menu="+ id,"DELETE FROM menus_sub WHERE id_menu="+ id};

		int[] updateCounts = jdbcTemplate.batchUpdate(sqlArray);

		return updateCounts;
	}

	@Override
	public int insert_usertype(Usertype u) {

		String sql = "INSERT INTO usertype(title) VALUES (?)";
		int i = jdbcTemplate.update(sql,u.getTitle());

	return i;
	}

	@Override
	public int insert_singlemenu(Menus m) {

		String sql = "INSERT INTO menus(usertype,title,active,icon,page,type) VALUES (?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql,m.getUsertype(),m.getTitle(),1,m.getIcon(),m.getPage(),0);

	return i;
	}
}
