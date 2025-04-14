package com.Jobsity.Interfaces.impl;

import org.openqa.selenium.WebDriver;

import com.Jobsity.Interfaces.IWebDriver;
import com.Jobsity.driver.web.local.LocalDriverFactory;
import com.Jobsity.entity.WebDriverData;

public class LocalWebDriverImpl implements IWebDriver {

	@Override
	public WebDriver getDriver(WebDriverData driverData) {
		return LocalDriverFactory.getDriver(driverData.getBrowserType());
	}

}
