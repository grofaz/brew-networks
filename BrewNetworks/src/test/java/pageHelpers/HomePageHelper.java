
package pageHelpers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import helpers.Helper;



/**
 * Helper for the Home Page
 */
public class HomePageHelper extends Helper {

	/**
	 * Clicks on the big Sign Up button
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickSignUpButton(WebDriver browser) throws Exception {
		String path = getUIMapValue("home", "signup");
		WebElement button = browser.findElement(By.xpath(path));
		Assert
				.assertEquals(button.getAttribute("href"), getUrl("signup"), String.format("Element [%s] does not point to expected URL", button.getText()));
		button.click();
		waitForPageToLoad(browser, getDefaultTimeout());
		SignupPageHelper signupPageHelper = new SignupPageHelper();
		Assert.assertEquals(signupPageHelper.isSignupPageDisplayedCorrectly(browser), true, "Opened page is not displayed correctly.");
	}

}