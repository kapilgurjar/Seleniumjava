package com.rediron.apex.testCases;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

public class SafeFloatTestcases {

	public class SafeWithdrawlTestcases extends TestBase {

		String userName = reader.getCellData("Login", 0, 2);
		String password = reader.getCellData("Login", 1, 2);
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		public String assignToRegister = "R0364";

		@Test(enabled=true)
		public void TC01verifySiteDetailsOnSafeFloatScreen() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			test.log(LogStatus.INFO, "Site Name & Number displayed on Safe Float Screen is : " + safeFloat.getSiteNumAndName());
			Thread.sleep(2000);
			Assert.assertEquals(safeFloat.getSiteNumAndName(), siteNameNum);
		}

		@Test(enabled=true)
		public void TC02verifyDisabledRegNamesAndEnabledExpFloat() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			safeFloat.selectMaxRowsPerPage();
			Thread.sleep(3000);
			boolean allRegsDisabled = safeFloat.checkAllRegNamesAreDisabled();
			boolean allExpectedFloatEnabled = safeFloat.checkAllExpectedFloatValuesEnabled();
			if(allRegsDisabled) {
				test.log(LogStatus.PASS, "All Reg Names are in disbaled state");
			} else {
				test.log(LogStatus.FAIL, "Reg Names are not in disabled state");
				softAssert.assertTrue(false, " is not verified");
			}

			if(allExpectedFloatEnabled) {
				test.log(LogStatus.PASS, "All Expected Float are in enabled state");
			} else {
				test.log(LogStatus.FAIL, "Expected Float are not enabled");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}



		@Test(enabled=true)
		public void TC03checkIfSafeFloatEditable() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			boolean status = safeFloat.checkIfSafeFltAmtisEditable();
			if(status) {
				test.log(LogStatus.PASS, "Safe Float Amount field is ediatble");
			} else {
				test.log(LogStatus.FAIL, "Safe Float Amount field is not ediatble");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}

		@Test(enabled=true)
		public void TC04checkIfTotalRegFloatAmtIsDisabled() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			boolean status = safeFloat.checkIfTotalRegAmtIsNotEditable();
			if(status) {
				test.log(LogStatus.PASS, "Total Reg Float Amount field is disabled & un-editable");
			} else {
				test.log(LogStatus.FAIL, "Total Reg Float Amount field is not disabled");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}


		@Test(enabled=true)
		public void TC05validateSafeFLoatAmtAgainstDB() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			String safeFltAmtUI = safeFloat.getSafeFloatAmtfromUI();
			String safeFltAmtDB = safeFloat.getSafeFloatAmtfromDB(siteNum);
			if(safeFltAmtUI.equals(safeFltAmtDB)) {
				test.log(LogStatus.PASS, "safe float amount displayed in UI: "+safeFltAmtUI+
						" and in DB : "+safeFltAmtDB + "& both are same.");
			} else {
				test.log(LogStatus.FAIL, "safe float amount displayed in UI: "+ safeFltAmtUI+ 
						"safe float amount displayed in DB: "+safeFltAmtDB);
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}

		@Test(enabled=true)
		public void TC06validateRegIDsSyncWithDB() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			List<String> regIdfromUI = safeFloat.getRegIDsfromUI();
			List<String> regIdfromDB = safeFloat.getRegIDsfromDB(siteNum);
			test.log(LogStatus.INFO, "regIDs from UI: "+regIdfromUI);
			test.log(LogStatus.INFO, "regIDs from UI: "+regIdfromDB);

			if(regIdfromUI.equals(regIdfromDB)) {
				test.log(LogStatus.PASS, "regIDs reflected in UI are in sync with DB");
			} else {
				test.log(LogStatus.FAIL, "regIDs reflected in UI are not in sync with DB");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}


		@Test(enabled=true)
		public void TC07validateIfCalculatedTotalIsCorrect() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			safeFloat.modifySafeFloat("9999999");
			safeFloat.enterValuesInExpFloatsWithRandomIndex();
			int calculatedTotalWithEnteredValues = safeFloat.actualTotalOfExpFloat();
			int calculatedTotalDisplyedInUI = safeFloat.totalOfExpFloatinUI();

			if(calculatedTotalDisplyedInUI==calculatedTotalWithEnteredValues) {
				test.log(LogStatus.PASS, "total matches");
			} else {
				test.log(LogStatus.FAIL, "Total not matches");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}

