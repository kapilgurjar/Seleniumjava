package com.rediron.apex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rediron.apex.factory.DriverFactory;
import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.ElementUtil;
import com.rediron.apex.utils.JavaScriptUtil;

public class MenuSelection {
	private ElementUtil eleUtil;
	private JavaScriptUtil jsUtil;
	private WebDriver driver;

	String xpath = "108";
	public By dailyOperations = By.xpath("//h3[text()='Daily Operations']");
	By fileMaintenance = By.xpath("//h3[text()='File Maintenance']//parent::div//parent::a");
	By managerUtilities = By.xpath("//h3[contains(text(),'Manager')]");
	public By expandAllButton = By.xpath("//span[contains(text(),'Expand All')]//parent::button");
	By cashDrawerSetup = By.xpath("//a[text()='Cash Drawer Setup']");
	By cashDrawerDeclaration = By.xpath("//a[text()='Cash Drawer Declaration']");
	By cashDrawerReconciliation = By.xpath("//a[text()='Cash Drawer Reconciliation']");
	By currentSite = By.xpath("//div[3]/ul/li[2]/a");
	By selectSite = By.xpath("//input[contains(@id,'_SITE') and @type='text']");
	By selectSiteName = By.xpath("//tr[@data-id='" + xpath + "')]//td");
	By saveButton = By.xpath("//*[@class=\"t-Button t-Button--hot \"]");
	By siteSearch = By.xpath("//input[@aria-label='Search']");
	By safeReceipt = By.xpath("//a[text()='Safe Receipt']");
	By safeWithdrawal = By.xpath("//a[text()='Bank Deposit']");
	By safeFloat = By.xpath("//a[text()='Safe Float']");
	By siteNamefilled = By.xpath("//input[contains(@id,'SITE') and @type='text']");
	By viewSiteClosing = By.xpath("//a[text()='View Site Closing']");
	By cashOnHandReport = By.xpath("//a[text()='Cash on Hand Report']");
	By cashDrawerContentsReport = By.xpath("//a[text()='Cash Drawer Contents Report']");
	By safeReconciliation = By.xpath("//a[text()='Safe Reconciliation']");
	By moveFunds = By.xpath("//li[contains(@id,'_tree')]//a[text()='Move Funds']");
	By endOfDay = By.xpath("//a[text()='End of Day']");
	By itemCriteria = By.xpath("//a[text()='Item Criteria']");
	By editThisCriteria = By.cssSelector("button#itm_criteria_ig_toolbar_editcriteria");
	By itemLov = By.cssSelector("td>button#itmlov");
	By vendorIdSearch = By.cssSelector("button[title='Vendor ID Search']");
	By itemTab = By.cssSelector("li#Item_tab");
	By vendorId = By.cssSelector("input[id*='P30_VENDOR_ID']");
	By configByName = By.xpath("//a[text()='Configuration by Name']");
	By entityOwnership = By.xpath("//a[text()='Entity Ownership']");
	By recAuditReport = By.xpath("//a[text()='Reconciliation Audit Report']");
	By siteInGroups = By.xpath("//a[text()='Site In Groups']");
	By generalSiteConfiguration=By.xpath("//li[contains(@id,'_tree_7')]//a[text()='General Site Configuration']");
	By siteInformation=By.xpath("//li[contains(@id,'_tree_3')]//a[text()='Sites']");
	By addASiteToDataBase=By.xpath("//li[contains(@id,'_tree_4')]//a[text()='Add a Site to Database']");
	By configurationFlagMaint=By.xpath("//a[text()='Configuration Flag Maintenance' and @role='treeitem' ]");
	By productCode=By.xpath("(//li[@id='nav_tree_tree_0']//a[text()='Product Code'])[2]");

