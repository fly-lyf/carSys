package com.jl.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TestTabbed extends JFrame{
   
	  
	private JTabbedPane tPane;
	private JPanel down,jp;
	
	public TestTabbed(){
		      down=new JPanel();
		      jp=new JPanel();
		      tPane = new JTabbedPane();  
		      tPane.setTabPlacement(JTabbedPane.TOP);  
		      tPane.setBorder(null);  
		      tPane.setUI(new SinaTabbedPaneUI());  
		      tPane.addTab("库存页面库存页面库存页面", down);  
		      
		      
		      jp.add(tPane);
		      this.add(jp);
		      this.setVisible(true);
		      this.setSize(500, 400);
	}
	
	public static void main(String args[]){
		TestTabbed tt=new TestTabbed();
	}
}
