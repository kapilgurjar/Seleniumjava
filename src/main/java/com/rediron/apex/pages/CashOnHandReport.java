package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class CashOnHandReport extends TestBase {

	public ElementUtil eleUtil;

	// (//input[contains(@id,'SITE_GROUP')])[2]
	//// input[contains(@id,'SITE_GROUP') and @type='text']
	By siteGroup = By.xpath("//input[contains(@id,'SITE_GROUP') and @type='text']");
	By cashOnHandTitle = By.xpath("//span[contains(@id,'ui-id')]");
	By closeButton = By.xpath("//button[@title='Close']");
	By cashOnHandReport = By.xpath("//a[text()='Cash on Hand Report']");
	By previewButton = By.xpath("//span[text()='Preview']//parent::button");
	By siteGroupButton = By.xpath("//button[contains(@id,'SITE_GROUP_lov_btn')]");
	By separateReportForEachSiteCheckBox = By.xpath("//input[@id='P18_SEP_RPT']");

	public CashOnHandReport() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
	}

	/**
	 * @author Kapil
	 * @return This Method will return current site/Group value
	 * 
	 */
	public String getSiteGroupTextValue() {
		eleUtil.waitForElementPresence(siteGroup, config.getExplicitWait());
		return eleUtil.doGetAttribute(siteGroup, "value");
	}

	/**
	 * @author Kapil
	 * @return This Method will return page title cash on hand
	 * 
	 */
	public String getWindowTitle() {
		return eleUtil.doGetText(cashOnHandTitle);
	}
	
	public void clickOnCloseButton() {
		
		eleUtil.clickWhenReady(closeButton, config.getExplicitWait());
	}

	public boolean verifyCloseButton() {
		eleUtil.clickWhenReady(closeButton, config.getExplicitWait());
		return eleUtil.doIsDisplayed(cashOnHandReport);

	}

	public void clickOnPreviewButton() {
		eleUtil.clickWhenReady(previewButton, config.getExplicitWait());
	}

	public void clickOnSiteGroupButton() {
		eleUtil.clickWhenReady(siteGroupButton, config.getExplicitWait());
	}

	/**
	 * @author Kapil @ This method will select site and group name from lov
	 * 
	 */
	public void selectSiteAndGroupName(String name) {
		By siteGroupName = By.xpath("//td[text()='" + name + "']");
		eleUtil.clickWhenReady(siteGroupName, config.getExplicitWait());
	}
	
	public boolean isCheckBoxEnableOrDisable() {
		return eleUtil.doIsEnabled(separateReportForEachSiteCheckBox);
	}
	

}
