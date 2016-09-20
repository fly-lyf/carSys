package com.jl.filter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import com.jl.tools.common;
import com.jl.sql.CreateIndex;
import com.jl.sql.commentService;
import com.jl.view.infoCollection_view;
import com.jl.view.infoSummary_view;

public class filter2 implements Runnable{
	File f;
	public void  run(){
		System.out.println("微博过滤调用");
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
		
	     File[] t=tree(f);
	     if(t!=null){
	      
	         for(int i=0;i<t.length;i++){
                        //判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
                       if(t[i].isDirectory()){
                          System.out.println(t[i].getName()+"\tttdir");
                        }
                      else{   
             	           saveNegativeInfo2 sa=new saveNegativeInfo2(t[i].getAbsolutePath());            	           
             	           sa.run(); 
             	          
        				    infoCollection_view in=common.mp.get(1);
        			    	in.tishi();
        			    	    	  
        				    
                          }          
               }         
             
                       System.out.println("结束了！"); 
                       CreateIndex cindex=new CreateIndex();
                       cindex.createIndex();
                       
                       infoCollection_view infoview=common.mp.get(1);
                       try {
   						Thread.sleep(5000);
   					} catch (InterruptedException e1) {
   						// TODO Auto-generated catch block
   						e1.printStackTrace();
   					}
                       infoview.refresh(0);  //设置0,使得取消打开面板juti_view
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
  public filter2(File f){
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
