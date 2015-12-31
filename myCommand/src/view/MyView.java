package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import controller.Command;
import controller.Controller;

/**
 * The class implements a View {@link View}
 * 
 */
public class MyView implements View {

	private Controller c;
	private PrintWriter out;
	private CLI cli;
	
	public MyView(Controller c, BufferedReader in, PrintWriter out) {
		this.c = c;
		this.out = out;
	    this.cli = new CLI(in, out);
	}

	@Override
	public void start() {
		this.cli.runTaskInThread();
	}

	@Override
	public void printStringArrayList(ArrayList<String> arr) {
		for(String str : arr){
			out.println(str);
			out.flush();
		}
			
	}

	@Override
	public void printMaze(Maze3d maze) {
		for (int py = 0; py < maze.getFloor(); py++) {
				out.println();
				out.flush();
			for (int pz = 0; pz < maze.getRow(); pz++) {
				out.println();
				out.flush();
				for (int px = 0; px < maze.getColumn(); px++) {
					out.print(maze.getValue(px, pz, py));
					out.flush();
				}
			}
		}
		out.println();
		out.flush();
	}

	@Override
	public void printToUser(String str) {
		out.println(str);
		out.flush();
	}

	@Override
	public void printMatrix(int[][] matrix) {
		
		out.println();
		for(int [] y : matrix){
			for(int m : y){
				out.print(m);
				out.flush();
			}
				out.println();
				out.flush();
		}
		out.println();
		out.flush();
	}

	@Override
	public void printSolution(ArrayList<State<Position>> solution) {
		for(State<Position> st : solution){
			out.println(st.getState());
			out.flush();
		}
	}

	@Override
	public void displayDirectoryContents(File dir) {
		c.displayDirectoryContents(dir);
	}

	@Override
	public void displayMaze(String name) {
		c.displayMaze(name);
	}

	@Override
	public void getCrossByX(String name, int index) {
		c.getCrossByX(name, index);
	}

	@Override
	public void getCrossByY(String name, int index) {
		c.getCrossByY(name, index);
	}

	@Override
	public void getCrossByZ(String name, int index) {
		c.getCrossByZ(name, index);
	}

	@Override
	public void displayMazeMemorySize(String name) {
		c.displayMazeMemorySize(name);
	}

	@Override
	public void displayFileSize(String fileName) {
		c.displayFileSize(fileName);
	}

	@Override
	public void displaySolution(String solutionName) {
		c.displaySolution(solutionName);
		
	}

	@Override
	public void setCLICommand(HashMap<String, Command> commands) {
		this.cli.setCommands(commands);
	}
	
}
