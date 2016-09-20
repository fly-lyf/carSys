package com.jl.test;

public class Test {

	 public static void main(String[] args) throws Exception{
		 
	        MyThread thread = new MyThread();
	        thread.setFlag(true);
	        new Thread(thread).start();
	        Thread.sleep(5000);
	        thread.stopCurrentThread();
	    }

}
