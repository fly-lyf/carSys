package com.jl.extract;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.jl.tools.common;


public class IKAnalysisSplit {

	
	public static void dictionary() throws Exception{
		    File f= new File("setConfig/word_dictionary.txt");
			InputStreamReader isr=new InputStreamReader(new FileInputStream(f),"utf-8");   //编码格式
			BufferedReader bisr=new BufferedReader(isr);
			String str="";
			while((str=bisr.readLine())!=null){
				CustomDictionary.add(str);
			}
			bisr.close();
			isr.close();
	}
	
	public static String IKAnalysis(String str) {
                 String content="";
                 try {
                	 List list=HanLP.segment(str);
                	 for(int i=0;i<list.size();i++){
                		 Term ss=(Term) list.get(i);
 				    	 content=content+ss.word+" ";
                	 }
                 }catch(Exception e){
                	 e.printStackTrace();
                 }
                 return content;

   }
	
	public static String IKAnalysis_stopwords(String str){
		
		String content="";
        try {
        	 List list=HanLP.segment(str);
        	 for(int i=0;i<list.size();i++){
        		 Term ss=(Term) list.get(i);
        		 if(!(common.stopwords.contains(ss.word))){
        			 content=content+ss.word+" ";
        		 }
			      
        	 }
              } catch (Exception e) {
          // TODO Auto-generated catch block
                 e.printStackTrace();
                 }
          //System.out.println(sb.toString());
         // System.out.println(content);
          return content;
	}
}
