
package com.shubham.automation.getpageobjects;

import static com.shubham.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetPage extends BaseUi {

	protected WebDriver driver;
	String pageName;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.driver = driver;
		this.pageName = pageName;
	}
	
	public void launchApplication(String applicationpath) {
		driver.manage().deleteAllCookies();
		driver.manage().deleteAllCookies();
		System.out.println("Cookies deleted");
		logMessage("Application url is :- " + applicationpath);
		driver.get(applicationpath);
	}

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
					.trim()));
		}
	}
	
	protected void refreshPage() {
		driver.navigate().refresh();
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}
	protected WebElement element1(String elementToken) {
		return element1(elementToken,"");
	}
	protected WebElement element2(String elementToken) {
		return element2(elementToken,"");
	}
	protected List<String>  getAllOptionsaOfDropDownBox(String elementToken){
		List<String> options=new ArrayList<String>();
		Select select = new Select(element(elementToken, "")); 
		List<WebElement> elements=select.getOptions();;
		 for(WebElement element: elements){
			 options.add(element.getText());
			 }
		 return options;
	}
	protected List<String>  getTextOfListElements(String elementToken){
		List<String> options=new ArrayList<String>();
		List<WebElement> elements=elements(elementToken);
		 for(WebElement element: elements){
			 options.add(element.getText());
			 }
		 return options;
	}
	protected List<String>  getTextOfListElements(String elementToken, String replacement){
		List<String> options=new ArrayList<String>();
		int count = 0; 
		while (count < 4){
		try{
		List<WebElement> elements=elements(elementToken,replacement);
		
		 for(WebElement element: elements){
			 options.add(element.getText());
			 }
		}
		 catch(StaleElementReferenceException e){
				System.out.println("inside StaleElementReferenceException");
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
			       count = count+1;
			}
		count = count+4;
		}
		 return options;
	
	}
	protected List<String>  getClassAttributeOfListElements(String elementToken, String replacement){
		List<String> options=new ArrayList<String>();
		List<WebElement> elements=elements(elementToken,replacement);
		 for(WebElement element: elements){
			 options.add(element.getAttribute("class"));
			 }
		 return options;
	}
	protected void selectOptionByVisibleText(String elementToken, String text) {
		Select sel = new Select(element(elementToken,""));
		sel.selectByVisibleText(text);
	}
	protected void selectOptionByValue(String elementToken, String text) {
		Select sel = new Select(element(elementToken, ""));
		sel.selectByValue(text);
		
	}
	
	protected void selectProvidedTextFromDropDown(WebElement el, String text) {

		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		try {
			sel.selectByVisibleText(text);
			logMessage("Step : Selected option is "+text);
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			// Select select = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			sel.selectByVisibleText(text);
			// logMessage("Element " + el + " could not be clicked! "
			// + ex2.getMessage());
		}
	}
	
	
	protected boolean isRadioButtonSelected(String elementToken) {
		boolean result = element(elementToken).isSelected();
		assertTrue(result, "Assertion Failed: radio button '" + elementToken
				+ "' is not selected.");
		logMessage("Assertion Passed: radio button " + elementToken
				+ " is selected.");
		return result;
	}

	protected WebElement element(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(driver
					.findElement(getLocator(elementToken,replacement)));
			System.out.println("element expression: " + elem);
		} catch (NoSuchElementException excp) {
			excp.printStackTrace();
			System.out.println("element expression: " + elem);
			fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
		}
		//System.out.println("element: " + elem);
		return elem;
	}
	protected WebElement element2(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeClickable(driver
					.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			//fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
			String message="FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!";
			throw new NoSuchElementException(message);
		}
		//System.out.println("Ele:"+ elem);
		return elem;
	}
	protected WebElement element1(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(driver
					.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			//fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
			String message="FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!";
			System.out.println(message);
			throw new NoSuchElementException(message);
		}
		return elem;
	}
	protected WebElement element(String elementToken, String first_Rep, String sec_Rep)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(driver
					.findElement(getLocator(elementToken, first_Rep,sec_Rep)));
			System.out.println("elemelem:: "+elem);
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	
	protected WebElement elementPresent(String elementToken, String first_Rep, String sec_Rep)
	throws NoSuchElementException
	{
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBePresent(getLocator(elementToken, first_Rep,sec_Rep));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	
	protected WebElement elementPresent(String token,String repl1) throws NoSuchElementException
	{
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBePresent(getLocator(token,repl1));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ token + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	
	
	
	protected WebElement elementPresent(String token) throws NoSuchElementException
	{
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBePresent(getLocator(token));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ token + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	
	protected List<WebElement> elementsPresent(String token) throws NoSuchElementException
	{
		List<WebElement> elem=null;
		try {
			elem = wait.waitForElementsToBePresent(getLocator(token));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ token + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(driver.findElements(getLocator(
				elementToken, replacement)));
	}
	protected List<WebElement> elementsWithOutWait(String elementToken, String replacement) {
		return driver.findElements(getLocator(elementToken, replacement));
	}
	
	protected List<WebElement> elements(String elementToken,String first_rep,String sec_repl)
	{
		return wait.waitForElementsToBeVisible(driver.findElements(getLocator(
				elementToken, first_rep,sec_repl)));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}
	
	protected List<WebElement> elementsWithOutWait(String elementToken) {
		return elementsWithOutWait(elementToken, "");
	}
	
	protected List<WebElement> elementsPresentInDOM(String elementToken)
	{
		return elementsPresent(elementToken);
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "with text " + elementTextReplace + "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName + " with text "
				+ elementTextReplace + " is displayed.");
		System.out.println("element returned:" + elementName);
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText(), expectedText,
				"Assertion Failed: element '" + elementName
						+ "' Text is not as expected: ");
		logMessage("Assertion Passed: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName
				+ " is displayed.");
		return result;
	}
	
	protected boolean isElementNotDisplayed(String elementName) {
		boolean result;
		try {
			wait.waitForPageToLoadCompletely();
			driver.findElement(getLocator(elementName));
			result = false;
		} catch (NoSuchElementException excp) {
			result = true;
		}
				
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "' is displayed.");
		logMessage("Assertion Passed: element " + elementName
				+ " is not displayed as intended.");
		return result;
	}
	
	protected boolean isElementNotDisplayed(String elementName, String replacement) {
		boolean result;
		try {
			wait.waitForPageToLoadCompletely();
			driver.findElement(getLocator(elementName, replacement));
			result = false;
		} catch (NoSuchElementException excp) {
			result = true;
		}
				
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "' is displayed.");
		logMessage("Assertion Passed: element " + elementName
				+ " is not displayed as intended.");
		return result;
	}
	
	protected void waitForElementToDisappear(String elementToken){
		wait.waitForElementToDisappear(element(elementToken));
		
	}
	
	protected void waitForElementToAppear(String elementToken){
		wait.waitForElementToAppear(element(elementToken));
		
	}

	protected By getLocator(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String first_Rep,String sec_Rep) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replace("%", first_Rep);
		locator[2] = locator[2].replace("$", sec_Rep);
		return getLocators(locator[1].trim(), locator[2].trim());
	}
	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		if(locator[2].contains("$")){
			locator[2] = locator[2].replace("$", replacement);
			}
		return getLocators(locator[1].trim(), locator[2].trim());
	}
	// TODO rename to distiguish between getlocator and getlocators
	private By getLocators(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}
	
	protected String getElementAttribute(String elementName,String attributeName) throws Exception {
			wait.waitForPageToLoadCompletely();
			String style = driver.findElement(getLocator(elementName)).getAttribute(attributeName);
			return style;
	}
	
	protected boolean checkIfElementIsThere(String eleString) {
		  int timeOut = 60;
		  int hiddenFieldTimeOut = 10;

		  boolean flag = false;
		  try {
		   wait.resetImplicitTimeout(0);
		   wait.resetExplicitTimeout(hiddenFieldTimeOut);
		if (driver.findElement(getLocator(eleString)).isDisplayed()) {
		    flag = true;
		    wait.resetImplicitTimeout(timeOut);
		    wait.resetExplicitTimeout(timeOut);
		   } else {
		    wait.resetImplicitTimeout(timeOut);
		    wait.resetExplicitTimeout(timeOut);
		    flag = false;
		   }
		  } catch (NoSuchElementException ex) {
		   wait.resetImplicitTimeout(timeOut);
		   wait.resetExplicitTimeout(timeOut);
		   flag = false;
		  }
		  return flag;
		 }
	
}
