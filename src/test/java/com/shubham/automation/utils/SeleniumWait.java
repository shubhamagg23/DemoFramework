package com.shubham.automation.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWait{
    
    WebDriver driver;
    WebDriverWait wait;
    
    public int timeout;
    
    public SeleniumWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.timeout = timeout;
    }

    /**
     * Returns webElement found by the locator if element is visible
     *
     * @param locator
     * @return
     */
    public WebElement getWhenVisible(By locator) {
        WebElement element;
        element = wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator));
        return element;
    }
    
    public WebElement getWhenClickable(By locator) {
        WebElement element;
        element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }
    
    public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
        return wait.until(ExpectedConditions.titleIs(expectedPagetitle));
    }
    
    public boolean waitForPageTitleToContain(String expectedPagetitle) {
        return wait.until(ExpectedConditions.titleContains(expectedPagetitle));
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public WebElement waitForElementToBePresent(By locator)
    {
    	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    	
    }
    public List<WebElement> waitForElementsToBePresent(By locator)
    {
    	return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
    
    public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    
    public boolean waitForElementToBeInVisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public void clickWhenReady(By locator) {
        WebElement element = wait.until(ExpectedConditions
                .elementToBeClickable(locator));
        element.click();
    }


    public void waitForMsgToastToDisappear() {
        int i = 0;
        resetImplicitTimeout(1);
        try {
            while (driver.findElement(By.className("toast-message")).isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
                System.out.println("i==="+ i);
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void waitForElementToDisappear(WebElement element) {
        int i = 0;
        System.out.println("waitForElementToDisappear");
        System.out.println("element.isDisplayed()"+ element.isDisplayed());
        resetImplicitTimeout(2);
        try {
            while (element.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
                System.out.println(i + " Seconds");  
            }
        } catch (Exception e) {
        	System.out.println("exception thrown");
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void waitForElementToAppear(WebElement element) {
        int i = 0;
        System.out.println("waiting For Element To Appear");
        System.out.println("element.isDisplayed()==="+ element.isDisplayed());
        resetImplicitTimeout(2);
//        hardWait(1);
        try {
        	System.out.println("try");
            while (!(element.isDisplayed()) && i <= timeout) {
            	System.out.println("while");
                hardWait(1);                
                i++;
                System.out.println(i + " Seconds");
            }
        } catch (StaleElementReferenceException e) {
        	System.out.println("exception thrown");
        }
        System.out.println("exitig loop");
        resetImplicitTimeout(timeout);   
        System.out.println("exiting function");
    }
    
    public void waitForExactValueOfElement(WebElement element, String ExpectedText) {
        int i = 0;
        resetImplicitTimeout(1);
        try {
            while (!((element.getText()).equals(ExpectedText)) && i <= timeout) {
                hardWait(1);                
                i++;
               System.out.println(i + " Seconds");
            }
         
        } catch (Exception e) {
        	System.out.println();
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void resetImplicitTimeout(int newTimeOut) {
    	  try {
            driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
        } catch (StaleElementReferenceException e) {	
        }
        
    }

    // TODO Implement Wait for page load for page synchronizations
    public void waitForPageToLoadCompletely() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//*")));
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    @FindBy(id = "loadingBlock")
    WebElement loader;
    
    public void waitForLoaderToDisappear() {
        int i = 0;
        resetImplicitTimeout(5);
        try {
            while (loader.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {	
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void resetExplicitTimeout(int newTimeOut) {
		  try {
		   this.wait = new WebDriverWait(driver, newTimeOut);
		  } catch (Exception e) {
		  }
		 }
    
}
