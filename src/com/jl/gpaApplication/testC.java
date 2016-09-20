package com.jl.gpaApplication;

import java.util.ArrayList;

import com.jl.extract.IKAnalysisSplit;

public class testC {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		  IKAnalysisSplit.dictionary();  //添加外部字典
		  
		  
		  
		  String  name1="Seats";   //创建性能指标表名
		  ArrayList positive1=new ArrayList();   //指标中正极性词
		  ArrayList negative1=new ArrayList();   //指标中负极性词
		  positive1.add("舒适");
		  positive1.add("柔软");
		  negative1.add("狭窄");
		  System.out.println("polar1");
		  Polarity po1=new Polarity(name1, positive1, negative1);
		  Thread a1=new Thread(po1);
		  a1.start();		 
		  a1.join(); 
		  
		     String  name2="Power";   //创建性能指标表名
			 ArrayList positive2=new ArrayList();   //指标中正极性词
			 ArrayList negative2=new ArrayList();   //指标中负极性词
			 positive2.add("强劲");
			 positive2.add("十足");
			 positive2.add("可以");
			 positive2.add("快");
			 negative2.add("弱");
			 negative2.add("很弱");
			 negative2.add("差");
			 negative2.add("极差");
			 negative2.add("慢");
			 negative2.add("一般");
			 System.out.println("polar2");
			 Polarity po2=new Polarity(name2, positive2, negative2);
			 Thread a2=new Thread(po2);
			 a2.start();		 
			 a2.join();
			 
			 String  name3="dazhong";   //创建性能指标表名
			 ArrayList positive3=new ArrayList();   //指标中正极性词
			 ArrayList negative3=new ArrayList();   //指标中负极性词
			 positive3.add("很棒");
			 positive3.add("普遍");
			 negative3.add("不普遍");			
			 System.out.println("polar3");
			 Polarity po3=new Polarity(name3, positive3, negative3);
			 Thread a3=new Thread(po3);
			 a3.start();		 
			 a3.join();
		  
			 String  name4="Appearance";   //创建性能指标表名
			 ArrayList positive4=new ArrayList();   //指标中正极性词
			 ArrayList negative4=new ArrayList();   //指标中负极性词
			 positive4.add("美观");
			 positive4.add("大方");
			 positive4.add("独特");
			 positive4.add("漂亮");
			 positive4.add("很漂亮");
			 positive4.add("好看");
			 negative4.add("丑");
			 negative4.add("难看");
			 negative4.add("很难看");
			 negative4.add("山寨");
			 negative4.add("模仿");
			 System.out.println("polar4");
			 Polarity po4=new Polarity(name4, positive4, negative4);
			 Thread a4=new Thread(po4);
			 a4.start();		 
			 a4.join();	
			 
			 String  name5="Noise";   //创建性能指标表名
			 ArrayList positive5=new ArrayList();   //指标中正极性词
			 ArrayList negative5=new ArrayList();   //指标中负极性词
			 positive5.add("小");
			 positive5.add("不大");
			 negative5.add("特别大");
			 negative5.add("大");
			 negative5.add("有点大");
			 System.out.println("polar5");
			 Polarity po5=new Polarity(name5, positive5, negative5);
			 Thread a5=new Thread(po5);
			 a5.start();	 
			 a5.join();
			 
			 String  name6="Space";   //创建性能指标表名
			 ArrayList positive6=new ArrayList();   //指标中正极性词
			 ArrayList negative6=new ArrayList();   //指标中负极性词
			 positive6.add("大");
			 positive6.add("更大");
			 positive6.add("很大") ;
			 negative6.add("小");
			 negative6.add("很小");		 
			 System.out.println("polar6");
			 Polarity po6=new Polarity(name6, positive6, negative6);
			 Thread a6=new Thread(po6);
			 a6.start();		 
			 a6.join();
			 
			 String  name7="customerService";   //创建性能指标表名
			 ArrayList positive7=new ArrayList();   //指标中正极性词
			 ArrayList negative7=new ArrayList();   //指标中负极性词
			 positive7.add("态度好");
			 positive7.add("时间长") ;
			 positive7.add("速度快") ;
			 positive7.add("好") ;
			 positive7.add("道歉") ;		 
			 negative7.add("差");
			 negative7.add("很差");
			 negative7.add("速度慢");
			 negative7.add("短");
			 negative7.add("态度差");
			 System.out.println("polar7");
			 Polarity po7=new Polarity(name7, positive7, negative7);
			 Thread a7=new Thread(po7);
			 a7.start();		 
			 a7.join();
		  
			 String  name8="Clutch";   //创建性能指标表名
			 ArrayList positive8=new ArrayList();   //指标中正极性词
			 ArrayList negative8=new ArrayList();   //指标中负极性词
			 positive8.add("顺畅") ;
			 positive8.add("好") ;
			 negative8.add("太软");
			 negative8.add("太硬");
			 negative8.add("异响");
			 System.out.println("polar8");
			 Polarity po8=new Polarity(name8, positive8, negative8);
			 Thread a8=new Thread(po8);
			 a8.start();		 
			 a8.join();
			 
