package com.rediron.apex.testCases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;

public class Login extends BaseTest {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);

	@Test
	public void TC01oginWithValidCredentials() throws Exception {
		String title = loginPage.Logintoapplication(userName, Utility.decodestring(password));
		//test.log(LogStatus.INFO, "Actual title is: " + title);
		Thread.sleep(2000);
		Assert.assertEquals(title, Utility.getGlobalValue("title"));
	}

	@Test
	public void TC02verifyCompanyLogoOnLoginSrreen() throws Exception {

		//boolean status = loginPage.verifyCompanyLogo();

//		if (status) {
//			test.log(LogStatus.PASS, "Company logo is displayed");
//		} else {
//			test.log(LogStatus.FAIL, "Company logo is not displayed");
//			softAssert.assertTrue(false, status + " is not verified");
//		}
//		softAssert.assertAll();

	}

	@Test
	public void TC03verifySiteNoListDataFromDataBase() throws Exception {
		loginPage.enterUserIdAndPasword(userName, Utility.decodestring(password));
		List<String> SiteNameDB = loginPage.getDataFromSiteNameListDB();
		List<String> SiteNameUI = loginPage.getDataFromSiteNameListUI(userName, Utility.decodestring(password));
		SiteNameUI.forEach(e -> System.out.println(e));
		System.out.println("***************");
		SiteNameDB.forEach(e -> System.out.println(e));
	}

	@Test
	public void TC04verifyCancelButton() throws Exception {

		loginPage.enterUserIdAndPasword(userName, Utility.decodestring(password));
		boolean status = loginPage.verifyIsCancelButtonWorking();
//		if (status) {
//			//test.log(LogStatus.PASS, "Clear button functionality is working");
//
//		} else {
//
//			//test.log(LogStatus.FAIL, "Clear button functionality is not working");
//
//			softAssert.assertTrue(false, " is not verified");
//		}
//		softAssert.assertAll();

	}

	@Test
	public void TC05verifySignOutFunctionality() throws Exception {

		loginPage.Logintoapplication(userName, Utility.decodestring(password));
		boolean status = loginPage.isSignOutWorking();
//		if (status) {
//			//test.log(LogStatus.PASS, "User is signOut succesfully");
//
//		} else {
//
//			test.log(LogStatus.FAIL, "user is not signOut succesfully");
//
//			softAssert.assertTrue(false, " is not verified");
//		}
//		softAssert.assertAll();
	}

//	@Test
//	public void TC06verifyViewGroupsFunctionality() throws Exception {
//
//		loginPage.Logintoapplication(userName, Utility.decodestring(password));
//	//	String title = loginPage.isviewGroupsWorking();
//		System.out.println(title);
//		if (title.equalsIgnoreCase("Data View")) {
//			test.log(LogStatus.PASS, "User is moved to view gropu page ");
//
//		} else {
//
//			test.log(LogStatus.FAIL, "User is not moved to view gropu pag");
//
//			softAssert.assertTrue(false, " is not verified");
//		}
//		softAssert.assertAll();
//	}

}
