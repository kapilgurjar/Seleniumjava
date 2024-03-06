package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.factory.DriverFactory;
import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ExcelUtil;
import com.rediron.apex.utils.Utility;

public class CashDrawerSetUp  {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	int timeout=DriverFactory.timeOut;

	// WebElements
	private By siteNo = By.id("P2_SITE_ID");
	private By siteName = By.id("P2_SITENAME");
	private By assignToRegisterTextField = By.id("P2_ASSIGN_TO_REG_lov_btn");
	private By assignToRegisterTextFieldSearch = By.xpath("//input[@aria-label='Search']");
	private By assignToRegisterSearchButton = By.xpath("//button[@aria-label='Search']");
	private By suggestedFloatAmount = By.id("P2_FLOAT_AMT");
	private By assignToRegisterlsitbox = By.xpath("//ul[@role='listbox']/li");
	private By loadMoreRowsButton = By.xpath("//button[text()='Load More Rows']");
	private By currencyName1 = By.xpath("//input[@id='curr_1' or @id='con_id_1']");
	private By currencyName5 = By.xpath("//input[@id='curr_5' or @id='con_id_5']");
	private By currencyName10 = By.xpath("//input[@id='curr_10' or @id='con_id_10']");
	private By currencyName20 = By.xpath("//input[@id='curr_20' or @id='con_id_20']");
	private By currencyName50 = By.xpath("//input[@id='curr_50' or @id='con_id_50']");
	private By currencyName100 = By.xpath("//input[@id='curr_100' or @id='con_id_100']");
	private By popUpWindow = By.id("paraId");
	private By okButton = By.xpath("//div[starts-with(@class,'ui-dialog-buttonset')]//button");
	private By saveButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[1]");
	private By clearButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[2]");
	private By exitButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[3]");
	private By homePageLink = By.xpath("//span[text()='Home']//parent::a");
	private By authorizationWindow = By
			.xpath("//div[@id='auth' or @id='authorization']//preceding::span[text()='Authorization']");
	private  By authorizingCashierTextField = By.id("P2_CASHIER");
	private  By passwordTextField = By.id("P2_PASSWORD");
	By authokButton = By.xpath("//td//button[@id='auth_ok']");
	By authcancelButton = By.id("auth_cancel");
	By userPassValMsg = By.xpath("(//*[starts-with(@id,'ui-id')]/p)[1]");
	By cash = By.xpath("//input[@id='assigncash_CASH_TNDR' or @id='t_cash']");
	By tenderCheck = By.id("assigncash_CHECK_TNDR");
	By tenderVisa = By.id("assigncash_CREDIT1_TNDR");
	By tenderMC = By.id("assigncash_CREDIT2_TNDR");
	By tenderAmex = By.id("assigncash_CREDIT3_TNDR");
	By tenderDiscover = By.id("assigncash_MISCT13_TNDR");
	By tenderCanada = By.id("assigncash_FORCUR1_TNDR");
	By tenderSingpore = By.id("assigncash_FORCUR2_TNDR");
	By tenderKmcharge = By.id("assigncash_CUSTCHG_TNDR");
	By totalAmount = By.xpath("//input[@id='P2_TOTAL']");
	By differenceBetweenSuggestedFloatandActualAmount = By.id("P2_DIFF");
	By firstSearch = By.xpath("//ul[@role='listbox']//li//span");
	By safeRecPopMsg = By.xpath("//div[contains(@id,'ui-id')]//p");
	By safeRecPopOKButton = By.cssSelector("button#Ok");
	public By noResultFound = By.xpath("//span[text()='No results found.']");
	By cashBoxNo = By.xpath("//div[starts-with(@id, 'ui-id')]/p");
	public By clearScreenOkButton = By.xpath("//*[@id='t_PageBody']//button[text()='OK']");

	// Initializing the Page Objects:
	public CashDrawerSetUp(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}

	public CashDrawerSetUp clickOnAssignToRegisterTextField() {
		eleUtil.clickWhenReady(assignToRegisterTextField, timeout);
		return this;
	}

