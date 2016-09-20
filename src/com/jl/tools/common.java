package com.jl.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.jl.classify.beginclassify;
import com.jl.view.infoCollection_view;
import com.jl.view.infoSummary_view;


public class common {
	
	public static HashMap<Integer,infoCollection_view> mp=new HashMap<Integer,infoCollection_view>();
	public static HashMap<Integer,infoSummary_view>     infoS=new HashMap<Integer,infoSummary_view>();
	
	public static HashMap<String,beginclassify>   thread=new HashMap<String,beginclassify>();
	
	public static ArrayList  words=new ArrayList();
	public static ArrayList  newords=new ArrayList();
	public static ArrayList  comp=new ArrayList();//过滤时用到的企业名称词表
	public static ArrayList  neg=new ArrayList();//过滤时用到处理产品属性词表
	
	public static ArrayList classnames=new ArrayList();//记录每个分类的名称
	
	public static HashMap<String,Integer> xunlian=new HashMap<String,Integer>();//记录每个分类训练文本的数目
	
	public static HashSet stopwords=new HashSet();//停用词表
	
	public static ArrayList tongxici=new ArrayList();
	
	public static HashMap<String,Integer>  othersWord=new HashMap<String,Integer>(); //存放others分类中的词计数
	
	public static HashMap<String,Integer>  productWord=new HashMap<String,Integer>(); //存放product分类中的词计数
	
  
}
