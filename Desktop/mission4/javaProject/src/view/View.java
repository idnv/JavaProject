package view;

import java.util.ArrayList;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;



/**
 * Interface that sets behavior of view
 * Input\Output from user and to user
 */
public interface View {

	/**
	 * the function start the CLI start
	 */
	public void start();
	/**
	 * The function prints the parameter array (type ArrayList of String {@link String}){@link ArrayList}
	 * @param arr {@link ArrayList}
	 */
	public void displayArrayList(ArrayList <?> arrList);
	/**
	 * The function prints the parameter array (type ArrayList of String {@link String}){@link ArrayList}
	 * @param arr {@link ArrayList}
	 */
	public void displayArrayListOfString(ArrayList<String> arr);
	/**
	 * The function print to out source the maze
	 * @param maze The maze {@link Maze3d}
	 */
	public void displayMaze(Maze3d maze);
	/**
	 * The  function print to out source message for the user
	 * @param str - massage for user
	 */
	public void displayString(String str);
	/**
	 * Print to out source a matrix, 2d array of {@link Integer}
	 * @param matrix 2d array of {@link Integer}
	 */
	public void displayMatrix(int [][] matrix);
	/**
	 * The function print to out source the solution of a maze.
	 * @param solution solution of maze {@link ArrayList}
	 */
	public void displaySolution(ArrayList<State<Position>> solution);
	
	
}

