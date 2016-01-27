package view;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.State;
import presenter.Properties;

public class MainWindow extends Observable implements View {

	 private MazeWindow mazeWindow;
	
	
	public MainWindow(String title, int width, int height) {
		this.mazeWindow = new MazeWindow(title, width, height);
	
	
	// --- Sets Listeners that are specific to this project MVP architecture ---//
		
		// set Exit Listener
		this.mazeWindow.setExitListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				setChanged();
				ArrayList<String> args = new ArrayList<String>();
				args.add("exit");
				notifyObservers(args);
			}
		});
		
		// set key pressed listener
		this.mazeWindow.setKeyPressedListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// case page up pressed
				if(arg0.keyCode == SWT.PAGE_UP){
					// pass the presenter that page up key pressed
					ArrayList<String> args = new ArrayList<String>();
					args.add("move charachter");
					args.add(mazeWindow.getMazeName());
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getFloor()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getRow()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getColumn()));
					args.add("up");
					System.out.println("Charachter move UP!!! from" + mazeWindow.getCharchterPosition());
					setChanged();
					notifyObservers(args);
					
				}
				// case page down pressed
				if (arg0.keyCode == SWT.PAGE_DOWN) {
					// pass the presenter that page down key pressed
					ArrayList<String> args = new ArrayList<String>();
					args.add("move charachter");
					args.add(mazeWindow.getMazeName());
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getFloor()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getRow()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getColumn()));
					System.out.println("Charachter move DOWN !!! from" + mazeWindow.getCharchterPosition());
					args.add("down");
					setChanged();
					notifyObservers(args);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// set save maze Listener
		this.mazeWindow.setSaveMazeListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				SaveMazeWindow saveWin = new SaveMazeWindow("Save Maze In File", width, height, mazeWindow.getShell());
				saveWin.run();
				if(saveWin.isChangeSucceeded()){
					String filePath = saveWin.getMazePath();
					String newMazeName = mazeWindow.getMazeName();
					
					ArrayList<String> args = new ArrayList<String>();
					args.add("save maze");
					args.add(newMazeName);
					args.add(filePath);
					setChanged();
					notifyObservers(args);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// set load maze Listener
		this.mazeWindow.setLoadMazeListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				LoadPropertiesWindow loadWin = new LoadPropertiesWindow("Maze Load", width, height, mazeWindow.getShell());
				loadWin.run();
				if(loadWin.isChangeSucceeded()){
					String filePath = loadWin.getMazePath();
					String newMazeName = loadWin.getMazeNewName();
					
					ArrayList<String> args = new ArrayList<String>();
					args.add("load maze");
					args.add(filePath);
					args.add(newMazeName);
					setChanged();
					notifyObservers(args);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// generate maze listener
		this.mazeWindow.setGenerateMazeListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ArrayList<String> args = new ArrayList<String>();
				args.add("generate 3d maze");
				args.add(mazeWindow.getMazeName());
				args.add(String.valueOf(mazeWindow.getNumOfFloors()));
				args.add(String.valueOf(mazeWindow.getNumOfRows()));
				args.add(String.valueOf(mazeWindow.getNumOfColumns()));
				setChanged();
				notifyObservers(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// solve listener
		mazeWindow.setSolveListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				// update start positin in Model;
				ArrayList<String> args = new ArrayList<String>();
				args.add("update start position");
				args.add(mazeWindow.getMazeName());	
				args.add(String.valueOf(mazeWindow.getCharchterPosition().getFloor()));
				args.add(String.valueOf(mazeWindow.getCharchterPosition().getRow()));
				args.add(String.valueOf(mazeWindow.getCharchterPosition().getColumn()));
				setChanged();
				notifyObservers(args);
				//solve request
				args.clear();
				args.add("solve");
				args.add(mazeWindow.getMazeName());	
				// solve with the search algorithm that the user set in Properties file
				args.add(" ");
				setChanged();
				notifyObservers(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		// set properties
		mazeWindow.setOpenProperties(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				ClassAutoForm Propertieswin = new ClassAutoForm("set properties", Properties.class , mazeWindow.getShell());
				Propertieswin.run();
				if (Propertieswin.isSuccessfullyCreated()) {
					Properties properties = (Properties) Propertieswin.getNewCreatedClass();
					setChanged();
					notifyObservers(properties);
					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void start() {
		mazeWindow.run();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void displayArrayList(ArrayList<?> arrList) {
	try{
			this.displaySolution((ArrayList<State<Position>>) arrList);
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
		mazeWindow.setCharchterPosition(maze.getStartPosition());
		mazeWindow.setGoalPosition(maze.getGoalPosition().getRow(),maze.getGoalPosition().getColumn());
		int crossSectionByFloorIndex = maze.getStartPosition().getFloor();
		ArrayList<String> args = new ArrayList<String>();
		args.add("get floor information");
		args.add(mazeWindow.getMazeName());
		args.add(String.valueOf(crossSectionByFloorIndex));
		setChanged();
		notifyObservers(args);
	}

	@Override
	public void displayString(String str) {
		if(str == null)
			return;
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
		else if(str.startsWith("solution for ") && str.endsWith(" is ready")){
			String mazeName = str.split(" ")[2];
			ArrayList<String> args = new ArrayList<String>();
			args.add("display solution");
			args.add(mazeName);
			setChanged();
			notifyObservers(args);
		}
		// case allowd charcter movement reques
		else if(str.startsWith("the movement ") && str.endsWith(" is allowd")){
			String wantedMovement = str.split(" ")[2];
			System.out.println("Wanted novment: " + wantedMovement);
			Position charcterCurrentPos = mazeWindow.getCharchterPosition();
			if(wantedMovement.equals("up")){
				mazeWindow.setCharchterPosition(new Position(charcterCurrentPos.getColumn(),charcterCurrentPos.getRow(), charcterCurrentPos.getFloor() + 1));
			}
			if(wantedMovement.equals("down")){
				mazeWindow.setCharchterPosition(new Position(charcterCurrentPos.getColumn(),charcterCurrentPos.getRow(), charcterCurrentPos.getFloor() - 1));
			}
			
			int crossSectionByFloorIndex = mazeWindow.getCharchterPosition().getFloor();
			ArrayList<String> args = new ArrayList<String>();
			args.add("get floor information");
			args.add(mazeWindow.getMazeName());
			args.add(String.valueOf(crossSectionByFloorIndex));
			setChanged();
			notifyObservers(args);
		}
		else if (str.startsWith("the maze ") && str.contains(" has been loaded from file ")) {
			String mazeName = str.split(" ")[2];
			this.mazeWindow.setMazeName(mazeName);
			ArrayList<String> args = new ArrayList<String>();
			args.add("display");
			args.add(mazeName);
			setChanged();
			notifyObservers(args);
		}
		else if (str.startsWith("the start poistion for maze ")) {
			String mazeName = str.split(" ")[5];
			int indexOfPositionStringStarts = str.lastIndexOf("{");
			int indexOfPositionStringEnd = str.lastIndexOf("}");
			String position = str.substring(indexOfPositionStringStarts + 1, indexOfPositionStringEnd);
			String floor = position.split(",")[0];
			String row = position.split(",")[1];
			String column = position.split(",")[2];
			//update charachter start position
			mazeWindow.setCharchterPosition(new Position(Integer.parseInt(column), Integer.parseInt(row), Integer.parseInt(floor)));
			mazeWindow.setMazeName(mazeName);
			// requests from Presenter to give the cross by floor of the maze
			ArrayList<String> args = new ArrayList<String>();
			args.add("get floor information");
			args.add(mazeName);
			args.add(floor);
			setChanged();
			notifyObservers(args);
		}
		else if (str.startsWith("solution for ") && str.endsWith(" is ready")) {
			String mazeName = str.split(" ")[2];
			ArrayList<String> args = new ArrayList<String>();
			args.add(mazeName);
			args.add(mazeWindow.getMazeName());	
			setChanged();
			notifyObservers(args);
		}
		else{
			mazeWindow.messageToUser(str);
		}
	}

	@Override
	public void displayMatrix(int[][] matrix) {
		
		mazeWindow.displayCrossSectionOfMaze(matrix);
	}

	@Override
	public void displaySolution(ArrayList<State<Position>> solution) {
		
		for (int i = 0; i <solution.size(); i++) {
			mazeWindow.moveCharchter(solution.get(i).getState());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(i < solution.size() - 1){
				int currentFloor = solution.get(i).getState().getFloor();
				int nextFloor = solution.get(i + 1).getState().getFloor();
				// Case you need to go floor up
				if (currentFloor + 1 == nextFloor) {
					ArrayList<String> args = new ArrayList<String>();
					args.add("move charachter");
					args.add(mazeWindow.getMazeName());
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getFloor()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getRow()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getColumn()));
					args.add("up");
					System.out.println("Charachter move UP!!! from" + mazeWindow.getCharchterPosition());
					setChanged();
					notifyObservers(args);
				}
				if (currentFloor - 1 == nextFloor) {
					ArrayList<String> args = new ArrayList<String>();
					args.add("move charachter");
					args.add(mazeWindow.getMazeName());
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getFloor()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getRow()));
					args.add(String.valueOf(mazeWindow.getCharchterPosition().getColumn()));
					args.add("down");
					System.out.println("Charachter move UP!!! from" + mazeWindow.getCharchterPosition());
					setChanged();
					notifyObservers(args);
				}
			}
			
		}
		
	}

	@Override
	public void displayEror(String eror) {
		if(eror != null)
			mazeWindow.displayEror(eror);
	}

}
