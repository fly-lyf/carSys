package com.jl.classify;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;


/**
* 中文分词器
*/
public class ChineseSpliter 
{
    /**
    * 对给定的文本进行中文分词
    * @param text 给定的文本
    * @param splitToken 用于分割的标记,如"|"
    * @return 分词完毕的文本
    */
    public static List split(String text)
    {
       
        List list=HanLP.segment(text);
        List result=new ArrayList();
	    String content="";
	    for(int t=0;t<list.size();t++){
	    	Term ss=(Term) list.get(t);
	    	 String[] word=ss.toString().split("\\/");	    	 
	    	 result.add(word[0]);
	    }
        return result;
    }
    
    public static void main(String[] args) throws Exception{
  	      ChineseSpliter cs=new ChineseSpliter();
  	      List result=cs.split("南京大学信息管理学院");
  	      
  	      System.out.println(result);
  	      
  	      
	}
}