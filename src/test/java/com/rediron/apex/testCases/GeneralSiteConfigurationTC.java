package com.rediron.apex.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class GeneralSiteConfigurationTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);

	@Story("General Site Configuration")
	@Description("Remote site will have read only access to this UI.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyRemoteSiteAccessOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("101").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		String siteNoClassAttr = generalSiteConfiguration.getSiteNoAttributeValue();
		System.out.println(siteNoClassAttr);
		test.log(LogStatus.INFO, "Site no class attribute is " + siteNoClassAttr);
		Assert.assertTrue(siteNoClassAttr.contains("apex_disabled"));
	}

	@Story("General Site Configuration")
	@Description("If user click on exit button then it should exit from the screen")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyExitButtonFunctionalityConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		boolean sts = generalSiteConfiguration.clickOnExitButton().IsExpanAllButtonDisplayed();
		Assert.assertTrue(sts);

	}

	@Story("General Site Configuration")
	@Description("Remote site will have read only access to this UI.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyThatSaveButtonIsDisabledIfNoChangeDoneOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		String saveClassAttr = generalSiteConfiguration.getSaveButtonClassAttribute();
		System.out.println(saveClassAttr);
		test.log(LogStatus.INFO, "Site no class attribute is " + saveClassAttr);
		Assert.assertTrue(saveClassAttr.contains("apex_disabled"));
	}

	@Story("General Site Configuration")
	@Description("Verify Pop message on clear alert Pop up.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyClearButtonFunctionalityOnGeneralSiteConfigurationScreen() throws Exception {
		String expPopMsg = reader.getCellData("GSI", 0, 2);
		test.log(LogStatus.INFO, "Expected Pop message is " + expPopMsg);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		generalSiteConfiguration.IsSkuRequiredCheckBoxChecked().clickOnClearButton();
		String actPopMessage = generalSiteConfiguration.getAlertPopMsg();
		test.log(LogStatus.INFO, "Actual Pop message is " + actPopMessage);
		Assert.assertEquals(actPopMessage, expPopMsg);
		test.log(LogStatus.PASS, "Actual message is equal to expected message");
	}

	@Story("General Site Configuration")
	@Description("Verify Pop message on clear alert Pop up.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyClearNOButtonFunctionalityOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		boolean beforeStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		generalSiteConfiguration.IsSkuRequiredCheckBoxChecked().clickOnClearButton().clickOnClearAlertPOpUpNoButton();
		boolean afterStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		if (beforeStsSku == afterStsSku) {
			test.log(LogStatus.PASS, "No button on alert window is working");
		} else {

			test.log(LogStatus.FAIL, "No button on alert window is not working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("General Site Configuration")
	@Description("Verify Pop message on clear alert Pop up.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyClearButtonFunctionalityYesOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		boolean beforeStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		generalSiteConfiguration.IsSkuRequiredCheckBoxChecked().clickOnClearButton().clickOnClearAlertPOpUpYesButton();
		boolean afterStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		Assert.assertNotEquals(beforeStsSku, afterStsSku);
	}

	@Story("General Site Configuration")
	@Description("Verify Pop message on clear alert Pop up.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyClearCancelButtonFunctionalityOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		boolean beforeStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		generalSiteConfiguration.IsSkuRequiredCheckBoxChecked().clickOnClearButton().clickClearAlertPOpUpCancelButton();
		eleUtil.pageRefresh();
		boolean afterStsSku = generalSiteConfiguration.getSkuCheckBoxStatus();
		if (beforeStsSku == afterStsSku) {
			test.log(LogStatus.PASS, "Cancel button on alert window is working");
		} else {

			test.log(LogStatus.FAIL, "Cancel button on alert window is not working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("General Site Configuration")
	@Description("Checking the Dept will uncheck the sku required and disable it.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyDeptgroupDeptClassLineOnGeneralSiteConfigurationScreen() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("1").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToGeneralSiteConfiguration();
		boolean sts=generalSiteConfiguration.getDepGroupDepClassAndLineCheckBoxButtonStatus();
		Assert.assertFalse(sts);
	}
}