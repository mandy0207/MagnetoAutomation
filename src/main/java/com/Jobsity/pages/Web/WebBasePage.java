package com.Jobsity.pages.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.Jobsity.utils.WebPageActionsHelper;



public class WebBasePage extends WebPageActionsHelper {
	

	public WebBasePage(WebDriver driver) {
		super(driver);
	}
	
	protected By formError = By.xpath("//*[@class='mage-error']");

	

}
