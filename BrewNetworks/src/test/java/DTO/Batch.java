
package DTO;

public class Batch {

	private int batchNum;
	private int privateBottles;
	private int publicBottles;
	private double abv;
	private double ibu;
	private String color;
	private String brewDate;
	private String bottleDate;
	private double bottleRating;
	private double originalGravity;
	private double finalGravity;


	/**
	 * @param attributeName
	 * @param value
	 */
	public void setAttribute(String attributeName, String value) {
		if (attributeName.contains("batchNum")) setBatchNumber(value);
		else if (attributeName.contains("privateBottles")) setPrivateBottles(value);
		else if (attributeName.contains("publicBottles")) setPublicBottles(value);
		else if (attributeName.contains("abv")) setAbv(value);
		else if (attributeName.contains("ibu")) setIbu(value);
		else if (attributeName.contains("color")) setColor(value);
		else if (attributeName.contains("brewDate")) setBrewDate(value);
		else if (attributeName.contains("bottleDate")) setBottleDate(value);
		else if (attributeName.contains("bottleRating")) setBottleRating(value);
		else if (attributeName.contains("originalGravity")) setOriginalGravity(value);
		else if (attributeName.contains("finalGravity")) setFinalGravity(value);
	}


	/**
	 * @return the privateBottles
	 */
	public int getPrivateBottles() {
		return privateBottles;
	}


	/**
	 * @param privateBottles
	 *          the privateBottles to set
	 */
	public void setPrivateBottles(String privateBottles) {
		this.privateBottles = Integer.parseInt(privateBottles);
	}


	/**
	 * @return the publicBottles
	 */
	public int getPublicBottles() {
		return publicBottles;
	}


	/**
	 * @param publicBottles
	 *          the publicBottles to set
	 */
	public void setPublicBottles(String publicBottles) {
		this.publicBottles = Integer.parseInt(publicBottles);
	}


	/**
	 * @return the abv
	 */
	public double getAbv() {
		return abv;
	}


	/**
	 * @param abv
	 *          the abv to set
	 */
	public void setAbv(String abv) {
		this.abv = Double.parseDouble(abv.contains("%") ? abv.replace("%", "") : abv);
	}


	/**
	 * @return the ibu
	 */
	public double getIbu() {
		return ibu;
	}


	/**
	 * @param ibu
	 *          the ibu to set
	 */
	public void setIbu(String ibu) {
		this.ibu = Double.parseDouble(ibu);
	}


	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}


	/**
	 * @param color
	 *          the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}


	/**
	 * @return the brewDate
	 */
	public String getBrewDate() {
		return brewDate;
	}


	/**
	 * @param brewDate
	 *          the brewDate to set
	 */
	public void setBrewDate(String brewDate) {
		this.brewDate = brewDate;
	}


	/**
	 * @return the bottleDate
	 */
	public String getBottleDate() {
		return bottleDate;
	}


	/**
	 * @param bottleDate
	 *          the bottleDate to set
	 */
	public void setBottleDate(String bottleDate) {
		this.bottleDate = bottleDate;
	}


	/**
	 * @return the originalGravity
	 */
	public double getOriginalGravity() {
		return originalGravity;
	}


	/**
	 * @param originalGravity
	 *          the originalGravity to set
	 */
	public void setOriginalGravity(String originalGravity) {
		this.originalGravity = Double.parseDouble(originalGravity);
	}


	/**
	 * @return the finalGravity
	 */
	public double getFinalGravity() {
		return finalGravity;
	}


	/**
	 * @param finalGravity
	 *          the finalGravity to set
	 */
	public void setFinalGravity(String finalGravity) {
		this.finalGravity = Double.parseDouble(finalGravity);
	}


	/**
	 * @return the bottleRating
	 */
	public double getBottleRating() {
		return bottleRating;
	}


	/**
	 * @param bottleRating
	 *          the bottleRating to set
	 */
	public void setBottleRating(String bottleRating) {
		this.bottleRating = Double.parseDouble(bottleRating.contains("N/A") ? "0.0" : bottleRating);
	}


	/**
	 * @return the batchNum
	 */
	public int getBatchNumber() {
		return batchNum;
	}


	/**
	 * @param batchNum
	 *          the batchNum to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNum = Integer.parseInt(batchNumber);
	}

}