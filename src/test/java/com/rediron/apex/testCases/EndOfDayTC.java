package com.rediron.apex.testCases;
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

public class EndOfDayTC extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);

	@Story("End Of Day")
	@Description("Verify Page Title of End Of Day Screen")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyPageTitle() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen();
		Assert.assertEquals(endOfDay.getPageTitle(), "End Of Day");
	}

	@Story("End Of Day")
	@Description("Verify Screen default data on UI")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyDefaultDataSetOnUI() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen();
		Assert.assertEquals(endOfDay.getTransDateValue("03/14/2020"), endOfDay.getOpenDateValue());
	}

	@Story("End Of Day")
	@Description("This will show text (Site has - open days from date_from to date_to)")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyUpdateUIMessageWithOpenDays() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen().getTransDateValue("03/14/2020");
		String uIMessage = endOfDay.getFromToDateUIMessage();
		String OpenDays = Utility.executeQuery(
				"SELECT  count(1) ndays\r\n" + "		FROM	dlysite\r\n" + "		WHERE	site_no  = 103\r\n"
						+ "		AND		tran_dt >= '08-03-2020'\r\n" + "		AND     tran_dt <= '14-03-2020'",
				"NDAYS");
		Assert.assertTrue(uIMessage.contains(OpenDays));
	}

	@Story("End Of Day")
	@Description("Verify Screen default data on UI")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 4, enabled = true)
	public void TC04verifyTransactionDateAgainstDB() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen();
		boolean sts=CollectionUtils.isEqualCollection(endOfDay.getTotalTransDateUI(), endOfDay.gettransDateDB());
		Assert.assertTrue(sts);

	}

	@Story("End Of Day")
	@Description("Date description for entered date will be populated")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyDateDesc() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		boolean status = menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen()
				.IsDateDescriptionPresentUI();
		Assert.assertEquals(status, true);
	}

	@Story("End Of Day")
	@Description("Verify The date is mandatory")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyTheDateIsMandatory() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen().verifyDateIsMandatory();
		Assert.assertEquals(endOfDay.getWindowPopText(), "Date must be entered.");
	}
	
	@Story("End Of Day")
	@Description("Verify exit button working on EOD screen")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyExitButtonOnEOD() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen().clickOnExitButton();
		boolean sts=menuSelection.IsExpanButtonDisplayed();
		if(sts) {
			test.log(LogStatus.PASS, "Exit button is working and user on menu selection screen");
		}else {
			test.log(LogStatus.FAIL, "Exit button is not working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();	
	}
	
	@Story("End Of Day")
	@Description("When EOD_POST_PO = 'Y' if there are unposted PO's between the dates "
			+ "'There are unposted purchase orders for this date.' will be shown or "
			+ "'There are unposted purchase orders for this date range' when the end date range is greater than 1")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifyENDPOSTPO() throws Exception {
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEndOfDayScreen().clickOnExitButton();
		boolean sts=menuSelection.IsExpanButtonDisplayed();
		if(sts) {
			test.log(LogStatus.PASS, "Exit button is working and user on menu selection screen");
		}else {
			test.log(LogStatus.FAIL, "Exit button is not working");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();	
	}
	
	
	
	
}
