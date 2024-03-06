package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.rediron.apex.utils.ElementUtil2;

public class LoginPageNew {
	
	private WebDriver driver;
	private ElementUtil2 eleUtil;
	
	
	// 2. page constructor
	public LoginPageNew(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil2(driver);
	}
	
	// 3. By locators 
	
	private By userName = By.xpath("//input[@placeholder='Username']");
	private By password = By.xpath("//input[@placeholder='Password']");
	
	
	//4. Page Actions
	
	public void loginToApplication(String un,String psw) {
		eleUtil.doSendKeys(userName, un);
		eleUtil.doSendKeys(password, psw);
		
		
	}
}
