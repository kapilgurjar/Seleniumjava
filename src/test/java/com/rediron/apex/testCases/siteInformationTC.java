package com.rediron.apex.testCases;

import java.util.List;
import java.util.Random;

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

public class siteInformationTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);

	@Story("Site Information")
	@Description("Every required fields needs to be filled before save is done. Show message when a required fields is not filled")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyRequiredFields() throws Exception {
		String expPopMsg = reader.getCellData("GSI", 1, 2).trim();
		test.log(LogStatus.INFO, "Exp message is = " + expPopMsg);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actMessage = siteInformation.getReqAddressValMessage().trim();
		test.log(LogStatus.INFO, "Act message is = " + actMessage);
		Assert.assertEquals(actMessage, expPopMsg);
		test.log(LogStatus.INFO, "Act message is equal to exp message ");
	}

	@Story("Site Information")
	@Description("Data will be shown from domain code for LANGUAGE CODES")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyLocaleLanguageOnLayawayInfoLocaleTab() throws Exception {
		String query = "SELECT code_value,code_meaning  FROM domain_ref_code \r\n"
				+ "WHERE domain_ref_id IN (select domain_ref_id from domain_ref where description = 'LANGUAGE CODES' )\r\n"
				+ "Order by code_value";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		List<String> uIListLang = siteInformation.getLocalLangListFromUI();
		List<String> dBListLang = siteInformation.getLocalListFromDB(query, "CODE_VALUE", "CODE_MEANING");
		Assert.assertEquals(uIListLang, dBListLang);

	}

	@Story("Site Information")
	@Description("Data will be shown from domain code for COUNTRY CD")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyLocaleCountryOnLayawayInfoLocaleTab() throws Exception {
		String query = "SELECT code_value,code_meaning  FROM domain_ref_code \r\n"
				+ "WHERE domain_ref_id IN (select domain_ref_id from domain_ref where description = 'COUNTRY CD' ) \r\n"
				+ "Order by code_value";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		List<String> uIListCountry = siteInformation.getLocalCountryListFromUI();
		List<String> dBListCountry = siteInformation.getLocalListFromDB(query, "CODE_VALUE", "CODE_MEANING");
		Assert.assertEquals(uIListCountry, dBListCountry);

	}

	@Story("Site Information")
	@Description("Data will be shown from domain code for COUNTRY CD")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyTrailerMessageOnMiscellaneousTab() throws Exception {
		String query = "select distinct h.message_cd message_cd, \r\n" + "h.description description\r\n"
				+ "FROM   softmsg_hdr h, softmsg d\r\n" + "WHERE  d.message_cd = h.message_cd\r\n"
				+ "AND    h.message_type_cd = 'DISC'\r\n" + "AND    d.site_no =103\r\n" + "Order by h.message_cd";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		List<String> uIListCountry = siteInformation.getTrailermessageListFromUI();
		List<String> dBListCountry = siteInformation.getLocalListFromDB(query, "MESSAGE_CD", "DESCRIPTION");
		Assert.assertEquals(uIListCountry, dBListCountry);

	}

	@Story("Site Information")
	@Description("Verify Zip code table search with wrong data")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyZipCodeTableWithWrongData() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen().clickOnLookUpButton().enterDataInSearchZipCodeInputBox("jdhjdjh");
		boolean sts = siteInformation.isNoResultMessageDisplayed();
		Assert.assertEquals(sts, true);
	}

	@Story("Site Information")
	@Description("Verify Zip code table search with wrong data")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyZipCodeTableWithValidData() throws Exception {
		String zipCode = "05681";
		test.log(LogStatus.INFO, "Expected zip code value is " + zipCode);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen().clickOnLookUpButton().enterDataInSearchZipCodeInputBox(zipCode);
		String actZipCode = siteInformation.getTextFromZipTable();
		test.log(LogStatus.INFO, "Actual zip code value is " + actZipCode);
		Assert.assertEquals(actZipCode, zipCode);
	}

	@Story("Site Information")
	@Description("Verify city,state,zip and Country field should be disabled, if we set validate zip code flag to Y")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyZipCodeWithFlagY() throws Exception {
		Utility.executeandcommit("declare \r\n" + "p_flag Varchar2(1);\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.set_flag('VALIDATE_ZIP','Y');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		softAssert.assertTrue(siteInformation.getCityClassAttribute().contains("apex_disabled"));
		softAssert.assertTrue(siteInformation.getStateClassAttribute().contains("apex_disabled"));
		softAssert.assertTrue(siteInformation.getZipClassAttribute().contains("apex_disabled"));
		softAssert.assertTrue(siteInformation.getCountryClassAttribute().contains("apex_disabled"));
		softAssert.assertAll();

	}

	@Story("Site Information")
	@Description("Verify city,state,zip and Country field should be Enabled, if we set validate zip code flag to N")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyZipCodeWithFlagN() throws Exception {
		Utility.executeandcommit("declare \r\n" + "p_flag Varchar2(1);\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
				+ "ret := tmxgbl.set_flag('VALIDATE_ZIP','N');\r\n" + "end;");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		try {
			softAssert.assertTrue(!siteInformation.getCityClassAttribute().contains("apex_disabled"));
			softAssert.assertTrue(!siteInformation.getStateClassAttribute().contains("apex_disabled"));
			softAssert.assertTrue(!siteInformation.getZipClassAttribute().contains("apex_disabled"));
			softAssert.assertTrue(!siteInformation.getCountryClassAttribute().contains("apex_disabled"));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			Utility.executeandcommit("declare \r\n" + "p_flag Varchar2(1);\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.set_flag('VALIDATE_ZIP','Y');\r\n" + "end;");
		}

	}

	@Story("Site Information")
	@Description("Verify configuration button and verify title of that page")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyConfigurationButtonOnSiteInformation() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String title = siteInformation.getGeneralSiteConfigurationTitle("General Site Configuration");
		test.log(LogStatus.PASS, "Title value is " + title);
	}

	@Story("Site Information")
	@Description("Verify that Exiting the config UI should close and show general site UI.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyConfigurationButtonBychangeDataInSiteInformationScreen() throws Exception { // TODO Yes button
																										// functionality
																										// is not
																										// working
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		// Checking for no
		String actAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress1 value is " + actAdress);
		String newAdress = actAdress + num;
		test.log(LogStatus.INFO, "Adress1 value is " + newAdress);
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnConfigurationButton();
		siteInGroups.clickOnClearNoButton();
		eleUtil.doStaticWait(config.getMediumWait());
		generalSiteConfiguration.clickOnExitButton();
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "No button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "No button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

		// Checking for yes
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnConfigurationButton();
		siteInGroups.clickOnClearYesButton();
		eleUtil.doStaticWait(config.getMediumWait());
		String afterAdressYes = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value  after click on yes button is " + afterAdressYes);
		Assert.assertNotEquals(actAdress, afterAdressYes);

	}

	@Story("Site Information")
	@Description("Verify that Exiting the config UI should close and show general site UI.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyConfigurationButtonBychangeDataInSiteInformationScreenClickOnCancel() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress1 value is " + actAdress);
		String newAdress = actAdress + num;
		test.log(LogStatus.INFO, "Adress1 value is " + newAdress);
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnConfigurationButton();
		siteInGroups.clickOnClearCancelButton();
		eleUtil.pageRefresh();
		eleUtil.doStaticWait(config.getMediumWait());
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "Cancel button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "Cancel button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Site Information")
	@Description("Verify site group button and verify title of that page")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifySiteGroupButtonOnSiteInformation() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		siteInformation.clickOnSiteGroupButton();
		String title = eleUtil.doGetPageTitleIs("Site in Group", config.getExplicitWait());
		test.log(LogStatus.PASS, "Title value is " + title);
	}

	@Story("Site Information")
	@Description("Check for changes before calling the site group UI.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifySiteGroupButtonBychangeDataInSiteInformationScreen() throws Exception { // TODO Yes button
																									// functionality is
																									// not working
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		// Checking for no
		String actAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress1 value is " + actAdress);
		String newAdress = actAdress + num;
		test.log(LogStatus.INFO, "Adress1 value is " + newAdress);
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnSiteGroupButton();
		siteInGroups.clickOnClearNoButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnExitButton();
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "No button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "No button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

		// Checking for yes
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnSiteGroupButton();
		siteInGroups.clickOnClearYesButton();
		eleUtil.doStaticWait(config.getMediumWait());
		String afterAdressYes = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value  after click on yes button is " + afterAdressYes);
		Assert.assertNotEquals(actAdress, afterAdressYes);
	}

	@Story("Site Information")
	@Description("Verify that Exiting the config UI should close and show general site UI.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifySiteGroupButtonBychangeDataInSiteInformationScreenClickOnCancel() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress1 value is " + actAdress);
		String newAdress = actAdress + num;
		test.log(LogStatus.INFO, "Adress1 value is " + newAdress);
		siteInformation.changeAdress1Data(newAdress);
		siteInformation.clickOnSiteGroupButton();
		siteInGroups.clickOnClearCancelButton();
		eleUtil.pageRefresh();
		eleUtil.doStaticWait(config.getMediumWait());
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "Cancel button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "Cancel button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Site Information")
	@Description("Unsaved changes prompt will be shown before exit.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifyExitButtonCancelFunOnSiteInformationScreen() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actAdress = siteInformation.verifyExitButton(num);
		// 1. If user click on cancel Button
		siteInGroups.clickOnClearCancelButton();
		eleUtil.pageRefresh();
		eleUtil.doStaticWait(config.getMediumWait());
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "Exit button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "Exit button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Site Information")
	@Description("Unsaved changes prompt will be shown before exit.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyExitButtonNoFunOnSiteInformationScreen() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actAdress = siteInformation.verifyExitButton(num);
		// 1. If user click on cancel Button
		siteInGroups.clickOnClearNoButton();
		Assert.assertTrue(generalSiteConfiguration.IsExpanAllButtonDisplayed());
		test.log(LogStatus.PASS, "User is on menu page");
		eleUtil.doStaticWait(config.getMediumWait());
		menuSelection.clickOnExpandAllButton().navigateToSiteInformationScreen();
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		if (actAdress.equalsIgnoreCase(afterAdress)) {
			test.log(LogStatus.PASS, "Exit button is visible on UI and working");
		} else {
			test.log(LogStatus.FAIL, "Exit button is not visible on UI and working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Site Information")
	@Description("Unsaved changes prompt will be shown before exit.")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority = 17, enabled = true)
	public void TC17verifyExitButtonYesFunOnSiteInformationScreen() throws Exception { // TODO Need to rework on this
																						// test case after bug fix
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actAdress = siteInformation.verifyExitButton(num);
		// 1. If user click on cancel Button
		siteInGroups.clickOnClearYesButton();
		// Assert.assertTrue(generalSiteConfiguration.IsExpanAllButtonDisplayed());
		// test.log(LogStatus.PASS, "User is on menu page");
		eleUtil.doStaticWait(config.getMediumWait());
		// menuSelection.clickOnExpandAllButton().navigateToSiteInformationScreen();
		String afterAdress = siteInformation.getAdress1Data();
		test.log(LogStatus.INFO, "Adress2 value is " + afterAdress);
		Assert.assertNotEquals(actAdress, afterAdress);
	}

	@Story("Site Information")
	@Description("Save button will be disabled till the user makes a change. Enabled after any change in UI.")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 18, enabled = true)
	public void TC18verifySaveButtonFunOnSiteInformationScreen() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(99) + 1;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String beforeAttribute = siteInformation.getSaveButtonClassAttr();
		test.log(LogStatus.INFO, "Save button class attribute befor any change is " + beforeAttribute);
		Assert.assertTrue(beforeAttribute.contains("apex_disabled"));
		String actAdress = siteInformation.getAdress1Data();
		String newAdress = actAdress + num;
		siteInformation.changeAdress1Data(newAdress);
		String afterAttribute = siteInformation.getSaveButtonClassAttr();
		test.log(LogStatus.INFO, "Save button class attribute after any change is " + afterAttribute);
		Assert.assertTrue(!afterAttribute.contains("apex_disabled"));
	}

	@Story("Site Information")
	@Description("Lalo gm cannot be greater than halo gm. Follow format mask")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 19, enabled = true)
	public void TC19verifyLoloGm() throws Exception {
		String expMesage = reader.getCellData("GSI", 1, 3).trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + expMesage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actMessage = siteInformation.getLaloGmValMessage().trim();
		test.log(LogStatus.INFO, "Actual alert pop message is :  " + actMessage);
		Assert.assertEquals(actMessage, expMesage);
	}

	@Story("Site Information")
	@Description("Attribute name will show values from the shown query")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 20, enabled = true)
	public void TC20verifySiteAttrributesNameAlongWithDataBase() throws Exception {
		String query = "select an.attribute_id, an.name_text \r\n"
				+ "from attribute_name an, ATTRIBUTE_FUNCTIONAL_USAGE afs \r\n"
				+ "where afs.attribute_id = an.attribute_id \r\n" + "and an.active_fl = 'Y' \r\n"
				+ "and afs.functional_area_cd = 'SITE'\r\n" + "order by an.attribute_id";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		List<String> attributeNameUI = siteInformation.getAtrributeNameFromAtrributeScreen();
		List<String> attributeNameDB = Utility.getDataFromDatabase(query, "NAME_TEXT");
		System.out.println(attributeNameUI.size());
		Boolean sts = CollectionUtils.isEqualCollection(attributeNameUI, attributeNameDB);
		if (sts) {
			test.log(LogStatus.PASS, "Atrribute name is matching with database");
		} else {
			test.log(LogStatus.FAIL, "Atrribute name is not matching with database");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	@Story("Site Information")
	@Description("Attribute Value will show data from the query")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 20, enabled = true)
	public void TC21verifySiteAttrributesValueAlongWithDataBase() throws Exception {
		String query = "select code_value, code_meaning\r\n" + "from attribute_list_value  \r\n"
				+ "where attribute_id = 58\r\n" + "order by code_value";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("108").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		List<String> attributeNameUI = siteInformation.getAtrributeValueFromAtrributeScreen();
		List<String> attributeNameDB = Utility.getDataFromDatabase(query, "CODE_VALUE");
		System.out.println(attributeNameUI.size());
		Boolean sts = CollectionUtils.isEqualCollection(attributeNameUI, attributeNameDB);
		if (sts) {
			test.log(LogStatus.PASS, "Atrribute value is matching with database");
		} else {
			test.log(LogStatus.FAIL, "Atrribute value is not matching with database");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}
	
	@Story("Site Information")
	@Description("Duplicate attributes can't be added. Message will be shown.")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 22, enabled = true)
	public void TC22verifyDuplicateAttributesCantBeAdded() throws Exception {
		String expMesage = reader.getCellData("GSI", 1, 4).trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + expMesage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("108").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actMessage=siteInformation.getDuplicateAttributeMessage().trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + actMessage);
		Assert.assertEquals(actMessage, expMesage);
		

	}
	
	@Story("Site Information")
	@Description("Duplicate attributes can't be added. Message will be shown.")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 23, enabled = true)
	public void TC23verifyDuplicateAttributesCantBeAdded() throws Exception {
		String expMesage = reader.getCellData("GSI", 1, 5).trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + expMesage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("108").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		String actMessage=siteInformation.getAddRowBlankMessage().trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + actMessage);
		Assert.assertEquals(actMessage, expMesage);
	}
	
	@Story("Site Information")
	@Description("Duplicate attributes can't be added. Message will be shown.")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 24, enabled = true)
	public void TC24verifySaveAndDeleteOnSiteAttributeScreen() throws Exception {
		String expMesage = reader.getCellData("GSI", 1, 5).trim();
		test.log(LogStatus.INFO, "Expected alert pop message is : " + expMesage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite("108").clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToSiteInformationScreen();
		boolean  sts=siteInformation.verifySaveAndDeleteOnSiteAttributeScreen();
		System.out.println(sts);
	}

}
