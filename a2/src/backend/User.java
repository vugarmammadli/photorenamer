package backend;

import java.io.File;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

public class User {

	public User() {

	}

	/**
	 * Returns list of all image files anywhere under the directory.
	 * 
	 * @param directory
	 *            folder to search for image files
	 * @return list of all image files
	 */
	public List<File> getAllImages(File directory) {
		List<File> allImages = new ArrayList<>();

		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (checkImageType(file))
					allImages.add(file);
			} else if (file.isDirectory()) {
				allImages.addAll(getAllImages(file));
			}
		}

		return allImages;
	}

	/**
	 * Creates new tag with the name
	 * 
	 * @param name
	 *            the name of new tag
	 * @return true if the tag is not already exists and created successfully
	 */
	public boolean addTag(String name) {
		Tag newTag = new Tag(name);

		if (Tag.getAllTags().contains(newTag))
			return false;

		Tag.getAllTags().add(newTag);
		return true;
	}

	/**
	 * Returns tag with the name
	 * 
	 * @param name
	 *            the name of the searched tag
	 * @return Tag if there is a tag with the name, otherwise null.
	 */
	public Tag getTag(String name) {
		for (Tag t : Tag.getAllTags()) {
			if (t.getName() == name)
				return t;
		}
		return null;
	}

	/**
	 * Removes tag from the list of all tags.
	 * 
	 * @param tag
	 *            the tag that should be removed
	 * @return true iff there is a tag and it is deleted successfully.
	 */
	public boolean deleteTag(Tag tag) {
		if (!Tag.getAllTags().contains(tag))
			return false;

		Tag.getAllTags().remove(tag);
		return true;
	}

	/**
	 * Helper function to check if the file type is image or not.
	 * 
	 * @param file
	 *            the file to be checked
	 * @return true if the file is image
	 */
	private boolean checkImageType(File file) {
		String mimetype = new MimetypesFileTypeMap().getContentType(file);

		if (mimetype != null && mimetype.split("/")[0].equals("image"))
			return true;

		return false;
	}
}
