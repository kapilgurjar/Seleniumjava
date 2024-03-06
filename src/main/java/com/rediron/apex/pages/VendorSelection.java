package com.rediron.apex.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

import io.qameta.allure.Step;

public class VendorSelection extends TestBase {

	By vendorFilters = By.cssSelector("div#Main_Region_Id>div>div>button");
	By frame = By.tagName("iframe");
	By vendorType = By.cssSelector("select[id*=VENDOR_TYPE]");
	By attribute = By.cssSelector("select[id*=ATTRIBUTE]");
	By siteButton = By.cssSelector("button[id*='SITE_LOV_lov_btn']");
	By siteLov = By.cssSelector("ul[role='listbox']>li");
	By siteLovTextBox = By.xpath("//input[contains(@id,'_SITE_LOV') and @type='text']");
	By showInResult = By.xpath("//label[contains(@id,'_SHOW_RESULTS_LABEL')]");
	By externalVendorId = By.xpath(
			"//span[@title='Vendor Id used in External System.']//preceding::th//span[text()='External Vendor Id']");
	By externalVendorLocation = By.xpath(
			"//span[@title='Vendor Loation used in External System.']//preceding::th//span[text()='External Vendor Location']");
	By vendorId = By.xpath("//button[@title='Help Text: Vendor ID']//parent::div//input");
	By uCCID = By.xpath("//button[@title='Help Text: UCC  ID']//parent::div//input");
	By extVenIDTextBox = By.xpath("//button[@title='Help Text: External Vendor ID']//parent::div//input");
	By extVenLocTextBox = By.xpath("//button[@title='Help Text: External Vendor Location']//parent::div//input");
	By clearButton = By.xpath("//div[@id='SearchMethod_Region']//td//button[1]");
	By okButton = By.xpath("//div[@id='SearchMethod_Region']//td//button[3]");
	By searchButton = By.xpath("//div[@id='SearchMethod_Region']//td//button[2]");
	By vendorIdSKU = By.cssSelector("input[id*='P30_VENDOR_ID']");
	By table = By.xpath("//div[@id='Vendor_Details_ir_ig_content_container']//tbody//tr//td[1]");
	By rowPerPage = By.xpath("//div[@id='Vendor_Details_ir_ig_toolbar']//select[@title='Rows Per Page']");
	By localRadioButton = By.xpath("//div[@id='P35_GLOBAL']//input[@id='P35_GLOBAL_0']");
	By globalRadioButton = By.xpath("//label[@for='P35_GLOBAL_1']");
	By criteriaAlertPopUp = By.id("paraId");
	By criteriaAlertOKButton = By.cssSelector("button#Ok");
	By criteriaAlertCancelButton = By.cssSelector("button#Cancel");

	// class constructor

