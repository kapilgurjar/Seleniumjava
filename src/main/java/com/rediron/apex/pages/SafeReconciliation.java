package com.rediron.apex.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

public class SafeReconciliation extends TestBase {

	public ElementUtil eleUtil;

	By siteNo = By.xpath("//input[contains(@id,'SITE_ID')]");
	By siteName = By.xpath("//input[contains(@id,'SITENAME')]");
	By currency1 = By.id("curr_1");
	By clearButton = By.xpath("//span[text()='Clear']//parent::button//parent::div//child::button[2]");
	By exitButton = By.xpath("//span[text()='Exit']//parent::button//parent::div//child::button[3]");
	By reconButton = By.xpath("//span[text()='Reconcile']//parent::button//parent::div//child::button[1]");
	By safeReconUIPopOKButton = By.xpath("//button[text()='OK']");
	By safeReconUIPopUp = By.xpath("//div[contains(@id,'ui-id')]");
	By overRideAuthorizationEmployee = By.xpath("//input[contains(@id,'OVERRIDE_CASHIER')]");
	By overRideAuthorizationPassword = By.xpath("//input[contains(@id,'OVERRIDE_PASSWORD')]");
	By overRideAuthOkButton = By.cssSelector("button#auth_override_ok");
	By checkAuditButton = By.xpath("//span[text()='Check Audit']//parent::button");
	By checkAuditOkButton = By.xpath("//span[text()='OK']//parent::button");
	By authCashier = By.cssSelector("input#P16_CASHIER");
	By authCashierPassword = By.cssSelector("input#P16_PASSWORD");
	By authOkButton = By.cssSelector("button#auth_ok");
	By reconDate = By.id("P16_RECON_DATE");
	By tenderCash = By.cssSelector("input#t_cash");
	By totalActual = By.cssSelector("input#P16_TOTAL");
	By actualFloat = By.xpath("//input[contains(@id,'ACTUAL_FLOAT')]");
	By tenderTable = By.xpath("//div[@id='tender_ir_data_panel']//table[contains(@id,'orig')]/tbody/tr");
	By totalExpected = By.xpath("//input[contains(@id,'TOT_EXP')]");
	By overShort = By.xpath("//div[contains(@id,'OVER_CONTAINER')]//input[contains(@id,'P16_OVER')]");
	By unreconciledCashDrawers = By.xpath("//span[contains(@id,'WARNING_MSG_DISPLAY')]");
	By checkAuditTable = By.xpath("(//div[@id='check_fix_ig_grid_vc_ctx']//following::table)[1]//tbody//tr//td[1]");
	By checkAuditExpAmount = By.xpath("//input[contains(@id,'AUDIT_EXPECTED')]");
	By rowActions = By.xpath("//span//parent::button[@title='Row Actions']");
	By addRow = By.xpath("//button[text()='Add Row']");
	By checkAmountInput = By.xpath("//input[@id='CHECK_AMT']");
	By checkAmoutCheckBox = By.xpath("//span[text()='Unchecked']");
	By checkAuditOverShort = By.cssSelector("input#P16_AUDIT_OVER");
	By checkAuditActAmount = By.cssSelector("input#P16_AUDIT_ACTUAL_AMT");
	By authCancelButton = By.xpath("//button[@id='auth_cancel']");
	By rowCount = By.cssSelector("input[id*=FIXED_COUNT]");
	By clearYesButton = By.cssSelector("button#Yes");
	public By safeReconOkButton = By.xpath("//button[text()='OK']");

