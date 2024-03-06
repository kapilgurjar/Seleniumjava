package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Step;

public class EntityOwnership extends TestBase {
	
	By clearButton=By.xpath("//span[text()='Clear']//parent::button");
	By exitButton=By.xpath("//span[text()='Exit']//parent::button");
	By windowPopUp = By.cssSelector("div#showAlert>p");
	By OkButton = By.cssSelector("button#OK");
	By totalSiteCount = By.cssSelector("span[id*='TOT_SITE_CNT_DISPLAY']");
	By affSite = By.cssSelector("span[id*='AFF_SITE_CNT_DISPLAY']");
	By corpHQ = By.cssSelector("span[id*='CORP_SITE_CNT_DISPLAY']");
	By viewCorporateSiteButton = By.xpath("//button[@title='View Corporate Site']");
	By currencyCode = By.cssSelector("Select[id*='_CURRENCY_CD']");
	By totalRows = By.xpath("//tr//td");
	By entityOwner = By.xpath("//input[contains(@id,'_OWNER_NAME')]");
	By entityID = By.xpath("//input[contains(@id,'_OWNER_ID')]");
	By entityName = By.xpath("//input[contains(@id,'_DESCRIPTION')]");
	By hQSite = By.xpath("//input[contains(@id,'_HQ_SITE_NO') and @type='text']");
	By hQSiteLov = By.cssSelector("button[id*='HQ_SITE_NO_lov_btn']");
	By extRefID = By.xpath("//input[contains(@id,'_EXT_REF_ID') and @type='text']");
	By numOfSites = By.xpath("//input[contains(@id,'SITE_CNT') and @type='text']");
	By corpSaveButton = By.cssSelector("button#SAVE_DTL");
	By noOfSitesCorHQ = By.cssSelector("input#P3175_SITE_CNT");
	By tableSiteNo = By.xpath("//div[@id='ENTITY_SITE_data_panel']//table//td[1]");
	By rowsSelect = By.cssSelector("select#ENTITY_SITE_row_select");
	By corpHQSearch = By.id("ENTITY_SITE_search_field");
	By corpSiteTable = By.xpath("//div[@id='ENTITY_SITE_data_panel']//table//tr//td");
	By corpSiteTableHeader = By.xpath("//div[@id='ENTITY_SITE_data_panel']//table//a");
	By goButton = By.id("ENTITY_SITE_search_button");
	By cancelButton = By.xpath("//span[text()='Cancel']//parent::button");
	By corpWinOkButton = By.xpath("//button[text()='OK']");
	By entityOwnershipTable = By.xpath("//table//tr[1]//td");
	By viewSelectedEntity = By.id("AE_ig_toolbar_VIEW_SELECTED_ENTITY_ID");
	By entityFirstName = By.xpath("//table//tr[1]//td[2]");
	By entityHQSite = By.xpath("//table//tr[1]//td[6]");
	By entOwnTable = By.xpath("//div[@id='AE_ig_content_container']//table//tr//td[2]");
	By saveButton = By.cssSelector("button#SAVE");
	By entityOwneShipexpandButton = By.xpath("//button[contains(@aria-labelledby,'_heading')]");
	By nextButton = By.xpath("//li//button[@aria-label='Next']");
	By extRefFirstData = By.xpath("//span[text()='Ext Ref Id']//following::table//tr[1]//td[4]");
	By createNewEntityButton = By.cssSelector("button[title='Create Entity']");
	By crNewEntityName = By.xpath("//input[contains(@id,'_ENTITY_NAME')]");
	By crNewEntOwner = By.xpath("//input[contains(@id,'_ENTITY_ONWER')]");
	By crNewEntExtRefID = By.xpath("//input[contains(@id,'_EXTN_REF_ID')]");
	By crNewEntCurCode = By.cssSelector("Select[id*='_CURRENCY_CODE']");
	By saveEntity = By.xpath("//span[text()='Save']//parent::button[@id='SAVE_ENTITY']");
	By cancelEntity = By.xpath("//td//child::span[text()='Cancel']//parent::button");
	By entityNameErrorMessage = By.cssSelector("div[id*='_ENTITY_NAME_error']");
	By entityOwnerErrorMessage = By.cssSelector("div[id*='_ENTITY_ONWER_error']");
	By pageSelector=By.xpath("//select[@title='Rows Per Page']");

