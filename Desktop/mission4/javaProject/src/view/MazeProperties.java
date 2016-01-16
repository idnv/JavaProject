package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MazeProperties extends BasicWindow {

	private String mazeName, numOfRows, numOfColumns, numOfFloors;
	private boolean changeSucceeded;
	
	public MazeProperties(String title, int width, int height, Shell parent) {
		super(title, width, height, parent);
		this.changeSucceeded = false;
	}
	
	
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2,false));
				// present's class name
				Label titel = new Label(shell,SWT.CENTER);		
				titel.setText("Fill all Fields of Maze Properties");
				titel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
				
				Label mazeNameLable = new Label(shell, SWT.READ_ONLY);
				mazeNameLable.setText("Maze Name: ");
				mazeNameLable.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
				
				Text mazeNameData = new Text(shell, SWT.BORDER);
				mazeNameData.setLayoutData(new GridData(SWT.FILL ,SWT.FILL ,true ,false ,1 ,1));
				
				Label numberOfFloors = new Label(shell, SWT.READ_ONLY);
				numberOfFloors.setText("Number Of Floors: ");
				numberOfFloors.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
				
				Text numberOfFloorsData = new Text(shell, SWT.BORDER);
				numberOfFloorsData.setLayoutData(new GridData(SWT.FILL ,SWT.FILL ,true ,false ,1 ,1));
				
				Label numberOfRows = new Label(shell, SWT.READ_ONLY);
				numberOfRows.setText("Number Of Rows: ");
				numberOfRows.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
				
				Text numberOfRowsData = new Text(shell, SWT.BORDER);
				numberOfRowsData.setLayoutData(new GridData(SWT.FILL ,SWT.FILL ,true ,false ,1 ,1));
				
				Label numberOfColumns = new Label(shell, SWT.READ_ONLY);
				numberOfColumns.setText("Number Of Columns: ");
				numberOfColumns.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
				
				Text numberOfColumnsData = new Text(shell, SWT.BORDER);
				numberOfColumnsData.setLayoutData(new GridData(SWT.FILL ,SWT.FILL ,true ,false ,1 ,1));
				
				Button saveButton = new Button(shell, SWT.PUSH);
				saveButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
				saveButton.setText("Save and Generate");
				
				saveButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						if(!numberOfFloorsData.getText().equals(" ") && !numberOfRowsData.getText().equals(" ") && !numberOfColumnsData.getText().equals("") && !mazeNameData.getText().equals(" ")){
							if(numberOfFloorsData.getText().matches("[0-9]+") &&  numberOfRowsData.getText().matches("[0-9]+") && numberOfColumnsData.getText().matches("[0-9]+")){
									numOfColumns =  numberOfColumnsData.getText();
									numOfFloors = numberOfFloorsData.getText();
									numOfRows = numberOfRowsData.getText();
									mazeName = mazeNameData.getText();
									changeSucceeded = true;
							}
							else{
								setEror("Ilegal Fields Input");
							}
						}	
						else{
							setEror("All fields should be filled");
						}	
						shell.close();
					}
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
	}


	/**
	 * @return the mazeName
	 */
	public String getMazeName() {
		return mazeName;
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
	 * @return changeSucceeded, true if all input is legal
	 * false id not
	 */
	public boolean isChangeSucceeded() {
		return changeSucceeded;
	}


	/**
	 * @return the numOfFloors
	 */
	public String getNumOfFloors() {
		return numOfFloors;
	}
	private void setEror(String eror){
		display.getCurrent().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox errorBox =  new MessageBox(shell, SWT.ICON_ERROR); 
				errorBox.setMessage(eror);
				errorBox.setText("Error");
				errorBox.open();				
			}
		});
	}
	
}
