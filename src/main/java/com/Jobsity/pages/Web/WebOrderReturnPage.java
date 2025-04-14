package com.Jobsity.pages.Web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Jobsity.enums.Locators;
import com.Jobsity.models.Address.OrderReturn;

public class WebOrderReturnPage extends WebBasePage {

	public WebOrderReturnPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	private  By orderID = By.xpath("//*[@id='oar-order-id']");
	private By billingLastName= By.xpath("//*[@id='oar-billing-lastname']");
	private By orderEmail = By.xpath("//*[@id='oar_email']");
	private By orderZip = By.xpath("//*[@id='oar_zip']");
	private By findOrderByDropdown= By.xpath("//*[@id='quick-search-type-id']");
	private By continueBtn= By.xpath("//*[@title='Continue']");
	
	public void searchOrder(Locators name, OrderReturn order) {
	    if (name == Locators.email) {
	        setTextBox(orderEmail, order.email);
	    } else if (name == Locators.zipCode) {
	        setTextBox(orderZip, order.zipCode);
	    }

	    setTextBox(orderID, order.orderID);
	    setTextBox(billingLastName, order.billingLastName);

	    clickElement(continueBtn);
	}

	
	public String getError(Locators name, OrderReturn order) {
		searchOrder(name, order);
		List<WebElement> list = getListofElements(formError);
		return getTextOfDisplayedElement(list);
	}
	
	public void chooseDropdown(Locators name) {
	    switch(name) {
	        case email:
	            selectFromDropdownByText(findOrderByDropdown, "Email");
	            break;
	        case zipCode:
	            selectFromDropdownByText(findOrderByDropdown, "ZIP Code");
	            break;
	        default:
	            break;
	    }
	}

	
	
}
