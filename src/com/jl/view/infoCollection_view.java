package com.jl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;

import com.jl.newords.NeWords_view;
import com.jl.sql.CreateDatabase;
import com.jl.sql.LogAccessModel;
import com.jl.sql.LogImportDialog;
import com.jl.sql.LogImportDialogW;
import com.jl.sql.Pager;
import com.jl.sql.PagerService;
import com.jl.tools.common;




public class infoCollection_view extends JPanel implements ActionListener{
	
	JPanel jp1,jp2;
	JPanel jp2_1,jp2_2,jp2_3;
	JLabel jl1;
	JButton jb1,jb2,jbback;
	JTable jt;
	JScrollPane jsp;
	JTextField jtf;
	JComboBox jcbFieldlist;
	JButton jbFirstPage,jbPreviousPage,jbNextPage,jbLastPage;
	JButton jbCL,jbCW;
	JLabel ltishi;
	LogAccessModel lam;
	
	int colflag=0;	
	
	
	
	public void refresh(int i){
		this.colflag=i;
		lam=new LogAccessModel("",new Pager(lam.getTotalRows()));
		jt.setModel(lam);
		TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
		 firsetColumn.setPreferredWidth(80);
		 firsetColumn.setMaxWidth(80);
		 firsetColumn.setMinWidth(80);
		
		jb1.setEnabled(true);
		jb2.setEnabled(true);	
		jbback.setEnabled(true);
		jbFirstPage.setEnabled(true);
		jbPreviousPage.setEnabled(true);
		jbNextPage.setEnabled(true);
		jbLastPage.setEnabled(true);
		jbCL.setEnabled(true);
		jbCW.setEnabled(true);	
		ltishi.setText("导入完成");
		
	}
	
	public void Flush(){
		this.colflag=1;
		lam=new LogAccessModel("",new Pager(lam.getTotalRows()));
		jt.setModel(lam);
		TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
		 firsetColumn.setPreferredWidth(80);
		 firsetColumn.setMaxWidth(80);
		 firsetColumn.setMinWidth(80);
	}
	
	public void tishi(){
		ltishi.setText("正在导入");
	}
	
