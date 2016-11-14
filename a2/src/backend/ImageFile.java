package backend;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

public class ImageFile implements Serializable {
	private static final long serialVersionUID = 4147923517015278579L;
	private File file;
	private String originalName;
	private Map<Date, String> nameHistory;
	private List<Tag> tags;
	private static List<ImageFile> allImageFiles = new ArrayList<ImageFile>();

	private static final Logger logger = Logger.getLogger(ImageFile.class.getName());
	private static FileHandler simpleHandler;

	public ImageFile(File file) {
		this.file = file;
		this.originalName = file.getName();
		this.nameHistory = new HashMap<Date, String>();
		nameHistory.put(new Date(), file.getName());
		this.tags = new ArrayList<Tag>();

		try {
			simpleHandler = new FileHandler("src/data/log.txt", true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		simpleHandler.setFormatter(new LogFormatter());
		logger.addHandler(simpleHandler);
	}

	/*
	 * Returns nameHistory in a friendly format
	 * 
	 * @return string representation of nameHistory
	 */
	public String getNameHistory() {
		String res = "";
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		for (Date date : this.nameHistory.keySet()) {
			res += "Date: " + format.format(date);
			res += " Name: " + this.nameHistory.get(date) + "\n";
		}
		return res;
	}

	/*
	 * Returns originalName
	 * 
	 * @return the original name of this image file
	 */
	public String getOriginalName() {
		return this.originalName;
	}

	/*
	 * Returns tags
	 * 
	 * @return the list of tags for this image file
	 */
	public List<Tag> getTags() {
		return this.tags;
	}

	/*
	 * Return allImageFiles
	 * 
	 * @return the list of all image files
	 */
	public static List<ImageFile> getAllImageFiles() {
		return allImageFiles;
	}

	/*
	 * Sets the value of allImageFiles
	 * 
	 * @param allImageFiles new list of image files
	 */
	public static void setAllImageFiles(List<ImageFile> allImageFiles) {
		ImageFile.allImageFiles = allImageFiles;
	}

	/*
	 * Adds tag to this image file. Renames the file
	 * 
	 * @param tags new set of tags to be added for this image file
	 */
	public void addTag(List<Tag> tags) {
		for (Tag tag : tags) {
			if (!this.tags.contains(tag)) {
				this.tags.add(tag);
				tag.setNumUsed(tag.getNumUsed() + 1);
			}
		}
		renameFile();
		try {
			Configuration.saveImageFiles(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Removes tag from this image file
	 * 
	 * @param tag new tag to be removed for this image file
	 */
	public void removeTag(Tag tag) {
		if (this.tags.contains(tag)) {
			this.tags.remove(tag);
			tag.setNumUsed(tag.getNumUsed() - 1);
		}

		renameFile();

		try {
			Configuration.saveImageFiles(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Reverts to a past name
	 * 
	 * @param name past name to revert to
	 */
	public void changeName(String name) {
		if (this.nameHistory.values().contains(name)) {
			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			logger.log(Level.INFO,
					"Date: " + format.format(new Date()) + " Old name: " + this.getName() + " New name: " + name);
			simpleHandler.close();
			String path = this.file.getAbsolutePath().split(this.getName())[0];
			File newFile = new File(path + name);
			this.file.renameTo(newFile);
			this.file = newFile;
			this.nameHistory.put(new Date(), name);
		}
	}

	/*
	 * Helper function to change file name according to tags
	 */
	private void renameFile() {
		String fileName = this.originalName;

		// creates new file name with previous one and new tags
		String newFileName = fileName.split("\\.")[0];
		for (Tag tag : this.tags) {
			newFileName += " @" + tag.getName();
		}
		newFileName += "." + fileName.split("\\.")[1];

		// path of current file
		String path = this.file.getAbsolutePath().split(this.getName())[0];

		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		logger.log(Level.INFO,
				"Date: " + format.format(new Date()) + " Old name: " + this.getName() + " New name: " + newFileName);
		simpleHandler.close();
		// changes file name
		File newFile = new File(path + newFileName);
		this.file.renameTo(newFile);
		this.file = newFile;

		this.nameHistory.put(new Date(), newFileName);
	}

	/*
	 * Returns current name
	 * 
	 * @return the current name of ImageFile
	 */
	public String getName() {
		List<Date> allNameDates = new ArrayList<Date>(this.nameHistory.keySet());
		Collections.sort(allNameDates);
		Date lastDate = allNameDates.get(allNameDates.size() - 1);

		return this.nameHistory.get(lastDate);
	}

	@Override
	/*
	 * Returns string representation of this image file
	 * 
	 * @return string representation of image file
	 */
	public String toString() {
		return "Image: " + this.getName();
	}

	@Override
	/*
	 * Returns true if two images files are equal according to name
	 * 
	 * @param other ImageFile object to compare to
	 * 
	 * @return true two ImageFile are same
	 */
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ImageFile))
			return false;
		if (other == this)
			return true;

		return (((ImageFile) other).file == this.file && ((ImageFile) other).getName().equals(this.getName()));
	}
}