	// Class constructor
	public EntityOwnership() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		basePage = new BasePage();
	}

	
	
	@Step("Clicking on clear button on main screen")
	public EntityOwnership clickOnClearButton() {
		
		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
		return this;
	}
	
	@Step("Clicking on exit button on main screen")
	public boolean clickOnExitButton() {
		
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
		MenuSelection mn = new MenuSelection(driver);
		return mn.IsExpanButtonDisplayed();
	}
	
	@Step("This method will return popup window alert text message")
	public String getWindowPopText() {
		return eleUtil.doGetText(windowPopUp);
		
	}

	@Step("Verify that user is on menu selection page or not")
	public boolean IsUserOnMenuPage() {
		MenuSelection mn = new MenuSelection(driver);
		eleUtil.doClick(OkButton);
		return mn.IsExpanButtonDisplayed();
	}

	@Step("This method will return total no of site")
	public String getTotalSiteCount() {

		return eleUtil.doGetText(totalSiteCount);
	}

	@Step("This method will return total no of affilated site")
	public String getTotalAffSiteCount() {

		return eleUtil.doGetText(affSite);
	}

	@Step("This method will return total no of corp site")
	public String getTotalCorpSiteCount() {

		return eleUtil.doGetText(corpHQ);
	}

	@Step("This method will click on view corporate site button")
	public EntityOwnership clickOnViewCorporateSiteButton() {
		eleUtil.clickWhenReady(viewCorporateSiteButton, config.getExplicitWait());
		// eleUtil.clickWhenReady(entityOwneShipexpandButton, config.getExplicitWait());
		return this;
	}

	@Step("This will return currency code value from view corp hq site")
	public List<String> getCurrencyCode() throws Exception {
		Thread.sleep(3000);
		return eleUtil.getDropDownOptionsList(currencyCode);
	}

	@Step("This method will verify that row data is not editable")
	public boolean verifyRowsDataAreReadOnly() {

		List<String> classAttr = eleUtil.getElementsAttributeList(totalRows, "class");

		for (String attr : classAttr) {
			// readonly
			if (attr.contains("readonly")) {
				return true;
			}
		}
		return false;
	}

	@Step("This method will return entity id from view corp site ")
	public String getEntityIdCorpDetails() throws Exception {
		Thread.sleep(2000);
		return eleUtil.doGetAttribute(entityID, "value");
	}

	@Step("This method will verify that entity id is not editable text filed on view corp site ")
	public boolean IsEntityIDReadOnly() throws Exception {
		String text = eleUtil.doGetAttribute(entityID, "class");
		if (text.contains("disabled")) {
			return true;
		}
		return false;
	}

	@Step("This method will prove true if entity owner is editable otherwise it will return false")
	public boolean IsEntityOwnerEditable() throws Exception {
		String rn = Utility.getRandomString();
		Thread.sleep(2000);
		String beforeText = eleUtil.doGetAttribute(entityOwner, "value");
		test.log(LogStatus.INFO, "before edit text is " + beforeText);
		eleUtil.doActionMoveClick(entityOwner);
		eleUtil.doSendKeys(entityOwner, "Corporate" + rn + "");
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		clickOnViewCorporateSiteButton();
		String afterText = eleUtil.doGetAttribute(entityOwner, "value");
		test.log(LogStatus.INFO, "After edit text is " + afterText);
		if (!beforeText.equalsIgnoreCase(afterText)) {
			return true;
		}
		return false;
	}

	@Step("This method will change corp owner name to previous name or actual name ")
	public boolean changeCorpValueToActualValue(String attr) throws Exception {
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doSendKeys(entityOwner, "Corporate");
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		driver.navigate().refresh();
		eleUtil.doStaticWait(config.getMediumWait());
		clickOnViewCorporateSiteButton();
		return eleUtil.waitForActAttribute(entityOwner, "value", attr, config.getExplicitWait());

	}

	@Step("This method will return total no of corp site on view corp site page")
	public String getNoOfSiteCorpHQ() {
		eleUtil.doStaticWait(2000);
		return eleUtil.doGetAttribute(noOfSitesCorHQ, "value");

	}

	@Step("This method will get data from table and click on the next page, if present")
	public List<String> getTableSiteNoList() throws Exception {
		eleUtil.doStaticWait(2000);
		return basePage.getTableDataAndClickOnNextButton(tableSiteNo, nextButton);
	}

	@Step("This method will enter data in search box and click on go button")
	public EntityOwnership searchCorporateSitesInTable(String value) {
		eleUtil.doStaticWait(2000);
		eleUtil.doSendKeys(corpHQSearch, value);
		eleUtil.clickWhenReady(goButton, config.getExplicitWait());
		return this;
	}

	@Step("Verify that corp hq site contains hq site in table")
	public boolean getCorpSiteTableData(String expText) {
		eleUtil.doStaticWait(2000);
		List<String> corpHqList = eleUtil.getElementsTextList(corpSiteTable);
		for (String hQSite : corpHqList) {
			if (hQSite.equalsIgnoreCase(expText)) {
				return true;
			}
		}
		return false;
	}

	@Step("Verify table header of corp site")
	public List<String> verifyTableHeader() {
		List<String> tbHeader = eleUtil.getElementsTextList(corpSiteTableHeader);
		return tbHeader;
	}

	/**
	 * @param text
	 * @return
	 */
	@Step("Enter data in ext ref id ")
	public EntityOwnership enterDataExternalRefID(String text) {
		eleUtil.doStaticWait(2000);
		eleUtil.doSendKeys(extRefID, text);
		return this;
	}

	@Step("Click on the save button ")
	public EntityOwnership clickOnSaveButton() {
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		return this;
	}

	public EntityOwnership clickOnCancelButton() {
		eleUtil.clickWhenReady(cancelButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on cancel button ")
	public List<String> getEntityOwnerShipTableFirstRowData() {
		eleUtil.doStaticWait(2000);
		return eleUtil.getElementsTextList(entityOwnershipTable);
	}

	/**
	 * 
	 * @return entityDetails
	 */
	@Step("Getting all entity details from view selected entity and adding into a list")
	public List<String> getAllEntityDetails() {
		List<String> entityDetails = new ArrayList<>();
		eleUtil.clickWhenReady(viewSelectedEntity, config.getExplicitWait());
		eleUtil.doStaticWait(2000);
		String enID = eleUtil.doGetAttribute(entityID, "value");
		entityDetails.add(enID);
		String enOwner = eleUtil.doGetAttribute(entityOwner, "value");
		entityDetails.add(enOwner);
		String enName = eleUtil.doGetAttribute(entityName, "value");
		entityDetails.add(enName);
		String enHQSite = eleUtil.doGetAttribute(hQSite, "value");
		entityDetails.add(enHQSite);
		String exRefID = eleUtil.doGetAttribute(extRefID, "value");
		entityDetails.add(exRefID);
		String currncy = eleUtil.doGetAttribute(currencyCode, "value");
		entityDetails.add(currncy);
		return entityDetails;

	}

	@Step("Click on the view selected entity button")
	public EntityOwnership clickOnViewSelectedEntityButton() {
		eleUtil.doStaticWait(2000);
		eleUtil.clickWhenReady(viewSelectedEntity, config.getExplicitWait());
		return this;
	}

	/**
	 * 
	 * @param enOwner
	 * @return
	 * @throws Exception
	 */
	public boolean IsEntityOwnerEditableOnViewSelectedEntityPage(String enOwner) throws Exception {
		String rn = Utility.getRandomString();
		Thread.sleep(2000);
		String beforeText = eleUtil.doGetAttribute(entityOwner, "value");
		test.log(LogStatus.INFO, "before edit text is " + beforeText);
		eleUtil.doActionMoveClick(entityOwner);
		eleUtil.doSendKeys(entityOwner, "" + enOwner + "" + rn + "");
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		clickOnViewSelectedEntityButton();
		String afterText = eleUtil.doGetAttribute(entityOwner, "value");
		test.log(LogStatus.INFO, "After edit text is " + afterText);
		if (!beforeText.equalsIgnoreCase(afterText)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param actValue
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public boolean changeEntityOwnerValueToActualValue(String actValue, String attr) throws Exception {
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doSendKeys(entityOwner, actValue);
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		driver.navigate().refresh();
		clickOnViewSelectedEntityButton();
		return eleUtil.waitForActAttribute(entityOwner, "value", attr, config.getExplicitWait());
	}

	@Step("Verify that entity name text field is editable or not")
	public boolean IsEntityNameTextFieldEditable() {
		String rn = Utility.getRandomString();
		eleUtil.doStaticWait(2000);
		String beforeEdit = eleUtil.doGetText(entityFirstName);
		test.log(LogStatus.INFO, "Actual value of entity owner before edit" + beforeEdit);
		System.out.println("Actual value of entity owner before edit" + beforeEdit);
		clickOnViewSelectedEntityButton();
		eleUtil.doStaticWait(2000);
		eleUtil.doActionMoveClick(entityName);
		eleUtil.doSendKey(entityName, "" + beforeEdit + "" + rn + "");
		eleUtil.doStaticWait(2000);
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		driver.navigate().refresh();
		eleUtil.doStaticWait(2000);
		String aftereEdit = eleUtil.doGetText(entityFirstName);
		test.log(LogStatus.INFO, "Actual value of entity owner after edit" + aftereEdit);
		System.out.println("Actual value of entity owner after edit" + aftereEdit);
		if (!beforeEdit.equalsIgnoreCase(aftereEdit)) {
			return true;
		}
		return false;
	}

	@Step("Change entity name to previous name")
	public EntityOwnership changeEntityNameToActualValue(String realEntityName) {
		eleUtil.doStaticWait(config.getMediumWait());
		clickOnViewSelectedEntityButton();
		eleUtil.doStaticWait(2000);
		eleUtil.doActionMoveClick(entityName);
		eleUtil.doSendKey(entityName, realEntityName);
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		driver.navigate().refresh();
		return this;
	}

	@Step("Change hq site value")
	public EntityOwnership changeHQSiteOnViewSelectEntity(String siteNum) {
		By xpath = By.xpath("//li[@data-id='" + siteNum + "']");
		clickOnViewSelectedEntityButton();
		eleUtil.clickWhenReady(hQSiteLov, config.getExplicitWait());
		eleUtil.clickWhenReady(xpath, config.getExplicitWait());
		eleUtil.clickWhenReady(corpSaveButton, config.getExplicitWait());
		eleUtil.clickWhenReady(OkButton, config.getExplicitWait());
		driver.navigate().refresh();
		return this;
	}

	@Step("Getting entity hq site text value")
	public String getEntityHQSite() {

		return eleUtil.doGetText(entityHQSite);
	}

	@Step("Getting table count")
	public int getRowsCount() {
		int count = eleUtil.getElementCount(entOwnTable);
		return count;
	}

	@Step("This method will return first text from ext ref coloumn")
	public String getFirstExtRefTextFromTable() {

		return eleUtil.doGetText(extRefFirstData);
	}

	@Step("This method will click on ok button which is appeared after saving some data")
	public EntityOwnership clickOnOkButton() {
		eleUtil.clickWhenReady(OkButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on create new entity ownership button")
	public EntityOwnership clickOnCreateNewEntityButton() {
		eleUtil.clickWhenReady(createNewEntityButton, config.getExplicitWait());
		return this;
	}

	@Step("Clicking on the save new entity button")
	public EntityOwnership clickOnCreateEntitySaveButton() {
		eleUtil.clickWhenReady(saveEntity, config.getExplicitWait());
		return this;
	}

	/**
	 * 
	 * @param entyNameTextValue
	 * @param entityOwnerTextValue
	 * @return
	 */
	@Step("Getting error message from entity owner and entity name, if user not entering any value")
	public boolean getErrorMessageFromEntityOwnerAndEntityName(String entyNameTextValue, String entityOwnerTextValue) {
		return eleUtil.waitForText(entityNameErrorMessage, entyNameTextValue, config.getExplicitWait())
				&& eleUtil.waitForText(entityOwnerErrorMessage, entityOwnerTextValue, config.getExplicitWait());
	}

	@Step("Enter data in external ref id text text field")
	public EntityOwnership enterDataInExTRefIdOnCreateNewEntityForm(String extRefID) {
		eleUtil.doSendKeys(crNewEntExtRefID, extRefID);
		return this;
	}
	@Step("Select currency code ")
	public EntityOwnership selectCurrencyCodeOnCreateNewEntityForm(String value) {
		eleUtil.selectValueFromDropDown(crNewEntCurCode, value);
		return this;
	}
	/**
	 * @author Kapil
	 * @param entityName
	 * @param entityOwner
	 * @param extRefID
	 * @param currencyCode
	 * @return EntityOwnership
	 */
	@Step("Create a new entity and click on save button ")
	public EntityOwnership createANewEntity(String entityName,String entityOwner,String extRefID,String currencyCode) {
		eleUtil.doSendKeys(crNewEntityName, entityName);
		eleUtil.doSendKeys(crNewEntOwner, entityOwner);
		enterDataInExTRefIdOnCreateNewEntityForm(extRefID);
		selectCurrencyCodeOnCreateNewEntityForm(currencyCode);
		clickOnCreateEntitySaveButton();
		return this;
	}
	
	public boolean verifyTableData(String text) {
		eleUtil.doSelectLastOptionfromDropDown(pageSelector);
		By xpath=By.xpath("//td[text()='"+text+"']");
		return eleUtil.isDisplayed(xpath);
	}
	
}