			 String  name9="Performances";   //创建性能指标表名
			 ArrayList positive9=new ArrayList();   //指标中正极性词
			 ArrayList negative9=new ArrayList();   //指标中负极性词
			 positive9.add("好") ;
			 positive9.add("不错") ;
			 positive9.add("掌握") ;
			 negative9.add("一般");
			 negative9.add("差");
			 negative9.add("不好");
			 System.out.println("polar9");
			 Polarity po9=new Polarity(name9, positive9, negative9);
			 Thread a9=new Thread(po9);
			 a9.start();		 
			 a9.join();
			 
			 String  name10="Light";   //创建性能指标表名
			 ArrayList positive10=new ArrayList();   //指标中正极性词
			 ArrayList negative10=new ArrayList();   //指标中负极性词
			 positive10.add("亮") ;
			 positive10.add("好") ;
			 negative10.add("暗");
			 negative10.add("易碎");
			 negative10.add("容易坏");
			 System.out.println("polar10");
			 Polarity po10=new Polarity(name10, positive10, negative10);
			 Thread a10=new Thread(po10);
			 a10.start();		 
			 a10.join();			 
		 //QG.txt放入最常用的词组组配
		 String  name11="Mark";   //创建性能指标表名
		 ArrayList positive11=new ArrayList();   //指标中正极性词
		 ArrayList negative11=new ArrayList();   //指标中负极性词
		 positive11.add("好看") ;
		 negative11.add("难看");
		 negative11.add("丑");
		 System.out.println("polar11");
		 Polarity po11=new Polarity(name11, positive11, negative11);
		 Thread a11=new Thread(po11);
		 a11.start();		 
		 a11.join();
		 
		 String  name12="Shockabsorber";   //创建性能指标表名
		 ArrayList positive12=new ArrayList();   //指标中正极性词
		 ArrayList negative12=new ArrayList();   //指标中负极性词
		 positive12.add("好") ;
		 positive12.add("不错") ;
		 negative12.add("一般");
		 negative12.add("差");
		 negative12.add("不好");
		 System.out.println("polar12");
		 Polarity po12=new Polarity(name12, positive12, negative12);
		 Thread a12=new Thread(po12);
		 a12.start();		 
		 a12.join();
		 
			 String  name13="Vehicleparam";   //创建性能指标表名
			 ArrayList positive13=new ArrayList();   //指标中正极性词
			 ArrayList negative13=new ArrayList();   //指标中负极性词
			 positive13.add("重") ;
			 negative13.add("轻");
			 negative13.add("薄");
			 System.out.println("polar13");
			 Polarity po13=new Polarity(name13, positive13, negative13);
			 Thread a13=new Thread(po13);
			 a13.start();		 
			 a13.join();
			 
			 String  name14="Vehicletool";   //创建性能指标表名
			 ArrayList positive14=new ArrayList();   //指标中正极性词
			 ArrayList negative14=new ArrayList();   //指标中负极性词
			 positive14.add("齐全") ;
			 positive14.add("完备");
			 negative14.add("缺少");
			 negative14.add("不齐全");
			 System.out.println("polar14");
			 Polarity po14=new Polarity(name14, positive14, negative14);
			 Thread a14=new Thread(po14);
			 a14.start();		 
			 a14.join();
			 
		  
		 String  name15="Chassis";   //创建性能指标表名
		 ArrayList positive15=new ArrayList();   //指标中正极性词
		 ArrayList negative15=new ArrayList();   //指标中负极性词
		 positive15.add("高") ;
		 positive15.add("比较高") ;
		 negative15.add("低");
		 negative15.add("太低");
		 System.out.println("polar15");
		 Polarity po15=new Polarity(name15, positive15, negative15);
		 Thread a15=new Thread(po15);
		 a15.start();		 
		 a15.join();
		 
		 String  name16="Sparetire";   //创建性能指标表名
		 ArrayList positive16=new ArrayList();   //指标中正极性词
		 ArrayList negative16=new ArrayList();   //指标中负极性词
		 positive16.add("耐用") ;
		 negative16.add("窄");
		 negative16.add("太窄");
		 System.out.println("polar16");
		 Polarity po16=new Polarity(name16, positive16, negative16);
		 Thread a16=new Thread(po16);
		 a16.start();		 
		 a16.join();
		 
		 String  name17="Measure";   //创建性能指标表名
		 ArrayList positive17=new ArrayList();   //指标中正极性词
		 ArrayList negative17=new ArrayList();   //指标中负极性词
		 positive17.add("长") ;
		 negative17.add("短");
		 System.out.println("polar17");
		 Polarity po17=new Polarity(name17, positive17, negative17);
		 Thread a17=new Thread(po17);
		 a17.start();		 
		 a17.join();
		 
