package com.Jobsity.converters;

import java.lang.reflect.Method;

import org.aeonbits.owner.Converter;

import com.Jobsity.enums.BrowserRemoteModeType;

public class StringToRemoteModeBrowserTypeConverter implements Converter<BrowserRemoteModeType> {

	@Override
	public BrowserRemoteModeType convert(Method method, String remoteMode) {
		return BrowserRemoteModeType.valueOf(remoteMode.toUpperCase());
	}
}
