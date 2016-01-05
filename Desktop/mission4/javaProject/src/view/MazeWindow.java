package view;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import presenter.Properties;

public class MazeWindow extends BasicWindow {

	Timer timer;
	TimerTask task;
	int[][] currentCrossedMaze;
	String mazeName;
	String fileToSave;
	String filePathToLoad;
	String loadFunctionNewMazeName;
	String numOfRows,numOfColumns,numOfFloors;
	MazeDisplayer mazeDisplayer;
	
	/**
	 * generateMazeListener set what to do when the user requests to generate new maze in a file (click the generate maze button)
	 */
	SelectionListener generateMazeListener;
	/**
	 * clueListener set what to do when the user requests a clue (click the clue button)
	 */
	SelectionListener clueListener;
	/**
	 * solveListener set what to do when the user requests to auto solve the maze (click the solve button)
	 */
	SelectionListener solveListener;
	/**
	 * solveListener set what to do when the user requests to save the maze in a file (click the save maze button)
	 */
	SelectionListener saveMazeListener;
	/**
	 * solveListener set what to do when the user requests to load the maze from a file (click the load maze button)
	 */
	SelectionListener loadMazeListener;
	/**
	 * exitListener set what to do when the user requests to exit the program (click the solve button)
	 */
	DisposeListener exitListener;
	
