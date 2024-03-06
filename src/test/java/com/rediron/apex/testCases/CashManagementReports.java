package com.rediron.apex.testCases;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

public class CashManagementReports extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);

	
	/*
	 * Verifying that user is logged in with default site 
	 */
	@Test(priority = 1, enabled = true)
	public void TC01verifyThatDefaultsitewithloggedinsite() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashOnHandReport();
		eleUtil.switchToFrameByIndex(0);
		String siteGroupValue = cashOnHandReport.getSiteGroupTextValue();
		if (siteGroupValue.contains(siteNum)) {
			test.log(LogStatus.PASS, "Site and name will be defaulted with Logged in site");
		} else {
			test.log(LogStatus.FAIL, "Site and name will not be defaulted with Logged in site");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}
	/*
	 * Verifying close button and title of window  
	 */
	@Test(priority = 2, enabled = true)
	public void TC02verifyTheTitleAndCloseButtonOnWindow() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String windTitle = reader.getCellData("Cash Managment Reports", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashOnHandReport();

		String title = cashOnHandReport.getWindowTitle();
		if (title.equalsIgnoreCase(windTitle)) {
			test.log(LogStatus.PASS, "Title is :" + title);
		} else {
			test.log(LogStatus.FAIL, "Title is not equal to :" + title);
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();

		boolean sts = cashOnHandReport.verifyCloseButton();
		if (sts) {
			test.log(LogStatus.PASS, "Close button is working and user is on menu selection page ");
		} else {
			test.log(LogStatus.FAIL, "Close button is not working and user is not on menu selection page ");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();

	}
	/*
	 * Verifying preview button is working or not 
	 */
	@Test(priority = 3, enabled = true)
	public void TC03verifyPreviewButton() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashOnHandReport();
		eleUtil.switchToFrameByIndex(0);
		cashOnHandReport.clickOnPreviewButton();
		Thread.sleep(2000);
		eleUtil.SwitchToWindow(1);
		String url = eleUtil.getcurrentURL();
		if (url.contains("jasperserver-pro")) {
			test.log(LogStatus.PASS, "Jasper report is open in new tab ");
		} else {
			test.log(LogStatus.FAIL, "Jasper report is not  open in new tab ");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	/*
	 * verify that check box is enable for hQ sites  
	 */
	@Test(priority = 4, enabled = true)
	public void TC04verifyEnablingOfSiteCheckBox() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String siteGroupName = reader.getCellData("Cash Managment Reports", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashOnHandReport();
		eleUtil.switchToFrameByIndex(0);
		cashOnHandReport.clickOnSiteGroupButton();
		eleUtil.switchToDefaultContent();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashOnHandReport.selectSiteAndGroupName(siteGroupName);
		eleUtil.switchToFrameByIndex(0);
		Thread.sleep(2000);
		boolean sts = cashOnHandReport.isCheckBoxEnableOrDisable();
		System.out.println("Check box status is =" + sts);

		if (sts) {
			test.log(LogStatus.PASS, "Check box is enable for site Groups " + siteGroupName + "");
		} else {
			test.log(LogStatus.FAIL, "Check box is not enable for site Groups " + siteGroupName + "");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(priority = 5, enabled = true)
	public void TC05verifyEnablingOfSiteGroupCheckBox() throws Exception {
		String siteNum = reader.getCellData("Cash Managment Reports", 2, 2);
		String siteGroupName = reader.getCellData("Cash Managment Reports", 1, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashOnHandReport();
		eleUtil.switchToFrameByIndex(0);
		cashOnHandReport.clickOnSiteGroupButton();
		eleUtil.switchToDefaultContent();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashOnHandReport.selectSiteAndGroupName(siteGroupName);
		eleUtil.switchToFrameByIndex(0);
		Thread.sleep(2000);
		boolean sts = cashOnHandReport.isCheckBoxEnableOrDisable();
		System.out.println("Check box status is =" + sts);

		if (sts == false) {
			test.log(LogStatus.PASS, "Check box is not enable for site Groups " + siteGroupName + "");
		} else {
			test.log(LogStatus.FAIL, "Check box is enable for site Groups " + siteGroupName + "");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	// Cash Drawer contents Report test cases

	@Test(priority = 6, enabled = true)
	public void TC06verifyThatSiteGroupValueIndividualCorpHQSiteIsEnable() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("Cash Managment Reports");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("Cash Managment Reports", 2, i);
			System.out.println("siteNum is " + siteNum);
			if (!siteNum.isEmpty()) {
				menuSelection.selectCurrentSite(siteNum).navigateTOCashDrawerContentsReport();
				eleUtil.switchToFrameByIndex(0);
				String siteGroupClassAttr = cashDrawerContentsReport.getSiteGroupClassValue();
				if (!siteGroupClassAttr.contains("disabled")) {
					test.log(LogStatus.PASS, "Site/Group is enable for site " + siteNum + "");
				} else {
					test.log(LogStatus.FAIL, "Site/Group is enable for site  " + siteNum + "");
					softAssert.assertTrue(false, " is not verified");
				}
				eleUtil.switchToDefaultContent();
				cashOnHandReport.clickOnCloseButton();
			}
		}
	}

	@Test(priority = 7, enabled = true)
	public void TC07verifyThatSiteGroupValueIndividualCorpRemoteSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("Cash Managment Reports");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("Cash Managment Reports", 3, i);
			System.out.println("siteNum is " + siteNum);
			if (!siteNum.isEmpty()) {
				menuSelection.selectCurrentSite(siteNum).navigateTOCashDrawerContentsReport();
				eleUtil.switchToFrameByIndex(0);
				String siteGroupClassAttr = cashDrawerContentsReport.getSiteGroupClassValue();
				if (siteGroupClassAttr.contains("disabled")) {
					test.log(LogStatus.PASS, "Site/Group is disabled for site " + siteNum + "");
				} else {
					test.log(LogStatus.FAIL, "Site/Group is enable for site  " + siteNum + "");
					softAssert.assertTrue(false, " is not verified");
				}
				eleUtil.switchToDefaultContent();
				cashOnHandReport.clickOnCloseButton();
			}
		}
	}

	@Test(priority = 8, enabled = true)
	public void TC08verifyThatSiteGroupValueIndividualRemoteSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("Cash Managment Reports");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("Cash Managment Reports", 4, i);
			System.out.println("siteNum is " + siteNum);
			if (!siteNum.isEmpty()) {
				menuSelection.selectCurrentSite(siteNum).navigateTOCashDrawerContentsReport();
				eleUtil.switchToFrameByIndex(0);
				String siteGroupClassAttr = cashDrawerContentsReport.getSiteGroupClassValue();
				if (siteGroupClassAttr.contains("disabled")) {
					test.log(LogStatus.PASS, "Site/Group is disabled for site " + siteNum + "");
				} else {
					test.log(LogStatus.FAIL, "Site/Group is enable for site  " + siteNum + "");
					softAssert.assertTrue(false, " is not verified");
				}
				softAssert.assertAll();
				eleUtil.switchToDefaultContent();
				cashOnHandReport.clickOnCloseButton();
			}
		}
	}

	@Test(priority = 9, enabled = true)
	public void TC09verifyPreviewButton() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateTOCashDrawerContentsReport();
		eleUtil.switchToFrameByIndex(0);
		cashOnHandReport.clickOnPreviewButton();
		Thread.sleep(2000);
		eleUtil.SwitchToWindow(1);
		softAssert.assertTrue(eleUtil.getcurrentURL().contains("jasperserver-pro"), "Jasper report open in new window");
		eleUtil.doGetPageTitleIs("TIBCO Jaspersoft: Cash Drawer Contents", config.getExplicitWait());	
	}

}
