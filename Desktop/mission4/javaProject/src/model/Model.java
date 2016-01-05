package model;

import java.io.IOException;

import algorithms.mazeGenerators.Position;

public interface Model {
	
	/**
	 * The function set the satrt position for the maze in DB
	 * @param name The name of the maze 
	 * @param pos The current position to update {@link Position}
	 */
	public void updateStatrPosition(String name, Position pos);
	/**
	 * The function calculate the optional moves from specific position
	 * @param name
	 * @param pos the current
	 * @param wantedMove The movement requestd to do
	 */
 	public void getPossibleMovesFromPosition(String name, Position pos, String wantedMove);
	/**
	 * The function add to arrayList all files names and directories names
	 * that in a given File.
	 * the function give to the controller all files {@link ArrayList} 
	 * @param dir current path {@link File}
	 * 
	 */
	public void displayDirectoryContents(String dir) throws NullPointerException;
	/**
	 * The function generates a maze in thread and update controller when finished
	 * and save it compressed in file.
	 * @param name name of the maze
	 * @param floor number of floors
	 * @param row number of rows
	 * @param column number of columns
	 */
	public void generate3dmaze (String name, int floor, int row, int column) throws Exception;
	/**
	 * The function give the Controller a maze {@link Maze3d} to display
	 * @param name name of maze
	 * 
	 */
	public void displayMaze(String name) throws Exception;
	/**
	 * The function gives the Controller {@link Controller} the cross by column of the maze
	 * @param name name of maze {@link Maze3d}
	 * @param index index of cross {@link Integer}
	 */
	public void getCrossBy(String name, int index, String dimension) throws IndexOutOfBoundsException, Exception;
	/**
	 * The function save a exists maze {@link Maze3d} in a file
	 * @param mazeName the name of the maze {@link Maze3d}
	 * @param fileName the name of file
	 */
	public void saveMazeInFile(String mazeName, String fileName) throws IOException, Exception;
	/**
	 * The function load a exists maze {@link Maze3d} from a file
	 * @param mazeName the name of the maze {@link Maze3d}
	 * @param fileName the name of file
	 */
	public void loadMazeFromFile(String mazeName, String fileName) throws IOException, Exception;
	/**
	 * The function gives the Controller {@link Controller} the memory size of a maze {@link Maze3d}
	 * @param name name of maze {@link String}
	 */
	public void displayMazeMemorySize(String name) throws Exception;
	/**
	 * The function gives to Controller {@link Controller} the file size of given maze
	 * @param fileName maze of maze {@link Maze3d}
	 */
	public void displayFileSize(String fileName) throws Exception;
	/**
	 * The solve a maze {@link Maze3d} in a separate 
	 * @param mazeName name of maze {@link Maze3d}
	 * @param algorithm algorithm to solve the maze {@link String}
	 */
	public void solve(String mazeName, String algorithm) throws Exception;
	/**
	 * The function gives the Controller {@link Controller} a solution to print
	 * @param solutionName the name of solution {@link Solution}
	 */
	public void displaySolution(String solutionName) throws Exception;
}
