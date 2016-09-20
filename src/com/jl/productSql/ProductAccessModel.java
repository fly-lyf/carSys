package com.jl.productSql;
import java.util.*;
import java.sql.*;
import java.math.*;
import javax.swing.table.*;
import com.jl.domain.Comment;
import com.jl.domain.Product;


public class ProductAccessModel extends AbstractTableModel{

	Vector rowData,columnNames;
		
	public pageProduct pager;

	
	
	public int getTotalRows(String schField,String schStr)
	{
		productService ps=new productService();		
		String countSql;
		int totalRows=0;
		countSql="select count(*) from product where ? like ? ";			
		try {
			
				totalRows=ps.getRows(countSql, schField, schStr);
			
						
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{			
			ps.Close();		
		}
		return totalRows;
	}
	
	//查询information表中的总行数
	public static int getTotalRows() 
	{
		   productService ps=new productService();
		   int totalRows=0;		
		
			totalRows=ps.queryForTotalRows();			
				
			ps.Close();
		
		   return totalRows;
	}
	
	
	
	public void init(String sql,pageProduct pager) throws Exception
	{			
		this.pager=pager;
		productService accessHelper=new productService();	
		if(sql=="")
		{	
			sql="select id,CONTENT from product limit ?,?";	
		}else
		{
			sql=sql+" limit ?,?";
		}
		int[] paras={pager.getStartRow(),pager.getPageSize()};
		
		columnNames=new Vector();
		columnNames.add("id");
		columnNames.add("内容");
		
		List<Product> list=accessHelper.queryForPage(sql,paras);  //读取页面的条数	
		rowData=new Vector();			
		
		try {						
			for(int i=0;i<list.size();i++)
			{
				Vector logRow=new Vector();
				Product cc=list.get(i);
				logRow.add(cc.getId());
				logRow.add(cc.getCONTENT());
				
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

	
	public ProductAccessModel(String sql,pageProduct pager)
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



