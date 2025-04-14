package com.Jobsity.driver.factory;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

import com.Jobsity.Config.FrameWorkConfigFactory;
import com.Jobsity.entity.WebDriverData;
import com.Jobsity.enums.BrowserType;


public class Driver {
	 
	// local web, remote web, local mobile, remote mobile

	public static void initDriverForWeb() {
		if (Objects.isNull(DriverManager.getDriver())) {

			BrowserType browserName = System.getProperty("browserName") != null
					? BrowserType.valueOf(System.getProperty("browserName").toUpperCase())
					: FrameWorkConfigFactory.getConfig().browserName();
			System.out.println("Browser selected =" + browserName);

			WebDriverData Driverdata = new WebDriverData(browserName,
					FrameWorkConfigFactory.getConfig().browserRemoteMode());

			WebDriver driver = DriverFactory.getDriverForWeb(FrameWorkConfigFactory.getConfig().browserRunMode())
					.getDriver(Driverdata);

			DriverManager.setDriver(driver);
		}
	}

	public static void quitDriver() {
		if (Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}
}
