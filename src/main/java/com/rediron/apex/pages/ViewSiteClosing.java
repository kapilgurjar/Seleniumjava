package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class ViewSiteClosing extends TestBase {

	public ElementUtil eleUtil;

	// WebElements
	By siteGroup = By.xpath("//input[contains(@id,'GROUP_ID')and @type='text']");
	By siteGroupTable = By.xpath("//div[contains(@id,'GROUP_ID_dlg')]//table/tbody/tr//td");
	By exitButton = By.xpath("//span[text()='Exit']//parent::button");
	By table = By.xpath("//*[contains(@id,\"_orig\")]//tbody//tr//td[1]");
	By nextButton = By.xpath("(//*[contains(@id,'_data_panel')]//button[@title='Next'])[2]");
	By siteInputBox = By.xpath("//div[contains(@id,'GROUP_ID_dlg')]//input");
	By allSite = By.xpath("//span[text()='ALL-ALL SITES']");
	By lastDayClosed = By.xpath("//*[contains(@id,'VSC_data_panel')]//tbody//tr//td[3]");
	By currentDay = By.xpath("//*[contains(@id,'VSC_data_panel')]//tbody//tr//td[4]");
	By tableColumn = By.xpath("//*[contains(@id,'VSC_data_panel')]//tbody/tr[2]/td[1]");

	public ViewSiteClosing() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
	}

	/**
	 * @author Kapil
	 */

	public void clickOnSiteGroup() {

		eleUtil.clickWhenReady(siteGroup, config.getExplicitWait());

	}

	public List<String> getSiteGroupName() {
		return eleUtil.getElementsTextList(siteGroupTable);
	}

	public String getSiteGroupClassAttr() {
		return eleUtil.doGetAttribute(siteGroup, "class");
	}

	public void clickOnExitButton() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
	}

	public List<String> getGetTableColoumData(String columnNO) throws Exception {
		By table = By.xpath("//*[contains(@id,'VSC_data_panel')]//tbody//tr//td[" + columnNO + "]");
		List<String> allSiteNo = new ArrayList<>();
		eleUtil.doSendKeys(siteInputBox, "ALL-ALL SITES");
		eleUtil.clickWhenReady(allSite, config.getExplicitWait());
		eleUtil.waitForElementsPresence(table, config.getExplicitWait());
		Thread.sleep(3000);
		List<String> SiteNo = eleUtil.getElementsTextList(table);
		for (String sitepage1 : SiteNo) {
			allSiteNo.add(sitepage1);
		}
		while (eleUtil.isDisplayed(nextButton)) {
			eleUtil.doStaticWait(config.getMediumWait());
			eleUtil.clickWhenReady(nextButton, config.getExplicitWait());
			eleUtil.waitForElementsPresence(table, config.getExplicitWait());
			Thread.sleep(5000);
			SiteNo = eleUtil.getElementsTextList(table);
			for (String sitepage : SiteNo) {
				allSiteNo.add(sitepage);

			}

		}

		System.out.println(allSiteNo.size());
		return allSiteNo;
	}

	public String getLastDayClosedDate() {
		return eleUtil.doGetText(lastDayClosed);
	}

	public String getCurrentDate() {
		return eleUtil.doGetText(currentDay);
	}

	public void enterSiteGroup(String groupName) {
		eleUtil.doSendKeys(siteInputBox, groupName);

	}

	public String getTableCoulmnData() {
		return eleUtil.doGetText(tableColumn);
	}
}
