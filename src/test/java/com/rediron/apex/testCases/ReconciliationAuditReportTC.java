package com.rediron.apex.testCases;

import java.util.Collections;
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

public class ReconciliationAuditReportTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);

	@Story("Reconciliation Audit Report")
	@Description("Site and name will be defaulted with Logged in site")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyDefaultSiteGroupWithLoggedInSite() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		String defaultLoggedInSite = reconciliationAuditReport.getDefaultLogginSiteValue("value");
		test.log(LogStatus.INFO, "Loggin site is " + defaultLoggedInSite);
		Assert.assertTrue(defaultLoggedInSite.contains(siteNum));
	}

	@Story("Reconciliation Audit Report")
	@Description("Site field will be enabled for CORP HQ & Franchise HQ site.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifySiteGroupTextFiledWillbeEnableForCORPHQFranchiseHQSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		int rowCount = reader.getRowCount("ReconAuditReports");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("ReconAuditReports", 0, i);
			System.out.println("siteNum is " + siteNum);
			if (!siteNum.isEmpty()) {
				menuSelection.selectCurrentSite(siteNum);
				eleUtil.doStaticWait(config.getMediumWait());
				menuSelection.clickOnExpandAllButton().clickOnRecAuditReport();
				String classAttr = reconciliationAuditReport.getDefaultLogginSiteValue("class");
				test.log(LogStatus.INFO, "Loggin site {" + siteNum + "} class attr is  " + classAttr);
				Assert.assertTrue(!classAttr.contains("disabled"));
				test.log(LogStatus.PASS, "Site field is not disbaled for {" + siteNum + "}");
				eleUtil.switchToDefaultContent();
				reconciliationAuditReport.clickOnCloseButton();
			}
		}
	}

	@Story("Reconciliation Audit Report")
	@Description("Site field will be disabled for remote site.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifySiteGroupTextFiledWillbeDisabledForRemote() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		int rowCount = reader.getRowCount("ReconAuditReports");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("ReconAuditReports", 1, i);
			System.out.println("siteNum is " + siteNum);
			if (!siteNum.isEmpty()) {
				menuSelection.selectCurrentSite(siteNum);
				eleUtil.doStaticWait(config.getMediumWait());
				menuSelection.clickOnExpandAllButton().clickOnRecAuditReport();
				String classAttr = reconciliationAuditReport.getDefaultLogginSiteValue("class");
				test.log(LogStatus.INFO, "Loggin site {" + siteNum + "} class attr is  " + classAttr);
				Assert.assertTrue(classAttr.contains("disabled"));
				test.log(LogStatus.PASS, "Site field is disbaled for {" + siteNum + "}");
				eleUtil.switchToDefaultContent();
				reconciliationAuditReport.clickOnCloseButton();
			}
		}
	}

	@Story("Reconciliation Audit Report")
	@Description("Default of employee will be All employees")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyThatDefaultOfEmployeeWillbeAllemployees() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		String allEmpValue = reconciliationAuditReport.getAllEmployeesVallue();
		test.log(LogStatus.INFO, "All Emp Value is " + allEmpValue);
		Assert.assertEquals(allEmpValue, "All Employees");
	}

	@Story("Reconciliation Audit Report")
	@Description("The data to be shown for emplyee should match the query and entered site")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyemployessDataAgainstDBQuery() throws Exception {
		String siteNum = "103";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		List<String> allEmpName = reconciliationAuditReport.getListOfEmployesUI();
		int index = allEmpName.indexOf("AllEmployees");
		allEmpName.remove(index);
		System.out.println("All employee data size UI " + allEmpName.size());
		test.log(LogStatus.INFO, "All Emp name is " + allEmpName);
		List<String> allEmpNameDB = reconciliationAuditReport.getListOfEmployesDB(siteNum);
		System.out.println("All employee data size DB " + allEmpNameDB.size());
		Collections.sort(allEmpName);
		Collections.sort(allEmpNameDB);
		if (CollectionUtils.isEqualCollection(allEmpName, allEmpNameDB)) {
			test.log(LogStatus.PASS, "Employees data is  matching the query and entered site");
		} else {
			test.log(LogStatus.FAIL, "Employees data is not matching the query and entered site");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Reconciliation Audit Report")
	@Description("The date field should match the return of rows in the query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyFromDateLovDataAgainstDBQuery() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		List<String> allFromDateUI = reconciliationAuditReport.getListOfFromDates();
		Collections.sort(allFromDateUI);
		System.out.println(allFromDateUI);
		System.out.println("Db start from here **********************");
		List<String> allFromDateDB = Utility
				.getDataFromDatabase("SELECT DISTINCT(recon_dt) AS recon_dt ,site_no AS site_no\r\n"
						+ "FROM cshbox WHERE site_no  in (select site_no from sitgrp where group_id ='" + siteNum
						+ "') AND recon_fl = 'Y'", "RECON_DT");
		int index = allFromDateDB.indexOf(null);
		System.out.println("index of null" + index);
		allFromDateDB.remove(index);
		Collections.sort(allFromDateDB);
		System.out.println(allFromDateDB);
		boolean sts = CollectionUtils.isEqualCollection(allFromDateUI, allFromDateDB);
		Assert.assertTrue(sts);
	}

	@Story("Reconciliation Audit Report")
	@Description("When this config is set to Y , Employee field will be disabled and the description will be “All Employees")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyPOS_SIGN_IN() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		// Setting flag to Y and verify that Employee should be disabled for user entry
		String query = "declare \r\n" + "  p_flag Varchar2(10);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ " ret := tmxgbl.setsite_flag('POS_SIGN_IN'," + siteNum + ",'Y');\r\n" + "end;";
		Utility.executeandcommit(query);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		try {
			if (reconciliationAuditReport.getEmployeeVallue().contains("disabled")) {
				test.log(LogStatus.PASS, "Employee filed is disabled for user entry ");
			} else {
				test.log(LogStatus.FAIL, "Employee filed is not disabled for user entry");
				softAssert.assertTrue(false, "not verified");
			}
			softAssert.assertAll();

		} catch (Exception e) {

		} finally {
			// Setting flag to N and verify that Employee should not be disabled for user
			// entry
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(10);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + " ret := tmxgbl.setsite_flag('POS_SIGN_IN'," + siteNum + ",'N');\r\n" + "end;");
			eleUtil.switchToDefaultContent();
			reconciliationAuditReport.clickOnCloseButton().clickOnReconAuditReport();
		}

		if (!reconciliationAuditReport.getEmployeeVallue().contains("disabled")) {
			test.log(LogStatus.PASS, "Employee filed is not disabled for user entry ");
		} else {
			test.log(LogStatus.FAIL, "Employee filed is disabled for user entry");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	@Story("Reconciliation Audit Report")
	@Description("This config is set to Y/N , float amount will visible/not visible")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyRECON_AUDIT_RPT_SHOW_FLOAT_AMT() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		// Setting flag to N and verify that float amount should not be visible
		String query = "declare \r\n" + "  p_flag Varchar2(10);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ " ret := tmxgbl.set_flag('RECON_AUDIT_RPT_SHOW_FLOAT_AMT','N');\r\n" + "end;";
		Utility.executeandcommit(query);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen();
		try {
			if (reconciliationAuditReport.IsFloatAmountTextFiledDisplayed() == false) {
				test.log(LogStatus.PASS, "Float amount is not visible");
			} else {
				test.log(LogStatus.FAIL, "float amount is visible");
				softAssert.assertTrue(false, "not verified");
			}
			softAssert.assertAll();

		} catch (Exception e) {

		} finally {
			// Setting flag to Y and verify that float amount should be visible
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(10);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + " ret := tmxgbl.set_flag('RECON_AUDIT_RPT_SHOW_FLOAT_AMT','Y');\r\n" + "end;");
			eleUtil.switchToDefaultContent();
			reconciliationAuditReport.clickOnCloseButton();

		}
		reconciliationAuditReport.clickOnReconAuditReport();
		if (reconciliationAuditReport.IsFloatAmountTextFiledDisplayed()) {
			test.log(LogStatus.PASS, "Float amount is visible");
		} else {
			test.log(LogStatus.FAIL, "float amount is not visible");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	@Story("Reconciliation Audit Report")
	@Description("This will verify that on clicking on preview button, Jasper report should open")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyPreviewButtonAndValidateJasperReportIsOpeningInNewTab() throws Exception {
		String siteNum = "103";
		String expTitle = reader.getCellData("ReconAuditReports", 3, 2);
		String fromDate = reader.getCellData("ReconAuditReports", 4, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
				.selectFromDateFromList(fromDate).clickOnPreviewButton();
		eleUtil.doStaticWait(config.getMediumWait());
		String title = reconciliationAuditReport.moveToJasperTabAndGetTitle();
		Assert.assertEquals(title, expTitle);
	}

	@Story("Reconciliation Audit Report")
	@Description("Float amount will be populated from the site group and the dates entered")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyFloatAmoutAgainstDB() throws Exception {
		String siteNum = "103";
		String date = reader.getCellData("ReconAuditReports", 5, 2);
		String fromDate = reader.getCellData("ReconAuditReports", 4, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
				.selectFromDateFromList(fromDate);
		eleUtil.doStaticWait(config.getMediumWait());
		String actAmount = reconciliationAuditReport.getFloatAmount();
		String toDate = reconciliationAuditReport.getCurrentToDate();
		System.out.println("Current to date is " + toDate);
		// 16-09-22
		String expFloatAmt = Utility.executeQuery("SELECT sum(beg_total_amt) \r\n" + "FROM  cshbox WHERE  site_no  ="
				+ siteNum + "\r\n" + "AND  recon_dt  >= ('" + date + "')\r\n" + "AND  recon_dt  <= ('" + toDate
				+ "')\r\n" + "AND  recon_fl  = 'Y'", "SUM(BEG_TOTAL_AMT)");
		test.log(LogStatus.INFO, "Expected float amount DB is " + expFloatAmt);
		test.log(LogStatus.INFO, "Actual float amount UI is " + actAmount);
		//Assert.assertEquals(expFloatAmt, actAmount);
		Assert.assertEquals(Double.parseDouble(expFloatAmt), Double.parseDouble(actAmount));
	}

	@Story("Reconciliation Audit Report")
	@Description("When user click on cancel button all data will be clear from UI")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyCancelButton() throws Exception {
		String siteNum = "103";
		String empName = reader.getCellData("ReconAuditReports", 6, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
				.selectEmployeeName(empName).clickOnEmpFirstSearchResult();
		boolean sts = reconciliationAuditReport.ISCancelButtonWorking();
		if (sts) {
			test.log(LogStatus.PASS, "Cancel button is working");
		} else {
			test.log(LogStatus.FAIL, "Cancel button is working");
			softAssert.assertTrue(sts, "not verifield ");
		}
		softAssert.assertAll();
	}
	@Story("Reconciliation Audit Report")
	@Description("List Of value search with invalid data")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifyEMPListOfValueSearchWithInValidData() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String invalidData="ABDGFGgfy77";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
				.selectEmployeeName(invalidData);
		String actValue=reconciliationAuditReport.getNoResultFoundMsgFromListOfValues();
		Assert.assertEquals(actValue,"No results found.");
	
		
	}
	
	@Story("Reconciliation Audit Report")
	@Description("List Of value search with invalid data")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifyFromDateListOfValueSearchWithInValidData() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String invalidData="ABDGFGgfy77";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
		.selectFromDateFromList(invalidData);
		eleUtil.doStaticWait(config.getMediumWait());
		String actValue=reconciliationAuditReport.getNoResultFoundMsgFromListOfValues();
		Assert.assertEquals(actValue,"No results found.");
		
	}
	
	@Story("Reconciliation Audit Report")
	@Description("List Of value search with invalid data")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyToDateListOfValueSearchWithInValidData() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String invalidData="ABDGFGgfy77";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToReconciliationAuditReportScreen()
		.selectToDateFromList(invalidData);
		eleUtil.doStaticWait(config.getMediumWait());
		String actValue=reconciliationAuditReport.getNoResultFoundMsgFromListOfValues();
		Assert.assertEquals(actValue,"No results found.");
		
	}

}
