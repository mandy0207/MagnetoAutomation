package com.Jobsity.manager.web.local;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EdgeManager {

	public static WebDriver getDriver() {
		EdgeOptions ops = new EdgeOptions();
		ops.addArguments("--headless=new","--start-maximized");
		ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
		return new EdgeDriver(ops);
	}
}
