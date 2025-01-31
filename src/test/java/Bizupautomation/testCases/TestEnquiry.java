package Bizupautomation.testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.SearchPage;

public class TestEnquiry extends Base {

	@BeforeMethod(alwaysRun = true)
	public void preSetup() throws InterruptedException {
		Restart(driver);
	}

	@Test(groups = ("Smoke"))
	public void SearchEnquiryFlow() throws InterruptedException {
		SearchPage search = new SearchPage(driver);

		System.out.println("✨✨✨------------ Search Enquiry Flow start -----------✨✨✨");

		// Click on the search icon
		search.SearchProduct("Jackets");

		// Seller Card
		search.SellerCard();

		// Catalog Select
		search.CatalogSelect();

		// Seller Enquiry
		search.SellerEnquiry();

		System.out.println("✨✨✨------------ Search Enquiry Flow working -----------✨✨✨");

	}

}