		 String  name18="Axis";   //创建性能指标表名
		 ArrayList positive18=new ArrayList();   //指标中正极性词
		 ArrayList negative18=new ArrayList();   //指标中负极性词
		 positive18.add("宽") ;
		 negative18.add("窄");
		 negative18.add("太窄");
		 System.out.println("polar18");
		 Polarity po18=new Polarity(name18, positive18, negative18);
		 Thread a18=new Thread(po18);
		 a18.start();		 
		 a18.join();
		 
		 String  name19="Suspension";   //创建性能指标表名
		 ArrayList positive19=new ArrayList();   //指标中正极性词
		 ArrayList negative19=new ArrayList();   //指标中负极性词
		 positive19.add("独立") ;
		 positive19.add("麦弗逊式独立") ;
		 positive19.add("好") ;
		 positive19.add("调校好") ;
		 negative19.add("太硬");
		 negative19.add("硬");
		 negative19.add("太软");
		 negative19.add("垃圾");
		 negative19.add("烂");
		 negative19.add("有缺陷");
		 negative19.add("支撑力差");
		 System.out.println("polar19");
		 Polarity po19=new Polarity(name19, positive19, negative19);
		 Thread a19=new Thread(po19);
		 a19.start();		 
		 a19.join();
		 
		 String  name20="Turn";   //创建性能指标表名
		 ArrayList positive20=new ArrayList();   //指标中正极性词
		 ArrayList negative20=new ArrayList();   //指标中负极性词
		 positive20.add("流畅") ;
		 positive20.add("顺畅") ;
		 negative20.add("打滑");
		 negative20.add("困难");
		 System.out.println("polar20");
		 Polarity po20=new Polarity(name20, positive20, negative20);
		 Thread a20=new Thread(po20);
		 a20.start();		 
		 a20.join();
		 
		 String  name21="Fuelconsumption";   //创建性能指标表名
		 ArrayList positive21=new ArrayList();   //指标中正极性词
		 ArrayList negative21=new ArrayList();   //指标中负极性词
		  	 positive21.add("小") ;
			 positive21.add("少") ;
			 positive21.add("不高") ;
			 positive21.add("低") ;
			 negative21.add("多");
			 negative21.add("不小");
			 negative21.add("大");
			 negative21.add("太高");
			 negative21.add("高");
			 negative21.add("偏高");
		 System.out.println("polar21");
		 Polarity po21=new Polarity(name21, positive21, negative21);
		 Thread a21=new Thread(po21);
		 a21.start();		 
		 a21.join();
		 
		 String  name22="Wheel";   //创建性能指标表名
		 ArrayList positive22=new ArrayList();   //指标中正极性词
		 ArrayList negative22=new ArrayList();   //指标中负极性词
		     positive22.add("宽") ;
		     negative22.add("窄");
			 positive22.add("抓地好") ;
			 positive22.add("耐用") ;
			 negative22.add("磨损");
			 negative22.add("打滑");
			 negative22.add("抓地不好");
		 System.out.println("polar22");
		 Polarity po22=new Polarity(name22, positive22, negative22);
		 Thread a22=new Thread(po22);
		 a22.start();		 
		 a22.join();
		 
		 String  name23="Driver";   //创建性能指标表名
		 ArrayList positive23=new ArrayList();   //指标中正极性词
		 ArrayList negative23=new ArrayList();   //指标中负极性词
		 positive23.add("有力") ;
		 positive23.add("强") ;
		 positive23.add("强劲") ;
		 positive23.add("强悍") ;
		 negative23.add("乏力");
		 negative23.add("弱");
		 System.out.println("polar23");
		 Polarity po23=new Polarity(name23, positive23, negative23);
		 Thread a23=new Thread(po23);
		 a23.start();		 
		 a23.join();
		 
		 String  name24="Engineperformance";   //创建性能指标表名
		 ArrayList positive24=new ArrayList();   //指标中正极性词
		 ArrayList negative24=new ArrayList();   //指标中负极性词
		 positive24.add("有力") ;
		 positive24.add("强") ;
		 positive24.add("强劲") ;
		 positive24.add("强悍") ;
		 negative24.add("乏力");
		 negative24.add("弱");
		 positive24.add("大") ;
		 positive24.add("高") ;
		 positive24.add("好") ;
		 negative24.add("小");
		 negative24.add("低");
		 negative24.add("太小");
		 negative24.add("太高");
		 negative24.add("太低");
		 negative24.add("差");
		 negative24.add("故障");
		 System.out.println("polar24");
		 Polarity po24=new Polarity(name24, positive24, negative24);
		 Thread a24=new Thread(po24);
		 a24.start();		 
		 a24.join();
		 
		 String  name25="Gearbox";   //创建性能指标表名
		 ArrayList positive25=new ArrayList();   //指标中正极性词
		 ArrayList negative25=new ArrayList();   //指标中负极性词
		 positive25.add("多") ;
		 positive25.add("流畅") ;
		 positive25.add("顺畅") ;
		 negative25.add("少");
		 negative25.add("顿挫");
		 System.out.println("polar25");
		 Polarity po25=new Polarity(name25, positive25, negative25);
		 Thread a25=new Thread(po25);
		 a25.start();		 
		 a25.join();
		 
