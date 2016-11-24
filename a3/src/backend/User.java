package backend;

import java.io.File;
import java.io.IOException;
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
	public List<ImageFile> getAllImages(File directory) {
		List<ImageFile> allImages = new ArrayList<>();

		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (checkImageType(file)) {
					allImages.add(new ImageFile(file));
				}
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
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}

	/**
	 * Adds selected tags to the file.
	 * 
	 * @param file
	 *            the image to tag
	 * @param tags
	 *            the tags to add image file
	 * @return true if image tagged successfully
	 */
	public boolean selectTag(ImageFile file, List<Tag> tags) {
		if (tags != null && file != null) {
			for (Tag t : tags) {
				if (!Tag.getAllTags().contains(t))
					return false;
			}
			file.addTag(tags);
			return true;
		} else {
			return false;
		}
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
	 * Remove tag from the selected image
	 * 
	 * @param selectedImage
	 *            the image to remove tag
	 * @param tag
	 *            the tag to remove from image
	 * @return true iff the tag removed from image successfully
	 */
	public boolean deleteTagFromImage(ImageFile selectedImage, Tag tag) {
		if (selectedImage != null && tag != null) {
			if (selectedImage.getTags().contains(tag)) {
				selectedImage.removeTag(tag);
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Reverts the name of the file to selected name
	 * 
	 * @param file
	 *            the file that is changed
	 * @param name
	 *            the name to revert back
	 */
	public void revertName(ImageFile file, String name) {
		if (file != null && !file.getName().equals(name)) {
			file.changeName(name);
		}
	}

	/**
	 * Helper function to check if the file type is image or not. Source:
	 * http://stackoverflow.com/questions/9643228/test-if-file-is-an-image
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
