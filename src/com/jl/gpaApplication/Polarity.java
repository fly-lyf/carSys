package com.jl.gpaApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jl.extract.IKAnalysisSplit;
import com.jl.polaritySql.polarityService;
import com.jl.sql.CreateDatabase;
import com.jl.tools.common;

public class Polarity implements Runnable {
	 String name="";
	 ArrayList<String> positive=new ArrayList<String>();
	 ArrayList<String> negative=new ArrayList<String>();
	 
	public Polarity(String name,ArrayList<String> positive,ArrayList<String> negative){
		this.name=name;
		this.positive=positive;
		this.negative=negative;
	}

    private static HashMap<String,HashMap<String,Integer>> cooccurrence_matrix(String[][] corpus)
    {
        HashMap<String,HashMap<String,Integer>> d=new HashMap<String,HashMap<String,Integer>>();
        int i,j,m;
        ArrayList<String> sort=new ArrayList();
        String w1,w2,wt;
        for(m=0;m<corpus.length;m++)
        {
            for(i=0;i<corpus[m].length;i++)
            {
                for(j=i;j<corpus[m].length;j++)
                {
                    w1=corpus[m][i];
                    w2=corpus[m][j];
                    if(w1.equals(w2)) continue;
                    if(w2.compareTo(w1)<0)
                    {
                        wt=w1;
                        w1=w2;
                        w2=wt;
                    }
                    if(!d.containsKey(w1))
                    {
                        d.put(w1,new HashMap<String,Integer>());
                    }
                    if(!d.get(w1).containsKey(w2))
                    {
                        d.get(w1).put(w2,0);
                    }
                    d.get(w1).put(w2,d.get(w1).get(w2)+1);                    
                   
                }
            }
        }
       
        return d;
    }
    
    private static ArrayList<String> get_sorted_vocab(HashMap<String,HashMap<String,Integer>> d)
    {
        HashMap<String,Integer> vocab=new HashMap<String,Integer>();
        Set<Map.Entry<String, HashMap<String,Integer>>> keyEntrySet = d.entrySet();
        Set<String> key2;
        for(Map.Entry<String,  HashMap<String,Integer>> entry:keyEntrySet) 
        { 
            String key = entry.getKey(); 
            vocab.put(key,0);
            key2 = entry.getValue().keySet(); 
            for(String str:key2)
            {
                vocab.put(str,0);
            }
       }  
       ArrayList<String> vocab2=new ArrayList(vocab.keySet());
       Collections.sort(vocab2);
       return vocab2;
    }
    
    private static HashMap<String,HashMap<String,Double>> cosine_similarity_matrix(ArrayList<String> vocab,HashMap<String,HashMap<String,Integer>> d)
    {
        HashMap<String,HashMap<String,Double>> cm=new HashMap<String,HashMap<String,Double>>();
        HashMap<String,ArrayList<Integer>> vectors=get_vector(d,vocab);
        HashMap<String,Double> dou;
        for(String w1:vocab)
        {
            dou=new HashMap<String,Double>();
            for(String w2:vocab)
            {
                dou.put(w2,cosim(vectors.get(w1),vectors.get(w2)));
            }
            cm.put(w1,dou);
        }
        return cm;
    }
    private static HashMap<String,ArrayList<Integer>> get_vector(HashMap<String,HashMap<String,Integer>> d , ArrayList<String> vocab)
    {
        HashMap<String,ArrayList<Integer>> vecs=new HashMap<String,ArrayList<Integer>>();
        ArrayList<Integer> v;
        String wA,wB;
        for(String w1:vocab)
        {
            v=new ArrayList<Integer>();
            for(String w2:vocab)
            {
                    if(w2.compareTo(w1)<0)
                    {
                        wA=w2;
                        wB=w1;
                    }else
                    {
                        wA=w1;
                        wB=w2;
                    }
                    if(wA.equals(wB)) v.add(0);
                    else
                    {
                        if(d.containsKey(wA))
                        {
                            if(d.get(wA).containsKey(wB))v.add(d.get(wA).get(wB));
                            else v.add(0);
                        }else v.add(0);
                    }
            }
            vecs.put(w1, v);
        }
        
        return vecs;
    }
    
