package com.jl.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.jl.tools.MyTools;
import com.jl.tools.common;
import com.jl.classify.beginclassify;
import com.jl.productSql.ProductAccessModel;
import com.jl.productSql.pageProduct;
import com.jl.productSql.pageServiceProduct;
import com.jl.productSql.productService;
import com.jl.sql.CreateDatabase;
import com.jl.sql.LogAccessModel;
import com.jl.sql.Pager;
import com.jl.sql.PagerService;
import com.jl.sql.commentService;


public class infoSummary_view extends JPanel implements ActionListener{
	
	JPanel jp1_t,jp1_tt,jp1_d,jp1_m,jp1_b;
	JTable jt;
	JScrollPane jpm_1;
	JButton jb1,jb2;
	JLabel jl1,jl2;
	ProductAccessModel lam;
	
	JPanel jp2,jp2_1,jp2_2;
	JButton jbFirstPage,jbPreviousPage,jbNextPage,jbLastPage;
	
//	public static HashMap map=new HashMap();
	
	int flag;
	
	public void setFlag(int i){
		this.flag=i;
		
	}
	
	
	
	public void flash(String str){
		jl2.setText("已经完成"+str+"条信息过滤处理");
		jl2.setFont(MyTools.f6);
	    jl2.setForeground(Color.WHITE);
	}
	
