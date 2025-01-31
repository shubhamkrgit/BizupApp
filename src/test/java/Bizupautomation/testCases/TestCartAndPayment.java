package Bizupautomation.testCases;

import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.HomePage;
import buyer.pageObjects.SearchPage;

public class TestCartAndPayment extends Base {

	@Test(groups = ("Smoke"))
	public void HomeVideoCartFlow() throws InterruptedException {
		HomePage home = new HomePage(driver);

		System.out.println("✨✨✨------------ Home Video Cart Flow Start -----------✨✨✨");

		// Restart App
		home.RestartApp();

		// Product Filter
		home.ProductSelect("Saree");

		// Home to seller page
		home.HomeToVideoFeed();
		
		// Video to collection
		home.VideoToCollection();

		// Catalog Enquiry
		home.CatalogAddToCart();
		
		// Pay Cart
		home.PayCartPage();

		System.out.println("✨✨✨------------ Home Video Cart Flow End -----------✨✨✨");
	}

	@Test(groups = ("Smoke"))
	public void SearchCartFlow() throws InterruptedException {
		SearchPage search = new SearchPage(driver);

		System.out.println("✨✨✨------------ Search Cart Flow start -----------✨✨✨");

		// Restart App	
		search.RestartApp();

		// Click on the search icon
		search.SearchProduct("Pant");

		// Video Tab
		search.VideoTab();
		
		// Play Video
		search.PlayVideo();

		// Video Enquiry
		search.VideoToCollection();
		
		// Catalog Enquiry
		search.CatalogAddToCart();
				
		// Pay Cart
		search.PayCartPage();
		
		// Back to Search
		search.Back();
		search.Back();
		search.Back();

		// Sample Tab
		search.SampleTab();

		// Sample Enquiry
		search.SampleToCollection();
		
		// Catalog Enquiry
		search.CatalogAddToCart();
						
		// Pay Cart
		search.PayCartPage();

		System.out.println("✨✨✨------------ Search Cart Flow End -----------✨✨✨");

	}
	
	
}