    private static double cosim(ArrayList<Integer> v1,ArrayList<Integer> v2)
    {
        int a=0;
        int b=0;
        int c=0;
        int n1,n2;
        for(int i=0;i<v1.size();i++)
        {
            n1=v1.get(i);
            n2=v2.get(i);
            a+=n1*n2;
            b+=n1*n1;
            c+=n2*n2;
        }
        double ben=Math.sqrt(b)*Math.sqrt(c);
        if(ben!=0.0)
        {
            return a/ben;
        }else return 0.0;
    }
    private static HashMap<String,Double> graph_propagation(HashMap<String,HashMap<String,Double>>cm,ArrayList<String> vocab,ArrayList<String> positive,ArrayList<String> negative,int iterations)
    {
        HashMap<String,Double> pol=new HashMap<String,Double>();//返回每个词的极性
        HashMap<String,HashMap<String,Double>> a=new HashMap();
        Iterator it=cm.entrySet().iterator();
        Iterator it2;
        String w1,w2;
        HashMap<String,Double> hm1;
        HashMap<String,Double> hm2;
        while(it.hasNext())
        {
            Map.Entry<String,HashMap<String,Double>> en=(Map.Entry)it.next();
            w1=en.getKey();
            hm1=en.getValue();
            hm2=new HashMap<String,Double>();
            it2=hm1.keySet().iterator();
            while(it2.hasNext())
            {
                w2=(String)it2.next();
                if(w1.equals(w2))
                {
                    hm2.put(w2, 1.0);
                }else
                {
                    hm2.put(w2,0.0);
                }
            }
            a.put(w1,hm2);            
        }
        
        HashMap<String,Object> rs=propagate(positive,cm,vocab,a,iterations);
        HashMap<String,Double> pol_positive=(HashMap<String,Double>)rs.get("pol");
        rs=propagate(negative,cm,vocab,(HashMap<String,HashMap<String,Double>>)rs.get("a"),iterations);
        HashMap<String,Double> pol_negative=(HashMap<String,Double>)rs.get("pol");
        
        double sum1=0.0;
        double sum2=0.0;
        for(String key:pol_positive.keySet())
        {
            sum1+=pol_positive.get(key);
            sum2+=pol_negative.get(key);
        }
        double beta=sum1/sum2;
        for(String v:vocab)
        {
            pol.put(v,pol_positive.get(v)-(beta*pol_negative.get(v)));
        }
        return pol;
    }
    
    
    private static HashMap<String,Object> propagate(ArrayList<String> seedset , HashMap<String,HashMap<String,Double>> cm ,ArrayList<String> vocab , HashMap<String,HashMap<String,Double>> a , int iterations)
    {
        HashMap<String,Double> pol=new HashMap<String,Double>();
        
        HashMap<String,Object> rs=new HashMap<String,Object>();
        HashMap<String,Boolean> f;
        String wj;
        for(String wi:seedset)
        {
            f=new HashMap<String,Boolean>();
            f.put(wi, Boolean.TRUE);
            for(int t=0;t<iterations;t++)
            {
                for(String wk:cm.keySet())
                {
                    if(f.containsKey(wk))
                    {
                        for(Map.Entry<String,Double> en:cm.get(wk).entrySet())
                        {
                            wj=en.getKey();
                            a.get(wi).put(wj,Math.max(a.get(wi).get(wj), a.get(wi).get(wk)*cm.get(wk).get(wj)));
                            //System.out.println("a[wi:"+wi+"][wj:"+wj+"]=max(a[wi:"+wi+"][wj:"+wj+"],a[wi:"+wi+"][wk:"+wk+"]*cosine(wk:"+wk+",wj:"+wj+")");
                            f.put(wj, Boolean.TRUE);
                        }
                    }
                }
            }
        }
        double sum;
         for(String w3 : vocab)
         {
             sum=0.0;
             for(String w4:seedset)
             {
                 sum+=a.get(w4).get(w3);
             }
             pol.put(w3,sum);
         }
        
        rs.put("pol",pol);
        rs.put("a",a);
        return rs;
    }
    
