package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Step;

public class SiteInformation extends TestBase {

	By address1 = By.cssSelector("input[id*='_ADR1']");
	By address2 = By.cssSelector("input[id*='ADR2']");
	By saveButton = By.cssSelector("button#SAVE");
	By popAlertMsg = By.id("paraId");
	By layawayInfoLocaleTab = By.xpath("//span[text()='Layaway Info/ Locale']//parent::a");
	By miscellaneousTab = By.xpath("//span[text()='Miscellaneous']//parent::a");
	By localLangButton = By.cssSelector("button[id*='_LOCALE_LANG_lov_btn']");
	By trailerMessageButton = By.cssSelector("button[id*='TRAILER_MESSAGE_CD_lov_btn']");
	By localLangList = By.xpath("//ul[@role='listbox']//li");
	By localCountryButton = By.xpath("//button[contains(@id,'LOCALE_COUNTRY_lov_btn')]");
	By lookUp = By.cssSelector("Span>button#itmlov");
	By searchZip = By.xpath("//input[@aria-label='Search']");
	By searchZipButton = By.xpath("//button[@aria-label='Search']");
	By noResultFoundMessage = By.xpath("//span[text()='No results found.']");
	By zipCodeTable=By.xpath("//table//tr//td[4]");
	By cityInput=By.xpath("//input[contains(@id,'_CITY') and @type='text']");
	By stateInput=By.xpath("//input[contains(@id,'_STATE') and @type='text']");
	By zipInput=By.xpath("//div[contains(@id,'ZIP_CONTAINER')]//input[contains(@id,'_ZIP') and @type='text']");
	By countryInput=By.xpath("//div[contains(@id,'P44_COUNTRY_CONTAINER')]//input[contains(@id,'_COUNTRY')]");
	By configurationbtn=By.id("Configurationbtn");
	By siteGroupBtn=By.xpath("//span[text()='Site Group']//parent::button");
	By popAlertWindow=By.id("paraId");
	By lowGM=By.cssSelector("input[id*=_LALO_GM]");
	By highGM=By.cssSelector("input[id*=_HALO_GM]");
	By site_Attribute=By.id("Site_Attribute");
	By site_Attr_AddRow=By.cssSelector("button#site_attr_ig_ig_toolbar_ad_rw");
	By attributeIDLovButton=By.xpath("//button[@id='ATTRIBUTE_ID_lov_btn']");
	By attributeNameColumn=By.xpath("//div[contains(@id,'_ATTRIBUTE_ID_dlg')]//table//tbody//tr//td[1]");
	By attributeValueLoveButton=By.cssSelector("button#ATTRIBUTE_VALUE_lov_btn");
	By attributeValueCoulmn=By.xpath("//div[contains(@id,'_ATTRIBUTE_VALUE_dlg')]//table//tbody//tr//td[1]");
	By siteAttrDelete=By.id("site_attr_ig_ig_toolbar_custdelete");
	By siteAttrSave=By.xpath("//span[text()='Save']//parent::button[@data-action='save']");
	By siteAttrSearch=By.id("site_attr_ig_ig_toolbar_search_field");
	By siteAttrGoButton=By.xpath("//span[text()='Go']//parent::button");
	

	public SiteInformation() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		menuSelection = new MenuSelection(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
		generalSiteConfiguration= new GeneralSiteConfiguration();
		siteInGroups= new SiteInGroups();
	}

	@Step("Get Address1 and Adress 2 validation messsage")
	public String getReqAddressValMessage() {
		eleUtil.doClearTextbox(address1);
		eleUtil.doClearTextbox(address2);
		eleUtil.clickWhenReady(address1, config.getExplicitWait());
		clickOnSaveButton();
		return getAlertPopUiMsg();
	}
	@Step("Change data in adress1 1 text box")
	public SiteInformation changeAdress1Data(String add) {
		eleUtil.doClearTextbox(address1);
		eleUtil.doSendKey(address1, add);
		return this;
	}
	
	@Step("Change data in adress1 1 text box")
	public String getAdress1Data() {
		return eleUtil.doGetAttribute(address1, "value");
	}

