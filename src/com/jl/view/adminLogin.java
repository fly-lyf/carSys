package com.jl.view;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;

import java.io.*;
public class adminLogin extends JDialog implements ActionListener,MouseListener,MouseMotionListener{

	RButton jb1,jb2;
	JTextField id;
	JPasswordField password;
	
	Font f=new Font("宋体",Font.BOLD,12);
	Font f1=new Font("宋体",Font.BOLD,14);
	private int xx,yy;
	private boolean isDraging=false;
	public static void main(String []args)
	{
		adminLogin ul=new adminLogin();
	}
	
	public adminLogin()
	{
		
		
		this.setLayout(null);
		//创建一个BackImage对象
		BackImage bi=new BackImage();
		bi.setBounds(0,0,315,445);
		bi.addMouseListener(this);
		bi.addMouseMotionListener(this);
		
		id=new JTextField(15);
		id.setBounds(87,258,170,22);
		id.setFont(f1);
		id.setBorder(BorderFactory.createEmptyBorder());
		id.setOpaque(false);
		
		password=new JPasswordField(15);
		password.setBounds(87,312,170,22);
		password.setFont(f);
		password.setBorder(BorderFactory.createEmptyBorder());
		password.setOpaque(false);
		
		
		jb1=new RButton("Login");
		jb1.setFont(f);
		
		jb1.setBounds(52,360,100,30);
		jb1.addActionListener(this);

		jb2=new RButton("Cancel");
		jb2.setFont(f);
		jb2.setBounds(177,360,100,30);
		jb2.addActionListener(this);
		
		this.add(id);
		this.add(password);
		this.add(jb1);
		this.add(jb2);
		this.add(bi);
		this.setUndecorated(true);
		this.setSize(315,445);
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-200,height/2-150);
		this.setVisible(true);
	}
	
	class BackImage  extends JPanel
	{
		Image im;
		public BackImage()
		{
			try {
				im=ImageIO.read(new File("image_login/login.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(im,0,0,315,445,this);
		}
		
	}
    
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==jb1)
		{
			    //增加用户登录和密码输入
			    String s1=id.getText();
			    String s2=password.getText();
			    if(s1.equals("admin")&&s2.equals("123456")){
				  try {
					Window1 w1=new Window1("1");
				  } catch (Exception e1) {
					
					    e1.printStackTrace();
				  }
				  //关闭登录界面
				  this.dispose();
			    }
			
		  
		}
		else if(e.getSource()==jb2)
		{			
			System.exit(0);
		}
      
	}

	public void mouseDragged(MouseEvent e) {
		if(isDraging)
		{
			int left=getLocation().x;
			int top=getLocation().y;
			setLocation(left+e.getX()-xx,top+e.getY()-yy);
		}
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		isDraging=true;
		xx=e.getX();
		yy=e.getY();
		
	}

	public void mouseReleased(MouseEvent e) {
		isDraging=false;
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

