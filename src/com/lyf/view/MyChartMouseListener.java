package com.lyf.view;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

import java.awt.*;

public class MyChartMouseListener implements ChartMouseListener {
    private String typeStr;
    private Integer level;

    public MyChartMouseListener(String type, int level) {
        // TODO Auto-generated constructor stub
        this.typeStr = type;
        this.level = level;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent arg0) {
        // TODO Auto-generated method stub
    	System.out.println(typeStr);
        if (!typeStr.equals("emptyValue")) {
            ShowDetailView sdv = new ShowDetailView(typeStr, level);
            sdv.setBackground(new Color(89, 194, 230));
            sdv.setVisible(true);
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent arg0) {
        // TODO Auto-generated method stub
    }

}
