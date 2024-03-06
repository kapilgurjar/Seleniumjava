package com.rediron.apex.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

import io.qameta.allure.Step;

public class ConfigurationFlagMaintenance extends TestBase {

	By saveButton = By.cssSelector("button#save_button");
	By clearButton = By.cssSelector("button#CLEAR");
	By search = By.id("GC_ig_toolbar_search_field");
	By goButton = By.xpath("//span[text()='Go']//parent::button");
	By table_NameColumn = By.xpath("//table//tr//td[1]");
	By table_TypeColumn = By.xpath("//table//tr//td[2]");
	By rowsPerPage = By.xpath("//select[@title='Rows Per Page']");
	By processing = By.xpath("//span[text()='Processing']");
	By noResultFound = By.xpath("//span[text()='No data found' and @id]");
	By typeButton = By.id("CONFIG_TYPE_HDR");
	By typeLovData = By.xpath("//div[@id='GC_ig_column_header_menu_rows']//a");
	By valueFirstColumn = By.xpath("(//table//tr//td[3])[1]");
	By alertPopText = By.id("paraId");
	By OkButton = By.cssSelector("button#OK");
	By actionButton = By.cssSelector("button#GC_ig_toolbar_actions_button");
	By filter = By.xpath("//button[text()='Filter']//parent::span");
	By coloumnName = By.cssSelector("select#GC_ig_FD_COLUMN");
	By valueButton = By.id("GC_ig_FD_VALUE-showAll");
	By filterSaveButton = By.xpath("//button[text()='Save']");
	By filterText = By.xpath("(//a[@data-setting='filter']//span[2])[1]");
	By clearYesButton=By.cssSelector("button#Yes");
	By clearNoButton=By.cssSelector("button#No");
	By clearCancelButton=By.cssSelector("button#Cancel");
	By exitButton=By.xpath("//span[text()='Exit']//parent::button");

	
	public ConfigurationFlagMaintenance() {
		PageFactory.initElements(driver, this);
		cashDrawerSetup = new CashDrawerSetUp(driver);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		menuSelection = new MenuSelection(driver);
	}

	@Step("Verify that clear and save button is not dispaying for remote site")
	public boolean isClearAndSaveButtonDisplayed() {

		return eleUtil.isDisplayed(clearButton) && eleUtil.isDisplayed(saveButton);
	}

	public ConfigurationFlagMaintenance clickOnSaveButton() {
		eleUtil.doClickWithWait(saveButton, config.getExplicitWait());
		return this;
	}
	public ConfigurationFlagMaintenance clickOnCancelButton() {
		eleUtil.doClickWithWait(clearButton, config.getExplicitWait());
		return this;
	}
	
	public ConfigurationFlagMaintenance clickOnClearYesButton() {
		eleUtil.doClickWithWait(clearYesButton, config.getExplicitWait());
		return this;
	}
	
	public ConfigurationFlagMaintenance clickOnClearNoButton() {
		eleUtil.doClickWithWait(clearNoButton, config.getExplicitWait());
		return this;
	}
	
	public ConfigurationFlagMaintenance clickOnExitButton() {
		eleUtil.doClickWithWait(exitButton, config.getExplicitWait());
		return this;
	}
	
	public ConfigurationFlagMaintenance clickOnClearCancelButton() {
		eleUtil.doClickWithWait(clearCancelButton, config.getExplicitWait());
		return this;
	}
	

	@Step("Select last value from dropdown")
	public ConfigurationFlagMaintenance selectRowsPerPage() {
		eleUtil.doSelectLastOptionfromDropDown(rowsPerPage);
		return this;
	}

	public ConfigurationFlagMaintenance searchDataInTable(String name) {
		selectRowsPerPage();
		eleUtil.waitForElementsNotVisible(processing, config.getExplicitWait());
		eleUtil.doSendKeysWithWait(search, config.getExplicitWait(), name);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(goButton, config.getExplicitWait());
		return this;
	}

	public String getTableFirstRowData() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doGetText(table_NameColumn);
	}

	@Step("Verify that clear and save button is not dispaying for remote site")
	public boolean isNoResultFoundDispalyed() {

		return eleUtil.isDisplayed(noResultFound);
	}

	public ConfigurationFlagMaintenance clickOnTypeLovButton() {
		eleUtil.clickWhenReady(typeButton, config.getExplicitWait());
		return this;
	}

	public ConfigurationFlagMaintenance selectDataFromTypeLov(String value) {
		eleUtil.clickOnLink(typeLovData, value);
		return this;
	}

	public ConfigurationFlagMaintenance enterDataInValueTextFiled(String value) {
		eleUtil.doStaticWait(4000);
		eleUtil.doActionsDoubleClick(valueFirstColumn);
		eleUtil.doActionClear();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doActionsSendKeys(valueFirstColumn, value);
		eleUtil.doEnter(valueFirstColumn);
		return this;
	}

	public String getAlertPopText() {
		eleUtil.doStaticWait(config.getSmallWait());
		return eleUtil.doGetText(alertPopText).trim();
	}

	public ConfigurationFlagMaintenance clickOnOkButton() {
		eleUtil.clickWhenReady(OkButton, config.getExplicitWait());
		return this;
	}

	public ConfigurationFlagMaintenance clickOnActionButton() {
		eleUtil.clickWhenReady(actionButton, config.getExplicitWait());
		return this;
	}

	public ConfigurationFlagMaintenance clickOnFilterButton() {
		eleUtil.clickWhenReady(filter, config.getExplicitWait());
		return this;
	}

	public String appliedFilterOnColumn(String colName, String value) {
		By xpath = By.xpath("//button[text()='" + value + "']");
		eleUtil.doStaticWait(config.getMediumWait());
		selectRowsPerPage();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.waitForElementsNotVisible(processing, config.getExplicitWait());
		clickOnActionButton();
		clickOnFilterButton();
		eleUtil.doSelectDropDownByVisibleText(coloumnName, colName);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(valueButton, config.getExplicitWait());
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(xpath, config.getExplicitWait());
		eleUtil.clickWhenReady(filterSaveButton, config.getExplicitWait());
		return eleUtil.doGetText(filterText);
	}

	public boolean verifyThatFilterIsApplied(String expValue) {
		boolean sts = false;
		List<String> TypeValues = eleUtil.getElementsTextList(table_TypeColumn);
		for (int i = 0; i < TypeValues.size(); i++) {
			String value = TypeValues.get(i);
			if (value.equalsIgnoreCase(expValue)) {
				sts = true;
			} else {
				sts = false;
			}
		}
		return sts;
	}
	
	
}
