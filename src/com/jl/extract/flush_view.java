package com.jl.extract;

import java.awt.BorderLayout;

import com.jl.tools.common;
import com.jl.view.infoAnalysis_view;

public class flush_view implements Runnable{
	
	private volatile boolean flag;  //设置以便临时中断分类

	 
	public void setFlag(boolean flag) {
	        this.flag = flag;
	    }
	    
	public synchronized void stopCurrentThread() {
	        this.flag = false;
	        
	    }
	

	 @Override
	 public void run() {
		while(flag){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			infoAnalysis_view iv=common.infoA.get(3);
			iv.zongCenter.removeAll();
			iv.flush_chart();
			iv.add(iv.zongCenter, BorderLayout.CENTER);
		}
		
	}

}
