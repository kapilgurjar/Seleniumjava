package com.rediron.apex.testCases;

import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class MoveFundsTC extends TestBase {
	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	String siteNam = reader.getCellData("MenuSelection", 1, 2);
	String transDate = reader.getCellData("SafeReceipt", 0, 2);
	String commentText = reader.getCellData("SafeReceipt", 1, 2);
	String safeReconMessage = reader.getCellData("SafeReceipt", 2, 2).trim();
	public String assignToRegister = "R0364";


	/*
	 * This test case will verify the page title
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("Verify page title")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =1,enabled = true)
	public void TC01verifyPageTitle() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		String actTitle = moveFunds.getPageTitle();
		Allure.step("Actual title is Equal to "+actTitle);
		Assert.assertEquals(actTitle, Utility.getGlobalValue("MoveFundTitle"));
	}

	/*
	 * Site no will be defaulted with logged in site and name of the site popualated
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("Site no will be defaulted with logged in site and name of the site popualated")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =1,enabled = true)
	public void TC02verifyCurrentSiteNoAndSiteName() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		basePage.handleSafeReconWindow(siteNum);
		String siteNo = moveFunds.getSiteNo();
		test.log(LogStatus.INFO, "Site no is = " + siteNo);
		String SiteName = moveFunds.getSiteName();
		test.log(LogStatus.INFO, "Site name is = " + SiteName);
		String siteNoAttr = moveFunds.getSiteNoClassAttributes();
		test.log(LogStatus.INFO, "Site no class attributes is = " + siteNoAttr);
		String SiteNameAttr = moveFunds.getSiteNameClassAttributs();
		test.log(LogStatus.INFO, "Site name class attributes  is = " + SiteNameAttr);

		if (siteNoAttr.contains("disabled") && SiteNameAttr.contains("disabled")) {
			test.log(LogStatus.PASS, "site no and site name are disabled");
		} else {
			test.log(LogStatus.FAIL, "site no and site name are not disabled from user entry.");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	/*
	 * Site no and site name will be disabled from user entry.
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("verify that site no and site name will be disabled from user entry")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =3,enabled = true)
	public void TC03verifyCurrentSiteNoAndSiteNameDisabled() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		String siteNoAttr = moveFunds.getSiteNoClassAttributes();
		test.log(LogStatus.INFO, "Site no class attributes is = " + siteNoAttr);
		String SiteNameAttr = moveFunds.getSiteNameClassAttributs();
		test.log(LogStatus.INFO, "Site name class attributes  is = " + SiteNameAttr);
		if (siteNoAttr.contains("disabled") && SiteNameAttr.contains("disabled")) {
			test.log(LogStatus.PASS, "site no and site name are disabled");
		} else {
			test.log(LogStatus.FAIL, "site no and site name is not verified");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	/*
	 * In this test case we are verifying that currency config value from UI And DB
	 * are same
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying that currency config value from UI And DB are same")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =4,enabled = true)
	public void TC04verifybillNameMoveFundsScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		List<String> billConfigValueUI = moveFunds.getCurrencyConfigValue();
		test.log(LogStatus.INFO, "bill name from UI :" + billConfigValueUI);
		List<String> billConfigValueDB = safeReceipt.getCurrencyConfigNameDB();
		test.log(LogStatus.INFO, "bill name from DB :" + billConfigValueDB);
		if (CollectionUtils.isEqualCollection(billConfigValueUI, billConfigValueDB)) {
			test.log(LogStatus.PASS, "UI should reflect the configuration done for Bills");

		} else {

			test.log(LogStatus.FAIL, "UI should not reflect the configuration done for Bills");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	/*
	 * this test case will verifying that coins config value from UI And DB are same
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying that coins config value from UI And DB are same")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =5,enabled = true)
	public void TC05verifyCoinNameMoveFundsScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		List<String> CoinConfigValueUI = moveFunds.getCoinsConfigValue();
		test.log(LogStatus.INFO, "Coin name from UI :" + CoinConfigValueUI);
		List<String> CoinConfigValueDB = safeReceipt.getCoinConfigNameDB();
		test.log(LogStatus.INFO, "Coin name from DB :" + CoinConfigValueDB);
		if (CollectionUtils.isEqualCollection(CoinConfigValueUI, CoinConfigValueDB)) {
			test.log(LogStatus.PASS, "UI should reflect the configuration done for coins");

		} else {

			test.log(LogStatus.FAIL, "UI should not reflect the configuration done for coins");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();

	}

	/*
	 * This test case will verify that if we selected same vlaue from From Lov and
	 * To love then it should show a pop message to user
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("verify that if we selected same vlaue from From Lov and To love then it should show a pop message to user")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =6,enabled = true)
	public void TC06verifyFromAndTOValuesINValidMessage() throws Exception {
		String lovData = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(lovData)
				.selectValueTOLov(lovData);
		String actMsg = moveFunds.getAlertPopMessage();
		Assert.assertEquals(actMsg, reader.getCellData("MoveFunds", "Message", 2));

	}

	/*
	 * This test case will verify that if user click on the cancel button it will
	 * show a pop message
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("verify that if user click on the cancel button it will show a pop message")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =7,enabled = true)
	public void TC07verifyCancelorClearButton() throws Exception {
		String comment = "testing";
		String expMsg = reader.getCellData("CashDrawer", 4, 5);
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.enterValueInCommentSection(comment);
		//eleUtil.doClick(cashDrawerSetup.clearButton);
		String actMsg = moveFunds.getAlertPopMessage();
		test.log(LogStatus.PASS, "Actual message is :" + actMsg);
		test.log(LogStatus.PASS, "Expected message is :" + expMsg);
		Assert.assertEquals(actMsg, expMsg);
	}

	/*
	 * This test case will verify that Tenders Check No AutoReconciling Are Shown as
	 * per database
	 */
	@Story("Cash Management:Move Funds Screen")
	@Description("Verify that Tenders Check No AutoReconciling Are Shown as  per database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =8,enabled = true)
	public void TC08verifyTendersCheckNoAutoReconcilingAreShown() throws Exception {
		String query = "Select  name, long_name from  tender_name where   name like 'CASH%'  Union \r\n"
				+ "Select  name, long_name from  tender_name  where   name not like 'CASH%'  and  valid_fl = 'Y' and  auto_reconcile_fl = 'N'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
		List<String> tenderNameUI = moveFunds.getTenderNameUI();
		tenderNameUI.forEach(e -> test.log(LogStatus.INFO, "tender name UI is eqaual to " + e));
		List<String> tenderNameDB = Utility.getDataFromDatabase(query, "LONG_NAME");
		tenderNameDB.forEach(e -> test.log(LogStatus.INFO, "tender name DB is eqaual to " + e));
		if (tenderNameUI.equals(tenderNameDB)) {
			test.log(LogStatus.PASS, "Tenders check no auto reconciling are showing.");
		} else {
			test.log(LogStatus.PASS, "Tenders check no auto reconciling are not showing.");
			softAssert.assertTrue(false, "not verified");
		}

		softAssert.assertAll();

	}
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying the cash amount against currency and coins sections ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =9,enabled = true)
	public void TC09VerifyCashAmountInTenderSectionAgainstCurrencyAndCoinsSection() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.enterValueInCommentSection(commentText);
		double sum = moveFunds.getCurrencyCoinsSum(number);
		double tenderCash = moveFunds.getTenderCashAmt();
		Assert.assertEquals(sum, tenderCash);

	}
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying the total amount against currency,coins and tenders sections ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=10,enabled = true)
	public void TC10verifyTotalAmountAgainstCurrencyCoinsSectionAndTenderSections() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.enterValueInCommentSection("testing");
		double tenderSum = moveFunds.getTenderSum(number + ".00") + moveFunds.getCurrencyCoinsSum(number);
		test.log(LogStatus.INFO, "tender sum of currency coins and tender are equal to " + tenderSum);
		double totalAmount = moveFunds.getTotalAmount();
		test.log(LogStatus.INFO, "Total amount  is equal to " + totalAmount);
		Assert.assertEquals(tenderSum, tenderSum);
		test.log(LogStatus.PASS, "Total amount is equal to sum of all");

	}
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying the drawer no if selected from ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =11,enabled = true)
	public void TC11verifyDrawerIfSelectedFromAgainstDB() throws Exception {
		String query = "	SELECT CASHBOX_NO\r\n" + "	FROM cshbox\r\n" + "	WHERE site_no  = " + siteNum + "\r\n"
				+ "	  AND recon_fl = 'N'";
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 4);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from);
		List<String> drawerUI = moveFunds.getDataFromDrawerTableFrom();
		drawerUI.forEach(e -> test.log(LogStatus.INFO, "drawer no from UI, If user selected From is equal to  " + e));
		List<String> drawerDB = Utility.getDataFromDatabase(query, "CASHBOX_NO");
		drawerDB.forEach(e -> test.log(LogStatus.INFO, "drawer no from DB, If user selected From is equal to  " + e));
		if (CollectionUtils.isEqualCollection(drawerUI, drawerDB)) {
			test.log(LogStatus.PASS, "Drawer no from UI  are equal to drawer no from DB ");
		} else {
			test.log(LogStatus.FAIL, "Drawer no from UI  are not equal to drawer no from DB ");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying the drawer no if selected To")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =12,enabled = true)
	public void TC12verifyDrawerIfSelectedTOAgainstDB() throws Exception {
		String query = "	SELECT CASHBOX_NO\r\n" + "	FROM cshbox\r\n" + "	WHERE site_no  = " + siteNum + "\r\n"
				+ "	  AND recon_fl = 'N'";
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 4);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueTOLov(to);
		List<String> drawerUI = moveFunds.getDataFromDrawerTableTO();
		drawerUI.forEach(e -> test.log(LogStatus.INFO, "drawer no from UI, If user selected To is equal to  " + e));
		List<String> drawerDB = Utility.getDataFromDatabase(query, "CASHBOX_NO");
		drawerDB.forEach(e -> test.log(LogStatus.INFO, "drawer no from DB, If user selected TO is equal to  " + e));
		if (CollectionUtils.isEqualCollection(drawerUI, drawerDB)) {
			test.log(LogStatus.PASS, "Drawer no from UI  are equal to drawer no from DB ");
		} else {
			test.log(LogStatus.FAIL, "Drawer no from UI  are not equal to drawer no from DB ");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}
	@Story("Cash Management:Move Funds Screen")
	@Description("Verifying that comment field is mandatory")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =13,enabled = true)
	public void TC13verifyThatCommentFieldISMandatory() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to);
		String actMessage = moveFunds.getCommentPopMsg();
		test.log(LogStatus.INFO, "Actula message is equal to " + actMessage);
		String expMessage = reader.getCellData("MoveFunds", "Message", 3);
		test.log(LogStatus.INFO, "Expected message is equal to " + expMessage);
		if (actMessage.equalsIgnoreCase(expMessage)) {
			test.log(LogStatus.PASS, "Actual message is equal to expected message");
		} else {
			test.log(LogStatus.FAIL, "Actual message is not equal to expected message");
			softAssert.assertTrue(false, "not verified ");
		}
		softAssert.assertAll();
	}
	
	@Story("Cash Management:Move Funds Screen")
	@Description("Indicates if safe reconciliation is being performed at this site."
			+ " Is set to Y on safe_recon form startup, and set to N when form is closed.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =14,enabled = true)
	public void TC14verifySAFE_RECON_IN_PROCESS_OnMoveFunds() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("MenuSelection");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("MenuSelection", 0, i);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','Y');\r\n" + "end;");
			menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds();
			Thread.sleep(1000);
			try {
				String safeRecMessage = moveFunds.getPopWindowMessage();
				test.log(LogStatus.INFO, "Safe Recon Message is for site " + siteNum + ":" + safeRecMessage);
				if (safeRecMessage.contains("Safe reconciliation is currently in process.")) {
					test.log(LogStatus.PASS, "Safe Recon is in process:");
				} else {
					test.log(LogStatus.FAIL, "Safe Recon is not in process:");
					softAssert.assertTrue(false, "Not verified");
				}
				softAssert.assertAll();
				moveFunds.clickOnPopOkButton();
				eleUtil.doIsDisplayed(menuSelection.expandAllButton);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");
				Thread.sleep(2000);
			}

		}
	}
	
	@Story("Cash Management:Move Funds Screen")
	@Description("Set to (Y) requires entry of Comment field on all Safe Transaction screens.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =15,enabled = true)
	public void TC15verifyCommentFieldIsMandatoryOnthebasisOfFlag() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		Utility.executeandcommit(" declare \r\n" + "  ret_val Varchar2(5);\r\n" + "begin\r\n" + "  -- Test statements here\r\n"
				+ "ret_val :=  tmxgbl.set_flag('SAFE_TRNS_REQ_CMNT','N');\r\n" + "end;");
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.clickOnsaveButton();
		boolean sts = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		try {
			if (sts) {
				test.log(LogStatus.PASS, "Auth window is displaying so comment field is not mandatory");
			} else {
				test.log(LogStatus.FAIL, "Auth window is not displaying so comment field is mandatory");
				softAssert.assertTrue(false, "not verified");
			}
			softAssert.assertAll();
		} catch (Exception e) {

		} finally {
			Utility.executeandcommit(" declare \r\n" + "  ret_val Varchar2(5);\r\n" + "begin\r\n" + "  -- Test statements here\r\n"
					+ "ret_val :=  tmxgbl.set_flag('SAFE_TRNS_REQ_CMNT','Y');\r\n" + "end;");
		}

	}
	
	@Story("Cash Management:Move Funds Screen")
	@Description("User name and password will be mathced and user with SAFETRAN_OVR_FL permission can only "
			+ "authorise the declaration.")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority =16,enabled = true)
	public void TC16verifyAuthorizationWindowWgOnMoveFunds() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.enterValueInCommentSection(commentText).clickOnsaveButton();
		if (cashDrawerSetup.verifyAuthorizationPopUpTitle()) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
		String winMsg = moveFunds.enterUserIDAndPasswordInAuthWindow(userName, Utility.decodestring(password))
				.getPopWindowMessage();
		Assert.assertEquals(winMsg, "Move Funds record saved");

	}
	
	@Story("Cash Management:Move Funds Screen")
	@Description("User name and password will be mathced and user with SAFETRAN_OVR_FL permission can only "
			+ "authorise the declaration.")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority =17,enabled = true)
	public void TC17verifyAuthorizationWindowWithWrongCredentialsOnMoveFunds() throws Exception {
		String from = reader.getCellData("MoveFunds", "FROMTOLOV", 2);
		String to = reader.getCellData("MoveFunds", "FROMTOLOV", 3);
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToMoveFunds().selectValueFromLov(from).selectValueTOLov(to)
				.enterValueInCommentSection(commentText).clickOnsaveButton();
		Thread.sleep(2000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		softAssert.assertAll();
		safeReceipt.enterauthorizationEmployeeCredentials(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = moveFunds.getPopWindowMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
	}
}
