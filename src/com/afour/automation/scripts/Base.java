package com.afour.automation.scripts;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.afour.automation.utilities.ReportExtent;
import com.afour.automation.utilities.SuiteConfiguration;
import com.afour.automation.utilities.TestDataHandler;



public class Base {


	public static String testName = null;
	private String testCaseName = null;
	static int count = 0;
	File file=new File("Extent Report");
	
	//Set Property for ATU Reporter Configuration
	{ 		   
		if(count!=1){				
			System.setProperty("atu.reporter.config", SuiteConfiguration.currentDir+"/atu.properties");
			count = 1;
		}
	}   

	public Base(){
		SuiteConfiguration suite;
	}


	@BeforeSuite
	public void init(){
	/*	try {
			ClassLoader.getSystemClassLoader().loadClass("com.vanilla.automation.utilities.SuiteConfiguration");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if((SuiteConfiguration.actions.getConfigData().get("ReportType")).equalsIgnoreCase("ExtentReport")){
	          if(file.exists()){
			        deleteFile(file);
			 }
			 else{
				file.mkdirs();
			 }
		}
	}
	public void deleteFile(File file){		

		File[] f=file.listFiles();
		if(null!=f){

			for(int i=0;i<f.length;i++){
				System.out.println(f[i].getName());
				if(f[i].isDirectory()){
					deleteFile(f[i]);
				}
				else{
					f[i].delete();
				}
			}
		}
	
	}

	//Getter for test caseName
	public String getTestCaseName(){
		testCaseName=this.getClass().getSimpleName();
		return testCaseName;
	}

	@DataProvider(name="csvData")
	public Object[][] csvData() {

		TestDataHandler handler =new TestDataHandler("");
		return handler.getDataMatrix(getTestCaseName()+".csv");

	}

	//Setter for TestCaseName
	@BeforeMethod
	public void setTestCaseName(Method method){  		
		testName = method.getName();
		
	   }

	public void initialize()
	{


	}

	@AfterMethod
	public void cleanUp()
	{
		if((SuiteConfiguration.actions.getConfigData().get("ReportType")).equalsIgnoreCase("ExtentReport")){
			ReportExtent.cleanUp(); 			
		}

	}
	@AfterTest()
	public void close(){	
		SuiteConfiguration.actions.closeBrowser();
	}



}
