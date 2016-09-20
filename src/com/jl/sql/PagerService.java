package com.jl.sql;


public class PagerService {

	public Pager getPager(String pagerMethod,Pager pager)
	{
		
		Pager p=pager;
		
		if(pagerMethod!=null)
		{
			if(pagerMethod.equals("firstPage"))
			{
				p.firstPage();
				
			}else if(pagerMethod.equals("previousPage"))
			{
				p.previousPage();
			}else if(pagerMethod.equals("nextPage"))
			{
				p.nextPage();
				System.out.println(pager.getCurrentPage());
			}else if(pagerMethod.equals("lastPage"))
			{
				p.lastPage();
			}
		}else{
			p.firstPage();
		}
		return p;
	}
}
