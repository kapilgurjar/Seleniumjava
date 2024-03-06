package com.rediron.apex.testCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class CashDrawerMaintenance extends BaseTest {
	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String drawerNo = reader.getCellData("CashDeclaration", 1, 2);
	String checkAmount = reader.getCellData("CashDeclaration", 2, 2);
	String checkNum = reader.getCellData("CashDeclaration", 3, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	public String assignToRegister = "R0364";
	public String cshBoxRecon = "13";

	// Cash drawer Setup test cases
	
	@Story("Cash drawer Setup")
	@Description("Verify login with valid credentails")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, groups = "CDS")
	public void TC01oginWithValidCredentials() throws Exception {
		String title = loginPage.Logintoapplication(userName, Utility.decodestring(password));
		//test.log(LogStatus.INFO, "Actual title is: " + title);
		Assert.assertEquals(title, Utility.getGlobalValue("title"));
	}

	@Story("Cash drawer Setup")
	@Description("Verifying that assign to register is mandatory filed")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC02verifyCurrencySectionValidationMessagesOnCashdrawerSetupScreen() throws Exception {
		String expected_Msg = reader.getCellData("CashDrawer", 4, 2);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup();
		//basePage.handleSafeReconWindow(siteNum);
		cashDrawerSetup.clickOnCurrencyOne();
		//eleUtil.doStaticWait(config.getMediumWait());
		String act_MSg = cashDrawerSetup.getPopWindowText();
		Assert.assertEquals(act_MSg, expected_Msg);
		cashDrawerSetup.clickOnOkButton();
	}

	@Story("Cash drawer Setup")
	@Description("Verify auth window with wrong user id and passowrd")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC03verifyAuthorizationWindowWithWrongcredentialsOnCashdrawerSetupScreen() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister).clickOnSaveButton();
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			//test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			//test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		softAssert.assertAll();
		cashDrawerSetup.enterCashierUserIdAndPass(cashierUserid, cashierPass);
		//test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		//test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		//test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
	}

	// This test will validate Assign to Register Lov Data from Data Base
	@Story("Cash drawer Setup")
	@Description("Verify Assign to Register Lov Data against DataBase")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC04verifyRegisterLovshouldbematchingtherecordsasperMMSOOnCashdrawerSetupScreen() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		// List<String> lovListDatabase = new ArrayList<>();
		List<String> lovListDatabase = Utility.getDataFromDatabase(
				"Select reg_id, reg_id from registr where post_site_no ='" + siteNum + "' and reg_id <> 'OEREG'",
				"REG_ID");

		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.clickOnLoadMoreRowsButton();
		List<String> registerListUI = cashDrawerSetup.getValueFromassignToRegister_listbox();
		boolean status = registerListUI.equals(lovListDatabase);
		Assert.assertTrue(status);
		softAssert.assertAll();
	}

	// This test case will calculate currency sum and validate with tender cash
	// amount
	@Story("Cash drawer Setup")
	@Description("Verify currency sum and validate with tender cash amount")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC05verify_Currency_SumOnCashdrawerSetupScreen() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		int currency_Sum = cashDrawerSetup.getCurrencySum(number);
		//test.log(LogStatus.INFO, "Expected currency sum :" + currency_Sum);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		//test.log(LogStatus.INFO, "Actaul currency sum :" + cashAmountSum_Tender);
		Assert.assertEquals(cashAmountSum_Tender, currency_Sum);
	}

	@Story("Cash drawer Setup")
	@Description("Verify rolls sum and validate with tender cash amount")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC06verify_rolls_SumOnCashdrawerSetupScreen() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		Thread.sleep(2000);
		double rolls_Sum = cashDrawerSetup.getRollsSum(number);
		//test.log(LogStatus.INFO, "Expected rolls sum :" + rolls_Sum);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		//test.log(LogStatus.INFO, "Actaul rolls sum from tender :" + cashAmountSum_Tender);
		Assert.assertEquals(cashAmountSum_Tender, rolls_Sum);
	}

	@Story("Cash drawer Setup")
	@Description("Verify loose sum and validate with tender cash amount")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true, invocationCount = 1)
	public void TC07verify_Loose_SumOnCashdrawerSetupScreen() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		double loose_Sum = cashDrawerSetup.getLooseSum(number);
		String rounded = String.format("%.2f", loose_Sum);
		double actLooseSum = Double.parseDouble(rounded);
		System.out.println(actLooseSum);
		//test.log(LogStatus.INFO, "Expected loose sum :" + actLooseSum);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		//test.log(LogStatus.INFO, "Actaul loose sum from tender :" + cashAmountSum_Tender);
		Assert.assertEquals(cashAmountSum_Tender, actLooseSum);
	}

	// This test will setup a new cash drawer and will match with data base
	@Story("Cash drawer Setup")
	@Description("Creating new cash drawer with HQ sites and validate into database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC08createNewCashDrawerwithRegidAndVerifySaveActionOnCashdrawerSetupScreen() throws Exception {
		String siteNo="108";
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNo).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		String floatAmount = cashDrawerSetup.getTheSuggestedFloatAmount();
		//test.log(LogStatus.INFO, "Suggested float amount is=" + floatAmount);
		int currency_Sum = cashDrawerSetup.getCurrencySum(number);
		double rolls_Sum = cashDrawerSetup.getRollsSum(number);
		double looseSum = cashDrawerSetup.getLooseSum(number);
		double totalSumCurr_rools_Loose = currency_Sum + rolls_Sum + looseSum;
		//test.log(LogStatus.INFO, "Total Sum currency coins is =" + totalSumCurr_rools_Loose);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		//test.log(LogStatus.INFO, "Actual cash amount from tender  =" + cashAmountSum_Tender);
		double tenderAmount = cashDrawerSetup.getValueFromTenderSection(number);
		//test.log(LogStatus.INFO, "tenderAmount is =" + tenderAmount);
		double totalAmount_Exp = totalSumCurr_rools_Loose + tenderAmount;
		//test.log(LogStatus.INFO, "Total Amount after sum=" + totalAmount_Exp);
		Assert.assertEquals(cashAmountSum_Tender, totalSumCurr_rools_Loose);
		double totalAmount_Act = cashDrawerSetup.getTotalAmount();
		//test.log(LogStatus.INFO, "Total amount from total amount text field:" + totalAmount_Act);
		Assert.assertEquals(totalAmount_Act, totalAmount_Exp);
		cashDrawerSetup.clickOnSaveButton().enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		String act_CashBox = cashDrawerSetup.getCahBoxNo();
		System.out.println(act_CashBox);
		//test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
		String query = "Select site_no,cashbox_no,reg_id,beg_total_amt from cshbox where cashbox_no=" + act_CashBox
				+ " and site_no=108 and REG_ID='" + assignToRegister + "'";
		String exp_CashBox = Utility.executeQuery(query, "CASHBOX_NO");
		System.out.println(exp_CashBox);
		//test.log(LogStatus.INFO, "Cash Box no from DB" + exp_CashBox);
		Assert.assertEquals(act_CashBox, exp_CashBox);
		//test.log(LogStatus.PASS, "Cash box no is saved into database");
	}

	@Story("Cash drawer Setup")
	@Description("Creating new cash drawer with remote sites and validate into database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC09createNewCashDrawerwithoutregidAndVerifySaveActionOnCashdrawerSetupScreen() throws Exception {
		List<String> siteNoList = new ArrayList<>(Arrays.asList("124", "125", "113", "114"));
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		// menuSelection.clickOnManagerOperations();
		for (int i = 0; i < siteNoList.size(); i++) {
			String siteNo = siteNoList.get(i);
			menuSelection.selectCurrentSite(siteNo);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton().clickOncashDrawerSetup();
			int currency_Sum = cashDrawerSetup.getCurrencySum(number);
			double rolls_Sum = cashDrawerSetup.getRollsSum(number);
			double looseSum = cashDrawerSetup.getLooseSum(number);
			double totalSumCurr_rools_Loose = currency_Sum + rolls_Sum + looseSum;
			//test.log(LogStatus.INFO, "Total Sum currency coins is =" + totalSumCurr_rools_Loose);
			double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
			//test.log(LogStatus.INFO, "Actual cash amount from tender  =" + cashAmountSum_Tender);
			double tenderAmount = cashDrawerSetup.getValueFromTenderSection(number);
			//test.log(LogStatus.INFO, "tenderAmount is =" + tenderAmount);
			double totalAmount_Exp = totalSumCurr_rools_Loose + tenderAmount;
			//test.log(LogStatus.INFO, "Total Amount after sum=" + totalAmount_Exp);
			Assert.assertEquals(cashAmountSum_Tender, totalSumCurr_rools_Loose);
			double totalAmount_Act = cashDrawerSetup.getTotalAmount();
			//test.log(LogStatus.INFO, "Total amount from total amount text field:" + totalAmount_Act);
			Assert.assertEquals(totalAmount_Act, totalAmount_Exp);
			cashDrawerSetup.clickOnSaveButton().enterCashierUserIdAndPass(userName, Utility.decodestring(password));
			String act_CashBox = cashDrawerSetup.getCahBoxNo();
			System.out.println(act_CashBox);
			//test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
			cashDrawerSetup.clickOnSafeRecOkButton();
			//test.log(LogStatus.INFO, "Clicking on ok button");
			String query = "Select site_no,cashbox_no,reg_id,beg_total_amt from cshbox where cashbox_no=" + act_CashBox
					+ " and site_no=" + siteNo + "";
			String exp_CashBox = Utility.executeQuery(query, "CASHBOX_NO");
			System.out.println(exp_CashBox);
			//test.log(LogStatus.INFO, "Cash Box no from DB" + exp_CashBox);
			Assert.assertEquals(act_CashBox, exp_CashBox);
			//test.log(LogStatus.PASS, "Cash box no is saved into database");
			//eleUtil.doClick(cashDrawerSetup.homePageLink);
		}
	}

	@Story("Cash drawer Setup")
	@Description("verify cancel button on auth window")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void verifyAuthCancelButtonOnCashdrawerSetupScreen() throws Exception {

		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister).getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		cashDrawerSetup.clickOnSaveButton();
		cashDrawerSetup.enterCashierUserIdAndPassAndClickOnCancel(userName, Utility.decodestring(password));
		// Thread.sleep(2000);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		//boolean isUserIdEmpty = eleUtil.readValueFromInput(cashDrawerSetup.authorizingCashierTextField).isEmpty();
		//Assert.assertEquals(isUserIdEmpty, true);
		//boolean isPasswordEmpty = eleUtil.readValueFromInput(cashDrawerSetup.passwordTextField).isEmpty();
		//Assert.assertEquals(isPasswordEmpty, true);
	}

	// This test case will validate clear button functionality
	@Story("Cash drawer Setup")
	@Description("verifying clear button")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void TC10verify_Clear_Button_cashDrawerSetUp() throws Exception {
		String expMsg = reader.getCellData("CashDrawer", 4, 5);
		System.out.println(expMsg);
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		//eleUtil.doClick(cashDrawerSetup.clearButton);
		String actMsg = cashDrawerSetup.getClearPopWindowText();
		//test.log(LogStatus.PASS, "Actual message is :" + actMsg);
		//test.log(LogStatus.PASS, "Expected message is :" + expMsg);
		Assert.assertEquals(actMsg, expMsg);
		Thread.sleep(2000);
		//Boolean status = eleUtil.readValueFromInput(cashDrawerSetup.currencyName1).isEmpty();
		//Assert.assertTrue(status);
	}

	// This test case will validate exit button functionality
	@Test(enabled = true)
	public void TC11verify_Exit_Button_CashDrawerSetUp() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		//eleUtil.doClick(cashDrawerSetup.exitButton);
		//eleUtil.AcceptAlertIfPresent();
		// Thread.sleep(2000);
