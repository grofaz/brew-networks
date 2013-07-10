
package pageHelpers


import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import helpers.Helper;



public class ForgotPasswordHelper extends Helper {

	/**
	 * Enters email in the Forgot Password page.
	 * 
	 * @param browser
	 * @param email
	 * @throws Exception
	 */
	public void setEmail(WebDriver browser, String email) throws Exception {
		System.out.printf("Entering email [%s]... ", email);
		String path = getUIMapValue("forgotPassword", "email");
		browser.findElement(By.xpath(path)).sendKeys(email);
		System.out.println("Done.");
	}


	/**
	 * Enters zipcode in the Forgot Password page.
	 * 
	 * @param browser
	 * @param zipcode
	 * @throws Exception
	 */
	public void setZipcode(WebDriver browser, String zipcode) throws Exception {
		System.out.printf("Entering zipcode [%s]... ", zipcode);
		String path = getUIMapValue("forgotPassword", "zipcode");
		browser.findElement(By.xpath(path)).sendKeys(zipcode);
		System.out.println("Done.");
	}


	/**
	 * Clicks the Reset Instructions button in the Forgot Password page.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickSendResetInstructions(WebDriver browser) throws Exception {
		System.out.print("Clicking [Send Reset Instructions]... ");
		String path = getUIMapValue("forgotPassword", "sendResetInstructions");
		browser.findElement(By.xpath(path)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * Clicks the Cancel button in the Forgot Password page.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickCancel(WebDriver browser) throws Exception {
		System.out.print("Clicking [Cancel Reset Instructions]... ");
		String path = getUIMapValue("forgotPassword", "cancelButton");
		browser.findElement(By.xpath(path)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * Checks if required fields were filled in the Forgot Password page.
	 * 
	 * @param browser
	 * @return true/false
	 * @throws Exception
	 */
	public boolean isResetInstructionsCompleted(WebDriver browser) throws Exception {
		String path = getUIMapValue("forgotPassword", "error");
		List<WebElement> errors = browser.findElements(By.xpath(path));
		for (WebElement error : errors) {
			String errorMessage = error.getText().toLowerCase();
			if (errorMessage.contains("required")) {
				System.out.println("Both fields are required to proceed.");
				return false;
			}
			if (errorMessage.contains("invalid")) {
				System.out.println("Both fields require valid inputs.");
				return false;
			}
		}
		Assert.assertEquals(browser.getCurrentUrl().contains(getUrl("forgotPassword")), false);
		System.out.println("Email requested successfully.");
		return true;
	}


	/**
	 * Enters a reset code in the input field (https://brewnetworks.com/user/reset_password/USER_ID).
	 * Randomly presses Enter or clicks 'Reset Password' button.
	 * 
	 * @param browser
	 * @param code
	 * @throws Exception
	 */
	public void inputResetCode(WebDriver browser, String url, String code) throws Exception {
		browser.get(url);
		System.out.printf("Entering the reset code [%s]... ", code);
		String codeInputField = getUIMapValue("forgotPassword", "code");
		Random random = new Random();
		boolean useButton = random.nextInt(1) == 0 ? true : false;
		if (useButton) {
			browser.findElement(By.xpath(codeInputField)).sendKeys(code);
			String buttonPath = getUIMapValue("forgotPassword", "resetButton");
			System.out.print("Clicking [Reset Password]... ");
			browser.findElement(By.xpath(buttonPath)).click();
		} else {
			System.out.print("Pressing [<Enter>]... ");
			browser.findElement(By.xpath(codeInputField)).sendKeys(code + "\n");
		}
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * Checks for the error message after a reset code has been entered.
	 * 
	 * @param browser
	 * @return true : false
	 * @throws Exception
	 */
	public boolean isResetCodeAccepted(WebDriver browser) throws Exception {
		try {
			String path = getUIMapValue("resetPassword", "wrongCodeAlert");
			String alert = browser.findElement(By.xpath(path)).getText();
			System.out.printf("Got [%s] error message.", alert);
			return false;
		} catch (Exception e) {}
		if (browser.getCurrentUrl().contains(getUrl("resetPassword"))) {
			System.out.println("No error message displayed, but page has not changed.");
			return false;
		}
		System.out.println("No error message displayed.");
		return true;
	}
}