	MenuItem mazeGetSoulutionItem, mazeGetHintItem;
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	//Menu menuBar, fileMenu, helpMenu;
	//MenuItem fileMenuHeader helpMenuHeader;
	
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2,false));
		
		// ------------------- Menu Bar -------------------------- //
		
		Menu menuBar, fileMenu, helpMenu, mazeMenu;
		MenuItem fileHeader ,helpHeader, mazeHeader;
		MenuItem fileExitItem,fileLoadXMLItem;
		//MenuItem mazeGetSoulutionItem, mazeGetHintItem;
		MenuItem helpGameInstructions;
		
		menuBar = new Menu(shell, SWT.BAR);
		
		fileHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileHeader.setText("File");
		mazeHeader = new MenuItem(menuBar, SWT.CASCADE);
		mazeHeader.setText("Maze");
		helpHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpHeader.setText("Help");
		
		fileMenu = new Menu(shell,SWT.DROP_DOWN);
		fileHeader.setMenu(fileMenu);
		
		helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpHeader.setMenu(helpMenu);
		
		mazeMenu = new Menu(shell, SWT.DROP_DOWN);
		mazeHeader.setMenu(mazeMenu);
		
		
		// ---------------------------------------- LoadXML (at menu bar) ----------------------------- //
		fileLoadXMLItem = new MenuItem(fileMenu,SWT.PUSH);
		fileLoadXMLItem.setText("Load XML File");
		
		// ---------------------------------------- Exit (at menu bar) ----------------------------- //
		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("Exit");
		// ---------------------------------------- GetSolution (at menu bar) ----------------------------- //
		mazeGetSoulutionItem = new MenuItem(mazeMenu, SWT.PUSH);
		mazeGetSoulutionItem.setText("Get Soluton");
		mazeGetSoulutionItem.setEnabled(false);
		mazeGetSoulutionItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				solveListener.widgetSelected(arg0);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		// ---------------------------------------- GetHint (at menu bar) ----------------------------- //
		mazeGetHintItem = new MenuItem(mazeMenu, SWT.PUSH);
		mazeGetHintItem.setText("Get Hint");
		mazeGetHintItem.setEnabled(false);
		// ---------------------------------------- GameInstructions(at menu bar) ----------------------------- //
		helpGameInstructions = new MenuItem(helpMenu,SWT.PUSH);
		helpGameInstructions.setText("Game Instructions");					
		shell.setMenuBar(menuBar);
		// ---------------------- generateNewMazeButton -------------------------------//
		
		Button generateNewMazeButton=new Button(shell, SWT.PUSH);
		generateNewMazeButton.setText("Generate New Maze");
		generateNewMazeButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		generateNewMazeButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			//	Properties p = new Properties();
				MazeProperties mazePropertiesWin= new MazeProperties("maze example",500,500, shell);
				mazePropertiesWin.run();
			
				setMazeName(mazePropertiesWin.getMazeName()); 
				setNumOfFloors(mazePropertiesWin.getNumOfFloors());
				setNumOfRows(mazePropertiesWin.getNumOfRows());
				setNumOfColumns(mazePropertiesWin.getNumOfColumns());
				
				System.out.println("ROWS" + numOfRows);
				System.out.println("Col" + numOfColumns);;
				System.out.println("F" + numOfFloors);;
				generateMazeListener.widgetSelected(arg0);
			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
	
		// -----------------MazeDisplayer------------------------- //
		//MazeDisplayer maze=new Maze2D(shell, SWT.BORDER);		
		mazeDisplayer=new Maze3dDisplayByFloor(shell, SWT.BORDER);
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
		
	}

	public void newGeneratedSolution(ArrayList<State<Position>> arr){
		
	}
	public void newGeneratedMaze(int[][] crossedMaze){
		this.currentCrossedMaze = crossedMaze;
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if(mazeDisplayer == null)
					mazeDisplayer = new Maze3dDisplayByFloor(shell,SWT.BORDER);
				else{
					mazeDisplayer.setMazeData(currentCrossedMaze);
					mazeDisplayer.redraw();
				}
				mazeGetSoulutionItem.setEnabled(true);
				mazeGetHintItem.setEnabled(true);
			}
		});
		
	}

	private void refreshPositions(ArrayList<State<Position>> arr){
		for(State<Position> p : arr)
			mazeDisplayer.setCharacterPosition(p.getState().getRow(), p.getState().getRow());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * @param generateMazeListener the generateMazeListener to set
	 */
	public void setGenerateMazeListener(SelectionListener generateMazeListener) {
		this.generateMazeListener = generateMazeListener;
	}

	/**
	 * @return the clueListener
	 */
	public SelectionListener getClueListener() {
		return clueListener;
	}

	/**
	 * @param clueListener the clueListener to set
	 */
	public void setClueListener(SelectionListener clueListener) {
		this.clueListener = clueListener;
	}

	/**
	 * @return the solveListener
	 */
	public SelectionListener getSolveListener() {
		return solveListener;
	}

	/**
	 * @param solveListener the solveListener to set
	 */
	public void setSolveListener(SelectionListener solveListener) {
		this.solveListener = solveListener;
	}

	/**
	 * @return the saveMazeListener
	 */
	public SelectionListener getSaveMazeListener() {
		return saveMazeListener;
	}

	/**
	 * @param saveMazeListener the saveMazeListener to set
	 */
	public void setSaveMazeListener(SelectionListener saveMazeListener) {
		this.saveMazeListener = saveMazeListener;
	}

	/**
	 * @return the loadMazeListener
	 */
	public SelectionListener getLoadMazeListener() {
		return loadMazeListener;
	}

	/**
	 * @param loadMazeListener the loadMazeListener to set
	 */
	public void setLoadMazeListener(SelectionListener loadMazeListener) {
		this.loadMazeListener = loadMazeListener;
	}

	/**
	 * @return the exitListener
	 */
	public DisposeListener getExitListener() {
		return exitListener;
	}

	/**
	 * @param exitListener the exitListener to set
	 */
	public void setExitListener(DisposeListener exitListener) {
		this.exitListener = exitListener;
	}

	/**
	 * @return the mazeName
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * @param mazeName the mazeName to set
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * @return the fileToSave
	 */
	public String getFileToSave() {
		return fileToSave;
	}

	/**
	 * @param fileToSave the fileToSave to set
	 */
	public void setFileToSave(String fileToSave) {
		this.fileToSave = fileToSave;
	}

	/**
	 * @return the filePathToLoad
	 */
	public String getFilePathToLoad() {
		return filePathToLoad;
	}

	/**
	 * @param filePathToLoad the filePathToLoad to set
	 */
	public void setFilePathToLoad(String filePathToLoad) {
		this.filePathToLoad = filePathToLoad;
	}


	/**
	 * @return the numOfRows
	 */
	public String getNumOfRows() {
		return numOfRows;
	}

	/**
	 * @return the numOfColumns
	 */
	public String getNumOfColumns() {
		return numOfColumns;
	}

	/**
	 * @return the numOfFloors
	 */
	public String getNumOfFloors() {
		return numOfFloors;
	}

	/**
	 * @return the loadFunctionNewMazeName
	 */
	public String getLoadFunctionNewMazeName() {
		return loadFunctionNewMazeName;
	}

	/**
	 * @param loadFunctionNewMazeName the loadFunctionNewMazeName to set
	 */
	public void setLoadFunctionNewMazeName(String loadFunctionNewMazeName) {
		this.loadFunctionNewMazeName = loadFunctionNewMazeName;
	}
	

	/**
	 * @param numOfRows the numOfRows to set
	 */
	public void setNumOfRows(String numOfRows) {
		this.numOfRows = numOfRows;
	}

	/**
	 * @param numOfColumns the numOfColumns to set
	 */
	public void setNumOfColumns(String numOfColumns) {
		this.numOfColumns = numOfColumns;
	}

	/**
	 * @param numOfFloors the numOfFloors to set
	 */
	public void setNumOfFloors(String numOfFloors) {
		this.numOfFloors = numOfFloors;
	}

	
	
}
