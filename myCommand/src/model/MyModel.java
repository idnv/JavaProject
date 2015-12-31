package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import controller.Controller;
import demo.MazeAdapter;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * 
 *The class implements a Model {@link Model}
 *
 */
public class MyModel implements Model {

	private Controller c;
	private HashMap<String, Maze3d> hashMap;
	private HashMap<String, Solution<Position>> solutionsHashMap;
	
	public MyModel(Controller c) {
		this.c = c;
		this.hashMap = new HashMap<String, Maze3d>();
		this.solutionsHashMap = new HashMap<String, Solution<Position>>();
	}
	
	@Override
	public void displayDirectoryContents(File dir) {
		 ArrayList<String> arr = new  ArrayList<String>();
		 if(dir.exists()){
			 recursiveFindAllFilesAndDirs(dir,arr);
			 c.printStringArrayList(arr);
		 }
		 else
			 c.printToUser("The path " + dir.getPath() + " is not exists");
	}
	
	private void recursiveFindAllFilesAndDirs(File dir, ArrayList<String> arr) {
		 try {
				File[] files = dir.listFiles();
				for (File file : files) {
					if (file.isDirectory()) {
						arr.add("Dir: " +file.getCanonicalPath());
						recursiveFindAllFilesAndDirs(file, arr);
					} else {
						arr.add("File: " +file.getCanonicalPath());
					}
				}
			} catch (IOException e) {
				c.printToUser(e.getStackTrace().toString());
			}
	}
	
	@Override
	public void generate3dmaze(String mazeName, int floor, int row, int column) {
		
		if(!hashMap.containsKey(mazeName)){
			Maze3dGenerator mg = new MyMaze3dGenerator();
			new Thread(new Runnable() {
				@Override
				public void run() {
					hashMap.put(mazeName, mg.generate(floor, row, column));
					c.printToUser("the maze " + mazeName + " has created");
				}
				}).start();
			
			} else{
				c.printToUser("Error! the maze named " + mazeName + " is already exits");
			}

		}

	@Override
	public void displayMaze(String name) {
		if(hashMap.containsKey(name))
			c.printMaze3d(hashMap.get(name));
		else
			c.printToUser("Error! the maze " + name + " not found");
	}

	@Override
	public void getCrossByX(String name, int index) {
		if(hashMap.containsKey(name))
		{
			try{
			c.printMatrix(hashMap.get(name).getCrossSectionByX(index));
			} catch (IndexOutOfBoundsException e){
				c.printToUser("The index is out of bound");
			}
		}
		else
			c.printToUser("Error! the maze " + name + " not found");
	}

	@Override
	public void getCrossByY(String name, int index) {
		if(hashMap.containsKey(name))
		{
			try{
			c.printMatrix(hashMap.get(name).getCrossSectionByY(index));
			} catch (IndexOutOfBoundsException e){
				c.printToUser("The index is out of bound");
			}
		}
		else
			c.printToUser("Error! the maze " + name + " not found");
	}

	@Override
	public void getCrossByZ(String name, int index) {
		if(hashMap.containsKey(name))
		{
			try{
			c.printMatrix(hashMap.get(name).getCrossSectionByZ(index));
			} catch (IndexOutOfBoundsException e){
				c.printToUser("The index is out of bound");
			}
		}
		else
			c.printToUser("Error! the maze " + name + " not found");
	}

	@Override
	public void saveMazeInFile(String mazeName, String fileName) {
		
		if(hashMap.containsKey(mazeName)){
			try{
			Maze3d maze = hashMap.get(mazeName);	
			OutputStream out = new MyCompressorOutputStream(
				new FileOutputStream(fileName + ".txt", false));
			
			// check if file is exists, if not create him
			File file = new File(fileName +".txt");
			if(!file.exists()) {
				file.createNewFile();
			} 
			
			// write our maze to file filename.txt
				out.write(maze.toByteArr());
				out.flush();
				out.close();
				c.printToUser("the maze " + mazeName + " has been saved in " + fileName + ".txt file");
			} catch(IOException e){
				c.printToUser(e.getStackTrace().toString());
			}
		}
		else
			c.printToUser("Error! the maze " + mazeName + " not found");
	}

