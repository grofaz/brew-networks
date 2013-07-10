
package tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageHelpers.BrewsPageHelper;



public class AppTest extends BrewsPageHelper {

	private WebDriver browser;


	@BeforeMethod
	public void setUp() throws Exception {
		browser = new FirefoxDriver();
	}


	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		browser.quit();
	}


	@Test(enabled = true)
	public void test() throws Exception {
		signIn(browser, "0");
		displayAllBrews(getBrewsList(browser));
	}

}