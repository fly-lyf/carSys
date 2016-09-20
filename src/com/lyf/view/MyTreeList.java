package com.lyf.view;

import com.jl.extract.extract_de;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MyTreeList{

    public JTree createTree() {
        HashMap<String, String[]> nodeNames = getTreeListNodeNames("setConfig/result.xml", "name");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("车型");
        for (Iterator<Map.Entry<String, String[]>> iterator = nodeNames.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String[]> it = iterator.next();
            DefaultMutableTreeNode brand = new DefaultMutableTreeNode(it.getKey());
            for (int i = 0; i < it.getValue().length; i++) {
                String s = it.getValue()[i];
                DefaultMutableTreeNode type = new DefaultMutableTreeNode(s);
                brand.add(type);
            }
            root.add(brand);
        }
        JTree tree = new JTree(root);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        ImageIcon img=new ImageIcon("image_left/logo_top.png");
        img.setImage(img.getImage().getScaledInstance(25,20,Image.SCALE_DEFAULT));
        renderer.setLeafIcon(img);
        renderer.setClosedIcon(img);
        renderer.setOpenIcon(img);
        tree.setCellRenderer(renderer);
        return tree;

    }

    public HashMap<String, String[]> getTreeListNodeNames(String File, String attr) {
        //获取属性值
        SAXReader reader = new SAXReader();
        java.io.File file = new File(File);
        Document doc;
        List<Element> brandNode;
        HashMap<String, String[]> resultMap = new HashMap<>();
        try {
            doc = reader.read(file);
            brandNode = extract_de.searchNodes("/tree/brand", doc.getRootElement());
            for (Element result : brandNode) {
                List list = extract_de.searchNodes("./" + "type", result);
                String[] types = new String[list.size()];
                for (int j = 0; j < list.size(); j++) {
                    Element typeNode = (Element) list.get(j);
                    types[j] = typeNode.attributeValue(attr);
                }
                resultMap.put(result.attributeValue(attr), types);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
