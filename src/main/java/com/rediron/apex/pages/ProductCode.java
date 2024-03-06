package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class ProductCode extends TestBase{
	
	By search= By.id("data_rep_ig_toolbar_search_field");
	By goButton=By.xpath("//span[text()='Go']//parent::button");
	
	
	
	public ProductCode() {
		PageFactory.initElements(driver, this);
		eleUtil= new ElementUtil(driver);
		jsUtil=new JavaScriptUtil(driver);
		menuSelection= new MenuSelection(driver);
	}
	
	public ProductCode search(String searchValue) {
		eleUtil.doSendKeysWithWait(search, config.getExplicitWait(), searchValue);
		eleUtil.clickWhenReady(goButton, config.getExplicitWait());
		return this;
	}

}