	public void clickOnCurrencyOne() {
		eleUtil.doStaticWait(3000);
		eleUtil.doClick(currencyName1);

	}

	public void clickOnOkButton() {
		eleUtil.doClick(okButton);
	}

	public CashDrawerSetUp clickOnSaveButton() {
		eleUtil.doClick(saveButton);
		return this;
	}

	// Clicking on the load more button
	public void clickOnLoadMoreRowsButton() throws Exception {

		boolean buttonStatus = eleUtil.doIsEnabled(loadMoreRowsButton);
		System.out.println(buttonStatus);
		while (buttonStatus) {
			try {
				eleUtil.doClickWithWait(loadMoreRowsButton, timeout);
			} catch (Exception e) {
				e.getStackTrace();
				break;
			}
		}

	}

	// Getting all list from assign to register list box
	public List<String> getValueFromassignToRegister_listbox() {
		List<String> allassignRegister = eleUtil.getElementsTextList(assignToRegisterlsitbox);
		return allassignRegister;
	}

	/**
	 * @author Kapil
	 * @return popUp Window text
	 */
	public String getPopWindowText() {

		return eleUtil.doGetText(popUpWindow);

	}

	/**
	 * @author Kapil
	 * @return authorizationWindow text
	 */
	public boolean verifyAuthorizationPopUpTitle() {
		eleUtil.waitForElementVisible(authorizationWindow, timeout, timeout);
		return eleUtil.doIsDisplayed(authorizationWindow);

	}

	/**
	 * @author Kapil
	 * @param cash_UserName
	 * @param cash_Password
	 * @return
	 */
	public CashDrawerSetUp enterCashierUserIdAndPass(String cash_UserName, String cash_Password) {

		eleUtil.doSendKeys(authorizingCashierTextField, cash_UserName);
		eleUtil.doSendKeys(passwordTextField, cash_Password);
		eleUtil.doClick(authorizingCashierTextField);
		eleUtil.doClick(authokButton);
		return this;
	}

	/**
	 * @author Kapil
	 * @param cash_UserName
	 * @param cash_Password
	 * @throws Exception
	 */
	public void enterCashierUserIdAndPassAndClickOnCancel(String cash_UserName, String cash_Password) throws Exception {

		eleUtil.doSendKeys(authorizingCashierTextField, cash_UserName);
		eleUtil.doSendKeys(passwordTextField, cash_Password);
		eleUtil.doClick(authorizingCashierTextField);
		eleUtil.doClick(authcancelButton);
	}

	/**
	 * @author Kapil
	 * @return Cash Drawer Number From UI
	 * @throws Exception
	 */
	public String getCahBoxNo() throws Exception {
		Thread.sleep(2000);
		String Text = eleUtil.doGetText(popUpWindow).toString();
		String cashBoxNum = Text.replaceAll("[^0-9]", "");
		return cashBoxNum;

	}

	// Getting validation message from authorization window
	public String getAuthUserAndPassValidationMessage() {
		return eleUtil.doGetText(popUpWindow).trim();
	}

	/**
	 * @author Kapil This method will select value from lov button
	 * @return
	 */
	public CashDrawerSetUp selectValueFromLOV(String xpathName) {
		String before_Xpath = "//*[text()='";
		String after_Xpath = "']";
		String xpath = before_Xpath + xpathName + after_Xpath;
		driver.findElement(By.xpath(xpath)).click();
		return this;
	}

	public String getTheSuggestedFloatAmount() {

		return eleUtil.doGetAttribute(suggestedFloatAmount, "value").toString();
	}

	/**
	 * @author Kapil
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
	 * @author Kapil
	 * @param rollsValue
	 * @return rolls sum in coin section
	 */
	public double getRollsSum(String rollsValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		double add = 0;
		List<WebElement> ele = driver.findElements(By.xpath("(//table)[4]//tbody//tr"));
		int count = ele.size();
		for (int i = 2; i <= count; i++) {
			String coins = reader.getCellData("CashDrawer", 0, i);
			double d = Double.parseDouble(coins);
			String before_xpath = "(//table/tbody/tr[";
			String after_xpath = "]/td[2]/input)[2]";
			String xpath = before_xpath + i + after_xpath;
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(rollsValue);
			double coins_multiplication = d * Integer.parseInt(rollsValue);
			add = coins_multiplication + add;
		}

		return add;

	}

