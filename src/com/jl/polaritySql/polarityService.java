package com.jl.polaritySql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jl.domain.Polarity;
import com.jl.test.DBUtil;

public class polarityService {
	
	JdbcTemplate  jdbcTemplate=null;
    DBUtil db=null;
    
    public polarityService(){
    	try{
    	  db=new  DBUtil();	
    	  jdbcTemplate=db.getJdbcTemplate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
   
    public void Close(){
    	 if(db!=null){
    		 db.closeDatabase();
    	 }
    }
    
	
     public void savePolarity(String tablename,String word,double probility){
    	jdbcTemplate.update(  
                "insert into "+tablename+" (word,probility) values(?,?)",   
                new Object[]{word,probility},   
                new int[]{java.sql.Types.VARCHAR,java.sql.Types.DOUBLE}  
                );  
     }
    
     public int queryForTotalRows(String table,String word){  
         return jdbcTemplate.queryForInt("select count(*) from "+table+" where word='"+word+"'");  
     }  
    
     public Polarity queryForOneProduct(String table,String  word) {  
    	 System.out.println("select * from "+table+" where word='"+word+"' limit 1");
        //方法有返回值  
        return (Polarity) jdbcTemplate.queryForObject("select * from "+table+" where word='"+word+"' limit 1",  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Polarity po  = new Polarity();
                        po.setId(rs.getInt("id"));
                        po.setPolarity(rs.getDouble("probility"));                    
                        return po;  
                    }

	
                }  
        );  
     }


}
