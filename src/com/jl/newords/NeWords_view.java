package com.jl.newords;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jl.tools.MyTools;
import com.jl.tools.common;

public class NeWords_view extends JFrame implements ActionListener{
	
	JPanel jp1,jp2;
	JLabel title,tishi;
	JTextField jtf;
	JButton tian,shan;
	JPanel jpl,jpr;
	
	JTable jta;
	JScrollPane jsp;
	
	public NeWords_view(){
		common.words.clear();
		try{
			FileReader r=new FileReader("setConfig/nWords.txt");
			BufferedReader br=new BufferedReader(r);
			String str;
			while((str=br.readLine())!=null){
				common.words.add(str);
			}
			br.close();
			r.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jp1=new JPanel();
	    title=new JLabel("车企名词表",new ImageIcon("image/reminder.png"),0);
	    title.setFont(MyTools.f6);
	    title.setForeground(Color.WHITE);
		jp1.add(title);
		
		jp2=new JPanel();
		tishi=new JLabel("修改:");
		tishi.setFont(MyTools.f6);
	    tishi.setForeground(Color.WHITE);
		jtf=new JTextField(10);
		tian=new JButton("添加");
		tian.setActionCommand("add");
		tian.addActionListener(this);
		shan=new JButton("删除");
		shan.setActionCommand("delete");
		shan.addActionListener(this);
		
		
		jp2.add(tishi);
		jp2.add(jtf);
		jp2.add(tian);
		jp2.add(shan);
		
		jp1.setBackground(new Color(89,194,230));
		jp2.setBackground(new Color(89,194,230));
		wordsModel wm=new wordsModel();
		
		jta=new JTable(wm);
		jsp=new JScrollPane(jta);
		
		jpl=new JPanel();
	    jpl.setPreferredSize(new Dimension(30,150));
		jpr=new JPanel();
		jpr.setPreferredSize(new Dimension(30, 150));
		jpl.setBackground(new Color(89,194,230));
		jpr.setBackground(new Color(89,194,230));
		
		this.setLayout(new BorderLayout());
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jsp,BorderLayout.CENTER);
		this.add(jp2,BorderLayout.SOUTH);
		this.add(jpl,BorderLayout.WEST);
		this.add(jpr,BorderLayout.EAST);
		this.setVisible(true);
		this.setSize(400,500);
		this.setBackground(new Color(89,194,230));
		
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width)/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height)/2;
		this.setLocation(x, y);
	}
  
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("add")){
			if(jtf.getText().equals("")){
				JOptionPane.showMessageDialog(this,"添加内容不得为空");
			}else{
				common.words.add(jtf.getText().trim());
				jtf.setText("");
				wordsModel wm=new wordsModel();
				jta.setModel(wm);
				try{
					FileWriter w=new FileWriter("setConfig/nWords.txt");
					BufferedWriter bw=new BufferedWriter(w);
					for(int x=0;x<common.words.size();x++){
						bw.write((String)common.words.get(x));
						bw.newLine();
					}
					bw.close();
					w.close();
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
			
		}
		if(e.getActionCommand().equals("delete")){
			int row = jta.getSelectedColumn();
			if(row == -1){
				JOptionPane.showMessageDialog(this,"请选择要删除的行!");
			}else{
				int num=jta.getSelectedRow();
				common.words.remove(num);
				wordsModel wm=new wordsModel();
				jta.setModel(wm);
				try{
					FileWriter w=new FileWriter("setConfig/nWords.txt");
					BufferedWriter bw=new BufferedWriter(w);
					for(int x=0;x<common.words.size();x++){
						bw.write((String)common.words.get(x));
						bw.newLine();
					}
					bw.close();
					w.close();
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
				
			}
		}
		
	}

}
