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
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class ViewSiteClosing extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNumber = reader.getCellData("ViewSiteClosing", 3, 2);

	@Test(priority = 1, enabled = true)
	public void TC01verifySiteGroupWithCorporateHQ() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing().clickOnSiteGroup();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> siteGroupName = viewSiteClosing.getSiteGroupName();
		siteGroupName.forEach(e -> test.log(LogStatus.PASS, e));

	}

	@Severity(value = SeverityLevel.NORMAL)
	@Description("Test Case Description: verify site group for francchise hq")
	@Story("Story Name: View Site closing date")
	@Test(priority = 2, enabled = true)
	public void TC02verifySiteGroupWithFranchiseHQ() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 3);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing().clickOnSiteGroup();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> siteGroupName = viewSiteClosing.getSiteGroupName();
		siteGroupName.forEach(e -> test.log(LogStatus.PASS, e));

	}

	@Test(priority = 3, enabled = true)
	public void TC03verifySiteGroupWithRemoteSites() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNumber).clickOnManagerOperations();
		int count = reader.getRowCount("ViewSiteClosing");
		for (int i = 2; i <= count; i++) {
			String siteNum = reader.getCellData("ViewSiteClosing", 2, i);
			menuSelection.selectCurrentSite(siteNum);
			eleUtil.doStaticWait(config.getMediumWait());
			menuSelection.navigateTOSiteClosing();
			String siteClassAttr = viewSiteClosing.getSiteGroupClassAttr();
			// Assert.assertTrue(siteClassAttr.contains("disabled"));
			if (siteClassAttr.contains("disabled")) {
				test.log(LogStatus.PASS, "Site group text filed is disabled for remote site " + siteNum + "");
			} else {
				test.log(LogStatus.FAIL, "Site group text filed is not disabled for remote site " + siteNum + "");
				softAssert.assertTrue(false, "Is not verified");
			}
			softAssert.assertAll();
			viewSiteClosing.clickOnExitButton();
		}

	}

	//@Test(priority = 4, enabled = true)//TODO rework Needed as this test case is unstable 
	public void TC04verifyAllSitesCountandSiteNO() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 2);
		String query = "SELECT distinct s.site_no, trunc(s.last_day_closed_dt) last_day_closed_date, trunc(cd.config_dt) current_date, full_name Site_desc\r\n"
				+ ", (SELECT trunc(max(tran_dt)) acct_close_dt\r\n" + "FROM dlysite\r\n"
				+ "WHERE site_no = s.site_no\r\n" + "and acct_close_dt is not null\r\n"
				+ ") last_accounting_close_date\r\n" + "FROM site s, configdate cd\r\n"
				+ "WHERE s.site_no in (Select site_no from sitgrp where group_id='ALL')\r\n"
				+ "AND cd.site_no = s.site_no\r\n" + "AND cd.name = 'CURRENT_DAY'\r\n" + "ORDER BY s.site_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing().clickOnSiteGroup();
		List<String> allSiteNoDB = Utility.getDataFromDatabase(query, "SITE_NO");
		System.out.println(allSiteNoDB.size());
		List<String> allSiteNoUI = viewSiteClosing.getGetTableColoumData("1");
		System.out.println(allSiteNoUI.size());
		Assert.assertEquals(allSiteNoDB.size(), allSiteNoUI.size());
		boolean sts = CollectionUtils.isEqualCollection(allSiteNoUI, allSiteNoDB);
		if (sts) {
			test.log(LogStatus.PASS, "All site no is present in DB");
		} else {
			test.log(LogStatus.PASS, "All site no is not present in DB");
			softAssert.assertTrue(false, "Is not verified");
		}

	}

	@Test(priority = 5, enabled = true)
	public void TC05verifyLastDayClosedDateAndCurrentDate() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 2);
		String query = "SELECT distinct s.site_no, trunc(s.last_day_closed_dt) last_day_closed_dt, trunc(cd.config_dt) current_day, full_name\r\n"
				+ "	FROM site s, configdate cd\r\n" + "	WHERE s.site_no = 1\r\n" + "	AND cd.site_no = s.site_no\r\n"
				+ "	AND cd.name = 'CURRENT_DAY'\r\n" + "	ORDER BY s.site_no";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing();
		String lastCloseDateDB = Utility.executeQuery(query, "LAST_DAY_CLOSED_DT");
		String currentDateDB = Utility.executeQuery(query, "CURRENT_DAY");
		System.out.println(lastCloseDateDB);
		System.out.println(currentDateDB);
		String lastCloseDateUI = viewSiteClosing.getLastDayClosedDate();
		Date lastdate = new SimpleDateFormat("MM/dd/yy").parse(lastCloseDateUI);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String actLastCloseDateUI = formatter.format(lastdate);
		Assert.assertEquals(actLastCloseDateUI, lastCloseDateDB);
		test.log(LogStatus.PASS, "Last close date is displaying on UI");
		String currentDateUI = viewSiteClosing.getCurrentDate();
		Date Currentdate = new SimpleDateFormat("MM/dd/yy").parse(currentDateUI);
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String actcurrentDateUI = formatter1.format(Currentdate);
		System.out.println(actcurrentDateUI);
		System.out.println("actcurrentDateUI" + actcurrentDateUI);
		Assert.assertEquals(actcurrentDateUI, currentDateDB);
		test.log(LogStatus.PASS, "Current date is displaying on UI");

	}

	@Test(priority = 6, enabled = true)
	public void TC06verifyChangeOfSiteGroupShouldRefreshTheReportData() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing();
		String beforeData = viewSiteClosing.getTableCoulmnData();
		System.out.println(beforeData);
		viewSiteClosing.clickOnExitButton();
		String siteNum1 = reader.getCellData("ViewSiteClosing", 3, 4);
		menuSelection.selectCurrentSite(siteNum1);
		eleUtil.doStaticWait(config.getMediumWait());
		menuSelection.navigateTOSiteClosing();
		String afterData = viewSiteClosing.getTableCoulmnData();
		System.out.println(afterData);
		if (!beforeData.equals(afterData)) {
			test.log(LogStatus.PASS, "Before data and after data is not equal");
		} else {
			test.log(LogStatus.FAIL, "Before data and after data is  equal");

		}
	}

	@Test(priority = 7, enabled = true)
	public void TC07verifyExitButton() throws Exception {
		String siteNum = reader.getCellData("ViewSiteClosing", 3, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).clickOnManagerOperations().navigateTOSiteClosing().clickOnExitButton();
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

}
