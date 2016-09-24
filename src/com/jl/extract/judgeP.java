package com.jl.extract;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jl.domain.Polarity;
import com.jl.polaritySql.polarityService;


public class judgeP {
  
	public String[] getXinneng(String str){
		String[] xinneng=new String[2];
		try{
		SAXReader reader=new SAXReader();
        File file=new File("setConfig/sim.xml");
        Document doc;
        doc = reader.read(file);
        Element  list=(Element) doc.selectSingleNode("/words/performance[word='"+str+"']/word");
        if(list!=null){
        Element p=list.getParent();
        String t=p.attributeValue("name");
        String table=p.attributeValue("table");
        xinneng[0]=t;
        xinneng[1]=table;
        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return xinneng;
	}
	
	public void judgeS1(String brand,String type,String tag,String des,int number,double fo_quan){
		try{
			String[] tt=this.getXinneng(tag);
			if(tt!=null){
				String newtag=tt[0];//得到评价性能名
				String table=tt[1];//得到极性表名
				polarityService  ps=new polarityService();
				int count= ps.queryForTotalRows(table, des);
				if(count!=0){
			    Polarity po=ps.queryForOneProduct(table, des);	
				double value=po.getPolarity()*fo_quan;
				System.out.println("词极性度："+value);
				ps.Close();
				if(value<-0.05)
				{
					SAXReader reader=new SAXReader();
			        File file=new File("setConfig/result.xml");
			        Document doc;
			        doc = reader.read(file);
			        Node node=doc.selectSingleNode("/tree/brand[@name='"+brand+"']");
			        if(node==null){
			        	Element newStu=DocumentHelper.createElement("brand");		
			    		//如何给元素添加属性
			    		newStu.addAttribute("name", brand);	
			    		Element newStu_type=DocumentHelper.createElement("type");
			    		newStu_type.addAttribute("name",type);
			    		Element newP=DocumentHelper.createElement("performance");
			    		newP.addAttribute("name", newtag);
			    		newP.addAttribute("weight", number+";");
			    		newP.setText("1");
			    		newStu_type.add(newP);
			    		newStu.add(newStu_type);
			    		doc.getRootElement().add(newStu);
			    		OutputFormat output=OutputFormat.createPrettyPrint();
			    		output.setEncoding("utf-8");//输出的编码utf-8
			    		XMLWriter writer = new XMLWriter(
			    	        		new FileOutputStream(file),output);
			    		writer.write( doc );
			    		writer.flush();
			            writer.close();
			                
			        }else{
			        	Node  node_t=node.selectSingleNode("/tree/brand[@name='"+brand+"']/type[@name='"+type+"']");
			        	if(node_t!=null){
			        		
			        		Node node_xin=node_t.selectSingleNode("/tree/brand[@name='"+brand+"']/type[@name='"+type+"']/performance[@name='"+newtag+"']");
			        		if(node_xin!=null){
			  
			        	            String num=node_xin.getText();
			        	            String weight=((Element) node_xin).attributeValue("weight");
			        	            
			        	            	int t=Integer.parseInt(num);
			        	            	t=t+1;
			        	            	node_xin.setText(t+"");
			        	            
			        	            weight=weight+number+";";
			        	            ((Element)node_xin).setAttributeValue("weight", weight);
			        	            OutputFormat output=OutputFormat.createPrettyPrint();
			        	            output.setEncoding("utf-8");
			        	            XMLWriter writer=new XMLWriter(new FileOutputStream(file),output);
			        	            writer.write(doc);
			        	            writer.flush();
			        	            writer.close();
			        			
			        		}else{
			        			
			        			Element newCar_per=((Element)node_t).addElement("performance");
			        			newCar_per.addAttribute("name", newtag);
			        			newCar_per.addAttribute("weight",number+";");
			        			newCar_per.setText("1");
			        			OutputFormat output=OutputFormat.createPrettyPrint();
			            		output.setEncoding("utf-8");//输出的编码utf-8
			            		 XMLWriter writer = new XMLWriter(
			            	        		new FileOutputStream(file),output);
			            		writer.write( doc );
			            		writer.flush();
			                    writer.close();
			        		}
			        		
			        	}else{
			        		
			        		Element newCar_type=((Element)node).addElement("type");
			        		newCar_type.addAttribute("name", type);
			        		Element newXinneng=newCar_type.addElement("performance");
			        		newXinneng.addAttribute("name", newtag);
			        		newXinneng.addAttribute("weight", number+";");
			        		newXinneng.setText("1");
			        		OutputFormat output=OutputFormat.createPrettyPrint();
			        		output.setEncoding("utf-8");//输出的编码utf-8
			        		 XMLWriter writer = new XMLWriter(
			        	        		new FileOutputStream(file),output);
			        		writer.write( doc );
			        		writer.flush();
			                writer.close();
			        	}
			        	
			        }
				}
	            
			}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public void judgeS2(String brand,String tag,String des,int number,double fo_quan){
		try{
			String[] tt=this.getXinneng(tag);
			if(tt!=null){
				String newtag=tt[0];//得到评价性能名
				String table=tt[1];//得到极性表名
				polarityService  ps=new polarityService();
				int count=ps.queryForTotalRows(table, des);
				if(count!=0){
			    Polarity po=ps.queryForOneProduct(table, des);	
				double value=po.getPolarity()*fo_quan;
				System.out.println("词极性度："+value);
				ps.Close();
				if(value<-0.05)
				{
					SAXReader reader=new SAXReader();
			        File file=new File("setConfig/result.xml");
			        Document doc;
			        doc = reader.read(file);
			        Node node=doc.selectSingleNode("/tree/brand[@name='"+brand+"']");
			        if(node==null){
			        	Element newStu=DocumentHelper.createElement("brand");		
			    		//如何给元素添加属性
			    		newStu.addAttribute("name", brand);	
			    		newStu.addAttribute("xmlns", "http://www.inktomi.com/");  //增加命名空间
			    		
			    		Element newP=DocumentHelper.createElement("performance");
			    		newP.addAttribute("name", newtag);
			    		newP.addAttribute("weight", number+";");
			    		newP.setText("1");			    		
			    		newStu.add(newP);
			    		doc.getRootElement().add(newStu);
			    		OutputFormat output=OutputFormat.createPrettyPrint();
			    		output.setEncoding("utf-8");//输出的编码utf-8
			    		XMLWriter writer = new XMLWriter(
			    	        		new FileOutputStream(file),output);
			    		writer.write( doc );
			    		writer.flush();
			            writer.close();
			             			            
			        }else{
			        		
			        		Node node_xin=node.selectSingleNode("/tree/brand[@name='"+brand+"']/performance[@name='"+newtag+"']");
			        		if(node_xin!=null){
			  
			        	            String num=node_xin.getText();
			        	            String weight=((Element) node_xin).attributeValue("weight");
			        	          
			        	            	int t=Integer.parseInt(num);
			        	            	t=t+1;
			        	            	node_xin.setText(t+"");
			        	            
			        	            weight=weight+number+";";
			        	            ((Element)node_xin).setAttributeValue("weight", weight);
			        	            OutputFormat output=OutputFormat.createPrettyPrint();
			        	            output.setEncoding("utf-8");
			        	            XMLWriter writer=new XMLWriter(new FileOutputStream(file),output);
			        	            writer.write(doc);
			        	            writer.flush();
			        	            writer.close();
			        			
			        		}else{
			        			
			        			Element newCar_per=((Element)node).addElement("performance");
			        			newCar_per.addAttribute("name", newtag);
			        			newCar_per.addAttribute("weight",number+";");
			        			newCar_per.setText("1");
			        			OutputFormat output=OutputFormat.createPrettyPrint();
			            		output.setEncoding("utf-8");//输出的编码utf-8
			            		 XMLWriter writer = new XMLWriter(
			            	        		new FileOutputStream(file),output);
			            		writer.write( doc );
			            		writer.flush();
			                    writer.close();
			        		}
			        		
			        	
			        	
			        }
				}
	            
			}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
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
}
