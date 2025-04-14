package com.Jobsity.pages.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebHomePage extends WebBasePage {

	public WebHomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	private  By createAccountLink = By.xpath("(//*[@class='header links']//a[text()='Create an Account'])[1]");
	
	public void navigateToSignupPage() {
		clickElement(createAccountLink);
	}
}
