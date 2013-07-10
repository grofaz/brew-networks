
package helpers;


import java.io.IOException;
import java.util.Properties;



public class UIMap {

	private static Properties uimap = new Properties();
	private static boolean loaded = false;


	public UIMap() {
		if (loaded) return;
		System.out.print("Loading uimap file... ");
		try {
			uimap.load(Helper.class.getResourceAsStream("/uimap.properties"));
		} catch (IOException e) {
			System.out.print("Failed.");
			e.printStackTrace();
		}
		loaded = true;
		System.out.println("Done.");
	}


	/**
	 * @param category
	 * @param item
	 * @return the value of the key (category+item)
	 */
	public String find(String category, String item) {
		return item == "" ? uimap.getProperty(category) : uimap.getProperty(category + "." + item);
	}
}