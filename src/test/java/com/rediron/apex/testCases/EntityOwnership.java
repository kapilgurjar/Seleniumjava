package com.rediron.apex.testCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class EntityOwnership extends TestBase {

	String userName = reader.getCellData("Login", 0, 2);
	String password = reader.getCellData("Login", 1, 2);
	String siteNum = reader.getCellData("MenuSelection", 0, 2);

	@Story("Entity Ownership")
	@Description("Remote sites cannot access the page."
			+ "Message This form is authorized only for Headquarters. Should show when log in to remote site.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, enabled = true)
	public void TC01verifyNoAccessToRemoteSite() throws Exception {
		String expText = reader.getCellData("ENTITYOWNERSHIP", 0, 2);
		test.log(LogStatus.INFO, "Exp window pop text is equal to " + expText);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(siteNum).navigateToEntityOwnershipScreen();
		String actText = entityOwnership.getWindowPopText();
		test.log(LogStatus.INFO, "Act window pop text is equal to " + actText);
		Assert.assertEquals(actText, expText);
		Assert.assertTrue(entityOwnership.IsUserOnMenuPage());
	}

	@Story("Entity Ownership")
	@Description("Corporate sites/Affiliate sites count to be shown.")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, enabled = true)
	public void TC02verifyOnPageloadtotalsofcorporatesiteAffiliatesites() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen();
		String totalSiteCountUI = entityOwnership.getTotalSiteCount();
		test.log(LogStatus.INFO, "TOtal UI site count is equal to " + totalSiteCountUI);
		String totalSiteCountDB = Utility.executeQuery("Select count(1) from site where site_active = 'Y'", "COUNT(1)");
		test.log(LogStatus.INFO, "TOtal DB site count is equal to " + totalSiteCountDB);
		Assert.assertEquals(totalSiteCountUI, totalSiteCountDB);
		// Verify CorpSite
		String totalCorpSiteCountUI = entityOwnership.getTotalCorpSiteCount();
		test.log(LogStatus.INFO, "TOtal UI Corpsite count is equal to " + totalCorpSiteCountUI);
		String totalCorpSiteCountDB = Utility.executeQuery(
				"select count(1)  from site \r\n" + "where owner_id = 1 and site_active = 'Y'", "COUNT(1)");
		test.log(LogStatus.INFO, "TOtal DB Corpsite count is equal to " + totalCorpSiteCountDB);
		Assert.assertEquals(totalCorpSiteCountUI, totalCorpSiteCountDB);
		// Verify Affiliated site
		String totalAffSiteCountUI = entityOwnership.getTotalAffSiteCount();
		test.log(LogStatus.INFO, "TOtal UI Affsite count is equal to " + totalAffSiteCountUI);
		String totalAffSiteCountDB = Utility.executeQuery(
				"select count(1)  from site \r\n" + "where owner_id <> 1 and site_active = 'Y'", "COUNT(1)");
		test.log(LogStatus.INFO, "TOtal DB Affsite count is equal to " + totalAffSiteCountDB);
		Assert.assertEquals(totalAffSiteCountUI, totalAffSiteCountDB);
	}

	@Story("Entity Ownership")
	@Description("Lists the currency code")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, enabled = true)
	public void TC03verifyCurrencyCode() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		List<String> currencyCodeLst = entityOwnership.getCurrencyCode();
		test.log(LogStatus.INFO, "currency code list" + currencyCodeLst);
		String currencyCodeACT = currencyCodeLst.get(1);
		test.log(LogStatus.INFO, "currency code value " + currencyCodeACT);
		String currencyCodeEXp = Utility.executeQuery(
				"select f.name name, f.name name1 from forgncur f where type = 'X' and  site_no = 1 order by f.name",
				"NAME");
		Assert.assertEquals(currencyCodeACT, currencyCodeEXp);
	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, enabled = true)
	public void TC05verifyEntityIDInCorporateSitesCorporateDetails() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		Assert.assertEquals(entityOwnership.getEntityIdCorpDetails(), "1");
		Assert.assertTrue(entityOwnership.IsEntityIDReadOnly());

	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, enabled = true)
	public void TC06verifyEntityOwnerInCorporateSitesCorporateDetails() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		Assert.assertTrue(entityOwnership.IsEntityOwnerEditable());
		System.out.println("is editable");
		Assert.assertTrue(entityOwnership.changeCorpValueToActualValue("Corporate"));
	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 7, enabled = true)
	public void TC07verifyNoOfSitesInCorporateSitesCorporateDetails() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String query = "select  s.site_no, full_name \r\n" + "from site s where s.owner_id=1 \r\n" + "order by 1";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		String noOfSiteUI = entityOwnership.getNoOfSiteCorpHQ();
		test.log(LogStatus.INFO, "No Of Sites From UI" + noOfSiteUI);
		int noOfSiteDB = Utility.getDataFromDatabase(query, "SITE_NO").size();
		test.log(LogStatus.INFO, "No Of Sites From DB" + noOfSiteDB);
		Assert.assertEquals(Integer.parseInt(noOfSiteUI), noOfSiteDB);

	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 8, enabled = true)
	public void TC08verifySiteNoListOnViewCorpHQSite() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String query = "select  s.site_no, full_name \r\n" + "from site s where s.owner_id=1 \r\n" + "order by 1";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		List<String> siteNoDB = Utility.getDataFromDatabase(query, "SITE_NO");
		List<String> siteNoUI = entityOwnership.getTableSiteNoList();
		boolean sts = CollectionUtils.isEqualCollection(siteNoUI, siteNoDB);
		Assert.assertTrue(sts);
	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 9, enabled = true)
	public void TC09verifySearchOnViewCorpHQDetailsPage() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String expText = "SAN CARLOS";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton()
				.searchCorporateSitesInTable(expText);
		boolean sts = entityOwnership.getCorpSiteTableData(expText);
		if (sts) {
			test.log(LogStatus.PASS, "Exp text " + expText + " is present in table and status is equal to " + sts + "");
		} else {

			test.log(LogStatus.FAIL,
					"Exp text " + expText + " is not present in table and status is equal to " + sts + "");
			softAssert.assertTrue(false, "not verified");
		}
		softAssert.assertAll();

	}

	@Story("Entity Ownership")
	@Description("Verify Table header on corpHQ table")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 10, enabled = true)
	public void TC10verifyTableHeadersOnCorpHQDetailsPage() throws Exception {
		List<String> expHeaders = new ArrayList<>();
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		int count = reader.getRowCount("ENTITYOWNERSHIP");
		for (int i = 2; i <= count; i++) {
			String headerEX = reader.getCellData("ENTITYOWNERSHIP", 2, i);
			if (!headerEX.isEmpty()) {
				expHeaders.add(headerEX);
			}
		}
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewCorporateSiteButton();
		List<String> tbHeaders = entityOwnership.verifyTableHeader();
		System.out.println(expHeaders);
		Assert.assertEquals(tbHeaders, expHeaders);
	}

	@Story("Entity Ownership")
	@Description("Verify save button on corpHQ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 11, enabled = true)
	public void TC11verifySaveButtonOnViewCoprpHQ() throws Exception {
		String extRefId = "test" + Utility.getRandomString();
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String expMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 3);
		String expMessage1 = reader.getCellData("ENTITYOWNERSHIP", 0, 4);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String actMessage = menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen()
				.clickOnViewCorporateSiteButton().enterDataExternalRefID(extRefId).clickOnSaveButton()
				.getWindowPopText();
		Assert.assertEquals(actMessage.trim(), expMessage.trim());
		eleUtil.pageRefresh();
		entityOwnership.clickOnViewCorporateSiteButton().enterDataExternalRefID("Test").clickOnSaveButton();
		eleUtil.pageRefresh();
		String actMessage1 = entityOwnership.clickOnViewCorporateSiteButton().clickOnSaveButton().getWindowPopText();
		Assert.assertEquals(actMessage1.trim(), expMessage1.trim());
	}

	@Story("Entity Ownership")
	@Description("Verify save button on corpHQ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 12, enabled = true)
	public void TC12verifyCancelButtonOnViewCoprpHQ() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String expMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 5).trim();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String actMessage = menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen()
				.clickOnViewCorporateSiteButton().enterDataExternalRefID("65325").clickOnCancelButton()
				.getWindowPopText();
		Assert.assertEquals(actMessage.trim(), expMessage);

	}

	@Story("Entity Ownership")
	@Description("Verify save button on corpHQ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 13, enabled = true)
	public void TC13verifyVeiwSelectedEntityData() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen();
		List<String> entityListExp = entityOwnership.getEntityOwnerShipTableFirstRowData();
		int idxex = entityListExp.indexOf("E2");
		entityListExp.remove(idxex);
		test.log(LogStatus.INFO, "Entity expected data is =" + entityListExp);
		List<String> entityListACT = entityOwnership.getAllEntityDetails();
		test.log(LogStatus.INFO, "Entity actual data is =" + entityListACT);
		boolean sts = CollectionUtils.isEqualCollection(entityListACT, entityListExp);
		Assert.assertTrue(sts);
	}

	@Story("Entity Ownership")
	@Description("Verify That entity id is not editable")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 14, enabled = true)
	public void TC14verifyThatEntityIdIsReadOnlyOnViewSelectedEntity() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewSelectedEntityButton();
		Assert.assertTrue(entityOwnership.IsEntityIDReadOnly());
	}

	@Story("Entity Ownership")
	@Description("Verify That entity owner is editable text field")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 15, enabled = true)
	public void TC15verifyThatEntityOwnersTextFieldIsEditable() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String entityOwner = reader.getCellData("ENTITYOWNERSHIP", 3, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewSelectedEntityButton();
		boolean sts = entityOwnership.IsEntityOwnerEditableOnViewSelectedEntityPage(entityOwner);
		if (sts) {
			test.log(LogStatus.PASS, "Entity Ownership value is chaged to some othe value");
		} else {
			test.log(LogStatus.FAIL, "Entity Ownership value is not chaged to some othe value");
		}
		Assert.assertTrue(entityOwnership.changeEntityOwnerValueToActualValue(entityOwner, entityOwner));
	}

	@Story("Entity Ownership")
	@Description("Verify That entity owner is editable text field")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 16, enabled = true)
	public void TC16verifyThatEntityNameTextFieldIsEditable() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String entityName = reader.getCellData("ENTITYOWNERSHIP", 4, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen();
		boolean sts = entityOwnership.IsEntityNameTextFieldEditable();
		if (sts) {
			test.log(LogStatus.PASS, "Entity name text field is editable");
		} else {
			test.log(LogStatus.PASS, "Entity name text field is not editable");
			softAssert.assertTrue(sts, "Not verified");
		}
		softAssert.assertAll();
		entityOwnership.changeEntityNameToActualValue(entityName);
	}

	@Story("Entity Ownership")
	@Description("Verify That HQ is editable text field")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 17, enabled = true)
	public void TC17verifyThatHQTextFieldIsEditable() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String newCorpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 3);
		String corpHQRev = reader.getCellData("ENTITYOWNERSHIP", 1, 4);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen()
				.changeHQSiteOnViewSelectEntity(newCorpHQ);
		String hQNewValue = entityOwnership.getEntityHQSite();
		Assert.assertEquals(hQNewValue, newCorpHQ);
		entityOwnership.changeHQSiteOnViewSelectEntity(corpHQRev);
		String hQNewValueRev = entityOwnership.getEntityHQSite();
		Assert.assertEquals(hQNewValueRev, corpHQRev);
	}

	@Story("Entity Ownership")
	@Description("Verify That Ext Ref Id is editable text field")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 18, enabled = true)
	public void TC18verifyThatExtRefTextFieldIsEditable() throws Exception {
		Random rn = new Random();
		int extRef = rn.nextInt(9999);
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen();
		String actextRefIdBefore = entityOwnership.getFirstExtRefTextFromTable();
		test.log(LogStatus.INFO, "Before edit external ref id value is " + actextRefIdBefore);
		entityOwnership.clickOnViewSelectedEntityButton().enterDataExternalRefID(String.valueOf(extRef))
				.clickOnSaveButton().clickOnOkButton();
		eleUtil.doStaticWait(config.getMediumWait());
		String actextRefIdAfter = entityOwnership.getFirstExtRefTextFromTable();
		test.log(LogStatus.INFO, "After edit external ref id value is " + actextRefIdAfter);
		if (!actextRefIdBefore.equalsIgnoreCase(actextRefIdAfter)) {
			test.log(LogStatus.PASS, "Ext ref id text field is editable");
		} else {
			test.log(LogStatus.FAIL, "Ext ref id text field is not editable");
			softAssert.assertTrue(false, "not verifield");
		}
		softAssert.assertAll();

		entityOwnership.clickOnViewSelectedEntityButton().enterDataExternalRefID(String.valueOf(actextRefIdBefore))
				.clickOnSaveButton().clickOnOkButton();
		if (actextRefIdBefore.equalsIgnoreCase(entityOwnership.getFirstExtRefTextFromTable())) {
			test.log(LogStatus.PASS, "Ext ref id value is changed to previous value");
		} else {
			test.log(LogStatus.FAIL, "Ext ref id is not changed to previous value");
			softAssert.assertTrue(false, "not verifield");
		}
		softAssert.assertAll();
	}

	@Story("Entity Ownership")
	@Description("Verify save button on view selected entity")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 20, enabled = true)
	public void TC20verifySaveButtonOnViewSelectedEntity() throws Exception {
		Random rn = new Random();
		int extRef = rn.nextInt(9999);
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String expMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 3).trim();
		String expMessage1 = reader.getCellData("ENTITYOWNERSHIP", 0, 4).trim();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewSelectedEntityButton()
				.enterDataExternalRefID(String.valueOf(extRef)).clickOnSaveButton();
		String actMessage = entityOwnership.getWindowPopText().trim();
		Assert.assertEquals(actMessage, expMessage);
		eleUtil.pageRefresh();
		entityOwnership.clickOnViewCorporateSiteButton().clickOnSaveButton();
		String actMessage1 = entityOwnership.getWindowPopText().trim();
		Assert.assertEquals(actMessage1, expMessage1);
	}

	@Story("Entity Ownership")
	@Description("Verify cancel button on view selected entity")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 21, enabled = true)
	public void TC21verifyCancelButtonOnViewCoprpHQ() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String expMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 5);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		String actMessage = menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen()
				.clickOnViewSelectedEntityButton().enterDataExternalRefID("test").clickOnCancelButton()
				.getWindowPopText();
		Assert.assertEquals(actMessage, expMessage);

	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 22, enabled = true)
	public void TC22verifyNoOfSitesInViewSelectedEntity() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String query = "select  s.site_no, full_name \r\n" + "from site s where s.owner_id=2 \r\n" + "order by 1";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewSelectedEntityButton();
		String noOfSiteUI = entityOwnership.getNoOfSiteCorpHQ();
		test.log(LogStatus.INFO, "No Of Sites From UI" + noOfSiteUI);
		int noOfSiteDB = Utility.getDataFromDatabase(query, "SITE_NO").size();
		test.log(LogStatus.INFO, "No Of Sites From DB" + noOfSiteDB);
		Assert.assertEquals(Integer.parseInt(noOfSiteUI), noOfSiteDB);
	}

	@Story("Entity Ownership")
	@Description("Clicking this will open a UI listing the corpoarte sites and shows site for owner _id=1"
			+ " order by site no.Site,site name,City,Manager,Phone will be listed and are ready only")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 23, enabled = true)
	public void TC23verifySiteNoListOnViewSelectedEntity() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String query = "select  s.site_no, full_name \r\n" + "from site s where s.owner_id=2 \r\n" + "order by 1";
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnViewSelectedEntityButton();
		List<String> siteNoDB = Utility.getDataFromDatabase(query, "SITE_NO");
		List<String> siteNoUI = entityOwnership.getTableSiteNoList();
		boolean sts = CollectionUtils.isEqualCollection(siteNoUI, siteNoDB);
		Assert.assertTrue(sts);
	}

	@Story("Entity Ownership")
	@Description("Verify Entity Owner & Entity Name Text field are mandatory on create new entity form")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 24, enabled = true)
	public void TC24verifyEntityOwnerAndEntityNameTextFiledAreMandatory() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		String entNameErrorMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 6).trim();
		String entOwnerErrorMessage = reader.getCellData("ENTITYOWNERSHIP", 0, 7).trim();
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnCreateNewEntityButton();
		entityOwnership.clickOnCreateEntitySaveButton();
		boolean sts = entityOwnership.getErrorMessageFromEntityOwnerAndEntityName(entNameErrorMessage,
				entOwnerErrorMessage);
		if (sts) {
			test.log(LogStatus.PASS, "EntityName and EntityOwner are mandatory field");
		} else {

			test.log(LogStatus.FAIL, "EntityName and EntityOwner are not mandatory field");
			softAssert.assertTrue(sts, "not verified");
		}
		softAssert.assertAll();

	}

	@Story("Entity Ownership")
	@Description("Verify that ext ref id should be unique")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 25, enabled = true)
	public void TC25verifyExtRefIdTextFieldIsUnique() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen();
		String extRefId = entityOwnership.getFirstExtRefTextFromTable();
		entityOwnership.clickOnCreateNewEntityButton().clickOnCreateEntitySaveButton()
				.enterDataInExTRefIdOnCreateNewEntityForm(extRefId).selectCurrencyCodeOnCreateNewEntityForm("USD");
		String windowActMsg = entityOwnership.getWindowPopText();
		Assert.assertEquals(windowActMsg, reader.getCellData("ENTITYOWNERSHIP", 0, 8).trim());
	}

	@Story("Entity Ownership")
	@Description("Create new entity and verify that entity is saved in table")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 26, enabled = true)
	public void TC26createANewEntityAndVerifyThatIsShowingInTable() throws Exception {
		Random rm = new Random();
		int num = rm.nextInt(9999);
		String entityName = reader.getCellData("ENTITYOWNERSHIP", 4, 2) + num;
		String entityOwner = reader.getCellData("ENTITYOWNERSHIP", 3, 2);
		String currencyCode = reader.getCellData("ENTITYOWNERSHIP", 5, 2);
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnCreateNewEntityButton();
		entityOwnership.createANewEntity(entityName, entityOwner, String.valueOf(num), currencyCode);
		boolean sts = entityOwnership.verifyTableData(entityName);
		if (sts) {
			test.log(LogStatus.PASS, "Created entity name is present in table");
		} else {
			test.log(LogStatus.PASS, "Created entity name is not present in table");
			softAssert.assertTrue(sts, "not verified");
		}
		softAssert.assertAll();
	}

	@Story("Entity Ownership")
	@Description("Verify clear and exit button on main screen")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 27, enabled = true)
	public void TC27verifyClearAndExitFunctionality() throws Exception {
		String corpHQ = reader.getCellData("ENTITYOWNERSHIP", 1, 2);
		loginpage.Logintoapplication(userName, Utility.decodestring(password));
		menuSelection.selectCurrentSite(corpHQ).navigateToEntityOwnershipScreen().clickOnClearButton();
		String winActMessage=entityOwnership.getWindowPopText().trim();
		test.log(LogStatus.INFO, "Actual Message is eqaul to " + winActMessage);
		Assert.assertEquals(winActMessage,  reader.getCellData("ENTITYOWNERSHIP", 0, 9).trim());
		boolean sts=entityOwnership.clickOnOkButton().clickOnExitButton();
		Assert.assertTrue(sts);
	}
}
