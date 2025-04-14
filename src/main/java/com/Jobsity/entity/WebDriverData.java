package com.Jobsity.entity;

import com.Jobsity.enums.BrowserRemoteModeType;
import com.Jobsity.enums.BrowserType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebDriverData {

	private BrowserType browserType;
	private BrowserRemoteModeType browserRemoteModeType;
}
