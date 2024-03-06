package com.rediron.apex.testCases;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.factory.DriverFactory;
import com.rediron.apex.pages.CashDrawerSetUp;
import com.rediron.apex.pages.Login;
import com.rediron.apex.pages.LoginPageNew;
import com.rediron.apex.pages.MenuSelection;
import com.rediron.apex.utils.ExcelUtil;

public class BaseTest {

	DriverFactory df;
	Properties prop;
	WebDriver driver;
	Login loginPage;
	MenuSelection menuSelection;
	CashDrawerSetUp cashDrawerSetup;
	SoftAssert softAssert;
	ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");

	//@Parameters({ "browser", "browserversion" })
	//String browser, String browserVersion)
	@BeforeTest
	public void setup() {
		df = new DriverFactory();
		prop = df.init_prop();

//		if (browser != null) {
//			prop.setProperty("browser", browser);
//			prop.setProperty("browserversion", browserVersion);
//		}

		driver = df.init_driver(prop);
		loginPage = new Login(driver);
		menuSelection= new MenuSelection(driver);
		cashDrawerSetup= new CashDrawerSetUp(driver);
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