	@Step("Click on the save button")
	public SiteInformation clickOnSaveButton() {
		eleUtil.clickWhenReady(saveButton, config.getExplicitWait());
		return this;
	}

	@Step("Get Alert pop message")
	public String getAlertPopUiMsg() {
		return eleUtil.doGetText(popAlertMsg);
	}

	@Step("click on Layaway Info Locale tab")
	public SiteInformation moveToLayawayInfoLocale() {
		eleUtil.clickWhenReady(layawayInfoLocaleTab, config.getExplicitWait());
		return this;
	}

	@Step("click on Layaway Info Locale tab")
	public SiteInformation moveToMiscellaneousTab() {
		eleUtil.clickWhenReady(miscellaneousTab, config.getExplicitWait());
		return this;
	}

	@Step("Getting local language list from UI")
	public List<String> getLocalLangListFromUI() throws Exception {
		List<String> LocalLangValue = new ArrayList<>();
		moveToLayawayInfoLocale();
		eleUtil.clickWhenReady(localLangButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> UILocalLang = eleUtil.getElementsTextList(localLangList);
		for (String localLang : UILocalLang) {
			String lang = localLang.replaceAll("[-, ,()]", "");
			// System.out.println(lang);
			LocalLangValue.add(lang);
		}
		return LocalLangValue;
	}

	@Step("Getting local Country list from UI")
	public List<String> getLocalCountryListFromUI() throws Exception {
		List<String> LocalCountryValue = new ArrayList<>();
		moveToLayawayInfoLocale();
		eleUtil.clickWhenReady(localCountryButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> uILocalCountry = eleUtil.getElementsTextList(localLangList);
		for (String localCountry : uILocalCountry) {
			String lang = localCountry.replaceAll("[-, ,()]", "");
			// System.out.println(lang);
			LocalCountryValue.add(lang);
		}
		return LocalCountryValue;
	}

	@Step("Getting trailer list from UI")
	public List<String> getTrailermessageListFromUI() throws Exception {
		List<String> LocalCountryValue = new ArrayList<>();
		moveToMiscellaneousTab();
		eleUtil.clickWhenReady(trailerMessageButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> uILocalCountry = eleUtil.getElementsTextList(localLangList);
		for (String localCountry : uILocalCountry) {
			String lang = localCountry.replaceAll("[-, ,()]", "");
			// System.out.println(lang);
			LocalCountryValue.add(lang);
		}
		return LocalCountryValue;
	}

	@Step("Getting local language list from database")
	public List<String> getLocalListFromDB(String query, String columnName1, String columnName2) throws Exception {
		List<String> valueList = new ArrayList<>();
		// CODE_VALUE
		// CODE_MEANING
		List<String> columnName1List = Utility.getDataFromDatabase(query, columnName1);
		List<String> columnName2List = Utility.getDataFromDatabase(query, columnName2);

		for (int i = 0; i < columnName1List.size(); i++) {
			String column1Value = columnName1List.get(i).trim().replaceAll("[  ,(),-]", "");
			for (int j = i; j <= i; j++) {
				String column2Value = columnName2List.get(j).trim().replaceAll("[  ,(),-]", "");
				String Value = column1Value + column2Value;
				// System.out.println(langValue);
				valueList.add(Value);
			}
		}
		return valueList;
	}

	@Step("Click on lookUp button and zip code list should show")
	public SiteInformation clickOnLookUpButton() throws Exception {
		boolean sts = eleUtil.ISclickable(lookUp,config.getExplicitWait());
		System.out.println(sts);
		if (sts==true) {
			eleUtil.clickWhenReady(lookUp, config.getExplicitWait());
			test.log(LogStatus.INFO, "Click on lookup button");
		} else {
			Utility.executeandcommit("declare \r\n" + "p_flag Varchar2(1);\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.set_flag('VALIDATE_ZIP','Y');\r\n" + "end;");
			test.log(LogStatus.INFO, "LookUp value reseted to Y");
			eleUtil.pageRefresh();
			eleUtil.clickWhenReady(lookUp, config.getExplicitWait());
			test.log(LogStatus.INFO, "Click on lookup button");
		}
		return this;
	}

	@Step("Enter data zip code search input box and click on search icon")
	public SiteInformation enterDataInSearchZipCodeInputBox(String zipValue) {
		eleUtil.doSendKeysWithWait(searchZip, config.getExplicitWait(), zipValue);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(searchZipButton, config.getExplicitWait());
		return this;
	}

	public boolean isNoResultMessageDisplayed() {
		return eleUtil.isDisplayed(noResultFoundMessage);
	}
	@Step("Get data from zip row from table")
	public String getTextFromZipTable() {
		return eleUtil.doGetText(zipCodeTable);
	}
	
	public String getCityClassAttribute() {
		return eleUtil.doGetAttribute(cityInput, "class");	
	}
	public String getStateClassAttribute() {
		return eleUtil.doGetAttribute(stateInput, "class");	
	}
	public String getZipClassAttribute() {
		return eleUtil.doGetAttribute(zipInput, "class");	
	}
	public String getCountryClassAttribute() {
		return eleUtil.doGetAttribute(countryInput, "class");	
	}
	@Step("Click on the Configuration Button")
	public SiteInformation clickOnConfigurationButton() {
		eleUtil.clickWhenReady(configurationbtn, config.getExplicitWait());
		return this;
	}
	@Step("Click Configurationbtn and verify that user on configuration page")
	public String getGeneralSiteConfigurationTitle(String title) {
		clickOnConfigurationButton();
		return eleUtil.doGetPageTitleIs(title, config.getExplicitWait());
	}
	
	@Step("Click on the site group Button")
	public SiteInformation clickOnSiteGroupButton() {
		eleUtil.clickWhenReady(siteGroupBtn, config.getExplicitWait());
		return this;
	}
	
	public String getAlertPopUpText() {
		return eleUtil.doGetText(popAlertWindow);
	}
	@Step("")
	public String verifyExitButton(int num) {
		String actAdress = getAdress1Data();
		test.log(LogStatus.INFO, "Adress1 value is " + actAdress);
		String newAdress=actAdress+num;
		test.log(LogStatus.INFO, "Adress1 value is " + newAdress);
		changeAdress1Data(newAdress);
		generalSiteConfiguration.clickOnExitButton();
		String winText=getAlertPopUpText();
		test.log(LogStatus.INFO, winText);
		boolean sts=siteInGroups.IsYesCancelAndNoButtonPresentOnClearPopWindow();
		Assert.assertTrue(sts);
		return actAdress;
	}
	@Step("Get save button class attributes")
	public String getSaveButtonClassAttr() {
		eleUtil.doClick(address2);
		return eleUtil.doGetAttribute(saveButton, "class");
	}
	@Step("Get low gm validation message from Ui")
	public String getLaloGmValMessage() {
		moveToMiscellaneousTab();
		eleUtil.doSendKeys(highGM,"8");
		eleUtil.doClearTextbox(lowGM);
		eleUtil.doSendKeys(lowGM,"12");
		eleUtil.doClick(highGM);
		return getAlertPopUiMsg();
	}
	
	public void clickOnSiteAttributeButton() {
		eleUtil.clickWhenReady(site_Attribute, config.getExplicitWait());
	}
	
	@Step("move to attribute screen and get attribute name in a list")
	public List<String> getAtrributeNameFromAtrributeScreen() throws Exception {
		clickOnSiteAttributeButton();
		eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
		By attrCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1]");
		int count=eleUtil.getElementCount(attrCloumn);
		By newAttrColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1])["+count+"]");
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionMoveClick(newAttrColumn);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(attributeIDLovButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(attributeNameColumn);
		
	}
	
	@Step("move to attribute screen and get attribute value in a list")
	public List<String> getAtrributeValueFromAtrributeScreen() throws Exception {
		clickOnSiteAttributeButton();
		eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
		By attrCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1]");
		int count=eleUtil.getElementCount(attrCloumn);
		By newAttrColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1])["+count+"]");
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionMoveClick(newAttrColumn);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(attributeIDLovButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.clickOnLink(attributeNameColumn, "DISTRIBUTION_CENTER_FL");
		By attrValueCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[2]");
		int valueCount=eleUtil.getElementCount(attrValueCloumn);
		By newAttrValueColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[2])["+valueCount+"]");
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionMoveClick(newAttrValueColumn);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(attributeValueLoveButton, config.getExplicitWait());
		//cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(attributeValueCoulmn);
		
	}
	@Step("This will return duplicate alert message on attribute screen")
	public String getDuplicateAttributeMessage() throws Exception {
		clickOnSiteAttributeButton();
		eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
		By attrCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1]");
		int count=eleUtil.getElementCount(attrCloumn);
		String firstValue=eleUtil.getElementsTextList(attrCloumn).get(0);
		By newAttrColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1])["+count+"]");
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionMoveClick(newAttrColumn);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(attributeIDLovButton, config.getExplicitWait());
		//cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(attributeNameColumn, firstValue);
		eleUtil.doStaticWait(config.getMediumWait());
		return getAlertPopUiMsg();
	}
	@Step("This will return blank row alert message on attribute screen")
	public String getAddRowBlankMessage() {
		clickOnSiteAttributeButton();
		eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
		eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
		return getAlertPopUiMsg();
	}
	
	public boolean verifySaveAndDeleteOnSiteAttributeScreen() throws Exception {
			clickOnSiteAttributeButton();
			By attrCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1]");
			eleUtil.clickOnLink(attrCloumn,"ORACLE_PAY_GROUP");
			eleUtil.clickWhenReady(siteAttrDelete, config.getExplicitWait());
			eleUtil.clickWhenReady(siteAttrSave, config.getExplicitWait());
			eleUtil.clickWhenReady(site_Attr_AddRow, config.getExplicitWait());
			eleUtil.doStaticWait(config.getSmallWait());
			int count=eleUtil.getElementCount(attrCloumn);
			By newAttrColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[1])["+count+"]");
			eleUtil.doStaticWait(config.getSmallWait());
			eleUtil.doActionMoveClick(newAttrColumn);
			eleUtil.doStaticWait(config.getSmallWait());
			eleUtil.clickWhenReady(attributeIDLovButton, config.getExplicitWait());
			cashDrawerSetup.clickOnLoadMoreRowsButton();
			eleUtil.clickOnLink(attributeNameColumn, "ORACLE_PAY_GROUP");
			By attrValueCloumn=By.xpath("//div[@id='site_attr_ig_ig_content_container']//table//tr//td[2]");
			int valueCount=eleUtil.getElementCount(attrValueCloumn);
			By newAttrValueColumn=By.xpath("(//div[@id='site_attr_ig_ig_content_container']//table//tr//td[2])["+valueCount+"]");
			eleUtil.doStaticWait(config.getSmallWait());
			eleUtil.doActionMoveClick(newAttrValueColumn);
			eleUtil.doStaticWait(config.getSmallWait());
			eleUtil.clickWhenReady(attributeValueLoveButton, config.getExplicitWait());
			eleUtil.clickOnLink(attributeValueCoulmn,"CORPORATE");
			eleUtil.clickWhenReady(siteAttrSave, config.getExplicitWait());
			verifySearchOnSiteAttrScreen("ORACLE_PAY_GROUP");
			eleUtil.getElementsTextList(attrCloumn).get(0);
			if(eleUtil.getElementsTextList(attrCloumn).get(0).equalsIgnoreCase("ORACLE_PAY_GROUP")) {
				return true;
			}else {
				return false;
			}
	}
	
	public SiteInformation verifySearchOnSiteAttrScreen(String text) {
		eleUtil.doSendKeysWithWait(siteAttrSearch, config.getExplicitWait(), text);
		eleUtil.doClickWithWait(siteAttrGoButton, config.getExplicitWait());
		return this;
	}
}
