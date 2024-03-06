package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ExcelUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

public class SafeWithdrawl extends TestBase {

	int count = 0;
	public ElementUtil eleUtil;

	By chkAuditBtn = By.xpath("(//div[@id='Tender']//div[@class=\"t-Region-header\"]//button)[1]");
	By overShort = By.id("P13_OVER_SHORT_1_CONTAINER");
	By siteNo = By.xpath("//label[text()='Site No']/../..//div/input");
	By siteName = By.xpath("//label[text()='Site Name']/../..//div/input");
	public By txnDateLovBtn = By.xpath("//span[contains(@id,'TRANSACTION_DATE-container')]");
	// changed below
	By transactionDate = By.xpath("//ul[contains(@id,'TRANSACTION_DATE-results')]/li");
	By tenderTable = By.xpath("(//table)[7]//tbody//tr");
	public By typeLov = By.xpath("//select[contains(@id,'TYPE')]");
	By currencyTable = By.xpath("//h2[text()='Currency']//ancestor::div[3]//tbody/tr");
	By coinTableCount = By.xpath("//h2[text()='Coin']//ancestor::div[3]//tbody/tr");
	By authorizationWindow = By.xpath("//div[@id='authorization']");
	public By cancelBtnOnAuthWindow = By.xpath("//div[@id='authorization']//button/span[text()='Cancel']");
	public By okBtnOnAuthWindow = By.xpath("//div[@id='authorization']//button/span[text()='Ok']");
	By authorizationEmployee = By.xpath("//input[contains(@id,'AUTH_CASHIER')]");
	By authorizationPassword = By.xpath("//input[contains(@id,'AUTH_PASSWORD')]");
	By saveBtn = By.xpath("//button[@id='save_button']");
	public By tenderAuditBtn = By.xpath("//button[@id='Tender_Audit_Btn']");
	public By ExptdAmtSection = By.xpath("(//th/span[contains(text(),'Expected Amount')])[1]");
	public By overShortLbl = By.xpath("//label[contains(@id,'OVER_SHORT_1')]");
	public By totalExpAmtLbl = By.xpath("//label[contains(@id,'EXPECTED_AMOUNT')]");
	By errMsg = By.xpath("//div[@role='alertdialog']//p");
	public By okBtnOnDlgBox = By.xpath("//button[text()='OK']");
	public By checkAuditBtn = By.xpath("//button[@id='check_audit']");
	public By safeReconMsg = By.xpath("//div[contains(@class,'notification ui-widget')]//div/p");
	public By safeWithdrawalPage = By.xpath("(//div[@class='t-BreadcrumbRegion-body']//li)[3]/h1");
	public By clearBtn = By.xpath("(//div[@class='t-BreadcrumbRegion-buttons']//button)[2]");
	public By clrBtnPopUpMsg = By.xpath("//div[contains(@class,'notification ui-widget')]//div/p");
	public By cancelBtnOnDlgBox = By.xpath("//button[text()='Cancel']");
	public By checkField = By.xpath("(//td[text()='CHECK']/..//td)[2]//input");
	public By totalAmt = By.xpath("//input[contains(@id,'EXPECTED_AMOUNT')]");
	public By actualAmt = By.xpath("//input[contains(@id,'ACTUAL_AMOUNT')]");
	public By overShortAmt = By.xpath("//input[contains(@id,'OVER_SHORT')]");
	public By currencyName1 = By.xpath("(//tbody//td[text()='$1']/..//td)[2]/input");
	By currencyName5 = By.xpath("(//tbody//td[text()='$5']/..//td)[2]/input");
	By currencyName10 = By.xpath("(//tbody//td[text()='$10']/..//td)[2]/input");
	By currencyName20 = By.xpath("(//tbody//td[text()='$20']/..//td)[2]/input");
	By currencyName50 = By.xpath("(//tbody//td[text()='$50']/..//td)[2]/input");
	By currencyName100 = By.xpath("(//tbody//td[text()='$100']/..//td)[2]/input");
	By tenderRows = By.xpath("//div[@id='Tender_data_panel']//tbody//tr");
	By txDateDropdown = By.xpath("//select[contains(@id,'TRANSACTION_DATE')]/..//span/b");

