package com.jl.sql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.jl.domain.Comment;
import com.jl.domain.Product;
import com.jl.test.DBUtil;
import com.mysql.jdbc.PreparedStatement;

public class commentService {
	
	JdbcTemplate  jdbcTemplate=null;
    DBUtil db=null;
    
    public commentService(){
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
    
    public int queryForTotalRows(){  
        return jdbcTemplate.queryForInt("select count(*) from information");  
    }  
    
    public List<Comment> queryForPage(String sql,int[] paras){   	
    	List<Comment> comments= (List<Comment>)jdbcTemplate.query(sql,
   			 new Object[]{paras[0],paras[1]},  
	             new int[]{java.sql.Types.INTEGER,java.sql.Types.INTEGER}, 
   			 new ResultSetExtractor() {  
          @Override  
          public Object extractData(ResultSet rs) throws SQLException,   
                                       DataAccessException {  
                         List<Comment> comments= new ArrayList<Comment>();  
                         while (rs.next()) {  
                      	Comment comment  = new Comment();  
 	                        comment.setId(rs.getInt("id"));
 	                        comment.setURLTIME(rs.getString("URLTIME"));
 	                        comment.setCHANNEL(rs.getString("CHANNEl"));
 	                        comment.setURLNAME(rs.getString("URLNAME"));
 	                        comment.setURLTITLE(rs.getString("URLTITLE"));
 	                        comment.setCONTENT(rs.getString("CONTENT"));
 	                        comment.setAUTHOR(rs.getString("AUTHOR")); 
                            comments.add(comment);  
                         }  
                  return comments;  
              }  
            });
		return comments;  		
    	
    }
    
    public void update1(int id,String URLTIME,String name){
    	 jdbcTemplate.update("update "+name+" set URLTIME='"+URLTIME+"' where id="+id);
    }
    
    public void update2(int id,String CHANNEL,String name){
   	 jdbcTemplate.update("update "+name+" set CHANNEL='"+CHANNEL+"' where id="+id);
   }
    
    public void update3(int id,String URLNAME,String name){
      	 jdbcTemplate.update("update "+name+" set URLNAME='"+URLNAME+"' where id="+id);
      }
    
    public void update4(int id,String URLTITLE,String name){
     	 jdbcTemplate.update("update "+name+" set URLTITLE='"+URLTITLE+"' where id="+id);
     }
   
    public void update5(int id,String CONTENT,String name){
    	 jdbcTemplate.update("update "+name+" set CONTENT='"+CONTENT+"' where id="+id);
    }
    
    public void update6(int id,String AUTHOR,String name){
   	 jdbcTemplate.update("update "+name+" set AUTHOR='"+AUTHOR+"' where id="+id);
   }
    
    public int getRows(String sql,String schField,String schStr){
    	return jdbcTemplate.queryForInt(sql,new Object[]{schField,schStr},  
	             new int[]{java.sql.Types.VARCHAR,java.sql.Types.VARCHAR});
    	
    }
    
    public void saveTrain(String  name,String content) {  	      
        jdbcTemplate.update(  
                "insert into "+name+"(CONTENT) values(?)",   
                new Object[]{content},   
                new int[]{java.sql.Types.VARCHAR}  
                );  
    }  
    
    public void saveProduct(String content){
    	jdbcTemplate.update(  
                "insert into product (CONTENT) values(?)",   
                new Object[]{content},   
                new int[]{java.sql.Types.VARCHAR}  
                );  
    }
    
    public void upExecute(String sql){
    	jdbcTemplate.execute(sql);
    }
    
    
    //批量操作    适合于增、删、改操作  
    public int[][] batchUpdate( List comments) {  
    	 int[][] updateCounts = jdbcTemplate.batchUpdate(
    		        "insert into information(URLTIME,CHANNEL,URLNAME,URLTITLE,CONTENT,AUTHOR) values(?,?,?,?,?,?)",
    		        comments,
    		        100,         //表示批量操作的容量
    		        new ParameterizedPreparedStatementSetter<Comment>() {

				
					public void setValues(java.sql.PreparedStatement ps, Comment  comment) throws SQLException {
						    ps.setString(1,  comment.getURLTIME());
	    		            ps.setString(2, comment.getCHANNEL());
	    		            ps.setString(3, comment.getURLNAME());
	    		            ps.setString(4, comment.getURLTITLE());
	    		            ps.setString(5, comment.getCONTENT());
	    		            ps.setString(6, comment.getAUTHOR());
						
					}
    		        });
    	
    		    return updateCounts;
    }  
    
  

    public void save1(Comment comment) {
        jdbcTemplate.update("insert into information(URLTIME,CHANNEL,URLNAME,URLTITLE,CONTENT,AUTHOR) values(?,?,?,?,?,?)",   
                new Object[]{comment.getURLTIME(),comment.getCHANNEL(),comment.getURLNAME(),comment.getURLTITLE(),comment.getCONTENT(),comment.getAUTHOR()});  
    }  
    
    
    public Comment queryForObject() {  
        //方法有返回值  
        return (Comment) jdbcTemplate.queryForObject("select * from information limit 1",  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Comment comment  = new Comment();  
                        comment.setId(rs.getInt("id"));
                        comment.setURLTIME(rs.getString("URLTIME"));
                        comment.setCHANNEL(rs.getString("CHANNEl"));
                        comment.setURLNAME(rs.getString("URLNAME"));
                        comment.setURLTITLE(rs.getString("URLTITLE"));
                        comment.setCONTENT(rs.getString("CONTENT"));
                        comment.setAUTHOR(rs.getString("AUTHOR"));
                        return comment;  
                    }

	
                }  
        );  
    }
    
    public Product queryForOneProduct() {  
        //方法有返回值  
        return (Product) jdbcTemplate.queryForObject("select id,CONTENT from information limit 1",  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Product product  = new Product();
                        product.setId(rs.getInt("id"));  
                        product.setCONTENT(rs.getString("CONTENT"));                    
                        return product;  
                    }

	
                }  
        );  
    }
    
    public void deleteRow(Product product ) {  	       
        jdbcTemplate.update(  
                "delete from information where id = ?",   
                new Object[]{product.getId()},   
                new int[]{java.sql.Types.INTEGER});  
    }  
    
    public int getClassCoun(String name){  
    	
        return jdbcTemplate.queryForInt("select count(*) from "+name+"train");  
        
    }  
    
    
    public List<Product> queryForAllProduct(String sql){   	
    	List<Product> products= (List<Product>)jdbcTemplate.query(sql,
   			 new ResultSetExtractor() {  
          @Override  
          public Object extractData(ResultSet rs) throws SQLException,   
                                       DataAccessException {  
                         List<Product> products= new ArrayList<Product>();  
                         while (rs.next()) {  
                      	    Product product  = new Product();  
 	                        product.setId(rs.getInt("id"));                   
 	                        product.setCONTENT(rs.getString("CONTENT"));	                        
                            products.add(product);  
                         }  
                  return products;  
              }  
            });
		return products;  		
    	
    }
    
}
