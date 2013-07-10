
package tests


import helpers.GMailHelper;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageHelpers.ForgotPasswordHelper;
import pageHelpers.SigninPageHelper;



public class ForgotPasswordTests extends ForgotPasswordHelper {

	private SigninPageHelper signin = new SigninPageHelper();
	private GMailHelper gmailHelper = new GMailHelper();
	private WebDriver browser;
	private Random random = new Random();
	private final int accounts = getNumberOfAccounts();
	private String account = String.valueOf(random.nextInt(accounts));
	private int testNumber = 0;


	@BeforeMethod
	public void setUp() throws Exception {
		++testNumber;
		System.out.printf("\n*** Starting test %d ***\n\n", testNumber);
		browser = new FirefoxDriver();
		browser.get(getUrl("signin"));
	}


	@AfterMethod
	public void tearDown() {
		browser.quit();
		System.out.printf("\n*** Finished test %d ***\n\n\n", testNumber);
	}


	/**
	 * Triggers a reset_password email.
	 * 
	 * @throws Exception
	 */
	private void requestResetEmail(String account) throws Exception {
		browser.get(getUrl("signin"));
		signin.clickForgotPassword(browser);
		String email = getEmail(account);
		setEmail(browser, email);
		String zipcode = getZipcode(account);
		setZipcode(browser, zipcode);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), true, "Reset Password status");
	}


	/**
	 * Get reset data from google mail.
	 * 
	 * @return {code, link}
	 * @throws Exception
	 */
	private String[] getResetCode(String account) throws Exception {
		gmailHelper.openSigninPage(browser);
		gmailHelper.setEmail(browser, account);
		gmailHelper.setPassword(browser, account);
		gmailHelper.clickSignin(browser);
		gmailHelper.openResetPasswordEmail(browser);
		String link = gmailHelper.getResetCode(browser);
		String code = gmailHelper.getResetLink(browser);
		String[] result = { code, link };
		browser.quit();
		return result;
	}


	/**
	 * Attempts to request a reset_password_email with no email or zip code.
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void sendResetInstructionsNoEmailNoCode() throws Exception {
		System.out.println("Attempts to request a reset_password_email with no email or zip code.");
		signin.clickForgotPassword(browser);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), false, "Reset Password status");
	}


	/**
	 * Attempts to request a reset_password_email with valid email but no zip code.
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void sendResetInstructionsEmailNoCode() throws Exception {
		System.out.println("Attempts to request a reset_password_email with valid email but no zip code.");
		signin.clickForgotPassword(browser);
		String email = getEmail(account);
		setEmail(browser, email);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), false, "Reset Password status");
	}


	/**
	 * Attempts to request a reset_password_email with no email but valid zip code.
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void sendResetInstructionsNoEmailCode() throws Exception {
		System.out.println("Attempts to request a reset_password_email with no email but valid zip code.");
		signin.clickForgotPassword(browser);
		String zipcode = getZipcode(account);
		setZipcode(browser, zipcode);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), false, "Reset Password status");
	}


	/**
	 * Attempts to request a reset_password_email with invalid email and valid zip code.
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void sendResetInstructionsInvalidEmailValidCode() throws Exception {
		System.out.println("Attempts to request a reset_password_email with invalid email and valid zip code.");
		signin.clickForgotPassword(browser);
		String email = getEmail(account);
		email = changeCharsInString(email, 3);
		setEmail(browser, email);
		String zipcode = getZipcode(account);
		setZipcode(browser, zipcode);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), false, "Reset Password status");
	}


	/**
	 * Attempts to request a reset_password_email with valid email and zip code.
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void sendResetInstructionsValidEmailValidCode() throws Exception {
		System.out.println("Attempts to request a reset_password_email with valid email and zip code.");
		signin.clickForgotPassword(browser);
		String email = getEmail(account);
		setEmail(browser, email);
		String zipcode = getZipcode(account);
		setZipcode(browser, zipcode);
		clickSendResetInstructions(browser);
		Assert.assertEquals(isResetInstructionsCompleted(browser), true, "Reset Password status");
	}


	@Test(enabled = true)
	public void inputInvalidCode() throws Exception {
		System.out.println("Attempts to reset password by using an invalid reset code.");
		requestResetEmail(account);
		String[] result = getResetCode(account);
		String code = result[0];
		String link = result[1];
		link = link.replace(code, "");
		code = changeCharsInString(code, 1);
		inputResetCode(browser, link, code);
		Assert.assertEquals(isResetCodeAccepted(browser), false, "Reset code accepted");
	}


	@Test(enabled = false)
	public void useInvalidLink() throws Exception {
		System.out.println("Attempts to reset password by using an invalid reset link.");

	}


	@Test(enabled = false)
	public void useValidCode() throws Exception {
		System.out.println("Attempts to reset password by using a valid reset code.");

	}


	@Test(enabled = false)
	public void useValidLink() throws Exception {
		System.out.println("Attempts to reset password by using a valid reset link.");

	}


	@Test(enabled = true)
	public void useValidCodeInvalidPW() throws Exception {
		System.out.println("Attempts to set an invalid password (confirmation pw doesn't match) by using a valid reset code.");

	}


	@Test(enabled = false)
	public void useValidLinkInvalidPW() throws Exception {
		System.out.println("Attempts to set an invalid password (confirmation pw doesn't match) by using a valid reset link.");

	}

}