			 String  name26="ABS";   //创建性能指标表名
			 ArrayList positive26=new ArrayList();   //指标中正极性词
			 ArrayList negative26=new ArrayList();   //指标中负极性词
			 positive26.add("好用") ;
			 positive26.add("有用") ;
			 negative26.add("没用");
			 System.out.println("polar26");
			 Polarity po26=new Polarity(name26, positive26, negative26);
			 Thread a26=new Thread(po26);
			 a26.start();		 
			 a26.join();
			 	
			 String  name27="OBD";   //创建性能指标表名
			 ArrayList positive27=new ArrayList();   //指标中正极性词
			 ArrayList negative27=new ArrayList();   //指标中负极性词
			 positive27.add("好用") ;
			 positive27.add("有用") ;
			 positive27.add("准确") ;
			 negative27.add("没用");
			 negative27.add("垃圾");
			 System.out.println("polar27");
			 Polarity po27=new Polarity(name27, positive27, negative27);
			 Thread a27=new Thread(po27);
			 a27.start();		 
			 a27.join();
			 
			 String  name28="Selfcheck";   //创建性能指标表名
			 ArrayList positive28=new ArrayList();   //指标中正极性词
			 ArrayList negative28=new ArrayList();   //指标中负极性词
			 positive28.add("好用") ;
			 positive28.add("有用") ;
			 positive28.add("准确") ;
			 negative28.add("没用");
			 negative28.add("垃圾");
			 System.out.println("polar28");
			 Polarity po28=new Polarity(name28, positive28, negative28);
			 Thread a28=new Thread(po28);
			 a28.start();		 
			 a28.join();	
			 
			 String  name29="ASR";   //创建性能指标表名
			 ArrayList positive29=new ArrayList();   //指标中正极性词
			 ArrayList negative29=new ArrayList();   //指标中负极性词
			 positive29.add("好用") ;
			 positive29.add("有用") ;
			 positive29.add("准确") ;
			 negative29.add("没用");
			 negative29.add("垃圾");
			 System.out.println("polar29");
			 Polarity po29=new Polarity(name29, positive29, negative29);
			 Thread a29=new Thread(po29);
			 a29.start();		 
			 a29.join();	
			 
			 String  name30="Antiskid";   //创建性能指标表名
			 ArrayList positive30=new ArrayList();   //指标中正极性词
			 ArrayList negative30=new ArrayList();   //指标中负极性词
			 positive30.add("效果好") ;
			 positive30.add("好") ;
			 negative30.add("效果差");
			 negative30.add("差");
			 negative30.add("严重");
			 System.out.println("polar30");
			 Polarity po30=new Polarity(name30, positive30, negative30);
			 Thread a30=new Thread(po30);
			 a30.start();		 
			 a30.join();
			 
			 String  name31="Turbo";   //创建性能指标表名
			 ArrayList positive31=new ArrayList();   //指标中正极性词
			 ArrayList negative31=new ArrayList();   //指标中负极性词
			 positive31.add("省油") ;
			 negative31.add("没效果");
			 negative31.add("费油");
			 negative31.add("耗油");
			 System.out.println("polar31");
			 Polarity po31=new Polarity(name31, positive31, negative31);
			 Thread a31=new Thread(po31);
			 a31.start();		 
			 a31.join();
			 
			 String  name32="Valve";   //创建性能指标表名
			 ArrayList positive32=new ArrayList();   //指标中正极性词
			 ArrayList negative32=new ArrayList();   //指标中负极性词
			 positive32.add("可变") ;
			 positive32.add("多") ;
			 negative32.add("密封不好") ;
			 negative32.add("磨损");
			 System.out.println("polar32");
			 Polarity po32=new Polarity(name32, positive32, negative32);
			 Thread a32=new Thread(po32);
			 a32.start();		 
			 a32.join();
			 			 
			 String  name33="Cylinder";   //创建性能指标表名
			 ArrayList positive33=new ArrayList();   //指标中正极性词
			 ArrayList negative33=new ArrayList();   //指标中负极性词
			 positive33.add("多") ;
			 negative33.add("少");
			 System.out.println("polar33");
			 Polarity po33=new Polarity(name33, positive33, negative33);
			 Thread a33=new Thread(po33);
			 a33.start();		 
			 a33.join();
			 
			 String  name34="Carburetor";   //创建性能指标表名
			 ArrayList positive34=new ArrayList();   //指标中正极性词
			 ArrayList negative34=new ArrayList();   //指标中负极性词
			 positive34.add("精巧");
			 positive34.add("优质");
			 negative34.add("粗糙");
			 negative34.add("劣质");
			 System.out.println("polar34");
			 Polarity po34=new Polarity(name34, positive34, negative34);
			 Thread a34=new Thread(po34);
			 a34.start();		 
			 a34.join();	
			 
