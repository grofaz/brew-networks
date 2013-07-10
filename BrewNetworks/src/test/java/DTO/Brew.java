
package DTO;


import java.util.List;



public class Brew {

	private String name;
	private int id;
	private String style;
	private double rating;
	private String description;
	private List<Batch> batches;


	/**
	 * @param attributeName
	 * @param value
	 */
	public void setAttribute(String attributeName, String value) {
		if (attributeName.contains("id")) setId(value);
		else if (attributeName.contains("name")) setName(value);
		else if (attributeName.contains("style")) setStyle(value);
		else if (attributeName.contains("rating")) setRating(value);
		else if (attributeName.contains("description")) setDescription(value);
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param string
	 *          the id to set
	 */
	public void setId(String string) {
		this.id = Integer.parseInt(string);
	}


	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}


	/**
	 * @param style
	 *          the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}


	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}


	/**
	 * @param rating
	 *          the rating to set
	 */
	public void setRating(String rating) {
		this.rating = Double.parseDouble(rating.contains("N/A") ? "0.0" : rating);
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description
	 *          the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the batches
	 */
	public List<Batch> getBatches() {
		return batches;
	}


	/**
	 * @param list
	 *          the batches to set
	 */
	public void setBatches(List<Batch> list) {
		this.batches = list;
	}

}