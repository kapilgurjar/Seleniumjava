package com.rediron.apex.pages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Step;

public class AddASiteToDataBase extends TestBase {

	By okButton = By.xpath("//button[@id='OK' or @id='Ok']");
	By newSiteColumn = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[1]");
	By copySiteButton = By.id("OLD_SITE_HDR");
	By copySiteLovData = By.xpath("//div[@id='CONTROL_ig_column_header_menu_rows']//a");
	By copyfromSiteFirstColmn = By.xpath("(//div[@id='CONTROL_ig_grid_vc']//table//tr//td[2])[1]");
	By copyfromSiteColmn = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[2]");
	By copyFromSiteFisrtColumnLovbtn = By.xpath("//button[@id='OLD_SITE_lov_btn']");
	By copySiteAlertTitle = By.xpath("//span[text()='Copy site']");
	public By copyAlertYesBtn = By.cssSelector("button#Yes");
	public By copyAlertNOBtn = By.cssSelector("button#No");
	By timeZoneLovButton = By.cssSelector("button#TIME_ZONE_CD_lov_btn");
	By timeZoneFirstColumn = By.xpath("(//div[@id='CONTROL_ig_grid_vc']//table//tr//td[6])[1]");
	By timeZoneColumn = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[6]");
	By timeZonesLovData = By.xpath("//ul[@role='listbox']//li");
	By inCityCheckBox = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[5]//div//span");
	By inCityEditButton = By.xpath("(//div[@id='CONTROL_ig_grid_vc']//table//tr//td[4]//button)[1]");
	By ownerIDColumn = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[7]");
	By ownerIDLovButton = By.xpath("(//button[contains(@id,'_lov_btn')])[1]");
	By affiliateCheckBox = By.xpath("//div[@id='CONTROL_ig_grid_vc']//table//tr//td[8]//div//span");
	By clearButton = By.xpath("//span[text()='Clear']//parent::button");
	By search = By.id("CONTROL_ig_toolbar_search_field");
	By goButton = By.xpath("//span[text()='Go']//parent::button");
	By noResultFound = By.xpath("//span[text()='No data found' and @id]");
	By editAdress1 = By.cssSelector("input[id*='_ADR1']");
	By editPhone = By.xpath("//input[contains(@id,'_PHONE_NO')][@type='text']");
	By editSave = By.cssSelector("button#btn_save");
	By submitCheckedButton=By.cssSelector("button#btn_submit_checked_items");

	//Class Constructor 
	public AddASiteToDataBase() {
		PageFactory.initElements(driver, this);
		cashDrawerSetup = new CashDrawerSetUp(driver);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		menuSelection = new MenuSelection(driver);
	}
	@Step("Get window alert message")
	public String getAlertPopMessage() {
		return cashDrawerSetup.getPopWindowText();
	}
	@Step("Click on ok button")
	public AddASiteToDataBase clickOnOkButton() {
		eleUtil.doClick(okButton);
		return this;
	}
	
	@Step("Click on submit check button")
	public AddASiteToDataBase clickOnSubmitCheckedItemButton() {
		eleUtil.doClick(submitCheckedButton);
		return this;
	}
	
	@Step("No access to remote site and it should show expand button")
	public boolean verifyNoAccessToRemoteSite() {
		return menuSelection.IsExpanButtonDisplayed();
	}

	@Step("Get siteno and siteName from database")
	public ArrayList<String> getSiteDataFromDB() throws SQLException {
		String siteNo = "";
		String siteName = "";
		ArrayList<String> newSite = new ArrayList<>();
		String query = "Select * from siteinst where site_no not in (select site_no from site)";
		List<String> siteNubers = Utility.getDataFromDatabase(query, "SITE_NO");
		List<String> siteNames = Utility.getDataFromDatabase(query, "NAME");
		for (int i = 0; i < siteNubers.size(); i++) {
			siteNo = siteNubers.get(i).trim().replaceAll("[^0-9,A-Z,a-z]", "");
			System.out.println(siteNo);
			for (int j = i; j <= i; j++) {
				siteName = siteNames.get(j).trim().replaceAll("[^0-9,A-Z,a-z]", "");
				System.out.println(siteName);
			}
			newSite.add(siteNo + siteName);
		}
		return newSite;
	}
	
