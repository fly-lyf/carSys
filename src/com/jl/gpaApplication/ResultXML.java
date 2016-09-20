package com.jl.gpaApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ResultXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
		File  f=new File("setConfig/result.xml");
		if(f.exists()){
			f.delete();
		}
		File f2=new File("setConfig/result.xml");
		OutputStreamWriter isr = new OutputStreamWriter(new FileOutputStream(f2), "utf-8");
    	BufferedWriter br=new BufferedWriter(isr);
    	br.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	br.newLine();
    	br.write("<tree>");
    	br.newLine();
    	br.write("</tree>");
    	br.flush();
    	br.close();
    	isr.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

}
