package com.jl.extract;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jl.domain.Product;
import com.jl.productSql.productService;
import com.jl.sql.CreateDatabase;
import com.jl.tools.common;
import com.jl.view.infoAnalysis_view;

import edu.fudan.util.exception.LoadModelException;

public class extract_de implements Runnable{
   
	private volatile boolean flag;  //设置以便临时中断分类

	 
	public void setFlag(boolean flag) {
	        this.flag = flag;
	    }
	    
	public synchronized void stopCurrentThread() {
	        this.flag = false;
	        
	    }
	
	
	public void run(){
		
		infoAnalysis_view iv=common.infoA.get(3);
		
		flush_view fv=new flush_view();   //设置一个刷新线程
		fv.setFlag(true);
		Thread a=new Thread(fv);
		a.start();
		common.thread_fl.put("fl", fv);
		
		try{	
		CreateDatabase cd= new CreateDatabase();		
		ArrayList tablename = extractXingNengTable();
		for(int tn=0;tn<tablename.size();tn++){
		   cd.createQG(tablename.get(tn).toString());
		}
		cd.Close();          //为每个产品属性创建一个极性词表
		
		
		IKAnalysisSplit.dictionary();  //加载外部词典
		
		productService ps=new productService();
		int count=ps.queryForTotalRows();
		int i=0;
		while(i<count&&flag){
			Product p=ps.queryForProductID(i+1);
			String text=p.getCONTENT();
			String[] aa=text.split("。");//拆分句子
			for(int j=0;j<aa.length;j++){
				if(aa[j].length() <= 3072){
				String splitsen=IKAnalysisSplit.IKAnalysis(aa[j]);//完成每个句子的分词
				ArrayList al=sortWords(splitsen);
				if(al!=null){
					for(int t=0;t<al.size();t++){
						String elem=(String)al.get(t);
						//划分为短句
						planA(elem,i+1);
				}		
				}
			}
		  }
			i++;
			
			iv.flush_count(i);
//			if(i%3==0){
//				iv.zongCenter.removeAll();
//				iv.flush_chart();
//				iv.add(iv.zongCenter, BorderLayout.CENTER);
//			
//			}
		}                   //
		
		}catch(Exception e){
			 e.printStackTrace();
		}
		
		iv.zongCenter.removeAll();
		iv.flush_chart();
		iv.add(iv.zongCenter, BorderLayout.CENTER);
		
	}
	
