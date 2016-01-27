package model;

import java.net.Socket;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	
	/**
	 * The function generates a maze in thread and update controller when finished
	 * and save it compressed in file.
	 * @param name name of the maze
	 * @param floor number of floors
	 * @param row number of rows
	 * @param column number of columns
	 */
	public Maze3d generate3dmaze (Socket socket, String mazeName, int floor, int row, int column);
	/**
	 * The solve a maze {@link Maze3d} in a separate 
	 * @param mazeName name of maze {@link Maze3d}
	 * @param algorithm algorithm to solve the maze {@link String}
	 */
	public Solution<Position> solve (Socket socket, String mazeName);
	
}
