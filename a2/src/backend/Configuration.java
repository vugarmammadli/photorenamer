package backend;

import java.io.*;
import java.util.*;

public class Configuration {
	private static final String TAGS_PATH = "C:/Users/Vugar Mammadli/Desktop/csc207/group_0825/a2/src/data/tags.ser";
	private static final String IMAGEFILE_PATH = "C:/Users/Vugar Mammadli/Desktop/csc207/group_0825/a2/src/data/imageFiles/";
	
	public static void uploadTags() throws IOException, ClassNotFoundException{
		File f = new File(TAGS_PATH);
		if (f.exists()) {
			InputStream file = new FileInputStream(TAGS_PATH);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			Tag.setAllTags((List<Tag>) input.readObject());
		}
	}
	
	public static void saveTags() throws IOException{
		OutputStream file = new FileOutputStream(TAGS_PATH);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(Tag.getAllTags());
		output.close();
	}
	
	public static void uploadImageFiles() throws IOException, ClassNotFoundException{
		ImageFile.setAllImageFiles(new ArrayList<ImageFile>());
		File f = new File(IMAGEFILE_PATH);
		for(File eachFile : f.listFiles()){
			InputStream file = new FileInputStream(eachFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			ImageFile.getAllImageFiles().add(((ImageFile) input.readObject()));
		}
	}
	
	public static void saveImageFiles(ImageFile selectedImage) throws IOException{
		OutputStream file = new FileOutputStream(IMAGEFILE_PATH + selectedImage.getOriginalName() + ".ser");
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(selectedImage);
		output.close();
	}
}
