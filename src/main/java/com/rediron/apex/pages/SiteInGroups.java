package com.rediron.apex.pages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Step;

public class SiteInGroups extends TestBase {

	public By loader = By.xpath("/html[1]/body[1]/span[1]/span[1]");
	By siteNameInput = By.cssSelector("input#P17_FSITE");
	By siteLovBtn = By.cssSelector("button#P17_FSITE_lov_btn");
	By listOfSite = By.xpath("//div[@id='PopupLov_17_P17_FSITE_dlg']//table//tr//td");
	By saveButton = By.cssSelector("button#save_button");
	By clearButton = By.cssSelector("button#CLEAR");
	By exitButton = By.cssSelector("button#EXIT");
	By addGroup = By.cssSelector("button#ADD_GROUP");
	By removeGroup = By.cssSelector("button#REMOVE_GROUP");
	By siteGroupTable = By.xpath("//div[@id='SITGRP_data_panel']//table//tr//td");
	By yesButton = By.id("Yes");
	By noButton = By.id("No");
	By cancelButton = By.id("Cancel");
	By clearPopMessage = By.id("paraId");

	public SiteInGroups() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
	}

	@Step("Get site name from site name input box")
	public String getSiteName() {
		return eleUtil.getElementAttributesWithWait(siteNameInput, config.getExplicitWait(), "value");
	}

	@Step("Get sites name from list of values")
	public List<String> getListOfSites() throws Exception {
		eleUtil.doClick(siteLovBtn);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.getElementsTextList(listOfSite);
	}

	@Step("Get sites name from list of values")
	public SiteInGroups selectSiteFromListOfValue(String text) throws Exception {
		eleUtil.doClick(siteLovBtn);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(listOfSite, text);
		return this;
	}

	@Step("Get list of sites from database")
	public List<String> getTwoColumnCombineDataFromDB(String query, String columnName1, String columnName2)
			throws SQLException {
		List<String> listOfSites = new ArrayList<>();
		List<String> siteNo = Utility.getDataFromDatabase(query, columnName1);
		List<String> fullName = Utility.getDataFromDatabase(query, columnName2);
		for (int i = 0; i < siteNo.size(); i++) {
			String siteNum = siteNo.get(i).trim();
			for (int j = i; j <= i; j++) {
				String fullNam = fullName.get(j).trim();
				listOfSites.add(siteNum + "-" + fullNam);
			}
		}
		return listOfSites;
	}

	@Step("Get site input box class attribute")
	public String getSiteInputAttributeValue() {
		return eleUtil.doGetAttribute(siteNameInput, "class");
	}

	@Step("Verify that clear button,save button,addgroup and remove button present or not page")
	public boolean isSaveClearAddGroupAndRemoveGroupDisplayed() {
		return eleUtil.isDisplayed(clearButton) && eleUtil.isDisplayed(saveButton) && eleUtil.isDisplayed(addGroup)
				&& eleUtil.isDisplayed(removeGroup);
	}

	@Step("Click on the exit button")
	public SiteInGroups clickOnExitButton() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on the clear button")
	public SiteInGroups clickOnClearButton() {
		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on  add to group check box")
	public SiteInGroups addASiteGroup(String text) {
		By xpath = By.xpath("//div[@id='GROUPS_data_panel']//td[text()='" + text + "']//preceding::td[1]");
		eleUtil.doClick(xpath);
		return this;
	}

	@Step("Click on remove to group check box")
	public SiteInGroups removeASiteGroup(String text) {
		By xpath = By.xpath("//div[@id='SITGRP_data_panel']//td[text()='" + text + "']//following::td[3]");
		eleUtil.doClick(xpath);
		return this;
	}

	@Step("Get window pop up message")
	public String getPopText() {
		return cashDrawerSetup.getPopWindowText();
	}

	@Step("Click on alert pop up message ok button")
	public SiteInGroups clickOnPopOkButton() {
		cashDrawerSetup.clickOnSafeRecOkButton();
		return this;
	}

	@Step("Click on add to group button")
	public SiteInGroups clickOnAddGroupButton() {
		eleUtil.doClick(addGroup);
		return this;
	}

	@Step("Click on add to group button")
	public SiteInGroups clickOnRemoveGroupButton() {
		eleUtil.doClick(removeGroup);
		return this;
	}

	@Step("Click on save button")
	public SiteInGroups clickOnSaveButton() {
		eleUtil.doClick(saveButton);
		return this;
	}

	@Step("")
	public List<String> getSiteGroupTableData() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.getElementsTextList(siteGroupTable);
	}

	@Step("")
	public boolean IsYesCancelAndNoButtonPresentOnClearPopWindow() {

		return eleUtil.isDisplayed(yesButton) && eleUtil.isDisplayed(noButton) && eleUtil.isDisplayed(cancelButton);
	}

	@Step("Click on yes button")
	public SiteInGroups clickOnClearYesButton() {
		eleUtil.clickWhenReady(yesButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on no button")
	public SiteInGroups clickOnClearNoButton() {
		eleUtil.clickWhenReady(noButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on no button")
	public SiteInGroups clickOnClearCancelButton() {
		eleUtil.clickWhenReady(cancelButton, config.getExplicitWait());
		return this;
	}
}
