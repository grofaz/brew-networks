
package helpers;


import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



/**
 * General Helper
 */
public class Helper {

	private UIMap uimap = new UIMap();
	private SiteConfig config = new SiteConfig();
	private Random random = new Random();


	/**
	 * Reads from UIMap.properties
	 * 
	 * @param category
	 * @param key
	 *          (optional)
	 * @return value of key
	 */
	public String getUIMapValue(String category, String key) {
		return uimap.find(category, key);
	}


	/**
	 * @return default timeout
	 */
	public int getDefaultTimeout() {
		return Integer.parseInt(config.find("timeout", ""));
	}


	/**
	 * Reads from siteConfig.properties (only one parameter required)
	 * 
	 * @param category
	 * @param key
	 * @return value of key
	 */
	public String getConfigValue(String category, String key) {
		return config.find(category, key);
	}


	/**
	 * Waits for a page to load.
	 * 
	 * @param browser
	 * @param timeout
	 */
	public static void waitForPageToLoad(WebDriver browser, int timeout) {
		System.out.print("Loading page... ");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Failed.");
			e.printStackTrace();
		}
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(browser, timeout);
		wait.until(pageLoadCondition);
		System.out.println("Done.");
	}


	//
	// /**
	// * Executes a script on an element
	// *
	// * @note Really should only be used when the web driver is sucking at
	// exposing
	// * functionality natively
	// * @param script
	// * The script to execute
	// * @param element
	// * The target of the script, referenced as arguments[0]
	// */
	// private void trigger(WebDriver browser, String script, WebElement element)
	// {
	// ((JavascriptExecutor) browser).executeScript(script, element);
	// }
	//
	//
	// /**
	// * Executes a script
	// *
	// * @note Really should only be used when the web driver is sucking at
	// exposing
	// * functionality natively
	// * @param script
	// * The script to execute
	// */
	// private Object trigger(WebDriver browser, String script) {
	// return ((JavascriptExecutor) browser).executeScript(script);
	// }
	//
	//
	// /**
	// * Opens a new tab for the given URL
	// *
	// * @param url
	// * The URL to
	// * @throws JavaScriptException
	// * If unable to open tab
	// */
	// public void openTab(WebDriver browser, String url) {
	// String script =
	// "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
	// Object element = trigger(browser, String.format(script, url));
	// if (element instanceof WebElement) {
	// WebElement anchor = (WebElement) element;
	// anchor.click();
	// trigger(browser, "var a=arguments[0];a.parentNode.removeChild(a);",
	// anchor);
	// } else {
	// throw new JavaScriptException(element, "Unable to open tab", 1);
	// }
	// }
	//
	//
	// /**
	// * Switches to the non-current window
	// */
	// public void switchWindow(WebDriver browser) throws NoSuchWindowException,
	// NoSuchWindowException {
	// Set<String> handles = browser.getWindowHandles();
	// String current = browser.getWindowHandle();
	// handles.remove(current);
	// String newTab = handles.iterator().next();
	// locator.window(newTab);
	// }

	/**
	 * Gets an URL from uimap.properties.
	 * 
	 * @param page
	 * @return url of a page
	 */
	public String getUrl(String page) {
		return uimap.find("URL", page);
	}


	/**
	 * Get the number of tester's created accounts.
	 * 
	 * @return accounts quantity
	 */
	public int getNumberOfAccounts() {
		return Integer.parseInt(config.find("accounts", ""));
	}


	/**
	 * Gets the email for specified account number.
	 * 
	 * @param accountNumber
	 * @return email address
	 */
	public String getEmail(String account) {
		return config.find("account", "e" + account);
	}


	/**
	 * Gets the password for the specified account number.
	 * 
	 * @param account
	 * @return password
	 */
	public String getPassword(String account) {
		return config.find("account", "p" + account);
	}


	/**
	 * Gets the zipcode for the specified account number.
	 * 
	 * @param account
	 * @return zipcode
	 */
	public String getZipcode(String account) {
		return config.find("account", "z" + account);
	}


	/**
	 * Logs in the user with the specified account number (random if none
	 * specified).
	 * 
	 * @param accountNumber
	 */
	public void signIn(WebDriver browser, String accountNumber) throws Exception {
		browser.get(getUrl("signin"));
		waitForPageToLoad(browser, getDefaultTimeout());
		System.out.print("Signing in ");

		Random random = new Random();
		String email, emailField, password, passwordField, signInButton;

		if (accountNumber == "") accountNumber = random.nextInt(getNumberOfAccounts()) + "";
		email = getEmail(accountNumber);
		password = getPassword(accountNumber);

		emailField = getUIMapValue("signin", "emailField");
		passwordField = getUIMapValue("signin", "passwordField");
		signInButton = getUIMapValue("signin", "signinButton");

		browser.findElement(By.xpath(emailField)).sendKeys(email);
		browser.findElement(By.xpath(passwordField)).sendKeys(password);
		System.out.printf("[%s / %s]... ", email, password);
		browser.findElement(By.xpath(signInButton)).click();
		waitForPageToLoad(browser, getDefaultTimeout());

		Assert.assertEquals(isUserLoggedIn(browser), true, "Log in status");
	}


	/**
	 * Logs out the user.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void signOut(WebDriver browser) throws Exception {
		browser.get(getUrl("accountSignOut"));
		Assert.assertEquals(browser.getCurrentUrl(), getUrl("home"));
	}


	/**
	 * Checks if the user is logged in.
	 * 
	 * @param browser
	 * @return login accepted status
	 * @throws Exception
	 */
	public boolean isUserLoggedIn(WebDriver browser) throws Exception {
		System.out.print("Checking login status... ");
		// is Account menu item displayed
		String path = getUIMapValue("navBar", "account");
		try {
			browser.findElement(By.xpath(path));
		} catch (Exception e) {
			System.out.println("Not logged in.");
			return false;
		}
		System.out.println("Logged in.");
		return true;
	}


	public String getRandomLetters(int size) throws Exception {
		String s = "";
		while (s.length() < size)
			s += (char) (random.nextInt(26) + 'a');
		return s;
	}


	public String changeCharsInString(String string, int numberOfRandomChars) throws Exception {
		System.out.printf("Changed [%s] to ", string);
		char[] characters = string.toCharArray();
		Assert.assertEquals(characters.length == string.length(), true, "Char array equals string lenght");
		Assert.assertEquals(string.length() >= numberOfRandomChars, true, "Changing more chars than string length.");
		for (int i = 0; i < numberOfRandomChars; i++) {
			char randomCharacter = getRandomLetters(1).charAt(0);
			characters[random.nextInt(string.length())] = randomCharacter;
		}
		String result = characters.toString();
		System.out.printf("[%s]\n", result);
		return result;
	}

}