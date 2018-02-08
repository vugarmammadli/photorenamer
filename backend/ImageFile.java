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
	}

	/**
	 * Returns pathname of ImageFile.
	 * 
	 * @return file which is full path of ImageFile in OS.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Returns nameHistory of file ordered by Date.
	 * 
	 * @return string representation of nameHistory
	 */
	public String getNameHistory() {
		String res = "";
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		// sorts name history according to date.
		List<Date> sortedKeys = new ArrayList<>();
		sortedKeys.addAll(this.nameHistory.keySet());
		Collections.sort(sortedKeys);

		for (Date date : sortedKeys) {
			res += "Date: " + format.format(date);
			res += " Name: " + this.nameHistory.get(date) + "\n";
		}
		return res;
	}

	/**
	 * Returns original name of the file.
	 * 
	 * @return the original name of this image file
	 */
	public String getOriginalName() {
		return this.originalName;
	}

	/**
	 * Returns tags of the image file.
	 * 
	 * @return the list of tags for this image file
	 */
	public List<Tag> getTags() {
		return this.tags;
	}

	/**
	 * Returns tag of this image by name.
	 * 
	 * @param tagName
	 *            Name of tag to return
	 * @return tag iff there is a tag with tagName.
	 */
	public Tag getTagOfImage(String tagName) {
		for (Tag t : this.getTags()) {
			if (t.getName().equals(tagName))
				return t;
		}
		return null;
	}

	/**
	 * Return allImageFiles
	 * 
	 * @return the list of all image files
	 */
	public static List<ImageFile> getAllImageFiles() {
		return allImageFiles;
	}

	/**
	 * Sets the value of allImageFiles
	 * 
	 * @param allImageFiles
	 *            new list of image files
	 */
	public static void setAllImageFiles(List<ImageFile> allImageFiles) {
		ImageFile.allImageFiles = allImageFiles;
	}

	/**
	 * Adds tag to this image file and renames the file according to tags.
	 * 
	 * @param tags
	 *            new list of tags to be added for this image file
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

	/**
	 * Removes tag from this image file and renames the file.
	 * 
	 * @param tag
	 *            the tag to be removed from this image file
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

	/**
	 * Reverts image name to a past name from name history.
	 * 
	 * @param name
	 *            past name in the name history to revert.
	 */
	public void changeName(String name) {
		if (this.nameHistory.values().contains(name)) {
			addLog(this.getName(), name);

			String path = this.file.getAbsolutePath().split(this.getName())[0];
			File newFile = new File(path + name);
			this.file.renameTo(newFile);
			this.file = newFile;
			this.nameHistory.put(new Date(), name);

			try {
				Configuration.saveImageFiles(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Helper function to change file name according to list of tags of the
	 * image.
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

		addLog(this.getName(), newFileName);

		// changes file name
		File newFile = new File(path + newFileName);
		this.file.renameTo(newFile);
		this.file = newFile;

		this.nameHistory.put(new Date(), newFileName);
	}

	/**
	 * Returns current name of the image.
	 * 
	 * @return the current name of ImageFile
	 */
	public String getName() {
		List<Date> allNameDates = new ArrayList<Date>(this.nameHistory.keySet());
		Collections.sort(allNameDates);
		Date lastDate = allNameDates.get(allNameDates.size() - 1);

		return this.nameHistory.get(lastDate);
	}

	/**
	 * Helper function to keeps a lot of all renaming ever done.
	 * 
	 * @param oldName
	 *            the name of the file before renaming
	 * @param newName
	 *            new name of the image file to change
	 */
	private void addLog(String oldName, String newName) {
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		try {
			simpleHandler = new FileHandler("./data/log.txt", true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		simpleHandler.setFormatter(new LogFormatter());
		logger.addHandler(simpleHandler);
		logger.log(Level.INFO,
				"Date: " + format.format(new Date()) + " Old name: " + oldName + " New name: " + newName);
		simpleHandler.close();
	}

	@Override
	/**
	 * Returns string representation of this image file
	 * 
	 * @return string representation of image file
	 */
	public String toString() {
		return "Image: " + this.getName();
	}

	@Override
	/**
	 * Returns true if two images files are equal according to name
	 * 
	 * @param other
	 *            ImageFile object to compare to
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
