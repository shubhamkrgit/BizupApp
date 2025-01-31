package Bizupautomation.testCases;

import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.EventsPage;
import buyer.utils.AndroidUtils;

public class TestEvents extends Base {

	@Test
	public void LoginLanguageEvent() throws Exception {

		// Sample usage of the methods
		AndroidUtils androidUtils = new AndroidUtils(driver);
		EventsPage eventTrigger = new EventsPage(driver);

		System.out.println("✨✨✨------------ Login Language Event start -----------✨✨✨");

		// Clear App data
		androidUtils.clearAppData();

		// Launch App
		androidUtils.RestartApp();

		// Example of triggering a single event (individual event)
		eventTrigger.clickButtonAndTriggerEvents("LoginLanguage", "lang_hindi", "individual");

		eventTrigger.clickButtonAndTriggerEvents("LoginLanguage", "lang_english", "individual");

		eventTrigger.clickButtonAndTriggerEvents("LoginLanguage", "lang_continue", "individual");

//		// Example of triggering multiple events (all events in sample_tab)
//		eventTrigger.clickButtonAndTriggerEvents("SearchPage", "video_tab", "multiple");
//
//		// Example of triggering multiple events for sample_order
//		eventTrigger.clickButtonAndTriggerEvents("SearchPage", "sample_order", "multiple");
//
//		// Example of triggering individual event for cityfilter
//		eventTrigger.clickButtonAndTriggerEvents("HomePage", "cityfilter", "individual");

		System.out.println("✨✨✨------------ Login Language Event End -----------✨✨✨");

	}

}