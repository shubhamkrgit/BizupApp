package Bizupautomation.testCases;

import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.HomePage;
import buyer.pageObjects.LoginPage;
import buyer.pageObjects.ProfilePage;
import buyer.pageObjects.SearchPage;
import buyer.pageObjects.VideoPage;

//import io.appium.java_client.pagefactory.AndroidFindBy;
public class TestSanity extends Base {

	@Test(priority = 1, description = "Verify user can successfully login")
	public void LoginFlow() throws InterruptedException {
		LoginPage login = new LoginPage(driver);

		System.out.println("✨✨✨------------ Login Flow start -----------✨✨✨");

		// App install and login
		login.Install();

		login.RestartApp();

		login.SelectLanguage();

		login.Login();

		System.out.println("✨✨✨------------ Login Flow End -----------✨✨✨");

	}

	@Test(priority = 2, description = "Verify home page filters reset functionality")
	public void HomeReset() throws InterruptedException {
		HomePage home = new HomePage(driver);

		System.out.println("✨✨✨------- Home Reset Flow start ------✨✨✨");

		home.RestartApp();

		// ---------- Product reset message check
		home.ClearFilters();

		home.GenderFilter("Men");

		home.ProductSelect("Saree");

		home.ResetMsg();

		// ---------- Price reset mesage check
		home.ClearFilters();

		home.ProductSelect("Lower");

		home.PriceSelect("100 and below");

		home.ResetMsg();

		// ----------- City reset message check
		home.ClearFilters();

		home.ProductSelect("Saree");

		home.CitySelect(2);

		home.ResetMsg();

		System.out.println("✨✨✨-------- Home Reset Flow checked ✅ ------✨✨✨");
	}

	@Test(priority = 3, description = "Verify video page filters reset functionality")
	public void VideoReset() throws InterruptedException {
		VideoPage video = new VideoPage(driver);

		System.out.println("✨✨✨------- Video Reset Flow start -------✨✨✨");

		video.RestartApp();

		video.HomeToVideoFeed();

		// ---------- Product reset message check
		video.ClearFilters();

		video.PriceSelect("100 and below");

		video.ProductSelect("Sweaters", "स्वीटर्स");

		video.ResetMsg();

		// ---------- Price reset mesage check
		video.ClearFilters();

		video.ProductSelect("Kurti Set", "कुर्टी सेट");

		video.PriceSelect("100 and below");

		video.ResetMsg();

		// ----------- City reset message check
		video.ClearFilters();

		video.ProductSelect("Saree", "साड़ी");

		video.CitySelect(2);

		video.ResetMsg();

		System.out.println("✨✨✨------- Video Reset Flow checked ✅ ------✨✨✨");

	}

	@Test(priority = 4, description = "Verify catalog add-to-cart and payment functionality (home page >> Video Page >> Collection Page/Product page)")
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

	@Test(priority = 5, description = "Verify catalog add-to-cart and payment functionality (search page >> Collection Page/Product page)")
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

	@Test(priority = 6, description = "Verify enquiry functionality (search >> seller tab >> seller page)")
	public void SearchEnquiryFlow() throws InterruptedException {
		SearchPage search = new SearchPage(driver);

		System.out.println("✨✨✨------------ Search Enquiry Flow start -----------✨✨✨");

		search.RestartApp();

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

	@Test(priority = 7, description = "Verify profile functionality")
	public void ProfileFlow() throws InterruptedException {
		try {
			ProfilePage profilePage = new ProfilePage(driver);

			System.out.println("✨✨✨------------ Profile Flow start -----------✨✨✨");

			profilePage.RestartApp();

			// Navigate to profile page
			profilePage.Profile();

			// Add small waits between actions to ensure stability
			Thread.sleep(1000);

			// Edit profile
			profilePage.EditProfile("Demo Test", "Test Seller", "Delhi");
			Thread.sleep(1000);

			// Refer
			profilePage.Refer();
			Thread.sleep(1000);

			// Setting
			profilePage.Setting();
			Thread.sleep(1000);

			// My Video
			profilePage.MyVideo();

			System.out.println("✨✨✨------------ Profile Flow checked ✅ -----------✨✨✨");
		} catch (Exception e) {
			System.err.println("Error in Profile Flow: " + e.getMessage());
			throw e;
		}
	}

}
