package com.Jobsity.manager.web.local;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FireFoxManager {

	public static WebDriver getDriver() {
		FirefoxOptions ops = new FirefoxOptions();
		ops.addArguments("--headless");
		ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
		WebDriver driver = new FirefoxDriver(ops);
		driver.manage().window().maximize();
		return driver;
	}
}