	// Initializing the Page Objects:
	public MenuSelection(WebDriver driver) {

		//PageFactory.initElements(driver, this);
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	// Actions
	public MenuSelection selectCurrentSite(String siteNum) throws Exception {
		eleUtil.doClick(currentSite);
		eleUtil.switchToFrameByIndex(0);
		eleUtil.doClick(selectSite);
		eleUtil.switchToDefaultContent();
		eleUtil.doSendKeys(siteSearch, siteNum);
		//eleUtil.doStaticWait(config.getSmallWait());
		Thread.sleep(2000);
		eleUtil.doActionMoveClick(By.xpath("//tr[@data-id='" + siteNum + "']//td"));
		eleUtil.switchToFrameByIndex(0);
		String siteNameNum = eleUtil.doGetAttribute(siteNamefilled, "value");
		//eleUtil.doStaticWait(config.getSmallWait());
		Thread.sleep(2000);
		System.out.println(siteNameNum);
		eleUtil.clickWhenReady(saveButton, DriverFactory.timeOut);
		eleUtil.switchToDefaultContent();
		return this;

	}

	public MenuSelection clickOnManagerOperations() throws Exception {
		Thread.sleep(3000);
		eleUtil.doClick(dailyOperations);
		return this;
	}

	public MenuSelection clickOnExpandAllButton() {
		eleUtil.doClickWithWait(expandAllButton, DriverFactory.timeOut);
		return this;
	}

	public boolean IsExpanButtonDisplayed() {
		return eleUtil.isDisplayed(expandAllButton);

	}

	public CashDrawerSetUp navigateToCashDrawerSetup() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClickWithWait(cashDrawerSetup,DriverFactory.timeOut);
		return new CashDrawerSetUp(driver);
	}

	public CashDrawerSetUp clickOncashDrawerSetup() {
		eleUtil.doActionMoveClick(cashDrawerSetup);
		return new CashDrawerSetUp(driver);
	}

	public void clickOncashDrawerDecalaration() {

		eleUtil.doClick(cashDrawerDeclaration);
	}

	public void clickOnCashDrawerReconciliation() {

		eleUtil.doActionMoveClick(cashDrawerReconciliation);
	}

	public void clickOnFilefileMaintenance() throws Exception {
		eleUtil.clickIgnoreStaleElementException(fileMaintenance,DriverFactory.timeOut);
		Thread.sleep(3000);
		jsUtil.doJsClick(fileMaintenance);
	}

