package com.jl.test;

import com.jl.extract.extract_de;
import com.jl.tools.MyTools;
import com.lyf.view.Data;
import com.lyf.view.MyChartMouseListener;
import com.lyf.view.MyPieDataset;
import com.lyf.view.MyTreeList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;


import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class testXMLView extends JPanel implements ActionListener {

    private ChartPanel chartPanel;

    //饼图
    MyPieDataset pie = new MyPieDataset();

    //树形列表
    MyTreeList treeList = new MyTreeList();
    JTree tree = treeList.createTree();

    public testXMLView() {
    	
        this.setBackground(new Color(89, 194, 230));
        JPanel headPane = new JPanel();
        JPanel treePane = new JPanel();
        JPanel chartPane = new JPanel();
        JLabel jl1 = new JLabel("汽车产品信息舆情分析");
        jl1.setFont(MyTools.f6);
        jl1.setForeground(Color.WHITE);
        headPane.add(jl1);
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
                        performances = testXMLView.this.getNodes("setConfig/result.xml", "/tree/brand[@name='" + name + "']", true);
                    } else if (node.getLevel() == 2) {
                        //车型名
                        performances = testXMLView.this.getNodes("setConfig/result.xml", "/tree/brand/type[@name='" + name + "']", false);
                        data = new Data[performances.size()];

                    }
                    data = new Data[performances.size()];
                    int i = 0;
                    for (Iterator<Map.Entry<String, Integer>> iterator = performances.entrySet().iterator(); iterator.hasNext(); ) {
                        Map.Entry<String, Integer> it = iterator.next();
                        data[i] = new Data(it.getKey(), it.getValue().toString());
                        i++;
                    }
                    JFreeChart chart = pie.createChart(name, data);
                    ChartPanel chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new MyChartMouseListener(name, node.getLevel()));
                    chartPanel.setBackground(new Color(89, 194, 230));
                    if (testXMLView.this.chartPanel != null) {
                        testXMLView.this.remove(testXMLView.this.chartPanel);
                    }
                    testXMLView.this.chartPanel = chartPanel;
                    testXMLView.this.add(chartPanel, BorderLayout.CENTER);
                }
            }
        });


        this.setLayout(new BorderLayout());

        headPane.setBackground(new Color(89, 194, 230));
        this.add(headPane, BorderLayout.NORTH);

        treePane.setLayout(new FlowLayout(FlowLayout.LEFT));
        treePane.add(new JLabel());
        treePane.add(tree);
        treePane.setBackground(Color.WHITE);
        treePane.setPreferredSize(new Dimension(200, this.getHeight()));
        this.add(treePane, BorderLayout.WEST);
    }

    public HashMap<String, Integer> getNodes(String File, String xpath, boolean hasChild) {
    	
    	System.out.println("*************调用了getNodes");
        //获取属性值
        SAXReader reader = new SAXReader();
        java.io.File file = new File(File);
        Document doc;
        List<Element> brandOrTypeNodes;
        HashMap<String, Integer> resultMap = new HashMap<>();
        try {
            doc = reader.read(file);
            brandOrTypeNodes = extract_de.searchNodes(xpath, doc.getRootElement());
            for (Element brandOrTypeNode : brandOrTypeNodes) {
                List<Element> list = new ArrayList<>();
                list = extract_de.searchNodes("./" + "performance", brandOrTypeNode);

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


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


