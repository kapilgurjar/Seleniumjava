package com.rediron.apex.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ElementUtil2;
import com.rediron.apex.utils.Utility;

public class Login  {

	private WebDriver driver;
	public ElementUtil eleUtil;

	private By userName = By.xpath("//input[@placeholder='Username']");
	private By password = By.xpath("//input[@placeholder='Password']");
	private By site = By.xpath("//button[contains(@id,'SITE_NO_lov_btn')]");
	private By siteName = By.xpath("//td[contains(text(),'108')]");
	private By signInButton = By.xpath("//button[@id='LOGIN']");
	private By allSiteList = By.cssSelector("#PopupLov_9999_P9999_SITE_NO_dlg table > tbody");
	private By companyLogo = By.xpath("//span[@class='t-Login-logo ']");
	private By cancelButton = By.cssSelector("#REFRESH span");
	private By signOut = By.xpath("//a[text()='Sign Out']");
	//private By viewGroup = By.xpath("//a[text()='View Groups']");
	private By profileMenu = By.cssSelector("#L417626162183893208");
	//private By oldPassword = By.cssSelector("#P19_OLD_PWD");
//	private By newPassword = By.cssSelector("#P19_NEW_PWD");
//	private By confirmPassword = By.cssSelector("#P19_CNFRM_NEW_PWD");
//	private By changePassword = By.xpath("//a[text()='Change Password']");
//	private By changePasswordOk = By.cssSelector("#B232072664759690897");
//	private By changePasswordCancel = By.cssSelector("#B232073049259690897");

	public Login(WebDriver driver) {
		this.driver=driver;
		//PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		//eleUtil = new ElementUtil2(driver);
		//cashDrawerSetup = new CashDrawerSetUp();
	}

	public String Logintoapplication(String name, String pass) throws Exception {
		eleUtil.doSendKeys(userName, name);
		eleUtil.doSendKeys(password, pass);
		eleUtil.clickWhenReady(signInButton, 30);
		Thread.sleep(3000);
		eleUtil.clickWhenReady(site, 30);
		Thread.sleep(3000);
		eleUtil.doActionMoveClick(siteName);
		eleUtil.clickWhenReady(signInButton, 30);
		return eleUtil.doGetPageTitleIs(Utility.getGlobalValue("title"), 30);
	}

	/**
	 * This method will return company logo is visible or not
	 * 
	 * @return
	 */
	public boolean verifyCompanyLogo() {

		return eleUtil.isDisplayed(companyLogo);

	}

	/**
	 * @param name,pass
	 * @return Site Name list
	 * @throws Exception
	 */
	public List<String> getDataFromSiteNameListUI(String name, String pass) throws Exception {
		eleUtil.doSendKeys(userName, name);
		eleUtil.doSendKeys(password, pass);
		eleUtil.doClick(signInButton);
		Thread.sleep(2000);
		eleUtil.doClick(site);
		Thread.sleep(2000);
		//cashDrawerSetup.clickOnLoadMoreRowsButton();
		Thread.sleep(2000);
		return eleUtil.getElementsTextList(allSiteList);
	}

	public List<String> getDataFromSiteNameListDB() throws Exception {

		List<String> SiteNameList = Utility
				.getDataFromDatabase("SELECT s.site_no || '-' || s.full_name site_nm, s.site_no r\r\n" + "\r\n"
						+ "    FROM site s, siteinst si, apex_collections m\r\n" + "\r\n"
						+ "   WHERE     s.site_no = si.site_no\r\n" + "\r\n" + "         AND s.site_no = m.c001\r\n"
						+ "\r\n" + "         AND s.site_active = 'Y'\r\n" + "\r\n"
						+ "         AND s.oracle_db_fl = 'Y'\r\n" + "\r\n" + "         AND s.download_fl = 'N'\r\n"
						+ "\r\n" + "         AND m.collection_name = 'MYSITES_COLL'---:APP_SITE_COLL_NM--\r\n" + "\r\n"
						+ "ORDER BY s.site_no", "SITE_NM");

		return SiteNameList;
	}

	public void enterUserIdAndPasword(String name, String pass) throws Exception {
		eleUtil.doSendKeys(userName, name);
		eleUtil.doSendKeys(password, pass);
		Thread.sleep(2000);
		eleUtil.doClick(signInButton);
	}

	public void enterSiteName() {
		eleUtil.doClick(signInButton);
		eleUtil.doClick(site);
		eleUtil.doClick(siteName);
	}

	public boolean verifyIsCancelButtonWorking() throws Exception {
		Thread.sleep(2000);
		eleUtil.doActionMoveClick(cancelButton);
		eleUtil.readValueFromInput(userName).isEmpty();
		return eleUtil.readValueFromInput(password).isEmpty();
	}

	public boolean isSignOutWorking() throws Exception {
		eleUtil.doClick(profileMenu);
		eleUtil.doClick(signOut);
		return eleUtil.doIsDisplayed(companyLogo);
	}
/*
	public String isviewGroupsWorking() throws Exception {
		eleUtil.doClick(profileMenu);
		eleUtil.doClick(viewGroup);
		//return eleUtil.doGetPageTitleIs("Data View", config.getImplicitWait());

	}*/

}
