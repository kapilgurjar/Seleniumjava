package com.rediron.apex.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class CashDrawerReconciliation extends TestBase {

	public ElementUtil eleUtil;

	// WebElements

	By actualFloat = By.cssSelector("input#P9_ACTUAL_FLOAT");
	By totalExpected = By.cssSelector("input#P9_TOT_EXP");
	By totalActaul = By.cssSelector("input#P9_TOTAL");
	By overShort = By.cssSelector("input#P9_OVER");
	By siteNo = By.xpath("//input[contains(@id,'SITE_ID')]");
	By siteName = By.xpath("//input[contains(@id,'SITENAME')]");
	public By assignToRegisterLovButton = By.xpath("//button[contains(@id,'ASSIGN_TO_REG_lov_btn')]");
	public By cashDrawerNoLovBtn = By.xpath("//button[contains(@id,'CASH_DRAWER_NO_lov_btn')]");
	By checkDetailsButton = By.xpath("(//div[@id='tender_ir']//button)[1]");
	By assignToRegisterTextField = By.xpath("//button[contains(@id,'ASSIGN_TO_REG_lov_btn')]");
	public By declareWindow = By.xpath("//div[contains(@id,'ui-id')]//p");
	By loadMoreRows = By.xpath("//div[contains(@id,\"CASH_DRAWER_NO_dlg\")]//button[text()='Load More Rows']");
	By loadMoreRows_RegID = By.xpath("(//button[text()='Load More Rows'])[1]");
	By suggestedFloatAmount = By.cssSelector("input#P9_SUGG_FLOAT_AMT");
	public By overRideWindow = By.xpath("//div[contains(@id,'ui-id')]");
	public By inUseWindow = By.id("paraId");
	By authorizingCashierTextField = By
			.xpath("//div[contains(@id,'CASHIER_CONTAINER')]//input[contains(@id,'CASHIER')]");
	By passwordTextField = By.xpath("//div[contains(@id,'PASSWORD_CONTAINER')]//input[contains(@id,'PASSWORD')]");
	By authokButton = By.xpath("//button[@id='auth_ok']");
	public By okOverRideWindow = By.xpath("//button[contains(text(),'Ok')]");
	By kmCharge = By.cssSelector("input#assigncash_CUSTCHG_TNDR");
	By checkDetailsChckBox = By.xpath("//td//input[@type='checkbox']");
	By cshBoxNo = By.xpath("//div[contains(@id,'CASH_DRAWER_NO_dlg')]//table//tbody//tr//td[1]");
	By actualAmoutLabel=By.xpath("//div//label[contains(@id,'TOTAL_LABEL')]");
	By notDeclaredMsg=By.xpath("//div[contains(@id,'ui-id')]//p");
	By popWindowText=By.id("paraId");
	public By okButton=By.xpath("//button[text()='OK']");

	public CashDrawerReconciliation() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
	}

	// Actions
	/**
	 * @Author Kapil return this method is returning total float amount
	 */
	public String getActualFloatAmount() {

		return eleUtil.doGetAttribute(actualFloat, "value");

	}

	/**
	 * Author Kapil 
	 * This method is returning total expected amount
	 */
	public String gettotalExpected() {
		
		return eleUtil.doGetAttribute(totalExpected, "value");

	}

	/**
	 * @Author Kapil 
	 * 
	 * return This method is returning total amount
	 */
	public String gettotalActaul() {
		eleUtil.doClick(actualAmoutLabel);
		return eleUtil.doGetAttribute(totalActaul, "value");

	}

	/**
	 * @Author Kapil 
	 *  This method is returning overshort amount
	 */
	public String getoverShort() {

		return eleUtil.doGetAttribute(overShort, "value");

	}

	/**
	 * * Author Kapil 
	 *  This method is returning site no attributes
	 */
	public String getSiteNoClassAttributes() {

		return eleUtil.doGetAttribute(siteNo, "Class");

	}

	/**
	 * @Author Kapil 
	 *  This method is returning site class attributes
	 */
	public String getSiteNameClassAttributes() {

		return eleUtil.doGetAttribute(siteName, "Class");

	}

	/**
	 * @ Author Kapil return this method is returning window pop UI
	 */
	public String getWindowPopMessage() {

		return eleUtil.doGetText(popWindowText);

	}

	/**
	 * @ Author Kapil
	 * 
	 * @return Assign to Register is displayed or not
	 */
	public boolean assignToRegisterIsDisplayed() {

		return eleUtil.isDisplayed(assignToRegisterTextField);
	}

	// Clicking on the load more button
	public void clickOnLoadMoreRowsButton() throws Exception {

		boolean buttonStatus = eleUtil.doIsEnabled(loadMoreRows);
		System.out.println(buttonStatus);
		while (buttonStatus) {
			try {
				Thread.sleep(5000);
				// loadMoreRows.click();
				eleUtil.doClick(loadMoreRows);
			} catch (Exception e) {
				e.getStackTrace();
				break;
			}
		}
	}

	// Clicking on the load more button
	public void clickOnLoadMoreRowsButton_RegId() throws Exception {

		boolean buttonStatus = eleUtil.doIsEnabled(loadMoreRows_RegID);
		System.out.println(buttonStatus);
		while (buttonStatus) {
			try {
				Thread.sleep(5000);
				// loadMoreRows.click();
				eleUtil.doClick(loadMoreRows_RegID);
			} catch (Exception e) {
				e.getStackTrace();
				break;
			}
		}
	}

	/**
	 * @Author Kapil return actualFloatAmount
	 */
	public double getSuggestedFloatAmount() {
		double actualFloatAmount = 0;
		String floatAmount = eleUtil.doGetAttribute(suggestedFloatAmount, "value").toString();
		String suggestedFloatAmount = floatAmount.replace(".00", "");
		System.out.println(suggestedFloatAmount);
		try {
			actualFloatAmount = Double.parseDouble(suggestedFloatAmount);
			System.out.println(actualFloatAmount);
		} catch (NumberFormatException ex) {
			System.out.println("NumberFormatException");
		}
		return actualFloatAmount;
	}

	/**
	 * @Author Kapil
	 * @param cash_UserName
	 * @param cash_Password
	 */
	public void enterCashierUserIdAndPass(String cash_UserName, String cash_Password) {
		//eleUtil.doActionMoveSendKey(authorizingCashierTextField, cash_UserName);
		eleUtil.doSendKeys(authorizingCashierTextField, cash_UserName);
		eleUtil.doSendKeys(passwordTextField, cash_Password);
		eleUtil.doClick(authorizingCashierTextField);
		eleUtil.doClick(authokButton);
	}

	/**
	 * @return
	 * @Author Kapil
	 */
	public boolean verifyISCheckButtonEnable() {

		return eleUtil.doIsEnabled(checkDetailsButton);
	}

	public void clickOnCheckDetailsButton() {
		eleUtil.doClick(checkDetailsButton);
	}

	/**
	 * This method is clicking on assign to register lov button
	 * 
	 * @Author Kapil
	 */
	public void clickOnAssignToRegisterLovButton() {
		eleUtil.doClick(assignToRegisterLovButton);
	}

	// eleUtil.doClick(cashDrawerReconciliation.cashDrawerNoLovBtn);

	/**
	 * This method is clicking on assign to register lov button
	 * 
	 * @Author Kapil
	 */
	public void clickOnCashDrawerNoLovBtn() {
		eleUtil.doClick(cashDrawerNoLovBtn);
	}

	/**
	 * @return KMCharge is displayed or not
	 * @Author Kapil
	 */
	public boolean verifyIsKMChargeDisplayed() {
		return eleUtil.isDisplayed(kmCharge);
	}

	/**
	 * @return CheckDetails is displayed or not
	 * @Author Kapil
	 */
	public boolean verifyIsCheckBoxDisplayed() {
		return eleUtil.isDisplayed(checkDetailsChckBox);
	}

	/**
	 * @author Kapil
	 * @param Amount
	 * @return
	 * @return Get total Actual sum from tender section
	 * @throws Exception
	 */
	public double getValueFromTenderSectionActualAmount(String Amount) throws Exception {
		double addDisable = 0;
		double valueDisable = 0;
		double addEnable = 0;
		double valeEnable = 0;
		double addInput = 0;
		double valINput = 0;
		double add = 0;

		By table_Xpath = By.xpath("//div[@id='tender_ir_data_panel']//table//tbody/tr");
		int count = eleUtil.getElementCount(table_Xpath);
		for (int i = 2; i < count; i++) {
			By table_input = By.xpath("//div[@id='tender_ir_data_panel']//table//tbody/tr[" + i + "]/td[2]//input");
			String classAttr = eleUtil.doGetAttribute(table_input, "class");
			//System.out.println("class atrr is " + classAttr);
			if (classAttr.contains("disabled")) {
				String value = eleUtil.doGetAttribute(table_input, "value");
				valueDisable = Double.parseDouble(value);
				addDisable = valueDisable + addDisable;

			} else if (Double.parseDouble(eleUtil.readValueFromInput(table_input)) > 0) {
				Thread.sleep(3000);
				String value = eleUtil.doGetAttribute(table_input, "value");
				valeEnable = Double.parseDouble(value);
				addEnable = valeEnable + addEnable;

			} else if (Double.parseDouble(eleUtil.readValueFromInput(table_input)) == 0) {

				eleUtil.doSendKey(table_input, Amount);
				String value = eleUtil.doGetAttribute(table_input, "value");
				valINput = Double.parseDouble(value);
				addInput = valINput + addInput;

			}

		}
		add = addInput + addDisable + addEnable;
		return add;
	}

	/**
	 * @author Kapil
	 * @param Amount
	 * @return Get total Expected total from tender section
	 * @throws Exception
	 */
	public double getValueFromTenderSectionExpectedAmount() throws Exception {
		double valueDis = 0;
		double add = 0;

		By table_Xpath = By.xpath("//div[@id='tender_ir_data_panel']//table//tbody/tr");
		int count = eleUtil.getElementCount(table_Xpath);
		for (int i = 2; i < count; i++) {
			By table_input = By.xpath("//div[@id='tender_ir_data_panel']//table//tbody/tr[" + i + "]/td[3]//input");
			String classAttr = eleUtil.doGetAttribute(table_input, "class");
			if (classAttr.contains("disabled")) {
				String value = eleUtil.doGetAttribute(table_input, "value");
				valueDis = Double.parseDouble(value);
				add = add + valueDis;
			}

		}
		return add;
	}

	/**
	 * 
	 * This method will return cash box no list from UI with Reg_Id
	 * 
	 * @return cashBoxLovUI
	 */
	public List<String> getCashBOXValue() {

		List<String> cashBoxLovUI = eleUtil.getElementsTextList(cshBoxNo);

		return cashBoxLovUI;
	}
	
	public String getNotDeclaredMsg() {
		return eleUtil.doGetText(notDeclaredMsg);
	}

}