	@Override
	public void loadMazeFromFile(String mazeName, String fileName) {
		if(!hashMap.containsKey(mazeName)){
			File f = new File(fileName + ".txt");
			if(f.exists()){
				try{
					InputStream inStream = new MyDecompressorInputStream(
							new FileInputStream(fileName + ".txt"));
							byte [] arr = new byte[20];
							inStream.read(arr);
							inStream.close();
							int size = (((int)arr[6] * (int)arr[7] * (int)arr[8]) + 9);
							
				InputStream in=new MyDecompressorInputStream(
						new FileInputStream(fileName + ".txt"));
						byte b[]= new byte[size];
						in.read(b);
						in.close();
						hashMap.put(mazeName, new Maze3d(b));
					c.printToUser("the file " + fileName + " has been loaded and his name is " + mazeName );
				} catch(IOException e){
					c.printToUser(e.getStackTrace().toString());
				}
			}
			else
				c.printToUser("Error! the maze file " + fileName + " not found");
		}
		else
			c.printToUser("Error! A maze with the name " + mazeName + " is already existts");
	}
	
	@Override
	public void displayMazeMemorySize(String name) {
		if(hashMap.containsKey(name)){
			int sizeInBytes = 0;
			Maze3d maze = hashMap.get(name);
			sizeInBytes = maze.getColumn() * maze.getFloor()* maze.getRow() * (Integer.SIZE/8);
			sizeInBytes += 3 * (Integer.SIZE/8);
			sizeInBytes += 2 * 4;
			// gives the controller output to show
			c.printToUser("the size of " + name + "maze is: " + sizeInBytes + " Bytes");	
		}
		else
			// maze doesn't exist - pass the controller output to show
			c.printToUser("Error! the maze " + name + " not found");
	}

	@Override
	public void displayFileSize(String fileName) {
		File f = new File(fileName + ".txt");
		if(f.exists())
			c.printToUser("The size of " + fileName + " is: " + f.length());
		else
			c.printToUser("Error! the file " + fileName + " not found");
	}

	@Override
	public void solve(String mazeName, String algorithm){
		if(hashMap.containsKey(mazeName)){
			if(!this.solutionsHashMap.containsKey(mazeName + algorithm)){
				// run solve algorithm in thread
				new Thread(new Runnable() {
					@Override
					public void run() {
						Maze3d maze = hashMap.get(mazeName);
						Searcher<Position> s;
						if(algorithm.equals("BFS")){
							s = new BFS<Position>();	
						}
						else if(algorithm.equals("mazeAirDistance")){
							 s = new Astar<Position>(new MazeAirDistance(maze.getGoalPosition()));
						}
						else if(algorithm.equals("mazeManhattanDistance")){
							 s = new Astar<Position>(new MazeManhattanDistance(maze.getGoalPosition()));
						}
						else{
							c.printToUser("There algorithm such " + algorithm + " noe found");
							return;
						}
						Solution<Position> sol = s.search(new MazeAdapter(maze));
						solutionsHashMap.put(mazeName + algorithm, sol);
						c.printToUser("the solution with " + algorithm + "alorithm of maze "+ mazeName +" has been created");
					}
				}).start();
			}
			else{
				c.printToUser("The solution with " + algorithm + "alorithm of maze "+ mazeName +" is already exists");
			}
		}
		else
			c.printToUser("Error! the maze " + mazeName + " not found");
	}
	
	@Override
	public void displaySolution(String solutionName) {
		
		if(this.solutionsHashMap.containsKey(solutionName))
			c.printSolution(this.solutionsHashMap.get(solutionName).getSolutionList());
		else
			c.printToUser("Error! the solution " + solutionName + " not found");
	}
}