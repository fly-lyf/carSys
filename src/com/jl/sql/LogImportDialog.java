
package com.jl.sql;
import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.jl.filter.filter1;
import com.jl.tools.common;

import com.jl.view.infoCollection_view;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LogImportDialog extends JFileChooser{


	public LogImportDialog()
	{
		super();
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
		String path=null;
		File f=null;
		int flag=0;
		     try{     
		            flag=this.showOpenDialog(null);     
		        }    
		        catch(HeadlessException head){     
		             System.out.println("Open File Dialog ERROR!");    
		        }        
		        if(flag==JFileChooser.APPROVE_OPTION){
		             //获得该文件    
		            f=this.getSelectedFile();    
		            path=f.getPath();
		            System.out.println("path:"+path);
		            infoCollection_view info=(infoCollection_view)common.mp.get(1);
		            System.out.println("info:"+info);
		            info.setLock(1);  //锁住
		            try{
		            	FileReader r=new FileReader("setConfig/nb.txt");
		            	BufferedReader  br=new BufferedReader(r);
		               String a=br.readLine();
		            if(a.equals("1")){
		            	CreateIndex c=new CreateIndex();
			            c.deleteIndex();
			            filter1 fter=new filter1(f);
			    		Thread a1=new Thread(fter);
			    		a1.start();
		            }else if(a.equals("0")){
		            	 filter1 fter=new filter1(f);
				    	 Thread a1=new Thread(fter);
				    	 a1.start();
		            }
		               br.close();
		               r.close();
		            }catch(Exception e){
		            	e.printStackTrace();
		            }
		            
		         }    
		this.setSize(400, 300);
	}
}

