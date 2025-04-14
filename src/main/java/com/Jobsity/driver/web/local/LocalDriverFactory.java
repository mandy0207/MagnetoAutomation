package com.Jobsity.driver.web.local;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;

import com.Jobsity.enums.BrowserType;
import com.Jobsity.manager.web.local.ChromeManager;
import com.Jobsity.manager.web.local.EdgeManager;
import com.Jobsity.manager.web.local.FireFoxManager;
import com.Jobsity.manager.web.local.SafariManager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalDriverFactory {

	private static final Map<BrowserType, Supplier<WebDriver>> MAP = new EnumMap<>(BrowserType.class);

	static {
		MAP.put(BrowserType.CHROME, ChromeManager::getDriver);
		MAP.put(BrowserType.FIREFOX, FireFoxManager::getDriver);
		MAP.put(BrowserType.SAFARI, SafariManager::getDriver);
		MAP.put(BrowserType.EDGE, EdgeManager::getDriver);

	}

	public static WebDriver getDriver(BrowserType browserType) {
		return MAP.get(browserType).get();
	}
}
