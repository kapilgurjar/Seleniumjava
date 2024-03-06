package com.rediron.apex.config;

import java.util.Properties;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;

public class ConfigReader extends TestBase {

	public Properties OR;

	public ConfigReader(Properties OR) {
		this.OR = OR;

	}

	public String getWebsite() {
		return OR.getProperty("URL");
	}

	public int getPageLoadTimeOut() {
		return Utility.parseToInteger(OR.getProperty("PageLoadTimeOut"));
	}

	public int getImplicitWait() {
		return Utility.parseToInteger(OR.getProperty("ImplcitWait"));
	}

	public int getExplicitWait() {
		return Utility.parseToInteger(OR.getProperty("ExplicitWait"));
	}

	public String getDbdriver() {
		return OR.getProperty("DataBase.driver");
	}

	public String getDbConnStr() {
		return OR.getProperty("DataBase.ConnectionStr");
	}

	public String getBrowser() {
		return OR.getProperty("Browser");
	}
	
	public int getMediumWait() {
		return Utility.parseToInteger(OR.getProperty("MediumWait"));
	}
	
	public int getSmallWait() {
		return Utility.parseToInteger(OR.getProperty("SmallWait"));
	}

}
