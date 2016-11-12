package backend;

import java.util.ArrayList;

public class Tag {
	ArrayList<String> tagList = new ArrayList<String>();

	public Tag(ArrayList<String> tagList) {
		this.tagList = tagList;
	}

	public ArrayList<String> getTagList() {
		return tagList;
	}

	public void addTagList(String tag) {
		// Check to see if the tag is already in the list of tags
		if (this.tagList.contains(tag) == false) {
			this.tagList.add(tag);
		}
	}

	@Override
	public String toString() {
		String tags = "";
		for (String tag : tagList) {
			tags = tags + tag + ", ";
		}
		tags.trim();
		return tags.substring(0, tags.length() - 1);
	}

	public int getCount() {
		return this.tagList.size();
	}
}
