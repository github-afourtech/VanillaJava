package com.afour.automation.utilities;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.afour.automation.actions.SeleniumActions;




public class SuiteConfiguration {
	public static SeleniumActions actions;
	public static String currentDir;
	public static File logFile;
	public static Logger logger;

	static{
		currentDir = System.getProperty("user.dir");			
		actions = new SeleniumActions();
		logFile=new File(currentDir+"/AutomationTestNG.log");
		logger = Logger.getLogger(Log4J.class);
		if(logFile.exists()){				
			logFile.delete();
		}

		PropertyConfigurator.configure("log4j.properties");
	}

	public static BrowserType getBrowser(String browser){
		if(browser.equals("CHROME")){
			return BrowserType.CHROME;
		}
		else if (browser.equals("FIREFOX")){
			return BrowserType.FIREFOX;
		}
		else if (browser.equals("IE")){
			return BrowserType.IE;
		}
		else{
			return null;
		}

	}

}
