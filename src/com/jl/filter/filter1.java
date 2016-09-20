package com.jl.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.jl.tools.common;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jl.sql.*;
import com.jl.view.infoCollection_view;
import com.jl.view.infoSummary_view;
public class filter1 implements Runnable{
	File f;
	public void  run(){	
		 //先把两张过滤词表加载
		 common.comp.clear();
		 common.neg.clear();
		 
		 try{
			 FileReader r1=new FileReader("setConfig/nWords.txt");
			 BufferedReader br1=new BufferedReader(r1);
			 String str1="";
			 while((str1=br1.readLine())!=null){
				 common.comp.add(str1);
			 }
			 br1.close();
			 r1.close();
			 
			 FileReader r2=new FileReader("setConfig/negativeW.txt");
			 BufferedReader br2=new BufferedReader(r2);
			 String str2="";
			 while((str2=br2.readLine())!=null){
				 common.neg.add(str2);
			 }
			 br2.close();
			 r2.close();
		
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
	     File[] t=tree(f);//读取文件列表
	     
	     if(t!=null){
	    	
	         for(int i=0;i<t.length;i++){
	        	 System.out.println(t.length);
                        //判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
                       if(t[i].isDirectory()){
                          System.out.println(t[i].getName()+"\tttdir:"+i);
                        }
                      else{   
             	           saveNegativeInfo1 sa=new saveNegativeInfo1(t[i].getAbsolutePath());
             	          try {
        					  Thread.sleep(2000);
        					  infoCollection_view in=common.mp.get(1);
        			    	  in.tishi();
        			    	   			    	  
        				      } catch (InterruptedException e) {
        				    	 
        					      e.printStackTrace();
        				       }  
             	               sa.run();             
                          }          
               }  
	         
               
                       System.out.println("结束了！");  
                       
                       try {
						Thread.sleep(3000);
					  } catch (InterruptedException e1) {
						
						e1.printStackTrace();
					   }
                       CreateIndex cindex=new CreateIndex();
                       cindex.createIndex();
                       
                       infoCollection_view infoview=common.mp.get(1);
                       try {
   						Thread.sleep(5000);
   					   } catch (InterruptedException e1) {
   						
   						  e1.printStackTrace();
   					   }   
                       infoview.refresh(0);     //设置0,使得取消打开面板juti_view
                       infoSummary_view info=common.infoS.get(2);
           			   info.setFlag(0);          			   
           			   info.ableButton();
           			   
                       try{
                    	   FileWriter w=new FileWriter("setConfig/nb.txt");
                    	   BufferedWriter bw=new BufferedWriter(w);
                    	   bw.write("1");
                    	   bw.close();
                    	   w.close();
                    	   
                       }catch(Exception e){
                    	   e.printStackTrace();
                       }
                      
               
	     }
	    
	   
	     
	}
	
  public filter1(File f){
	  this.f=f;
  }
	public static File[] tree(File f){
        
        if(!f.isDirectory()){
            System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
            return null;
        }
        else{
        	
            File[] t = f.listFiles();
            return t;
           
           
        }
 
    }
	

}
