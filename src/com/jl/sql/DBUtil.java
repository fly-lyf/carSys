package com.jl.sql;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {

	
	private static JdbcTemplate jdbcTemplate=null;
	private static Connection conn = null;
    private static  DataSource  ds=null; 

	public DBUtil() throws Exception{
		
		 ApplicationContext filecontext = new ClassPathXmlApplicationContext("config.xml");
	     ds =  (DataSource) filecontext.getBean("dataSource");
	    // System.out.println("datasource : "+ds);		
	     jdbcTemplate = new JdbcTemplate(ds);  
	    
	
	}
	public static void  getDS(){
		 ApplicationContext filecontext = new ClassPathXmlApplicationContext("config.xml");
	     ds =  (DataSource) filecontext.getBean("dataSource");
	    // System.out.println("datasource : "+ds);
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			getDS();
			return new JdbcTemplate(ds);
		}
		return jdbcTemplate;
	}

	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn  = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeDatabase(){
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(jdbcTemplate !=null){
			try {
				jdbcTemplate = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(ds !=null){
			try {
				ds = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String[] args) throws Exception{
//		 DBUtil db=new  DBUtil();
//		 JdbcTemplate aa=db.getJdbcTemplate();
//		 aa.execute("create table "+"test2"+" (id integer,user_name varchar(40),password varchar(40))"); 
//		 String sql="select * from test2";
//		 
//		
//	}
	
	
}