			 String  name35="Tappet";   //创建性能指标表名
			 ArrayList positive35=new ArrayList();   //指标中正极性词
			 ArrayList negative35=new ArrayList();   //指标中负极性词
			 positive35.add("精巧");
			 positive35.add("优质");
			 positive35.add("有力");
			 negative35.add("粗糙");
			 negative35.add("劣质");
			 System.out.println("polar35");
			 Polarity po35=new Polarity(name35, positive35, negative35);
			 Thread a35=new Thread(po35);
			 a35.start();		 
			 a35.join();
			 
			 String  name36="Turbine";   //创建性能指标表名
			 ArrayList positive36=new ArrayList();   //指标中正极性词
			 ArrayList negative36=new ArrayList();   //指标中负极性词
			 positive36.add("精巧");
			 positive36.add("优质");
			 positive36.add("有力");
			 negative36.add("粗糙");
			 negative36.add("劣质");
			 System.out.println("polar36");
			 Polarity po36=new Polarity(name36, positive36, negative36);
			 Thread a36=new Thread(po36);
			 a36.start();		 
			 a36.join();
			 
			 String  name37="Rockershaft";   //创建性能指标表名
			 ArrayList positive37=new ArrayList();   //指标中正极性词
			 ArrayList negative37=new ArrayList();   //指标中负极性词
			 positive37.add("精巧");
			 positive37.add("优质");
			 positive37.add("有力");
			 negative37.add("粗糙");
			 negative37.add("劣质");
			 System.out.println("polar37");
			 Polarity po37=new Polarity(name37, positive37, negative37);
			 Thread a37=new Thread(po37);
			 a37.start();		 
			 a37.join();
			 
			 String  name38="EFI";   //创建性能指标表名
			 ArrayList positive38=new ArrayList();   //指标中正极性词
			 ArrayList negative38=new ArrayList();   //指标中负极性词
			 positive38.add("好");
			 negative38.add("不好");
			 negative38.add("劣质");
			 System.out.println("polar38");
			 Polarity po38=new Polarity(name38, positive38, negative38);
			 Thread a38=new Thread(po38);
			 a38.start();		 
			 a38.join();
			 
			 String  name39="Camshaft";   //创建性能指标表名
			 ArrayList positive39=new ArrayList();   //指标中正极性词
			 ArrayList negative39=new ArrayList();   //指标中负极性词
			 positive39.add("好");
			 negative39.add("不好");
			 negative39.add("劣质");
			 System.out.println("polar39");
			 Polarity po39=new Polarity(name39, positive39, negative39);
			 Thread a39=new Thread(po39);
			 a39.start();		 
			 a39.join();
			 
			 String  name40="Model";   //创建性能指标表名
			 ArrayList positive40=new ArrayList();   //指标中正极性词
			 ArrayList negative40=new ArrayList();   //指标中负极性词
			 positive40.add("好");
			 positive40.add("多");
			 positive40.add("合理");
			 negative40.add("不好");
			 negative40.add("少");
			 negative40.add("不合理");	 
			 System.out.println("polar40");
			 Polarity po40=new Polarity(name40, positive40, negative40);
			 Thread a40=new Thread(po40);
			 a40.start();		 
			 a40.join();
			 
			 String  name41="GPS";   //创建性能指标表名
			 ArrayList positive41=new ArrayList();   //指标中正极性词
			 ArrayList negative41=new ArrayList();   //指标中负极性词
			 positive41.add("稳定") ;
			 negative41.add("不稳定");
			 System.out.println("polar41");
			 Polarity po41=new Polarity(name41, positive41, negative41);
			 Thread a41=new Thread(po41);
			 a41.start();		 
			 a41.join();
	
			 
			 String  name42="ESP";   //创建性能指标表名
			 ArrayList positive42=new ArrayList();   //指标中正极性词
			 ArrayList negative42=new ArrayList();   //指标中负极性词
			 positive42.add("好");
			 negative42.add("不好");
			 negative42.add("劣质");
			 System.out.println("polar42");
			 Polarity po42=new Polarity(name42, positive42, negative42);
			 Thread a42=new Thread(po42);
			 a42.start();		 
			 a42.join();
			 
			 String  name43="Flick";   //创建性能指标表名
			 ArrayList positive43=new ArrayList();   //指标中正极性词
			 ArrayList negative43=new ArrayList();   //指标中负极性词
			 positive43.add("飘逸") ;
			 negative43.add("严重");
			 System.out.println("polar43");
			 Polarity po43=new Polarity(name43, positive43, negative43);
			 Thread a43=new Thread(po43);
			 a43.start();		 
			 a43.join();
			 
			 String  name44="Tow";   //创建性能指标表名
			 ArrayList positive44=new ArrayList();   //指标中正极性词
			 ArrayList negative44=new ArrayList();   //指标中负极性词
			 positive44.add("强劲") ;
			 positive44.add("有力") ;
			 negative44.add("弱");
			 System.out.println("polar44");
			 Polarity po44=new Polarity(name44, positive44, negative44);
			 Thread a44=new Thread(po44);
			 a44.start();		 
			 a44.join();
			 
