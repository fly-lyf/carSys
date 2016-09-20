package com.lyf.view;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

public class MyChartMouseListener implements ChartMouseListener {
	private String typestr;
	public MyChartMouseListener(String type) {
		// TODO Auto-generated constructor stub

		typestr  = type;
	}

	@Override
	public void chartMouseClicked(ChartMouseEvent arg0) {
		// TODO Auto-generated method stub
		if(typestr == "emptyValue"){
	}else{
		showDetail_view sdv = new showDetail_view(typestr);
	}
		
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
