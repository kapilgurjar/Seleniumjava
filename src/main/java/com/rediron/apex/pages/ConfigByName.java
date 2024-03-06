package com.rediron.apex.pages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Step;

public class ConfigByName extends TestBase {
	
	By applyAllButton=By.xpath("//span[text()=' Apply All']//parent::button");
	By applyAllNumbers=By.xpath("(//span[contains(text(),'Apply All')]//parent::button)[3]");
	By applyAllText=By.xpath("(//span[contains(text(),'Apply All')]//parent::button)[4]");
	By dateInput=By.cssSelector("input[aria-describedby=P26_DATE_FIELD_format_help]");
	By flag1 = By.xpath("(//div[@id='Flag_Configurations_ir_ig_grid_vc']//table//tr//td[3]//div//span[2])[1]");
	By flag2 = By.xpath(
			"/html[1]/body[1]/form[1]/div[1]/div[2]/div[2]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[3]/div[3]/div[1]/div[2]/div[4]/table[1]/tbody[1]/tr[1]/td[3]/div[1]/span[1]");
	By text = By.xpath("//span[text()='Text']//parent::a");
	By dates = By.xpath("//span[text()='Dates']//parent::a");
	By numbers = By.xpath("//span[text()='Numbers']//parent::a");
	By search = By.id("Text_configurations_desc_ig_toolbar_search_field");
	By flagSearch = By.id("Flag_configurations_desc_ig_toolbar_search_field");
	By goButtonFlag = By.xpath("//div[@id='Flag_configurations_desc_ig_toolbar']//span[text()='Go']//parent::button");
	By saveButton = By.cssSelector("div>button[id='save_button']");
	By clearButton=By.xpath("//span[text()='Clear']//parent::button");
	By exitButton=By.xpath("//span[text()='Exit']//parent::button");
	By homeLink = By.xpath("//a[text()='Home']");
	By flag_CheckBox = By.id("Flag_Configurations_ir_ig_grid_vc_cur");
	By table_FlagName = By.xpath("//div[@id='Flag_configurations_desc_ig_content_container']//table//tr//td[1]");
	By table_FlagDesc = By.xpath("//div[@id='Flag_configurations_desc_ig_content_container']//table//tr//td[2]");
	By table_DateName = By.xpath("//div[@id='Date_configuration_desc_ig_grid_vc']//table//tr//td[1]");
	By table_DateDes = By.xpath("//div[@id='Date_configuration_desc_ig_grid_vc']//table//tr//td[2]");
	By table_NumberName = By.xpath("//div[@id='Number_configurations_desc_ig_grid_vc']//table//tr//td[1]");
	By table_NumberDesc = By.xpath("//div[@id='Number_configurations_desc_ig_grid_vc']//table//tr//td[2]");
	By table_TextName = By.xpath("//div[@id='Text_configurations_desc_ig']//table//tr//td[1]");
	By table_TextDesc = By.xpath("//div[@id='Text_configurations_desc_ig']//table//tr//td[2]");
	By pageSelectorFlag = By
			.xpath("//div[@id='Flag_configurations_desc_ig_toolbar']//Select[@aria-label='Rows Per Page']");
	By pageSelectorDate = By
			.xpath("//div[@id='Date_configuration_desc_ig_toolbar']//Select[@aria-label='Rows Per Page']");
	By pageSelectorNumbers = By
			.xpath("//div[@id='Number_configurations_desc_ig_toolbar']//Select[@aria-label='Rows Per Page']");
	By pageSelectorText = By.xpath("//div[@id='Text_configurations_desc_ig']//Select[@aria-label='Rows Per Page']");
	By nextButton = By.xpath("(//button[@title='Next'])[1]");
	By dateNextButton = By.xpath("//div[@id='Date_configuration_desc_ig_grid_vc']//button[@title='Next']");
	By numbersNextButton = By.xpath("//div[@id='Number_configurations_desc_ig_grid_vc']//button[@title='Next']");
	By textNextButton = By.xpath("//div[@id='Text_configurations_desc_ig']//button[@title='Next']");
	By removeButton = By.xpath("//button[@title='Remove Filter']");
	By siteGroupTable = By.xpath("//span[text()='List of Sites/Group']//following::table//tr//td");
	By siteGroupLov = By.id("P26_SITE_GROUP_LOV_lov_btn");
	By popUPWindowText = By.xpath("//div[contains(@id,'ui-id') or @id='saveConfigdialog']//p");
	By noButton=By.xpath("//button[text()='No']");
	By successMessage= By.xpath("//div[@id='t_Alert_Success']//h2");
	By yesButton=By.xpath("//button[text()='Yes']");
	By dateText=By.id("(//div[@id='Date_Configurations_ir_ig_grid_vc']//table//tr//td[3])[1]");
	By numberInput=By.cssSelector("input#P26_NUMBER_FIELD");
	By textInput=By.cssSelector("input#P26_TEXT_FIELD");
	By configurationsFlagGroup=By.xpath("//div[@id='Flag_Configurations_ir_ig_grid_vc']//table//tr//td[1]");
	By configurationsNextButton=By.xpath("//div[@id='Flag_Configurations_ir_ig_grid_vc']//button[@title='Next']");