	public void setLock(int i){
		this.colflag=i;
		jb1.setEnabled(false);
		jb2.setEnabled(false);
		jbback.setEnabled(false);
		jbFirstPage.setEnabled(false);
		jbPreviousPage.setEnabled(false);
		jbNextPage.setEnabled(false);
		jbLastPage.setEnabled(false);
		jbCL.setEnabled(false);
		jbCW.setEnabled(false);
	
		
	}
	public infoCollection_view() throws Exception{
		
		this.colflag=0;
		
		CreateDatabase cd=new CreateDatabase();
		cd.createTables();    //创建信息表 并 关闭数据库连接
		cd.Close();
		
		jp1=new JPanel();
		jbback=new JButton("返回首页");
		jbback.addActionListener(this);
		jbback.setActionCommand("back");
		jtf=new JTextField(10);
		jb1=new JButton("搜索");
		jb1.addActionListener(this);
		jb1.setActionCommand("Search");
		
		String[] fieldStrings={"信息id","来源","来源网址","标题","作者"};
		jcbFieldlist=new JComboBox(fieldStrings);
		jcbFieldlist.setBackground(Color.white);
		jcbFieldlist.addActionListener(this);
		
		jp1.add(jbback);
		jp1.add(jcbFieldlist);
		jp1.add(jtf);		
		jp1.add(jb1);
		
		lam=new LogAccessModel("",new Pager(lam.getTotalRows()));
		jt=new JTable(lam);
		TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(80);
		firsetColumn.setMaxWidth(80);
		firsetColumn.setMinWidth(80);
		
		jt.addMouseListener(new MouseAdapter()
		{ 

		      public void mouseClicked(MouseEvent e) 
              { 
		    	  if(e.getClickCount() == 2)
		    	  {
		    		        
		    		         
		                      int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 

                              int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 
                           
                              String cellVal=(String)(jt.getValueAt(row,col)+""); //获得点击单元格数据                       
                              if(col!=0){ 		                      
  		                        System.out.println(cellVal);
  		                        int id=(int) jt.getValueAt(row,0);
  		                         if(colflag==0)
  		                         {
  		                          juti_view jj=new juti_view(cellVal,id,"information",col);
  		                         }
                                }
		    	              
		    	  } else return; 
		    	 }
		} );

		jsp=new JScrollPane(jt);
		
		jp2=new JPanel(new BorderLayout());
		
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
		
		jp2_2=new JPanel();
		jb2=new JButton("车企名词表");
		jb2.addActionListener(this);
		jb2.setActionCommand("comp");
		
		
		jp2_2.add(jb2);
		
		
		jp2_3=new JPanel();
		jl1=new JLabel("选择导入数据文件夹：");
		jbCL=new JButton("选择论坛文件夹");
		jbCL.addActionListener(this);
		jbCL.setActionCommand("ImportLun");
		jbCW=new JButton("选择微博文件夹");
		jbCW.addActionListener(this);
		jbCW.setActionCommand("ImportWei");
		ltishi=new JLabel();
		jp2_3.add(jl1);
		jp2_3.add(jbCL);
		jp2_3.add(jbCW);
		jp2_3.add(ltishi);
		
		jp2_1.setBackground(new Color(89,194,230));
		jp2_2.setBackground(new Color(89,194,230));
		jp2_3.setBackground(new Color(89,194,230));
		jp2.add(jp2_1,BorderLayout.NORTH);
		jp2.add(jp2_2,BorderLayout.CENTER);
		jp2.add(jp2_3,BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(jp1,BorderLayout.NORTH);
		this.add(jsp,BorderLayout.CENTER);
		this.add(jp2,BorderLayout.SOUTH);
		jp1.setBackground(new Color(89,194,230));
		jp2.setBackground(new Color(89,194,230));
		this.setBackground(new Color(89,194,230));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("back")){
			lam=new LogAccessModel("",new Pager(lam.getTotalRows()));
			jt.setModel(lam);
		}	
		if(e.getActionCommand().equals("firstPage")||e.getActionCommand().equals("previousPage")||e.getActionCommand().equals("nextPage")||e.getActionCommand().equals("lastPage"))
		{
			PagerService pagerService=new PagerService();		
			Pager pager=pagerService.getPager(e.getActionCommand(),this.lam.pager);
			String schStr="";
			if(pager.flag==1)
				lam=new LogAccessModel("",pager);		   
			else		    
			{
  		        schStr=jtf.getText().trim();	
				String schField="id";			
				switch(jcbFieldlist.getSelectedIndex())
				{
				case 0:
					schField="id";
					break;
				case 1:
					schField="CHANNEL";
					break;
				case 2:
					schField="URLNAME";
					break;				
				case 3:
					schField="URLTITLE";
					break;
				case 4:
					schField="AUTHOR";
					break;				
				default:
					break;	
				}
				
				String sql="select * from information "
						+ "where "+schField+" like '"+schStr+"'";
				lam=new LogAccessModel(sql,pager);
				
			    
			}
			   jt.setModel(lam);
			   TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
			   firsetColumn.setPreferredWidth(80);
			   firsetColumn.setMaxWidth(80);
			   firsetColumn.setMinWidth(80);
	      }
		if(e.getActionCommand().equals("Search"))
		{
			String schStr=jtf.getText().trim();
			
			String schField="id";			
			switch(jcbFieldlist.getSelectedIndex())
			{
			case 0:
				schField="id";
				break;
			case 1:
				schField="CHANNEL";
				break;
			case 2:
				schField="URLNAME";
				break;				
			case 3:
				schField="URLTITLE";
				break;
			case 4:
				schField="AUTHOR";
				break;				
			default:
				break;	
			}
			String sql="select * from  information "
					+ "where "+schField+" like '"+schStr+"'";
			int totalRows=lam.getTotalRows(schField, schStr);
			Pager pager=new Pager(totalRows);
			pager.flag=2;
			lam=new LogAccessModel(sql,pager);
			jt.setModel(lam);
			TableColumn firsetColumn = jt.getColumnModel().getColumn(0);
			 firsetColumn.setPreferredWidth(80);
			 firsetColumn.setMaxWidth(80);
			 firsetColumn.setMinWidth(80);
			
		}
		if(e.getActionCommand().equals("ImportLun"))
		{
			
			LogImportDialog logImportDialog=new LogImportDialog();
			infoSummary_view info=common.infoS.get(2);
			info.setFlag(1);
			info.enableButton();
		   
		}
		if(e.getActionCommand().equals("ImportWei")){
			
			LogImportDialogW logImportDialog=new LogImportDialogW();
			infoSummary_view info=common.infoS.get(2);
			info.setFlag(1);
			info.enableButton();
		}
		if(e.getActionCommand().equals("comp")){
			NeWords_view wo=new NeWords_view();
		}
				
	}
}
