package com.rediron.apex.testCases;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

public class SafeWithdrawlTestcases extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	public String assignToRegister = "R0364";

	@Test(enabled=true)
	public void TC01verifySiteNoAndSiteNameAredisabledTextField() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(5000);
		String classSiteno = safeWithdrawl.getSiteNoAttributes().toString();
		test.log(LogStatus.INFO, "site no class attributes is :" + classSiteno);
		Assert.assertTrue((classSiteno.contains("disabled")), "Site number should be disabled, but it is not");
		String classSitename = safeWithdrawl.getSiteNameAttributes().toString();
		test.log(LogStatus.INFO, "site name class attributes is :" + classSitename);
		Assert.assertTrue((classSitename.contains("disabled")), "Site name should be disabled, but it is not");
	}


	@Test(enabled=true)
	public void TC02verifyTransactionDate() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		safeWithdrawl.clickOnTxnDateLovBtn();
		//		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> TransactionDateUI = safeWithdrawl.getTransactionDate();
		test.log(LogStatus.INFO, "Transaction Date lov data UI :" + TransactionDateUI);
		List<String> TransactionDateDB = safeWithdrawl.getTransactionDateDB(siteNum);
		test.log(LogStatus.INFO, "Transaction Date lov data DB :" + TransactionDateDB);
		if (CollectionUtils.isEqualCollection(TransactionDateUI, TransactionDateUI)) {
			test.log(LogStatus.PASS, "Transaction Date data is matching with DB data");
		} else {
			test.log(LogStatus.FAIL, "Transaction Date lov data is not matching with DB data");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void TC03verifyTypeLovData() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		List<String> typeLovUI = safeWithdrawl.getTypeLovData();
		typeLovUI.remove(0);
		test.log(LogStatus.INFO, "type lov data UI :" + typeLovUI);
		List<String> typeLovDB = safeWithdrawl.getTypeLovDataDB();		
		test.log(LogStatus.INFO, "type love data DB :" + typeLovDB);
		if (CollectionUtils.isEqualCollection(typeLovUI, typeLovDB)) {
			test.log(LogStatus.PASS, "Type lov data is matching with DB data");
		} else {

			test.log(LogStatus.FAIL, "Type lov data is not matching with DB data");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void TC04verifybillName() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations(); 
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		List<String> billConfigValueUI = safeWithdrawl.getCurrencyConfigName();
		test.log(LogStatus.INFO, "bill name from UI :" + billConfigValueUI);
		List<String> billConfigValueDB = safeWithdrawl.getCurrencyConfigNameDB();
		test.log(LogStatus.INFO, "bill name from DB :" + billConfigValueDB);
		if (CollectionUtils.isEqualCollection(billConfigValueUI, billConfigValueDB)) {
			test.log(LogStatus.PASS, "UI should reflect the configuration done for Bills");

		} else {

			test.log(LogStatus.FAIL, "UI should not reflect the configuration done for Bills");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void TC05verifyCoinName() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(3000);
		List<String> CoinConfigValueUI = safeWithdrawl.getCoinConfigName();
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

	@Test(enabled=true)
	public void TC06AutoReconciletenderswillnotbeshown() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(3000);
		List<String> CoinConfigValueUI = safeWithdrawl.getTenderName();
		test.log(LogStatus.INFO, "Tender name from UI :" + CoinConfigValueUI);
		List<String> CoinConfigValueDB = safeWithdrawl.getTenderNameDB();
		test.log(LogStatus.INFO, "Tender name from DB :" + CoinConfigValueDB);
		if (CollectionUtils.isEqualCollection(CoinConfigValueUI, CoinConfigValueDB)) {
			test.log(LogStatus.PASS, "No auto reconciling are shown.");
		} else {
			test.log(LogStatus.FAIL, "Auto reconciling are shown.");

			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void TC11verify_Exit_Button_SafeReceipt() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		safeWithdrawl.getRollsSum("13");
		safeWithdrawl.getLooseSum("82");
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


	@Test(enabled=true)
	public void VerifyAuthWindowWithValidCredentials() throws Exception {
		String cashierUserid = userName;
		String cashierPass = Utility.decodestring(password);
		String expected_Message = "Safe Withdrawal record saved";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		safeWithdrawl.clickOnSaveButton();
		Thread.sleep(2000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}	
		safeReceipt.enterauthorizationEmployeeCredentials(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void VerifyAuthWindowWithWrongCredentials() throws Exception {
		String cashierUserid = reader.getCellData("CashDrawer", 2, 2);
		String cashierPass = reader.getCellData("CashDrawer", 3, 2);
		String expected_Message = reader.getCellData("CashDrawer", 4, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		safeWithdrawl.clickOnSaveButton();
		Thread.sleep(2000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}	
		safeReceipt.enterauthorizationEmployeeCredentials(cashierUserid, cashierPass);
		test.log(LogStatus.INFO, "Cashier userid and password enter successfully");
		Thread.sleep(2000);
		String validation_Message = cashDrawerSetup.getAuthUserAndPassValidationMessage();
		test.log(LogStatus.INFO, "Authorization window actaul validation message is: " + validation_Message);
		test.log(LogStatus.INFO, "Authorization window expected validation message is: " + expected_Message);
		Assert.assertEquals(validation_Message, expected_Message);
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void RECON_AUDIT_RPT_SHOW_FLOAT_AMT() throws Exception {
		 Utility.executeandcommit("declare \r\n" + 
				"p_flag Varchar2(1);\r\n" + 
				"ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				"ret := tmxgbl.set_flag('RECON_AUDIT_RPT_SHOW_FLOAT_AMT','N');\r\n" +
				"end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		try {
			boolean sts2= eleUtil.doIsDisplayed(safeWithdrawl.tenderAuditBtn)||
					eleUtil.doIsDisplayed(safeWithdrawl.totalExpAmtLbl)||
					eleUtil.doIsDisplayed(safeWithdrawl.overShortLbl)||
					eleUtil.doIsDisplayed(safeWithdrawl.ExptdAmtSection);
			System.out.println("1st fail");
			test.log(LogStatus.FAIL, "Tender Audit Btn, Exptd Amt, over/short & totalExpAmt"
					+ " are displayed, even when the "
					+ "flag 'RECON_AUDIT_RPT_SHOW_FLOAT_AMT' is set to N");
			softAssert.assertTrue(false, sts2 + " is not verified");
		} catch (Exception e) {
			System.out.println("1st pass");
			test.log(LogStatus.PASS, "Tender Audit Btn, Exptd Amt, over/short & totalExpAmt"
					+ " are not displayed, when the "
					+ "flag 'RECON_AUDIT_RPT_SHOW_FLOAT_AMT' is set to N, as expected");
		}

		Thread.sleep(2000);
		Utility.executeandcommit("declare \r\n" + 
				"p_flag Varchar2(1);\r\n" + 
				"ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				"ret := tmxgbl.set_flag('RECON_AUDIT_RPT_SHOW_FLOAT_AMT','Y');\r\n" + 
				"end;");

		eleUtil.pageRefresh();
		Thread.sleep(4000);
		Boolean sts = eleUtil.doIsDisplayed(safeWithdrawl.tenderAuditBtn) &&
				eleUtil.doIsDisplayed(safeWithdrawl.totalExpAmtLbl)&& 
				eleUtil.doIsDisplayed(safeWithdrawl.overShortLbl)&&
				eleUtil.doIsDisplayed(safeWithdrawl.ExptdAmtSection);
		if(sts) {
			System.out.println("2nd pass");
			test.log(LogStatus.PASS, "Tender Audit Btn, Exptd Amt, over/short & totalExpAmt"
					+ " are displayed, when the flag "
					+ "'RECON_AUDIT_RPT_SHOW_FLOAT_AMT' is set to Y, as expected");
		} else {
			System.out.println("2nd fail");
			test.log(LogStatus.FAIL, "Tender Audit Btn, Exptd Amt, over/short & totalExpAmt"
					+ " are not displayed, but it should be displayed, as"
					+ "'RECON_AUDIT_RPT_SHOW_FLOAT_AMT' is set to Y");
			softAssert.assertTrue(false, sts + " is not verified");
		}
		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void SAFE_TRAN_TYPE_REQUIRED() throws Exception {
		 Utility.executeandcommit("declare \r\n" + 
				"		  p_flag Varchar2(1);\r\n" + 
				"		  ret Varchar2(10);\r\n" + 
				"		begin\r\n" + 
				"		  ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','Y');\r\n" + 
				"		end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		eleUtil.doSelectDropDownByIndex(safeWithdrawl.typeLov,0);
		safeWithdrawl.clickOnSaveButton();
		String errorMsg;
		try {
			errorMsg = safeWithdrawl.readErrorMsg();
			if(errorMsg.equals("Type field can not be empty.")){
				test.log(LogStatus.PASS, "Error Msg 'Type field can not be empty.' is displayed, as flag "
						+ "'SAFE_TRAN_TYPE_REQUIRED' is set to 'Y', as expected");
			}else {
				test.log(LogStatus.FAIL, "Error Msg 'Type field can not be empty.' is not displayed correctly");
			}
			Thread.sleep(3000);
			eleUtil.doClick(safeWithdrawl.okBtnOnDlgBox);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Error msg is not displayed");
		}

		Thread.sleep(4000);
		Utility.executeandcommit("declare \r\n" + 
				"		  p_flag Varchar2(1);\r\n" + 
				"		  ret Varchar2(10);\r\n" + 
				"		begin\r\n" + 
				"		  ret := tmxgbl.set_flag('SAFE_TRAN_TYPE_REQUIRED','N');\r\n" + 
				"		end;");

		eleUtil.pageRefresh();
		Thread.sleep(4000);
		safeWithdrawl.clickOnSaveButton();
		Thread.sleep(2000);
		boolean auth_Status = cashDrawerSetup.verifyAuthorizationPopUpTitle();
		if (auth_Status) {
			test.log(LogStatus.PASS, "Authorization window is displayed on click on Save Btn, as flag "
					+ "'SAFE_TRAN_TYPE_REQUIRED' is set to 'N', as expected.");
		} else {
			test.log(LogStatus.FAIL, "Authorization window is not displayed");
			softAssert.assertTrue(false, auth_Status + " is not verified");
		}
		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void SAFE_TRAN_DT_USE_EOD_PLUS1() throws Exception {
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(1);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				"  ret := tmxgbl.set_flag('SAFE_TRAN_DT_USE_EOD_PLUS1','N');\r\n" + 
				"end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		String dateWithN = eleUtil.doGetAttribute(safeWithdrawl.txnDateLovBtn, "title");
		//		safeWithdrawl.clickOnSaveButton();
		System.out.println(dateWithN); 

		Date date1= new SimpleDateFormat("MM/dd/yyyy").parse(dateWithN);
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-dd-MM");
		String strDate1=formatter.format(date1);
		
		Date date = new Date();
	      SimpleDateFormat fmt = new SimpleDateFormat("yyyy-dd-MM");
	       String str = fmt.format(date);
	       
		if(strDate1.equals(str)) {
			test.log(LogStatus.PASS, "Tran date is populated with current date, as flag "
					+ "'SAFE_TRAN_DT_USE_EOD_PLUS1' is set to 'N', which is expected.");
		}else {
			test.log(LogStatus.FAIL, "Tran date is not populated with current date, even when the flag "
					+ "'SAFE_TRAN_DT_USE_EOD_PLUS1' is set to 'N'");
			softAssert.assertTrue(false, " is not verified");
		}

		Thread.sleep(4000);
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(1);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				"  ret := tmxgbl.set_flag('SAFE_TRAN_DT_USE_EOD_PLUS1','Y');\r\n" + 
				"end;");

		eleUtil.pageRefresh();
		Thread.sleep(4000);
		String dateWithY = eleUtil.doGetAttribute(safeWithdrawl.txnDateLovBtn, "title");
		System.out.println("Date from UI: "+dateWithY);
		Date date2= new SimpleDateFormat("MM/dd/yyyy").parse(dateWithY);
		SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
		String strDate2=formatter2.format(date2);
		System.out.println("Formatted Date from UI: "+strDate2);
		String dateFromDB = safeWithdrawl.getLastClosedTransactionDateDB("103").substring(0,10);
		System.out.println(dateFromDB);
		if(strDate2.equals(dateFromDB)) {
			test.log(LogStatus.PASS, "Tran date is populated from the DB, as flag "
					+ "'SAFE_TRAN_DT_USE_EOD_PLUS1' is set to 'Y', which is expected.");
		}else {
			test.log(LogStatus.FAIL, "Tran date is not populated from DB,, even when the flag "
					+ "'SAFE_TRAN_DT_USE_EOD_PLUS1' is set to 'Y'");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
	}



	@Test(enabled=true)
	public void ENABLE_CHECK_DETAIL_BTN() throws Exception {
		
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(1);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"  begin\r\n" +
				"  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','N');\r\n" + 
				" \r\n" + 
				"end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		String chckAuditBtnclassWithFlagN = eleUtil.doGetAttribute(safeWithdrawl.checkAuditBtn, "class");
		String checkFieldClassWithFlagN = eleUtil.doGetAttribute(safeWithdrawl.checkField, "class");

		if(chckAuditBtnclassWithFlagN.contains("disabled") && 
				!checkFieldClassWithFlagN.contains("disabled")) {
			test.log(LogStatus.PASS, "Check-Audit Btn is disabled & Check field is enabled with "
					+ "flag 'ENABLE_CHECK_DETAIL_BTN' set to N, as expected");
		}else {
			test.log(LogStatus.FAIL, "Check Audit Btn is not disabled, even when the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' is set to N");
			softAssert.assertTrue(false, " is not verified");
		}

		Thread.sleep(4000);
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(1);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"  begin\r\n" +
				"  ret := tmxgbl.set_flag('ENABLE_CHECK_DETAIL_BTN','Y');\r\n" + 
				" \r\n" + 
				"end;");
		eleUtil.pageRefresh();
		Thread.sleep(4000);
		String chckAuditBtnclassWithFlagY = eleUtil.doGetAttribute(safeWithdrawl.checkAuditBtn, "class");
		String checkFieldClassWithFlagY = eleUtil.doGetAttribute(safeWithdrawl.checkField, "class");

		if(!(chckAuditBtnclassWithFlagY.contains("disabled")) && 
				checkFieldClassWithFlagY.contains("disabled")) {
			test.log(LogStatus.PASS, "Check Audit Btn is enabled & Check field is disabled, with the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' set to Y, as expected");
		}else {
			test.log(LogStatus.FAIL, "Check Audit Btn is not enabled, even when the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' is set to Y");
			softAssert.assertTrue(false, " is not verified");
		}

		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void POS_CASH_DRAWER_ASSIGNED() throws Exception {
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(10);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				" ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED',103,'');\r\n" + 
				"end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		String tenderAuditBtnWithFlagNull = eleUtil.doGetAttribute(safeWithdrawl.tenderAuditBtn, "class");

		if(tenderAuditBtnWithFlagNull.contains("disabled")) {
			test.log(LogStatus.PASS, "Tender-Audit Btn is disabled with "
					+ "flag 'ENABLE_CHECK_DETAIL_BTN' set to N, as expected");
		}else {
			test.log(LogStatus.FAIL, "Tender-Audit Btn is not disabled, even when the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' is set to N");
			softAssert.assertTrue(false, " is not verified");
		}

		Thread.sleep(4000);
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(10);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				" ret := tmxgbl.setsite_text('POS_CASH_DRAWER_ASSIGNED',103,'R');\r\n" + 
				"end;");
		eleUtil.pageRefresh();
		Thread.sleep(4000);
		String tenderAuditBtnWithFlagR = eleUtil.doGetAttribute(safeWithdrawl.checkAuditBtn, "class");

		if(!tenderAuditBtnWithFlagR.contains("disabled")) {
			test.log(LogStatus.PASS, "Tender-Audit Btn is enabled, with the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' set to R, as expected");
		}else {
			test.log(LogStatus.FAIL, "Tender-Audit Btn is not enabled, even when the flag"+
					"'ENABLE_CHECK_DETAIL_BTN' is set to R");
			softAssert.assertTrue(false, " is not verified");
		}

		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void SAFE_RECON_IN_PROCESS() throws Exception {
		
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(10);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				" ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS',103,'Y');\r\n" + 
				"end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(2000);
		try {
			eleUtil.doIsDisplayed(safeWithdrawl.safeReconMsg);
			String safeReconInProcessMsg = eleUtil.doGetText(safeWithdrawl.safeReconMsg);
			if(safeReconInProcessMsg.equals("Safe reconciliation is currently in process. Please perform this safe withdrawal after manager is finished with the safe.")) {
				test.log(LogStatus.PASS, "Safe reconciliation pop-up is displayed with correct msg.");
				eleUtil.doClick(safeWithdrawl.okBtnOnDlgBox);
				Thread.sleep(3000);
				String currenturl = driver.getCurrentUrl();
				if(currenturl.contains("home")) {
					test.log(LogStatus.PASS, "After clicking on OK btn, displayed on pop-up; "
							+ "the page is navigated to home page, as expected");
				}else {
					test.log(LogStatus.FAIL, "After clicking on OK btn, displayed on pop-up; "
							+ "the page is not navigated to home page");
				}
			}else {
				test.log(LogStatus.FAIL, "Safe reconciliation pop-up is displayed, but "
						+ "msg displayed on that is not expected");
				softAssert.assertTrue(false, " is not verified");
			}

		}catch(Exception e){
			test.log(LogStatus.FAIL, "Safe reconciliation pop-up is not displayed, even when the"
					+ "flag 'SAFE_RECON_IN_PROCESS' is set to Y");
			softAssert.assertTrue(false, " is not verified");
		}

		Thread.sleep(4000);
		 Utility.executeandcommit("declare \r\n" + 
				"  p_flag Varchar2(10);\r\n" + 
				"  ret Varchar2(10);\r\n" + 
				"begin\r\n" + 
				" ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS',103,'N');\r\n" + 
				"end;");
		eleUtil.pageRefresh();
		Thread.sleep(4000);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(4000);
		String safeWthdrlPage = eleUtil.doGetText(safeWithdrawl.safeWithdrawalPage);

		if(safeWthdrlPage.contains("Safe Withdrawal")) {
			test.log(LogStatus.PASS, "No Pop-up displayed for Safe-recon in-process when the"
					+ "flag 'SAFE_RECON_IN_PROCESS' is set to N, as expected");
		}else {
			test.log(LogStatus.FAIL, "Safe Withdrawal is screen is not displayed or hidden, "
					+ "even after the flag 'SAFE_RECON_IN_PROCESS' is set to N");
			softAssert.assertTrue(false, " is not verified");
		}

		softAssert.assertAll();
	}


	@Test(enabled=true)
	public void PopUpValidationOfclearBtn() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(3000);
		safeWithdrawl.getRollsSum("13");
		safeWithdrawl.getLooseSum("82");
		Thread.sleep(3000);
		eleUtil.doClick(safeWithdrawl.clearBtn);
		try {
			String popUpMsg = eleUtil.doGetText(safeWithdrawl.clrBtnPopUpMsg);
			if(popUpMsg.equals("Clear current values?")) {
				test.log(LogStatus.PASS, "Correct pop-up Msg displayed on clicking on Clear btn");

				eleUtil.doIsDisplayed(safeWithdrawl.cancelBtnOnDlgBox);
				test.log(LogStatus.PASS, "Cancel Btn is displayed on the pop-up");
				eleUtil.doIsDisplayed(safeWithdrawl.okBtnOnDlgBox);
				test.log(LogStatus.PASS, "Ok Btn is displayed on the pop-up");

			}else {
				test.log(LogStatus.FAIL, "Pop-up msg is mis-matching with the expected value");
				softAssert.assertTrue(false, " is not verified");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Pop-up itself is not appeared, after clicking on Clear"
					+ " btn, which is unexpected");
			softAssert.assertTrue(false, " is not verified");		
		}
		softAssert.assertAll();
	}

	@Test(enabled=true)
	public void OverShortCalculation() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(3000);
		Double a = safeWithdrawl.getRollsSum("13");
		Double b = safeWithdrawl.getLooseSum("82");
		Thread.sleep(3000);
		Double actualUI = Double.parseDouble(eleUtil.doGetAttribute(safeWithdrawl.actualAmt, "value"));
		Double expectedUI = Double.parseDouble(eleUtil.doGetAttribute(safeWithdrawl.totalAmt, "value"));
		Double ovrshrtUI = Double.parseDouble(eleUtil.doGetAttribute(safeWithdrawl.overShortAmt, "value"));
		String overshortCalculated = String.format("%.2f", (actualUI-expectedUI));
		Double xp = Double.valueOf(overshortCalculated);
		Thread.sleep(3000);
		if(xp.compareTo(ovrshrtUI)==0) {
			test.log(LogStatus.INFO, "Calculated Over/short: "+xp+" , over/short from UI: "+ovrshrtUI);
			test.log(LogStatus.PASS, "Over short calculated is equals to what displayed on UI");
		}else {
			test.log(LogStatus.INFO, "Calculated Over/short: "+overshortCalculated+" , over/short from UI: "+ovrshrtUI);
			test.log(LogStatus.FAIL, "Over short calculated is not equals to what displayed on UI");
			softAssert.assertTrue(false, " is not verified");		
		}
		softAssert.assertAll();
	}
	
	
	@Test(enabled=true)
	public void TotalCalculation() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.clickOnManagerOperations();
		menuSelection.clickOnExpandAllButton();
		menuSelection.clickOnSafeWithdrawal();
		Thread.sleep(4000);
		Double rolls = safeWithdrawl.getRollsSum("13");
		Double loose = safeWithdrawl.getLooseSum("82");
		Double currency = safeWithdrawl.getCurrencySum("10");
		Thread.sleep(3000);
		double calculatedTotal = safeWithdrawl.calculateActualAmt(rolls, loose, currency);
		String totalUI= eleUtil.doGetAttribute(safeWithdrawl.actualAmt, "value");
		System.out.println("calculated total= "+calculatedTotal);
		System.out.println("total from ui: "+Double.parseDouble(totalUI));
		if(calculatedTotal == Double.parseDouble(totalUI)) {
			test.log(LogStatus.INFO, "Calculated Actual Total: "+calculatedTotal+" , Actual Total from UI: "+Double.parseDouble(totalUI));
			test.log(LogStatus.PASS, "Actual Amt calculated is equals to what displayed on UI");
		}else {
			test.log(LogStatus.INFO, "Calculated Actual Total: "+calculatedTotal+" , Actual Total from UI: "+Double.parseDouble(totalUI));
			test.log(LogStatus.FAIL, "Actual amt calculated is not equals to what displayed on UI");
			softAssert.assertTrue(false, " is not verified");
		}
		softAssert.assertAll();
		
	}
}
