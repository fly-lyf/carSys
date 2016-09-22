package com.lyf.view;


import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;

public class SetFont {
	public SetFont(JFreeChart chart){
	  
		Font titleFont = new Font("黑体", Font.BOLD, 20);  
      TextTitle textTitle = chart.getTitle();  
      textTitle.setFont(titleFont);// 为标题设置上字体  
        
      Font plotFont = new Font("宋体", Font.PLAIN, 16);  
      PiePlot plot = (PiePlot) chart.getPlot();  
      plot.setLabelFont(plotFont); // 为饼图元素设置上字体  
        
      Font LegendFont = new Font("楷体", Font.PLAIN, 18);  
      LegendTitle legend = chart.getLegend(0);  
      legend.setItemFont(LegendFont);// 为图例说明设置字体 
	}
}

