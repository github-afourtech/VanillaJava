package com.afour.automation.scripts;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.afour.automation.pages.GoogleHomePage;

public class TC_1 extends Base{
	
	GoogleHomePage page;
	
	@BeforeTest
	public void tests(){
		page = new GoogleHomePage();
	}
	
	@Test
	public void verifyLink(){
		
		
		
		if(page.verifyGmailLink()){
			System.out.println("Link is Present");
		}
		else{
			System.out.println("Link is NOT Present");
		}
	}

}
