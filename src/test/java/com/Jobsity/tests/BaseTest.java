package com.Jobsity.tests;


import java.time.Duration;

import com.Jobsity.Config.Frameworkconfig;
import com.Jobsity.driver.factory.DriverManager;
import com.Jobsity.pageInstanceFactory.PageInstancesFactory;
import com.Jobsity.pages.Web.WebAccountUserPage;
import com.Jobsity.pages.Web.WebBasePage;
import com.Jobsity.pages.Web.WebHomePage;
import com.Jobsity.pages.Web.WebOrderReturnPage;
import com.Jobsity.utils.LoggerUtil;

import lombok.Getter;
import lombok.Setter;

public class BaseTest {

	@Getter
	@Setter
	private Frameworkconfig config;
	String environment;
	String url;

	public WebBasePage webBasePage;
	public WebHomePage webHomePage;
	public  WebAccountUserPage  webAccountUserPage;
	public  WebOrderReturnPage webOrderReturnPage;
	
	public void initMagnetoPages() {
		webBasePage = PageInstancesFactory.getInstance(WebBasePage.class);
		webHomePage = PageInstancesFactory.getInstance(WebHomePage.class);
		webAccountUserPage= PageInstancesFactory.getInstance(WebAccountUserPage.class);
		webOrderReturnPage=  PageInstancesFactory.getInstance(WebOrderReturnPage.class);
	}



	public void LaunchMagneto() {
		environment = getEnvironment("jobsityEnvironment");
		url = getURL("jobsityEnvironment");
		DriverManager.getDriver().get(getURL("jobsityEnvironment"));
		initMagnetoPages();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
	}

	public String getURL(String environment) {
		LoggerUtil.log("Executing Test in " + getEnvironment(environment) + " Environment");
		switch (environment) {
		case "jobsityEnvironment":
			return config.getProperty(getEnvironment(environment));

		default:
			// Handle unknown environment or provide a default property
			return config.getProperty("defaultEnvironment");
		}

	}
	


	public String getEnvironment(String environment) {
		switch (environment) {
		case "jobsityEnvironment":
			String jobsityEnvironment = System.getProperty(environment) != null ? System.getProperty(environment)
					: config.jobsityEnvironment();
			return jobsityEnvironment;
		
		default:
			// Handle unknown environment or provide a default property
			return System.getProperty(environment) != null ? System.getProperty(environment)
					: config.jobsityEnvironment();
		}
	}


	

	
	


	
	
	
	
	
	

}
