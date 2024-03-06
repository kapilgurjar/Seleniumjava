package com.rediron.apex.pages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Step;

public class ItemCriteria extends TestBase {

	By nextButton = By.xpath("//button[@title='Next']");
	By itemListButton = By.cssSelector("button#itm_criteria_ig_toolbar_lsitm");
	By ownerId = By.xpath("//input[contains(@id,'_OWNER_ID') and @type='text']");
	By toolBarSearch = By.cssSelector("input#itm_criteria_ig_toolbar_search_field");
	By goButton = By.xpath("//span[text()='Go']//parent::button");
	By removeFilter = By.xpath("//button[@title='Remove Filter']");
	By removeFilter2 = By.xpath("(//button[@title='Remove Filter'])[2]");
	By tableFirstColoumn = By.xpath("(//table//tbody//tr//td[1])[1]");
	By editThisCriteriaButton = By.cssSelector("button#itm_criteria_ig_toolbar_editcriteria");
	By createNewCriteria = By.id("itm_criteria_ig_toolbar_newcriteria");
	By copyThisCriteria = By.cssSelector("button#itm_criteria_ig_toolbar_clone");
	By criteriaTable = By.xpath("//table//tbody//tr//td");
	By description = By.cssSelector("input[id*='_DESCRIPTION']");
	By columnFilter = By
			.xpath("//div[@id='itm_criteria_ig_toolbar_column_filter_button_menu']//span//span//following::button");
	By columnFilterButton = By.cssSelector("button#itm_criteria_ig_toolbar_column_filter_button");
	By noDataFound = By.xpath("//span[text()='No data found' and @id='itm_criteria_ig_grid_vc_no_msg']");
	By noDataFoundCriteriaSel=By.xpath("//span[text()='No data found' and @id='criteriaandig_ig_grid_vc_no_msg']");
	By criteriaID = By.xpath("//input[contains(@id,'_CRITERIA_ID') and @type='text']");
	By itemListTab = By.xpath("//span[text()='Item List']");
	By rowLabel = By.xpath("//label[text()='Rows']");
	By criteriaIdLovButton = By.cssSelector("button[id*='CRITERIA_ID_lov_btn']");
	By categoryCdLovButton = By.cssSelector("button[id*='CATEGORY_DESC_lov_btn']");
	By listOfCategoryCode=By.xpath("//div[contains(@id,'_CATEGORY_DESC_dlg')]//table//tr//td");
	By saveCriteriaButton = By.cssSelector("button#SAVE_CRITERIA");
	By altUiMessage = By.className("a-AlertMessage-details");
	By altUiOkButton = By.xpath("//button[text()='OK']");
	By deptGroupLovBtn = By.cssSelector("button[id*='DPTG_lov_btn']");
	By listOfDepGrp = By.xpath("//span[text()='List of Department Group']//following::table//tr//td");
	By deptLovBtn = By.cssSelector("button[id*='DEPT_lov_btn']");
	By listOfDep = By.xpath("//div[@id='PopupLov_38_P38_DEPT_dlg']//ul//li");
	By classLovBtn = By.cssSelector("button[id*='CLASS_lov_btn']");
	By listOfClass = By.xpath("//div[@id='PopupLov_38_P38_CLASS_dlg']//ul//li");
	By lineLovBtn = By.cssSelector("button[id*='LINE_lov_btn']");
	By listOfLine = By.xpath("//div[@id='PopupLov_38_P38_LINE_dlg']//ul//li");
	By includeCriteria = By.xpath("//span[text()='Include Criteria']//parent::button");
	By excludeCriteria = By.xpath("//span[text()='Exclude Criteria']//parent::button");
	By deptGroupInput = By.xpath("//input[contains(@id,'_DPTG') and @type='text']");
	By deptInput = By.xpath("//input[contains(@id,'_DEPT') and @type='text']");
	By classInput = By.xpath("//input[contains(@id,'_CLASS') and @type='text']");
	By lineInput = By.xpath("//input[contains(@id,'LINE') and @type='text']");
	By clrBtnPrdctCode = By.cssSelector("button#CLEAR_DISP");
	By vendorTab = By.xpath("//span[text()='Vendor']//parent::a[@role='tab']");
	By vendorLov = By.xpath("//td//button[@id='vndrlov']");
	By venderSelectionUI = By.cssSelector("div#Main_Region_Id");
	By vendorInput = By.id("VENDR_IG_ig_grid_vc_cur");
	By nameInput = By.xpath("//table//tr//td[3]");
	By addRowsButton = By.cssSelector("button#VENDR_IG_ig_toolbar_vndrnewrow");
	By editCriteriaButton = By.cssSelector("button#EDIT_CRITERIA");
	By attributesTab = By.xpath("//span[text()='Attribute']//parent::a[@role='tab']");
	By attributeNameTable = By.xpath("//div[@id='PopupLov_38_ATTR_NAME_dlg']//table//tr//td[1]");
	By attributeInput = By.id("ATTR_IG_ig_grid_vc_cur");
	By attributeSelectBox = By.cssSelector("input#ATTR_NAME");
	By attributeValueInput = By.xpath("//div[@id='ATTR_IG_ig_grid_vc']//table//tr//td[3]");
	By attributeValueSelectBox = By.id("ATTR_VAL_lov_btn");
	By attributeValueTable = By.xpath("//div[@id='PopupLov_38_ATTR_VAL_dlg']//table//tr//td[1]");
	By itemTab = By.xpath("//span[text()='Item']//parent::a[@role='tab']");
	By skuInput = By.id("ITEM_IG_ig_grid_vc_cur");
	By descInput = By.xpath("//div[@id='ITEM_IG_ig_grid_vc']//table//tr//td[3]");
	By itmLov = By.id("itmlov");
	By criteriaSelectionItemValue = By.xpath("//div[@id='criteriaandig_ig_content_container']//table//tr//td[2]");
	private By addRowsItemTab = By.cssSelector("button#ITEM_IG_ig_toolbar_skuignewrow");
	By itemColumnHeader = By.id("ITEM_IG_ig_grid_vc_cur");
	By itemGroupTab = By.xpath("//span[text()='Item Group']//parent::a[@role='tab']");
	By groupIdLovButton = By.cssSelector("button[id*='GRP_ID_lov_btn']");
	By groupIdDesc = By.cssSelector("input[id*='_GRP_DESC']");
	By groupCategory = By.cssSelector("input[id*='GRP_CATEGORY']");
	By groupIdTable = By.xpath("//div[@id='PopupLov_38_P38_GRP_ID_dlg']//table//tr//td[2]");
	By itemLocationTab = By.xpath("//span[text()='Item Location']//parent::a[@role='tab']");
	By locationsLovBtn = By.id("P38_LOCATION_lov_btn");
	By listOfLocations = By.xpath("//div[@id='PopupLov_38_P38_LOCATION_dlg']//table//tr//td[1]");
	By shelfLov = By.id("P38_SHELF_lov_btn");
	By listOfShelf = By.xpath("//div[@id='PopupLov_38_P38_SHELF_dlg']//table//tr//td[1]");
	By itemLocationMsg = By.xpath("//div//span[@id='P38_DISPLAY_MSG_ONDISB']");
	By itemSettingTab = By.xpath("//span[text()='Item Setting']//parent::a[@role='tab']");
	By itemInfoTab=By.xpath("//span[text()='Item Info']//parent::a[@role='tab']");
	By itemStatusLovBtn = By.cssSelector("button[id*='ITM_STATUS_lov_btn']");
	By listOfStatusId = By.xpath("//div[@id='PopupLov_38_P38_ITM_STATUS_dlg']//table//tr//td");
	By quantityOnHandSlct = By.cssSelector("select[id*='_QTY_ONHND']");
	By quantityOnHandInput = By.cssSelector("input[id*='_QTY_ONHND_TXT']");
	By productStartDaysSlct = By.cssSelector("select[id*='_START_DAYS']");
	By productStartDaysInput = By.cssSelector("input[id*='_START_DAY_TXT']");
	By productEndDaysSlct = By.cssSelector("select[id*='_END_DAYS']");
	By productEndDaysInput = By.cssSelector("input[id*='END_DAY_TXT']");
	By reOrderPointSlct = By.cssSelector("select[id*='_REORDER_POINT']");
	By reOrderPointInput = By.cssSelector("input[id*='_REORDER_POINT']");
	By reOrderToSlct = By.cssSelector("select[id*='_REORDER_TO']");
	By reOrderToInput = By.cssSelector("input[id*='_REORDER_TO']");
	By lastSoldDateSL=By.cssSelector("select[id*='_LAST_SOLD_OPR']");
	By calendra=By.xpath("//table[@class='ui-datepicker-calendar']//tr//td//a");
	By calendraButton=By.xpath("(//button[@class='ui-datepicker-trigger a-Button a-Button--calendar'])[1]");
	By lastOrderDateSL=By.cssSelector("select[id*='_LAST_ODR_OPR']");
	By lastRecDateSL=By.cssSelector("select[id*='_LAST_RECEIVED_OPR']");
	By lASTPRCHGSL=By.cssSelector("select[id*='_LAST_PRC_CHG_OPR']");
	By lastPhysicalCntSL=By.cssSelector("select[id*='_LAST_PHYSICAL_CNT_OPR']");
	By qtyReservedSL=By.cssSelector("select[id*='_QTY_RESERVED_OPR']");
	By qtyReservedInput=By.cssSelector("input[id*='_QTY_RESERVED_TXT']");
	By qtyInTransitSL=By.cssSelector("select[id*='_QTY_TRANSIT_OPR']");
	By qtyInTransitInput=By.cssSelector("input[id*='_QTY_TRANSIT_TXT']");
	By qtyOnOrderSL =By.cssSelector("select[id*='QTY_ON_ORD_OPR']");
	By qtyOnOrderInput=By.cssSelector("input[id*='_QTY_ON_ORD_TXT']");
	By qtyOnBackOrderSL=By.cssSelector("select[id*='_QTY_ONBACKORD_OPR']");
	By qtyOnBackOrderInput=By.cssSelector("input[id*='_QTY_ONBACKORD_TXT']");
	By addToCriteraiButton=By.cssSelector("button#ADD_TO_CRITERIA");
	By notesInput=By.cssSelector("textarea[id*='_NOTES']");
	By criteriaIdInput=By.xpath("//input[contains(@id,'_CRITERIA_ID') and @type='text']");
	By exitButton=By.xpath("//span[text()='Exit']//parent::button");
	By deleteCriteria=By.id("DELETE_CRITERIA_SELECTION");
	By clearAllCriteriaSel=By.xpath("//span[text()='Clear All']//parent::button");
	

