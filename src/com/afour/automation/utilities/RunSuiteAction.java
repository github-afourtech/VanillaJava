package com.afour.automation.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;




import testData.TestDataLoader;

public class RunSuiteAction {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			ClassLoader.getSystemClassLoader().loadClass("com.vanilla.automation.utilities.SuiteConfiguration");			
			TestNG runner=new TestNG();
			List<String> suitefiles=new ArrayList<String>();
			suitefiles.add(SuiteConfiguration.currentDir+"/TestNg.xml");			
			runner.setTestSuites(suitefiles);		
			runner.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
