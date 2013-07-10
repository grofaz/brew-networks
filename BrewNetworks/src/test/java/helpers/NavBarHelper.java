
package helpers;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;



public class NavBarHelper extends Helper {

	/**
	 * Clicks on a bar item (either navbar or bottom links).
	 * 
	 * @param browser
	 * @param bar
	 *          "navBar" or ""
	 * @param item
	 *          to be clicked
	 */
	public void clickNavBarItem(WebDriver browser, String bar, String item) throws Exception {
		System.out.printf("Clicking on [%s]... ", item);
		bar = (bar == "navBar" ? "navBar" : "bottomLinks.");
		String path = getUIMapValue(bar, item);
		WebElement link = browser.findElement(By.xpath(path));
		link.click();
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * Checks all navigation items in a specific page.
	 * 
	 * @param browser
	 * @param startPage
	 *          to check
	 * @throws Exception
	 */
	public void checkAllNavBarItems(WebDriver browser, String startPage) throws Exception {
		System.out.printf("Loading page [%s]... ", startPage);
		browser.get(getUrl(startPage));
		System.out.println("Done.");

		String navBar = getUIMapValue("navBar", "items");
		String bottomLinks = getUIMapValue("bottomLinks", "items");

		// checks the items that are dependent on being logged in/out
		if (isUserLoggedIn(browser)) signOut(browser);
		browser.get(getUrl("signin"));
		checkCurrentPage(browser, "navBar", "signin");
		browser.get(getUrl("signup"));
		checkCurrentPage(browser, "navBar", "signup");
		checkAccountMenuOptions(browser);

		// checks the rest of the items
		String[] categories = { "navBar", "bottomLinks" };
		for (String category : categories) {
			System.out.printf("Checking [%s]... \n", category.equals("navBar") ? "navigation bar links" : "bottom links");
			String[] pages;

			if (category.contains("navBar")) pages = navBar.trim().split(" ");
			else pages = bottomLinks.trim().split(" ");

			for (String page : pages) {
				System.out.printf("[%s] ", page);
				clickNavBarItem(browser, category, page);
				checkCurrentPage(browser, category, page);
				browser.navigate().back();
				if (startPage.equals("signin")) Assert.assertEquals(browser.getCurrentUrl(), getUrl("brews"));
				else Assert.assertEquals(browser.getCurrentUrl(), getUrl(startPage));
			}
		}
		System.out.println("\n  Done.");
	}


	/**
	 * Checks if current page is displayed correctly.
	 * 
	 * @param browser
	 * @param category
	 * @param page
	 * @throws Exception
	 */
	private void checkCurrentPage(WebDriver browser, String category, String page) throws Exception {
		page = page.toLowerCase();
		if (page.contains("poweredby")) {
			checkPoweredBy(browser);
			return;
		}
		if (page.contains("facebook") || page.contains("google") || page.contains("twitter")) {
			checkSocialSites(browser, category, page);
			return;
		}
		if (page.contains("account")) {
			checkAccountMenuOptions(browser);
			return;
		}
		// see if focus is set on the main 4 navbar items
		String[] exceptions = { "home", "find", "brews", "trades" };
		for (String exception : exceptions) {
			String path = getUIMapValue("navBar", "activeItem");
			WebElement activeItem = browser.findElement(By.xpath(path));
			if (exception.indexOf(page) > -1) Assert.assertEquals(activeItem.getText().toLowerCase().contains(page), true);
		}
		Assert.assertEquals(browser.getCurrentUrl().contains(getUrl(page)), true);
	}


	/**
	 * Check the Powered By box.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	private void checkPoweredBy(WebDriver browser) throws Exception {
		String path = getUIMapValue("poweredBy", "window");
		Assert.assertEquals(browser.findElement(By.xpath(path)).isDisplayed(), true);
	}


	/**
	 * Check social sites (new tabs).
	 * 
	 * @param browser
	 * @param category
	 * @param page
	 * @throws Exception
	 */
	private void checkSocialSites(WebDriver browser, String category, String page) throws Exception {
		String path = getUIMapValue(category, page);
		String expected = getUrl(page);
		String actual = browser.findElement(By.xpath(path)).getAttribute("href");
		Assert.assertEquals(expected, actual);
	}


	/**
	 * Checks the Account menu options.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	private void checkAccountMenuOptions(WebDriver browser) throws Exception {
		signIn(browser, "");
		// String[] options = { "Messages", "Account/Profile", "Change Password",
		// "Email Preferences", "Sign Out" };
		String path, expected, actual;
		path = getUIMapValue("navBar", "account");
		WebElement dropdown = browser.findElement(By.xpath(path));
		List<WebElement> options = dropdown.findElements(By.tagName("option"));

		for (WebElement option : options) {
			option.click();
			waitForPageToLoad(browser, getDefaultTimeout());
			expected = getUrl("account" + option);
			actual = browser.getCurrentUrl();
			Assert.assertEquals(actual, expected);
		}
	}

}