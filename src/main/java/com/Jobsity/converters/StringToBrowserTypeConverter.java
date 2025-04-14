package com.Jobsity.converters;

import java.lang.reflect.Method;

import org.aeonbits.owner.Converter;

import com.Jobsity.enums.BrowserType;



public class StringToBrowserTypeConverter implements Converter<BrowserType> {

	@Override
	public BrowserType convert(Method method, String browserName) {
		return BrowserType.valueOf(browserName.toUpperCase());
	}
}
