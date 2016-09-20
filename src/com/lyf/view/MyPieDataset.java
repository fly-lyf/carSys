package com.lyf.view;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;

public class MyPieDataset {
    JFreeChart chart;

    public MyPieDataset() {

    }

    public PieDataset createDataset(Data[] data) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (int i = 0; i < data.length; i++) {
            dataset.setValue(data[i].getName(), new Double(data[i].getData()));
        }
        return dataset;
    }

    public JFreeChart createChart(Data[] data) {
        PieDataset dataset = createDataset(data);
        chart = ChartFactory.createPieChart3D(
                "",  // chart title
                dataset,             // data
                false,               // include legend
                true,
                true
        );

        chart.setBackgroundPaint(new Color(89, 194, 230));
        chart.setBorderVisible(false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(true);
//	        plot.setToolTipGenerator(new StandardPieToolTipGenerator());

        plot.setCircular(true);
        plot.setBackgroundPaint(new Color(89, 194, 230));
        plot.setOutlinePaint(new Color(89, 194, 230));

        LegendTitle legendTitle = new LegendTitle(plot);//创建图例
        legendTitle.setPosition(RectangleEdge.LEFT);  //设置图例的位置
        legendTitle.setBorder(1, 1, 1, 1);
        legendTitle.setItemFont(new Font("宋体", Font.PLAIN, 10));

//	        plot.setLabelGenerator(null);
        plot.setLabelFont(new Font("宋体", Font.PLAIN, 10));
//	        plot.setLabelOutlinePaint(null);//标签边框颜色
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(null);
        plot.setForegroundAlpha(Float.parseFloat("0.7")); //图片前景的透明度，图片的前景就是这里的饼状图，透明度为0.0~1.0
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1},{2})",
                NumberFormat.getNumberInstance(),
                new DecimalFormat("0.00%"))); //设置工具条是否显示出百分比，其它同上

        chart.addLegend(legendTitle);
        return chart;

    }
}

