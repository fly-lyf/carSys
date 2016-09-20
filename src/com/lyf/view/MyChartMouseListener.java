package com.lyf.view;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

public class MyChartMouseListener implements ChartMouseListener {
    private String typestr;
    private Integer level;

    public MyChartMouseListener(String type, int level) {
        // TODO Auto-generated constructor stub
        this.typestr = type;
        this.level = level;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent arg0) {
        // TODO Auto-generated method stub
        if (!typestr.equals("emptyValue")) {
            showDetail_view sdv = new showDetail_view(typestr, level);
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent arg0) {
        // TODO Auto-generated method stub
    }

}
