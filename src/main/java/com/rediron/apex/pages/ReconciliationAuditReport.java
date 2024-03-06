package com.rediron.apex.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

public class ReconciliationAuditReport extends TestBase {

	By site_Group = By.xpath("//input[@type='text' and contains(@id,'_SITE_GROUP_LOV')]");
	By closeButton = By.xpath("//button[@title='Close']");
	By allEmployees = By.xpath("//input[@type='text' and contains(@id,'_EMPLOYEE_NAME')]");
	By employee= By.cssSelector("input#P21_EMPLOYEE_ID");
	By empLovButton = By.cssSelector("button#P21_EMPLOYEE_ID_lov_btn");
	By listOfEmpFullName = By.xpath("//div[@id='PopupLov_21_P21_EMPLOYEE_ID_dlg']//table//td[2]");
	By fromLovButton = By.id("P21_FROM_lov_btn");
	By toLovButton=By.id("P21_TO_lov_btn");
	By listOfFromDates = By.xpath("//table//td[2]");
	By floatAmount=By.xpath("//input[@type='text' and contains(@id,'_FLOAT_TOTAL')]");
	By fromLovSearch=By.xpath("//div[@id='PopupLov_21_P21_FROM_dlg']//input[@aria-label='Search']");
	By toLovSearch=By.xpath("//div[@id='PopupLov_21_P21_TO_dlg']//input[@aria-label='Search']");
	By previewButton=By.xpath("//span[text()='Preview']/parent::button");
	By cancelButton=By.xpath("//span[text()='Clear']/parent::button");
	By toDate=By.cssSelector("input#P21_TO");
	By empSearch=By.xpath("//div[contains(@id,'EMPLOYEE_ID_dlg')]//input[@aria-label='Search']");
	By empSearchFirstRes=By.xpath("//div[@class='a-PopupLOV-results a-GV']//table//tr//td[1]");
	By fromDateNoRsFound=By.xpath("//span[text()='No results found.']");
	By loadMoreRows=By.xpath("//div[contains(@id,'_EMPLOYEE_ID_dlg')]//button[text()='Load More Rows']");
	

	// class constructor
	public ReconciliationAuditReport() {

		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
		menuSelection=new MenuSelection(driver);
		basePage = new BasePage();
	}

	/**
	 * This method will return default login site value
	 */
	public String getDefaultLogginSiteValue(String attValue) {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doGetAttribute(site_Group, attValue);
	}

	/**
	 * This method will click on the close button
	 * @return
	 */
	public ReconciliationAuditReport clickOnCloseButton() {
		eleUtil.clickWhenReady(closeButton, config.getExplicitWait());
		return this;
	}
	/**
	 * This method will return all employee text field value attributes value 
	 * @return
	 */
	public String getAllEmployeesVallue() {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doGetAttribute(allEmployees, "value");
	}
	
