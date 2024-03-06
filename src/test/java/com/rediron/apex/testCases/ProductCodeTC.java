package com.rediron.apex.testCases;

import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;

public class ProductCodeTC extends TestBase{
	
	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);
	
	
	@Test
	public void TC01verifySearchOnProductCode() throws Exception {
		String searchValue = reader.getCellData("ProductCode", 0, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum);
		menuSelection.navigateToProductCode();
		productCode.search(searchValue);
	}

}
