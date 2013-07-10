
package tests;


import helpers.NavBarHelper;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageHelpers.SigninPageHelper;



public class LoginPageTests extends SigninPageHelper {

	private NavBarHelper navBar = new NavBarHelper();
	private WebDriver browser;
	private Random random = new Random();
	private final int accounts = getNumberOfAccounts();
	private String account = String.valueOf(random.nextInt(accounts));
	private int testNumber = 0;


	@BeforeMethod
	public void setUp() {
		++testNumber;
		System.out.printf("\n*** Starting test %d ***\n\n", testNumber);
		browser = new FirefoxDriver();
		browser.get(getUrl("signin"));
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	@AfterMethod
	public void tearDown() {
		browser.quit();
		System.out.printf("\n*** Finished test %d ***\n\n\n", testNumber);
	}


	@Test(enabled = true)
	public void blankEmailAndPassword() throws Exception {
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), false);
	}


	@Test(enabled = true)
	public void testNavBarItems() throws Exception {
		navBar.checkAllNavBarItems(browser, "signin");
	}


	@Test(enabled = true)
	public void invalidEmailValidPassword() throws Exception {
		String[] specialChars = { "@", "." };
		for (String specialChar : specialChars) {
			String email = getEmail(account).replace(specialChar, "");
			String password = getPassword(account);
			setEmail(browser, email);
			setPassword(browser, password);
			clickSignin(browser);
			Assert.assertEquals(isUserLoggedIn(browser), false);
			System.out.println();
		}
	}


	@Test(enabled = true)
	public void validEmailBlankPassword() throws Exception {
		String email = getEmail(account);
		setEmail(browser, email);
		setPassword(browser, "");
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), false);
	}


	@Test(enabled = true)
	public void validEmailInvalidPassword() throws Exception {
		String email = getEmail(account);
		String password = getPassword(account);
		password = changeCharsInString(password, 3);
		setEmail(browser, email);
		setPassword(browser, password);
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), false);
	}


	@Test(enabled = true)
	public void blankEmailValidPassword() throws Exception {
		String password = getPassword(account);
		setPassword(browser, password);
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), false);
	}


	@Test(enabled = true)
	public void validEmailAndPasswordButFromDifferentAccounts() throws Exception {
		String email = getEmail(account);
		String newAccount = account;
		while (newAccount.equals(account))
			newAccount = String.valueOf(random.nextInt(accounts));
		String password = getPassword(newAccount);
		setEmail(browser, email);
		setPassword(browser, password);
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), false);
	}


	@Test(enabled = true)
	public void validCredentials() throws Exception {
		String email = getEmail(account);
		String password = getPassword(account);
		setEmail(browser, email);
		setPassword(browser, password);
		clickSignin(browser);
		Assert.assertEquals(isUserLoggedIn(browser), true);

	}

}