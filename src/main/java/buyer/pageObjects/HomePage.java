package buyer.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends SellerPage {
	AndroidDriver driver;

	public HomePage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
	private WebElement notipermission;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbSearch")
	private WebElement searchBar;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSavedSeller")
	private WebElement save;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtUserProfile")
	private WebElement profle;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivBanner")
	private WebElement banner;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtTopAll")
	private WebElement genderAll;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivCoachmarkText")
	private WebElement coachmark;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbCategory")
	private WebElement productFilter;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbDone")
	private WebElement productApply;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbMainCategory")
	private WebElement priceFilter;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"400 and below\"]")
	private WebElement priceSelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbCity")
	private WebElement cityFilter;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivSelected\"])")
	private List<WebElement> citySelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvClear")
	private WebElement clear;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[1]")
	private WebElement videoPlay;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[1]")
	private WebElement homeTab;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[2]")
	private WebElement searchTab;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[3]")
	private WebElement mereSellerTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvNoResult")
	private WebElement resetResult;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtChangeFilter")
	private WebElement resetBtn;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Suggestion`s\"]")
	private WebElement suggestion;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvTitle")
	private WebElement suggestionTitle;

	// Coachmark
	public void CoachMark() {
		save.click();
	}

	// Notification Permission
	public void NotificationPermission() {
		try {
			if (notipermission.isDisplayed()) {
				notipermission.click();
				System.out.println("Notification working ✔");
				System.out.println("Login Sucessfull ✔");
				Thread.sleep(5000);
			} else {
				System.out.println("Notification permission not displayed ❌");
			}
		} catch (InterruptedException e) {
			System.out.println("Error: " + e.getMessage());

		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());

		}
	}

	// Gender filter
	public void GenderFilter(String gender) {
		try {
			if (genderAll.isDisplayed()) {
				ClickXp("//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mtTop" + gender + "\"]");
				System.out.println("Gender filter set ✔");
			}
		} catch (Exception e) {
			Assert.fail("Gender filter failed " + e);
		}
	}

	// Product Select
	public void ProductSelect(String product) {
		try {
			if (productFilter.isDisplayed()) {
				productFilter.click();
				ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
						+ product + "\"]");
				productApply.click();
				HomeWait();
				System.out.println("Product selected ✔");
			}
		} catch (Exception e) {
			Assert.fail("Product filter failed " + e);
		}
	}

	// Price Select
	public void PriceSelect(String price) {
		try {
			if (priceFilter.isDisplayed()) {
				priceFilter.click();
				ClickXp("//android.widget.Button[@text=\"" + price + "\"]");
				HomeWait();
				System.out.println("Price selected ✔");
			}
		} catch (Exception e) {
			Assert.fail("Price filter failed ❌" + e);
		}
	}

	// City Select
	public void CitySelect(int index) {
		try {
			if (cityFilter.isDisplayed()) {
				cityFilter.click();
				citySelect.get(index).click();
				HomeWait();
				System.out.println("City selected ✔");
			}
		} catch (Exception e) {
			Assert.fail("City filter failed ❌" + e);
		}
	}

	// Clear Filter
	public void ClearFilters() {
		try {
			HomeWait();
			productFilter.click();
			clear.click();
			HomeWait();
			priceFilter.click();
			clear.click();
			HomeWait();
			cityFilter.click();
			clear.click();
			HomeWait();
			System.out.println("All Filters are Clear ✔");
		} catch (Exception e) {
			Assert.fail("Filters are not clear ❌");
		}
	}

	// Save page
	public void Save() {
		save.click();
	}

	// Profile page
	public void Profle() {
		profle.click();
	}

	// Banner click
	public void Banner() {
		banner.click();
	}

	// Home tab
	public void HomeTab() {
		homeTab.click();
	}

	// Search tab
	public void SearchBar() {
		searchBar.click();
	}

	// Seller Journey
	public void SellerPresentCheck(String seller) throws InterruptedException {
		try {
			RestartApp();
			ScrollEle("Keep watching your sellers");
			By FindSeller = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
							+ seller + "\"]");
			if (driver.findElements(FindSeller).size() > 0) {
				System.out.println("Seller " + seller + " is present in the Seller Journey section ✔");
				ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
						+ seller + "\"]");
				shortestEnquiry();
				RestartApp();
			}
		} catch (Exception e) {
			Assert.fail("Seller Present Check " + e);
		}
	}

	// Seller Journey
	public void sellerRemoveCheck(String seller) {
		try {
			ScrollEle("Keep watching your sellers");
			By FindSeller = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
							+ seller + "\"]");
			if (driver.findElements(FindSeller).size() > 0) {
				Assert.fail("Seller " + seller + " is not removed from the section after doing Enquiry ❌");
			} else {
				System.out.println("Seller " + seller + " is remove from the section after doing Enquiry ✔");
			}
		} catch (Exception e) {
			Assert.fail("seller Remove failed " + e);
		}
	}

	// Seller Recommendation
	public void SellerRec(String product) {
		ScrollEle("Suggestion`s");

		String prod = suggestionTitle.getText();

		if (product == prod) {

		}

	}

	// Filter Reset message
	public void ResetMsg() {
		waitUntilText(resetResult, "No Result Found");
		if (resetResult.isDisplayed()) {
			String resetMsg = resetResult.getText();
			String resetBtnMsg = resetBtn.getText();
			System.out.println("Reset Message is " + resetMsg + " " + resetBtnMsg);
		} else {
			Assert.fail("Reset Message is not Displayed ❌");
		}
	}

}
