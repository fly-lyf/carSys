package com.jl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jl.productSql.productService;
import com.jl.sql.commentService;
import com.jl.tools.MyTools;
import com.jl.tools.common;


public class juti_view extends JFrame implements ActionListener{
    JPanel jp,jp1,jp2;
    JScrollPane jsp;
	JTextArea ja;
	JButton jb1,jb2;
	JLabel jl1;
	JPanel pl,pr;
	int id;
	String name;
	int col;
	public juti_view(String str,int id,String name,int col){
		this.id=id;
		this.name=name;
		this.col=col;
		
		jp2=new JPanel();
		jl1=new JLabel("修改信息面板",new ImageIcon("image/note.png"),0);
		jl1.setFont(MyTools.f6);
	    jl1.setForeground(Color.WHITE);
		jp2.add(jl1);
				
		
		ja=new JTextArea();
		 ja.setFont(new Font("标楷体", Font.PLAIN, 14));  
	     ja.setLineWrap(true);// 激活自动换行功能  
	     ja.setWrapStyleWord(true);// 激活断行不断字功能  
	     ja.setText(str);
	     ja.setEditable(false);
	    jsp=new JScrollPane(ja);
		
		
		jb1=new JButton("添加修改");
		jb1.addActionListener(this);
		jb1.setActionCommand("edit");
		jb2=new JButton("修改完成");
		jb2.addActionListener(this);
		jb2.setActionCommand("done");
		
		jp1=new JPanel();
		jp1.add(jb1);
		jp1.add(jb2);
		
		
		pl=new JPanel();
	    pl.setPreferredSize(new Dimension(30,150));
		pr=new JPanel();
		pr.setPreferredSize(new Dimension(30, 150));
		
		jp=new JPanel(new BorderLayout());
		jp.add(jp1,BorderLayout.SOUTH);
		jp.add(jsp,BorderLayout.CENTER);
		jp.add(jp2,BorderLayout.NORTH);
		jp.add(pl,BorderLayout.WEST);
		jp.add(pr,BorderLayout.EAST);
		
		jp1.setBackground(new Color(89,194,230));
		jp2.setBackground(new Color(89,194,230));	
		jp.setBackground(new Color(89,194,230));
		pl.setBackground(new Color(89,194,230));
		pr.setBackground(new Color(89,194,230));
		
		this.add(jp);
		this.setVisible(true);
		this.setSize(400,300);
		this.setBackground(new Color(89,194,230));
		
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
		this.setLocation(x, y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("edit")){
			ja.setEditable(true);
		}
		if(e.getActionCommand().equals("done")){
			ja.setEditable(false);
		    if(name.equals("product")){
		    	System.out.println("name="+name);
		    	//完成更新
		    	productService ps=new productService();
		    	String content=ja.getText();
		    	//System.out.println("content="+content);
		    	ps.updateProduct(id, name, content);
		    	ps.Close();
		    	//刷新页面
		    	infoSummary_view info=common.infoS.get(2);
		    	info.editFlush();
		    	
		    }if(name.equals("information")){
		    	System.out.println("name="+name);
		    	if(col==1){
		    	  commentService cs=new commentService();
		    	  String content=ja.getText();
		    	  cs.update1(id, content, name);
		    	  cs.Close();
		    	  
		    	}if(col==2){
		    		  commentService cs=new commentService();
			    	  String content=ja.getText();
			    	  cs.update2(id, content, name);
			    	  cs.Close();
		    	}if(col==3){
		    		  commentService cs=new commentService();
			    	  String content=ja.getText();
			    	  cs.update3(id, content, name);
			    	  cs.Close();
		    	}if(col==4){
		    		  commentService cs=new commentService();
			    	  String content=ja.getText();
			    	  cs.update4(id, content, name);
			    	  cs.Close();
		    	}if(col==5){
		    		 commentService cs=new commentService();
			    	  String content=ja.getText();
			    	  cs.update5(id, content, name);
			    	  cs.Close();
		    	}if(col==6){
		    		  commentService cs=new commentService();
			    	  String content=ja.getText();
			    	  cs.update6(id, content, name);
			    	  cs.Close();
		    	}
		    	infoCollection_view info=common.mp.get(1);
		    	info.Flush();
		    }
		}
		
	}

	public static void main(String[] args){
		
	}
}