	// Initializing the Page Objects:
	public SafeReconciliation() {

		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	/**
	 * This method will return page title
	 * 
	 * @return
	 */
	public String getPageTitle() {

		return eleUtil.doGetPageTitleIs("Safe Reconciliation", config.getExplicitWait());
	}

	/**
	 * This method will return site no
	 * 
	 * @return
	 */
	public String getSiteNo() {
		return eleUtil.doGetAttribute(siteNo, "value");
	}

	/**
	 * This method will return site name
	 * 
	 * @return
	 */
	public String getSiteName() {
		return eleUtil.doGetAttribute(siteName, "value");
	}

	public void enterValueInCurrency1(String number) throws Exception {
		Thread.sleep(2000);
		eleUtil.waitForElementVisible(currency1, config.getExplicitWait());
		eleUtil.doSendKey(currency1, number);
	}

	public void clickOnClearButton() {
		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
	}

	public void ClickOnSafeReconUIPopUpOkButton() {
		eleUtil.clickWhenReady(safeReconUIPopOKButton, config.getExplicitWait());
	}

	public boolean ISSafeReconUIPopUpMessageDisplayed() {
		return eleUtil.isDisplayed(safeReconUIPopUp);
	}

	/**
	 * @param UserName
	 * @param Password
	 * @throws Exception
	 */
	public void enterOverRideAuthorizationEmployeeCredentials(String UserName, String Password) throws Exception {

		eleUtil.doSendKeys(overRideAuthorizationEmployee, UserName);
		eleUtil.doSendKeys(overRideAuthorizationPassword, Password);
		eleUtil.doClick(overRideAuthorizationEmployee);
		Thread.sleep(2000);
		eleUtil.doClick(overRideAuthOkButton);
	}

	public void clickOnExitButton() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
	}

	public void clickOnClearYesButton() {
		eleUtil.clickWhenReady(clearYesButton, config.getExplicitWait());
	}

	public void clickOnReconButton() {
		eleUtil.clickWhenReady(reconButton, config.getExplicitWait());
	}

	public void clickOnCheckAuditButton() {
		eleUtil.clickWhenReady(checkAuditButton, config.getExplicitWait());
		eleUtil.clickWhenReady(checkAuditOkButton, config.getExplicitWait());
	}

	/**
	 * @param UserName
	 * @param Password
	 * @throws Exception
	 */
	public void enterauthorizationEmployeeCredentials(String UserName, String Password) throws Exception {

		eleUtil.doSendKeys(authCashier, UserName);
		eleUtil.doSendKeys(authCashierPassword, Password);
		eleUtil.doClick(authCashier);
		Thread.sleep(2000);
		eleUtil.doClick(authOkButton);
	}

	/**
	 * @param UserName
	 * @param Password
	 * @throws Exception
	 */
	public void enterauthorizationEmployee(String UserName, String Password) throws Exception {

		eleUtil.doSendKeys(authCashier, UserName);
		eleUtil.doSendKeys(authCashierPassword, Password);
		eleUtil.doClick(authCashier);
		Thread.sleep(2000);

	}

	/**
	 * This will return recon date values
	 * 
	 * @return
	 * @return
	 */
	public String getCurrentReconDayDate() {

		List<String> dates = eleUtil.getDropDownOptionsList(reconDate);
		return dates.get(1);
	}

	/**
	 * This will return recon date values
	 * 
	 * @return
	 * @return
	 * @throws ParseException
	 */
	public List<String> getReconDayDate() throws ParseException {
		List<String> ReconDates = new ArrayList<>();
		List<String> dates = eleUtil.getDropDownOptionsList(reconDate);
		for (String date : dates) {
			if (date.equalsIgnoreCase("Select")) {
				System.out.println("date format is not correct");
			} else {
				Date reconDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String actReconDateUI = formatter.format(reconDate);
				System.out.println("Act Recon Date UI =" + actReconDateUI);
				ReconDates.add(actReconDateUI);
			}
		}
		return ReconDates;
	}

	/**
	 * This will return tender cash amount from tender section
	 * 
	 * @return
	 * @return
	 */
	public double getTenderCashValue() {
		eleUtil.doClick(currency1);
		String value = eleUtil.doGetAttribute(tenderCash, "value");
		return Double.parseDouble(value);
	}

	/**
	 * This will return tender cash amount from tender section
	 * 
	 * @return
	 * @return
	 */
	public double getTotalActualValue() {
		eleUtil.doClick(currency1);
		String value = eleUtil.doGetAttribute(totalActual, "value");
		return Double.parseDouble(value);
	}