			 String  name45="Sharpturn";
			 ArrayList positive45=new ArrayList();   //指标中正极性词
			 ArrayList negative45=new ArrayList();   //指标中负极性词
			 positive45.add("飘逸") ;
			 positive45.add("安全") ;
			 negative45.add("严重");
			 negative45.add("不安全");
			 System.out.println("polar45");
			 Polarity po45=new Polarity(name45, positive45, negative45);
			 Thread a45=new Thread(po45);
			 a45.start();		 
			 a45.join();
			 
			 String  name46="Sideslip";   //创建性能指标表名
			 ArrayList positive46=new ArrayList();   //指标中正极性词
			 ArrayList negative46=new ArrayList();   //指标中负极性词
			 positive46.add("不易") ;
			 negative46.add("严重");
			 negative46.add("容易");
			 System.out.println("polar46");
			 Polarity po46=new Polarity(name46, positive46, negative46);
			 Thread a46=new Thread(po46);
			 a46.start();		 
			 a46.join();
			 
			 String  name47="Gascirculation";   //创建性能指标表名
			 ArrayList positive47=new ArrayList();   //指标中正极性词
			 ArrayList negative47=new ArrayList();   //指标中负极性词
			 positive47.add("不易") ;
			 negative47.add("严重");
			 negative47.add("容易");
			 System.out.println("polar47");
			 Polarity po47=new Polarity(name47, positive47, negative47);
			 Thread a47=new Thread(po47);
			 a47.start();		 
			 a47.join();
			 
			 String  name48="EDB";   //创建性能指标表名
			 ArrayList positive48=new ArrayList();   //指标中正极性词
			 ArrayList negative48=new ArrayList();   //指标中负极性词
			 positive48.add("稳定") ;
			 negative48.add("不稳定");
			 System.out.println("polar48");
			 Polarity po48=new Polarity(name48, positive48, negative48);
			 Thread a48=new Thread(po48);
			 a48.start();		 
			 a48.join();
			
			 String  name49="ABD";   //创建性能指标表名
			 ArrayList positive49=new ArrayList();   //指标中正极性词
			 ArrayList negative49=new ArrayList();   //指标中负极性词
			 positive49.add("稳定") ;
			 negative49.add("不稳定");
			 System.out.println("polar49");
			 Polarity po49=new Polarity(name49, positive49, negative49);
			 Thread a49=new Thread(po49);
			 a49.start();		 
			 a49.join();
	
			 String  name50="Airbag";   //创建性能指标表名
			 ArrayList positive50=new ArrayList();   //指标中正极性词
			 ArrayList negative50=new ArrayList();   //指标中负极性词
			 positive50.add("耐用") ;
			 negative50.add("不好");
			 negative50.add("老化");
			 System.out.println("polar50");
			 Polarity po50=new Polarity(name50, positive50, negative50);
			 Thread a50=new Thread(po50);
			 a50.start();		 
			 a50.join();
	
			 String  name51="ABC";   //创建性能指标表名
			 ArrayList positive51=new ArrayList();   //指标中正极性词
			 ArrayList negative51=new ArrayList();   //指标中负极性词
			 positive51.add("稳定") ;
			 negative51.add("不稳定");
			 System.out.println("polar51");
			 Polarity po51=new Polarity(name51, positive51, negative51);
			 Thread a51=new Thread(po51);
			 a51.start();		 
			 a51.join();
	
			 String  name52="CH";   //创建性能指标表名
			 ArrayList positive52=new ArrayList();   //指标中正极性词
			 ArrayList negative52=new ArrayList();   //指标中负极性词
			 positive52.add("合理") ;
			 negative52.add("超标");
			 System.out.println("polar52");
			 Polarity po52=new Polarity(name52, positive52, negative52);
			 Thread a52=new Thread(po52);
			 a52.start();		 
			 a52.join();
	
			 String  name53="NO";   //创建性能指标表名
			 ArrayList positive53=new ArrayList();   //指标中正极性词
			 ArrayList negative53=new ArrayList();   //指标中负极性词
			 positive53.add("合理") ;
			 negative53.add("超标");
			 System.out.println("polar53");
			 Polarity po53=new Polarity(name53, positive53, negative53);
			 Thread a53=new Thread(po53);
			 a53.start();		 
			 a53.join();
	
			 String  name54="Brake";   //创建性能指标表名
			 ArrayList positive54=new ArrayList();   //指标中正极性词
			 ArrayList negative54=new ArrayList();   //指标中负极性词
			 positive54.add("灵敏") ;
			 positive54.add("短") ;
			 positive54.add("好") ;
			 negative54.add("不好");
			 negative54.add("硬");
			 negative54.add("软") ;
			 negative54.add("磨损") ;
			 negative54.add("长") ;
			 negative54.add("打滑") ;
			 System.out.println("polar54");
			 Polarity po54=new Polarity(name54, positive54, negative54);
			 Thread a54=new Thread(po54);
			 a54.start();		 
			 a54.join();
	
