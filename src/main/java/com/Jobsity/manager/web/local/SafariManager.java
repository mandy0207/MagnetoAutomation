package com.Jobsity.manager.web.local;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SafariManager {

	public static WebDriver getDriver() {
		SafariOptions ops = new SafariOptions();
		ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
		WebDriver driver = new SafariDriver(ops);
		driver.manage().window().maximize();
		return driver;
	}
}
