package com.rediron.apex.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rediron.apex.factory.DriverFactory;
import com.rediron.apex.testBase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Step;

public class ElementUtil  {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	int timeout=DriverFactory.timeOut;
	
	private static final String ELEMENT_NOT_FOUND_ERROR = "element is not available on the page : ";

	public By getBy(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "css":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;

		default:
			break;
		}

		return locator;
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
		//WebElement ele = driver.findElement(locator);
		//return ele;
	}

	public void doClick(By locator) {
		getElement(locator).click();
		// test.log(LogStatus.INFO, "Clicking on link :" + locator);
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(getBy(locatorType, locatorValue)).click();
	}

	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doEnter(By locator) {
		WebElement ele = getElement(locator);
		ele.sendKeys(Keys.ENTER);

	}

	public void doSendKeysWithOutClear(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doSendKey(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.sendKeys(Keys.CONTROL, "a");
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys(value);
	}

	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(getBy(locatorType, locatorValue)).sendKeys(value);
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	public boolean waitForActAttribute(By locator,String actAttr,String value,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 return wait.until(ExpectedConditions.attributeToBe(locator, actAttr, value));
	}

	public String doGetCssValue(By locator, String value) {
		return getElement(locator).getCssValue(value);
	}

	public boolean doIsDisplayed(By locator) {
		return waitForElementVisible(locator, timeout, timeout).isDisplayed();
	}

	public boolean doIsEnabled(By locator) {
	//	waitForElementPresenceWithWait(locator, config.getImplicitWait(), config.getImplicitWait());
		return getElement(locator).isEnabled();

	}

	public void doClearTextbox(By locator) {
		getElement(locator).clear();

	}

	public String readValueFromElement(By locator) {

		if (null == locator) {
			return null;
		}

		boolean displayed = false;
		try {
			displayed = getElement(locator).isDisplayed();
		} catch (Exception e) {
			return null;
		}

		if (!displayed) {
			return null;
		}
		String text = getElement(locator).getText();
		//test.log(LogStatus.INFO, "weblement valus is.." + text);
		return text;
	}

	public String readValueFromInput(By locator) throws Exception {
		waitForElementPresenceWithWait(locator, timeout, timeout);
		if (null == locator) {
			return null;
		}
		if (!isDisplayed(locator)) {
			return null;
		}

		String value = getElement(locator).getAttribute("value");
		//test.log(LogStatus.INFO, "weblement valus is.." + value);
		return value;
	}

	@Step("element is displayed..")
	public boolean isDisplayed(By locator) {
		try {
			getElement(locator).isDisplayed();
			//test.log(LogStatus.INFO, "element is displayed.." + locator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean isNotDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			return false;
		} catch (Exception e) {
			return true;
		}

	}

	public List<WebElement> getElements(By locator) {
		waitForElementPresenceWithWait(locator, timeout, timeout);
		return driver.findElements(locator);
	}

	public int getElementCount(By locator) {
		waitForElementPresenceWithWait(locator, timeout, timeout);
		return getElements(locator).size();
	}

	public void printElementsText(By locator) {
		waitForElementPresenceWithWait(locator, timeout, timeout);
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
		}
	}

	/**
	 * This method will return the list of element's text
	 * 
	 * @param locator
	 * @return
	 */
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<>();
		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	/**
	 * This method will return the list of element's attribute value
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param attrName
	 * @return
	 */
	public List<String> getElementsAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<>();
		for (WebElement e : eleList) {
			String attrVal = e.getAttribute(attrName);
			eleAttrList.add(attrVal);
		}
		return eleAttrList;
	}

	/**
	 * This method will click element 's link text
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param attrName
	 * @return
	 */
	public void clickOnLink(By locator, String linkText) {
		List<WebElement> langList = getElements(locator);
		System.out.println(langList.size());
		for (WebElement e : langList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(linkText)) {
				e.click();
				break;
			}
		}
	}

	/**
	 * This method will click element 's link text
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param attrName
	 * @return
	 */
	public void clickOnListOfElement(By locator) {
		List<WebElement> langList = getElements(locator);
		System.out.println(langList.size());
		for (WebElement e : langList) {
			e.click();
			break;
		}
	}

	/******************************* Drop Down Utils ***************************/

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
		//test.log(LogStatus.INFO, "Locator : " + locator + " Value : " + index);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
		//test.log(LogStatus.INFO, "Locator : " + locator + " Value : " + visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
		//test.log(LogStatus.INFO, "Locator : " + locator + " Value : " + value);

	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public List<String> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<>();
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void doSelectLastOptionfromDropDown(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> ls = select.getOptions();
		select.selectByIndex(ls.size() - 1);
		//test.log(LogStatus.INFO, "Locator : " + locator);
	}

	/******************* Actions methods **********************/

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	/**
	 * @param locator
	 */
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	public void doActionsDoubleClick(By locator) {
		Actions act = new Actions(driver);
		act.doubleClick(getElement(locator)).build().perform();
	}

	public void doActionMoveClick(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).click().build().perform();
	}

	public void doActionMoveSendKey(By locator, String value) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).sendKeys(value).build().perform();
	}

	public void doActionClear() {
		Actions act = new Actions(driver);
		act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform();

	}

	public void doActionEnter() {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).build().perform();

	}

	/************************* Wait Utils **************************/

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}
	
	
	public void doSendKeysWithWait(By locator, int timeOut, String value) {
		waitForElementPresence(locator, timeOut).sendKeys(value);
	}

	public void doClickWithWait(By locator, int timeOut) {
		waitForElementPresence(locator, timeOut).click();
	}
	
	/**
	 * An expectation for checking an element is click able and visible such that you
	 * can click it.
	 * @author Kapil
	 * @param locator
	 * @param timeOut
	 */
	public  void clickWithRetry(By locator, long timeOut) {
		WebElement ele = getElement(locator);
        try {
        	
        	WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
        	wait.until(ExpectedConditions.visibilityOf(ele));
        	wait.until(ExpectedConditions.elementToBeClickable(ele));
        	ele.click();
        } catch (ElementClickInterceptedException exception) {
            log("Warning :: element was not clicked " + exception.getMessage());
            for (long i = 0; i < timeOut; i++) {
                try {
                    sleep(1);
                    ele.click();
                    break;
                } catch (ElementClickInterceptedException exception1) {
                    log("Warning :: " + exception1.getMessage() + " Try " + i);
                }
            }
        }
    }
	
	public  void clickIgnoreStaleElementException(By locator, long timeOut) {
		WebElement ele = getElement(locator);
        try {
        	
        	WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
        	wait.until(ExpectedConditions.visibilityOf(ele));
        	wait.until(ExpectedConditions.elementToBeClickable(ele));
        	ele.click();
        } catch (StaleElementReferenceException exception) {
            log("Warning :: element was not clicked " + exception.getMessage());
            for (long i = 0; i < timeOut; i++) {
                try {
                    sleep(1);
                    ele.click();
                    break;
                } catch (StaleElementReferenceException exception1) {
                    log("Warning :: " + exception1.getMessage() + " Try " + i);
                }
            }
        }
    }
	
	public void click(By locator, long timeOut) {
		WebElement ele = getElement(locator);
		for(int i=0; i<=timeOut;i++){
			  try{
				  ele.click();
			     break;
			  }
			  catch(Exception e){
				  log(e.getMessage());
			  }
			}
	}

	public String getElementTextWithWait(By locator, int timeOut) {
		return waitForElementPresence(locator, timeOut).getText();
	}
	
	public String getElementAttributesWithWait(By locator, int timeOut,String value) {
		return waitForElementPresence(locator, timeOut).getAttribute(value);
	}

	/**
	 * An expectation for checking an element isClickable or not
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param timeOut
	 */
	public boolean ISclickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void doStaticWait(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	public void doSendKeysWithVisibleElement(By locator, int timeOut, String value) {
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}

	public void doClickWithVisibleElement(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}

	public String getElementTextWithVisibleElement(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut).getText();
	}

	
	public WebElement waitForElementToBeVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(ElementNotInteractableException.class)
				.withMessage(ElementUtil.ELEMENT_NOT_FOUND_ERROR + locator);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @Author Kapil
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * @Author Kapil
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public Boolean waitForElementsNotVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.invisibilityOf(getElement(locator)));
	}

	/**
	 * @Author Kapil
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public boolean waitForPageTitle(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleContains(titleVal));
	}

	public boolean waitForPageActTitle(String actTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleIs(actTitle));
	}

	public String doGetPageTitleContains(String titleVal, int timeOut) {
		if (waitForPageTitle(titleVal, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}

	public String doGetPageTitleIs(String titleVal, int timeOut) {
		if (waitForPageActTitle(titleVal, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}

	public boolean waitForText(By locator, String textVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.textToBe(locator, textVal));
	}

	public String waitForUrlContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			return null;

		}
		return null;
	}

	public String waitForUrlToBe(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			return null;

		}
		return null;
	}

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public Alert getAlert() throws Exception {
		//test.log(LogStatus.INFO, "Switch Control To Alert");
		return driver.switchTo().alert();
	}

	public void AcceptAlert() throws Exception {
		//test.log(LogStatus.INFO, "Alert Accepted");
		getAlert().accept();
	}

	public void DismissAlert() throws Exception {
		//test.log(LogStatus.INFO, "Alert Dismissed");
		getAlert().dismiss();
	}

	public String getAlertText() throws Exception {
		String text = getAlert().getText();
		//test.log(LogStatus.INFO, text);
		return text;
	}

	public String getcurrentURL() throws Exception {

		return driver.getCurrentUrl();
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			System.out.println("true");
			return true;
		} catch (NoAlertPresentException e) {
			System.out.println("false");
			return false;
		}
	}

	public void AcceptPrompt(String text) throws Exception {

		if (!isAlertPresent())
			return;

		Alert alert = getAlert();
		alert.sendKeys(text);
		alert.accept();
		System.out.println(text);
	}

	public void AcceptAlertIfPresent() throws Exception {
		if (!isAlertPresent())
			return;
		AcceptAlert();
		System.out.println("AcceptAlert");
	}

	public void DismissAlertIfPresent() throws Exception {

		if (!isAlertPresent())
			return;
		DismissAlert();
		System.out.println("DismissAlert");
	}

	public WebDriver waitForFrameByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public WebDriver waitForFrameByLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public WebDriver waitForFrameByElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage(locator + " is not found within the given time......");
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceWithWait(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage(locator + " is not found within the given time......");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
	
	public WebElement retryingElement(By locator, int timeOut) {

		WebElement element = null;

		int attempts = 0;

		while (attempts < timeOut) {

			try {
				element = getElement(locator);
				System.out.println("element is found in attempt: " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt : " + attempts + " : " + " for " + locator);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;

		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println("element is not found exception ...tried for : " + timeOut + " secs"
						+ " with the interval of : " + 500 + " ms");

			}

		}

		return element;

	}

	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement element = null;

		int attempts = 0;

		while (attempts < timeOut) {

			try {
				element = getElement(locator);
				System.out.println("element is found in attempt: " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt : " + attempts + " : " + " for " + locator);

				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;

		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println("element is not found exception ...tried for : " + timeOut + " secs"
						+ " with the interval of : " + pollingTime + " ms");

			}

		}

		return element;

	}
	
	

	/*******************************
	 * Window Handling Utils
	 ***************************/

	public Set<String> getWindowHandles() {
		// Log.info("Window Handling");
		return driver.getWindowHandles();
	}

	public void SwitchToWindow(int index) {

		LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
		System.out.println(windowsId);
		driver.switchTo().window(windowsId.get(index));
		driver.manage().window().maximize();
		//test.log(LogStatus.INFO, "Switch to Window is Successfull");
	}

	public void switchToParentWindow() {
		LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
		driver.switchTo().window(windowsId.get(0));
		//test.log(LogStatus.INFO, "Switch to Parent Window");
	}

	public void switchToParentWithChildClose() {
		LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());

		for (int i = 1; i < windowsId.size(); i++) {
			System.out.println(windowsId.get(i));
			driver.switchTo().window(windowsId.get(i));
			driver.close();
		}
		switchToParentWindow();
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
		System.out.println("Frame ID/Name : " + nameOrId);
	}

	public void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
		//test.log(LogStatus.INFO, "Frame Index : " + index);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		//test.log(LogStatus.INFO, "Switch to Default Content");
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	
	 public static void log(String message) {
	        System.out.println(message);
	    }

	    public static void sleep(long timeOut) {
	        try {
	            Thread.sleep(timeOut);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}
