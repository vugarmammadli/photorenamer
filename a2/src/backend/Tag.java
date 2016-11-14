package backend;

import java.io.Serializable;
import java.util.*;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1022090674498375140L;
	private String name;
	private int numUsed;
	private static List<Tag> allTags = new ArrayList<>();

	public Tag(String name) {
		this.name = name;
		this.numUsed = 0;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumUsed() {
		return this.numUsed;
	}

	public void setNumUsed(int numUsed) {
		this.numUsed = numUsed;
	}

	public static List<Tag> getAllTags() {
		return allTags;
	}

	public static void setAllTags(List<Tag> allTags) {
		Tag.allTags = allTags;
	}

	@Override
	public String toString() {
		return "Tag: " + name;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Tag))
			return false;
		if (other == this)
			return true;

		return (((Tag) other).getName().equals(this.name));
	}
}
