package backend;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;

public class PhotoRenamer {

	public static void main(String[] args) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				Configuration.uploadTags();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
			try {
				Configuration.uploadImageFiles();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
			File file = fileChooser.getSelectedFile();
			User user = new User();
			List<ImageFile> allFiles = user.getAllImages(file);
			
			if(!ImageFile.getAllImageFiles().isEmpty()){
				for(ImageFile f : ImageFile.getAllImageFiles()){
					for(ImageFile f2 : allFiles){
						if(f.getName().equals(f2.getName()))
							allFiles.set(allFiles.indexOf(f2), f);
					}
				}
			}
			
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Welcome to PhotoRenamer!");
			System.out.println("---------------------------");
			System.out.println("1. See all tags");
			System.out.println("2. Add new tag");
			System.out.println("3. Delete tag");
			System.out.println("4. View all images");
			System.out.println("5. Tag image");
			System.out.println("6. Delete tag from image");
			System.out.println("7. Change name to previous one");
			System.out.println("0. Quit");
			System.out.println("---------------------------");
			System.out.println("Please select one from menu: ");
			
			int option = scan.nextInt();
			
			while(option != 0){
				
				if(option == 1){
					System.out.println("List of all tags: ");
					System.out.println(Tag.getAllTags());
					System.out.println("---------------------------");
				}
				
				if(option == 2){
					System.out.println("Please add the name of new tag: ");
					String tag = scan.nextLine();
					tag = scan.nextLine();
					user.addTag(tag);
					System.out.println("List of all tags: ");
					System.out.println(Tag.getAllTags());
					System.out.println("---------------------------");
				}
				
				if(option == 3){
					System.out.println("Please enter the name of the tag: ");
					String name = scan.nextLine();
					name = scan.nextLine();
					user.deleteTag(user.getTag(name));
					System.out.println("List of all tags: ");
					System.out.println(Tag.getAllTags());
					System.out.println("---------------------------");
				}
				
				if(option == 4){
					System.out.println("List of all images: ");
					System.out.println(user.getAllImages(file));
					System.out.println("---------------------------");
				}
				
				if(option == 5){
					System.out.println("List of all images: ");
					for(int i = 1; i <= allFiles.size(); i++){
						System.out.println(i + ". " + allFiles.get(i - 1));
					}
					System.out.println("Please enter id of image to tag: ");
					int imageId = scan.nextInt();
					
					System.out.println("List of all tags: ");
					System.out.println(Tag.getAllTags());
					
					System.out.println("Please enter the name of tags: ");
					String tagName = scan.nextLine();
					tagName = scan.nextLine();
					
					List<Tag> selectedTags = new ArrayList<>();
					selectedTags.add(user.getTag(tagName));
					
					while(!tagName.equals("done")){
						System.out.println("Please enter the name of tags: ");
						System.out.println("Enter 'done' to finish tagging.");
						tagName = scan.nextLine();
						if(!tagName.equals("done"))
							selectedTags.add(user.getTag(tagName));
					}
					
					ImageFile selectedImage = allFiles.get(imageId - 1);
					
					if(!ImageFile.getAllImageFiles().isEmpty()){
						for(ImageFile f : ImageFile.getAllImageFiles()){
							if(f.getName().equals(selectedImage.getName()))
								selectedImage = f;
						}
					}
					
					user.selectTag(selectedImage, selectedTags);
					
					System.out.println("New file name: " + selectedImage.getName());
					System.out.println("---------------------------");
				}
				
				if(option == 6){
					System.out.println("List of all images: ");
					for(int i = 1; i <= allFiles.size(); i++){
						System.out.println(i + ". " + allFiles.get(i - 1));
					}
					System.out.println("Please enter id of image to remove tag: ");
					int imageId = scan.nextInt();
					ImageFile selectedImage = allFiles.get(imageId - 1);
					
					System.out.println("List of all tags this image have: ");
					System.out.println(selectedImage.getTags());
					
					System.out.println("Please enter the name of tag to remove: ");
					String tagName = scan.nextLine();
					tagName = scan.nextLine();
					
					Tag selectedTag = selectedImage.getTagOfImage(tagName);
					user.deleteTagFromImage(selectedImage, selectedTag);
					
					System.out.println("New file name: " + selectedImage.getName());
					System.out.println("List of all tags this image have: ");
					System.out.println(selectedImage.getTags());
					
					System.out.println("---------------------------");
				}
				
				if(option == 7){
					System.out.println("List of all images: ");
					for(int i = 1; i <= allFiles.size(); i++){
						System.out.println(i + ". " + allFiles.get(i - 1));
					}
					System.out.println("Please enter id of image to change name: ");
					int imageId = scan.nextInt();
					
					ImageFile selectedImage = allFiles.get(imageId - 1);
					
					System.out.println(selectedImage.getNameHistory());
					
					System.out.println("Enter name you want to revert: ");
					String name = scan.nextLine();
					name = scan.nextLine();
					
					user.revertName(selectedImage, name);
					
					System.out.println("New file name: " + selectedImage.getName());
					System.out.println("---------------------------");
				}
				
				
				System.out.println("1. See all tags");
				System.out.println("2. Add new tag");
				System.out.println("3. Delete tag");
				System.out.println("4. View all images");
				System.out.println("5. Tag image");
				System.out.println("6. Delete tag from image");
				System.out.println("7. Change name to previous one");
				System.out.println("0. Quit");
				System.out.println("---------------------------");
				System.out.println("Please select one from menu: ");
				option = scan.nextInt();
			}
				
			try {
				Configuration.saveTags();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
