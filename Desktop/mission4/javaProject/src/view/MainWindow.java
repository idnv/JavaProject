package view;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;

public class MainWindow extends Observable implements View {

	 private MazeWindow mazeWindoe;
	
	
	public MainWindow(String title, int width, int height) {
		this.mazeWindoe = new MazeWindow(title, width, height);
	
	
	
	

	// -------------------------------------- Sets Listeners that are specific to this project MVP architecture------------------------- //
		
		// set Exit Listener
		this.mazeWindoe.setExitListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				setChanged();
				notifyObservers("exit");
			}
		});
		
		// set load maze Listener
		this.mazeWindoe.setLoadMazeListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(mazeWindoe.shell,SWT.OPEN);
				String mazePath = fd.open();
				if(mazePath!=null){
					setChanged();
					notifyObservers("load maze " +mazeWindoe.getFilePathToLoad() + " " + mazeWindoe.getLoadFunctionNewMazeName());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		// generate maze listener
		this.mazeWindoe.setGenerateMazeListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ArrayList<String> args = new ArrayList<String>();
				args.add("generate 3d maze");
				args.add(mazeWindoe.getMazeName());
				args.add(String.valueOf(mazeWindoe.getNumOfFloors()));
				args.add(String.valueOf(mazeWindoe.getNumOfRows()));
				args.add(String.valueOf(mazeWindoe.getNumOfColumns()));
				setChanged();
				notifyObservers(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// solve listener
		mazeWindoe.setSolveListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ArrayList<String> args = new ArrayList<String>();
				args.add("solve");
				args.add(mazeWindoe.getMazeName());	
				args.add(" ");
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void start() {
		mazeWindoe.run();
	}
	
	@Override
	public void displayArrayList(ArrayList<?> arrList) {
	try{
			mazeWindoe.newGeneratedSolution((ArrayList<State<Position>>) arrList);
		}
		catch(IllegalFormatException e){
			e.printStackTrace();
		}

	}

	@Override
	public void displayArrayListOfString(ArrayList<String> arr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d maze) {
		int crossSectionByFloorIndex = maze.getStartPosition().getFloor();
		ArrayList<String> args = new ArrayList<String>();
		args.add("display cross section by");
		args.add("Y");
		args.add(String.valueOf(crossSectionByFloorIndex));
		args.add(mazeWindoe.getMazeName());
		setChanged();
		notifyObservers(args);
	}

	@Override
	public void displayString(String str) {
		// case new maze generated
		if(str.startsWith("the maze ") && str.endsWith(" has created")){
			String mazeName = str.split(" ")[2];
			ArrayList<String> args = new ArrayList<String>();
			args.add("display");
			args.add(mazeName);
			setChanged();
			notifyObservers(args);
		}
		// case new solution had calculated
		if(str.startsWith("solution for ") && str.endsWith(" is ready")){
			String mazeName = str.split(" ")[2];
			ArrayList<String> args = new ArrayList<String>();
			args.add("display solution");
			args.add(mazeName);
			setChanged();
			notifyObservers(args);
			
		}
	}

	@Override
	public void displayMatrix(int[][] matrix) {
		
		mazeWindoe.newGeneratedMaze(matrix);
	}

	@Override
	public void displaySolution(ArrayList<State<Position>> solution) {
		// TODO Auto-generated method stub

	}

}