		@Test(enabled=true)
		public void TC08validateIfNegativeValuesAreNotAllowedInSafeFloat() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			String negativeFloatAmt = "-89213";
			safeFloat.modifySafeFloat(negativeFloatAmt);
			String filledValueInSafeFloat = safeFloat.getSafeFloatAmtfromUI();
			if(negativeFloatAmt.contains(filledValueInSafeFloat) && (!filledValueInSafeFloat.contains("-"))) {
				test.log(LogStatus.PASS, "Safe Float field doesn't allow negative number, as the "
						+ "entered value was: "+negativeFloatAmt+" and the value fetched from UI is: "+filledValueInSafeFloat);
			} else {
				test.log(LogStatus.FAIL, "Safe Float field allowed negative number, which is not expected.");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();	
		}

		@Test(enabled=true)
		public void TC09validateErrorMsgWhenSafeFltAmtIsLessThanTotalRegAmt() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			int totalRegAmtUI = safeFloat.totalOfExpFloatinUI();
			int safeFloatAmtUI = Integer.valueOf(safeFloat.getSafeFloatAmtfromUI());
			if(safeFloatAmtUI > totalRegAmtUI) {
				test.log(LogStatus.PASS, "Default values fetched from UI :: Safe FLoat ="+safeFloatAmtUI+
						"TotalRegAmt= "+totalRegAmtUI+", Hence safeFLoat vlaue >= TotalRegValue, as expected.");
			}else {
				test.log(LogStatus.FAIL, "Default values fetched from UI :: Safe FLoat ="+safeFloatAmtUI+
						"TotalRegAmt= "+totalRegAmtUI+", safeFLoat vlaue < TotalRegValue, which is not expected");
			}
			int modifiedSafeFloatValue = totalRegAmtUI-1;
			safeFloat.modifySafeFloatAndPressTab(String.valueOf(modifiedSafeFloatValue));
			Thread.sleep(2000);
			test.log(LogStatus.INFO, "Modified the Safe FLoat value in UI with: "+modifiedSafeFloatValue+ 
					", which is lesser value then the totalRegValue: "+safeFloatAmtUI);
			String alertMsgDisplayed = safeFloat.getAlertMsgText();
			String expectedAltmsg = "The Safe Float Amount cannot be less than the Total Register Float Amount.";
			if(alertMsgDisplayed.contains(expectedAltmsg)) {
				test.log(LogStatus.PASS, "An alert pop-up displayed with the expected error msg i.e. "+expectedAltmsg );
			} else {
				test.log(LogStatus.FAIL, "Alert msg displayed is not correct");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}

		@Test(enabled=true)
		public void TC10validateErrorMsgWhenTotalRegAmtCrossesSafeFloatAmt() throws Exception {
			loginpage.Logintoapplication(userName, Utility.decodestring(password));
			menuSelection.selectCurrentSite(siteNum);
			test.log(LogStatus.INFO, "Selected site is : " + siteNameNum);
			menuSelection.clickOnManagerOperations();
			menuSelection.clickOnExpandAllButton();
			menuSelection.clickOnSafeFloat();
			Thread.sleep(3000);
			safeFloat.selectMaxRowsPerPage();
			Thread.sleep(3000);
			int totalRegAmtUI = safeFloat.totalOfExpFloatinUI();
			int safeFloatAmtUI = Integer.valueOf(safeFloat.getSafeFloatAmtfromUI());
			if(safeFloatAmtUI > totalRegAmtUI) {
				test.log(LogStatus.PASS, "Default values fetched from UI :: Safe FLoat = "+safeFloatAmtUI+
						"TotalRegAmt= "+totalRegAmtUI+", Hence safeFLoat vlaue >= TotalRegValue, as expected.");
				safeFloat.makeTotalRegAmtMoreThanSafeFloatAmt(safeFloatAmtUI, totalRegAmtUI);
				Thread.sleep(5000);
				String alertMsgDisplayed = safeFloat.getAlertMsgText();
				String expectedAltmsg = "The total of register float amounts is greater than the current safe float amount. Click 'OK' to automatically change the safe float amount to match.";
				if(alertMsgDisplayed.equalsIgnoreCase(expectedAltmsg)) {
					test.log(LogStatus.PASS, "An alert pop-up displayed with the expected error msg i.e. "+expectedAltmsg );
				} else {
					test.log(LogStatus.FAIL, "Alert msg displayed is not correct");
					softAssert.assertTrue(false, " is not verified");
				}
			}else {
				test.log(LogStatus.FAIL, "Default values fetched from UI :: Safe FLoat ="+safeFloatAmtUI+
						"TotalRegAmt= "+totalRegAmtUI+", safeFLoat vlaue < TotalRegValue, which is not expected");
				softAssert.assertTrue(false, " is not verified");
			}
			softAssert.assertAll();
		}
	
	}
}
