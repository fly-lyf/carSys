package com.jl.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;

public class t1 {

	public static void main(String[] args) throws Exception {
		try{
			 CustomDictionary.insert("M3", "nz 2");
			 CustomDictionary.insert("G3", "nz 2");
			 File f3=new File("Out/1.txt");
			 OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f3),"utf-8");
			 BufferedWriter bosw=new BufferedWriter(osw);
						
			  File file=new File("test");
			  File[] tempList = file.listFiles();
			  System.out.println("该目录下对象个数："+tempList.length);
			  for (int i = 0; i < tempList.length; i++) {
			   if (tempList[i].isFile()) {			  
			    File f= tempList[i].getAbsoluteFile();
				InputStreamReader isr=new InputStreamReader(new FileInputStream(f),"utf-8");   //编码格式
				BufferedReader bisr=new BufferedReader(isr);
				String str="";
				while((str=bisr.readLine())!=null){
					System.out.println(str);
				    List list=HanLP.segment(str);
				    String content="";
				    for(int t=0;t<list.size();t++){
				    	Term ss=(Term) list.get(t);
				    	content=content+ss+" ";
				    }
					bosw.write(content);
					bosw.newLine();
					bosw.flush();					
				}
				
				bisr.close();
				isr.close();
			    
			   }
			   
			  
			   
			  }	
			  bosw.close();
			  osw.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
