package backend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageFile {

	String filePath;
	String fileName;
	HashMap<Date, String> nameHistory = new HashMap<>();
	ArrayList<String> tags = new ArrayList<String>();

	public ImageFile(String filePath, String fileName, HashMap<Date, String> nameHistory, ArrayList<String> tags) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.nameHistory = nameHistory;
		this.tags = tags;
	}

	/*
	 * Returns the path to the image file
	 * 
	 * @return path to the file
	 */
	public String getFilePath() {
		return filePath;
	}

	/*
	 * Sets the file's path
	 * 
	 * @param filePath filePath new path to be set for the image file
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/*
	 * Returns the name of the image file
	 * 
	 * @return name of file
	 */
	public String getFileName() {
		return fileName;
	}
	/*
	 * Sets the name of the image file
	 * 
	 * @param fileName new name to be set for the image file
	 */

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * Returns the array list nameHistory
	 * 
	 * @return the history of all the name changes for this image file
	 */
	public HashMap<Date, String> getNameHistory() {
		return nameHistory;
	}

	/*
	 * Returns the array list tags
	 * 
	 * @return the list of tags for this image file
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/*
	 * Adds a change to array list nameHistory
	 * 
	 * @param date Date that the change was made
	 * 
	 * @param newName New name that the name was changed to
	 */
	public void updateNameHistory(Date date, String newName) {
		this.nameHistory.put(date, newName);
	}

}