	public SafeWithdrawl() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
	}

	public String getSiteNoAttributes() {
		return eleUtil.doGetAttribute(siteNo, "class");
	}

	public String getSiteNameAttributes() {
		return eleUtil.doGetAttribute(siteName, "class");
	}

	public List<String> getTransactionDate() {
		return eleUtil.getElementsTextList(transactionDate);
	}

	public void clickOnTxnDateLovBtn() {
		eleUtil.doClick(txnDateLovBtn);
	}

	public boolean verifyAuthorizationPopUpTitle() {

		return eleUtil.doIsDisplayed(authorizationWindow);

	}

	/**
	 * This method will type lov data from UI
	 */
	public List<String> getTypeLovData() {
		return eleUtil.getDropDownOptionsList(typeLov);
	}

	public void clickOnSaveButton() {
		eleUtil.doClick(saveBtn);
	}

	public List<String> getTransactionDateDB(String siteNo) throws Exception {
		List<String> TransactionDateDB = Utility.getDataFromDatabase("SELECT tran_dt FROM dlysite WHERE site_no = '"
				+ siteNo + "' AND tran_dt > (select last_day_closed_dt from site where site_no = '" + siteNo
				+ "') order by tran_dt desc ", "TRAN_DT");

		return TransactionDateDB;
	}

	public String getLastClosedTransactionDateDB(String siteNo) throws Exception {
		String tranDate = "";

		tranDate = Utility.executeQuery("SELECT tran_dt FROM dlysite WHERE ROWNUM<=1 AND site_no = '" + siteNo
				+ "' AND tran_dt > (select last_day_closed_dt from site where site_no = '" + siteNo
				+ "') order by tran_dt asc", "TRAN_DT");
		return tranDate;
	}

	public List<String> getTenderName() {

		List<String> tenderNameListUI = new ArrayList<>();
		count = eleUtil.getElementCount(tenderTable);
		System.out.println(count);
		for (int i = 2; i <= count; i++) {
			By tenderName = By.xpath("(//table)[7]//tr[" + i + "]//td[1]");
			String coinName = eleUtil.doGetText(tenderName);
			tenderNameListUI.add(coinName);

		}
		return tenderNameListUI;
	}

	/**
	 * @return Tender Name List from DB
	 * @throws Exception
	 */
	public List<String> getTenderNameDB() throws Exception {
		List<String> tenderNameListDB = Utility.getDataFromDatabase(
				"SELECT name, report_name\r\n" + "FROM tender_name\r\n"
						+ "WHERE (name NOT LIKE 'CASH%' OR NAME LIKE 'CASH%')\r\n" + "AND valid_fl = 'Y'\r\n"
						+ "AND auto_reconcile_fl = 'N'\r\n"
						+ "AND b_cash_management.get_tender_deposit_type(NAME) = 1\r\n" + "ORDER BY report_name",
				"REPORT_NAME");
		return tenderNameListDB;
	}

	/**
	 * This method will type lov data from UI
	 * 
	 * @throws Exception
	 */
	public List<String> getTypeLovDataDB() throws Exception {
		List<String> TypeLovDB = Utility
				.getDataFromDatabase("SELECT txtype_cd, description FROM txtype ORDER BY txtype_cd ASC", "TXTYPE_CD");
		return TypeLovDB;
	}

	/**
	 * @return billNameListUI
	 */
	public List<String> getCurrencyConfigName() {
		List<String> billNameListUI = new ArrayList<>();
		count = eleUtil.getElementCount(currencyTable);
		for (int i = 2; i <= count; i++) {
			By billtable = By.xpath("(//table/tbody/tr[" + i + "]/td[1])[1]");
			String billName = eleUtil.doGetText(billtable);
			billNameListUI.add(billName);
		}
		return billNameListUI;
	}

	/**
	 * @return billNameListDB
	 * @throws Exception
	 */
	public List<String> getCurrencyConfigNameDB() throws Exception {
		List<String> billNameListDB = Utility.getDataFromDatabase(
				"Select config_txt from configrec where config_name like 'BILLNAME' order by config_index",
				"CONFIG_TXT");
		return billNameListDB;
	}

	/**
	 * @return CoinNameListUI
	 */
	public List<String> getCoinConfigName() {

		List<String> CoinNameListUI = new ArrayList<>();
		count = eleUtil.getElementCount(coinTableCount);
		System.out.println(count);
		for (int i = 2; i <= count; i++) {
			By cointable = By.xpath("(//table/tbody/tr[" + i + "]/td[1])[2]");
			String coinName = eleUtil.doGetText(cointable);
			CoinNameListUI.add(coinName);

		}
		return CoinNameListUI;
	}

	/**
	 * @return CoinNameListDB
	 * @throws Exception
	 */
	public List<String> getCoinConfigNameDB() throws Exception {
		List<String> billNameListDB = Utility.getDataFromDatabase(
				"Select config_txt from configrec where config_name like 'COINNAME'", "CONFIG_TXT");
		return billNameListDB;
	}

	/**
	 * @param rollsValue
	 * @return rolls sum in coin section
	 */
	public double getRollsSum(String rollsValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		double add = 0;
		count = eleUtil.getElementCount(currencyTable);
		for (int i = 2; i <= count; i++) {
			String coins = reader.getCellData("CashDrawer", 0, i);
			Double d = Double.parseDouble(coins);
			By xpath = By.xpath("(//table/tbody/tr[" + i + "]/td[2]/input)[2]");
			eleUtil.doSendKeys(xpath, rollsValue);
			double coins_multiplication = d * Integer.parseInt(rollsValue);
			add = coins_multiplication + add;
		}

		return add;

	}

	/**
	 * @param looseValue
	 * @return loose sum in coins section
	 */
	public double getLooseSum(String looseValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		double add = 0;
		count = eleUtil.getElementCount(currencyTable);
		for (int i = 2; i <= count; i++) {
			String coinsConfigValue = reader.getCellData("CashDrawer", 1, i);
			double d = Double.parseDouble(coinsConfigValue);
			By xpath = By.xpath("//table/tbody/tr[" + i + "]/td[3]/input");
			eleUtil.doSendKeys(xpath, looseValue);
			double coins_multiplication = d * Integer.parseInt(looseValue);
			add = coins_multiplication + add;
		}

		return add;
	}

	/**
	 * @param UserName
	 * @param Password
	 * @throws Exception
	 */
	public void enterauthorizationEmployeeCredentials(String UserName, String Password) throws Exception {

		eleUtil.doSendKeys(authorizationEmployee, UserName);
		eleUtil.doSendKeys(authorizationPassword, Password);
		eleUtil.doClick(authorizationEmployee);
		Thread.sleep(2000);
		eleUtil.doClick(okBtnOnAuthWindow);
	}

	public String readErrorMsg() {
		return eleUtil.doGetText(errMsg);
	}

	public double getCurrencySum(String currenyNum) {
		eleUtil.doSendKeys(currencyName1, currenyNum);
		String currenyNam1 = eleUtil.doGetAttribute(currencyName1, "value");
		int curreny1 = Integer.parseInt(currenyNam1) * 1;

		eleUtil.doSendKeys(currencyName5, currenyNum);
		String currenyNam5 = eleUtil.doGetAttribute(currencyName5, "value");
		int curreny5 = Integer.parseInt(currenyNam5) * 5;

		eleUtil.doSendKeys(currencyName10, currenyNum);
		String currenyNam10 = eleUtil.doGetAttribute(currencyName10, "value");
		int curreny10 = Integer.parseInt(currenyNam10) * 10;

		eleUtil.doSendKeys(currencyName20, currenyNum);
		String currenyNam20 = eleUtil.doGetAttribute(currencyName20, "value");
		int curreny20 = Integer.parseInt(currenyNam20) * 20;

		eleUtil.doSendKeys(currencyName50, currenyNum);
		String currenyNam50 = eleUtil.doGetAttribute(currencyName50, "value");
		int curreny50 = Integer.parseInt(currenyNam50) * 50;

		eleUtil.doSendKeys(currencyName100, currenyNum);
		String currenyNam100 = eleUtil.doGetAttribute(currencyName100, "value");
		int curreny100 = Integer.parseInt(currenyNam100) * 100;
		return curreny1 + curreny5 + curreny10 + curreny20 + curreny50 + curreny100;

	}

	public double calculateActualAmt(double currencySum, double rollsum, double looseSum) throws InterruptedException {
		double actualTotal = 0;

		List<WebElement> tenderRowsList = driver.findElements(tenderRows);
		System.out.println(tenderRowsList.size());
		for (int i = 3; i <= tenderRowsList.size(); i++) {
			double value = i * 10;
			if (i >= 4) {
				WebElement tenderSection = driver
						.findElement(By.xpath("(//div[@id='Tender_data_panel']//tbody//tr)[" + i + "]//td[2]/input"));
				tenderSection.clear();
				tenderSection.sendKeys(String.valueOf(value));
				tenderSection.sendKeys(Keys.TAB);
				Thread.sleep(1000);
			}
			String p = driver
					.findElement(By.xpath("(//div[@id='Tender_data_panel']//tbody//tr)[" + i + "]//td[2]/input"))
					.getAttribute("value").toString();
			actualTotal = actualTotal + Double.valueOf(p);
		}
		System.out.println(actualTotal);
		actualTotal = actualTotal + currencySum + rollsum + looseSum;
		System.out.println(actualTotal);

		return actualTotal;
	}

}
