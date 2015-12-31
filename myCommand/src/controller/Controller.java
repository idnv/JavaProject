package controller;


import java.io.File;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import model.Model;
import view.View;
/**
 * The class represents a Controller from MVC architecture design
 * The controller responsible of sends data between Model {@link Model} and View {@link View}
 *
 */
public interface Controller {
	/**
	 * set model
	 * @param m model {@link Model}
	 */
	public void setModel(Model m);
	/**
	 * Set view
	 * @param v view {@link View}
	 */
	public void setView(View v);
	/**
	 * Set the commands to view and CLI
	 */
	public void setViewCommand();
	//////////////////////////////////
	/**
	 * The function asks from View print to out source the maze
	 * @param maze The maze {@link Maze3d}
	 */
	public void printMaze3d(Maze3d maze);
	/**
	 * The  function asks from View print to out source message for the user
	 * @param str - massage for user
	 */
	public void printToUser(String str);
	/**
	 * The function asks from View print to out source a matrix, 2d array of {@link Integer}
	 * @param matrix 2d array of {@link Integer}
	 */
	public void printMatrix(int[][] matrix);
	/**
	 * The function asks from View print to out source the solution of a maze.
	 * @param solution solution of maze {@link ArrayList}
	 */
	public void printSolution(ArrayList<State<Position>> solution);
	/**
	 * The function call to function in View that prints the parameter array
	 * @param arr {@link ArrayList}
	 */
	public void printStringArrayList(ArrayList<String> arr);
	/////////////////////////////////////
	/**
	 * The function call to function in Model that finds all files names and directories names
	 * that in a given File.
	 * the function give to the controller all files {@link ArrayList} 
	 * @param dir current path {@link File}
	 * 
	 */
	public void displayDirectoryContents(File dir);
	/**
	 * The function ask from Model the controller to get all files and directory's
	 * of the parameter file
	 * @param name file - {@link File}
	 */
	public void displayMaze(String name);
	/**
	 * The function ask from Model to get the cross of maze in column
	 * @param name the name of maze {@link String}
	 * @param index index of cross section
	 */
	public void getCrossByX(String name, int index);
	/**
	 * The function ask from Model to get the cross of maze in floor
	 * @param name the name of maze {@link String}
	 * @param index index of cross section
	 */
	public void getCrossByY(String name, int index);
	/**
	 * The function ask from Model to get the cross of maze in row
	 * @param name the name of maze {@link String}
	 * @param index index of cross section
	 */
	public void getCrossByZ(String name, int index);
	/**
	 * Ask from Model the memory size of a maze {@link Maze3d}
	 * @param name maze name
	 */
	public void displayMazeMemorySize(String name);
	/**
	 * Ask from Model the File size of a maze {@link Maze3d}
	 * @param name file name
	 */
	public void displayFileSize(String fileName);
	/**
	 * The function ask from Model the solution of a maze
	 * @param solution solution of maze {@link ArrayList}
	 */
	public void displaySolution(String solutionName);
	////////////////////////////////////////////////
}