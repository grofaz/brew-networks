
package helpers;


import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



/**
 * Opens google mail; signs in; gets password reset code and link.
 */
public class GMailHelper extends Helper {

	private String signinURL = "http://mail.google.com";
	private String emailPath = "//*[@id='Email']";
	private String passwordPath = "//*[@id='Passwd']";
	private String signinPath = "//*[@id='signIn']";
	private String passwordResetEmailPath = "//*[contains (@id, ':pb')]//b";// contains(text(), 'Brew Networks - Password Reset')]";
	private String passwordResetLink = "https://brewnetworks.com/user/reset_password/";


	/**
	 * @param browser
	 * @throws Exception
	 */
	public void openSigninPage(WebDriver browser) throws Exception {
		System.out.print("Opening gmail login page... ");
		browser.get(signinURL);
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * @param browser
	 * @param account
	 * @throws Exception
	 */
	public void setEmail(WebDriver browser, String account) throws Exception {
		System.out.print("Setting gmail email ");
		String email;
		email = getConfigValue("account", "e" + account);
		System.out.printf("[%s]... ", email);
		browser.findElement(By.xpath(emailPath)).sendKeys(email);
		System.out.println("Done");
	}


	/**
	 * @param browser
	 * @param account
	 * @throws Exception
	 */
	public void setPassword(WebDriver browser, String account) throws Exception {
		System.out.print("Setting gmail password ");
		String password;
		password = getConfigValue("account", "gpw" + account);
		System.out.printf("[%s]... ", password);
		browser.findElement(By.xpath(passwordPath)).sendKeys(password);
		System.out.println("Done");
	}


	/**
	 * @param browser
	 * @throws Exception
	 */
	public void clickSignin(WebDriver browser) throws Exception {
		System.out.print("Clicking [sign in]... ");
		browser.findElement(By.xpath(signinPath)).click();
		Thread.sleep(1000);
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * @param browser
	 * @throws Exception
	 */
	public void openResetPasswordEmail(WebDriver browser) throws Exception {
		System.out.print("Opening [reset password email]... ");
		browser.findElement(By.xpath(passwordResetEmailPath)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
	}


	/**
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	public String getResetLink(WebDriver browser) throws Exception {
		System.out.print("Gmail reset password link = ");
		String link = browser.findElement(By.partialLinkText(passwordResetLink)).getText();
		System.out.println(link);
		return link;
	}


	/**
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	public String getResetCode(WebDriver browser) throws Exception {
		System.out.print("Gmail reset password code = ");
		String code = "";
		Scanner source = new Scanner(browser.getPageSource());
		while (source.hasNext())
			if (source.next().toLowerCase().equals("reset") && source.next().toLowerCase().equals("code:")) code = source.next();
		code = code.replace("<br", "");
		System.out.println(code);
		source.close();
		return code;
	}
}