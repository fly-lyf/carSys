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
    
     public Polarity queryForOneProduct(String table,String ci) {  
        //方法有返回值  
        return (Polarity) jdbcTemplate.queryForObject("select probility from "+table+" where word='"+ci+"'limit 1",  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Polarity po  = new Polarity();
                        po.setPolarity(rs.getDouble("probility"));                    
                        return po;  
                    }

	
                }  
        );  
     }

//	public Integer selectCP(String tag, String des1) {
//		Integer flag = 0;
//		try {
//			this.connect();
//			ps = ct.prepareStatement("select probility from xuanci where name1='"
//					+ tag + "' and name2='" + des1 + "' limit 1");
//			rs = ps.executeQuery();
//			if (rs.next()) {
//
//				flag = rs.getInt(1);
//				System.out.println(flag);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			this.close();
//		}
//		return flag;
//	}
}
