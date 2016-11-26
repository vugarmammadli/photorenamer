package backend;

import java.io.Serializable;
import java.util.*;

public class Tag implements Serializable {
	private static final long serialVersionUID = 1022090674498375140L;
	private String name;
	private int numUsed;
	private static List<Tag> allTags = new ArrayList<>();

	public Tag(String name) {
		this.name = name;
		this.numUsed = 0;
	}

	/**
	 * Returns name of tag
	 * 
	 * @return String name of tag
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of tag given String name
	 * 
	 * @param name
	 *            String to replace name of tag
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value of numUsed
	 * 
	 * @return the value of numUsed
	 */
	public int getNumUsed() {
		return this.numUsed;
	}

	/**
	 * Sets the value of numUsed
	 * 
	 * @param numUsed
	 *            new value of numUsed
	 */
	public void setNumUsed(int numUsed) {
		this.numUsed = numUsed;
	}

	/**
	 * Returns allTags
	 * 
	 * @return list of all tags
	 */
	public static List<Tag> getAllTags() {
		return allTags;
	}

	/**
	 * Sets the value of allTags
	 * 
	 * @param allTags
	 *            new list of tags to replace allTags
	 */
	public static void setAllTags(List<Tag> allTags) {
		Tag.allTags = allTags;
	}

	@Override
	/**
	 * Returns string representation of a tag.
	 * 
	 * @return string representation of tag
	 */
	public String toString() {
		return "Tag: " + name;
	}

	@Override
	/**
	 * Returns if two tags are equal according to name
	 * 
	 * @param other
	 *            Tag object to compare to
	 * 
	 * @return true if two tags are same
	 */
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Tag))
			return false;
		if (other == this)
			return true;

		return (((Tag) other).getName().equals(this.name));
	}
}
