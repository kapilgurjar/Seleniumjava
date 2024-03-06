package com.rediron.apex.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class CashDrawerDeclaration extends TestBase {

	public ElementUtil eleUtil;
	int rowCount = 0;

	// WebElements

	public By siteNo = By.id("P11_SITE");
	public By siteName = By.id("P11_SITE_NAME");
	By assignToRegister = By.id("P11_ASSIGN_TO");
	By cashDrawer = By.id("P11_CASH_DRAWER");
	By datePicker = By.xpath("//*[@id=\"P11_DATE_CONTAINER\"]/div[2]/div/button");
	By saveButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[1]");
	By exitButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[3]");
	By cancelButton = By.xpath("(//*[@class='t-BreadcrumbRegion-buttons']//button)[2]");
	By popUpWindowOkButton = By.cssSelector("button#Ok");
	By popUpWindowCancelButton = By.xpath("//*[@id='t_PageBody']/div[11]/div[3]/div/button[1]");
	By popUpWindowText = By.id("paraId");
	By authorizationEmployee = By.xpath("//input[@id='P11_AUTHORIZATION_EMPLOYEE']");
	By authorizationPassword = By.xpath("//input[@id='P11_PASSWORD']");
	By authorizationOKButton = By.xpath("//td//button[@id='AUTH_OK']");
	By authorizationValidationMessage = By.xpath("//div[contains(@id,'ui-id')]//p");
	By assignToLovBtn = By.xpath("//button[@id='P11_ASSIGN_TO_lov_btn']");
	By CASHDRAWERlovBtn = By.xpath("//button[@id='P11_CASH_DRAWER_lov_btn']");
	public By cashBoxLovList = By.xpath("(//ul[@role='listbox'])[2]//li");
	public By AssignToRegisterLovList = By.xpath("(//ul[@role='listbox'])[1]//li");
	By tenderCash = By.id("assigncash_CUSTCHG_TNDR");
	public By selectAssignToRegister = By.xpath("//div[@id='ui-id-3']/p");
	public By cashDrawerTextField = By.xpath("//input[@id='P11_CASH_DRAWER']");
	public By selectCashDrawer = By.xpath("//div[@id='ui-id-3']/p");
	By checkDetailsButton = By.xpath("//button//span[text()='Check Detail']");
	// By checkAmount=By.xpath("//*[@id='check_ig_grid_vc_cur']");
	By totalAmount = By.xpath("//input[@id='P11_TOTAL_AMOUNT']");
	By tenderCheck = By.id("assigncash_CHECK_TNDR");
	By tenderVisa = By.id("assigncash_CREDIT1_TNDR");
	By tenderMC = By.id("assigncash_CREDIT2_TNDR");
	By tenderAmex = By.id("assigncash_CREDIT3_TNDR");
	By tenderDiscover = By.id("assigncash_CREDIT4_TNDR");
	By tenderCanada = By.id("assigncash_FORCUR1_TNDR");
	By tenderSingpore = By.id("assigncash_FORCUR2_TNDR");
	By tenderKmcharge = By.id("assigncash_CUSTCHG_TNDR");
	By checkAmount = By.xpath("//div[@id='check_ig_content_container']//table/tbody/tr/td[2]");
	By checkNumber = By.xpath("//div[@id='check_ig_content_container']//table/tbody/tr/td[3]");
	By selecationAction = By.xpath("//*[@id='check_ig_grid_vc_cur']//button//span");
	By addRows = By.xpath("//button[text()='Add Row']");
	By button = By.xpath("(//*[@id=\"check_ig_grid_vc\"]//td/button)[" + rowCount + "]");
	//By checkDetailsOkButton = By.xpath("//button[@id='B118337350802723230'or @id='B322056558109345095']");
	By checkDetailsOkButton = By.xpath("//span[contains(text(),'Ok')]//parent::button");
	By assignToRegisterSearchBox=By.xpath("//input[@aria-label='Search']");
	By cashDrawerLovSearch=By.xpath("//div[contains(@id,'CASH_DRAWER_dlg')]//div//input");
	By loadMoreRowsButton=By.xpath("//div[@id='PopupLov_11_P11_CASH_DRAWER_dlg']//button");
	By checkValue=By.cssSelector("input#assigncash_CHECK_TNDR");

	// Initializing the Page Objects:

	public CashDrawerDeclaration() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil=new JavaScriptUtil(driver);
	}

	/**
	 * @Author Kapil
	 * @return Site no is displayed or not on UI
	 */
	public boolean verifySiteNoIsPopualated() {
		return eleUtil.isDisplayed(siteNo);

	}

	/**
	 * @Author Kapil
	 * @return Site name is displayed or not on UI
	 */
	public boolean verifySiteNameIsPopualated() {
		return eleUtil.isDisplayed(siteName);
	}
	
	/**
	 * @Author Kapil
	*This method will return assign to register validation message 
	 */
	public String getAssignToRegisterValidationMessage() {
		eleUtil.doClick(cashDrawerTextField);
		return eleUtil.doGetText(popUpWindowText);
	}

	/**
	 * @Author Kapil
	*This method will return cash drawer validation message 
	 */
	public String getAssignToCashDrawerMessage() {
		return eleUtil.doGetText(popUpWindowText);
	}
	
	/**
	 * @Author Kapil
	*@param text
	 */
	public void enterValueInAssignToRegisterSearch(String text) {
		eleUtil.doSendKeys(assignToRegisterSearchBox, text);
	}
	
	/**
	 * @Author Kapil
	*@param text
	 */
	public void enterValueInCashDrawerLovSearch(String text) {
		//jsUtil.setAttributes(cashDrawerLovSearch, text);
		//eleUtil.doActionMoveSendKey(cashDrawerLovSearch, text);
		eleUtil.doSendKeys(cashDrawerLovSearch, text);
	}


	/**
	 * @Author Kapil
	 * @return Site no class attribute
	 */
	public String verifySiteNoIsDisabled() {

		return eleUtil.doGetAttribute(siteNo, "class");
	}

	/**
	 * @Author Kapil
	 * @return Site name class attribute
	 */
