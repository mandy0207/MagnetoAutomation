package com.Jobsity.manager.web.local;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChromeManager {

	public static WebDriver getDriver() {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--headless=new","--start-maximized");
		ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
		return new ChromeDriver(ops);
	}
}
