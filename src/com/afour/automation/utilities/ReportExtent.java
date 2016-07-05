package com.afour.automation.utilities;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.afour.automation.actions.SeleniumActions;
import com.afour.automation.scripts.Base;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class ReportExtent implements Reporting{

	private static String currentDir = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;			
	private static int folderCount = 0;
	private static int testCount = 0;
	public static String methodName;

	public ReportExtent() {
		// TODO Auto-generated constructor stub
		if(folderCount!=1){		
			currentDir = System.getProperty("user.dir");				
			extent = new ExtentReports(currentDir + "/Extent Report/Extent Report.html", false,DisplayOrder.OLDEST_FIRST);
			folderCount = 1;			     
		}				
	}



	public void log(String s, String result)
	{		

		methodName = Base.testName;
		System.out.println("======"+Base.testName);
		if(methodName!=null){	

			if(testCount!=1){
				testCount = 1;		
				test = extent.startTest(methodName);			
			}


			if(result.equals("Pass")){
				test.log(LogStatus.PASS, s);
				SuiteConfiguration.logger.info(s);
			}else{

				File scrFile = ((TakesScreenshot)SeleniumActions.webDriver).getScreenshotAs(OutputType.FILE);			
				try {
					FileUtils.copyFile(scrFile, new File(currentDir + "/Extent Report/"+Base.testName+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				test.log(LogStatus.FAIL, test.addScreenCapture(Base.testName+".jpg"));
				SuiteConfiguration.logger.error(s);
			}	
		}
	}

	public static void cleanUp()
	{
		testCount = 0;	
		extent.endTest(test);		
		extent.flush();

	}  
}