//	public String verifySiteNameIsDisabled() {
//
//		//return eleUtil.doGetAttribute(siteName, "class");
//	}

	/**
	 * @Author Kapil
	 * @return Cancel button window warning message
	 */
	public String getCancelButtonPopWindowText() {

		return eleUtil.doGetText(popUpWindowText);
	}

	public void clickOnPopupOkButton() {

		eleUtil.doClick(popUpWindowOkButton);

	}

	/**
	 * @Author Kapil
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
	 * @Author Kapil
	 * @return Authorization Window validation message
	 */
	public String getauthorizationWindowMessage() {

		return eleUtil.doGetText(popUpWindowText);
	}

	public void clickAssignToLovBtn() {

		eleUtil.doClick(assignToLovBtn);
	}

	public void clickCASHDRAWERlovBtn() {

		eleUtil.doClick(CASHDRAWERlovBtn);
	}

	/**
	 * @Author Kapil
	 * @return cash amount from cash text field
	 * @throws Exception
	 */
	public double getValueFromCash() throws Exception {
		double cashAmount = 0;
		eleUtil.doClick(tenderKmcharge);
		Thread.sleep(5000);
//		String cashValue = eleUtil.doGetAttribute(cash, "value").toString();
//		String ActualCashValue = cashValue.replace(".00", "");
//		try {
//			cashAmount = Double.parseDouble(ActualCashValue);
//		} catch (NumberFormatException ex) {
//			System.out.println("NumberFormatException");
//		}
		return cashAmount;

	}

	/**
	 * @return Cash Drawer Declaration message is displayed
	 */
	public boolean verifyCashDrawerDeclared() {
		return eleUtil.isDisplayed(popUpWindowText);
	}

	/**
	 * @Author Kapil
	 * @return total amount
	 */
	public double getTotalAmount() {
		eleUtil.doClick(tenderVisa);
		String total = eleUtil.doGetAttribute(totalAmount, "value");
		System.out.println(total);
		return Double.parseDouble(total);

	}

	/**
	 * @Author Kapil
	 * @param Amount
	 * @return Get total sum from tender section
	 * @throws Exception
	 */
	public double getValueFromTenderSection(String Amount) throws Exception {

		//eleUtil.doSendKeys(tenderCheck, Amount);

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

		//eleUtil.doSendKeys(tenderKmcharge, Amount);

		//String tenderKmchargeValue = eleUtil.doGetAttribute(tenderKmcharge, "value");

		//double tenderKmchargeAmount = Double.parseDouble(tenderKmchargeValue);

		return tenderVisaAmount + tenderCheckAmount + tenderMCAmount + tenderAmexAmount + tenderDiscoverAmount
				;
	}

	/**
	 * @Author Kapil
	 * This method will click on check details button
	 */
	public void clickOnCheckDetailsButton() {
		// eleUtil.doClick(checkDetailsButton);
		eleUtil.doActionMoveClick(checkDetailsButton);
	}
	/**
	 * @Author Kapil
	 * This method will return check details sum 
	 */
	public void getCheckDetailsTableSum(String checkAmt, String checkNum) throws Exception {
		int rowCount = eleUtil.getElementCount(checkAmount);
		if (rowCount == 1) {
			eleUtil.doActionsDoubleClick(checkAmount);
			eleUtil.doActionClear();
			eleUtil.doActionsSendKeys(checkAmount, checkAmt);
			eleUtil.doActionsDoubleClick(checkNumber);
			eleUtil.doActionClear();
			eleUtil.doActionsSendKeys(checkNumber, checkNum);
			eleUtil.doClick(checkDetailsOkButton);
		} else {

			By button = By.xpath("(//*[@id=\"check_ig_grid_vc\"]//td/button)[" + rowCount + "]");
			eleUtil.doActionMoveClick(button);
			eleUtil.doActionMoveClick(addRows);
			int Count = eleUtil.getElementCount(checkAmount);
			By checkNumber = By.xpath("//div[@id='check_ig_content_container']//table/tbody/tr[" + Count + "]/td[3]");
			String beforeXpath = "//div[@id='check_ig_content_container']//table//tbody//tr[";
			String afterXpath = "]/td[2]";
			String xpath = beforeXpath + Count + afterXpath;
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath(xpath))).sendKeys(checkAmt).build().perform();
			Thread.sleep(2000);
			eleUtil.doActionsDoubleClick(checkNumber);
			eleUtil.doActionClear();
			eleUtil.doActionsSendKeys(checkNumber, checkNum);
			Thread.sleep(2000);
			eleUtil.doClick(checkDetailsOkButton);
			Thread.sleep(2000);
		}

	}
	
		/**
		 * @author Kapil
		 * This method will scroll down and click on load more rows button
		 * @throws Exception
		 */
		public void clickOnLoadMoreRowsButton() throws Exception {

			boolean buttonStatus = eleUtil.doIsEnabled(loadMoreRowsButton);
			System.out.println(buttonStatus);
			while (buttonStatus) {
				try {
					Thread.sleep(5000);
					// loadMoreRows.click();
					eleUtil.doClick(loadMoreRowsButton);
				} catch (Exception e) {
					e.getStackTrace();
					break;
				}
			}
		}
		/**
		 * @author Kapil
		 * @return 
		 */
		public String getCheckAmount() {
			
			return eleUtil.doGetAttribute(checkValue,"value");
		}

}
