package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import algorithms.mazeGenerators.Maze3d;
import io.MyDecompressorInputStream;
/**
 * The class responsible of loading a compressed maze {@link Maze3d} in file 
 * @author Idan
 */
public class LoadMazeFromFile {

	/**
	 * The function load a exists maze {@link Maze3d} from a file (read and decompressed)
	 * @param newMazeName The new name of the maze {@link Maze3d} to set
	 * @param fileName The path to read from
	 * @return the loaded maze {@link Maze3d}
	 * @throws IOException for problems with writing to files\open new files and etc.
	 * @throws NullPointerException for not existing File
	 */
	public static Maze3d loadMazeFromFile(String newMazeName, String fileName) throws IOException, NullPointerException{
		
		File f = new File(fileName + ".txt");
		if(!f.exists())
			throw new NullPointerException("Error! the maze file " + fileName + " not found");
		// Calculate size of decompressed array
		InputStream inStream = new MyDecompressorInputStream(new FileInputStream(fileName + ".txt"));
		byte [] arr = new byte[20];
		inStream.read(arr);
		inStream.close();
		int size = (((int)arr[6] * (int)arr[7] * (int)arr[8]) + 9);
		//Load maze					
		InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName + ".txt"));
		byte decompressedMazeByteArr[]= new byte[size];
		in.read(decompressedMazeByteArr);
		in.close();
		
		return new Maze3d(decompressedMazeByteArr);
	}
}
