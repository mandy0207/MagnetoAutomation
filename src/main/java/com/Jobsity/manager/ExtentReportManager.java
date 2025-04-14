package com.Jobsity.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.Jobsity.Config.Constants;
import com.Jobsity.Config.FrameWorkConfigFactory;
import com.Jobsity.Config.Frameworkconfig;
import com.Jobsity.enums.BrowserType;
import com.Jobsity.utils.EmailHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtentReportManager {
	/** The extent reports. */
	private volatile static ExtentReports extentReports;
	@Getter()
	private static String reportName;

	/** The map. */
	private static Map<Long, ExtentTest> map = new HashMap<>();
	
	
	/**
	 * Gets the current test.
	 *
	 * @return the current test
	 */
	public synchronized static ExtentTest getCurrentTest() {
		return map.get(Thread.currentThread().getId());
	}
	

	/**
	 * Gets the extent reports.
	 *
	 * @return the extent reports
	 * @throws IOException
	 */
	public static ExtentReports getExtentReports() {
		
		if (extentReports == null) {
			synchronized(ExtentReportManager.class) {
				System.out.println("**************************************");
				reportName = Constants.REPORTNAME;
				ExtentSparkReporter reporter = new ExtentSparkReporter(Constants.EXTENTREPORT_DIR+"/"+reportName);
				extentReports = new ExtentReports();
				reporter.config().setReportName(Constants.PROJECT_NAME);
				try {
					reporter.loadXMLConfig(new File(Constants.EXTENT_CONFIG_PATH));
				} catch (IOException e) {
					e.printStackTrace();
				}
				extentReports.attachReporter(reporter);
				processReportEnvironments();
			}
			}
			
		return extentReports;
	}

	public static void webEnvInfo(Frameworkconfig config, BrowserType browserName) {
		extentReports.setSystemInfo("Brand", EmailHandler.getBrand());
		extentReports.setSystemInfo("Environment", EmailHandler.getEnvironment(config));
		extentReports.setSystemInfo("URL", "<a href=\"" + EmailHandler.url + "\">" + EmailHandler.url + "</a>");
		extentReports.setSystemInfo("Browser", browserName.toString());
	}


	public synchronized static void startTest(String testName, String desc) {
		ExtentTest test = getExtentReports().createTest(testName, desc);
		String group=System.getProperty("groups")!= null ? System.getProperty("groups"): "Regression";
		group=EmailHandler.getGroupType(group);
		test.assignCategory(group);
		map.put(Thread.currentThread().getId(), test);
	}
	
	
	public static void processReportEnvironments() {
		Frameworkconfig config = FrameWorkConfigFactory.getConfig();

		BrowserType browserName = System.getProperty("browserName") != null
				? BrowserType.valueOf(System.getProperty("browserName").toUpperCase())
				: FrameWorkConfigFactory.getConfig().browserName();
	 
	        webEnvInfo(config, browserName);
	        System.out.println("######### web ##################");
	    
	    extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
	}

}