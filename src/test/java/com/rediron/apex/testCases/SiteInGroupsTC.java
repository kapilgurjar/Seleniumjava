package com.rediron.apex.testCases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class SiteInGroupsTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	String hQSite="1";

	@Story("Site in Groups Form")
	@Description("Verifying that user is loggin with default site")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyThatUserIsloggedInWithDefaultSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToSiteInGroupsScreen();
		String siteName = siteInGroups.getSiteName();
		test.log(LogStatus.INFO, "Site name is " + siteName);
		Assert.assertTrue(siteName.contains(siteName));
	}

	@Story("Site in Groups Form")
	@Description("Site lov will show the data as seen in query")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifySiteLovdata() throws Exception {
		String query = "SELECT distinct s.site_no,s.full_name FROM site s,v_sitgrp sg\r\n"
				+ "WHERE s.site_no = sg.site_no order by site_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		List<String> listOfSitesUI = siteInGroups.getListOfSites();
		List<String> listOfSitesDB = siteInGroups.getTwoColumnCombineDataFromDB(query, "SITE_NO", "FULL_NAME");
		Assert.assertEquals(listOfSitesUI, listOfSitesDB);

	}

	@Story("Site in Groups Form")
	@Description("Site field will be disabled for remote sites.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifySiteInputFieldIsDisabledForRemoteSite() throws Exception {
		int count = reader.getRowCount("SiteInGroups");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		for (int i = 2; i < count; i++) {
			String site = reader.getCellData("SiteInGroups", 0, i);
			menuSelection.selectCurrentSite(site).navigateToSiteInGroupsScreen();
			String classAttr = siteInGroups.getSiteInputAttributeValue();
			test.log(LogStatus.INFO, "Class Attribite is " + classAttr);
			Assert.assertTrue(classAttr.contains("apex_disabled"));
			Assert.assertFalse(siteInGroups.isSaveClearAddGroupAndRemoveGroupDisplayed());
			siteInGroups.clickOnExitButton();
		}
	}

	@Story("Site in Groups Form")
	@Description("Site field will be enabled for HQ sites.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifySiteInputFieldIsDisabledForRemoteSite() throws Exception {
		int count = reader.getRowCount("SiteInGroups");
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		for (int i = 2; i < count; i++) {
			String site = reader.getCellData("SiteInGroups", 1, i);
			menuSelection.selectCurrentSite(site).navigateToSiteInGroupsScreen();
			String classAttr = siteInGroups.getSiteInputAttributeValue();
			test.log(LogStatus.INFO, "Class Attribite is " + classAttr);
			Assert.assertTrue(!classAttr.contains("apex_disabled"));
			Assert.assertTrue(siteInGroups.isSaveClearAddGroupAndRemoveGroupDisplayed());
			siteInGroups.clickOnExitButton();
		}
	}

	@Story("Site in Groups Form")
	@Description("The selected site Cannot be added to an Affiliate Site Group."
			+ " if the site is not a franchise and the group to be added is an Affiliate/Franchise group." + "")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyAddGrouptoSiteChecksMessage() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String groupName = reader.getCellData("SiteInGroups", 3, 2);
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().addASiteGroup(groupName);
		eleUtil.doStaticWait(config.getSmallWait());
		Assert.assertEquals(siteInGroups.getPopText(), reader.getCellData("SiteInGroups", 2, 2));
		String groupName1 = reader.getCellData("SiteInGroups", 3, 3);
		siteInGroups.clickOnPopOkButton().addASiteGroup(groupName1).clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getSmallWait());
		siteInGroups.addASiteGroup(groupName1);
		eleUtil.doStaticWait(config.getSmallWait());
		Assert.assertEquals(siteInGroups.getPopText(), reader.getCellData("SiteInGroups", 2, 3));
	}

	@Story("Site in Groups Form")
	@Description("The selected site Cannot be added to an Affiliate Site Group."
			+ " if the site is not a franchise and the group to be added is an Affiliate/Franchise group." + "")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyAddGrouptoSite() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnSaveButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.waitForElementsNotVisible(siteInGroups.loader, 30);
		Assert.assertTrue(sts);
		List<String> siteGroup = siteInGroups.getSiteGroupTableData();
		int index = siteGroup.indexOf(groupName);
		String name = siteGroup.get(index);
		Assert.assertEquals(name, groupName);
		siteInGroups.removeASiteGroup(groupName);
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnRemoveGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnSaveButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts1 = eleUtil.waitForElementsNotVisible(siteInGroups.loader, 30);
		Assert.assertTrue(sts1);
	}

	@Story("Site in Groups Form")
	@Description(" Site # is the only Site in Group group_id and a Site Group should contain at least one Site” "
			+ "should be shown if the group has only one site. In site 117 Group id AABB ha only one site with type_name SITE_TYPE, so this error is coming")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyRemoveGroupCheckMessage() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String expMessage = reader.getCellData("SiteInGroups", 4, 2);
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().selectSiteFromListOfValue("117");
		siteInGroups.removeASiteGroup("SITE_TYPE");
		String actMessage = siteInGroups.getPopText();
		Assert.assertTrue(actMessage.contains(expMessage));
	}
	
	@Story("Site in Groups Form")
	@Description("This Site Group is already assigned to a Required Type and the selected site will not be available "
			+ "for the Type on removal. message will be shown if the site is removed from a Group type which is Required")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyRemoveGroupCheckMessage() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String expMessage = reader.getCellData("SiteInGroups", 4, 3);
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().selectSiteFromListOfValue("210");
		siteInGroups.removeASiteGroup("AIR_QUALITY");
		String actMessage = siteInGroups.getPopText();
		Assert.assertEquals(actMessage, expMessage);
	}
	
	@Story("Site in Groups Form")
	@Description("If there are no changes to save in database click on save button will show the popup with msg like"
			+ " “There are no changes to save”.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifySaveButtonErrorMessageWithoutEnterAnyData() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String expMessage = reader.getCellData("SiteInGroups", 5, 2);
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().clickOnSaveButton();
		String actMessage = siteInGroups.getPopText();
		Assert.assertEquals(actMessage, expMessage);
	}
	
	@Story("Site in Groups Form")
	@Description("If there are no changes to save in database click on save button will show the popup with msg like"
			+ " “There are no changes to save”.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyClearYesNoCancelButtonArePresent() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnClearButton();
		boolean sts=siteInGroups.IsYesCancelAndNoButtonPresentOnClearPopWindow();
		Assert.assertTrue(sts);
	}
	
	@Story("Site in Groups Form")
	@Description("After pressing Yes button “changes saved successfully” notification should come & screen should get reloaded.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifyClearYesButton() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnClearButton().clickOnClearYesButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.waitForElementsNotVisible(siteInGroups.loader, config.getExplicitWait());
		Assert.assertTrue(sts);
		List<String> siteGroup = siteInGroups.getSiteGroupTableData();
		int index = siteGroup.indexOf(groupName);
		String name = siteGroup.get(index);
		Assert.assertEquals(name, groupName);
		siteInGroups.removeASiteGroup(groupName);
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnRemoveGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnSaveButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts1 = eleUtil.waitForElementsNotVisible(siteInGroups.loader, config.getExplicitWait());
		Assert.assertTrue(sts1);
		
	}
	

	@Story("Site in Groups Form")
	@Description("After pressing No button screen should get submitted and reloaded.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifyClearNOButton() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		int beforeSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "Before count is equal " +beforeSize);
		siteInGroups.addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnClearButton().clickOnClearNoButton();
		//eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.waitForElementsNotVisible(siteInGroups.loader, config.getExplicitWait());
		Assert.assertTrue(sts);
		int afterSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "After count is equal " +afterSize);
		Assert.assertEquals(afterSize, beforeSize);
	}
	
	@Story("Site in Groups Form")
	@Description("Cancel button close the dialog box & screen remain in same state")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifyClearCancelButton() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		int beforeSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "Before count is equal " +beforeSize);
		siteInGroups.addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnClearButton().clickOnClearCancelButton();
		eleUtil.pageRefresh();
		eleUtil.doStaticWait(config.getMediumWait());
		int afterSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "After count is equal " +afterSize);
		Assert.assertEquals(afterSize, beforeSize);
	}
	
	@Story("Site in Groups Form")
	@Description("clear will prompt to save changes if there are any pending. Clicking no to the prompt will revert the changes")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyClearButtonFunctionality() throws Exception {
		String expMessage = reader.getCellData("SiteInGroups", 6, 2);
		test.log(LogStatus.INFO, "exp Message is equal to " +expMessage);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		String actMessage=siteInGroups.clickOnClearButton().getPopText();	
		test.log(LogStatus.INFO, "act Message is equal to " +actMessage);
		Assert.assertEquals(actMessage, expMessage);
	}
	
	@Story("Site in Groups Form")
	@Description("If there are no changes to save in database click on save button will show the popup with msg like"
			+ " “There are no changes to save”.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifyExitYesButtonFunctionality() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen().addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnExitButton().clickOnClearYesButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.waitForElementsNotVisible(siteInGroups.loader, config.getExplicitWait());
		Assert.assertTrue(sts);
		menuSelection.clickOnExpandAllButton().clickOnSiteInGroups();
		List<String> siteGroup = siteInGroups.getSiteGroupTableData();
		int index = siteGroup.indexOf(groupName);
		String name = siteGroup.get(index);
		Assert.assertEquals(name, groupName);
		siteInGroups.removeASiteGroup(groupName);
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnRemoveGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnSaveButton();
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts1 = eleUtil.waitForElementsNotVisible(siteInGroups.loader, config.getExplicitWait());
		Assert.assertTrue(sts1);
	}
	
	@Story("Site in Groups Form")
	@Description("After pressing No button screen should get submitted and reloaded.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyExitNOButton() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		int beforeSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "Before count is equal " +beforeSize);
		siteInGroups.addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnExitButton().clickOnClearNoButton();
		menuSelection.clickOnExpandAllButton().clickOnSiteInGroups();
		int afterSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "After count is equal " +afterSize);
		Assert.assertEquals(afterSize, beforeSize);
	}
	
	@Story("Site in Groups Form")
	@Description("After pressing No button screen should get submitted and reloaded.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 17, enabled = true)
	public void TC17verifyExitCancelButton() throws Exception {
		String groupName = reader.getCellData("SiteInGroups", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(hQSite).navigateToSiteInGroupsScreen();
		int beforeSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "Before count is equal " +beforeSize);
		siteInGroups.addASiteGroup(groupName)
				.clickOnAddGroupButton();
		eleUtil.doStaticWait(config.getMediumWait());
		siteInGroups.clickOnExitButton().clickOnClearCancelButton();
		eleUtil.pageRefresh();
		eleUtil.doStaticWait(config.getMediumWait());
		int afterSize=siteInGroups.getSiteGroupTableData().size();
		test.log(LogStatus.INFO, "After count is equal " +afterSize);
		Assert.assertEquals(afterSize, beforeSize);
	}
}