	/**
	 * @author Kapil
	 * @param looseValue
	 * @return loose sum in coins section
	 */
	public double getLooseSum(String looseValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		double add = 0;
		List<WebElement> ele = driver.findElements(By.xpath("(//table)[4]//tbody//tr"));
		int count = ele.size();
		for (int i = 2; i <= count; i++) {
			String coinsConfigValue = reader.getCellData("CashDrawer", 1, i);
			double d = Double.parseDouble(coinsConfigValue);
			String before_xpath = "//table/tbody/tr[";
			String after_xpath = "]/td[3]/input";
			String xpath = before_xpath + i + after_xpath;
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(looseValue);
			double coins_multiplication = d * Integer.parseInt(looseValue);
			add = coins_multiplication + add;
		}

		return add;
	}

	/**
	 * @author Kapil
	 * @return cash amount from cash text field
	 * @throws Exception
	 */
	public double getValueFromCash() throws Exception {
		double cashAmount = 0;
		Thread.sleep(2000);
		// eleUtil.doClick(tenderKmcharge);
		eleUtil.doActionMoveClick(tenderKmcharge);
		Thread.sleep(2000);
		String cashValue = eleUtil.doGetAttribute(cash, "value").toString();
		String ActualCashValue = cashValue.replace(".00", "");
		try {
			cashAmount = Double.parseDouble(ActualCashValue);
		} catch (NumberFormatException ex) {
			System.out.println("NumberFormatException");
		}
		return cashAmount;

	}

	/**
	 * @author Kapil
	 * @param Amount
	 * @return Get total sum from tender section
	 * @throws Exception
	 */
	public double getValueFromTenderSection(String Amount) throws Exception {

		eleUtil.doSendKey(tenderCheck, Amount);

		String tenderCheckValue = eleUtil.doGetAttribute(tenderCheck, "value");

		double tenderCheckAmount = Double.parseDouble(tenderCheckValue);

		eleUtil.doSendKey(tenderVisa, Amount);

		String tenderVisaValue = eleUtil.doGetAttribute(tenderVisa, "value");

		double tenderVisaAmount = Double.parseDouble(tenderVisaValue);

		eleUtil.doSendKey(tenderMC, Amount);

		String tenderMCValue = eleUtil.doGetAttribute(tenderMC, "value");

		double tenderMCAmount = Double.parseDouble(tenderMCValue);

		eleUtil.doSendKey(tenderAmex, Amount);

		String tenderAmexValue = eleUtil.doGetAttribute(tenderAmex, "value");

		double tenderAmexAmount = Double.parseDouble(tenderAmexValue);

		eleUtil.doSendKey(tenderDiscover, Amount);

		String tenderDiscoverValue = eleUtil.doGetAttribute(tenderDiscover, "value");

		double tenderDiscoverAmount = Double.parseDouble(tenderDiscoverValue);

		// eleUtil.doSendKeys(tenderCanada, Amount);

		// String tenderCanadaValue = eleUtil.doGetAttribute(tenderCanada, "value");

		// double tenderCanadaAmount = Double.parseDouble(tenderCanadaValue);

		// eleUtil.doSendKeys(tenderSingpore, Amount);

		// String tenderSingporeValue = eleUtil.doGetAttribute(tenderSingpore, "value");

		// double tenderSingporeAmount = Double.parseDouble(tenderSingporeValue);

		eleUtil.doSendKey(tenderKmcharge, Amount);

		String tenderKmchargeValue = eleUtil.doGetAttribute(tenderKmcharge, "value");

		double tenderKmchargeAmount = Double.parseDouble(tenderKmchargeValue);

		return tenderCheckAmount + tenderVisaAmount + tenderMCAmount + tenderAmexAmount + tenderDiscoverAmount
				+ tenderKmchargeAmount;
	}

