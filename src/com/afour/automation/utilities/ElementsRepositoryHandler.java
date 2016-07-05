package com.afour.automation.utilities;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import objectRepository.ObjectRepositoryLoader;

public class ElementsRepositoryHandler {
	WebDriver driver = null;
	String currentDir = System.getProperty("user.dir");
	static ObjectRepositoryLoader objRepo = null;
	
	public ElementsRepositoryHandler(){}
	
	public ElementsRepositoryHandler(WebDriver driver){
		this.driver = driver ;
		
	}
	
	/**
	 * @param FileName
	 * @return
	 * @throws Exception
	 */
	public  HashMap<String, HashMap <String, String>>readXml(String FileName)throws Exception
	{
			HashMap<String, HashMap <String, String>> mHashMap =new HashMap<String, HashMap <String, String>>();
			objRepo = new ObjectRepositoryLoader();
			String FilePath= currentDir+"//Element Repository//"+FileName+".xml";
			mHashMap=objRepo.objectRepositoryLoader(FilePath);
			return mHashMap;
	}
	 
	
}

