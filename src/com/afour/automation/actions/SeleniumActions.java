package com.afour.automation.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;  
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.afour.automation.utilities.BrowserType;
import com.afour.automation.utilities.IdentifierMethod;
import com.afour.automation.utilities.Log4J;
import com.afour.automation.utilities.ReportATU;
import com.afour.automation.utilities.ReportExtent;
import com.afour.automation.utilities.Reporting;
import testData.TestDataLoader;





public class SeleniumActions {

	public static WebDriver webDriver = null;
	public static WebDriverWait wait = null;
	public HashMap<String,String> map = null;
	//private Utility utility = new Utility();
	private static Reporting report;


	
	public SeleniumActions() {
		// TODO Auto-generated constructor stub
		map = getConfigData();	
		
	}


	/** Gets Config data
	 * @return Hashtable
	 */
	public HashMap<String,String> getConfigData()
	{
		HashMap<String, String> hashMap= null;
		try {
			hashMap =  new TestDataLoader().testDataLoader("Config.properties");
		} catch (Exception e) {
			report.log("Unable to get config data :" + e.getMessage(), "Fail") ;

		}
		return hashMap;
	}

	/**
	 * Sets up the browser to be used for running automation
	 * @param browser : See {@link BrowserType}
	 */
	public  void setWebDriver(BrowserType browser) {
		DesiredCapabilities desiredCapabilities = null;
		switch (browser){
		case FIREFOX:
			desiredCapabilities = DesiredCapabilities.firefox();
			break;
		case CHROME:
			desiredCapabilities = DesiredCapabilities.chrome();
			break;
		case IE:
			desiredCapabilities = DesiredCapabilities.internetExplorer();
			break;
		}
		setWebDriver (browser, desiredCapabilities);

	}

	/**
	 * Sets up the Webdriver for running automation using the supplied browser type and desired capabilities
	 */
	public void setWebDriver(BrowserType browser, DesiredCapabilities desiredCapabilities) {

		//This code will be implemented if Selenium Grid needs to be implemented

		/*	if (SuiteConfiguration.useGrid){
			URL remoteDriverUrl = null;
			try {
				remoteDriverUrl = new URL(SuiteConfiguration.remoteDriverUrlString);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			webDriver = new RemoteWebDriver(remoteDriverUrl,desiredCapabilities);
			} 
		 */	
		try{
			switch (browser){
			case FIREFOX:
				webDriver = new FirefoxDriver(desiredCapabilities);
				break;
			case IE:
				System.setProperty("webdriver.ie.driver", "Drivers/IEDriverServer.exe");
				webDriver = new InternetExplorerDriver(desiredCapabilities);
				break;
			case CHROME:
				System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
				webDriver = new ChromeDriver(desiredCapabilities);
				break;
			}
			wait = new WebDriverWait(getWebDriver(),Integer.parseInt(map.get("TimeOut")));
			webDriver.manage().timeouts().implicitlyWait(Long.parseLong(map.get("ImplicitWait")), TimeUnit.SECONDS);
			webDriver.manage().window().maximize();
 
			System.out.println("-------------> "+map.get("ReportType"));
			if(map.get("ReportType").equals("ATUReport")){
				report = new ReportATU(webDriver);
			}
			else if (map.get("ReportType").equals("ExtentReport")){
				report = new ReportExtent();

			}
		
				report=new Log4J();
	

		}
		catch(WebDriverException wd)
		{
			report.log("Unable to set web driver :" + wd.getMessage(), "Fail") ;
		}
		catch(Exception e)
		{
			report.log("Unable to set web driver :" + e.getMessage(), "Fail") ;
		}

	}

	/**
	 * Gets the current instance of webDriver
	 */
	public  WebDriver getWebDriver()
	{	
		return webDriver;
	}