	/**
	 * @author Kapil
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
	 * @author Kapil
	 * @return Difference Between Suggested Float and ActualAmount
	 * @throws Exception
	 */
	public double getDifferenceBetweenSuggestedFloatandActualAmount() throws Exception {

		String amount = eleUtil.doGetAttribute(differenceBetweenSuggestedFloatandActualAmount, "value");
		double totalAmount = Double.parseDouble(amount);
		return totalAmount;
	}

	/**
	 * @author Kapil
	 * @Param Value
	 */
	public ArrayList<Integer> getLengthOfRollsTextFiledInBillSection(String Value) {
		int length = 0;
		ArrayList<Integer> lengthList = new ArrayList<>();
		List<WebElement> ele = driver.findElements(By.xpath("(//table)[4]//tbody//tr"));
		int count = ele.size();
		for (int i = 2; i <= count; i++) {
			String before_xpath = "(//table/tbody/tr[";
			String after_xpath = "]/td[2]/input)[2]";
			String xpath = before_xpath + i + after_xpath;
			driver.findElement(By.xpath(xpath)).sendKeys(Value);
			length = driver.findElement(By.xpath(xpath)).getAttribute("value").length();
			lengthList.add(length);
		}
		return lengthList;
	}

	/**
	 * @author Kapil return site no class attributes
	 */
	public String verifySiteNoTextField() {

		return eleUtil.doGetAttribute(siteNo, "class");
	}

	/**
	 * @author Kapil return site name class attributes
	 */
	public String verifySiteNameTextField() {

		return eleUtil.doGetAttribute(siteName, "class");
	}

	/**
	 * @author Kapil
	 * @Param searchText
	 */
	public void sendAssignToRegisterSearch(String searchText) {

		eleUtil.doSendKeys(assignToRegisterTextFieldSearch, searchText);
		eleUtil.doClick(assignToRegisterSearchButton);

	}

	/**
	 * @author Kapil
	 * @return get search text form assign to register text filed
	 */
	public String getAssignToRegisterSearchText() {
		return eleUtil.doGetText(firstSearch).trim();
	}

	/**
	 * @author Kapil
	 * @return Assign to Register is displayed or not
	 */
	public boolean assignToRegisterIsDisplayed() {

		return eleUtil.isDisplayed(assignToRegisterTextField);
	}

	/**
	 * @author Kapil
	 * @return Suggested FloatAmount is displayed or not
	 */
	public boolean suggestedFloatAmountIsDisplayed() {

		return eleUtil.isDisplayed(suggestedFloatAmount);
	}

	/**
	 * @author Kapil
	 * @return Safe Recon In Process message on UI
	 */
	public String getSafeReconPopMessage() {
		return eleUtil.doGetText(popUpWindow);

	}

	public void clickOnSafeRecOkButton() {
		eleUtil.doClick(safeRecPopOKButton);
	}

	/**
	 * @author Kapil
	 * @return Tender Name List From UI
	 */
	public ArrayList<String> getTenderNameUI() {
		ArrayList<String> tenderNameListUI = new ArrayList<>();
		List<WebElement> ele = driver.findElements(By.xpath("(//table)[6]//tbody//tr"));
		int count = ele.size();
		for (int i = 2; i <= count; i++) {
			String before_xpath = "((//table)[6]//tbody//tr[";
			String after_xpath = "]//td)[1]";
			String xpath = before_xpath + i + after_xpath;
			String tenderName = driver.findElement(By.xpath(xpath)).getText().trim();
			tenderNameListUI.add(tenderName);
		}
		return tenderNameListUI;
	}

	/**
	 * @author Kapil
	 * @return Tender Name List From DB
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
	 * @author Kapil
	 * @return Clear screen pop up window text
	 */
	public String getClearPopWindowText() {
		String text = "";
		text = eleUtil.doGetText(popUpWindow);
		eleUtil.doClick(safeRecPopOKButton);
		return text;
	}

}