	@Step("Get siteno and siteName from UI")
	public ArrayList<String> getNewSiteNameUI() {
		ArrayList<String> newSite = new ArrayList<>();
		List<String> siteNames = eleUtil.getElementsTextList(newSiteColumn);
		for (int i = 0; i < siteNames.size(); i++) {
			String name = siteNames.get(i).trim().replaceAll("[^0-9,A-Z,a-z]", "");
			newSite.add(name);
		}
		return newSite;
	}

	@Step("Get data from copy site love data from UI")
	public List<String> getCopySiteLovDataUI() {
		ArrayList<String> copySiteLov = new ArrayList<>();
		eleUtil.clickWhenReady(copySiteButton, config.getExplicitWait());
		List<String> siteNames = eleUtil.getElementsTextList(copySiteLovData);
		for (int i = 0; i < siteNames.size(); i++) {
			String name = siteNames.get(i).trim().replaceAll("[^0-9,A-Z,a-z]", "");
			copySiteLov.add(name);
		}
		return copySiteLov;
	}

	@Step("Get data from copy site love from DB")
	public ArrayList<String> getCopyLovDataDB() throws SQLException {
		String query = " select site_no, full_name,site_no||'-'||full_name full_del from site where name not in ('Site Being Created', 'Add a Site utility failed!') order by site_no";
		ArrayList<String> copySiteLov = new ArrayList<>();
		List<String> siteNames = Utility.getDataFromDatabase(query, "FULL_DEL");
		for (int i = 0; i < siteNames.size(); i++) {
			String name = siteNames.get(i).trim().replaceAll("[^0-9,A-Z,a-z]", "");
			copySiteLov.add(name);
		}
		return copySiteLov;
	}

	@Step("Select site from copy from site lov")
	public void selectSiteFromCopyFromSiteColumn(String siteNum) throws Exception {
		// eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(copyfromSiteFirstColmn, config.getExplicitWait());
		eleUtil.clickWhenReady(copyFromSiteFisrtColumnLovbtn, config.getExplicitWait());
		selectSiteFromLov(siteNum);
	}
	
	@Step("Select site from lov")
	public void selectSiteFromLov(String siteNum) throws Exception {
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		eleUtil.doActionMoveClick(By.xpath("//li[@data-id='" + siteNum + "']"));
	}

	@Step("Is copy site alert title displayed or not")
	public boolean iSCopySiteAlertTitleDispalyed() {
		return eleUtil.doIsDisplayed(copySiteAlertTitle);
	}

	@Step("Verify that if clicked on yes data should be pasted to all rows")
	public boolean ISDataSelectFormCopyFromSiteColumn(String siteNo) {
		eleUtil.doClick(copyAlertYesBtn);
		boolean sts = false;
		List<String> text = eleUtil.getElementsTextList(copyfromSiteColmn);
		for (int i = 0; i < text.size(); i++) {
			String textVal = text.get(i);
			if (textVal.equalsIgnoreCase(siteNo)) {
				sts = true;
			} else {
				sts = false;
			}
		}
		return sts;
	}

