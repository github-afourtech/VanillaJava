package com.afour.automation.utilities;

import java.util.HashMap;

import testData.TestDataLoader;

public class TestDataHandler 
{
	private HashMap<String, String> testDataInfo = null;
	
	/**
	 * @param filePath
	 */
	public TestDataHandler(String filePath)
	{
		TestDataLoader testDataLoad = new TestDataLoader();
		testDataInfo=testDataLoad.testDataLoader(filePath);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public String getTestData(String key)
	{
		return testDataInfo.get(key);				
	}
	
	public Object[][] getDataMatrix(String fileName){
		  TestDataLoader testDataLoad = new TestDataLoader();
		  return testDataLoad.getTestDataByRowAndColumn(fileName);
		 }
	
	
}
