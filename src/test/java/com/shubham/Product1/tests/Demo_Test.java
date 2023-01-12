package com.shubham.Product1.tests;

import static com.shubham.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.shubham.automation.TestSessionInitiator;
import com.shubham.automation.utils.DateUtil;
import com.shubham.automation.utils.PropFileHandler;
import com.shubham.automation.utils.YamlReader;

public class Demo_Test extends TestSessionInitiator {

	public static String tier;
	
	@Test
	public void Test01_Launch_Google() {
		landingPage.verifyUserIsOnGoogleLandingPage();
	}
	
	
	
}

