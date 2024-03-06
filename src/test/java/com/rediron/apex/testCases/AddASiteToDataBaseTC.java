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

public class AddASiteToDataBaseTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	String currentSite = "1";

	@Story("Add A site To DataBase")
	@Description("Remote sites should get a message This form is not available to a remote site")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyNoAccessToRemoteSite() throws Exception {
		String expMessage = "This form is not available to a Remote Site.";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		String actText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actText);
		Assert.assertEquals(actText, expMessage);
		addASiteToDataBase.clickOnOkButton();
		Assert.assertTrue(addASiteToDataBase.verifyNoAccessToRemoteSite());
	}

	@Story("Add A site To DataBase")
	@Description("New sites added using licensing utility will need to be shown. ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyDataLoadOfSites() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		List<String> newSite = addASiteToDataBase.getSiteDataFromDB();
		Assert.assertTrue(CollectionUtils.isEqualCollection(newSite, addASiteToDataBase.getNewSiteNameUI()));
	}

	@Story("Add A site To DataBase")
	@Description("Lov will display the data for copy sites.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyCopyFromSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		Assert.assertTrue(CollectionUtils.isEqualCollection(addASiteToDataBase.getCopyLovDataDB(),
				addASiteToDataBase.getCopySiteLovDataUI()));
	}

	@Story("Add A site To DataBase")
	@Description("Use this Copy from site # for all null/blank values listed below?"
			+ " Prompt will show if there are multiple records shown. "
			+ "Pressing yes will copy the values to below records for null value rows.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyCopyFromSiteValidationMessageForYesButton() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.selectSiteFromCopyFromSiteColumn(currentSite);
		Assert.assertTrue(addASiteToDataBase.iSCopySiteAlertTitleDispalyed());
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
		boolean sts=addASiteToDataBase.ISDataSelectFormCopyFromSiteColumn(currentSite);
		Assert.assertTrue(sts);
	}
	

	@Story("Add A site To DataBase")
	@Description("Data should list the values for the time zone based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyTimeZone() throws Exception {
		String query="select drc.code_value, drc.code_meaning  from domain_ref_code drc, domain_ref_usage dru\r\n"
				+ "where dru.domain_ref_id = drc.domain_ref_id and dru.table_name = 'SITE'\r\n"
				+ "and dru.column_name = 'TIME_ZONE_CD' order by drc.display_seq";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
	List<String>TimeZonesDB=Utility.getDataFromDatabase(query, "CODE_MEANING");
	List<String>TimeZonesUI=addASiteToDataBase.getTimeZoneLovData();
	Assert.assertTrue(CollectionUtils.isEqualCollection(TimeZonesUI, TimeZonesDB));
		
	}
	
	@Story("Add A site To DataBase")
	@Description("'Use this Time Zone for all new sites listed below to be shown if there are multiple records.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyTimeZoneAlertMessageAndYesAndNoButton() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.selectTimeZone("AST4");
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
		boolean sts=addASiteToDataBase.ISDataSelectFormCopyFromTimeZoneColumn("AST4");
		Assert.assertTrue(sts);
		Thread.sleep(3000);
	}
	
	@Story("Add A site To DataBase")
	@Description("'Use this Time Zone for all new sites listed below to be shown if there are multiple records.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyInCityCheckBoxAlertMessageAndYesAndNoButton() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 4);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.clickOnInCityCheckBox();
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
	}
	
	@Story("Add A site To DataBase")
	@Description("Data should list the values for the owner id description based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyOwnerIDDescriptionAddToSiteDataBaseAgainstTheDB() throws Exception {
		String query="select description, owner_name, owner_id from entity_ownership  order by description";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		List<String>DescUI=addASiteToDataBase.getOwnerIdTableColumnData("1");
		DescUI.forEach(e-> System.out.println(e));
		List<String>DescDB=Utility.getDataFromDatabase(query, "DESCRIPTION");
		Assert.assertEquals(DescUI, DescDB);
		
	}
	
	@Story("Add A site To DataBase")
	@Description("Data should list the values for the owner id owner name based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifyOwnerIDOwnerNameAddToSiteDataBaseAgainstTheDB() throws Exception {
		String query="select description, owner_name, owner_id from entity_ownership  order by description";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		List<String>OwnerNameUI=addASiteToDataBase.getOwnerIdTableColumnData("2");
		OwnerNameUI.forEach(e-> System.out.println(e));
		List<String>OwnerNameDB=Utility.getDataFromDatabase(query, "OWNER_NAME");
		Assert.assertEquals(OwnerNameUI, OwnerNameDB);
		
	}
	
	@Story("Add A site To DataBase")
	@Description("Data should list the values for the owner id based on query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyOwnerIDOwnerIDAddToSiteDataBaseAgainstTheDB() throws Exception {
		String query="select description, owner_name, owner_id from entity_ownership  order by description";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		List<String>ownerIDUI=addASiteToDataBase.getOwnerIdTableColumnData("3");
		ownerIDUI.forEach(e-> System.out.println(e));
		List<String>ownerIDDB=Utility.getDataFromDatabase(query, "OWNER_ID");
		Assert.assertEquals(ownerIDUI, ownerIDDB);	
	}
	
	@Story("Add A site To DataBase")
	@Description("Use this Owner ID for all new sites listed below shown if there are multiple record."
			+ " On yes only null records  will be populated.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyOwnerIdAlertMessageAndYesAndNoButton() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 5);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.selectOwnerId("3","6");
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
		boolean sts=addASiteToDataBase.ISDataSelectFormCopyFromOwnerID("6");
		Assert.assertTrue(sts);
	}
	
	@Story("Add A site To DataBase")
	@Description("Use this Affiliate for all new sites listed below ?"
			+ " Will be shown if here are multiple records. On yes null valued records will be updated.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = true)
	public void TC012verifyAffiliateCheckBoxAlertMessageAndYesAndNoButton() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 6);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.clickOnAffiliateCheckBox();
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
	}
	@Story("Add A site To DataBase")
	@Description("If we make any change and click on clear button then it will show a alert message")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifyClearButtonOnAddSiteToDataBase() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 7);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.selectSiteFromCopyFromSiteColumn(currentSite);
		eleUtil.clickWhenReady(addASiteToDataBase.copyAlertNOBtn, config.getExplicitWait());
		addASiteToDataBase.clickOnClearButton();
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
		//Verify  for no button
		eleUtil.clickWhenReady(addASiteToDataBase.copyAlertNOBtn, config.getExplicitWait());
		eleUtil.doStaticWait(config.getMediumWait());
		String actValue=addASiteToDataBase.isCopySiteInputEmpty();
		Assert.assertEquals(actValue, currentSite);
		//Verify for yes button
		eleUtil.pageRefresh();
		addASiteToDataBase.selectSiteFromCopyFromSiteColumn(currentSite);
		eleUtil.clickWhenReady(addASiteToDataBase.copyAlertNOBtn, config.getExplicitWait());
		addASiteToDataBase.clickOnClearButton();
		eleUtil.clickWhenReady(addASiteToDataBase.copyAlertYesBtn, config.getExplicitWait());
		eleUtil.doStaticWait(config.getMediumWait());
		String Value=addASiteToDataBase.isCopySiteInputEmpty();
		boolean status=Value.isEmpty();
		Assert.assertTrue(status);
	}
	
	@Story("Add A site To DataBase")
	@Description("If we make any change and click on clear button then it will show a alert message")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifySearchOnMainScreen() throws Exception {
		String newSite="528";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		String expNewSite=addASiteToDataBase.SearchSite(newSite).getNewSiteText();
		test.log(LogStatus.INFO, "Exp new site is " + expNewSite);
		Assert.assertTrue(expNewSite.contains(newSite));
		eleUtil.pageRefresh();
		addASiteToDataBase.SearchSite("sdjsh");
		Assert.assertTrue(addASiteToDataBase.iSNoResultFoundDisplayed());
	}
	
	@Story("Add A site To DataBase")
	@Description("If we make any change and click on clear button then it will show a alert message")
	@Severity(SeverityLevel.CRITICAL)
	//@Test(priority = 14, enabled = true)
	public void TC14verifyEditScreen() throws Exception {//TODO
		Random random = new Random();
		int num = random.nextInt(99) + 1;
		String number = String.valueOf(num);
		String newAddress="3A1, building 44-"+number;
		String newPhone="123-4567 Ext:898-"+number;
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.clickOnEditAdressButton();
		String beforeEditAdd=addASiteToDataBase.getEditAdress1Value();
		test.log(LogStatus.INFO, "Before edit adress1 value is  " + beforeEditAdd);
		String beforeEditPH=addASiteToDataBase.getEditPhoneValue();
		test.log(LogStatus.INFO, "Before edit Phone value is  " + beforeEditPH);
		addASiteToDataBase.enterDataInAdress1AndPhoneTextbox(newAddress, newPhone);
		eleUtil.doStaticWait(config.getMediumWait());
		addASiteToDataBase.clickOnEditAdressButton();
		String afterEditAdd=addASiteToDataBase.getEditAdress1Value();
		test.log(LogStatus.INFO, "After edit adress1 value is  " + afterEditAdd);
		String afterEditPH=addASiteToDataBase.getEditPhoneValue();
		test.log(LogStatus.INFO, "After edit Phone value is  " + afterEditPH);
		
	}
	
	@Story("Add A site To DataBase")
	@Description("After submission show alert for no of rows submitted for processing.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifySubmitsiteaddition() throws Exception {
		String expAlertMessage = reader.getCellData("AddASiteToDB", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(currentSite).clickOnManageUtilities().clickOnExpandAllButton()
				.navigateToAddSiteToDataBaseScreen();
		addASiteToDataBase.selectSiteFromCopyFromSiteColumn(currentSite);
		Assert.assertTrue(addASiteToDataBase.iSCopySiteAlertTitleDispalyed());
		String actAlertText = addASiteToDataBase.getAlertPopMessage().trim();
		test.log(LogStatus.INFO, "Actual message is " + actAlertText);
		Assert.assertEquals(actAlertText, expAlertMessage);
		boolean sts=addASiteToDataBase.ISDataSelectFormCopyFromSiteColumn(currentSite);
		Assert.assertTrue(sts);
		addASiteToDataBase.clickOnSubmitCheckedItemButton();
		String expMessage=addASiteToDataBase.getAlertPopMessage().trim();
		Assert.assertTrue(expMessage.contains("record was submitted to the queue."));
		addASiteToDataBase.clickOnOkButton();
		try {
			System.out.println("Do nothing");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			Utility.executeandcommit("delete from add_site_queue where site_no=528");
		}
	}
	
	
	
}
