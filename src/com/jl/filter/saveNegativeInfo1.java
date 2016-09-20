package com.jl.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.jl.domain.Comment;
import com.jl.sql.commentService;
import com.jl.tools.common;


public class saveNegativeInfo1{
	
	String path;
	
	public saveNegativeInfo1(String path){
		this.path=path;
	}
	
	public void run(){
		try{
			List<Comment> list=new ArrayList<Comment>();
			System.out.println(Thread.currentThread().getName() + "正在执行。。。");
			File f=new File(path);
            System.out.println("path:"+path);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
			BufferedReader bfr=new BufferedReader(isr);
			bfr.readLine();
			String str;
			int flag=0;
			String content="";			
			String[] params=new String[6];
			  while((str=bfr.readLine())!=null){				  
				  if(str.startsWith("<IR_URLTIME>")){
					  str=str.substring(13, str.length());
					  params[0]=str;
					  continue;
				  }
				  if(str.startsWith("<IR_CHANNEL>")){
					  str=str.substring(13,str.length());
					  params[1]=str;
					  continue;
				  }
				  if(str.startsWith("<IR_URLNAME>")){
					  str=str.substring(13, str.length());
					  params[2]=str;
					  continue;
				  }
				  if(str.startsWith("<IR_URLTITLE>")){
					  str=str.substring(14, str.length());
					  params[3]=str;
					  continue;
				  }
				  if(str.startsWith("<IR_AUTHORS>")){
					  str=str.substring(13, str.length());					 
					  params[5]=str;
					  continue;
				  }
				  if(str.startsWith("<IR_CONTENT>")){
					  str=str.substring(13, str.length());
					  content=str;
					  flag=1;
					  continue;					  
				  }
				  if(str.startsWith("<REC>")){
					  flag=0;
					  params[4]=content;					  
					  Comment c=tiqu(params);
					  if(c!=null){
						  list.add(c);
					  }
					  content="";
					  continue;
				  }
				  if(flag==1){					 
					  content=content+str;
				  }
				
			   }
			  params[4]=content;
			  Comment c=tiqu(params);
			  if(c!=null){
				  list.add(c);
			  }
			  
			  commentService sc=new commentService();
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
    			int x=str.indexOf((String)common.comp.get(i));
    			if(x!=-1){
    				for(int j=0;j<common.neg.size();j++)
    				{
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