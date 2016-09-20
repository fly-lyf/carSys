package com.jl.extract;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.jl.domain.Product;
import com.jl.productSql.productService;
import com.jl.sql.CreateDatabase;

import edu.fudan.util.exception.LoadModelException;

public class extract_de implements Runnable{
   
	
	public void run(){
		
		
		try{	
		CreateDatabase cd= new CreateDatabase();		
		ArrayList tablename = extractXingNengTable();
		for(int tn=0;tn<tablename.size();tn++){
		   cd.createQG(tablename.get(tn).toString());
		}
		cd.Close();          //为每个产品属性创建一个极性词表
		
		productService ps=new productService();
		int count=ps.queryForTotalRows();
		for(int i=0;i<count;i++){
			Product p=ps.queryForProductID(i+1);
			String text=p.getCONTENT();
			String[] aa=text.split("。");//拆分句子
			for (int j = 0; j < aa.length ; j++) {
				System.out.println("拆分后的句子aa:" + aa[j]);
			}
			for(int j=0;j<aa.length;j++){
				if(aa[j].length() <= 3072){
				String splitsen=IKAnalysisSplit.IKAnalysis(aa[j]);//完成每个句子的分词
				ArrayList al=sortWords(splitsen);
				if(al!=null){
					for(int t=0;t<al.size();t++){
						String elem=(String)al.get(t);
						//划分为短句
						planA(elem,i);
				}		
				}
			}
		}
		}
		}catch(Exception e){
			 e.printStackTrace();
		}
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
//				 String newSen1=newSen.replaceAll(" ", "");
//				 System.out.println(newSen1);
//				 
//				 DepParser de=new DepParser();
				String tag=(String)xinneng.get(y);
				String[] tokens = newSen.split(" ");
				System.out.println(newSen);
				 try {
					 if(tokens.length < 5){
						 for(int i=0;i<tokens.length-1;i++){
							    String des = tokens[i+1];
//								System.out.println(tag+":"+des);//将结果添加回CarOntology
								judgeP jp=new judgeP();
								jp.judgeS2(brand, tag, des,number);
						 }
					 }else if(tokens.length >= 5){
						 for(int i=0;i<tokens.length-1;i++){
							    String des = tokens[i+1];
//								System.out.println(tag+":"+des);//将结果添加回CarOntology
								judgeP jp=new judgeP();
								jp.judgeS2(brand, tag, des,number);
						 }
					 }
					//句法分析
//					String answer1=de.deparser(newSen1,tag );
//					if(!(answer1.equals(""))){
//						String[] tt=answer1.split(" ");
//						if(tt.length==2){
//						String des1=tt[0];
//						String des2=tt[1];
//						if(des1.equals("")||des1==null){
//							System.out.println(des2);
//						}else{
//							if(des2.equals("")||des2==null){
//								
//							}else{
//								if(des1.equals(des2)){									
//									System.out.println(tag+":"+des1);//将结果添加回CarOntology
//									judgeP jp=new judgeP();
//									jp.judgeS2(brand, tag, des1,number);
//									
//								}else{
//									//处理
//									String des=selectPro(tag,des1,des2);									
//									System.out.println(tag+":"+des);//将结果添加回CarOntology
//									judgeP jp=new judgeP();
//									jp.judgeS2(brand, tag, des,number);
//									
//								}
//							}
//						}
//					}
//					}


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
		 
		 }
		 
		 }
	 }
	}

	
	//处理含有汽车品牌名的句子
   public void planA_sub2(String elem,String type,String brand,int number){
		 ArrayList xin=sortTypeXinneng(elem,brand,type);	
		 if(xin!=null){		 
		 for(int r=0;r<xin.size();r++)
		 {
		 String newSen=(String)xin.get(r);
		 ArrayList xinneng=extractTypeXingNeng(brand,type);
		 //System.out.println(xinneng);
		 for(int y=0;y<xinneng.size();y++){      //如果性能返回斯空，再查找品牌
			 int m=newSen.indexOf((String)xinneng.get(y));
			 if(m!=-1){		
//				 String newSen1=newSen.replaceAll(" ", "");
//				 DepParser de=new DepParser();
					String tag=(String)xinneng.get(y);
					String[] tokens = newSen.split(" ");
					System.out.println(newSen);
					 try {
						 if(tokens.length < 5){
							 for(int i=0;i<tokens.length-1;i++){
								    String des = tokens[i+1];
//									System.out.println(tag+":"+des);//将结果添加回CarOntology
									judgeP jp=new judgeP();
									jp.judgeS2(brand, tag, des,number);
							 }
						 }else if(tokens.length >= 5){
							 for(int i=0;i<tokens.length-1;i++){
								 String des = tokens[i+1];
//									System.out.println(tag+":"+des);//将结果添加回CarOntology
									judgeP jp=new judgeP();
									jp.judgeS2(brand, tag, des,number);
							 }
						 }
		
//				 try {
//					String tag=(String)xinneng.get(y);
//					String answer1=de.deparser(newSen1,tag);
//					
//					if(!(answer1.equals(""))){
//						String[] tt=answer1.split(" ");
//						if(tt.length==2){
//						String des1=tt[0];
//						String des2=tt[1];
//						if(des1.equals("")||des1==null){
//							System.out.println(des2);
//						}else{
//							if(des2.equals("")||des2==null){
//								
//							}else{
//								if(des1.equals(des2)){									
//									System.out.println(brand+"-"+type+":"+tag+"-"+des1);//将结果添加回CarOntology
//									judgeP jp=new judgeP();
//									jp.judgeS1(brand, type, tag, des1,number);
//									
//								}else{
//									String des=selectPro(tag,des1,des2);								
//									System.out.println(brand+"-"+type+":"+tag+"-"+des);//将结果添加回CarOntology
//									judgeP jp=new judgeP();
//									jp.judgeS1(brand, type, tag, des,number);
//									
//								}
//							}
//						}
//					}
//					}


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
	
	
	
//	public String selectPro(String tag,String des1,String des2){   //选择词的极性词
//		String des="";
//		SqlHelper sh=new SqlHelper();
//		Integer d1=sh.selectCP(tag, des1);
//		Integer d2=sh.selectCP(tag, des2);
//		if(d1==0&&d2==0){
//			des=des1;
//		}else{
//			if(d1>=d2){
//				des=des1;
//			}else{
//				des=des2;
//			}
//		}
//		return des;
//	}
	
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
		ArrayList l1=new ArrayList();
		ArrayList num=new ArrayList();
		StringTokenizer fenxi=new StringTokenizer(str," ");
    	while(fenxi.hasMoreTokens()){
    		String ele=fenxi.nextToken();
    		l1.add(ele);
    	}
    	ArrayList al=extractBrand();
    	for(int i=0;i<l1.size();i++){
    		for(int j=0;j<al.size();j++){
    			String str1=(String)l1.get(i);
    			String str2=(String)al.get(j);
    			if(str1.equals(str2)){
    				num.add(i);
    			}
    		}
    	}//把所有车名的位置存储到num链表里
    	if(num.size()==0){
    		result=null;
    		return result;
    	}else if(num.size()==1)
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
    	    		for(int nn=0;nn<80;nn++){
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

//            List<Element> result_nodes=searchNodes("/tree/company/brand[@name='"+brand+"']",doc.getRootElement());
//            for(Element result:result_nodes){                
//                List list=searchNodes("./performance",result);               
//                for(int j=0;j<list.size();j++)
//                {
//                	Element sen1=(Element)list.get(j);
//                	String text=sen1.attributeValue("name");
//                	ArrayList tong=getTong(text);
//                	list1.addAll(tong);
//                }
//                
//            }
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

