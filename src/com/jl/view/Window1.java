package com.jl.view;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import com.jl.tools.common;
import com.jl.tools.ImagePanel;
import com.jl.tools.MyTools;

public class Window1 extends JFrame implements ActionListener,MouseListener,WindowListener{

	
	//定义需要的组件
	Image titleIcon,timebg;
	
	
	ImageIcon jmm1_icon1,jmm1_icon2,jmm1_icon3,jmm1_icon4,jmm1_icon5;
	
	
	//定义需要的5个面板
	JPanel p1,p2,p3,p4,p5;
	JLabel timeNow;
	JLabel p1_lab1, p1_lab2, p1_lab3, p1_lab4, p1_lab5, p1_lab6, p1_lab7,p1_lab8,p1_lab9;
	//给p2面板定义JLabel
	JLabel p2_lab1,p2_lab2;
	//javax.swing中的timer可以定时的触发Action 事件
	javax.swing.Timer t;
	
	//ImagePanel p1_imgPanel;
	ImagePanel top;
	JLabel top_l;
	JPanel p1_imgPanel;
	JSplitPane jsp1;
	
	CardLayout cardp2,cardp3;
	//初始化菜单
	
	String userId;
	String id;

	JLabel logo;
	
	JPanel ip1,ip2,ip3,ip4,ip5,ip6,ip7;
	

