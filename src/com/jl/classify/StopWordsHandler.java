package com.jl.classify;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.io.*;
/**
* 停用词处理器
* @author phinecos 
* 
*/
public class StopWordsHandler 
{
	public static HashSet<String> set=new HashSet<String>();
	
	public static void ReadFile(){
		
		try{ 
			 File file=new File("setConfig/words.txt");
			 InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8"); 
			 BufferedReader br=new BufferedReader(isr);
			String str=null;
			while((str=br.readLine())!=null){
			  set.add(str);
			}
			br.close();
			isr.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		
		}
	}
       
    public static boolean IsStopWord(String word)
    {
    	
        if(set.contains(word)){
        	//System.out.println("文中含有："+word+"**********");
        	return true;
        }else{
            return false;
        }
    }
    
    public static String[] DropStopWords(List oldWords)
    {
    	//去除重复的词语
    	HashSet set=new HashSet();
    	for(int i=0;i<oldWords.size();i++){
    		set.add(oldWords.get(i));
    	}
    	Iterator<String> iterator=set.iterator();		
        Vector<String> v1 = new Vector<String>();
        while(iterator.hasNext()){
            String str=iterator.next().toString();
            if(StopWordsHandler.IsStopWord(str)==false)
            {//不是停用词
                v1.add(str);
            }
        }
        String[] newWords = new String[v1.size()];
        v1.toArray(newWords);
        return newWords;
    }
}
