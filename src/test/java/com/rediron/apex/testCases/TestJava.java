package com.rediron.apex.testCases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestJava {
	
	@Test
	public void tc01() throws MalformedURLException {
	
	ChromeOptions chromeOptions = new ChromeOptions();
	chromeOptions.setCapability("browserVersion", "122.0");
	chromeOptions.setCapability("platformName", "Windows");
	// Showing a test name instead of the session id in the Grid UI
	chromeOptions.setCapability("se:name", "My simple test"); 
	// Other type of metadata can be seen in the Grid UI by clicking on the 
	// session info or via GraphQL
	chromeOptions.setCapability("se:sampleMetadata", "Sample metadata value"); 
	WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
	driver.get("http://www.google.com");
	driver.quit();
	
	}

}
