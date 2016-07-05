package com.afour.automation.utilities;



public class Log4J implements Reporting{	
	
	@Override
	public void log(String stepDescription, String result) {
		
		if(result.equals("Pass")){			
			SuiteConfiguration.logger.info(stepDescription);
		}
		else{			
			SuiteConfiguration.logger.error(stepDescription);
	    }
		
	}
}
