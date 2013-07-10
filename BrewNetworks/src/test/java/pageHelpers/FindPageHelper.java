
package pageHelpers;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helpers.Helper;



public class FindPageHelper extends Helper {

	public boolean isFindPageDisplayedCorrectly(WebDriver browser) throws Exception {
		return false;
	}


	/**
	 * Sets location.
	 * 
	 * @param browser
	 * @param location
	 * @throws Exception
	 */
	public void setLocation(WebDriver browser, String location) throws Exception {
		System.out.println("Setting location... ");
		String path = getUIMapValue("find", "location");
		browser.findElement(By.xpath(path)).sendKeys(location);
		System.out.println("Done.");
	}


	/**
	 * Sets the radius size.
	 * 
	 * @param browser
	 * @param distance
	 *          (1, 5, 10, 20, 50, 100, 200 miles)
	 * @throws Exception
	 */
	public void setRadius(WebDriver browser, String distance) throws Exception {
		System.out.print("Setting radius... ");
		String path = getUIMapValue("find", "radius");
		WebElement radius = browser.findElement(By.xpath(path));
		List<WebElement> options = radius.findElements(By.tagName("option"));
		for (WebElement option : options)
			if (distance.equals(option.getText())) {
				option.click();
				System.out.println("Done.");
				return;
			}
		System.out.printf("Failed ([%s] does not exist in the drop down list).\n", distance);
	}


	/**
	 * Sets the sorting filter.
	 * 
	 * @param browser
	 * @param filter
	 *          (brew rating, brewer rating, distance, availability)
	 * @throws Exception
	 */
	public void sortBy(WebDriver browser, String filter) throws Exception {
		System.out.print("Sorting results... ");
		String path = getUIMapValue("find", "sortBy");
		WebElement sort = browser.findElement(By.xpath(path));
		List<WebElement> options = sort.findElements(By.tagName("option"));
		for (WebElement option : options)
			if (filter.equals(option.getText())) {
				option.click();
				System.out.println("Done.");
				return;
			}
		System.out.printf("Failed ([%s] does not exist in the drop down list).\n", filter);
	}


	/**
	 * Sort by styles (only specified styles).
	 * 
	 * @param browser
	 * @param brewStyles
	 *          String[]
	 * @throws Exception
	 */
	public void sortByStyle(WebDriver browser, String... brewStyles) throws Exception {
		System.out.println("Setting styles...");
		String path = getUIMapValue("find", "styles");
		WebElement styles = browser.findElement(By.xpath(path));
		List<WebElement> options = styles.findElements(By.tagName("li"));
		for (WebElement option : options) {
			WebElement checkbox = option.findElement(By.tagName("input"));
			String text = option.findElement(By.tagName("span")).getText();
			for (String brewStyle : brewStyles)
				if (brewStyle.equals(text)) if (!checkbox.isSelected()) {
					checkbox.click();
					System.out.printf("\tStyle [%s] selected.");
				} else {
					checkbox.click();
					System.out.printf("\tStyle [%s] de-selected.");
				}
		}
		System.out.println("Done.");
	}

}