package com.lyf.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jl.domain.Product;
import com.jl.extract.extract_de;
import com.jl.productSql.productService;

public class showDetail_view extends JDialog implements ActionListener {
    public showDetail_view(String type, int level) {
        JScrollPane jp = new JScrollPane();
//		JPanel jp = new JPanel();
        JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
        JTextArea jtxt = new JTextArea();
        jtxt.setLineWrap(true);
        jtxt.setText("");
        ArrayList<String> showPerf;
        ArrayList<String> showWght;

        Vector<String> column = new Vector<String>();
        column.addElement("id");
        column.addElement("内容");
        String xpath = "/tree/brand";
        if (level == 2) {
            xpath += "/type";
        }
        showPerf = extractDetail("setConfig/result.xml", xpath + "[@name='" + type + "']", "name");
        showWght = extractDetail("setConfig/result.xml", xpath + "[@name='" + type + "']", "weight");
        String swght, stype;
        for (int i = 0; i < showWght.size(); i++) {
            stype = showPerf.get(i);
            swght = showWght.get(i);
            String[] wgtnumber = swght.split(";");
            myAddTab(tab, stype, wgtnumber, column, jtxt);
        }
        tab.setVisible(true);
        jp.setViewportView(jtxt);
        jp.setVisible(true);
        jp.setPreferredSize(new Dimension(700, 200));


        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("负面信息");
        this.setLayout(new BorderLayout());
        this.add(tab, BorderLayout.NORTH);
        this.add(jp, BorderLayout.SOUTH);
        this.setModal(true);
        this.setVisible(true);
//		centerWindow(this);
        validate();

    }

    public void myAddTab(JTabbedPane tab, String stype, String[] wgtnumber, Vector<String> column, final JTextArea jtxt) {
        // TODO Auto-generated method stub
        Vector data = new Vector();
        final DefaultTableModel tm = new DefaultTableModel();
        JButton button = new JButton(stype);
        JScrollPane panel = new JScrollPane();
        final JTable table = new JTable();
        panel.setViewportView(table);
        tab.addTab(stype, panel);
        // todo-fly 去数据库拉取数据，传给下边的tm，用于表格数据显示
        // todo-fly 原先的sql语句为 select id,urltime,channel,content from negativeinfo where id={wgtnumber[j]}，后边这个貌似可以优化为 id in {wgtnumber}
        for (int j = 0; j < wgtnumber.length; j++) {
            try {
                String id = wgtnumber[j];
              //  String sql = "select id,urltime,channel,content from negativeinfo where id="
              //          + id + "";
               // SqlHelper sqh = new SqlHelper();
                System.out.println("id:" + id);
                productService ps=new productService();
               //  ResultSet rs;
                Product pro= ps.queryForProductID(Integer.parseInt(id));
                Vector row = new Vector();
              
                row.addElement(pro.getId());
                row.addElement(pro.getCONTENT());
                data.add(row);
                
                ps.Close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tm.setDataVector(data, column);
        table.setModel(tm);
        table.setVisible(true);
        TableColumn columnid = table.getColumnModel().getColumn(0);
        TableColumn columncontent= table.getColumnModel().getColumn(1);
       
        columnid.setMaxWidth(40);
       // columncontent.setMaxWidth(500);
        columncontent.setPreferredWidth(800);
      
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {//点击几次，这里是双击事件
                    int row = table.getSelectedRow();
                    String str = (String) tm.getValueAt(row, 1);
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

    public ArrayList extractDetail(String File, String xpath, String attr) {
        //获取属性值
        SAXReader reader = new SAXReader();
        File file = new File(File);
        Document doc;
        List<Element> result_Nodes;
        ArrayList list1 = new ArrayList();
        try {
            doc = reader.read(file);
            //result_Nodes = extract_de.searchNodes(xpath, doc.getRootElement());
              System.out.println("xpath:"+xpath);
            result_Nodes =  doc.selectNodes(xpath);
            for (Element result : result_Nodes) {          	
                //List list = extract_de.searchNodes("./" + "performance" + "", result);
            	List list =doc.selectNodes(xpath+"/performance");
                for (int j = 0; j < list.size(); j++) {
                    Element sen1 = (Element) list.get(j);
                    String text = sen1.attributeValue(attr);
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