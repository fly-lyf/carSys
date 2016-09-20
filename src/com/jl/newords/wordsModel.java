package com.jl.newords;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.jl.tools.common;

public class wordsModel extends AbstractTableModel{
	Vector rowData,columnNames;
	public wordsModel(){
		columnNames=new Vector();
		columnNames.add("字典编号");
		columnNames.add("车企名词");
		
		rowData=new Vector();
		for(int g=0;g<common.words.size();g++){
		   Vector hang=new Vector();
		   hang.add(g);
		   hang.add(common.words.get(g));
		   rowData.add(hang);
		}
	}
	
	@Override
	public int getColumnCount() {
		
		return this.columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(arg0);
	}

	
}
