package backend;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

/**
 * The class is in singleton pattern.
 * 
 * @author Vugar Mammadli
 *
 */
public class User {
	private static final User instance = new User();

	private User() {
	}

	public static User getInstance() {
		return instance;
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

		// recursively get all image files from the directory
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (checkImageType(file)) {
					allImages.add(new ImageFile(file));
				}
			} else if (file.isDirectory()) {
				allImages.addAll(getAllImages(file));
			}
		}

		// uploads all previously saved image files
		try {
			Configuration.uploadImageFiles();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		// if the image tagged before, sets image file to uploaded image file
		if (!ImageFile.getAllImageFiles().isEmpty()) {
			for (ImageFile f : ImageFile.getAllImageFiles()) {
				for (ImageFile f2 : allImages) {
					if (f.getName().equals(f2.getName()))
						allImages.set(allImages.indexOf(f2), f);
				}
			}
		}

		return allImages;
	}

	/**
	 * Creates a new tag with the given name
	 * 
	 * @param name
	 *            the name of the new tag
	 * @return true if the tag is not already exists and created successfully
	 */
	public boolean addTag(String name) {
		if (name.isEmpty() || name == null)
			return false;

		Tag newTag = new Tag(name);

		if (Tag.getAllTags().contains(newTag))
			return false;

		Tag.getAllTags().add(newTag);

		// saves the master list of tags.
		try {
			Configuration.saveTags();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return true;
	}

	/**
	 * Returns tag according to the name
	 * 
	 * @param name
	 *            the name of the tag
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
	 *            the image file to tag
	 * @param tags
	 *            the list of tags to add the image file
	 * @return true iff image tagged successfully
	 */
	public boolean selectTag(ImageFile file, List<Tag> tags) {
		if (tags != null && !tags.isEmpty() && file != null) {
			for (Tag t : tags) {
				if (!Tag.getAllTags().contains(t))
					return false;
			}
			file.addTag(tags);

			// saves the master list of tags.
			try {
				Configuration.saveTags();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return true;
		}

		return false;
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

		// saves the master list of tags.
		try {
			Configuration.saveTags();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return true;
	}

	/**
	 * Removes tag from the selected image file.
	 * 
	 * @param selectedImage
	 *            the image to remove tag from
	 * @param tag
	 *            the tag to remove from image
	 * @return true iff the tag removed from image successfully
	 */
	public boolean deleteTagFromImage(ImageFile selectedImage, Tag tag) {
		if (selectedImage != null && tag != null) {
			if (selectedImage.getTags().contains(tag)) {
				selectedImage.removeTag(tag);

				// saves the master list of tags.
				try {
					Configuration.saveTags();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				return true;
			} else
				return false;
		}

		return false;
	}

	/**
	 * Reverts the name of the file to selected name.
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
	 * Returns the most common tag.
	 * 
	 * @return the most common tag that files tagged.
	 */
	public Tag getMostCommonTag() {
		if (!Tag.getAllTags().isEmpty()) {
			Tag tag = Tag.getAllTags().get(0);
			for (Tag t : Tag.getAllTags()) {
				if (t.getNumUsed() > tag.getNumUsed())
					tag = t;
			}
			return tag;
		}
		return null;
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
