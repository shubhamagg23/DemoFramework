/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shubham.automation.getpageobjects;

import static com.shubham.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.shubham.automation.utils.DataReadWrite.getProperty;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.shubham.automation.utils.SeleniumWait;

/**
 * 
 * @author Shubham Aggarwal
 */
public class BaseUi {

	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty(
				"Config.properties", "timeout")));
	}
	
	public void hardWaitForIEBrowser(int seconds) {
		  if (getProperty(
					"Config.properties", "browser").equalsIgnoreCase("IE")
		    || getProperty(
					"Config.properties", "browser")
		      .equalsIgnoreCase("ie")
		    || getProperty(
					"Config.properties", "browser")
		      .equalsIgnoreCase("internetexplorer")) {
		   wait.hardWait(seconds);
		  }
		 }

	protected String getPageTitle() {
		return driver.getTitle();
	}

	public void logMessage(String message) {
		Reporter.log(message, true);
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle);
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
		} catch (TimeoutException ex) {
			Assert.fail("FAILED: PageTitle for " + pageName
					+ " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
		logMessage("PASSED: PageTitle for " + pageName + " is exactly: '"
				+ expectedPagetitle + "'");
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 */
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	/**
	 * this method will get page title of current window and match it partially
	 * with the param provided
	 * 
	 * @param expectedPagetitle
	 *            partial page title text
	 */
	protected void verifyPageTitleContains(String expectedPagetitle) {

		try {
			wait.waitForPageTitleToContain(expectedPagetitle);
		} catch (TimeoutException exp) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("FAILED: As actual Page Title: '" + actualPageTitle
					+ "' does not contain expected Page Title : '"
					+ expectedPagetitle + "'.");
		}
		String actualPageTitle = getPageTitle().trim();
		logMessage("PASSED: PageTitle for " + actualPageTitle + " contains: '"
				+ expectedPagetitle + "'.");
	}
	
	public void hardWait(int seconds){
		try{
			Thread.sleep(seconds*1000);
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}

	protected void verifyPageUrlContains(String pageUrlPart){
		String currentUrl = getCurrentURL();
		assertThat("FAILED: wrong page. Url does not contain expected value", currentUrl, containsString(pageUrlPart));
		logMessage("PASSED: Current Page url '" + currentUrl + "' is expected!!!");
	}
	
	protected WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list No element
		// exception
		if (element == null) {
		}
		return element;
	}
	
	protected WebElement getElementByChangingText(WebElement element,String text)
	{		
		for(int i=1;i<=500;i++)
		{
			
			if(element.getText().trim().equals(text))
				{ 
				  
				break; }
		}
		
		return element;
					
	}

	
	protected WebElement getElementByContainsText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		// switchToDefaultContent();
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}
	
	public void switchToFrame(String id) {
		  driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}
	
	protected  String executeJavascript1(String script) {
		return (String) ((JavascriptExecutor) driver).executeScript(script);
	}
	
	protected Object clickUsingJavaScript(WebElement element) {
		  return ((JavascriptExecutor) driver).executeScript("arguments[0].baseURI;",element);
		 }

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}

	protected void handleAlert() {
		try {
			switchToAlert().accept();
			logMessage("Alert handled..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
		}
	}

	private Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}
	
	protected void scrollUp() {
		((JavascriptExecutor) driver).executeScript(
			    "window.scrollBy(0,-10000)");
	}


	protected void scrollMid(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("window.scrollBy(0, -100000)");
    	hardWait(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        hardWait(1);
        jse.executeScript("window.scrollBy(0,-350)");
        hardWait(1);
	}

	
	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! "
					+ ex2.getMessage());
		}
	}
	
	private boolean isElementAHyperlink(WebElement element){
		boolean flag = false;
		//System.err.println("****** tag Name = " + element.getTagName() );
		if(element.getTagName().equals("a")){
		flag = true;
		}else{
			flag = false;}
		return flag;
	}
	protected void verifyElementIsHyperlinked(WebElement element){
		Assert.assertTrue(isElementAHyperlink(element), "[Failed]: Element \'"+element.getText()+ "\' is not a hyperlink");
		logMessage("[Passed: Element \'"+element.getText()+ "\' is a hyperlink]");
	}
	
	protected void verifyElementIsNotHyperlinked(WebElement element){
		System.out.println(isElementAHyperlink(element));
		Assert.assertTrue(!(isElementAHyperlink(element)), "[Failed]: Element \'"+element.getText()+ "\' is a hyperlink");
		logMessage("[Passed: Element \'"+element.getText()+ "\' is not a hyperlink]");
	}
	
	public void selectUsingVisibleText(WebElement element,String visibleText)
	{
		Select s=new Select(element);
		s.selectByVisibleText(visibleText);	
	}
	
	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
	}
	
	public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	public void sendKeysUsingXpathInJavaScriptExecutor(WebElement element,
			   String text) {
			  JavascriptExecutor executor = (JavascriptExecutor) driver;
			  executor.executeScript("arguments[0].setAttribute('value', '" + text
			    + "')", element);
			 }
}
	