	//初始化中间的Panel
	public void initCenter(String id) throws Exception
	{
			
		//处理p1面板
				p1=new JPanel(new BorderLayout());
				p1.setBorder(BorderFactory.createLineBorder(new Color(89,194,230)));
				Cursor mycursor=new Cursor(Cursor.HAND_CURSOR);
				this.p1_imgPanel=new JPanel();
				this.p1_imgPanel.setBackground(new Color(89,194,230));
				this.p1_imgPanel.setLayout(new GridLayout(9,1));
				logo=new JLabel(new ImageIcon("image_left/logo.png"));
				p1_imgPanel.add(logo);
				
				p1_lab2=new JLabel("信息过滤和和导入", new ImageIcon("image_left/logo0.png"),0);
				p1_lab2.setFont(MyTools.f7);
				p1_lab2.setEnabled(true);
				p1_lab2.setCursor(mycursor);
				p1_lab2.setForeground(Color.white);
				//注册监听
				p1_lab2.addMouseListener(this);
				p1_imgPanel.add(p1_lab2);
				
				p1_lab3=new JLabel("产品信息分类汇总", new ImageIcon("image_left/logo1.png"),0);
				p1_lab3.setFont(MyTools.f7);
				p1_lab3.setEnabled(true);
				p1_lab3.setCursor(mycursor);
				p1_lab3.setForeground(Color.white);				
				p1_lab3.addMouseListener(this);
				p1_imgPanel.add(p1_lab3);
				
				p1_lab4=new JLabel("产品信息舆情分析", new ImageIcon("image_left/logo2.png"),0);
				p1_lab4.setFont(MyTools.f7);
				p1_lab4.setEnabled(true);
				p1_lab4.setCursor(mycursor);
				p1_lab4.setForeground(Color.white);
				p1_lab4.addMouseListener(this);
				p1_imgPanel.add(p1_lab4);
/*				
				p1_lab5=new JLabel("负面信息统计分析", new ImageIcon("image_left/logo3.png"),0);
				p1_lab5.setFont(MyTools.f3);
				p1_lab5.setEnabled(true);
				p1_lab5.setCursor(mycursor);
				p1_lab5.setForeground(Color.white);
				p1_lab5.addMouseListener(this);
				p1_imgPanel.add(p1_lab5);
			
				p1_lab6=new JLabel("产品缺陷舆情监测", new ImageIcon("image_left/logo4.png"),0);
				p1_lab6.setFont(MyTools.f3);
				p1_lab6.setEnabled(true);
				p1_lab6.setCursor(mycursor);
				p1_lab6.setForeground(Color.white);
				p1_lab6.addMouseListener(this);
				p1_imgPanel.add(p1_lab6);
				
				
				p1_lab7=new JLabel("品牌声誉舆情监测", new ImageIcon("image_left/logo5.png"),0);
				p1_lab7.setFont(MyTools.f3);
				p1_lab7.setEnabled(true);
				p1_lab7.setCursor(mycursor);
				p1_lab7.setForeground(Color.white);
				p1_lab7.addMouseListener(this);
				p1_imgPanel.add(p1_lab7);
				
				p1_lab8=new JLabel("品牌丑闻溢出监测", new ImageIcon("image_left/logo6.png"),0);
				p1_lab8.setFont(MyTools.f3);
				p1_lab8.setEnabled(true);
				p1_lab8.setCursor(mycursor);
				p1_lab8.setForeground(Color.white);
				p1_lab8.addMouseListener(this);
				p1_imgPanel.add(p1_lab8);
*/					
				p1.add(this.p1_imgPanel);
				
				//处理p2,p3,p4
				p4=new JPanel(new BorderLayout());
				this.cardp2=new CardLayout();
				p2=new JPanel(this.cardp2);
				p2.setBackground(new Color(79,95,111));
				p2_lab1=new JLabel(new ImageIcon("image_left/left.jpg"));
				p2_lab1.addMouseListener(this);
				p2_lab2=new JLabel(new ImageIcon("image_left/right.jpg"));
				p2_lab2.addMouseListener(this);
				p2.add(p2_lab1,"0");
				p2.add(p2_lab2,"1");
				
				this.cardp3=new CardLayout();
				p3=new JPanel(this.cardp3);
				
				Image zhu_image=null;
				try {
					zhu_image = ImageIO.read(new File("image/welcome.jpg"));
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				ImagePanel ip=new ImagePanel(zhu_image);
				p3.add(ip,"0");
				//对p3添加几个JPanel
				
				//信息过滤的初步设置 所使用的面板
				infoCollection_view ip1=new infoCollection_view();
				common.mp.put(1, ip1);
				p3.add(ip1,"1");
			
                //负面信息分类和汇总 所使用的面板
				infoSummary_view ip2=new infoSummary_view();
				common.infoS.put(2, ip2);
				p3.add(ip2,"2");
//				
				//负面信息专题分析 所使用的面板
				infoAnalysis_view ip3=new infoAnalysis_view ();
				p3.add(ip3,"3");
/*				
				//负面信息统计分析
				infoAnalysis_view ip4=new infoAnalysis_view();
				p3.add(ip4,"4");
				
				//产品缺陷舆情监测
				defect_view ip5=new defect_view();
				p3.add(ip5,"5");
				
				//品牌声誉舆情监测
				fame_view ip6=new fame_view();
				p3.add(ip6,"6");
				
				//品牌丑闻溢出监测
				overflow_view ip7=new overflow_view();
				p3.add(ip7,"7");
				
				//实现聊天的功能模块
				this.id=id;
*/								
				p4.add(p2,"West");
				p4.add(p3,"Center");
				//做一个拆分窗口，分别存放p1和p4
				jsp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,p1,p4);
				
				//指定左边的面板占多大像素
				jsp1.setDividerLocation(240);
				jsp1.setDividerSize(0);
				jsp1.setBorder(BorderFactory.createLineBorder(new Color(79,95,111)));
				
	}
	public static void main(String[] args) throws Exception {
                   // TODO Auto-generated method stub
		Window1 w1=new Window1("2");

	}
	
