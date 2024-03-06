package com.rediron.apex.testCases;

import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.pages.CashDrawerSetUp;
import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class ItemCriteriaSelectionTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);

	@Story("Item Criteria Selection Form")
	@Description("Verifying that edit criteria button will be enable, if owner id =1 and site is HQ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyEditThisCriteriaButtonIsEnableForHQSite() throws Exception {
		String ownerId = reader.getCellData("Item Criteria", 0, 2);
		String siteNo = reader.getCellData("Item Criteria", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNo).navigateToItemCriteriaScreen();
		String query = " Select * from item_criteria_hdr where TYPE_CODE ='F' and owner_id=" + ownerId + "";
		List<String> criteriaIdes = Utility.getDataFromDatabase(query, "CRITERIA_ID");
		for (int i = 0; i < 3; i++) {
			String criteriaId = criteriaIdes.get(i);
			itemCriteria.enterDataInSearchToolBar(criteriaId).clickOnCriteriaIdFromTable();
			boolean sts = itemCriteria.IsEditThisCriteriaEnable();
			if (sts) {
				test.log(LogStatus.PASS, "Edit this criteria button is enable for criteria Id ={" + criteriaId
						+ "} & owner id ={" + ownerId + "");
			} else {
				test.log(LogStatus.FAIL, "Edit this  criteria button is not enable for criteria Id ={" + criteriaId
						+ "} & owner id ={" + ownerId + "}");
				softAssert.assertTrue(sts, "Not verified");
			}
			softAssert.assertAll();
			itemCriteria.clickOnRemoveFilter();
		}

	}

	@Story("Item Criteria Selection Form")
	@Description("This button when selected should open the criteria in another UI for new criteria creation")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyCreateCriteriaButtonOnItemCiteriaSelectionScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		eleUtil.doGetPageTitleIs("Create/Edit Criteria", config.getExplicitWait());

	}

	@Story("Item Criteria Selection Form")
	@Description("This button will copy the criteria and will open that in another UI for view/edit.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyCopyCriteriaButtononItemCiteriaSelectionScreen() throws Exception {
		String criteriaId = reader.getCellData("Item Criteria", 2, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen();
		itemCriteria.clickOnTableElement(criteriaId).clickOnCopyThisCriteriaButton();
		System.out.println(itemCriteria.getDescriptionValueText());
		Assert.assertTrue(itemCriteria.getDescriptionValueText().contains(criteriaId));
	}

	@Story("Item Criteria Selection Form")
	@Description("If logged in from HQ- then all owner id data will be visible in table")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04VerifyCreateCriteriaTableDataForHQSite() throws Exception {
		String siteNo = reader.getCellData("Item Criteria", 1, 2);
		String query = "Select * from item_criteria_hdr where TYPE_CODE ='F'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNo).navigateToItemCriteriaScreen();
		List<String> criteria = itemCriteria.getCriteriaTableData();
		System.out.println("UI data size " + criteria.size());
		test.log(LogStatus.INFO, "UI data size " + criteria.size());
		test.log(LogStatus.INFO, "DB data size " + itemCriteria.getCriteriaTableDataDB(query).size());
		Assert.assertTrue(CollectionUtils.isEqualCollection(criteria, itemCriteria.getCriteriaTableDataDB(query)));
	}

	@Story("Item Criteria Selection Form")
	@Description("If logged in from HQ- then only site data will be visible which come under remote hq site")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05VerifyCreateCriteriaTableDataForRemoteHQ() throws Exception {
		String ownerId = reader.getCellData("Item Criteria", 0, 3);
		String query = "Select * from item_criteria_hdr where \r\n" + "Owner_Id in (" + siteNum + "," + ownerId
				+ ")\r\n" + "and TYpe_code ='F'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().selectDataFromFilterMenu("Owner ID")
				.enterDataInSearchToolBar(ownerId);
		eleUtil.doStaticWait(config.getMediumWait());
		List<String> criteria = itemCriteria.getCriteriaTableData();
		System.out.println("UI data size " + criteria.size());
		test.log(LogStatus.INFO, "UI data size " + criteria.size());
		test.log(LogStatus.INFO, "DB data size " + itemCriteria.getCriteriaTableDataDB(query).size());
		Assert.assertTrue(CollectionUtils.isEqualCollection(criteria, itemCriteria.getCriteriaTableDataDB(query)));
	}

	@Story("Item Criteria Selection Form")
	@Description("If logged in from from diffrent hq and then search data from diffrent hq criteria id")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyCriteriaTableDataWithInvalidData() throws Exception {
		String ownerId = reader.getCellData("Item Criteria", 0, 3);
		String query = "Select * from item_criteria_hdr where \r\n" + "Owner_Id in (" + siteNum + "," + ownerId
				+ ")\r\n" + "and TYpe_code ='F'";
		List<String> criteriaList = Utility.getDataFromDatabase(query, "CRITERIA_ID");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String ownerID1 = reader.getCellData("Item Criteria", 0, 4);
		String siteNo = reader.getCellData("Item Criteria", 1, 3);
		menuSelection.selectCurrentSite(siteNo).navigateToItemCriteriaScreen().selectDataFromFilterMenu("Owner ID")
				.enterDataInSearchToolBar(ownerID1);
		String criteriaID = criteriaList.get(0);
		eleUtil.doStaticWait(config.getMediumWait());
		itemCriteria.selectDataFromFilterMenu("Criteria ID").enterDataInSearchToolBar(criteriaID).clickOnRemoveFilter();
		eleUtil.doStaticWait(config.getSmallWait());
		boolean sts = itemCriteria.isNoDataFoundMessageDisplayed();
		if (sts) {
			test.log(LogStatus.PASS, "No result found for criteria id {" + criteriaID + "}");
		} else {
			test.log(LogStatus.FAIL, "Result found for criteria id {" + criteriaID + "}");
			softAssert.assertTrue(sts, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Selection Form")
	@Description("If logged in from from diffrent hq and then search data from diffrent hq criteria id")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyviewCriteriaDetails() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen()
				.clickOnTableElement(reader.getCellData("Item Criteria", 2, 3));
		Assert.assertEquals(itemCriteria.getCriteriaId(), reader.getCellData("Item Criteria", 2, 3));
		itemCriteria.clickOnTableElement(reader.getCellData("Item Criteria", 2, 4));
		Assert.assertEquals(itemCriteria.getCriteriaId(), reader.getCellData("Item Criteria", 2, 4));
	}

	@Story("Item Criteria Selection Form")
	@Description("Verifying if user is clicked on item list button the user should be on item list page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyItemListOnItemCriteriSelectionScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen()
				.clickOnTableElement(reader.getCellData("Item Criteria", 2, 3)).clickOnItemListButton();
		boolean sts = itemCriteria.getItemTabTextValue("Item List");
		if (sts) {
			test.log(LogStatus.PASS, "User is on list item tab");
		} else {
			test.log(LogStatus.FAIL, "User is not on list item tab");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

	}

	@Story("Item Criteria Selection Form")
	@Description("Verify item list table is visible or not ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyItemListOnItemCriteriSelectionScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen()
				.clickOnTableElement(reader.getCellData("Item Criteria", 2, 3)).clickOnItemListButton();
		eleUtil.waitForFrameByIndex(config.getExplicitWait(), 0);
		boolean sts = itemCriteria.IsUserOnItemListPage("Rows");
		if (sts) {
			test.log(LogStatus.PASS, "User is on Item List table  ");
		} else {

			test.log(LogStatus.FAIL, "User is on Item List table ");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

	}

	// Create new Criteria Scrren test cases

	@Story("Item Criteria Creation Form")
	@Description("Verify Criteria id List on create new criteria scrren")
	@Severity(SeverityLevel.NORMAL)
	//@Test(priority = 10, enabled = true)
	public void TC10verifyCriteriaIDnOnCreateNewCriteriaScreen() throws Exception {
		String query = "select Criteria_Id, i.category_cd, Description from item_criteria_hdr i\r\n"
				+ "WHERE NVL(Type_Code, 'S') = 'F'\r\n" + "order by TO_NUMBER(Criteria_Id)";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.clickOnCriteriaIdLovButton();
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> criteriaIdUI = itemCriteria.getCriteriaIdLovColumnData("Criteria ID", 1);
		List<String> CategoryCdUI = itemCriteria.getCriteriaIdLovColumnData("Criteria ID", 2);
		List<String> criteriaDescUI = itemCriteria.getCriteriaIdLovColumnData("Criteria ID", 3);
		List<String> criteriaIdDB = Utility.getDataFromDatabase(query, "CRITERIA_ID");
		Assert.assertTrue(CollectionUtils.isEqualCollection(criteriaIdUI, criteriaIdDB));
		List<String> CategoryCdDB = Utility.getDataFromDatabase(query, "CATEGORY_CD");
		Assert.assertTrue(CollectionUtils.isEqualCollection(CategoryCdUI, CategoryCdDB));
		List<String> CategoryDescDB = Utility.getDataFromDatabase(query, "DESCRIPTION");
		Assert.assertTrue(CollectionUtils.isEqualCollection(criteriaDescUI, CategoryDescDB));
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify Category Cd on create new criteria scrren")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyCriteriaCategorCdOnCreateNewCriteriaScreen() throws Exception {
		String query = "select DCODE.CODE_MEANING MEANING, DCODE.CODE_VALUE CODE\r\n"
				+ "  from DOMAIN_REF_CODE DCODE, DOMAIN_REF_USAGE DUSAGE\r\n"
				+ " where DUSAGE.TABLE_NAME = 'ITEM_CRITERIA_HDR'\r\n" + "   and DUSAGE.COLUMN_NAME = 'CATEGORY_CD'\r\n"
				+ "   and DCODE.DOMAIN_REF_ID = DUSAGE.DOMAIN_REF_ID\r\n"
				+ " order by DCODE.DISPLAY_SEQ, DCODE.CODE_MEANING";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.clickOnCategoryCDLovButton();
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> categoryUI = itemCriteria.getCriteriaIdLovColumnData("Category", 1);
		List<String> descriptionUI = itemCriteria.getCriteriaIdLovColumnData("Category", 2);
		List<String> categoryDB = Utility.getDataFromDatabase(query, "CODE");
		List<String> descriptionDB = Utility.getDataFromDatabase(query, "MEANING");
		Assert.assertTrue(CollectionUtils.isEqualCollection(categoryUI, categoryDB));
		Assert.assertTrue(CollectionUtils.isEqualCollection(descriptionUI, descriptionDB));
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify Category Cd on create new criteria scrren")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 12, enabled = true)
	public void TC12verifyCriteriaIdAndCategoryValidationMessageCreateNewCriteriaScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.clickOnSaveCriteriaButton();
		String actUIMessageCategory = itemCriteria.getPopUIMessage();
		Assert.assertEquals(actUIMessageCategory, Utility.getGlobalValue("CategoryMessage"));
		itemCriteria.clickOnAlertOkButton().clickOnCategoryCDLovButton().clickOnTableElement("Category", 1, "DP")
				.clickOnSaveCriteriaButton();
		String actUIMessageDesc = itemCriteria.getPopUIMessage();
		Assert.assertEquals(actUIMessageDesc, Utility.getGlobalValue("DescMessage"));
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify List of Department Group against Database ")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 13, enabled = true)
	public void TC13verifyProductCode_DepGrroupAgainstDBONCreateNewCriteriaScreen() throws Exception {
		String query = "select DeptGrp_No, Description from deptgrp t\r\n" + "order by DeptGrp_No";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> ListOfDep = itemCriteria.getListOfDeptGroupUI();
		List<String> deptGroup = itemCriteria.getListOfValueFromDB(query, "DEPTGRP_NO", "DESCRIPTION");
		Assert.assertEquals(deptGroup.size(), ListOfDep.size());
		if (CollectionUtils.isEqualCollection(deptGroup, ListOfDep)) {
			test.log(LogStatus.PASS, "Department Group values are matching with database value");
		} else {
			test.log(LogStatus.FAIL, "Department Group values are not matching with database value");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify List of Department against Database")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyProductCode_DepartmentAgainstDBONCreateNewCriteriaScreen() throws Exception {
		String query = "select dept_no, description from dept order by dept_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		// 1. Verify all list of Department
		List<String> ListOfDep = itemCriteria.getListOfDeptUI();
		List<String> deptGroup = itemCriteria.getListOfValueFromDB(query, "DEPT_NO", "DESCRIPTION");
		Assert.assertEquals(deptGroup.size(), ListOfDep.size());
		if (CollectionUtils.isEqualCollection(deptGroup, ListOfDep)) {
			test.log(LogStatus.PASS, "Department values are matching with database value");
		} else {
			test.log(LogStatus.FAIL, "Department values are not matching with database value");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify List of Department, "
			+ "if user selected Department group then List of Department should be visible on the basis of Department Group ")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifyProductCode_DepartmentAgainstDBifDepGrpIsSelectedCreateNewCriteriaScreen() throws Exception {
		String depGroup = reader.getCellData("Item Criteria", 3, 2);
		String query = "select dept_no, description from dept where deptgrp_no =" + depGroup + " order by dept_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		itemCriteria.clickAnyGivenDepGroup("" + depGroup + "").clickOnDepLovBtn();
		// 1000 - fdgdfg
		// 1. Verify all list of Department
		List<String> ListOfDep = itemCriteria.getListOfDeptUI();
		List<String> deptGroup = itemCriteria.getListOfValueFromDB(query, "DEPT_NO", "DESCRIPTION");
		Assert.assertEquals(deptGroup.size(), ListOfDep.size());
		if (CollectionUtils.isEqualCollection(deptGroup, ListOfDep)) {
			test.log(LogStatus.PASS, "Department values are matching with database value");
		} else {
			test.log(LogStatus.FAIL, "Department values are not matching with database value");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify List of class, "
			+ "if user selected Department then List of class should be visible on the basis of Department Group ")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyProductCode_ListOfClassValuesAgainstDBONCreateNewCriteriaScreen() throws Exception {
		String department = reader.getCellData("Item Criteria", 4, 2);
		String query = "select class_no, description from class where dept_no = " + department + "order by class_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		itemCriteria.clickAnyGivenDepartment("" + department + "").clickOnClassLovBtn();
		// 1000 - fdgdfg
		// 1. Verify all list of Department
		List<String> listOfclassUI = itemCriteria.getListOfClassUI();
		List<String> listOfclassDB = itemCriteria.getListOfValueFromDB(query, "CLASS_NO", "DESCRIPTION");
		Assert.assertEquals(listOfclassUI.size(), listOfclassDB.size());
		if (CollectionUtils.isEqualCollection(listOfclassDB, listOfclassUI)) {
			test.log(LogStatus.PASS, "Class values are matching with database value");
		} else {
			test.log(LogStatus.FAIL, "Class values are not matching with database value");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify List of Line, "
			+ "if user selected Department & Class then List of line should be visible on the basis of Department and class ")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 17, enabled = true)
	public void TC17verifyProductCode_ListOfLineValuesAgainstDBOnCreateNewCriteriaScreen() throws Exception {
		String department = reader.getCellData("Item Criteria", 4, 2);
		String classValue = reader.getCellData("Item Criteria", 5, 2);
		String query = "select Line_No, Description from line t\r\n" + "where t.Dept_No = " + department + "\r\n"
				+ "and t.Class_No =" + classValue + "\r\n" + "order by Line_No";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		itemCriteria.clickAnyGivenDepartment("" + department + "").clickOnClassLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		itemCriteria.clickAnyGivenClassValue(classValue).clickOnLineLovBtn();
		// 1. Verify all list of Line
		List<String> listOfLineUI = itemCriteria.getListOfLineValueUI();
		List<String> listOfLineDB = itemCriteria.getListOfValueFromDB(query, "LINE_NO", "DESCRIPTION");
		Assert.assertEquals(listOfLineUI.size(), listOfLineDB.size());
		if (CollectionUtils.isEqualCollection(listOfLineDB, listOfLineUI)) {
			test.log(LogStatus.PASS, "Line values are matching with database value");
		} else {
			test.log(LogStatus.FAIL, "Line values are not matching with database value");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify Validation message on include criteria and exclude criteria button, "
			+ "if user does not enter any data in product tab text field")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 18, enabled = true)
	public void TC18verifyIncludeCriteriaAndExcludeCriteriaButtonValidationMessage() throws Exception {
		String expMessage = reader.getCellData("Item Criteria", 6, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.clickOnIncludeCriteriaButton();
		String actPopMessage1 = itemCriteria.getPopUIMessage();
		Assert.assertEquals(actPopMessage1, expMessage);
		itemCriteria.clickOnAlertOkButton().clickOnExcludeCriteriaButton();
		String actPopMessage2 = itemCriteria.getPopUIMessage();
		Assert.assertEquals(actPopMessage2, expMessage);

	}

	@Story("Item Criteria Creation Form")
	@Description("'This will clear the contents of all the current tab.Do you want to proceed ?'"
			+ " message with Yes/No button and will clear the data in all the Tabs.")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 19, enabled = true)
	public void TC19verifyThatClearButtonFunctionality() throws Exception {
		String depGroup = reader.getCellData("Item Criteria", 3, 2);
		String department = reader.getCellData("Item Criteria", 4, 2);
		String classValue = reader.getCellData("Item Criteria", 5, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn().clickAnyGivenDepGroup(depGroup).clickOnDepLovBtn()
				.clickAnyGivenDepartment(department).clickOnClassLovBtn().clickAnyGivenClassValue(classValue)
				.clickOnClearButtonProductCodePage().clickOnAlertOkButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean status = itemCriteria.areProductCodeTabInputEmpty();
		Assert.assertTrue(status);

	}

	@Story("Item Criteria Creation Form")
	@Description("clicking on vendor lov should open the venddor selection UI.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 20, enabled = true)
	public void TC20verifyVendorLovShouldOpenTheVenddorSelectionUI() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		boolean sts = itemCriteria.isVendorUIDisplayed();
		if (sts) {
			test.log(LogStatus.PASS, "Vendor Selection Ui is opened");
		} else {
			test.log(LogStatus.FAIL, "Vendor Selection Ui is not opened");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();
	}

	@Story("Item Criteria Creation Form")
	@Description("Vendor ID can be entered. Name should be populated")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 21, enabled = true)
	public void TC21verifyVendorIdForInvalidData() throws Exception {
		String expMessage = reader.getCellData("Item Criteria", 6, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		String actExpMessage = itemCriteria.getVendorMessageforInvalidVendorID("dsdsd");
		Assert.assertEquals(actExpMessage, expMessage);
	}

	@Story("Item Criteria Creation Form")
	@Description("Vendor ID can be entered. Name should be populated")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 22, enabled = true)
	public void TC22verifyVendorIdForDuplicate() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		String actExpMessage = itemCriteria.getVendorMessageforDuplicateVendorID("3m");
		Assert.assertTrue(actExpMessage.contains("already exists."));
	}

	@Story("Item Criteria Creation Form")
	@Description("Vendor ID can be entered. Name should be populated")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 23, enabled = true)
	public void TC23verifyVendorIdForDuplicate() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> actExpMessage = itemCriteria.getVendorNameCorrespondingVendorID("3m");
		String actMsg = actExpMessage.toString().trim();
		if (actMsg.contains("Minnesota Mining & Manufacturi"))
			;
		Assert.assertTrue(actMsg.contains("Minnesota Mining & Manufacturi"));

	}

	@Story("Item Criteria Creation Form")
	@Description("Data shown for attribute name should match the  query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 24, enabled = true)
	public void TC24verifyAttributeNameLovDataAgainstDB() throws Exception {
		String query = "Select Name_Text, AN.Attribute_Id, Lov_Fl from Attribute_Name AN,\r\n"
				+ "Attribute_Functional_Usage AU\r\n" + "where AN.ATTRIBUTE_ID = AU.ATTRIBUTE_ID\r\n"
				+ "and au.functional_area_cd = 'INV'\r\n" + "AND nvl(AN.ACTIVE_FL, 'N') = 'Y'\r\n"
				+ "order by Name_Text";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> attributeNameUI = itemCriteria.getValueFromAttributeNameLov();
		List<String> attributeNameDB = Utility.getDataFromDatabase(query, "NAME_TEXT");
		boolean sts = CollectionUtils.isEqualCollection(attributeNameUI, attributeNameDB);
		if (sts) {
			test.log(LogStatus.PASS, "Data shown for attribute name are matching the query");
		} else {
			test.log(LogStatus.FAIL, "Data shown for attribute name are not matching the query");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

	}

	@Story("Item Criteria Creation Form")
	@Description("Data shown for attribute name should match the  query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 25, enabled = true)
	public void TC25verifyAttributeNameLovDataAgainstDB() throws Exception {
		String query = "SELECT Code_Meaning, Code_Value \r\n"
				+ "FROM Attribute_List_Value Where Attribute_Id ='583'\r\n" + "AND Active_Fl='Y' ORDER BY Code_Meaning";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> attributeValueUI = itemCriteria.getValueFromAttributeValueLovUI("ACTION");
		List<String> attributeValueDB = itemCriteria.getValueFromAttributeValueLovDB(query);
		boolean sts = CollectionUtils.isEqualCollection(attributeValueUI, attributeValueDB);
		if (sts) {
			test.log(LogStatus.PASS, "Data shown for attribute value are matching the query");
		} else {
			test.log(LogStatus.FAIL, "Data shown for attribute value are not matching the query");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

	}

	@Story("Item Criteria Creation Form")
	@Description("If the attribute used is not set with lov_fl set to then any value can be entered by the user.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 26, enabled = true)
	public void TC26verifyAttributeValue() throws Exception {
		String expMessage = "This is not a valid attribute value for the attribute. Please select from the list.";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.enterDataInAttributeValueInput("ACTION", "khkssj");
		Assert.assertEquals(itemCriteria.getPopUIMessage().trim(), expMessage.trim());
	}

	@Story("Item Criteria Creation Form")
	@Description("In criteria section Tab= Item, Value = sku_no will be added.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 27, enabled = true)
	public void verifyItemTabSkuInputBox() throws Exception {
		String expSkuValue = "2000";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.navigateToItemTab().enterDataInSkuInputBox(expSkuValue).clickOnIncludeCriteriaButton();
		String actSkuValue = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		Assert.assertEquals(actSkuValue, expSkuValue);

	}

	@Story("Item Criteria Creation Form")
	@Description("In criteria section Tab= Item, Value = sku_no will be added.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 28, enabled = false)//TODO 
	public void Tc28verifyInItemTabThatDuplicateSkuNoCanNotbeAllowed() throws Exception {
		String expSkuValue = "2000";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.navigateToItemTab().enterDataInSkuInputBox(expSkuValue).clickOnDescriptionInputItemTab();
		Thread.sleep(4000);
		itemCriteria.clickOnAddRowsButtonItemTab();
		Thread.sleep(2000);
		System.out.println(itemCriteria.getPopUIMessage());
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify GropuId and description in list of Values")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 29, enabled = true)
	public void TC29verifyGroupIdTableData() throws Exception {
		String query = "Select Group_ID, Name, Category, Owner_ID \r\n" + "from Item_Group \r\n"
				+ "WHERE Owner_ID in (Select Owner_ID from site where site_no = TmxGbl.My_Site) \r\n"
				+ "or Owner_ID = 1 \r\n" + "Order by Group_ID, Owner_ID, Category";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.navigateToItemGroupTab().clickOnItemGroupLovButton();
		// Verify Group id
		List<String> groupIdUI = itemCriteria.getGroupIdTableData("1");
		groupIdUI.forEach(e -> System.out.println(e));
		List<String> groupIdDB = itemCriteria.getGroupTableDataDB(query, "GROUP_ID");
		Assert.assertEquals(groupIdUI.size(), groupIdDB.size());
		System.out.println("DB List size is =" + groupIdDB.size());
		boolean stsGroup = CollectionUtils.isEqualCollection(groupIdUI, groupIdDB);
		Assert.assertTrue(stsGroup);
		// Verify description
		boolean stsDesc = CollectionUtils.isEqualCollection(itemCriteria.getGroupIdTableData("2"),
				itemCriteria.getGroupTableDataDB(query, "NAME"));
		Assert.assertTrue(stsDesc);
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify that group id is added to criteria section and "
			+ "if a user provide same group id two times then it will throw an error message")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 30, enabled = true)
	public void TC30GroupIdIsAddedToCriteriSectionAndDulplicateMessageIfUserAddedSameSku() throws Exception {
		String SkuValue = reader.getCellData("Item Criteria", 7, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.navigateToItemGroupTab().clickOnItemGroupLovButton().clickOnGroupIdInItemGroupTab(SkuValue)
				.clickOnIncludeCriteriaButton();
		test.log(LogStatus.INFO, "Expected Sku value is " + SkuValue);
		String ActSkutvalue = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		test.log(LogStatus.INFO, "Actual Sku value is " + ActSkutvalue);
		Assert.assertEquals(ActSkutvalue, SkuValue);
		itemCriteria.clickOnItemGroupLovButton().clickOnGroupIdInItemGroupTab(SkuValue).clickOnIncludeCriteriaButton();
		String ActMessage = itemCriteria.getPopUIMessage();
		test.log(LogStatus.INFO, "Actual duplicate message is  " + ActMessage);
		Assert.assertEquals(ActMessage, reader.getCellData("Item Criteria", 6, 5));

	}

	@Story("Item Criteria Creation Form")
	@Description("Verify that group id is added to criteria section and "
			+ "if a user provide same group id two times then it will throw an error message")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 31, enabled = true)
	public void TC31verifyItemLocationIsAddedToCriteriaSelectionScreen() throws Exception {
		String expMessage = reader.getCellData("Item Criteria", 6, 6);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.includeItemLocation("1", "S1");
		String expValue = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		Assert.assertTrue(expValue.contains("1"), "Test case is passed");
		String actMessage = itemCriteria.getItemLocationMessage();
		Assert.assertEquals(expMessage, actMessage);
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify item location lov against database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 32, enabled = true)
	public void TC32verifyItemLocatio() throws Exception {
		String query = "select distinct Location_ID, Location_Name FROM Location_Definition\r\n"
				+ "where Location_ID in (select DISTINCT Location_ID from item_location where site_no IN (\r\n"
				+ "Select Site_No from Site WHERE Owner_Id IN (SELECT Owner_ID from Site WHERE Site_No = 1)))\r\n"
				+ "and site_no IN (Select Site_No from Site WHERE Owner_Id IN (SELECT Owner_ID from Site WHERE Site_No =1)) \r\n"
				+ "Order By Location_ID";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> locationLovUI = itemCriteria.getListOfValueFromLocationLov();
		List<String> locationLovDB = Utility.getDataFromDatabase(query, "LOCATION_ID");
		boolean sts = CollectionUtils.isEqualCollection(locationLovUI, locationLovDB);
		Assert.assertEquals(locationLovUI, locationLovDB);
		Assert.assertTrue(sts);

	}

	@Story("Item Criteria Creation Form")
	@Description("Verify item shelf lov against database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 33, enabled = true)
	public void TC33verifyItemShelfLovDataAgianstDB() throws Exception {
		String query = "select DISTINCT Shelf_ID, Location_ID, Planogram_ID,  Placement_ID from item_location where site_no \r\n"
				+ "IN (Select Site_No from Site WHERE Owner_Id IN (SELECT Owner_ID from Site WHERE Site_No = 1))\r\n"
				+ "AND SHELF_ID IS NOT NULL and (Location_ID = '1')\r\n" + "ORDER BY SHELF_ID";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> shelfLovUI = itemCriteria.getListOfValueFromShelfLov("1");
		List<String> shelfLovDB = Utility.getDataFromDatabase(query, "SHELF_ID");
		boolean sts = CollectionUtils.isEqualCollection(shelfLovUI, shelfLovDB);
		Assert.assertEquals(shelfLovUI, shelfLovDB);
		Assert.assertTrue(sts);

	}

	@Story("Item Criteria Creation Form")
	@Description("Verify item status lov data in item setting tab")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 34, enabled = true)
	public void TC34verifyItemsStatusLovDataAgianstDB() throws Exception {
		String query = "select DISTINCT Sku_Status_ID from invbysit where site_no =TmxGbl.My_Site and sku_status_id IS NOT NULL order by sku_status_id";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		List<String> statusLovUI = itemCriteria.getItemLovStatusData();
		List<String> statusLovDB = Utility.getDataFromDatabase(query, "SKU_STATUS_ID");
		boolean sts = CollectionUtils.isEqualCollection(statusLovUI, statusLovDB);
		Assert.assertEquals(statusLovUI, statusLovDB);
		Assert.assertTrue(sts);
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify item status lov data in item setting tab")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 35, enabled = true)
	public void TC35enterDataInItemSettingTabAndVerifyThatItIsAddedToCriteriaSection() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		String number = String.valueOf(num);
		String statusId = reader.getCellData("Item Criteria", 9, 2);
		String qntyOnHandSlct = reader.getCellData("Item Criteria", 9, 3);
		String prdStartDaySlct = reader.getCellData("Item Criteria", 9, 4);
		String prdEndDaySlct = reader.getCellData("Item Criteria", 9, 5);
		String reOrderPointSlc = reader.getCellData("Item Criteria", 9, 6);
		String reOrderSlct = reader.getCellData("Item Criteria", 9, 7);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.enterDataInItemSettingTab(statusId, qntyOnHandSlct, number, prdStartDaySlct, number, prdEndDaySlct,
				number, reOrderPointSlc, number, reOrderSlct, number);
		String text = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		Assert.assertTrue(text.contains(number), "Test case is passed");
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify item info tab")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 36, enabled = true)
	public void TC36enterDataInItemInfoTabAndVerifyThatItIsAddedToCriteriaSection() throws Exception {
		Random r = new Random();
		int low = 1;
		int high = 30;
		int dateNum = r.nextInt(high - low) + low;
		String date = String.valueOf(dateNum);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.enterDataInItemInfoTab("<=", date);
		for (int i = 1; i <= 5; i++) {
			itemCriteria.selectDateFromCalendra(date, String.valueOf(i));
		}
		itemCriteria.clickOnIncludeCriteriaButton();
		String text = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		Assert.assertTrue(text.contains(date), "Test case is passed");
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify validation message, if user does not select data in any select box")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 37, enabled = true)
	public void TC37verifyValidationMessageOnItemInfoTab() throws Exception {
		Random r = new Random();
		int low = 1;
		int high = 30;
		int dateNum = r.nextInt(high - low) + low;
		String date = String.valueOf(dateNum);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton()
				.navigateToItemInfoTab();
		itemCriteria.selectDateFromCalendra(date, "1").clickOnIncludeCriteriaButton();
		String expMessage = itemCriteria.getPopUIMessage();
		Assert.assertEquals(expMessage, "Please select the equation for Last Sold Date");
	}

	@Story("Item Criteria Creation Form")
	@Description("Verify validation message, if user does not select data in any select box")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 38, enabled = true)
	public void TC38AddNewItemCriteriaAndverifyThatAddedToList() throws Exception {
		String depGroup = reader.getCellData("Item Criteria", 3, 2);
		String department = reader.getCellData("Item Criteria", 4, 2);
		String classValue = reader.getCellData("Item Criteria", 5, 2);
		String SkuValue = reader.getCellData("Item Criteria", 7, 2);
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		String number = String.valueOf(num);
		String statusId = reader.getCellData("Item Criteria", 9, 2);
		String qntyOnHandSlct = reader.getCellData("Item Criteria", 9, 3);
		String prdStartDaySlct = reader.getCellData("Item Criteria", 9, 4);
		String prdEndDaySlct = reader.getCellData("Item Criteria", 9, 5);
		String reOrderPointSlc = reader.getCellData("Item Criteria", 9, 6);
		String reOrderSlct = reader.getCellData("Item Criteria", 9, 7);
		String categoryCode = reader.getCellData("Item Criteria", 10, 2);
		String description = reader.getCellData("Item Criteria", 11, 2).toString();
		String notes = reader.getCellData("Item Criteria", 12, 2);
		Random r = new Random();
		int low = 1;
		int high = 30;
		int dateNum = r.nextInt(high - low) + low;
		String date = String.valueOf(dateNum);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn().clickAnyGivenDepGroup(depGroup).clickOnDepLovBtn()
				.clickAnyGivenDepartment(department).clickOnClassLovBtn().clickAnyGivenClassValue(classValue)
				.clickOnIncludeCriteriaButton();
		String textDep = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		Assert.assertTrue(textDep.contains(depGroup), "Test case is passed");
		itemCriteria.getVendorNameCorrespondingVendorID("3M");
		itemCriteria.clickOnIncludeCriteriaButton();
		String textVender = itemCriteria.getTextValueOfItemFromCriteriaSelectionTable();
		System.out.println(textVender);
		itemCriteria.navigateToAttributeTab();
		itemCriteria.getValueFromAttributeValueLovUI("ACTION");
		itemCriteria.clickOnIncludeCriteriaButton().navigateToItemTab().enterDataInSkuInputBox("2000")
				.clickOnIncludeCriteriaButton().navigateToItemGroupTab().clickOnItemGroupLovButton()
				.clickOnGroupIdInItemGroupTab(SkuValue).clickOnIncludeCriteriaButton().includeItemLocation("1", "S1")
				.enterDataInItemSettingTab(statusId, qntyOnHandSlct, number, prdStartDaySlct, number, prdEndDaySlct,
						number, reOrderPointSlc, number, reOrderSlct, number);
		itemCriteria.enterDataInItemInfoTab("<=", date);
		for (int i = 1; i <= 5; i++) {
			itemCriteria.selectDateFromCalendra(date, String.valueOf(i));
		}
		String actUiMessage = itemCriteria.clickOnIncludeCriteriaButton().clickOnAddToCriteriaButton()
				.getPopUIMessage();
		Assert.assertEquals(actUiMessage, "Please enter the criteria header details and try again.");
		itemCriteria.clickOnAlertOkButton().selectCategoryCodeFromListOfCategoryCode(categoryCode, description, notes)
				.clickOnAddToCriteriaButton();
		eleUtil.doStaticWait(config.getMediumWait());
		String criteriaIdValue = itemCriteria.clickOnSaveCriteriaButton().getCriteriaIdValue();
		test.log(LogStatus.INFO, "New criteria id value is equal to " + criteriaIdValue);
		// verify that user is on home page after click on exit button
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = itemCriteria.verifyExitButton();
		Assert.assertTrue(sts);
		itemCriteria.enterDataInSearchToolBar(criteriaIdValue);
		eleUtil.doStaticWait(config.getMediumWait());
		String criteriaIdtableData = itemCriteria.getCriteriaTableData().toString();
		if (criteriaIdtableData.contains(criteriaIdValue)) {
			test.log(LogStatus.PASS, "Criteria Id is added to table");
		} else if (criteriaIdtableData.contains(description)) {
			test.log(LogStatus.PASS, "Criteria description is added to table");
		} else if (criteriaIdtableData.contains(categoryCode)) {
			test.log(LogStatus.PASS, "Criteria code is added to table");
		} else {
			test.log(LogStatus.FAIL, "Criteria is not added to table");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}
	@Story("Item Criteria Creation Form")
	@Description("Verify delete button functionality on criteria selection")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 39, enabled = true)
	public void TC39verifyDeleteOnCriteriaSelection() throws Exception {
		String depGroup = reader.getCellData("Item Criteria", 3, 2);
		String department = reader.getCellData("Item Criteria", 4, 2);
		String classValue = reader.getCellData("Item Criteria", 5, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn().clickAnyGivenDepGroup(depGroup).clickOnDepLovBtn()
				.clickAnyGivenDepartment(department).clickOnClassLovBtn().clickAnyGivenClassValue(classValue)
				.clickOnIncludeCriteriaButton();
		boolean sts=itemCriteria.clickOnDeleteButtonOnCriteriaSelection().IsNoDataFoudnMsgDisplayedOnCriteriaSelection();
		Assert.assertTrue(sts);
		
	}
	
	@Story("Item Criteria Creation Form")
	@Description("Verify delete button functionality on criteria selection")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 40, enabled = true)
	public void TC40verifyClearAllOnCriteriaSelection() throws Exception {
		String depGroup = reader.getCellData("Item Criteria", 3, 2);
		String department = reader.getCellData("Item Criteria", 4, 2);
		String classValue = reader.getCellData("Item Criteria", 5, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToItemCriteriaScreen().clickOnCreateNewCriteriaButton();
		itemCriteria.clickOnDepGroupLovBtn().clickAnyGivenDepGroup(depGroup).clickOnDepLovBtn()
				.clickAnyGivenDepartment(department).clickOnClassLovBtn().clickAnyGivenClassValue(classValue)
				.clickOnIncludeCriteriaButton();
		boolean sts=itemCriteria.clickOnClearAllButtonOnCriteriaSelection().IsNoDataFoudnMsgDisplayedOnCriteriaSelection();
		Assert.assertTrue(sts);
		
	}
}
