package com.paddy.OPCT.config;



import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.paddy.OPCT.dao.Dao_Admin;
import com.paddy.OPCT.dao.Dao_Paddy;
import com.paddy.OPCT.dao_impl.Dao_Admin_impl;
import com.paddy.OPCT.dao_impl.Dao_Paddy_impl;

@Configuration
@ComponentScan(basePackages="com.paddy.OPCT")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/assets/**").addResourceLocations("/resources/assets/");
		registry.addResourceHandler("/ckeditor/**").addResourceLocations("/resources/ckeditor/");
		registry.addResourceHandler("/starters/**").addResourceLocations("/resources/starter/");
	}
	
	@Bean
	public DataSource getMysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		  dataSource.setUrl("jdbc:mysql://192.168.5.115:3306/paddycurrection");
		  dataSource.setUsername("paddy"); 
		  dataSource.setPassword("paddy123");
		  
		  System.out.println("Connected successfully"+dataSource.getUrl());

		return dataSource;
	} 
	
	@Bean
	public DataSource getMsSqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		  dataSource.setUrl("jdbc:sqlserver://192.168.5.150:3577;databaseName=2019-2020;user=sa;password=studio");

		  
			System.out.println("Connected successfully"+dataSource.getUrl());

		return dataSource;
	}
	
	
	@Bean
	public Dao_Admin getLogingDao() {
		
		return new Dao_Admin_impl(getMysqlDataSource()) ;
	}
	
	@Bean
	public Dao_Paddy getDao_Paddy() {
	
		return new Dao_Paddy_impl(getMsSqlDataSource());
	}
	
	

	
}
