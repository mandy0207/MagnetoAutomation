package com.Jobsity.utils;
import java.util.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.Jobsity.driver.factory.DriverManager;
import com.Jobsity.manager.ExtentReportManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;



public class ReportUtil {
	/**
	 * Adds the screen shot.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public static void addScreenShot(Status status, String message) {
		String path =  getScreenshot();
		ExtentReportManager.getCurrentTest().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
	}

	/**
	 * Adds the screen shot.
	 *
	 * @param message the message
	 */
	public static void addScreenShot(String message) {
		String path =  getScreenshot();
		ExtentReportManager.getCurrentTest().log(Status.INFO, message, MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		
	}
	
	public static void addScreenShot(String message, boolean highlightInRed) {
	    String path = getScreenshot();
	    
	    // Apply red color only if highlightInRed is true
	    String formattedMessage = highlightInRed 
	        ? MarkupHelper.createLabel(message, ExtentColor.RED).getMarkup() 
	        : message;
	    
	    ExtentReportManager.getCurrentTest().log(Status.FAIL, formattedMessage, 
	        MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
	}
	
	public static String getScreenshot() {
	    TakesScreenshot scrshot = (TakesScreenshot) DriverManager.getDriver();
	    // Capture screenshot as byte array
	    byte[] screenshotBytes = scrshot.getScreenshotAs(OutputType.BYTES);
	    // Encode byte array to Base64
	    String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
	    // Pass the Base64 string to MediaEntityBuilder
	    return base64Screenshot;
	}

	/**
	 * log as message with status to the report
	 *
	 * @param status  the pass/fail/info status
	 * @param message the message for the log
	 * @param details any details for the log entry
	 */
	public static void logMessage(Status status, String details) {
		ExtentReportManager.getCurrentTest().log(status, details);

		LoggerUtil.log(String.format("ReportUtil.logMessage - %s - %s", status,  details));
	}

	/**
	 * log as message to the report
	 *
	 * @param message the message for the log
	 * @param details any details for the log entry
	 */
	public static void logMessage(String message, String details) {
		ExtentReportManager.getCurrentTest().log(Status.INFO,  message +" "+details);
		LoggerUtil.log(String.format("ReportUtil.logMessage - %s - %s", Status.INFO, message, details));
	}
	

}
