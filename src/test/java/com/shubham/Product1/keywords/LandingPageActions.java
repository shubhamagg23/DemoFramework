package com.shubham.Product1.keywords;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.shubham.automation.getpageobjects.GetPage;
import com.shubham.automation.utils.Msg;
import com.shubham.automation.utils.YamlReader;

public class LandingPageActions extends GetPage {

	public LandingPageActions(WebDriver driver) {
		super(driver, "LandingPage");
	}

	public void verifyUserIsOnLandingPage() {
		verifyPageTitleExact();
		logMessage("Verified that the user is on the Login Page!!!");
	}
	public void verifyPageTitle(String pageName) {
		String exp_pageTitle =YamlReader.getYamlValue(pageName);
		Msg.logMessage("Expected Page Title: "+exp_pageTitle);
		String actual_PageTitle = driver.getTitle();
		Msg.logMessage("Actual Page Title: "+actual_PageTitle);
		Assert.assertEquals(actual_PageTitle, exp_pageTitle,"[Aseertion Failed]: Page title don't match");
		Msg.reportPass("User redirected to correct page");
		
	}
	
	public void verifyUserIsOnGoogleLandingPage() {
		wait.waitForPageToLoadCompletely();
		hardWait(1);
		verifyPageTitle("google_LandingPageTitle");
		wait.waitForElementToBeVisible(element("google_Logo"));
		Assert.assertTrue(element("google_Logo").isDisplayed());
		Msg.reportPass("User is on Google Landing Page");
	}
	
	public void clickSignInMenuLink() {
		Assert.assertFalse(true);
		//wait.waitForPageToLoadCompletely();
		//element("lnk_SignIn").click();
	}
	public void clickHomeMenuLink() {
		element("lnk_Home").click();
	}
	public void clickMenuLink(String linktext){
		element("lnk_Menu", linktext).click();
	}
	public static int getResponseCode() throws IOException
	{
		    WebClient webClient = new WebClient();
		    int code = webClient.getPage("http://thepoint1.technotects.com/Authentication/ProcessPostbackFromSSO").getWebResponse().getStatusCode();
			System.out.println("Code:"+ code);
		    return code;
	}

	public void clickOnLogOutButton() 
		{ wait.waitForPageToLoadCompletely();   
		hardWaitForIEBrowser(6);
			driver.switchTo().defaultContent();
			isElementDisplayed("lnk_Logout");
			clickUsingXpathInJavaScriptExecutor(element("lnk_Logout"));
			//element("lnk_Logout").click();
			logMessage("Clicked on logout");
			hardWaitForIEBrowser(6);
			isElementDisplayed("btn_ReturnUser");
			Assert.assertTrue((element("btn_ReturnUser").isDisplayed()), "User should land on login page after logout");
			logMessage("Verfied user is login page after log out");
		}
	
	public void checkStatusCode(){
		try{
		 WebClient webClient = new WebClient();
		 DefaultCredentialsProvider credential = new DefaultCredentialsProvider();
		 credential.addCredentials("Shubham", "Password1");
		 webClient.setCredentialsProvider(credential);
           
		 HtmlPage htmlPage = webClient.getPage("http://thepoint1.technotects.com/Authentication/ProcessPostbackFromSso");
		 
		//verify response
		 Assert.assertEquals(302,htmlPage.getWebResponse().getStatusCode(), "Correct code should be dispalyed");
		 Assert.assertEquals("Found",htmlPage.getWebResponse().getStatusMessage(), "correct message not there");
		}
		catch (Exception e)
		{
			System.out.println("In Catch block");
		}
	}

	public void selectBrunnerAndVerifySSO() {
		Assert.assertEquals(isElementDisplayed("list_products"), true, "Page does not list products in this account");
		element("lnk_Hinkle").click();

	}

	public void selectPassPointAndVerifySSO(String product) {
		hardWaitForIEBrowser(5);
		wait.waitForElementToAppear(element("list_products"));
		Assert.assertEquals(isElementDisplayed("list_products"), true, "Page does not list products in this account");
		hardWaitForIEBrowser(5);
		isElementDisplayed("lnk_NCLEX-RN",product);
		clickUsingXpathInJavaScriptExecutor(element("lnk_NCLEX-RN",product));
		//element("lnk_NCLEX-RN",product).click();
		hardWaitForIEBrowser(5);
	}

