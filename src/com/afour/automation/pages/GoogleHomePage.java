package com.afour.automation.pages;

import java.util.HashMap;

import com.afour.automation.actions.SeleniumActions;
import com.afour.automation.utilities.BrowserType;
import com.afour.automation.utilities.ElementsRepositoryHandler;
import com.afour.automation.utilities.IdentifierMethod;
import com.afour.automation.utilities.SuiteConfiguration;



public class GoogleHomePage {

	HashMap<String, HashMap <String, String>> hMap = null;
	ElementsRepositoryHandler efr = null;

	public GoogleHomePage()  {	
		
		
		efr = new ElementsRepositoryHandler();		
		try {
			hMap = efr.readXml("Repository");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	SuiteConfiguration.actions.openBrowser(SuiteConfiguration.getBrowser(SuiteConfiguration.actions.map.get("Browser")), SuiteConfiguration.actions.map.get("url"));

	}



	public boolean verifyGmailLink()	{
		
		return SuiteConfiguration.actions.exists(IdentifierMethod.LINKTEXT, hMap.get("GmailLink").get("LINKTEXT"));

	}
	public boolean verifySerchBox()	{
		return SuiteConfiguration.actions.exists(IdentifierMethod.XPATH,hMap.get("SearchTextBox").get("XPATH"));

	}

	public boolean verifySerchButton(){		
		return SuiteConfiguration.actions.exists(IdentifierMethod.XPATH, hMap.get("Feeling Lucky Button").get("XPATH"));
	}


}


