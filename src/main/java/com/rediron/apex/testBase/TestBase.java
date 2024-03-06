package com.rediron.apex.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.rediron.apex.config.ConfigReader;
import com.rediron.apex.pages.AddASiteToDataBase;
import com.rediron.apex.pages.BasePage;
import com.rediron.apex.pages.CashDrawerContentsReport;
import com.rediron.apex.pages.CashDrawerDeclaration;
import com.rediron.apex.pages.CashDrawerReconciliation;
import com.rediron.apex.pages.CashDrawerSetUp;
import com.rediron.apex.pages.CashOnHandReport;
import com.rediron.apex.pages.ConfigByName;
import com.rediron.apex.pages.ConfigurationFlagMaintenance;
import com.rediron.apex.pages.EndOfDay;
import com.rediron.apex.pages.EntityOwnership;
import com.rediron.apex.pages.GeneralSiteConfiguration;
import com.rediron.apex.pages.ItemCriteria;
import com.rediron.apex.pages.Login;
import com.rediron.apex.pages.MenuSelection;
import com.rediron.apex.pages.MoveFunds;
import com.rediron.apex.pages.ProductCode;
import com.rediron.apex.pages.ReconciliationAuditReport;
import com.rediron.apex.pages.SafeFloat;
import com.rediron.apex.pages.SafeReceipt;
import com.rediron.apex.pages.SafeReconciliation;
import com.rediron.apex.pages.SafeWithdrawl;
import com.rediron.apex.pages.SiteInGroups;
import com.rediron.apex.pages.SiteInformation;
import com.rediron.apex.pages.VendorSelection;
import com.rediron.apex.pages.ViewSiteClosing;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.ExcelUtil;
import com.rediron.apex.utils.JavaScriptUtil;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

public class TestBase {
	
	public static ExtentReports extent;
	public static WebDriver driver;
	public static Properties OR;
	public File f1;
	public FileInputStream file;
	public static ExtentTest test;
	public static ConfigReader config;
	public Login loginpage;
	public BasePage basePage;
	public CashDrawerSetUp cashDrawerSetup;
	public CashDrawerDeclaration cashDrawerDeclaration;
	public CashDrawerReconciliation cashDrawerReconciliation;
	public MenuSelection menuSelection;
	public SafeReceipt safeReceipt;
	public SafeWithdrawl safeWithdrawl;
	public SafeFloat safeFloat;
	public ViewSiteClosing viewSiteClosing;
	public CashOnHandReport cashOnHandReport;
	public CashDrawerContentsReport cashDrawerContentsReport;
	public SafeReconciliation safeReconciliation;
	public MoveFunds moveFunds;
	public EndOfDay endOfDay;
	public VendorSelection vendorSelection;
	public ConfigByName configByName;
	public EntityOwnership entityOwnership;
	public ReconciliationAuditReport reconciliationAuditReport;
	public GeneralSiteConfiguration generalSiteConfiguration;
	public SiteInformation siteInformation;
	public SiteInGroups siteInGroups;
	public AddASiteToDataBase addASiteToDataBase;
	public ConfigurationFlagMaintenance configurationFlagMaint;
	public ItemCriteria itemCriteria;
	public ProductCode productCode;
	public ElementUtil eleUtil;
	public JavaScriptUtil jsUtil;
	public SoftAssert softAssert;
	public ExcelUtil reader = new ExcelUtil(System.getProperty("user.dir") + "/src/test/resources/TestData/Data.xlsx");
	public static String siteNameNum=null;
	static {

		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/Reports/Apex_"
				+ formater.format(calender.getTime()) + ".html", false);
			
	}

	public TestBase() {
		try {
			loadPropertiesFile();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void init() throws Exception {
		String browser = Utility.getGlobalValue("Browser");			
			if (browser.equals("headless")) {
				ChromeOptions options = new ChromeOptions();
				options.setHeadless(true);
				driver=WebDriverManager.chromedriver().capabilities(options).create();
				config = new ConfigReader(OR);
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				test.log(LogStatus.INFO, " Using Chrome Driver");
				driver.manage().window().maximize();
				test.log(LogStatus.INFO, " Maximize the window ");
				driver.get(config.getWebsite());
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeOut()));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
			}
			else if (browser.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				config = new ConfigReader(OR);
				options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				test.log(LogStatus.INFO, " Using Chrome Driver");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				driver=WebDriverManager.chromedriver().capabilities(options).create();
				driver.manage().window().maximize();
				test.log(LogStatus.INFO, " Maximize the window ");
				driver.get(config.getWebsite());
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeOut()));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
			} else if (browser.equals("edge")) {
				WebDriverManager.edgedriver().setup();
				config = new ConfigReader(OR);
				driver = new EdgeDriver();
				test.log(LogStatus.INFO, " Using Edge Driver");
				driver.manage().window().maximize();
				test.log(LogStatus.INFO, " Maximize the window ");
				driver.get(config.getWebsite());
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
			} else {
				System.out.println("No suitable browser found");
			}
	}

	public String getScreenShot(String imageName) throws IOException {

		if (imageName.equals("")) {
			imageName = "Apex_";
		}
		File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imagelocation = System.getProperty("user.dir") + "/test-output/ScreenShots/";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imagelocation + imageName + "_" + formater.format(calendar.getTime()) + ".png";
		File destFile = new File(actualImageName);
		FileUtils.copyFile(image, destFile);
		return actualImageName;
	}

	public void getresult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName()+ " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getName() + " test is failed" + result.getThrowable());
			String screen = getScreenShot("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	public void loadPropertiesFile() throws IOException {
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir") + "/src/main/java/com/rediron/apex/config/config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
	}

	@BeforeClass
	public void setup() throws Exception {
		softAssert = new SoftAssert();
		basePage= new BasePage();
		loginpage = new Login(driver);
		cashDrawerSetup = new CashDrawerSetUp(driver);
		cashDrawerDeclaration = new CashDrawerDeclaration();
		cashDrawerReconciliation = new CashDrawerReconciliation();
		menuSelection = new MenuSelection(driver);
		safeReceipt = new SafeReceipt();
		viewSiteClosing= new ViewSiteClosing();
		cashOnHandReport=new CashOnHandReport();
		cashDrawerContentsReport= new CashDrawerContentsReport();
		safeReconciliation= new SafeReconciliation();
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		safeWithdrawl = new SafeWithdrawl();
		safeFloat = new SafeFloat();
		moveFunds= new MoveFunds();
		endOfDay= new EndOfDay();
		vendorSelection = new VendorSelection();
		configByName =new ConfigByName();
		entityOwnership = new EntityOwnership();
		reconciliationAuditReport= new ReconciliationAuditReport();
		itemCriteria= new ItemCriteria();
		siteInGroups=new SiteInGroups();
		generalSiteConfiguration= new GeneralSiteConfiguration();
		siteInformation=  new SiteInformation();
		addASiteToDataBase=new AddASiteToDataBase();
		configurationFlagMaint= new ConfigurationFlagMaintenance();
		productCode=new ProductCode();

	}
	@BeforeMethod()
	public void beforeMethod(Method result) throws Exception {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
		init();
	}
	
	@Step("Stopping the Driver ")
	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		getresult(result);
		driver.quit();
		test.log(LogStatus.INFO, "Stopping the Driver ");
	}

	@AfterClass(alwaysRun = true)
	public void endTest() throws Exception {
		extent.endTest(test);
		extent.flush();
	}
}
