package com.rediron.apex.testCases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class ConfigurationFlagMaintenanceTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	String currentSite = "1";

	@Story("Configuration Flag Maintenance ")
	@Description("Verify Page title")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyPageTitleONConfigurationFlagMaintenance() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		eleUtil.waitForPageActTitle("Confguration Flag Maintenance", config.getExplicitWait());
	}

	@Story("Configuration Flag Maintenance ")
	@Description("All data are read only. Save and clear will not be visible.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyRemotesiteAccessONConfigurationFlagMaintenance() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		boolean sts = configurationFlagMaint.isClearAndSaveButtonDisplayed();
		Assert.assertFalse(sts);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify search on main page and it should show result as per search keyword")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifySearchConfigurationFlagMaintenance() throws Exception {
		String expSearch = "POS_SALES_TO_FULFILL_SITE";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		String actSearch = configurationFlagMaint.searchDataInTable(expSearch).getTableFirstRowData();
		Assert.assertEquals(actSearch, expSearch);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify search on main page and it should show result as per search keyword")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifySearchWithInvalidDataConfigurationFlagMaintenance() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.searchDataInTable("dhfjahf");
		boolean sts = configurationFlagMaint.isNoResultFoundDispalyed();
		Assert.assertTrue(sts);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("For number type value will allow with 12,2 value.Error message will be shown if wrongly entered")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyValueFieldCheckForNumberConfigurationFlagMaintenance() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Number");
		configurationFlagMaint.enterDataInValueTextFiled("ABCDEF");
		String text = configurationFlagMaint.getAlertPopText();
		Assert.assertTrue(text.contains("9999999999.99"));
	}

	@Story("Configuration Flag Maintenance ")
	@Description("For flag type Value need to be Y or N")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyValueFieldCheckForFlagConfigurationFlagMaintenance() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Flag");
		configurationFlagMaint.enterDataInValueTextFiled("ABCDEF");
		String text = configurationFlagMaint.getAlertPopText();
		Assert.assertTrue(text.contains("Allowed values for Flag is Y or N."));
	}

	@Story("Configuration Flag Maintenance ")
	@Description("For flag type Value need to be Y or N")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyValueFieldCheckForDateConfigurationFlagMaintenance() throws Exception {
		int count = reader.getRowCount("ConfigFlagMaint");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Date");
		for (int i = 2; i <= count; i++) {
			String date = reader.getCellData("ConfigFlagMaint", 0, i);
			configurationFlagMaint.enterDataInValueTextFiled(date);
			String text = configurationFlagMaint.getAlertPopText();
			Assert.assertTrue(text.contains("Value must be a valid date in MM/DD/YY format."));
			configurationFlagMaint.clickOnOkButton();
		}

	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify that given nuber is saving to database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyValueFieldCheckForNumberAgainstDBOnConfigurationFlagMaintenance() throws Exception {
		String expNumber = Utility.getRandomNumber();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Number");
		configurationFlagMaint.searchDataInTable("AGING_PER1");
		configurationFlagMaint.enterDataInValueTextFiled(expNumber).clickOnSaveButton();
		String actNuber = Utility.getDataFromDatabase(
				"Select *From configrec where config_name='AGING_PER1' and config_type='N'", "CONFIG_NO").get(0);
		Assert.assertEquals(actNuber, expNumber);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify that given flag is saving to database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyValueFieldCheckForFlagAgainstDBOnConfigurationFlagMaintenance() throws Exception {
		String expFlag = "Y";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Flag");
		configurationFlagMaint.searchDataInTable("2359_4INCOMPLETE_ENTRIES");
		configurationFlagMaint.enterDataInValueTextFiled(expFlag).clickOnSaveButton();
		String actFlag = Utility.getDataFromDatabase(
				"Select *From configrec where config_name='2359_4INCOMPLETE_ENTRIES' and config_type='F'", "CONFIG_FL")
				.get(0);
		Assert.assertEquals(actFlag, expFlag);
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Flag");
		configurationFlagMaint.searchDataInTable("2359_4INCOMPLETE_ENTRIES");
		configurationFlagMaint.enterDataInValueTextFiled("N").clickOnSaveButton();
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify that given text is saving to database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyValueFieldCheckForTextAgainstDBOnConfigurationFlagMaintenance() throws Exception {
		String expText = "Testqa" + Utility.getRandomString();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Text");
		configurationFlagMaint.searchDataInTable("ACCOUNTING_METHOD");
		configurationFlagMaint.enterDataInValueTextFiled(expText).clickOnSaveButton();
		String actText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		Assert.assertEquals(actText, expText);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Verify that given date is saving to database")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyValueFieldCheckForDateAgainstDBOnConfigurationFlagMaintenance() throws Exception {
		String Date = Utility.getCurrentDate();
		Date date1 = new SimpleDateFormat("_yyyy-MM-dd").parse(Date);
		System.out.println(date1);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
		String expDate = formatter.format(date1);
		System.out.println(expDate);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Date");
		configurationFlagMaint.searchDataInTable("ARTRANSNO_USE_DATE");
		configurationFlagMaint.enterDataInValueTextFiled(expDate).clickOnSaveButton();
		String dBDate = Utility.getDataFromDatabase(
				"Select *From configrec where config_name='ARTRANSNO_USE_DATE' and config_type='D'", "CONFIG_DT")
				.get(0);
		Date dbDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dBDate);
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/YYYY");
		String actDate = formatter1.format(dbDate1);
		Assert.assertEquals(actDate, expDate);
	}

	@Story("Configuration Flag Maintenance ")
	@Description("Check whether filter can be applied for type.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifyFilterTypeDataOnConfigurationFlagMaintenance() throws Exception {
		String columnName = "Type";
		String columnValue = "Date";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		String textFilter = configurationFlagMaint.appliedFilterOnColumn(columnName, columnValue);
		Assert.assertTrue(textFilter.contains("Type equals"));
		Thread.sleep(2000);
		boolean sts = configurationFlagMaint.verifyThatFilterIsApplied(columnValue);
		Assert.assertTrue(sts);
	}
	
	@Story("Configuration Flag Maintenance ")
	@Description("Verify clear button pop window for yes button")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC13verifyCancelPopYesButtonOnConfigurationFlagMaintenance() throws Exception {
		String expText = "Testqa" + Utility.getRandomString();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Text");
		configurationFlagMaint.searchDataInTable("ACCOUNTING_METHOD");
		String popText=configurationFlagMaint.enterDataInValueTextFiled(expText).clickOnCancelButton().getAlertPopText();
		Assert.assertTrue(popText.contains("Do you want to save your changes in database?"));
		configurationFlagMaint.clickOnClearYesButton();
		String actText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		Assert.assertEquals(actText, expText);
	}
	
	@Story("Configuration Flag Maintenance ")
	@Description("Verify clear button pop window for No button")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyCancelPopNoButtonOnConfigurationFlagMaintenance() throws Exception {
		String beforeActText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		String expText = "Testqa" + Utility.getRandomString();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Text");
		configurationFlagMaint.searchDataInTable("ACCOUNTING_METHOD");
		String popText=configurationFlagMaint.enterDataInValueTextFiled(expText).clickOnCancelButton().getAlertPopText();
		Assert.assertTrue(popText.contains("Do you want to save your changes in database?"));
		configurationFlagMaint.clickOnClearNoButton();
		String AfterActText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		Assert.assertEquals(beforeActText, AfterActText);
	}
	
	@Story("Configuration Flag Maintenance ")
	@Description("Verify clear button pop window for cancel button")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifyCancelPopCancelButtonOnConfigurationFlagMaintenance() throws Exception {
		String beforeActText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		String expText = "Testqa" + Utility.getRandomString();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Text");
		configurationFlagMaint.searchDataInTable("ACCOUNTING_METHOD");
		String popText=configurationFlagMaint.enterDataInValueTextFiled(expText).clickOnCancelButton().getAlertPopText();
		Assert.assertTrue(popText.contains("Do you want to save your changes in database?"));
		configurationFlagMaint.clickOnClearCancelButton();
		String AfterActText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		Assert.assertEquals(beforeActText, AfterActText);
	}
	
	@Story("Configuration Flag Maintenance ")
	@Description("Verify clear button pop window for yes button")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyExitButtonOnConfigurationFlagMaintenance() throws Exception {
		String expText = "Testqa" + Utility.getRandomString();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToConfigurationFlagMaintenance();
		configurationFlagMaint.clickOnTypeLovButton().selectDataFromTypeLov("Text");
		configurationFlagMaint.searchDataInTable("ACCOUNTING_METHOD");
		String popText=configurationFlagMaint.enterDataInValueTextFiled(expText).clickOnExitButton().getAlertPopText();
		Assert.assertTrue(popText.contains("Do you want to save your changes in database?"));
		configurationFlagMaint.clickOnClearYesButton();
		Assert.assertTrue(menuSelection.IsExpanButtonDisplayed());
		String actText = Utility
				.getDataFromDatabase("Select *From configrec where config_name='ACCOUNTING_METHOD' and config_type='T'",
						"CONFIG_TXT")
				.get(0);
		Assert.assertEquals(actText, expText);
	}
}
