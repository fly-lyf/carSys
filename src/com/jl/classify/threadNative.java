package com.jl.classify;
/***
 *  进行贝叶斯分类的线程
 */
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jl.domain.Product;
import com.jl.sql.commentService;



public class threadNative {
	private Product info;
	private commentService cs;
	
	public threadNative(Product info,commentService cs){
		this.info=info;
		this.cs=cs;
	}
	public void run(){
		
		
		try{
		System.out.println("负面信息id:"+info.getId());
		String text=info.getCONTENT();
		
		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
        String result = classifier.classify(text);//进行分类
        if(result.equals("不确定")){
        	System.out.println("抛掉一条负面信息");
        }else{
            System.out.println("分类结果："+result); 
            //将分类好的结果中属于产品的内容添加到产品评论表中
            if(result.equals("product")){
            
            	cs.saveProduct(text);
            	           	
            }
        
        }
        
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
    
	
	
}