	/**
	 * This will return actual float amount
	 * 
	 * @return
	 * @return
	 */
	public String getActualFloatValue() {
		return eleUtil.doGetAttribute(actualFloat, "value");
	}

	/**
	 * This will return tender name and value in a hashmap
	 * 
	 * @return tenderNameExpAmount
	 * @return
	 */
	public HashMap<String, String> getTenderValueFromUI() {
		HashMap<String, String> tenderNameExpAmount = new HashMap<>();
		int count = eleUtil.getElementCount(tenderTable);
		for (int i = 3; i <= count; i++) {
			By tenderName = By
					.xpath("//div[@id='tender_ir_data_panel']//table[contains(@id,'orig')]/tbody/tr[" + i + "]//td[1]");
			String tenderNme = eleUtil.doGetText(tenderName);
			By tenderAmount = By.xpath(
					"//div[@id='tender_ir_data_panel']//table[contains(@id,'orig')]/tbody/tr[" + i + "]//td[3]//input");
			String tenderExpAmount = eleUtil.doGetAttribute(tenderAmount, "value");
			String roundedtenderExpAmount = tenderExpAmount.replaceAll("\\.\\d+$", "");
			System.out.println(roundedtenderExpAmount);
			tenderNameExpAmount.put(tenderNme, roundedtenderExpAmount);
		}
		return tenderNameExpAmount;
	}

	/**
	 * This method will return tender name and tender value from DB
	 * 
	 * @return tenderNameExpAmount
	 * @throws Exception
	 */
	public HashMap<String, String> getTenderValueFromDB() throws Exception {
		HashMap<String, String> tenderNameExpAmount = new HashMap<>();
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "\r\n" + "    select  t.name, t.report_name, s.amt, t.auto_reconcile_fl\r\n"
				+ "     from tender_name t, site_tender s\r\n" + "    where s.site_no = " + siteNum + "\r\n"
				+ "      and t.name = s.name\r\n" + "      and t.name != 'CASH_TNDR'\r\n"
				+ "      and t.auto_reconcile_fl = 'N'\r\n" + "      and t.valid_fl = 'Y'\r\n"
				+ "      and function_no in \r\n" + "        (select function_no\r\n" + "           from keycodes\r\n"
				+ "          where key_name in \r\n" + "              (select key_name\r\n"
				+ "                 from keyboard\r\n" + "                where keyboard_type = 'T'\r\n"
				+ "                  and site_no = 108\r\n" + "                  and keyboard_name in \r\n"
				+ "                    (select distinct(keyboard_name) \r\n" + "                       from registr\r\n"
				+ "                      where post_site_no = " + siteNum + "\r\n"
				+ "                        and reg_id != 'R0364'\r\n" + "                     )\r\n"
				+ "               )\r\n" + "          )\r\n" + "   order by t.auto_reconcile_fl, t.report_name\r\n"
				+ "   ";
		String name = "";
		String value = "";
		List<String> tenderName = Utility.getDataFromDatabase(query, "REPORT_NAME");
		List<String> tenderValue = Utility.getDataFromDatabase(query, "AMT");
		for (int i = 0; i < tenderName.size(); i++) {
			name = tenderName.get(i);
			for (int j = 0; j <= i; j++) {
				value = tenderValue.get(j);
				tenderNameExpAmount.put(name, value);
			}

		}
		return tenderNameExpAmount;
	}

	/**
	 * 
	 * @return expectedAmount
	 * @throws Exception
	 */
	public double getCashExpectedAmountInTenderSectionFromDB() throws Exception {
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "SELECT tn.name, tn.report_name, st.amt, tn.auto_reconcile_fl\r\n"
				+ "	    FROM tender_name tn, site_tender st\r\n" + "	   WHERE tn.valid_fl = 'Y'\r\n"
				+ "	     AND tn.name = st.name\r\n" + "		   AND st.site_no = " + siteNum + "\r\n"
				+ "	     AND tn.name like 'CASH%'";
		String expectedAmount = Utility.executeQuery(query, "AMT");
		return Double.parseDouble(expectedAmount);

	}

