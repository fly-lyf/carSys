package com.jl.classify;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jl.tools.common;
import com.jl.domain.Product;
import com.jl.productSql.productService;
import com.jl.sql.CreateDatabase;
import com.jl.sql.CreateIndex;
import com.jl.sql.commentService;
import com.jl.view.infoCollection_view;
import com.jl.view.infoSummary_view;

public class beginclassify extends Thread{
	
	
	private  commentService sc;
	
	private volatile boolean flag;  //设置以便临时中断分类
	 
	public void setFlag(boolean flag) {
	        this.flag = flag;
	    }
	    
	public synchronized void stopCurrentThread() {
	        this.flag = false;
	    }
	
	@Override
	public void run(){
		
		     //完成分类
	        System.out.println("*****开始分类*****");           
            StopWordsHandler.set.clear();
            StopWordsHandler.ReadFile();
		    sc=new commentService();
		    common.othersWord.clear();
		    common.productWord.clear();
		    //将others表中数据加入到hashmap表中
		    String sql1="select * from otherstrain";
		    List li=sc.queryForAllProduct(sql1);
		    for(int i1=0;i1<li.size();i1++){
		    	Product p1=(Product) li.get(i1);
		    	//System.out.println(p1.getCONTENT());
		    	List wo=ChineseSpliter.split(p1.getCONTENT());
		    	String[] rd=StopWordsHandler.DropStopWords(wo);
		    	for(int x=0;x<rd.length;x++){
		    		String word1=rd[x].trim();
		    		if(!word1.equals("")&&word1!=null&&!word1.equals("　　")){
		    		if(common.othersWord.containsKey(rd[x])){
		    			int count=common.othersWord.get(rd[x])+1;
		    			common.othersWord.put(rd[x], count);
		    		}else{
		    			common.othersWord.put(rd[x], 1);
		    		}
		    		}
		    	}
		    }
		    System.out.println(common.othersWord);
		    //将product表中的数据加入到hashmap表中
		    String sql2="select * from producttrain";
		    List li1=sc.queryForAllProduct(sql2);
		    for(int i2=0;i2<li1.size();i2++){
		    	
		    	Product p2=(Product) li1.get(i2);
		    //	System.out.println(p2.getCONTENT());
		    	List wo1=ChineseSpliter.split(p2.getCONTENT());
		    	String[] rd1=StopWordsHandler.DropStopWords(wo1);
		    	for(int x1=0;x1<rd1.length;x1++){
		    		String word2=rd1[x1].trim();
		    		if(!word2.equals("")&&word2!=null&&!word2.equals("　　")){
		    		if(common.productWord.containsKey(word2)){
		    			int count1=common.productWord.get(word2)+1;
		    			common.productWord.put(word2, count1);
		    		}else{
		    			common.productWord.put(word2, 1);
		    		}
		    		}
		    	}
		    	
		    }
		    System.out.println(common.productWord);
		    		 
		   int i=0;		   
		   while(sc.queryForTotalRows()>0&&flag){			       
		    	Product info=sc.queryForOneProduct();
		    	System.out.println(info.getCONTENT());
		    	if(info.getCONTENT().trim().length()>6)
		    	{	
		    	  threadNative tn=new threadNative(info,sc);
		    	  tn.run();
   	              i++;
   	              infoSummary_view infoS=(infoSummary_view)common.infoS.get(2);
   	              infoS.flash(""+i+"");
		    	}
		        sc.deleteRow(info);
		       }
		    
		  
		    infoSummary_view info=(infoSummary_view)common.infoS.get(2);
            info.stopClassify();
            
		    sc.Close();
		    StopWordsHandler.set.clear();
	    	common.othersWord.clear();
	    	common.productWord.clear();
	    		 
   
	    		    
	}
   
      
      public static void main(String[] args) throws Exception{
    	  beginclassify bc=new beginclassify();
    	  Thread a=new Thread(bc);
    	  a.start();
  	}
}