	/**
	 * Opens the browser
	 * @param browser
	 * @param url
	 */
	public  void openBrowser(BrowserType browser, String url)
	{
		try{
			setWebDriver(browser);
			getWebDriver().get(url);
			report.log("Open browser", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Unable to open browser :" + wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Unable to open browser :" + e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Finds WebElement
	 * @param method
	 * @param identifier
	 * @return
	 */
	public WebElement find(IdentifierMethod method,String identifier)
	{
		WebElement foundElement = null;

		try{
			if(wait==null){
				wait = new WebDriverWait(getWebDriver(),Integer.parseInt(map.get("TimeOut")));
			}
			
			Assert.assertNotNull(getWebDriver() , "Error browser is not opened.  Use Open Browser action");

			switch (method){
			case CLASSNAME:
				wait.until(ExpectedConditions.elementToBeClickable(By.className(identifier)));
				foundElement = getWebDriver().findElement(By.className(identifier));
				break;
			case CSSSELECTOR:
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifier)));
				foundElement = getWebDriver().findElement(By.cssSelector(identifier));
				break;
			case ID:
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
				foundElement = getWebDriver().findElement(By.id(identifier));
				break ;     
			case LINKTEXT:
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));
				foundElement = getWebDriver().findElement(By.linkText(identifier));

				break ;     
			case XPATH:
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
				foundElement = getWebDriver().findElement(By.xpath(identifier));
				break ;     
			case NAME:
				wait.until(ExpectedConditions.elementToBeClickable(By.name(identifier)));
				foundElement = getWebDriver().findElement(By.name(identifier));
				break;      
			case PARTIALLINKTEXT:
				wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifier)));
				foundElement = getWebDriver().findElement(By.partialLinkText(identifier));
				break  ;  
			case TAGNAME:
				wait.until(ExpectedConditions.elementToBeClickable(By.tagName(identifier)));
				foundElement = getWebDriver().findElement(By.tagName(identifier));
				break;
			default:
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
				foundElement = getWebDriver().findElement(By.id(identifier));
			}
			return foundElement;
		}
		catch(WebDriverException wd){		
			report.log("Not able to find the element :"+ foundElement + wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to find the element :"+ foundElement + e.getMessage(), "Fail") ;
		}
		return foundElement;

	}

	/**
	 * Finds all WebElements
	 * @param method
	 * @param identifier
	 * @return
	 */
	public List<WebElement> findAll(IdentifierMethod method,String identifier){

		List<WebElement> foundElements = null;
		try{
			Assert.assertNull(getWebDriver() , "Error browser is not opened.  Use Open Browser action");
			switch (method){
			case CLASSNAME:
				foundElements = getWebDriver().findElements(By.className(identifier));
				break;
			case CSSSELECTOR:
				foundElements = getWebDriver().findElements(By.cssSelector(identifier));
				break;
			case ID:
				foundElements = getWebDriver().findElements(By.id(identifier));
				break ;     
			case LINKTEXT:
				foundElements = getWebDriver().findElements(By.linkText(identifier));
				break ;     
			case XPATH:
				foundElements = getWebDriver().findElements(By.xpath(identifier));
				break ;     
			case NAME:
				foundElements = getWebDriver().findElements(By.name(identifier));
				break;      
			case PARTIALLINKTEXT:
				foundElements = getWebDriver().findElements(By.partialLinkText(identifier));
				break  ;  
			case TAGNAME:
				foundElements = getWebDriver().findElements(By.tagName(identifier));
				break;
			default:
				foundElements = getWebDriver().findElements(By.id(identifier));
			}   
			return foundElements;
		}
		catch(WebDriverException wd){
			report.log("Not able to find the elements :"+ foundElements + wd.getMessage(), "Fail") ;
		}
		catch (Exception e) {
			report.log("Not able to find the elements :"+ foundElements + e.getMessage(), "Fail") ;
		}
		return foundElements;
	}

	/**
	 * Clicks on the identified web element
	 * @param method
	 * @param identifier
	 */
	public void click(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					element.click();
					report.log("Clicked on element : "+ element , "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to click on element :"+ element + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to click on element :"+ element + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Closes the browser
	 */
	public void closeBrowser()
	{
		try{
			getWebDriver().quit();
			report.log("Close browser", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to close browser : "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to close browser : "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Closes the window
	 */
	public void closeWindow()
	{
		try{
			getWebDriver().close();
			report.log("Close Window", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to close Window : "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to close Window : "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Deletes all the cookies
	 */
	public void deleteAllCookies()
	{
		try{
			getWebDriver().manage().deleteAllCookies();
			report.log("Cookies deleted", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to delete cookies "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to delete cookies "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Deletes the given cookie
	 * @param cookieName
	 */
	public void deleteCookie(Cookie cookieName)
	{
		try{
			getWebDriver().manage().deleteCookie(cookieName);
			report.log("Cookie deleted", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to delete cookie "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to delete cookie "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Double clicks on the identified web element
	 * @param method
	 * @param identifier
	 */
	public void doubleClick(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;
		Actions action = new Actions(getWebDriver());
		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					action.doubleClick(element).perform();
					report.log("Double clicked on element : "+ element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to double click on element : "+ element + "  " +wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to double click on element : "+ element + "  " +e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Drags the source element to target element
	 * @param sourceMethod
	 * @param sourceIdentifier
	 * @param targetMethod
	 * @param targetIdentifier
	 */
	public void dragAndDrop(IdentifierMethod sourceMethod,String sourceIdentifier, IdentifierMethod targetMethod,String targetIdentifier)
	{
		WebElement sourceElement = find(sourceMethod, sourceIdentifier);
		WebElement targetElement = find(targetMethod, targetIdentifier);
		int count = 5;
		Actions action = new Actions(getWebDriver());
        Action builder;
		while(count>0)
		{
			try{

				if(sourceElement.isDisplayed() & sourceElement.isEnabled() & targetElement.isDisplayed() & targetElement.isEnabled())
				{
					//action.dragAndDrop(sourceElement, targetElement).click().perform();
				   action.clickAndHold(sourceElement).moveToElement(targetElement).perform();
					waitForTime(2);
					action.release(targetElement).build().perform();
				    // action.clickAndHold(sourceElement).moveToElement(targetElement).release(targetElement).build().perform();
					report.log("Drag and drop element from "+ sourceElement +" to "+ targetElement , "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to drag drop from "+ sourceElement +" to "+ targetElement + " : " + wd.getMessage() , "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to drag drop from "+ sourceElement +" to "+ targetElement + " : " + e.getMessage() , "Fail") ;
				}
			}
		}


	}

	/**
	 * Clicks on web element using javascript 
	 * @param method
	 * @param identifier
	 */
	public void clickJScript(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					js.executeScript("arguemts[0].click();", element);
					report.log("Clicked on element : "+ element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to click on element : "+ element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to click on element : "+ element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}


	}

	/**
	 * Executes javascript on identified web element
	 * @param method
	 * @param identifier
	 * @param script
	 */
	public void executeJavaScript(IdentifierMethod method,String identifier,String script)
	{
		WebElement element = find(method, identifier);
		int count = 5;
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					js.executeScript(script, element);
					report.log("Execute javascript on element : "+ element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to execute javascript on element : "+ element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to execute javascript on element : "+ element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}


	}

	/**
	 * Verifies the existence of the web element
	 * @param method
	 * @param identifier
	 * @return
	 */
	public boolean exists(IdentifierMethod method,String identifier)
	{

		WebElement element = find(method, identifier);

		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					report.log("Element exists : "+ element, "Pass") ;
					return true;
				}
				else {
					count--;
					if(count == 0){
						report.log("Element does not exists : "+ element, "Fail") ;
						return false;
					}
				}

			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Element does not exists : "+ element + " : " + wd.getMessage(), "Fail") ;
					return false;

				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("does not exists : "+ element + " : " + e.getMessage(), "Fail") ;
					return false;
				}
			}
		}

		return false;

	}

	/**
	 * Gets the text on the web element
	 * @param method
	 * @param identifier
	 * @return
	 */
	public String getText(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					report.log("Get text on element : "+ element, "Pass") ;
					return element.getText();				
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to get text for element : "+ element + " : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to get text for element : "+ element + " : " + e.getMessage(), "Fail") ;
				}
			}
		}
		return null;

	}

	/**
	 * Navigates on back page
	 */
	public void goBack()
	{
		try{
			getWebDriver().navigate().back();
			report.log("Navigate back ", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to go back : "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to go back : "+ e.getMessage(), "Fail") ;
		}
	}


	/**
	 * Navigates on forward page
	 */
	public void goForward()
	{
		try{
			getWebDriver().navigate().forward();
			report.log("Navigate forward ", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to go forward : "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to go forward : "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Takes the mouse curser to identified web element
	 * @param method
	 * @param identifier
	 */
	public void mouseHover(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;
		Actions action = new Actions(getWebDriver());
		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					action.moveToElement(element).perform();
					report.log("Mouse Hover on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Mouse Hover on element : " + element +" : " + wd.getMessage(), "Pass") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Mouse Hover on element : " + element +" : " + e.getMessage(), "Pass") ;
				}
			}
		}

	}

	/**
	 * Navigates to given URL
	 * @param URL
	 */
	public void navigateToURL(String URL)
	{
		try{
			getWebDriver().navigate().to(URL);
			report.log("Navigate to URL " + " : " + URL, "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to naviagte to URL : "+ wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to naviagte to URL : "+ e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Refreshes the current page
	 */
	public void refresh()
	{
		try{
			getWebDriver().navigate().refresh();
			report.log("Refresh" , "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to Refresh : " + wd.getMessage() , "Pass") ;
		}
		catch(Exception e){
			report.log("Not able to Refresh : " + e.getMessage() , "Pass") ;
		}
	}

	/**
	 * Right clicks on identified web element
	 * @param method
	 * @param identifier
	 */
	public void rightClick(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;
		Actions action = new Actions(getWebDriver());
		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					action.contextClick(element).perform();
					report.log("Right Click on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to right click on element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to right click on element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Selects element in combo box by visible text
	 * @param method
	 * @param identifier
	 * @param visibleText
	 */
	public void selectItemByVisibleText(IdentifierMethod method,String identifier, String visibleText)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					new Select(element).selectByVisibleText(visibleText);
					report.log("Select item by visible text on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by visible text on element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by visible text on element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Selects element in combo box by index
	 * @param method
	 * @param identifier
	 * @param index
	 */
	public void selectItemByIndex(IdentifierMethod method,String identifier, int index)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					new Select(element).selectByIndex(index);
					report.log("Select item by index on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by index on element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by index on element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Selects element in combo box by value
	 * @param method
	 * @param identifier
	 * @param value
	 */
	public void selectItemByValue(IdentifierMethod method,String identifier, String value)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					new Select(element).selectByValue(value);
					report.log("Select item by value on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by value on element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Select item by value on element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Sets text on the web element
	 * @param method
	 * @param identifier
	 * @param text
	 */
	public void setText(IdentifierMethod method,String identifier, String text)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					element.clear();
					element.sendKeys(text);
					report.log("Set text on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Set text on element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Set text on element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}


	/**
	 * Checks the checkbox or radiobutton
	 * @param method
	 * @param identifier
	 */
	public void check(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled() & !element.isSelected())
				{
					element.click();
					report.log("Check checkbox on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to check checkbox element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to check checkbox element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}


	/**
	 * Unchecks the checkbox or radiobutton
	 * @param method
	 * @param identifier
	 */
	public void uncheck(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled() & element.isSelected())
				{
					element.click();
					report.log("UnCheck checkbox on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Uncheck checkbox element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to Uncheck checkbox element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Submits the web element
	 * @param method
	 * @param identifier
	 */
	public void submit(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					element.submit();
					report.log("Submit on element : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to submit element : " + element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to submit element : " + element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Switches to default content
	 */
	public void switchToDefaultContent()
	{
		try{
			getWebDriver().switchTo().defaultContent();
			report.log("Switch to default content ", "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Not able to switch to default content : " + wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Not able to switch to default content : " + e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Switches to identified frame
	 * @param method
	 * @param identifier
	 */
	public void switchToFrame(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed() & element.isEnabled())
				{
					getWebDriver().switchTo().frame(element);
					report.log("Switch to frame : " + element, "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to switch to frame : " + element +" : "+ wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to switch to frame : " + element +" : "+ e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Switches to given window title
	 * @param windowTitle
	 */
	public void switchToWindow(String windowTitle)
	{
		try{
			Set<String> handles = getWebDriver().getWindowHandles();
			for(Iterator<String> it = handles.iterator(); it.hasNext();)
			{	
				getWebDriver().switchTo().window(windowTitle);
				if(getWebDriver().getTitle().equals(windowTitle))
				{
					report.log("Switch to Window " , "Pass") ;
					return;
				}

			}

		}

		catch(WebDriverException wd)
		{
			report.log("Not able to switch to Window : " + wd.getMessage(), "Fail") ;

		}
		catch(Exception e)
		{
			report.log("Not able to switch to Window : " + e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Verifies whether checkbox is selected or not
	 * @param method
	 * @param identifier
	 */
	public void verifyCheckbox(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				Assert.assertTrue(element.isDisplayed() & element.isEnabled() & element.isSelected(), "Checkbox is checked");
				report.log("Verified checked checkbox : " + element , "Pass") ;
				break;

			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Checkbox Verification Failed : "+element +" : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Checkbox Verification Failed : "+element +" : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Verifies the given page title
	 * @param pageTitle
	 */
	public void verifyPageTitle(String pageTitle)
	{
		try{
			Assert.assertEquals(getWebDriver().getTitle(), pageTitle);
			report.log("Page title verified " , "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("Page title verification failed : " + wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("Page title verification failed : " + e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Verifies the text of identified web element
	 * @param method
	 * @param identifier
	 * @param text
	 */
	public void verifyText(IdentifierMethod method,String identifier,String text)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					Assert.assertEquals(element.getText(), text);
					report.log("Text verification " , "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Text verification failed : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Text verification failed : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

	/**
	 * Verifies the current URL
	 * @param URL
	 */
	public void verifyURL(String URL)
	{
		try{
			Assert.assertEquals(getWebDriver().getCurrentUrl(), URL);
			report.log("URL verification " , "Pass") ;
		}
		catch(WebDriverException wd){
			report.log("URL verification failed : " + wd.getMessage(), "Fail") ;
		}
		catch(Exception e){
			report.log("URL verification failed : " + e.getMessage(), "Fail") ;
		}
	}

	/**
	 * Clicks on web element when ready
	 * @param method
	 * @param identifier
	 */
	public void clickWhenReady(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method,identifier);

		try{
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			report.log("Click when ready : " + element , "Pass") ;
		}

		catch(WebDriverException wd)
		{
			report.log("Click when ready element : "+element +" : " + wd.getMessage(), "Fail") ;
		}
		catch(Exception e)
		{
			report.log("Click when ready element : "+element +" : " + e.getMessage(), "Fail") ;
		}


	}

	/** Waits for partiular element for specified amount of time 
	 * @param timeout
	 * @param method
	 * @param identifier
	 */
	public void waitForElementExplicitly(int timeout, IdentifierMethod method,String identifier){
		WebElement element = null;
		try{

			switch (method){
			case CLASSNAME:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.className(identifier)));
				break;
			case CSSSELECTOR:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifier)));
				break;
			case ID:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));

				break ;     
			case LINKTEXT:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));

				break ;     
			case XPATH:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));

				break ;     
			case NAME:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.name(identifier)));

				break;      
			case PARTIALLINKTEXT:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifier)));

				break  ;  
			case TAGNAME:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.tagName(identifier)));

				break;
			default:
				element = wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
			}


		}
		catch(WebDriverException wd)
		{
			report.log("Unable to wait : "+element + wd.getMessage(), "Fail") ;
		}
		catch(Exception e)
		{
			report.log("Unable to wait : "+element + e.getMessage(), "Fail") ;
		}

	}

	/**Verifies element is visible or not
	 * @param element
	 * @return
	 */
	public boolean isElementVisible(IdentifierMethod method,String identifier){
		WebElement element = find(method, identifier);
		try{

			if(element.isDisplayed()){
				return true;
			}
			else{
				return false;
			}
		}
		catch(WebDriverException wd)
		{
			report.log("Element not visible : "+element + wd.getMessage(), "Fail") ;
			return false;
		}
		catch(Exception e)
		{
			report.log("Element not visible : "+element + e.getMessage(), "Fail") ;
			return false;
		}
	}

	/**Verifies element is selected or not
	 * @param element
	 * @return
	 */
	public boolean isElementSelected(IdentifierMethod method,String identifier){

		WebElement element = find(method, identifier);
		try{

			if(element.isSelected()){
				return true;
			}
			else{
				return false;
			}
		}
		catch(WebDriverException wd)
		{
			report.log("Unable to select : "+element + wd.getMessage(), "Fail") ;
			return false;
		}
		catch(Exception e)
		{
			report.log("Unable to select : "+element + e.getMessage(), "Fail") ;
			return false;
		}
	}

	/**Verifies element is enabled or not
	 * @param element
	 * @return
	 */
	public boolean isElementEnabled(IdentifierMethod method,String identifier){
		WebElement element = find(method, identifier);
		try{

			if(element.isEnabled()){
				return true;
			}
			else{
				return false;
			}
		}
		catch(WebDriverException wd)
		{
			report.log("Element not enabled : "+element + wd.getMessage(), "Fail") ;
			return false;
		}
		catch(Exception e)
		{
			report.log("Element not enabled : "+element + e.getMessage(), "Fail") ;
			return false;
		}

	}


	/**
	 * Switches to identified Alert
	 * @param method
	 */
	public Alert switchToAlert()
	{
		try{
			Alert alert = getWebDriver().switchTo().alert();
			report.log("Switched to Alert : " , "Pass") ;
			return alert;
		}

		catch(WebDriverException wd)
		{

			report.log("Not able to switch to Alert : " + wd.getMessage(), "Fail") ;
			return null;

		}
		catch(Exception e)
		{

			report.log("Not able to switch to Alert : " + e.getMessage(), "Fail") ;
			return null;

		}
	}

	/** Waits for specified amount of time in seconds
	 * @param timeout
	 */
	public void waitForTime(int timeout){

		try{
			Thread.sleep(timeout*1000);
			report.log("Wait successful for "+timeout +"seconds" , "Pass") ;
		}
		catch(InterruptedException ie){
			report.log("Not able to wait : " + ie.getMessage(), "Fail") ;
		}

	}
 
	/** Verify Alert Message
	 * @param String
	 * 
	 */
	public void verifyAlertMessage(String messageText)
	{
		try{
			Alert alert = getWebDriver().switchTo().alert();

			if(alert.getText().equals(messageText)){
				report.log("Alert Verification successful : " , "Pass") ;
			}
			else{
				report.log("Alert Verification Failed : " , "Fail") ;
			}

		}

		catch(WebDriverException wd)
		{

			report.log("Not able to switch to Alert : " + wd.getMessage(), "Fail") ;

		}
		catch(Exception e)
		{

			report.log("Not able to switch to Alert : " + e.getMessage(), "Fail") ;


		}
	}
	/**
	 * Gets the text on the web element
	 * @param method
	 * @param identifier
	 * @return
	 */
	public String getTextAttribute(IdentifierMethod method,String identifier)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					report.log("Get text on element : "+ element, "Pass") ;
					return element.getAttribute("value");			
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to get text for element : "+ element + " : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Not able to get text for element : "+ element + " : " + e.getMessage(), "Fail") ;
				}
			}
		}
		return null;
	}
	/**
	 * Verifies the text of identified web element
	 * @param method
	 * @param identifier
	 * @param text
	 */
	public void verifyTextAttribute(IdentifierMethod method,String identifier,String text)
	{
		WebElement element = find(method, identifier);
		int count = 5;

		while(count>0)
		{
			try{

				if(element.isDisplayed())
				{
					Assert.assertEquals(element.getAttribute("value"), text);
					report.log("Text verification " , "Pass") ;
					break;
				}
				else{
					count--;
				}
			}

			catch(WebDriverException wd)
			{
				count--;
				if(count == 0)
				{
					report.log("Text verification failed : " + wd.getMessage(), "Fail") ;
				}
			}
			catch(Exception e)
			{
				count--;
				if(count == 0)
				{
					report.log("Text verification failed : " + e.getMessage(), "Fail") ;
				}
			}
		}

	}

}
