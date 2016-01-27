package model;


import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Observable;

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
import controller.Properties;
import demo.MazeAdapter;

public class MyModel extends Observable implements Model {

	private Controller controller;
	
	private HashMap<String, Maze3d> mazeHashMap;
	private HashMap<String, Solution<Position>> solutionsHashMap;
	private HashMap<Maze3d, Solution<Position>> cach;
	private String defaultSolveAlgorithm = "mazeManhattanDistance";
	
	
	public MyModel(/*Properties properties,*/Controller controller) {
		this.controller = controller;
		this.cach = new HashMap<Maze3d, Solution<Position>>();
		this.mazeHashMap = new HashMap<String, Maze3d>();
		this.solutionsHashMap = new HashMap<String, Solution<Position>>();
		//this.defaultSolveAlgorithm = properties.getDefaultSolver();
	}
	
	
	@Override
	public Maze3d generate3dmaze (Socket socket,String mazeName, int floor, int row, int column) { 
	
			if(!socket.isClosed())
				controller.updateStatus(socket, "Generating new Maze" + " | " + LocalDateTime.now().toString());
			Maze3dGenerator mg = new MyMaze3dGenerator();
			Maze3d maze = mg.generate(column, row, floor);	
			this.mazeHashMap.put(mazeName, maze);
			if(!socket.isClosed())
				controller.updateStatus(socket, "Finished generation of new Maze" + " | " + LocalDateTime.now().toString());
			return maze; 
	}

	@Override
	public Solution<Position> solve(Socket socket, String mazeName)  {
			
			Maze3d maze = mazeHashMap.get(mazeName);
			Searcher<Position> s;
			
		    String algorithm = this.defaultSolveAlgorithm;
		    if(!socket.isClosed())
		    	controller.updateStatus(socket, "Solve Maze With " + algorithm + " | " + LocalDateTime.now().toString());
		    
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
				throw new NullPointerException("There algorithm such " + algorithm + " noe found");
			}
			
			Solution<Position> sol = s.search(new MazeAdapter(maze));
			cach.put(mazeHashMap.get(mazeName), sol);
			solutionsHashMap.put(mazeName, sol);
			if(!socket.isClosed())
				controller.updateStatus(socket, "Finish Solve Maze" + algorithm + " | " + LocalDateTime.now().toString());
			
			return sol;
	}

}
