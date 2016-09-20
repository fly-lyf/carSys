/*
 * 鏁版嵁搴撲富琛ㄧ殑妯″瀷
 */
package com.jl.sql;
import java.util.*;
import java.sql.*;
import java.math.*;
import javax.swing.table.*;

import com.jl.domain.Comment;

public class LogAccessModel extends AbstractTableModel{

	Vector rowData,columnNames;
	
	ResultSet rs=null;
	
	public Pager pager;

	
	
	public int getTotalRows(String schField,String schStr)
	{
		commentService sc=new commentService();
		String countSql;
		int totalRows=0;
		countSql="select count(*) from information where ? like ? ";			
		try {
			
				totalRows=sc.getRows(countSql, schField, schStr);
			
						
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{			
			sc.Close();		
		}
		return totalRows;
	}
	
	//查询information表中的总行数
	public static int getTotalRows() 
	{
		   commentService sc=new commentService();
		   int totalRows=0;		
		
			totalRows=sc.queryForTotalRows();			
				
			sc.Close();
		
		   return totalRows;
	}
	
	
	
	public void init(String sql,Pager pager) throws Exception
	{			
		this.pager=pager;
		commentService accessHelper=new commentService();	
		if(sql=="")
		{	
			sql="select id,URLTIME,CHANNEL,URLNAME,	URLTITLE,CONTENT,AUTHOR from information limit ?,?";	
		}else
		{
			sql=sql+" limit ?,?";
		}
		int[] paras={pager.getStartRow(),pager.getPageSize()};
		
		columnNames=new Vector();
		columnNames.add("id");
		columnNames.add("时间");
		columnNames.add("来源");
		columnNames.add("来源网址");
		columnNames.add("标题");
		columnNames.add("内容");
		columnNames.add("作者");
		
		List<Comment> list=accessHelper.queryForPage(sql,paras);  //读取页面的条数	
		rowData=new Vector();			
		
		try {						
			for(int i=0;i<list.size();i++)
			{
				Vector logRow=new Vector();
				Comment cc=list.get(i);
				logRow.add(cc.getId());
				logRow.add(cc.getURLTIME());
				logRow.add(cc.getCHANNEL());
				logRow.add(cc.getURLNAME());
				logRow.add(cc.getURLTITLE());
				logRow.add(cc.getCONTENT());
				logRow.add(cc.getAUTHOR());
				
				rowData.add(logRow);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{
			
			accessHelper.Close();	
			
		}			
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}

	
	public LogAccessModel(String sql,Pager pager)
	{
		try{
		     this.init(sql,pager);
		     
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
	}

}



