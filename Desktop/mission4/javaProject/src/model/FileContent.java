package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class responsible on returning all files(their path) that in a file {@link File}
 */
public class FileContent {

	/**
	 * The function add to arrayList all files names and directories names
	 * that in a given file {@link File}
	 * @param path The path of file {@link File}
	 * @return  ArrayList of String  {@link ArrayList}. The content of the file {@link File} that found
	 * @throws IOException of problems with files
	 * @throws NullPointerException of file {@link File} is null
	 */
	public static ArrayList<String> fileContent(String path) throws NullPointerException{
		
		ArrayList<String> contentData = new  ArrayList<String>();
		// Initialize new File
		File folder = new File(path);
		// check if the path is exists in OS
		if(!folder.exists())
			throw new NullPointerException("Path not exists");
		// return all files that in the path 
		File[] listOfFiles = folder.listFiles();
		// adds all files to the arrayList
		for (File file : listOfFiles) {
			if (file.isDirectory()) 
				contentData.add("Dir: " +file.toString());
			else 
				contentData.add("File: " +file.toString());
		}
		
		return contentData;
	}
}