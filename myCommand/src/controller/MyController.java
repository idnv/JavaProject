package controller;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import model.Model;
import view.View;
/**
 *  The class implements a View {@link Controller}s
 *
 */
public class MyController implements Controller {

	protected Model m;
	protected View v;
	private HashMap<String,Command> commands;
	
	public MyController() {
		//TODO: add commands in hashmap
		this.commands = new HashMap<String,Command>();
	}
	
	/**
	 * @return the m
	 */
	public Model getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(Model m) {
		this.m = m;
	}

	/**
	 * @return the v
	 */
	public View getV() {
		return v;
	}

	/**
	 * @param v the v to set
	 */
	public void setV(View v) {
		this.v = v;
	}

	/**
	 * @return the commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	@Override
	public void setModel(Model m) {
		this.m = m;
	}

	@Override
	public void setView(View v) {
		this.v = v;
	}

	@Override
	public void printMaze3d(Maze3d maze) {
		v.printMaze(maze);
	}

	@Override
	public void printToUser(String eror) {
		v.printToUser(eror);
	}

	@Override
	public void printMatrix(int[][] matrix) {
		v.printMatrix(matrix);
	}

	@Override
	public void printSolution(ArrayList<State<Position>> solution) {
		v.printSolution(solution);
	}

	@Override
	public void displayDirectoryContents(File dir) {
		m.displayDirectoryContents(dir);
	}

	@Override
	public void displayMaze(String name) {
		m.displayMaze(name);
	}

	@Override
	public void getCrossByX(String name, int index) {
		m.getCrossByX(name, index);
	}

	@Override
	public void getCrossByY(String name, int index) {
		m.getCrossByY(name, index);
	}

	@Override
	public void getCrossByZ(String name, int index) {
		m.getCrossByZ(name, index);
	}

	@Override
	public void displayMazeMemorySize(String name) {
		m.displayMazeMemorySize(name);
	}

	@Override
	public void displayFileSize(String fileName) {
		m.displayFileSize(fileName);
	}

	@Override
	public void displaySolution(String solutionName) {
		m.displaySolution(solutionName);
	}

	@Override
	public void printStringArrayList(ArrayList<String> arr) {
		v.printStringArrayList(arr);
	}

	@Override
	public void setViewCommand() {
		commands.put("dir", new DirCommand(v, m));
		commands.put("generate 3d maze", new Generate3dMazeCommand(v, m));
		commands.put("display", new DisplayCommand(v, m));
		commands.put("display cross section by X", new GetCrossByXCommand(v, m));
		commands.put("display cross section by Y", new GetCrossByYCommand(v, m));
		commands.put("display cross section by Z", new GetCrossByZCommand(v, m));
		commands.put("save maze", new SaveMazeCommand(v, m));
		commands.put("load maze", new LoadMazeCommand(v, m));
		commands.put("maze size", new MazeSizeCommand(v, m));
		commands.put("file size", new FileSizeCommand(v, m));
		commands.put("solve", new SolveCommand(v, m));
		commands.put("display solution", new DisplaySolutionCommand(v, m));
		v.setCLICommand(this.getCommands());
	}
}
