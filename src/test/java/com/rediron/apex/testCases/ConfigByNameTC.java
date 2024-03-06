package com.rediron.apex.testCases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class ConfigByNameTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String hQSite=reader.getCellData("ConfigByName", 0, 2);
	String remoteSite=reader.getCellData("ConfigByName", 0, 3);
	String siteGroup=reader.getCellData("ConfigByName", 2, 2);

	@Story("Configuration by Name")
	@Description("All data are read only. Apply button and site group selection will be hidden."
			+ " All the data will be displayed for the logn site.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyRemoteSiteAccess() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(remoteSite).navigateToConfigByNameScreen();
		boolean sts = configByName.isSaveButtonDisplayed();
		if (sts == false) {
			test.log(LogStatus.PASS, "Save button is not dispalyed ");
		} else {
			test.log(LogStatus.FAIL, "Save button is dispalyed ");
			softAssert.fail();
		}
		softAssert.assertAll();

		Assert.assertTrue(configByName.getCheckBoxClassAttr().contains("readonly"));
	}

	@Story("Configuration by Name")
	@Description("All data are visible. Apply button and site group selection will be displayed."
			+ " All the data will be displayed for the logn site.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyHQSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		boolean sts = configByName.isSaveButtonDisplayed();
		if (sts) {
			test.log(LogStatus.PASS, "Save button is dispalyed ");
		} else {
			test.log(LogStatus.FAIL, "Save button is not dispalyed ");
			softAssert.assertTrue(sts, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Configuration by Name")
	@Description("Data should match MMSO for flags. Name and descrption will be shown.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyFlagConfigData() throws Exception {
		String query = "Select * from configdesc where type = 'F'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		// Getting Name and description value from UI
		HashMap<String, String> namesAndDescUI = configByName.getFlagTableDataUI();
		HashMap<String, String> namesAndDescDB = configByName.getFlagTableDataDB(query, "NAME", "DESCRIPTION");

		// Comparing both hashmap keys

		boolean sts = namesAndDescUI.keySet().equals(namesAndDescDB.keySet());
		if (sts) {
			test.log(LogStatus.PASS, "Flag name are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Flag name are not matching with data base");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

		// Comparing both hashmap values

		HashSet<String> set1 = new HashSet<>(namesAndDescUI.values());

		HashSet<String> set2 = new HashSet<>(namesAndDescDB.values());

		System.out.println("map1 values == map2 values : " + set1.equals(set2));

		if (set1.equals(set2)) {

			test.log(LogStatus.PASS, "Flag description are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Flag description are not matching with data base");
			softAssert.assertTrue(false, "not verified");
		}

	}

	
	@Story("Configuration by Name")
	@Description("Data should match MMSO for flags. Name and descrption will be shown. ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyDateConfigData() throws Exception {
		String query = "Select * from configdesc where type = 'D'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		// Getting Name and description value from UI
		HashMap<String, String> namesAndDescUI = configByName.getDateTableDataUI();
		HashMap<String, String> namesAndDescDB = configByName.getFlagTableDataDB(query, "NAME", "DESCRIPTION");

		// Comparing both hashmap keys

		boolean sts = namesAndDescUI.keySet().equals(namesAndDescDB.keySet());
		if (sts) {
			test.log(LogStatus.PASS, "Date name are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Date name are not matching with data base");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

		// Comparing both hashmap values

		HashSet<String> set1 = new HashSet<>(namesAndDescUI.values());

		HashSet<String> set2 = new HashSet<>(namesAndDescDB.values());

		System.out.println("map1 values == map2 values : " + set1.equals(set2));

		if (set1.equals(set2)) {

			test.log(LogStatus.PASS, "Date description are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Date description are not matching with data base");
			softAssert.assertTrue(false, "not verified");
		}

	}

	@Story("Configuration by Name")
	@Description("Data should match MMSO for Numbers. Name and descrption will be shown. ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyNumberConfigData() throws Exception {
		String query = "Select * from configdesc where type = 'N'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		// Getting Name and description value from UI
		HashMap<String, String> namesAndDescUI = configByName.getNumberTableDataUI();
		HashMap<String, String> namesAndDescDB = configByName.getFlagTableDataDB(query, "NAME", "DESCRIPTION");

		// Comparing both hashmap keys

		boolean sts = namesAndDescUI.keySet().equals(namesAndDescDB.keySet());
		if (sts) {
			test.log(LogStatus.PASS, "Number name are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Number name are not matching with data base");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

		// Comparing both hashmap values

		HashSet<String> set1 = new HashSet<>(namesAndDescUI.values());

		HashSet<String> set2 = new HashSet<>(namesAndDescDB.values());

		System.out.println("map1 values == map2 values : " + set1.equals(set2));

		if (set1.equals(set2)) {

			test.log(LogStatus.PASS, "Number description are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Number description are not matching with data base");
			softAssert.assertTrue(false, "not verified");
		}

	}

	@Story("Configuration by Name")
	@Description("Data should match MMSO for text. Name and descrption will be shown. ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyTextConfigData() throws Exception {
		String query = "Select * from configdesc where type = 'T'";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		// Getting Name and description value from UI
		HashMap<String, String> namesAndDescUI = configByName.getTextTabTableDataUI();
		HashMap<String, String> namesAndDescDB = configByName.getFlagTableDataDB(query, "NAME", "DESCRIPTION");

		// Comparing both hashmap keys

		boolean sts = namesAndDescUI.keySet().equals(namesAndDescDB.keySet());
		if (sts) {
			test.log(LogStatus.PASS, "Text name are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Text name are not matching with data base");
			softAssert.assertTrue(sts, "not verified");
		}

		softAssert.assertAll();

		// Comparing both hashmap values

		HashSet<String> set1 = new HashSet<>(namesAndDescUI.values());

		HashSet<String> set2 = new HashSet<>(namesAndDescDB.values());

		System.out.println("map1 values == map2 values : " + set1.equals(set2));

		if (set1.equals(set2)) {

			test.log(LogStatus.PASS, "Text description are matching with data base");
		} else {
			test.log(LogStatus.FAIL, "Text description are not matching with data base");
			softAssert.assertTrue(false, "not verified");
		}

	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyFlagSaveConfigurationPopUpMessage() throws Exception {
		String expPopMessage=reader.getCellData("ConfigByName", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen();
		String actPopMessage = configByName.getSaveConfigurationPopUpMessage();
		Assert.assertEquals(actPopMessage, expPopMessage);
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyFlagSaveConfigurationPopUpNoButton() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup);
		Assert.assertTrue(configByName.clickOnNoButtonAndVerifyCheckBox());
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyFlagSaveConfigurationPopUpYesButton() throws Exception {
		String expPopMessage=reader.getCellData("ConfigByName", 1, 2);
		//String expMessage = "Do you want to save the changes you have made in Flag Config?";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup);
		String actPopMessage = configByName.getSaveConfigurationPopUpMessage();
		Assert.assertEquals(actPopMessage, expPopMessage);
		Assert.assertEquals(configByName.clickOnYesButtonAndVerifyCheckBox(), "Checked");
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyDateSaveConfigurationPopUpMessage() throws Exception {
		//String expMessage = "Do you want to save the changes you have made in Date Config?";
		String expPopMessage=reader.getCellData("ConfigByName", 1, 3);
		String name1 = reader.getCellData("ConfigByName", 3, 2);
		String name2 = reader.getCellData("ConfigByName", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup).clickOnDate();
		configByName.enterDataInDateInputText("11/07/22").clickOnApplyAllButton();
		String actPopMessage = configByName.getSaveDateConfigurationPopUpMessage(name1, name2);
		Assert.assertEquals(actPopMessage, expPopMessage);
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = false)
	public void TC12verifyDateSaveConfigurationPopUpYesButton() throws Exception {// need to change dates every run
		String expPopMessage=reader.getCellData("ConfigByName", 1, 3);
		String name1 = reader.getCellData("ConfigByName", 3, 2);
		String name2 = reader.getCellData("ConfigByName", 3, 3);
		String date = "11/09/22";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup).clickOnDate();
		configByName.enterDataInDateInputText(date).clickOnApplyAllButton();
		String actPopMessage = configByName.getSaveDateConfigurationPopUpMessage(name1, name2);
		Assert.assertEquals(actPopMessage, expPopMessage);
		configByName.clickOnYesButton();
		String expDate = Utility.executeQuery("SELECT * FROM configdate where site_no=112", "CONFIG_DT");
		String actDate = Utility.dateFormater(date);
		Assert.assertEquals(expDate, actDate);

	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifyNumbersSaveConfigurationPopUpMessage() throws Exception {
		String expPopMessage=reader.getCellData("ConfigByName", 1, 4);
		String name1 = reader.getCellData("ConfigByName", 4, 2);
		String name2 = reader.getCellData("ConfigByName", 4, 3);
		Random rm = new Random();
		int num = rm.nextInt(999) + 1;
		String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup)
				.clickOnNumberTab();
		configByName.enetrDataInNumberInput(String.valueOf(num));
		String actPopMessage = configByName.getSaveNumberConfigurationPopUpMessage(name1, name2);
		Assert.assertEquals(actPopMessage, expPopMessage);
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyTextSaveConfigurationPopUpMessage() throws Exception {
		String expPopMessage=reader.getCellData("ConfigByName", 1, 5);
		String name1 = reader.getCellData("ConfigByName", 5, 2);
		String name2 = reader.getCellData("ConfigByName", 5, 3);
		Random rm = new Random();
		int num = rm.nextInt(999) + 1;
		String.valueOf(num);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup).clickOnText();
		configByName.enetrDataTextInput(String.valueOf(num));
		String actPopMessage = configByName.getSaveTextConfigurationPopUpMessage(name1, name2);
		Assert.assertEquals(actPopMessage, expPopMessage);
	}

	@Story("Configuration by Name")
	@Description("if there are changes done and if user navigates to any of the tab "
			+ "or any other record then user should be prompted to save changes. Ok/Cancel alert will be show. "
			+ "Ok will save , cancel will not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifySiteCountInGivenGroupID() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup);
		int sizeUI = configByName.getNoOfSiteInASiteGroup();
		test.log(LogStatus.INFO, "Site numbers from Ui are in a given SiteGroup equal to " + sizeUI);
		int sizeDB = Utility.getDataFromDatabase("SELECT* FROM sitgrp_all where GROUP_ID='"+siteGroup+"'", "SITE_NO").size();
		test.log(LogStatus.INFO, "Site numbers from DB are in a given SiteGroup equal to " + sizeDB);
		Assert.assertEquals(sizeUI, sizeDB);
	}

	@Story("Configuration by Name")
	@Description("Verify that if user made some changes and click on save button then "
			+ "data should be saved into respective db table ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifySaveButtonOnConfigByName() throws Exception {
		String siteNo=reader.getCellData("ConfigByName", 0, 4);
		System.out.println("siteNo" + siteNo);
		String flagName = reader.getCellData("ConfigByName", 6, 2);
		System.out.println("flagName" + flagName);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup);
		String flagValueUI = configByName.getFlagValueForASite(siteNo, flagName);
		if (flagValueUI.equalsIgnoreCase("Unchecked")) {
			configByName.selectFlagValueForASite(""+siteNo+"", ""+flagName+"");
			String flagValue = Utility
					.executeQuery("SELECT* FROM configflag where site_no ="+siteNo+" and name ='"+flagName+"'", "CONFIG_FL");
			Assert.assertEquals(flagValue, "Y");
		} else {
			configByName.selectFlagValueForASite(""+siteNo+"", ""+flagName+"");
			String flagValue = Utility
					.executeQuery("SELECT* FROM configflag where site_no ="+siteNo+" and name ='"+flagName+"'", "CONFIG_FL");
			Assert.assertEquals(flagValue, "N");
		}
	}

	@Story("Configuration by Name")
	@Description("Verify that if user click on exit button then user should be on menu page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 17, enabled = true)
	public void TC17verifyExitButtonOnConfigByName() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup);
		boolean sts = configByName.isExitButtonWorking();
		Assert.assertTrue(sts);
	}
	
	@Story("Configuration by Name")
	@Description("Verify date text input with wrong data")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 18, enabled = true)
	public void TC18verifyDateInputTextFieldWithWrongDataOnConfigByNameScreen() throws Exception {
		String expWarningMessage=reader.getCellData("ConfigByName", 1, 6);
		test.log(LogStatus.INFO, "Exp warning message is equal to " + expWarningMessage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup).clickOnDate();
		configByName.enterDataInDateInputText("1234jhjh").clickOnApplyAllButton();
		String actWarningMessage=configByName.getWindowPopWarningMessage();
		test.log(LogStatus.INFO, "Actual warning message is equal to " + actWarningMessage);
		Assert.assertEquals(actWarningMessage.trim(), expWarningMessage.trim());
		
	}
	
	@Story("Configuration by Name")
	@Description("Verify Number text input with wrong data")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 19, enabled = true)
	public void TC19verifyNumberInputTextFieldWithWrongDataOnConfigByNameScreen() throws Exception {
		String expWarningMessage=reader.getCellData("ConfigByName", 1, 7);
		test.log(LogStatus.INFO, "Exp warning message is equal to " + expWarningMessage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToConfigByNameScreen().selectSiteGroupFromLov(siteGroup).clickOnNumberTab();
		configByName.enetrDataInNumberInput("djhfhd");
		String actWarningMessage=configByName.getWindowPopWarningMessage();
		test.log(LogStatus.INFO, "Actual warning message is equal to " + actWarningMessage);
		Assert.assertEquals(actWarningMessage.trim(), expWarningMessage.trim());	
	}
}
