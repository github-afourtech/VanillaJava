package com.afour.automation.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

@Listeners({ ATUReportsListener.class,ConfigurationListener.class,MethodListener.class })
public class ReportATU implements Reporting{

	public ReportATU(WebDriver driver) {

		ATUReports.setWebDriver(driver);
		setIndexPageDescription();

	}

	public ReportATU() {
		// TODO Auto-generated constructor stub
	}

	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("Amit", Utils.getCurrentTime(),"1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "My Project Description <br/> <b>Can include Full set of HTML Tags</b>";
	}

	public void log(String s, String result)
	{

		if(result.equals("Pass")){
			ATUReports.add(s, LogAs.PASSED, null);
			SuiteConfiguration.logger.info(s);
		}
		else{
			ATUReports.add(s, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			SuiteConfiguration.logger.error(s);
		}
	}	

}
