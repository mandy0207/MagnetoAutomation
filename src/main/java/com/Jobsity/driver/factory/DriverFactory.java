package com.Jobsity.driver.factory;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import com.Jobsity.Interfaces.IWebDriver;
import com.Jobsity.Interfaces.impl.LocalWebDriverImpl;
import com.Jobsity.enums.RunModeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverFactory {
	private static final Map<RunModeType, Supplier<IWebDriver>> WEB = new EnumMap<>(RunModeType.class);

	static {
		WEB.put(RunModeType.LOCAL, LocalWebDriverImpl::new);
		//WEB.put(RunModeType.REMOTE, RemoteWebDriverImpl::new);
	}

	public static IWebDriver getDriverForWeb(RunModeType runModeType) {
		return WEB.get(runModeType).get();
	}

}
