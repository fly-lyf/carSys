package com.jl.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.jdbc.core.RowMapper;

import com.jl.domain.Comment;
import com.jl.domain.Product;
import com.jl.sql.commentService;
import com.jl.tools.common;




public class saveNegativeInfo2 {
	String path;
	
	public saveNegativeInfo2(String path){
		this.path=path;
	}
	
	public void run(){
		try{
			List<Comment> list=new ArrayList<Comment>();
			System.out.println(Thread.currentThread().getName() + "正在执行。。。");
			File f=new File(path);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
			BufferedReader bfr=new BufferedReader(isr);
			bfr.readLine();
			String str;
			String content="";
			String sql="INSERT INTO `classify`.`negativeinfo` (`URLTIME`, `CHANNEL`, `URLNAME`, `URLTITLE`, `CONTENT`, `AUTHOR`) VALUES (?, ?, ?, ?, ?, ?)";
			String[] params=new String[6];
			  while((str=bfr.readLine())!=null){				  
				  if(str.startsWith("<IR_STATUS_CONTENT>")){
					  str=str.substring(20, str.length());
					  params[4]=str;	
					  continue;
				  }
				  if(str.startsWith("<IR_CREATED_DATE>")){
					  str=str.substring(18,str.length());
					  params[0]=str;				 
					  continue;
				  }
				  if(str.startsWith("<IR_SITENAME>")){
					  str=str.substring(14, str.length());
					  params[1]=str;					
					  continue;
				  }
				  if(str.startsWith("<IR_URLNAME>")){
					  str=str.substring(13, str.length());
					  params[2]=str;					 
					  continue;
				  }
				  if(str.startsWith("<IR_SCREEN_NAME>")){
					  str=str.substring(17, str.length());					 
					  params[5]=str;				  
					  continue;
				  }
				  if(str.startsWith("<REC>")){
					  params[3]="新浪微博";
					  Comment c=tiqu(params);
					  if(c!=null){
						 list.add(c);
					  }
					  continue;
				  }
				
			   }
			  params[3]="新浪微博";
			  Comment c=tiqu(params);
			  if(c!=null){
				 list.add(c);
			  }
			  
			  commentService sc=new commentService();
			  System.out.println("list的容量："+list.size());
			  sc.batchUpdate(list);
			  sc.Close();
			  
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	  public static  Comment tiqu(String[] params) throws Exception{
	      	
	    	String con=params[4];//params[4]存放内容部分 
	    	if(con==null||con.equals("")){
	    		System.out.println("内容为空");
	    	}else{
	    	String newcon="";    	
	    	StringTokenizer fenxi=new StringTokenizer(con,"。！");
	      	while(fenxi.hasMoreTokens()){
	    		String str=fenxi.nextToken();
	    		for(int i=0;i<common.comp.size();i++){
//	    			System.out.println((String)common.comp.get(i));
	    			int x=str.indexOf((String)common.comp.get(i));
	    			if(x!=-1){
	    				for(int j=0;j<common.neg.size();j++)
	    				{
//	    					System.out.println((String)common.neg.get(j));
	    					int y=str.indexOf((String)common.neg.get(j));
	    					if(y!=-1){
	    				     newcon=newcon+str+"。";
	    				     break;
	    					}
	    				}
	    				break;
	    			}
	    			
	    		}
	    	}
	    	params[4]=newcon;
	    	if(!newcon.equals("")){
	    		if(params[4].length()>20000){
	 			   System.out.println("太长，抛出="+params[4].length());	
	 			}else{
	 		              //添加到列表中
	 				Comment c=new Comment();
	 				c.setURLTIME(params[0]);
	 				c.setCHANNEL(params[1]);
	 				c.setURLNAME(params[2]);
	 				c.setURLTITLE(params[3]);
	 				c.setCONTENT(params[4]);
	 				c.setAUTHOR(params[5]);
	 				if(c.getCONTENT().length()>5500){
	 				   System.out.println("c的="+c.getCONTENT().length());
	 				   return c;
	 				}else{
	 				   return c;
	 				}
	 			}
	    	}
	    	
	    	}
	    	
	   
		  return null;
	    	
	    }
}
