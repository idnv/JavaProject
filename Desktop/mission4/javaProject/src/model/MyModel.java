package model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import demo.MazeAdapter;
import io.MyDecompressorInputStream;
import presenter.Properties;

public class MyModel extends Observable implements Model {

	private HashMap<String, Maze3d> mazeHashMap;
	private HashMap<String, Solution<Position>> solutionsHashMap;
	private ExecutorService threadPool;
	Socket theServer;
	///////////Properties ///////
	String serverIP = "127.0.0.1" ;
	int portServerIsListene = 3750;
	private String defaultSolveAlgorithm;
	
	
	public MyModel(Properties properties) {
		this.mazeHashMap = new HashMap<String, Maze3d>();
		this.solutionsHashMap = new HashMap<String, Solution<Position>>();
		this.threadPool = Executors.newFixedThreadPool(properties.getNumOfThreadsInThreadPool());
		this.defaultSolveAlgorithm = properties.getDefaultSolver();
		this.serverIP = properties.getIPOfServer();
		this.portServerIsListene = properties.getPortServerIsListening();
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
			
		    // Creats new connection to server
			theServer = new Socket(serverIP, portServerIsListene);
			theServer.setSoTimeout(80000);
			
			// Define Stream of connection
			ObjectOutputStream objectToServer = new ObjectOutputStream(theServer.getOutputStream());
			ObjectInputStream objectFromServer = new ObjectInputStream(theServer.getInputStream());
			
			// request from server to generate new maze
			String str = "generate 3d maze " + mazeName + " " + floor + " " + row + " " + column;
			objectToServer.writeObject(str);
			objectToServer.flush();
			
			// read maze
			Maze3d maze = (Maze3d)objectFromServer.readObject();
			// close connection with the server
			theServer.close();
			
			if(maze != null){
				// save new maze at hashmap
				mazeHashMap.put(mazeName, maze);
				// notify observers
				setChanged();
				System.out.println(theServer.isConnected() + "1!!!!!!!!!!!!!");
				notifyObservers("the maze " + mazeName + " has created");
			}
			else{
				//throw new SocketException("Server is not connect - you got  a kick");
				System.out.println("maze is null kick!");
				throw new Exception("Server is not connect - you got  a kick");
			}
				
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
		notifyObservers("the maze " + mazeName + " has been loaded from file " + fileName );
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
	public void solve(String mazeName, String algorithm) throws NullPointerException {
		if(!mazeHashMap.containsKey(mazeName))
			throw new NullPointerException("Error! the maze " + mazeName + " not found");
		
			if(this.solutionsHashMap.containsKey(mazeName)){
				setChanged();
				notifyObservers("The solution with " + algorithm + "alorithm of maze "+ mazeName +" is ready");
			}
			
			
				// run solve algorithm in thread
			threadPool.execute(new Runnable() {
					@Override
					public void run() {
						try{
							
							// Creats new connection to server
							theServer = new Socket(serverIP, portServerIsListene);
							theServer.setSoTimeout(800000);
							
							// Define Stream of connection
							ObjectOutputStream objectToServer = new ObjectOutputStream(theServer.getOutputStream());
							ObjectInputStream objectFromServer = new ObjectInputStream(theServer.getInputStream());
							
							// request from server to solve the maze
							String str = "solve " + mazeName + " " + algorithm;
							objectToServer.writeObject(str);
							objectToServer.flush();
							
							// read maze
							@SuppressWarnings("unchecked")
							Solution<Position> solution = (Solution<Position>)objectFromServer.readObject();
							// close connection with the server
							theServer.close();
							// save new solution at hashmap
							solutionsHashMap.put(mazeName, solution);
							// notify pressenter
							setChanged();
							notifyObservers("solution for " + mazeName +" is ready");
						}catch(IOException e){
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						};
					}
				});
	}
	
	@Override
	public void displaySolution(String solutionName) throws NullPointerException {
		
		if(!this.solutionsHashMap.containsKey(solutionName))
			throw new NullPointerException("Error! the solution " + solutionName + " not found");
		
		setChanged();
		notifyObservers(this.solutionsHashMap.get(solutionName).getSolutionList());
	}

	@Override
	public void getPossibleMovesFromPosition(String name, Position pos, String wantedMove) throws NullPointerException {
		if(!mazeHashMap.containsKey(name))
			throw new NullPointerException("Error! the maze " + name + " not found");
		Maze3d maze = mazeHashMap.get(name);
		String [] possibleMoves = maze.getPossibleMoves(pos);
		for(String str : possibleMoves){
			if (str.equals(wantedMove)) {
				setChanged();
				notifyObservers("the movement " + wantedMove + " is allowd");
				return;
			}
		}
		// not allowd to do the movement
		return;
	}

	@Override
	public void updateStatrPosition(String name, Position pos) throws Exception {
		if(!mazeHashMap.containsKey(name))
			throw new Exception("Error! the maze " + name + " not found");
		mazeHashMap.get(name).setStartPosition(pos);
		setChanged();
		notifyObservers("finish update start position for maze " + name);
	}

	@Override
	public void getStatrPosition(String name) throws NullPointerException{
		if(!mazeHashMap.containsKey(name))
			throw new NullPointerException("Error! the maze " + name + " not found");
		setChanged();
		notifyObservers("the start poistion for maze " + name + " is " + mazeHashMap.get(name).getStartPosition());
		
	}

	@Override
	public void saveAndUpdateProperties(Properties properties) throws Exception {
		
		this.defaultSolveAlgorithm = properties.getDefaultSolver();
		this.portServerIsListene = properties.getPortServerIsListening();
		this.serverIP = properties.getIPOfServer();
		setChanged();
		notifyObservers("Properties Updated Successfully");
		
	}

	@Override
	public void getCrossByFloorWithInformation(String name, int index) throws IndexOutOfBoundsException, Exception {
		if(!mazeHashMap.containsKey(name))
			throw new Exception("Error! the maze " + name + " not found");
		
		int [][]floorCrossSectionWithInfo = mazeHashMap.get(name).floorCrossSectionInfo(index);
		setChanged();
		notifyObservers(floorCrossSectionWithInfo);
	}

	@Override
	public void exit() {
		try {
			threadPool.shutdown();
			// if not  executor terminated 
			if (!threadPool.awaitTermination(3, TimeUnit.SECONDS)) {
				threadPool.shutdownNow();
				System.out.println("Thread pool has been shut down NOW");
			}
			System.out.println("Thread pool has been shut down: " + threadPool.isShutdown());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers("exit");
	}
}
