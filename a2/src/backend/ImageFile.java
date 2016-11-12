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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HashMap<Date, String> getNameHistory() {
		return nameHistory;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

}
