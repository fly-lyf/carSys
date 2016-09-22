package com.jl.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class testXML extends JFrame{
	

	public static void main(String[] args) throws Exception{
		
		 HashMap<String, Integer> performances = new HashMap<String, Integer>();
		 performances=getNodes("setConfig/result.xml", "/tree/brand[@name='比亚迪']", false);
		 System.out.println(performances);
		//getNodes("setConfig/result.xml", "/tree/brand[@name='奇瑞']", true);
		
	}


	//获取节点 修改
	 public static  HashMap<String, Integer> getNodes(String File, String xpath, boolean hasChild) {
		   
		    System.out.println("********调用了获取节点的程序**********");
		 
	        //获取属性值
	        SAXReader reader = new SAXReader();
	        File file = new File(File);
	        Document doc;
	        List<Element> brandOrTypeNodes;
	        HashMap<String, Integer> resultMap = new HashMap<>();
	        try {
	            doc = reader.read(file);
	            System.out.println("xpath路径："+xpath);           
	            brandOrTypeNodes = doc.selectNodes(xpath);            
	            for (Element brandOrTypeNode : brandOrTypeNodes) {
	            	
	                List<Element> list = new ArrayList<>();	                
	                list =brandOrTypeNode.selectNodes(xpath+"/performance");
	                Integer count;
	                for (int j = 0; j < list.size(); j++) {
	                    Element performanceNode = list.get(j);
	                    System.out.println("****"+performanceNode.getText());
	                    String weight = performanceNode.attributeValue("weight");
	                    String name = performanceNode.attributeValue("name");
	                    count = weight.split(";").length;
	                    if (resultMap.get(name) != null) {
	                        resultMap.put(name, resultMap.get(name) + count);
	                    } else {
	                        resultMap.put(name, count);
	                    }
	                }
	            }
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return resultMap;
	    }

}
