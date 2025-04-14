package com.Jobsity.pages.Web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Jobsity.models.MagnetoUser.MagnetoUser;

public class WebAccountUserPage extends WebBasePage {

	public WebAccountUserPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	private  By firstName = By.xpath("//*[@id='firstname']");
	private By lastName= By.xpath("//*[@id='lastname']");
	private By email = By.xpath("//*[@id='email_address']");
	private By password = By.xpath("//*[@id='password']");
	private By confirmPassword= By.xpath("//*[@id='password-confirmation']");
	private By createAccountBtn = By.xpath("//*[@title='Create an Account']");
	
	
	public String getErrorMsg(MagnetoUser user) {
		setUserData(user);
		List<WebElement> list = getListofElements(formError);
		return getTextOfDisplayedElement(list);
		
	}
	
	
	public void setUserData(MagnetoUser user) {
		setTextBox(firstName, user.firstName);
		setTextBox(lastName, user.lastName);
		setTextBox(email, user.email);
		setTextBox(password,user.password);
		setTextBox(confirmPassword,user.confirmPassword);
		clickElement(createAccountBtn);
	
	}
	
	
	

}
