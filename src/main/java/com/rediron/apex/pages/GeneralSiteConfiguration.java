package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Step;

public class GeneralSiteConfiguration extends TestBase {

	By siteNo = By.xpath("//input[contains(@id,'_SITE_NO') and @type='text']");
	By exitButton = By.xpath("//span[text()='Exit']//parent::button");
	By saveButton = By.cssSelector("button#SAVE_CHANGES");
	By clearButton = By.xpath("//span[text()='Clear']//parent::button");
	By popUpAlertMsg = By.id("paraId");
	By skuRequired = By.xpath("//input[contains(@id,'_PC_SKU_REQUIRED') and @type='checkbox']");
	By depGroup = By.xpath("//input[contains(@id,'PC_DEPTGRP_REQUIRED') and @type='checkbox']");
	By dep = By.xpath("//input[contains(@id,'_PC_DEPT_REQUIRED') and @type='checkbox']");
	By clasSS = By.xpath("//input[contains(@id,'_PC_CLASS_REQUIRED') and @type='checkbox']");
	By line = By.xpath("//input[contains(@id,'_PC_LINE_REQUIRED') and @type='checkbox']");
	By skuCheckBox = By.xpath("//label[contains(@id,'PC_SKU_REQUIRED_LABEL')]");
	By deptGroupCheckBox = By.xpath("//label[contains(@id,'_PC_DEPTGRP_REQUIRED_LABEL')]");
	By deptCheckBox = By.xpath("//label[contains(@id,'_PC_DEPT_REQUIRED_LABEL')]");
	By alertPopNoButton = By.id("No");
	By alertPopYesButton = By.id("Yes");
	By alertPopCancelButton = By.id("Cancel");
	By saveMessage = By.xpath("//h2[text()='Changes saved Successfully']");

	public GeneralSiteConfiguration() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
		menuSelection = new MenuSelection(driver);
	}

	@Step("Get site no class attribute")
	public String getSiteNoAttributeValue() {
		return eleUtil.doGetAttribute(siteNo, "class");
	}

	@Step("Click on exit button")
	public GeneralSiteConfiguration clickOnExitButton() {
		eleUtil.doClick(exitButton);
		return this;
	}

	@Step("Is user on menu page")
	public boolean IsExpanAllButtonDisplayed() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.isDisplayed(menuSelection.expandAllButton);
	}

	@Step("Get Save button class attribute")
	public String getSaveButtonClassAttribute() {
		return eleUtil.doGetAttribute(saveButton, "class");
	}

	@Step("Verify sku check box is checked or not")
	public GeneralSiteConfiguration IsSkuRequiredCheckBoxChecked() {
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.doIsEnabled(skuRequired);
		if (sts) {
			eleUtil.doStaticWait(config.getMediumWait());
			eleUtil.clickWhenReady(skuCheckBox, config.getExplicitWait());
		} else {
			eleUtil.doStaticWait(config.getMediumWait());
			eleUtil.clickWhenReady(deptGroupCheckBox, config.getExplicitWait());
			eleUtil.clickWhenReady(deptCheckBox, config.getExplicitWait());
		}

		return this;
	}

	@Step("Verify sku check box is checked or not")
	public boolean getDepGroupDepClassAndLineCheckBoxButtonStatus() {
		eleUtil.doStaticWait(config.getMediumWait());
		boolean sts = eleUtil.doIsEnabled(skuRequired);
		if (sts) {
			test.log(LogStatus.INFO, "Sku check box is enable");
		} else {
			eleUtil.doStaticWait(config.getMediumWait());
			eleUtil.clickWhenReady(deptGroupCheckBox, config.getExplicitWait());
			eleUtil.clickWhenReady(deptCheckBox, config.getExplicitWait());
		}
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doIsEnabled(depGroup) && eleUtil.doIsEnabled(dep) && eleUtil.doIsEnabled(clasSS)
				&& eleUtil.doIsEnabled(line);

	}

	@Step("Get Sku checkBox status ")
	public boolean getSkuCheckBoxStatus() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.doIsEnabled(skuRequired);

	}

	@Step("Click on clear button")
	public GeneralSiteConfiguration clickOnClearButton() {
		eleUtil.clickWhenReady(clearButton, config.getExplicitWait());
		return this;
	}

	@Step("Get Alert pop text message")
	public String getAlertPopMsg() {
		return eleUtil.doGetText(popUpAlertMsg);
	}

	@Step("Click on clear button")
	public GeneralSiteConfiguration clickOnClearAlertPOpUpNoButton() {
		eleUtil.clickWhenReady(alertPopNoButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on clear button")
	public GeneralSiteConfiguration clickOnClearAlertPOpUpYesButton() {
		eleUtil.clickWhenReady(alertPopYesButton, config.getExplicitWait());
		return this;
	}

	@Step("Click on clear button")
	public GeneralSiteConfiguration clickClearAlertPOpUpCancelButton() {
		eleUtil.clickWhenReady(alertPopCancelButton, config.getExplicitWait());
		return this;
	}

	public boolean isSaveMessageDisplayed() {
		eleUtil.doStaticWait(config.getMediumWait());
		return eleUtil.isDisplayed(saveMessage);
	}

}
