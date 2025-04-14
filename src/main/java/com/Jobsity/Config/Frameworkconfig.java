package com.Jobsity.Config;

import java.net.URL;
import java.util.List;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.HotReload;
import org.aeonbits.owner.Mutable;

import com.Jobsity.converters.StringToBrowserTypeConverter;
import com.Jobsity.converters.StringToRemoteModeBrowserTypeConverter;
import com.Jobsity.converters.StringToRunModeBrowserTypeConverter;
import com.Jobsity.enums.BrowserRemoteModeType;
import com.Jobsity.enums.BrowserType;
import com.Jobsity.enums.RunModeType;


@HotReload
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
		"file:${user.dir}/src/main/resources/configurations/Config.properties",
		"file:${user.dir}/src/main/resources/configurations/emailHandler.properties",
		"file:${user.dir}/src/main/resources/configurations/environments.properties" })
public interface Frameworkconfig extends Config, Mutable, Accessible {

	@DefaultValue("CHROME")
	@ConverterClass(StringToBrowserTypeConverter.class)
	BrowserType browserName();

	@Key("browserRunMode")
	@ConverterClass(StringToRunModeBrowserTypeConverter.class)
	RunModeType browserRunMode();

	@Key("browserRemoteMode")
	@ConverterClass(StringToRemoteModeBrowserTypeConverter.class)
	BrowserRemoteModeType browserRemoteMode();

	@DefaultValue("http://127.0.0.1:4723")
	URL localAppiumServerURL();

	Integer TIMEOUT();

	Integer SHORT_TIMEOUT();
	
	String automationType();

	String hostName();
	String senderEmail();
	String gmailAuthenticationToken();
	Integer SMTPPORT();
	List<String> recipientEmail();
	List<String> jobsityrecipientAutomationReportEmail();
	String jobsityEnvironment();
	String orderReturnPage();
	
	@Key("runModeMobile")
	@ConverterClass(StringToRunModeBrowserTypeConverter.class)
	RunModeType mobileRunMode();

	

	
}