	@Step("Get the time zone from Lov")
	public List<String> getTimeZoneLovData() throws Exception {
		eleUtil.clickWhenReady(timeZoneFirstColumn, config.getExplicitWait());
		eleUtil.clickWhenReady(timeZoneLovButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(timeZonesLovData);
	}

	@Step("select a time zone")
	public void selectTimeZone(String timeZone) throws Exception {
		eleUtil.clickWhenReady(timeZoneFirstColumn, config.getExplicitWait());
		eleUtil.clickWhenReady(timeZoneLovButton, config.getExplicitWait());
		selectSiteFromLov(timeZone);
	}

	@Step("Verify that if clicked on yes data should be pasted to all rows")
	public boolean ISDataSelectFormCopyFromTimeZoneColumn(String timeZone) {
		eleUtil.doClick(copyAlertYesBtn);
		boolean sts = false;
		List<String> text = eleUtil.getElementsTextList(timeZoneColumn);
		for (int i = 0; i < text.size(); i++) {
			String textVal = text.get(i);
			if (textVal.equalsIgnoreCase(timeZone)) {
				sts = true;
			} else {
				sts = false;
			}
		}
		return sts;

	}

	public void clickOnInCityCheckBox() {

		eleUtil.clickWhenReady(inCityCheckBox, config.getExplicitWait());
	}

	@Step("Verify that if clicked on yes data should be pasted to all rows")
	public boolean ISAllCheckBoxSelectedIfClickOnYesButton() {
		List<String> textbefore = eleUtil.getElementsTextList(inCityCheckBox);
		if (textbefore.get(0).equalsIgnoreCase(""))
			eleUtil.doClick(copyAlertYesBtn);
		boolean sts = false;
		List<String> text = eleUtil.getElementsTextList(inCityCheckBox);
		System.out.println(text);
		for (int i = 0; i < text.size(); i++) {
			String textVal = text.get(i);
			if (textVal.equalsIgnoreCase("")) {
				sts = true;
			} else {
				sts = false;
			}
		}
		return sts;

	}

	@Step("Get owner id table data on the basis of column number")
	public List<String> getOwnerIdTableColumnData(String columnNo) throws Exception {
		eleUtil.clickWhenReady(ownerIDColumn, config.getExplicitWait());
		// eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(ownerIDLovButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		By xpath = By.xpath("(//table)[4]//tr//td[" + columnNo + "]");
		return eleUtil.getElementsTextList(xpath);
	}

	@Step("Select owner id from lov")
	public AddASiteToDataBase selectOwnerId(String columnNo, String ownerId) throws Exception {
		eleUtil.clickWhenReady(ownerIDColumn, config.getExplicitWait());
		eleUtil.clickWhenReady(ownerIDLovButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		By xpath = By.xpath("(//table)[4]//tr//td[" + columnNo + "]");
		eleUtil.clickOnLink(xpath, ownerId);
		return this;
	}

	@Step("Verify that if clicked on yes data should be pasted to all rows")
	public boolean ISDataSelectFormCopyFromOwnerID(String ownerID) {
		eleUtil.doClick(copyAlertYesBtn);
		boolean sts = false;
		List<String> text = eleUtil.getElementsTextList(ownerIDColumn);
		for (int i = 0; i < text.size(); i++) {
			String textVal = text.get(i);
			if (textVal.equalsIgnoreCase(ownerID)) {
				sts = true;
			} else {
				sts = false;
			}
		}
		return sts;

	}

	public AddASiteToDataBase clickOnAffiliateCheckBox() {
		eleUtil.clickWhenReady(affiliateCheckBox, config.getExplicitWait());
		return this;
	}

	public AddASiteToDataBase clickOnClearButton() {
		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
		return this;
	}

	public String isCopySiteInputEmpty() throws Exception {
		return eleUtil.doGetText(copyfromSiteFirstColmn);
	}

	public AddASiteToDataBase SearchSite(String text) {
		eleUtil.doSendKey(search, text);
		eleUtil.clickWhenReady(goButton, config.getExplicitWait());
		return this;
	}

	public boolean iSNoResultFoundDisplayed() {
		return eleUtil.isDisplayed(noResultFound);
	}

	@Step("Geting new Site column first column data")
	public String getNewSiteText() {
		return eleUtil.doGetText(newSiteColumn);
	}
	
	public AddASiteToDataBase clickOnEditAdressButton() {
		eleUtil.clickWhenReady(inCityEditButton, config.getExplicitWait());
		return this;
	}

	public String getEditAdress1Value() throws Exception {
		return eleUtil.readValueFromInput(editAdress1);
	}

	public String getEditPhoneValue() throws Exception {
		return eleUtil.readValueFromInput(editPhone);

	}
	@Step("Enter data in address1 and phone text field")
	public AddASiteToDataBase enterDataInAdress1AndPhoneTextbox(String Address,String phone) {
		eleUtil.doSendKeys(editAdress1, Address);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.doSendKeys(editPhone, phone);
		eleUtil.doStaticWait(config.getMediumWait());
		eleUtil.clickWhenReady(editSave, config.getExplicitWait());
		eleUtil.doStaticWait(config.getMediumWait());
		return this;
	}
}
