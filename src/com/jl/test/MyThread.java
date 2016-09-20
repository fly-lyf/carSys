package com.jl.test;
public class MyThread extends Thread{
	 
    private volatile boolean flag;
 
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
 
    @Override
    public void run() {
      
        while (flag) {
            System.out.println("*****doJob");
        }
    }
 
    public synchronized void stopCurrentThread() {
        this.flag = false;
    }
}