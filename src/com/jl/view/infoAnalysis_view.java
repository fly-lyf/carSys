package com.jl.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.jl.classify.beginclassify;
import com.jl.extract.extract_de;
import com.jl.extract.flush_view;
import com.jl.gpaApplication.testC;
import com.jl.sql.CreateDatabase;
import com.jl.tools.MyTools;
import com.jl.tools.common;
import com.lyf.view.*;

public class infoAnalysis_view extends JPanel implements ActionListener {
    public JPanel kz_analysis, kz_title, kz_button;
    public JButton jb_start, jb_stop;
    public JLabel jl_title, jl_count;

    public JPanel kz_dictionary;
    public JLabel label_dictionary;
    public JButton load_dictionary, delete_dictionary;

    public ChartPanel chartPanel;

    public JPanel headPane;
    public JScrollPane treePane;
    public JPanel chartPane;
    public JPanel zongCenter;
    public JLabel jl1;

    public MyPieDataset pie;
    public JFreeChart chart;
    public JTree tree;
    public MyTreeList treeList;
    public JPanel colorPane;

    public JLabel dic_title;
    public JPanel dic_top, dic_bottom;

    public void flush_dicLabel() {
        label_dictionary.setText("极性词典创建完成");
    }

    public void flush_count(int i) {
        jl_count.setText("已经分析完" + i + "条信息");
    }