//		Boolean status = eleUtil.isDisplayed(menuSelection.expandAllButton);
//		if (status == true) {
//			test.log(LogStatus.PASS, "Exit button functionality is working");
//
//		} else {
//
//			test.log(LogStatus.FAIL, "Exit button functionality is not working");
//
//			softAssert.assertTrue(false, " is not verified");
//		}
//		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC12verify_CurrencySection_With_Invalid_DataOnCashdrawerSetupScreen() throws Exception {
		String currencyAmount = "99999";
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		ArrayList<Integer> lengthList = cashDrawerSetup.getLengthOfRollsTextFiledInBillSection(currencyAmount);
		for (int e : lengthList) {
			if (e == 4) {
				//test.log(LogStatus.PASS, "Bills text field length is equal to 4");
			} else {
				//test.log(LogStatus.FAIL, "Bills text field length is not equal to 4");
			}
			softAssert.assertAll();
		}
	}

	// This test case will verify that site no and site name should be disabled by
	// default
	@Test(enabled = true)
	public void TC13verifySiteNoAndSiteNameAreDisabledOnCashdrawerSetupScreen() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup();
		// Thread.sleep(2000);
		String siteNoReadOnly = cashDrawerSetup.verifySiteNoTextField();
		Assert.assertTrue(siteNoReadOnly.contains("disabled"));
		String siteNameReadOnly = cashDrawerSetup.verifySiteNameTextField();
		Assert.assertTrue(siteNameReadOnly.contains("disabled"));

	}

	// This test case will verify that assign to search is working as per
	// requirement with valid data
	@Test(enabled = true)
	public void TC14verifyAssignToRegisterSearchWithValidDataOnCashdrawerSetupScreen() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField().sendAssignToRegisterSearch("5012");
		String text = cashDrawerSetup.getAssignToRegisterSearchText();
		Assert.assertEquals(text, "5012");

	}

	// This test case will verify that assign to search is working as per
	// requirement with invalid data
	@Test(enabled = true)
	public void TC15verifyAssignToRegisterSearchWithInValidDataOnCashdrawerSetupScreen() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.navigateToCashDrawerSetup().clickOnAssignToRegisterTextField();
		Thread.sleep(2000);
		cashDrawerSetup.sendAssignToRegisterSearch("4r733476");
		//eleUtil.isDisplayed(cashDrawerSetup.noResultFound);

	}

	// This test case will verify that if POS CASH DRAWER ASSIGNED set to null then
	// assign to register and Suggested Float Amount will not be visible to end user
	@Test(enabled = true)
	public void TC16verifyPOSCASHDRAWERASSIGNEDOnCashdrawerSetupScreen() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "',null);\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup();
		boolean assignToResgiterStatus = cashDrawerSetup.assignToRegisterIsDisplayed();
		try {
			if (assignToResgiterStatus) {
				//test.log(LogStatus.FAIL, "Assign To Resgiter Status is " + assignToResgiterStatus);
			} else {
				//test.log(LogStatus.PASS, "Assign To Resgiter Status is " + assignToResgiterStatus);
				softAssert.assertTrue(true, "verified");
			}
			softAssert.assertAll();
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");
		}
		//eleUtil.pageRefresh();
		//eleUtil.doStaticWait(config.getMediumWait());
		Assert.assertTrue(cashDrawerSetup.assignToRegisterIsDisplayed());
		Assert.assertTrue(cashDrawerSetup.suggestedFloatAmountIsDisplayed());

	}

	// This test case will verify suggested float amount
	@Test(enabled = true)
	public void TC17verifyTheSuggestedFloatAmountShouldBeComingFromTheQueryOnCashdrawerSetupScreen() throws Exception {
		String expectedFloatAmount = null;
		String Site = reader.getCellData("CashDrawer", 5, 2);
		int site_No = Integer.parseInt(Site);
		String reg_Id = reader.getCellData("CashDrawer", 6, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		// menuSelection.selectCurrentSite();
		menuSelection.navigateToCashDrawerSetup();
		String query = "Select sugg_float_amt FROM registr where post_site_no =" + site_No + " and reg_id= '" + reg_Id
				+ "'\r\n" + "";
		expectedFloatAmount = Utility.executeQuery(query, "SUGG_FLOAT_AMT");
		System.out.println("expectedFloatAmount is equql to" + expectedFloatAmount);
		String actuaFloatlAnmount = cashDrawerSetup.getTheSuggestedFloatAmount();
		System.out.println("floatActualAnmount is eqaul to " + actuaFloatlAnmount);
		Assert.assertEquals(actuaFloatlAnmount, expectedFloatAmount);

	}
	// This test case will verify If set then Cash box setup cannot be done, Exits
	// the UI with message.

	@Test(enabled = true)
	public void TC18verifySAFE_RECON_IN_PROCESSOnCashdrawerSetupScreen() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("MenuSelection");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("MenuSelection", 0, i);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','Y');\r\n" + "end;");
			menuSelection.selectCurrentSite(siteNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton().clickOncashDrawerSetup();
			Thread.sleep(1000);
			String safeRecMessage = cashDrawerSetup.getSafeReconPopMessage();
			//test.log(LogStatus.INFO, "Safe Recon Message is:" + safeRecMessage);
			Assert.assertTrue(safeRecMessage.contains("Safe reconciliation is currently in process."));
			cashDrawerSetup.clickOnSafeRecOkButton();
			//eleUtil.doStaticWait(config.getMediumWait());
			//eleUtil.doIsDisplayed(menuSelection.dailyOperations);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");
			Thread.sleep(2000);

		}
	}

	@Test(enabled = true)
	public void TC19verifyThatOnly_Depositabe_Tenders_Are_Shown_In_The_Tender_SectionOnCashdrawerSetupScreen()
			throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup();
		List<String> tenderNameUI = cashDrawerSetup.getTenderNameUI();
		tenderNameUI.forEach(e -> System.out.println(e));
		List<String> tenderNameDB = cashDrawerSetup.getTenderNameDB();
		tenderNameDB.forEach(e -> System.out.println(e));
		//List<String> commonElement = tenderNameUI.stream().filter(tenderNameDB::contains).collect(Collectors.toList());
		//test.log(LogStatus.INFO, "Common element is " + commonElement);
		boolean status = CollectionUtils.isEqualCollection(tenderNameUI, tenderNameDB);
		if (status) {
			//test.log(LogStatus.PASS, "Depositabe tenders are Shown in the tender section");
		} else {
			//test.log(LogStatus.FAIL, "Depositabe tenders are not Shown in the tender section");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	// Cash Drawer deceleration test cases start
	/*
	@Test(enabled = true)
	public void TC20verifySiteNoAndSiteNameTextFiledAreVisibleOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		boolean statusSiteNo = cashDrawerDeclaration.verifySiteNoIsPopualated();
		if (statusSiteNo) {
			test.log(LogStatus.PASS, "Site no text filed is displayed on UI ");
		} else {
			test.log(LogStatus.FAIL, "Site no text filed is not displayed on UI");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();

		boolean statusSiteName = cashDrawerDeclaration.verifySiteNameIsPopualated();

		if (statusSiteName) {
			test.log(LogStatus.PASS, "Site name text filed is displayed on UI ");
		} else {
			test.log(LogStatus.FAIL, "Site name text filed is not displayed on UI");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();

		String siteNoClassAttr = cashDrawerDeclaration.verifySiteNoIsDisabled();
		test.log(LogStatus.INFO, "Site no text field class attribute is " + siteNoClassAttr);
		Assert.assertTrue(siteNoClassAttr.contains("disabled"));
		//String siteNameClassAttr = cashDrawerDeclaration.verifySiteNameIsDisabled();
		//test.log(LogStatus.INFO, "Site name text field class attribute is " + siteNameClassAttr);
		//Assert.assertTrue(siteNameClassAttr.contains("disabled"));
	}

	@Test(enabled = true)
	public void TC21verifyExitButtonCashDrawerDecelaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		eleUtil.doClick(cashDrawerSetup.exitButton);
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
	public void TC22verifyCancelButtonCashDrawerDecelaration() throws Exception {
		String expectedWaringMsg = reader.getCellData("CashDrawer", 4, 4);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		// Cancel button and clear button on cash drawer setup and cash drawer
		// deceleration both have same xpath
		eleUtil.doClick(cashDrawerSetup.clearButton);
		eleUtil.AcceptAlertIfPresent();
		String waringText = cashDrawerDeclaration.getCancelButtonPopWindowText();
		test.log(LogStatus.INFO, "Popup window warning text is " + waringText);
		Assert.assertEquals(waringText, expectedWaringMsg);
		Thread.sleep(1000);
		cashDrawerDeclaration.clickOnPopupOkButton();
	}

	@Test(enabled = true)
	public void TC23verifyAuthorisationWindowWithInvalidcredentialsOnCashDrawerDeclaration() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerSetup.selectValueFromLOV(drawerNo);
		cashDrawerSetup.getCurrencySum("3");
		cashDrawerSetup.clickOnSaveButton();
		//cashDrawerDeclaration.clickOnPopupOkButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterauthorizationEmployeeCredentials(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Employee userid and password enter successfully");
		String validation_Message = cashDrawerDeclaration.getauthorizationWindowMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
	}

	@Test(enabled = true)
	public void TC24verifycashBoxLovDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		List<String> cashBoxLovDB = Utility.getDataFromDatabase("select b.cashbox_no\r\n" + "from cshbox b\r\n"
				+ "where b.site_no = " + siteNum + "\r\n" + "and b.recon_fl = 'N'\r\n" + "and b.reg_id = '"
				+ assignToRegister + "'\r\n" + "order by b.cashbox_no", "CASHBOX_NO");
		cashBoxLovDB.forEach(e -> test.log(LogStatus.INFO, "Cash Box Lov DB values is " + e));
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerDeclaration.clickOnLoadMoreRowsButton();
		List<String> cashBoxLovUI = eleUtil.getElementsTextList(cashDrawerDeclaration.cashBoxLovList);
		cashBoxLovUI.forEach(e -> test.log(LogStatus.INFO, "Cash Box Lov UI values is " + e));
		test.log(LogStatus.INFO, "Cash box lov data from UI" + cashBoxLovUI);
		test.log(LogStatus.INFO, "Cash box lov data from DB" + cashBoxLovDB);
		List<String> differenceBetweenTwoList = new ArrayList<>(cashBoxLovUI);
		differenceBetweenTwoList.removeAll(cashBoxLovDB);
		test.log(LogStatus.INFO, "Diffrence between two list is " + differenceBetweenTwoList);
		if (CollectionUtils.isEqualCollection(cashBoxLovDB, cashBoxLovUI)) {
			test.log(LogStatus.PASS, "Cash box lov data is matching with MMSO UI");

		} else {

			test.log(LogStatus.FAIL, "Cash box lov data is not matching with MMSO UI");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC25verifyRegLovDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);

		List<String> assignToRegisterDB = Utility.getDataFromDatabase("Select distinct reg_id\r\n"
				+ "from cshbox where site_no =" + siteNum + "\r\n" + "and reg_id <> 'OEREG'\r\n" + "ORDER BY REG_ID",
				"REG_ID");
		assignToRegisterDB.forEach(e -> System.out.println(e));
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> assignToRegisterUI = eleUtil.getElementsTextList(cashDrawerDeclaration.AssignToRegisterLovList);
		test.log(LogStatus.INFO, "Assign to Register Lov Data form UI:" + assignToRegisterUI);
		test.log(LogStatus.INFO, "Assign to Register Lov Data form DB:" + assignToRegisterDB);
		List<String> differenceBetweenTwoList = new ArrayList<>(assignToRegisterUI);
		differenceBetweenTwoList.removeAll(assignToRegisterDB);
		test.log(LogStatus.INFO, "Diffrence between two list is " + differenceBetweenTwoList);
		if (CollectionUtils.isEqualCollection(assignToRegisterDB, assignToRegisterUI)) {
			test.log(LogStatus.PASS, "Assign To Register lov data is matching with MMSO UI");

		} else {

			test.log(LogStatus.FAIL, "Assign To Register lov data is not matching with MMSO UI");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC26verifyHeaderFieldsGreyedOutOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		String siteNoColor = eleUtil.doGetCssValue(cashDrawerDeclaration.siteNo, "background-color");
		Assert.assertEquals(siteNoColor, "rgba(228, 228, 228, 1)");
		test.log(LogStatus.INFO, "Background color is " + siteNoColor);
		String siteNameColor = eleUtil.doGetCssValue(cashDrawerDeclaration.siteName, "background-color");
		test.log(LogStatus.INFO, "Background color is " + siteNameColor);
		Assert.assertEquals(siteNameColor, "rgba(228, 228, 228, 1)");
	}

	@Test(enabled = true)
	public void TC27verifyTenderCashAmountInCashDrawerDeclaration() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerSetup.selectValueFromLOV(drawerNo);
		Thread.sleep(2000);
		int currency_Sum = cashDrawerSetup.getCurrencySum(number);
		double looseSum = cashDrawerSetup.getLooseSum(number);
		double rolls_Sum = cashDrawerSetup.getRollsSum(number);
		double expCashSum = currency_Sum + rolls_Sum + looseSum;
		test.log(LogStatus.INFO, "Expected rolls sum :" + rolls_Sum);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		test.log(LogStatus.INFO, "Actaul rolls sum from tender :" + cashAmountSum_Tender);
		Assert.assertEquals(cashAmountSum_Tender, expCashSum);
	}

	@Test(enabled = true)
	public void TC30changesIncurrencyCoinOtherTendersOnCashDrawerDeclaration() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerSetup.selectValueFromLOV("73");
		Thread.sleep(1000);
		int currency_Sum = cashDrawerSetup.getCurrencySum(number);
		double rolls_Sum = cashDrawerSetup.getRollsSum(number);
		double looseSum = cashDrawerSetup.getLooseSum(number);
		double totalSumCurr_rools_Loose = currency_Sum + rolls_Sum + looseSum;
		test.log(LogStatus.INFO, "Total Sum currency coins is =" + totalSumCurr_rools_Loose);
		double cashAmountSum_Tender = cashDrawerSetup.getValueFromCash();
		test.log(LogStatus.INFO, "Actual cash amount from tender  =" + cashAmountSum_Tender);
		cashDrawerDeclaration.clickOnCheckDetailsButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.getCheckDetailsTableSum(checkAmount, checkNum);
		Thread.sleep(2000);
		double checkAmount = Double.parseDouble(cashDrawerDeclaration.getCheckAmount());
		double tenderAmount = cashDrawerDeclaration.getValueFromTenderSection(number);
		System.out.println("Tender Amount is " + tenderAmount);
		test.log(LogStatus.INFO, "Check amount form tender section is : " + checkAmount);
		double actaulTotal = cashAmountSum_Tender + tenderAmount;
		System.out.println("total actual after addidtion of all section " + actaulTotal);
		Assert.assertEquals(cashAmountSum_Tender, cashAmountSum_Tender);
		double totalAmount = cashDrawerDeclaration.getTotalAmount();
		System.out.println("total expected after addidtion of all section" + totalAmount);
		Assert.assertEquals(actaulTotal, totalAmount);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		Thread.sleep(2000);
		if (cashDrawerDeclaration.verifyCashDrawerDeclared()) {
			test.log(LogStatus.PASS, "Cash Drawer is Declared successfully");

		} else {

			test.log(LogStatus.FAIL, "Cash Drawer is not Declared successfully");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC31verifyTheAssignToRegisterValidationMessageOnCashDrawerDeclaration() throws Exception {
		String selectAssignToRegisterExp = reader.getCellData("CashDeclaration", 0, 2).toLowerCase().trim();
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		eleUtil.doClick(cashDrawerDeclaration.cashDrawerTextField);
		String selectAssignToRegisterAct = cashDrawerDeclaration.getauthorizationWindowMessage().toLowerCase().trim();
		test.log(LogStatus.INFO, "Assign to register validation message is :" + selectAssignToRegisterAct);
		Assert.assertEquals(selectAssignToRegisterAct, selectAssignToRegisterExp);
		test.log(LogStatus.PASS, "Actaual message is equal to expected message");

	}

	@Test(enabled = true)
	public void TC32verifyTheCashDrawerValidationMessageOnCashDrawerDeclaration() throws Exception {
		String selectCashDrawerExp = reader.getCellData("CashDeclaration", 0, 3);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		eleUtil.doClick(cashDrawerSetup.currencyName1);
		String selectCashDrawerAct = cashDrawerDeclaration.getauthorizationWindowMessage();
		test.log(LogStatus.INFO, "Assign to register validation message is :" + selectCashDrawerAct);
		Assert.assertEquals(selectCashDrawerAct, selectCashDrawerExp);
		test.log(LogStatus.PASS, "Actaual message is equal to expected message");

	}

	@Test(enabled = true)
	public void TC33verifyPOSCASHDRAWERASSIGNEDOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "',null);\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		boolean assignToResgiterStatus = cashDrawerSetup.assignToRegisterIsDisplayed();
		try {
			if (assignToResgiterStatus) {
				test.log(LogStatus.FAIL, "Assign To Resgiter Status is " + assignToResgiterStatus);
			} else {
				test.log(LogStatus.PASS, "Assign To Resgiter Status is " + assignToResgiterStatus);
			}

			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println(e);
		}
		// Updating the POS CASH DRAWER ASSIGNED to R
		finally {
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");
		}

		driver.navigate().refresh();
		Thread.sleep(2000);
		cashDrawerSetup.assignToRegisterIsDisplayed();

	}

	@Test(enabled = true)
	public void TC34verifyCheckdetailsValueISAddedToCheckFieldOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerSetup.selectValueFromLOV(drawerNo);
		Thread.sleep(2000);
		cashDrawerDeclaration.clickOnCheckDetailsButton();
		cashDrawerDeclaration.getCheckDetailsTableSum(checkAmount, checkNum);
		Thread.sleep(3000);

	}

	@Test(enabled = true)
	public void TC35verifyThatAssignToRegisterIsMandatoryOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		String actaulMessage = cashDrawerDeclaration.getAssignToRegisterValidationMessage();
		test.log(LogStatus.INFO, "Actual Message is:" + actaulMessage);
		String expMessage = reader.getCellData("CashDeclaration", 0, 2);
		test.log(LogStatus.INFO, "Actual Message is:" + expMessage);
		Assert.assertEquals(actaulMessage, expMessage);
		test.log(LogStatus.PASS, "Actaul message and expected message are same");

	}

	@Test(enabled = true)
	public void TC36verifyThatCashDrawerIsMandatoryOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		eleUtil.doClick(cashDrawerSetup.currencyName1);
		String actaulMessage = cashDrawerDeclaration.getAssignToCashDrawerMessage();
		test.log(LogStatus.INFO, "Actual Message is:" + actaulMessage);
		String expMessage = reader.getCellData("CashDeclaration", 0, 3);
		test.log(LogStatus.INFO, "Actual Message is:" + expMessage);
		Assert.assertEquals(actaulMessage, expMessage);
		test.log(LogStatus.PASS, "Actaul message and expected message are same");
	}

	@Test(enabled = true)
	public void TC37verifyThatAssignToRegisterSearchIsWorkingWithValidDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerDeclaration.enterValueInAssignToRegisterSearch(assignToRegister);
		// Cash Drawer And setup both have same method to verify search first element
		String actualText = cashDrawerSetup.getAssignToRegisterSearchText();
		Assert.assertEquals(actualText, assignToRegister);
		test.log(LogStatus.PASS, "Search is working");
	}

	@Test(enabled = true)
	public void TC38verifyThatAssignToRegisterSearchIsWorkingWithInValidDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerDeclaration.enterValueInAssignToRegisterSearch("abcd");
		// Cash Drawer And setup both have same method to verify search first element
		eleUtil.isDisplayed(cashDrawerSetup.noResultFound);
		test.log(LogStatus.PASS, "Search is showing no result found on UI");
	}

	@Test(enabled = true)
	public void TC39verifyThatCashDrawerLovSearchIsWorkingWithValidDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		Thread.sleep(2000);
		System.out.println(drawerNo);
		cashDrawerDeclaration.enterValueInCashDrawerLovSearch(drawerNo);
		Thread.sleep(2000);
		String actualText = cashDrawerSetup.getAssignToRegisterSearchText();
		Assert.assertEquals(actualText, drawerNo);
		test.log(LogStatus.PASS, "Search is working");
	}

	@Test(enabled = true)
	public void TC40verifyThatCashDrawerLovIsWorkingWithInValidDataOnCashDrawerDeclaration() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterValueInCashDrawerLovSearch("8987");
		Thread.sleep(3000);
		// Cash Drawer And setup both have same method to verify search first element
		eleUtil.isDisplayed(cashDrawerSetup.noResultFound);
		test.log(LogStatus.PASS, "Search is showing no result found on UI");
	}

	// Cash Drawer Reconciliation Test Cases
	/*
	 * This test case will calculate actual amount, exp amount, overshot and verify
	 * the amount
	 
	@Test(enabled = true)
	public void TC41verifyActualExpectedAndOverShortAmountOnCashDrawerReconciliation() throws Exception {
//		Random random = new Random();
//		int num = random.nextInt(99) + 1;
//		//String number = String.valueOf(num);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String number = "15";
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		double totalCashDrawerSetUp = cashDrawerSetup.getTotalAmount();
		test.log(LogStatus.INFO, "Cash Drawer Setup total amount" + totalCashDrawerSetUp);
		cashDrawerSetup.clickOnSaveButton();
		cashDrawerSetup.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		String act_CashBox = cashDrawerSetup.getCahBoxNo();
		System.out.println(act_CashBox);
		test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
		cashDrawerSetup.clickOnOkButton();
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		eleUtil.doClick(cashDrawerSetup.exitButton);
		eleUtil.AcceptAlertIfPresent();
		// Now Declaring the above cash box number
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOncashDrawerDecalaration();
		cashDrawerDeclaration.clickAssignToLovBtn();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerDeclaration.clickCASHDRAWERlovBtn();
		cashDrawerDeclaration.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(act_CashBox);
		Thread.sleep(1000);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		cashDrawerDeclaration.clickOnCheckDetailsButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.getCheckDetailsTableSum(checkAmount, checkNum);
		Thread.sleep(2000);
		cashDrawerDeclaration.getValueFromTenderSection(number);
		double totalAmount = cashDrawerDeclaration.getTotalAmount();
		test.log(LogStatus.INFO, "Total amount from cash drawer declaration" + totalAmount);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		Thread.sleep(3000);
		cashDrawerSetup.clickOnOkButton();
		Thread.sleep(3000);
		eleUtil.doClick(cashDrawerSetup.exitButton);
		eleUtil.AcceptAlertIfPresent();
		// Cash drawer reconciliation
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerReconciliation.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(act_CashBox);

		Thread.sleep(3000);
		double totalExpected = Double.parseDouble(cashDrawerReconciliation.gettotalExpected());
		test.log(LogStatus.INFO, "Total expected amount from cash drawer reconciliation: " + totalExpected);
		Assert.assertEquals(totalExpected, totalCashDrawerSetUp);
		Thread.sleep(1000);
		double totalActaul = Double.parseDouble(cashDrawerReconciliation.gettotalActaul());
		test.log(LogStatus.INFO, "Total actual amount from cash drawer reconciliation:: " + totalActaul);
		Thread.sleep(1000);
		double totalActaulFloatAmount = Double.parseDouble(cashDrawerReconciliation.getActualFloatAmount());
		Assert.assertEquals(totalActaulFloatAmount, totalExpected);
		double overShortAct = totalActaul - totalExpected;
		test.log(LogStatus.INFO, "Total actual over short value from UI is: " + overShortAct);
		Thread.sleep(1000);
		double overShortExp = Double.parseDouble(cashDrawerReconciliation.getoverShort());
		Assert.assertEquals(overShortExp, overShortAct);
		cashDrawerSetup.clickOnSaveButton();
		cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));

		String query = "Select site_no,cashbox_no,recon_dt,recon_fl,manager_id,off_amt,actual_amt,"
				+ " expected_amt from cshbox where site_no=" + siteNum + " and cashbox_no=" + act_CashBox + "";

		String offAmount = Utility.executeQuery(query, "OFF_AMT");
		test.log(LogStatus.INFO, "Total actual over short value from DB is: " + offAmount);
		Assert.assertEquals(Double.parseDouble(offAmount), overShortAct);
		String actualAMT = Utility.executeQuery(query, "ACTUAL_AMT");
		test.log(LogStatus.INFO, "Total actual amount value from DB is: " + actualAMT);
		Assert.assertEquals(Double.parseDouble(actualAMT), totalActaul);
		String expectedAMT = Utility.executeQuery(query, "EXPECTED_AMT");
		test.log(LogStatus.INFO, "Total expected amount value from DB is: " + expectedAMT);
		Assert.assertEquals(Double.parseDouble(expectedAMT), totalExpected);

	}

	@Test(enabled = true)
	public void TC42VerifySiteNoAndSiteNameAreDisabledOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		String siteNameAttr = cashDrawerReconciliation.getSiteNoClassAttributes();
		Assert.assertTrue(siteNameAttr.contains("disabled"));
		String siteNoAttr = cashDrawerReconciliation.getSiteNameClassAttributes();
		Assert.assertTrue(siteNoAttr.contains("disabled"));

	}

	@Test(enabled = true)
	public void TC43VerifyCancelButtonOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String expectedWaringMsg = reader.getCellData("CashDrawer", 4, 5);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit(
					"Update cshbox set inuse_fl='N' where site_no=108 and cashbox_no =" + cshBoxRecon + "");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}

		cashDrawerSetup.getCurrencySum("77");
		// Cancel button and clear button on cash drawer setup and cash drawer
		// deceleration both have same xpath
		eleUtil.doClick(cashDrawerSetup.clearButton);
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		String waringText = cashDrawerReconciliation.getWindowPopMessage();
		test.log(LogStatus.INFO, "Popup window warning text is " + waringText);
		Assert.assertEquals(waringText, expectedWaringMsg);
	}

	@Test(enabled = true)
	public void TC44VerifyThatCashDrawerTextFieldIsAMandatoryOnCashDrawerReconciliation() throws Exception {
		String selectCashDrawerExp = reader.getCellData("CashDeclaration", 0, 3);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		Thread.sleep(3000);
		cashDrawerReconciliation.clickOnCheckDetailsButton();
		Thread.sleep(1000);
		String uIPopMessage = cashDrawerReconciliation.getWindowPopMessage();
		System.out.println(uIPopMessage);
		Assert.assertEquals(uIPopMessage, selectCashDrawerExp);

	}

	@Test(enabled = true)
	public void TC44verifySAFE_RECON_IN_PROCESS_OnReconScreenOnCashDrawerReconciliation() throws Exception {
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		int rowCount = reader.getRowCount("MenuSelection");
		for (int i = 2; i <= rowCount; i++) {
			String siteNum = reader.getCellData("MenuSelection", 0, i);
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','Y');\r\n" + "end;");
			menuSelection.selectCurrentSite(siteNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnCashDrawerReconciliation();
			Thread.sleep(1000);

			try {
				String safeRecMessage = cashDrawerReconciliation.getWindowPopMessage();
				test.log(LogStatus.INFO, "Safe Recon Message is:" + safeRecMessage);
				if (safeRecMessage.contains("Safe reconciliation is currently in process.")) {
					test.log(LogStatus.PASS, "Safe Recon is in process:");
				} else {
					test.log(LogStatus.FAIL, "Safe Recon is not in process:");
					softAssert.assertTrue(false, "Not verified");
				}
				softAssert.assertAll();
				eleUtil.doStaticWait(config.getMediumWait());
				eleUtil.doClick(cashDrawerReconciliation.okButton);
				eleUtil.doIsDisplayed(menuSelection.dailyOperations);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNum + "','N');\r\n" + "end;");
				Thread.sleep(2000);
			}

		}
	}

	// This test case will verify that if POS CASH DRAWER ASSIGNED set to null then
	// assign to register and Suggested Float Amount will not be visible to end user
	@Test(enabled = true)
	public void TC45verifyPOSCASHDRAWERASSIGNEDRECONOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "',null);\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		boolean assignToResgiterStatus = cashDrawerReconciliation.assignToRegisterIsDisplayed();
		try {
		if (assignToResgiterStatus) {
			test.log(LogStatus.FAIL, "Assign To Resgiter Status is " + assignToResgiterStatus);
		} else {
			test.log(LogStatus.PASS, "Assign To Resgiter Status is " + assignToResgiterStatus);
		}
		softAssert.assertAll();
		}catch(Exception e) {
			System.out.println(e);
			
			// Updating the POS CASH DRAWER ASSIGNED to R	
		}finally{
		
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");
		}
		driver.navigate().refresh();
		Thread.sleep(2000);

		boolean status = cashDrawerReconciliation.assignToRegisterIsDisplayed();
		if (status) {
			System.out.println("POS_CASH_DRAWER_ASSIGNED ");
		} else {
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");
		}

	}

	// This test case will verify, if check button flag is set to N The it will be
	// disable for user action
	@Test(enabled = true)
	public void TC46verifyCheckDetailsButtonIsEnableOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('ENABLE_CHECK_DETAIL_BTN');\r\n"
				+ "  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','N');\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		try {
			boolean checkDetailsButtonStatus = cashDrawerReconciliation.verifyISCheckButtonEnable();
			if (checkDetailsButtonStatus == false) {
				test.log(LogStatus.PASS,
						"Check details button is disable & Status of button is :" + checkDetailsButtonStatus);
			} else {
				test.log(LogStatus.FAIL,
						"Check details button is enable & Status of button is :" + checkDetailsButtonStatus);
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
		boolean checkDetailsButtonStatus = cashDrawerReconciliation.verifyISCheckButtonEnable();
		if (checkDetailsButtonStatus == true) {
			test.log(LogStatus.PASS,
					"Check details button is enable & Status of button is :" + checkDetailsButtonStatus);
		} else {
			test.log(LogStatus.FAIL,
					"Check details button is disable & Status of button is :" + checkDetailsButtonStatus);
			softAssert.assertTrue(false, "not verified");
		}

		softAssert.assertAll();
	}

	// This test case will verify force declaration, if a user has not declared the
	// cashbox then it will force to declare
	@Test(enabled = true, groups = { "Cash Drawer Recon" })
	public void TC47verifyCSHBOX_FORCE_DECLARATIONOnCashDrawerReconciliation() throws Exception {
		// Creating a new cash drawer no
		Utility.executeandcommit("\r\n" + "declare \r\n" + "  p_flag Varchar2(10);\r\n" + "  ret Varchar2(10);\r\n"
				+ "begin\r\n" + "    ret := tmxgbl.setsite_flag('CSHBOX_FORCE_DECLARATION',108,'Y');\r\n" + "\r\n"
				+ "  \r\n" + "end;");
		String number = "74";
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		Thread.sleep(2000);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.clickOnSaveButton();
		cashDrawerSetup.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		String act_CashBox = cashDrawerSetup.getCahBoxNo();
		System.out.println(act_CashBox);
		test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
		cashDrawerSetup.clickOnOkButton();
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		eleUtil.doClick(cashDrawerSetup.exitButton);
		eleUtil.AcceptAlertIfPresent();
		// Now Reconciliation the above cash box number
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		eleUtil.doClick(cashDrawerReconciliation.assignToRegisterLovButton);
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		eleUtil.doClick(cashDrawerReconciliation.cashDrawerNoLovBtn);
		Thread.sleep(2000);
		cashDrawerReconciliation.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(act_CashBox);
		Thread.sleep(2000);
		boolean status = eleUtil.doIsDisplayed(cashDrawerReconciliation.declareWindow);
		if (status) {
			test.log(LogStatus.PASS, "Declaration window is visible");
		} else {
			test.log(LogStatus.FAIL, "Declaration window is not visible");
		}
		String text = eleUtil.doGetText(cashDrawerReconciliation.declareWindow);
		test.log(LogStatus.INFO, "Declaration window text is :" + text);
		cashDrawerSetup.clickOnOkButton();
		// No control should be move to cash drawer declaration screen
		String declarationTitle = eleUtil.doGetPageTitleIs("Cash Drawer Declaration", config.getImplicitWait());
		test.log(LogStatus.INFO, "Control move to cash Drawer declaration screen and title is " + declarationTitle);
		if (declarationTitle.equalsIgnoreCase("Cash Drawer Declaration")) {
			test.log(LogStatus.PASS, "User is on the cash drawer declaration screen");
		} else {
			test.log(LogStatus.PASS, "User is not on the cash drawer declaration screen");
			softAssert.assertTrue(false, declarationTitle + " is not verified");
		}
		softAssert.assertAll();

		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		Thread.sleep(3000);
		cashDrawerSetup.clickOnOkButton();
		// No control should be move to cash drawer reconciliation screen
		String reconciliationTitle = eleUtil.doGetPageTitleIs("Cash Drawer Reconciliation", config.getImplicitWait());

		if (reconciliationTitle.equalsIgnoreCase("Cash Drawer Reconciliation")) {
			test.log(LogStatus.PASS, "User is on the cash drawer Reconciliation screen");
		} else {
			test.log(LogStatus.PASS, "User is not on the cash drawer Reconciliation screen");
			softAssert.assertTrue(false, reconciliationTitle + " is not verified");
		}
		softAssert.assertAll();
		test.log(LogStatus.INFO,
				"Control move to cash Drawer Reconciliation screen and title is " + reconciliationTitle);
		Thread.sleep(3000);

	}

	@Test(enabled = true, groups = { "Cash Drawer Recon" })
	public void TC48verifySuggestedFloatAmountInCashDrawerRecon() throws Exception {
		// Creating a new cash drawer no
		String query = "SELECT reg_id, sugg_float_amt FROM registr WHERE	post_site_no = 108 AND reg_id='REG20'";
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		eleUtil.doClick(cashDrawerReconciliation.assignToRegisterLovButton);
		cashDrawerSetup.selectValueFromLOV("REG20");
		eleUtil.doClick(cashDrawerReconciliation.cashDrawerNoLovBtn);
		Thread.sleep(2000);
		cashDrawerReconciliation.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV("8");
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit("Update cshbox set inuse_fl='N' where site_no=108 and cashbox_no =8");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			eleUtil.doClick(cashDrawerReconciliation.assignToRegisterLovButton);
			cashDrawerSetup.selectValueFromLOV("REG20");
			eleUtil.doClick(cashDrawerReconciliation.cashDrawerNoLovBtn);
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV("8");
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}
		String suggestedFloatDB = Utility.executeQuery(query, "SUGG_FLOAT_AMT");
		double suggestedFloat = Double.parseDouble(suggestedFloatDB);
		System.out.println("Suggested Amount from DB: " + suggestedFloat);
		if (suggestedFloat > 0) {
			Thread.sleep(2000);
			double suggestedfloatAmount = cashDrawerReconciliation.getSuggestedFloatAmount();
			Assert.assertEquals(suggestedfloatAmount, suggestedFloat);
		} else {
			test.log(LogStatus.INFO, "suggested float Amount is less than Zero");
		}
	}

	// This test case will verify actual amount which is coming from the cash drawer
	// setUp Screen at the time of creation
	@Test(enabled = true, groups = { "Cash Drawer Recon" })
	public void TC49verifyTheActualAmountOnCashDrawerReconciliation() throws Exception {
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
				.selectValueFromLOV(assignToRegister);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getValueFromTenderSection(number);
		double totalamount = cashDrawerSetup.getTotalAmount();
		System.out.println(totalamount);
		test.log(LogStatus.INFO, "Total Amount from cash drawer setUp Screen =" + totalamount);
		cashDrawerSetup.clickOnSaveButton();
		cashDrawerSetup.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		String act_CashBox = cashDrawerSetup.getCahBoxNo();
		System.out.println(act_CashBox);
		test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
		cashDrawerSetup.clickOnOkButton();
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		eleUtil.doClick(cashDrawerSetup.exitButton);
		eleUtil.AcceptAlertIfPresent();
		// Now Reconciliation the above cash box number
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		eleUtil.doClick(cashDrawerReconciliation.assignToRegisterLovButton);
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		eleUtil.doClick(cashDrawerReconciliation.cashDrawerNoLovBtn);
		Thread.sleep(2000);
		cashDrawerReconciliation.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(act_CashBox);
		Thread.sleep(2000);
		boolean status = eleUtil.doIsDisplayed(cashDrawerReconciliation.declareWindow);
		Assert.assertTrue(status);
		test.log(LogStatus.PASS, "Declare window is visible");
		String text = eleUtil.doGetText(cashDrawerReconciliation.declareWindow);
		System.out.println(text);
		cashDrawerSetup.clickOnOkButton();
		// No control should be move to cash drawer declaration screen
		String declarationTitle = eleUtil.doGetPageTitleIs("Cash Drawer Declaration", config.getImplicitWait());
		test.log(LogStatus.PASS, "Control move to cash Drawer declaration screen and title is " + declarationTitle);
		cashDrawerSetup.getCurrencySum(number);
		cashDrawerSetup.getRollsSum(number);
		cashDrawerSetup.getLooseSum(number);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(2000);
		cashDrawerDeclaration.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
		Thread.sleep(3000);
		cashDrawerSetup.clickOnOkButton();
		// No control should be move to cash drawer reconciliation screen
		String reconciliationTitle = eleUtil.doGetPageTitleIs("Cash Drawer Reconciliation", config.getImplicitWait());
		test.log(LogStatus.PASS,
				"Control move to cash Drawer Reconciliation screen and title is " + reconciliationTitle);
		Thread.sleep(3000);
		double actualamount = Double.parseDouble(cashDrawerReconciliation.getActualFloatAmount());
		test.log(LogStatus.INFO, "Actual amount from Cash Drawer Recon" + actualamount);
		System.out.println(actualamount);
		Assert.assertEquals(actualamount, totalamount);
	}

	//@Test(enabled = true)
	public void TC50verifyPOSCASHDRAWERASSIGNEDOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		// Checking flag value
		List<Object> flagValue = Utility
				.getFlagValueFromDBMS("declare\r\n" + "p_flag Varchar2(10);\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "dbms_output.enable(10000);\r\n" + "p_flag := tmxgbl.getsite_text('POS_CASH_DRAWER_ASSIGNED',"
						+ siteNum + ");\r\n" + "cout(p_flag);\r\n" + "end;");
		flagValue.remove(1);
		System.out.println(flagValue.get(0));
		Object flagType = flagValue.get(0);
		String flag = String.valueOf(flagType);
		try {

			if (flag.equals("R")) {

				Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "',null);\r\n"
						+ "end;");
				test.log(LogStatus.INFO, "Set Flag to N");
				loginpage.Logintoapplication(userName, Utility.decodestring(password));
				menuSelection.selectCurrentSite(siteNum);
				menuSelection.clickOnManagerOperations();
				menuSelection.clickOnExpandAllButton();
				menuSelection.clickOnCashDrawerReconciliation();
				try {
					boolean status = cashDrawerReconciliation.assignToRegisterIsDisplayed();
					System.out.println(status);
					if (status == false) {
						test.log(LogStatus.PASS, "Assign to register text field is not displayed ");
					} else {
						test.log(LogStatus.FAIL, "Assign to register text field is displayed ");
						softAssert.assertTrue(false, "Not verified");
					}
					softAssert.assertAll();
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else if (flag.equals("null")) {
				Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
						+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");
				test.log(LogStatus.INFO, "Set Flag to R");
				loginpage.Logintoapplication(userName, Utility.decodestring(password));
				menuSelection.selectCurrentSite(siteNum);
				menuSelection.clickOnManagerOperations();
				menuSelection.clickOnExpandAllButton();
				menuSelection.clickOnCashDrawerReconciliation();
				try {
					boolean status = cashDrawerReconciliation.assignToRegisterIsDisplayed();
					System.out.println(status);
					if (status == true) {
						test.log(LogStatus.PASS, "Assign to register text field is displayed ");
					} else {
						test.log(LogStatus.FAIL, "Assign to register text field is not displayed ");
						softAssert.assertTrue(false, "Not verified");
					}
					softAssert.assertAll();
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		} finally {
			Thread.sleep(2000);
			// Updating the POS CASH DRAWER ASSIGNED to R
			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','" + flagValue.get(0)
					+ "');\r\n" + "end;");
			test.log(LogStatus.INFO, "Set Flag to " + flagValue.get(0));
		}

		driver.navigate().refresh();
		Thread.sleep(2000);
		try {
			// If Flag value is R then Assign to register should be displayed on UI
			if (flag.equals("R")) {
				boolean status = cashDrawerReconciliation.assignToRegisterIsDisplayed();
				test.log(LogStatus.PASS, "Assign to register text field is displayed & status is = " + status);
			} else {
				// If Flag value is null then Assign to register should not be displayed on UI
				boolean status = cashDrawerReconciliation.assignToRegisterIsDisplayed();
				test.log(LogStatus.PASS, "Assign to register text field is not displayed  & status is = " + status);
			}
			// boolean status=cashDrawerReconciliation.assignToRegisterIsDisplayed();
			// test.log(LogStatus.PASS, "Assign to register text field is visible "+status);
		} catch (NullPointerException e) {
			System.out.println(e);
		}
	}

	/*
	 * This test case will verify while retrieving tender names CUSTCHG_TNDR will be
	 * shown based on this config based on include Y/N
	 
	@Test(enabled = true)
	public void TC51verifyINCLUDE_AR_CHARGESOnCashDrawerReconciliation() throws Exception {

		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('INCLUDE_AR_CHARGES');\r\n"
				+ "  ret := tmxgbl.set_flag('INCLUDE_AR_CHARGES','N');\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
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

	@Test
	public void TC52verifyCASH_RECON_CHECK_AUTO_VERIFYOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
				+ "  p_flag :=tmxgbl.get_flag('CASH_RECON_CHECK_AUTO_VERIFY');\r\n"
				+ "  ret := tmxgbl.setsite_flag('CASH_RECON_CHECK_AUTO_VERIFY',108,'N');\r\n" + "end;");
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit(
					"Update cshbox set inuse_fl='N' where site_no=" + siteNum + " and cashbox_no =" + cshBoxRecon + "");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}
		Thread.sleep(4000);
		cashDrawerReconciliation.clickOnCheckDetailsButton();

		try {
			boolean chckBoxStatus = cashDrawerReconciliation.verifyIsCheckBoxDisplayed();
			if (chckBoxStatus == false) {
				test.log(LogStatus.PASS, "Check details button is not displayed on UI & Status is  :" + chckBoxStatus);
			} else {
				test.log(LogStatus.FAIL, "Check details button is  displayed on UI & Status is :" + chckBoxStatus);
				softAssert.assertTrue(false, "not verified");
			}

			softAssert.assertAll();
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + "  p_flag :=tmxgbl.get_flag('CASH_RECON_CHECK_AUTO_VERIFY');\r\n"
					+ "  ret := tmxgbl.setsite_flag('CASH_RECON_CHECK_AUTO_VERIFY',108,'Y');\r\n" + "end;");
		}
	}

	@Test(enabled = true)
	public void TC53verifyRegisterLovshouldbematchingtherecordsasperMMSOOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "Select distinct reg_id from cshbox where site_no =" + siteNum
				+ " and reg_id <> 'OEREG' ORDER BY REG_ID";
		// List<String> lovListDatabase = new ArrayList<>();
		List<String> reg_IDDB = Utility.getDataFromDatabase(query, "REG_ID");
		test.log(LogStatus.INFO, "Reg Id from DB is : " + reg_IDDB);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerReconciliation.clickOnLoadMoreRowsButton_RegId();
		List<String> reg_IDUI = cashDrawerSetup.getValueFromassignToRegister_listbox();
		test.log(LogStatus.INFO, "Reg Id from UI is : " + reg_IDUI);
		boolean status = reg_IDUI.equals(reg_IDDB);
		if (status) {
			test.log(LogStatus.PASS, "" + reg_IDUI + " is verified");
		} else {
			test.log(LogStatus.FAIL, "" + reg_IDUI + " is not verified");
			softAssert.assertTrue(false, reg_IDUI + " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled = true)
	public void TC54verifyCashBoxshouldbematchingtherecordsasperMMSOWithReg_IDOnCashDrawerReconciliation()
			throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "select b.cashbox_no, e.full_name\r\n" + "	from cshbox b, employee e, empbysit ebs\r\n"
				+ " where (b.checkin_dt is null or\r\n" + "			 (b.checkin_dt >= tmxgbl.date_last_closed and\r\n"
				+ "			 b.checkin_dt in\r\n"
				+ "			 (select tran_dt from dlysite where site_no = 108)))\r\n" + "	 and b.site_no = 108\r\n"
				+ "	 and b.recon_fl = 'N'\r\n" + "	 and ebs.site_no(+) = 108\r\n"
				+ "	 and ebs.employee_id(+) = b.employee_id\r\n" + "	 and e.employee_id(+) = ebs.employee_id\r\n"
				+ "	 and b.oe_drawer_fl != 'Y'\r\n"
				+ "	 and (b.reg_id = 'R0364' OR (b.reg_id is null and b.manager_id='R0364' and  \r\n"
				+ "                      b.csh_rec_in_proc_fl= 'Y' and b.inuse_fl='Y')) \r\n"
				+ " 	 order by b.cashbox_no\r\n" + "";
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerReconciliation.clickOnLoadMoreRowsButton_RegId();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerReconciliation.clickOnLoadMoreRowsButton();
		List<String> cashBoxLovUI = cashDrawerReconciliation.getCashBOXValue();
		test.log(LogStatus.INFO, "Cash Box lov data from UI with Reg_id is : " + cashBoxLovUI);
		List<String> cashBoxLovDB = Utility.getDataFromDatabase(query, "CASHBOX_NO");
		test.log(LogStatus.INFO, "Cash Box lov data from DB with Reg_id is : " + cashBoxLovDB);

		boolean status = cashBoxLovUI.equals(cashBoxLovDB);
		if (status) {
			test.log(LogStatus.PASS, "" + cashBoxLovUI + " is verified");
		} else {
			test.log(LogStatus.FAIL, "" + cashBoxLovUI + " is not verified");
			softAssert.assertTrue(false, cashBoxLovUI + " is not verified");
		}
		softAssert.assertAll();

	}

	@Test(enabled = true)
	public void TC55verifyExitButtonIsWorkingOnCashDrawerReconciliation() throws Exception {

		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String expectedWaringMsg = reader.getCellData("CashDrawer", 4, 5);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit(
					"Update cshbox set inuse_fl='N' where site_no=" + siteNum + " and cashbox_no =" + cshBoxRecon + "");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}

		cashDrawerSetup.getCurrencySum("77");
		// Cancel button and clear button on cash drawer setup and cash drawer
		// deceleration both have same xpath
		eleUtil.doClick(cashDrawerSetup.exitButton);
		Thread.sleep(2000);
		eleUtil.AcceptAlertIfPresent();
		String waringText = cashDrawerReconciliation.getWindowPopMessage();
		test.log(LogStatus.INFO, "Popup window warning text is " + waringText);
		Assert.assertEquals(waringText, expectedWaringMsg);
		Thread.sleep(1000);
		//cashDrawerDeclaration.clickOnPopupOkButton();

	}

	@Test(enabled = true)
	public void TC56verifyAuthorizationWindowWithWrongCredentailOnCashDrawerReconciliation() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit(
					"Update cshbox set inuse_fl='N' where site_no=108 and cashbox_no =" + cshBoxRecon + "");
			System.out.println("Set to no use");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			eleUtil.doStaticWait(config.getMediumWait());
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}
		Thread.sleep(2000);
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(3000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		softAssert.assertAll();
		cashDrawerReconciliation.enterCashierUserIdAndPass(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = cashDrawerReconciliation.getWindowPopMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);

	}

	@Test(enabled = false)
	public void TC57verifyCoinsCurrencyandTenderSectionOnCashDrawerReconciliation() throws Exception {// TODO
		String number = "16";
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		// String expectedWaringMsg = reader.getCellData("CashDrawer", 4, 5);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV("81");
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit("Update cshbox set inuse_fl='N' where site_no=108 and cashbox_no =81");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV("81");
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}
		Thread.sleep(2000);
		double actualAmount = cashDrawerReconciliation.getValueFromTenderSectionActualAmount(number);
		double expectedAmount = cashDrawerReconciliation.getValueFromTenderSectionExpectedAmount();
		double totalActaul = Double.parseDouble(cashDrawerReconciliation.gettotalActaul());
		double totalExpected = Double.parseDouble(cashDrawerReconciliation.gettotalExpected());
		Assert.assertEquals(actualAmount, totalActaul);
		Assert.assertEquals(expectedAmount, totalExpected);

	}

	@Test(enabled = true)
	public void TC58verifyCashBoxshouldbematchingtherecordsasperMMSOWithoutRegIDOnCashDrawerReconciliation()
			throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "select b.cashbox_no, e.full_name\r\n" + "from cshbox b, employee e, empbysit ebs\r\n"
				+ "where (b.checkin_dt is null or (b.checkin_dt >= tmxgbl.get_date_last_closed(108)\r\n"
				+ "and b.checkin_dt in (select tran_dt from dlysite where site_no=108))) and\r\n"
				+ "b.site_no = 108 and b.recon_fl = 'N' and ebs.site_no(+) = 108 and\r\n"
				+ "ebs.employee_id(+) = b.employee_id and e.employee_id(+) = ebs.employee_id\r\n"
				+ "and b.oe_drawer_fl != 'Y' order by b.cashbox_no";
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "',null);\r\n" + "end;");
		try {
			menuSelection.clickOnCashDrawerReconciliation();
			// cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			// cashDrawerReconciliation.clickOnLoadMoreRowsButton_RegId();
			// cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			List<String> cashBoxLovUI = cashDrawerReconciliation.getCashBOXValue();
			System.out.println(cashBoxLovUI.size());
			test.log(LogStatus.INFO, "Cash Box lov data from UI with Reg_id is : " + cashBoxLovUI);
			List<String> cashBoxLovDB = Utility.getDataFromDatabase(query, "CASHBOX_NO");
			System.out.println(cashBoxLovDB.size());
			test.log(LogStatus.INFO, "Cash Box lov data from DB with Reg_id is : " + cashBoxLovDB);
			List<String> differenceBetweenTwoList = new ArrayList<>(cashBoxLovUI);
			differenceBetweenTwoList.removeAll(cashBoxLovDB);
			test.log(LogStatus.INFO, "Diffrence between two list is " + differenceBetweenTwoList);
			boolean status = CollectionUtils.isEqualCollection(cashBoxLovUI, cashBoxLovDB);
			if (status) {
				test.log(LogStatus.PASS, "" + cashBoxLovUI + " is verified");
			} else {
				test.log(LogStatus.FAIL, "" + cashBoxLovUI + " is not verified");
				softAssert.assertTrue(false, cashBoxLovUI + " is not verified");
			}
			softAssert.assertAll();

		}

		finally {

			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED','" + siteNum + "','R');\r\n" + "end;");

		}

	}

	@Test(enabled = true)
	public void TC59verifyCASH_RECON_USER_NOTDECL_USEROnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String number = "15";
		try {
			String query = "declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n" + "begin\r\n"
					+ "  ret := tmxgbl.set_flag('CASH_RECON_USER_NOT_DECL_USER','Y');  \r\n" + "end;";
			Utility.executeandcommit(query);
			loginPage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum).navigateToCashDrawerSetup().clickOnAssignToRegisterTextField()
					.selectValueFromLOV(assignToRegister);
			cashDrawerSetup.getCurrencySum(number);
			cashDrawerSetup.getRollsSum(number);
			cashDrawerSetup.getLooseSum(number);
			cashDrawerSetup.clickOnSaveButton();
			cashDrawerSetup.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
			String act_CashBox = cashDrawerSetup.getCahBoxNo();
			System.out.println(act_CashBox);
			test.log(LogStatus.INFO, "Cash Box no from UI" + act_CashBox);
			cashDrawerSetup.clickOnOkButton();
			Thread.sleep(2000);
			eleUtil.AcceptAlertIfPresent();
			eleUtil.doClick(cashDrawerSetup.exitButton);
			eleUtil.AcceptAlertIfPresent();
			// Now Declaring the above cash box number
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOncashDrawerDecalaration();
			cashDrawerDeclaration.clickAssignToLovBtn();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerDeclaration.clickCASHDRAWERlovBtn();
			cashDrawerDeclaration.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(act_CashBox);
			Thread.sleep(1000);
			cashDrawerSetup.getCurrencySum(number);
			cashDrawerSetup.getRollsSum(number);
			cashDrawerSetup.getLooseSum(number);
			cashDrawerDeclaration.clickOnCheckDetailsButton();
			Thread.sleep(2000);
			cashDrawerDeclaration.getCheckDetailsTableSum(checkAmount, checkNum);
			Thread.sleep(2000);
			cashDrawerDeclaration.getValueFromTenderSection(number);
			cashDrawerSetup.clickOnSaveButton();
			Thread.sleep(2000);
			cashDrawerDeclaration.enterauthorizationEmployeeCredentials(userName, Utility.decodestring(password));
			Thread.sleep(3000);
			eleUtil.doClick(cashDrawerReconciliation.okButton);
			Thread.sleep(3000);
			eleUtil.doClick(cashDrawerSetup.exitButton);
			eleUtil.AcceptAlertIfPresent();
			// Cash drawer reconciliation
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnCashDrawerReconciliation();
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(act_CashBox);
			cashDrawerSetup.clickOnSaveButton();
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
			String msg = cashDrawerReconciliation.getNotDeclaredMsg();
			if (msg.contains("Drawer Reconciliation cannot be completed by same user as Drawer Declare")) {
				test.log(LogStatus.PASS, "Not declared with same user");
			} else {
				test.log(LogStatus.FAIL, " declared with same user");
				softAssert.assertTrue(false, "not verified");
			}
			softAssert.assertAll();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Utility.executeandcommit("declare \r\n" + "  p_flag Varchar2(1);\r\n" + "  ret Varchar2(10);\r\n"
					+ "begin\r\n" + " ret := tmxgbl.set_flag('CASH_RECON_USER_NOT_DECL_USER','N'); \r\n" + "end;");
		}

	}

	// @Test
	public void TC60VerifySAFE_RECON_STATUSOnCashDrawerReconciliation() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnCashDrawerReconciliation();
		cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
		cashDrawerSetup.selectValueFromLOV(assignToRegister);
		cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		if (eleUtil.isDisplayed(cashDrawerReconciliation.inUseWindow)) {
			Utility.executeandcommit(
					"Update cshbox set inuse_fl='N' where site_no=108 and cashbox_no =" + cshBoxRecon + "");
			driver.navigate().refresh();
			eleUtil.AcceptAlertIfPresent();
			Thread.sleep(3000);
			cashDrawerReconciliation.clickOnAssignToRegisterLovButton();
			cashDrawerSetup.selectValueFromLOV(assignToRegister);
			cashDrawerReconciliation.clickOnCashDrawerNoLovBtn();
			Thread.sleep(2000);
			cashDrawerReconciliation.clickOnLoadMoreRowsButton();
			cashDrawerSetup.selectValueFromLOV(cshBoxRecon);
		} else if (eleUtil.isDisplayed(cashDrawerReconciliation.overRideWindow)) {
			eleUtil.doClick(cashDrawerReconciliation.okOverRideWindow);
			cashDrawerReconciliation.enterCashierUserIdAndPass(userName, Utility.decodestring(password));
		} else {
			System.out.println("Warning message is not coming");
		}
		Thread.sleep(2000);
		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.get(config.getWebsite());
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.navigateTOReconciliation();
		System.out.println(driver.getTitle());
		eleUtil.switchToParentWindow();
		System.out.println(driver.getTitle());
		cashDrawerSetup.clickOnSaveButton();
		Thread.sleep(5000);
	}
	*/

}
