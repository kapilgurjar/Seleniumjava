package com.rediron.apex.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ExcelUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class MoveFunds extends TestBase {

	By siteNo = By.xpath("//input[contains(@id,'SITE_ID')]");
	By siteName = By.xpath("//input[contains(@id,'SITE_NAME')]");
	By currencyTable = By.xpath("//div[@id='currency_ir_ig_content_container']//table//tbody//tr//td[1]");
	By coinsTable = By.xpath("//div[@id='coin_ir_ig_content_container']//table//tbody//tr//td[1]");
	By coinsTableValue = By.xpath("//div[@id='coin_ir_ig_content_container']//table//tbody//tr//td[2]");
	By tenderTable = By.xpath("//div[@id='tender_ir_ig_grid_vc']//table//tbody//tr//td[1]");
	By fromSelect = By.cssSelector("select#P32_FROM_TRAN_TYPE");
	By toSelect = By.cssSelector("select#P32_TO_TRAN_TYPE");
	By comment = By.xpath("//input[contains(@id,'COMMENT')]");
	By commentPopMsg = By.xpath("//div[@id='showAlert']//p");
	By currency1 = By.cssSelector("#currency_ir_ig_grid_vc_cur");
	By totalAmount = By.xpath("//input[@id='P32_TOTAL_AMOUNT']");
	By tenderCash = By.xpath("//div[@id='tender_ir_ig_grid_vc']//table//tbody//tr[1]//td[2]");
	By fromDrawerButton = By.cssSelector("button[id*=FROM_DRAWER_CONTAINER_lov_btn]");
	By ToDrawerButton = By.cssSelector("button[id*=TO_DRAWER_CONTAINER_lov_btn]");
	By fromAndTODrawerTable = By.xpath("//div[contains(@id,'DRAWER_CONTAINER_dlg')]//table//tbody//tr//td[1]");
	By saveButton = By.cssSelector("button#save_button");
	By popOkButton=By.cssSelector("button#OK");

	public MoveFunds() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
		cashDrawerSetup= new CashDrawerSetUp(driver);
	}

	public String getPageTitle() {

		return eleUtil.doGetPageTitleIs("Move Funds", config.getExplicitWait());
	}

	/**
	 * This method will return site no
	 * 
	 * @return
	 */
	public String getSiteNo() {
		return eleUtil.doGetAttribute(siteNo, "value");

	}
	
	public void clickOnPopOkButton() {
		eleUtil.clickWhenReady(popOkButton, config.getExplicitWait());
	}

	/**
	 * This method will return site name
	 * 
	 * @return
	 */
	public String getSiteName() {
		return eleUtil.doGetAttribute(siteName, "value");
	}

	/**
	 * This method will return site no class attributes
	 * 
	 * @return
	 */
	public String getSiteNoClassAttributes() {
		return eleUtil.doGetAttribute(siteNo, "class");
	}

	/**
	 * This method will return site name class attributes
	 * 
	 * @return
	 */
	public String getSiteNameClassAttributs() {
		return eleUtil.doGetAttribute(siteName, "class");
	}

	/**
	 * This method will return currency config value from UI
	 * 
	 * @return
	 */
	public List<String> getCurrencyConfigValue() {
		return eleUtil.getElementsTextList(currencyTable);
	}

	/**
	 * This method will return coins config value from UI
	 * 
	 * @return
	 */
	public List<String> getCoinsConfigValue() {
		return eleUtil.getElementsTextList(coinsTable);
	}

	public MoveFunds selectValueFromLov(String value) {
		eleUtil.doSelectDropDownByVisibleText(fromSelect, value);
		return this;
	}

	public MoveFunds selectValueTOLov(String value) {
		eleUtil.doSelectDropDownByVisibleText(toSelect, value);
		return this;
	}

	public String getAlertPopMessage() {
		return eleUtil.doGetText(commentPopMsg);
	}

	public MoveFunds enterValueInCommentSection(String commentText) {
		eleUtil.doSendKeys(comment, commentText);
		return this;
	}

	public List<String> getTenderNameUI() {
		return eleUtil.getElementsTextList(tenderTable);
	}

	/**
	 * @author Kapil
	 * @param rollsValue
	 * @return rolls sum in coin section
	 */
	public double getRollsSum(String rollsValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		jsUtil.scrollToElemet(totalAmount);
		double add = 0;
		int count = reader.getRowCount("CashDrawer");
		for (int i = 2; i <= count; i++) {
			String coins = reader.getCellData("CashDrawer", 0, i);
			double d = Double.parseDouble(coins);
			By table = By.xpath("//div[@id='coin_ir_ig_content_container']//table//tbody//tr[" + i + "-1]//td[2]");
			eleUtil.doActionsDoubleClick(table);
			eleUtil.doActionMoveSendKey(table, rollsValue);
			double coins_multiplication = d * Double.parseDouble(rollsValue);
			add = coins_multiplication + add;
		}
		return add;

	}

	/**
	 * @author Kapil
	 * @param rollsValue
	 * @return rolls sum in coin section
	 */
	public double getLooseSum(String rollsValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		jsUtil.scrollToElemet(totalAmount);
		double add = 0;
		int count = reader.getRowCount("CashDrawer");
		for (int i = 2; i <= count; i++) {
			String coins = reader.getCellData("CashDrawer", 1, i);
			double d = Double.parseDouble(coins);
			By table = By.xpath("//div[@id='coin_ir_ig_content_container']//table//tbody//tr[" + i + "-1]//td[3]");
			eleUtil.doActionsDoubleClick(table);
			eleUtil.doActionMoveSendKey(table, rollsValue);
			double coins_multiplication = d * Double.parseDouble(rollsValue);
			add = coins_multiplication + add;
		}
		return add;

	}

	/**
	 * @author Kapil
	 * @param rollsValue
	 * @return rolls sum in coin section
	 */
	public double getCurrencySum(String rollsValue) throws Exception {
		ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
		jsUtil.scrollToElemet(totalAmount);
		double add = 0;
		int count = reader.getRowCount("CashDrawer");
		for (int i = 2; i <= count; i++) {
			String coins = reader.getCellData("CashDrawer", 7, i);
			double d = Double.parseDouble(coins);
			By table = By.xpath("//div[@id='currency_ir_ig_content_container']//table//tbody//tr[" + i + "-1]//td[2]");
			eleUtil.doActionsDoubleClick(table);
			eleUtil.doActionMoveSendKey(table, rollsValue);
			double coins_multiplication = d * Double.parseDouble(rollsValue);
			add = coins_multiplication + add;
		}
		return add;

	}

	public double getCurrencyCoinsSum(String amount) throws Exception {
		return getCurrencySum(amount) + getLooseSum(amount) + getRollsSum(amount);
	}

	public double getTenderCashAmt() throws Exception {
		eleUtil.doActionsDoubleClick(currency1);
		String value = eleUtil.doGetAttribute(tenderCash, "innerHTML");
		return Double.parseDouble(value);

	}

	/**
	 * @author Kapil
	 * @param value
	 * @return tender sum in tender section
	 */
	public double getTenderSum(String value) throws Exception {
		jsUtil.scrollToElemet(totalAmount);
		double add = 0;
		int count = eleUtil.getElementCount(tenderTable);
		for (int i = 2; i <= count; i++) {
			By table = By.xpath("//div[@id='tender_ir_ig_grid_vc']//table//tbody//tr[" + i + "]//td[2]");
			eleUtil.doActionsDoubleClick(table);
			eleUtil.doActionClear();
			eleUtil.doActionMoveSendKey(table, value);
			double tenderAmount = Double.parseDouble(value);
			add = tenderAmount + add;
		}
		return add;

	}

	/**
	 * @return This method will return total amount
	 */
	public double getTotalAmount() throws Exception {
		jsUtil.doJsClick(currency1);
		String value = eleUtil.doGetAttribute(totalAmount, "value");
		System.out.println("value" + value);
		return Double.parseDouble(value);

	}

	public void clickOnFromDrawerButton() {
		eleUtil.clickWhenReady(fromDrawerButton, config.getExplicitWait());
	}

	public void clickOnToDrawerButton() {
		eleUtil.clickWhenReady(ToDrawerButton, config.getExplicitWait());
	}

	public List<String> getDataFromDrawerTableFrom() throws Exception {
		CashDrawerSetUp cashDrawerSetup = new CashDrawerSetUp(driver);
		clickOnFromDrawerButton();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(fromAndTODrawerTable);
	}

	public List<String> getDataFromDrawerTableTO() throws Exception {
		CashDrawerSetUp cashDrawerSetup = new CashDrawerSetUp(driver);
		clickOnToDrawerButton();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(fromAndTODrawerTable);
	}

	public void clickOnsaveButton() {
		eleUtil.clickWhenReady(saveButton, config.getExplicitWait());
	}

	public String getCommentPopMsg() {
		clickOnsaveButton();
		return eleUtil.doGetText(commentPopMsg);
	}

	public MoveFunds enterUserIDAndPasswordInAuthWindow(String userName, String password) throws Exception {
		SafeReceipt sf = new SafeReceipt();
		sf.enterauthorizationEmployeeCredentials(userName, password);
		return this;
	}

	public String getPopWindowMessage() {
		return eleUtil.doGetText(commentPopMsg);
	}

}
