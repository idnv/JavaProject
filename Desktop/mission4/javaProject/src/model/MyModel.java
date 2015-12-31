package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import demo.MazeAdapter;
import presenter.Properties;

public class MyModel extends Observable implements Model {

	private HashMap<String, Maze3d> mazeHashMap;
	private HashMap<String, Solution<Position>> solutionsHashMap;
	private HashMap<Maze3d, Solution<Position>> cach;
	private ExecutorService threadPool;
	public MyModel(Properties properties) {
		this.cach = new HashMap<Maze3d, Solution<Position>>();
		this.mazeHashMap = new HashMap<String, Maze3d>();
		this.solutionsHashMap = new HashMap<String, Solution<Position>>();
		this.threadPool = Executors.newFixedThreadPool(properties.getNumOfThreadsInThreadPool());
	}
	
	@Override
	public void displayDirectoryContents(String path) throws NullPointerException{
		ArrayList<String> content = new ArrayList<String>();
		content = FileContent.fileContent(path);
		setChanged();
		notifyObservers(content);
	}
	
	@Override
	public void generate3dmaze(String mazeName, int floor, int row, int column) throws Exception { 
		
		if(mazeHashMap.containsKey(mazeName))
			throw new Exception("Error! the maze named " + mazeName + " is already exits");
			
			Maze3dGenerator mg = new MyMaze3dGenerator();
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					mazeHashMap.put(mazeName, mg.generate(floor, row, column));
					setChanged();
					notifyObservers("the maze " + mazeName + " has created");
				}
				});
	}

	@Override
	public void displayMaze(String name) throws Exception {
		if(mazeHashMap.containsKey(name)){
			setChanged();
			notifyObservers(mazeHashMap.get(name));
		}
		else
			throw new Exception("Error! the maze " + name + " not found");
	}

	@Override
	public void getCrossBy(String name, int index, String dimension)  throws  IndexOutOfBoundsException, Exception {
		//TODO change get cross to throw IndexOutOfBoundsException with information
		if(!mazeHashMap.containsKey(name))
			throw new Exception("Error! the maze " + name + " not found");
		
		int[][] crossSection = null;
			switch (dimension) {
			case "X":
					crossSection = mazeHashMap.get(name).getCrossSectionByX(index);
					setChanged();
					notifyObservers(crossSection);
					break;
			case "Y":		
				crossSection = mazeHashMap.get(name).getCrossSectionByY(index);
				setChanged();
				notifyObservers(crossSection);
				break;
			case "Z":
				crossSection = mazeHashMap.get(name).getCrossSectionByZ(index);
				setChanged();
				notifyObservers(crossSection);
				break;
				default:
						throw new Exception("Dimension not valid");
			}
	}

	@Override
	public void saveMazeInFile(String mazeName, String fileName) throws IOException, NullPointerException {
		
		if(!mazeHashMap.containsKey(mazeName))
			throw new NullPointerException("Error! the maze " + mazeName + " not found");
		
			Maze3d maze = mazeHashMap.get(mazeName);
			SaveMazeInFile.saveMazeInFile(maze, fileName);
			setChanged();
			notifyObservers("the maze " + mazeName + " has been saved in " + fileName + ".txt file");
	}

	@Override
	public void loadMazeFromFile(String mazeName, String fileName) throws Exception, IOException, NullPointerException {
		if(mazeHashMap.containsKey(mazeName))
			throw new Exception("Error! A maze with the name " + mazeName + " is already existts");
		// load maze
		Maze3d loadedMaze = LoadMazeFromFile.loadMazeFromFile(mazeName, fileName);
		// update hashmap
		mazeHashMap.put(mazeName, loadedMaze);
		setChanged();
		notifyObservers("the file " + fileName + " has been loaded and his name is " + mazeName );
	}
	
	@Override
	public void displayMazeMemorySize(String name) throws NullPointerException {
		if(!mazeHashMap.containsKey(name))
			throw new NullPointerException("Error! the maze " + name + " not found");
		
			int sizeInBytes = 0;
			Maze3d maze = mazeHashMap.get(name);
			sizeInBytes = maze.getColumn() * maze.getFloor()* maze.getRow() * ((Integer.SIZE)/8);
			sizeInBytes += (3 * ((Integer.SIZE)/8));
			sizeInBytes += 2 * 4;//(size of reference) - the 2 Positions
			setChanged();
			notifyObservers("the size of " + name + "maze is: " + sizeInBytes + " Bytes");	
	}

	@Override
	public void displayFileSize(String fileName) throws NullPointerException {
		File f = new File(fileName + ".txt");
		if(!f.exists())
			throw new NullPointerException("Error! the file " + fileName + " not found");
		
			setChanged();
			notifyObservers("The size of " + fileName + " is: " + f.length());

	}

	@Override
	public void solve(String mazeName, String algorithm) throws Exception {
		if(!mazeHashMap.containsKey(mazeName))
			throw new Exception("Error! the maze " + mazeName + " not found");
		
			if(this.solutionsHashMap.containsKey(mazeName)){
				setChanged();
				notifyObservers("The solution with " + algorithm + "alorithm of maze "+ mazeName +" is ready");
			}
			
			Maze3d maze = mazeHashMap.get(mazeName);
			Searcher<Position> s;
			
			switch (algorithm) {
			case "BFS":
				s = new BFS<Position>();	
				break;
			case "mazeAirDistance":
				 s = new Astar<Position>(new MazeAirDistance(maze.getGoalPosition()));
				break;
			case "mazeManhattanDistance":
				s = new Astar<Position>(new MazeManhattanDistance(maze.getGoalPosition()));
				break;
			default:
				throw new Exception("There algorithm such " + algorithm + " noe found");
			}
				// run solve algorithm in thread
				new Thread(new Runnable() {
					@Override
					public void run() {
						
						Solution<Position> sol = s.search(new MazeAdapter(maze));
						cach.put(mazeHashMap.get(mazeName), sol);
						solutionsHashMap.put(mazeName, sol);
						clearChanged();
						notifyObservers("the solution with " + algorithm + "alorithm of maze "+ mazeName +" has been created");
					}
				}).start();
	}
	
	@Override
	public void displaySolution(String solutionName) throws NullPointerException {
		
		if(!this.solutionsHashMap.containsKey(solutionName))
			throw new NullPointerException("Error! the solution " + solutionName + " not found");
		
		setChanged();
		notifyObservers(this.solutionsHashMap.get(solutionName).getSolutionList());
	}
}
