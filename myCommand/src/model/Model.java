package model;

import java.io.File;
import java.util.ArrayList;

public interface Model {

	/**
	 * The function recursively add to arrayList all files names and directories names
	 * that in a given path;
	 * @param dir current path {@link File}
	 * @return all the paths of directories and files that in parameter file {@link ArrayList}
	 */
	public ArrayList<String> displayDirectoryContents(File dir);
}