	/**
	 * This method will return employee text field  value attributes value 
	 * @return
	 */
	public String getEmployeeVallue() {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doGetAttribute(employee, "class");
	}
	/**
	 * @author Kapil
	 * This will return employees lov data 
	 * @return
	 */
	public List<String> getListOfEmployesUI() throws Exception {
		List<String>allEmpList= new ArrayList<>();
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWithRetry(empLovButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		basePage.clickOnLoadMoreRowsButton(loadMoreRows);
		List<String>allemp= eleUtil.getElementsTextList(listOfEmpFullName);
		for(String emp:allemp) {
			String empWithOutSpace=emp.trim().replace(" ", "");
			allEmpList.add(empWithOutSpace);
		}
		return allEmpList;
	}
	
	/**
	 * @author Kapil
	 * This will return employees lov data from data base
	 * @return
	 */
	public List<String> getListOfEmployesDB(String siteNum) throws Exception {
		List<String>allEmpList= new ArrayList<>();
		List<String> allEmpNameDB = Utility.getDataFromDatabase(
				"select e.full_name employee_nm  from employee e, empbysit ebs  where ebs.employee_id = e.employee_id\r\n"
						+ "and ebs.site_no in  (select site_no  from sitgrp_All \r\n" + "where group_id = '"+siteNum+"')",
				"EMPLOYEE_NM");
		for(String emp:allEmpNameDB) {
			String empWithOutSpace=emp.trim().replace(" ", "");
			allEmpList.add(empWithOutSpace);
		}
		return allEmpList;
	}
	/**
	 * @author Kapil
	 * This will return from Dates lov data 
	 * @return
	 */
	public List<String> getListOfFromDates() throws Exception {
		List<String> actDate = new ArrayList<>();
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(fromLovButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		List<String> date = eleUtil.getElementsTextList(listOfFromDates);
		int index = date.lastIndexOf("-");
		date.remove(index);
		for (int i = 0; i < date.size(); i++) {
			String strDate = date.get(i);
			Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate1 = formatter.format(date1);
			actDate.add(strDate1);
		}
		return actDate;
	}
	/**
	 * @author Kapil
	 * This will return float amount text field status
	 */
	public boolean IsFloatAmountTextFiledDisplayed() {
		eleUtil.switchToFrameByIndex(0);
		return eleUtil.isDisplayed(floatAmount);
	}
	
	public ReconciliationAuditReport clickOnReconAuditReport() {
		eleUtil.doClick(menuSelection.recAuditReport);
		return this;
	}
	/**
	 *@author Kapil
	 *@param fromDate
	 */
	public ReconciliationAuditReport selectFromDateFromList(String fromDate) {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(fromLovButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doSendKeys(fromLovSearch, fromDate);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doEnter(fromLovSearch);
		return this;
	}
	
	/**
	 *@author Kapil
	 *@param fromDate
	 */
	public ReconciliationAuditReport selectToDateFromList(String fromDate) {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(toLovButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		eleUtil.doSendKeys(toLovSearch, fromDate);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doEnter(toLovSearch);
		return this;
	}
	
	
	public ReconciliationAuditReport clickOnPreviewButton() {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.clickWithRetry(previewButton, config.getExplicitWait());
		return this;
	}
	
	/**
	 * @author Kapil
	 * Move to new tabs and getting window title
	 */
	public String moveToJasperTabAndGetTitle() {
		eleUtil.SwitchToWindow(1);
		eleUtil.doStaticWait(config.getMediumWait());
		return driver.getTitle();
	}
	/**
	 * @author Kapil
	 * @return This will return float amount 
	 */
	public String getFloatAmount() {
		eleUtil.switchToFrameByIndex(0);
		return eleUtil.doGetAttribute(floatAmount, "value");
	}
	
	public String getCurrentToDate() throws ParseException {
		//eleUtil.switchToFrameByIndex(0);
		eleUtil.doStaticWait(config.getMediumWait());
		String date =eleUtil.doGetAttribute(toDate, "value");
		Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String strDate1 = formatter.format(date1);
		System.out.println(strDate1);
		return strDate1;
	}
	
	public boolean ISCancelButtonWorking() {
		eleUtil.switchToFrameByIndex(0);
		eleUtil.clickWhenReady(cancelButton, config.getExplicitWait());
		return eleUtil.readValueFromElement(employee).isEmpty();
		
	}
	
	public ReconciliationAuditReport selectEmployeeName(String empName) {
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.switchToFrameByIndex(0);
		eleUtil.clickWhenReady(empLovButton, config.getExplicitWait());
		eleUtil.switchToDefaultContent();
		eleUtil.doSendKeys(empSearch, empName);
		eleUtil.doStaticWait(config.getSmallWait());
		eleUtil.doEnter(empSearch);	
		return this;
	}
	
	/*
	 * This will click on the first value from employees list
	 */
	public ReconciliationAuditReport clickOnEmpFirstSearchResult() {
		eleUtil.clickWhenReady(empSearchFirstRes, config.getExplicitWait());
		return this;
	}
	
	
	/**
	 * @return No result found message on list, if user enter invalid data 
	 */
	public String getNoResultFoundMsgFromListOfValues() {
		 eleUtil.waitForText(fromDateNoRsFound, "No results found.", config.getExplicitWait());
		return eleUtil.doGetText(fromDateNoRsFound);
	}

}