	public SafeReceipt navigateToSafeReceipt() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClick(safeReceipt);
		return new SafeReceipt();
	}
	
	public SafeReceipt clickOnSafeReceipt() {
		eleUtil.doActionMoveClick(safeReceipt);
		return new SafeReceipt();
	}

	public void clickOnSafeWithdrawal() {

		eleUtil.doClick(safeWithdrawal);
	}

	public void clickOnSafeFloat() {
		eleUtil.doClick(safeFloat);
	}

	public ViewSiteClosing navigateTOSiteClosing() throws Exception {
		clickOnExpandAllButton();
		Thread.sleep(2000);
		eleUtil.doActionMoveClick(viewSiteClosing);
		return new ViewSiteClosing();
	}

	public CashOnHandReport navigateTOCashOnHandReport() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClick(cashOnHandReport);
		return new CashOnHandReport();
	}

	public CashDrawerContentsReport navigateTOCashDrawerContentsReport() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClick(cashDrawerContentsReport);
		return new CashDrawerContentsReport();
	}

	public SafeReconciliation navigateTOReconciliation() throws Exception {
		eleUtil.doClick(safeReconciliation);
		return new SafeReconciliation();
	}
	
	public SafeReconciliation clickOnSafeReconciliation() throws Exception {
		eleUtil.doActionMoveClick(safeReconciliation);
		return new SafeReconciliation();
	}

	public MoveFunds navigateToMoveFunds() throws Exception {
		clickOnManagerOperations();
		Thread.sleep(2000);
		clickOnExpandAllButton();
		eleUtil.doClick(moveFunds);
		return new MoveFunds();

	}

	public EndOfDay navigateToEndOfDayScreen() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClick(endOfDay);
		return new EndOfDay();
	}

	public ItemCriteria navigateToItemCriteriaScreen() throws Exception {
		clickOnFilefileMaintenance();
		clickOnExpandAllButton();
		eleUtil.doClick(itemCriteria);
		return new ItemCriteria();
	}

	public VendorSelection navigateToVenderSelectionScreen(String Criteria) throws Exception {
		navigateToItemCriteriaScreen();
		By xpath = By.xpath("//td[text()='" + Criteria + "']");
		eleUtil.clickWhenReady(xpath, DriverFactory.timeOut);
		eleUtil.clickWhenReady(editThisCriteria, DriverFactory.timeOut);
		eleUtil.clickWhenReady(itemTab, DriverFactory.timeOut);
		eleUtil.clickWhenReady(itemLov, DriverFactory.timeOut);
		eleUtil.clickWhenReady(vendorIdSearch, DriverFactory.timeOut);
		return new VendorSelection();
	}

	public VendorSelection navigateToVenderSelectionScreenWithVendorID(String criteria, String vendor)
			throws Exception {
		navigateToItemCriteriaScreen();
		By xpath = By.xpath("//td[text()='" + criteria + "']");
		eleUtil.clickWhenReady(xpath, DriverFactory.timeOut);
		eleUtil.clickWhenReady(editThisCriteria, DriverFactory.timeOut);
		eleUtil.clickWhenReady(itemTab, DriverFactory.timeOut);
		eleUtil.clickWhenReady(itemLov, DriverFactory.timeOut);
		eleUtil.doSendKeys(vendorId, vendor);
		eleUtil.clickWhenReady(vendorIdSearch, DriverFactory.timeOut);
		return new VendorSelection();
	}

	public ConfigByName navigateToConfigByNameScreen() throws Exception {
		eleUtil.waitForText(managerUtilities, "Manager's Utilities", DriverFactory.timeOut);
		eleUtil.doStaticWait(2000);
		eleUtil.clickWhenReady(managerUtilities, DriverFactory.timeOut);
		clickOnExpandAllButton();
		eleUtil.clickWhenReady(configByName, DriverFactory.timeOut);
		return new ConfigByName();
	}

	public EntityOwnership navigateToEntityOwnershipScreen() throws Exception {
		eleUtil.waitForText(managerUtilities, "Manager's Utilities", DriverFactory.timeOut);
		eleUtil.doStaticWait(2000);
		eleUtil.clickWhenReady(managerUtilities, DriverFactory.timeOut);
		clickOnExpandAllButton();
		eleUtil.clickWhenReady(entityOwnership, DriverFactory.timeOut);
		return new EntityOwnership();
	}

	public ReconciliationAuditReport navigateToReconciliationAuditReportScreen() throws Exception {
		clickOnManagerOperations();
		clickOnExpandAllButton();
		eleUtil.doClick(recAuditReport);
		return new ReconciliationAuditReport();
	}

	public ReconciliationAuditReport clickOnRecAuditReport() {
		eleUtil.clickWhenReady(recAuditReport, DriverFactory.timeOut);
		return new ReconciliationAuditReport();
	}

	public SiteInGroups navigateToSiteInGroupsScreen() throws Exception {
		eleUtil.doStaticWait(2000);
		clickOnFilefileMaintenance();
		clickOnExpandAllButton();
		eleUtil.clickWhenReady(siteInGroups, DriverFactory.timeOut);
		return new SiteInGroups();
	}

	public MenuSelection clickOnSiteInGroups() {
		eleUtil.clickWhenReady(siteInGroups, DriverFactory.timeOut);
		return this;
	}
	
	public MenuSelection clickOnManageUtilities() {
		eleUtil.doStaticWait(2000);
		eleUtil.clickWhenReady(managerUtilities, DriverFactory.timeOut);
		return this;
	}
	
	public GeneralSiteConfiguration navigateToGeneralSiteConfiguration() {
		eleUtil.clickWhenReady(generalSiteConfiguration, DriverFactory.timeOut);
		return new GeneralSiteConfiguration();
	}
	
	public SiteInformation navigateToSiteInformationScreen() {
		eleUtil.clickWhenReady(siteInformation, DriverFactory.timeOut);
		return new SiteInformation();
	}
	
	public AddASiteToDataBase navigateToAddSiteToDataBaseScreen() {
		eleUtil.clickWhenReady(addASiteToDataBase, DriverFactory.timeOut);
		return new AddASiteToDataBase();
	}
	
	public ConfigurationFlagMaintenance navigateToConfigurationFlagMaintenance() {
		eleUtil.clickWhenReady(configurationFlagMaint, DriverFactory.timeOut);
		return new ConfigurationFlagMaintenance();
	}
	
	public ProductCode navigateToProductCode() throws Exception {
		eleUtil.doStaticWait(2000);
		clickOnFilefileMaintenance();
		eleUtil.clickWhenReady(productCode, DriverFactory.timeOut);
		return new ProductCode();
	}
	
	

}
