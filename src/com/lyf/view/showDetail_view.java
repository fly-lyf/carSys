package com.lyf.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.base.modules.SubSystem;

import com.jl.extract.extract_de;
import com.jl.sql.*;

public class showDetail_view extends JDialog implements ActionListener {
	public showDetail_view(String type) {
		JScrollPane jp = new JScrollPane();
//		JPanel jp = new JPanel();
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		JTextArea jtxt = new JTextArea();
		jtxt.setLineWrap(true);
        jtxt.setText("");
		ArrayList<String> showPerf;
		ArrayList<String> showWght;
		//表格数据

		SAXReader reader=new SAXReader();
        File file=new File("setConfig/result.xml");
        Document doc = null;
        try {
			doc = reader.read(file);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Vector<String> column = new Vector<String>();
		column.addElement("id");
		column.addElement("时间");
		column.addElement("来源");
		column.addElement("内容");

		showPerf = extractDetail("setConfig/result.xml",type,"name");
		showWght = extractDetail("setConfig/result.xml",type,"weight");
		String swght,stype;
		for(int i=0;i<showWght.size();i++){
			stype=showPerf.get(i);
			swght = showWght.get(i);
			String[] wgtnumber = swght.split(";");
			myaddtab(tab,stype,wgtnumber,column,jtxt);
		}
		tab.setVisible(true);
		jp.setViewportView(jtxt);
//	this.add(jp);
//		jp.add(jtxt);
		jp.setVisible(true);
//		jtxt.setVisible(true);
		jp.setPreferredSize(new Dimension(700,200));
//        jtxt.setPreferredSize(new Dimension(400,400));
 //       jp.setSize(this.getContentPane().getWidth()-20,this.getContentPane().getHeight()-tab.getHeight());

//        jtxt.setSize(jp.getWidth(), jp.getHeight());
 //       System.out.println(this.getContentPane().getWidth()+"|"+jp.getWidth());
        
		this.setSize(900, 700);
		this.setLocationRelativeTo(null);
		this.setTitle("负面信息");
		this.setLayout(new BorderLayout());
		this.add(tab,BorderLayout.NORTH);
		this.add(jp,BorderLayout.SOUTH);
		this.setModal(true);
		this.setVisible(true);		
//		centerWindow(this);
		validate();

	}

	public void myaddtab(JTabbedPane tab, String stype,String[] wgtnumber, Vector<String> column, final JTextArea jtxt) {
		// TODO Auto-generated method stub
		Vector data = new Vector();
//		Vector<Vector<String>> data = new Vector<Vector<String>>();
		final DefaultTableModel tm = new DefaultTableModel();
	        JButton button = new JButton(stype);  
	        JScrollPane panel = new JScrollPane(); 
	        final JTable table = new JTable();
	        panel.setViewportView(table);
	        tab.addTab(stype, panel);             
		for(int j=0;j<wgtnumber.length;j++){
			String id;
//			try {
//					id = wgtnumber[j];
//					String sql = "select id,urltime,channel,content from negativeinfo where id="
//							+ id + "";
//					SqlHelper sqh = new SqlHelper();
//					System.out.println("id:" + id);
//					ResultSet rs;
//					rs = sqh.queryExecute(sql);
//					Vector row = new Vector();
//					while (rs.next()) {
//						row.addElement(rs.getString(1));
//						row.addElement(rs.getString(2));
//						row.addElement(rs.getString(3));
//						row.addElement(rs.getString(4));
//						data.add(row);
//					}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

		tm.setDataVector(data, column);
		table.setModel(tm);
		table.setVisible(true);
		TableColumn columnid = table.getColumnModel().getColumn(0);
		TableColumn columntime = table.getColumnModel().getColumn(1);
		TableColumn columnchannel = table.getColumnModel().getColumn(2);
		columnid.setMaxWidth(40);
		columntime.setMaxWidth(200);
		columntime.setPreferredWidth(150);
		columnchannel.setMaxWidth(200);
		columnchannel.setPreferredWidth(150);
	    table.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e) {
	           if(e.getClickCount()==1){//点击几次，这里是双击事件
	        	   int row=table.getSelectedRow();   
	        	  String str = (String) tm.getValueAt(row, 3);
	        	   jtxt.setText(str);
	           }
	        }
	       });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void centerWindow(showDetail_view showDetail_view) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scmSize = toolkit.getScreenSize();
		int width = showDetail_view.getSize().width, height = showDetail_view.getSize().height;
		showDetail_view.setLocation(scmSize.width / 2 - (width / 2), scmSize.height
				/ 2 - (height / 2));
	}
	
	public ArrayList extractDetail(String File, String type, String detail) {
		//获取属性值
		SAXReader reader = new SAXReader();
		File file = new File(File);
		Document doc;
		List<Element> result_Nodes;
		ArrayList list1 = new ArrayList();
		try {
			doc = reader.read(file);
			result_Nodes = extract_de.searchNodes("/tree/brand/type[@name='"
					+ type + "']", doc.getRootElement());
			for (Element result : result_Nodes) {
				List list = extract_de.searchNodes("./" + "performance" + "", result);
				for (int j = 0; j < list.size(); j++) {
					Element sen1 = (Element) list.get(j);
					String text = sen1.attributeValue(detail);
					list1.add(text);
				}
			}
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return list1;
	}
}