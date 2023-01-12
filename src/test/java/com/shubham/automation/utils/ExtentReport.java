package com.shubham.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shubham.automation.TestSessionInitiator;

public class ExtentReport extends TestSessionInitiator {
	
	public static ExtentReports setupExtentReport() {
		SimpleDateFormat format = new SimpleDateFormat("DD-MM-YYYY HH-MM-SS");
		Date date = new Date();
		String actualDate = format.format(date);		
		String reportPath = System.getProperty("user.dir")+"/Report/ExecutionReport.html";
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setDocumentTitle("ExecutionReportDemo");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("Demo Report Name");
		return extent;
	}

}
