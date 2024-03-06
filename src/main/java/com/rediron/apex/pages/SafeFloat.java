package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

public class SafeFloat  extends TestBase {

	public ElementUtil eleUtil;

	By siteNumAndName = By.xpath("//label[contains(@id,'SITE_LABEL')]/ancestor::div[contains(@id,'SITE_CONTAINER')]//span[contains(@id,'SITE') and contains(@class,'item-display')]");
	By dropDownRowsPerPage = By.xpath("//select[@title='Rows Per Page']");
	By numOfRowsOnPage = By.xpath("//table//tbody/tr");
	By safeFloatAmt = By.xpath("//input[contains(@id,'SAFE_FLOAT_AMT')]");
	By totalRegFloatAmt = By.xpath("//input[@type='text' and contains(@id,'TOT_RGSTR_AMT')]");
	By alertMsg = By.xpath("//div[@role='alertdialog']//p");
	By cancelBtnOnAlt = By.xpath("//div[@role='alertdialog']//button[text()='Cancel']");
	By okBtnOnAlt = By.xpath("//div[@role='alertdialog']//button[text()='Ok']");

	
	public SafeFloat() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
	}

	public String getSiteNumAndName() {
		return eleUtil.doGetText(siteNumAndName);
	}

	public void selectMaxRowsPerPage() {
		eleUtil.doSelectLastOptionfromDropDown(dropDownRowsPerPage);
	}

	public boolean checkAllRegNamesAreDisabled() {
		boolean status = true;
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		for(int i=1; i<=numOfRows; i++) {
			WebElement regName = driver.findElement(By.xpath("(//table//tbody/tr/td[1])["+i+"]"));
			String regNameClass = regName.getAttribute("class");
			if(!regNameClass.contains("disabled")) {
				status = false;
				break;
			}
		}
		return status;
	}

	public boolean checkAllExpectedFloatValuesEnabled() {
		boolean status = true;
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		for(int i=1; i<=numOfRows; i++) {
			WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])["+i+"]"));
			String expFloatValueClass = expFloatValue.getAttribute("class");
			if(expFloatValueClass.contains("disabled")) {
				status = false;
				break;
			}
		}
		return status;
	}

	public boolean checkIfSafeFltAmtisEditable() throws Exception {
		boolean status = false;
		String s = eleUtil.readValueFromInput(safeFloatAmt);
		eleUtil.doClearTextbox(safeFloatAmt);
		eleUtil.doSendKeys(safeFloatAmt, "4000");
		String s1 = eleUtil.readValueFromInput(safeFloatAmt);
		if(s1.equals("4000")) {
			status = true;
		}
		return status;
	}

	public boolean checkIfTotalRegAmtIsNotEditable() throws Exception {
		boolean status = false;
		String s = eleUtil.doGetAttribute(totalRegFloatAmt, "class");
		if(s.contains("disabled")){
			status = true;
		}
		return status;
	}

	public String getSafeFloatAmtfromUI() throws Exception {
		return eleUtil.readValueFromInput(safeFloatAmt);
	}


	public String getSafeFloatAmtfromDB(String siteNum) throws Exception {
		String activeSafeFloatAmtDB ="";
		activeSafeFloatAmtDB = Utility.executeQuery("SELECT * FROM SAFE_FLOAT where SITE_NO = '"+siteNum+"' and INACTIVE_DT is null","FLOAT_AMT");
		return activeSafeFloatAmtDB;
	}

	public List<String> getRegIDsfromUI() throws Exception {
		List<String> regIDsUI = new ArrayList<String>();
		selectMaxRowsPerPage();
		Thread.sleep(3000);
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		for(int i=1; i<=numOfRows; i++) {
			WebElement regName = driver.findElement(By.xpath("(//table//tbody/tr/td[1])["+i+"]"));
			String regIdName = regName.getText();
			regIDsUI.add(regIdName);
		}
		return regIDsUI;
	}

	public List<String> getRegIDsfromDB(String siteNum) throws Exception {
		List<String> regIDsDB = Utility.getDataFromDatabase("Select REG_ID from registr where post_site_no = '"+siteNum+"' and reg_id != 'OEREG'  Order by reg_id","REG_ID");
		return regIDsDB;
	}

	public void modifySafeFloat(String num) throws Exception {
		eleUtil.doClearTextbox(safeFloatAmt);
		eleUtil.doSendKeys(safeFloatAmt, num);
	}

	public void modifySafeFloatAndPressTab(String num) throws Exception {
		eleUtil.doClearTextbox(safeFloatAmt);
		eleUtil.doSendKeys(safeFloatAmt, num);
		driver.findElement(safeFloatAmt).sendKeys(Keys.TAB);

	}

	public void enterValuesInExpFloatsWithRandomIndex() throws Exception {
		selectMaxRowsPerPage();
		Thread.sleep(3000);
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		Random ran = new Random();
		WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])[1]"));
		Actions a = new Actions(driver);
		Thread.sleep(2000);
		a.doubleClick(expFloatValue).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys("9999").build().perform();
		expFloatValue.sendKeys(Keys.TAB);

		for(int i=2; i<=numOfRows; i++) {
			WebElement expFloatValue2 = driver.findElement(By.xpath("(//table//tbody/tr/td[2])["+i+"]"));
			expFloatValue2.sendKeys(Keys.CLEAR, String.valueOf(ran.nextInt(9999)));
			Thread.sleep(1000);
			expFloatValue2.sendKeys(Keys.TAB);
			Thread.sleep(1000);
		}
	}

	public int actualTotalOfExpFloat() throws Exception {
		Integer expectedTotal = 0;
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		for(int i=1; i<=numOfRows; i++) {
			WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])["+i+"]"));
			Integer expFloatValueInt = Integer.valueOf(expFloatValue.getText().toString());
			Thread.sleep(1000);
			expectedTotal = expectedTotal + expFloatValueInt;
			Thread.sleep(1000);
		}
		System.out.println("Expected total: "+expectedTotal);
		return expectedTotal;
	}

	public int totalOfExpFloatinUI() throws Exception {
		Integer actualTotalRegAmt = Integer.valueOf(eleUtil.doGetAttribute(totalRegFloatAmt, "value"));	
		return actualTotalRegAmt;
	}

	public String getAlertMsgText() throws Exception {
		eleUtil.waitForElementPresenceWithWait(alertMsg, 30, 5);
		String altmsg = eleUtil.doGetText(alertMsg);
		return altmsg;
	}
	

	
	public void clickOnOkBtn() throws Exception {
		eleUtil.doClick(okBtnOnAlt);
	}
	
	public boolean checkIfPrevValueRestoresOnCancel(int safeFlt, int totalRegAmt) throws Exception {
		boolean status = false;
		WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])[3]"));
		String originalValue = expFloatValue.getText();
		Actions a = new Actions(driver);
		Thread.sleep(2000);
		a.doubleClick(expFloatValue).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys("9999").build().perform();
		Thread.sleep(2000);
		expFloatValue.sendKeys(Keys.TAB);	
		Thread.sleep(2000);
		eleUtil.doClick(cancelBtnOnAlt);
		Thread.sleep(1000);
		a.doubleClick(expFloatValue).keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.TAB).build().perform();
		Thread.sleep(2000);
		String restoredValue = expFloatValue.getText();
		if(originalValue.equals(restoredValue)) {
			System.out.println("good");
			status = true;
		}
		return status;
	}

	public void makeTotalRegAmtMoreThanSafeFloatAmt(int safeFloat, int totalRegAmt) throws InterruptedException {
		int diff = safeFloat-totalRegAmt;
		List<WebElement> ls = driver.findElements(numOfRowsOnPage);
		int numOfRows = ls.size();
		System.out.println(ls.size());
		if(diff>9999) {
			int k = diff/9999;
			System.out.println(k);
			for(int i=1; i<=k+1; i++) {
				WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])["+i+"]"));
				System.out.println(expFloatValue.getText().toString());
				Integer expFloatValueInt = Integer.valueOf(expFloatValue.getText().toString());
				Thread.sleep(1000);
				if(expFloatValueInt==0) {
					Actions a = new Actions(driver);
					Thread.sleep(2000);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					a.moveToElement(expFloatValue).build().perform();
					Thread.sleep(4000);
					a.doubleClick(expFloatValue).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys("9999").build().perform();
					expFloatValue.sendKeys(Keys.TAB);
					js.executeScript("window.scrollBy(0,100)");
					
					
				}else {
					k=k+1;
//					expFloatValue.sendKeys(Keys.TAB);
				}
			}
		}else {
			for(int i=1; i<=numOfRows; i++) {
				WebElement expFloatValue = driver.findElement(By.xpath("(//table//tbody/tr/td[2])["+i+"]"));
				Integer expFloatValueInt = Integer.valueOf(expFloatValue.getText().toString());
				Thread.sleep(1000);
				if(expFloatValueInt==0) {
					Actions a = new Actions(driver);
					Thread.sleep(2000);
					a.doubleClick(expFloatValue).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys("9999").build().perform();
					expFloatValue.sendKeys(Keys.TAB);
					break;
				}
			}
		}
	}	
}	
