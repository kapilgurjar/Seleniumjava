package com.rediron.apex.testCases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

public class SafeMaintenance extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	String siteNam = reader.getCellData("MenuSelection", 1, 2);
	String transDate = reader.getCellData("SafeReceipt", 0, 2);
	String commentText = reader.getCellData("SafeReceipt", 1, 2);
	String safeReconMessage = reader.getCellData("SafeReceipt", 2, 2).trim();
	public String assignToRegister = "R0364";
	
	@BeforeMethod
	public void setSafeReconFlagToN() throws Exception {
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");
		System.out.println("Inside before test and siteNo is== " +siteNum);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','N');\r\n" + "end;");
		System.out.println("Inside before test and siteNo is== " +siteNum);
	}


	@Test(enabled = true)
	public void TC01verifySiteNoAndSiteNameAredisabledTextFiledOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		String Siteno = safeReceipt.getSiteNoAttributes();
		test.log(LogStatus.INFO, "site no class attributes is :" + Siteno);
		Assert.assertTrue(Siteno.contains("disabled"));
		String siteName = safeReceipt.getSiteNameAttributes();
		test.log(LogStatus.INFO, "site name class attributes is :" + siteName);
		Assert.assertTrue(siteName.contains("disabled"));
	}

	@Test(enabled = true)
	public void TC02verifyTypeLovDataOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		basePage.handleSafeReconWindow(siteNum);
		List<String> typeLovUI = safeReceipt.getTypeLovData();
		typeLovUI.remove(0);
		test.log(LogStatus.INFO, "type lov data UI :" + typeLovUI);
		List<String> typeLovDB = safeReceipt.getTypeLovDataDB();
		test.log(LogStatus.INFO, "type love data DB :" + typeLovDB);
		boolean status = CollectionUtils.isEqualCollection(typeLovUI, typeLovDB);
		if (status) {
			test.log(LogStatus.PASS, "Type lov data is matching with DB data");

		} else {

			test.log(LogStatus.FAIL, "Type lov data is not matching with DB data");

			softAssert.assertTrue(false, status + " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC03verifyTransactionDateOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		List<String> TransactionDateUI = safeReceipt.getTransactionDate();
		System.out.println(TransactionDateUI.size());
		test.log(LogStatus.INFO, "Transaction Date lov data UI :" + TransactionDateUI);
		List<String> TransactionDateDB = safeReceipt.getTransactionDateDB(siteNum);
		System.out.println(TransactionDateDB.size());
		test.log(LogStatus.INFO, "Transaction Date lov data DB :" + TransactionDateDB);
		System.out.println(TransactionDateUI.equals(TransactionDateDB));
		if (CollectionUtils.isEqualCollection(TransactionDateUI, TransactionDateDB)) {
			test.log(LogStatus.PASS, "Transaction Date data is matching with DB data");

		} else {

			test.log(LogStatus.FAIL, "Transaction Date lov data is not matching with DB data");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC04verifybillNameOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		List<String> billConfigValueUI = safeReceipt.getCurrencyConfigName();
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

	@Test(enabled = true)
	public void TC05verifyCoinNameOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		List<String> CoinConfigValueUI = safeReceipt.getCoinConfigName();
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

	@Test(enabled = true)
	public void TC06AutoReconciletenderswillnotbeshownOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		List<String> CoinConfigValueUI = safeReceipt.getTenderName();
		test.log(LogStatus.INFO, "Tender name from UI :" + CoinConfigValueUI);
		List<String> CoinConfigValueDB = safeReceipt.getTenderNameDB();
		test.log(LogStatus.INFO, "Tender name from DB :" + CoinConfigValueDB);
		if (CollectionUtils.isEqualCollection(CoinConfigValueUI, CoinConfigValueDB)) {
			test.log(LogStatus.PASS, "No auto reconciling are shown.");

		} else {

			test.log(LogStatus.FAIL, "Auto reconciling are shown.");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC07verifyAuthorizationWindowWithWrongcredentialsOnSafeReceipt() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToSafeReceipt();
		cashDrawerSetup.clickOnSaveButton();
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
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
	}

	@Test(enabled = true)
	public void TC08verifyCancelButtonOnSafeReceipt() throws Exception {
		String expMsg = reader.getCellData("CashDrawer", 4, 5);
		System.out.println(expMsg);
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToSafeReceipt()
		.getRollsSum(number);
		//eleUtil.doClick(cashDrawerSetup.clearButton);
		String actMsg = cashDrawerSetup.getClearPopWindowText();
		test.log(LogStatus.PASS, "Actual message is :" + actMsg);
		test.log(LogStatus.PASS, "Expected message is :" + expMsg);
		Assert.assertEquals(actMsg, expMsg);
		Thread.sleep(5000);
		Boolean status = eleUtil.readValueFromInput(safeReceipt.currencyName1).isEmpty();
		Assert.assertTrue(status);
		if (status == true) {
			test.log(LogStatus.PASS, "Clear button functionality is working");

		} else {

			test.log(LogStatus.FAIL, "Clear button functionality is not working");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC09verify_Exit_Button_SafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToSafeReceipt().getRollsSum("25");
		safeReceipt.getLooseSum("14");
		//eleUtil.doClick(cashDrawerSetup.exitButton);
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		Thread.sleep(5000);
		Boolean status = eleUtil.isDisplayed(menuSelection.expandAllButton);
		if (status == true) {
			test.log(LogStatus.PASS, "Exit button functionality is working");

		} else {

			test.log(LogStatus.FAIL, "Exit button functionality is not working");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC10verify_Cash_Amount_RowinTender_RegionOnSafeReceipt() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToSafeReceipt();
		int currencySum = safeReceipt.getCurrencySum(number);
		double rollsSum = safeReceipt.getRollsSum(number);
		Double looseSum = safeReceipt.getLooseSum(number);
		double actualSum = rollsSum + looseSum + currencySum;
		String rounded = String.format("%.2f", actualSum);
		double actSum = Double.parseDouble(rounded);
		test.log(LogStatus.INFO, "Total Amount after adding currency, rolls and loose is :" + actSum);
		double tenderCash = safeReceipt.getValueFromCash();
		test.log(LogStatus.INFO, "Cash amount from tender section  :" + tenderCash);
		Assert.assertEquals(actSum, tenderCash);

	}

	@Test(enabled = true)
	public void TC11verifyTotalAmountCalculationOnSafeReceipt() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToSafeReceipt();
		int currencySum = safeReceipt.getCurrencySum(number);
		double rollsSum = safeReceipt.getRollsSum(number);
		Double looseSum = safeReceipt.getLooseSum(number);
		Double tenderSum = safeReceipt.getValueFromTenderSection(number);
		double expectedtotalAmount = rollsSum + looseSum + currencySum + tenderSum;
		test.log(LogStatus.INFO, "Total Amount after adding currency, rolls and loose is :" + expectedtotalAmount);
		double actualTotalAnount = safeReceipt.getTotalAmount();
		test.log(LogStatus.INFO, "Total amount from tender section  :" + actualTotalAnount);
		Assert.assertEquals(actualTotalAnount, expectedtotalAmount);
	}

	@Test(enabled = true)
	public void TC12verifyDataChangesOnSaveOnSafeReceipt() throws Exception {
		String SafeDetailNo = "";
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt()
		.clickOnTransactionDateLovBtn();
		String newComment = commentText + Utility.getRandomString();
		safeReceipt.selectTransactionDate(transDate);
		safeReceipt.enterTextInCommentText(newComment);
		safeReceipt.getCurrencySum(number);
		safeReceipt.getRollsSum(number);
		safeReceipt.getLooseSum(number);
		safeReceipt.getValueFromTenderSection(number);
		cashDrawerSetup.clickOnSaveButton();
		safeReceipt.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		Assert.assertEquals(validation_Message, "Safe Receipt record saved.");
		SafeDetailNo=Utility.executeQuery("select *from safedetl where site_no="+siteNum+" and comment_txt='" + newComment + "'","SAFEDETL_NO");
		String name = Utility.executeQuery("select *from safedetl_tender where safedetl_no='" + SafeDetailNo + "'","SAFEDETL_NO");
			System.out.println(name);
			if(name.equalsIgnoreCase(SafeDetailNo)) {
				test.log(LogStatus.PASS, "test case is passed");
			}else {
				test.log(LogStatus.PASS, "test case is passed");
				softAssert.assertTrue(false,"not verified");
			}
			softAssert.assertAll();
		}


	@Test(enabled = true)
	public void TC13verifySAFE_RECON_IN_PROCESSOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		//menuSelection.clickOnManagerOperations();
		System.out.println(safeReconMessage);
		int rowCount = reader.getRowCount("MenuSelection");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("MenuSelection", 0, i);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','Y');\r\n" + "end;");
			menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations();
			eleUtil.doStaticWait(config.getSmallWait());
			menuSelection.clickOnExpandAllButton().clickOnSafeReceipt();
			Thread.sleep(1000);
			String safeRecMessage = cashDrawerSetup.getSafeReconPopMessage();
			test.log(LogStatus.INFO, "Safe Recon Message is:" + safeRecMessage);
			Assert.assertEquals(safeRecMessage, safeReconMessage);
			cashDrawerSetup.clickOnSafeRecOkButton();
			eleUtil.doIsDisplayed(menuSelection.dailyOperations);
			Thread.sleep(3000);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");

		}
	}

	@Test(enabled = true)
	public void TC14verifyCancelButtonOnAuthWindowOnSafeReceipt() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("104").navigateToSafeReceipt();
		safeReceipt.clickOnTransactionDateLovBtn();
		safeReceipt.selectTransactionDate(transDate);
		safeReceipt.enterTextInCommentText(commentText);
		safeReceipt.getCurrencySum(number);
		safeReceipt.getRollsSum(number);
		safeReceipt.getLooseSum(number);
		safeReceipt.getValueFromTenderSection(number);
		cashDrawerSetup.clickOnSaveButton();
		safeReceipt.enterauthorizationEmployeeCredentialsAndClickOnCancel(userName, Utility.decodestring(password));
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		boolean isUserIdEmpty = eleUtil.readValueFromInput(safeReceipt.authorizationEmployee).isEmpty();
		Assert.assertEquals(isUserIdEmpty, true);
		boolean isPasswordEmpty = eleUtil.readValueFromInput(safeReceipt.authorizationPassword).isEmpty();
		Assert.assertEquals(isPasswordEmpty, true);
	}

	@Test(enabled = true)
	public void TC15verifyTransactionDateSearchWithValidDataOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt()
		.clickOnTransactionDateLovBtn();
		Thread.sleep(2000);
		safeReceipt.searchTranscationDate(transDate);
		Thread.sleep(2000);
		String date = eleUtil.doGetText(safeReceipt.transactionSearchFirst);
		Thread.sleep(2000);
		Assert.assertEquals(date, transDate);
		eleUtil.doClick(safeReceipt.transactionSearchFirst);

	}

	@Test(enabled = true)
	public void TC16verifyTransactionDateSearchWithInValidDataOnSafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt()
		.clickOnTransactionDateLovBtn();
		Thread.sleep(2000);
		safeReceipt.searchTranscationDate("8732872");
		Thread.sleep(2000);
		boolean status = safeReceipt.isNoResultFoundIsVisible();
		Assert.assertEquals(status, true);

	}

	@Test(enabled = true)
	public void TC17verifySAFE_TRAN_TYPE_REQUIREDOnSafeReceipt() throws Exception {
		String expPopMessage = reader.getCellData("SafeReceipt", 2, 3).trim();
		Utility.executeandcommit("declare \r\n" + " \r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n" + " \r\n"
				+ "ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','Y');\r\n" + "\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		cashDrawerSetup.clickOnSaveButton();
		try {
		String actualPopUpWinText = eleUtil.doGetText(safeReceipt.typeFieldPopMsg);
		test.log(LogStatus.INFO, "Actual pop window message is :" + actualPopUpWinText);
		test.log(LogStatus.INFO, "Expected pop window message is :" + expPopMessage);
		Assert.assertEquals(actualPopUpWinText, expPopMessage);
		}catch(Exception e) {
			System.out.println("not verified");
		}
		finally {
			Utility.executeandcommit("declare \r\n" + " \r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n" + " \r\n"
					+ "ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','N');\r\n" + "\r\n" + "end;");
		}
		eleUtil.doStaticWait(config.getMediumWait());
		driver.navigate().refresh();
		cashDrawerSetup.clickOnSaveButton();

	}

	@Test(enabled = true)
	public void TC18verifyThatTypeFiledIsRequiredOnSafeReceipt() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		String expPopMessage = reader.getCellData("SafeReceipt", 2, 3).trim();
		Utility.executeandcommit("declare \r\n" + " \r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n" + " \r\n"
				+ "ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','Y');\r\n" + "\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSafeReceipt();
		eleUtil.doClick(safeReceipt.currencyName1);
		String actualPopUpWinText = eleUtil.doGetText(safeReceipt.typeFieldPopMsg);
		try {
		test.log(LogStatus.INFO, "Actual pop window message is :" + actualPopUpWinText);
		test.log(LogStatus.INFO, "Expected pop window message is :" + expPopMessage);
		Assert.assertEquals(actualPopUpWinText, expPopMessage);
		eleUtil.doClick(safeReceipt.okButton);
		safeReceipt.clickOnTransactionDateLovBtn();
		safeReceipt.selectTransactionDate(transDate);
		safeReceipt.selectTypeLovData("SD");
		safeReceipt.enterTextInCommentText(commentText);
		safeReceipt.getCurrencySum(number);
		safeReceipt.getRollsSum(number);
		safeReceipt.getLooseSum(number);
		safeReceipt.getValueFromTenderSection(number);
		cashDrawerSetup.clickOnSaveButton();
		safeReceipt.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		}catch(Exception e) {
			test.log(LogStatus.FAIL, e);
		}
		finally{
			Utility.executeandcommit("declare \r\n" + " \r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n" + " \r\n"
					+ "ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','N');\r\n" + "\r\n" + "end;");
		}
		
		Thread.sleep(2000);
		driver.navigate().refresh();

	}

	// Safe Recon test Cases

	@Test(enabled = true)
	public void TC19verifyPageTitleSafeRecon() throws Exception {
		String expTitle = reader.getCellData("SafeReconciliation", 0, 2).trim();
		System.out.println("Exp Page title is = " + expTitle);
		test.log(LogStatus.INFO, " Exp Page title is = " + expTitle);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		String actTitle = safeReconciliation.getPageTitle();
		System.out.println("Actual Page title is = " + actTitle);
		test.log(LogStatus.INFO, " Actual Page title is = " + actTitle);
		Assert.assertEquals(actTitle, expTitle);
		test.log(LogStatus.PASS, "Actual title is equal to exp title");

	}

	@Test(enabled = true)
	public void TC20verifyCurrentSiteNoAndSiteNameSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		Thread.sleep(2000);
		String siteNo = safeReconciliation.getSiteNo();
		System.out.println("Site no is = " + siteNo);
		test.log(LogStatus.INFO, "Site no is = " + siteNo);
		String SiteName = safeReconciliation.getSiteName();
		System.out.println("Site name is = " + SiteName);
		test.log(LogStatus.INFO, "Site name is = " + SiteName);
		if (siteNo.contains(siteNum) && SiteName.contains(siteNam)) {
			test.log(LogStatus.PASS, "site no and site name is displaying on UI");
		} else {
			test.log(LogStatus.FAIL, "site no and site name is not displaying on UI");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
		try {
			System.out.println("Test");
		}finally {
			safeReconciliation.clickOnExitButton();
			eleUtil.AcceptAlertIfPresent();	
		}
		
	}

	@Test(enabled = true,invocationCount = 3)
	public void TC21verifyCancleButtonOnSafeReconScreen() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		String expectedWaringMsg = reader.getCellData("safeReconciliation", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		safeReconciliation.enterValueInCurrency1(number);
		safeReconciliation.clickOnClearButton();
		eleUtil.AcceptAlertIfPresent();
		String waringText = cashDrawerDeclaration.getCancelButtonPopWindowText();
		test.log(LogStatus.INFO, "Popup window warning text is " + waringText);
		Assert.assertEquals(waringText, expectedWaringMsg);
		Thread.sleep(1000);
		safeReconciliation.clickOnClearYesButton();
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC22verifyExitButtonOnSafeReconScreen() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		safeReconciliation.enterValueInCurrency1(number);
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
		Boolean status = eleUtil.isDisplayed(menuSelection.expandAllButton);
		if (status == true) {
			test.log(LogStatus.PASS, "Exit button functionality is working");

		} else {

			test.log(LogStatus.FAIL, "Exit button functionality is not working");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC23verifyAuthWindowWithWrongCredentialsSafeRecon() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		// safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		safeReconciliation.clickOnCheckAuditButton();
		safeReconciliation.clickOnReconButton();
		Thread.sleep(3000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		// softAssert.assertAll();
		safeReconciliation.enterauthorizationEmployeeCredentials(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
	}

	@Test(enabled = true)
	public void TC24verifyReconciliationDateSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		Thread.sleep(2000);
		String reconCurrentDate = safeReconciliation.getCurrentReconDayDate();
		Date reconDate = new SimpleDateFormat("MM/dd/yyyy").parse(reconCurrentDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String actReconDateUI = formatter.format(reconDate);
		test.log(LogStatus.INFO, "Actual Recon Date UI " + actReconDateUI);
		String query = "select ds.tran_dt\r\n" + "\r\n" + "from dlysite ds, site s\r\n" + "\r\n"
				+ "where ds.site_no = s.site_no\r\n" + "\r\n" + "and ds.site_no = " + siteNum + "\r\n" + "\r\n"
				+ "and ds.tran_dt = tmxgbl.current_Day\r\n" + "\r\n" + "and ds.tran_dt > s.last_day_closed_dt\r\n"
				+ "\r\n" + "order by ds.tran_dt desc";
		List<String> trnsDate = Utility.getDataFromDatabase(query, "TRAN_DT");
		String CurrentDayDB = trnsDate.get(0);
		test.log(LogStatus.INFO, "Actual Recon Date DB " + CurrentDayDB);
		Assert.assertEquals(actReconDateUI, CurrentDayDB);
		test.log(LogStatus.PASS, " CUrrent Day Reconciliation Date is showing on UI");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC25verifyReconciliationDateLessthanCurrentDate() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		Thread.sleep(2000);
		List<String> reconCurrentDateUI = safeReconciliation.getReconDayDate();
		test.log(LogStatus.INFO, "Reconciliation dates from UI = " + reconCurrentDateUI);
		String query = "select ds.tran_dt\r\n" + "\r\n" + "from dlysite ds, site s\r\n" + "\r\n"
				+ "where ds.site_no = s.site_no\r\n" + "\r\n" + "and ds.site_no = " + siteNum + "\r\n" + "\r\n"
				+ "and ds.tran_dt <= tmxgbl.current_Day\r\n" + "\r\n" + "and ds.tran_dt > s.last_day_closed_dt\r\n"
				+ "\r\n" + "order by ds.tran_dt desc";
		List<String> trnsDateDB = Utility.getDataFromDatabase(query, "TRAN_DT");
		test.log(LogStatus.INFO, "Reconciliation dates from DB = " + trnsDateDB);
		Assert.assertEquals(reconCurrentDateUI, trnsDateDB);
		test.log(LogStatus.PASS, "Reconciliation Date are showing on UI");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test
	public void TC26verifyCurrencyCoinsSectionSafeRecon() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		// safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		safeReconciliation.clickOnCheckAuditButton();
		int currencySum = cashDrawerSetup.getCurrencySum(number);
		test.log(LogStatus.INFO, "Currency sum is = " + currencySum);
		double looseSum = cashDrawerSetup.getLooseSum(number);
		test.log(LogStatus.INFO, "Loose sum is = " + looseSum);
		double rollsSum = cashDrawerSetup.getRollsSum(number);
		test.log(LogStatus.INFO, "Rolls sum is = " + rollsSum);
		double actSum = currencySum + looseSum + rollsSum;
		System.out.println("act Sum is = " + actSum);
		test.log(LogStatus.INFO, "Total sum of currency & coins section " + actSum);
		double tenderCash = safeReconciliation.getTenderCashValue();
		System.out.println("tender Cash is = " + tenderCash);
		test.log(LogStatus.INFO, "Tender cash amount is = " + tenderCash);
		Assert.assertEquals(actSum, tenderCash);
		test.log(LogStatus.PASS, "Currency & Coins section value is equal to tender cash amount");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC27verifyTotalActualAmountInTenderSectionSafeRecon() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		// safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		safeReconciliation.clickOnCheckAuditButton();
		int currencySum = cashDrawerSetup.getCurrencySum(number);
		test.log(LogStatus.INFO, "Currency sum is = " + currencySum);
		double looseSum = cashDrawerSetup.getLooseSum(number);
		test.log(LogStatus.INFO, "Loose sum is = " + looseSum);
		double rollsSum = cashDrawerSetup.getRollsSum(number);
		test.log(LogStatus.INFO, "Rolls sum is = " + rollsSum);
		safeReconciliation.getTenderCashValue();
		double tenderAmount = cashDrawerReconciliation.getValueFromTenderSectionActualAmount(number);
		test.log(LogStatus.INFO, "Tender amount is = " + tenderAmount);
		System.out.println("Exp Total amount is =" + tenderAmount);
		double totalAct = safeReconciliation.getTotalActualValue();
		System.out.println("Act Total amount is =" + totalAct);
		test.log(LogStatus.INFO, "Act Total amount is = " + tenderAmount);
		Assert.assertEquals(totalAct, tenderAmount);
		test.log(LogStatus.PASS, "Total Actual amount is Equal to tender section sum ");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC28VerifyActualFloatAmountSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}

		String FloatValueUI = safeReconciliation.getActualFloatValue();
		double actFloatValueUI = Double.parseDouble(FloatValueUI);
		test.log(LogStatus.INFO, "Actual float value from UI is " + actFloatValueUI);
		System.out.println("Actual float value from UI is " + actFloatValueUI);
		String query = "select B_Cash_Management.get_site_safe_float(" + siteNum + ",tmxgbl.current_day) from dual";
		String FloatValueDB = Utility.executeQuery(query,
				"B_CASH_MANAGEMENT.GET_SITE_SAFE_FLOAT(108,TMXGBL.CURRENT_DAY)");
		double actFloatValueDB = Double.parseDouble(FloatValueDB);
		test.log(LogStatus.INFO, "Actual float value from DB is " + actFloatValueDB);
		System.out.println("Actual float value from DB is " + actFloatValueDB);
		Assert.assertEquals(actFloatValueUI, actFloatValueDB);
		test.log(LogStatus.PASS, "Actual Float Value UI is equal to Actual Float Value DB");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC29VerifyTenderRegionShouldBePopulatedSafeRecon() throws Exception {//TODO
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		HashMap<String, String> tenderValueUI = safeReconciliation.getTenderValueFromUI();
		Iterator<Entry<String, String>> itr = tenderValueUI.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			test.log(LogStatus.INFO, "Tender Name UI = " + entry.getKey() + ",Tender Value UI = " + entry.getValue());
		}

		HashMap<String, String> tenderValueDB = safeReconciliation.getTenderValueFromDB();
		Iterator<Entry<String, String>> itr1 = tenderValueDB.entrySet().iterator();
		while (itr1.hasNext()) {
			Map.Entry<String, String> entry = itr1.next();
			test.log(LogStatus.INFO, "Tender Name DB = " + entry.getKey() + ",Tender Value DB = " + entry.getValue());
		}
		boolean sts1 = tenderValueDB.keySet().equals(tenderValueUI.keySet());
		if (sts1 == true) {
			test.log(LogStatus.PASS, "Actual Tender names are equal to expected tender names");
		} else {
			test.log(LogStatus.FAIL, "Actual Tender names are not equal to expected tender names");
			softAssert.assertTrue(false, "not verified");
		}

		HashSet<String> tenderHasSetUI = new HashSet<>(tenderValueUI.values());
		System.out.println(tenderHasSetUI);
		HashSet<String> tenderHasSetDB = new HashSet<>(tenderValueDB.values());
		System.out.println(tenderHasSetDB);
		boolean sts3 = tenderHasSetUI.equals(tenderHasSetDB);
		System.out.println("status is =========" +sts3);
		//boolean sts3 =CollectionUtils.isEqualCollection(tenderHasSetUI, tenderHasSetDB);
		if (sts3 == true) {
			test.log(LogStatus.PASS, "Actual Tender valus are equal to expected tender values");
		} else {
			test.log(LogStatus.FAIL, "Actual Tender values are not equal to expected tender values");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();

	}

	@Test(enabled = true)
	public void TC30verifyTotalExpectedAmountInTenderSectionSafeRecon() throws Exception {//TODO
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		double totalExpAmountDB = safeReconciliation.totalExpectedAmountFromDBINTenderSection();
		test.log(LogStatus.INFO, "Total expected amount from DB is equal to " + totalExpAmountDB);
		double totalExpAmountUI = safeReconciliation.getTotalExpectedAmountInTenderSectionUI();
		test.log(LogStatus.INFO, "Total expected amount from UI is equal to " + totalExpAmountUI);
		Assert.assertEquals(totalExpAmountDB, totalExpAmountUI);
		test.log(LogStatus.PASS, "Actual total Amount is equal to total expected Amount");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();

	}
	/**
	 * 
	 */
	@Test(enabled = true)
	public void TC31verifyOverShortAmountSafeRecon() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}

		double totalExpAmountUI = safeReconciliation.getTotalExpectedAmountInTenderSectionUI();
		test.log(LogStatus.INFO, "Total expected amount from UI is equal to " + totalExpAmountUI);
		int currencySum = cashDrawerSetup.getCurrencySum(number);
		test.log(LogStatus.INFO, "Currency sum is = " + currencySum);
		double actualValue = safeReconciliation.getTotalActualValue();
		double overShortExp = actualValue - totalExpAmountUI;
		System.out.println(overShortExp);
		test.log(LogStatus.INFO, "Over Short expected is equal to " + overShortExp);
		double overShortAct = safeReconciliation.getOverShort();
		System.out.println(overShortAct);
		test.log(LogStatus.INFO, "Over Short actual is equal to " + overShortAct);
		Assert.assertEquals(overShortAct, overShortExp);
		test.log(LogStatus.PASS, "Overshort actual amount is equal to expected overshort amount");
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();

	}

	@Test(enabled = true)
	public void TC32verifybillNameSafeReconSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		List<String> billConfigValueUI = safeReceipt.getCurrencyConfigName();
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
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC33verifyCoinNameSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		List<String> CoinConfigValueUI = safeReceipt.getCoinConfigName();
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
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC34verifyunreconciledDrawersDateSafeRecon() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		String query = "SELECT COUNT(cashbox_no)\r\n" + "		FROM cshbox\r\n" + "		WHERE site_no = " + siteNum
				+ "\r\n" + "		AND recon_fl = 'N'\r\n" + "		AND ((inuse_fl = 'Y' AND store_safe_fl = 'Y')\r\n"
				+ "        OR (store_safe_fl = 'N'))";
		String count = Utility.executeQuery(query, "COUNT(CASHBOX_NO)");
		int ExpcashBoxCount = Integer.parseInt(count);
		test.log(LogStatus.INFO, "Unreconciled Cash Drawers expected count from DB are equal to " + ExpcashBoxCount);
		if (ExpcashBoxCount > 0) {
			int ActcashBoxCount = safeReconciliation.getUnreconciledCashDrawersCount();
			test.log(LogStatus.INFO, "Unreconciled Cash Drawers actual count from UI are equal to " + ActcashBoxCount);
			Assert.assertEquals(ExpcashBoxCount, ActcashBoxCount);
			test.log(LogStatus.PASS, "Unreconciled Cash Drawers actual count is eqaul to expected count");
		} else {
			getScreenShot("Apex");
		}
		safeReconciliation.clickOnExitButton();
		eleUtil.AcceptAlertIfPresent();
	}

	@Test(enabled = true)
	public void TC35verifyExpAmountSumInCheckAuditScreenSafeRecon() throws Exception {
		double addCheckAuditExpAmountUI = 0;
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		Thread.sleep(5000);
		safeReconciliation.clickOnCheckAuditButton1();
		Thread.sleep(5000);
		List<String> data = safeReconciliation.getChekAuditTableData();
		for (int i = 0; i < data.size(); i++) {
			String checkValue = data.get(i);
			double checkAmount = Double.parseDouble(checkValue);
			addCheckAuditExpAmountUI = addCheckAuditExpAmountUI + checkAmount;
		}
		test.log(LogStatus.INFO, "Check Audit exp amount from UI is = " + addCheckAuditExpAmountUI);
		Assert.assertEquals(addCheckAuditExpAmountUI, safeReconciliation.getExpectedAmountCheckAuditTable());
		double addCheckAuditExpAmountDB = safeReconciliation.getChekAuditTableDataFromDB();
		test.log(LogStatus.INFO, "Check Audit exp amount from DB is = " + addCheckAuditExpAmountDB);
		Assert.assertEquals(addCheckAuditExpAmountDB, addCheckAuditExpAmountUI);
	}

	@Test(enabled = true)
	public void TC36verifyOverShortAmountInCheckAuditScreenSafeRecon() throws Exception {
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		String Amount = "200";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		Thread.sleep(5000);
		safeReconciliation.clickOnCheckAuditButton1();
		double overShortAmout = safeReconciliation.addRowandGetOverShortAmount(Amount);
		test.log(LogStatus.INFO, "Actual Over Short Amount is = " + overShortAmout);
		double actAmount = safeReconciliation.getActualAmountCheckAuditTable();
		double expAmount = safeReconciliation.getExpectedAmountCheckAuditTable();
		double expOverShort = actAmount - expAmount;
		test.log(LogStatus.INFO, "Expected Over Short Amount is = " + expOverShort);
		Assert.assertEquals(overShortAmout, expOverShort);

	}

	@Test(enabled = true)
	public void TC37verifyCancelButtonOnAuthWindowSafeRecon() throws Exception {
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		boolean sts = safeReconciliation.ISSafeReconUIPopUpMessageDisplayed();
		if (sts == true) {
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
			safeReconciliation.enterOverRideAuthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			cashDrawerSetup.clickOnSaveButton();
			safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		} else {
			System.out.println("do nothing");
		}
		// safeReconciliation.ClickOnSafeReconUIPopUpOkButton();
		safeReconciliation.clickOnCheckAuditButton();
		safeReconciliation.clickOnReconButton();
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		// softAssert.assertAll();
		safeReconciliation.enterauthorizationEmployee(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		safeReconciliation.clickOnAuthCancelButton();
		safeReconciliation.clickOnReconButton();
		boolean status = safeReconciliation.authCashierWindowIsEmpty();
		if (status) {
			test.log(LogStatus.PASS, "Auth user id and password fields are empty");
		} else {
			test.log(LogStatus.FAIL, "Auth user id and password fields are not empty");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	// This test case will verify, if check button flag is set to N The it will be
	// disable for user action
	@Test(enabled = true)
	public void TC38verifyCheckDetailsButtonIsEnableSafeRecon() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','N');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		try {
			boolean checkAuditButtonStatus = safeReconciliation.verifyISCheckButtonEnable();
			if (checkAuditButtonStatus == false) {
				test.log(LogStatus.PASS,
						"Check details button is disable & Status of button is :" + checkAuditButtonStatus);
			} else {
				test.log(LogStatus.FAIL,
						"Check details button is enable & Status of button is :" + checkAuditButtonStatus);
				softAssert.assertTrue(false, "not verified");
			}

			softAssert.assertAll();
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
					+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + "end;");
		}
		driver.navigate().refresh();
		Thread.sleep(2000);
		boolean checkAuditButtonStatus = safeReconciliation.verifyISCheckButtonEnable();
		if (checkAuditButtonStatus == true) {
			test.log(LogStatus.PASS, "Check details button is enable & Status of button is :" + checkAuditButtonStatus);
		} else {
			test.log(LogStatus.FAIL,
					"Check details button is disable & Status of button is :" + checkAuditButtonStatus);
			softAssert.assertTrue(false, "not verified");
		}

		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC39verifyINCLUDE_AR_CHARGESSafeRecon() throws Exception {

		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('INCLUDE_AR_CHARGES');\r\n"
				+ "  ret := tmxgbl.set_flag('INCLUDE_AR_CHARGES','N');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		try {
			boolean kmChargeButtonStatus = cashDrawerReconciliation.verifyIsKMChargeDisplayed();
			if (kmChargeButtonStatus == false) {
				test.log(LogStatus.PASS, "KM charge button is disable & Status of button is :" + kmChargeButtonStatus);
			} else {
				test.log(LogStatus.FAIL, "KM charge  button is enable & Status of button is :" + kmChargeButtonStatus);
				softAssert.assertTrue(false, "not verified");
			}

			softAssert.assertAll();
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('INCLUDE_AR_CHARGES');\r\n"
					+ "  ret := tmxgbl.set_flag('INCLUDE_AR_CHARGES','Y');\r\n" + "end;");
		}
		driver.navigate().refresh();
		Thread.sleep(2000);
		boolean kmChargeButtonStatus = cashDrawerReconciliation.verifyIsKMChargeDisplayed();
		if (kmChargeButtonStatus == true) {
			test.log(LogStatus.PASS, "KM charge button is enable & Status of button is :" + kmChargeButtonStatus);
		} else {
			test.log(LogStatus.FAIL, "KM charge button is disable & Status of button is :" + kmChargeButtonStatus);
			softAssert.assertTrue(false, "not verified");
		}

		softAssert.assertAll();

	}

	@Test(enabled = true)
	public void TC40verifySAFE_RECON_IN_PROCESS_OnSAfeReconScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
			String siteNum = reader.getCellData("MenuSelection", 0, 2);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','Y');\r\n" + "end;");		
			menuSelection.selectCurrentSite(siteNum);
			eleUtil.doStaticWait(config.getSmallWait());
			menuSelection.clickOnExpandAllButton();
			eleUtil.doStaticWait(config.getSmallWait());
			menuSelection.clickOnSafeReconciliation();
			Thread.sleep(1000);
			try {
				String safeRecMessage = cashDrawerSetup.getSafeReconPopMessage();
				test.log(LogStatus.INFO, "Safe Recon Message is for site " + siteNum + ":" + safeRecMessage);
				if (safeRecMessage.contains("Safe Reconciliation appears to be already open at your site")) {
					test.log(LogStatus.PASS, "Safe Recon is in process:");
				} else {
					test.log(LogStatus.FAIL, "Safe Recon is not in process:");
					softAssert.assertTrue(false, "Not verified");
				}
				softAssert.assertAll();
				eleUtil.clickWhenReady(safeReconciliation.safeReconOkButton, config.getExplicitWait());
				eleUtil.doIsDisplayed(menuSelection.expandAllButton);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");
			}

		}
	}


