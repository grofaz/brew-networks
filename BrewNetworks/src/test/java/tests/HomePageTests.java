
package tests;


import helpers.NavBarHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageHelpers.HomePageHelper;



public class HomePageTests extends HomePageHelper {

	private WebDriver browser;
	private NavBarHelper navbarHelper = new NavBarHelper();


	@BeforeMethod
	public void setUp() {
		browser = new FirefoxDriver();
	}


	@AfterMethod
	public void tearDown() {
		browser.quit();
	}


	/**
	 * Checks href for navigation bar items (both top and bottom links)
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void testNavBarItems() throws Exception {
		navbarHelper.checkAllNavBarItems(browser, "home");
	}


	/**
	 * Checks href for big SignUp button
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void testBigSignupButton() throws Exception {
		browser.get(getUrl("home"));
		Thread.sleep(500);
		waitForPageToLoad(browser, getDefaultTimeout());
		clickSignUpButton(browser);
	}
}