	public VendorSelection() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
	}

	/**
	 * This method will click on the collapse button returning the attributes value
	 */
	public String verifyVendorFilterButtonCollapse() {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		eleUtil.doGetAttribute(vendorFilters, "aria-expanded");
		if (eleUtil.doGetAttribute(vendorFilters, "aria-expanded").equalsIgnoreCase("true")) {
			eleUtil.clickWhenReady(vendorFilters, config.getExplicitWait());
		}
		return eleUtil.doGetAttribute(vendorFilters, "aria-expanded");
	}

	public String verifyVendorFilterButtonExpand() {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		eleUtil.doGetAttribute(vendorFilters, "aria-expanded");
		if (eleUtil.doGetAttribute(vendorFilters, "aria-expanded").equalsIgnoreCase("false")) {
			eleUtil.clickWhenReady(vendorFilters, config.getExplicitWait());
		}
		return eleUtil.doGetAttribute(vendorFilters, "aria-expanded");
	}

	/**
	 * @return List of values vendor type cd lov
	 * @throws Exception
	 */
	public List<String> getVendorTypeLovData() throws Exception {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		Thread.sleep(5000);
		return eleUtil.getDropDownOptionsList(vendorType);
	}

	/**
	 * @return List of values attribute lov
	 * @throws Exception
	 */
	public List<String> getAttributeTypeLovData() throws Exception {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		return eleUtil.getDropDownOptionsList(attribute);
	}

	/**
	 * @return List of values site lov
	 * @throws Exception
	 */
	public List<String> getSiteLovData() throws Exception {
		CashDrawerSetUp csd = new CashDrawerSetUp(driver);
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		eleUtil.clickWhenReady(siteButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		csd.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(siteLov);
	}

	/**
	 * @return Site Lov text box class attributes
	 *
	 */
	public String getSiteLovClassAttributes() throws Exception {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		return eleUtil.doGetAttribute(siteLovTextBox, "class");
	}

	/**
	 * This method will click on Show In Result check box
	 *
	 */
	public VendorSelection clickOnShowInResultCheckBox() {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		eleUtil.clickWhenReady(showInResult, config.getExplicitWait());
		return this;
	}

	/**
	 * @return is ExternalVendorId and ExternalVendorLocation is displayed or not
	 *
	 */
	public boolean IsExternalVendorIdAndLocationDisplayed() {
		eleUtil.waitForElementPresence(externalVendorLocation, config.getExplicitWait());
		return eleUtil.isDisplayed(externalVendorId) && eleUtil.isDisplayed(externalVendorLocation);
	}

	/**
	 * @param vendoIDValue
	 *
	 */
	public VendorSelection enterDataInVendorID(String vendoIDValue) {
		// eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		eleUtil.doSendKeys(vendorId, vendoIDValue);
		return this;
	}

	/**
	 * @param vendoTypeValue
	 *
	 */
	public VendorSelection selectVendorType(String vendoTypeValue) {
		eleUtil.doSelectDropDownByVisibleText(vendorType, vendoTypeValue);
		return this;
	}

	/**
	 * @param attribute1
	 *
	 */
	public VendorSelection selectAttribute(String attribute1) {
		eleUtil.doSelectDropDownByVisibleText(attribute, attribute1);
		return this;
	}

	/**
	 * @param uCCIDValue
	 *
	 */
	public VendorSelection enterDataInUCCID(String uCCIDValue) {
		eleUtil.doSendKeys(uCCID, uCCIDValue);
		return this;
	}

	/**
	 * @param externalVenderIDValue
	 *
	 */
	public VendorSelection enterDataInExtVenIDTextBox(String externalVenderIDValue) {
		eleUtil.doSendKeys(extVenIDTextBox, externalVenderIDValue);
		return this;
	}

	/**
	 * @param externalVenderLocValue
	 *
	 */
	public VendorSelection enterDataInExtVenLocTextBox(String externalVenderLocValue) {
		eleUtil.doSendKeys(extVenLocTextBox, externalVenderLocValue);
		return this;
	}

	/**
	 * @param vendoIDValue,vendoTypeValue,attribute1,uCCIDValue,externalVenderIDValue,externalVenderLocValue
	 *
	 */
	public VendorSelection enterDataInVendorSelectionForm(String vendorIDValue, String vendoTypeValue,
			String attribute1, String uCCIDValue, String externalVenderIDValue, String externalVenderLocValue) {
		enterDataInVendorID(vendorIDValue);
		selectVendorType(vendoTypeValue);
		selectAttribute(attribute1);
		enterDataInUCCID(uCCIDValue);
		enterDataInExtVenIDTextBox(externalVenderIDValue);
		enterDataInExtVenLocTextBox(externalVenderLocValue);
		return this;
	}

	public boolean iSEmptyButtonFuncatnalityWorking() throws Exception {

		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
		Thread.sleep(5000);
		return eleUtil.readValueFromInput(vendorId).isEmpty() && eleUtil.readValueFromInput(vendorType).isEmpty()
				&& eleUtil.readValueFromInput(attribute).isEmpty() && eleUtil.readValueFromInput(uCCID).isEmpty()
				&& eleUtil.readValueFromInput(extVenIDTextBox).isEmpty()
				&& eleUtil.readValueFromInput(extVenLocTextBox).isEmpty();
	}

	public String verifyOKButton() throws Exception {
		eleUtil.clickWhenReady(searchButton, config.getExplicitWait());
		Thread.sleep(3000);
		eleUtil.clickWhenReady(okButton, config.getExplicitWait());
		Thread.sleep(3000);
		return eleUtil.doGetAttribute(vendorIdSKU, "value");
	}

	public String verifySearchButton() throws Exception {
		eleUtil.clickWhenReady(searchButton, config.getExplicitWait());
		Thread.sleep(3000);
		return eleUtil.doGetAttribute(searchButton, "class");
	}

	@Step("Returning search button attribute message")
	public String getSearchButtonAttrText() {
		return eleUtil.doGetAttribute(searchButton, "class");
	}

	public String getColoumDataInTable() {
		eleUtil.clickWhenReady(searchButton, config.getExplicitWait());
		return eleUtil.doGetText(table);
	}

	public String getDataFromTable() {
		return eleUtil.doGetText(table);
	}

	public VendorSelection selectPerPage(String value) {
		eleUtil.doSelectDropDownByVisibleText(rowPerPage, value);
		return this;
	}

	public List<String> getVendorIdFromTable(String value) {
		selectPerPage(value);
		eleUtil.clickWhenReady(searchButton, config.getExplicitWait());
		return eleUtil.getElementsTextList(table);
	}

	public String getVendorIDValue() {
		eleUtil.waitForFrameByLocator(config.getExplicitWait(), frame);
		return eleUtil.doGetAttribute(vendorId, "value");
	}

	@Step("Check if local radio button is checked or not ")
	public String isLocalRadioButtonChecked() {

		return eleUtil.doGetAttribute(localRadioButton, "checked");

	}

	@Step("Check if global radio button is checked or not ")
	public String isGlobalRadioButtonChecked() {

		return eleUtil.doGetAttribute(globalRadioButton, "checked");
	}

	@Step("Getting Criteria Alert PopUp Text, if user is not enter any keyword in search")
	public String getCriteriaAlertPopUpText() {
		return eleUtil.doGetText(criteriaAlertPopUp);
	}

	@Step("Clicking on the criteria alert ok button")
	public void clickOnAlertOkButton() {
		eleUtil.clickWhenReady(criteriaAlertOKButton, config.getExplicitWait());
	}

	@Step("Clicking on the criteria alert cancel button")
	public void clickOnAlertCancelButton() {
		eleUtil.clickWhenReady(criteriaAlertCancelButton, config.getExplicitWait());
	}

}