	public void checkSSO(String role, String product) {
		Assert.assertEquals(isElementDisplayed("prepU_Header"), true, "'Adaptive learning by prepU' is not displayed upon selecting a product from My Contents page.");
		driver.switchTo().frame(element("prepU_iFrame"));
		
		if (role.equals("instructor") && product.equalsIgnoreCase("Brunner"))
		{
			logMessage("Selected Product:"+product+" as "+role);
			isElementDisplayed("ins_prepUHeader");
			isElementDisplayed("ins_ClassPerf");
			System.out.println("Clicking on Question library.. .. .");
			element("lnk_Q.Library").click();
			Assert.assertEquals(isElementDisplayed("Lib_CreatedQues"), true, "Created questions should be dispalyed on Question Library page");
			element("lnk_AssignQuiz").click();
			Assert.assertEquals(isElementDisplayed("Header_QuizPage"), true, "Instructor should land on Assign a Quiz page");
			element("lnk_GoBack").click();
			Assert.assertEquals(isElementDisplayed("ins_ClassPerf"), true, "Instrcutor should land on HMCD page after clicking 'Go Back' link on Assignments page");
		}
		else if (role.equals("instructor") && product.equalsIgnoreCase("NCLEX-RN"))
		{
			logMessage("Selected Product:"+product+" as "+role);
			isElementDisplayed("ins_prepUHeader");
			isElementDisplayed("ins_ClassPerf");
			System.out.println("Clicking on Question library.. .. .");
			element("lnk_Q.Library").click();
			Assert.assertEquals(isElementDisplayed("Lib_CreatedQues"), true, "Created questions should be dispalyed on Question Library page");
			element("lnk_AssignQuiz").click();
			Assert.assertEquals(isElementDisplayed("Header_QuizPage"), true, "Instructor should land on Assign a Quiz page");
			element("lnk_GoBack").click();
			Assert.assertEquals(isElementDisplayed("ins_ClassPerf"), true, "Instrcutor should land on HMCD page after clicking 'Go Back' link on Assignments page");
		
				isElementDisplayed("lnk_AssignExam");
				element("lnk_ExamSummary").click();
				Assert.assertEquals(isElementDisplayed("ExamSummaryHeading"), true, "User should see exam summary tab");
		}
		else if (role.equals("student") && product.equalsIgnoreCase("Brunner"))
		{
			logMessage("Selected Product:"+product+" as "+role);
			isElementDisplayed("stu_HAID");
			wait.waitForPageToLoadCompletely();
			element("stu_PracticeQuiz").click();
			wait.waitForPageToLoadCompletely();
			Assert.assertEquals(isElementDisplayed("btn_StartQuiz"), true, "Student should navigate to Practice quiz page");		
		}
		else if (role.equals("student") && product.equals("NCLEX-RN"))
				{
			logMessage("Selected Product:"+product+" as "+role);
				isElementDisplayed("stu_MyClasses");
				element("stu_Class1").click();
				wait.waitForElementToAppear(element("stu_HAID"));
				wait.waitForPageToLoadCompletely();
				element("stu_PracticeQuiz").click();
				Assert.assertEquals(isElementDisplayed("btn_StartQuiz"), true, "Student should navigate to Practice quiz page");		
				isElementDisplayed("stu_prepUHeader");
				Assert.assertEquals(isElementDisplayed("stu_Assignments"), true, "User should see assignments tab");
				element("stu_PracticeExam").click();
				Assert.assertEquals(isElementDisplayed("stu_ExamHeader"), true, "User should land on Practice Exam page");
				element("stu_ExamReports").click();
				Assert.assertEquals(isElementDisplayed("stu_ExamReportsHeader"), true, "User should land on Exam Reports page");
				}
		
	}

	public void goToMyContentsPage() {
		driver.switchTo().defaultContent();
		element("lnk_MyContent").click();
		wait.waitForElementToAppear(element("MyContent_Heading"));
		Assert.assertEquals(isElementDisplayed("list_products"), true, "Clicking on My Contents page shoud take user to the desired page");
		
	}
	
}