    public void flush_chart() {
        System.out.println("调用刷新函数");
        zongCenter = new JPanel();
        zongCenter.setBackground(new Color(89, 194, 230));
        chartPane = new JPanel();


        //饼图
        pie = new MyPieDataset();

        //树形列表
        treeList = new MyTreeList();
        tree = treeList.createTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (!node.getUserObject().equals("车型")) {
                    String name = (String) node.getUserObject();
                    Data[] data;
                    HashMap<String, Integer> performances = new HashMap<String, Integer>();
                    if (node.getLevel() == 1) {
                        //品牌名
                        performances = getNodes("setConfig/result.xml", "/tree/brand[@name='" + name + "']", true);
                    } else if (node.getLevel() == 2) {
                        //车型名
                        performances = getNodes("setConfig/result.xml", "/tree/brand/type[@name='" + name + "']", false);

                    }
                    data = new Data[performances.size()];
                    int i = 0;
                    for (Iterator<Map.Entry<String, Integer>> iterator = performances.entrySet().iterator(); iterator.hasNext(); ) {
                        Map.Entry<String, Integer> it = iterator.next();
                        data[i] = new Data(it.getKey(), it.getValue().toString());
                        i++;
                    }
                    chart = pie.createChart(name, data);
                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new MyChartMouseListener(name, node.getLevel()));
                    chartPanel.setBackground(new Color(89, 194, 230));
                    //this.chartPanel存的是图表，chartPane是图表容器，zongcenter是chartPane的外层panel
                    //每次点击树图的时候，删除chartPanel并新建一个新的，重新放进chartPane里
                    //chartPane的用处是设置了BorderLayout，在west部分加了一个JPanel，给图例挤出来一点空间
//                  if (this.chartPanel != null) {
//                      chartPane.remove(this.chartPanel);  //去掉的不全，导致可能点击报错
//                  }
//                  this.chartPanel = chartPanel;
                    if (chartPanel != null) {
                        chartPane.removeAll();
                    }
                    chartPane.setLayout(new BorderLayout());
                    colorPane = new JPanel();
                    colorPane.setBackground(new Color(89, 194, 230));
                    chartPane.add(colorPane, BorderLayout.WEST);
                    chartPanel.setPreferredSize(new Dimension(chartPane.getWidth(), chartPane.getHeight()));
                    chartPane.add(chartPanel, BorderLayout.CENTER);
                    zongCenter.add(chartPane, BorderLayout.CENTER);
                }
            }
        });

        zongCenter.setLayout(new BorderLayout());
        //貌似JScrollpane必须加component初始化，所以现在树形列表有点贴边了
        treePane = new JScrollPane(tree);
        treePane.setBorder(null);
        colorPane = new JPanel();
        colorPane.setBackground(new Color(89, 194, 230));
        treePane.setBackground(Color.WHITE);
        treePane.setPreferredSize(new Dimension(300, this.getHeight()));
        zongCenter.add(treePane, BorderLayout.WEST);


    }

    public infoAnalysis_view() {

        //上面的面板控制
        kz_title = new JPanel();
        jl_title = new JLabel("汽车负面产品信息汇总");
        jl_title.setFont(MyTools.f6);
        jl_title.setForeground(Color.WHITE);
        kz_title.add(jl_title);
        kz_title.setBackground(new Color(89, 194, 230));

        kz_button = new JPanel();
        jb_start = new JButton("开始分析");
        jb_start.addActionListener(this);
        jb_start.setActionCommand("analysis_start");

        jb_stop = new JButton("停止分析");
        jb_stop.addActionListener(this);
        jb_stop.setActionCommand("analysis_stop");

        jl_count = new JLabel();
        jl_count.setFont(MyTools.f6);
        jl_count.setForeground(Color.WHITE);

        kz_button.add(jb_start);
        kz_button.add(jb_stop);
        kz_button.add(jl_count);
        kz_button.setBackground(new Color(89, 194, 230));


        kz_analysis = new JPanel();
        kz_analysis.setLayout(new BorderLayout());
        kz_analysis.add(kz_title, BorderLayout.NORTH);
        kz_analysis.add(kz_button, BorderLayout.CENTER);
        kz_analysis.setBackground(new Color(89, 194, 230));

        //中间的面板

        flush_chart();

        //下面的面板控制
        kz_dictionary = new JPanel(new BorderLayout());
        dic_top = new JPanel();
        dic_top.setBackground(new Color(89, 194, 230));
        dic_bottom = new JPanel();
        dic_bottom.setBackground(new Color(89, 194, 230));

        dic_title = new JLabel("极性词典");
        dic_title.setFont(MyTools.f6);
        dic_title.setForeground(Color.WHITE);
        dic_top.add(dic_title);


        load_dictionary = new JButton("创建极性词典");
        load_dictionary.addActionListener(this);
        load_dictionary.setActionCommand("dictionary");
        delete_dictionary = new JButton("删除极性词典");
        delete_dictionary.addActionListener(this);
        delete_dictionary.setActionCommand("delete");

        label_dictionary = new JLabel();
        label_dictionary.setFont(MyTools.f6);
        label_dictionary.setForeground(Color.WHITE);

        dic_bottom.add(load_dictionary);
        dic_bottom.add(delete_dictionary);
        dic_bottom.add(label_dictionary);

        kz_dictionary.add(dic_top, BorderLayout.NORTH);
        kz_dictionary.add(dic_bottom, BorderLayout.CENTER);
        kz_dictionary.setBackground(new Color(89, 194, 230));


        this.setLayout(new BorderLayout());
        this.add(kz_analysis, BorderLayout.NORTH);
        this.add(zongCenter, BorderLayout.CENTER);
        this.add(kz_dictionary, BorderLayout.SOUTH);
        this.setBackground(new Color(89, 194, 230));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("analysis_start")) {
            System.out.println("开始分析");
            boolean flag = deleteFile("setConfig/result.xml");
            if (flag == true) {
                System.out.println("文件删除成功");
            }
            try {
                createFile("setConfig/result.xml");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            extract_de de = common.thread_ex.get("th");
            if (de == null) {
                System.out.println("线程第一次开始");
                de = new extract_de();
                de.setFlag(true);
                new Thread(de).start();
                common.thread_ex.put("th", de);


            } else {

                System.out.println("重新开始");
                common.thread_ex.remove("th");
                de = new extract_de();
                de.setFlag(true);
                new Thread(de).start();
                              
                common.thread_ex.put("th", de);

            }


        }
        if (e.getActionCommand().equals("analysis_stop")) {

            System.out.println("终止分析");

            extract_de de = common.thread_ex.get("th");
            de.stopCurrentThread();
            

            flush_view fv = common.thread_fl.get("fl");
            fv.setFlag(false);


        }
        if (e.getActionCommand().equals("dictionary")) {
            label_dictionary.setText("开始创建极性词典");
            System.out.println("加载词典");
            testC bc = new testC();
            Thread a = new Thread(bc);
            a.start();
        }
        if (e.getActionCommand().equals("delete")) {
            System.out.println("删除极性词典");
            try {
                CreateDatabase cd = new CreateDatabase();
                ArrayList tablename = extractXingNengTable();
                for (int tn = 0; tn < tablename.size(); tn++) {
                    cd.deleteTable(tablename.get(tn).toString());
                }
                cd.Close();          //为每个产品属性创建一个极性词表
                label_dictionary.setText("极性词典已经全部删除");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


    }

    //读取table字段属性
    public ArrayList extractXingNengTable() {
        // 获取属性值
        SAXReader reader = new SAXReader();
        File file = new File("setConfig/sim.xml");
        Document doc;
        List<Element> result_Nodes;
        ArrayList list1 = new ArrayList();
        try {
            doc = reader.read(file);
            List<Element> result = doc.selectNodes("/words/performance");

            for (Element subnode : result) {
                String str = subnode.attributeValue("table");
                list1.add(str);
            }
        } catch (DocumentException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        return list1;
    }

    //删除原有的result.xml文件
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    //创建初始文件
    public void createFile(String sPath) throws Exception {
        File f = new File(sPath);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
        BufferedWriter bosw = new BufferedWriter(osw);
        bosw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        bosw.newLine();
        bosw.write("<tree >");
        bosw.newLine();
        bosw.write("</tree>");
        bosw.newLine();
        bosw.close();
        osw.close();
    }


    //获取节点 修改
    public static HashMap<String, Integer> getNodes(String File, String xpath, boolean hasChild) {

        System.out.println("********调用了获取节点的程序**********");
        //获取属性值
        SAXReader reader = new SAXReader();
        File file = new File(File);
        Document doc;
        List<Element> brandOrTypeNodes;
        HashMap<String, Integer> resultMap = new HashMap<>();
        try {
            doc = reader.read(file);
            Element root = doc.getRootElement();
            brandOrTypeNodes = doc.selectNodes(xpath);

            for (Element brandOrTypeNode : brandOrTypeNodes) {

                List<Element> list = new ArrayList<>();
                list = brandOrTypeNode.selectNodes(xpath + "/performance");
                Integer count;
                for (int j = 0; j < list.size(); j++) {
                    Element performanceNode = list.get(j);
                    String weight = performanceNode.attributeValue("weight");
                    String name = performanceNode.attributeValue("name");
                    count = weight.split(";").length;
                    if (resultMap.get(name) != null) {
                        resultMap.put(name, resultMap.get(name) + count);
                    } else {
                        resultMap.put(name, count);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return resultMap;
    }


}