	// class constructor
	public ItemCriteria() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
	}

	/**
	 * @author Kapil
	 * @param criteriaID
	 * @return
	 */
	@Step("Enter data in search bar and click on the go button")
	public ItemCriteria enterDataInSearchToolBar(String criteriaID) {
		eleUtil.doSendKeys(toolBarSearch, criteriaID);
		eleUtil.clickWhenReady(goButton, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Click on the criteria id from table")
	public ItemCriteria clickOnCriteriaIdFromTable() {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickWhenReady(tableFirstColoumn, config.getExplicitWait());
		return this;
	}
	

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Verifying that edit this criteria button is enable or disable")
	public boolean IsEditThisCriteriaEnable() {
		eleUtil.doStaticWait(config.getMediumWait());
		String attr = eleUtil.doGetAttribute(editThisCriteriaButton, "class");
		System.out.println(attr);
		if (!attr.contains("apex_disabled")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Click on the remove filter")
	public ItemCriteria clickOnRemoveFilter() {
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(removeFilter, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Click on the remove filter")
	public ItemCriteria clickOnRemoveFilter2() {
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(removeFilter2, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Click on create new criteria button")
	public ItemCriteria clickOnCreateNewCriteriaButton() {
		eleUtil.clickWhenReady(createNewCriteria, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("Click on copy this criteria button")
	public ItemCriteria clickOnCopyThisCriteriaButton() {
		eleUtil.clickWhenReady(copyThisCriteria, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will click on table webelement which user provided")
	public ItemCriteria clickOnTableElement(String criteriaId) {
		eleUtil.clickOnLink(criteriaTable, criteriaId);
		return this;
	}

	/**
	 * @author : Kapil
	 * @return : This will return description text field value
	 */
	@Step("This will return description text field value")
	public String getDescriptionValueText() {
		eleUtil.waitForActAttribute(description, "value", "** Copied from 180 **", config.getExplicitWait());
		return eleUtil.doGetAttribute(description, "value");
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will click coloum filter button main criteria screen and list will be visible to user")
	public ItemCriteria clickOnColomnFilterButton() {
		eleUtil.clickWhenReady(columnFilterButton, config.getExplicitWait());
		return this;

	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will select value of owner id & cretria id from filter menu")
	public ItemCriteria selectDataFromFilterMenu(String columnValue) {
		clickOnColomnFilterButton();
		eleUtil.clickOnLink(columnFilter, columnValue);
		return this;
	}

	/**
	 * @author : Kapil
	 * @return : This will return complete data from criteria table from each page
	 */
	@Step("This will return complete data from criteria table from each page ")
	public List<String> getCriteriaTableData() throws InterruptedException {
		List<String> TableData = new ArrayList<>();
		List<String> firtsPageData = eleUtil.getElementsTextList(criteriaTable);
		for (String page1Data : firtsPageData) {
			TableData.add(page1Data);
		}
		while (eleUtil.doIsEnabled(nextButton)) {
			eleUtil.clickWhenReady(nextButton, config.getExplicitWait());
			List<String> otherPageData = eleUtil.getElementsTextList(criteriaTable);
			for (String otherData : otherPageData) {
				TableData.add(otherData);
			}
		}
		return TableData;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will return complete data from criteria table from data base ")
	public List<String> getCriteriaTableDataDB(String query) throws SQLException {
		// Select * from item_criteria_hdr where TYPE_CODE ='F'
		List<String> criteria_id = Utility.getDataFromDatabase(query, "CRITERIA_ID");
		List<String> criteria_CD = Utility.getDataFromDatabase(query, "CATEGORY_CD");
		List<String> criteria_Des = Utility.getDataFromDatabase(query, "DESCRIPTION");
		List<String> tableDBData = Stream.of(criteria_id, criteria_CD, criteria_Des).flatMap(Collection::stream)
				.collect(Collectors.toList());
		return tableDBData;
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will return no result found is displayed or not")
	public boolean isNoDataFoundMessageDisplayed() {

		return eleUtil.isDisplayed(noDataFound);
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will return criteria id from RHS from criteria details")
	public String getCriteriaId() {
		return eleUtil.doGetAttribute(criteriaID, "value");
	}

	/**
	 * @author Kapil
	 * @return
	 * @param text
	 */
	@Step("This will return item tab text value")
	public boolean getItemTabTextValue(String text) {
		return eleUtil.waitForText(itemListTab, text, config.getExplicitWait());
	}

	/**
	 * @author Kapil
	 * @return
	 */
	@Step("This will click on item list button on header")
	public ItemCriteria clickOnItemListButton() {
		eleUtil.clickWhenReady(itemListButton, config.getExplicitWait());
		return this;
	}

	@Step("Verifying that user is on item list page or not")
	public boolean IsUserOnItemListPage(String text) {
		return eleUtil.waitForText(rowLabel, text, config.getExplicitWait());
	}

	@Step("Click on the Criteria Id Lov button to open List Of values ")
	public ItemCriteria clickOnCriteriaIdLovButton() throws Exception {
		eleUtil.clickWithRetry(criteriaIdLovButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on the Category Lov button to open List Of values ")
	public ItemCriteria clickOnCategoryCDLovButton() throws Exception {
		eleUtil.clickWithRetry(categoryCdLovButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on the Save criteria")
	public ItemCriteria clickOnSaveCriteriaButton() throws Exception {
		eleUtil.clickWithRetry(saveCriteriaButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on the Alert Ok button")
	public ItemCriteria clickOnAlertOkButton() throws Exception {
		eleUtil.doClickWithWait(altUiOkButton, config.getExplicitWait());
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 * @param text,columnNumber
	 * @return
	 */
	@Step("Click on table element")
	public ItemCriteria clickOnTableElement(String text, int columnNumber, String eleText) throws Exception {
		By xpath = By.xpath("//span[text()='" + text + "']//following::tr//td[" + columnNumber + "]");
		eleUtil.clickOnLink(xpath, eleText);
		return this;
	}

	/**
	 * @author Kapil
	 * @return
	 * @param text,columnNumber
	 */
	@Step("Getting text from List Of Values")
	public List<String> getCriteriaIdLovColumnData(String text, int columnNumber) throws Exception {
		By xpath = By.xpath("//span[text()='" + text + "']//following::tr//td[" + columnNumber + "]");
		return eleUtil.getElementsTextList(xpath);
	}

	/**
	 * @author Kapil
	 * @return
	 * @param text,columnNumber
	 * @return
	 */
	@Step("Getting text from pop Window")
	public String getPopUIMessage() throws Exception {
		return eleUtil.getElementTextWithWait(altUiMessage, config.getExplicitWait());
	}

	@Step("Click on Dept Group Lov Button")
	public ItemCriteria clickOnDepGroupLovBtn() {
		eleUtil.doClickWithWait(deptGroupLovBtn, config.getExplicitWait());
		return this;

	}

	@Step("Click on Dept Group which user provided")
	public ItemCriteria clickAnyGivenDepGroup(String DepGroup) {
		eleUtil.clickOnLink(listOfDepGrp, DepGroup);
		return this;

	}

	@Step("Click on Dept which user provided")
	public ItemCriteria clickAnyGivenDepartment(String DepGroup) {
		eleUtil.clickOnLink(listOfDep, DepGroup);
		return this;

	}

	@Step("Click on Class from the dropdown, which user provided")
	public ItemCriteria clickAnyGivenClassValue(String classValue) {
		eleUtil.clickOnLink(listOfClass, classValue);
		return this;

	}

	@Step("Click on Dept Lov Button")
	public ItemCriteria clickOnDepLovBtn() {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(deptLovBtn, config.getExplicitWait());
		return this;

	}

	@Step("Click on class Lov Button")
	public ItemCriteria clickOnClassLovBtn() {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(classLovBtn, config.getExplicitWait());
		return this;

	}

	@Step("Click on line Lov Button in product tab")
	public ItemCriteria clickOnLineLovBtn() {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(lineLovBtn, config.getExplicitWait());
		return this;

	}

	@Step("Getting list of values from Dept Group dropdown")
	public List<String> getListOfDeptGroupUI() throws Exception {
		List<String> actDepGrp = new ArrayList<>();
		List<String> depGrp = eleUtil.getElementsTextList(listOfDepGrp);
		for (String dep : depGrp) {
			actDepGrp.add(dep.trim().replaceAll("\\s", ""));
		}
		return actDepGrp;

	}

	@Step("This method will get combine list values from database")
	public List<String> getListOfValueFromDB(String query, String columnName1, String coloumnName2) throws Exception {
		ArrayList<String> deptGroup = new ArrayList<>();
		List<String> DEPTGRP_NO = Utility.getDataFromDatabase(query, columnName1);
		List<String> DESCRIPTION = Utility.getDataFromDatabase(query, coloumnName2);
		for (int i = 0; i < DEPTGRP_NO.size(); i++) {
			String groupId = DEPTGRP_NO.get(i);
			for (int j = i; j <= i; j++) {
				String desc = DESCRIPTION.get(j);
				if (desc == null) {
					String siteGroup = groupId + "-";
					deptGroup.add(siteGroup.trim().replaceAll("\\s", ""));
				} else {
					String siteGroup = groupId + "-" + desc;
					deptGroup.add(siteGroup.trim().replaceAll("\\s", ""));
				}

			}

		}
		return deptGroup;
	}

	@Step("Getting list of values from Dept Group dropdown")
	public List<String> getListOfDeptUI() throws Exception {
		List<String> actDepGrp = new ArrayList<>();
		List<String> depGrp = eleUtil.getElementsTextList(listOfDep);
		for (String dep : depGrp) {
			actDepGrp.add(dep.trim().replaceAll("\\s", ""));
		}
		return actDepGrp;

	}

	@Step("Getting list of values from list of class dropdown")
	public List<String> getListOfClassUI() throws Exception {
		List<String> actDepGrp = new ArrayList<>();
		List<String> depGrp = eleUtil.getElementsTextList(listOfClass);
		for (String dep : depGrp) {
			actDepGrp.add(dep.trim().replaceAll("\\s", ""));
		}
		return actDepGrp;

	}

	@Step("Getting list of values from list of line dropdown")
	public List<String> getListOfLineValueUI() throws Exception {
		List<String> actDepGrp = new ArrayList<>();
		List<String> depGrp = eleUtil.getElementsTextList(listOfLine);
		for (String dep : depGrp) {
			actDepGrp.add(dep.trim().replaceAll("\\s", ""));
		}
		return actDepGrp;
	}

	@Step("Click on include criteria button")
	public ItemCriteria clickOnIncludeCriteriaButton() {
		eleUtil.doClickWithVisibleElement(includeCriteria, config.getExplicitWait());
		return this;
	}

	@Step("Click on exclude criteria button")
	public ItemCriteria clickOnExcludeCriteriaButton() {
		eleUtil.doClickWithVisibleElement(excludeCriteria, config.getExplicitWait());
		return this;
	}

	@Step("Click on clear button on product code tab")
	public ItemCriteria clickOnClearButtonProductCodePage() {
		eleUtil.doClickWithVisibleElement(clrBtnPrdctCode, config.getExplicitWait());
		return this;
	}

	@Step("If product code tab input is empty then it will return true oterwise it will return false")
	public boolean areProductCodeTabInputEmpty() throws Exception {
		return eleUtil.readValueFromInput(deptGroupInput).isEmpty() && eleUtil.readValueFromInput(deptInput).isEmpty()
				&& eleUtil.readValueFromInput(classInput).isEmpty();
	}

	@Step("Click on vendor tab")
	public void clickOnVendorTab() {
		eleUtil.clickWithRetry(vendorTab, config.getExplicitWait());
	}

	@Step("Vendor page should be displayed if click on vendor lov button")
	public boolean isVendorUIDisplayed() {
		clickOnVendorTab();
		eleUtil.doClickWithWait(vendorLov, config.getExplicitWait());
		eleUtil.switchToFrameByIndex(0);
		return eleUtil.doIsDisplayed(venderSelectionUI);
	}

	@Step("Enter data in Vendor id input box")
	public void enterDataInVendorInput(String vendorId) {
		eleUtil.doActionMoveSendKey(vendorInput, vendorId);
	}

	@Step("Getting Alert message from ui, if user entered wrong vendor id")
	public String getVendorMessageforInvalidVendorID(String vendorId) {
		clickOnVendorTab();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doActionsDoubleClick(vendorInput);
		enterDataInVendorInput(vendorId);
		eleUtil.doActionsDoubleClick(nameInput);
		return eleUtil.getElementTextWithWait(altUiMessage, config.getExplicitWait());
	}

	@Step("Getting Alert message from ui, if user entered wrong vendor id")
	public List<String> getVendorNameCorrespondingVendorID(String vendorId) {
		clickOnVendorTab();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doActionsDoubleClick(vendorInput);
		enterDataInVendorInput(vendorId);
		eleUtil.doActionMoveClick(editCriteriaButton);
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.getElementsTextList(nameInput);
	}

	@Step("Getting Alert message from ui, if user entered duplicate vendor id and click on add rows button")
	public String getVendorMessageforDuplicateVendorID(String vendorId) {
		clickOnVendorTab();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doActionsDoubleClick(vendorInput);
		enterDataInVendorInput(vendorId);
		eleUtil.doActionsDoubleClick(nameInput);
		eleUtil.doClickWithWait(addRowsButton, config.getExplicitWait());
		eleUtil.doActionsDoubleClick(vendorInput);
		enterDataInVendorInput(vendorId);
		eleUtil.doActionsDoubleClick(nameInput);
		return eleUtil.getElementTextWithWait(altUiMessage, config.getExplicitWait());
	}

	@Step("Click on attribte tab")
	public void navigateToAttributeTab() {
		eleUtil.clickWithRetry(attributesTab,config.getExplicitWait());
	}

	@Step("Double click on attribute then attribute select box will open")
	public void clickOnAtrributeInput() {
		eleUtil.doActionsDoubleClick(attributeInput);
		eleUtil.doStaticWait(config.getSmallWait());
	}

	@Step("Double click on attribute value then attribute value select box will open")
	public void clickOnAtrributeValueInput() {
		eleUtil.doActionsDoubleClick(attributeValueInput);
		eleUtil.doStaticWait(config.getSmallWait());
	}

	@Step("Get value from attribute name dropdown")
	public List<String> getValueFromAttributeNameLov() throws Exception {
		navigateToAttributeTab();
		clickOnAtrributeInput();
		eleUtil.doClickWithWait(attributeSelectBox, config.getExplicitWait());
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(attributeNameTable);
	}

	@Step("Get value from attribute value dropdown")
	public List<String> getValueFromAttributeValueLovUI(String attrName) throws Exception {
		List<String> attrList = new ArrayList<>();
		navigateToAttributeTab();
		clickOnAtrributeInput();
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(attributeSelectBox, config.getExplicitWait());
		eleUtil.doStaticWait(config.getSmallWait());
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.clickOnLink(attributeNameTable, attrName);
		clickOnAtrributeValueInput();
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(attributeValueSelectBox, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> attrValue = eleUtil.getElementsTextList(attributeValueTable);
		for (String attr : attrValue) {
			String actAttr = attr.replaceAll("[~]", "");
			attrList.add(actAttr.trim().replaceAll("\\s", ""));
		}
		return attrList;
	}

	@Step("Get value from attribute value dropdown")
	public List<String> getValueFromAttributeValueLovDB(String query) throws Exception {
		List<String> attrList = new ArrayList<>();
		List<String> codeValue = Utility.getDataFromDatabase(query, "CODE_VALUE");
		List<String> codeMeaning = Utility.getDataFromDatabase(query, "CODE_MEANING");
		for (int i = 0; i < codeValue.size(); i++) {
			String actCodeValue = codeValue.get(i).trim();
			for (int j = i; j <= i; j++) {
				String actCodeMeaning = codeMeaning.get(j).trim();
				String attributeValue = actCodeValue + actCodeMeaning;
				attrList.add(attributeValue.trim().replaceAll("\\s", ""));
			}

		}
		return attrList;
	}

	@Step("Enter data in Attribute value input box inside attribute tab")
	public ItemCriteria enterDataInAttributeValueInput(String attrName, String attrValue) throws Exception {
		navigateToAttributeTab();
		eleUtil.doStaticWait(config.getSmallWait());
		clickOnAtrributeInput();
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doClickWithWait(attributeSelectBox, config.getExplicitWait());
		eleUtil.doStaticWait(config.getSmallWait());
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.clickOnLink(attributeNameTable, attrName);
		clickOnAtrributeValueInput();
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionMoveSendKey(attributeValueInput, attrValue);
		eleUtil.doClickWithWait(attributeValueSelectBox, config.getExplicitWait());
		return this;
	}

	public ItemCriteria navigateToItemTab() {
		eleUtil.clickWithRetry(itemTab, config.getExplicitWait());
		return this;
	}

	public ItemCriteria enterDataInSkuInputBox(String skuValue) {
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doActionsDoubleClick(skuInput);
		eleUtil.doActionMoveSendKey(skuInput, skuValue);
		return this;
	}

	public String getTextValueOfItemFromCriteriaSelectionTable() {

		return eleUtil.getElementTextWithWait(criteriaSelectionItemValue, config.getExplicitWait());

	}

	public ItemCriteria clickOnAddRowsButtonItemTab() {
		eleUtil.doStaticWait(config.getMediumWait());
		//jsUtil.clickElementByJS(addRowsItemTab);
		return this;
	}

	public ItemCriteria clickOnDescriptionInputItemTab() {
		eleUtil.doClick(descInput);
		return this;
	}

	@Step("Click on item group tab")
	public ItemCriteria navigateToItemGroupTab() {
		eleUtil.clickWithRetry(itemGroupTab, config.getExplicitWait());
		return this;
	}

	@Step("Click on item group tab")
	public ItemCriteria clickOnItemGroupLovButton() {
		eleUtil.doClickWithWait(groupIdLovButton, config.getExplicitWait());
		return this;
	}

	@Step("Get data from groud id table and pass parameter coumn no")
	public List<String> getGroupIdTableData(String coumnNo) throws Exception {
		List<String> Groups = new ArrayList<>();
		By xpath = By.xpath("//div[@id='PopupLov_38_P38_GRP_ID_dlg']//table//tr//td[" + coumnNo + "]");
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.doStaticWait(config.getMediumWait());
		List<String> groupIdes = eleUtil.getElementsTextList(xpath);
		for (String groupId : groupIdes) {
			String group = groupId.trim().replaceAll("\\s", "");
			Groups.add(group);
		}

		return Groups;
	}

	public List<String> getGroupTableDataDB(String query, String columnName) throws Exception {
		List<String> Groups = new ArrayList<>();

		List<String> groupIdes = Utility.getDataFromDatabase(query, columnName);
		for (String groupId : groupIdes) {
			String group = groupId.trim().replaceAll("\\s", "");
			Groups.add(group);
		}

		return Groups;
	}

	@Step("Click on the group id inside List of group ids")
	public ItemCriteria clickOnGroupIdInItemGroupTab(String groupDesc) {

		eleUtil.clickOnLink(groupIdTable, groupDesc);
		return this;
	}

	@Step("Click on item location button")
	public ItemCriteria navigateToItemLocation() {
		eleUtil.clickWithRetry(itemLocationTab, config.getExplicitWait());
		return this;
	}

	@Step("Click on item location lov button")
	public ItemCriteria clickOnLocationLovBtn() {

		eleUtil.doClickWithWait(locationsLovBtn, config.getExplicitWait());
		return this;

	}

	@Step("Click on item location lov button")
	public ItemCriteria selectLocationFromLOv(String location) {

		eleUtil.clickOnLink(listOfLocations, location);
		return this;
	}

	@Step("Click on item location lov button")
	public ItemCriteria clickOnShelfLovBtn() {

		eleUtil.doClickWithWait(shelfLov, config.getExplicitWait());
		return this;

	}

	@Step("Click on item location lov button")
	public ItemCriteria selectshelfFromLOv(String shelf) {

		eleUtil.clickOnLink(listOfShelf, shelf);
		return this;
	}

	public ItemCriteria includeItemLocation(String location, String shelf) {
		navigateToItemLocation();
		clickOnLocationLovBtn();
		selectLocationFromLOv(location);
		clickOnShelfLovBtn();
		selectshelfFromLOv(shelf);
		clickOnIncludeCriteriaButton();
		return this;
	}

	@Step("Get the item loaction message")
	public String getItemLocationMessage() {
		return eleUtil.doGetText(itemLocationMsg);
	}

	@Step("Get list of value from location lov")
	public List<String> getListOfValueFromLocationLov() {
		navigateToItemLocation();
		clickOnLocationLovBtn();
		return eleUtil.getElementsTextList(listOfLocations);
	}

	@Step("Get list of value from shelf lov")
	public List<String> getListOfValueFromShelfLov(String location) {
		navigateToItemLocation();
		clickOnLocationLovBtn();
		selectLocationFromLOv(location);
		clickOnShelfLovBtn();
		return eleUtil.getElementsTextList(listOfShelf);
	}

	@Step("Click on item setting tab")
	public ItemCriteria navigateToItemSetting() {
		eleUtil.clickWithRetry(itemSettingTab, config.getExplicitWait());
		return this;
	}
	
	@Step("Click on attribte tab")
	public void navigateToItemInfoTab() {
		eleUtil.clickWithRetry(itemInfoTab,config.getExplicitWait());
	}


	@Step("Click on item status lov button")
	public ItemCriteria clickOnStatusLovBtn() {
		eleUtil.doClickWithWait(itemStatusLovBtn, config.getExplicitWait());
		return this;
	}

	@Step("Get data from item staus lov from item setting tab ")
	public List<String> getItemLovStatusData() throws Exception {
		navigateToItemSetting();
		clickOnStatusLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(listOfStatusId);
	}
	@Step("Entering data in item setting tab")
	public void enterDataInItemSettingTab(String statusId, String qntyOnHandSlct, String qntyOnInput,
			String prdStartDaySlct, String prdStartDatInp, String prdEndDaySlct, String prdEndDayInp,
			String reOrderPointSlc, String reOrderPointInp,String reOrderSlct,String reOrderInp) throws Exception {
		navigateToItemSetting();
		clickOnStatusLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.clickOnLink(listOfStatusId, statusId);
		eleUtil.doSelectDropDownByValue(quantityOnHandSlct, qntyOnHandSlct);
		eleUtil.doSendKeys(quantityOnHandInput, qntyOnInput);
		eleUtil.doSelectDropDownByValue(productStartDaysSlct, prdStartDaySlct);
		eleUtil.doSendKeys(productStartDaysInput, prdStartDatInp);
		eleUtil.doSelectDropDownByValue(productEndDaysSlct, prdEndDaySlct);
		eleUtil.doSendKeys(productEndDaysInput, prdEndDayInp);
		eleUtil.doSelectDropDownByValue(reOrderPointSlct, reOrderPointSlc);
		eleUtil.doSendKeys(reOrderPointInput, reOrderPointInp);
		eleUtil.doSelectDropDownByValue(reOrderToSlct, reOrderSlct);
		eleUtil.doSendKeys(reOrderToInput, reOrderInp);
		clickOnIncludeCriteriaButton();
	}
	
	public ItemCriteria selectDateFromCalendra(String date,String buttonNo) {
		By calendraButton=By.xpath("(//button[@class='ui-datepicker-trigger a-Button a-Button--calendar'])["+buttonNo+"]");
		eleUtil.doClickWithWait(calendraButton, config.getExplicitWait());
		eleUtil.clickOnLink(calendra, date);
		return this;
	}
	
	public ItemCriteria enterDataInItemInfoTab(String LastSoldDate,String value) {
		navigateToItemInfoTab();	
		eleUtil.doSelectDropDownByValue(lastSoldDateSL, LastSoldDate);
		eleUtil.doSelectDropDownByValue(lastOrderDateSL, LastSoldDate);
		eleUtil.doSelectDropDownByValue(lastRecDateSL, LastSoldDate);
		eleUtil.doSelectDropDownByValue(lASTPRCHGSL, LastSoldDate);
		eleUtil.doSelectDropDownByValue(lastPhysicalCntSL, LastSoldDate);
		eleUtil.doSelectDropDownByValue(qtyReservedSL, LastSoldDate);
		eleUtil.doSendKeysWithWait(qtyReservedInput, config.getExplicitWait(), value);
		eleUtil.doSelectDropDownByValue(qtyInTransitSL, LastSoldDate);
		eleUtil.doSendKeysWithWait(qtyInTransitInput, config.getExplicitWait(), value);
		eleUtil.doSelectDropDownByValue(qtyOnOrderSL, LastSoldDate);
		eleUtil.doSendKeysWithWait(qtyOnOrderInput, config.getExplicitWait(), value);
		eleUtil.doSelectDropDownByValue(qtyOnBackOrderSL, LastSoldDate);
		eleUtil.doSendKeysWithWait(qtyOnBackOrderInput, config.getExplicitWait(), value);
		return this;
	}
	@Step("Click on the add to criterai button")
	public ItemCriteria clickOnAddToCriteriaButton() {
		eleUtil.clickWithRetry(addToCriteraiButton,config.getExplicitWait());
		return this;
	}
	@Step("Select any given value from list of category")
	public ItemCriteria selectCategoryCodeFromListOfCategoryCode(String categoryCode,String desc,String notes) throws Exception
	{
		//jsUtil.scrollUpByPixel();
		clickOnCategoryCDLovButton();
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.clickOnLink(listOfCategoryCode, categoryCode);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doSendKeysWithWait(description, config.getExplicitWait(), desc);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doSendKeysWithWait(notesInput, config.getExplicitWait(), notes);
		return this;
	}
	@Step("Get criteria id from text field")
	public String getCriteriaIdValue() {
		return eleUtil.getElementAttributesWithWait(criteriaIdInput, config.getExplicitWait(), "value");
	}
	@Step("verify that exit button is working")
	public boolean verifyExitButton() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
		return eleUtil.isDisplayed(createNewCriteria);
	}
	@Step("Click on the delete button inside criteria selection section")
	public ItemCriteria clickOnDeleteButtonOnCriteriaSelection() {
		eleUtil.clickWhenReady(criteriaSelectionItemValue, config.getExplicitWait());
		eleUtil.clickWhenReady(deleteCriteria, config.getExplicitWait());	
		return this;	
	}
	
	@Step("Click on the clear all button inside criteria selection section")
	public ItemCriteria clickOnClearAllButtonOnCriteriaSelection() {
		eleUtil.clickWithRetry(clearAllCriteriaSel, config.getExplicitWait());	
		return this;	
	}
	@Step("verify that no data found message is displayed or not")
	public boolean IsNoDataFoudnMsgDisplayedOnCriteriaSelection() {
		return eleUtil.isDisplayed(noDataFoundCriteriaSel);
	}

}