	/**
	 * 
	 * @return Total Expected Amount non AR from Database
	 * @throws Exception
	 */
	public double totalExpectedAmountFromDBINTenderSection() throws Exception {
		double add = 0;
		HashMap<String, String> tenderValueDB = getTenderValueFromDB();
		Iterator<Entry<String, String>> itr1 = tenderValueDB.entrySet().iterator();
		while (itr1.hasNext()) {
			Map.Entry<String, String> entry = itr1.next();
			String value = entry.getValue();
			double value1 = Double.parseDouble(value);
			add = add + value1;
		}
		double expAmountDB = getCashExpectedAmountInTenderSectionFromDB();
		return add + expAmountDB;
	}

	public double getTotalExpectedAmountInTenderSectionUI() {

		String value = eleUtil.doGetAttribute(totalExpected, "value");
		return Double.parseDouble(value);
	}

	public double getOverShort() {
		String value = eleUtil.doGetAttribute(overShort, "value");
		return Double.parseDouble(value);
	}

	/**
	 * 
	 * @return This method will Unreconciled Cash Drawers Count from UI
	 */
	public int getUnreconciledCashDrawersCount() {
		String text = eleUtil.doGetText(unreconciledCashDrawers);
		String drawercount = text.replaceAll("[^0-9]", "");
		return Integer.parseInt(drawercount);
	}

	/**
	 * 
	 * @return This method will return table data for first table
	 * @throws Exception
	 */
	public List<String> getChekAuditTableData() throws Exception {
		String value = eleUtil.doGetAttribute(rowCount, "value");
		int count = Integer.parseInt(value);
		eleUtil.doActionMoveClick(checkAuditTable);
		for (int i = 0; i < count; i++) {
			Actions act = new Actions(driver);
			act.sendKeys(Keys.ARROW_DOWN).build().perform();
		}
		Thread.sleep(2000);
		return eleUtil.getElementsTextList(checkAuditTable);
	}

	public void clickOnCheckAuditButton1() {
		eleUtil.clickWhenReady(checkAuditButton, config.getExplicitWait());
	}

