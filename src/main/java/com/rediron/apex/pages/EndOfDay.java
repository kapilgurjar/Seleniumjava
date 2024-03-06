package com.rediron.apex.pages;

import java.sql.SQLException;
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

public class EndOfDay extends TestBase {
	By transDate = By.cssSelector("input[id*=DATE_TRAN_TO]");
	By transButton = By.cssSelector("button[id*=DATE_TRAN_TO_lov_btn]");
	By transDateLov = By.xpath("//ul[@role='listbox']//li");
	By openDate = By.cssSelector("span[id*=TO_DATE_DESC]");
	By updateUIMsg = By.cssSelector("span[id=P1002_INFO]");
	By windowOkButton = By.xpath("//div[contains(@id,'ui-id')]//following::button");
	By runEodButton = By.cssSelector("button#OK");
	By windowText = By.cssSelector("div[id*=ui-id]>p");
	By exitButton = By.cssSelector("button#CANCEL");

	public EndOfDay() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public String getPageTitle() {
		
		return eleUtil.doGetPageTitleIs("End Of Day", config.getExplicitWait());

	}

	public String getTransDateValue(String date) throws Exception {
		eleUtil.clickWhenReady(transButton, config.getExplicitWait());
		eleUtil.doActionMoveClick(By.xpath("//*[@data-id= '" + date + "']"));
		return eleUtil.doGetAttribute(transDate, "value");
	}

	public String getOpenDateValue() throws Exception {
		Thread.sleep(2000);
		String Date = eleUtil.doGetText(openDate);
		Date date1 = new SimpleDateFormat("MMM d,yyyy").parse(Date);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate1 = formatter.format(date1);
		return strDate1;
	}

	public String getFromToDateUIMessage() {
		eleUtil.waitForText(updateUIMsg, "Site 103 has 7 open days from 03/08/20 to 03/14/20.",
				config.getExplicitWait());
		return eleUtil.doGetText(updateUIMsg);
	}

	public List<String> getTotalTransDateUI() throws Exception {
		CashDrawerSetUp cashDrawerSetup = new CashDrawerSetUp(driver);
		eleUtil.clickWhenReady(transButton, config.getExplicitWait());
		cashDrawerSetup.clickOnLoadMoreRowsButton();
		return eleUtil.getElementsTextList(transDateLov);
	}
	public List<String> gettransDateDB() throws SQLException, Exception {
		List<String>DateDB= new ArrayList<>();
		List<String> Date = Utility.getDataFromDatabase(
				" select tran_dt, site_no from dlysite \r\n" + "where site_no = 103 and \r\n"
						+ "tran_dt > (select last_day_closed_dt from site where site_no = 103) order by tran_dt",
				"TRAN_DT");
		for(int i=0;i<Date.size();i++) {
			String dbDate=Date.get(i);
			//2020-04-01 00:00:00
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dbDate);
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String strDate1 = formatter.format(date1);
			DateDB.add(strDate1);
		}
		return DateDB;
	}

	public boolean IsDateDescriptionPresentUI() {
		return eleUtil.isDisplayed(openDate);
	}

	public EndOfDay verifyDateIsMandatory() {

		eleUtil.doClearTextbox(transDate);
		if (eleUtil.isDisplayed(windowOkButton)) {
			eleUtil.clickWhenReady(windowOkButton, config.getExplicitWait());
		}
		eleUtil.clickWhenReady(runEodButton, config.getExplicitWait());
		return this;
	}

	public String getWindowPopText() {
		return eleUtil.doGetText(windowText);
	}

	public EndOfDay clickOnExitButton() {
		eleUtil.clickWhenReady(exitButton, config.getExplicitWait());
		return this;

	}

}