	public ConfigByName() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	@Step("Click on text header")
	public ConfigByName clickOnText() {
		eleUtil.clickWhenReady(text, config.getExplicitWait());
		return this;
	}

	@Step("Click on Dates header")
	public ConfigByName clickOnDate() {
		eleUtil.clickWhenReady(dates, config.getExplicitWait());
		return this;
	}

	@Step("Click on Numbers header")
	public ConfigByName clickOnNumberTab() {
		eleUtil.clickWhenReady(numbers, config.getExplicitWait());
		return this;
	}

	public ConfigByName searchByText(String flagValue) {
		eleUtil.doSendKeys(flagSearch, flagValue);
		eleUtil.clickWhenReady(goButtonFlag, config.getExplicitWait());
		return this;
	}

	public ConfigByName searchByFlag(String flagValue) {
		eleUtil.doSendKeys(flagSearch, flagValue);
		eleUtil.clickWhenReady(goButtonFlag, config.getExplicitWait());
		return this;
	}

	public String getFlagDescriptionText() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doGetText(table_FlagDesc);
	}

	public void clickOnRemoveFilter() {
		eleUtil.clickWhenReady(removeButton, config.getExplicitWait());
	}

	@Step("Clicking on the home page")
	public void clickOnHomeButton() {
		eleUtil.clickWhenReady(homeLink, config.getExplicitWait());
	}

	@Step("Clicking on the save  button on Config By Name Screen")
	public void clickOnSaveButton() {
		eleUtil.clickWhenReady(saveButton, config.getExplicitWait());
	}

	@Step("Setting the flag value")
	public ConfigByName selectFlagValueForASite(String siteNo, String flagValue) throws Exception {
		searchByFlag(flagValue);
		By xpath = By.xpath("//td[text()='"+siteNo+"']//following::td[2]//div//span[1]");
		eleUtil.doClickWithVisibleElement(xpath,config.getExplicitWait());
		clickOnSaveButton();
		return this;
	}
	
	@Step("getting the flag value")
	public String getFlagValueForASite(String siteNo, String flagValue) throws Exception {
		searchByFlag(flagValue);
		By xpath = By.xpath("//td[text()='"+siteNo+"']//following::td[2]//div//span[2]");
		String text=eleUtil.getElementTextWithVisibleElement(xpath, config.getExplicitWait());
		return text;
	}

	@Step("Checking that save button is displayed or not")
	public boolean isSaveButtonDisplayed() {
		return eleUtil.isDisplayed(saveButton);
	}

	@Step("Getting check box class attributes to verify that check box is a readonly")
	public String getCheckBoxClassAttr() {
		return eleUtil.doGetAttribute(flag_CheckBox, "class");
	}

	@Step("Getting flag names and description from UI in key and value pair")
	public HashMap<String, String> getFlagTableDataUI() throws Exception {
		basePage = new BasePage();

		return basePage.getConfigByNameFlagDatesNumberAndTextData(pageSelectorFlag, table_FlagName, nextButton,
				table_FlagDesc);
	}

	@Step("Getting date names and description from UI in key and value pair")
	public HashMap<String, String> getDateTableDataUI() throws Exception {
		clickOnDate();
		basePage = new BasePage();
		return basePage.getConfigByNameFlagDatesNumberAndTextData(pageSelectorDate, table_DateName, dateNextButton,
				table_DateDes);
	}

	@Step("Getting Numbers names and description from UI in key and value pair")
	public HashMap<String, String> getNumberTableDataUI() throws Exception {
		clickOnNumberTab();
		basePage = new BasePage();
		return basePage.getConfigByNameFlagDatesNumberAndTextData(pageSelectorNumbers, table_NumberName,
				numbersNextButton, table_NumberDesc);

	}

	@Step("Getting text names and description from UI in key and value pair")
	public HashMap<String, String> getTextTabTableDataUI() throws Exception {
		clickOnText();
		basePage = new BasePage();
		return basePage.getConfigByNameFlagDatesNumberAndTextData(pageSelectorText, table_TextName, textNextButton,
				table_TextDesc);

	}

	public HashMap<String, String> getFlagTableDataDB(String query, String columnName1, String columnName2)
			throws Exception {
		HashMap<String, String> nameDes = new HashMap<>();
		List<String> names = Utility.getDataFromDatabase(query, columnName1);
		Collections.sort(names);
		List<String> descriptions = Utility.getDataFromDatabase(query, columnName2);
		System.out.println(descriptions.size());
		Collections.sort(descriptions);
		String name = "";
		String des = "";
		for (int i = 0; i < names.size(); i++) {
			name = names.get(i).trim().replaceAll("\\s", "");
			for (int j = 0; j <= i; j++) {
				des = descriptions.get(j).trim().replaceAll("\\s", "");
				nameDes.put(name, des);
			}
		}
		return nameDes;
	}

	public List<String> getSiteGroupValueDB(String query, String columnName, String coulmnName1) throws SQLException {
		List<String> siteGroupList = new ArrayList<>();
		List<String> group_ID = Utility.getDataFromDatabase(query, columnName);
		List<String> description = Utility.getDataFromDatabase(query, coulmnName1);
		for (int i = 0; i < group_ID.size(); i++) {
			String groupId = group_ID.get(i);
			for (int j = 0; j <= i; j++) {
				String desc = description.get(j);
				String siteGroup = groupId + "-" + desc;
				System.out.println("siteGroup is equal = " + siteGroup);
				siteGroupList.add(siteGroup);
			}
		}
		return siteGroupList;
	}

	@Step("Getting all value from site/Group lov")
	public List<String> getSiteGroupListOfValues() throws Exception {
		eleUtil.clickWhenReady(siteGroupLov, config.getExplicitWait());
		CashDrawerSetUp csd = new CashDrawerSetUp(driver);
		csd.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(siteGroupTable);
	}
	
	@Step("Getting all value from site/Group lov")
	public ConfigByName selectSiteGroupFromLov(String siteGroup) throws Exception {
		eleUtil.clickWhenReady(siteGroupLov, config.getExplicitWait());
		CashDrawerSetUp csd = new CashDrawerSetUp(driver);
		csd.clickOnLoadMoreRowsButton();
		eleUtil.clickOnLink(siteGroupTable, siteGroup);
		return this;
	}
	@Step("This method will return save congiguration message if "
			+ " user clicked on flag check box and then click on some other flag")
	public String getSaveConfigurationPopUpMessage() {
		eleUtil.clickOnLink(table_FlagName, "24HR_CASHIER");
		String flagValue=eleUtil.waitForElementToBeVisibleWithFluentWait(flag1,config.getExplicitWait(),2).getText();
		System.out.println("flag Value is "+flagValue);
		if(flagValue.equalsIgnoreCase("Checked")) {
			eleUtil.doClickWithVisibleElement(flag2, 60);
			System.out.println("element is unchecked");
		}
		//eleUtil.clickOnLink(table_FlagName, "24HR_CASHIER");
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doClickWithVisibleElement(flag2, 60);
		eleUtil.clickOnLink(table_FlagName, "ACCT_ON_LAYAWAY");
		return eleUtil.getElementTextWithWait(popUPWindowText,config.getExplicitWait());
	}
	
	public boolean clickOnNoButtonAndVerifyCheckBox() {
		eleUtil.clickOnLink(table_FlagName, "24HR_CASHIER");
		String flagTextBefore= eleUtil.waitForElementToBeVisibleWithFluentWait(flag1,config.getExplicitWait(),2).getText();
		eleUtil.doClickWithVisibleElement(flag2, 60);
		System.out.println("Flag after text is equal to"+flagTextBefore);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(table_FlagName, "ACCT_ON_LAYAWAY");
		eleUtil.doClick(noButton);
		eleUtil.clickOnLink(table_FlagName, "24HR_CASHIER");
		String flagTextAfter= eleUtil.waitForElementToBeVisibleWithFluentWait(flag1,config.getExplicitWait(),2).getText();
		System.out.println("Flag after text is equal to " +flagTextAfter);
		if(flagTextBefore.equalsIgnoreCase(flagTextAfter)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String clickOnYesButtonAndVerifyCheckBox() {
			eleUtil.doClick(yesButton);
			eleUtil.clickOnLink(table_FlagName, "24HR_CASHIER");
			String flagText= eleUtil.waitForElementToBeVisibleWithFluentWait(flag1,config.getExplicitWait(),2).getText();
			System.out.println("Flag is equal to " +flagText);
			return flagText;
	}
	
	public ConfigByName clickOnApplyAllButton() {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(applyAllButton, config.getExplicitWait());
		return this;
	}
	
	public ConfigByName enterDataInDateInputText(String date) {
		eleUtil.doClearTextbox(dateInput);
		eleUtil.doSendKeysWithWait(dateInput, config.getExplicitWait(), date);
		return this;
	}
	
	@Step("This method will return save congiguration message if "
			+ " user entered date and click on apply alll")
	public String getSaveDateConfigurationPopUpMessage(String name1, String name2) {
		eleUtil.clickOnLink(table_DateName, name1);;
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(table_DateName, name2);
		return eleUtil.getElementTextWithWait(popUPWindowText,config.getExplicitWait());
	}
	
	public ConfigByName clickOnYesButton() {
		eleUtil.doClickWithVisibleElement(yesButton, config.getExplicitWait());
		return this;
	}
	
	@Step("This method will return save congiguration message if "
			+ " user entered date and click on apply alll")
	public String getSaveNumberConfigurationPopUpMessage(String name1, String name2) {
		eleUtil.clickOnLink(table_NumberName, name1);;
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(table_NumberName, name2);
		return eleUtil.getElementTextWithWait(popUPWindowText,config.getExplicitWait());
	}
	
	public ConfigByName enetrDataInNumberInput(String number) {
		eleUtil.doSendKeysWithWait(numberInput, config.getExplicitWait(), number);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(applyAllNumbers, config.getExplicitWait());
		return this;
	}
	
	@Step("This method will return save congiguration message if "
			+ " user entered date and click on apply alll")
	public String getSaveTextConfigurationPopUpMessage(String name1, String name2) {
		eleUtil.clickOnLink(table_TextName, name1);;
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickOnLink(table_TextName, name2);
		return eleUtil.getElementTextWithWait(popUPWindowText,config.getExplicitWait());
	}
	
	public ConfigByName enetrDataTextInput(String number) {
		eleUtil.doSendKeysWithWait(textInput, config.getExplicitWait(), number);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(applyAllText, config.getExplicitWait());
		return this;
	}
	
	public int getNoOfSiteInASiteGroup() throws Exception {
		basePage = new BasePage() ;
		return basePage.getTableDataAndClickOnNextButtonIfEnable(configurationsFlagGroup, configurationsNextButton).size();	
	}
	
	public boolean isExitButtonWorking() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
		menuSelection = new MenuSelection(driver);
		return eleUtil.isDisplayed(menuSelection.expandAllButton);
	}
	
	public String getWindowPopWarningMessage() {
		return eleUtil.getElementTextWithWait(popUPWindowText,config.getExplicitWait());
	}
}
