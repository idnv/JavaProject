package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import algorithms.mazeGenerators.Maze3d;
import io.MyCompressorOutputStream;
/**
 * The class responsible of saving a maze {@link Maze3d} in file 
 */
public class SaveMazeInFile {

	/**
	 * The function save a exists maze {@link Maze3d} in a file
	 * @param maze The maze {@link Maze3d}
	 * @param fileName The path to save the maze
	 * @throws IOException for problems with file {@link File}
	 */
	public static void saveMazeInFile(Maze3d maze, String fileName) throws IOException
	{
		// check if file is exists, if not create him
		File file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		} 
		
		OutputStream out  = new MyCompressorOutputStream(new FileOutputStream(fileName));
		out.write(maze.toByteArr());
		out.flush();
		out.close();
	}
}
