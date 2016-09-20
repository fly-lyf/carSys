package com.jl.productSql;

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

import com.jl.domain.Product;
import com.jl.domain.Product;
import com.jl.test.DBUtil;

public class productService {
	
	JdbcTemplate  jdbcTemplate=null;
    DBUtil db=null;
    
    public productService(){
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
        return jdbcTemplate.queryForInt("select count(*) from product");  
    }  
    
    public List<Product> queryForPage(String sql,int[] paras){   	
    	List<Product> products= (List<Product>)jdbcTemplate.query(sql,
   			 new Object[]{paras[0],paras[1]},  
	             new int[]{java.sql.Types.INTEGER,java.sql.Types.INTEGER}, 
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
   
    public void updateProduct(int id,String name,String content) {  	      
        jdbcTemplate.update("update "+name+" set CONTENT='"+content+"' where id="+id);  
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
    		        "insert into product(CONTENT) values(?)",
    		        comments,
    		        1000,         //表示批量操作的容量
    		        new ParameterizedPreparedStatementSetter<Product>() {

					@Override
					public void setValues(java.sql.PreparedStatement ps, Product  comment) throws SQLException {
					
	    		            ps.setString(5, comment.getCONTENT());
	    		        
						
					}
    		        });
    	        System.out.println(updateCounts);
    		    return updateCounts;
    }  

    public void save1(Product comment) {  
	     
        jdbcTemplate.update("insert into product(CONTENT) values(?)",   
                new Object[]{comment.getCONTENT()});  
    }  
    
    
    public Product queryForObject() {  
        //方法有返回值  
        return (Product) jdbcTemplate.queryForObject("select * from product limit 1",  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Product comment  = new Product();  
                        comment.setId(rs.getInt("id"));                  
                        comment.setCONTENT(rs.getString("CONTENT"));
                        return comment;  
                    }

	
                }  
        );  
    }
    
    public Product queryForProductID(int id) { 
        //方法有返回值  
        return (Product) jdbcTemplate.queryForObject("select * from product where id="+id,  
                new RowMapper() {  	        	
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        Product comment  = new Product();  
                        comment.setId(rs.getInt("id"));                  
                        comment.setCONTENT(rs.getString("CONTENT"));
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
                "delete from product where id = ?",   
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
