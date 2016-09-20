package com.jl.productSql;

import com.jl.sql.Pager;

public class pageServiceProduct {
	public pageProduct getPager(String pagerMethod,pageProduct pager)
	{
		
		pageProduct p=pager;
		
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
