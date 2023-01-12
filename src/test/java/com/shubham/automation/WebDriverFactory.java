/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shubham.automation;

import static com.shubham.automation.utils.YamlReader.getData;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.google.common.base.Strings;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    private static String browser;
    private static DesiredCapabilities capabilities = new DesiredCapabilities();

    public WebDriver getDriver(Map<String, String> seleniumconfig) {
    	if(!Strings.isNullOrEmpty(System.getProperty("browser"))){
    		browser = System.getProperty("browser");	
    	}else{
    		browser = seleniumconfig.get("browser").toString();
    	}
    	System.out.println("browser="+ browser);
        if (seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("local")) {
            if (browser.equalsIgnoreCase("firefox")) {
                return getFirefoxDriver();
            } else if (browser.equalsIgnoreCase("chrome")) {
                return getChromeDriver(seleniumconfig.get("driverpath")
                        .toString());
            } else if (browser.equalsIgnoreCase("Safari")) {
                return getSafariDriver();
            } else if ((browser.equalsIgnoreCase("ie"))
                    || (browser.equalsIgnoreCase("internetexplorer"))
                    || (browser.equalsIgnoreCase("internet explorer"))) {
                return getInternetExplorerDriver(seleniumconfig.get(
                        "driverpath").toString());
            }
        }
        if (seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("remote")) {
            return setRemoteDriver(seleniumconfig);
        }
        return new FirefoxDriver();
    }

    private WebDriver setRemoteDriver(Map<String, String> selConfig) {
        DesiredCapabilities cap = null;
                if(!Strings.isNullOrEmpty(System.getProperty("browser"))){
        	browser = System.getProperty("browser");	
    	}else{
    		 browser = selConfig.get("browser").toString();
    	 }
        System.out.println("browser&&&&&&&&&"+ browser);
       
        if (browser.equalsIgnoreCase("firefox")) {
        	  cap = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            cap = DesiredCapabilities.chrome();
        } else if (browser.equalsIgnoreCase("Safari")) {
            cap = DesiredCapabilities.safari();
        } else if ((browser.equalsIgnoreCase("ie"))
                || (browser.equalsIgnoreCase("internetexplorer"))
                || (browser.equalsIgnoreCase("internet explorer"))) {
            cap = DesiredCapabilities.internetExplorer();
        }
        String seleniuhubaddress = selConfig.get("seleniumserverhost");
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        cap.setJavascriptEnabled(true);
        return new RemoteWebDriver(selserverhost, cap);
    }

    private static WebDriver getChromeDriver(String driverpath) {
    	driverpath=driverpath+"chromedriver.exe";
    	    System.setProperty("webdriver.chrome.driver", driverpath);
        capabilities.setJavascriptEnabled(true);
        return new ChromeDriver(capabilities);
    }

    private static WebDriver getInternetExplorerDriver(String driverpath) {
    	driverpath=driverpath+"IEDriverServer.exe";
        System.setProperty("webdriver.ie.driver", driverpath);
        capabilities.setCapability("ie.ensureCleanSession", true);
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        return new InternetExplorerDriver(capabilities);
    }

    private static WebDriver getSafariDriver() {
        return new SafariDriver();
    }

    private static WebDriver getFirefoxDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.cache.disk.enable", false);
       profile.setPreference("dom.max_chrome_script_run_time", 0);
        profile.setPreference("dom.max_script_run_time", 0);
        profile.setPreference("network.automatic-ntlm-auth.trusted-uris", getData("app_url"));
        profile.setPreference("network.automatic-ntlm-auth.allow-non-fqdn", "true");

        return new FirefoxDriver((Capabilities) profile);
    }
}