	public Window1(String userId) throws Exception
	{	
		this.userId=userId;
		
		//创建组件
		try {
			titleIcon=ImageIO.read(new File("image/title.png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//调用初始化菜单函数
		this.initCenter(userId);
		
		
		//处理p5
		p5=new JPanel(new BorderLayout());
		//创建timer
		t=new Timer(1000,this);
		t.start();
		timeNow=new JLabel("当前时间： "+Calendar.getInstance().getTime().toLocaleString()+"   ");
		timeNow.setFont(MyTools.f1);
		timeNow.setForeground(Color.white);
		try {
			timebg=ImageIO.read(new File("image_bottom/time_bg.jpg"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		ImagePanel ip1=new ImagePanel(timebg);
		ip1.setLayout(new BorderLayout());
		ip1.add(timeNow,"East");
		
		p5.add(ip1);
		p5.setBorder(BorderFactory.createLineBorder(new Color(79,95,111)));
		Container ct=this.getContentPane();
		
		top=new ImagePanel(timebg);
		top.setLayout(new FlowLayout());
		top_l=new JLabel("汽车行业上市公司舆情预警系统",new ImageIcon("image_left/logo_top.png"),0);
		top_l.setForeground(Color.white);
		top_l.setFont(MyTools.f5);
		top.add(top_l);
//		top.setPreferredSize(new Dimension(10, 70));//this.getContentPane().getWidth()
		ct.add(top,"North");
		ct.add(jsp1,"Center");
		ct.add(p5,"South");
		
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		//关闭窗口是退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		this.setIconImage(titleIcon);
		this.setTitle("舆情监测系统");
		this.setSize(w-160,h-100);
//		this.setLocation(80,50);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		this.timeNow.setText("当前时间： "+Calendar.getInstance().getTime().toLocaleString()+"   ");
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.p2_lab1)
		{
			this.jsp1.setDividerLocation(0);
			this.cardp2.show(p2,"1");
		}
		else if(e.getSource()==this.p2_lab2)
		{
			this.jsp1.setDividerLocation(240);
			this.cardp2.show(p2,"0");
		}
		
		//添加panel的响应
		else if(e.getSource()==this.p1_lab2)
		{
			this.cardp3.show(p3,"1");
			
			
		}
		else if(e.getSource()==this.p1_lab3)
		{
			this.cardp3.show(p3, "2");

		}
		else if(e.getSource()==this.p1_lab4)
		{
			this.cardp3.show(p3, "3");
		
		}
		else if(e.getSource()==this.p1_lab5)
		{
			this.cardp3.show(p3, "4");
			
		}
		else if(e.getSource()==this.p1_lab6)
		{
			this.cardp3.show(p3, "5");
			
		}
		else if(e.getSource()==this.p1_lab7)
		{
			this.cardp3.show(p3, "6");
		}
		else if(e.getSource()==this.p1_lab8)
		{
			this.cardp3.show(p3, "7");
			
		}
		
		
	}
	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseEntered(MouseEvent e) {
		//如果用户选择了label
		if(e.getSource()==this.p1_lab2)
		{
			
			this.p1_lab2.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab3)
		{
			
			this.p1_lab3.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab4)
		{
			
			this.p1_lab4.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab5)
		{
			
			this.p1_lab5.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab6)
		{
			
			this.p1_lab6.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab7)
		{
			
			this.p1_lab7.setForeground(Color.red);
		}
		else if(e.getSource()==this.p1_lab8)
		{
			
			this.p1_lab8.setForeground(Color.red);
		}
		
	}
	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.p1_lab2)
		{
			this.p1_lab2.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab3)
		{
			this.p1_lab3.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab4)
		{
			this.p1_lab4.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab5)
		{
			this.p1_lab5.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab6)
		{
			this.p1_lab6.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab7)
		{
			this.p1_lab7.setForeground(Color.white);
		}
		else if(e.getSource()==this.p1_lab8)
		{
			this.p1_lab8.setForeground(Color.white);
		}
		
		
	}
	
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void windowClosing(WindowEvent e) {
		
		 
		
	}
	
	public void windowClosed(WindowEvent e) {
		System.exit(0);
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
