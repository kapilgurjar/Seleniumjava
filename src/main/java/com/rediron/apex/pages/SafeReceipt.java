package com.rediron.apex.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ExcelUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

public class SafeReceipt extends TestBase {

	public ElementUtil eleUtil;
	int count = 0;

	// WebElements

	By siteNo = By.xpath("//input[contains(@id,'SITE_ID')]");
	By siteName = By.xpath("//input[contains(@id,'SITE_NAME')]");
	By totalActaul = By.cssSelector("input#P9_TOTAL");
	By overShort = By.cssSelector("input#P9_OVER");
	//Changed
	By typeLov = By.cssSelector("Select#P15_TYPE");
	By transactionDate = By.xpath("//*[contains(@id,'TRANSACTION_DATE-container')]");
	By transactionDateList=By.xpath("//*[contains(@id,'select2-P15_TRANSACTION_DATE-results')]");
	By transactionDateLovBtn = By.id("//*[contains(@id,'TRANSACTION_DATE-container')]");
	By currencyTable = By.xpath("(//table[contains(@id,'orig')])[1]//tbody//tr");
	By tenderTable = By.xpath("(//table)[7]//tbody//tr");
	public By authorizationEmployee = By.xpath("//input[contains(@id,'AUTH_CASHIER')]");
	public By authorizationPassword = By.xpath("//input[contains(@id,'AUTH_PASSWORD')]");
	By authorizationOKButton = By.cssSelector("button#Authorization_OK");
	By authorizationCancelButton=By.xpath("//div[@id='authorization']//table//td[1]//button");
	public By currencyName1 = By.id("bill_$1");
	By tenderCash = By.cssSelector("#amount_CASH");
	By tenderCheck = By.id("amount_CHECK");
	By currencyName5 = By.id("bill_$5");
	By currencyName10 = By.id("bill_$10");
	By currencyName20 = By.id("bill_$20");
	By currencyName50 = By.id("bill_$50");
	By currencyName100 = By.id("bill_$100");
	By tenderVisa=By.id("amount_VISA");
	By tenderMC=By.id("amount_MC");
	By tenderAmex=By.id("amount_AMEX");
	By tenderKMCHARGE=By.id("amount_KMCHARGE");
	By tenderEBTFS=By.id("amount_EBT_FS");
	By tenderDENISE=By.id("amount_$_DENISE");
	By tenderREWARDS=By.id("amount_REWARDS");
	By totalAmount=By.xpath("//input[contains(@id,'TOTAL_AMOUNT')]");
	By comment=By.xpath("//input[contains(@id,'COMMENT')]");
	By transactionDateSearch=By.xpath("//input[@role='textbox']");
	By noResultFound=By.xpath("//*[text()='No results found']");
	public By transactionSearchFirst=By.xpath("//ul[contains(@id,'TRANSACTION_DATE-results')]//li");
	public By typeFieldPopMsg=By.id("paraId");
	By trsDate=By.xpath("//ul[@id='select2-P15_TRANSACTION_DATE-results']//li");
	public By okButton=By.id("Ok");

