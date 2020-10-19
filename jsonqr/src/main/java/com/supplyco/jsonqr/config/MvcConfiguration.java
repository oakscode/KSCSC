package com.supplyco.jsonqr.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.supplyco.jsonqr.dao.AdminDao;
import com.supplyco.jsonqr.dao.MasterDAO;
import com.supplyco.jsonqr.dao_impl.Admin_Dao_impl;
import com.supplyco.jsonqr.dao_impl.MasterDAO_impl;

@Configuration
@ComponentScan(basePackages="com.supplyco.jsonqr")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	@Bean
	public DataSource get808023() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		  dataSource.setUrl("jdbc:mysql://80.0.0.23:3306/einvoice");
		  dataSource.setUsername("dmsroot"); 
		  dataSource.setPassword("supplyco");
		  
		  System.out.println("80.0.0.23 Connected successfully"+dataSource.getUrl());

		return dataSource;
	}
	
	@Bean
	public DataSource get1921681002() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		  dataSource.setUrl("jdbc:mysql://192.168.100.2:3306/hodms");
		  dataSource.setUsername("hodbrroot"); 
		  dataSource.setPassword("root");
		  
		  System.out.println("192.168.100.2 Connected successfully"+dataSource.getUrl());

		return dataSource;
	}
	
	@Bean
	public DataSource get192168559() { //oracle
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		
		  dataSource.setUrl("jdbc:oracle:thin:@192.168.5.59:1521:SEIPS1");
		  dataSource.setUsername("seips"); 
		  dataSource.setPassword("seips2013");
		  
		  System.out.println("192.168.5.59 Connected successfully"+dataSource.getUrl());

		return dataSource;
	}
	
	
	
	@Bean
	public AdminDao get_Admin_Dao() {
		return new Admin_Dao_impl(get808023());
	}
	
	@Bean
	public MasterDAO get_MasterDAO() {
		return new MasterDAO_impl(get1921681002(),get192168559());
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();

		// Set the maximum allowed size (in bytes) for each individual file.
		resolver.setMaxUploadSize(5242880);// 5MB

		// You may also set other available properties.

		return resolver;
	}
	
	

	
}