    public static HashMap<String,Double> test(ArrayList<String[]> cc,ArrayList<String> pss,ArrayList<String> nss,int t,double gm)
    {
        //构建共现矩阵
        String[][] c = (String [][])cc.toArray(new String[0][0]);
        HashMap<String,HashMap<String,Integer>> d=cooccurrence_matrix(c);
        
        //构建排序词典
        ArrayList<String> vocab=get_sorted_vocab(d);
        //计算余弦相似度
        HashMap<String,HashMap<String,Double>> cm=cosine_similarity_matrix(vocab,d);
        
        
        HashMap<String,Double> prop=graph_propagation(cm,vocab,pss,nss,t);
        
        
        return prop;
        
    }
    /**
     * @param args the command line arguments
     * @throws Exception 
     */   
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
    	String tablename="Seats";   	
    	CreateDatabase cd=new CreateDatabase();
    	cd.createQG(tablename);
    	//加载停用词表
       FileReader f1=new FileReader("setConfig/stopwords.txt");
       BufferedReader br1=new BufferedReader(f1);
   	   String str1="";
   	    while((str1=br1.readLine())!=null){
   	    	//  System.out.println(str1);
	          common.stopwords.add(str1);
		
	   } 	
    	File f=new File("setConfig/wordsSim1.txt");
    	InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "utf-8");
    	BufferedReader br=new BufferedReader(isr);
    	String str="";
    	int max_c=0;
    	ArrayList<String[]> al=new ArrayList<String[]>();    	
    	while((str=br.readLine())!=null){
    		System.out.println(str);
    		String splitsen=IKAnalysisSplit.IKAnalysis_stopwords(str);//完成每个句子的分词和去除停用词 		
    		String[] aa=splitsen.split(" ");
    		al.add(aa);
    	}
    	
       String corpus[][]=new String[al.size()][];
       for(int x=0;x<al.size();x++){
    	   corpus[x]=al.get(x);
       }

        
        //构建共现矩阵
        HashMap<String,HashMap<String,Integer>> d=cooccurrence_matrix(corpus);
        
        //构建排序词典,得到训练集中所以的词
        ArrayList<String> vocab=get_sorted_vocab(d);
        
        //计算余弦相似度
        HashMap<String,HashMap<String,Double>> cm=cosine_similarity_matrix(vocab,d);
        
        ArrayList<String> positive=new ArrayList<String>();
        positive.add("宽敞");
        
        ArrayList<String> negative=new ArrayList<String>();
        negative.add("狭窄");
        
        //negative.add("horrible");
        HashMap<String,Double> prop=graph_propagation(cm,vocab,positive,negative,2);
        //System.out.print(prop);
        //遍历hashmap表，将利用共线概率求出的词匹配存入到数据库中
        Set set = prop.entrySet() ;
        java.util.Iterator it = prop.entrySet().iterator();
        polarityService ps=new polarityService();
        while(it.hasNext()){
        java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
        String word=(String) entry.getKey();// 返回与此项对应的键
        Double  value=(Double)entry.getValue();// 返回与此项对应的值
        ps.savePolarity(tablename, word, value);
                            
        }
        ps.Close();
        } 
    
    
       public void run(){
      
       try{	
    	   
    	 
          String tablename=name;       	
       	  CreateDatabase cd=new CreateDatabase();
       	  cd.createQG(tablename);
       	//加载停用词表
          FileReader f1=new FileReader("setConfig/stopwords.txt");
          BufferedReader br1=new BufferedReader(f1);
      	   String str1="";
      	    while((str1=br1.readLine())!=null){
   	          common.stopwords.add(str1);
   		
   	   }
       	
       	File f=new File("setConfig/wordsSim1.txt");
       	InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "utf-8");
       	BufferedReader br=new BufferedReader(isr);
       	String str="";
       	int max_c=0;
       	ArrayList<String[]> al=new ArrayList<String[]>();
       	
       	while((str=br.readLine())!=null){
       		String splitsen=IKAnalysisSplit.IKAnalysis_stopwords(str);//完成每个句子的分词和去除停用词
       		//System.out.println(splitsen);
       		String[] aa=splitsen.split(" ");
       		al.add(aa);
       	}
       	
          String corpus[][]=new String[al.size()][];
          for(int x=0;x<al.size();x++){
       	   corpus[x]=al.get(x);
          }

           
           //构建共现矩阵
           HashMap<String,HashMap<String,Integer>> d=cooccurrence_matrix(corpus);
           
           //构建排序词典,得到训练集中所以的词
           ArrayList<String> vocab=get_sorted_vocab(d);
           
           //计算余弦相似度
           HashMap<String,HashMap<String,Double>> cm=cosine_similarity_matrix(vocab,d);
           
           //negative.add("horrible");
           HashMap<String,Double> prop=graph_propagation(cm,vocab,positive,negative,2);
//           System.out.print(prop);
           System.out.println("prop "+tablename);
           //遍历hashmap表，将利用共线概率求出的词匹配存入到数据库中
           Set set = prop.entrySet() ;
           java.util.Iterator it = prop.entrySet().iterator();
           polarityService ps=new polarityService();
           while(it.hasNext()){
           java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
           String word=(String) entry.getKey();// 返回与此项对应的键
           Double  value=(Double)entry.getValue();// 返回与此项对应的值
           ps.savePolarity(tablename, word, value);
                                                      
           }
           ps.Close();
           System.out.println("polar ****");
        }catch(Exception e){
 	       e.printStackTrace();
         }
      
       }
      
    }
    


