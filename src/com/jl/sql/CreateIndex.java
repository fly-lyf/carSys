package com.jl.sql;

public class CreateIndex {

	//create index
	String addAccessIndex1="ALTER TABLE `information` ADD INDEX `idindex` (`id`) USING BTREE ;";
	String addAccessIndex2="ALTER TABLE `information` ADD INDEX `channeindex` (`CHANNEL`) USING BTREE ;";
	String addAccessIndex3="ALTER TABLE `information` ADD INDEX `authorindex` (`AUTHOR`) USING BTREE ;";
	//delete index sentences
	
	String dropAccessIndex1="ALTER TABLE `information` DROP INDEX `idindex`;";
	String dropAccessIndex2="ALTER TABLE `information` DROP INDEX `channeindex`;";
	String dropAccessIndex3="ALTER TABLE `information` DROP INDEX `authorindex`;";
	
	
	
	
	public void createIndex()
	{
		commentService sc=new commentService();
		sc.upExecute(addAccessIndex1);
		sc.upExecute(addAccessIndex2);
		sc.upExecute(addAccessIndex3);
		System.out.println("index created");
		sc.Close();
	}
	
	public void deleteIndex()
	{
		commentService sc=new commentService();
		sc.upExecute(dropAccessIndex1);
		sc.upExecute(dropAccessIndex2);
		sc.upExecute(dropAccessIndex3);		
		System.out.println("index removed");
		sc.Close();
			
		
	}
	
}