			 String  name55="Compression";   //创建性能指标表名
			 ArrayList positive55=new ArrayList();   //指标中正极性词
			 ArrayList negative55=new ArrayList();   //指标中负极性词
			 positive55.add("合理") ;
			 negative55.add("不合理") ;
			 System.out.println("polar55");
			 Polarity po55=new Polarity(name55, positive55, negative55);
			 Thread a55=new Thread(po55);
			 a55.start();		 
			 a55.join();
			 
			 String  name56="Tansmission";   //创建性能指标表名
			 ArrayList positive56=new ArrayList();   //指标中正极性词
			 ArrayList negative56=new ArrayList();   //指标中负极性词
			 positive56.add("合理") ;
			 negative56.add("不合理") ;
			 System.out.println("polar56");
			 Polarity po56=new Polarity(name56, positive56, negative56);
			 Thread a56=new Thread(po56);
			 a56.start();		 
			 a56.join();
	
			 String  name57="Safetybelt";   //创建性能指标表名
			 ArrayList positive57=new ArrayList();   //指标中正极性词
			 ArrayList negative57=new ArrayList();   //指标中负极性词
			 positive57.add("耐用") ;
			 negative57.add("老化");
			 System.out.println("polar57");
			 Polarity po57=new Polarity(name57, positive57, negative57);
			 Thread a57=new Thread(po57);
			 a57.start();		 
			 a57.join();
		
			 String  name58="WHIPS";   //创建性能指标表名
			 ArrayList positive58=new ArrayList();   //指标中正极性词
			 ArrayList negative58=new ArrayList();   //指标中负极性词
			 positive58.add("有用") ;
			 negative58.add("没用");
			 System.out.println("polar58");
			 Polarity po58=new Polarity(name58, positive58, negative58);
			 Thread a58=new Thread(po58);
			 a58.start();		 
			 a58.join();
			 
			 String  name59="Theftlock";   //创建性能指标表名
			 ArrayList positive59=new ArrayList();   //指标中正极性词
			 ArrayList negative59=new ArrayList();   //指标中负极性词
			 positive59.add("有用") ;
			 negative59.add("没用");
			 positive59.add("先进") ;
			 positive59.add("灵敏") ;
			 negative59.add("没用");
			 System.out.println("polar59");
			 Polarity po59=new Polarity(name59, positive59, negative59);
			 Thread a59=new Thread(po59);
			 a59.start();		 
			 a59.join();
	
			 String  name60="Rainfallsensor";   //创建性能指标表名
			 ArrayList positive60=new ArrayList();   //指标中正极性词
			 ArrayList negative60=new ArrayList();   //指标中负极性词
			 positive60.add("有用") ;
			 negative60.add("没用");
			 positive60.add("先进") ;
			 positive60.add("灵敏") ;
			 negative60.add("没用");
			 System.out.println("polar60");
			 Polarity po60=new Polarity(name60, positive60, negative60);
			 Thread a60=new Thread(po60);
			 a60.start();		 
			 a60.join();
	
			 String  name61="Suspension";   //创建性能指标表名
			 ArrayList positive61=new ArrayList();   //指标中正极性词
			 ArrayList negative61=new ArrayList();   //指标中负极性词
			 positive61.add("稳定") ;
			 negative61.add("不稳定");
			 System.out.println("polar61");
			 Polarity po61=new Polarity(name61, positive61, negative61);
			 Thread a61=new Thread(po61);
			 a61.start();		 
			 a61.join();
		  
			 String  name62="Ignition";   //创建性能指标表名
			 ArrayList positive62=new ArrayList();   //指标中正极性词
			 ArrayList negative62=new ArrayList();   //指标中负极性词
			 positive62.add("稳定") ;
			 positive62.add("敏感") ;
			 positive62.add("灵敏") ;
			 negative62.add("不稳定");
			 System.out.println("polar62");
			 Polarity po62=new Polarity(name62, positive62, negative62);
			 Thread a62=new Thread(po62);
			 a62.start();		 
			 a62.join();
		  
			 String  name63="Engine";   //创建性能指标表名
			 ArrayList positive63=new ArrayList();   //指标中正极性词
			 ArrayList negative63=new ArrayList();   //指标中负极性词
			 positive63.add("有力") ;
			 positive63.add("强") ;
			 positive63.add("强劲") ;
			 positive63.add("强悍") ;
			 negative63.add("乏力");
			 negative63.add("弱");
			 positive63.add("大") ;
			 positive63.add("高") ;
			 positive63.add("好") ;
			 negative63.add("小");
			 negative63.add("低");
			 negative63.add("太小");
			 negative63.add("太高");
			 negative63.add("太低");
			 negative63.add("差");
			 negative63.add("故障");
			 System.out.println("polar63");
			 Polarity po63=new Polarity(name63, positive63, negative63);
			 Thread a63=new Thread(po63);
			 a63.start();		 
			 a63.join();
		  
