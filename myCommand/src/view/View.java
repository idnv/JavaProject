package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import controller.Command;

/**
 * Interface that sets behavior of view
 * Input\Output from user and to user
 */
public interface View {
	public void setCLICommand(HashMap<String,Command> commands);
	///////////////////////////////////////////
	/**
	 * the function start the CLI start
	 */
	public void start();
	/**
	 * The function prints the parameter array (type ArrayList of String {@link String}){@link ArrayList}
	 * @param arr {@link ArrayList}
	 */
	public void printStringArrayList(ArrayList<String> arr);
	/**
	 * The function print to out source the maze
	 * @param maze The maze {@link Maze3d}
	 */
	public void printMaze(Maze3d maze);
	/**
	 * The  function print to out source message for the user
	 * @param str - massage for user
	 */
	public void printToUser(String str);
	/**
	 * Print to out source a matrix, 2d array of {@link Integer}
	 * @param matrix 2d array of {@link Integer}
	 */
	public void printMatrix(int [][] matrix);
	/**
	 * The function print to out source the solution of a maze.
	 * @param solution solution of maze {@link ArrayList}
	 */
	public void printSolution(ArrayList<State<Position>> solution);
	//////////////////////////////////////////////////
	/**
	 * The function call to function in Model that finds all files names and directories names
	 * that in a given File.
	 * the function give to the controller all files {@link ArrayList} 
	 * @param dir current path {@link File}
	 * 
	 */
	public void displayDirectoryContents(File dir);
	/**
	 * The function ask from the controller to get all files and directory's
	 * of the parameter file
	 * @param name file - {@link File}
	 */
	public void displayMaze(String name);
	/**
	 * The function ask from the controller to get the cross of maze in column
	 * @param name the name of maze {@link String}
	 * @param index index of cross section
	 * @param dimention  on which dimension set the crossBy 
	 */
	public void getCrossBy(String name, int index, String dimension);
	/**
	 * Ask from the controller the memory size of a maze {@link Maze3d}
	 * @param name maze name
	 */
	public void displayMazeMemorySize(String name);
	/**
	 * Ask from the controller the File size of a maze {@link Maze3d}
	 * @param name file name
	 */
	public void displayFileSize(String fileName);
	/**
	 * The function ask from the controller the solution of a maze
	 * @param solution solution of maze {@link ArrayList}
	 */
	public void displaySolution(String solutionName);
}

