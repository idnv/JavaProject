package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;

public class MyModel implements Model {

	private Controller c;
	
	public MyModel(Controller c) {
		this.c = c;
	}
	

	@Override
	public ArrayList<String> displayDirectoryContents(File dir) {
		 ArrayList<String> arr = new  ArrayList<String>();
		 
		 try {
				File[] files = dir.listFiles();
				
				for (File file : files) {
					if (file.isDirectory()) {
						arr.add("Dir: " +file.getCanonicalPath());
						displayDirectoryContents(file);
					} else {
						arr.add("Dir: " +file.getCanonicalPath());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		 return arr;
	}
}
