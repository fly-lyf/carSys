package com.jl.classify;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jl.sql.commentService;
import com.jl.tools.common;
/**
* 训练集管理器
*/
public class TrainingDataManager 
{
    /**
    * 返回训练文本类别，这个类别就是目录名
    * @return 训练文本类别
    */
    public String[] getTraningClassifications() 
    {
    	String[] aa=new String[common.classnames.size()];
    	for(int i=0;i<common.classnames.size();i++){
    		aa[i]=(String)common.classnames.get(i);
    	}
       
    	return aa;
    }
    /**
    * 返回训练文本集中所有的文本数目
    * @return 训练文本集中所有的文本数目
    */
    public int getTrainingFileCount()
    {
        int ret = 0;
        for (int i = 0; i <common.classnames.size(); i++)
        {
            ret +=getTrainingFileCountOfClassification((String)common.classnames.get(i));
        }
        return ret;
    }
    /**
    * 返回训练文本集中在给定分类下的训练文本数目
    * @param classification 给定的分类
    * @return 训练文本集中在给定分类下的训练文本数目
    */
    public int getTrainingFileCountOfClassification(String classification)
    {
    	 int num=0;
    	 if(common.xunlian.get(classification)!=null){
    		 
    		 num=common.xunlian.get(classification);
    		 
    	 }else{
    	 System.out.println("第一次使用");
         commentService sc=new commentService();
    	 num=sc.getClassCoun(classification);
    	 common.xunlian.put(classification, num);
    	 sc.Close();
    	 }
         return num;
    }
    /**
    * 返回给定分类中包含关键字／词的训练文本的数目
    * @param classification 给定的分类
    * @param key 给定的关键字／词
    * @return 给定分类中包含关键字／词的训练文本的数目
    */
    public int getCountContainKeyOfClassification(String classification,String key) 
    {
        int ret = 0;
      
//       ret=sqlh.getCountContainKeyOfClassification(classification+"train", key);
        if(classification.equals("others")){
        	if(common.othersWord.containsKey(key))
        	 {
        		ret=common.othersWord.get(key);
        	 }
        	
        }
        if(classification.equals("product")){
        	if(common.productWord.containsKey(key))
        	{
        		ret=common.productWord.get(key);
        	}
        	
        }
        return ret;
    }
}
