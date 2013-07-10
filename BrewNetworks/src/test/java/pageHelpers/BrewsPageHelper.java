
package pageHelpers;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import DTO.Batch;
import DTO.Brew;

import helpers.Helper;



public class BrewsPageHelper extends Helper {

	/**
	 * Clicks the 'Add a New Brew' button.
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void clickAddNewBrew(WebDriver browser) throws Exception {
		System.out.print("Clicking [Add a New Brew]... ");
		String buttonPath = getUIMapValue("myBrews", "addBrewButton");
		browser.findElement(By.xpath(buttonPath)).click();
		waitForPageToLoad(browser, getDefaultTimeout());
		String expected = getUrl("brewsAddNew");
		Assert.assertEquals(browser.getCurrentUrl(), expected, "URL for Add a New Brew");
	}


	/**
	 * Searches for brews elements.
	 * 
	 * @param browser
	 * @return list of users brews
	 * @throws Exception
	 */
	private List<WebElement> updateBrewsList(WebDriver browser) throws Exception {
		List<WebElement> list = browser.findElements(By.xpath(getUIMapValue("myBrews", "brewsList")));
		return list;
	}


	/**
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	private List<WebElement> updateBatchesList(WebDriver browser) throws Exception {
		List<WebElement> batches;
		String path = getUIMapValue("myBrews", "batchesList");
		batches = browser.findElements(By.xpath(path));
		return batches;
	}


	/**
	 * Creates a list of the brew's batches.
	 * 
	 * @param browser
	 * @return list of Batch objects
	 * @throws Exception
	 */
	private List<Batch> getBatchesList(WebDriver browser) throws Exception {
		System.out.print("Adding attributes to batches list... ");
		List<WebElement> elements = updateBatchesList(browser);
		List<Batch> batches = new ArrayList<Batch>();
		for (WebElement element : elements) {
			Batch batch = new Batch();

			// set private/public bottles
			String bottlePath = getUIMapValue("myBrews", "brewsList") + getUIMapValue("brew", "bottles");
			String[] bottleValues = element.findElement(By.xpath(bottlePath)).getText().split(" \\| ");
			batch.setAttribute("privateBottles", bottleValues[0]);
			batch.setAttribute("publicBottles", bottleValues[1]);

			// check total number of bottles
			int actual = Integer.parseInt(bottleValues[0]) + Integer.parseInt(bottleValues[1]);
			int expected = Integer.parseInt(bottleValues[2]);
			Assert.assertEquals(actual, expected, "private + public = total bottles");

			String[] characteristics = { "batchNum", "abv", "ibu", "color", "brewDate", "bottleDate", "bottleRating" };
			for (String attribute : characteristics) {
				String path = getUIMapValue("myBrews", "brewsList") + getUIMapValue("brew", attribute);
				String value = element.findElement(By.xpath(path)).getText();
				batch.setAttribute(attribute, value);
			}

			// add batch to batches list
			batches.add(batch);
		}
		System.out.println("Done.");
		return batches;
	}


	/**
	 * Creates a list of the user's brews.
	 * 
	 * @param browser
	 * @return list of Brew objects
	 * @throws Exception
	 */
	public List<Brew> getBrewsList(WebDriver browser) throws Exception {
		System.out.println("\nGetting brew list... ");
		List<WebElement> brewElements = updateBrewsList(browser);
		List<Brew> brews = new ArrayList<Brew>();

		for (WebElement element : brewElements) {
			Brew brew = new Brew();
			String[] characteristics = { "id", "name", "style", "rating", "description" };
			for (String attribute : characteristics)
				brew.setAttribute(attribute, element.getAttribute(getUIMapValue("brew", attribute)));
			brew.setBatches(getBatchesList(browser));
			brews.add(brew);
		}
		return brews;
	}


	public boolean isAddNewBrewSuccessful(WebDriver browser, String brewName) throws Exception {
		System.out.printf("Checking that [%s] was successfully added to brews list... \n", brewName);
		boolean status = true;
		// alert should report adding the new brew
		try {
			WebElement alert = browser.findElement(By.xpath(getUIMapValue("myBrews", "alert")));
			if (!alert.getText().contains(String.format("successfully added %s", brewName))) {
				System.out.println("\tBrew is not displayed in successful alert message");
				status = false;
			}
			browser.findElement(By.xpath(getUIMapValue("myBrews", "alertClose"))).click();
		} catch (Exception e) {}
		// alert should be closed
		try {
			browser.findElement(By.xpath(getUIMapValue("myBrews", "alert")));
			Assert.assertEquals(true, false, "Is alert still displayed after trying to close it");
		} catch (Exception e) {}
		// the new brew should be displayed in the page
		if (!browser.getPageSource().contains(brewName)) {
			System.out.println("\tBrew not found in page source.");
			status = false;
			return status;
		}
		// check for brew in brews list

		System.out.println("Done.");
		return status;
	}


	/**
	 * Displays all user brews and batches.
	 * 
	 * @param brews
	 * @throws Exception
	 */
	public void displayAllBrews(List<Brew> brews) throws Exception {
		for (Brew brew : brews) {
			System.out.printf("\nBrew ID: %s\n\tName: %s\n\tStyle: %s\n\tRating: %s\n\tDescription: %s\n\n", brew.getName(), brew.getStyle(),
					brew.getRating(), brew.getDescription());

			List<Batch> batches = brew.getBatches();
			for (Batch batch : batches)
				System.out.printf("Batch %s\n\tTotal bottles: %s\n\tAbv: %s\n\tBrew date: %s\n\tGravity: %s\n\n", batch.getBatchNumber(),
						batch.getPrivateBottles() + batch.getPublicBottles(), batch.getAbv(), batch.getBrewDate(),
						String.format("%s / %s", batch.getOriginalGravity(), batch.getFinalGravity()));

		}
	}
}