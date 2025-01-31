package Bizupautomation.testCases;

import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.LoginPage;

public class TestInstallAndLogin extends Base {

	@Test(groups = ("Smoke"))
	public void LoginFlow() throws InterruptedException {
		LoginPage login = new LoginPage(driver);

		System.out.println("✨✨✨------------ Login Flow start -----------✨✨✨");

		// App install and login
		login.Install();

		login.RestartApp();

		login.SelectLanguage();

		login.Login();

		System.out.println("✨✨✨------------ Login Flow checked -----------✨✨✨");

	}
}
