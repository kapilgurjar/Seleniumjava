package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class CashDrawerContentsReport extends TestBase{
	
	public ElementUtil eleUtil;
	
	By siteGroupTextField = By.xpath("//button[contains(@id,'SITE_GROUP_lov_btn')]");
	By siteGroupTextBox=By.xpath("//input[@id='P24_SITE_GROUP']");

	// Class constructor
	public CashDrawerContentsReport() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		softAssert = new SoftAssert();
		jsUtil = new JavaScriptUtil(driver);
	}
	
	/**
	 * 
	 * @return Site/Group class attributes 
	 */
	public String getSiteGroupClassValue() {
		return eleUtil.doGetAttribute(siteGroupTextField, "class");
	}
	
	/**
	 * 
	 * @return Site/Group  
	 */
	public boolean getSiteGroupIsClikable() {
		return eleUtil.doIsEnabled(siteGroupTextField);
	}
	
	public String getSiteGroupTextBox() {
		return eleUtil.doGetAttribute(siteGroupTextBox, "class");
	}
	
	

}