	//只含有汽车品牌名的句子
	public void planA_sub1(String elem,String brand,int number){		 
		 ArrayList xin=sortXinneng(elem,brand);
		 if(xin!=null){		 
		 for(int r=0;r<xin.size();r++)
		 {
		 String newSen=(String)xin.get(r);
		 ArrayList xinneng=extractXingNeng(brand);							 
		 for(int y=0;y<xinneng.size();y++){      //如果性能返回斯空，再查找品牌
			 int m=newSen.indexOf((String)xinneng.get(y));
			 if(m!=-1){
				 try{
				 String newSen1=newSen.replaceAll(" ", "");
				 double fo_quan=1.0;
				    int no=newSen1.indexOf("没有");    //查看句子中是否带有否定词“不”
				    int noh=newSen1.indexOf("不太");
				    if(noh!=-1){
				    	fo_quan=-0.5;
				    }
				    if(no!=-1){
				    	fo_quan=-1;
				    }			 
				 DepParser de=new DepParser();
				 String tag=(String)xinneng.get(y);   //评价的具体产品属性
					//句法分析
					String answer1=de.deparser(newSen1,tag );  //获得情感词
					if(!(answer1.equals(""))){
						  judgeP jp=new judgeP();
						  System.out.println("情感词："+ answer1);
						  jp.judgeS2(brand, tag, answer1,number,fo_quan);						
					}


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
		 
		 }
		 
		 }
	 }
	}

	
	//处理含有汽车品牌名和类型名的句子
   public void planA_sub2(String elem,String type,String brand,int number){
		 ArrayList xin=sortTypeXinneng(elem,brand,type);	
		 if(xin!=null){		 
		 for(int r=0;r<xin.size();r++)
		 {
		 String newSen=(String)xin.get(r);
		 ArrayList xinneng=extractTypeXingNeng(brand,type);
		 for(int y=0;y<xinneng.size();y++){      //如果性能返回斯空，再查找品牌			 
			 int m=newSen.indexOf((String)xinneng.get(y));
			 if(m!=-1){
			    try{
				    String newSen1=newSen.replaceAll(" ", "");
				    double fo_quan=1.0;
				    int no=newSen1.indexOf("没有");    //查看句子中是否带有否定词“不”	
				    int noh=newSen1.indexOf("不太");
				    if(noh!=-1){
				    	fo_quan=-0.5;
				    }
				    if(no!=-1){
				    	fo_quan=-1;
				    }
				    DepParser de=new DepParser();
					String tag=(String)xinneng.get(y);
					//句法分析
					String answer1=de.deparser(newSen1,tag );
					if(!(answer1.equals(""))){
						 judgeP jp=new judgeP();
						 System.out.println("情感词："+ answer1);
						 jp.judgeS1(brand,type,tag,answer1,number,fo_quan);
						
					}



				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
		 
		 }
		 
		 }
	 }
   }
	
    //只找到每个句子评价的汽车品牌和型号	
	public void planA(String elem,int number){	
		System.out.println("每个切分后短句子的内容："+elem);
		ArrayList brand=extractBrand();//										
		for(int x=0;x<brand.size();x++)
		{
		 int flag1=elem.indexOf((String)brand.get(x));
		 if(flag1!=-1)
		 {	
			 ArrayList al2=extractType((String)brand.get(x));
			 String flag="";
			 for(int r=0;r<al2.size();r++){
				 String type=(String)al2.get(r);
				 type=type.toUpperCase(); //防止出现的车型由于大小写的原因没有识别出来，所以将俩个都转换为大写识别
				 elem=elem.toUpperCase();
				 int em=elem.indexOf(type);
				 if(em!=-1){
					 flag=type;
					 break;
				 }
			 }
			 
			 if(flag.equals("")){
				 planA_sub1(elem,(String)brand.get(x),number);//只含有品牌名的句子
			 }else{
				 planA_sub2(elem,flag,(String)brand.get(x),number);//处理含有汽车类型名的句子。
			 }		
			 break;					
		 }
		   
		}	
		
	}
	
	
	
	
	public ArrayList sortTypeXinneng(String str,String brand,String type){
		ArrayList result=new ArrayList();
		ArrayList l1=new ArrayList();
		ArrayList num=new ArrayList();
		StringTokenizer fenxi=new StringTokenizer(str," ");
    	while(fenxi.hasMoreTokens()){
    		String ele=fenxi.nextToken();
    		l1.add(ele);
    	}
    	ArrayList al=extractTypeXingNeng(brand,type);
    	for(int i=0;i<l1.size();i++){
    		for(int j=0;j<al.size();j++){
    			String str1=(String)l1.get(i);
    			String str2=(String)al.get(j);
    			if(str1.equals(str2)){
    				num.add(i);
    			}
    		}
    	}//把所有性能的位置存储到num链表里
    	if(num.size()==0){
    		result=null;
    		return result;
    	}else if(num.size()==1)
    	{
    		int start=(Integer)num.get(0);
    		String back="";
    		for(int nn=0;nn<5;nn++){
    			if(start<l1.size()){
    			back=back+l1.get(start)+" ";
    			start++;
    			}else{
    				break;
    			}
    		}
    		result.add(back);
    		return result;
    	}
    	else{
    	        int start=(Integer)num.get(0);
    	        for(int x=1;x<num.size();x++){
    		        int end=(Integer)num.get(x);
    		        String back1="";
    		        for(int t=start;t<end;t++)
    		        {
    		        	back1=back1+(String)l1.get(t)+" ";
    		        }
    		        result.add(back1);
    		        start=end;
    	         }
    	         start=(Integer)num.get((num.size()-1));
    	         String back2="";
    	    		for(int nn=0;nn<5;nn++){
    	    			if(start<l1.size()){
    	    			back2=back2+l1.get(start)+" ";
    	    			start++;
    	    			}else{
    	    				break;
    	    			}
    	    		}
    	    		result.add(back2);
    	    		
    	    		return result;
    	}
	}
	
	
	public ArrayList sortXinneng(String str,String brand){
		ArrayList result=new ArrayList();
		ArrayList l1=new ArrayList();
		ArrayList num=new ArrayList();
		StringTokenizer fenxi=new StringTokenizer(str," ");
    	while(fenxi.hasMoreTokens()){
    		String ele=fenxi.nextToken();
    		l1.add(ele);
    	}
    	ArrayList al=extractXingNeng(brand);
    	for(int i=0;i<l1.size();i++){
    		for(int j=0;j<al.size();j++){
    			String str1=(String)l1.get(i);
    			String str2=(String)al.get(j);
    			if(str1.equals(str2)){
    				num.add(i);
    			}
    		}
    	}//把所有性能的位置存储到num链表里
    	if(num.size()==0){
    		result=null;
    		return result;
    	}else if(num.size()==1)
    	{
    		int start=(Integer)num.get(0);
    		String back="";
    		for(int nn=0;nn<5;nn++){
    			if(start<l1.size()){
    			back=back+l1.get(start)+" ";
    			start++;
    			}else{
    				break;
    			}
    		}
    		result.add(back);
    		return result;
    	}
    	else{
    	        int start=(Integer)num.get(0);
    	        for(int x=1;x<num.size();x++){
    		        int end=(Integer)num.get(x);
    		        String back1="";
    		        for(int t=start;t<end;t++)
    		        {
    		        	back1=back1+(String)l1.get(t)+" ";
    		        }
    		        result.add(back1);
    		        start=end;
    	         }
    	         start=(Integer)num.get((num.size()-1));
    	         String back2="";
    	    		for(int nn=0;nn<5;nn++){
    	    			if(start<l1.size()){
    	    			back2=back2+l1.get(start)+" ";
    	    			start++;
    	    			}else{
    	    				break;
    	    			}
    	    		}
    	    		result.add(back2);
    	    		
    	    		return result;
    	}
	}
	
	
	//把句子中含有多个汽车品牌分解成每个短句只有一个汽车品牌
	public ArrayList sortWords(String str){
		
		ArrayList result=new ArrayList();
		ArrayList l1=new ArrayList();  //存储所有的词
		ArrayList num=new ArrayList();
		
		StringTokenizer fenxi=new StringTokenizer(str," ");
    	while(fenxi.hasMoreTokens()){
    		String ele=fenxi.nextToken();
    		l1.add(ele);
    	}
    	ArrayList al=extractBrand();  //存储所有的汽车品牌名称
    	for(int i=0;i<l1.size();i++){
    		for(int j=0;j<al.size();j++){
    			String str1=(String)l1.get(i);
    			String str2=(String)al.get(j);
    			if(str1.equals(str2)){
    				num.add(i);
    			}
    		}
    	}//把所有车名的位置存储到num链表里
    	
    	if(num.size()==0){     //如果句子中没有车名
    		result=null;
    		
    	}
    	if(num.size()==1)   //如果句子中有一个车名
    	{
    		int start=(Integer)num.get(0);
    		String back="";
    		for(int nn=0;nn<80;nn++){
    			if(start<l1.size()){
    			back=back+l1.get(start)+" ";
    			start++;
    			}else{
    				break;
    			}
    		}
    		result.add(back);
    		
    	}
    	
        if(num.size()>=2){
    	        int start=(Integer)num.get(0);
    	        for(int x=1;x<num.size();x++){
    		        int end=(Integer)num.get(x);
    		        String back1="";
    		        for(int t=start;t<end;t++)
    		        {
    		        	back1=back1+(String)l1.get(t)+" ";
    		        }
    		        result.add(back1);
    		        start=end;
    	         }
    	         start=(Integer)num.get((num.size()-1));
    	         String back2="";
    	    		for(int nn=0;nn<80;nn++){
    	    			if(start<l1.size()){
    	    			back2=back2+l1.get(start)+" ";
    	    			start++;
    	    			}else{
    	    				break;
    	    			}
    	    		}
    	    		result.add(back2);
    	    		
    	    		
    	}
        
        return result;
	}
	
	public ArrayList extractXingNeng(String brand){
		ArrayList list1=new ArrayList();
		try {
            SAXReader reader=new SAXReader();
            File file=new File("setConfig/sim.xml");
            Document doc;
            doc = reader.read(file);
            List<Element>  list=doc.selectNodes("//word");
            for(int i=0;i<list.size();i++)
            {
            	Element sen1=(Element)list.get(i);
            	String text=sen1.getTextTrim();
            	
             	list1.add(text);
            }

        }
        catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
		return list1;
	}
	
	public ArrayList getTong(String name){
		ArrayList tong=new ArrayList();
		try{
		SAXReader reader=new SAXReader();
        File file=new File("setConfig/sim.xml");
        Document doc;
        doc = reader.read(file);
        List<Element>  list=doc.selectNodes("/words/performance[@name='"+name+"']/word");
        for(int i=0;i<list.size();i++){
        	Element e=(Element)list.get(i);       	
        	String t=e.getText();     	
        	tong.add(t);
        	
        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return tong;
	}
	
	public ArrayList extractTypeXingNeng(String brand,String type){
		ArrayList list1=new ArrayList();
		try {
            SAXReader reader=new SAXReader();
            File file=new File("setConfig/sim.xml");
            Document doc;
            doc = reader.read(file); 
            List<Element>  list=doc.selectNodes("//word");
            for(int i=0;i<list.size();i++)
            {
            	Element sen1=(Element)list.get(i);
            	String text=sen1.getTextTrim();
             	list1.add(text);
            }
        }
        catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
		return list1;
	}
	
	//读取table字段属性
	public ArrayList extractXingNengTable() {
		// 获取属性值
		SAXReader reader = new SAXReader();
        File file=new File("setConfig/sim.xml");
		Document doc;
		List<Element> result_Nodes;
		ArrayList list1 = new ArrayList();
		try {
			doc = reader.read(file);
			 List<Element> result= doc.selectNodes("/words/performance");
					 //searchNodes("//words",doc.getRootElement());
	            for(Element subnode:result){                
	            	String str=subnode.attributeValue("table");
	                list1.add(str);
	            }
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return list1;
	}
	
	
	public ArrayList extractBrand(){
		ArrayList list1=new ArrayList();
		try {
            SAXReader reader=new SAXReader();
            File file=new File("setConfig/CarOntology.xml");
            Document doc;
            doc = reader.read(file);            
            List<Element> result=searchNodes("/tree/company/brand",doc.getRootElement());
            for(Element subnode:result){                
            	String str=subnode.attributeValue("name");
                //System.out.println(str);
                list1.add(str);
            }
           
        }
        catch (DocumentException e) {
            // TODO Auto-generated catch bloc	k
            e.printStackTrace();
        }		
		return list1;
	}
	
	public ArrayList extractType(String brand){
		ArrayList list1=new ArrayList();
		try {
            SAXReader reader=new SAXReader();
            File file=new File("setConfig/CarOntology.xml");
            Document doc;
            doc = reader.read(file); 
            List<Element> result_nodes=searchNodes("/tree/company/brand[@name='"+brand+"']",doc.getRootElement());
            for(Element result:result_nodes){                
                List list=searchNodes("./type",result);               
                for(int j=0;j<list.size();j++)
                {
                	Element sen1=(Element)list.get(j);
                	String text=sen1.attributeValue("name");
                	list1.add(text);
                }
                
            }
        }
        catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
		return list1;
	}
	
	 public static List<Element> searchNodes(String xpath,Node node){
	        xpath=xpath.replace("/", "/boss:");
	        HashMap xmlMap = new HashMap();  
	        xmlMap.put("boss","http://www.inktomi.com/");
	        XPath x = node.createXPath(xpath);
	        x.setNamespaceURIs(xmlMap);           
	        return x.selectNodes(node);
	    }
	    public static Node searchSingleNode(String xpath,Node node){
	        xpath=xpath.replace("/", "/boss:");	         
	        HashMap xmlMap = new HashMap();  
	        xmlMap.put("boss","http://www.inktomi.com/");
	        XPath x = node.createXPath(xpath);
	        x.setNamespaceURIs(xmlMap);           
	        return x.selectSingleNode(node);
	    }
	    
	    
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  extract_de a=new extract_de();
		  Thread b=new Thread(a);
		  b.start();

	}

}

