
package pageHelpers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import helpers.Helper;



public class SigninPageHelper extends Helper {

	/**
	 * Enters email in the appropriate field.
	 * 
	 * @param browser
	 * @param account
	 * @throws Exception
	 */
	public void setEmail(WebDriver browser, String email) throws Exception {
		System.out.printf("Setting email [%s]... ", email);
		String path = getUIMapValue("signin", "emailField");
		browser.findElement(By.xpath(path)).sendKeys(email);
		System.out.println("Done.");
	}


	/**
	 * Enters password in the appropriate field
	 * 
	 * @param browser
	 * @param account
	 * @throws Exception
	 */
	public void setPassword(WebDriver browser, String password) throws Exception {
		System.out.printf("Setting password [%s]... ", password);
		String path = getUIMapValue("signin", "passwordField");
		browser.findElement(By.xpath(path)).sendKeys(password);
		System.out.println("Done.");
	}


	/**
	 * Clicks on the Forgot Password link
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickForgotPassword(WebDriver browser) throws Exception {
		System.out.printf("Clicking [Forgot password]... ");
		String path = getUIMapValue("signin", "forgotPassword");
		browser.findElement(By.xpath(path)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
		Assert.assertEquals(browser.getCurrentUrl(), getUrl("forgotPassword"), "Unexpected URL.");
	}


	/**
	 * Clicks Sign In button.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickSignin(WebDriver browser) throws Exception {
		System.out.printf("Clicking [Sign In]... ");
		String path = getUIMapValue("signin", "signinButton");
		browser.findElement(By.xpath(path)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
		isLoginSuccessful(browser);
	}


	/**
	 * Checks if the email and password are valid and the combination is correct.
	 * 
	 * @param browser
	 * @return credentials accepted ? true : false
	 * @throws Exception
	 */
	private boolean isLoginSuccessful(WebDriver browser) throws Exception {
		// blank email/pw or email in invalid format
		String path = getUIMapValue("signin", "inlineError");
		try {
			WebElement inlineError = browser.findElement(By.xpath(path));
			String inlineErrorText = inlineError.getText().toLowerCase();
			String[] errors = { "required-one or more fields were left blank", "email-invalid email format" };
			boolean status = true;
			for (String error : errors)
				if (inlineErrorText.contains(error.split("-")[0])) {
					System.out.printf("Credentials not accepted (%s).\n", error.split("-")[1]);
					status = false;
				}
			if (status == false) return false;
		} catch (Exception e) {}

		// wrong combination of U/N and PW
		path = getUIMapValue("signin", "wrongCredentialsAlert");
		try {
			browser.findElement(By.xpath(path));
			System.out.printf("Credentials not accepted (%s).\n", "wrong combination of email and password");
			return false;
		} catch (Exception e) {}

		System.out.println("Credentials accepted.");
		return true;
	}


	/**
	 * Checks to see if the 'please log in' alert is displayed.
	 * 
	 * @param browser
	 * @return true or false
	 * @throws Exception
	 */
	public boolean isLoginRequested(WebDriver browser) throws Exception {
		System.out.print("Checking for 'please login' message... ");
		String path = getUIMapValue("signin", "wrongCredentialsAlert");
		try {
			browser.findElement(By.xpath(path));
			System.out.println("Found.");
			return true;
		} catch (Exception e) {
			System.out.println("Not found.");
			return false;
		}
	}


	/**
	 * Closes the wrong credentials alert
	 * 
	 * @param browser
	 * @throws Exception
	 *           if alert is not closed
	 */
	public void closeSigninAlert(WebDriver browser) throws Exception {
		System.out.print("Closing [check credentials] alert... ");
		String path = getUIMapValue("signin", "wrongCredentialsAlert");
		String pathClose = getUIMapValue("signin", "wrongCredentialsAlertClose");
		try {
			browser.findElement(By.xpath(path));
			browser.findElement(By.xpath(pathClose)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			browser.findElement(By.xpath(path));
			System.out.print("Failed.\n\t");
		} catch (Exception e) {
			System.out.println("Done.");
			return;
		}
		throw new IllegalArgumentException("The alert was not closed.");
	}


	/**
	 * Checks if the login page is displayed correctly: email, pw, signup button,
	 * forgot pw.
	 * 
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	public boolean isLoginPageDisplayedCorrectly(WebDriver browser) throws Exception {
		System.out.println("Checking if the login page is displayed correctly... ");
		// check email field
		boolean status = true;
		String path = getUIMapValue("signin", "emailField");
		try {
			browser.findElement(By.xpath(path));
		} catch (Exception e) {
			System.out.println("\tEmail field not found.");
			status = false;
		}
		// check password field
		path = getUIMapValue("signin", "passwordField");
		try {
			browser.findElement(By.xpath(path));
		} catch (Exception e) {
			System.out.println("\tPassword field not found.");
			status = false;
		}
		// check big signup button (uses a method from HomePageHelper.java)
		HomePageHelper homePageHelper = new HomePageHelper();
		homePageHelper.clickSignUpButton(browser);
		SignupPageHelper signupPageHelper = new SignupPageHelper();
		if (!signupPageHelper.isSignupPageDisplayedCorrectly(browser)) {
			System.out.println("\tThe page opened by the big Sign Up Now button (signup page) is not displayed correctly.");
			status = false;
			signupPageHelper = null;
		}
		// checks forgot pw link
		path = getUIMapValue("signin", "forgotPassword");
		path = browser.findElement(By.xpath(path)).getAttribute("href");
		if (path != getUIMapValue("URL", "forgotPassword")) {
			System.out.println("\tForgot Password link does not point to expected URL.");
			status = false;
		}
		return status == true ? true : false;
	}
}