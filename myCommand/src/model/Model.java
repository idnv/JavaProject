package model;

import java.io.File;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import controller.Controller;

/**
 * Interface that sets behavior of Model
 * Responsible of calculation and algorithms of the program
 */
public interface Model {

	/**
	 * The function add to arrayList all files names and directories names
	 * that in a given File.
	 * the function give to the controller all files {@link ArrayList} 
	 * @param dir current path {@link File}
	 * 
	 */
	public void displayDirectoryContents(File dir);
	/**
	 * The function generates a maze in thread and update controller when finished
	 * and save it compressed in file.
	 * @param name name of the maze
	 * @param floor number of floors
	 * @param row number of rows
	 * @param column number of columns
	 */
	public void generate3dmaze (String name, int floor, int row, int column);
	/**
	 * The function give the Controller a maze {@link Maze3d} to display
	 * @param name name of maze
	 * 
	 */
	public void displayMaze(String name);
	/**
	 * The function gives the Controller {@link Controller} the cross by column of the maze
	 * @param name name of maze {@link Maze3d}
	 * @param index index of cross {@link Integer}
	 */
	public void getCrossByX(String name, int index);
	/**
	 * The function gives the Controller {@link Controller} the cross by floor of the maze
	 * @param name name of maze {@link Maze3d}
	 * @param index index of cross {@link Integer}
	 */
	public void getCrossByY(String name, int index);
	/**
	 * The function gives the Controller {@link Controller} the cross by row of the maze
	 * @param name name of maze {@link Maze3d}
	 * @param index index of cross {@link Integer}
	 */
	public void getCrossByZ(String name, int index);
	/**
	 * The function save a exists maze {@link Maze3d} in a file
	 * @param mazeName the name of the maze {@link Maze3d}
	 * @param fileName the name of file
	 */
	public void saveMazeInFile(String mazeName, String fileName);
	/**
	 * The function load a exists maze {@link Maze3d} from a file
	 * @param mazeName the name of the maze {@link Maze3d}
	 * @param fileName the name of file
	 */
	public void loadMazeFromFile(String mazeName, String fileName);
	/**
	 * The function gives the Controller {@link Controller} the memory size of a maze {@link Maze3d}
	 * @param name name of maze {@link String}
	 */
	public void displayMazeMemorySize(String name);
	/**
	 * The function gives to Controller {@link Controller} the file size of given maze
	 * @param fileName maze of maze {@link Maze3d}
	 */
	public void displayFileSize(String fileName);
	/**
	 * The solve a maze {@link Maze3d} in a separate 
	 * @param mazeName name of maze {@link Maze3d}
	 * @param algorithm algorithm to solve the maze {@link String}
	 */
	public void solve(String mazeName, String algorithm);
	/**
	 * The function gives the Controller {@link Controller} a solution to print
	 * @param solutionName the name of solution {@link Solution}
	 */
	public void displaySolution(String solutionName);
}
