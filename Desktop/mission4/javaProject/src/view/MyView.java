package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;

public class MyView extends Observable implements View, Observer {

	CLI cli;
	BufferedReader in;
	PrintWriter out;

	public MyView(CLI cli, BufferedReader in, PrintWriter out) {
		this.out = out;
		this.in = in;
		this.cli = cli;
	}
	
	public MyView(BufferedReader in, PrintWriter out) {
		this.out = out;
		this.in = in;
		cli = new CLI(in, out);
	}
	
	public void displayArrayList(ArrayList<?> arr) {	
		for(Object str : arr){
			out.println(str);
			out.flush();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o==cli){
			setChanged();
			notifyObservers(arg);
		}	
	}
	
	@Override
	public void start() {
		cli.runTaskInThread();
	}

	@Override
	public void displayArrayListOfString(ArrayList<String> arr) {	
		for(String str : arr){
			out.println(str);
			out.flush();
		}
	}

	@Override
	public void displayMaze(Maze3d maze) {
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
	public void displayString(String str) {
		out.println(str);
		out.flush();
	}

	@Override
	public void displayMatrix(int[][] matrix) {
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
	public void displaySolution(ArrayList<State<Position>> solution) {
		for(State<Position> st : solution){
			out.println(st.getState());
			out.flush();
		}
	}

	@Override
	public void displayEror(String eror) {
		out.println(eror);
		out.flush();
	}

	
}