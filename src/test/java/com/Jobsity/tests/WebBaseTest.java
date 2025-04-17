package com.Jobsity.tests;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.Jobsity.Config.FrameWorkConfigFactory;
import com.Jobsity.Config.Frameworkconfig;
import com.Jobsity.driver.factory.Driver;
import com.Jobsity.utils.EmailHandler;
import com.Jobsity.utils.LoggerUtil;

@Listeners({ com.Jobsity.listeners.ReportListener.class, com.Jobsity.listeners.LogListener.class })
public class WebBaseTest extends BaseTest {
	public Frameworkconfig config;
	public static SoftAssert softAssert = new SoftAssert();

	@BeforeMethod(alwaysRun = true)
	public void Setup() {
		config = FrameWorkConfigFactory.getConfig();

		setConfig(config);

		Driver.initDriverForWeb();

	}

	@BeforeSuite(alwaysRun = true)
	public void suiteSetup() throws IOException, GeneralSecurityException {
		LoggerUtil.log("************************** Test Execution Started ************************************");
		EmailHandler.setSuiteStartTime(System.currentTimeMillis());
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		Driver.quitDriver();
		webBasePage.failSoftAssert();

	}

	

}
