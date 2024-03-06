package com.rediron.apex.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.Utility;

import io.qameta.allure.Step;

public class BasePage extends TestBase {

	// By
	// popUpWindow=By.xpath("//div[starts-with(@class,'ui-dialog-buttonset')]//button");
	By popUpWindow = By.xpath("//div[starts-with(@id,'ui-id')]//p");
	

	public BasePage() {
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
	}

	public BasePage handleSafeReconWindow(String siteNo) throws Exception {

		if (eleUtil.isDisplayed(popUpWindow)) {

			Utility.executeandcommit("declare\r\n" + "ret Varchar2(10);\r\n" + "begin\r\n"
					+ "ret := tmxgbl.setsite_flag('SAFE_RECON_IN_PROCESS','" + siteNo + "','N');\r\n" + "end;");
			driver.navigate().refresh();
		} else {
			System.out.println("pop window is not visible");
		}

		return this;

	}

	/**
	 * @author Kapil
	 * @return table data
	 * @param tablelocator, nextButton
	 * 
	 */
	@Step("This will return table data and click on the next button until it is not get disable")
	public List<String> getTableDataAndClickOnNextButton(By tablelocator, By nextButton) throws InterruptedException {

		List<String> tableData = new ArrayList<>();
		List<String> firtsPageData = eleUtil.getElementsTextList(tablelocator);
		for (String page1Data : firtsPageData) {
			tableData.add(page1Data);
		}

		while (eleUtil.isDisplayed(nextButton)) {
			eleUtil.clickWhenReady(nextButton, config.getExplicitWait());
			Thread.sleep(3000);
			List<String> otherPageData = eleUtil.getElementsTextList(tablelocator);
			for (String otherData : otherPageData) {
				tableData.add(otherData);
			}
		}
		return tableData;
	}
	
	/**
	 * @author Kapil
	 * @return table data
	 * @param tablelocator, nextButton
	 * 
	 */
	@Step("This will return table data and click on the next button until it is not get disable")
	public List<String> getTableDataAndClickOnNextButtonIfEnable(By tablelocator, By nextButton) throws InterruptedException {

		List<String> tableData = new ArrayList<>();
		List<String> firtsPageData = eleUtil.getElementsTextList(tablelocator);
		for (String page1Data : firtsPageData) {
			tableData.add(page1Data);
		}

		while (eleUtil.doIsEnabled(nextButton)) {
			eleUtil.clickWhenReady(nextButton, config.getExplicitWait());
			Thread.sleep(3000);
			List<String> otherPageData = eleUtil.getElementsTextList(tablelocator);
			for (String otherData : otherPageData) {
				tableData.add(otherData);
			}
		}
		return tableData;
	}

	
	@Step("Getting names and description from UI in key and value pair")
	public HashMap<String, String> getConfigByNameFlagDatesNumberAndTextData(By pageSelector,By tablecoulunName,By nextButton,By tableColumnDesc) throws Exception {
		HashMap<String, String> nameDes = new HashMap<>();
		//basePage = new BasePage();
		eleUtil.doSelectLastOptionfromDropDown(pageSelector);
		eleUtil.doStaticWait(config.getMediumWait());
		List<String> names = getTableDataAndClickOnNextButtonIfEnable(tablecoulunName, nextButton);
		Collections.sort(names);
		List<String> descriptions =getTableDataAndClickOnNextButtonIfEnable(tableColumnDesc, nextButton);
		Collections.sort(descriptions);
		System.out.println(descriptions.size());
		String name = "";
		String des = "";
		for (int i = 0; i < names.size(); i++) {
			name = names.get(i).trim().replaceAll("\\s", "");
			for (int j = 0; j <= i; j++) {
				des = descriptions.get(j).trim().replaceAll("\\s", "");
				nameDes.put(name, des);
			}
		}
		return nameDes;

	}
	
	
	// Clicking on the load more button
		public void clickOnLoadMoreRowsButton(By locator) throws Exception {

			boolean buttonStatus = eleUtil.doIsEnabled(locator);
			System.out.println(buttonStatus);
			while (buttonStatus) {
				try {
					eleUtil.doStaticWait(config.getMediumWait());
					eleUtil.doClickWithWait(locator, config.getExplicitWait());
				} catch (Exception e) {
					e.getStackTrace();
					break;
				}
			}
			
		}

}
