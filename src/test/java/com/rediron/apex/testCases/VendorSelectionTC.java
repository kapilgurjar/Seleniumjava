package com.rediron.apex.testCases;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class VendorSelectionTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String criteriaID = "113";
	String vendorId = "CORONA";
	String vendorType = "INTERNAL VENDOR";
	String attribute = "CORECENTRIC_VENDOR_PO";
	String uccID = "TEST";
	String extVenID = "01234";
	String extVendLoc = "0123S";
	String alertMessage = "As no query criteria was defined this could take several minutes. Do you wish to continue?";

	@Story("Vendor Selection")
	@Description("This test case will verify expand and collapse button on vender selection")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyVendorFilterCollapseButton() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String attr = menuSelection.navigateToVenderSelectionScreen(criteriaID).verifyVendorFilterButtonCollapse();
		if (attr.equalsIgnoreCase("false")) {
			test.log(LogStatus.PASS, "Vendor Filter button is collapsed");
		} else {
			test.log(LogStatus.FAIL, "Vendor Filter button is not collapsed");
		}

	}

	@Story("Vendor Selection")
	@Description("This test case will verify expand and collapse button on vender selection")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyVendorIdFromParentPage() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToVenderSelectionScreenWithVendorID(criteriaID, vendorId);
		Assert.assertEquals(vendorSelection.getVendorIDValue(), vendorId);

	}

	@Story("Vendor Selection")
	@Description("User to select list of values for Vendor type field based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyVendorTypeCdLOVData() throws Exception {
		String query = "select drc.code_meaning, drc.code_value \r\n"
				+ "from domain_ref_code drc, domain_ref_usage dru\r\n"
				+ "where dru.domain_ref_id = drc.domain_ref_id\r\n" + "and dru.table_name = UPPER('VENDOR')\r\n"
				+ "and dru.column_name = UPPER('VENDOR_TYPE_CD')";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToVenderSelectionScreen(criteriaID);
		List<String> vendorTypeLovUI = vendorSelection.getVendorTypeLovData();
		vendorTypeLovUI.remove(0);
		List<String> vendorTypeLovDB = Utility.getDataFromDatabase(query, "CODE_MEANING");
		boolean sts = CollectionUtils.isEqualCollection(vendorTypeLovUI, vendorTypeLovDB);
		if (sts) {
			test.log(LogStatus.PASS, "Vendor type lov UI values is equal to DB values");
		} else {
			test.log(LogStatus.FAIL, "Vendor type lov UI values is not equal to DB values");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Vendor Selection")
	@Description("User to select list of values for Attribute field based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyValueForAttribute() throws Exception {
		String query = "select unique an.name_text, an.attribute_id from attribute_name an,\r\n"
				+ "vendor_attribute_value iav where an.attribute_id = iav.attribute_id\r\n"
				+ "and iav.functional_area_cd = 'VEN' order by an.name_text";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToVenderSelectionScreen(criteriaID);
		List<String> attributeLovUI = vendorSelection.getAttributeTypeLovData();
		attributeLovUI.remove(0);
		List<String> attributeLovDB = Utility.getDataFromDatabase(query, "NAME_TEXT");
		boolean sts = CollectionUtils.isEqualCollection(attributeLovUI, attributeLovDB);
		if (sts) {
			test.log(LogStatus.PASS, "Attribute lov UI values is equal to DB values");
		} else {
			test.log(LogStatus.FAIL, "Attribute lov UI values is not equal to DB values");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Vendor Selection")
	@Description("User to select list of values for sites field based on query")
	@Severity(SeverityLevel.CRITICAL)
	// @Test(priority=5,enabled=true)
	public void TC05verifyValueOfSiteNo() throws Exception {
		String query = "Select distinct s.site_no, s.full_name from site s, sitgrp sg where s.site_no = sg.site_no order by site_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToVenderSelectionScreen("161");
		List<String> siteLovUI = vendorSelection.getSiteLovData();
		System.out.println(siteLovUI.size());
		List<String> siteLovDB = Utility.getDataFromDatabase(query, "SITE_NO");
		System.out.println(siteLovDB.size());
		boolean sts = CollectionUtils.isEqualCollection(siteLovUI, siteLovDB);
		if (sts) {
			test.log(LogStatus.PASS, "Attribute lov UI values is equal to DB values");
		} else {
			test.log(LogStatus.FAIL, "Attribute lov UI values is not equal to DB values");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Vendor Selection")
	@Description("Verify that user entry is disabled for Site lov text box")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifySiteNoFieldWillBeDisbaledForRemoteSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("101").navigateToVenderSelectionScreen("161");
		String attr = vendorSelection.getSiteLovClassAttributes();
		if (attr.contains("apex_disabled")) {
			test.log(LogStatus.PASS, "Site lov text field is disabled for Remote site");
		} else {
			test.log(LogStatus.FAIL, "Site lov text field is not disabled for Remote site");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Vendor Selection")
	@Description("If we click on show in results checkbox, "
			+ "External Vendor Location and External Vendor ID will be shown in result section.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyShowInResultsWillShowAdditionalFields() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID)
				.clickOnShowInResultCheckBox();
		boolean sts = vendorSelection.IsExternalVendorIdAndLocationDisplayed();
		Assert.assertTrue(sts);
	}

	@Story("Vendor Selection")
	@Description("Clear the UI in results and set the default values")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyClearButtonOnVendorSelection() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID)
				.clickOnShowInResultCheckBox();
		vendorSelection.enterDataInVendorSelectionForm(vendorId, vendorType, attribute, uccID, extVenID, extVendLoc);
		boolean sts = vendorSelection.iSEmptyButtonFuncatnalityWorking();
		Assert.assertTrue(sts);
	}

	@Story("Vendor Selection")
	@Description("Vendor in the row will be sent back to the parent page.")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 9, enabled = true)
	public void TC09verifyOkButtonOnVendorSelection() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID)
				.clickOnShowInResultCheckBox();
		vendorSelection.enterDataInVendorID(vendorId);
		String text = vendorSelection.verifyOKButton();
		Assert.assertEquals(text, vendorId);

	}

	@Story("Vendor Selection")
	@Description("Once a criteria is searched, the button will be disabled and will be enabled only a new criteria is added.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifySearchButtonOnVendorSelection() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID)
				.clickOnShowInResultCheckBox();
		String searchText = vendorSelection.enterDataInVendorID(vendorId).verifySearchButton();
		Assert.assertTrue(searchText.contains("apex_disabled"), "search field is disabled");
	}

	@Story("Vendor Selection")
	@Description("Vendor in the row will be sent back to the parent page.")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 11, enabled = true)
	public void TC11verifyVendorID() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID)
				.clickOnShowInResultCheckBox();
		String Tabledata = vendorSelection.enterDataInVendorID(vendorId).getColoumDataInTable();
		Assert.assertEquals(Tabledata, vendorId);

	}

	@Story("Vendor Selection")
	@Description("Only Vendor based on selected type will be show")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 12, enabled = true)
	public void TC12verifyVendorType() throws Exception {
		String vendorType = "MERCHANDISING VENDOR";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToVenderSelectionScreen("161").clickOnShowInResultCheckBox();
		List<String> venIDUI = vendorSelection.selectVendorType(vendorType).getVendorIdFromTable("1000");
		String query = "Select * from vendor where vendor_type_cd= 'M'";
		List<String> venIDDB = Utility.getDataFromDatabase(query, "VENDOR_ID");
		Assert.assertEquals(venIDUI.size(), venIDDB.size());
		if (CollectionUtils.isEqualCollection(venIDUI, venIDDB)) {
			test.log(LogStatus.PASS, "Vendor based on selected type is showing");
		} else {
			test.log(LogStatus.FAIL, "Vendor based on selected type is not showing");
			softAssert.assertTrue(false, "not verified");
		}

		softAssert.assertAll();
	}

	@Story("Vendor Selection")
	@Description("Ucc id entered will be used to search the vendor")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 13, enabled = true)
	public void TC13verifyUCCID() throws Exception {
		String uccID = "6549874";
		String expVendorId = "DC920";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToVenderSelectionScreen("161").clickOnShowInResultCheckBox();
		String actVendorId = vendorSelection.enterDataInUCCID(uccID).getColoumDataInTable();
		Assert.assertEquals(actVendorId, expVendorId);
	}

	@Story("Vendor Selection")
	@Description("Given ext_vendor_num will be filtered in the vendor table")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 14, enabled = true)
	public void TC14verifyext_Vendor_Num() throws Exception {
		String extVenID = "BRADLEY1001";
		String expVendorId = "BRADLEY";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToVenderSelectionScreen("161").clickOnShowInResultCheckBox();
		String actVendorId = vendorSelection.enterDataInExtVenIDTextBox(extVenID).getColoumDataInTable();
		Assert.assertEquals(actVendorId, expVendorId);
	}

	@Story("Vendor Selection")
	@Description("Given ext_vendor_num will be filtered in the vendor table")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 15, enabled = true)
	public void TC15verifyext_Vendor_Site() throws Exception {
		String extVenID = "1001";
		String expVendorId = "BRADLEY";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").navigateToVenderSelectionScreen("161").clickOnShowInResultCheckBox();
		String actVendorId = vendorSelection.enterDataInExtVenLocTextBox(extVenID).getColoumDataInTable();
		Assert.assertEquals(actVendorId, expVendorId);
	}

	@Story("Vendor Selection")
	@Description("This will verify that criteria alert pop is showing on ui, "
			+ "if user is not enter any search keyword")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyCriteriaAlertBoxOnBlankSearch() throws Exception { // TODO
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("103").navigateToVenderSelectionScreen(criteriaID).clickOnShowInResultCheckBox()
				.verifySearchButton();
		// Verify pop up text
		Assert.assertEquals(vendorSelection.getCriteriaAlertPopUpText(), alertMessage);
		// verify that after click on cancel pop up button search button should be
		// enable
		vendorSelection.clickOnAlertCancelButton();
		Assert.assertTrue(!vendorSelection.getSearchButtonAttrText().contains("apex_disabled"));
		// verify that after click on pk pop up button search button should be disabled
		vendorSelection.verifySearchButton();
		vendorSelection.clickOnAlertOkButton();
		Assert.assertTrue(vendorSelection.getSearchButtonAttrText().contains("apex_disabled"));
		String data =vendorSelection.getDataFromTable();
		Assert.assertTrue(!data.isEmpty());
	}

}