	public void editFlush(){
		
		lam=new ProductAccessModel("",new pageProduct(lam.getTotalRows()));
		jt.setModel(lam);
		TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);
	}
	
	public void preHandle(){
		
		CreateDatabase cd=null;
		try {
			cd = new CreateDatabase();
			cd.createClass("product");
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}	
		
		 common.classnames.clear();
		 common.xunlian.clear();
		 File  f=new File("training");
		 File[] t=tree(f);//读取文件列表		     
	     if(t!=null){
		              for(int i=0;i<t.length;i++){
                    //判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
                                if(t[i].isDirectory()){
       	                          System.out.println(t[i].getName()+"\tttdir");
                                 }
                                else{
                               	 String name=t[i].getName();                             	
                                	 int n=name.indexOf(".");
                                	 String newname=name.substring(0,n); 
                               	     System.out.println(newname);
                               	                                	 
									try {							
										 cd.createTraining(newname+"train");//创建训练集表
	                                	 
									} catch (Exception e) {
										
										e.printStackTrace();
									}
                               	
	                                 common.classnames.add(newname); //将分类名存储起来 名字不加 train
	                                 
	                                
	                                 try{
	                                 FileReader r=new FileReader(t[i].getAbsolutePath());
	                                 BufferedReader br=new BufferedReader(r);
	                                 String str=""; 
	                                 String nn=newname+"train";
	                                 commentService sc=new commentService();	
	                                
	                                 while((str=br.readLine())!=null){	                                	                              	
	                                	sc.saveTrain(nn, str);

	                                 }
	                                 int count=sc.getClassCoun(newname);
	                                 System.out.println("get count from class "+newname+" = "+count);
	                                 common.xunlian.put(newname,count); //将结果暂存在hashmap表中
	                                 br.close();                       //将分类名存储起来 名字不加 train
	                                 r.close();
	                                 
	                                 //将训练词表中的内容加入到hashmap表中，便于快速分类
	                                                                  
	                                 sc.Close();
	                                 }catch(Exception e1){
	                                	 e1.printStackTrace();
	                                 }
	                                
	                                
                                 }
                                
		                                          }
		              
		              
               } 
	}
	
	public infoSummary_view(){
		
		this.flag=0;
		preHandle();		
		jp1_t=new JPanel(new BorderLayout());
		jp1_t.setBackground(new Color(89,194,230));
		
		jp1_t=new JPanel(new BorderLayout());
		jp1_tt=new JPanel();
		jb1=new JButton("开始分类");
		jb1.addActionListener(this);
		jb1.setActionCommand("classify");
		jb2=new JButton("终止分类");
		jb2.addActionListener(this);
		jb2.setActionCommand("stop");
		
		jp1_d=new JPanel();
		jl1=new JLabel("汽车产品信息汇总");
		jl1.setFont(MyTools.f6);
	    jl1.setForeground(Color.WHITE);
		jp1_tt.add(jb1);
		jp1_tt.add(jb2);
		jp1_t.add(jp1_tt, BorderLayout.CENTER);
		jp1_d.add(jl1);
		jp1_t.add(jp1_d,BorderLayout.NORTH);		
		lam=new ProductAccessModel("",new pageProduct(lam.getTotalRows()));
		jt=new JTable(lam);	
		TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);
		jt.addMouseListener(new MouseAdapter()
		{ 

		      public void mouseClicked(MouseEvent e) 
              { 
		    	  if(e.getClickCount() == 2)
		    	  {
		                      //实现双击 {                             
		                      int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
                              int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 
                              
                              String cellVal=(String)(jt.getValueAt(row,col)+""); //获得点击单元格数据  
                              if(col!=0){
		                        int id=(int) jt.getValueAt(row,0);
		                        System.out.println(cellVal);
		                        if(flag==0){
		                         juti_view jj=new juti_view(cellVal,id,"product",1);
		                        }
		                        
                              }
		                      
		    	  } else return; 
		    	 }
		} );

	
		
		jpm_1=new JScrollPane(jt);
				
		jp2_1=new JPanel();
		jbFirstPage=new JButton("首页");
		jbFirstPage.addActionListener(this);
		jbFirstPage.setActionCommand("firstPage");
		
		jbPreviousPage=new JButton("上一页");
		jbPreviousPage.addActionListener(this);
		jbPreviousPage.setActionCommand("previousPage");
		
		jbNextPage=new JButton("下一页");
		jbNextPage.addActionListener(this);
		jbNextPage.setActionCommand("nextPage");
		
		jbLastPage=new JButton("末页");
		jbLastPage.addActionListener(this);
		jbLastPage.setActionCommand("lastPage");
		
		jp2_1.add(jbFirstPage);
		jp2_1.add(jbPreviousPage);
		jp2_1.add(jbNextPage);
		jp2_1.add(jbLastPage);
		
		productService ps=new productService();
		int total=ps.queryForTotalRows();
		ps.Close();
		
		jp2_2=new JPanel();
		jl2=new JLabel("当前产品信息共有"+total+"条");
		jl2.setFont(MyTools.f6);
	    jl2.setForeground(Color.WHITE);
		
		jp2_2.add(jl2);
		
		jp2=new JPanel(new BorderLayout());
		jp2.add(jp2_1,BorderLayout.NORTH);
		jp2.add(jp2_2,BorderLayout.CENTER);
		
		jp1_tt.setBackground(new Color(89,194,230));
		jp1_d.setBackground(new Color(89,194,230));
		jp1_t.setBackground(new Color(89,194,230));
		jpm_1.setBackground(new Color(89,194,230));
		jp2_1.setBackground(new Color(89,194,230));
		jp2_2.setBackground(new Color(89,194,230));
		jp2.setBackground(new Color(89,194,230));
		
		this.setLayout(new BorderLayout());
		this.add(jp1_t,BorderLayout.NORTH);
		this.add(jpm_1,BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);
		this.setBackground(new Color(89,194,230));
		this.setForeground(new Color(89,194,230));
	}
	
	public void enableButton(){
		jb1.setEnabled(false);
		jbFirstPage.setEnabled(false);
		jbPreviousPage.setEnabled(false);
		jbNextPage.setEnabled(false);
		jbLastPage.setEnabled(false);
	}
	
	public void ableButton(){
		jb1.setEnabled(true);
		jbFirstPage.setEnabled(true);
		jbPreviousPage.setEnabled(true);
		jbNextPage.setEnabled(true);
		jbLastPage.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("classify")){
			beginclassify bc=new beginclassify();
			bc.setFlag(true);
		    new Thread(bc).start();
		    common.thread.put("thread", bc);
		    //停止所有的按钮控制
		    enableButton();
		    //
		    infoCollection_view info=common.mp.get(1);
		    info.setLock(1);	 //设置锁住infoCollection的juti_view	    
		    this.flag=1;
		}
		if(e.getActionCommand().equals("stop")){
			beginclassify bb=common.thread.get("thread");	
			bb.stopCurrentThread();
			System.out.println("success");
			ableButton();
			lam=new ProductAccessModel("",new pageProduct(lam.getTotalRows()));
			jt.setModel(lam);
			TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
			firsetColumn.setPreferredWidth(50);
			firsetColumn.setMaxWidth(50);
			firsetColumn.setMinWidth(50);
			productService ps=new productService();
			int total=ps.queryForTotalRows();
			ps.Close();
			jl2.setText("当前产品信息共有"+total+"条");
			//
			infoCollection_view info=common.mp.get(1);
			info.refresh(0);
			this.flag=0;
			
		}
		
		if(e.getActionCommand().equals("firstPage")||e.getActionCommand().equals("previousPage")||e.getActionCommand().equals("nextPage")||e.getActionCommand().equals("lastPage"))
		{
			pageServiceProduct pagerService=new pageServiceProduct();		
			pageProduct pager=pagerService.getPager(e.getActionCommand(),this.lam.pager);
			
			if(pager.flag==1)
				lam=new ProductAccessModel("",pager);		   			
			jt.setModel(lam);
			TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
			firsetColumn.setPreferredWidth(50);
			firsetColumn.setMaxWidth(50);
			firsetColumn.setMinWidth(50);
	      }
	}

     public void stopClassify(){
    	    ableButton();
			lam=new ProductAccessModel("",new pageProduct(lam.getTotalRows()));
			jt.setModel(lam);
			TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
			firsetColumn.setPreferredWidth(50);
			firsetColumn.setMaxWidth(50);
			firsetColumn.setMinWidth(50);
			productService ps=new productService();
			int total=ps.queryForTotalRows();
			ps.Close();
			jl2.setText("当前产品信息共有"+total+"条");
			//
			infoCollection_view info=common.mp.get(1);
			info.refresh(0);
			this.flag=0;
     }
	
      public static File[] tree(File f){
        
        if(!f.isDirectory()){
            System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
            return null;
        }
        else{
        	
            File[] t = f.listFiles();
            return t;
           
           
        }
 
    }
    
    
}
