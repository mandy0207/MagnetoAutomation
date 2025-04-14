package com.Jobsity.driver.factory;

import java.util.EnumMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.Jobsity.enums.PlatformType;

public class DriverManager {

	/**
	 * context--: web test -> web mobile test-> mobile web-> mobile mobile->web
	 *
	 * ThreadLocals - 1)Web 2)Mobile MAP --> WEB, WEB_DRIVER_THREADLOCAL ; MOBILE,
	 * MOBILEDRIVER_THREADLOCAL
	 *
	 * At a particular instance, context -->web, mobile
	 */

	private static ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();
	private static final ThreadLocal<PlatformType> CONTEXT = ThreadLocal.withInitial(() -> PlatformType.WEB);
	private static final Map<PlatformType, ThreadLocal<WebDriver>> DRIVER_MAP = new EnumMap<>(PlatformType.class);

	public static WebDriver getDriver() {
		return  WEB_DRIVER_THREAD_LOCAL.get() ;
	}

	public static void setDriver(WebDriver driver) {
			WEB_DRIVER_THREAD_LOCAL.set(driver);
			DRIVER_MAP.put(PlatformType.WEB, WEB_DRIVER_THREAD_LOCAL);
			CONTEXT.set(PlatformType.WEB);
		
	}

	public static WebDriver getWebDriver() {
		return WEB_DRIVER_THREAD_LOCAL.get();
	}

	public static void switchToWebContext() {
		CONTEXT.set(PlatformType.WEB);
	}

	public static void unload() {
		WEB_DRIVER_THREAD_LOCAL.remove();
		CONTEXT.remove();
	}

	public static void quitDrivers() {
		// Close all drivers based on the context
		for (ThreadLocal<WebDriver> driverThreadLocal : DRIVER_MAP.values()) {
			WebDriver driver = driverThreadLocal.get();
			if (driver != null) {
				driver.quit();
				driverThreadLocal.remove();
			}
		}
		CONTEXT.remove();
	}
}
