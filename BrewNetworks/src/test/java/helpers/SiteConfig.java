
package helpers;


import java.io.IOException;
import java.util.Properties;



public class SiteConfig {

	private static Properties config = new Properties();
	private static boolean loaded = false;


	public SiteConfig() {
		if (loaded) return;
		System.out.print("Loading site config file... ");
		try {
			config.load(Helper.class.getResourceAsStream("/siteConfig.properties"));
		} catch (IOException e) {
			System.out.println("Failed.");
			e.printStackTrace();
		}
		loaded = true;
		System.out.println("Done.");
	}


	/**
	 * @param category
	 * @param item
	 * @return value of a key (key = category.item)
	 */
	public String find(String category, String item) {
		return item == "" ? config.getProperty(category) : config.getProperty(category + "." + item);
	}
}