	public SafeReceipt() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
	}

	// Actions

	/**
	 * This method will return site no text filed class attributes
	 */
	public String getSiteNoAttributes() {

		return eleUtil.doGetAttribute(siteNo, "class");
	}

	/**
	 * This method will return site name text field class attributes
	 */
	public String getSiteNameAttributes() {

		return eleUtil.doGetAttribute(siteName, "class");
	}

	/**
	 * This method will type lov data from UI
	 */
	public List<String> getTypeLovData() {

		return eleUtil.getDropDownOptionsList(typeLov);
		

	}
	
	/**
	 * This method will type lov data from UI
	 */
	public void selectTypeLovData(String value) {

		eleUtil.doSelectDropDownByValue(typeLov, value);

	}

	/**
	 * This method will type lov data from UI
	 * 
	 * @throws Exception
	 */
	public List<String> getTypeLovDataDB() throws Exception {
		List<String> TypeLovDB = Utility.getDataFromDatabase("SELECT txtype_cd, description FROM txtype ORDER BY txtype_cd ASC","TXTYPE_CD");
		return TypeLovDB;
	}

	/**
	 * This method will return transaction date love data from UI
	 * @throws ParseException 
	 */
	public List<String> getTransactionDate() throws ParseException {
		eleUtil.doClick(transactionDate);
		List<String>actDate= new ArrayList<>();
		List<String>date= eleUtil.getElementsTextList(trsDate);
		for(int i=0;i<date.size();i++) {
			String strDate=date.get(i);
			//System.out.println(strDate);
			Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
	    	//System.out.println(date1);
	    	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	//2022-05-27 00:00:00
	    	String strDate1=formatter.format(date1);
	    	//System.out.println(strDate1);
	    	actDate.add(strDate1);	
		}
		return actDate;
	}

	/**
	 * This method will return transaction date love data from DB
	 * 
	 * @throws Exception
	 */
	public List<String> getTransactionDateDB(String siteNo) throws Exception {
		List<String> TransactionDateDB = Utility.getDataFromDatabase("SELECT tran_dt FROM dlysite WHERE site_no = '" + siteNo
				+ "' AND tran_dt > (select last_day_closed_dt from site where site_no = '" + siteNo
				+ "') order by tran_dt desc ","TRAN_DT");
		return TransactionDateDB;
	}

	// Clicking on transaction date lov button
	public SafeReceipt clickOnTransactionDateLovBtn() {
		eleUtil.doClick(transactionDate);
		return this;
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
		List<String> billNameListDB = Utility.getDataFromDatabase("Select config_txt from configrec where config_name like 'BILLNAME'","CONFIG_TXT");
		return billNameListDB;
	}

	/**
	 * @return CoinNameListUI
	 */
	public List<String> getCoinConfigName() {

		List<String> CoinNameListUI = new ArrayList<>();
		count = eleUtil.getElementCount(currencyTable);
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
		List<String> billNameListDB  = Utility.getDataFromDatabase("Select config_txt from configrec where config_name like 'COINNAME'","CONFIG_TXT");
		return billNameListDB;
	}

	/**
	 * @return Tender Name List from UI
	 */
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
		List<String> tenderNameListDB  = Utility.getDataFromDatabase("Select	name, report_name  from tender_name  where name like 'CASH%' \r\n"
				+ "Union\r\n" + "Select	name, report_name  from tender_name E23 where name not like 'CASH%' \r\n"
				+ "and auto_reconcile_fl = 'N' and valid_fl = 'Y' order by 2","REPORT_NAME");
		
		return tenderNameListDB;
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
		eleUtil.doClick(authorizationOKButton);
	}
	
	/**
	 * @param UserName
	 * @param Password
	 * @throws Exception
	 */
	public void enterauthorizationEmployeeCredentialsAndClickOnCancel(String UserName, String Password) throws Exception {

		eleUtil.doSendKeys(authorizationEmployee, UserName);
		eleUtil.doSendKeys(authorizationPassword, Password);
		eleUtil.doClick(authorizationEmployee);
		Thread.sleep(2000);
		eleUtil.doClick(authorizationCancelButton);
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
	 * @return cash amount from cash text field
	 * @throws Exception
	 */
	public double getValueFromCash() throws Exception {
		double cashAmount = 0;
		Thread.sleep(2000);
		// eleUtil.doClick(tenderKmcharge);
		eleUtil.doActionMoveClick(tenderCheck);
		Thread.sleep(2000);
		String cashValue = eleUtil.doGetAttribute(tenderCash, "value").toString();
		String ActualCashValue = cashValue.replace(".00", "");
		try {
			cashAmount = Double.parseDouble(ActualCashValue);
		} catch (NumberFormatException ex) {
			System.out.println("NumberFormatException");
		}
		return cashAmount;

	}

	/**
	 * @param currenyNum
	 * @param bills
	 * @param ele
	 * @return bills sum in currency section
	 */
	public int getCurrencySum(String currenyNum) {
		eleUtil.doSendKeys(currencyName1, currenyNum);
		String currenyNam1 = eleUtil.doGetAttribute(currencyName1, "value");
		int curreny1 = Integer.parseInt(currenyNam1) * 1;

		eleUtil.doSendKeys(currencyName5, currenyNum);
		String currenyNam5 = eleUtil.doGetAttribute(currencyName1, "value");
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
	
	/**
	 * @param Amount
	 * @return Get total sum from tender section
	 * @throws Exception
	 */
	public double getValueFromTenderSection(String Amount) throws Exception {
		
		eleUtil.doSendKeys(tenderCheck, Amount);
		
		String tenderCheckValue = eleUtil.doGetAttribute(tenderCheck, "value");
		
		double tenderCheckAmount = Double.parseDouble(tenderCheckValue);

		eleUtil.doSendKeys(tenderVisa, Amount);

		String tenderVisaValue = eleUtil.doGetAttribute(tenderVisa, "value");

		double tenderVisaAmount = Double.parseDouble(tenderVisaValue);

		eleUtil.doSendKeys(tenderMC, Amount);

		String tenderMCValue = eleUtil.doGetAttribute(tenderMC, "value");

		double tenderMCAmount = Double.parseDouble(tenderMCValue);

		eleUtil.doSendKeys(tenderAmex, Amount);

		String tenderAmexValue = eleUtil.doGetAttribute(tenderAmex, "value");

		double tenderAmexAmount = Double.parseDouble(tenderAmexValue);

		eleUtil.doSendKeys(tenderEBTFS, Amount);

		String tenderEBTFSValue = eleUtil.doGetAttribute(tenderEBTFS, "value");

		double tenderEBTFSAmount = Double.parseDouble(tenderEBTFSValue);

		eleUtil.doSendKeys(tenderDENISE, Amount);

		String tenderDENISEValue = eleUtil.doGetAttribute(tenderDENISE, "value");

		double tenderDENISEAmount = Double.parseDouble(tenderDENISEValue);

		//eleUtil.doSendKeys(tenderSingpore, Amount);

		//String tenderSingporeValue = eleUtil.doGetAttribute(tenderSingpore, "value");

		//double tenderSingporeAmount = Double.parseDouble(tenderSingporeValue);

		eleUtil.doSendKeys(tenderKMCHARGE, Amount);

		String tenderKmchargeValue = eleUtil.doGetAttribute(tenderKMCHARGE, "value");

		double tenderKmchargeAmount = Double.parseDouble(tenderKmchargeValue);

		return tenderCheckAmount + tenderVisaAmount + tenderMCAmount + tenderAmexAmount + tenderDENISEAmount
				  +tenderEBTFSAmount+ tenderKmchargeAmount;
	}
	
	/**
	 * @return total amount value from total amount text field
	 * @throws Exception
	 */
	public double getTotalAmount() throws Exception {
		eleUtil.doClick(tenderAmex);
		Thread.sleep(4000);
		String amount = eleUtil.doGetAttribute(totalAmount, "value");
		double totalAmount = Double.parseDouble(amount);
		return totalAmount;
	}
	/**
	 * This method will select data from Transaction Date Lov
	 */
	public void selectTransactionDate(String tranDate) {
		By transactionDate=By.xpath("//li[text()='"+tranDate+"']");
		eleUtil.doClick(transactionDate);
	}
	/**
	 * This method will enter data in comment text field 
	 */
	public void enterTextInCommentText(String commentText) {
		
		eleUtil.doSendKeys(comment, commentText);
	}
	
	public void searchTranscationDate(String transDate) {
		eleUtil.doSendKeys(transactionDateSearch, transDate);
	
	}
	
	
	public Boolean isNoResultFoundIsVisible() {
		return eleUtil.doIsDisplayed(noResultFound);
	}

}