	/**
	 * 
	 * @return This method will return exp amount sum from database
	 */
	public double getChekAuditTableDataFromDB() throws Exception {
		double add = 0;
		String siteNum = reader.getCellData("MenuSelection", 0, 2);
		String query = "select sc.site_no,\r\n" + "           sc.name,\r\n" + "           sc.seq_no,\r\n"
				+ "           sc.check_number,\r\n" + "           sc.check_amt,\r\n" + "           sc.verified_fl,\r\n"
				+ "           sc.verified_fl verified_fl_db,\r\n" + "          stc.routing_id,\r\n"
				+ "           sc.reg_no,\r\n" + "           sc.tran_no,\r\n" + "          stc.tran_dt,\r\n"
				+ "          stc.order_id,\r\n" + "           null cashbox_no,\r\n" + "           null off_amt,\r\n"
				+ "           null safedetl_no\r\n" + "     from site_tender_check sc, safedetl_tender_check stc\r\n"
				+ "    where sc.site_no = " + siteNum + "\r\n"
				+ "      and sc.safedetl_tender_check_seq_no = stc.seq_no(+)\r\n"
				+ "       AND Exists (SELECT 1 --COR-21509\r\n"
				+ "              From Safedetl s, safedetl_tender st, safedetl_tender_check stc1\r\n"
				+ "             Where s.site_no = st.site_no\r\n" + "               AND sc.site_no = 108\r\n"
				+ "               And s.safedetl_no = st.safedetl_no\r\n"
				+ "               AND sc.safedetl_tender_check_seq_no = stc1.seq_no(+)\r\n"
				+ "               AND st.safedetl_no = stc1.safedetl_no\r\n"
				+ "               AND s.Tran_Time > (SELECT NVL(MAX(Tran_Time), TO_DATE('01/01/2000','MM/DD/YYYY')) Tran_Time\r\n"
				+ "                                    FROM  Safedetl\r\n"
				+ "                                   WHERE  Site_No = " + siteNum + "\r\n"
				+ "                                     AND  Tran_Type = 'W'\r\n"
				+ "                                     AND  NVL(Comment_Txt, 'NULL') != 'Auto Deposit'))\r\n"
				+ "   UNION\r\n" + "   select sc.site_no,\r\n" + "           sc.name,\r\n" + "           sc.seq_no,\r\n"
				+ "           sc.check_number,\r\n" + "           sc.check_amt,\r\n" + "           sc.verified_fl,\r\n"
				+ "           sc.verified_fl verified_fl_db,\r\n" + "          stc.routing_id,\r\n"
				+ "           sc.reg_no,\r\n" + "           sc.tran_no,\r\n" + "          stc.tran_dt,\r\n"
				+ "          stc.order_id,\r\n" + "           null cashbox_no,\r\n" + "           null off_amt,\r\n"
				+ "           null safedetl_no\r\n" + "     from site_tender_check sc, safedetl_tender_check stc\r\n"
				+ "    where sc.site_no = 108\r\n" + "      and sc.safedetl_tender_check_seq_no = stc.seq_no(+)\r\n"
				+ "       AND Exists (SELECT 1 \r\n"
				+ "              From Safedetl s, safedetl_tender st, safedetl_tender_check stc1\r\n"
				+ "             Where s.site_no = st.site_no\r\n" + "               AND sc.site_no = " + siteNum
				+ "\r\n" + "               And s.safedetl_no = st.safedetl_no\r\n"
				+ "               AND sc.safedetl_tender_check_seq_no = stc1.seq_no(+)\r\n"
				+ "               AND st.safedetl_no = stc1.safedetl_no(+)\r\n"
				+ "               /*AND sc.verified_fl = 'N'*/)\r\n" + "     order by check_amt";
		List<String> data = Utility.getDataFromDatabase(query, "CHECK_AMT");
		for (int i = 0; i < data.size(); i++) {
			String checkValue = data.get(i);
			double value = Double.parseDouble(checkValue);
			add = add + value;
		}
		return add;
	}

	/**
	 * 
	 * @return This method will return exp amount from expected amount text filed
	 *         from Check audit table
	 */
	public double getExpectedAmountCheckAuditTable() {
		String value = eleUtil.doGetAttribute(checkAuditExpAmount, "value");
		return Double.parseDouble(value);
	}

	/**
	 * 
	 * @return This method will return exp amount from expected amount text filed
	 *         from Check audit table
	 */
	public double getActualAmountCheckAuditTable() {
		String value = eleUtil.doGetAttribute(checkAuditActAmount, "value");
		return Double.parseDouble(value);
	}

	public double addRowandGetOverShortAmount(String checkAmount) {
		int count = eleUtil.getElementCount(rowActions);
		System.out.println("row count is equal to " + count);
		By hamIcon = By.xpath("//table/tbody/tr[" + count + "]/td/button");
		jsUtil.scrollIntoView(hamIcon);
		eleUtil.clickWhenReady(hamIcon, config.getExplicitWait());
		eleUtil.clickWhenReady(addRow, config.getExplicitWait());
		eleUtil.clickWhenReady(checkAmountInput, config.getExplicitWait());
		eleUtil.doSendKey(checkAmountInput, checkAmount);
		jsUtil.doJsClick(checkAmoutCheckBox);
		String value = eleUtil.doGetAttribute(checkAuditOverShort, "value");
		return Double.parseDouble(value);
	}

	public void clickOnAuthCancelButton() {
		eleUtil.clickWhenReady(authCancelButton, config.getExplicitWait());
	}

	public boolean authCashierWindowIsEmpty() throws Exception {

		return eleUtil.readValueFromInput(authCashierPassword).isEmpty()
				&& eleUtil.readValueFromInput(authCashier).isEmpty();

	}

	/**
	 * @return
	 * @Author Kapil
	 */
	public boolean verifyISCheckButtonEnable() {

		return eleUtil.doIsEnabled(checkAuditButton);
	}
}
