package com.Jobsity.utils;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.Jobsity.Config.FrameWorkConfigFactory;
import com.Jobsity.Interfaces.PageActionsHelper;
import com.Jobsity.driver.factory.DriverManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
	
public class WebPageActionsHelper implements PageActionsHelper {

	protected WebDriver driver;

	protected FluentWait<WebDriver> waiter;
	protected FluentWait<WebDriver> shortWait;
	protected SoftAssert softAssert;

	public WebPageActionsHelper(WebDriver driver) {
		super();
		this.driver = driver;
		waiter = new FluentWait<>(driver).ignoring(NoSuchElementException.class, WebDriverException.class)
				.withTimeout(Duration.ofSeconds(FrameWorkConfigFactory.getConfig().TIMEOUT()))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
				.ignoring(TimeoutException.class);

		shortWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(FrameWorkConfigFactory.getConfig().SHORT_TIMEOUT()))
				.pollingEvery(Duration.ofMillis(100));

		softAssert = new SoftAssert();
	}
	
	public static List<String> convertToLowerCase(List<String> stringList) {
		List<String> lowercaseList = new ArrayList<>();
		for (String str : stringList) {
			lowercaseList.add(str.toLowerCase());
		}
		return lowercaseList;
	}

	
	public List<WebElement> getListofElements(By locator) {
		shortWait();
		//waiter.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return driver.findElements(locator);
	}
	
	
	
	public List<String> getListOfStrings(List<WebElement> elements) {
	    List<String> listValues = new ArrayList<>();
	    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	    elements.forEach(element -> {
	        String text = (String) jsExecutor.executeScript("return arguments[0].textContent;", element);
	        listValues.add(text.trim()); 
	    });

	    return listValues;
	}

	public String getTextFromWebElementUsingJS(WebElement element) {
		 JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		 return(String) jsExecutor.executeScript("return arguments[0].textContent;", element);
		 
	}
	
	public static int findKeywordIndexInList(List<String> strings, String keyword) {
        if (strings == null || keyword == null) {
            throw new IllegalArgumentException("List of strings or keyword cannot be null.");
        }

        String lowerCaseKeyword = keyword.toLowerCase();

        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);
            if (str != null && str.toLowerCase().contains(lowerCaseKeyword)) {
                return i; // Return the index immediately when a match is found
            }
        }
        return -1; // Return -1 if the keyword is not found
    }
	
	 
	public void clickElement(By locator) {
		retryingFindClick(locator);
	}

	public void clickElementForMobile(By locator) {
		if (isMobileDriver()) {

			try {
				LoggerUtil.log("Waiting for element to  clickable - " + locator.toString());
				waiter.until(ExpectedConditions.elementToBeClickable(locator));
				LoggerUtil.log("Click element " + locator.toString());
				driver.findElement(locator).click();

			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}
	}

	public void clickElement(WebElement element) {
		retryingFindClick(element);
		element.click();
		
	}
	

	public String getTitleOfPage() {
		return driver.getTitle();
	}

	public String getUrlOfPage() {
		return driver.getCurrentUrl();
	}

	public void retryingFindClick(By locator) {
		int maxRetries = 15;
		int attempts = 0;
		while (attempts < maxRetries) {
			try {
				waitUntilEnabled(locator).click();
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	public void retryingFindClick(WebElement element) {
		int maxRetries = 100;
		int attempts = 0;
		while (attempts < maxRetries) {
			try {
				waiter.until(ExpectedConditions.elementToBeClickable(element));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	private WebElement waitUntilEnabled(By locator) {
		LoggerUtil.log("Wait until element is enabled - " + locator);
		steadyWait(locator);

		try {
			this.waiter
					.until(v -> driver.findElement(locator).isEnabled() && driver.findElement(locator).isDisplayed());
		} catch (Exception e) {
			Assert.assertTrue(false, "WaitUntilPresent -> Element was not enabled");
		}

		return driver.findElement(locator);
	}

	public void javaScriptClickElement(By locator) {
		
		LoggerUtil.log("JavaScript Executor Click element " + locator.toString());

		WebElement element = waitUntilClickable(locator);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
		
	}

	public void javaScriptClickElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
	}

	public Map<String, String> childWindowSwitch() {

		Set<String> windowHandles = driver.getWindowHandles();
		Map<String, String> windowsMap = new HashMap<>();

		if (windowHandles.size() > 1) {
			Iterator<String> it = windowHandles.iterator();
			String parentWindow = it.next();
			String childWindow = it.next();
			windowsMap.put("Parent Window", parentWindow);
			windowsMap.put("Child Window", childWindow);
			driver.switchTo().window(childWindow);
			ReportUtil.addScreenShot("Switched to new Page =" + getTitleOfPage());
		} else {
			windowsMap.put("Current Window", windowHandles.iterator().next());
		}
		return windowsMap;
	}

	public void switchToParentWindow(String parentHandle) {
		driver.switchTo().window(parentHandle);
	}

	public void setTextBox(By locator, String textToAdd) {
		LoggerUtil.log("Fill text box " + locator.toString() + " : " + textToAdd);

		waitUntilVisible(locator);
		WebElement element = waitUntilClickable(locator);

		moveToElement(element);

		try {
			//element.click();
			clearTextboxWithBackspace(locator);
			element.sendKeys(textToAdd);
		} catch (Exception e) {
			Assert.assertTrue(false, String.format("Could not set element text to '%s'", textToAdd));
		}

	}

	public String getElementValue(By locator) {
		String value = driver.findElement(locator).getAttribute("value");
		if (value != null) {
			return value.trim();
		} else {
			return "";
		}
	}

	public void failSoftAssert() {
		softAssert.assertAll();
	}
	
	public boolean isIOS() {
		return driver instanceof IOSDriver;
	}

	public boolean isAndroid() {
		return driver instanceof AndroidDriver;
	}

	public void clearElement(By locator) {

		LoggerUtil.log("clearing element..." + locator.toString());
		if (isIOS()) {
			driver.findElement(locator).clear();
			if (getElementValue(locator).equals("0")) {
			}
		} else if (isAndroid()) {
			driver.findElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			driver.findElement(locator).sendKeys(Keys.DELETE);
		} else {
			driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
			driver.findElement(locator).sendKeys(Keys.DELETE);

		}

	}

	public void enterData(By locator, String textToAdd) {
		waitForElementVisibilityShort(locator, true);
		LoggerUtil.log("Fill text box " + locator.toString() + " : " + textToAdd);
		try {
			clearElement(locator);
			driver.findElement(locator).sendKeys(textToAdd);
		} catch (Exception e) {
			Assert.assertTrue(false, String.format("Could not set element text to '%s'", textToAdd));
		}

	}

	public void scrollUntilElementVisible(By locator) {
	
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		while (!element.isDisplayed()) {
            // Scroll down the page
            jsExecutor.executeScript("window.scrollBy(0,100)");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}
	public void JavaScriptSetTextBox(By locator, String value) {
		LoggerUtil.log("JavaScript Fill text box " + locator.toString() + " : " + value);

		WebElement element = waitUntilVisible(locator);

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + value + "';", element);
		} catch (Exception e) {
			// doSoftAssertFailure(String.format("Could not set element text to '%s'",
			// value));
		}

	}

	public void setTextBoxAndReturnAndTab(By locator, String textToAdd) {
		setTextBox(locator, textToAdd);

		try {
			driver.findElement(locator).sendKeys(Keys.RETURN, Keys.TAB);
		} catch (Exception e) {
			Assert.assertTrue(false, "Could not RETURN and TAB on field");
		}

	}

	public void setTextBoxAndTab(By locator, String textToAdd) {
		setTextBox(locator, textToAdd);

		try {
			driver.findElement(locator).sendKeys(Keys.TAB);
		} catch (Exception e) {
			doSoftAssertFailure("Failed to press TAB from field");
		}

	}

	public void clearTextboxWithBackspace(By locator) {
		WebElement element = waitUntilClickable(locator);

		String fieldValue = element.getAttribute("value");

		try {
			for (int i = 0; i < fieldValue.length(); i++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed adding characters to field");
		}

	}

	public WebElement moveToElementUsingActions(WebElement element) {
		try {
			Actions act = new Actions(driver);
			waiter.until(ExpectedConditions.visibilityOf(element));
			act.moveToElement(element).build().perform();

		} catch (Exception e) {
			doSoftAssertFailure("Failed To move on element");
		}
		return element;
	}

	public WebElement moveToElementUsingActions(By locator) {
		WebElement element = driver.findElement(locator);
		try {
			Actions act = new Actions(driver);
			waiter.until(ExpectedConditions.visibilityOf(element));
			act.moveToElement(element).build().perform();

		} catch (Exception e) {
			doSoftAssertFailure("Failed To move on element");
		}
		return element;
	}

	public String getTextByJavaScriptExecutor(WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;", element);
	}

	public WebElement moveToElement(WebElement element) {
		try {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			javascriptExecutor.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to move to element");
		}
		return element;
	}

	public void selectFromDropdownByText(By dropdown, String value) {
		LoggerUtil.log("Select " + value + " from dropdown " + dropdown.toString());

		waitUntilVisible(dropdown);
		WebElement dropdownElement = waitUntilClickable(dropdown);

		moveToElement(dropdownElement);

		try {
			Select dd = new Select(dropdownElement);
			dd.selectByVisibleText(value);

			dropdownElement.sendKeys(Keys.RETURN, Keys.TAB);
		} catch (Exception e) {
			Assert.assertTrue(false, String.format("Could not select '%s' from dropdown", value));
		}

	}

	public void setValueAttribute(By locator, String value) {
		WebElement element = waitUntilVisible(locator);

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='arguments[1]';", element, value);
		} catch (Exception e) {
			Assert.assertTrue(false, String.format("Could not set 'value' attribute to '%s'", value));
		}
	}

	public String getElementText(By locator) {
		try {
			String elementText = waitUntilVisible(locator).getText();
			// ReportUtil.logMessage("Get Element Text", elementText);
			return elementText;
		} catch (Exception e) {
			Assert.fail("Failed to get text from element");
			return "";
		}

	}

	public String getText(By locator) {
		waitForElementVisibilityShort(locator, true);
		try {
			String elementText = driver.findElement(locator).getText();
			return elementText;
		} catch (Exception e) {
			Assert.fail("Failed to get text from element");
			return "";
		}
	}

	public WebElement getElement(By locator) {

		try {
			waitUntilVisible(locator);
			return driver.findElement(locator);
		} catch (Exception e) {
			Assert.fail("Failed to get frame element");
			return null;
		}
	}

	public void HandleAdvertisement(By advertisementLocator, int waitInSeconds) {
		try {
			Thread.sleep(waitInSeconds * 1000);
			driver.findElement(advertisementLocator).click();
		} catch (Exception e) {
			// System.out.println("Advertisement not present");
		}
	}

	public String getElementValue(WebElement element) {
		try {
			String elementValue = element.getAttribute("value");

			ReportUtil.logMessage("Get Element Value", elementValue);

			return elementValue;
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to get value from element");
			return "";
		}
	}

	public String getElementAttribute(WebElement element, String attribute) {
		try {
			String elementValue = element.getAttribute(attribute);
			return elementValue;
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed to get value from element");
			return "";
		}
	}

	public void scrollToView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToView(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
	}

	public void scrollToBottomOfPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollToBottomOfPanel(By panelLocator) {
	    WebElement panel = driver.findElement(panelLocator);
	    long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollTop;", panel);
	    
	    while (true) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop += 100;", panel);
	        try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Add a short delay for smooth scrolling
	        
	        long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollTop;", panel);
	        if (newHeight == lastHeight) {
	            break;
	        }
	        lastHeight = newHeight;
	    }
	}


	public void scrollToTopOfPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void scrollToElement(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollToCenterView(By locator) {
		WebElement element = driver.findElement(locator);
		int elementHeight = element.getSize().getHeight();
		Point location = element.getLocation();
		int windowHeight = driver.manage().window().getSize().getHeight();
		int scrollOffset = (location.getY() + elementHeight / 2) - (windowHeight / 2);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollOffset + ");");
	}

	public boolean Exists(By locator) {
		try {
			waitUntilVisible(locator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean steadyWait(final By locator) {
		LoggerUtil.log("Waiting for " + locator.toString());

		String domWait = "y";
		String steadyWait = "y";

		if (domWait.equals("y")) {
			DomWait();
		}

		if (steadyWait.equals("n")) {
			return OverrideExpectedCondition(driver.findElement(locator));
		} else {
			return true;
		}
	}

	public Boolean OverrideExpectedCondition(WebElement element) {
		return waiter.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				try {
					element.isDisplayed();
					return true;
				} catch (StaleElementReferenceException e) {
					Assert.assertTrue(false, "Failed waiting for element to be ready");
					return false;
				}
			}
		});
	}

	public WebElement waitUntilClickable(By locator) {
		LoggerUtil.log("Wait until element is clickable - " + locator);

		waitUntilEnabled(locator);

		try {
			this.waiter.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			Assert.assertTrue(false, "WaitUntilClickable -> Element did not become clickable");
		}

		return driver.findElement(locator);
	}

	public void doSoftAssertFailure(String msg) {
		softAssert.fail(msg);
	}

	public void DomWait() {
		LoggerUtil.log("Waiting for dom load to complete");

		try {
			this.waiter.until(
					d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			Assert.assertTrue(false, "Failed waiting for DOM load to complete");
		}

	}

	public WebElement waitUntilVisible(By locator) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LoggerUtil.log("Wait until element is visible - " + locator);
		steadyWait(locator);

		try {
			WebElement element = driver.findElement(locator);
			this.waiter.until(ExpectedConditions.visibilityOf(element));
		} catch (NoSuchElementException e) {
			LoggerUtil.log("Element not found while waiting for visibility: " + e.getMessage());
			return null;
		} catch (Exception e) {
			Assert.assertTrue(false, "WaitUntilVisible -> Element did not become visible");
		}

		return driver.findElement(locator);
	}

	public void LongWait() {

		try {
			Thread.sleep(18000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shortWait() {
		try {
			Thread.sleep(2000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void midWait() {
		try {
			Thread.sleep(8000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchToFrameByElement(WebElement element) {
		driver.switchTo().frame(element);
	}

	public void switchOutOfFrame() {
		driver.switchTo().defaultContent();
	}

	public void ScrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,500)");
		shortWait();
	}

	public void ScrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-350)");
		shortWait();
	}

	public void scrollTop() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void AcceptCookies(By locator) {
		try {
			driver.findElement(locator).click();
		} catch (Exception e) {

		}
	}	
	
	public WebPageActionsHelper navigateToURL(String URL) {
		DriverManager.getDriver().navigate().to(URL);
		return this;
	}

	public WebPageActionsHelper navigateBack() {
		driver.navigate().back();
		return this;
	}
	
	public void openChildWindow(String newUrl) {
		((JavascriptExecutor) DriverManager.getDriver()).executeScript("window.open(arguments[0], '_blank');", newUrl);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void navigateForword() {
		driver.navigate().forward();

	}

	public boolean isElementDisplayed(By locator) {
		waitUntilVisible(locator);
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (Exception e) {

		}
		return false;
	}

	

	public int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	public boolean isElementEnabled(By locator) {
		waitUntilVisible(locator);
		try {
			LoggerUtil.log("is Element Enable ..." + locator.toString());
			return driver.findElement(locator).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementEnabled(WebElement element) {
		try {
			LoggerUtil.log("is Element Enable ..." + element.toString());
			return element.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	

	public void clickCommandAndTab(By locator) {
		WebElement element = driver.findElement(locator);
		Actions actions = new Actions(driver);
		// Press and hold the COMMAND key
		actions.keyDown(Keys.COMMAND);
		// Click on the element with the pressed COMMAND key
		actions.click(element);
		// Release the COMMAND key
		actions.keyUp(Keys.COMMAND);
		// Press the TAB key
		actions.sendKeys(Keys.TAB);
		// Build and perform the actions
		actions.build().perform();
		
	}

	public void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrameByLocator(By locator) {
		driver.switchTo().frame(getElement(locator));
	}

	public void switchOutFromIframe() {
		driver.switchTo().defaultContent();
	}
	
	public void acceptAlert() {
		try {
			DriverManager.getDriver().switchTo().alert().accept();
		}
		catch(Exception e) {
			System.out.println("Alert is not present");
		}
		
	}

	public void dropdownSelectByText(By locator, String text) {
		shortWait();
		try {
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByVisibleText(text);
			ReportUtil.logMessage("SelectByVisibleText action performed successfully on element ", text.toUpperCase());
		} catch (TimeoutException e) {
			ReportUtil.logMessage("Timeout occurs while selecting dropdrown option for element ", text.toUpperCase());
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			ReportUtil.logMessage("NoSuchElementException occurs while selecting dropdrown option for ",
					text.toUpperCase());
		}
	}

	public void dropdownSelectByValue(By locator, String value) {
		shortWait();
		try {
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByValue(value);
			ReportUtil.logMessage("SelectByValue action performed successfully on element ", value.toUpperCase());
		} catch (TimeoutException e) {
			ReportUtil.logMessage("Timeout occurs while selecting dropdrown option for element ", value.toUpperCase());
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			ReportUtil.logMessage("NoSuchElementException occurs while selecting dropdrown option for ",
					value.toUpperCase());
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> mergeLists(List<String>... lists) {
		return Stream.of(lists).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public boolean isMobileDriver() {
		if (DriverManager.getDriver() instanceof AndroidDriver || DriverManager.getDriver() instanceof IOSDriver)
			return true;

		else
			return false;

	}

	public void clickUsingAction(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).click().build().perform();

	}

	public boolean isElementPresent(By locator) {
		try {
			LoggerUtil.log("is Element present ..." + locator.toString());
			System.out.println(driver.findElements(locator).size());
			return (driver.findElements(locator).size() == 1);
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isElementExists(By locator) {
		try {
			LoggerUtil.log("is Element present ..." + locator.toString());
			System.out.println(driver.findElements(locator).size());
			return (driver.findElements(locator).size() > 0);
		} catch (Exception e) {
			return false;
		}
	}
	
	

	public boolean isElementInVisibleShort(By locator, boolean ignoreException) {
		waitForElementInVisibilityShort(locator, ignoreException);
		try {
			LoggerUtil.log("is Element InVisible ..." + locator.toString());
			return !(driver.findElement(locator).isDisplayed());
		} catch (StaleElementReferenceException e) {
			return isElementInVisibleShort(locator, ignoreException);
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	public void waitForElementInVisibilityShort(By locator, boolean ignoreException) {
		try {
			LoggerUtil.log("Waiting for element Invisibility - " + locator.toString());
			shortWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {
			if (!ignoreException) {
				Assert.fail(e.getMessage());
			}
		}
	}

	public boolean isElementVisibleShort(By locator) {
		waitForElementVisibilityShort(locator, true);
		try {
			LoggerUtil.log("is Element Visible ..." + locator.toString());
			return driver.findElement(locator).isDisplayed();
		} catch (StaleElementReferenceException e) {
			return isElementVisibleShort(locator);
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementVisibleShort(WebElement element) {
	//	waitForElementVisibilityShort(locator, true);
		try {
			LoggerUtil.log("is Element Visible ..." + element.toString());
			return element.isDisplayed();
		} catch (StaleElementReferenceException e) {
			return isElementVisibleShort(element);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void waitForElementVisibilityShort(By locator, boolean ignoreException) {
		try {
			LoggerUtil.log("Waiting for element Visibility - " + locator.toString());
			shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			if (!ignoreException) {
				Assert.fail(e.getMessage());
			}
		}
	}

	public List<String> removeSpecialCharacters(List<String> str) {
		// .replaceAll("Ã¢â‚¬â„¢", "").replaceAll("Ã¢??", "").replaceAll("â€™", "")
		List<String> result = new ArrayList<>();
		for (String text : str) {
			result.add(text.replaceAll("[?â€œ?â€�Ã¢â‚¬â„¢Ã¢??â€™Ã¢â‚¬Å“]", ""));
		}
		return result;
	}

	public LinkedHashMap<String, Boolean> navigateAndGetText(By headingLocator, List<String> urls,
			List<String> urlTexts, boolean isTitle) {
		String text = null;
		boolean isElement = false;
		LinkedHashMap<String, Boolean> results = new LinkedHashMap<>();
		String stage = "https://www-staging.adoptapet.com";
		By banner = By.cssSelector("div[class*='header-cta'] >button");
		for (int i = 0; i < urls.size(); i++) {
			String url = urls.get(i);
			String urlText = urlTexts.get(i);
			if (url.contains("http"))
				driver.get(url);
			else
				driver.get(stage + url);
			if (!isTitle) {
				isElement = isElementDisplayed(headingLocator);
				shortWait();
				if (isElementDisplayed(banner))
					clickElement(banner);
				shortWait();
				text = getElementText(headingLocator).replaceAll("[\\r\n]", " ").trim();
				if (isElement && !text.equals(null) && !text.contains("you have been blocked")) {
					ReportUtil.addScreenShot(Status.PASS,
							"\"" + text + "\" page heading comes after navigation of \"" + urlText + "\".");
					if(text.toUpperCase().contains("ZOETIS PRIVACY"))
						text = "Welcome to the Zoetis Privacy Center";		
					results.put(text, isElement);
				} else {
					ReportUtil.addScreenShot(urlText + " link is not working.");
					results.put(urlText, false);
				}
			} else {
				if (isElementPresent(headingLocator))
					ReportUtil.addScreenShot(Status.PASS, getTitleOfPage().toLowerCase() + " contains " + urlText);
				else
					ReportUtil.addScreenShot(urlText + " link is not working.");
			}
		}
		return results;
	}
	
	public String getPageSource() {
		midWait();
		String pageSource= DriverManager.getDriver().getPageSource();
		return pageSource;
	}
	
	public WebElement getActiveElement() {
		return DriverManager.getDriver().switchTo().activeElement();
	}

	public void navigateToPeviousPage(String pageUrl) {
		driver.get(pageUrl);
	}
	
	public  String convertDomain(String url) {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            if (host.endsWith(".com")) {
                return url.replace(".com", ".co.uk");
            } else if (host.endsWith(".co.uk")) {
                return url.replace(".co.uk", ".com");
            } else {
                // If the input URL doesn't end with ".com" or ".co.uk", return as is
                return url;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // In case of a malformed URL, return input URL as is
            return url;
        }
    }
	
	 public static String getTextOfDisplayedElement(List<WebElement> elements) {
	        for (WebElement el : elements) {
	            String style = el.getAttribute("style");
	            if (style == null || !style.replace(" ", "").contains("display:none")) {
	                return el.getText().trim();
	            }
	        }
	        return ""; // No visible element found
	    }
	
	
	public void verifyEquals(String actualText, String expectedText, String passMsg, String failMsg) {
	    softAssert.assertEquals(actualText.toLowerCase(), expectedText.toLowerCase(), failMsg);

	    if (actualText.equalsIgnoreCase(expectedText)) {
	    	ReportUtil.logMessage(Status.PASS, expectedText);
	        ReportUtil.addScreenShot(Status.PASS, passMsg);
	    } else {
	    	ReportUtil.addScreenShot(failMsg, true);
	        ReportUtil.logMessage(Status.FAIL, "Text Assertion Error " +" Expected: " + expectedText + ", but got: " + actualText);
	    }
	}
	
	public void validateTrue(boolean flag, String validationMessage, String messageIfValidationFails) {
	    if (flag) { 
	        ReportUtil.logMessage(Status.PASS, validationMessage);
	        ReportUtil.addScreenShot(Status.PASS, validationMessage);
	    } else {
	        ReportUtil.logMessage(Status.FAIL, messageIfValidationFails);
	        ReportUtil.addScreenShot(Status.FAIL, messageIfValidationFails);
	    }

	    softAssert.assertTrue(flag, messageIfValidationFails); 
	}
	
	public void verifyElementEquals(WebElement actualElement, WebElement expectedElement, String passMsg, String failMsg) {
	    softAssert.assertEquals(actualElement, expectedElement, failMsg);

	    if (actualElement.equals(expectedElement)) {
	        ReportUtil.logMessage(Status.PASS, passMsg);
	        ReportUtil.addScreenShot(Status.PASS, passMsg);
	    } else {
	        ReportUtil.addScreenShot(failMsg, true);
	        ReportUtil.logMessage(Status.FAIL, "Element Assertion Error - Expected: " + expectedElement + ", but got: " + actualElement);
	    }
	}


	
}
