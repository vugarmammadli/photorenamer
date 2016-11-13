package backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tag {
	ArrayList<String> tagList = new ArrayList<String>();

	public Tag(ArrayList<String> tagList) {
		this.tagList = tagList;
	}
	/*
	 * Returns the array list tagList
	 */
	public ArrayList<String> getTagList() {
		return tagList;
	}
	/*
	 * Adds a String tag to the current tagList if and only if tag 
	 * is not in tagList
	 * 
	 * @param tag  String to be added as a tag
	 */
	public void addTagList(String tag) {
		// Check to see if the tag is already in the list of tags
		if (this.tagList.contains(tag) == false) {
			this.tagList.add(tag);
		}
	}
	/*
	 * Checks if there exists a text file called tagList.txt
	 * that represents tagList. If it exists, an error message will be printed. 
	 * If it does not exist, it will create a new text file called tagList.txt
	 * 
	 * @param directory  folder to search for the text file tagList.txt
	 */

	public void checkTagTextFile(File directory) {
		 /* Optional
		  * Boolean check = false;
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (file.getName() == "tagList.txt") {
					check = true;
				}
			}
		}
		 if (check == false) { */
		
			File tagFile = new File(directory.getPath() + "tagList.txt");
			try {
				tagFile.createNewFile();
			} catch (IOException e) {
				System.out.println("File already exists.");
			}
		// }
	}
	/*
	 * Updates the text file tagList.txt to match tagList. 
	 * Error message will only run if checkTagTextFile has not been executed. 
	 * 
	 * @param tag  String to be added to the text file tagList.txt 
	 */
	public void updateTagFile(String tag) {
		try 
		{
			String fileName = "tagList.txt";
			FileWriter fw = new FileWriter(fileName,true);
			fw.write(tag);
			fw.close();
		}
		catch (IOException e) {
			System.out.println("checkTagTextFile has not been run");
		}
		
	}
	/*
	 * Returns the string representation of tagList by concatenating all 
	 * elements of tagList
	 * 
	 * @return string representation of tagList
	 */

	public String tagListToString() {
		String tags = "";
		for (String tag : tagList) {
			tags = tags + tag + ", ";
		}
		tags.trim();
		return tags.substring(0, tags.length() - 1);
	}
	/*
	 * Returns number of tags in tagList
	 * 
	 * @return number of elements in tagList
	 */

	public int getCount() {
		return this.tagList.size();
	}
}
