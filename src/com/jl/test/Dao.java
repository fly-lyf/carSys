package com.jl.test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.jl.domain.Comment;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Dao {
	
	    JdbcTemplate  jdbcTemplate=null;
	    DBUtil db=null;
	    public Dao() throws Exception{
	    	  db=new  DBUtil();	
	    	  jdbcTemplate=db.getJdbcTemplate();
	    }
	   
	    public void Close(){
	    	 if(db!=null){
	    		 db.closeDatabase();
	    	 }
	    }
	
	  public void create(String tableName){ //tb_test1  
		  String  tt="CREATE TABLE if Not EXISTS `"+tableName+"` ("
				  +"`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT  COMMENT 'id号',"
				  +"`URLTIME` varchar(50) DEFAULT NULL COMMENT '时间',"
				  +"`CHANNEL` varchar(100) DEFAULT NULL COMMENT '采集来源',"
				  +"`URLNAME` varchar(500) DEFAULT NULL COMMENT 'url名称',"
				  +"`URLTITLE` varchar(500) DEFAULT NULL COMMENT 'url标题',"
				  +"`CONTENT` text DEFAULT NULL,"
				  +"`AUTHOR` varchar(100) DEFAULT NULL COMMENT '作者'"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	        jdbcTemplate.execute(tt);  
	    }  
	      
	     
	   
	    public void save1(Comment comment) {  
	     
	        jdbcTemplate.update("insert into test1(URLTIME,CHANNEL) values(?,?)",   
	                new Object[]{comment.getURLTIME(),comment.getCHANNEL()});  
	    }  
	      
	   
	    public void save2(Comment comment) {  
	      
	        jdbcTemplate.update(  
	                "insert into test1(URLTIME,CHANNEL) values(?,?)",   
	                new Object[]{comment.getURLTIME(),comment.getCHANNEL()},   
	                new int[]{java.sql.Types.DATE,java.sql.Types.VARCHAR}  
	                );  
	    }  
	    
	    //避免sql注入  
	    public void save3(Comment comment) {  

	        jdbcTemplate.update("insert into test1(URLTIME,CHANNEL) values(?,?)",   
	                new PreparedStatementSetter(){             
						@Override
						public void setValues(java.sql.PreparedStatement ps) throws SQLException {
							 ps.setString(1,  comment.getURLTIME());
		                     ps.setString(2, comment.getCHANNEL()); 
							
						}  
	        });  
	          
	    } 
	    

	    //批量操作    适合于增、删、改操作  
	    public int[][] batchUpdate( List comments) {  
	    	 int[][] updateCounts = jdbcTemplate.batchUpdate(
	    		        "update test1 set  URLTIME= ?, CHANNEL= ? where id = ?",
	    		        comments,
	    		        100,         //表示批量操作的容量
	    		        new ParameterizedPreparedStatementSetter<Comment>() {

						@Override
						public void setValues(java.sql.PreparedStatement ps, Comment  comment) throws SQLException {
							    ps.setString(1,  comment.getURLTIME());
		    		            ps.setString(2, comment.getCHANNEL());
		    		            ps.setInt(   3,   comment.getId());
							
						}
	    		        });
	    	        System.out.println(updateCounts);
	    		    return updateCounts;
	    }  
	    
	    public void delete(Comment comment) {  
	       
	        jdbcTemplate.update(  
	                "delete from test1 where id = ?",   
	                new Object[]{comment.getId()},   
	                new int[]{java.sql.Types.INTEGER});  
	    }  
	    
	    
	    //查询语句
	    public Comment queryForObject(int id) {  
	        //方法有返回值  
	        return (Comment) jdbcTemplate.queryForObject("select * from test1 where id = ?",  
	                new Object[]{id},  
	                new int[]{java.sql.Types.INTEGER},   
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
	  
	    public List<Comment> queryForList(String channel) {  
	    	 List<Comment> comments= (List<Comment>)jdbcTemplate.query("select * from test1 where CHANNEL='"+channel+"'", new ResultSetExtractor() {  
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
	    
	    public int queryForInt1(){  
	        return jdbcTemplate.queryForInt("select count(*) from test1");  
	    }  
	    
	    public List<Comment> queryForList1(String channel,int[] paras) {  
	    	 List<Comment> comments= (List<Comment>)jdbcTemplate.query("select * from test1 where CHANNEL='"+channel+"' limit ?,?",
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
	    public int[][] batchInsert( List comments) {  
	    	 int[][] updateCounts = jdbcTemplate.batchUpdate(
	    		        "insert into test1(URLTIME,CHANNEL,URLNAME,URLTITLE,CONTENT,AUTHOR) values(?,?,?,?,?,?)",
	    		        comments,
	    		        100,         //表示批量操作的容量
	    		        new ParameterizedPreparedStatementSetter<Comment>() {

						@Override
						public void setValues(java.sql.PreparedStatement ps, Comment  comment) throws SQLException {
							    ps.setString(1, comment.getURLTIME());
		    		            ps.setString(2, comment.getCHANNEL());
		    		            ps.setString(3, comment.getURLNAME());
		    		            ps.setString(4, comment.getURLTITLE());
		    		            ps.setString(5, comment.getCONTENT());
		    		            ps.setString(6, comment.getAUTHOR());
							
						}
	    		        });
	    	        System.out.println(updateCounts);
	    		    return updateCounts;
	    }  
	    
	public static void main(String[] args) throws Exception {
		Dao jl=new Dao();
		jl.create("test1");
        Comment comment=new Comment();
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        String dd=date.toString();
        comment.setURLTIME(dd);
        comment.setCHANNEL("新浪微博3");
        jl.save1(comment);
        jl.save3(comment);
        Comment comment1=new Comment();
        java.sql.Date date1 = new java.sql.Date(new java.util.Date().getTime());
        String dd1=date1.toString();
        comment1.setURLTIME(dd1);
        comment1.setCHANNEL("新浪2222");
        comment1.setId(1);
        
        Comment comment2=new Comment();
        java.sql.Date date2 = new java.sql.Date(new java.util.Date().getTime());
        String dd3=date2.toString();
        comment2.setURLTIME(dd3);
        comment2.setCHANNEL("新浪888888");
        comment2.setId(2);
        
        List<Comment> li=new ArrayList<Comment>();
        li.add(comment1);
        li.add(comment2);
        jl.batchInsert(li);
       
        Comment comment3=new Comment();
        comment3.setId(2);
        jl.delete(comment3);
        
        Comment cc=jl.queryForObject(3);
        System.out.println(cc.getCHANNEL());
        
        int[] paras=new int[2];
        paras[0]=3;
        paras[1]=6;
        List ll=jl.queryForList1("新浪微博3",paras);
        for(int i=0;i<ll.size();i++){
        	Comment c=(Comment) ll.get(i);
        	System.out.println("**");
        	System.out.println(c.getCHANNEL());
        	System.out.println(c.getURLTIME());
        }
        
        int total=jl.queryForInt1();
        System.out.println(total);
        
        jl.Close();
        
        
	}

}
