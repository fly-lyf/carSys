package com.jl.sql;

public class Pager {

	public int flag=1;
	
	private int totalRows;
	
	private int totalPages;
	
	private int currentPage;
	
	private int startRow;
	
	private int pageSize=50;
		
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Pager()
	{
		this.totalRows=this.getTotalRows();
		totalPages=totalRows/pageSize;
		
		int mod=totalRows%pageSize;
		if(mod>0)
		{
			totalPages++;
		}
		currentPage=1;
		startRow=0;
	}
	
	public Pager(int totalRows)
	{
		this.totalRows=totalRows;
		totalPages=totalRows/pageSize;
		
		int mod=totalRows%pageSize;
		if(mod>0)
		{
			totalPages++;
		}
		currentPage=1;
		startRow=0;
	}
	
	
	public void firstPage()
	{
		currentPage=1;
		startRow=0;
		
	}
	
	
	public void previousPage()
	{
		if(currentPage==1)
		{
			return;
		}
		currentPage--;
		
		startRow=(currentPage-1)*pageSize;
	}
	
	public void nextPage()
	{
		if(currentPage<totalPages)
		{
			currentPage++;
		}
		
		startRow=(currentPage-1)*pageSize;
	}
	
	public void lastPage()
	{
		currentPage=totalPages;
		
		startRow=(currentPage-1)*pageSize;
	}
	
	public void refresh(int currentPage)
	{
		this.currentPage=currentPage;
		if(currentPage>totalPages)
		{
			lastPage();
		}
		
	}
}
