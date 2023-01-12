/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shubham.automation;

import static com.shubham.automation.utils.DataReadWrite.getProperty;
import static com.shubham.automation.utils.YamlReader.getData;
import static com.shubham.automation.utils.YamlReader.getYamlValue;
import static com.shubham.automation.utils.YamlReader.setYamlFilePath;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.common.base.Strings;
import com.shubham.Product1.keywords.LandingPageActions;
import com.shubham.automation.utils.CustomFunctions;
import com.shubham.automation.utils.PropFileHandler;

public class TestSessionInitiator {

	public static WebDriver driver;
	private WebDriverFactory wdfactory;
	public static String appUrl = null;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;
	
	String takeScreenshot;
	String screenshotPath;
	
	public static ExtentReports extent;
	public static ExtentTest extentTest;

	/**
	 * Initiating the page objects
	 */
	public LandingPageActions landingPage;
	public CustomFunctions customFunctions;

	Robot a;

	private void _initPage() {
		landingPage = new LandingPageActions(driver);
		customFunctions = new CustomFunctions(driver);
	}

	// --------------- TestNg annotations ---------------
	
	@BeforeSuite(alwaysRun = true)
	public void TestSessionInitiators() {
		setYamlFilePath();
		appUrl = getData("app_url");
		Reporter.log("The application url is :- " + appUrl, true);
	}
	
	@BeforeClass
	public void launchMyApplication() {
		wdfactory = new WebDriverFactory();
		testInitiator();
		launchApplication(appUrl);
	}

	@BeforeMethod(alwaysRun = true)
	public void logTestMethod(Method method) {
		String className = method.getDeclaringClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		System.out.println(
				"**********************************************************" + "\n" + "Running Test:" + className + "."
						+ method.getName() + "\n" + "**********************************************************");
	}

	@AfterMethod(alwaysRun = true)
	public void take_screenshot_on_failure(ITestResult result) {
		takeScreenshot = _getSessionConfig().get("take-screenshot").toString();
		screenshotPath = _getSessionConfig().get("screenshot-path").toString();
		
		customFunctions.takeScreenShotOnException(result, takeScreenshot, screenshotPath, this.getClass().getSimpleName());
	}

	@AfterClass(alwaysRun = true)
	public void stop_test_session() {
		closeBrowserSession();
	}
	
	// ----------- Private Methods -------------

	private void testInitiator() {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(_getSessionConfig().get("timeout")),
				TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver", "seleniumserverhost", "timeout", "driverpath", "take-screenshot", "screenshot-path" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	/**
	 * Launches the application found at provided URL
	 * 
	 * @param applicationpath
	 */
	private void launchApplication(String applicationpath) {
		Reporter.log("The application url is :- " + applicationpath, true);
		driver.get(applicationpath);
	}

	/**
	 * This keyword is used to launch applications that use NTLM authentication
	 * to validate user
	 * 
	 * @param applicationpath
	 * @param authUser
	 * @param authPed
	 */
	private void launchApplication(String applicationpath, String authUser, String authPwd) {
		applicationpath = applicationpath.replace("http://",
				"http://" + authUser.replaceAll("@", "%40") + ":" + authPwd.replaceAll("@", "%40") + "@");
		Reporter.log("The application url is :- " + applicationpath, true);
		driver.get(applicationpath);
	}

	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	/**
	 * The Test Session including the browser is closed
	 */
	public void closeBrowserSession() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	public static void typeCharacter(Robot robot, String letter) {

		for (int i = 0; i < letter.length(); i++) {
			try {
				boolean upperCase = Character.isUpperCase(letter.charAt(i));
				String KeyVal = Character.toString(letter.charAt(i));
				String variableName = "VK_" + KeyVal.toUpperCase();
				Class clazz = KeyEvent.class;
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(null);

				robot.delay(300);

				if (upperCase)
					robot.keyPress(KeyEvent.VK_SHIFT);

				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);

				if (upperCase)
					robot.keyRelease(KeyEvent.VK_SHIFT);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public void handleAuthenticationAlert() {
		// Code to handle Basic Browser Authentication in Selenium.
		// if(dri)
		String path = _getSessionConfig().get("driverpath");
		path = path + "WindowsAuthentication.exe";

		String os = System.getProperty("os.name").toLowerCase();
		System.out.println("os====" + os);
		try {

			String[] cmd = { path, getData("users.http_auth.username"), getData("users.http_auth.password") };

			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			// Runtime.getRuntime().exec("src/test/resources/drivers/WindowsAuthentication.exe",getData("users.http_auth.username"),getData("users.http_auth.password"));
		} catch (Exception e) {
			System.out.println("inside exception");
		}
		// try {
		// Robot robot = new Robot();
		// Thread.sleep(4000);
		// typeCharacter(robot, "sakurai");
		// Thread.sleep(500);
		// robot.keyPress(KeyEvent.VK_SHIFT);
		// robot.keyPress(KeyEvent.VK_MINUS);
		// robot.keyRelease(KeyEvent.VK_MINUS);
		// robot.keyRelease(KeyEvent.VK_SHIFT);
		// Thread.sleep(500);
		// typeCharacter(robot, "user");
		// Thread.sleep(500);
		// robot.keyPress(KeyEvent.VK_TAB);
		// robot.keyRelease(KeyEvent.VK_TAB);
		// Thread.sleep(1000);
		// typeCharacter(robot, getData("users.http_auth.password"));
		// Thread.sleep(500);
		// robot.keyPress(KeyEvent.VK_TAB);
		// robot.keyRelease(KeyEvent.VK_TAB);
		// Thread.sleep(500);
		// robot.keyPress(KeyEvent.VK_TAB);
		// robot.keyRelease(KeyEvent.VK_TAB);
		// Thread.sleep(500);
		// robot.keyPress(KeyEvent.VK_ENTER);
		// robot.keyRelease(KeyEvent.VK_ENTER);
		// Thread.sleep(1000);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//

		// try {
		// System.out.println("handling alert");
		// Alert aa = driver.switchTo().alert();
		// System.out.println("entering username");
		// aa.sendKeys(getData("users.http_auth.username"));
		//
		// Robot a = new Robot();
		// a.keyPress(KeyEvent.VK_TAB);
		// DesktopKeyboard sc = new DesktopKeyboard();
		// if (os.indexOf("mac") >= 0) {
		// sc.type(Key.CMD + Key.SHIFT + "G");
		// sc.type(getData("users.http_auth.password"));
		// Thread.sleep(1000);
		//
		// } else {
		// System.out.println("entering password");
		// sc.type(getData("users.http_auth.password"));
		// Thread.sleep(2000);
		//
		// }
		// System.out.println("entering ");
		// a.keyPress(KeyEvent.VK_ENTER);
		//
		// } catch (Exception e) {
		// }
	}

	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
	}

}
