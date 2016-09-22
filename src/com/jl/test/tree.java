package com.jl.test;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.event.*;
import javax.swing.text.AbstractDocument.BranchElement;
public class tree extends JFrame{
    private JTree tree;
    
    public tree() throws Exception{
        setTree();
        this.add(tree);
        this.setSize(500,300);
    }
    
    public void setTree() throws Exception{
    	
        DefaultMutableTreeNode lx=new DefaultMutableTreeNode("车型");  //创建树根节点
        //创建一级节点
        SAXReader reader = new SAXReader();
        File file = new File("setConfig/result.xml");
        Document doc = reader.read(file);     
        List<Element> brand=doc.selectNodes("/tree/brand");
        for(int i=0;i<brand.size();i++){
        	Element node_l1=brand.get(i);
        	String l1_name=node_l1.attributeValue("name");
        	System.out.println(l1_name);
        	DefaultMutableTreeNode l1=new DefaultMutableTreeNode(l1_name);
        	List<Element> type=node_l1.selectNodes("/tree/brand[@name='"+l1_name+"']/type");
        	for(int j=0;j<type.size();j++){
        		Element node_l2=type.get(j);
        		String l2_name=node_l2.attributeValue("name");
        		System.out.println(l2_name);
        		DefaultMutableTreeNode l2=new DefaultMutableTreeNode(l2_name);
        		l1.add(l2);
        	}
        	lx.add(l1);
        }
        
        
        
        tree=new JTree(lx);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                if(node==null) return;
                if(node.isLeaf()){
                    leaf(node);
                }else{
                    branch(node);
                }
                
            }
        });
       } 
    
       private void leaf(DefaultMutableTreeNode node){ 
            System.out.println("叶节点：" + node.getUserObject()); 
       } 


       private void branch(DefaultMutableTreeNode node) { 
            System.out.println("枝节点：" + node.getUserObject()); 
       } 

        
      public static void main(String[] args) throws Exception { 
         JFrame frame = new tree(); 
         frame.setVisible(true); 
   } 
     
}