			 String  name64="LSD";   //创建性能指标表名
			 ArrayList positive64=new ArrayList();   //指标中正极性词
			 ArrayList negative64=new ArrayList();   //指标中负极性词
			 positive64.add("有用") ;
			 negative64.add("没用");
			 System.out.println("polar64");
			 Polarity po64=new Polarity(name64, positive64, negative64);
			 Thread a64=new Thread(po64);
			 a64.start();		 
			 a64.join();		  
	
			 String  name65="Battery";   //创建性能指标表名
			 ArrayList positive65=new ArrayList();   //指标中正极性词
			 ArrayList negative65=new ArrayList();   //指标中负极性词
			 positive65.add("耐用") ;
			 positive65.add("好用") ;
			 negative65.add("容易坏");
			 negative65.add("漏电");
			 negative65.add("出问题");
			 System.out.println("polar65");
			 Polarity po65=new Polarity(name65, positive65, negative65);
			 Thread a65=new Thread(po65);
			 a65.start();		 
			 a65.join();
	
			 String  name66="GOA";   //创建性能指标表名
			 ArrayList positive66=new ArrayList();   //指标中正极性词
			 ArrayList negative66=new ArrayList();   //指标中负极性词
			 positive66.add("有用") ;
			 negative66.add("没用");
			 System.out.println("polar66");
			 Polarity po66=new Polarity(name66, positive66, negative66);
			 Thread a66=new Thread(po66);
			 a66.start();		 
			 a66.join();		  
	
			 String  name67="SAHR";   //创建性能指标表名
			 ArrayList positive67=new ArrayList();   //指标中正极性词
			 ArrayList negative67=new ArrayList();   //指标中负极性词
			 positive67.add("有用") ;
			 negative67.add("没用");
			 positive67.add("舒适") ;
			 positive67.add("软") ;
			 negative67.add("不合理");
			 negative67.add("太硬");
			 System.out.println("polar67");
			 Polarity po67=new Polarity(name67, positive67, negative67);
			 Thread a67=new Thread(po67);
			 a67.start();		 
			 a67.join();		  
	
			 String  name68="DSE";   //创建性能指标表名
			 ArrayList positive68=new ArrayList();   //指标中正极性词
			 ArrayList negative68=new ArrayList();   //指标中负极性词
			 positive68.add("有用") ;
			 negative68.add("没用");
			 positive68.add("舒适") ;
			 positive68.add("软") ;
			 negative68.add("不合理");
			 negative68.add("太硬");
			 System.out.println("polar68");
			 System.out.println("polar68");
			 Polarity po68=new Polarity(name68, positive68, negative68);
			 Thread a68=new Thread(po68);
			 a68.start();		 
			 a68.join();		  
	
			 String  name69="EES";   //创建性能指标表名
			 ArrayList positive69=new ArrayList();   //指标中正极性词
			 ArrayList negative69=new ArrayList();   //指标中负极性词
			 positive69.add("有用") ;
			 negative69.add("没用");
			 positive69.add("舒适") ;
			 positive69.add("软") ;
			 negative69.add("不合理");
			 negative69.add("太硬");
			 System.out.println("polar69");
			 System.out.println("polar69");
			 Polarity po69=new Polarity(name69, positive69, negative69);
			 Thread a69=new Thread(po69);
			 a69.start();		 
			 a69.join();		  
	
 
			 String  name70="ITEC";   //创建性能指标表名
			 ArrayList positive70=new ArrayList();   //指标中正极性词
			 ArrayList negative70=new ArrayList();   //指标中负极性词
			 positive70.add("有用") ;
			 negative70.add("没用");
			 positive70.add("舒适") ;
			 positive70.add("软") ;
			 negative70.add("不合理");
			 negative70.add("太硬");
			 System.out.println("polar70");
			 System.out.println("polar70");
			 Polarity po70=new Polarity(name70, positive70, negative70);
			 Thread a70=new Thread(po70);
			 a70.start();		 
			 a70.join();		  
	
			 String  name71="Connectingcolumn";   //创建性能指标表名
			 ArrayList positive71=new ArrayList();   //指标中正极性词
			 ArrayList negative71=new ArrayList();   //指标中负极性词
			 positive71.add("有用") ;
			 negative71.add("没用");
			 System.out.println("polar71");
			 System.out.println("polar71");
			 Polarity po71=new Polarity(name71, positive71, negative71);
			 Thread a71=new Thread(po71);
			 a71.start();		 
			 a71.join();		  
	
			 String  name72="throttlevalve";   //创建性能指标表名
			 ArrayList positive72=new ArrayList();   //指标中正极性词
			 ArrayList negative72=new ArrayList();   //指标中负极性词
			 positive72.add("有用") ;
			 negative72.add("没用");
			 positive72.add("敏感") ;
			 System.out.println("polar72");
			 Polarity po72=new Polarity(name72, positive72, negative72);
			 Thread a72=new Thread(po72);
			 a72.start();		 
			 a72.join();		  
	  
		 
	}

}
