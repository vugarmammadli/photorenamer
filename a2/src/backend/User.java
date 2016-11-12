package backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

public class User {
	
	public User(){
		
	}
	
	/**
	 * Returns list of all image files anywhere under the directory.
	 * @param directory folder to search for image files
	 * @return list of all image files
	 */
	public List<File> getAllImages(File directory){
		List<File> allImages = new ArrayList<>();
		
		for(File file : directory.listFiles()){
			if(file.isFile()){
				if(checkImageType(file))
					allImages.add(file);
			} else if (file.isDirectory()){
				allImages.addAll(getAllImages(file));
			}
		}
		
		return allImages;
	}
	
	/**
	 * Helper function to check if the file type is image or not.
	 * @param file the file to be checked
	 * @return true if the file is image
	 */
	private boolean checkImageType(File file){
		String mimetype = new MimetypesFileTypeMap().getContentType(file);
		
		if(mimetype != null && mimetype.split("/")[0].equals("image"))
			return true;
		
		return false;
	}
}
