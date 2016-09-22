package com.jl.sql;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jl.test.DBUtil;


public class CreateDatabase {
  
	JdbcTemplate  jdbcTemplate=null;
    DBUtil db=null;
    
    public CreateDatabase() throws Exception{
    	  db=new  DBUtil();	
    	  jdbcTemplate=db.getJdbcTemplate();
    }
   
    public void Close(){
    	 if(db!=null){
    		 db.closeDatabase();
    	 }
    }
	
	

	public void createTables(){
		String createInformation="CREATE TABLE if Not EXISTS `information` ("
				  +"`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT  COMMENT 'id号',"
				  +"`URLTIME` varchar(50) DEFAULT NULL COMMENT '时间',"
				  +"`CHANNEL` varchar(100) DEFAULT NULL COMMENT '采集来源',"
				  +"`URLNAME` varchar(500) DEFAULT NULL COMMENT 'url名称',"
				  +"`URLTITLE` varchar(500) DEFAULT NULL COMMENT 'url标题',"
				  +"`CONTENT` text DEFAULT NULL,"
				  +"`AUTHOR` varchar(100) DEFAULT NULL COMMENT '作者'"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 System.out.println("调用创建数据表函数");
		 jdbcTemplate.execute(createInformation); 
		 		
	 }
	
	
	public void createTraining(String name){
		String createProduct="CREATE TABLE if Not EXISTS `"+name+"` ("
				  +"`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT  COMMENT 'id号',"				 
				  +"`CONTENT` text DEFAULT NULL"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 System.out.println("调用创建数据表函数"+name);
		 jdbcTemplate.execute(createProduct); 
	}
	
	public void createClass(String name){
		String createOthers="CREATE TABLE if Not EXISTS `"+name+"` ("
				  +"`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT  COMMENT 'id号',"
				  +"`CONTENT` text DEFAULT NULL"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 System.out.println("调用创建数据表函数"+name);
		 jdbcTemplate.execute(createOthers); 
	}
	
    public void createQG(String name){
		
		String createTable="CREATE TABLE if Not EXISTS `"+name+"` ("
				  +"`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT  COMMENT 'id号',"		
				  +"`word` varchar(100) DEFAULT NULL ,"
				  +"`probility` double DEFAULT NULL"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 System.out.println("调用创建数据表函数"+name);
		 jdbcTemplate.execute(createTable);  
	}
    

    
    public void deleteTable(String name){
    	 String dele="DROP TABLE IF EXISTS "+name+";";
    	 System.out.println("调用删除数据表函数"+dele);
    	 jdbcTemplate.execute(dele); 
    }

	public static void main(String[] args) throws Exception{
		CreateDatabase b=new CreateDatabase();
        b.createTables();
        b.Close();
	}
}
